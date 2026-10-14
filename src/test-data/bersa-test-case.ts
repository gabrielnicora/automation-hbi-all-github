import { readFile } from 'node:fs/promises';
import path from 'node:path';

export type BersaTestCaseRow = Record<string, unknown>;

const BERSA_TEST_CASE_PATTERN = /^TC\d+BERSA$/;

function isJsonObject(value: unknown): value is BersaTestCaseRow {
  return typeof value === 'object' && value !== null && !Array.isArray(value);
}

function normalizeTestCaseId(value: string): string {
  const normalizedValue = value.trim().toUpperCase();

  if (!BERSA_TEST_CASE_PATTERN.test(normalizedValue)) {
    throw new Error(`Identificador de caso inválido: "${value}". Formato esperado: TC<numero>BERSA.`);
  }

  return normalizedValue;
}

function extractTestCases(document: unknown): BersaTestCaseRow {
  if (!isJsonObject(document)) {
    throw new Error('El export JSON de BERSA debe contener un objeto raíz.');
  }

  const testCases = document.testCases;

  if (!isJsonObject(testCases)) {
    throw new Error('El export JSON de BERSA debe contener el objeto testCases indexado por caso.');
  }

  return testCases;
}

async function readPrivateBersaExport(): Promise<unknown> {
  const filePath = path.resolve(process.cwd(), 'test-data', 'private', 'bersa.json');
  let content: string;

  try {
    content = await readFile(filePath, 'utf8');
  } catch {
    throw new Error(`No se pudo leer el export JSON privado de BERSA: ${filePath}.`);
  }

  try {
    return JSON.parse(content) as unknown;
  } catch {
    throw new Error('El export JSON privado de BERSA no contiene JSON válido.');
  }
}

export async function loadBersaTestCaseRow(testCaseId: string): Promise<BersaTestCaseRow> {
  const normalizedTestCaseId = normalizeTestCaseId(testCaseId);
  const testCases = extractTestCases(await readPrivateBersaExport());
  const row = testCases[normalizedTestCaseId];

  if (!isJsonObject(row)) {
    throw new Error(`No se encontró el caso ${normalizedTestCaseId} en el export JSON de BERSA.`);
  }

  if (row.tcName !== normalizedTestCaseId) {
    throw new Error(`El registro ${normalizedTestCaseId} no coincide con su tcName interno.`);
  }

  return row;
}
