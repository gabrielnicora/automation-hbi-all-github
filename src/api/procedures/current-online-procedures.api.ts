import { randomUUID } from 'node:crypto';
import type { AuthenticatedSession } from '../../models/authenticated-session';
import { ReportingApiRequest } from '../reporting-api-request';

const SUCCESS_CODE = 'COR000I';
const APP_VERSION = '1.95.0';
const ENDPOINT = '/api/v1/execute/onlineProcedure.list';

export interface CurrentOnlineProceduresResult {
  readonly wasAccepted: boolean;
}

export class CurrentOnlineProceduresApi {
  public constructor(private readonly api: ReportingApiRequest) {}

  public async list(session: AuthenticatedSession): Promise<CurrentOnlineProceduresResult> {
    await this.api.post({
      data: { ajax_uuid: randomUUID(), xAppVersion: APP_VERSION },
      endpoint: ENDPOINT,
      expectedCodes: [SUCCESS_CODE],
      headers: { Authorization: session.authorizationHeader },
      step: 'consulta de trámites vigentes',
    });

    return { wasAccepted: true };
  }
}
