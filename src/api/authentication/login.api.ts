import type { LoginCredentials } from '../../test-data/login-credentials';
import { createAuthenticatedSession, type AuthenticatedSession } from '../../models/authenticated-session';
import { ReportingApiRequest, type ApiJsonObject } from '../reporting-api-request';
import { LOGIN_FINGERPRINT } from './login-fingerprint';

type LoginStep = 'step1' | 'step2' | 'step3' | 'step4';

interface ValidLoginStepResponse {
  readonly data: ApiJsonObject;
}

const SUCCESS_CODE = 'COR000I';
const LOGIN_ENDPOINTS: Readonly<Record<LoginStep, string>> = {
  step1: '/api/v1/execute/session.login.step1',
  step2: '/api/v1/execute/session.login.step2',
  step3: '/api/v1/execute/session.login.step3',
  step4: '/api/v1/execute/session.login.step4',
};

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

function readRequiredString(source: ApiJsonObject, propertyName: string, context: string): string {
  const value = source[propertyName];

  if (typeof value !== 'string' || value.trim().length === 0) {
    throw new Error(`${context} no devolvió ${propertyName} válido.`);
  }

  return value.trim();
}

function hasGeneralConditions(data: ApiJsonObject): boolean {
  const value = data.generalConditionsText;
  return typeof value === 'string' ? value.trim().length > 0 : Boolean(value);
}

export class LoginApi {
  public constructor(private readonly api: ReportingApiRequest) {}

  public async authenticate(credentials: LoginCredentials): Promise<AuthenticatedSession> {
    const step1 = await this.executeStep(
      'step1',
      {
        lang: 'es',
        _usuario: credentials.username,
        _secondFactor: '',
        channel: 'frontend',
        ajax_uuid: '',
        xAppVersion: '',
      },
      undefined,
    );
    const exchangeToken = readRequiredString(step1.data, '_exchangeToken', 'El login API en step1');

    const step2 = await this.executeStep(
      'step2',
      {
        lang: 'es',
        _username: credentials.username,
        _password: credentials.password,
        _captcha: '',
        channel: 'frontend',
        ajax_uuid: '',
        xAppVersion: '',
      },
      exchangeToken,
    );
    const step2ExchangeToken = readRequiredString(step2.data, '_exchangeToken', 'El login API en step2');

    if (step2ExchangeToken !== exchangeToken) {
      throw new Error('El exchange token de step2 no coincide con el obtenido en step1.');
    }

    const step3 = await this.executeStep(
      'step3',
      {
        lang: 'es',
        isFromButtonPaymentLogin: false,
        _fingerprint: LOGIN_FINGERPRINT,
        isFromMessenger: false,
        isFromGoogle: false,
        isFromAmazon: false,
        isFromWhatsapp: false,
        accountLinkingToken: null,
        clientNumber: null,
        channel: 'frontend',
        ajax_uuid: '',
        xAppVersion: '',
      },
      exchangeToken,
    );

    const requiresGeneralConditions = hasGeneralConditions(step3.data);
    const finalStep = requiresGeneralConditions
      ? await this.executeStep(
          'step4',
          {
            lang: 'es',
            forceSession: false,
            acceptConditions: true,
            _fingerprint: LOGIN_FINGERPRINT,
            _idDevice: '',
            channel: 'frontend',
            ajax_uuid: '',
            xAppVersion: '',
          },
          exchangeToken,
        )
      : step3;

    const finalStepName = requiresGeneralConditions ? 'step4' : 'step3';
    const accessToken = readRequiredString(finalStep.data, '_accessToken', `El login API en ${finalStepName}`);
    const documentNumber = finalStep.data.documentNumber;

    return createAuthenticatedSession(accessToken, typeof documentNumber === 'string' ? documentNumber : undefined);
  }

  private async executeStep(
    step: LoginStep,
    data: ApiJsonObject,
    exchangeToken: string | undefined,
  ): Promise<ValidLoginStepResponse> {
    const body = await this.api.post({
      data,
      endpoint: LOGIN_ENDPOINTS[step],
      expectedCodes: [SUCCESS_CODE],
      headers: exchangeToken ? { Authorization: `exchange ${exchangeToken}` } : undefined,
      step: `login API en ${step}`,
    });

    return { data: readRequiredObject(body, 'data', `El login API en ${step}`) };
  }
}
