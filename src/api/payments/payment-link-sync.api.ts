import { randomUUID } from 'node:crypto';
import type { AuthenticatedSession } from '../../models/authenticated-session';
import { ReportingApiRequest } from '../reporting-api-request';

const ENDPOINT = '/api/v1/execute/payment.idCard.link';
// Códigos de éxito del validador legado (Config/ValidarResponse.validateResponses).
const SUCCESS_CODES: readonly string[] = ['COR000I', 'COR013I', 'API690W', 'API691W', 'API692W'];

/** Sincronización de pagos Link previa a cada operación de pagos (legado PagosSteps.sincronizacionPagosLink). */
export class PaymentLinkSyncApi {
  public constructor(private readonly api: ReportingApiRequest) {}

  public async synchronize(session: AuthenticatedSession): Promise<void> {
    await this.api.post({
      data: { ajax_uuid: randomUUID(), xAppVersion: '1.95.0' },
      endpoint: ENDPOINT,
      expectedCodes: SUCCESS_CODES,
      headers: { Authorization: session.authorizationHeader },
      step: 'sincronización de pagos Link',
    });
  }
}
