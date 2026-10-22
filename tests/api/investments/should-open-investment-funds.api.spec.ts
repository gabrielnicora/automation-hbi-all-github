import { test } from '../../../src/fixtures/api-test';

const TEST_CASE_ID = 'TC0115BERSA';

test.describe('Fondos comunes de inversión API de Banco Entre Ríos', () => {
  test.use({ testCaseId: TEST_CASE_ID });

  // El legado sólo valida que la consulta de ingreso responda con un código de éxito.
  test(`${TEST_CASE_ID} - FCI - Simular API (inicio a la funcionalidad)`, async ({
    investmentFundsApi,
    loginApi,
    loginCredentials,
  }) => {
    const session = await test.step('Autenticar al usuario', () => loginApi.authenticate(loginCredentials));
    await test.step('Ingresar a la sección de fondos', () => investmentFundsApi.listFunds(session));
  });
});
