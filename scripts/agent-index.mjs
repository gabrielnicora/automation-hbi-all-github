// Genera el catálogo de .agents/index/ para que un agente ubique fixtures, Page Objects,
// clientes API, workflows, loaders, tests y campos de datos sin leer el código (SPEC.md,
// sección 3). Uso: `node scripts/agent-index.mjs` para regenerar, `--check` para fallar si
// el catálogo versionado está desactualizado. De test-data sólo publica nombres de campo.
import { execFileSync } from 'node:child_process';
import { existsSync, mkdirSync, readdirSync, readFileSync, writeFileSync } from 'node:fs';
import { createRequire } from 'node:module';
import path from 'node:path';
import ts from 'typescript';

const ROOT = process.cwd();
const OUTPUT_DIR = path.join(ROOT, '.agents', 'index');
const FIXTURE_FILES = ['src/fixtures/test-case.fixture.ts', 'src/fixtures/api-test.ts', 'src/fixtures/ui-test.ts'];
const NO_DATA_PLACEHOLDER = 'sindatos';
const HEADER = '<!-- Generado por `pnpm run agent:index`. No editar a mano. -->';

const toPosix = (file) => file.split(path.sep).join('/');
const relative = (file) => toPosix(path.relative(ROOT, file));
const byText = (left, right) => left.localeCompare(right, 'en');
const oneLine = (text) => text.replace(/\s+/g, ' ').trim();
const cell = (text) => oneLine(text).replace(/\|/g, '\\|');

function listFiles(directory, suffix) {
  if (!existsSync(directory)) {
    return [];
  }

  return readdirSync(directory, { recursive: true, withFileTypes: true })
    .filter((entry) => entry.isFile() && entry.name.endsWith(suffix))
    .map((entry) => path.join(entry.parentPath, entry.name))
    .sort(byText);
}

// ---------------------------------------------------------------------------------------
// Programa TypeScript
// ---------------------------------------------------------------------------------------

function createProgram(files) {
  const configPath = path.join(ROOT, 'tsconfig.json');
  const { config } = ts.readConfigFile(configPath, ts.sys.readFile);
  const { options } = ts.parseJsonConfigFileContent(config, ts.sys, ROOT);
  return ts.createProgram(files, { ...options, noEmit: true });
}

function firstDocLine(node) {
  const docs = ts.getJSDocCommentsAndTags(node).filter(ts.isJSDoc);
  const comment = docs.length > 0 ? ts.getTextOfJSDocComment(docs[0].comment) : undefined;
  return comment ? oneLine(comment.split(/\n\s*\n/)[0]) : '';
}

function isPublicMember(member) {
  const flags = ts.getCombinedModifierFlags(member);
  const isPrivateName = member.name && ts.isPrivateIdentifier(member.name);
  return !isPrivateName && !(flags & ts.ModifierFlags.Private) && !(flags & ts.ModifierFlags.Protected);
}

function isExported(node) {
  return (ts.getCombinedModifierFlags(node) & ts.ModifierFlags.Export) !== 0;
}

function parameterText(parameter) {
  const name = parameter.name.getText();
  const optional = parameter.questionToken || parameter.initializer ? '?' : '';
  const type = parameter.type ? `: ${oneLine(parameter.type.getText())}` : '';
  return `${parameter.dotDotDotToken ? '...' : ''}${name}${optional}${type}`;
}

function returnTypeText(checker, declaration) {
  if (declaration.type) {
    return oneLine(declaration.type.getText());
  }

  const signature = checker.getSignatureFromDeclaration(declaration);
  return signature ? oneLine(checker.typeToString(checker.getReturnTypeOfSignature(signature))) : 'void';
}

function describeClass(checker, node) {
  const members = [];
  let constructorText = 'constructor()';

  for (const member of node.members) {
    if (ts.isConstructorDeclaration(member)) {
      constructorText = `constructor(${member.parameters.map(parameterText).join(', ')})`;
      continue;
    }

    if (!isPublicMember(member) || !member.name) {
      continue;
    }

    const name = member.name.getText();

    if (ts.isMethodDeclaration(member)) {
      const parameters = member.parameters.map(parameterText).join(', ');
      members.push({
        doc: firstDocLine(member),
        text: `${name}(${parameters}): ${returnTypeText(checker, member)}`,
      });
    } else if (ts.isGetAccessorDeclaration(member)) {
      members.push({ doc: firstDocLine(member), text: `get ${name}: ${returnTypeText(checker, member)}` });
    }
  }

  return { constructorText, doc: firstDocLine(node), members, name: node.name?.getText() ?? '(anónima)' };
}

function describeModule(checker, sourceFile) {
  const classes = [];
  const functions = [];
  const types = [];

  for (const statement of sourceFile.statements) {
    if (!isExported(statement)) {
      continue;
    }

    if (ts.isClassDeclaration(statement)) {
      const description = describeClass(checker, statement);
      // Las clases de error sólo se mencionan: no son puntos de uso para un test.
      if (statement.heritageClauses?.some((clause) => clause.getText().includes('Error'))) {
        types.push(description.name);
      } else {
        classes.push(description);
      }
    } else if (ts.isFunctionDeclaration(statement) && statement.name) {
      const parameters = statement.parameters.map(parameterText).join(', ');
      functions.push({
        doc: firstDocLine(statement),
        text: `${statement.name.getText()}(${parameters}): ${returnTypeText(checker, statement)}`,
      });
    } else if (ts.isInterfaceDeclaration(statement) || ts.isTypeAliasDeclaration(statement)) {
      types.push(statement.name.getText());
    } else if (ts.isVariableStatement(statement)) {
      for (const declaration of statement.declarationList.declarations) {
        types.push(declaration.name.getText());
      }
    }
  }

  return { classes, functions, types: types.sort(byText) };
}

// ---------------------------------------------------------------------------------------
// Fixtures
// ---------------------------------------------------------------------------------------

function importedTypeSources(sourceFile) {
  const sources = new Map();

  for (const statement of sourceFile.statements) {
    if (!ts.isImportDeclaration(statement) || !statement.importClause) {
      continue;
    }

    const specifier = statement.moduleSpecifier.text;
    const resolved = specifier.startsWith('.')
      ? relative(path.resolve(path.dirname(sourceFile.fileName), specifier)) + '.ts'
      : specifier;
    const bindings = statement.importClause.namedBindings;

    if (bindings && ts.isNamedImports(bindings)) {
      for (const element of bindings.elements) {
        sources.set(element.name.getText(), resolved);
      }
    }
  }

  return sources;
}

function autoFixtureNames(sourceFile) {
  const names = new Set();

  const visit = (node) => {
    if (
      ts.isPropertyAssignment(node) &&
      ts.isArrayLiteralExpression(node.initializer) &&
      node.initializer.elements.some((element) => element.getText().includes('auto: true'))
    ) {
      names.add(node.name.getText());
    }
    ts.forEachChild(node, visit);
  };

  visit(sourceFile);
  return names;
}

function collectFixtures(program) {
  const fixtures = [];

  for (const file of FIXTURE_FILES) {
    const sourceFile = program.getSourceFile(path.join(ROOT, file));
    const sources = importedTypeSources(sourceFile);
    const autoNames = autoFixtureNames(sourceFile);

    for (const statement of sourceFile.statements) {
      if (!ts.isInterfaceDeclaration(statement)) {
        continue;
      }

      for (const member of statement.members) {
        if (!ts.isPropertySignature(member) || !member.type) {
          continue;
        }

        const typeName = oneLine(member.type.getText());
        fixtures.push({
          auto: autoNames.has(member.name.getText()),
          entry: file,
          name: member.name.getText(),
          source: sources.get(typeName) ?? (typeName === 'string' || typeName === 'void' ? '' : '@playwright/test'),
          typeName,
        });
      }
    }

    for (const name of autoNames) {
      if (!fixtures.some((fixture) => fixture.entry === file && fixture.name === name)) {
        fixtures.push({ auto: true, entry: file, name, source: '', typeName: 'void' });
      }
    }
  }

  return fixtures;
}

// ---------------------------------------------------------------------------------------
// Tests
// ---------------------------------------------------------------------------------------

function listPlaywrightTests() {
  const require = createRequire(import.meta.url);
  const cli = require.resolve('@playwright/test/cli');
  const output = execFileSync(process.execPath, [cli, 'test', '--list', '--reporter=json'], {
    cwd: ROOT,
    encoding: 'utf8',
    env: { ...process.env, FORCE_COLOR: '0' },
    maxBuffer: 64 * 1024 * 1024,
  });
  const report = JSON.parse(output);

  if (report.errors?.length) {
    throw new Error(`playwright test --list informó errores:\n${report.errors.map((e) => e.message).join('\n')}`);
  }

  const testDir = report.config.rootDir;
  const byFile = new Map();

  const walk = (suite) => {
    for (const spec of suite.specs ?? []) {
      const file = relative(path.join(testDir, spec.file));
      const entries = byFile.get(file) ?? [];
      entries.push({
        line: spec.line,
        projects: [...new Set(spec.tests.map((test) => test.projectName))].sort(byText),
        tags: spec.tags.map((tag) => `@${tag}`),
        title: spec.title,
      });
      byFile.set(file, entries);
    }
    for (const child of suite.suites ?? []) {
      walk(child);
    }
  };

  report.suites.forEach(walk);
  return byFile;
}

function fixturesUsedBySpec(sourceFile) {
  const names = new Set();

  const visit = (node) => {
    if (ts.isCallExpression(node)) {
      const callee = node.expression.getText();
      const callback = node.arguments.at(-1);

      if (
        /^test(\.(only|fail|fixme|skip))?$/.test(callee) &&
        callback &&
        (ts.isArrowFunction(callback) || ts.isFunctionExpression(callback))
      ) {
        const parameter = callback.parameters[0];

        if (parameter && ts.isObjectBindingPattern(parameter.name)) {
          for (const element of parameter.name.elements) {
            names.add((element.propertyName ?? element.name).getText());
          }
        }
      }
    }
    ts.forEachChild(node, visit);
  };

  visit(sourceFile);
  return [...names].sort(byText);
}

// ---------------------------------------------------------------------------------------
// Datos de prueba: sólo nombres de campo
// ---------------------------------------------------------------------------------------

function collectCaseFields() {
  const document = JSON.parse(readFileSync(path.join(ROOT, 'test-data', 'private', 'bersa.json'), 'utf8'));
  const rows = Object.entries(document.testCases ?? {}).sort(([left], [right]) => byText(left, right));

  return rows.map(([testCaseId, row]) => ({
    fields: Object.entries(row)
      .filter(([name]) => name !== 'tcName')
      .filter(
        ([, value]) => typeof value === 'string' && value.trim() && value.trim().toLowerCase() !== NO_DATA_PLACEHOLDER,
      )
      .map(([name]) => name)
      .sort(byText),
    testCaseId,
  }));
}

// ---------------------------------------------------------------------------------------
// Render
// ---------------------------------------------------------------------------------------

function renderModuleSection(file, module, fixturesByType) {
  const lines = [`### \`${file}\``, ''];

  for (const description of module.classes) {
    const fixtureNames = fixturesByType.get(description.name) ?? [];
    const via = fixtureNames.length > 0 ? ` — fixture ${fixtureNames.map((name) => `\`${name}\``).join(', ')}` : '';
    lines.push(`**${description.name}**${via}`);
    if (description.doc) {
      lines.push(`: ${description.doc}`);
    }
    lines.push('', `- \`${description.constructorText}\``);
    for (const member of description.members) {
      lines.push(`- \`${member.text}\`${member.doc ? ` — ${member.doc}` : ''}`);
    }
    lines.push('');
  }

  for (const fn of module.functions) {
    lines.push(`- \`${fn.text}\`${fn.doc ? ` — ${fn.doc}` : ''}`);
  }
  if (module.functions.length > 0) {
    lines.push('');
  }

  if (module.types.length > 0) {
    lines.push(`Otros exports: ${module.types.map((name) => `\`${name}\``).join(', ')}`, '');
  }

  return lines;
}

function renderModules(title, intro, files, program, checker, fixturesByType) {
  const lines = [HEADER, '', `# ${title}`, '', intro, ''];
  let currentArea = '';

  for (const file of files) {
    const relativeFile = relative(file);
    const module = describeModule(checker, program.getSourceFile(file));

    if (module.classes.length === 0 && module.functions.length === 0) {
      continue;
    }

    const area = relativeFile.split('/').slice(0, 3).join('/');
    if (area !== currentArea && !area.endsWith('.ts')) {
      lines.push(`## \`${area}/\``, '');
      currentArea = area;
    }
    lines.push(...renderModuleSection(relativeFile, module, fixturesByType));
  }

  return lines.join('\n').trimEnd() + '\n';
}

function renderFixtures(fixtures) {
  const lines = [
    HEADER,
    '',
    '# Fixtures',
    '',
    'Los tests importan `test` y `expect` de `src/fixtures/api-test.ts` o `src/fixtures/ui-test.ts`.',
    'Ambas entradas incluyen las fixtures de `test-case.fixture.ts`, que dependen de `testCaseId`',
    '(`test.use({ testCaseId })`). Las fixtures automáticas se aplican solas a cada test UI.',
    'Además están las nativas de Playwright (`page`, `context`, `request`).',
    '',
  ];

  for (const entry of FIXTURE_FILES) {
    lines.push(`## \`${entry}\``, '', '| Fixture | Tipo | Definido en |', '| --- | --- | --- |');
    for (const fixture of fixtures.filter((item) => item.entry === entry).sort((a, b) => byText(a.name, b.name))) {
      const name = fixture.auto ? `\`${fixture.name}\` (automática)` : `\`${fixture.name}\``;
      lines.push(`| ${name} | \`${cell(fixture.typeName)}\` | ${fixture.source ? `\`${fixture.source}\`` : '—'} |`);
    }
    lines.push('');
  }

  return lines.join('\n').trimEnd() + '\n';
}

function renderTests(testsByFile, program) {
  const specs = listFiles(path.join(ROOT, 'specs'), '.md').map(relative);
  const lines = [
    HEADER,
    '',
    '# Tests',
    '',
    'Fuente: `playwright test --list` (títulos resueltos, incluidos los parametrizados) y las',
    'fixtures que cada archivo pide en sus callbacks. Para un caso nuevo, buscar acá un test del',
    'mismo patrón y la especificación de la capacidad.',
    '',
  ];
  let currentArea = '';

  for (const file of [...testsByFile.keys()].sort(byText)) {
    const area = file.split('/').slice(0, 3).join('/');
    if (area !== currentArea) {
      // Carpeta <FFF>-<familia>: las especificaciones se agrupan por el nombre de la familia.
      const areaName = area.split('/')[2].replace(/^\d{3}-/, '');
      const areaSpecs = specs.filter((spec) => spec.startsWith(`specs/${areaName}/`));
      lines.push(`## \`${area}/\``, '');
      if (areaSpecs.length > 0) {
        lines.push(`Especificaciones: ${areaSpecs.map((spec) => `\`${spec}\``).join(', ')}`, '');
      }
      currentArea = area;
    }

    const used = fixturesUsedBySpec(program.getSourceFile(path.join(ROOT, file)));
    lines.push(`### \`${file}\``, '');
    if (used.length > 0) {
      lines.push(`Fixtures: ${used.map((name) => `\`${name}\``).join(', ')}`, '');
    }
    for (const entry of testsByFile.get(file).sort((a, b) => a.line - b.line || byText(a.title, b.title))) {
      const tags = entry.tags.length > 0 ? ` ${entry.tags.join(' ')}` : '';
      lines.push(`- L${entry.line}: ${oneLine(entry.title)}${tags}`);
    }
    lines.push('');
  }

  return lines.join('\n').trimEnd() + '\n';
}

function renderTestData(caseFields, testsByFile, loaderLines) {
  const usage = new Map();

  for (const [file, entries] of testsByFile) {
    for (const entry of entries) {
      for (const id of entry.title.match(/TC-\d{3}-[12]-\d{4}-UNE/g) ?? []) {
        const files = usage.get(id) ?? new Set();
        files.add(file);
        usage.set(id, files);
      }
    }
  }

  const lines = [
    HEADER,
    '',
    '# Datos de prueba',
    '',
    'Campos con valor de cada caso en `test-data/private/bersa.json` (sólo nombres; los valores',
    'se leen con los loaders). Un campo ausente es `null`, vacío o `sinDatos`. Reglas en',
    '`test-data/AGENTS.md`.',
    '',
    '## Loaders',
    '',
    ...loaderLines,
    '',
    '## Casos',
    '',
    '| Caso | Campos con valor | Tests |',
    '| --- | --- | --- |',
  ];

  for (const { fields, testCaseId } of caseFields) {
    const files = [...(usage.get(testCaseId) ?? [])].sort(byText);
    const testCell = files.length > 0 ? files.map((file) => `\`${file.replace(/^tests\//, '')}\``).join('<br>') : '—';
    lines.push(`| ${testCaseId} | ${fields.join(', ') || '—'} | ${testCell} |`);
  }

  return lines.join('\n').trimEnd() + '\n';
}

function renderReadme(counts) {
  return [
    HEADER,
    '',
    '# Catálogo del framework para agentes',
    '',
    'Consultar este catálogo antes de abrir código. Abrir un archivo fuente sólo para modificarlo',
    'o cuando se necesite un comportamiento que el catálogo no muestra. Se regenera con',
    '`pnpm run agent:index` y `pnpm run check` falla si quedó desactualizado.',
    '',
    '| Archivo | Contenido |',
    '| --- | --- |',
    `| \`fixtures.md\` | ${counts.fixtures} fixtures por entrada (\`api-test\`, \`ui-test\`, \`test-case\`) con su tipo y archivo. |`,
    `| \`pages.md\` | ${counts.pages} Page Objects: constructor, métodos públicos y fixture que los entrega. |`,
    `| \`api.md\` | ${counts.api} clientes API y workflows con sus métodos públicos. |`,
    `| \`tests.md\` | ${counts.tests} tests en ${counts.specFiles} archivos, con tags y fixtures usadas. |`,
    `| \`test-data.md\` | Loaders y ${counts.cases} casos de \`bersa.json\` con sus campos disponibles y tests que los usan. |`,
    '',
  ].join('\n');
}

// ---------------------------------------------------------------------------------------
// Main
// ---------------------------------------------------------------------------------------

function buildIndex() {
  const pageFiles = listFiles(path.join(ROOT, 'src', 'pages'), '.ts');
  const apiFiles = [
    ...listFiles(path.join(ROOT, 'src', 'api'), '.ts'),
    ...listFiles(path.join(ROOT, 'src', 'workflows'), '.ts'),
  ];
  const testDataFiles = listFiles(path.join(ROOT, 'src', 'test-data'), '.ts');
  const specFiles = listFiles(path.join(ROOT, 'tests'), '.spec.ts');
  const program = createProgram([
    ...pageFiles,
    ...apiFiles,
    ...testDataFiles,
    ...specFiles,
    ...FIXTURE_FILES.map((file) => path.join(ROOT, file)),
  ]);
  const checker = program.getTypeChecker();

  const fixtures = collectFixtures(program);
  const fixturesByType = new Map();
  for (const fixture of fixtures) {
    const names = fixturesByType.get(fixture.typeName) ?? [];
    if (!names.includes(fixture.name)) {
      names.push(fixture.name);
    }
    fixturesByType.set(fixture.typeName, names);
  }

  const loaderLines = testDataFiles.flatMap((file) => {
    const module = describeModule(checker, program.getSourceFile(file));
    return module.functions
      .filter((fn) => fn.text.startsWith('load'))
      .map((fn) => {
        const fixtureNames = [...fixturesByType.entries()]
          .filter(([typeName]) => fn.text.includes(`Promise<${typeName}>`))
          .flatMap(([, names]) => names);
        const via = fixtureNames.length > 0 ? ` → fixture ${fixtureNames.map((n) => `\`${n}\``).join(', ')}` : '';
        return `- \`${relative(file)}\`: \`${fn.text}\`${via}`;
      });
  });

  const testsByFile = listPlaywrightTests();
  const caseFields = collectCaseFields();
  const countClasses = (files) =>
    files.reduce((total, file) => total + describeModule(checker, program.getSourceFile(file)).classes.length, 0);

  return {
    'README.md': renderReadme({
      api: countClasses(apiFiles),
      cases: caseFields.length,
      fixtures: fixtures.length,
      pages: countClasses(pageFiles),
      specFiles: testsByFile.size,
      tests: [...testsByFile.values()].reduce((total, entries) => total + entries.length, 0),
    }),
    'api.md': renderModules(
      'Clientes API y workflows',
      'Clases públicas de `src/api/` y `src/workflows/`. Reglas en `src/api/AGENTS.md` y `src/workflows/AGENTS.md`.',
      apiFiles,
      program,
      checker,
      fixturesByType,
    ),
    'fixtures.md': renderFixtures(fixtures),
    'pages.md': renderModules(
      'Page Objects',
      'Clases públicas de `src/pages/`. Reglas en `src/pages/AGENTS.md`.',
      pageFiles,
      program,
      checker,
      fixturesByType,
    ),
    'test-data.md': renderTestData(caseFields, testsByFile, loaderLines),
    'tests.md': renderTests(testsByFile, program),
  };
}

const checkOnly = process.argv.includes('--check');
const index = buildIndex();

if (checkOnly) {
  const stale = Object.entries(index)
    .filter(([name, content]) => {
      const file = path.join(OUTPUT_DIR, name);
      return !existsSync(file) || readFileSync(file, 'utf8') !== content;
    })
    .map(([name]) => name);
  const extra = existsSync(OUTPUT_DIR) ? readdirSync(OUTPUT_DIR).filter((name) => !(name in index)) : [];

  if (stale.length > 0 || extra.length > 0) {
    console.error('El catálogo .agents/index/ está desactualizado. Ejecutar `pnpm run agent:index`.');
    for (const name of [...stale, ...extra]) {
      console.error(`  .agents/index/${name}`);
    }
    process.exit(1);
  }

  console.log('Catálogo de agentes actualizado.');
} else {
  mkdirSync(OUTPUT_DIR, { recursive: true });
  for (const name of readdirSync(OUTPUT_DIR)) {
    if (!(name in index)) {
      console.warn(`Archivo ajeno al catálogo, revisar: .agents/index/${name}`);
    }
  }
  for (const [name, content] of Object.entries(index)) {
    writeFileSync(path.join(OUTPUT_DIR, name), content);
  }
  console.log(`Catálogo generado en .agents/index/ (${Object.keys(index).length} archivos).`);
}
