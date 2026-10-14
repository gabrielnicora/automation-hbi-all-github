import { randomUUID } from 'node:crypto';
import { ReportingApiRequest, type ApiJsonObject } from '../reporting-api-request';

const ENDPOINT = '/api/v1/execute/get.temsAndConditions.anonymous.ForFunctionality';
const SUCCESS_CODE = 'COR000I';
const APP_VERSION = '1.88.0';

export interface TermsAndConditionsResult {
  readonly hasContent: boolean;
}

function isJsonObject(value: unknown): value is ApiJsonObject {
  return typeof value === 'object' && value !== null && !Array.isArray(value);
}

export class TermsAndConditionsApi {
  public constructor(private readonly api: ReportingApiRequest) {}

  public async getGeneralConditions(): Promise<TermsAndConditionsResult> {
    const response = await this.api.post({
      data: {
        lang: 'es',
        functionalityCode: 'GENERAL_CONDITIONS',
        channel: 'frontend',
        ajax_uuid: randomUUID(),
        xAppVersion: APP_VERSION,
      },
      endpoint: ENDPOINT,
      expectedCodes: [SUCCESS_CODE],
      step: 'consulta anónima de términos y condiciones',
    });
    const data = response.data;

    if (!isJsonObject(data)) {
      throw new Error('La consulta de términos y condiciones no devolvió un objeto data válido.');
    }

    return {
      hasContent: typeof data._termAndCondition === 'string' && data._termAndCondition.trim().length > 0,
    };
  }
}
