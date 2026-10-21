import type { AuthenticatedSession } from '../../models/authenticated-session';
import type { MercadoLibrePayment } from '../../test-data/payment-services-data';
import { legacyNow, legacyStartOfDayDaysAgo } from '../legacy-request-dates';
import { ReportingApiRequest, type ApiJsonObject } from '../reporting-api-request';

const LIST_ENDPOINT = '/api/v1/execute/payments.made.list';
const DOWNLOAD_ENDPOINT = '/api/v1/execute/files.download';
// Códigos de éxito del validador legado (Config/ValidarResponse.validateResponses).
const SUCCESS_CODES: readonly string[] = ['COR000I', 'COR013I', 'API690W', 'API691W', 'API692W'];
// Metadatos fijos de Requests/Pagos/paymentsMadeList.json y fileDownload.json.
const LIST_METADATA = { ajax_uuid: 'e8aa7831-ba38-4d71-93b8-773ed1e9f372', xAppVersion: '1.88.0' } as const;
const DOWNLOAD_METADATA = { ajax_uuid: '7db3b498-f0e4-4b36-89ee-6dd201b1622f', xAppVersion: '1.88.0' } as const;
// Rango de comprobantes del legado: desde hace 30 días hasta ahora.
const RECEIPTS_RANGE_DAYS = 30;

function isJsonObject(value: unknown): value is ApiJsonObject {
  return typeof value === 'object' && value !== null && !Array.isArray(value);
}

export interface PaymentReceiptsQuery {
  /** `true` solicita el envío del período a Mensajes en el formato indicado. */
  readonly isExport: boolean;
  /** Formato de exportación (`pdf`, `xls`) o vacío en la consulta simple. */
  readonly exportFormat: string;
}

/** Comprobantes de pagos de servicios (legado PagosSteps.comprobantePagos y descargaDeComprobantePeriodo). */
export class PaymentReceiptsApi {
  public constructor(private readonly api: ReportingApiRequest) {}

  /** Consulta o exporta los comprobantes del período; devuelve la cantidad listada si la respuesta la trae. */
  public async list(
    session: AuthenticatedSession,
    debitCardId: string,
    payment: MercadoLibrePayment,
    query: PaymentReceiptsQuery,
  ): Promise<{ readonly listedCount: number | undefined }> {
    const now = new Date();
    const response = await this.post(
      session,
      LIST_ENDPOINT,
      {
        idProduct: debitCardId,
        enteCode: payment.enteCode,
        paymentCode: payment.paymentCode,
        categoryCode: payment.categoryCode,
        start: legacyStartOfDayDaysAgo(RECEIPTS_RANGE_DAYS, now),
        end: legacyNow(now),
        page: 1,
        isExport: query.isExport,
        cdpExportFileFormat: query.exportFormat,
        ...LIST_METADATA,
      },
      query.isExport ? `exportación de comprobantes en ${query.exportFormat}` : 'consulta de comprobantes de pagos',
    );
    const data = response.data;
    const paginatedData = isJsonObject(data) ? data.paginatedData : undefined;
    const paginatedList = isJsonObject(paginatedData) ? paginatedData.paginatedList : undefined;
    const elementList = isJsonObject(paginatedList) ? paginatedList.elementList : undefined;

    return { listedCount: Array.isArray(elementList) ? elementList.length : undefined };
  }

  /** Descarga un archivo enviado a Mensajes y devuelve el nombre informado por la respuesta. */
  public async download(session: AuthenticatedSession, idFile: unknown): Promise<{ readonly fileName: unknown }> {
    const response = await this.post(
      session,
      DOWNLOAD_ENDPOINT,
      { idFile, ...DOWNLOAD_METADATA },
      'descarga del período de comprobantes',
    );

    return { fileName: isJsonObject(response.data) ? response.data.fileName : undefined };
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
