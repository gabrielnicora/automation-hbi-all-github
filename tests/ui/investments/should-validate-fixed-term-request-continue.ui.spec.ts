import { expect, test } from '../../../src/fixtures/ui-test';

const TEST_CASE_ID = 'TC128244BERSA';

/**
 * Importe que carga el legado a partir del saldo mostrado (PlazosFijosSteps + UtilsGP.parseMontoAR):
 * toma el saldo "1.234,56", le suma 100000 y quita el punto decimal ("10123456"). Supera el saldo.
 */
function legacyAmountFromBalance(accountText: string, currencyPrefix: '$' | 'USD'): string {
  const pattern = currencyPrefix === '$' ? /^\$\s*([0-9.]+,[0-9]{2})\s*$/m : /^USD\s*([0-9.]+,[0-9]{2})\s*$/m;
  const balance = pattern.exec(accountText)?.[1];

  if (!balance) {
    throw new Error(`No se encontró el saldo en ${currencyPrefix} de la cuenta débito seleccionada.`);
  }

  const cents = BigInt(balance.replace(/\./g, '').replace(',', ''));
  return String(cents + 10_000_000n);
}

test.describe('Plazo fijo - control del botón Continuar por UI', () => {
  test.use({ testCaseId: TEST_CASE_ID });

  test(`${TEST_CASE_ID} - Web - Alta de PF - Verificar lógica del botón Continuar y mensaje de error en Simulación`, async ({
    accountPairData,
    fixedTermsPage,
    loginCredentials,
    loginPage,
    startPage,
  }) => {
    const pesosAccount = accountPairData.originAccount;
    const dollarAccount = accountPairData.destinationAccount;

    await test.step('Autenticar al usuario', async () => {
      await startPage.open();
      await startPage.openLogin();
      await loginPage.authenticate(loginCredentials);
    });
    await test.step('Abrir la solicitud de plazo fijo', async () => {
      await fixedTermsPage.open();
      await fixedTermsPage.openRequest();
    });

    expect(await fixedTermsPage.isContinueEnabled(), 'Continuar debe iniciar deshabilitado.').toBe(false);

    await test.step('Completar plazo fijo tradicional en pesos', async () => {
      await fixedTermsPage.selectDebitAccount(pesosAccount);
      const amount = legacyAmountFromBalance(await fixedTermsPage.readSelectedDebitAccountText(), '$');
      await fixedTermsPage.selectType('Plazo Fijo Tradicional en P');
      await fixedTermsPage.typeAmount(amount);
      await fixedTermsPage.typeTermDays('180');
      await fixedTermsPage.selectMaturityAction('al vencimiento');
    });

    expect(await fixedTermsPage.isContinueEnabled(), 'Continuar debe habilitarse (pesos).').toBe(true);

    await test.step('Completar plazo fijo tradicional en dólares', async () => {
      await fixedTermsPage.goBack();
      await fixedTermsPage.openRequest();
      await fixedTermsPage.selectType('Plazo Fijo Tradicional en D');
      await fixedTermsPage.selectDebitAccount(dollarAccount);
      const amount = legacyAmountFromBalance(await fixedTermsPage.readSelectedDebitAccountText(), 'USD');
      await fixedTermsPage.typeAmount(amount);
      await fixedTermsPage.typeTermDays('180');
      await fixedTermsPage.selectMaturityAction('al vencimiento');
    });

    expect(await fixedTermsPage.isContinueEnabled(), 'Continuar debe habilitarse (dólares).').toBe(true);

    await test.step('Completar plazo fijo UVA', async () => {
      await fixedTermsPage.goBack();
      await fixedTermsPage.openRequest();
      await fixedTermsPage.selectUvaType();
      await fixedTermsPage.selectDebitAccount(pesosAccount);
      const amount = legacyAmountFromBalance(await fixedTermsPage.readSelectedDebitAccountText(), '$');
      await fixedTermsPage.typeAmount(amount);
      await fixedTermsPage.typeTermDays('180');
      await fixedTermsPage.selectMaturityAction('al vencimiento');
    });

    expect(await fixedTermsPage.isContinueEnabled(), 'Continuar debe habilitarse (UVA).').toBe(true);

    await test.step('Completar plazo fijo UVA precancelable', async () => {
      await fixedTermsPage.goBack();
      await fixedTermsPage.openRequest();
      await fixedTermsPage.selectType('Plazo Fijo UVA Precancelable');
      await fixedTermsPage.selectDebitAccount(pesosAccount);
      const amount = legacyAmountFromBalance(await fixedTermsPage.readSelectedDebitAccountText(), '$');
      await fixedTermsPage.typeAmount(amount);
      await fixedTermsPage.typeTermDays('180');
      await fixedTermsPage.selectMaturityAction('al vencimiento');
    });

    expect(await fixedTermsPage.isContinueEnabled(), 'Continuar debe habilitarse (UVA precancelable).').toBe(true);

    const exceedingAmount = await test.step('Simular con importe mayor al saldo', async () => {
      await fixedTermsPage.goBack();
      await fixedTermsPage.openRequest();
      await fixedTermsPage.selectDebitAccount(pesosAccount);
      const amount = legacyAmountFromBalance(await fixedTermsPage.readSelectedDebitAccountText(), '$');
      await fixedTermsPage.selectType('Plazo Fijo Tradicional en P');
      await fixedTermsPage.typeAmount(amount);
      await fixedTermsPage.typeTermDays('180');
      await fixedTermsPage.selectMaturityAction('al vencimiento');
      await fixedTermsPage.pressContinue();
      return amount;
    });

    expect(await fixedTermsPage.isContinueEnabled(), 'Continuar debe deshabilitarse si el monto supera el saldo.').toBe(
      false,
    );
    expect(await fixedTermsPage.isFormErrorShown(), 'Debe mostrarse el mensaje de error del monto.').toBe(true);

    await test.step('Reducir el importe en 4.000.000', async () => {
      await fixedTermsPage.clearAmount();
      await fixedTermsPage.typeAmount(String(BigInt(exceedingAmount) - 4_000_000n));
    });

    expect(await fixedTermsPage.isContinueEnabled(), 'Continuar debe habilitarse con el importe modificado.').toBe(
      true,
    );
  });
});
