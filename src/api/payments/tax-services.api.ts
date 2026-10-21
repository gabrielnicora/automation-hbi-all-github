import type { AuthenticatedSession } from '../../models/authenticated-session';
import type { DebitCard } from '../accounts/product-widgets.api';
import type { MercadoLibrePayment, MercadoPagoService } from '../../test-data/payment-services-data';
import { SmsOperationOtpApi } from '../authentication/sms-operation-otp.api';
import { ReportingApiRequest, type ApiJsonObject } from '../reporting-api-request';

const ENDPOINTS = {
  addService: '/api/v1/execute/payments.addTaxService',
  addWithoutBase: '/api/v1/execute/firstPayment.addEntityByCpe',
  pay: '/api/v1/execute/firstPayment.request',
  queryAdhesions: '/api/v1/execute/payments.query.adhesions',
  unsubscribe: '/api/v1/execute/payments.unsubscribe.adhered.service',
} as const;
// Códigos de éxito del validador legado (Config/ValidarResponse.validateResponses).
const SUCCESS_CODES: readonly string[] = ['COR000I', 'COR013I', 'API690W', 'API691W', 'API692W'];
// Metadatos fijos de las plantillas legadas de Requests/Pagos/ (firstPaymentRequest.json no los envía).
const METADATA = {
  addService: { ajax_uuid: 'bce385c5-54a6-43e3-8e91-a40960d2800e', xAppVersion: '1.75.0' },
  addWithoutBase: { ajax_uuid: 'fe0e3dc9-1b4c-45a1-91b5-8c30adc919c0', xAppVersion: '1.79.0' },
  queryAdhesions: { ajax_uuid: '2917d1c6-f306-4b8e-bacc-8ab97e901486', xAppVersion: '1.74.0' },
  unsubscribe: { ajax_uuid: 'ac0144b9-d7a6-4fb1-a93c-97b04e5b5658', xAppVersion: '1.74.0' },
} as const;

function isJsonObject(value: unknown): value is ApiJsonObject {
  return typeof value === 'object' && value !== null && !Array.isArray(value);
}

/** `quitarCochetesJsonPath` del legado aplicado al texto de un valor leído. */
function legacyText(value: unknown): string {
  return String(value).replace(/\[/g, '').replace(/]/g, '').replace(/"/g, '');
}

export interface WithoutBasePaymentData {
  readonly adhesionCode: string;
  readonly conceptCode: string;
}

/** Pago de impuestos y servicios con tarjeta de débito (legado PagosSteps). Una mutación por intento. */
export class TaxServicesApi {
  public constructor(
    private readonly api: ReportingApiRequest,
    private readonly smsOperationOtp: SmsOperationOtpApi,
  ) {}

  /** Agenda el servicio para la tarjeta de débito con OTP por SMS (`payments.addTaxService`). */
  public async adhere(
    session: AuthenticatedSession,
    username: string,
    debitCard: DebitCard,
    service: MercadoPagoService,
  ): Promise<void> {
    const otp = await this.smsOperationOtp.resolve(session, username);

    await this.post(
      session,
      ENDPOINTS.addService,
      {
        debitCard: debitCard.idProduct,
        entityCode: service.entityCode,
        paymentCode: service.paymentCode,
        belongsTo: service.belongsTo,
        handleBaseDebts: false,
        debitCardDesc: debitCard.label,
        categoryDesc: service.categoryDesc,
        entityDesc: service.entityDesc,
        userLP: service.paymentCode,
        _otp: `OTP_TOKEN@${otp}`,
        ...METADATA.addService,
      },
      'adhesión del servicio',
    );
  }

  /** Código de la adhesión que coincide con ente, usuario y referencia del servicio, si existe. */
  public async findAdhesionCode(
    session: AuthenticatedSession,
    debitCardId: string,
    service: MercadoPagoService,
  ): Promise<string | undefined> {
    const response = await this.post(
      session,
      ENDPOINTS.queryAdhesions,
      { idProduct: debitCardId, ...METADATA.queryAdhesions },
      'consulta de servicios adheridos',
    );
    const adhesions =
      isJsonObject(response.data) && Array.isArray(response.data.adhesions) ? response.data.adhesions : [];
    const match = adhesions.find(
      (adhesion): adhesion is ApiJsonObject =>
        isJsonObject(adhesion) &&
        String(adhesion.enteCode ?? '') === service.entityCode &&
        String(adhesion.userLp ?? '') === service.paymentCode &&
        String(adhesion.belongsTo ?? '') === service.belongsTo &&
        String(adhesion.adhesionCode ?? '').trim().length > 0,
    );

    return match ? String(match.adhesionCode) : undefined;
  }

  public async unsubscribe(
    session: AuthenticatedSession,
    debitCardId: string,
    adhesionCode: string,
    service: MercadoPagoService,
  ): Promise<void> {
    await this.post(
      session,
      ENDPOINTS.unsubscribe,
      {
        idProduct: debitCardId,
        adhesionCode,
        enteDescription: service.entityDesc,
        belongsTo: service.belongsTo,
        userLp: service.paymentCode,
        ...METADATA.unsubscribe,
      },
      'baja del servicio adherido',
    );
  }

  /** Prepara un pago sin base con OTP por SMS y devuelve el concepto y la adhesión a pagar. */
  public async prepareWithoutBasePayment(
    session: AuthenticatedSession,
    username: string,
    debitCardId: string,
    payment: MercadoLibrePayment,
  ): Promise<WithoutBasePaymentData> {
    const otp = await this.smsOperationOtp.resolve(session, username);
    const response = await this.post(
      session,
      ENDPOINTS.addWithoutBase,
      {
        idProduct: debitCardId,
        enteCode: payment.enteCode,
        paymentCode: payment.paymentCode,
        belongsTo: '',
        _otp: `OTP_TOKEN@${otp}`,
        ...METADATA.addWithoutBase,
      },
      'alta del ente para pago sin base',
    );
    const data = isJsonObject(response.data) ? response.data : {};
    const concepts = Array.isArray(data.enteConcepts) ? data.enteConcepts.filter(isJsonObject) : [];
    const conceptCodes = concepts
      .filter((concept) => concept.description === payment.conceptDescription)
      .map((concept) => legacyText(concept.conceptCode));
    const adhesion = isJsonObject(data.resultWithoutBase) ? data.resultWithoutBase.adhesion : undefined;
    const adhesionCode = isJsonObject(adhesion) ? adhesion.adhesionCode : undefined;

    if (conceptCodes.length === 0) {
      throw new Error(`El ente no informa el concepto "${payment.conceptDescription}".`);
    }

    if (adhesionCode === undefined || adhesionCode === null) {
      throw new Error('El ente no informa resultWithoutBase.adhesion.adhesionCode.');
    }

    // El legado une varios conceptos coincidentes por coma al quitar los corchetes del resultado.
    return { adhesionCode: legacyText(adhesionCode), conceptCode: conceptCodes.join(',') };
  }

  public async payWithoutBase(
    session: AuthenticatedSession,
    accountId: string,
    debitCardId: string,
    prepared: WithoutBasePaymentData,
    payment: MercadoLibrePayment,
  ): Promise<void> {
    await this.post(
      session,
      ENDPOINTS.pay,
      {
        currency: 'ARS',
        idProductTD: debitCardId,
        idProductDA: accountId,
        amount: payment.amount,
        enteCode: payment.enteCode,
        enteDescription: payment.enteDescription,
        reference: payment.reference,
        debtID: '',
        conceptDescription: '',
        conceptCode: prepared.conceptCode,
        ususarioLP: payment.paymentCode,
        subscribe: false,
        adhesionCode: prepared.adhesionCode,
        manageBaseDebt: false,
      },
      'pago del servicio sin base',
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
