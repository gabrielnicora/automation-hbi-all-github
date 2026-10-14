import { test, type APIRequestContext, type TestInfo } from '@playwright/test';

export type ApiJsonObject = Record<string, unknown>;

interface ApiPostOptions {
  readonly data: ApiJsonObject;
  readonly endpoint: string;
  readonly expectedCodes: readonly string[];
  readonly headers?: Readonly<Record<string, string>>;
  readonly step: string;
}

interface ApiExchange {
  readonly durationMs: number;
  readonly sequence: number;
  readonly source?: ApiCallLocation;
  readonly request: {
    readonly body: unknown;
    readonly headers: unknown;
    readonly method: 'POST';
    readonly path: string;
  };
  readonly response?: {
    readonly body: unknown;
    readonly headers: unknown;
    readonly status: number;
  };
  readonly step: string;
  readonly transportError?: string;
}

interface ApiCallLocation {
  readonly column: number;
  readonly file: string;
  readonly line: number;
  readonly methodName: string;
}

const OMITTED_CREDENTIAL = '<omitido: credencial o código de autenticación>';
const SECRET_FIELD_NAMES = new Set([
  'accesstoken',
  'authorization',
  'codenoformat',
  'cookie',
  'exchangetoken',
  'idsession',
  'mastercode',
  'mobilephonecode',
  'otp',
  'pass',
  'password',
  'proxyauthorization',
  'setcookie',
]);
const LARGE_BASE64_FIELD_NAMES = new Set(['encodebase64', 'fingerprint']);

function normalizeFieldName(value: string): string {
  return value.replace(/[^a-z0-9]/gi, '').toLowerCase();
}

function omittedLargeValue(value: unknown): string {
  const length = typeof value === 'string' ? value.length : 0;
  return `<omitido: Base64 de ${length} caracteres>`;
}

function isBase64DataUri(value: unknown): value is string {
  return typeof value === 'string' && /^data:[^;,]+;base64,/i.test(value);
}

function sanitizeForReport(value: unknown, path: readonly string[] = []): unknown {
  if (Array.isArray(value)) {
    return value.map((item, index) => sanitizeForReport(item, [...path, String(index)]));
  }

  if (value === null || typeof value !== 'object') {
    return value;
  }

  return Object.fromEntries(
    Object.entries(value).map(([propertyName, propertyValue]) => {
      const normalizedName = normalizeFieldName(propertyName);
      const normalizedPath = path.map(normalizeFieldName);
      const isSecondFactorCode = normalizedName === 'code' && normalizedPath.includes('codelist');
      const isSessionCredential =
        SECRET_FIELD_NAMES.has(normalizedName) ||
        normalizedName.includes('authorization') ||
        normalizedName.includes('cookie') ||
        normalizedName.endsWith('token') ||
        normalizedName === 'apikey';

      if (isSessionCredential || isSecondFactorCode) {
        return [propertyName, OMITTED_CREDENTIAL];
      }

      if (LARGE_BASE64_FIELD_NAMES.has(normalizedName) || isBase64DataUri(propertyValue)) {
        return [propertyName, omittedLargeValue(propertyValue)];
      }

      return [propertyName, sanitizeForReport(propertyValue, [...path, propertyName])];
    }),
  );
}

function isApiJsonObject(value: unknown): value is ApiJsonObject {
  return typeof value === 'object' && value !== null && !Array.isArray(value);
}

function errorMessage(error: unknown): string {
  const message = error instanceof Error ? error.message : 'Error de transporte desconocido.';

  return message
    .replace(/(Authorization:\s*Bearer\s+)[^\s\r\n]+/gi, '$1<omitido: credencial o código de autenticación>')
    .replace(/(cookie:\s*)[^\r\n]+/gi, '$1<omitido: credencial o código de autenticación>')
    .replace(/(set-cookie:\s*)[^\r\n]+/gi, '$1<omitido: credencial o código de autenticación>');
}

function apiCallLocation(): ApiCallLocation | undefined {
  const stackFrames = new Error().stack?.split('\n').slice(1) ?? [];
  const callerLocations: ApiCallLocation[] = [];

  for (const stackFrame of stackFrames) {
    if (stackFrame.includes('reporting-api-request')) {
      continue;
    }

    const namedFrame = stackFrame.match(/^\s*at (.+) \((.+):(\d+):(\d+)\)$/);
    const plainFrame = stackFrame.match(/^\s*at (.+):(\d+):(\d+)$/);
    const methodName = namedFrame?.[1] ?? '(anonymous)';
    const file = namedFrame?.[2] ?? plainFrame?.[1];
    const line = namedFrame?.[3] ?? plainFrame?.[2];
    const column = namedFrame?.[4] ?? plainFrame?.[3];

    if (!file || !line || !column || file.includes('node_modules')) {
      continue;
    }

    callerLocations.push({ column: Number(column), file, line: Number(line), methodName });
  }

  const firstCaller = callerLocations[0];

  if (!firstCaller) {
    return undefined;
  }

  const isGenericRequestMethod = /\.(?:post|executeStep|executePost)$/.test(firstCaller.methodName);
  return isGenericRequestMethod
    ? (callerLocations.find((location) => location.file === firstCaller.file && location !== firstCaller) ??
        firstCaller)
    : firstCaller;
}

function attachmentName(sequence: number, endpoint: string): string {
  const endpointName =
    endpoint
      .split('/')
      .filter(Boolean)
      .pop()
      ?.replace(/[^a-z\d._-]/gi, '-') ?? 'request';

  return `api-${String(sequence).padStart(2, '0')}-${endpointName}-request-response.json`;
}

export class ReportingApiRequest {
  private readonly exchanges: ApiExchange[] = [];
  private nextSequence = 1;

  public constructor(private readonly api: APIRequestContext) {}

  public async post(options: ApiPostOptions): Promise<ApiJsonObject> {
    const sequence = this.nextSequence++;
    const location = apiCallLocation();
    const callerFile = location?.file.split(/[\\/]/).pop();
    const callerHint = location && callerFile ? ` — ${callerFile}:${location.line} (${location.methodName})` : '';

    return test.step(
      `API ${sequence}: POST ${options.endpoint} — ${options.step}${callerHint}`,
      async (stepInfo) => {
        try {
          return await this.executePost(options, sequence, location);
        } finally {
          const exchange = this.exchanges.find((recordedExchange) => recordedExchange.sequence === sequence);

          if (exchange) {
            await stepInfo.attach(attachmentName(sequence, options.endpoint), {
              body: Buffer.from(JSON.stringify(exchange, null, 2), 'utf8'),
              contentType: 'application/json',
            });
          }
        }
      },
      { box: true, ...(location ? { location } : {}) },
    );
  }

  private async executePost(
    options: ApiPostOptions,
    sequence: number,
    source: ApiCallLocation | undefined,
  ): Promise<ApiJsonObject> {
    const startedAt = Date.now();
    const requestHeaders = {
      'Content-Type': 'application/json',
      ...options.headers,
    };
    let responseRecorded = false;

    try {
      const response = await this.api.post(options.endpoint, {
        headers: options.headers,
        data: options.data,
      });
      const responseBuffer = await response.body();
      const responseText = responseBuffer.toString('utf8');
      let responseBody: unknown;

      try {
        responseBody = JSON.parse(responseText) as unknown;
      } catch {
        responseBody = responseText;
      }

      this.exchanges.push({
        durationMs: Date.now() - startedAt,
        sequence,
        ...(source ? { source } : {}),
        request: {
          body: sanitizeForReport(options.data),
          headers: sanitizeForReport(requestHeaders),
          method: 'POST',
          path: options.endpoint,
        },
        response: {
          body: sanitizeForReport(responseBody),
          headers: sanitizeForReport(response.headers()),
          status: response.status(),
        },
        step: options.step,
      });
      responseRecorded = true;

      if (response.status() !== 200) {
        throw new Error(`La API respondió con HTTP ${response.status()} durante ${options.step}.`);
      }

      if (!isApiJsonObject(responseBody)) {
        const contentType = response.headers()['content-type'] ?? 'ausente';
        throw new Error(
          `La API durante ${options.step} no devolvió un objeto JSON válido. Content-Type: ${contentType}.`,
        );
      }

      const receivedCode = responseBody.code;

      if (typeof receivedCode !== 'string' || !options.expectedCodes.includes(receivedCode)) {
        const safeCode = typeof receivedCode === 'string' ? receivedCode : 'ausente o inválido';
        const safeMessage = typeof responseBody.message === 'string' ? responseBody.message : undefined;
        const messageDetail = safeMessage ? ` Mensaje: ${safeMessage}` : '';
        throw new Error(`La API devolvió el código de negocio ${safeCode} durante ${options.step}.${messageDetail}`);
      }

      return responseBody;
    } catch (error) {
      if (!responseRecorded) {
        this.exchanges.push({
          durationMs: Date.now() - startedAt,
          sequence,
          ...(source ? { source } : {}),
          request: {
            body: sanitizeForReport(options.data),
            headers: sanitizeForReport(requestHeaders),
            method: 'POST',
            path: options.endpoint,
          },
          step: options.step,
          transportError: errorMessage(error),
        });
      }

      throw error;
    }
  }

  public async attachIfTestFailed(testInfo: TestInfo): Promise<void> {
    if (testInfo.status === testInfo.expectedStatus || this.exchanges.length === 0) {
      return;
    }

    const report = {
      attempt: testInfo.retry + 1,
      requests: [...this.exchanges].sort((first, second) => first.sequence - second.sequence),
      test: testInfo.title,
    };

    await testInfo.attach(`diagnostico-api-intento-${testInfo.retry + 1}`, {
      body: Buffer.from(JSON.stringify(report, null, 2), 'utf8'),
      contentType: 'application/json',
    });
  }
}
