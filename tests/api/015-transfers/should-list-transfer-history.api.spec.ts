import { ProductWidgetsApi } from '../../../src/api/accounts/product-widgets.api';
import { expect, test } from '../../../src/fixtures/api-test';
import { requiredTransferValue } from '../../../src/test-data/transfer-case-data';
import type { TransferHistoryPage } from '../../../src/api/transfers/transfer-history.api';

// Historial de transferencias (legado TransferenciasSteps.validaHistorial*).

/** Legado validaHistorialDeTransferencias: hay páginas, existen las filas 1 y 10, y no más de 10 por página. */
function expectLastYearPage(history: TransferHistoryPage): void {
  expect(history.totalPages, 'Debe haber resultados para esta búsqueda.').not.toBe(0);
  expect(history.creationDateTimes[0], 'La primera transferencia debe informar su fecha.').toBeDefined();
  expect(history.creationDateTimes[9], 'La décima transferencia debe informar su fecha.').toBeDefined();
  expect(history.creationDateTimes.length, 'La grilla no debe tener más de diez registros.').toBeLessThanOrEqual(10);
}

test.describe('Historial de transferencias API de Banco Entre Ríos', () => {
  test.describe('TC-015-1-0045-UNE', () => {
    test.use({ testCaseId: 'TC-015-1-0045-UNE' });

    test('TC-015-1-0045-UNE - Transferencias programadas - Fecha específica agendada - Historial de transferencias', async ({
      loginApi,
      loginCredentials,
      thirdPartyTransferWorkflow,
      thirdPartyTransfersApi,
      transferCaseData,
      transferHistoryApi,
    }) => {
      const session = await test.step('Autenticar al usuario', () => loginApi.authenticate(loginCredentials));
      const previous = await test.step('Buscar el CBU y quitarlo de la agenda', () =>
        thirdPartyTransferWorkflow.findDestinationOutsideAgenda(
          session,
          requiredTransferValue(transferCaseData.destinationCbu, 'cbuDestino'),
          'CBU',
        ));
      const { destination } = await test.step('Agendar el contacto', () =>
        thirdPartyTransferWorkflow.adhereAndFindDestination(
          session,
          loginCredentials.username,
          'TC-015-1-0045-UNE',
          previous,
        ));
      const { debitCard, origin } = await test.step('Consultar los productos del usuario', () =>
        thirdPartyTransferWorkflow.loadOriginProducts(
          session,
          requiredTransferValue(transferCaseData.originAccount, 'ctaOrigen'),
          requiredTransferValue(transferCaseData.debitCardLastDigits, 'tarjetaDebito'),
        ));
      await test.step('Programar la transferencia para mañana', () =>
        thirdPartyTransfersApi.scheduleToThirdParty(
          session,
          loginCredentials.username,
          destination,
          origin,
          debitCard,
        ));
      const history = await test.step('Consultar el historial del último año', () =>
        transferHistoryApi.listLastYear(session));

      expectLastYearPage(history);
    });
  });

  test.describe('TC-015-1-0046-UNE', () => {
    test.use({ testCaseId: 'TC-015-1-0046-UNE' });

    test('TC-015-1-0046-UNE - Transferencias a contacto agendado - Historial de transferencias', async ({
      loginApi,
      loginCredentials,
      thirdPartyTransferWorkflow,
      thirdPartyTransfersApi,
      transferCaseData,
      transferHistoryApi,
    }) => {
      const originAccount = requiredTransferValue(transferCaseData.originAccount, 'ctaOrigen');
      const session = await test.step('Autenticar al usuario', () => loginApi.authenticate(loginCredentials));
      const previous = await test.step('Buscar el CBU y quitarlo de la agenda', () =>
        thirdPartyTransferWorkflow.findDestinationOutsideAgenda(
          session,
          requiredTransferValue(transferCaseData.destinationCbu, 'cbuDestino'),
          'CBU',
        ));
      const { destination, idAdheredAccount } = await test.step('Agendar el contacto', () =>
        thirdPartyTransferWorkflow.adhereAndFindDestination(
          session,
          loginCredentials.username,
          'TC-015-1-0046-UNE',
          previous,
        ));
      const { debitCard, origin, products } = await test.step('Consultar los productos del usuario', () =>
        thirdPartyTransferWorkflow.loadOriginProducts(
          session,
          originAccount,
          requiredTransferValue(transferCaseData.debitCardLastDigits, 'tarjetaDebito'),
        ));
      // El legado lee el saldo inicial aunque este caso no lo compara.
      ProductWidgetsApi.legacyBalanceUnits(products, originAccount);
      await test.step('Transferir al contacto agendado', () =>
        thirdPartyTransfersApi.transferToAdheredContact(
          session,
          destination,
          idAdheredAccount,
          origin,
          debitCard,
          'TC-015-1-0046-UNE',
        ));
      const history = await test.step('Consultar el historial del último año', () =>
        transferHistoryApi.listLastYear(session));

      expectLastYearPage(history);
    });
  });

  test.describe('TC-015-1-0047-UNE', () => {
    test.use({ testCaseId: 'TC-015-1-0047-UNE' });

    test('TC-015-1-0047-UNE - Transferencias a CVU - Historial de transferencias', async ({
      loginApi,
      loginCredentials,
      thirdPartyTransferWorkflow,
      thirdPartyTransfersApi,
      transferCaseData,
      transferHistoryApi,
    }) => {
      const originAccount = requiredTransferValue(transferCaseData.originAccount, 'ctaOrigen');
      const session = await test.step('Autenticar al usuario', () => loginApi.authenticate(loginCredentials));
      const destination = await test.step('Buscar el CBU y quitarlo de la agenda', () =>
        thirdPartyTransferWorkflow.findDestinationOutsideAgenda(
          session,
          requiredTransferValue(transferCaseData.destinationCbu, 'cbuDestino'),
          'CBU',
        ));
      const { debitCard, origin, products } = await test.step('Consultar los productos del usuario', () =>
        thirdPartyTransferWorkflow.loadOriginProducts(
          session,
          originAccount,
          requiredTransferValue(transferCaseData.debitCardLastDigits, 'tarjetaDebito'),
        ));
      ProductWidgetsApi.legacyBalanceUnits(products, originAccount);
      await test.step('Transferir al destinatario', () =>
        thirdPartyTransfersApi.transferToThirdParty(
          session,
          loginCredentials.username,
          'standard',
          destination,
          origin,
          debitCard,
        ));
      const history = await test.step('Consultar el historial del último año', () =>
        transferHistoryApi.listLastYear(session));

      expectLastYearPage(history);
    });
  });

  test.describe('TC-015-1-0036-UNE', () => {
    test.use({ testCaseId: 'TC-015-1-0036-UNE' });

    test('TC-015-1-0036-UNE - Transferencias realizadas - Ver mis transferencias - Filtros', async ({
      loginApi,
      loginCredentials,
      transferHistoryApi,
    }) => {
      const session = await test.step('Autenticar al usuario', () => loginApi.authenticate(loginCredentials));
      const history = await test.step('Consultar el historial de un año', () =>
        transferHistoryApi.listOneYearRange(session));

      expect(history.totalPages, 'Debe haber resultados para esta búsqueda.').not.toBe(0);
      expect(history.creationDateTimes[0], 'La primera transferencia debe informar su fecha.').toBeDefined();
    });
  });

  const SEARCHES = [
    { id: 'TC-015-1-0037-UNE', title: 'Ver mis transferencias - rango más nombre en buscador', searchedText: 'Borges' },
    {
      id: 'TC-015-1-0038-UNE',
      title: 'Ver mis transferencias - rango más nombre en buscador',
      searchedText: 'TestAutomation',
    },
  ] as const;

  for (const search of SEARCHES) {
    test.describe(search.id, () => {
      test.use({ testCaseId: search.id });

      test(`${search.id} - Transferencias realizadas - ${search.title}`, async ({
        loginApi,
        loginCredentials,
        transferHistoryApi,
      }) => {
        const session = await test.step('Autenticar al usuario', () => loginApi.authenticate(loginCredentials));
        const history = await test.step(`Buscar "${search.searchedText}" en el historial`, () =>
          transferHistoryApi.searchLastSemester(session, search.searchedText));

        expect(history.totalPages, 'Debe haber resultados para esta búsqueda.').not.toBe(0);
        expect(history.creationDateTimes[0], 'La primera transferencia debe informar su fecha.').toBeDefined();
      });
    });
  }

  test.describe('TC-015-1-0048-UNE', () => {
    test.use({ testCaseId: 'TC-015-1-0048-UNE' });

    test('TC-015-1-0048-UNE - Transferencias realizadas - Ver mis transferencias - Búsqueda sin resultados', async ({
      loginApi,
      loginCredentials,
      thirdPartyTransferWorkflow,
      thirdPartyTransfersApi,
      transferCaseData,
      transferHistoryApi,
    }) => {
      const originAccount = requiredTransferValue(transferCaseData.originAccount, 'ctaOrigen');
      const session = await test.step('Autenticar al usuario', () => loginApi.authenticate(loginCredentials));
      const destination = await test.step('Buscar el CBU y quitarlo de la agenda', () =>
        thirdPartyTransferWorkflow.findDestinationOutsideAgenda(
          session,
          requiredTransferValue(transferCaseData.destinationCbu, 'cbuDestino'),
          'CBU',
        ));
      const { debitCard, origin, products } = await test.step('Consultar los productos del usuario', () =>
        thirdPartyTransferWorkflow.loadOriginProducts(
          session,
          originAccount,
          requiredTransferValue(transferCaseData.debitCardLastDigits, 'tarjetaDebito'),
        ));
      ProductWidgetsApi.legacyBalanceUnits(products, originAccount);
      await test.step('Transferir al destinatario', () =>
        thirdPartyTransfersApi.transferToThirdParty(
          session,
          loginCredentials.username,
          'standard',
          destination,
          origin,
          debitCard,
        ));
      const history = await test.step('Buscar un nombre inexistente en el historial', () =>
        transferHistoryApi.searchLastSemester(session, 'nombresinresultados'));

      expect(history.totalPages, 'La búsqueda no debe arrojar resultados.').toBe(0);
    });
  });
});
