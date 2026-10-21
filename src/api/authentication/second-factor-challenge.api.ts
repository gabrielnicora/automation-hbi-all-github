import {
  SecondFactorCodeInboxApi,
  SecondFactorCodeUnavailableError,
  type SecondFactorCodeCriteria,
} from './second-factor-code-inbox.api';
import { resolveEffectiveSecondFactorMethod, type SecondFactorConfig } from '../../config/second-factor';
import type { AuthenticatedSession } from '../../models/authenticated-session';

const MAXIMUM_CODE_SENDS = 3;
const INITIAL_CODE_WAIT_POLLS = 12;
const REPLACEMENT_CODE_WAIT_POLLS = 24;

export interface SecondFactorCodeRequest {
  readonly criteria: SecondFactorCodeCriteria;
  readonly requestInitialCode: () => Promise<void>;
  readonly requestReplacementCode?: () => Promise<void>;
}

export class SecondFactorChallengeApi {
  public constructor(
    private readonly codeInbox: SecondFactorCodeInboxApi,
    private readonly config: SecondFactorConfig,
  ) {}

  public async resolveOperationOtp(
    session: AuthenticatedSession,
    username: string,
    request: SecondFactorCodeRequest,
  ): Promise<string> {
    if (resolveEffectiveSecondFactorMethod(this.config, username) === 'master-code') {
      return this.config.masterCode;
    }

    return this.requestNewCode(session, request);
  }

  /**
   * Espera el código de un único envío (sin reenvíos), para desafíos cuyo disparo no puede
   * repetirse desde la misma pantalla (por ejemplo, el correo de recupero de usuario).
   */
  public async requestSingleCode(
    session: AuthenticatedSession,
    criteria: SecondFactorCodeCriteria,
    requestCode: () => Promise<void>,
  ): Promise<string> {
    const previousSnapshot = await this.codeInbox.snapshot(session, criteria);

    await requestCode();
    const code = await this.codeInbox.waitForNewCode(session, criteria, previousSnapshot, INITIAL_CODE_WAIT_POLLS);

    if (!code) {
      throw new SecondFactorCodeUnavailableError(criteria, 1, INITIAL_CODE_WAIT_POLLS);
    }

    return code;
  }

  public async requestNewCode(session: AuthenticatedSession, request: SecondFactorCodeRequest): Promise<string> {
    const previousSnapshot = await this.codeInbox.snapshot(session, request.criteria);

    await request.requestInitialCode();

    for (let sendAttempt = 1; sendAttempt <= MAXIMUM_CODE_SENDS; sendAttempt += 1) {
      const pollAttempts = sendAttempt === 1 ? INITIAL_CODE_WAIT_POLLS : REPLACEMENT_CODE_WAIT_POLLS;
      const code = await this.codeInbox.waitForNewCode(session, request.criteria, previousSnapshot, pollAttempts);

      if (code) {
        return code;
      }

      if (sendAttempt < MAXIMUM_CODE_SENDS) {
        await (request.requestReplacementCode ?? request.requestInitialCode)();
      }
    }

    throw new SecondFactorCodeUnavailableError(
      request.criteria,
      MAXIMUM_CODE_SENDS,
      INITIAL_CODE_WAIT_POLLS + (MAXIMUM_CODE_SENDS - 1) * REPLACEMENT_CODE_WAIT_POLLS,
    );
  }
}
