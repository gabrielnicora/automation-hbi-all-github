import { expect, test } from '../../../src/fixtures/ui-test';

const TEST_CASE_ID = 'TC-014-2-0003-UNE';

test.describe('Pantalla principal UI de Banco Entre Ríos', () => {
  test.use({ testCaseId: TEST_CASE_ID });
  test.setTimeout(180_000);

  test(`${TEST_CASE_ID} - abre el enlace Información de seguridad`, async ({ loginPage, startPage }) => {
    await startPage.open();
    await startPage.openLogin();

    const securityPage = await loginPage.openSecurityInformation();

    expect(securityPage.heading).toContain('Seguridad para usar Home Banking / Office Banking');
  });
});
