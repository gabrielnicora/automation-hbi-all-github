// Registro y control de IDs de casos (SPEC.md, sección 10.1).
//   node scripts/case-ids.mjs check                              valida tests contra el registro
//   node scripts/case-ids.mjs new <familia> <api|ui> [--new-family]   asigna y registra el próximo ID
// Formato: TC<FF familia><C canal: 1 api, 2 ui><NNNN secuencia>BERSA.
import { readdirSync, readFileSync, writeFileSync } from 'node:fs';
import path from 'node:path';

const ROOT = process.cwd();
const REGISTRY_PATH = path.join(ROOT, 'test-data', 'case-ids.json');
const ID_PATTERN = /^TC(\d{2})([12])(\d{4})BERSA$/;
const ID_IN_TEXT = /\bTC\d+BERSA\b/g;

function readRegistry() {
  return JSON.parse(readFileSync(REGISTRY_PATH, 'utf8'));
}

function writeRegistry(registry) {
  writeFileSync(REGISTRY_PATH, `${JSON.stringify(registry, null, 2)}\n`);
}

function specFiles() {
  return readdirSync(path.join(ROOT, 'tests'), { recursive: true, withFileTypes: true })
    .filter((entry) => entry.isFile() && entry.name.endsWith('.spec.ts'))
    .map((entry) => path.relative(ROOT, path.join(entry.parentPath, entry.name)).split(path.sep).join('/'))
    .sort();
}

function check() {
  const registry = readRegistry();
  const errors = [];
  const byId = new Map();
  const byLegacy = new Map();

  for (const entry of registry.cases) {
    const match = ID_PATTERN.exec(entry.id);
    if (!match) {
      errors.push(`Registro: ${entry.id} no cumple TC<FF><C><NNNN>BERSA.`);
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

  for (const file of specFiles()) {
    const [, channel, family] = file.split('/');
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

  if (errors.length > 0) {
    console.error('IDs de casos inválidos (SPEC.md, sección 10.1):');
    errors.forEach((error) => console.error(`  ${error}`));
    process.exit(1);
  }

  console.log(`IDs de casos: ${registry.cases.length} registrados, tests consistentes.`);
}

function allocate(family, channel, allowNewFamily) {
  const registry = readRegistry();

  if (!(channel in registry.channels)) {
    throw new Error(`Canal inválido: "${channel}". Valores: ${Object.keys(registry.channels).join(', ')}.`);
  }

  if (!(family in registry.families)) {
    if (!allowNewFamily) {
      throw new Error(
        `Familia desconocida: "${family}". Familias: ${Object.keys(registry.families).join(', ')}. ` +
          'Si es una carpeta nueva de tests/, repetir con --new-family.',
      );
    }
    const nextCode = Math.max(0, ...Object.values(registry.families).map(Number)) + 1;
    registry.families[family] = String(nextCode).padStart(2, '0');
  }

  const prefix = `TC${registry.families[family]}${registry.channels[channel]}`;
  const lastSequence = Math.max(
    0,
    ...registry.cases.filter((entry) => entry.id.startsWith(prefix)).map((entry) => Number(entry.id.slice(5, 9))),
  );

  if (lastSequence >= 9999) {
    throw new Error(`La familia ${family}/${channel} agotó su rango de 9999 casos.`);
  }

  const id = `${prefix}${String(lastSequence + 1).padStart(4, '0')}BERSA`;
  registry.cases.push({ id, family, channel, legacyId: null });
  writeRegistry(registry);
  console.log(id);
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
