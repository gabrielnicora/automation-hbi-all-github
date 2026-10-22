import { expect, test } from '../../../src/fixtures/ui-test';

const NEW_ACCOUNT_CASES = [
  { testCaseId: 'TC0120011BERSA', accountType: 'Caja de Ahorro', currency: 'Pesos' },
  { testCaseId: 'TC0120012BERSA', accountType: 'Caja de Ahorro', currency: 'Dólares' },
  { testCaseId: 'TC0120013BERSA', accountType: 'Cuenta Corriente', currency: 'Pesos' },
] as const;

for (const { testCaseId, accountType, currency } of NEW_ACCOUNT_CASES) {
  test.describe(`Solicitud de nueva cuenta ${accountType} en ${currency} UI de Banco Entre Ríos`, () => {
    test.use({ testCaseId });

    test(`${testCaseId} - habilita Continuar al elegir ${accountType} en ${currency}`, async ({
      accountsPage,
      loginCredentials,
      loginPage,
      startPage,
    }) => {
      await startPage.open();
      await startPage.openLogin();
      await loginPage.authenticate(loginCredentials);

      await accountsPage.open();
      await accountsPage.startNewAccountRequest();
      expect(await accountsPage.isContinueEnabled(), 'Sin tipo ni moneda, Continuar debe estar deshabilitado.').toBe(
        false,
      );

      await accountsPage.selectAccountType(accountType);
      await accountsPage.selectAccountCurrency(currency);

      expect(await accountsPage.isContinueShownDisabled(), 'Con tipo y moneda, Continuar debe habilitarse.').toBe(
        false,
      );
    });
  });
}
