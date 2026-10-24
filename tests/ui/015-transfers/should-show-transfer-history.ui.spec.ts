import { expect, test } from '../../../src/fixtures/ui-test';
import { requiredTransferValue } from '../../../src/test-data/transfer-case-data';

// "Ver mis transferencias" (legado TransferenciasInmediatasSteps). TC16756 y TC18103 quitan antes el
// CBU del caso de la agenda por API, como el legado.

test.describe('Historial de transferencias por UI', () => {
  test.describe('TC-015-2-0003-UNE', () => {
    test.use({ testCaseId: 'TC-015-2-0003-UNE' });

    test('TC-015-2-0003-UNE - Transferencias realizadas - Ver mis transferencias (movimientos de hace dos meses)', async ({
      loginApi,
      loginCredentials,
      loginPage,
      startPage,
      thirdPartyTransferWorkflow,
      transferCaseData,
      transferFormPage,
      transferHistoryPage,
    }) => {
      const apiSession = await test.step('Autenticar al usuario', async () => {
        await startPage.open();
        await startPage.openLogin();
        await loginPage.authenticate(loginCredentials);
        return loginApi.authenticate(loginCredentials);
      });
      await test.step('Quitar el CBU de la agenda por API', () =>
        thirdPartyTransferWorkflow.findDestinationOutsideAgenda(
          apiSession,
          requiredTransferValue(transferCaseData.destinationCbu, 'cbuDestino'),
          'CBU',
        ));
      await test.step('Abrir el historial', async () => {
        await transferFormPage.open();
        await transferFormPage.openHistory();
      });
      const twoMonthsAgo = new Date();
      twoMonthsAgo.setMonth(twoMonthsAgo.getMonth() - 2);
      const month = String(twoMonthsAgo.getMonth() + 1).padStart(2, '0');
      const movementDate = await test.step(`Buscar un movimiento del mes ${month}`, () =>
        transferHistoryPage.findMovementDateOfMonth(month));

      expect(movementDate, 'Debe haber movimientos con dos meses de antigüedad.').toBeDefined();
    });
  });

  test.describe('TC-015-2-0008-UNE', () => {
    test.use({ testCaseId: 'TC-015-2-0008-UNE' });

    test('TC-015-2-0008-UNE - Transferencias realizadas - Ver mis transferencias (borrar filtros)', async ({
      loginApi,
      loginCredentials,
      loginPage,
      startPage,
      thirdPartyTransferWorkflow,
      transferCaseData,
      transferFormPage,
      transferHistoryPage,
    }) => {
      const apiSession = await test.step('Autenticar al usuario', async () => {
        await startPage.open();
        await startPage.openLogin();
        await loginPage.authenticate(loginCredentials);
        return loginApi.authenticate(loginCredentials);
      });
      await test.step('Quitar el CBU de la agenda por API', () =>
        thirdPartyTransferWorkflow.findDestinationOutsideAgenda(
          apiSession,
          requiredTransferValue(transferCaseData.destinationCbu, 'cbuDestino'),
          'CBU',
        ));
      await test.step('Abrir el historial', async () => {
        await transferFormPage.open();
        await transferFormPage.openHistory();
      });
      const initialFilters = await test.step('Leer los filtros iniciales', async () => {
        await transferHistoryPage.openFilters();
        return transferHistoryPage.readDateFilters();
      });
      const resetFilters = await test.step('Cambiar y borrar los filtros', async () => {
        await transferHistoryPage.changeDateFilters();
        await transferHistoryPage.clearFilters();
        await transferHistoryPage.openFilters();
        return transferHistoryPage.readDateFilters();
      });

      expect(resetFilters, 'Borrar filtros debe restablecer las fechas iniciales.').toEqual(initialFilters);
    });
  });

  test.describe('TC-015-2-0009-UNE', () => {
    test.use({ testCaseId: 'TC-015-2-0009-UNE' });

    test('TC-015-2-0009-UNE - Transferencias Programadas - Nuevo historial - Visualización', async ({
      loginCredentials,
      loginPage,
      startPage,
      transferFormPage,
      transferHistoryPage,
    }) => {
      await test.step('Autenticar al usuario', async () => {
        await startPage.open();
        await startPage.openLogin();
        await loginPage.authenticate(loginCredentials);
      });
      await test.step('Abrir el historial filtrado', async () => {
        await transferFormPage.open();
        await transferFormPage.openHistory();
        await transferHistoryPage.openFilters();
      });

      expect(await transferHistoryPage.countRows(), 'La grilla no debe superar diez registros.').toBeLessThanOrEqual(
        10,
      );
      expect(await transferHistoryPage.isPaginatorShown(), 'Debe verse el paginador.').toBe(true);

      await test.step('Volver a Transferencias', () => transferHistoryPage.goBack());

      expect(await transferFormPage.isTextShown('Transferencias'), 'Debe volver a Transferencias.').toBe(true);
    });
  });
});
