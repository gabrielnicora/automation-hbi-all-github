import { expect, test } from '../../../src/fixtures/ui-test';

const TEST_CASE_ID = 'TC0120002BERSA';
const DETAIL_TEXTS = ['Fecha', 'Concepto', 'Monto'];

test.describe('Detalle de movimiento de cuenta UI de Banco Entre Ríos', () => {
  test.use({ testCaseId: TEST_CASE_ID });

  test(`${TEST_CASE_ID} - muestra fecha, concepto y monto del primer movimiento`, async ({
    accountsPage,
    loginCredentials,
    loginPage,
    originAccountUiData,
    startPage,
  }) => {
    await startPage.open();
    await startPage.openLogin();
    await loginPage.authenticate(loginCredentials);

    await accountsPage.open();
    await accountsPage.openAccountDetail(originAccountUiData.originAccount);
    await accountsPage.openMovementDetail(1);

    for (const text of DETAIL_TEXTS) {
      expect(await accountsPage.isTextShown(text), `El detalle debe mostrar "${text}".`).toBe(true);
    }
  });
});
