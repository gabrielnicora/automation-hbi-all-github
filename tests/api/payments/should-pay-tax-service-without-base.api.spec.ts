import { ProductWidgetsApi } from '../../../src/api/accounts/product-widgets.api';
import { test } from '../../../src/fixtures/api-test';
import { MERCADO_LIBRE_PAYMENT } from '../../../src/test-data/payment-services-data';

const TEST_CASE_ID = 'TC12964BERSA';

test.describe('Pagos de servicios API de Banco Entre Ríos', () => {
  test.use({ testCaseId: TEST_CASE_ID });

  // El legado sólo valida que el pago responda con un código de éxito.
  test(`${TEST_CASE_ID} - Pagar impuesto/servicio nuevo pago (sin base)`, async ({
    debitCardData,
    loginApi,
    loginCredentials,
    originAccountData,
    paymentLinkSyncApi,
    productWidgetsApi,
    taxServicesApi,
  }) => {
    const session = await test.step('Autenticar al usuario', () => loginApi.authenticate(loginCredentials));
    const products = await test.step('Consultar los productos del usuario', () => productWidgetsApi.load(session));
    const accountId = ProductWidgetsApi.accountId(products, originAccountData.originAccount);
    const debitCard = ProductWidgetsApi.debitCard(products, debitCardData.debitCardLastDigits);
    await test.step('Sincronizar pagos Link', () => paymentLinkSyncApi.synchronize(session));
    const prepared = await test.step('Preparar el pago sin base', () =>
      taxServicesApi.prepareWithoutBasePayment(
        session,
        loginCredentials.username,
        debitCard.idProduct,
        MERCADO_LIBRE_PAYMENT,
      ));

    await test.step('Pagar el servicio', () =>
      taxServicesApi.payWithoutBase(session, accountId, debitCard.idProduct, prepared, MERCADO_LIBRE_PAYMENT));
  });
});
