import { expect, test } from '../../../src/fixtures/ui-test';

const TEST_CASE_ID = 'TC-001-2-0003-UNE';

test.describe('Consulta de movimientos de cuenta UI de Banco Entre Ríos', () => {
  test.use({ testCaseId: TEST_CASE_ID });

  test(`${TEST_CASE_ID} - filtra movimientos por tipo de transacción en una cuenta`, async ({
    accountMovementsPage,
    accountMovementsUiData,
    loginCredentials,
    loginPage,
    startPage,
  }) => {
    await startPage.open();
    await startPage.openLogin();
    await loginPage.authenticate(loginCredentials);

    await accountMovementsPage.openAccountDetails(accountMovementsUiData.account);
    await accountMovementsPage.applyCreationDateFilter(2, '8');
    await accountMovementsPage.filterByTransactionType();

    expect(await accountMovementsPage.hasMovement()).toBe(true);
  });
});
