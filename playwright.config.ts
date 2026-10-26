import { defineConfig } from '@playwright/test';

const isCi = Boolean(process.env.CI);
const browserChannel = process.env.PW_BROWSER_CHANNEL as 'chrome' | 'msedge' | undefined;
const retriesValue = process.env.PW_RETRIES ?? '1';
const retries = Number(retriesValue);

if (!/^\d+$/.test(retriesValue) || !Number.isSafeInteger(retries)) {
  throw new Error('PW_RETRIES debe ser un entero no negativo. Usar 0 para desactivar los reintentos.');
}

export default defineConfig({
  testDir: './tests',
  outputDir: './test-results/artifacts',
  // Los archivos pueden ejecutarse en paralelo; los tests de un mismo archivo no.
  fullyParallel: false,
  forbidOnly: isCi,
  retries,
  workers: Number(process.env.PW_WORKERS ?? 1),
  timeout: 90_000,
  expect: { timeout: 15_000 },
  reporter: [
    ['list'],
    ['html', { open: 'never', outputFolder: 'playwright-report' }],
    ['junit', { outputFile: 'test-results/junit-report.xml' }],
  ],
  use: {
    channel: browserChannel,
    ignoreHTTPSErrors: true,
    actionTimeout: 20_000,
    navigationTimeout: 120_000,
    trace: 'off',
    video: 'retain-on-failure',
    screenshot: 'only-on-failure',
    viewport: { width: 1920, height: 1080 },
  },
  projects: [
    {
      name: 'api',
      testMatch: /tests\/api\/.*\.spec\.ts/,
    },
    {
      name: 'ui',
      use: { browserName: 'chromium' },
    },
  ],
});
