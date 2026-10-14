import { loadBersaTestCaseRow } from './bersa-test-case';

export interface OwnAccountTransferUiData {
  readonly destinationCbu: string;
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

export async function loadOwnAccountTransferUiData(testCaseId: string): Promise<OwnAccountTransferUiData> {
  const normalizedTestCaseId = testCaseId.trim().toUpperCase();
  const row = await loadBersaTestCaseRow(normalizedTestCaseId);
  const destinationCbu = readRequiredString(row, 'cbuDestino', normalizedTestCaseId);
  const destinationAccount = readRequiredString(row, 'ctaDestino', normalizedTestCaseId);
  const originAccount = readRequiredString(row, 'ctaOrigen', normalizedTestCaseId);

  if (!/^\d{22}$/.test(destinationCbu)) {
    throw new Error(`El caso ${normalizedTestCaseId} no contiene un CBU destino válido.`);
  }

  if (originAccount === destinationAccount) {
    throw new Error(`El caso ${normalizedTestCaseId} debe usar cuentas origen y destino diferentes.`);
  }

  return { destinationCbu, destinationAccount, originAccount };
}
