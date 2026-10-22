import type { AuthenticatedSession } from '../../models/authenticated-session';
import { SmsOperationOtpApi } from '../authentication/sms-operation-otp.api';
import { fillLegacyTemplate, legacyNullable, legacyStripBrackets, type LegacyReplacement } from '../legacy-template';
import { ReportingApiRequest, type ApiJsonObject } from '../reporting-api-request';
import adhesionTemplate from './transfer-templates/adhesionAccountNew.json';
import deleteTemplate from './transfer-templates/deleteAdheredAccount.json';
import makeWithCapsCbuTemplate from './transfer-templates/transfersAccountMakeWithCapsCbu.json';
import makeWithCapsCbuWithoutDescriptionTemplate from './transfer-templates/transfersAccountMakeWithCapsCbuSinDescripcion.json';
import makeWithCapsTemplate from './transfer-templates/transfersAccountMakeWithCaps.json';
import scheduleMakeTemplate from './transfer-templates/transfersAccountScheduleMake.json';
import scheduleWithCapsCbuTemplate from './transfer-templates/transfersAccountScheduleWithCapsCbu.json';
import scheduleWithCapsTemplate from './transfer-templates/transfersAccountScheduleWithCaps.json';
import searchTemplate from './transfer-templates/accountTransferSearch.json';
import ticketTemplate from './transfer-templates/ticket.json';

const ENDPOINTS = {
  adhere: '/api/v1/execute/adhesion.account.new',
  delete: '/api/v1/execute/delete.adheredAccount',
  makeToAdhered: '/api/v1/execute/transfers.account.makeWithCaps',
  makeWithCapsCbu: '/api/v1/execute/account.transfer.makeWithCapsCbu',
  makeWithoutDescription: '/api/v1/execute/transfers.account.makeWithCapsCbu',
  scheduleBetweenOwn: '/api/v1/execute/transfers.account.scheduleMake',
  scheduleToAdhered: '/api/v1/execute/transfers.account.scheduleMakeWithCaps',
  scheduleWithCapsCbu: '/api/v1/execute/transfers.account.scheduleWithCapsCbu',
  search: '/api/v1/execute/account.transfer.search',
  ticket: '/api/v1/execute/ticket.get',
} as const;
// Códigos de éxito del validador legado (Config/ValidarResponse.validateResponses).
const SUCCESS_CODES: readonly string[] = ['COR000I', 'COR013I', 'API690W', 'API691W', 'API692W'];

function isJsonObject(value: unknown): value is ApiJsonObject {
  return typeof value === 'object' && value !== null && !Array.isArray(value);
}

function pad(value: number, length = 2): string {
  return String(value).padStart(length, '0');
}

/** Fecha programada del legado: ahora + 1 día, hora local con el literal `Z`. */
function legacyTomorrow(now: Date = new Date()): string {
  const date = new Date(now);
  date.setDate(date.getDate() + 1);
  return (
    `${date.getFullYear()}-${pad(date.getMonth() + 1)}-${pad(date.getDate())}` +
    `T${pad(date.getHours())}:${pad(date.getMinutes())}:${pad(date.getSeconds())}.${pad(date.getMilliseconds(), 3)}Z`
  );
}

/** Destinatario leído de `account.transfer.search` (legado validarCbuAgendado / validarAliasAgendado). */
export interface TransferDestination {
  readonly accountAlias: string;
  readonly accountCode: string;
  readonly accountCodeUsd: string;
  readonly accountNumber: string;
  readonly accountTypeLabel: string;
  readonly accountTypeUsdLabel: string;
  readonly adheredAccountLabel: string;
  readonly balance: string;
  readonly bankName: string;
  readonly brandType: string;
  /** CBU buscado (búsqueda por CBU) o `customer.cbu` (búsqueda por alias). */
  readonly cbu: string;
  /** `customer.cuil` sin normalizar, como el legado. */
  readonly cuit: string;
  readonly idAdheredAccount: string | undefined;
  readonly isAdhered: boolean;
  readonly ownAccount: unknown;
  readonly productType: string;
  readonly synchronisedAccountLabel: string;
  readonly targetName: string;
  readonly targetNumberBank: string;
}

export interface TransferOrigin {
  /** `currency` de la cuenta origen en `widgets.accounts`. */
  readonly currency: string;
  readonly idProduct: string;
  /** `label` de la cuenta origen en `widgets.accounts`. */
  readonly label: string;
}

export interface TransferDebitCard {
  readonly idProduct: string;
  readonly label: string;
}

export interface TransferTicket {
  readonly amount: string;
  readonly cbuTarget: string;
  readonly conceptLabel: string;
  readonly originAccountDocument: string;
  readonly originAccountLabel: string;
  readonly originAccountName: string;
  readonly targetAccountCuil: string;
  readonly targetAccountDenomination: string;
}

/** Variante de transferencia inmediata a terceros del legado TransferenciasSteps. */
export type ThirdPartyTransferVariant = 'standard' | 'usd' | 'withoutDescription';

/**
 * Transferencias a terceros, agenda de destinatarios y comprobantes (legado TransferenciasSteps).
 * Cada mutación se ejecuta una sola vez por intento; el OTP usa la política central de segundo factor.
 */
export class ThirdPartyTransfersApi {
  public constructor(
    private readonly api: ReportingApiRequest,
    private readonly smsOperationOtp: SmsOperationOtpApi,
  ) {}

  public async findDestination(
    session: AuthenticatedSession,
    accountOrAlias: string,
    typeAccount: 'ALIAS' | 'CBU',
  ): Promise<TransferDestination> {
    const response = await this.post(
      session,
      ENDPOINTS.search,
      fillLegacyTemplate(searchTemplate, [
        ['_cbu_', accountOrAlias],
        ['_typeAccount_', typeAccount],
      ]),
      `búsqueda del destinatario por ${typeAccount}`,
    );
    const data = isJsonObject(response.data) ? response.data : {};
    const customer = isJsonObject(data.customer) ? data.customer : {};

    if (typeof data.isAdhered !== 'boolean') {
      throw new Error('La búsqueda del destinatario no devolvió data.isAdhered.');
    }

    return {
      accountAlias: legacyNullable(customer.accountAlias),
      accountCode: legacyNullable(customer.accountCode),
      accountCodeUsd: legacyNullable(customer.accountCodeUsd),
      accountNumber: legacyNullable(customer.accountNumber),
      accountTypeLabel: legacyNullable(customer.accountTypeLabel),
      accountTypeUsdLabel: legacyNullable(customer.accountTypeUsdLabel),
      adheredAccountLabel: legacyNullable(customer.adheredAccountLabel),
      balance: legacyNullable(customer.balance),
      bankName: legacyNullable(customer.bankName),
      brandType: legacyNullable(customer.brandType),
      cbu: typeAccount === 'CBU' ? accountOrAlias : legacyNullable(customer.cbu),
      cuit: legacyNullable(customer.cuil),
      idAdheredAccount: data.isAdhered ? legacyStripBrackets(customer.idAdheredAccount) : undefined,
      isAdhered: data.isAdhered,
      ownAccount: data.ownAccount,
      productType: legacyNullable(customer.productType),
      synchronisedAccountLabel: legacyNullable(customer.synchronisedAccountLabel),
      targetName: legacyNullable(customer.owner),
      targetNumberBank: legacyNullable(customer.bankNumber),
    };
  }

  public async removeDestination(session: AuthenticatedSession, idAdheredAccount: string): Promise<void> {
    await this.post(
      session,
      ENDPOINTS.delete,
      fillLegacyTemplate(deleteTemplate, [['_idAdhered_', idAdheredAccount]]),
      'eliminación del destinatario agendado',
    );
  }

  /** Agenda el CBU con referencia `belongsTo` (el legado usa el ID del caso) y OTP por SMS. */
  public async adhereDestination(
    session: AuthenticatedSession,
    username: string,
    belongsTo: string,
    cbu: string,
    ownAccount: unknown,
  ): Promise<string> {
    const otp = await this.smsOperationOtp.resolve(session, username);
    const response = await this.post(
      session,
      ENDPOINTS.adhere,
      fillLegacyTemplate(adhesionTemplate, [
        ['_destinatarioCBU_', cbu],
        ['_perteneceA_', belongsTo],
        ['_ownAccount_', String(ownAccount), { raw: true }],
        ['_code_', otp],
      ]),
      'alta del destinatario en la agenda',
    );
    const idAdheredAccount = isJsonObject(response.data) ? response.data.idAdheredAccount : undefined;

    return legacyNullable(idAdheredAccount);
  }

  /** Transferencia inmediata a terceros con OTP; devuelve `idTransaction`. */
  public async transferToThirdParty(
    session: AuthenticatedSession,
    username: string,
    variant: ThirdPartyTransferVariant,
    destination: TransferDestination,
    origin: TransferOrigin,
    debitCard: TransferDebitCard,
  ): Promise<string> {
    const otp = await this.smsOperationOtp.resolve(session, username);
    const common: LegacyReplacement[] = [
      ['_cbu_', destination.cbu],
      ['_originAccount_', origin.idProduct],
      ['_targetCuit_', destination.cuit],
      ['_code_', otp],
      ['_debitCardId_', debitCard.idProduct],
      ['_debitCardLabel_', debitCard.label],
      ['_currency_', origin.currency],
      ['_originAccountLabel_', origin.label],
      ['_targetAccountLabel_', destination.targetName],
      ['_targetBankName_', destination.bankName],
    ];
    const isWithoutDescription = variant === 'withoutDescription';
    const replacements: LegacyReplacement[] = isWithoutDescription
      ? [
          ...common,
          ['_targetAccountNumberLabel_', destination.accountNumber],
          ['_targetAccountCode_', destination.accountCode],
          ['_targetBrandType_', destination.brandType],
          ['_productType_', destination.productType],
          ['_adheredAccountFalse_', destination.adheredAccountLabel],
          ['_accountTypeLabel_', destination.accountTypeLabel],
          ['_accountAlias_', destination.accountAlias],
          ['_targetNumberBank_', destination.targetNumberBank],
        ]
      : [
          ...common,
          ['_targetAccountTypeLabel_', destination.synchronisedAccountLabel],
          ['_targetAccountNumberLabel_', destination.accountNumber],
          // En USD el legado usa accountCodeUsd como código de cuenta destino y deja accountCode vacío.
          ['_targetAccountCode_', variant === 'usd' ? destination.accountCodeUsd : destination.accountCode],
          ['_targetBrandType_', destination.brandType],
          ['_productType_', destination.productType],
          ['_adheredAccountFalse_', destination.adheredAccountLabel],
          ['_accountTypeLabel_', destination.accountTypeLabel],
          ['_synchronisedAccountLabel_', destination.synchronisedAccountLabel],
          ['_accountAlias_', destination.accountAlias],
          ['_targetNumberBank_', destination.targetNumberBank],
          ['_accountCodeUsd_', destination.accountCodeUsd],
          ['_balance_', destination.balance],
          ['_accountTypeUsdLabel_', destination.accountTypeUsdLabel],
          ['_accountCode_', variant === 'usd' ? '' : destination.accountCode],
        ];
    const response = await this.post(
      session,
      isWithoutDescription ? ENDPOINTS.makeWithoutDescription : ENDPOINTS.makeWithCapsCbu,
      fillLegacyTemplate(
        isWithoutDescription ? makeWithCapsCbuWithoutDescriptionTemplate : makeWithCapsCbuTemplate,
        replacements,
      ),
      `transferencia inmediata a terceros (${variant})`,
    );

    return legacyNullable(response.idTransaction);
  }

  /** Transferencia a terceros programada para mañana, con OTP; devuelve `idTransaction`. */
  public async scheduleToThirdParty(
    session: AuthenticatedSession,
    username: string,
    destination: TransferDestination,
    origin: TransferOrigin,
    debitCard: TransferDebitCard,
  ): Promise<string> {
    const otp = await this.smsOperationOtp.resolve(session, username);
    const response = await this.post(
      session,
      ENDPOINTS.scheduleWithCapsCbu,
      fillLegacyTemplate(scheduleWithCapsCbuTemplate, [
        ['_cbu_', destination.cbu],
        ['_originAccount_', origin.idProduct],
        ['_targetCuit_', destination.cuit],
        ['_code_', otp],
        ['_debitCardId_', debitCard.idProduct],
        ['_debitCardLabel_', debitCard.label],
        ['_currency_', origin.currency],
        ['_originAccountLabel_', origin.label],
        ['_targetAccountLabel_', destination.targetName],
        ['_targetBankName_', destination.bankName],
        ['_targetAccountNumberLabel_', destination.accountNumber],
        ['_targetAccountCode_', destination.accountCode],
        ['_targetBrandType_', destination.brandType],
        ['_productType_', destination.productType],
        ['_adheredAccountFalse_', destination.adheredAccountLabel],
        ['_accountTypeLabel_', destination.accountTypeLabel],
        ['_accountAlias_', destination.accountAlias],
        ['_date_', legacyTomorrow()],
        ['_targetNumberBank_', destination.targetNumberBank],
      ]),
      'transferencia programada a terceros',
    );

    return legacyNullable(response.idTransaction);
  }

  /** Transferencia inmediata a un contacto agendado (sin OTP en el legado). */
  public async transferToAdheredContact(
    session: AuthenticatedSession,
    destination: TransferDestination,
    idAdheredAccount: string,
    origin: TransferOrigin,
    debitCard: TransferDebitCard,
    belongsTo: string,
  ): Promise<void> {
    await this.post(
      session,
      ENDPOINTS.makeToAdhered,
      fillLegacyTemplate(makeWithCapsTemplate, [
        ['_cbu_', destination.cbu],
        ['_originAccount_', origin.idProduct],
        ['_targetCuit_', destination.cuit],
        ['_debitCardId_', debitCard.idProduct],
        ['_debitCardLabel_', debitCard.label],
        ['_currency_', origin.currency],
        ['_originAccountLabel_', origin.label],
        ['_targetAccountLabel_', destination.targetName],
        ['_targetBankName_', destination.bankName],
        ['_targetAccountNumberLabel_', destination.accountNumber],
        ['_targetAccountCode_', destination.accountCode],
        ['_targetAccount_', idAdheredAccount],
        ['_belongsTo_', belongsTo],
      ]),
      'transferencia a contacto agendado',
    );
  }

  /** Transferencia programada para mañana a un contacto agendado (sin OTP en el legado). */
  public async scheduleToAdheredContact(
    session: AuthenticatedSession,
    destination: TransferDestination,
    origin: TransferOrigin,
    debitCard: TransferDebitCard,
    belongsTo: string,
  ): Promise<void> {
    await this.post(
      session,
      ENDPOINTS.scheduleToAdhered,
      fillLegacyTemplate(scheduleWithCapsTemplate, [
        ['_cbu_', destination.cbu],
        ['_originAccount_', origin.idProduct],
        ['_targetCuit_', destination.cuit],
        ['_belongsTo_', belongsTo],
        ['_debitCardId_', debitCard.idProduct],
        ['_debitCardLabel_', debitCard.label],
        ['_currency_', origin.currency],
        ['_originAccountLabel_', origin.label],
        ['_targetAccountLabel_', destination.targetName],
        ['_targetBankName_', destination.bankName],
        ['_targetAccountNumberLabel_', destination.accountNumber],
        ['_targetAccountCode_', destination.accountCode],
        ['_targetBrandType_', destination.brandType],
        ['_productType_', destination.productType],
        ['_adheredAccountFalse_', destination.adheredAccountLabel],
        ['_accountTypeLabel_', destination.accountTypeLabel],
        ['_accountAlias_', destination.accountAlias],
        ['_date_', legacyTomorrow()],
        ['_targetNumberBank_', destination.targetNumberBank],
      ]),
      'transferencia programada a contacto agendado',
    );
  }

  /**
   * Transferencia programada entre cuentas propias (legado trasnferenciasProgramadaEntreCuentaPropias).
   * `cbuTarget` es el CBU que el legado toma de la cuenta origen (`obtenerCbuCuenta(cuentaOrigen)`).
   */
  public async scheduleBetweenOwnAccounts(
    session: AuthenticatedSession,
    originAccountId: string,
    targetAccountId: string,
    debitCard: TransferDebitCard,
    cbuTarget: string,
  ): Promise<void> {
    await this.post(
      session,
      ENDPOINTS.scheduleBetweenOwn,
      fillLegacyTemplate(scheduleMakeTemplate, [
        ['_originAccount_', originAccountId],
        ['_targetAccount_', targetAccountId],
        ['_debitCardId_', debitCard.idProduct],
        ['_debitCardLabel_', debitCard.label],
        ['_cbu_', cbuTarget],
        ['_date_', legacyTomorrow()],
      ]),
      'transferencia programada entre cuentas propias',
    );
  }

  public async readTicket(session: AuthenticatedSession, idTransaction: string): Promise<TransferTicket> {
    const response = await this.post(
      session,
      ENDPOINTS.ticket,
      fillLegacyTemplate(ticketTemplate, [['_idTransaccion_', idTransaction]]),
      'consulta del comprobante',
    );
    const ticketData = isJsonObject(response.data) ? response.data._ticket : undefined;
    const ticketBody = isJsonObject(ticketData) ? ticketData.data : undefined;
    const ticket = isJsonObject(ticketBody) && isJsonObject(ticketBody.ticket) ? ticketBody.ticket : undefined;

    if (!ticket) {
      throw new Error('El comprobante no devolvió data._ticket.data.ticket.');
    }

    const read = (field: string): string => {
      if (ticket[field] === undefined || ticket[field] === null) {
        throw new Error(`El comprobante no informa ${field}.`);
      }

      return legacyStripBrackets(ticket[field]);
    };

    return {
      amount: read('amount'),
      cbuTarget: read('cbuTarget'),
      conceptLabel: ticket.conceptLabel === undefined ? '' : legacyStripBrackets(ticket.conceptLabel),
      originAccountDocument: read('originAccountDocument'),
      originAccountLabel: read('originAccountLabel'),
      originAccountName: read('originAccountName'),
      targetAccountCuil: read('targetAccountCuil'),
      targetAccountDenomination: read('targetAccountDenomination'),
    };
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
