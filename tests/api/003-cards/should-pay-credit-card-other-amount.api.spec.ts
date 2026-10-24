import { expect, test } from '../../../src/fixtures/api-test';

const TEST_CASE_ID = 'TC-003-1-0007-UNE';

test.describe('Tarjetas API de Banco Entre Ríos', () => {
  test.use({ testCaseId: TEST_CASE_ID });

  test(
    `${TEST_CASE_ID} - paga ARS 1 de una tarjeta Visa por otro importe`,
    { tag: '@regression' },
    async ({ creditCardOtherAmountPaymentApi, creditCardOtherAmountPaymentData, loginApi, loginCredentials }) => {
      const session = await test.step('Autenticar al usuario', () => loginApi.authenticate(loginCredentials));
      const result = await test.step('Pagar otro importe de la tarjeta Visa', () =>
        creditCardOtherAmountPaymentApi.pay(session, creditCardOtherAmountPaymentData));

      expect(result.wasAccepted, 'El pago de otro importe debe ser aceptado por la API.').toBe(true);
    },
  );
});
