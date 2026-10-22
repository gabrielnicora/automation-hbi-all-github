import { defineConfig } from '@playwright/test';

// Configuración de `playwright merge-reports` para unir los blobs de los shards de CI.
// Replica los reportes HTML y JUnit de playwright.config.ts.
export default defineConfig({
  testDir: './tests',
  reporter: [
    ['html', { open: 'never', outputFolder: 'playwright-report' }],
    ['junit', { outputFile: 'test-results/junit-report.xml' }],
  ],
});
