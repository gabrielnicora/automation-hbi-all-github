import { ProductWidgetsApi } from '../../../src/api/accounts/product-widgets.api';
import { test } from '../../../src/fixtures/api-test';

const TEST_CASE_ID = 'TC0165BERSA';
// Importe fijo del legado TarjetaSteps.realizarPagoOtroImporte.
const OTHER_AMOUNT = 1;

test.describe('Pago de tarjeta de crédito API de Banco Entre Ríos', () => {
  test.use({ testCaseId: TEST_CASE_ID });

  // El legado sólo valida que el pago responda con un código de éxito.
  test(`${TEST_CASE_ID} - Pagar tarjeta de crédito Master - Otro importe`, async ({
    creditCardPaymentData,
    creditCardPaymentsApi,
    loginApi,
    loginCredentials,
    productWidgetsApi,
  }) => {
    const session = await test.step('Autenticar al usuario', () => loginApi.authenticate(loginCredentials));
    const products = await test.step('Consultar los productos del usuario', () => productWidgetsApi.load(session));

    await test.step('Pagar otro importe de la tarjeta', () =>
      creditCardPaymentsApi.pay(
        session,
        {
          accountId: ProductWidgetsApi.accountId(products, creditCardPaymentData.originAccount),
          creditCardId: ProductWidgetsApi.creditCardId(products, creditCardPaymentData.creditCardLastDigits),
          debitCardId: ProductWidgetsApi.debitCard(products, creditCardPaymentData.debitCardLastDigits).idProduct,
        },
        OTHER_AMOUNT,
      ));
  });
});
