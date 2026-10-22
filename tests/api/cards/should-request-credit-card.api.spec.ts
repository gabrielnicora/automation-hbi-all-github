import { ProductWidgetsApi } from '../../../src/api/accounts/product-widgets.api';
import { expect, test } from '../../../src/fixtures/api-test';

const TEST_CASE_ID = 'TC0149BERSA';

test.describe('Solicitud de tarjeta de crédito API de Banco Entre Ríos', () => {
  test.use({ testCaseId: TEST_CASE_ID });

  test(`${TEST_CASE_ID} - Tarjeta de Crédito - Solicitar nueva tarjeta de crédito`, async ({
    creditCardApplicationApi,
    loginApi,
    loginCredentials,
    optionalOriginAccountData,
    paymentLinkSyncApi,
    productWidgetsApi,
  }) => {
    const session = await test.step('Autenticar al usuario', () => loginApi.authenticate(loginCredentials));
    const products = await test.step('Consultar los productos del usuario', () => productWidgetsApi.load(session));
    // Legado: idProduct de ctaOrigen sin validar (vacío si el registro no informa cuenta).
    const debitIdProduct = ProductWidgetsApi.legacyAccountIdOrEmpty(products, optionalOriginAccountData.originAccount);
    await test.step('Sincronizar pagos Link', () => paymentLinkSyncApi.synchronize(session));
    const offers = await test.step('Consultar las ofertas de tarjeta', () =>
      creditCardApplicationApi.readOffers(session));

    expect(offers.offersCount, 'El usuario debe tener ofertas de tarjeta de crédito.').toBeGreaterThan(0);

    await test.step('Crear la solicitud de tarjeta', () =>
      creditCardApplicationApi.create(session, offers.firstOffer!, debitIdProduct));
  });
});
