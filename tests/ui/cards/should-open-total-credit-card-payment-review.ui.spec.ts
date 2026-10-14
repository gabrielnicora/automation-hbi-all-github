import { expect, test } from '../../../src/fixtures/ui-test';

const TEST_CASE_ID = 'TC0163BERSA';
const TOTAL_PAYMENT = 'Pagar el total';
const PAYMENT_REVIEW_FIELDS = [
  'Alias por defecto',
  'Saldo en pesos',
  'Pago m',
  'Vencimiento',
  'Cuenta d',
  'Tipo de pago',
  'Importe a pagar',
] as const;

test.describe('Pago UI de tarjeta de crédito de Banco Entre Ríos', () => {
  test.use({ testCaseId: TEST_CASE_ID });

  test(`${TEST_CASE_ID} - muestra la revisión del pago total de una tarjeta Mastercard propia`, async ({
    creditCardPaymentPage,
    creditCardTotalPaymentUiData,
    loginCredentials,
    loginPage,
    startPage,
  }) => {
    await test.step('Autenticar al usuario', async () => {
      await startPage.open();
      await startPage.openLogin();
      await loginPage.authenticate(loginCredentials);
    });

    await test.step('Configurar el pago total de la tarjeta Mastercard', async () => {
      await creditCardPaymentPage.open();
      await creditCardPaymentPage.openOwnCardPayment(creditCardTotalPaymentUiData.creditCard);
      await creditCardPaymentPage.selectDebitCard(creditCardTotalPaymentUiData.debitCardLastFour);
      await creditCardPaymentPage.continueToPaymentDetails();
      await creditCardPaymentPage.selectDebitAccount(creditCardTotalPaymentUiData.originAccount);
      await creditCardPaymentPage.selectPaymentType(TOTAL_PAYMENT);
      await creditCardPaymentPage.openPaymentReview();
    });

    const review = await test.step('Leer la pantalla de revisión de pago', () =>
      creditCardPaymentPage.readPaymentReview());
    for (const field of PAYMENT_REVIEW_FIELDS) {
      expect(review, 'La revisión debe mostrar todos los campos de pago esperados.').toContain(field);
    }
  });
});
