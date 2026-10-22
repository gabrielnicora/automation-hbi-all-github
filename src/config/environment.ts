export type TestEnvironment = 'qa' | 'une-dev';

export interface EnvironmentConfig {
  readonly name: TestEnvironment;
  readonly apiBaseUrl: string;
  readonly uiBaseUrl: string;
}

const DEFAULT_URLS: Record<TestEnvironment, { readonly apiBaseUrl: string; readonly uiBaseUrl: string }> = {
  qa: {
    apiBaseUrl: 'https://apitest01.bancoentrerios.com',
    uiBaseUrl: 'https://hbtest01.bancoentrerios.com/startPage',
  },
  'une-dev': {
    apiBaseUrl: 'https://apii.dev.bancoune.com',
    uiBaseUrl: 'https://onlinebanking.dev.bancoune.com',
  },
};

function readBaseUrl(value: string | undefined, defaultValue: string, variableName: string): string {
  const configuredValue = value?.trim() || defaultValue;

  try {
    return new URL(configuredValue).toString();
  } catch {
    throw new Error(`${variableName} debe contener una URL válida.`);
  }
}

export function readTestEnvironment(): TestEnvironment {
  const testEnvironment = process.env.TEST_ENV?.trim().toLowerCase() || 'qa';

  if (testEnvironment !== 'qa' && testEnvironment !== 'une-dev') {
    throw new Error(`Ambiente no soportado: "${testEnvironment}". Valores permitidos: qa, une-dev.`);
  }

  return testEnvironment;
}

export function loadEnvironmentConfig(): EnvironmentConfig {
  const name = readTestEnvironment();
  const defaults = DEFAULT_URLS[name];

  return {
    name,
    apiBaseUrl: readBaseUrl(process.env.API_BASE_URL, defaults.apiBaseUrl, 'API_BASE_URL'),
    uiBaseUrl: readBaseUrl(process.env.UI_BASE_URL, defaults.uiBaseUrl, 'UI_BASE_URL'),
  };
}
