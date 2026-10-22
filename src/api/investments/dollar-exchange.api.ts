import type { AuthenticatedSession } from '../../models/authenticated-session';
import { ReportingApiRequest } from '../reporting-api-request';

const ENDPOINT = '/api/v1/execute/buyselldollar.buyorselldollartransfer';
// Códigos de éxito del validador legado (Config/ValidarResponse.validateResponses).
const SUCCESS_CODES: readonly string[] = ['COR000I', 'COR013I', 'API690W', 'API691W', 'API692W'];
const APP_VERSION = '1.80.0';
// ajax_uuid fijos de Requests/Inversiones/buyDollarTransfer.json y sellDollarTransfer.json.
const AJAX_UUID = {
  buy: '0c3d4a96-a2b7-40e7-aaf4-a6fadddd752f',
  sell: 'df1a9ed2-15ed-4819-8719-779a156c588c',
} as const;

export interface DollarExchangeRequest {
  /** Importe tal como lo envía el legado: texto en compra y número en venta. */
  readonly amount: string | number;
  readonly dollarProductId: string;
  readonly pesosProductId: string;
}

/** Compra y venta de dólares (legado InversionesSteps.compraUSD y ventaUSD). Una mutación por intento. */
export class DollarExchangeApi {
  public constructor(private readonly api: ReportingApiRequest) {}

  public async buy(session: AuthenticatedSession, request: DollarExchangeRequest): Promise<void> {
    await this.exchange(session, request, 'C', AJAX_UUID.buy, 'compra de dólares');
  }

  public async sell(session: AuthenticatedSession, request: DollarExchangeRequest): Promise<void> {
    await this.exchange(session, request, 'V', AJAX_UUID.sell, 'venta de dólares');
  }

  private async exchange(
    session: AuthenticatedSession,
    request: DollarExchangeRequest,
    transferType: 'C' | 'V',
    ajaxUuid: string,
    step: string,
  ): Promise<void> {
    await this.api.post({
      data: {
        transferType,
        amount: request.amount,
        idDollarProduct: request.dollarProductId,
        idPesosProduct: request.pesosProductId,
        currency: 'USD',
        agreement: true,
        ajax_uuid: ajaxUuid,
        xAppVersion: APP_VERSION,
      },
      endpoint: ENDPOINT,
      expectedCodes: SUCCESS_CODES,
      headers: { Authorization: session.authorizationHeader },
      step,
    });
  }
}
