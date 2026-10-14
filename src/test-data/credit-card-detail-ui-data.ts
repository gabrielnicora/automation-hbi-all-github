import { loadCaseRow, readRequiredField } from './case-fields';

export interface CreditCardDetailUiData {
  /** Últimos cuatro dígitos de la tarjeta de crédito que se abre (`tarjetaOperar`). */
  readonly creditCard: string;
}

export async function loadCreditCardDetailUiData(testCaseId: string): Promise<CreditCardDetailUiData> {
  const caseRow = await loadCaseRow(testCaseId);
  return { creditCard: readRequiredField(caseRow, 'tarjetaOperar') };
}
