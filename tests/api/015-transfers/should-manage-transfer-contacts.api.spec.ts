import { test } from '../../../src/fixtures/api-test';
import { requiredTransferValue } from '../../../src/test-data/transfer-case-data';

// Agenda de destinatarios de transferencias (legado validarCbuAgendado, eliminarContactoAgendado y
// agendarNuevoContacto). El legado sólo valida códigos de éxito; no afirma el estado final de la agenda.

test.describe('Agenda de destinatarios de transferencias API de Banco Entre Ríos', () => {
  test.describe('TC-015-1-0004-UNE', () => {
    test.use({ testCaseId: 'TC-015-1-0004-UNE' });

    test('TC-015-1-0004-UNE - Transferencias - Añadir nuevo destinatario - Con cbu valido', async ({
      loginApi,
      loginCredentials,
      thirdPartyTransferWorkflow,
      transferCaseData,
    }) => {
      const session = await test.step('Autenticar al usuario', () => loginApi.authenticate(loginCredentials));
      const previous = await test.step('Buscar el CBU y quitarlo de la agenda', () =>
        thirdPartyTransferWorkflow.findDestinationOutsideAgenda(
          session,
          requiredTransferValue(transferCaseData.destinationCbu, 'cbuDestino'),
          'CBU',
        ));

      await test.step('Agendar el destinatario y volver a buscarlo', () =>
        thirdPartyTransferWorkflow.adhereAndFindDestination(
          session,
          loginCredentials.username,
          'TC-015-1-0004-UNE',
          previous,
        ));
    });
  });

  test.describe('TC-015-1-0035-UNE', () => {
    test.use({ testCaseId: 'TC-015-1-0035-UNE' });

    test('TC-015-1-0035-UNE - Transferencias - Eliminar destinatario de agenda', async ({
      loginApi,
      loginCredentials,
      thirdPartyTransferWorkflow,
      thirdPartyTransfersApi,
      transferCaseData,
    }) => {
      const cbu = requiredTransferValue(transferCaseData.destinationCbu, 'cbuDestino');
      const session = await test.step('Autenticar al usuario', () => loginApi.authenticate(loginCredentials));
      const previous = await test.step('Buscar el CBU y quitarlo de la agenda', () =>
        thirdPartyTransferWorkflow.findDestinationOutsideAgenda(session, cbu, 'CBU'));
      const { idAdheredAccount } = await test.step('Agendar el destinatario y volver a buscarlo', () =>
        thirdPartyTransferWorkflow.adhereAndFindDestination(
          session,
          loginCredentials.username,
          'TC-015-1-0035-UNE',
          previous,
        ));

      await test.step('Eliminar el destinatario agendado', () =>
        thirdPartyTransfersApi.removeDestination(session, idAdheredAccount));
      await test.step('Buscar nuevamente el CBU', () => thirdPartyTransfersApi.findDestination(session, cbu, 'CBU'));
    });
  });
});
