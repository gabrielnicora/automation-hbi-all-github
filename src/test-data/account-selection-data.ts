import { loadCaseRow, readRawField, readRequiredField } from './case-fields';

export interface OriginAccountData {
  /** Cuenta origen del caso tal como la informa `widgets.accounts` (`ctaOrigen`). */
  readonly originAccount: string;
}

export interface AccountPairData extends OriginAccountData {
  /** Cuenta destino del caso (`ctaDestino`). */
  readonly destinationAccount: string;
}

export async function loadOriginAccountData(testCaseId: string): Promise<OriginAccountData> {
  const caseRow = await loadCaseRow(testCaseId);
  return { originAccount: readRequiredField(caseRow, 'ctaOrigen') };
}

export async function loadAccountPairData(testCaseId: string): Promise<AccountPairData> {
  const caseRow = await loadCaseRow(testCaseId);
  return {
    destinationAccount: readRequiredField(caseRow, 'ctaDestino'),
    originAccount: readRequiredField(caseRow, 'ctaOrigen'),
  };
}

export interface DebitCardData {
  /** Últimos cuatro dígitos de la tarjeta de débito del caso (`tarjetaDebito`). */
  readonly debitCardLastDigits: string;
}

export async function loadDebitCardData(testCaseId: string): Promise<DebitCardData> {
  const caseRow = await loadCaseRow(testCaseId);
  return { debitCardLastDigits: readRequiredField(caseRow, 'tarjetaDebito') };
}

export interface CreditCardPaymentData extends OriginAccountData, DebitCardData {
  /** Últimos dígitos de la tarjeta de crédito a pagar (`tarjetaOperar`). */
  readonly creditCardLastDigits: string;
}

export async function loadCreditCardPaymentData(testCaseId: string): Promise<CreditCardPaymentData> {
  const caseRow = await loadCaseRow(testCaseId);
  return {
    creditCardLastDigits: readRequiredField(caseRow, 'tarjetaOperar'),
    debitCardLastDigits: readRequiredField(caseRow, 'tarjetaDebito'),
    originAccount: readRequiredField(caseRow, 'ctaOrigen'),
  };
}

export interface OptionalOriginAccountData {
  /** Cuenta origen del caso; `undefined` cuando el registro tiene el marcador `sindatos`. */
  readonly originAccount: string | undefined;
}

export async function loadOptionalOriginAccountData(testCaseId: string): Promise<OptionalOriginAccountData> {
  const caseRow = await loadCaseRow(testCaseId);
  const value = readRawField(caseRow, 'ctaOrigen');
  return { originAccount: value.toLowerCase() === 'sindatos' ? undefined : value };
}

export interface EcheqEndorsementData {
  /** CUIT al que se endosa el eCheq (`cuitDestino`). */
  readonly endorseCuit: string;
}

export async function loadEcheqEndorsementData(testCaseId: string): Promise<EcheqEndorsementData> {
  const caseRow = await loadCaseRow(testCaseId);
  return { endorseCuit: readRequiredField(caseRow, 'cuitDestino') };
}
