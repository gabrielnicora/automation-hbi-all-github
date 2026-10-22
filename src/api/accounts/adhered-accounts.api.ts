import type { AuthenticatedSession } from '../../models/authenticated-session';
import { SmsOperationOtpApi } from '../authentication/sms-operation-otp.api';
import { ReportingApiRequest } from '../reporting-api-request';

const ADHERE_ENDPOINT = '/api/v1/execute/adhesion.account.new';
const DELETE_ENDPOINT = '/api/v1/execute/delete.adheredAccount';
// Códigos de éxito del validador legado (Config/ValidarResponse.validateResponses).
const SUCCESS_CODES: readonly string[] = ['COR000I', 'COR013I', 'API690W', 'API691W', 'API692W'];

export interface NewAdheredAccount {
  /** CBU, CVU o alias del destinatario. */
  readonly account: string;
  /** Tipo de destino (`CBU`, `CVU`, `ALIAS`); el legado lo envía también como `belongsTo`. */
  readonly typeAccount: string;
  /** Valor `ownAccount` informado por la búsqueda previa del destinatario. */
  readonly ownAccount: unknown;
}

/** Agenda de destinatarios (legado CuentasSteps: adhesionAccountNew y deleteAdheredAccount). */
export class AdheredAccountsApi {
  public constructor(
    private readonly api: ReportingApiRequest,
    private readonly smsOperationOtp: SmsOperationOtpApi,
  ) {}

  public async remove(session: AuthenticatedSession, idAdheredAccount: string): Promise<void> {
    await this.api.post({
      data: { idAdheredAccount },
      endpoint: DELETE_ENDPOINT,
      expectedCodes: SUCCESS_CODES,
      headers: { Authorization: session.authorizationHeader },
      step: 'eliminación del destinatario agendado',
    });
  }

  /** Agenda el destinatario con OTP por SMS. Ejecuta la mutación una sola vez por intento. */
  public async adhere(session: AuthenticatedSession, username: string, destination: NewAdheredAccount): Promise<void> {
    const otp = await this.smsOperationOtp.resolve(session, username);

    await this.api.post({
      data: {
        account: destination.account,
        typeAccount: destination.typeAccount,
        belongsTo: destination.typeAccount,
        email: '',
        ownAccount: destination.ownAccount,
        _otp: `OTP_TOKEN@${otp}`,
      },
      endpoint: ADHERE_ENDPOINT,
      expectedCodes: SUCCESS_CODES,
      headers: { Authorization: session.authorizationHeader },
      step: 'alta del destinatario en la agenda',
    });
  }
}
