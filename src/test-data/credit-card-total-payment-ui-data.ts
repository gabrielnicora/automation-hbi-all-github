import { loadBersaTestCaseRow } from './bersa-test-case';

export interface CreditCardTotalPaymentUiData {
  readonly creditCard: string;
  readonly debitCardLastFour: string;
  readonly originAccount: string;
}

function readRequiredString(row: Record<string, unknown>, propertyName: string, testCaseId: string): string {
  const value = row[propertyName];

  if (typeof value !== 'string' || value.trim().length === 0 || value.trim().toLowerCase() === 'sindatos') {
    throw new Error(`El caso ${testCaseId} no contiene ${propertyName} válido.`);
  }

  return value.trim();
}

export async function loadCreditCardTotalPaymentUiData(testCaseId: string): Promise<CreditCardTotalPaymentUiData> {
  const normalizedTestCaseId = testCaseId.trim().toUpperCase();
  const row = await loadBersaTestCaseRow(normalizedTestCaseId);
  const debitCardLastFour = readRequiredString(row, 'tarjetaDebito', normalizedTestCaseId);

  if (!/^\d{4}$/.test(debitCardLastFour)) {
    throw new Error(`El caso ${normalizedTestCaseId} no contiene los últimos cuatro dígitos de tarjetaDebito.`);
  }

  return {
    creditCard: readRequiredString(row, 'tarjetaOperar', normalizedTestCaseId),
    debitCardLastFour,
    originAccount: readRequiredString(row, 'ctaOrigen', normalizedTestCaseId),
  };
}
