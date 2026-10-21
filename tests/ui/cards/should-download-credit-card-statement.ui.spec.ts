import { expect, test } from '../../../src/fixtures/ui-test';

// Descarga del resumen del primer período desde el detalle de la tarjeta (legado clickDescargarResumen,
// clickPrimerPeriodo, clickDescargaPrimerPeriodo y validarDescarga("Estado")).

test.describe('Resúmenes de tarjeta de crédito por UI', () => {
  test.describe('TC0104BERSA', () => {
    test.use({ testCaseId: 'TC0104BERSA' });

    test('TC0104BERSA - Tarjeta de Crédito - Descarga de resumen', async ({
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
      await test.step('Abrir los resúmenes de la tarjeta', async () => {
        await creditCardsPage.open();
        await creditCardsPage.openDetail(creditCardDetailUiData.creditCard);
        await creditCardsPage.openStatementDownloads();
      });

      expect(await creditCardsPage.isTextShown('Descargar resumen'), 'Debe mostrarse "Descargar resumen".').toBe(true);

      const fileName = await test.step('Descargar el primer período', () =>
        creditCardsPage.downloadFirstPeriodStatement());

      expect(fileName, 'Debe descargarse el resumen del período.').toContain('Estado');
    });
  });

  test.describe('TC0167BERSA', () => {
    test.use({ testCaseId: 'TC0167BERSA' });

    test('TC0167BERSA - Tarjeta de Crédito - Consultar último resumen de una Tarjeta de Crédito', async ({
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
      await test.step('Abrir los resúmenes de la tarjeta', async () => {
        await creditCardsPage.open();
        await creditCardsPage.openDetail(creditCardDetailUiData.creditCard);
        await creditCardsPage.openStatementDownloads();
      });
      const fileName = await test.step('Descargar el primer período', () =>
        creditCardsPage.downloadFirstPeriodStatement());

      expect(fileName, 'Debe descargarse el resumen del período.').toContain('Estado');
    });
  });
});
