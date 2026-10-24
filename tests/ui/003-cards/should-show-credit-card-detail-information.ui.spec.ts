import { expect, test } from '../../../src/fixtures/ui-test';

const TEST_CASE_ID = 'TC-003-2-0013-UNE';
// Información del detalle de TC del legado (validacionTarjetaCreditoVisaYMaster).
const DETAIL_INFORMATION = [
  'Límite de compra',
  'Disponible',
  'Límite adelanto en efectivo',
  'Disponible adelanto en efectivo',
  'Cierre',
  'Vencimiento',
  'Próximo cierre',
  'Próximo vencimiento',
  'Saldo actual en pesos',
  'Saldo actual en dólares',
] as const;

test.describe('Detalle de tarjeta de crédito por UI', () => {
  test.use({ testCaseId: TEST_CASE_ID });

  test(`${TEST_CASE_ID} - Web - Detalle de TC - Validar la información en Detalle de TC VISA y MASTER`, async ({
    creditCardPaymentData,
    creditCardsPage,
    loginCredentials,
    loginPage,
    startPage,
  }) => {
    await test.step('Autenticar al usuario', async () => {
      await startPage.open();
      await startPage.openLogin();
      await loginPage.authenticate(loginCredentials);
    });
    await test.step('Abrir el detalle de la primera tarjeta', async () => {
      await creditCardsPage.open();
      await creditCardsPage.openDetailByLabel(creditCardPaymentData.creditCardLastDigits);
    });

    expect(
      await creditCardsPage.findMissingTexts(DETAIL_INFORMATION),
      'Debe verse toda la información del detalle (primera tarjeta).',
    ).toEqual([]);

    // El legado usa el campo tarjetaDebito, que en este registro contiene la otra tarjeta de crédito.
    await test.step('Abrir el detalle de la segunda tarjeta', async () => {
      await creditCardsPage.goBackToList();
      await creditCardsPage.openDetailByLabel(creditCardPaymentData.debitCardLastDigits);
    });

    expect(
      await creditCardsPage.findMissingTexts(DETAIL_INFORMATION),
      'Debe verse toda la información del detalle (segunda tarjeta).',
    ).toEqual([]);
  });
});
