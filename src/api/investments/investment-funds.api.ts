import type { AuthenticatedSession } from '../../models/authenticated-session';
import { legacyNow, legacyStartOfDayDaysAgo } from '../legacy-request-dates';
import { ReportingApiRequest, type ApiJsonObject } from '../reporting-api-request';

const ENDPOINTS = {
  download: '/api/v1/execute/investment.movement.download',
  funds: '/api/v1/execute/investment.listFund.pre',
  investor: '/api/v1/execute/investments.data.investor',
  movements: '/api/v1/execute/investment.fund.movements',
} as const;
// Códigos de éxito del validador legado (Config/ValidarResponse.validateResponses).
const SUCCESS_CODES: readonly string[] = ['COR000I', 'COR013I', 'API690W', 'API691W', 'API692W'];
// Metadatos fijos de las plantillas legadas requestVacio.json, investmentFundMovements.json e
// investmentMovementDownload.json.
const EMPTY_REQUEST = { ajax_uuid: '274dd2b3-9fc7-4f82-b954-34a5d784ec9a', xAppVersion: '1.95.0' } as const;
const MOVEMENTS_METADATA = { ajax_uuid: '53b460e6-3901-4454-9474-8961779530fa', xAppVersion: '1.88.0' } as const;
const DOWNLOAD_METADATA = { ajax_uuid: '83b63db9-a7a5-474c-a166-8f51cd4eb832', xAppVersion: '1.88.0' } as const;
// Rango de movimientos del legado: desde hace 40 días hasta ahora.
const MOVEMENTS_RANGE_DAYS = 40;

function isJsonObject(value: unknown): value is ApiJsonObject {
  return typeof value === 'object' && value !== null && !Array.isArray(value);
}

export interface InvestmentFund {
  /** `idInstrument` tal como lo informa el listado; el legado lo reenvía sin comillas. */
  readonly fundId: unknown;
  readonly name: string;
}

export interface FundMovement {
  readonly fund: unknown;
  readonly requestTemplate: unknown;
  readonly statusCode: unknown;
}

/** Fondos comunes de inversión (legado InversionesSteps: listFund.pre, data.investor y movimientos). */
export class InvestmentFundsApi {
  public constructor(private readonly api: ReportingApiRequest) {}

  /** Ingreso a la sección de fondos: devuelve los fondos listados por `investment.listFund.pre`. */
  public async listFunds(session: AuthenticatedSession): Promise<readonly ApiJsonObject[]> {
    const response = await this.post(session, ENDPOINTS.funds, { ...EMPTY_REQUEST }, 'consulta de fondos disponibles');
    const data = response.data;

    if (!isJsonObject(data) || !Array.isArray(data.list)) {
      return [];
    }

    return data.list.filter(isJsonObject);
  }

  /** Número de la primera cuenta comitente del inversor (`data.accounts[0].number`). */
  public async readCommitentAccount(session: AuthenticatedSession): Promise<string> {
    const response = await this.post(session, ENDPOINTS.investor, { ...EMPTY_REQUEST }, 'consulta del inversor');
    const data = response.data;
    const firstAccount = isJsonObject(data) && Array.isArray(data.accounts) ? data.accounts[0] : undefined;

    if (!isJsonObject(firstAccount) || firstAccount.number === undefined || firstAccount.number === null) {
      throw new Error('El inversor no informa una cuenta comitente en data.accounts[0].number.');
    }

    return String(firstAccount.number);
  }

  /** Busca el único fondo con el nombre indicado. */
  public static findFund(funds: readonly ApiJsonObject[], name: string): InvestmentFund {
    const matches = funds.filter((fund) => fund.name === name);

    if (matches.length !== 1) {
      throw new Error(
        matches.length === 0
          ? `El listado de fondos no incluye "${name}".`
          : `El listado de fondos incluye ${matches.length} veces "${name}".`,
      );
    }

    return { fundId: matches[0].idInstrument, name };
  }

  public async listMovements(
    session: AuthenticatedSession,
    fund: InvestmentFund,
    commitentAccount: string,
  ): Promise<readonly FundMovement[]> {
    const now = new Date();
    const response = await this.post(
      session,
      ENDPOINTS.movements,
      {
        fundId: fund.fundId,
        startDate: legacyStartOfDayDaysAgo(MOVEMENTS_RANGE_DAYS, now),
        endDate: legacyNow(now),
        commitentAccount,
        ...MOVEMENTS_METADATA,
      },
      'consulta de movimientos del fondo',
    );
    const data = response.data;

    if (!isJsonObject(data) || !Array.isArray(data.movements)) {
      throw new Error('La consulta de movimientos no devolvió un arreglo data.movements válido.');
    }

    return data.movements.filter(isJsonObject).map((movement) => ({
      fund: movement.fund,
      requestTemplate: movement.requestTemplate,
      statusCode: movement.statusCode,
    }));
  }

  /** Descarga un movimiento y devuelve el contenido cifrado informado en `data.content`. */
  public async downloadMovement(
    session: AuthenticatedSession,
    formName: string,
    keyLongValue: string,
  ): Promise<{ readonly content: unknown }> {
    const response = await this.post(
      session,
      ENDPOINTS.download,
      { formName, keyLongValue, ...DOWNLOAD_METADATA },
      'descarga del movimiento del fondo',
    );
    const data = response.data;

    return { content: isJsonObject(data) ? data.content : undefined };
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
