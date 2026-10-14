import { randomUUID } from 'node:crypto';
import type { AuthenticatedSession } from '../../models/authenticated-session';
import type { OwnAccountTransferData } from '../../test-data/own-account-transfer-data';
import { ReportingApiRequest, type ApiJsonObject } from '../reporting-api-request';

const SUCCESS_CODE = 'COR000I';
const APP_VERSION = '2.04.0';
const CURRENCY = 'ARS';
const TRANSFER_AMOUNT = 1;
const BERSA_LEGAL_NAME = 'NUEVO BANCO DE ENTRE RÍOS S.A.';

const ENDPOINTS = {
  accounts: '/api/v1/execute/widgets.accounts',
  debitCards: '/api/v1/execute/widgets.creditCards',
  makeTransfer: '/api/v1/execute/account.transfer.make',
  ticket: '/api/v1/execute/ticket.get',
} as const;

interface AccountProduct {
  readonly cbu: string;
  readonly currency: string;
  readonly idProduct: string;
  readonly label: string;
  readonly number: string;
  readonly shortLabel: string;
}

interface DebitCardProduct {
  readonly currency: string;
  readonly idProduct: string;
  readonly label: string;
}

export interface OwnAccountTransferResult {
  readonly hasReceiptPdf: boolean;
  readonly isOwnAccountTransfer: boolean;
  readonly isTicketFinished: boolean;
  readonly ticketMatchesTransfer: boolean;
}

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

function readRequiredArray(source: ApiJsonObject, propertyName: string, context: string): readonly unknown[] {
  const value = source[propertyName];

  if (!Array.isArray(value)) {
    throw new Error(`${context} no devolvió un arreglo ${propertyName} válido.`);
  }

  return value;
}

function readRequiredString(source: ApiJsonObject, propertyName: string, context: string): string {
  const value = source[propertyName];

  if (typeof value !== 'string' || value.trim().length === 0) {
    throw new Error(`${context} no devolvió ${propertyName} válido.`);
  }

  return value.trim();
}

function selectUniqueObject(
  items: readonly unknown[],
  predicate: (item: ApiJsonObject) => boolean,
  missingMessage: string,
  duplicateMessage: string,
): ApiJsonObject {
  const matches = items.filter((item): item is ApiJsonObject => isJsonObject(item) && predicate(item));

  if (matches.length === 0) {
    throw new Error(missingMessage);
  }

  if (matches.length > 1) {
    throw new Error(duplicateMessage);
  }

  return matches[0];
}

function parseAccount(source: ApiJsonObject, context: string): AccountProduct {
  return {
    cbu: readRequiredString(source, 'cbu', context),
    currency: readRequiredString(source, 'currency', context),
    idProduct: readRequiredString(source, 'idProduct', context),
    label: readRequiredString(source, 'label', context),
    number: readRequiredString(source, 'number', context),
    shortLabel: readRequiredString(source, 'shortLabel', context),
  };
}

function parseDebitCard(source: ApiJsonObject, context: string): DebitCardProduct {
  return {
    currency: readRequiredString(source, 'currency', context),
    idProduct: readRequiredString(source, 'idProduct', context),
    label: readRequiredString(source, 'label', context),
  };
}

export class OwnAccountTransferApi {
  private readonly bcCuil = randomUUID();

  public constructor(private readonly api: ReportingApiRequest) {}

  public async transfer(
    session: AuthenticatedSession,
    testData: OwnAccountTransferData,
  ): Promise<OwnAccountTransferResult> {
    const accounts = await this.readAccounts(session);
    const originAccount = this.selectAccount(accounts, testData.originAccount, 'origen');
    const destinationAccount = this.selectAccount(accounts, testData.destinationAccount, 'destino');
    const debitCards = await this.readDebitCards(session);
    const debitCard = this.selectDebitCard(debitCards, testData.debitCardLastFour);

    this.validateProducts(originAccount, destinationAccount, debitCard, testData.destinationCbu);

    const transferBody = await this.post(
      'transferencia entre cuentas propias',
      ENDPOINTS.makeTransfer,
      this.transferPayload(originAccount, destinationAccount, debitCard, debitCards.length, testData),
      session,
    );
    const transactionId = readRequiredString(transferBody, 'idTransaction', 'La transferencia entre cuentas propias');
    const ticketBody = await this.post('consulta del ticket', ENDPOINTS.ticket, { transactionId }, session);

    return this.readTransferResult(ticketBody, transactionId);
  }

  private async readAccounts(session: AuthenticatedSession): Promise<readonly unknown[]> {
    const body = await this.post('consulta de cuentas', ENDPOINTS.accounts, {}, session);
    const data = readRequiredObject(body, 'data', 'La consulta de cuentas');
    return readRequiredArray(data, 'accounts', 'La consulta de cuentas');
  }

  private async readDebitCards(session: AuthenticatedSession): Promise<readonly ApiJsonObject[]> {
    const body = await this.post('consulta de tarjetas', ENDPOINTS.debitCards, {}, session);
    const data = readRequiredObject(body, 'data', 'La consulta de tarjetas');
    const products = readRequiredArray(data, 'creditCards', 'La consulta de tarjetas');

    return products.filter(
      (product): product is ApiJsonObject => isJsonObject(product) && product.productType === 'TD',
    );
  }

  private selectAccount(accounts: readonly unknown[], shortLabel: string, role: 'origen' | 'destino'): AccountProduct {
    const account = selectUniqueObject(
      accounts,
      (item) => item.shortLabel === shortLabel,
      `No se encontró la cuenta ${role} configurada.`,
      `La cuenta ${role} configurada está duplicada.`,
    );

    return parseAccount(account, `La cuenta ${role}`);
  }

  private selectDebitCard(debitCards: readonly ApiJsonObject[], lastFour: string): DebitCardProduct {
    const card = selectUniqueObject(
      debitCards,
      (item) => typeof item.label === 'string' && item.label.endsWith(` ${lastFour}`),
      'No se encontró la tarjeta de débito configurada.',
      'La tarjeta de débito configurada está duplicada.',
    );

    return parseDebitCard(card, 'La tarjeta de débito');
  }

  private validateProducts(
    originAccount: AccountProduct,
    destinationAccount: AccountProduct,
    debitCard: DebitCardProduct,
    configuredDestinationCbu: string,
  ): void {
    if (originAccount.idProduct === destinationAccount.idProduct) {
      throw new Error('Las cuentas resueltas para origen y destino deben ser diferentes.');
    }

    if (
      originAccount.currency !== CURRENCY ||
      destinationAccount.currency !== CURRENCY ||
      debitCard.currency !== CURRENCY
    ) {
      throw new Error('Las cuentas y la tarjeta seleccionadas deben operar en ARS.');
    }

    if (destinationAccount.cbu !== configuredDestinationCbu) {
      throw new Error('El CBU configurado no corresponde a la cuenta destino seleccionada.');
    }
  }

  private transferPayload(
    originAccount: AccountProduct,
    destinationAccount: AccountProduct,
    debitCard: DebitCardProduct,
    debitCardCount: number,
    testData: OwnAccountTransferData,
  ): ApiJsonObject {
    return {
      tranferType: 'betweenOwnAccounts',
      transferTypeLabel: 'Entre cuentas propias',
      amount: TRANSFER_AMOUNT,
      currency: CURRENCY,
      originAccountID: originAccount.idProduct,
      targetAccountID: destinationAccount.idProduct,
      concept: 'VAR',
      conceptLabel: 'Varios',
      reference: 'Otros',
      emailMessagge: '',
      originAccountLabel: originAccount.label,
      targetAccountLabel: testData.destinationName,
      targetCuitCuil: testData.destinationCuitCuil,
      targetName: testData.destinationName,
      targetBankName: BERSA_LEGAL_NAME,
      targetAccountTypeLabel: destinationAccount.shortLabel,
      _targetAccountNumberLabel: destinationAccount.number,
      isOwnSynchronisedAccount: true,
      debitCard: debitCard.idProduct,
      debitCardLabel: debitCard.label,
      debitCardCurrency: debitCard.currency,
      hasOnlyOneDebitCard: debitCardCount === 1,
      goBackPath: '/transfers',
      isNewTransferFlow: true,
      isAdheredAccount: true,
      cbuTarget: destinationAccount.cbu,
      targetAccountCode: null,
      targetBrandType: null,
      targetNumberBank: null,
      extraValues: {
        isAdheredAccount: false,
        isOwnAccount: true,
      },
      belongsTo: null,
    };
  }

  private readTransferResult(ticketBody: ApiJsonObject, transactionId: string): OwnAccountTransferResult {
    const data = readRequiredObject(ticketBody, 'data', 'La consulta del ticket');
    const ticket = readRequiredObject(data, '_ticket', 'La consulta del ticket');
    const ticketData = readRequiredObject(ticket, 'data', 'El ticket de transferencia');
    const encodedPdf = data.encodeBase64;

    return {
      hasReceiptPdf: data.fileName === 'Ticket.pdf' && typeof encodedPdf === 'string' && encodedPdf.trim().length > 0,
      isOwnAccountTransfer:
        ticket.idActivity === 'account.transfer.make' && ticketData.tranferType === 'betweenOwnAccounts',
      isTicketFinished: ticket.status === 'FINISHED',
      ticketMatchesTransfer: ticket.idTransaction === transactionId,
    };
  }

  private async post(
    step: string,
    endpoint: string,
    payload: ApiJsonObject,
    session: AuthenticatedSession,
  ): Promise<ApiJsonObject> {
    const ajaxUuid = randomUUID();
    return this.api.post({
      data: {
        ...payload,
        _bcCuil: this.bcCuil,
        ajax_uuid: ajaxUuid,
        xAppVersion: APP_VERSION,
      },
      endpoint,
      expectedCodes: [SUCCESS_CODE],
      headers: {
        Authorization: session.authorizationHeader,
        ajax_uuid: ajaxUuid,
      },
      step,
    });
  }
}
