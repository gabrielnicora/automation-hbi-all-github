import { loadCaseRow, readRequiredField } from './case-fields';

export interface OriginAccountUiData {
  /** Cuenta origen del caso, tal como se muestra en Home Banking (`ctaOrigen`). */
  readonly originAccount: string;
}

export async function loadOriginAccountUiData(testCaseId: string): Promise<OriginAccountUiData> {
  const caseRow = await loadCaseRow(testCaseId);
  return { originAccount: readRequiredField(caseRow, 'ctaOrigen') };
}
