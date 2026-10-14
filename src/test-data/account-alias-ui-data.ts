import { loadBersaTestCaseRow } from './bersa-test-case';

export interface AccountAliasUiData {
  readonly account: string;
}

export async function loadAccountAliasUiData(testCaseId: string): Promise<AccountAliasUiData> {
  const normalizedTestCaseId = testCaseId.trim().toUpperCase();
  const row = await loadBersaTestCaseRow(normalizedTestCaseId);
  const account = row.ctaOrigen;

  if (typeof account !== 'string' || account.trim().length === 0 || account.trim().toLowerCase() === 'sindatos') {
    throw new Error(`El caso ${normalizedTestCaseId} no contiene una cuenta origen válida.`);
  }

  return { account: account.trim() };
}
