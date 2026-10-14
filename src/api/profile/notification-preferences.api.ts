import { randomUUID } from 'node:crypto';
import type { AuthenticatedSession } from '../../models/authenticated-session';
import { ReportingApiRequest, type ApiJsonObject } from '../reporting-api-request';

const SUCCESS_CODE = 'COR000I';
const PRE_ENDPOINT = '/api/v1/execute/preferences.notifications.configuration.pre';
const CONFIGURATION_ENDPOINT = '/api/v1/execute/preferences.notifications.configuration.modify.pre';

function isJsonObject(value: unknown): value is ApiJsonObject {
  return typeof value === 'object' && value !== null && !Array.isArray(value);
}

export interface NotificationPreferencesResult {
  readonly hasMailPreference: boolean;
  readonly hasPushPreference: boolean;
  readonly hasSmsPreference: boolean;
}

export class NotificationPreferencesApi {
  public constructor(private readonly api: ReportingApiRequest) {}

  public async getMessagePreferences(session: AuthenticatedSession): Promise<NotificationPreferencesResult> {
    await this.post('preconsulta de preferencias de notificaciones', PRE_ENDPOINT, {}, '1.95.0', session);
    const response = await this.post(
      'consulta de preferencias de notificaciones de mensajes',
      CONFIGURATION_ENDPOINT,
      { communicationType: 'message' },
      '1.79.1',
      session,
    );
    const data = response.data;
    const communicationConfigurations = isJsonObject(data) ? data.communicationConfigurations : undefined;

    if (!isJsonObject(communicationConfigurations)) {
      throw new Error('La configuración de notificaciones no devolvió communicationConfigurations válido.');
    }

    return {
      hasMailPreference: typeof communicationConfigurations.subscribedMail === 'boolean',
      hasPushPreference: typeof communicationConfigurations.subscribedPush === 'boolean',
      hasSmsPreference: typeof communicationConfigurations.subscribedSMS === 'boolean',
    };
  }

  private async post(
    step: string,
    endpoint: string,
    payload: ApiJsonObject,
    appVersion: string,
    session: AuthenticatedSession,
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
