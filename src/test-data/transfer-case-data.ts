import { loadCaseRow, readRawField, type CaseRow } from './case-fields';

/** Datos de los casos legados de transferencias; `undefined` cuando el registro tiene `sindatos`. */
export interface TransferCaseData {
  readonly debitCardLastDigits: string | undefined;
  readonly destinationAccount: string | undefined;
  readonly destinationAlias: string | undefined;
  /** CBU o CVU destino (`cbuDestino`). */
  readonly destinationCbu: string | undefined;
  readonly originAccount: string | undefined;
}

function optionalField(caseRow: CaseRow, propertyName: string): string | undefined {
  if (typeof caseRow.row[propertyName] !== 'string' || String(caseRow.row[propertyName]).trim() === '') {
    return undefined;
  }

  const value = readRawField(caseRow, propertyName);
  return /^sin ?dat/i.test(value) ? undefined : value;
}

export async function loadTransferCaseData(testCaseId: string): Promise<TransferCaseData> {
  const caseRow = await loadCaseRow(testCaseId);

  return {
    debitCardLastDigits: optionalField(caseRow, 'tarjetaDebito'),
    destinationAccount: optionalField(caseRow, 'ctaDestino'),
    destinationAlias: optionalField(caseRow, 'aliasDestino'),
    destinationCbu: optionalField(caseRow, 'cbuDestino'),
    originAccount: optionalField(caseRow, 'ctaOrigen'),
  };
}

/** Exige un dato del caso con un error que nombra el campo del registro. */
export function requiredTransferValue(value: string | undefined, field: string): string {
  if (value === undefined) {
    throw new Error(`El registro del caso no contiene ${field} válido.`);
  }

  return value;
}
