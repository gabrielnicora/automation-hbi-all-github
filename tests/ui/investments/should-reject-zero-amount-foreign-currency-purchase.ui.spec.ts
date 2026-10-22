import { expect, test } from '../../../src/fixtures/ui-test';

const TEST_CASE_ID = 'TC0720011BERSA';

test.describe('Compra de moneda extranjera por UI', () => {
  test.use({ testCaseId: TEST_CASE_ID });

  test(`${TEST_CASE_ID} - rechaza la compra de dólares con importe cero`, async ({
    foreignCurrencyPurchasePage,
    foreignCurrencyPurchaseUiData,
    loginCredentials,
    loginPage,
    startPage,
  }) => {
    await startPage.open();
    await startPage.openLogin();
    await loginPage.authenticate(loginCredentials);
    await foreignCurrencyPurchasePage.open();
    await foreignCurrencyPurchasePage.completeForm({
      operationType: 'Comprar dólares',
      originAccount: foreignCurrencyPurchaseUiData.originAccount,
      destinationAccount: foreignCurrencyPurchaseUiData.destinationAccount,
      amount: '0',
    });
    await foreignCurrencyPurchasePage.continue();

    expect(await foreignCurrencyPurchasePage.readValidationMessage()).toBe('El monto ingresado debe ser mayor a 0');
  });
});
