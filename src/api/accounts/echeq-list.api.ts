import type { AuthenticatedSession } from '../../models/authenticated-session';
import { ReportingApiRequest } from '../reporting-api-request';

const ENDPOINT = '/api/v1/execute/list.echeck';

/** Valor enviado tal cual en el JSON; los casos negativos usan también booleanos. */
export type EcheqListFilterValue = string | boolean;

export interface EcheqListRequest {
  readonly echeqNumber: EcheqListFilterValue;
  readonly filterCuil: EcheqListFilterValue;
  readonly operationType: EcheqListFilterValue;
  readonly page: EcheqListFilterValue;
  readonly statement: EcheqListFilterValue;
}

export interface EcheqListResult {
  readonly code: string;
  readonly message: string | undefined;
}

export class EcheqListApi {
  public constructor(private readonly api: ReportingApiRequest) {}

  /**
   * Consulta el listado de eCheq con los filtros recibidos. Replica la plantilla legada
   * Requests/Echeq/listEcheqValidaciones.json, que no incluye ajax_uuid ni xAppVersion.
   * `acceptedCodes` son los códigos de negocio que el escenario espera.
   */
  public async list(
    session: AuthenticatedSession,
    request: EcheqListRequest,
    acceptedCodes: readonly string[],
  ): Promise<EcheqListResult> {
    const response = await this.api.post({
      data: {
        echeqNumber: request.echeqNumber,
        page: request.page,
        statement: request.statement,
        filterCuil: request.filterCuil,
        operationType: request.operationType,
      },
      endpoint: ENDPOINT,
      expectedCodes: acceptedCodes,
      headers: { Authorization: session.authorizationHeader },
      step: 'consulta del listado de eCheq',
    });

    return {
      code: String(response.code),
      message: typeof response.message === 'string' ? response.message : undefined,
    };
  }
}
