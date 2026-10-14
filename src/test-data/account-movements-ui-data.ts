import { loadBersaTestCaseRow } from './bersa-test-case';

export interface AccountMovementsUiData {
  readonly account: string;
}

export async function loadAccountMovementsUiData(testCaseId: string): Promise<AccountMovementsUiData> {
  const normalizedTestCaseId = testCaseId.trim().toUpperCase();
  const row = await loadBersaTestCaseRow(normalizedTestCaseId);
  const account = row.ctaOrigen;

  if (typeof account !== 'string' || !account.trim() || account.trim().toLowerCase() === 'sindatos') {
    throw new Error(`El caso ${normalizedTestCaseId} no contiene ctaOrigen válida.`);
  }

  return { account: account.trim() };
}
