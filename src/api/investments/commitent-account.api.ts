import type { AuthenticatedSession } from '../../models/authenticated-session';
import { ReportingApiRequest, type ApiJsonObject } from '../reporting-api-request';

const ENDPOINT = '/api/v1/execute/list.dynamicFormFields.commitentAccount';
// Códigos de éxito del validador legado (Config/ValidarResponse.validateResponses).
const SUCCESS_CODES: readonly string[] = ['COR000I', 'COR013I', 'API690W', 'API691W', 'API692W'];
// Request común legado Requests/Compartidos/requestVacio.json.
const EMPTY_REQUEST = { ajax_uuid: '274dd2b3-9fc7-4f82-b954-34a5d784ec9a', xAppVersion: '1.95.0' } as const;

function isJsonObject(value: unknown): value is ApiJsonObject {
  return typeof value === 'object' && value !== null && !Array.isArray(value);
}

export interface ClosureFormField {
  readonly order: unknown;
  readonly placeHolder: unknown;
  readonly required: unknown;
}

/** Formulario de baja de cuenta comitente (legado InversionesSteps.ingresoYValidacionBajaCuentaComitente). */
export class CommitentAccountApi {
  public constructor(private readonly api: ReportingApiRequest) {}

  /** Devuelve los campos dinámicos del formulario en el orden de la respuesta. */
  public async readClosureFormFields(session: AuthenticatedSession): Promise<readonly ClosureFormField[]> {
    const response = await this.api.post({
      data: { ...EMPTY_REQUEST },
      endpoint: ENDPOINT,
      expectedCodes: SUCCESS_CODES,
      headers: { Authorization: session.authorizationHeader },
      step: 'consulta del formulario de baja de cuenta comitente',
    });
    const data = response.data;

    if (!isJsonObject(data) || !Array.isArray(data.dynamicFields)) {
      throw new Error('El formulario de baja no devolvió un arreglo data.dynamicFields válido.');
    }

    return data.dynamicFields.filter(isJsonObject).map((field) => ({
      order: field.order,
      placeHolder: field.placeHolder,
      required: field.required,
    }));
  }
}
