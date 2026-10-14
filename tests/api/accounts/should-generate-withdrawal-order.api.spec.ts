import { test } from '../../../src/fixtures/api-test';

const TEST_CASE_ID = 'TC0027BERSA';

test.describe('Orden de extracción API de Banco Entre Ríos', () => {
  test.use({ testCaseId: TEST_CASE_ID });

  test(`${TEST_CASE_ID} - genera una orden de extracción con saldo disponible`, async ({
    loginApi,
    loginCredentials,
    withdrawalOrderApi,
    withdrawalOrderData,
  }) => {
    const session = await test.step('Autenticar al usuario', () => loginApi.authenticate(loginCredentials));

    await test.step('Generar la orden de extracción', () =>
      withdrawalOrderApi.generate(session, loginCredentials.username, withdrawalOrderData));
  });
});
