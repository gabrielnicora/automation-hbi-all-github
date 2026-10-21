import { ProductWidgetsApi } from '../../../src/api/accounts/product-widgets.api';
import { test } from '../../../src/fixtures/api-test';

const MINIMUM_PAYMENTS = [
  {
    id: 'TC16039BERSA',
    title: 'Tarjetas de crédito - Pagar el importe mínimo de una tarjeta de crédito Mastercard',
  },
  { id: 'TC0164BERSA', title: 'Tarjeta de Crédito - Pagar mínimo de una Tarjeta de Crédito Visa' },
] as const;

// El legado sólo valida que la consulta del mínimo y el pago respondan con un código de éxito.
test.describe('Pago mínimo de tarjeta de crédito API de Banco Entre Ríos', () => {
  for (const minimumPayment of MINIMUM_PAYMENTS) {
    test.describe(minimumPayment.id, () => {
      test.use({ testCaseId: minimumPayment.id });

      test(`${minimumPayment.id} - ${minimumPayment.title}`, async ({
        creditCardPaymentData,
        creditCardPaymentsApi,
        loginApi,
        loginCredentials,
        productWidgetsApi,
      }) => {
        const session = await test.step('Autenticar al usuario', () => loginApi.authenticate(loginCredentials));
        const products = await test.step('Consultar los productos del usuario', () => productWidgetsApi.load(session));
        const paymentProducts = {
          accountId: ProductWidgetsApi.accountId(products, creditCardPaymentData.originAccount),
          creditCardId: ProductWidgetsApi.creditCardId(products, creditCardPaymentData.creditCardLastDigits),
          debitCardId: ProductWidgetsApi.debitCard(products, creditCardPaymentData.debitCardLastDigits).idProduct,
        };
        const minimumAmount = await test.step('Consultar el pago mínimo', () =>
          creditCardPaymentsApi.readMinimumPaymentArs(session, paymentProducts.creditCardId));

        await test.step('Pagar el mínimo de la tarjeta', () =>
          creditCardPaymentsApi.pay(session, paymentProducts, minimumAmount));
      });
    });
  }
});
