import { randomUUID } from 'node:crypto';
import type { AuthenticatedSession } from '../../models/authenticated-session';
import { ReportingApiRequest, type ApiJsonObject } from '../reporting-api-request';

const SUCCESS_CODE = 'COR000I';
const APP_VERSION = '1.79.1';
const ENDPOINT = '/api/v1/execute/communications.list';

function isJsonObject(value: unknown): value is ApiJsonObject {
  return typeof value === 'object' && value !== null && !Array.isArray(value);
}

export interface ReceivedMessagesResult {
  readonly hasDataObject: boolean;
}

export class ReceivedMessagesApi {
  public constructor(private readonly api: ReportingApiRequest) {}

  public async list(session: AuthenticatedSession): Promise<ReceivedMessagesResult> {
    const response = await this.api.post({
      data: {
        ajax_uuid: randomUUID(),
        direction: 'BANK_TO_CUSTOMER',
        xAppVersion: APP_VERSION,
      },
      endpoint: ENDPOINT,
      expectedCodes: [SUCCESS_CODE],
      headers: { Authorization: session.authorizationHeader },
      step: 'consulta de mensajes recibidos',
    });

    return { hasDataObject: isJsonObject(response.data) };
  }
}
