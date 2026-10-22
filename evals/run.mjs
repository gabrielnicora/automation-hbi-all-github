// Evaluador del agente de automatización (SPEC.md, sección 3, "Evaluación del agente").
// Uso: pnpm run eval -- <tarea> [--model <modelo>] [--budget <usd>] [--keep] [--skip-precheck]
// Por cada tarea: login UI de control contra UAT (qa) → worktree descartable sin el caso →
// agente sin interacción → corrección automática → ejecución del caso producido.
import { spawnSync } from 'node:child_process';
import {
  existsSync,
  mkdirSync,
  mkdtempSync,
  readFileSync,
  realpathSync,
  rmSync,
  symlinkSync,
  writeFileSync,
} from 'node:fs';
import { createRequire } from 'node:module';
import os from 'node:os';
import path from 'node:path';
import ts from 'typescript';

const ROOT = process.cwd();
const PLAYWRIGHT_CLI = createRequire(import.meta.url).resolve('@playwright/test/cli');
const PRECHECK = {
  grep: 'TC0220001BERSA',
  project: 'ui',
  spec: 'tests/ui/authentication/should-login-and-capture-bearer.ui.spec.ts',
};
const AGENT_TOOLS = [
  'Read',
  'Edit',
  'Write',
  'Glob',
  'Grep',
  'Bash(pnpm run check)',
  'Bash(pnpm run agent:index)',
  'Bash(pnpm run lint:*)',
  'Bash(pnpm run typecheck)',
  'Bash(pnpm exec playwright test --list:*)',
  'Bash(pnpm exec prettier:*)',
  'Bash(pnpm exec eslint:*)',
  'Bash(node scripts/agent-index.mjs:*)',
  'Bash(git status:*)',
  'Bash(git diff:*)',
  'Bash(ls:*)',
];
// En el test producido: nada de selectores, URLs, esperas fijas ni configuración (SPEC.md §6.1).
const FORBIDDEN_IN_TEST = [
  { name: 'locator', pattern: /\.locator\(/ },
  { name: 'getBy*', pattern: /\bgetBy[A-Z]\w*\(/ },
  { name: 'URL', pattern: /https?:\/\// },
  { name: 'waitForTimeout', pattern: /waitForTimeout/ },
  { name: 'networkidle', pattern: /networkidle/ },
  { name: 'page directo', pattern: /\bpage\.\w+/ },
  { name: 'process.env', pattern: /process\.env/ },
];

// ---------------------------------------------------------------------------------------
// Utilidades
// ---------------------------------------------------------------------------------------

function parseArguments(argv) {
  const options = { budget: '5', keep: false, model: undefined, skipPrecheck: false, tasks: [] };

  for (let index = 0; index < argv.length; index++) {
    const value = argv[index];
    if (value === '--keep') options.keep = true;
    else if (value === '--skip-precheck') options.skipPrecheck = true;
    else if (value === '--model') options.model = argv[++index];
    else if (value === '--budget') options.budget = argv[++index];
    else if (value !== '--') options.tasks.push(value);
  }

  if (options.tasks.length === 0) {
    throw new Error('Indicar al menos una tarea de evals/tasks/ (por ejemplo tc0033-mastercard-detail-ui).');
  }

  return options;
}

/** Ambiente UAT = qa con URLs por defecto: se descartan overrides locales (SPEC.md §1). */
function uatEnvironment() {
  const environment = { ...process.env, FORCE_COLOR: '0', PW_RETRIES: '0', PW_WORKERS: '1', TEST_ENV: 'qa' };
  delete environment.UI_BASE_URL;
  delete environment.API_BASE_URL;
  delete environment.CI;
  return environment;
}

/**
 * El agente usa el login propio del CLI `claude`. Si el runner se lanza desde otra sesión de
 * Claude (app de escritorio, SDK), sus variables de sesión harían que el agente intente
 * autenticarse por ese canal y falle; se quitan sólo para el proceso del agente.
 */
function agentEnvironment() {
  return Object.fromEntries(Object.entries(uatEnvironment()).filter(([name]) => !/^(ANTHROPIC_|CLAUDE)/.test(name)));
}

function run(command, args, { cwd = ROOT, env = uatEnvironment(), input, logFile, timeoutMs = 15 * 60_000 } = {}) {
  const result = spawnSync(command, args, {
    cwd,
    encoding: 'utf8',
    env,
    input,
    maxBuffer: 256 * 1024 * 1024,
    timeout: timeoutMs,
  });
  const output = `${result.stdout ?? ''}${result.stderr ?? ''}`;

  if (logFile) {
    writeFileSync(logFile, output);
  }

  return { ok: result.status === 0, output, status: result.status, stdout: result.stdout ?? '' };
}

function git(cwd, ...args) {
  const result = run('git', args, { cwd });
  if (!result.ok) {
    throw new Error(`git ${args.join(' ')} falló:\n${result.output}`);
  }
  return result.stdout.trim();
}

// ---------------------------------------------------------------------------------------
// Preparación del worktree
// ---------------------------------------------------------------------------------------

function describeCallsContaining(sourceFile, marker) {
  const matches = [];

  for (const statement of sourceFile.statements) {
    if (
      ts.isExpressionStatement(statement) &&
      ts.isCallExpression(statement.expression) &&
      statement.expression.expression.getText(sourceFile) === 'test.describe' &&
      statement.getText(sourceFile).includes(marker)
    ) {
      matches.push(statement);
    }
  }

  return matches;
}

function removeDescribe(worktree, { describeContaining, file }) {
  const filePath = path.join(worktree, file);
  const text = readFileSync(filePath, 'utf8');
  const sourceFile = ts.createSourceFile(filePath, text, ts.ScriptTarget.Latest, true);
  const matches = describeCallsContaining(sourceFile, describeContaining);

  if (matches.length !== 1) {
    throw new Error(`Se esperaba un test.describe con ${describeContaining} en ${file}; hay ${matches.length}.`);
  }

  const [statement] = matches;
  const updated = `${text.slice(0, statement.getFullStart()).trimEnd()}\n${text.slice(statement.getEnd()).replace(/^\n+/, '\n')}`;
  writeFileSync(filePath, updated.replace(/\n{3,}/g, '\n\n'));
}

/** Reemplazo exacto y único: simula un cambio de pantalla rompiendo un locator (tareas de reparación). */
function applyReplacement(worktree, { file, find, replace }) {
  const filePath = path.join(worktree, file);
  const text = readFileSync(filePath, 'utf8');
  const occurrences = text.split(find).length - 1;

  if (occurrences !== 1) {
    throw new Error(`Se esperaba una ocurrencia del texto a reemplazar en ${file}; hay ${occurrences}.`);
  }

  writeFileSync(filePath, text.replace(find, replace));
}

function prepareWorktree(task) {
  // Copia de HEAD sin historial: con un worktree, `git log`/`git show` revelan el caso quitado.
  const worktree = path.join(realpathSync(mkdtempSync(path.join(os.tmpdir(), 'hbi-eval-'))), 'repo');
  mkdirSync(worktree);
  const archive = spawnSync('git', ['archive', '--format=tar', 'HEAD'], { cwd: ROOT, maxBuffer: 1024 * 1024 * 1024 });
  if (archive.status !== 0) {
    throw new Error(`git archive falló: ${archive.stderr}`);
  }
  const extract = spawnSync('tar', ['-x', '-C', worktree], { input: archive.stdout });
  if (extract.status !== 0) {
    throw new Error(`No se pudo extraer la copia de HEAD: ${extract.stderr}`);
  }
  git(worktree, 'init', '-q');
  symlinkSync(path.join(ROOT, 'node_modules'), path.join(worktree, 'node_modules'));

  for (const removal of task.remove ?? []) {
    removeDescribe(worktree, removal);
  }

  for (const replacement of task.replace ?? []) {
    applyReplacement(worktree, replacement);
  }

  // Regenerar el catálogo evita que el agente encuentre la respuesta en .agents/index/.
  const index = run(process.execPath, ['scripts/agent-index.mjs'], { cwd: worktree });
  if (!index.ok) {
    throw new Error(`No se pudo regenerar el catálogo en el worktree:\n${index.output}`);
  }

  // node_modules es un symlink al repositorio principal: no forma parte del caso.
  git(worktree, 'add', '-A', '--', '.', ':!node_modules');
  git(
    worktree,
    '-c',
    'user.name=eval',
    '-c',
    'user.email=eval@localhost',
    'commit',
    '-q',
    '--no-verify',
    '-m',
    `eval: ${task.id}`,
  );
  return worktree;
}

// ---------------------------------------------------------------------------------------
// Agente
// ---------------------------------------------------------------------------------------

function runAgent(task, worktree, runDir, options) {
  const args = [
    '-p',
    task.prompt.join('\n'),
    '--output-format',
    'stream-json',
    '--verbose',
    '--permission-mode',
    'dontAsk',
    '--no-session-persistence',
    '--max-budget-usd',
    options.budget,
    '--allowedTools',
    ...AGENT_TOOLS,
    // Sólo en tareas de reparación: los specs de consulta que la tarea permite ejecutar.
    ...(task.agentMayRun ?? []).flatMap((spec) => [
      `Bash(PW_RETRIES=0 pnpm exec playwright test ${spec}:*)`,
      `Bash(pnpm exec playwright test ${spec}:*)`,
    ]),
  ];

  if (options.model) {
    args.push('--model', options.model);
  }

  const started = Date.now();
  const result = run('claude', args, {
    cwd: worktree,
    env: agentEnvironment(),
    logFile: path.join(runDir, 'transcript.jsonl'),
    timeoutMs: 45 * 60_000,
  });
  return { durationMs: Date.now() - started, ok: result.ok, status: result.status, transcript: result.stdout };
}

function summarizeTranscript(transcript, worktree) {
  const events = transcript
    .split('\n')
    .filter((line) => line.trim().startsWith('{'))
    .map((line) => {
      try {
        return JSON.parse(line);
      } catch {
        return undefined;
      }
    })
    .filter(Boolean);
  const toolUses = events
    .filter((event) => event.type === 'assistant')
    .flatMap((event) => event.message?.content ?? [])
    .filter((block) => block.type === 'tool_use');
  const toPath = (file) => path.relative(worktree, path.resolve(worktree, file));
  // Archivos consultados con Read o mencionados en comandos Bash (cat, sed, grep…), en orden.
  const REPO_PATH =
    /(?:^|[\s'"=])((?:\.agents|\.claude|src|tests|specs|docs|test-data|scripts|evals)\/[\w./-]+|[A-Z][\w-]*\.md|package\.json|\.env[\w.]*)/g;
  const mentions = toolUses.flatMap((use) => {
    if (use.name === 'Read' && use.input?.file_path) return [toPath(use.input.file_path)];
    if (use.name === 'Bash') return [...String(use.input?.command ?? '').matchAll(REPO_PATH)].map((match) => match[1]);
    return [];
  });
  const filesRead = [...new Set(mentions.filter((file) => !file.startsWith('..')))];
  const firstIndexRead = filesRead.findIndex((file) => file.startsWith('.agents/index/'));
  const firstCodeRead = filesRead.findIndex((file) => /^(src|tests)\/.*\.ts$/.test(file));
  const historyAccess = toolUses.some(
    (use) => use.name === 'Bash' && /\bgit\s+(log|show|blame|reflog)\b/.test(String(use.input?.command)),
  );
  const envAccess = toolUses.some((use) => {
    const text = JSON.stringify(use.input ?? {});
    return /\.env(?!\.example)\b/.test(text);
  });
  const result = events.findLast((event) => event.type === 'result') ?? {};
  const toolCounts = {};
  for (const use of toolUses) {
    toolCounts[use.name] = (toolCounts[use.name] ?? 0) + 1;
  }

  return {
    commands: toolUses.filter((use) => use.name === 'Bash').map((use) => use.input?.command),
    costUsd: result.total_cost_usd,
    envAccess,
    filesRead,
    historyAccess,
    finalReport: result.result ?? '',
    indexBeforeCode: firstIndexRead !== -1 && (firstCodeRead === -1 || firstIndexRead < firstCodeRead),
    isError: Boolean(result.is_error),
    stopReason: result.subtype,
    toolCounts,
    turns: result.num_turns,
    usage: result.usage,
  };
}

// ---------------------------------------------------------------------------------------
// Corrección
// ---------------------------------------------------------------------------------------

function changedFiles(worktree) {
  // -z conserva las rutas tal cual: sin comillas ni recortes del formato de texto.
  const status = run('git', ['status', '--porcelain=v1', '-z', '--untracked-files=all'], { cwd: worktree }).stdout;
  return status
    .split('\0')
    .filter((entry) => entry.length > 3)
    .map((entry) => entry.slice(3))
    .filter((file) => file !== 'node_modules');
}

function findListedTest(worktree, expected) {
  const list = run(process.execPath, [PLAYWRIGHT_CLI, 'test', '--list', '--reporter=json'], { cwd: worktree });
  if (!list.ok) {
    return { error: list.output.slice(0, 2000) };
  }

  const report = JSON.parse(list.stdout);
  const found = [];
  const walk = (suite) => {
    for (const spec of suite.specs ?? []) {
      if (spec.title.includes(expected.testCaseId)) {
        found.push({
          file: path.relative(worktree, path.join(report.config.rootDir, spec.file)),
          projects: spec.tests.map((test) => test.projectName),
          title: spec.title,
        });
      }
    }
    (suite.suites ?? []).forEach(walk);
  };
  report.suites.forEach(walk);
  return { found };
}

function fixturesOfCase(fileText, testCaseId) {
  const sourceFile = ts.createSourceFile('case.ts', fileText, ts.ScriptTarget.Latest, true);
  const names = new Set();

  const visit = (node, insideCase) => {
    if (ts.isCallExpression(node)) {
      const callee = node.expression.getText(sourceFile);
      const isDescribe = callee === 'test.describe';
      const nowInside = insideCase || (isDescribe && node.getText(sourceFile).includes(testCaseId));
      const callback = node.arguments.at(-1);

      if (/^test(\.only)?$/.test(callee) && nowInside && callback && ts.isFunctionLike(callback)) {
        const parameter = callback.parameters[0];
        if (parameter && ts.isObjectBindingPattern(parameter.name)) {
          parameter.name.elements.forEach((element) =>
            names.add((element.propertyName ?? element.name).getText(sourceFile)),
          );
        }
      }

      ts.forEachChild(node, (child) => visit(child, nowInside));
      return;
    }
    ts.forEachChild(node, (child) => visit(child, insideCase));
  };

  visit(sourceFile, !fileText.includes('test.describe'));
  return [...names].sort();
}

function addedLinesOf(worktree, file) {
  return git(worktree, 'diff', '-U0', 'HEAD', '--', file)
    .split('\n')
    .filter((line) => line.startsWith('+') && !line.startsWith('+++'))
    .join('\n');
}

/** Specs que el agente ejecutó contra el banco (excluye `--list`). */
function specsRunByAgent(commands) {
  return (commands ?? [])
    .filter((command) => /playwright\s+test\b/.test(command) && !/--list\b/.test(command))
    .map((command) => ({ command, specs: command.match(/tests\/[\w./-]+\.spec\.ts/g) ?? [] }));
}

const LOCATOR_STRATEGIES = [
  { name: 'getByRole', pattern: /\bgetByRole\(/ },
  { name: 'getByLabel', pattern: /\bgetByLabel\(/ },
  { name: 'getByText', pattern: /\bgetByText\(/ },
  { name: 'getByPlaceholder', pattern: /\bgetByPlaceholder\(/ },
  { name: 'getByTestId', pattern: /\bgetByTestId\(/ },
  { name: 'CSS', pattern: /\.locator\(\s*['"`](?!xpath=|\/\/)/ },
  { name: 'XPath', pattern: /xpath=|\.locator\(\s*['"`]\(?\/\// },
];

function gradeLocatorRepair(worktree, repair, add) {
  const text = readFileSync(path.join(worktree, repair.file), 'utf8');
  const added = addedLinesOf(worktree, repair.file);
  const strategies = LOCATOR_STRATEGIES.filter((strategy) => strategy.pattern.test(added)).map((s) => s.name);
  const waits = /waitForTimeout|networkidle|setTimeout\(/.test(added);

  add('Locator roto reemplazado', !text.includes(repair.brokenMarker), `marcador: ${repair.brokenMarker}`);
  add(
    'Locator nuevo sin XPath ni esperas fijas',
    added.length > 0 && !strategies.includes('XPath') && !waits,
    `estrategias en líneas nuevas: ${strategies.join(', ') || 'ninguna'}${waits ? ' | agrega esperas fijas' : ''}`,
  );
  add(
    'Usa un locator accesible (prioridad de src/pages/AGENTS.md)',
    strategies.some((name) => name.startsWith('getBy')),
    strategies.join(', ') || 'ninguna',
  );
}

function grade(task, worktree, runDir, agent) {
  const { expected } = task;
  const changed = changedFiles(worktree);
  const diff = git(worktree, 'diff', 'HEAD');
  const untracked = changed.filter((file) => !diff.includes(`b/${file}`));
  writeFileSync(
    path.join(runDir, 'diff.patch'),
    `${diff}\n${untracked.map((file) => `\n=== archivo nuevo: ${file} ===\n${readFileSync(path.join(worktree, file), 'utf8')}`).join('')}`,
  );

  const checks = [];
  const add = (name, ok, detail = '') => checks.push({ detail, name, ok });

  add('El agente modificó archivos', changed.length > 0, changed.join(', '));
  const outside = changed.filter((file) => !expected.allowedPaths.some((allowed) => file.startsWith(allowed)));
  add(
    'Sólo rutas permitidas',
    outside.length === 0,
    outside.length ? `fuera: ${outside.join(', ')}` : expected.allowedPaths.join(', '),
  );

  const check = run('pnpm', ['run', 'check'], { cwd: worktree, logFile: path.join(runDir, 'check.log') });
  add('pnpm run check', check.ok, check.ok ? '' : 'ver check.log');

  const listed = findListedTest(worktree, expected);
  const matches = (listed.found ?? []).filter((item) => item.projects.includes(expected.project));
  add(
    `--list incluye ${expected.testCaseId} (${expected.project})`,
    matches.length === 1,
    listed.error ?? matches.map((m) => `${m.file}: ${m.title}`).join(' | '),
  );
  add(
    'Título comienza con el ID',
    matches.length === 1 && matches[0].title.startsWith(`${expected.testCaseId} - `),
    matches[0]?.title ?? '',
  );

  const testFile = matches[0]?.file;
  const addedLines = testFile
    ? git(worktree, 'diff', '-U0', 'HEAD', '--', testFile)
        .split('\n')
        .filter((line) => line.startsWith('+') && !line.startsWith('+++'))
        .join('\n') || readFileSync(path.join(worktree, testFile), 'utf8')
    : '';
  const forbidden = FORBIDDEN_IN_TEST.filter((rule) => rule.pattern.test(addedLines)).map((rule) => rule.name);
  add(
    'Test sin selectores, URLs ni esperas fijas',
    testFile !== undefined && forbidden.length === 0,
    forbidden.join(', '),
  );
  add('Declara testCaseId del caso', /testCaseId/.test(addedLines) && addedLines.includes(expected.testCaseId));

  if (expected.fixtures) {
    const fixtures = testFile
      ? fixturesOfCase(readFileSync(path.join(worktree, testFile), 'utf8'), expected.testCaseId)
      : [];
    const missing = expected.fixtures.filter((name) => !fixtures.includes(name));
    const extra = fixtures.filter((name) => !expected.fixtures.includes(name));
    add(
      'Fixtures esperadas',
      missing.length === 0,
      `usadas: ${fixtures.join(', ')}${missing.length ? ` | faltan: ${missing.join(', ')}` : ''}${extra.length ? ` | extra: ${extra.join(', ')}` : ''}`,
    );
  }

  for (const file of expected.unchangedPaths ?? []) {
    const touched = changed.filter((changedFile) => changedFile.startsWith(file));
    add(`Sin cambios en ${file}`, touched.length === 0, touched.join(', '));
  }

  if (expected.locatorRepair) {
    gradeLocatorRepair(worktree, expected.locatorRepair, add);
  }

  const allowedRuns = task.agentMayRun ?? [];
  const runs = specsRunByAgent(agent?.commands);
  const forbiddenRuns = runs.filter(
    (item) => item.specs.length === 0 || item.specs.some((spec) => !allowedRuns.includes(spec)),
  );
  add(
    'El agente sólo ejecutó specs permitidos',
    forbiddenRuns.length === 0,
    forbiddenRuns.length
      ? `no permitidos: ${forbiddenRuns.map((item) => item.command.slice(0, 120)).join(' | ')}`
      : `${runs.length} ejecución(es) de: ${allowedRuns.join(', ') || 'ninguno'}`,
  );

  const existing = changed.filter((file) => existsSync(path.join(worktree, file)));
  const secrets = existing.length
    ? run(process.execPath, ['scripts/check-secrets.mjs', ...existing], { cwd: worktree })
    : { ok: true, output: '' };
  add('Escaneo de secretos del cambio', secrets.ok, secrets.ok ? '' : secrets.output.trim());

  return { changed, checks, testFile };
}

// ---------------------------------------------------------------------------------------
// Reporte
// ---------------------------------------------------------------------------------------

function renderReport(report) {
  const mark = (ok) => (ok ? '✅' : '❌');
  const agent = report.agent ?? {};
  const lines = [
    `# Evaluación ${report.task} — ${report.status}`,
    '',
    `- Commit evaluado: \`${report.commit}\` · ambiente: UAT (qa, URLs por defecto)`,
    `- Login de control TC0220001BERSA: ${report.precheck}`,
  ];

  if (report.agent) {
    lines.push(
      `- Agente: ${agent.turns ?? '?'} turnos, ${agent.costUsd !== undefined ? `US$ ${agent.costUsd.toFixed(2)}` : 'costo ?'}, ${Math.round((report.agentDurationMs ?? 0) / 1000)} s, fin: ${agent.stopReason ?? '?'}`,
      `- Herramientas: ${Object.entries(agent.toolCounts ?? {})
        .map(([name, count]) => `${name} ${count}`)
        .join(', ')}`,
      `- Archivos consultados (${agent.filesRead.length}): ${agent.filesRead.map((file) => `\`${file}\``).join(', ')}`,
      `- Consultó el catálogo antes del código: ${agent.indexBeforeCode ? 'sí' : 'no'} · accedió a .env: ${agent.envAccess ? 'sí' : 'no'} · usó el historial de Git: ${agent.historyAccess ? 'sí' : 'no'}`,
    );
  }

  if (report.grade) {
    lines.push('', '## Corrección', '', '| | Control | Detalle |', '| --- | --- | --- |');
    for (const item of report.grade.checks) {
      lines.push(
        `| ${mark(item.ok)} | ${item.name} | ${String(item.detail).replace(/\|/g, '\\|').replace(/\n/g, ' ')} |`,
      );
    }
  }

  if (report.execution) {
    lines.push('', `## Ejecución contra UAT`, '', `${mark(report.execution.ok)} ${report.execution.summary}`);
  }

  if (report.agent?.finalReport) {
    lines.push('', '## Reporte final del agente', '', report.agent.finalReport);
  }

  return `${lines.join('\n')}\n`;
}

function evaluate(taskId, options) {
  const task = JSON.parse(readFileSync(path.join(ROOT, 'evals', 'tasks', `${taskId}.json`), 'utf8'));
  const stamp = new Date().toISOString().replace(/[:.]/g, '-');
  const runDir = path.join(ROOT, 'evals', 'results', `${stamp}-${task.id}`);
  mkdirSync(runDir, { recursive: true });
  const report = { commit: git(ROOT, 'rev-parse', '--short', 'HEAD'), status: 'en curso', task: task.id };
  const save = () => {
    writeFileSync(path.join(runDir, 'report.json'), `${JSON.stringify(report, null, 2)}\n`);
    writeFileSync(path.join(runDir, 'report.md'), renderReport(report));
  };

  if (git(ROOT, 'status', '--porcelain')) {
    console.warn('Aviso: hay cambios sin commitear; el worktree evalúa HEAD sin ellos.');
  }

  if (options.skipPrecheck) {
    report.precheck = 'omitido';
  } else {
    console.log(`[${task.id}] Login de control TC0220001BERSA contra UAT…`);
    const precheck = run(
      process.execPath,
      [
        PLAYWRIGHT_CLI,
        'test',
        PRECHECK.spec,
        '--grep',
        PRECHECK.grep,
        `--project=${PRECHECK.project}`,
        '--reporter=line',
      ],
      { logFile: path.join(runDir, 'precheck.log') },
    );
    report.precheck = precheck.ok ? 'ok' : 'falló (ver precheck.log)';
    if (!precheck.ok) {
      report.status = 'ambiente no disponible';
      save();
      return report;
    }
  }

  console.log(`[${task.id}] Preparando worktree sin el caso…`);
  const worktree = prepareWorktree(task);

  try {
    console.log(`[${task.id}] Ejecutando el agente (puede tardar varios minutos)…`);
    const agent = runAgent(task, worktree, runDir, options);
    report.agentDurationMs = agent.durationMs;
    report.agent = summarizeTranscript(agent.transcript, worktree);

    if (report.agent.isError || report.agent.turns === undefined) {
      // Sin una corrida válida del agente no hay nada que corregir (autenticación, presupuesto, etc.).
      report.status = 'agente no ejecutado';
      return report;
    }

    console.log(`[${task.id}] Corrigiendo…`);
    report.grade = grade(task, worktree, runDir, report.agent);

    if (task.expected.run && report.grade.testFile) {
      console.log(`[${task.id}] Ejecutando ${task.expected.testCaseId} contra UAT…`);
      const execution = run(
        process.execPath,
        [
          PLAYWRIGHT_CLI,
          'test',
          report.grade.testFile,
          '--grep',
          task.expected.testCaseId,
          `--project=${task.expected.project}`,
          '--reporter=line',
        ],
        { cwd: worktree, logFile: path.join(runDir, 'execution.log') },
      );

      const summaryLine = execution.output
        .replace(/\u001b\[[0-9;]*[A-Za-z]/g, '')
        .split('\n')
        .filter((line) => /\d+ (passed|failed|flaky|skipped)/.test(line));
      report.execution = { ok: execution.ok, summary: summaryLine.join(' · ') || 'sin resumen (ver execution.log)' };
    }

    const allChecks = report.grade.checks.every((item) => item.ok) && (report.execution?.ok ?? true);
    report.status = allChecks ? 'aprobado' : 'con fallos';
  } finally {
    save();
    if (options.keep) {
      console.log(`Copia de trabajo conservada en ${worktree}`);
    } else {
      rmSync(path.dirname(worktree), { force: true, recursive: true });
    }
  }

  return report;
}

const options = parseArguments(process.argv.slice(2));
let failed = false;

for (const taskId of options.tasks) {
  const report = evaluate(taskId, options);
  console.log(`[${report.task}] ${report.status} → evals/results/ (report.md)`);
  failed ||= report.status !== 'aprobado';
}

process.exit(failed ? 1 : 0);
