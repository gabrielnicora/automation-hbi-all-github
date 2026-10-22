import { expect, test } from '../../../src/fixtures/ui-test';

const TEST_CASE_ID = 'TC0620001BERSA';

test.describe('Seguros UI de Banco Entre Ríos', () => {
  test.use({ testCaseId: TEST_CASE_ID });
  test.setTimeout(180_000);

  test(`${TEST_CASE_ID} - muestra la tarjeta del seguro de Robo`, async ({
    insurancePage,
    loginCredentials,
    loginPage,
    startPage,
  }) => {
    await startPage.open();
    await startPage.openLogin();
    await loginPage.authenticate(loginCredentials);

    await insurancePage.open();

    expect(await insurancePage.isInsuranceCardAvailable('Robo'), 'Debe listar la card del seguro de Robo.').toBe(true);
  });
});
