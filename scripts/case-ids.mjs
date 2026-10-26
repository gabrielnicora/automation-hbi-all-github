// Registro y control de IDs de casos (SPEC.md, sección 10.1).
//   node scripts/case-ids.mjs check                                  valida carpetas y tests contra el registro
//   node scripts/case-ids.mjs new <familia> <api|ui> [--new-family]  asigna y registra el próximo ID
// Formato: TC-<FFF familia>-<C canal: 1 api, 2 ui>-<NNNN secuencia>-UNE.
// Carpetas: tests/<canal>/<FFF>-<familia>/.
import { spawnSync } from 'node:child_process';
import { readdirSync, readFileSync, writeFileSync } from 'node:fs';
import { createRequire } from 'node:module';
import path from 'node:path';

const ROOT = process.cwd();
const REGISTRY_PATH = path.join(ROOT, 'test-data', 'case-ids.json');
const ID_PATTERN = /^TC-(\d{3})-([12])-(\d{4})-UNE$/;
// IDs con este formato y también los anteriores (TC<numero>BERSA), para detectar los que quedaron.
const ID_IN_TEXT = /\bTC-\d{3}-[12]-\d{4}-UNE\b|\bTC\d+BERSA\b/g;
const FOLDER_PATTERN = /^(\d{3})-([a-z0-9-]+)$/;

function readRegistry() {
  return JSON.parse(readFileSync(REGISTRY_PATH, 'utf8'));
}

function writeRegistry(registry) {
  writeFileSync(REGISTRY_PATH, `${JSON.stringify(registry, null, 2)}\n`);
}

function familyFolders() {
  const folders = [];
  for (const channel of readdirSync(path.join(ROOT, 'tests'), { withFileTypes: true })) {
    if (!channel.isDirectory()) continue;
    for (const folder of readdirSync(path.join(ROOT, 'tests', channel.name), { withFileTypes: true })) {
      if (folder.isDirectory()) folders.push({ channel: channel.name, folder: folder.name });
    }
  }
  return folders;
}

function specFiles(channel, folder) {
  return readdirSync(path.join(ROOT, 'tests', channel, folder), { recursive: true, withFileTypes: true })
    .filter((entry) => entry.isFile() && entry.name.endsWith('.spec.ts'))
    .map((entry) => path.relative(ROOT, path.join(entry.parentPath, entry.name)).split(path.sep).join('/'))
    .sort();
}

function check() {
  const registry = readRegistry();
  const errors = [];
  const byId = new Map();
  const byLegacy = new Map();
  const familyByCode = new Map(Object.entries(registry.families).map(([name, code]) => [code, name]));

  for (const entry of registry.cases) {
    const match = ID_PATTERN.exec(entry.id);
    if (!match) {
      errors.push(`Registro: ${entry.id} no cumple TC-<FFF>-<C>-<NNNN>-UNE.`);
      continue;
    }
    if (byId.has(entry.id)) {
      errors.push(`Registro: ${entry.id} está duplicado.`);
    }
    if (registry.families[entry.family] !== match[1] || registry.channels[entry.channel] !== match[2]) {
      errors.push(`Registro: ${entry.id} no corresponde a ${entry.family}/${entry.channel}.`);
    }
    byId.set(entry.id, entry);
    if (entry.legacyId) {
      byLegacy.set(entry.legacyId, [...(byLegacy.get(entry.legacyId) ?? []), entry]);
    }
  }

  for (const { channel, folder } of familyFolders()) {
    const folderMatch = FOLDER_PATTERN.exec(folder);
    // Sin código (por ejemplo tests/ui/cards) se usa el nombre completo para sugerir el correcto.
    const family = folderMatch?.[2] ?? folder;

    if (!(channel in registry.channels)) {
      errors.push(
        `tests/${channel}/: canal desconocido; los canales son ${Object.keys(registry.channels).join(', ')}.`,
      );
      continue;
    }
    if (!folderMatch || familyByCode.get(folderMatch[1]) !== family) {
      const expected =
        family && registry.families[family] ? `${registry.families[family]}-${family}` : '<FFF>-<familia>';
      errors.push(`tests/${channel}/${folder}/: la carpeta debe llamarse ${expected} (familia registrada).`);
      continue;
    }

    for (const file of specFiles(channel, folder)) {
      const ids = new Set(readFileSync(path.join(ROOT, file), 'utf8').match(ID_IN_TEXT) ?? []);

      for (const id of ids) {
        const entry = byId.get(id);
        if (entry) {
          if (entry.family !== family || entry.channel !== channel) {
            errors.push(`${file}: ${id} está registrado para ${entry.family}/${entry.channel}.`);
          }
        } else if (byLegacy.has(id)) {
          const current = byLegacy.get(id).find((item) => item.family === family && item.channel === channel);
          errors.push(`${file}: ${id} es un ID anterior; usar ${current?.id ?? byLegacy.get(id)[0].id}.`);
        } else {
          errors.push(`${file}: ${id} no está registrado. Obtenerlo con pnpm run case:new -- ${family} ${channel}.`);
        }
      }
    }
  }

  errors.push(...checkScenarios(byId));

  if (errors.length > 0) {
    console.error('IDs de casos inválidos (SPEC.md, sección 10.1):');
    errors.forEach((error) => console.error(`  ${error}`));
    process.exit(1);
  }

  console.log(`IDs de casos: ${registry.cases.length} registrados, carpetas y tests consistentes.`);
}

// Escenarios efectivos según Playwright (--list no ejecuta tests ni llama al banco).
function listScenarios() {
  const cli = createRequire(path.join(ROOT, 'package.json')).resolve('@playwright/test/cli');
  const result = spawnSync(process.execPath, [cli, 'test', '--list', '--reporter=json'], {
    cwd: ROOT,
    encoding: 'utf8',
    maxBuffer: 64 * 1024 * 1024,
  });
  let report;
  try {
    report = JSON.parse(result.stdout.slice(result.stdout.indexOf('{')));
  } catch {
    return { errors: [`playwright --list no devolvió JSON: ${(result.stderr || result.stdout).trim()}`] };
  }

  const scenarios = [];
  const walk = (suite) => {
    for (const spec of suite.specs ?? []) {
      const file = path.relative(ROOT, path.join(report.config.rootDir, spec.file)).split(path.sep).join('/');
      for (const test of spec.tests) {
        scenarios.push({ file, line: spec.line, title: spec.title, project: test.projectName });
      }
    }
    (suite.suites ?? []).forEach(walk);
  };
  (report.suites ?? []).forEach(walk);
  return { scenarios, errors: (report.errors ?? []).map((error) => `playwright --list: ${error.message}`) };
}

// Cada escenario declara un ID válido y registrado al inicio del título, corre sólo en el
// proyecto de su canal, y ningún ID identifica a dos escenarios distintos.
function checkScenarios(byId) {
  const { scenarios, errors } = listScenarios();
  if (!scenarios) return errors;

  const ownerById = new Map();
  for (const { file, line, title, project } of scenarios) {
    const where = `${file}:${line}`;
    const channel = file.split('/')[1];
    const family = FOLDER_PATTERN.exec(file.split('/')[2] ?? '')?.[2];

    if (project !== channel) {
      errors.push(`${where}: el proyecto "${project}" no debe incluir tests de tests/${channel}/.`);
      continue;
    }
    const id = /^(\S+) - /.exec(title)?.[1];
    if (!id) {
      errors.push(`${where}: el título "${title}" debe empezar con "<ID> - ".`);
      continue;
    }
    if (!ID_PATTERN.test(id)) {
      errors.push(`${where}: ${id} no cumple TC-<FFF>-<C>-<NNNN>-UNE.`);
      continue;
    }
    const entry = byId.get(id);
    if (!entry) {
      errors.push(`${where}: ${id} no está registrado. Obtenerlo con pnpm run case:new -- ${family} ${channel}.`);
      continue;
    }
    if (entry.family !== family || entry.channel !== channel) {
      errors.push(`${where}: ${id} está registrado para ${entry.family}/${entry.channel}.`);
    }
    const owner = ownerById.get(id);
    if (owner) {
      errors.push(
        `${where}: ${id} ya identifica a ${owner}. Asignar un ID nuevo con pnpm run case:new -- ${family} ${channel}.`,
      );
    } else {
      ownerById.set(id, where);
    }
  }
  return errors;
}

function allocate(family, channel, allowNewFamily) {
  const registry = readRegistry();

  if (!(channel in registry.channels)) {
    throw new Error(`Canal inválido: "${channel}". Valores: ${Object.keys(registry.channels).join(', ')}.`);
  }

  let createdFamily = false;
  if (!(family in registry.families)) {
    if (!allowNewFamily) {
      throw new Error(
        `Familia desconocida: "${family}". Familias: ${Object.keys(registry.families).join(', ')}. ` +
          'Usar el nombre sin el código; si es una familia nueva, repetir con --new-family.',
      );
    }
    if (!/^[a-z0-9-]+$/.test(family)) {
      throw new Error(`Nombre de familia inválido: "${family}". Usar kebab-case.`);
    }
    const nextCode = Math.max(0, ...Object.values(registry.families).map(Number)) + 1;
    registry.families[family] = String(nextCode).padStart(3, '0');
    createdFamily = true;
  }

  const code = registry.families[family];
  const channelCode = registry.channels[channel];
  const lastSequence = Math.max(
    0,
    ...registry.cases
      .map((entry) => ID_PATTERN.exec(entry.id))
      .filter((match) => match && match[1] === code && match[2] === channelCode)
      .map((match) => Number(match[3])),
  );

  if (lastSequence >= 9999) {
    throw new Error(`La familia ${family}/${channel} agotó su rango de 9999 casos.`);
  }

  const id = `TC-${code}-${channelCode}-${String(lastSequence + 1).padStart(4, '0')}-UNE`;
  registry.cases.push({ id, family, channel, legacyId: null });
  writeRegistry(registry);
  console.log(id);

  if (createdFamily) {
    console.error(`Familia nueva registrada: crear la carpeta tests/${channel}/${code}-${family}/.`);
  }
}

const [command, ...args] = process.argv.slice(2).filter((value) => value !== '--');

if (command === 'check') {
  check();
} else if (command === 'new' && args.length >= 2) {
  try {
    allocate(args[0], args[1], args.includes('--new-family'));
  } catch (error) {
    console.error(error.message);
    process.exit(1);
  }
} else {
  console.error('Uso: case-ids.mjs check | case-ids.mjs new <familia> <api|ui> [--new-family]');
  process.exit(1);
}
