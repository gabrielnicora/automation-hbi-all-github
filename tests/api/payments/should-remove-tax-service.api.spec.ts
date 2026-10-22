import { ProductWidgetsApi } from '../../../src/api/accounts/product-widgets.api';
import { expect, test } from '../../../src/fixtures/api-test';
import { MERCADO_PAGO_SERVICE } from '../../../src/test-data/payment-services-data';

const TEST_CASE_ID = 'TC1110010BERSA';

test.describe('Pagos de servicios API de Banco Entre Ríos', () => {
  test.use({ testCaseId: TEST_CASE_ID });

  test(`${TEST_CASE_ID} - Pagar impuesto/servicio - Eliminar servicio`, async ({
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

    // Precondición legada: si el servicio no está adherido, se adhiere para poder eliminarlo.
    const adhesionCode = await test.step('Obtener la adhesión del servicio', async () => {
      const existing = await taxServicesApi.findAdhesionCode(session, debitCard.idProduct, MERCADO_PAGO_SERVICE);

      if (existing) {
        return existing;
      }

      await taxServicesApi.adhere(session, loginCredentials.username, debitCard, MERCADO_PAGO_SERVICE);
      return taxServicesApi.findAdhesionCode(session, debitCard.idProduct, MERCADO_PAGO_SERVICE);
    });

    expect(adhesionCode, 'Debe existir la adhesión del servicio Mercado Pago para eliminarla.').toBeTruthy();

    await test.step('Eliminar el servicio adherido', () =>
      taxServicesApi.unsubscribe(session, debitCard.idProduct, String(adhesionCode), MERCADO_PAGO_SERVICE));
  });
});
