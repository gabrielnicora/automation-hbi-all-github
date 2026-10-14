import type { AuthenticatedSession } from '../../models/authenticated-session';
import type { CreditCardOtherAmountPaymentData } from '../../test-data/credit-card-other-amount-payment-data';
import { ReportingApiRequest, type ApiJsonObject } from '../reporting-api-request';

const SUCCESS_CODE = 'COR000I';
const PAYMENT_AMOUNT = 1;
const WIDGET_REQUEST = {
  ajaxUuid: '274dd2b3-9fc7-4f82-b954-34a5d784ec9a',
  appVersion: '1.95.0',
} as const;
const PAYMENT_REQUEST = {
  ajaxUuid: '54e704fe-84b5-4f71-b37e-762261f9e21a',
  appVersion: '1.81.1',
} as const;
const PAYMENT_TICKET_DATA =
  '{"dueDate":"2025-05-08T00:00:00.000-03","balanceArs":9049.47,"balanceUsd":0,"minimumPayment":9049.47}';

const ENDPOINTS = {
  accounts: '/api/v1/execute/widgets.accounts',
  cards: '/api/v1/execute/widgets.creditCards',
  makePayment: '/api/v1/execute/creditCard.make.payment',
} as const;

function isJsonObject(value: unknown): value is ApiJsonObject {
  return typeof value === 'object' && value !== null && !Array.isArray(value);
}

function requiredDataArray(body: ApiJsonObject, propertyName: string, step: string): readonly unknown[] {
  if (!isJsonObject(body.data) || !Array.isArray(body.data[propertyName])) {
    throw new Error(`La ${step} no devolvió un arreglo ${propertyName} válido.`);
  }

  return body.data[propertyName];
}

function uniqueProduct(
  products: readonly unknown[],
  matches: (product: ApiJsonObject) => boolean,
  missingMessage: string,
  duplicateMessage: string,
): ApiJsonObject {
  const matchingProducts = products.filter(
    (product): product is ApiJsonObject => isJsonObject(product) && matches(product),
  );

  if (matchingProducts.length === 0) {
    throw new Error(missingMessage);
  }

  if (matchingProducts.length > 1) {
    throw new Error(duplicateMessage);
  }

  return matchingProducts[0];
}

function requiredProductId(product: ApiJsonObject, description: string): string {
  if (typeof product.idProduct !== 'string' || product.idProduct.trim().length === 0) {
    throw new Error(`El producto ${description} no contiene un idProduct válido.`);
  }

  return product.idProduct.trim();
}

export interface CreditCardOtherAmountPaymentResult {
  readonly wasAccepted: boolean;
}

export class CreditCardOtherAmountPaymentApi {
  public constructor(private readonly api: ReportingApiRequest) {}

  public async pay(
    session: AuthenticatedSession,
    testData: CreditCardOtherAmountPaymentData,
  ): Promise<CreditCardOtherAmountPaymentResult> {
    const accountsBody = await this.post(
      'consulta de cuentas para pago de tarjeta',
      ENDPOINTS.accounts,
      {},
      session,
      WIDGET_REQUEST,
    );
    const accounts = requiredDataArray(accountsBody, 'accounts', 'consulta de cuentas');
    const account = uniqueProduct(
      accounts,
      (product) => product.shortLabel === testData.originAccount,
      'No se encontró la cuenta origen configurada para el pago de tarjeta.',
      'La cuenta origen configurada para el pago de tarjeta está duplicada.',
    );

    const cardsBody = await this.post('consulta de tarjetas para pago', ENDPOINTS.cards, {}, session, WIDGET_REQUEST);
    const cards = requiredDataArray(cardsBody, 'creditCards', 'consulta de tarjetas');
    const debitCard = uniqueProduct(
      cards,
      (product) =>
        product.productType === 'TD' &&
        typeof product.label === 'string' &&
        product.label.endsWith(` ${testData.debitCardLastFour}`),
      'No se encontró la tarjeta de débito configurada para el pago de tarjeta.',
      'La tarjeta de débito configurada para el pago de tarjeta está duplicada.',
    );
    const creditCard = uniqueProduct(
      cards,
      (product) =>
        product.productType === 'TC' &&
        typeof product.label === 'string' &&
        /visa/i.test(product.label) &&
        product.label.endsWith(` ${testData.creditCardLastFour}`),
      'No se encontró la tarjeta Visa configurada para el pago de tarjeta.',
      'La tarjeta Visa configurada para el pago de tarjeta está duplicada.',
    );

    const accountId = requiredProductId(account, 'la cuenta origen');
    const debitCardId = requiredProductId(debitCard, 'la tarjeta de débito');
    const creditCardId = requiredProductId(creditCard, 'la tarjeta Visa');

    await this.post(
      'pago de tarjeta Visa por otro importe',
      ENDPOINTS.makePayment,
      {
        amount: PAYMENT_AMOUNT,
        currency: 'ARS',
        accountId,
        debitCardId,
        creditCardId,
        ticketData: PAYMENT_TICKET_DATA,
      },
      session,
      PAYMENT_REQUEST,
    );

    return { wasAccepted: true };
  }

  private async post(
    step: string,
    endpoint: string,
    payload: ApiJsonObject,
    session: AuthenticatedSession,
    requestMetadata: { readonly ajaxUuid: string; readonly appVersion: string },
  ): Promise<ApiJsonObject> {
    return this.api.post({
      data: { ...payload, ajax_uuid: requestMetadata.ajaxUuid, xAppVersion: requestMetadata.appVersion },
      endpoint,
      expectedCodes: [SUCCESS_CODE],
      headers: { Authorization: session.authorizationHeader },
      step,
    });
  }
}
