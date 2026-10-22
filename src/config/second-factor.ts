import { readFile } from 'node:fs/promises';
import { loadFixedAccess } from '../test-data/fixed-access';

export type RequestedSecondFactorMethod = 'common' | 'master-code';
export type EffectiveSecondFactorMethod = RequestedSecondFactorMethod;

export interface SecondFactorConfig {
  readonly requestedMethod: RequestedSecondFactorMethod;
  readonly masterCode: string;
  readonly masterCodeEnabledUsers: ReadonlySet<string>;
  /** Código común a todos los usuarios del ambiente (`une-dev`); tiene prioridad sobre el master code. */
  readonly fixedCode: string | undefined;
}

function isJsonObject(value: unknown): value is Record<string, unknown> {
  return typeof value === 'object' && value !== null && !Array.isArray(value);
}

function readRequestedMethod(value: string | undefined): RequestedSecondFactorMethod {
  const method = value?.trim().toLowerCase() || 'common';

  if (method !== 'common' && method !== 'master-code') {
    throw new Error(`SECOND_FACTOR_METHOD inválido: "${method}". Valores permitidos: common, master-code.`);
  }

  return method;
}

function readMasterCode(document: Record<string, unknown>): string {
  const masterCode = document.masterCode;

  if (typeof masterCode !== 'string' || masterCode.trim().length === 0) {
    throw new Error('La configuración privada de segundo factor no contiene un masterCode válido.');
  }

  return masterCode.trim();
}

function readEnabledUsers(document: Record<string, unknown>): ReadonlySet<string> {
  const users = document.masterCodeEnabledUsers;

  if (!Array.isArray(users) || users.length === 0) {
    throw new Error('La configuración privada de segundo factor no contiene usuarios habilitados.');
  }

  const normalizedUsers = users.map((user) => {
    if (typeof user !== 'string' || user.trim().length === 0) {
      throw new Error('La configuración privada de segundo factor contiene un usuario inválido.');
    }

    return user.trim().toLowerCase();
  });

  if (new Set(normalizedUsers).size !== normalizedUsers.length) {
    throw new Error('La configuración privada de segundo factor contiene usuarios duplicados.');
  }

  return new Set(normalizedUsers);
}

export async function loadSecondFactorConfig(
  filePath: string,
  source: NodeJS.ProcessEnv = process.env,
): Promise<SecondFactorConfig> {
  let document: unknown;

  try {
    document = JSON.parse(await readFile(filePath, 'utf8')) as unknown;
  } catch {
    throw new Error(`No se pudo leer la configuración privada de segundo factor: ${filePath}.`);
  }

  if (!isJsonObject(document)) {
    throw new Error('La configuración privada de segundo factor debe contener un objeto JSON.');
  }

  return {
    requestedMethod: readRequestedMethod(source.SECOND_FACTOR_METHOD),
    masterCode: readMasterCode(document),
    masterCodeEnabledUsers: readEnabledUsers(document),
    fixedCode: (await loadFixedAccess())?.secondFactorCode,
  };
}

export function resolveEffectiveSecondFactorMethod(
  config: SecondFactorConfig,
  username: string,
): EffectiveSecondFactorMethod {
  if (config.requestedMethod === 'common') {
    return 'common';
  }

  return config.masterCodeEnabledUsers.has(username.trim().toLowerCase()) ? 'master-code' : 'common';
}
