import { readFile } from 'node:fs/promises';

export interface PhoneChangeTestData {
  readonly phoneCandidates: readonly string[];
}

function isJsonObject(value: unknown): value is Record<string, unknown> {
  return typeof value === 'object' && value !== null && !Array.isArray(value);
}

export async function loadPhoneChangeTestData(filePath: string): Promise<PhoneChangeTestData> {
  let document: unknown;

  try {
    document = JSON.parse(await readFile(filePath, 'utf8')) as unknown;
  } catch {
    throw new Error(`No se pudieron leer los teléfonos privados para cambio de perfil: ${filePath}.`);
  }

  if (!isJsonObject(document) || !Array.isArray(document.phoneCandidates)) {
    throw new Error('El archivo privado de cambio de teléfono debe contener phoneCandidates.');
  }

  const phoneCandidates = document.phoneCandidates.map((phone) => {
    if (typeof phone !== 'string' || !/^\d{10}$/.test(phone)) {
      throw new Error('El archivo privado de cambio de teléfono contiene un teléfono inválido.');
    }

    return phone;
  });

  if (phoneCandidates.length < 2) {
    throw new Error('El cambio de teléfono requiere al menos dos teléfonos candidatos.');
  }

  if (new Set(phoneCandidates).size !== phoneCandidates.length) {
    throw new Error('El archivo privado de cambio de teléfono contiene teléfonos duplicados.');
  }

  return { phoneCandidates };
}
