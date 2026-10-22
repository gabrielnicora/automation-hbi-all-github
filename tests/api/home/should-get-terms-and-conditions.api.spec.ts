import { expect, test } from '../../../src/fixtures/api-test';

const TEST_CASE_ID = 'TC0510001BERSA';

test.describe('Pantalla principal API de Banco Entre Ríos', () => {
  test.use({ testCaseId: TEST_CASE_ID });

  test(`${TEST_CASE_ID} - obtiene los términos y condiciones generales`, async ({ termsAndConditionsApi }) => {
    const result = await test.step('Consultar los términos y condiciones generales', () =>
      termsAndConditionsApi.getGeneralConditions());

    expect(result.hasContent, 'Los términos y condiciones generales deben contener texto.').toBe(true);
  });
});
