import { loadBersaTestCaseRow, type BersaTestCaseRow } from './bersa-test-case';

export interface WithdrawalOrderData {
  readonly originAccount: string;
}

function readRequiredString(row: BersaTestCaseRow, propertyName: string, testCaseId: string): string {
  const value = row[propertyName];

  if (typeof value !== 'string' || value.trim().length === 0 || value.trim().toLowerCase() === 'sindatos') {
    throw new Error(`El caso ${testCaseId} no contiene ${propertyName} válido.`);
  }

  return value.trim();
}

export async function loadWithdrawalOrderData(testCaseId: string): Promise<WithdrawalOrderData> {
  const normalizedTestCaseId = testCaseId.trim().toUpperCase();
  const row = await loadBersaTestCaseRow(normalizedTestCaseId);

  return { originAccount: readRequiredString(row, 'ctaOrigen', normalizedTestCaseId) };
}
