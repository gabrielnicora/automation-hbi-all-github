import type { AuthenticatedSession } from '../../models/authenticated-session';
import type { WithdrawalOrderData } from '../../test-data/withdrawal-order-data';
import { SecondFactorChallengeApi } from '../authentication/second-factor-challenge.api';
import { ReportingApiRequest, type ApiJsonObject } from '../reporting-api-request';
import { ProfileMobilePhoneApi } from '../profile/profile-mobile-phone.api';

const SUCCESS_CODES = ['COR000I', 'COR013I', 'API690W', 'API691W', 'API692W'] as const;
const ENDPOINTS = {
  accounts: '/api/v1/execute/widgets.accounts',
  generate: '/api/v1/execute/atm.generatePullOrder.byAccounts',
  sendSmsOtp: '/api/v1/execute/secondFactor.otp.sms.sendToken',
} as const;

const ORDER_REQUEST = {
  ajax_uuid: '6afb0b1d-9053-4928-9f84-89e151ee21bc',
  amount: 100,
  appVersion: '1.79.1',
  currency: 'ARS',
  idOrder: 'f111ee40-1634-11f0-9a5a-b31db4c8c0ab',
  reference: 'TestAutomation',
} as const;

function isJsonObject(value: unknown): value is ApiJsonObject {
  return typeof value === 'object' && value !== null && !Array.isArray(value);
}

function readRequiredObject(source: ApiJsonObject, propertyName: string, context: string): ApiJsonObject {
  const value = source[propertyName];

  if (!isJsonObject(value)) {
    throw new Error(`${context} no devolvió un objeto ${propertyName} válido.`);
  }

  return value;
}

function readRequiredArray(source: ApiJsonObject, propertyName: string, context: string): readonly unknown[] {
  const value = source[propertyName];

  if (!Array.isArray(value)) {
    throw new Error(`${context} no devolvió un arreglo ${propertyName} válido.`);
  }

  return value;
}

function readRequiredString(source: ApiJsonObject, propertyName: string, context: string): string {
  const value = source[propertyName];

  if (typeof value !== 'string' || value.trim().length === 0) {
    throw new Error(`${context} no devolvió ${propertyName} válido.`);
  }

  return value.trim();
}

export class WithdrawalOrderApi {
  public constructor(
    private readonly api: ReportingApiRequest,
    private readonly profileMobilePhone: ProfileMobilePhoneApi,
    private readonly secondFactor: SecondFactorChallengeApi,
  ) {}

  public async generate(session: AuthenticatedSession, username: string, data: WithdrawalOrderData): Promise<void> {
    const documentNumber = session.documentNumber;

    if (!documentNumber) {
      throw new Error('El login API no devolvió el número de documento requerido para la orden de extracción.');
    }

    const account = await this.readOriginAccount(session, data.originAccount);
    const phone = await this.profileMobilePhone.readCurrentPhone(session);
    const otp = await this.secondFactor.resolveOperationOtp(session, username, {
      criteria: { origin: phone },
      requestInitialCode: () => this.sendSmsOtp(session),
    });

    await this.api.post({
      data: {
        ajax_uuid: ORDER_REQUEST.ajax_uuid,
        currency: ORDER_REQUEST.currency,
        orders: [
          {
            amount: ORDER_REQUEST.amount,
            documentNumber,
            documentType: 'DNI',
            documentTypeLabel: 'DNI',
            idOrder: ORDER_REQUEST.idOrder,
            idProduct: account.idProduct,
            originAccountLabel: data.originAccount,
            originAccountNumber: account.number,
            reference: ORDER_REQUEST.reference,
          },
        ],
        total: ORDER_REQUEST.amount,
        xAppVersion: ORDER_REQUEST.appVersion,
        _otp: `OTP_TOKEN@${otp}`,
      },
      endpoint: ENDPOINTS.generate,
      expectedCodes: SUCCESS_CODES,
      headers: { Authorization: session.authorizationHeader },
      step: 'generación de la orden de extracción',
    });
  }

  private async readOriginAccount(
    session: AuthenticatedSession,
    shortLabel: string,
  ): Promise<{ readonly idProduct: string; readonly number: string }> {
    const body = await this.api.post({
      data: {},
      endpoint: ENDPOINTS.accounts,
      expectedCodes: SUCCESS_CODES,
      headers: { Authorization: session.authorizationHeader },
      step: 'consulta de cuentas para orden de extracción',
    });
    const data = readRequiredObject(body, 'data', 'La consulta de cuentas');
    const accounts = readRequiredArray(data, 'accounts', 'La consulta de cuentas');
    const matches = accounts.filter(
      (item): item is ApiJsonObject => isJsonObject(item) && item.shortLabel === shortLabel,
    );

    if (matches.length !== 1) {
      throw new Error(
        matches.length === 0
          ? 'No se encontró la cuenta origen configurada.'
          : 'La cuenta origen configurada está duplicada.',
      );
    }

    return {
      idProduct: readRequiredString(matches[0], 'idProduct', 'La cuenta origen'),
      number: readRequiredString(matches[0], 'number', 'La cuenta origen'),
    };
  }

  private async sendSmsOtp(session: AuthenticatedSession): Promise<void> {
    await this.api.post({
      data: {
        ajax_uuid: '274dd2b3-9fc7-4f82-b954-34a5d784ec9a',
        xAppVersion: '1.95.0',
      },
      endpoint: ENDPOINTS.sendSmsOtp,
      expectedCodes: SUCCESS_CODES,
      headers: { Authorization: session.authorizationHeader },
      step: 'solicitud de OTP por SMS para orden de extracción',
    });
  }
}
