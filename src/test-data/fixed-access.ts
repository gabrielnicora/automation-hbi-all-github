import { readFile } from 'node:fs/promises';
import path from 'node:path';
import { readTestEnvironment } from '../config/environment';

/** Contraseña y código de segundo factor comunes a todos los usuarios de un ambiente. */
export interface FixedAccess {
  readonly password: string;
  readonly secondFactorCode: string;
}

function readRequiredString(document: Record<string, unknown>, field: string, filePath: string): string {
  const value = document[field];

  if (typeof value !== 'string' || value.trim().length === 0) {
    throw new Error(`${filePath} no contiene un valor válido para "${field}".`);
  }

  return value.trim();
}

/** Devuelve el acceso fijo de `une-dev`; en `qa` cada usuario conserva sus propios valores. */
export async function loadFixedAccess(): Promise<FixedAccess | undefined> {
  const environment = readTestEnvironment();

  if (environment === 'qa') {
    return undefined;
  }

  const filePath = path.resolve(process.cwd(), 'test-data', 'private', `${environment}.json`);
  let document: unknown;

  try {
    document = JSON.parse(await readFile(filePath, 'utf8')) as unknown;
  } catch {
    throw new Error(`No se pudo leer el acceso fijo del ambiente ${environment}: ${filePath}.`);
  }

  if (typeof document !== 'object' || document === null || Array.isArray(document)) {
    throw new Error(`${filePath} debe contener un objeto JSON.`);
  }

  const fields = document as Record<string, unknown>;

  return {
    password: readRequiredString(fields, 'password', filePath),
    secondFactorCode: readRequiredString(fields, 'secondFactorCode', filePath),
  };
}
