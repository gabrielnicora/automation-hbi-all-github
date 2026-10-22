import { test } from '../../../src/fixtures/api-test';
import { requiredTransferValue } from '../../../src/test-data/transfer-case-data';

// Agenda de destinatarios de transferencias (legado validarCbuAgendado, eliminarContactoAgendado y
// agendarNuevoContacto). El legado sólo valida códigos de éxito; no afirma el estado final de la agenda.

test.describe('Agenda de destinatarios de transferencias API de Banco Entre Ríos', () => {
  test.describe('TC0145BERSA', () => {
    test.use({ testCaseId: 'TC0145BERSA' });

    test('TC0145BERSA - Transferencias - Añadir nuevo destinatario - Con cbu valido', async ({
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
          'TC0145BERSA',
          previous,
        ));
    });
  });

  test.describe('TC12981BERSA', () => {
    test.use({ testCaseId: 'TC12981BERSA' });

    test('TC12981BERSA - Transferencias - Eliminar destinatario de agenda', async ({
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
          'TC12981BERSA',
          previous,
        ));

      await test.step('Eliminar el destinatario agendado', () =>
        thirdPartyTransfersApi.removeDestination(session, idAdheredAccount));
      await test.step('Buscar nuevamente el CBU', () => thirdPartyTransfersApi.findDestination(session, cbu, 'CBU'));
    });
  });
});
