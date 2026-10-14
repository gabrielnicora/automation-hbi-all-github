import type { AuthenticatedSession } from '../../models/authenticated-session';
import { ReportingApiRequest, type ApiJsonObject } from '../reporting-api-request';

const SUCCESS_CODES = ['COR000I', 'COR013I', 'API690W', 'API691W', 'API692W'];
const ENDPOINT = '/api/v1/execute/util.codes';
const POLL_INTERVAL_MS = 2_500;
const TRANSIENT_READ_RETRIES = 2;
const TRANSIENT_RETRY_DELAY_MS = 500;
const REQUEST_DATA = {
  ajax_uuid: '274dd2b3-9fc7-4f82-b954-34a5d784ec9a',
  xAppVersion: '1.95.0',
} as const;

export const SECOND_FACTOR_POLL_INTERVAL_MS = POLL_INTERVAL_MS;

export type SecondFactorCodeProperty = 'code' | 'codeNoFormat';

export interface SecondFactorCodeEntry {
  readonly code: string;
  readonly createdAt: number;
  readonly origin: string;
  readonly type?: string;
}

export interface SecondFactorCodeSnapshot {
  readonly codes: ReadonlySet<string>;
  readonly latestCreatedAt: number;
}

export interface SecondFactorCodeCriteria {
  readonly origin: string;
  readonly property?: SecondFactorCodeProperty;
  readonly type?: string;
}

export class SecondFactorCodeUnavailableError extends Error {
  public constructor(criteria: SecondFactorCodeCriteria, sends: number, polls: number) {
    super(
      `No llegó un código nuevo de segundo factor para tipo ${criteria.type ?? 'no informado'}, origen ${normalizeSecondFactorOrigin(criteria.origin)}, tras ${sends} envíos y ${polls} consultas.`,
    );
    this.name = 'SecondFactorCodeUnavailableError';
  }
}

function isJsonObject(value: unknown): value is ApiJsonObject {
  return typeof value === 'object' && value !== null && !Array.isArray(value);
}

function readString(value: unknown): string | undefined {
  if (typeof value === 'string' && value.trim()) {
    return value.trim();
  }

  return typeof value === 'number' && Number.isFinite(value) ? String(value) : undefined;
}

export function normalizeSecondFactorOrigin(value: string): string {
  return value.includes('@') ? value.trim().toLowerCase() : value.replace(/\D/g, '');
}

export class SecondFactorCodeInboxApi {
  public constructor(private readonly api: ReportingApiRequest) {}

  public async snapshot(
    session: AuthenticatedSession,
    criteria: SecondFactorCodeCriteria,
  ): Promise<SecondFactorCodeSnapshot> {
    const entries = await this.readEntries(session, criteria);

    return {
      codes: new Set(entries.map((entry) => entry.code)),
      latestCreatedAt: Math.max(...entries.map((entry) => entry.createdAt), 0),
    };
  }

  public async waitForNewCode(
    session: AuthenticatedSession,
    criteria: SecondFactorCodeCriteria,
    previousSnapshot: SecondFactorCodeSnapshot,
    pollAttempts: number,
  ): Promise<string | undefined> {
    for (let attempt = 0; attempt < pollAttempts; attempt += 1) {
      await new Promise((resolve) => setTimeout(resolve, POLL_INTERVAL_MS));
      const entries = await this.readEntries(session, criteria);
      const code = entries
        .filter(
          (entry) => entry.createdAt > previousSnapshot.latestCreatedAt && !previousSnapshot.codes.has(entry.code),
        )
        .sort((left, right) => right.createdAt - left.createdAt)
        .at(0)?.code;

      if (code) {
        return code;
      }
    }

    return undefined;
  }

  private async readEntries(
    session: AuthenticatedSession,
    criteria: SecondFactorCodeCriteria,
  ): Promise<readonly SecondFactorCodeEntry[]> {
    for (let retry = 0; retry <= TRANSIENT_READ_RETRIES; retry += 1) {
      try {
        return await this.readEntriesOnce(session, criteria);
      } catch (error) {
        if (retry === TRANSIENT_READ_RETRIES || !isTransientTransportError(error)) {
          throw error;
        }

        await new Promise((resolve) => setTimeout(resolve, TRANSIENT_RETRY_DELAY_MS));
      }
    }

    throw new Error('La consulta de códigos de segundo factor agotó los reintentos de transporte.');
  }

  private async readEntriesOnce(
    session: AuthenticatedSession,
    criteria: SecondFactorCodeCriteria,
  ): Promise<readonly SecondFactorCodeEntry[]> {
    const body = await this.api.post({
      data: REQUEST_DATA,
      endpoint: ENDPOINT,
      expectedCodes: SUCCESS_CODES,
      headers: { Authorization: session.authorizationHeader },
      step: 'consulta de códigos de segundo factor',
    });
    const data = body.data;
    const codeList = isJsonObject(data) && Array.isArray(data.codeList) ? data.codeList : undefined;

    if (!codeList) {
      throw new Error('La consulta de códigos de segundo factor no devolvió data.codeList válido.');
    }

    const normalizedOrigin = normalizeSecondFactorOrigin(criteria.origin);
    const property = criteria.property ?? 'code';
    return codeList.flatMap((item) => {
      if (!isJsonObject(item)) {
        return [];
      }

      const origin = readString(item.origin);
      const code = readString(item[property]);
      const type = readString(item.type) ?? readString(item.channel);
      const createdAt = parseCodeTimestamp(readString(item.create));
      if (
        !origin ||
        !code ||
        createdAt === undefined ||
        normalizeSecondFactorOrigin(origin) !== normalizedOrigin ||
        (criteria.type && type !== criteria.type)
      ) {
        return [];
      }

      return [{ code, createdAt, origin, type }];
    });
  }
}

function isTransientTransportError(error: unknown): boolean {
  const message = error instanceof Error ? error.message : String(error);
  return /ECONNRESET|ECONNREFUSED|ETIMEDOUT|socket hang up|network/i.test(message);
}

function parseCodeTimestamp(value: string | undefined): number | undefined {
  if (!value) {
    return undefined;
  }

  const match = /^(\d{2})\/(\d{2})\/(\d{4}) (\d{2}):(\d{2}):(\d{2})$/.exec(value);
  if (!match) {
    return undefined;
  }

  const [, day, month, year, hours, minutes, seconds] = match;
  return Date.UTC(Number(year), Number(month) - 1, Number(day), Number(hours), Number(minutes), Number(seconds));
}
