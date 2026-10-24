import { ProductWidgetsApi } from '../../../src/api/accounts/product-widgets.api';
import { test } from '../../../src/fixtures/api-test';

// El legado sólo valida que la operación responda con un código de éxito.
test.describe('Compra y venta de dólares API de Banco Entre Ríos', () => {
  test.describe('TC-007-1-0003-UNE', () => {
    test.use({ testCaseId: 'TC-007-1-0003-UNE' });

    test('TC-007-1-0003-UNE - Compra de moneda extranjera (USD)', async ({
      accountPairData,
      dollarExchangeApi,
      loginApi,
      loginCredentials,
      productWidgetsApi,
    }) => {
      const session = await test.step('Autenticar al usuario', () => loginApi.authenticate(loginCredentials));
      const products = await test.step('Consultar los productos del usuario', () => productWidgetsApi.load(session));

      // Legado: la cuenta origen del caso es la de pesos y la destino la de dólares; importe "10".
      await test.step('Comprar dólares', () =>
        dollarExchangeApi.buy(session, {
          amount: '10',
          dollarProductId: ProductWidgetsApi.accountId(products, accountPairData.destinationAccount),
          pesosProductId: ProductWidgetsApi.accountId(products, accountPairData.originAccount),
        }));
    });
  });

  test.describe('TC-007-1-0001-UNE', () => {
    test.use({ testCaseId: 'TC-007-1-0001-UNE' });

    test('TC-007-1-0001-UNE - Venta de moneda extranjera (USD)', async ({
      accountPairData,
      dollarExchangeApi,
      loginApi,
      loginCredentials,
      productWidgetsApi,
    }) => {
      const session = await test.step('Autenticar al usuario', () => loginApi.authenticate(loginCredentials));
      const products = await test.step('Consultar los productos del usuario', () => productWidgetsApi.load(session));

      // Legado: la cuenta origen del caso es la de dólares y la destino la de pesos; importe 100.
      await test.step('Vender dólares', () =>
        dollarExchangeApi.sell(session, {
          amount: 100,
          dollarProductId: ProductWidgetsApi.accountId(products, accountPairData.originAccount),
          pesosProductId: ProductWidgetsApi.accountId(products, accountPairData.destinationAccount),
        }));
    });
  });
});
