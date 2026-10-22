import { expect, test } from '../../../src/fixtures/ui-test';

const TEST_CASE_ID = 'TC0620003BERSA';

test.describe('Alta de seguro de robo UI de Banco Entre Ríos', () => {
  test.use({ testCaseId: TEST_CASE_ID });

  test(`${TEST_CASE_ID} - llega a la confirmación de la solicitud de seguro`, async ({
    insurancePage,
    loginCredentials,
    loginPage,
    startPage,
  }) => {
    await startPage.open();
    await startPage.openLogin();
    await loginPage.authenticate(loginCredentials);

    await insurancePage.openRobberyInsuranceDetail();
    await insurancePage.continueRequest();
    await insurancePage.acceptTerms();

    expect(await insurancePage.isButtonShown('Confirmar'), 'Debe ofrecer Confirmar.').toBe(true);
    expect(await insurancePage.isButtonShown('Cancelar'), 'Debe ofrecer Cancelar.').toBe(true);
  });
});
