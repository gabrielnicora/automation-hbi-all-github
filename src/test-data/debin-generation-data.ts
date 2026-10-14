import { loadBersaTestCaseRow, type BersaTestCaseRow } from './bersa-test-case';

export interface DebinGenerationData {
  readonly destinationCbu: string;
  readonly destinationCuit: string;
  readonly originAccount: string;
}

function readRequiredString(row: BersaTestCaseRow, propertyName: string, testCaseId: string): string {
  const value = row[propertyName];

  if (typeof value !== 'string' || value.trim().length === 0 || value.trim().toLowerCase() === 'sindatos') {
    throw new Error(`El caso ${testCaseId} no contiene ${propertyName} válido.`);
  }

  return value.trim();
}

export async function loadDebinGenerationData(testCaseId: string): Promise<DebinGenerationData> {
  const normalizedTestCaseId = testCaseId.trim().toUpperCase();
  const row = await loadBersaTestCaseRow(normalizedTestCaseId);
  const destinationCbu = readRequiredString(row, 'cbuDestino', normalizedTestCaseId);
  const destinationCuit = readRequiredString(row, 'cuitDestino', normalizedTestCaseId);

  if (!/^\d{22}$/.test(destinationCbu)) {
    throw new Error(`El caso ${normalizedTestCaseId} no contiene un CBU destino válido.`);
  }

  if (!/^\d{11}$/.test(destinationCuit)) {
    throw new Error(`El caso ${normalizedTestCaseId} no contiene un CUIT destino válido.`);
  }

  return {
    destinationCbu,
    destinationCuit,
    originAccount: readRequiredString(row, 'ctaOrigen', normalizedTestCaseId),
  };
}
