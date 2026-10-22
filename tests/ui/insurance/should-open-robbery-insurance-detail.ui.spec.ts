import { expect, test } from '../../../src/fixtures/ui-test';

const TEST_CASE_ID = 'TC0620002BERSA';

test.describe('Seguros UI de Banco Entre Ríos', () => {
  test.use({ testCaseId: TEST_CASE_ID });

  test(`${TEST_CASE_ID} - muestra el detalle del seguro de Robo`, async ({
    insurancePage,
    loginCredentials,
    loginPage,
    startPage,
  }) => {
    await startPage.open();
    await startPage.openLogin();
    await loginPage.authenticate(loginCredentials);
    await insurancePage.openRobberyInsuranceDetail();

    const detail = await insurancePage.readDetail();
    expect(detail.title).toBe('Seguro de Bolso');
    expect(detail.description).toBe('Protección para tus pertenencias en la vía pública');
  });
});
