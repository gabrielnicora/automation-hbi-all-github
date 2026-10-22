import type { AuthenticatedSession } from '../../models/authenticated-session';
import type { PhoneChangeTestData } from '../../test-data/phone-change-data';
import { ReportingApiRequest, type ApiJsonObject } from '../reporting-api-request';
import { SecondFactorChallengeApi } from '../authentication/second-factor-challenge.api';
import { ProfileMobilePhoneApi } from './profile-mobile-phone.api';

const SUCCESS_CODES: readonly string[] = ['COR000I', 'COR013I', 'API690W', 'API691W', 'API692W'];
const RETRYABLE_VERIFICATION_CODE = 'COR020W';
const MAX_VERIFICATION_CODE_REQUESTS = 4;
const MOBILE_AREA_CODE = '11';

const ENDPOINTS = {
  sendSmsOtp: '/api/v1/execute/secondFactor.otp.sms.sendToken',
  sendPhoneVerificationCode: '/api/v1/execute/preferences.userData.mobilePhone.sendCode',
  updatePhone: '/api/v1/execute/preferences.userData.mobilePhone.update',
} as const;

const REQUEST_METADATA = {
  emptyRequest: {
    ajaxUuid: '274dd2b3-9fc7-4f82-b954-34a5d784ec9a',
    appVersion: '1.95.0',
  },
  sendPhoneVerificationCode: {
    ajaxUuid: 'abcf3a35-95db-4c23-aac4-323faedf5e8f',
    appVersion: '1.79.1',
  },
  updatePhone: {
    ajaxUuid: '3f9f92fd-b365-4f36-aa85-e73085a89655',
    appVersion: '1.79.1',
  },
} as const;

export interface PhoneChangeResult {
  readonly isPersisted: boolean;
}

export class PhoneChangeApi {
  public constructor(
    private readonly api: ReportingApiRequest,
    private readonly testData: PhoneChangeTestData,
    private readonly profileMobilePhone: ProfileMobilePhoneApi,
    private readonly secondFactor: SecondFactorChallengeApi,
  ) {}

  public async changePhone(session: AuthenticatedSession, username: string): Promise<PhoneChangeResult> {
    const currentPhone = await this.profileMobilePhone.readCurrentPhone(session);
    const replacementPhone = this.selectReplacementPhone(currentPhone);
    const rawVerificationCode = await this.secondFactor.requestNewCode(session, {
      criteria: { origin: replacementPhone, property: 'codeNoFormat' },
      requestInitialCode: () => this.sendPhoneVerificationCode(session, username, currentPhone, replacementPhone),
    });
    // El código fijo de une-dev se usa tal cual; el de util.codes trae el formato BERSA con '-'.
    const verificationCode = this.secondFactor.usesFixedCode
      ? rawVerificationCode
      : this.extractVerificationCode(rawVerificationCode);

    if (!verificationCode) {
      throw new Error('El código de verificación del teléfono nuevo no tiene el formato esperado.');
    }
    await this.updatePhone(session, verificationCode);

    const persistedPhone = await this.profileMobilePhone.readCurrentPhone(session);

    return { isPersisted: persistedPhone === replacementPhone };
  }

  private selectReplacementPhone(currentPhone: string): string {
    const currentIndex = this.testData.phoneCandidates.indexOf(currentPhone);
    const replacementPhone =
      currentIndex >= 0
        ? this.testData.phoneCandidates[(currentIndex + 1) % this.testData.phoneCandidates.length]
        : this.testData.phoneCandidates[0];

    if (!replacementPhone) {
      throw new Error('No existe un teléfono autorizado diferente al teléfono actual.');
    }

    if (!replacementPhone || !replacementPhone.startsWith(MOBILE_AREA_CODE)) {
      throw new Error('El teléfono autorizado no coincide con el código de área configurado.');
    }

    return replacementPhone;
  }

  private async sendSmsOtp(session: AuthenticatedSession): Promise<void> {
    await this.post(
      'solicitud de OTP por SMS',
      ENDPOINTS.sendSmsOtp,
      this.emptyRequestPayload(),
      session.authorizationHeader,
    );
  }

  private async sendPhoneVerificationCode(
    session: AuthenticatedSession,
    username: string,
    currentPhone: string,
    replacementPhone: string,
  ): Promise<void> {
    const metadata = REQUEST_METADATA.sendPhoneVerificationCode;
    const localPhone = replacementPhone.slice(MOBILE_AREA_CODE.length);
    let lastMessage: string | undefined;
    let lastRejectedFields: string | undefined;

    for (let attempt = 1; attempt <= MAX_VERIFICATION_CODE_REQUESTS; attempt += 1) {
      const operationOtp = await this.secondFactor.resolveOperationOtp(session, username, {
        criteria: { origin: currentPhone, type: 'smsCode' },
        requestInitialCode: () => this.sendSmsOtp(session),
      });
      const response = await this.post(
        'envío del código de verificación del teléfono',
        ENDPOINTS.sendPhoneVerificationCode,
        {
          mobileAreaCode: MOBILE_AREA_CODE,
          mobilePhone: localPhone,
          _otp: `OTP_TOKEN@${operationOtp}`,
          ajax_uuid: metadata.ajaxUuid,
          xAppVersion: metadata.appVersion,
        },
        session.authorizationHeader,
        [...SUCCESS_CODES, RETRYABLE_VERIFICATION_CODE],
      );

      if (response.code !== RETRYABLE_VERIFICATION_CODE) {
        return;
      }

      lastMessage = typeof response.message === 'string' ? response.message : undefined;
      lastRejectedFields =
        response.data && typeof response.data === 'object' && !Array.isArray(response.data)
          ? Object.keys(response.data).join(', ')
          : undefined;

      if (lastRejectedFields?.split(', ').includes('mobilePhone')) {
        throw new Error(
          `Los datos QA del cambio de teléfono fueron rechazados: el teléfono destino ${replacementPhone} no es válido para BERSA.`,
        );
      }

      if (attempt === MAX_VERIFICATION_CODE_REQUESTS) {
        throw new Error(
          `La API no pudo solicitar el código de verificación del teléfono nuevo: ${RETRYABLE_VERIFICATION_CODE} tras ${MAX_VERIFICATION_CODE_REQUESTS} intentos para ${replacementPhone}.${lastMessage ? ` Mensaje: ${lastMessage}` : ''}${lastRejectedFields ? ` Campos rechazados: ${lastRejectedFields}.` : ''}`,
        );
      }
    }
  }

  private async updatePhone(session: AuthenticatedSession, verificationCode: string): Promise<void> {
    const metadata = REQUEST_METADATA.updatePhone;

    await this.post(
      'confirmación del teléfono nuevo',
      ENDPOINTS.updatePhone,
      {
        idForm: 'modifyUserData',
        mobilePhoneCode: verificationCode,
        ajax_uuid: metadata.ajaxUuid,
        xAppVersion: metadata.appVersion,
      },
      session.authorizationHeader,
    );
  }

  private extractVerificationCode(value: string): string | undefined {
    const separatorIndex = value.indexOf(':');
    const code = separatorIndex >= 0 ? value.slice(separatorIndex + 1).trim() : value.trim();

    return code.includes('-') ? code : undefined;
  }

  private emptyRequestPayload(): ApiJsonObject {
    const metadata = REQUEST_METADATA.emptyRequest;

    return {
      ajax_uuid: metadata.ajaxUuid,
      xAppVersion: metadata.appVersion,
    };
  }

  private async post(
    step: string,
    endpoint: string,
    data: ApiJsonObject,
    authorizationHeader: string | undefined,
    expectedCodes: readonly string[] = SUCCESS_CODES,
  ): Promise<ApiJsonObject> {
    return this.api.post({
      data,
      endpoint,
      expectedCodes,
      headers: authorizationHeader ? { Authorization: authorizationHeader } : undefined,
      step,
    });
  }
}
