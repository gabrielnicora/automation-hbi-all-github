import { expect, test } from '../../../src/fixtures/api-test';

const TEST_CASE_ID = 'TC1110001BERSA';

test.describe('DEBIN API de Banco Entre Ríos', () => {
  test.use({ testCaseId: TEST_CASE_ID });

  test(`${TEST_CASE_ID} - DEBIN Comprador - Consulta DEBINes históricos - Listado`, async ({
    debinOperationsApi,
    loginApi,
    loginCredentials,
    paymentLinkSyncApi,
  }) => {
    const session = await test.step('Autenticar al usuario', () => loginApi.authenticate(loginCredentials));
    await test.step('Sincronizar pagos Link', () => paymentLinkSyncApi.synchronize(session));
    const historyCount = await test.step('Consultar DEBINes históricos', () =>
      debinOperationsApi.countHistory(session));

    expect(historyCount, 'La consulta debe encontrar DEBINes históricos.').toBeGreaterThan(0);
  });
});
