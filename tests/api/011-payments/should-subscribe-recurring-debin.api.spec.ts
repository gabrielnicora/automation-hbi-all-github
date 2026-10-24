import { ProductWidgetsApi } from '../../../src/api/accounts/product-widgets.api';
import { test } from '../../../src/fixtures/api-test';
import { RECURRING_DEBIN_SUBSCRIPTION } from '../../../src/test-data/payment-services-data';

const TEST_CASE_ID = 'TC-011-1-0003-UNE';

test.describe('DEBIN API de Banco Entre Ríos', () => {
  test.use({ testCaseId: TEST_CASE_ID });

  // El legado sólo valida que la adhesión responda con un código de éxito.
  test(`${TEST_CASE_ID} - Adherir DEBIN recurrente (Pesos) - segundo factor por SMS`, async ({
    debinOperationsApi,
    loginApi,
    loginCredentials,
    originAccountData,
    paymentLinkSyncApi,
    productWidgetsApi,
  }) => {
    const session = await test.step('Autenticar al usuario', () => loginApi.authenticate(loginCredentials));
    const products = await test.step('Consultar los productos del usuario', () => productWidgetsApi.load(session));
    const accountId = ProductWidgetsApi.accountId(products, originAccountData.originAccount);
    await test.step('Sincronizar pagos Link', () => paymentLinkSyncApi.synchronize(session));

    await test.step('Adherir el DEBIN recurrente', () =>
      debinOperationsApi.subscribeRecurrency(
        session,
        loginCredentials.username,
        accountId,
        RECURRING_DEBIN_SUBSCRIPTION,
      ));
  });
});
