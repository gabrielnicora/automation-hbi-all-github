import { loadCaseRow, readRawField } from './case-fields';

export interface ContactSearchUiData {
  readonly cbu: string;
  readonly alias: string;
}

/** CBU y alias son entradas literales del caso: pueden ser inexistentes a propósito. */
export async function loadContactSearchUiData(testCaseId: string): Promise<ContactSearchUiData> {
  const caseRow = await loadCaseRow(testCaseId);
  return { cbu: readRawField(caseRow, 'cbuDestino'), alias: readRawField(caseRow, 'aliasDestino') };
}
