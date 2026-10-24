import { ProductWidgetsApi } from '../../../src/api/accounts/product-widgets.api';
import { expect, test } from '../../../src/fixtures/api-test';

const TEST_CASE_ID = 'TC-007-1-0008-UNE';

test.describe('Plazo fijo API de Banco Entre Ríos', () => {
  test.use({ testCaseId: TEST_CASE_ID });

  test(`${TEST_CASE_ID} - Plazo Fijo Tradicional en Dólares`, async ({
    loginApi,
    loginCredentials,
    originAccountData,
    productWidgetsApi,
    traditionalFixedTermApi,
  }) => {
    const session = await test.step('Autenticar al usuario', () => loginApi.authenticate(loginCredentials));
    const products = await test.step('Consultar los productos del usuario', () => productWidgetsApi.load(session));
    const accountId = ProductWidgetsApi.accountId(products, originAccountData.originAccount);
    // Condiciones del caso legado: USD, tipo D, 10001, 30 días y renovación T (usuarios Unisys).
    const result = await test.step('Constituir el plazo fijo en dólares', () =>
      traditionalFixedTermApi.createFixedTerm(session, accountId, {
        amount: 10001,
        currency: 'USD',
        fixedTermType: 'D',
        renewalType: 'T',
        term: '30',
      }));

    expect(result.wasAccepted, 'El plazo fijo en dólares debe ser aceptado por la API.').toBe(true);
  });
});
