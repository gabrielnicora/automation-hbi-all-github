import type { AuthenticatedSession } from '../../models/authenticated-session';
import { ReportingApiRequest, type ApiJsonObject } from '../reporting-api-request';

const PAY_ENDPOINT = '/api/v1/execute/creditCard.make.payment';
const READ_ENDPOINT = '/api/v1/execute/creditCard.read';
// Códigos de éxito del validador legado (Config/ValidarResponse.validateResponses).
const SUCCESS_CODES: readonly string[] = ['COR000I', 'COR013I', 'API690W', 'API691W', 'API692W'];
// Valores fijos de Requests/Tarjetas/creditCardMakePayment.json y creditCardRead.json.
const PAYMENT_METADATA = { ajax_uuid: '54e704fe-84b5-4f71-b37e-762261f9e21a', xAppVersion: '1.81.1' } as const;
const READ_METADATA = { ajax_uuid: 'a3740b37-8a51-450d-8f76-c7496f6c498f', xAppVersion: '1.81.1' } as const;
const PAYMENT_TICKET_DATA =
  '{"dueDate":"2025-05-08T00:00:00.000-03","balanceArs":9049.47,"balanceUsd":0,"minimumPayment":9049.47}';

function isJsonObject(value: unknown): value is ApiJsonObject {
  return typeof value === 'object' && value !== null && !Array.isArray(value);
}

export interface CreditCardPaymentProducts {
  readonly accountId: string;
  readonly creditCardId: string;
  readonly debitCardId: string;
}

/** Pagos de tarjeta de crédito (legado TarjetaSteps.realizarPagoOtroImporte / realizarPagoMinimo). */
export class CreditCardPaymentsApi {
  public constructor(private readonly api: ReportingApiRequest) {}

  /** Pago mínimo en pesos informado por `creditCard.read` (`data.info.minimumPaymentArs`). */
  public async readMinimumPaymentArs(session: AuthenticatedSession, creditCardId: string): Promise<number> {
    const response = await this.post(
      session,
      READ_ENDPOINT,
      { idProduct: creditCardId, ...READ_METADATA },
      'consulta del resumen de la tarjeta',
    );
    const info = isJsonObject(response.data) ? response.data.info : undefined;
    const minimum = isJsonObject(info) ? info.minimumPaymentArs : undefined;

    if (typeof minimum !== 'number') {
      throw new Error('La tarjeta no informa data.info.minimumPaymentArs numérico.');
    }

    return minimum;
  }

  /** Paga la tarjeta por el importe indicado. Ejecuta la mutación una sola vez por intento. */
  public async pay(session: AuthenticatedSession, products: CreditCardPaymentProducts, amount: number): Promise<void> {
    await this.post(
      session,
      PAY_ENDPOINT,
      {
        debitCardId: products.debitCardId,
        creditCardId: products.creditCardId,
        accountId: products.accountId,
        amount,
        currency: 'ARS',
        ticketData: PAYMENT_TICKET_DATA,
        ...PAYMENT_METADATA,
      },
      'pago de tarjeta de crédito',
    );
  }

  private async post(
    session: AuthenticatedSession,
    endpoint: string,
    data: ApiJsonObject,
    step: string,
  ): Promise<ApiJsonObject> {
    return this.api.post({
      data,
      endpoint,
      expectedCodes: SUCCESS_CODES,
      headers: { Authorization: session.authorizationHeader },
      step,
    });
  }
}
