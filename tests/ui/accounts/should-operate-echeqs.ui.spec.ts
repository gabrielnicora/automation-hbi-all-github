import { expect, test } from '../../../src/fixtures/ui-test';
import type { EcheqPage } from '../../../src/pages/accounts/echeq.page';

const FILTER_LABELS = [
  { text: 'Filtrar', element: 'span' },
  { text: 'cuit/cuil Emisor', element: 'div' },
  { text: 'Estado', element: 'span' },
  { text: 'Nro. ECheq', element: 'div' },
  { text: 'Id agrupador', element: 'div' },
  { text: 'Fecha de emisión desde', element: 'span' },
  { text: 'Fecha de emisión hasta', element: 'span' },
  { text: 'Fecha de pago desde', element: 'span' },
  { text: 'Fecha de pago hasta', element: 'span' },
  { text: 'Aplicar', element: 'span' },
] as const;

test.describe('Consulta y aceptación de eCheq UI de Banco Entre Ríos', () => {
  test.setTimeout(180_000);

  test.describe('TC29413BERSA', () => {
    test.use({ testCaseId: 'TC29413BERSA' });

    test('TC29413BERSA - muestra los campos del filtro de eCheq', async ({
      echeqPage,
      loginCredentials,
      loginPage,
      startPage,
    }) => {
      await startPage.open();
      await startPage.openLogin();
      await loginPage.authenticate(loginCredentials);

      await echeqPage.openFromAccounts();
      await echeqPage.openFilters();

      for (const { text, element } of FILTER_LABELS) {
        expect(await echeqPage.isLabelShown(text, element), `El filtro debe mostrar "${text}".`).toBe(true);
      }
    });
  });

  test.describe('TC0026BERSA', () => {
    test.use({ testCaseId: 'TC0026BERSA' });

    test('TC0026BERSA - muestra el detalle del primer eCheq con todos los estados', async ({
      echeqPage,
      loginCredentials,
      loginPage,
      startPage,
    }) => {
      await startPage.open();
      await startPage.openLogin();
      await loginPage.authenticate(loginCredentials);

      await echeqPage.openFromAccounts();
      await echeqPage.openFilters();
      expect(await echeqPage.isLabelShown('Todos', 'div'), 'El estado debe ofrecer "Todos".').toBe(true);
      await echeqPage.applyFilters();
      await echeqPage.openFirstDetail();

      for (const text of ['Detalle de ECheq', 'Id ECheq', 'Nro. ECheq', 'Estado']) {
        expect(await echeqPage.isTextShown(text), `El detalle debe mostrar "${text}".`).toBe(true);
      }
    });
  });

  test.describe('TC124865BERSA', () => {
    test.use({ testCaseId: 'TC124865BERSA' });

    test('TC124865BERSA - acepta un eCheq emitido pendiente con segundo factor', async ({
      echeqPage,
      loginApi,
      loginCredentials,
      loginPage,
      smsOperationOtpApi,
      startPage,
    }) => {
      const echeqNumber = await test.step('Emitir el eCheq a aceptar (precondición legada por API)', () =>
        issuePendingEcheqForAcceptance());

      const apiSession = await test.step('Autenticar al usuario', async () => {
        await startPage.open();
        await startPage.openLogin();
        await loginPage.authenticate(loginCredentials);
        return loginApi.authenticate(loginCredentials);
      });

      await echeqPage.openFromAccounts();
      expect(await echeqPage.isTextShown('Consulta de ECheq'), 'Debe mostrar la consulta de eCheq.').toBe(true);
      await echeqPage.searchByNumberAndPendingState(echeqNumber);
      await echeqPage.openFirstDetail();
      for (const text of ['Detalle de ECHEQ', 'Id Echeq', 'Nro Echeq', 'Estado']) {
        expect(await echeqPage.isTextShown(text), `El detalle debe mostrar "${text}".`).toBe(true);
      }

      await echeqPage.startAcceptance();
      await expectAcceptanceGrid(echeqPage, echeqNumber);
      await echeqPage.continueToSecondFactor();
      await echeqPage.enterSecondFactorCode(await smsOperationOtpApi.resolve(apiSession, loginCredentials.username));

      await expectAcceptanceGrid(echeqPage, echeqNumber);
    });
  });
});

async function expectAcceptanceGrid(echeqPage: EcheqPage, echeqNumber: string): Promise<void> {
  for (const text of ['Aceptar ECheqs', echeqNumber, 'EMITIDO-PENDIENTE']) {
    expect(await echeqPage.isTextShown(text), `La aceptación debe mostrar "${text}".`).toBe(true);
  }
}

/**
 * El legado emite el eCheq con `echeqAPI.generacionDeEcheqAPI` usando el registro `TCECHEQBERSA`
 * (login de empresas). El proyecto no soporta ese login y la clave no cumple `TC<n>BERSA`; ver
 * `docs/guides/ambiguities/lote-l10-ui.md`.
 */
function issuePendingEcheqForAcceptance(): Promise<string> {
  return Promise.reject(
    new Error(
      'Precondición no portada: la emisión de eCheq por API con login de empresas (TCECHEQBERSA) no está disponible.',
    ),
  );
}
