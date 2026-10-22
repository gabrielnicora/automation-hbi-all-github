import { test } from '../../../src/fixtures/ui-test';

// Detalle "Mi fondo" de A-Delta Ahorro y su modal "Más Información" (legado FrontTest TC0133/TC0134).
// El legado no afirma resultados después de aceptar o cancelar: las acciones deben poder ejecutarse.
const FUND_NAME = 'A-Delta Ahorro';

test.describe('FCI - Más información del fondo por UI', () => {
  test.describe('TC0720008BERSA', () => {
    test.use({ testCaseId: 'TC0720008BERSA' });

    test('TC0720008BERSA - FCI - Más información - Aceptar modal', async ({
      investmentFundsPage,
      loginCredentials,
      loginPage,
      startPage,
    }) => {
      await test.step('Autenticar al usuario', async () => {
        await startPage.open();
        await startPage.openLogin();
        await loginPage.authenticate(loginCredentials);
      });
      await test.step('Abrir el detalle del fondo', async () => {
        await investmentFundsPage.open();
        await investmentFundsPage.openMyFundDetail(FUND_NAME);
      });
      await test.step('Aceptar "Más Información"', async () => {
        await investmentFundsPage.openMoreInformation();
        await investmentFundsPage.acceptMoreInformation();
      });
    });
  });

  test.describe('TC0720009BERSA', () => {
    test.use({ testCaseId: 'TC0720009BERSA' });

    test('TC0720009BERSA - FCI - Más información - Rechazar modal', async ({
      investmentFundsPage,
      loginCredentials,
      loginPage,
      startPage,
    }) => {
      await test.step('Autenticar al usuario', async () => {
        await startPage.open();
        await startPage.openLogin();
        await loginPage.authenticate(loginCredentials);
      });
      await test.step('Abrir el detalle del fondo', async () => {
        await investmentFundsPage.open();
        await investmentFundsPage.openMyFundDetail(FUND_NAME);
      });
      await test.step('Cancelar "Más Información"', async () => {
        await investmentFundsPage.openMoreInformation();
        await investmentFundsPage.cancelIfShown();
      });
    });
  });
});
