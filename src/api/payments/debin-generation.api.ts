import { randomUUID } from 'node:crypto';
import type { AuthenticatedSession } from '../../models/authenticated-session';
import type { DebinGenerationData } from '../../test-data/debin-generation-data';
import { ReportingApiRequest, type ApiJsonObject } from '../reporting-api-request';

const SUCCESS_CODE = 'COR000I';
const ACCOUNTS_ENDPOINT = '/api/v1/execute/widgets.accounts';
const LINK_SYNCHRONIZATION_ENDPOINT = '/api/v1/execute/payment.idCard.link';
const GENERATE_DEBIN_ENDPOINT = '/api/v1/execute/debin.generate';

function isJsonObject(value: unknown): value is ApiJsonObject {
  return typeof value === 'object' && value !== null && !Array.isArray(value);
}

function readOriginAccountId(body: ApiJsonObject, originAccount: string): string {
  const data = body.data;

  if (!isJsonObject(data) || !Array.isArray(data.accounts)) {
    throw new Error('La consulta de cuentas no devolvió un arreglo accounts válido.');
  }

  const matches = data.accounts.filter(
    (account): account is ApiJsonObject =>
      isJsonObject(account) && account.shortLabel === originAccount && typeof account.idProduct === 'string',
  );

  if (matches.length === 0) {
    throw new Error(
      `Datos de prueba inválidos: ctaOrigen "${originAccount}" no coincide con ninguna cuenta disponible.`,
    );
  }

  if (matches.length > 1) {
    throw new Error(
      `Datos de prueba ambiguos: ctaOrigen "${originAccount}" coincide con ${matches.length} cuentas disponibles.`,
    );
  }

  const accountId = matches[0].idProduct;

  if (typeof accountId !== 'string') {
    throw new Error('La cuenta origen resuelta no contiene un idProduct válido.');
  }

  return accountId;
}

export interface DebinGenerationResult {
  readonly wasAccepted: boolean;
}

export class DebinGenerationApi {
  public constructor(private readonly api: ReportingApiRequest) {}

  public async generate(session: AuthenticatedSession, testData: DebinGenerationData): Promise<DebinGenerationResult> {
    const accounts = await this.post('consulta de cuentas para DEBIN', ACCOUNTS_ENDPOINT, {}, session, '1.74.0');
    const originAccountId = readOriginAccountId(accounts, testData.originAccount);

    await this.post('sincronización de pagos Link', LINK_SYNCHRONIZATION_ENDPOINT, {}, session, '1.95.0');
    await this.post(
      'generación de orden DEBIN',
      GENERATE_DEBIN_ENDPOINT,
      {
        accountSelector: originAccountId,
        aliasCbu: testData.destinationCbu,
        amount: 1,
        buyCuit: testData.destinationCuit,
        conceptSelector: 'ALQ',
        currency: 'ARS',
        reference: 'TestAutomation',
        typeSelector: 'CBU',
        validitySelector: '720',
      },
      session,
      '1.74.0',
    );

    return { wasAccepted: true };
  }

  private async post(
    step: string,
    endpoint: string,
    payload: ApiJsonObject,
    session: AuthenticatedSession,
    appVersion: string,
  ): Promise<ApiJsonObject> {
    return this.api.post({
      data: { ...payload, ajax_uuid: randomUUID(), xAppVersion: appVersion },
      endpoint,
      expectedCodes: [SUCCESS_CODE],
      headers: { Authorization: session.authorizationHeader },
      step,
    });
  }
}
