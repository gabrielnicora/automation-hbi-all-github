import type { AuthenticatedSession } from '../../models/authenticated-session';
import { ReportingApiRequest, type ApiJsonObject } from '../reporting-api-request';

const ENDPOINT = '/api/v1/execute/util.logger.read.transactions';
const SUCCESS_CODES: readonly string[] = ['COR000I', 'COR013I', 'API690W', 'API691W', 'API692W'];

function isJsonObject(value: unknown): value is ApiJsonObject {
  return typeof value === 'object' && value !== null && !Array.isArray(value);
}

function readRequiredObject(source: ApiJsonObject, propertyName: string, context: string): ApiJsonObject {
  const value = source[propertyName];

  if (!isJsonObject(value)) {
    throw new Error(`${context} no devolvió un objeto ${propertyName} válido.`);
  }

  return value;
}

export class ProfileMobilePhoneApi {
  public constructor(private readonly api: ReportingApiRequest) {}

  public async readCurrentPhone(session: AuthenticatedSession): Promise<string> {
    const userData = await this.readUserData(session, 'lectura del teléfono actual del perfil');
    const mobileNumber = userData.mobileNumber;
    const phone =
      typeof mobileNumber === 'string'
        ? mobileNumber.trim()
        : typeof mobileNumber === 'number' && Number.isFinite(mobileNumber)
          ? String(mobileNumber)
          : undefined;

    if (!phone) {
      const mobileNumberType = mobileNumber === null ? 'null' : typeof mobileNumber;
      throw new Error(`La lectura de perfil no devolvió mobileNumber válido. Tipo recibido: ${mobileNumberType}.`);
    }

    return normalizeProfileMobilePhone(phone);
  }

  /** Email del perfil (`userData.email`), origen de los códigos enviados por correo (legado `LoginAPI.email`). */
  public async readCurrentEmail(session: AuthenticatedSession): Promise<string> {
    const userData = await this.readUserData(session, 'lectura del email del perfil');
    const email = typeof userData.email === 'string' ? userData.email.trim() : '';

    if (!email) {
      throw new Error('La lectura de perfil no devolvió email válido.');
    }

    return email;
  }

  private async readUserData(session: AuthenticatedSession, step: string): Promise<ApiJsonObject> {
    const body = await this.api.post({
      data: {
        lang: 'es',
        idSession: session.accessToken,
        channel: 'frontend',
        ajax_uuid: '',
        xAppVersion: '',
      },
      endpoint: ENDPOINT,
      expectedCodes: SUCCESS_CODES,
      step,
    });
    const data = readRequiredObject(body, 'data', 'La lectura de perfil');
    return readRequiredObject(data, 'userData', 'La lectura de perfil');
  }
}

export function normalizeProfileMobilePhone(value: string): string {
  const digits = value.replace(/\D/g, '');

  if (digits.length < 10) {
    throw new Error('La lectura de perfil devolvió un teléfono inválido.');
  }

  return digits.slice(-10);
}
