import type { AuthenticatedSession } from '../../models/authenticated-session';
import { SmsOperationOtpApi } from '../authentication/sms-operation-otp.api';
import { ReportingApiRequest, type ApiJsonObject } from '../reporting-api-request';

const OFFER_ENDPOINT = '/api/v1/execute/salary.advance.pre';
const LOAN_AMOUNTS_ENDPOINT = '/api/v1/execute/seal.loans.byAmount';
const CONFIRM_ENDPOINT = '/api/v1/execute/salary.advance.confirm';
// Códigos de éxito del validador legado (Config/ValidarResponse.validateResponses).
const SUCCESS_CODES: readonly string[] = ['COR000I', 'COR013I', 'API690W', 'API691W', 'API692W'];
// Metadatos fijos de las plantillas legadas requestVacio.json, sealLoansByAmount.json y salaryAdvanceConfirm.json.
const OFFER_REQUEST = { ajax_uuid: '274dd2b3-9fc7-4f82-b954-34a5d784ec9a', xAppVersion: '1.95.0' } as const;
const LOAN_AMOUNTS_METADATA = { ajax_uuid: 'bfe7e36c-8005-4ddd-86d7-f5eab6bdb796', xAppVersion: '1.79.1' } as const;
const CONFIRM_METADATA = { ajax_uuid: '0d799e36-c002-4aee-9d65-f463e4436c8e', xAppVersion: '1.79.1' } as const;

function isJsonObject(value: unknown): value is ApiJsonObject {
  return typeof value === 'object' && value !== null && !Array.isArray(value);
}

/** Equivalente de `Double.valueOf(valor).intValue()` del legado. */
function toLegacyInteger(value: unknown, field: string): number {
  const numericValue = Number(value);

  if (value === null || value === undefined || value === '' || Number.isNaN(numericValue)) {
    throw new Error(`El adelanto de sueldo no informa ${field} numérico.`);
  }

  return Math.trunc(numericValue);
}

function toNumber(value: unknown, field: string): number {
  const numericValue = Number(value);

  if (value === null || value === undefined || value === '' || Number.isNaN(numericValue)) {
    throw new Error(`El adelanto de sueldo no informa ${field} numérico.`);
  }

  return numericValue;
}

export interface SalaryAdvanceOffer {
  /** Cuentas con oferta de adelanto (`data.accounts`). */
  readonly accounts: readonly ApiJsonObject[];
}

export interface SalaryAdvanceRates {
  readonly cft: number;
  readonly idSalaryAdvance: number;
  readonly tea: number;
  readonly tna: number;
}

export interface SalaryAdvanceAmounts {
  readonly amountLoan: number;
  readonly moneyToAccredit: number;
}

export interface SalaryAdvanceConfirmation {
  readonly amount: number;
  readonly amounts: SalaryAdvanceAmounts;
  readonly idProduct: string;
  readonly rates: SalaryAdvanceRates;
}

/** Adelanto de sueldo (legado CuentasSteps: salaryAdvancePre, sealLoansByAmount y salaryAdvanceConfirm). */
export class SalaryAdvanceApi {
  public constructor(
    private readonly api: ReportingApiRequest,
    private readonly smsOperationOtp: SmsOperationOtpApi,
  ) {}

  public async readOffer(session: AuthenticatedSession): Promise<SalaryAdvanceOffer> {
    const response = await this.post(session, OFFER_ENDPOINT, { ...OFFER_REQUEST }, 'consulta de oferta de adelanto');
    const data = response.data;

    if (!isJsonObject(data) || !Array.isArray(data.accounts)) {
      throw new Error('La oferta de adelanto de sueldo no devolvió un arreglo data.accounts válido.');
    }

    return { accounts: data.accounts.filter(isJsonObject) };
  }

  /** Tasas e identificador de la oferta para la cuenta del caso, buscada por shortLabel. */
  public static ratesFor(offer: SalaryAdvanceOffer, shortLabel: string): SalaryAdvanceRates {
    const matches = offer.accounts.filter((account) => account.shortLabel === shortLabel);

    if (matches.length !== 1) {
      throw new Error(
        matches.length === 0
          ? `La oferta de adelanto no incluye la cuenta "${shortLabel}".`
          : `La oferta de adelanto incluye ${matches.length} veces la cuenta "${shortLabel}".`,
      );
    }

    const account = matches[0];

    return {
      cft: toLegacyInteger(account.cft, 'cft'),
      idSalaryAdvance: toLegacyInteger(account.idSalaryAdvance, 'idSalaryAdvance'),
      tea: toLegacyInteger(account.tea, 'tea'),
      tna: toNumber(account.tna, 'tna'),
    };
  }

  public async readAmounts(session: AuthenticatedSession, amount: number): Promise<SalaryAdvanceAmounts> {
    const response = await this.post(
      session,
      LOAN_AMOUNTS_ENDPOINT,
      { amount, origin: 'PRD', ...LOAN_AMOUNTS_METADATA },
      'consulta de montos del adelanto',
    );
    const data = response.data;

    if (!isJsonObject(data) || !isJsonObject(data.amountLoan) || !isJsonObject(data.moneyToAccredit)) {
      throw new Error('La consulta de montos del adelanto no devolvió amountLoan y moneyToAccredit.');
    }

    return {
      amountLoan: toLegacyInteger(data.amountLoan.quantity, 'amountLoan.quantity'),
      moneyToAccredit: toLegacyInteger(data.moneyToAccredit.quantity, 'moneyToAccredit.quantity'),
    };
  }

  /** Confirma el adelanto con OTP por SMS. Ejecuta la mutación una sola vez por intento. */
  public async confirm(
    session: AuthenticatedSession,
    username: string,
    confirmation: SalaryAdvanceConfirmation,
  ): Promise<void> {
    const otp = await this.smsOperationOtp.resolve(session, username);

    await this.post(
      session,
      CONFIRM_ENDPOINT,
      {
        idProduct: confirmation.idProduct,
        amount: confirmation.amount,
        idAdvance: confirmation.rates.idSalaryAdvance,
        tna: confirmation.rates.tna,
        tea: confirmation.rates.tea,
        cft: confirmation.rates.cft,
        currency: 'ARS',
        amountSeals: confirmation.amounts.amountLoan,
        moneyToAccredit: confirmation.amounts.moneyToAccredit,
        _otp: `OTP_TOKEN@${otp}`,
        ...CONFIRM_METADATA,
      },
      'confirmación del adelanto de sueldo',
    );
  }

  private async post(
    session: AuthenticatedSession,
    endpoint: string,
    data: ApiJsonObject,
    step: string,
  ): Promise<ApiJsonObject> {
    return this.api.post({
      data,
      endpoint,
      expectedCodes: SUCCESS_CODES,
      headers: { Authorization: session.authorizationHeader },
      step,
    });
  }
}
