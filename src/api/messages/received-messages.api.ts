import { randomUUID } from 'node:crypto';
import type { AuthenticatedSession } from '../../models/authenticated-session';
import { ReportingApiRequest, type ApiJsonObject } from '../reporting-api-request';

const SUCCESS_CODE = 'COR000I';
// Códigos de éxito del validador legado (Config/ValidarResponse.validateResponses).
const LEGACY_SUCCESS_CODES: readonly string[] = ['COR000I', 'COR013I', 'API690W', 'API691W', 'API692W'];
// Valor fijo de la plantilla legada Requests/Mensajes/communicationsListMSG.json.
const READ_STATE_AJAX_UUID = 'ac849396-58c2-4ee5-8955-ea39326a22c9';
// La plantilla communicationsList.json usa el mismo ajax_uuid fijo.
const ATTACHMENTS_AJAX_UUID = READ_STATE_AJAX_UUID;

/** Recorre `value` como el operador `..fileList` de JSONPath y devuelve los archivos encontrados. */
function collectFileLists(value: unknown, found: unknown[] = []): unknown[] {
  if (Array.isArray(value)) {
    value.forEach((item) => collectFileLists(item, found));
  } else if (isJsonObject(value)) {
    for (const [key, child] of Object.entries(value)) {
      if (key === 'fileList' && Array.isArray(child)) {
        found.push(...child);
      }

      collectFileLists(child, found);
    }
  }

  return found;
}
const APP_VERSION = '1.79.1';
const ENDPOINT = '/api/v1/execute/communications.list';

function isJsonObject(value: unknown): value is ApiJsonObject {
  return typeof value === 'object' && value !== null && !Array.isArray(value);
}

export interface ReceivedMessagesResult {
  readonly hasDataObject: boolean;
}

export interface MessagesByReadStateResult {
  /** Valor crudo de `userRead` de cada mensaje devuelto, en el orden de la respuesta. */
  readonly userReadValues: readonly unknown[];
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

  /** Lista los mensajes recibidos filtrando por `onlyUnread`, como la consulta legada de leídos/no leídos. */
  public async listByReadState(session: AuthenticatedSession, onlyUnread: boolean): Promise<MessagesByReadStateResult> {
    const response = await this.api.post({
      data: {
        ajax_uuid: READ_STATE_AJAX_UUID,
        direction: 'BANK_TO_CUSTOMER',
        onlyUnread,
        xAppVersion: APP_VERSION,
      },
      endpoint: ENDPOINT,
      expectedCodes: LEGACY_SUCCESS_CODES,
      headers: { Authorization: session.authorizationHeader },
      step: onlyUnread ? 'consulta de mensajes sin leer' : 'consulta de mensajes leídos',
    });
    const data = response.data;

    if (!isJsonObject(data) || !Array.isArray(data.communications)) {
      throw new Error('La consulta de mensajes no devolvió un arreglo data.communications válido.');
    }

    return {
      userReadValues: data.communications.map((message: unknown) =>
        isJsonObject(message) ? message.userRead : undefined,
      ),
    };
  }

  /**
   * Id del primer archivo adjunto con el nombre indicado en los mensajes recibidos
   * (legado MensajesSteps.obtenerIdArchivoParaDescargaPagos*). Hace una sola consulta.
   */
  public async findAttachmentFileId(session: AuthenticatedSession, fileName: string): Promise<unknown> {
    const response = await this.api.post({
      data: { ajax_uuid: ATTACHMENTS_AJAX_UUID, direction: 'BANK_TO_CUSTOMER', xAppVersion: APP_VERSION },
      endpoint: ENDPOINT,
      expectedCodes: LEGACY_SUCCESS_CODES,
      headers: { Authorization: session.authorizationHeader },
      step: 'búsqueda del archivo enviado a Mensajes',
    });
    const attachments = isJsonObject(response.data) ? response.data.attachments : undefined;
    const file = collectFileLists(attachments).find((item) => isJsonObject(item) && item.fileName === fileName);

    if (!isJsonObject(file) || file.idFile === undefined || file.idFile === null) {
      throw new Error(`Mensajes no contiene el archivo "${fileName}".`);
    }

    return file.idFile;
  }
}
