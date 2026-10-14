import { loadBersaTestCaseRow } from './bersa-test-case';

export interface ForeignCurrencyPurchaseUiData {
  readonly destinationAccount: string;
  readonly originAccount: string;
}

function readRequiredString(row: Record<string, unknown>, propertyName: string, testCaseId: string): string {
  const value = row[propertyName];

  if (typeof value !== 'string' || value.trim().length === 0 || value.trim().toLowerCase() === 'sindatos') {
    throw new Error(`El caso ${testCaseId} no contiene ${propertyName} válido.`);
  }

  return value.trim();
}

export async function loadForeignCurrencyPurchaseUiData(testCaseId: string): Promise<ForeignCurrencyPurchaseUiData> {
  const normalizedTestCaseId = testCaseId.trim().toUpperCase();
  const row = await loadBersaTestCaseRow(normalizedTestCaseId);

  return {
    destinationAccount: readRequiredString(row, 'ctaDestino', normalizedTestCaseId),
    originAccount: readRequiredString(row, 'ctaOrigen', normalizedTestCaseId),
  };
}
