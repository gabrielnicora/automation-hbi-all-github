import { randomUUID } from 'node:crypto';
import type { AuthenticatedSession } from '../../models/authenticated-session';
import type { TraditionalFixedTermData } from '../../test-data/traditional-fixed-term-data';
import { ReportingApiRequest, type ApiJsonObject } from '../reporting-api-request';

const SUCCESS_CODE = 'COR000I';
const APP_VERSION = '1.78.0';
const ACCOUNTS_ENDPOINT = '/api/v1/execute/widgets.accounts';
const CREATE_FIXED_TERM_ENDPOINT = '/api/v1/execute/fixedTerms.make.send';

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

export interface TraditionalFixedTermResult {
  readonly wasAccepted: boolean;
}

export class TraditionalFixedTermApi {
  public constructor(private readonly api: ReportingApiRequest) {}

  public async create(
    session: AuthenticatedSession,
    testData: TraditionalFixedTermData,
  ): Promise<TraditionalFixedTermResult> {
    const accounts = await this.post('consulta de cuentas para plazo fijo', ACCOUNTS_ENDPOINT, {}, session);
    const accountId = readOriginAccountId(accounts, testData.originAccount);

    await this.post(
      'alta de plazo fijo tradicional',
      CREATE_FIXED_TERM_ENDPOINT,
      {
        accountId,
        amount: '5002',
        currency: 'ARS',
        fixedTermType: 'T',
        renewalType: 'N',
        term: '30',
      },
      session,
    );

    return { wasAccepted: true };
  }

  private async post(
    step: string,
    endpoint: string,
    payload: ApiJsonObject,
    session: AuthenticatedSession,
  ): Promise<ApiJsonObject> {
    return this.api.post({
      data: { ...payload, ajax_uuid: randomUUID(), xAppVersion: APP_VERSION },
      endpoint,
      expectedCodes: [SUCCESS_CODE],
      headers: { Authorization: session.authorizationHeader },
      step,
    });
  }
}
