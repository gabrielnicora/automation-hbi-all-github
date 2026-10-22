import { randomUUID } from 'node:crypto';
import type { AuthenticatedSession } from '../../models/authenticated-session';
import type { RecurringDebinSubscription } from '../../test-data/payment-services-data';
import { SmsOperationOtpApi } from '../authentication/sms-operation-otp.api';
import { legacyNow } from '../legacy-request-dates';
import { ReportingApiRequest, type ApiJsonObject } from '../reporting-api-request';

const ENDPOINTS = {
  accept: '/api/v1/execute/debins.inform.acceptance.accept',
  details: '/api/v1/execute/debins.retrieve.details',
  history: '/api/v1/execute/debins.search',
  pending: '/api/v1/execute/debins.pending.list',
  subscribe: '/api/v1/execute/debins.recurrencySubscription.subscribe',
} as const;
// Códigos de éxito del validador legado (Config/ValidarResponse.validateResponses).
const SUCCESS_CODES: readonly string[] = ['COR000I', 'COR013I', 'API690W', 'API691W', 'API692W'];
const APP_VERSION = '1.75.0';
// Inicio fijo del rango de la plantilla legada Requests/Pagos/debinSearch.json.
const HISTORY_START_DATE = '2022-12-01T12:47:54.000Z';
// ticketData literal de Requests/Pagos/debinsInformAcceptanceAccept.json; los marcadores se reemplazan
// con los datos leídos del DEBIN, como hace el legado sobre el texto del body.
const ACCEPTANCE_TICKET_TEMPLATE =
  '{"id":"_idDebin_","shortDescription":"Alquileres","chargeBack":"","userId":"0","receiptId":"0","currency":"032","amount":1,"expirationTime":null,"description":"TestAutomation","sameHolder":null,"status":"INICIADO","creationDate":"_createDate_","expirationDate":"_expirationDate_","isPreAuthorized":false,"wasReturned":false,"buyer":{"field":null,"fantasyName":null,"holder":"_holderBuyer_","cuit":"_cuitBuyer_","operatesWithPreAuthorizations":null,"account":{"idEnvironment":0,"idProduct":null,"productType":null,"productTypeLabel":null,"productTypeShortLabel":null,"description":null,"productAlias":null,"smsAlias":null,"label":null,"shortLabel":null,"extraInfo":null,"deleted":false,"client":null,"currency":"032","number":null,"ownerName":null,"consolidatedAmount":0,"permissions":null,"family":null,"segment":null,"favourite":false,"overdraftLine":0,"pendingBalance":0,"balance":null,"countableBalance":0,"totalCheckAmount":0,"cbu":"_cbuBuyer_","aliasCbu":null,"statements":null,"overdraft":false,"loadBalance":false,"debitCardNumber":null,"amountPesos":0,"quotation":0,"brandType":null,"id":null,"labelWithBalance":null,"numberBank":null,"bankName":null,"active":false,"acceptDeposit":false,"useSignature":null,"modifyAliasCbu":false,"cbuHash":null,"shortLabelWithBalance":null,"bimonetary":false,"holder":null,"bank":"_bankBuyer_","branch":"_branchBuyer_","alias":"_aliasBuyer_","type":"_typeBuyer_","setupDate":null,"isCurrentAccount":false,"isSavingsBank":false}},"seller":{"field":null,"fantasyName":null,"holder":"_holderSeller_ ","cuit":"_cuitSeller_","operatesWithPreAuthorizations":null,"account":{"idEnvironment":0,"idProduct":null,"productType":null,"productTypeLabel":null,"productTypeShortLabel":null,"description":null,"productAlias":null,"smsAlias":null,"label":null,"shortLabel":null,"extraInfo":null,"deleted":false,"client":null,"currency":"032","number":null,"ownerName":null,"consolidatedAmount":0,"permissions":null,"family":null,"segment":null,"favourite":false,"overdraftLine":0,"pendingBalance":0,"balance":null,"countableBalance":0,"totalCheckAmount":0,"cbu":"_cbuSeller_","aliasCbu":null,"statements":null,"overdraft":false,"loadBalance":false,"debitCardNumber":null,"amountPesos":0,"quotation":0,"brandType":null,"id":null,"labelWithBalance":null,"numberBank":null,"bankName":null,"active":false,"acceptDeposit":false,"useSignature":null,"modifyAliasCbu":false,"cbuHash":null,"shortLabelWithBalance":null,"bimonetary":false,"holder":null,"bank":"_bankSeller_","branch":"_branchSeller_","alias":"_aliasSeller_","type":"_typeSeller_","setupDate":null,"isCurrentAccount":false,"isSavingsBank":false}},"causeOfRejection":"","benefitName":null,"reference":null,"creator":null,"action":"accept","chargebackReason":{},"agreement":true,"idProduct":"_idProducto_"}';

function isJsonObject(value: unknown): value is ApiJsonObject {
  return typeof value === 'object' && value !== null && !Array.isArray(value);
}

/** Equivalente de `JsonPath.read(...).toString()` + `quitarCochetesJsonPath` del legado. */
function legacyText(value: unknown, field: string): string {
  if (value === null || value === undefined) {
    throw new Error(`El detalle del DEBIN no informa ${field}.`);
  }

  return String(value).replace(/\[/g, '').replace(/]/g, '').replace(/"/g, '');
}

function readPath(source: unknown, path: readonly string[]): unknown {
  return path.reduce<unknown>((current, key) => (isJsonObject(current) ? current[key] : undefined), source);
}

export interface DebinParty {
  readonly alias: string;
  readonly bank: string;
  readonly branch: string;
  readonly cbu: string;
  readonly cuit: string;
  readonly holder: string;
  readonly type: string;
}

export interface DebinDetails {
  readonly buyer: DebinParty;
  readonly creationDate: string;
  readonly expirationDate: string;
  readonly id: string;
  readonly seller: DebinParty;
}

function readParty(details: unknown, role: 'buyer' | 'seller'): DebinParty {
  const read = (path: readonly string[]): string =>
    legacyText(readPath(details, [role, ...path]), `${role}.${path.join('.')}`);

  return {
    alias: read(['account', 'alias']),
    bank: read(['account', 'bank']),
    branch: read(['account', 'branch']),
    cbu: read(['account', 'cbu']),
    cuit: read(['cuit']),
    holder: read(['holder']),
    type: read(['account', 'type']),
  };
}

/** Operaciones DEBIN posteriores a la generación (legado PagosSteps). Una mutación por intento. */
export class DebinOperationsApi {
  public constructor(
    private readonly api: ReportingApiRequest,
    private readonly smsOperationOtp: SmsOperationOtpApi,
  ) {}

  /** Consulta de DEBINes históricos; devuelve la cantidad de elementos listados. */
  public async countHistory(session: AuthenticatedSession): Promise<number> {
    const response = await this.post(
      session,
      ENDPOINTS.history,
      { startDate: HISTORY_START_DATE, endDate: legacyNow(), isExport: false, page: '1' },
      'consulta de DEBINes históricos',
    );
    const list = readPath(response.data, ['paginatedData', 'paginatedList', 'elementList']);

    if (!Array.isArray(list)) {
      throw new Error('La consulta de DEBINes históricos no devolvió paginatedList.elementList.');
    }

    return list.length;
  }

  /** Adhiere un DEBIN recurrente desde la cuenta indicada con OTP por SMS. */
  public async subscribeRecurrency(
    session: AuthenticatedSession,
    username: string,
    accountId: string,
    subscription: RecurringDebinSubscription,
  ): Promise<void> {
    const otp = await this.smsOperationOtp.resolve(session, username);

    await this.post(
      session,
      ENDPOINTS.subscribe,
      {
        sellerCuit: subscription.sellerCuit,
        accountId,
        entitlement: subscription.entitlement,
        concept: subscription.concept,
        description: subscription.description,
        reference: subscription.reference,
        ticketData: subscription.ticketData,
        _otp: `OTP_TOKEN@${otp}`,
      },
      'adhesión a DEBIN recurrente',
    );
  }

  /** Id del primer DEBIN con estado PENDIENTE del listado de pendientes. */
  public async findFirstPendingDebinId(session: AuthenticatedSession): Promise<string> {
    const response = await this.post(session, ENDPOINTS.pending, { isExport: false }, 'consulta de DEBINes pendientes');
    const debins = readPath(response.data, ['debins']);
    const pending = Array.isArray(debins)
      ? debins.filter((debin): debin is ApiJsonObject => isJsonObject(debin) && debin.status === 'PENDIENTE')
      : [];

    if (pending.length === 0) {
      throw new Error('No hay DEBINes en estado PENDIENTE para aceptar.');
    }

    return legacyText(pending[0].id, 'id del DEBIN pendiente');
  }

  public async readDetails(session: AuthenticatedSession, debinId: string): Promise<DebinDetails> {
    const response = await this.post(session, ENDPOINTS.details, { debinId }, 'consulta del detalle del DEBIN');
    const details = readPath(response.data, ['details']);

    return {
      buyer: readParty(details, 'buyer'),
      creationDate: legacyText(readPath(details, ['creationDate']), 'creationDate'),
      expirationDate: legacyText(readPath(details, ['expirationDate']), 'expirationDate'),
      id: debinId,
      seller: readParty(details, 'seller'),
    };
  }

  /** Acepta el DEBIN con OTP por SMS, debitando desde la tarjeta de débito indicada. */
  public async accept(
    session: AuthenticatedSession,
    username: string,
    details: DebinDetails,
    debitCardId: string,
  ): Promise<void> {
    const otp = await this.smsOperationOtp.resolve(session, username);
    const replacements: Record<string, string> = {
      _idDebin_: details.id,
      _createDate_: details.creationDate,
      _expirationDate_: details.expirationDate,
      _holderBuyer_: details.buyer.holder,
      _cuitBuyer_: details.buyer.cuit,
      _cbuBuyer_: details.buyer.cbu,
      _bankBuyer_: details.buyer.bank,
      _branchBuyer_: details.buyer.branch,
      _aliasBuyer_: details.buyer.alias,
      _typeBuyer_: details.buyer.type,
      _holderSeller_: details.seller.holder,
      _cuitSeller_: details.seller.cuit,
      _cbuSeller_: details.seller.cbu,
      _bankSeller_: details.seller.bank,
      _branchSeller_: details.seller.branch,
      _aliasSeller_: details.seller.alias,
      _typeSeller_: details.seller.type,
      _idProducto_: debitCardId,
    };
    const ticketData = Object.entries(replacements).reduce(
      (text, [marker, value]) => text.split(marker).join(value),
      ACCEPTANCE_TICKET_TEMPLATE,
    );

    await this.post(
      session,
      ENDPOINTS.accept,
      {
        debinId: details.id,
        ticketData,
        acceptConditions: true,
        targetCBU: details.seller.cbu,
        currency: 'ARS',
        totalAmount: 1,
        _otp: `OTP_TOKEN@${otp}`,
      },
      'aceptación del DEBIN',
    );
  }

  private async post(
    session: AuthenticatedSession,
    endpoint: string,
    payload: ApiJsonObject,
    step: string,
  ): Promise<ApiJsonObject> {
    return this.api.post({
      // UUID por request según la guía de la familia DEBIN.
      data: { ...payload, ajax_uuid: randomUUID(), xAppVersion: APP_VERSION },
      endpoint,
      expectedCodes: SUCCESS_CODES,
      headers: { Authorization: session.authorizationHeader },
      step,
    });
  }
}
