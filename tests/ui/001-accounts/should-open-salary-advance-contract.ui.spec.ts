import { expect, test } from '../../../src/fixtures/ui-test';
import { expectSalaryAdvanceRequestScreen } from './salary-advance-request-screen';

test.describe('Resumen de contrato de adelanto de sueldo UI de Banco Entre Ríos', () => {
  const TEST_CASE_ID = 'TC-001-2-0007-UNE';
  test.use({ testCaseId: TEST_CASE_ID });
  test.setTimeout(180_000);

  test(`${TEST_CASE_ID} - redirige al documento del contrato al aceptar`, async ({
    loginCredentials,
    loginPage,
    originAccountUiData,
    salaryAdvancePage,
    startPage,
  }) => {
    await startPage.open();
    await startPage.openLogin();
    await loginPage.authenticate(loginCredentials);

    await salaryAdvancePage.openRequest(originAccountUiData.originAccount);
    await salaryAdvancePage.openContractSummary();
    const contractPage = await salaryAdvancePage.acceptContractRedirect();

    expect(contractPage.url).toContain('assets');
  });
});

test.describe('Cancelación de redirección al contrato UI de Banco Entre Ríos', () => {
  const TEST_CASE_ID = 'TC-001-2-0008-UNE';
  test.use({ testCaseId: TEST_CASE_ID });
  test.setTimeout(180_000);

  test(`${TEST_CASE_ID} - vuelve a la solicitud de adelanto al cancelar la redirección`, async ({
    loginCredentials,
    loginPage,
    originAccountUiData,
    salaryAdvancePage,
    startPage,
  }) => {
    await startPage.open();
    await startPage.openLogin();
    await loginPage.authenticate(loginCredentials);

    await salaryAdvancePage.openRequest(originAccountUiData.originAccount);
    await salaryAdvancePage.openContractSummary();
    await salaryAdvancePage.cancelContractRedirect();

    await expectSalaryAdvanceRequestScreen(salaryAdvancePage);
  });
});
