import { loadBersaTestCaseRow, type BersaTestCaseRow } from './bersa-test-case';

const NO_DATA_PLACEHOLDER = 'sindatos';

export interface CaseRow {
  readonly testCaseId: string;
  readonly row: BersaTestCaseRow;
}

export async function loadCaseRow(testCaseId: string): Promise<CaseRow> {
  const normalizedTestCaseId = testCaseId.trim().toUpperCase();
  return { testCaseId: normalizedTestCaseId, row: await loadBersaTestCaseRow(normalizedTestCaseId) };
}

/** Lee un campo de texto del registro; rechaza vacíos y el marcador `sindatos` del export. */
export function readRequiredField({ row, testCaseId }: CaseRow, propertyName: string): string {
  const value = readRawField({ row, testCaseId }, propertyName);

  if (value.toLowerCase() === NO_DATA_PLACEHOLDER) {
    throw new Error(`El caso ${testCaseId} no contiene ${propertyName} válido.`);
  }

  return value;
}

/**
 * Lee un campo que el caso usa como entrada literal, incluso si el valor es el marcador
 * `sindatos` (por ejemplo, un alias inexistente que debe ser rechazado por la aplicación).
 */
export function readRawField({ row, testCaseId }: CaseRow, propertyName: string): string {
  const value = row[propertyName];

  if (typeof value !== 'string' || value.trim().length === 0) {
    throw new Error(`El caso ${testCaseId} no contiene ${propertyName} válido.`);
  }

  return value.trim();
}
