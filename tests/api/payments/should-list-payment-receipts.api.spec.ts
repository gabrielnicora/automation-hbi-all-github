import { ProductWidgetsApi } from '../../../src/api/accounts/product-widgets.api';
import { test } from '../../../src/fixtures/api-test';
import { MERCADO_LIBRE_PAYMENT } from '../../../src/test-data/payment-services-data';

const TEST_CASE_ID = 'TC1110006BERSA';

test.describe('Comprobantes de pagos API de Banco Entre Ríos', () => {
  test.use({ testCaseId: TEST_CASE_ID });

  // El legado llama comprobantePagos("false", ""): su assertion de lista no vacía sólo corre con
  // isExport "", por lo que el caso valida únicamente el código de éxito (ver lote-l3-api.md).
  test(`${TEST_CASE_ID} - Pagos - Consulta de comprobantes`, async ({
    debitCardData,
    loginApi,
    loginCredentials,
    paymentLinkSyncApi,
    paymentReceiptsApi,
    productWidgetsApi,
  }) => {
    const session = await test.step('Autenticar al usuario', () => loginApi.authenticate(loginCredentials));
    const products = await test.step('Consultar los productos del usuario', () => productWidgetsApi.load(session));
    const debitCard = ProductWidgetsApi.debitCard(products, debitCardData.debitCardLastDigits);
    await test.step('Sincronizar pagos Link', () => paymentLinkSyncApi.synchronize(session));

    await test.step('Consultar los comprobantes de pagos', () =>
      paymentReceiptsApi.list(session, debitCard.idProduct, MERCADO_LIBRE_PAYMENT, {
        exportFormat: '',
        isExport: false,
      }));
  });
});
