import { expect, test } from '../../../src/fixtures/ui-test';

// Formulario de compra/venta de dólares (legado CompraVentaDeDolaresSteps). Reutiliza la navegación
// del testigo TC0720011BERSA: Inversiones → Gestioná tus inversiones → Compra venta de dólares.
const TERMS_REQUIRED_MESSAGE = 'Leer y aceptar los términos y condiciones es obligatorio para continuar';

test.describe('Compra y venta de dólares por UI', () => {
  const WITHOUT_TERMS = [
    {
      id: 'TC0720012BERSA',
      title: 'Compra de moneda extranjera - Seguir flujo de compra de dólar sin aceptar check',
      operation: 'Comprar',
      destination: 'USD',
    },
    {
      id: 'TC0720016BERSA',
      title: 'Venta de moneda extranjera - Seguir flujo de venta de dólar sin aceptar check',
      operation: 'Vender',
      destination: '$',
    },
  ] as const;

  for (const formCase of WITHOUT_TERMS) {
    test.describe(formCase.id, () => {
      test.use({ testCaseId: formCase.id });

      test(`${formCase.id} - Inversiones - ${formCase.title}`, async ({
        foreignCurrencyPurchasePage,
        loginCredentials,
        loginPage,
        originAccountData,
        startPage,
      }) => {
        await test.step('Autenticar al usuario', async () => {
          await startPage.open();
          await startPage.openLogin();
          await loginPage.authenticate(loginCredentials);
        });
        await test.step('Completar la operación de 10', async () => {
          await foreignCurrencyPurchasePage.open();
          await foreignCurrencyPurchasePage.selectOperationType(formCase.operation);
          await foreignCurrencyPurchasePage.selectOriginAccount(originAccountData.originAccount);
          await foreignCurrencyPurchasePage.selectDestinationAccount(formCase.destination);
          await foreignCurrencyPurchasePage.typeAmount('10');
          await foreignCurrencyPurchasePage.pressContinue();
          await foreignCurrencyPurchasePage.pressConfirm();
        });

        expect(
          await foreignCurrencyPurchasePage.isMessageShown(TERMS_REQUIRED_MESSAGE),
          'Debe exigirse aceptar los términos y condiciones.',
        ).toBe(true);
      });
    });
  }

  test.describe('TC0720015BERSA', () => {
    test.use({ testCaseId: 'TC0720015BERSA' });

    test('TC0720015BERSA - Inversiones - Venta de moneda extranjera - Importe 0 - Validar mensaje', async ({
      foreignCurrencyPurchasePage,
      loginCredentials,
      loginPage,
      originAccountData,
      startPage,
    }) => {
      await test.step('Autenticar al usuario', async () => {
        await startPage.open();
        await startPage.openLogin();
        await loginPage.authenticate(loginCredentials);
      });
      await test.step('Completar la venta con importe 0', async () => {
        await foreignCurrencyPurchasePage.open();
        await foreignCurrencyPurchasePage.selectOperationType('Vender');
        await foreignCurrencyPurchasePage.selectOriginAccount(originAccountData.originAccount);
        await foreignCurrencyPurchasePage.selectDestinationAccount('$');
        await foreignCurrencyPurchasePage.typeAmount('0');
        await foreignCurrencyPurchasePage.pressContinue();
      });

      expect(
        await foreignCurrencyPurchasePage.isMessageShown('El monto ingresado debe ser mayor a 0'),
        'Debe rechazarse el importe 0.',
      ).toBe(true);
    });
  });

  // El legado recorre el desplegable y exige que muestre una opción con la moneda indicada.
  const ACCOUNT_OPTIONS = [
    {
      id: 'TC0720013BERSA',
      title: 'Compra - Desplegable Cuenta Origen sólo cuentas en pesos',
      operation: 'Comprar',
      field: 'origin',
      currency: '$',
    },
    {
      id: 'TC0720014BERSA',
      title: 'Compra - Desplegable Cuenta Destino sólo cuentas en dólares',
      operation: 'Comprar',
      field: 'destination',
      currency: 'USD',
    },
    {
      id: 'TC0720017BERSA',
      title: 'Venta - Desplegable Cuenta Destino sólo cuentas en pesos',
      operation: 'Vender',
      field: 'destination',
      currency: '$',
    },
    {
      id: 'TC0720018BERSA',
      title: 'Venta - Desplegable Cuenta Origen sólo cuentas en dólares',
      operation: 'Vender',
      field: 'origin',
      currency: 'USD',
    },
  ] as const;

  for (const optionsCase of ACCOUNT_OPTIONS) {
    test.describe(optionsCase.id, () => {
      test.use({ testCaseId: optionsCase.id });

      test(`${optionsCase.id} - Inversiones - ${optionsCase.title}`, async ({
        foreignCurrencyPurchasePage,
        loginCredentials,
        loginPage,
        startPage,
      }) => {
        await test.step('Autenticar al usuario', async () => {
          await startPage.open();
          await startPage.openLogin();
          await loginPage.authenticate(loginCredentials);
        });
        await test.step(`Elegir la operación ${optionsCase.operation}`, async () => {
          await foreignCurrencyPurchasePage.open();
          await foreignCurrencyPurchasePage.selectOperationType(optionsCase.operation);
        });

        expect(
          await foreignCurrencyPurchasePage.accountOptionsInclude(optionsCase.field, optionsCase.currency),
          `El desplegable debe ofrecer cuentas en ${optionsCase.currency}.`,
        ).toBe(true);
      });
    });
  }
});
