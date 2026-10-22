import { ProductWidgetsApi } from '../../../src/api/accounts/product-widgets.api';
import { test } from '../../../src/fixtures/api-test';

const TEST_CASE_ID = 'TC1110004BERSA';

test.describe('DEBIN API de Banco Entre Ríos', () => {
  test.use({ testCaseId: TEST_CASE_ID });

  // El legado sólo valida que la aceptación responda con un código de éxito.
  test(`${TEST_CASE_ID} - Aceptación de DEBIN`, async ({
    debinOperationsApi,
    debitCardData,
    loginApi,
    loginCredentials,
    paymentLinkSyncApi,
    productWidgetsApi,
  }) => {
    const session = await test.step('Autenticar al usuario', () => loginApi.authenticate(loginCredentials));
    const products = await test.step('Consultar los productos del usuario', () => productWidgetsApi.load(session));
    const debitCard = ProductWidgetsApi.debitCard(products, debitCardData.debitCardLastDigits);
    await test.step('Sincronizar pagos Link', () => paymentLinkSyncApi.synchronize(session));
    const debinId = await test.step('Obtener el primer DEBIN pendiente', () =>
      debinOperationsApi.findFirstPendingDebinId(session));
    const details = await test.step('Consultar el detalle del DEBIN', () =>
      debinOperationsApi.readDetails(session, debinId));

    await test.step('Aceptar el DEBIN', () =>
      debinOperationsApi.accept(session, loginCredentials.username, details, debitCard.idProduct));
  });
});
