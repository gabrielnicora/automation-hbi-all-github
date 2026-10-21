import type { AuthenticatedSession } from '../../models/authenticated-session';
import { SmsOperationOtpApi } from '../authentication/sms-operation-otp.api';
import { fillLegacyTemplate } from '../legacy-template';
import { ReportingApiRequest, type ApiJsonObject } from '../reporting-api-request';
import credinGenerateTemplate from './transfer-templates/credinGenerate.json';
import credinGenerateSearchTemplate from './transfer-templates/credinGenerateSearch.json';
import credinSearchTemplate from './transfer-templates/credinSearch.json';

const ENDPOINTS = {
  generate: '/api/v1/execute/credin.generate',
  party: '/api/v1/execute/credin.generate.search',
  search: '/api/v1/execute/credin.search',
} as const;
// Códigos de éxito del validador legado (Config/ValidarResponse.validateResponses).
const SUCCESS_CODES: readonly string[] = ['COR000I', 'COR013I', 'API690W', 'API691W', 'API692W'];

function isJsonObject(value: unknown): value is ApiJsonObject {
  return typeof value === 'object' && value !== null && !Array.isArray(value);
}

/** Lectura legada `JsonPath.read(...)` asignada a `String`: un valor ausente hace fallar el reemplazo. */
function requiredText(value: unknown, field: string): string {
  if (value === null || value === undefined) {
    throw new Error(`La búsqueda CREDIN no informa ${field}.`);
  }

  return String(value);
}

/** Datos de una cuenta para CREDIN (`data.customerSearched` de `credin.generate.search`). */
export interface CredinParty {
  readonly aliasCbu: string;
  readonly brandType: string;
  readonly cuil: string;
  readonly firstName: string;
  readonly number: string;
  readonly numberBank: string;
}

export interface CredinGeneration {
  readonly credit: CredinParty;
  /** CBU destino (`cbu` del caso). */
  readonly creditCbu: string;
  readonly currency: string;
  readonly debit: CredinParty;
  /** CBU de la cuenta origen (`obtenerCbuCuenta(cuentaOrigen)`). */
  readonly debitCbu: string;
  /** idProduct de la tarjeta de débito resuelta por el legado. */
  readonly debitCardId: string;
}

/** CREDIN (legado TransferenciasSteps.obtenerDatosDebitos/Credito, generarCredin, consultarCredin). */
export class CredinApi {
  public constructor(
    private readonly api: ReportingApiRequest,
    private readonly smsOperationOtp: SmsOperationOtpApi,
  ) {}

  /** `owner` es `"true"` para la cuenta débito y `""` para la de crédito, como el legado. */
  public async findParty(session: AuthenticatedSession, cbu: string, owner: 'true' | ''): Promise<CredinParty> {
    const response = await this.post(
      session,
      ENDPOINTS.party,
      fillLegacyTemplate(credinGenerateSearchTemplate, [
        ['_cbu_', cbu],
        ['_owner_', owner],
      ]),
      owner === 'true' ? 'búsqueda de la cuenta débito CREDIN' : 'búsqueda de la cuenta crédito CREDIN',
    );
    const customer = isJsonObject(response.data) ? response.data.customerSearched : undefined;
    const headline = isJsonObject(customer) && isJsonObject(customer.headline) ? customer.headline : {};
    const account = isJsonObject(customer) && isJsonObject(customer.account) ? customer.account : {};

    return {
      aliasCbu: requiredText(account.aliasCbu, 'account.aliasCbu'),
      brandType: requiredText(account.brandType, 'account.brandType'),
      cuil: requiredText(headline.cuil, 'headline.cuil'),
      firstName: requiredText(headline.firstName, 'headline.firstName'),
      number: requiredText(account.number, 'account.number'),
      numberBank: requiredText(account.numberBank, 'account.numberBank'),
    };
  }

  /** Genera el CREDIN con OTP por SMS. Ejecuta la mutación una sola vez por intento. */
  public async generate(session: AuthenticatedSession, username: string, credin: CredinGeneration): Promise<void> {
    const otp = await this.smsOperationOtp.resolve(session, username);

    await this.post(
      session,
      ENDPOINTS.generate,
      fillLegacyTemplate(credinGenerateTemplate, [
        ['_debitName_', credin.debit.firstName],
        ['_debitCuit_', credin.debit.cuil],
        ['_debitNumber_', credin.debit.number],
        ['_debitAlias_', credin.debit.aliasCbu],
        ['_debitBrandType_', credin.debit.brandType],
        ['_debitNumberBank_', credin.debit.numberBank],
        ['_debitIdProduct_', credin.debitCardId],
        ['_creditOwner_', credin.credit.firstName],
        ['_creditCuit_', credin.credit.cuil],
        ['_creidtNumber_', credin.credit.number],
        ['_cbuDestino_', credin.creditCbu],
        ['_creditAlias_', credin.credit.aliasCbu],
        ['_creditBrandtype_', credin.credit.brandType],
        ['_creditNumberBank_', credin.credit.numberBank],
        ['_cbuOrigen_', credin.debitCbu],
        ['_code_', otp],
        ['_currency_', credin.currency],
      ]),
      'generación del CREDIN',
    );
  }

  /** Cantidad de CREDIN del día buscando como `Originante` o `Destinatario`. */
  public async countToday(
    session: AuthenticatedSession,
    searchAs: 'Destinatario' | 'Originante',
    originatorCuit: string,
    recipientCuit: string,
  ): Promise<number> {
    const now = new Date();
    const pad = (value: number, length = 2): string => String(value).padStart(length, '0');
    // Legado: patrón YYYY-MM-dd'T'HH:mm:ss.SSS'Z' en hora local (ver lote-l6-api.md sobre YYYY).
    const today =
      `${now.getFullYear()}-${pad(now.getMonth() + 1)}-${pad(now.getDate())}` +
      `T${pad(now.getHours())}:${pad(now.getMinutes())}:${pad(now.getSeconds())}.${pad(now.getMilliseconds(), 3)}Z`;
    const response = await this.post(
      session,
      ENDPOINTS.search,
      fillLegacyTemplate(credinSearchTemplate, [
        ['_startDate_', today],
        ['_endDate_', today],
        ['_cuitOriginator_', originatorCuit],
        ['_cuitRecipient_', recipientCuit],
        ['_searchAs_', searchAs],
      ]),
      `consulta de CREDIN como ${searchAs}`,
    );
    const paginatedData = isJsonObject(response.data) ? response.data.paginatedData : undefined;
    const paginatedList = isJsonObject(paginatedData) ? paginatedData.paginatedList : undefined;
    const elementList = isJsonObject(paginatedList) ? paginatedList.elementList : undefined;

    if (!Array.isArray(elementList)) {
      throw new Error('La consulta de CREDIN no devolvió paginatedList.elementList.');
    }

    return elementList.length;
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
