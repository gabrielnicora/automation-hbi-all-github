import type { AuthenticatedSession } from '../../models/authenticated-session';
import { SmsOperationOtpApi } from '../authentication/sms-operation-otp.api';
import { ReportingApiRequest, type ApiJsonObject } from '../reporting-api-request';

const ENDPOINTS = {
  confirm: '/api/v1/execute/creditCard.application.confirm',
  create: '/api/v1/execute/creditCard.application.create',
  offers: '/api/v1/execute/creditCard.list.product.offer',
  update: '/api/v1/execute/creditCard.application.update',
} as const;
// Códigos de éxito del validador legado (Config/ValidarResponse.validateResponses).
const SUCCESS_CODES: readonly string[] = ['COR000I', 'COR013I', 'API690W', 'API691W', 'API692W'];
// Metadatos fijos de Requests/Tarjetas/creditCardListProductOffer.json y CreditCardAplication*.json.
const OFFERS_METADATA = { ajax_uuid: 'b800d4b5-8333-4aeb-9c87-ee4e705f05e1', xAppVersion: '1.88.0' } as const;
const APPLICATION_METADATA = { ajax_uuid: 'b4f15bef-1e49-48ab-aa64-f31861052393', xAppVersion: '1.88.0' } as const;
// Domicilio de entrega fijo de las plantillas legadas.
const DELIVERY_ADDRESS = {
  apartment: '',
  floor: 'PB',
  locality: { description: 'CAPITAL FEDERAL', value: '0' },
  number: '550',
  postalCode: { description: '00001004', value: '00001004' },
  province: { description: 'CAPITAL FEDERAL', value: 'CAPITAL FEDERAL' },
  street: 'SAN MARTIN',
  addressLine: 'SAN MARTIN 550, piso PB, CAPITAL FEDERAL (00001004), CAPITAL FEDERAL',
} as const;

function isJsonObject(value: unknown): value is ApiJsonObject {
  return typeof value === 'object' && value !== null && !Array.isArray(value);
}

/** `JsonPath.read(...).toString()` + `quitarCochetesJsonPath` del legado; un valor nulo hace fallar. */
function legacyText(value: unknown, field: string): string {
  if (value === null || value === undefined) {
    throw new Error(`La oferta de tarjeta no informa ${field}.`);
  }

  return String(value).replace(/\[/g, '').replace(/]/g, '').replace(/"/g, '');
}

/** `new BigDecimal(texto)` del legado: el valor viaja como número JSON. */
function legacyDecimal(value: unknown, field: string): number {
  const text = legacyText(value, field);
  const numericValue = Number(text);

  if (text.trim() === '' || Number.isNaN(numericValue)) {
    throw new Error(`La oferta de tarjeta informa ${field} no numérico.`);
  }

  return numericValue;
}

export interface CreditCardOffer {
  readonly accountNumber: string;
  readonly bonusAnualChargePeriod: number;
  readonly buyLimit: number;
  readonly cardBrand: string;
  readonly cardType: string;
  readonly cuil: string;
  readonly debitConcept: string;
  readonly dni: string;
  readonly dueBuyLimit: number;
  readonly duePaymentAnualRenewal: number;
  readonly idOrder: string;
  readonly offerDescription: string;
  readonly shortLabel: string;
  readonly startDate: string;
  readonly validDateFrom: string;
  readonly validDateTo: string;
}

export interface CreditCardOffers {
  /** Primera oferta, como la usa el legado; `undefined` si no hay ofertas. */
  readonly firstOffer: CreditCardOffer | undefined;
  readonly offersCount: number;
}

export interface PersonDeclarations {
  readonly exteriorResidence: boolean;
  readonly pep: boolean;
  readonly soUIF: boolean;
}

function readOffer(offer: ApiJsonObject): CreditCardOffer {
  const dynamic = isJsonObject(offer.dynamicOfferField) ? offer.dynamicOfferField : {};

  return {
    accountNumber: legacyText(offer.accountNumber, 'accountNumber'),
    bonusAnualChargePeriod: legacyDecimal(dynamic.bonusAnualChargePeriod, 'bonusAnualChargePeriod'),
    buyLimit: legacyDecimal(dynamic.buyLimit, 'buyLimit'),
    cardBrand: legacyText(offer.idSubProduct1, 'idSubProduct1'),
    cardType: legacyText(offer.idSubProduct2, 'idSubProduct2'),
    cuil: legacyText(offer.cuil, 'cuil'),
    debitConcept: legacyText(offer.debitConcept, 'debitConcept'),
    dni: legacyText(offer.dni, 'dni'),
    dueBuyLimit: legacyDecimal(dynamic.dueBuyLimit, 'dueBuyLimit'),
    duePaymentAnualRenewal: legacyDecimal(dynamic.duePaymentAnualRenewal, 'duePaymentAnualRenewal'),
    idOrder: legacyText(offer.id, 'id'),
    offerDescription: legacyText(offer.offerDescription, 'offerDescription'),
    shortLabel: legacyText(offer.shortLabel, 'shortLabel'),
    startDate: legacyText(offer.startDate, 'startDate'),
    validDateFrom: legacyText(offer.validDateFrom, 'validDateFrom'),
    validDateTo: legacyText(offer.validDateTo, 'validDateTo'),
  };
}

/** Cuerpo común de create/update/confirm de las plantillas legadas. */
function applicationBody(
  offer: CreditCardOffer,
  debitIdProduct: string,
  paymentType: 'FULL' | 'MINIMUM',
  declarations: PersonDeclarations,
): ApiJsonObject {
  return {
    cardBrand: offer.cardBrand,
    cardType: offer.cardType,
    paymentType,
    deliveryAddress: DELIVERY_ADDRESS,
    additionals: [],
    offer: {
      id: offer.idOrder,
      channel: '',
      campaignCode: '0',
      campaignDescription: 'OFERTA_MOTOR_COMERCIAL',
      transactionCode: '',
      visualMaxQuantity: 999999,
      daysBetweenDisplay: 0,
      order: 1,
      cuil: offer.cuil,
      dni: offer.dni,
      idProduct: 'TC',
      idSubProduct1: offer.cardBrand,
      idSubProduct2: offer.cardType,
      dynamicOfferField: {
        buyLimit: offer.buyLimit,
        dueBuyLimit: offer.dueBuyLimit,
        // El legado nunca asigna bonusPercentage: viaja como null.
        bonusPercentage: null,
        bonusAnualChargePeriod: offer.bonusAnualChargePeriod,
        duePaymentAnualRenewal: offer.duePaymentAnualRenewal,
        cuentaPBF: '',
        hasVerifyCalification: true,
        verifyCalification: 'false',
      },
      offerDescription: offer.offerDescription,
      debitConcept: offer.debitConcept,
      // El legado reemplaza _debitCurrency_ con debitConcept.
      debitCurrency: offer.debitConcept,
      branchOffice: '0',
      accountNumber: offer.accountNumber,
      verifierDigit: 0,
      creditCardBrand: '',
      creditCardNumber: '',
      debitCardBrand: '',
      debitCardNumber: '',
      startDate: offer.startDate,
      validDateFrom: offer.validDateFrom,
      validDateTo: offer.validDateTo,
      segmentClient: '',
      shortLabel: offer.shortLabel,
      calificationId: null,
      offerId: null,
      idSolicitud: null,
      originSystemId: null,
      banco: null,
      idProductTcAcc: null,
      productTcId: null,
      ctaDebSis: null,
      ctaDebTipo: null,
      randomImageIndex: null,
      coinNumber: '80',
      offerWithRequestData: false,
      requestCode: 'bsf.visa.vsign',
      debitIdProduct,
      cardShortLabel: offer.shortLabel,
    },
    sendEmailResume: true,
    pep: declarations.pep,
    exteriorResidence: declarations.exteriorResidence,
    soUIF: declarations.soUIF,
  };
}

const NO_DECLARATIONS: PersonDeclarations = { exteriorResidence: false, pep: false, soUIF: false };

/** Solicitud de nueva tarjeta de crédito (legado TarjetaSteps). Una mutación por intento. */
export class CreditCardApplicationApi {
  public constructor(
    private readonly api: ReportingApiRequest,
    private readonly smsOperationOtp: SmsOperationOtpApi,
  ) {}

  public async readOffers(session: AuthenticatedSession): Promise<CreditCardOffers> {
    const response = await this.post(
      session,
      ENDPOINTS.offers,
      { isCreditCardRequest: true, ...OFFERS_METADATA },
      'consulta de ofertas de tarjeta',
    );
    const productOffer = isJsonObject(response.data) ? response.data.productOffer : undefined;
    const offers = isJsonObject(productOffer) ? productOffer.offers : undefined;

    if (!Array.isArray(offers)) {
      throw new Error('La consulta de ofertas no devolvió data.productOffer.offers.');
    }

    return {
      firstOffer: isJsonObject(offers[0]) ? readOffer(offers[0]) : undefined,
      offersCount: offers.length,
    };
  }

  /** Crea la solicitud y devuelve `data.__applicationId`. */
  public async create(session: AuthenticatedSession, offer: CreditCardOffer, debitIdProduct: string): Promise<string> {
    const response = await this.post(
      session,
      ENDPOINTS.create,
      { ...applicationBody(offer, debitIdProduct, 'MINIMUM', NO_DECLARATIONS), ...APPLICATION_METADATA },
      'alta de la solicitud de tarjeta',
    );
    const applicationId = isJsonObject(response.data) ? response.data.__applicationId : undefined;

    if (typeof applicationId !== 'string' || applicationId.length === 0) {
      throw new Error('El alta de la solicitud no devolvió data.__applicationId.');
    }

    return legacyText(applicationId, '__applicationId');
  }

  /** Actualiza la solicitud con las declaraciones de la persona y devuelve el `message` de la respuesta. */
  public async updateDeclarations(
    session: AuthenticatedSession,
    offer: CreditCardOffer,
    debitIdProduct: string,
    declarations: PersonDeclarations,
  ): Promise<string> {
    const response = await this.post(
      session,
      ENDPOINTS.update,
      { ...applicationBody(offer, debitIdProduct, 'FULL', declarations), ...APPLICATION_METADATA },
      'actualización de declaraciones de la solicitud',
    );

    return String(response.message);
  }

  /** Confirma la solicitud con OTP por SMS. */
  public async confirm(
    session: AuthenticatedSession,
    username: string,
    offer: CreditCardOffer,
    debitIdProduct: string,
    applicationId: string,
  ): Promise<void> {
    const otp = await this.smsOperationOtp.resolve(session, username);

    await this.post(
      session,
      ENDPOINTS.confirm,
      {
        ...applicationBody(offer, debitIdProduct, 'MINIMUM', NO_DECLARATIONS),
        _otp: `OTP_TOKEN@${otp}`,
        __applicationId: applicationId,
        ...APPLICATION_METADATA,
      },
      'confirmación de la solicitud de tarjeta',
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
