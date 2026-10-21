import type { AuthenticatedSession } from '../../models/authenticated-session';
import { SmsOperationOtpApi } from '../authentication/sms-operation-otp.api';
import { ReportingApiRequest, type ApiJsonObject } from '../reporting-api-request';
import acceptTemplate from './echeq-templates/echeqOperationAccept.json';
import depositTemplate from './echeq-templates/echeqOperationDeposit.json';
import endorseTemplate from './echeq-templates/echeqOperationEndorse.json';
import keepTemplate from './echeq-templates/echeqOperationKeep.json';
import listTemplate from './echeq-templates/listEcheq.json';
import repudiateTemplate from './echeq-templates/echeqOperationRepudiate.json';
import requestReturnTemplate from './echeq-templates/echeqOperationRequestReturn.json';

const LIST_ENDPOINT = '/api/v1/execute/list.echeck';
// Códigos de éxito del validador legado (Config/ValidarResponse.validateResponses).
const SUCCESS_CODES: readonly string[] = ['COR000I', 'COR013I', 'API690W', 'API691W', 'API692W'];
// El legado recorre hasta 10 páginas buscando el eCheq a operar.
const SEARCH_PAGES = 10;
// Valor que deja `Utils.validarNullParametro` cuando la búsqueda no encuentra un eCheq.
const LEGACY_NOT_FOUND = 'null';

/**
 * Operaciones de eCheq con su endpoint y la plantilla legada de `Requests/Echeq/`. El rescate usa
 * la plantilla de repudio, como `EcheqSteps.generaEcheqRescatado`.
 */
const OPERATIONS = {
  accept: { endpoint: '/api/v1/execute/echeq.operation.accept', template: acceptTemplate, step: 'aceptación' },
  deposit: { endpoint: '/api/v1/execute/echeq.operation.deposit', template: depositTemplate, step: 'depósito' },
  endorse: { endpoint: '/api/v1/execute/echeq.operation.endorse', template: endorseTemplate, step: 'endoso' },
  keep: { endpoint: '/api/v1/execute/echeq.operation.keep', template: keepTemplate, step: 'custodia' },
  repudiate: { endpoint: '/api/v1/execute/echeq.operation.repudiate', template: repudiateTemplate, step: 'repudio' },
  requestReturn: {
    endpoint: '/api/v1/execute/echeq.operation.requestReturnV2',
    template: requestReturnTemplate,
    step: 'solicitud de devolución',
  },
  rescue: { endpoint: '/api/v1/execute/echeq.operation.rescue', template: repudiateTemplate, step: 'rescate' },
} as const;

export type EcheqOperation = keyof typeof OPERATIONS;

function isJsonObject(value: unknown): value is ApiJsonObject {
  return typeof value === 'object' && value !== null && !Array.isArray(value);
}

/** Reemplaza marcadores `_x_` de la plantilla, como el `String.replace` legado sobre el body. */
function fillTemplate(template: unknown, replacements: Readonly<Record<string, string>>): ApiJsonObject {
  const text = Object.entries(replacements).reduce(
    (body, [marker, value]) => body.split(marker).join(JSON.stringify(value).slice(1, -1)),
    JSON.stringify(template),
  );

  return JSON.parse(text) as ApiJsonObject;
}

/** Fecha de pago con el formato del legado: `yyyy-MM-dd` local más `T00:00:00.000-03`. */
export function legacyEcheqPaymentDate(daysFromToday: number, now: Date = new Date()): string {
  const date = new Date(now);
  date.setDate(date.getDate() + daysFromToday);
  const pad = (value: number): string => String(value).padStart(2, '0');
  return `${date.getFullYear()}-${pad(date.getMonth() + 1)}-${pad(date.getDate())}T00:00:00.000-03`;
}

export interface EcheqReference {
  readonly checkId: string;
  readonly checkNumber: string;
  /** `false` cuando ninguna página contiene el eCheq; los campos quedan en `"null"` como el legado. */
  readonly found: boolean;
}

export interface EcheqSearch {
  readonly beneficiaryCuil: string | undefined;
  readonly paymentDate: string;
  readonly statement: string;
}

/** eCheq de individuos (legado EcheqSteps). Cada operación es una mutación por intento con OTP SMS. */
export class EcheqOperationsApi {
  public constructor(
    private readonly api: ReportingApiRequest,
    private readonly smsOperationOtp: SmsOperationOtpApi,
  ) {}

  /** Lista los eCheq del estado y la página indicados (`list.echeck`). */
  public async list(
    session: AuthenticatedSession,
    statement: string,
    page: string,
    operationType: string,
  ): Promise<readonly ApiJsonObject[]> {
    const response = await this.api.post({
      data: fillTemplate(listTemplate, { _statement_: statement, _page_: page, _operationType_: operationType }),
      endpoint: LIST_ENDPOINT,
      expectedCodes: SUCCESS_CODES,
      headers: { Authorization: session.authorizationHeader },
      step: `listado de eCheq ${statement} página ${page}`,
    });
    const paginatedData = isJsonObject(response.data) ? response.data.paginatedData : undefined;
    const paginatedList = isJsonObject(paginatedData) ? paginatedData.paginatedList : undefined;
    const elementList = isJsonObject(paginatedList) ? paginatedList.elementList : undefined;

    if (!Array.isArray(elementList)) {
      throw new Error('El listado de eCheq no devolvió paginatedList.elementList.');
    }

    return elementList.filter(isJsonObject);
  }

  /**
   * Busca, página por página, el primer eCheq del estado con el beneficiario y la fecha de pago
   * indicados (legado generacionDeAceptaciondeEcheq / generacionRepudioDeEcheq).
   */
  public async findEcheq(session: AuthenticatedSession, search: EcheqSearch): Promise<EcheqReference> {
    for (let page = 1; page <= SEARCH_PAGES; page += 1) {
      const elements = await this.list(session, search.statement, String(page), '');
      const match = elements.find((element) => {
        const beneficiary = isJsonObject(element.beneficiary) ? element.beneficiary : {};
        return (
          element.statement === search.statement &&
          beneficiary.identificationNumber === search.beneficiaryCuil &&
          element.paymentDate === search.paymentDate
        );
      });

      if (match && match.checkId !== null && match.checkId !== undefined) {
        return { checkId: String(match.checkId), checkNumber: String(match.checkNumber), found: true };
      }
    }

    return { checkId: LEGACY_NOT_FOUND, checkNumber: LEGACY_NOT_FOUND, found: false };
  }

  /**
   * Ejecuta una operación sobre el eCheq con OTP por SMS y devuelve el `message` de la respuesta.
   * `idProduct` aplica a custodia y depósito; `endorseDocumentNumber`, al endoso.
   */
  public async operate(
    session: AuthenticatedSession,
    username: string,
    operation: EcheqOperation,
    echeq: EcheqReference,
    options: { readonly endorseDocumentNumber?: string; readonly idProduct?: string } = {},
  ): Promise<string> {
    const definition = OPERATIONS[operation];
    const otp = await this.smsOperationOtp.resolve(session, username);
    const replacements: Record<string, string> = {
      _checkId_: echeq.checkId,
      _nroEcheq_: echeq.checkNumber,
      _code_: otp,
    };

    if (options.idProduct !== undefined) {
      replacements._idProduct_ = options.idProduct;
    }

    if (options.endorseDocumentNumber !== undefined) {
      replacements._documentNumber_ = options.endorseDocumentNumber;
    }

    const response = await this.api.post({
      data: fillTemplate(definition.template, replacements),
      endpoint: definition.endpoint,
      expectedCodes: SUCCESS_CODES,
      headers: { Authorization: session.authorizationHeader },
      step: `${definition.step} del eCheq`,
    });

    return String(response.message);
  }
}
