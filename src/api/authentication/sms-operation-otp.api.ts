import type { AuthenticatedSession } from '../../models/authenticated-session';
import { ProfileMobilePhoneApi } from '../profile/profile-mobile-phone.api';
import { ReportingApiRequest } from '../reporting-api-request';
import { SecondFactorChallengeApi } from './second-factor-challenge.api';

const SEND_SMS_TOKEN_ENDPOINT = '/api/v1/execute/secondFactor.otp.sms.sendToken';
const SUCCESS_CODES: readonly string[] = ['COR000I', 'COR013I', 'API690W', 'API691W', 'API692W'];
// Request común legado Requests/Compartidos/requestVacio.json.
const EMPTY_REQUEST = { ajax_uuid: '274dd2b3-9fc7-4f82-b954-34a5d784ec9a', xAppVersion: '1.95.0' } as const;

/**
 * OTP de autorización de operaciones enviado por SMS al teléfono actual del perfil
 * (legado DobleFactor.getSecondFactor). La espera y los reenvíos pertenecen a la política
 * central de segundo factor; este cliente sólo dispara el envío.
 */
export class SmsOperationOtpApi {
  public constructor(
    private readonly api: ReportingApiRequest,
    private readonly profileMobilePhone: ProfileMobilePhoneApi,
    private readonly secondFactor: SecondFactorChallengeApi,
  ) {}

  public async resolve(session: AuthenticatedSession, username: string): Promise<string> {
    const phone = await this.profileMobilePhone.readCurrentPhone(session);

    return this.secondFactor.resolveOperationOtp(session, username, {
      criteria: { origin: phone },
      requestInitialCode: () => this.sendSmsToken(session),
    });
  }

  private async sendSmsToken(session: AuthenticatedSession): Promise<void> {
    await this.api.post({
      data: { ...EMPTY_REQUEST },
      endpoint: SEND_SMS_TOKEN_ENDPOINT,
      expectedCodes: SUCCESS_CODES,
      headers: { Authorization: session.authorizationHeader },
      step: 'solicitud de OTP por SMS',
    });
  }
}
