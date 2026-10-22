import { expect, test } from '../../../src/fixtures/api-test';

const TEST_CASE_ID = 'TC1110002BERSA';

test.describe('Pagos API de Banco Entre Ríos', () => {
  test.use({ testCaseId: TEST_CASE_ID });

  test(`${TEST_CASE_ID} - genera una orden DEBIN seleccionando CBU`, async ({
    debinGenerationApi,
    debinGenerationData,
    loginApi,
    loginCredentials,
  }) => {
    const session = await test.step('Autenticar al usuario', () => loginApi.authenticate(loginCredentials));
    const result = await test.step('Generar la orden DEBIN', () =>
      debinGenerationApi.generate(session, debinGenerationData));

    expect(result.wasAccepted, 'La orden DEBIN debe ser aceptada por la API.').toBe(true);
  });
});
