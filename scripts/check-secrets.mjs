// Busca secretos de plataforma en archivos versionados (o en los recibidos por argumento,
// como hace lint-staged). No marca los datos dummy de test-data/private (SPEC.md, sección 8).
// Para aceptar una línea revisada, agregar en ella el comentario `secret-scan:allow`.
import { execFileSync } from 'node:child_process';
import { readFileSync, statSync } from 'node:fs';
import path from 'node:path';

const RULES = [
  { name: 'Clave privada', pattern: /-----BEGIN [A-Z ]*PRIVATE KEY-----/ },
  { name: 'Token JWT', pattern: /\beyJ[\w-]{10,}\.eyJ[\w-]{10,}\.[\w-]{10,}/ },
  { name: 'Header Bearer con valor', pattern: /\bBearer\s+[A-Za-z0-9._~+/-]{20,}=*/ },
  { name: 'PAT de GitLab', pattern: /\bglpat-[\w-]{20,}/ },
  { name: 'Token de GitHub', pattern: /\bgh[pousr]_[A-Za-z0-9]{36,}/ },
  { name: 'Token de Atlassian', pattern: /\bATATT3[\w=-]{20,}/ },
  { name: 'Clave de Anthropic u OpenAI', pattern: /\bsk-(?:ant-)?[A-Za-z0-9_-]{32,}/ },
  { name: 'Clave de acceso AWS', pattern: /\bAKIA[0-9A-Z]{16}\b/ },
  { name: 'Token de Slack', pattern: /\bxox[abprs]-[A-Za-z0-9-]{10,}/ },
  { name: 'Credenciales en URL', pattern: /\bhttps?:\/\/[^\s/:@'"`]+:[^\s/:@'"`]+@[\w.-]+/ },
];

// Legado de referencia y artefactos binarios: no forman parte del código mantenido.
const EXCLUDED_PREFIXES = ['java/', 'web/', 'node_modules/'];
const EXCLUDED_FILES = new Set(['pnpm-lock.yaml', 'mcp-xray/pnpm-lock.yaml']);
const BINARY_EXTENSIONS = new Set([
  '.png',
  '.jpg',
  '.jpeg',
  '.gif',
  '.ico',
  '.pdf',
  '.zip',
  '.jar',
  '.class',
  '.woff',
  '.woff2',
]);
const MAX_FILE_BYTES = 2_000_000;
const ALLOW_MARKER = 'secret-scan:allow';

function listFiles() {
  const fromArguments = process.argv.slice(2);

  if (fromArguments.length > 0) {
    return fromArguments.map((file) => path.relative(process.cwd(), path.resolve(file)));
  }

  return execFileSync('git', ['ls-files', '-z'], { encoding: 'utf8' }).split('\0').filter(Boolean);
}

function isEnvFile(file) {
  const name = path.basename(file);
  return name === '.env' || (name.startsWith('.env.') && name !== '.env.example');
}

function shouldScan(file) {
  const normalized = file.split(path.sep).join('/');
  return (
    !EXCLUDED_PREFIXES.some((prefix) => normalized.startsWith(prefix)) &&
    !EXCLUDED_FILES.has(normalized) &&
    !BINARY_EXTENSIONS.has(path.extname(normalized).toLowerCase())
  );
}

function scanFile(file) {
  const findings = [];

  if (isEnvFile(file)) {
    findings.push({ file, line: 0, rule: 'Archivo .env versionado o preparado' });
  }

  let size;
  try {
    size = statSync(file).size;
  } catch {
    return findings; // Archivo borrado en el índice.
  }

  if (size > MAX_FILE_BYTES) {
    return findings;
  }

  readFileSync(file, 'utf8')
    .split(/\r?\n/)
    .forEach((text, index) => {
      if (text.includes(ALLOW_MARKER)) {
        return;
      }

      for (const rule of RULES) {
        if (rule.pattern.test(text)) {
          findings.push({ file, line: index + 1, rule: rule.name });
        }
      }
    });

  return findings;
}

const findings = listFiles().filter(shouldScan).flatMap(scanFile);

if (findings.length > 0) {
  // No se imprime el valor encontrado: sólo su ubicación y el tipo de secreto.
  console.error('Posibles secretos de plataforma (SPEC.md, sección 8):');
  for (const finding of findings) {
    console.error(`  ${finding.file}:${finding.line} — ${finding.rule}`);
  }
  console.error(`Quitar el valor o, si fue revisado y no es secreto, marcar la línea con "${ALLOW_MARKER}".`);
  process.exit(1);
}

console.log('Escaneo de secretos: sin hallazgos.');
