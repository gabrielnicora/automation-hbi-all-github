import type { CreditCardTrackingPage } from '../../../src/pages/cards/credit-card-tracking.page';
import { expect, test } from '../../../src/fixtures/ui-test';

// Seguimiento de tarjetas de crédito (legado validacionCamposSeguimientoTarjeta y validacionDentroDeTarjeta).
const LIST_FIELDS = ['Fecha de', 'Tipo', 'Marca', 'de tarjeta', 'Nombre y Apellido', 'Estado'] as const;
const DETAIL_FIELDS = [
  'Fecha de',
  'Tipo',
  'Marca',
  'de tarjeta',
  'Datos del titular',
  'Nombre y Apellido',
  'Tipo de documento',
  'de documento',
  'Tarjeta',
] as const;

/** Verificación común del legado al abrir el seguimiento. */
async function expectTrackingList(trackingPage: CreditCardTrackingPage): Promise<void> {
  expect(await trackingPage.isTextShown('Seguimiento de tarjetas'), 'Debe mostrarse "Seguimiento de tarjetas".').toBe(
    true,
  );
  expect(await trackingPage.findMissingTexts(LIST_FIELDS), 'Deben verse todos los campos del seguimiento.').toEqual([]);
}

test.describe('Seguimiento de tarjetas de crédito por UI', () => {
  test.beforeEach(async ({ creditCardTrackingPage, creditCardsPage, loginCredentials, loginPage, startPage }) => {
    await test.step('Autenticar al usuario', async () => {
      await startPage.open();
      await startPage.openLogin();
      await loginPage.authenticate(loginCredentials);
    });
    await test.step('Abrir el seguimiento de tarjetas', async () => {
      await creditCardsPage.open();
      await creditCardTrackingPage.open();
    });
  });

  test.describe('TC0153BERSA', () => {
    test.use({ testCaseId: 'TC0153BERSA' });

    test('TC0153BERSA - Tarjeta de Crédito - Seguimiento de Tarjetas de Crédito', async ({
      creditCardTrackingPage,
    }) => {
      await expectTrackingList(creditCardTrackingPage);
    });
  });

  test.describe('TC0155BERSA', () => {
    test.use({ testCaseId: 'TC0155BERSA' });

    test('TC0155BERSA - Tarjeta de Crédito - Seguimiento de Tarjetas de Crédito - Detalles', async ({
      creditCardTrackingPage,
    }) => {
      await expectTrackingList(creditCardTrackingPage);
      await test.step('Ingresar al detalle del seguimiento', () => creditCardTrackingPage.openFirstDetail());

      expect(
        await creditCardTrackingPage.findMissingTexts(DETAIL_FIELDS),
        'Deben verse todos los campos del detalle.',
      ).toEqual([]);
    });
  });

  test.describe('TC0157BERSA', () => {
    test.use({ testCaseId: 'TC0157BERSA' });

    test('TC0157BERSA - Tarjeta de Crédito - Seguimiento de Tarjetas de Crédito - Descargar Detalles', async ({
      creditCardTrackingPage,
    }) => {
      await expectTrackingList(creditCardTrackingPage);
      await test.step('Ingresar al detalle del seguimiento', () => creditCardTrackingPage.openFirstDetail());
      const fileName = await test.step('Descargar el detalle', () => creditCardTrackingPage.downloadDetail());

      expect(fileName, 'Debe descargarse el detalle del seguimiento.').toContain(
        'Detalle de seguimiento de tarjeta.pdf',
      );
    });
  });

  test.describe('TC0158BERSA', () => {
    test.use({ testCaseId: 'TC0158BERSA' });

    // El legado presiona imprimir y cambia de pestaña sin afirmar un resultado.
    test('TC0158BERSA - Tarjeta de Crédito - Seguimiento de Tarjetas de Crédito - Imprimir Detalles', async ({
      creditCardTrackingPage,
    }) => {
      await expectTrackingList(creditCardTrackingPage);
      await test.step('Ingresar al detalle del seguimiento', () => creditCardTrackingPage.openFirstDetail());
      await test.step('Imprimir el detalle', () => creditCardTrackingPage.printDetail());
    });
  });
});
