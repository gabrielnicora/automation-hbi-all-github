import { expect, test } from '../../../src/fixtures/ui-test';

const TEST_CASE_ID = 'TC0162BERSA';

test.describe('Denuncia de tarjeta de crédito por UI', () => {
  test.use({ testCaseId: TEST_CASE_ID });

  // El legado llega a la pantalla de confirmación sin confirmar la denuncia.
  test(`${TEST_CASE_ID} - Tarjeta de Crédito - Denunciar robo o extravío de Tarjeta de Crédito`, async ({
    creditCardDetailUiData,
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
    await test.step('Iniciar la denuncia por robo', async () => {
      await creditCardsPage.open();
      await creditCardsPage.openDetail(creditCardDetailUiData.creditCard);
      await creditCardsPage.startTheftReport();
    });

    expect(
      await creditCardsPage.isTextShown('Confirmar denuncia por robo o extrav'),
      'Debe mostrarse la confirmación de la denuncia.',
    ).toBe(true);
  });
});
