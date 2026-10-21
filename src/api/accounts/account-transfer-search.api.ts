import type { AuthenticatedSession } from '../../models/authenticated-session';
import { ReportingApiRequest, type ApiJsonObject } from '../reporting-api-request';

const ENDPOINT = '/api/v1/execute/account.transfer.search';
// Valores fijos de la plantilla legada Requests/Cuentas/accountTransferSearch.json.
const AJAX_UUID = '40b0b455-2ad4-4b6d-969b-f55f4028e5f2';
const APP_VERSION = '1.66.0';
// Códigos de éxito del validador legado (Config/ValidarResponse.validateResponses).
const SUCCESS_CODES: readonly string[] = ['COR000I', 'COR013I', 'API690W', 'API691W', 'API692W'];

function isJsonObject(value: unknown): value is ApiJsonObject {
  return typeof value === 'object' && value !== null && !Array.isArray(value);
}

/** Valor enviado tal cual en el JSON; los casos negativos usan también booleanos y null. */
export type AccountTransferSearchValue = string | boolean | null;

export interface AccountTransferSearchRequest {
  readonly account: AccountTransferSearchValue;
  readonly typeAccount: AccountTransferSearchValue;
}

export interface AccountTransferSearchResult {
  readonly code: string;
  readonly message: string | undefined;
}

export interface TransferDestination {
  readonly idAdheredAccount: string | undefined;
  readonly isAdhered: boolean;
  /** Valor `ownAccount` informado por la búsqueda; el alta de agenda lo reenvía sin transformar. */
  readonly ownAccount: unknown;
}

export class AccountTransferSearchApi {
  public constructor(private readonly api: ReportingApiRequest) {}

  /**
   * Busca una cuenta destino por CBU, CVU o alias. `acceptedCodes` son los códigos de negocio
   * que el escenario espera; cualquier otro código o un HTTP distinto de 200 hace fallar la request.
   */
  public async search(
    session: AuthenticatedSession,
    request: AccountTransferSearchRequest,
    acceptedCodes: readonly string[],
  ): Promise<AccountTransferSearchResult> {
    const response = await this.api.post({
      data: {
        typeAccount: request.typeAccount,
        account: request.account,
        ajax_uuid: AJAX_UUID,
        xAppVersion: APP_VERSION,
      },
      endpoint: ENDPOINT,
      expectedCodes: acceptedCodes,
      headers: { Authorization: session.authorizationHeader },
      step: 'búsqueda de cuenta destino',
    });

    return {
      code: String(response.code),
      message: typeof response.message === 'string' ? response.message : undefined,
    };
  }

  /** Busca un destino válido y devuelve si ya está agendado (legado CuentasSteps.consultarDestinatarioAgendaCBU). */
  public async findDestination(
    session: AuthenticatedSession,
    request: AccountTransferSearchRequest,
  ): Promise<TransferDestination> {
    const response = await this.api.post({
      data: {
        typeAccount: request.typeAccount,
        account: request.account,
        ajax_uuid: AJAX_UUID,
        xAppVersion: APP_VERSION,
      },
      endpoint: ENDPOINT,
      expectedCodes: SUCCESS_CODES,
      headers: { Authorization: session.authorizationHeader },
      step: 'búsqueda de destinatario',
    });
    const data = response.data;

    if (!isJsonObject(data) || typeof data.isAdhered !== 'boolean') {
      throw new Error('La búsqueda de destinatario no devolvió data.isAdhered válido.');
    }

    if (!data.isAdhered) {
      return { idAdheredAccount: undefined, isAdhered: false, ownAccount: data.ownAccount };
    }

    const idAdheredAccount = isJsonObject(data.customer) ? data.customer.idAdheredAccount : undefined;

    if (
      (typeof idAdheredAccount !== 'string' && typeof idAdheredAccount !== 'number') ||
      String(idAdheredAccount) === ''
    ) {
      throw new Error('El destinatario agendado no informa data.customer.idAdheredAccount.');
    }

    return { idAdheredAccount: String(idAdheredAccount), isAdhered: true, ownAccount: data.ownAccount };
  }
}
