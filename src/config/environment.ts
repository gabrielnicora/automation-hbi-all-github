export interface EnvironmentConfig {
  readonly apiBaseUrl: string;
  readonly uiBaseUrl: string;
}

const BERSA_QA_API_URL = 'https://apitest01.bancoentrerios.com';
const BERSA_QA_UI_URL = 'https://hbtest01.bancoentrerios.com/startPage';

function readBaseUrl(value: string | undefined, defaultValue: string, variableName: string): string {
  const configuredValue = value?.trim() || defaultValue;

  try {
    return new URL(configuredValue).toString();
  } catch {
    throw new Error(`${variableName} debe contener una URL válida.`);
  }
}

export function loadEnvironmentConfig(): EnvironmentConfig {
  const testEnvironment = (process.env.TEST_ENV ?? 'qa').trim().toLowerCase();

  if (testEnvironment !== 'qa') {
    throw new Error(`Ambiente no soportado: "${testEnvironment}". El único ambiente aprobado es qa.`);
  }

  return {
    apiBaseUrl: readBaseUrl(process.env.API_BASE_URL, BERSA_QA_API_URL, 'API_BASE_URL'),
    uiBaseUrl: readBaseUrl(process.env.UI_BASE_URL, BERSA_QA_UI_URL, 'UI_BASE_URL'),
  };
}
