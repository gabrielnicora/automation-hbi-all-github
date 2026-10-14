import { loadBersaTestCaseRow, type BersaTestCaseRow } from './bersa-test-case';

export interface OwnAccountTransferData {
  readonly debitCardLastFour: string;
  readonly destinationAccount: string;
  readonly destinationCbu: string;
  readonly destinationCuitCuil: string;
  readonly destinationName: string;
  readonly originAccount: string;
}

function readRequiredString(row: BersaTestCaseRow, propertyName: string, testCaseId: string): string {
  const value = row[propertyName];

  if (typeof value !== 'string' || value.trim().length === 0 || value.trim().toLowerCase() === 'sindatos') {
    throw new Error(`El caso ${testCaseId} no contiene ${propertyName} válido.`);
  }

  return value.trim();
}

export async function loadOwnAccountTransferData(testCaseId: string): Promise<OwnAccountTransferData> {
  const normalizedTestCaseId = testCaseId.trim().toUpperCase();
  const row = await loadBersaTestCaseRow(normalizedTestCaseId);
  const originAccount = readRequiredString(row, 'ctaOrigen', normalizedTestCaseId);
  const destinationAccount = readRequiredString(row, 'ctaDestino', normalizedTestCaseId);
  const destinationCbu = readRequiredString(row, 'cbuDestino', normalizedTestCaseId);
  const destinationCuitCuil = readRequiredString(row, 'cuitDestino', normalizedTestCaseId);
  const destinationName = readRequiredString(row, 'nombreDestino', normalizedTestCaseId);
  const debitCardLastFour = readRequiredString(row, 'tarjetaDebito', normalizedTestCaseId);

  if (originAccount === destinationAccount) {
    throw new Error(`El caso ${normalizedTestCaseId} debe usar cuentas origen y destino diferentes.`);
  }

  if (!/^\d{22}$/.test(destinationCbu)) {
    throw new Error(`El caso ${normalizedTestCaseId} no contiene un CBU destino válido.`);
  }

  if (!/^\d{11}$/.test(destinationCuitCuil)) {
    throw new Error(`El caso ${normalizedTestCaseId} no contiene un CUIT/CUIL destino válido.`);
  }

  if (!/^\d{4}$/.test(debitCardLastFour)) {
    throw new Error(`El caso ${normalizedTestCaseId} no contiene últimos cuatro de tarjeta válidos.`);
  }

  return {
    debitCardLastFour,
    destinationAccount,
    destinationCbu,
    destinationCuitCuil,
    destinationName,
    originAccount,
  };
}
