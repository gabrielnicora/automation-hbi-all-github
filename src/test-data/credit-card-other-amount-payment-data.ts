import { loadBersaTestCaseRow, type BersaTestCaseRow } from './bersa-test-case';

export interface CreditCardOtherAmountPaymentData {
  readonly creditCardLastFour: string;
  readonly debitCardLastFour: string;
  readonly originAccount: string;
}

function readRequiredString(row: BersaTestCaseRow, propertyName: string, testCaseId: string): string {
  const value = row[propertyName];

  if (typeof value !== 'string' || value.trim().length === 0 || value.trim().toLowerCase() === 'sindatos') {
    throw new Error(`El caso ${testCaseId} no contiene ${propertyName} válido.`);
  }

  return value.trim();
}

export async function loadCreditCardOtherAmountPaymentData(
  testCaseId: string,
): Promise<CreditCardOtherAmountPaymentData> {
  const normalizedTestCaseId = testCaseId.trim().toUpperCase();
  const row = await loadBersaTestCaseRow(normalizedTestCaseId);
  const debitCardLastFour = readRequiredString(row, 'tarjetaDebito', normalizedTestCaseId);
  const creditCardLastFour = readRequiredString(row, 'tarjetaOperar', normalizedTestCaseId);

  if (!/^\d{4}$/.test(debitCardLastFour) || !/^\d{4}$/.test(creditCardLastFour)) {
    throw new Error(`El caso ${normalizedTestCaseId} no contiene últimos cuatro de tarjeta válidos.`);
  }

  return {
    creditCardLastFour,
    debitCardLastFour,
    originAccount: readRequiredString(row, 'ctaOrigen', normalizedTestCaseId),
  };
}
