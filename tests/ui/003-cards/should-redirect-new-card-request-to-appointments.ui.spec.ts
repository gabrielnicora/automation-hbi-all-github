import { expect, test } from '../../../src/fixtures/ui-test';

const TEST_CASE_ID = 'TC-003-2-0012-UNE';

test.describe('Solicitud de tarjeta de crédito por UI', () => {
  test.use({ testCaseId: TEST_CASE_ID });

  test(`${TEST_CASE_ID} - Solicitud de tarjeta de crédito - Sujeto obligado UIF redirige a solicitar turno`, async ({
    creditCardsPage,
    loginCredentials,
    loginPage,
    newCreditCardRequestPage,
    optionalOriginAccountData,
    startPage,
  }) => {
    await test.step('Autenticar al usuario', async () => {
      await startPage.open();
      await startPage.openLogin();
      await loginPage.authenticate(loginCredentials);
    });
    await test.step('Abrir la solicitud de tarjeta nueva', async () => {
      await creditCardsPage.open();
      await newCreditCardRequestPage.open();
    });

    expect(
      await newCreditCardRequestPage.isTextShown('Solicitud de tarjeta de cr'),
      'Debe mostrarse la solicitud de tarjeta de crédito.',
    ).toBe(true);

    const appointmentPage = await test.step('Declarar sujeto obligado UIF y solicitar turno', async () => {
      // El registro no informa cuenta (sindatos): el legado intenta elegirla y descarta el error.
      if (optionalOriginAccountData.originAccount !== undefined) {
        await newCreditCardRequestPage.selectDebitAccountIfShown(optionalOriginAccountData.originAccount);
      }

      await newCreditCardRequestPage.declareUifObligatedSubject();
      await newCreditCardRequestPage.continue();
      return newCreditCardRequestPage.requestBranchAppointment();
    });

    expect(
      await newCreditCardRequestPage.isTextShownIn(appointmentPage, 'Bienvenido a Citas'),
      'Debe abrirse la solicitud de turnos ("Bienvenido a Citas").',
    ).toBe(true);
  });
});
