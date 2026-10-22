import type { AuthenticatedSession } from '../../models/authenticated-session';
import { fillLegacyTemplate } from '../legacy-template';
import { legacyNow } from '../legacy-request-dates';
import { ReportingApiRequest, type ApiJsonObject } from '../reporting-api-request';
import historyTemplate from './transfer-templates/transferListHistory.json';
import historySearchTemplate from './transfer-templates/transferListHistoryPorRangoNombreODescripcion.json';

const ENDPOINT = '/api/v1/execute/transfer.list.history';
// Códigos de éxito del validador legado (Config/ValidarResponse.validateResponses).
const SUCCESS_CODES: readonly string[] = ['COR000I', 'COR013I', 'API690W', 'API691W', 'API692W'];

function isJsonObject(value: unknown): value is ApiJsonObject {
  return typeof value === 'object' && value !== null && !Array.isArray(value);
}

/** `Utils.fechaTransferenciaUnAñoAntiguedad(dias)`: ahora − días, hora local con el literal `Z`. */
function legacyDaysAgo(days: number): string {
  const date = new Date();
  date.setDate(date.getDate() - days);
  return legacyNow(date);
}

export interface TransferHistoryPage {
  /** `creationDateTime` de cada transferencia de la página, en el orden de la respuesta. */
  readonly creationDateTimes: readonly unknown[];
  readonly totalPages: number;
}

/** Historial de transferencias (legado TransferenciasSteps.validaHistorial*). */
export class TransferHistoryApi {
  public constructor(private readonly api: ReportingApiRequest) {}

  /** Último año hasta ahora (`historialTransferencias`: hasta `fechaHoy`). */
  public listLastYear(session: AuthenticatedSession): Promise<TransferHistoryPage> {
    return this.list(
      session,
      fillLegacyTemplate(historyTemplate, [
        ['_dateFrom_', legacyDaysAgo(365)],
        ['_dateTo_', legacyNow()],
      ]),
      'historial de transferencias del último año',
    );
  }

  /** Último año con ambos extremos calculados por días (`historialTransferenciasUnAño`). */
  public listOneYearRange(session: AuthenticatedSession): Promise<TransferHistoryPage> {
    return this.list(
      session,
      fillLegacyTemplate(historyTemplate, [
        ['_dateFrom_', legacyDaysAgo(365)],
        ['_dateTo_', legacyDaysAgo(0)],
      ]),
      'historial de transferencias de un año de antigüedad',
    );
  }

  /** Últimos 180 días filtrando por nombre o descripción. */
  public searchLastSemester(session: AuthenticatedSession, searchedText: string): Promise<TransferHistoryPage> {
    return this.list(
      session,
      fillLegacyTemplate(historySearchTemplate, [
        ['_dateFrom_', legacyDaysAgo(180)],
        ['_dateTo_', legacyNow()],
        ['_searchedText_', searchedText],
      ]),
      `historial de transferencias buscando "${searchedText}"`,
    );
  }

  private async list(session: AuthenticatedSession, data: ApiJsonObject, step: string): Promise<TransferHistoryPage> {
    const response = await this.api.post({
      data,
      endpoint: ENDPOINT,
      expectedCodes: SUCCESS_CODES,
      headers: { Authorization: session.authorizationHeader },
      step,
    });
    const body = isJsonObject(response.data) ? response.data : {};

    if (typeof body.totalPages !== 'number') {
      throw new Error('El historial no devolvió data.totalPages numérico.');
    }

    const transfers = Array.isArray(body.transfers) ? body.transfers : [];

    return {
      creationDateTimes: transfers.map((transfer: unknown) =>
        isJsonObject(transfer) ? transfer.creationDateTime : undefined,
      ),
      totalPages: body.totalPages,
    };
  }
}
