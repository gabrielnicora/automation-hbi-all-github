import { expect, test } from '../../../src/fixtures/api-test';

const TEST_CASE_ID = 'TC0042BERSA';

test.describe('Inversiones API de Banco Entre Ríos', () => {
  test.use({ testCaseId: TEST_CASE_ID });

  test(`${TEST_CASE_ID} - constituye un plazo fijo tradicional en ARS a 30 días`, async ({
    loginApi,
    loginCredentials,
    traditionalFixedTermApi,
    traditionalFixedTermData,
  }) => {
    const session = await test.step('Autenticar al usuario', () => loginApi.authenticate(loginCredentials));
    const result = await test.step('Constituir el plazo fijo tradicional', () =>
      traditionalFixedTermApi.create(session, traditionalFixedTermData));

    expect(result.wasAccepted, 'El plazo fijo tradicional debe ser aceptado por la API.').toBe(true);
  });
});
