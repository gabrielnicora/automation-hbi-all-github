import { ProductWidgetsApi } from '../../../src/api/accounts/product-widgets.api';
import { test } from '../../../src/fixtures/api-test';
import { MERCADO_PAGO_SERVICE } from '../../../src/test-data/payment-services-data';

const TEST_CASE_ID = 'TC0074BERSA';

test.describe('Pagos de servicios API de Banco Entre Ríos', () => {
  test.use({ testCaseId: TEST_CASE_ID });

  // El legado sólo valida que la adhesión responda con un código de éxito.
  test(`${TEST_CASE_ID} - Adherir nuevo pago o servicio`, async ({
    debitCardData,
    loginApi,
    loginCredentials,
    paymentLinkSyncApi,
    productWidgetsApi,
    taxServicesApi,
  }) => {
    const session = await test.step('Autenticar al usuario', () => loginApi.authenticate(loginCredentials));
    const products = await test.step('Consultar los productos del usuario', () => productWidgetsApi.load(session));
    const debitCard = ProductWidgetsApi.debitCard(products, debitCardData.debitCardLastDigits);
    await test.step('Sincronizar pagos Link', () => paymentLinkSyncApi.synchronize(session));

    await test.step('Adherir el servicio Mercado Pago', () =>
      taxServicesApi.adhere(session, loginCredentials.username, debitCard, MERCADO_PAGO_SERVICE));
  });
});
