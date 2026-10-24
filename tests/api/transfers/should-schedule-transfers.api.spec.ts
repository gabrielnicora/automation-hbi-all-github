import { ProductWidgetsApi } from '../../../src/api/accounts/product-widgets.api';
import { expect, test } from '../../../src/fixtures/api-test';
import { requiredTransferValue, type TransferCaseData } from '../../../src/test-data/transfer-case-data';

// Transferencias programadas para mañana (legado TransferenciasSteps.transferenciaProgramadaTerceros,
// trasnferenciaProgramadaContactoAgendado y trasnferenciasProgramadaEntreCuentaPropias).

type DestinationType = 'ALIAS' | 'CBU';

function destinationValue(data: TransferCaseData, type: DestinationType): string {
  return type === 'CBU'
    ? requiredTransferValue(data.destinationCbu, 'cbuDestino')
    : requiredTransferValue(data.destinationAlias, 'aliasDestino');
}

// Programadas a terceros: el legado sólo valida la aceptación.
const SCHEDULED_TO_THIRD_PARTIES: readonly { id: string; title: string; destinationType: DestinationType }[] = [
  { id: 'TC0473BERSA', title: 'CBU - terceros otros bancos $ - NO AGENDADOS', destinationType: 'CBU' },
  { id: 'TC0484BERSA', title: 'CVU - terceros $ NO AGENDADOS', destinationType: 'CBU' },
  { id: 'TC0472BERSA', title: 'CBU - terceros mismo banco USD - NO AGENDADOS', destinationType: 'CBU' },
  { id: 'TC0474BERSA', title: 'CBU - terceros otros bancos USD - NO AGENDADOS', destinationType: 'CBU' },
  { id: 'TC0485BERSA', title: 'ALIAS de CVU - terceros $ NO AGENDADOS', destinationType: 'ALIAS' },
  { id: 'TC0478BERSA', title: 'ALIAS - terceros mismo banco USD - NO AGENDADOS', destinationType: 'ALIAS' },
  { id: 'TC0479BERSA', title: 'ALIAS - terceros otros bancos $ - NO AGENDADOS', destinationType: 'ALIAS' },
  { id: 'TC0480BERSA', title: 'ALIAS - terceros otros bancos USD - NO AGENDADOS', destinationType: 'ALIAS' },
];

// Programadas a terceros con comprobante completo (validarticketTodosLosCampos).
const SCHEDULED_WITH_TICKET = [
  { id: 'TC18090BERSA', title: 'Programada (fecha específica) CBU a cuentas propias otro banco $ - NO AGENDADO' },
  { id: 'TC18094BERSA', title: 'Programada (fecha específica) CBU a Terceros otro banco $ - NO AGENDADOS' },
] as const;

// Programadas a un contacto que el caso agenda con su ID como referencia.
const SCHEDULED_TO_ADHERED_CONTACT = [
  { id: 'TC0465BERSA', title: 'Programadas (fecha específica) - terceros mismo banco $ - AGENDADOS' },
  { id: 'TC0468BERSA', title: 'Programadas (fecha específica) - terceros otros bancos USD - AGENDADOS' },
  { id: 'TC0467BERSA', title: 'Programadas (fecha específica) - terceros otros bancos $ - AGENDADOS' },
  { id: 'TC0483BERSA', title: 'Programadas (fecha específica) CVU - terceros $ AGENDADOS' },
] as const;

test.describe('Transferencias programadas API de Banco Entre Ríos', () => {
  for (const transferCase of SCHEDULED_TO_THIRD_PARTIES) {
    test.describe(transferCase.id, () => {
      test.use({ testCaseId: transferCase.id });

      test(`${transferCase.id} - Transferencias Programadas (Fecha especifica) ${transferCase.title}`, async ({
        loginApi,
        loginCredentials,
        thirdPartyTransferWorkflow,
        thirdPartyTransfersApi,
        transferCaseData,
      }) => {
        const session = await test.step('Autenticar al usuario', () => loginApi.authenticate(loginCredentials));
        const destination = await test.step('Buscar el destino y quitarlo de la agenda', () =>
          thirdPartyTransferWorkflow.findDestinationOutsideAgenda(
            session,
            destinationValue(transferCaseData, transferCase.destinationType),
            transferCase.destinationType,
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
      });
    });
  }

  test.describe('TC0418BERSA', () => {
    test.use({ testCaseId: 'TC0418BERSA' });

    // El título legado dice "NO AGENDADOS" inmediata, pero el caso programa la transferencia.
    test('TC0418BERSA - Transferencias CBU - terceros otros bancos USD - NO AGENDADOS', async ({
      loginApi,
      loginCredentials,
      thirdPartyTransferWorkflow,
      thirdPartyTransfersApi,
      transferCaseData,
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
      const balanceBefore = ProductWidgetsApi.legacyBalanceUnits(products, originAccount);

      await test.step('Programar la transferencia para mañana', () =>
        thirdPartyTransfersApi.scheduleToThirdParty(
          session,
          loginCredentials.username,
          destination,
          origin,
          debitCard,
        ));
      const balanceAfter = await test.step('Consultar el saldo actualizado', () =>
        thirdPartyTransferWorkflow.readBalanceAfter(session, originAccount));

      expect(
        balanceBefore - 1,
        'La cuenta origen debe reflejar el descuento (comparación legada).',
      ).toBeGreaterThanOrEqual(balanceAfter);
    });
  });

  for (const transferCase of SCHEDULED_WITH_TICKET) {
    test.describe(transferCase.id, () => {
      test.use({ testCaseId: transferCase.id });

      test(`${transferCase.id} - ${transferCase.title}`, async ({
        loginApi,
        loginCredentials,
        thirdPartyTransferWorkflow,
        thirdPartyTransfersApi,
        transferCaseData,
      }) => {
        const originAccount = requiredTransferValue(transferCaseData.originAccount, 'ctaOrigen');
        const session = await test.step('Autenticar al usuario', () => loginApi.authenticate(loginCredentials));
        const destination = await test.step('Buscar el CBU y quitarlo de la agenda', () =>
          thirdPartyTransferWorkflow.findDestinationOutsideAgenda(
            session,
            requiredTransferValue(transferCaseData.destinationCbu, 'cbuDestino'),
            'CBU',
          ));
        const { debitCard, origin } = await test.step('Consultar los productos del usuario', () =>
          thirdPartyTransferWorkflow.loadOriginProducts(
            session,
            originAccount,
            requiredTransferValue(transferCaseData.debitCardLastDigits, 'tarjetaDebito'),
          ));
        const idTransaction = await test.step('Programar la transferencia para mañana', () =>
          thirdPartyTransfersApi.scheduleToThirdParty(
            session,
            loginCredentials.username,
            destination,
            origin,
            debitCard,
          ));
        const ticket = await test.step('Consultar el comprobante', () =>
          thirdPartyTransfersApi.readTicket(session, idTransaction));

        expect.soft(ticket.originAccountName, 'Nombre del emisor').toBe(session.customerName);
        expect.soft(ticket.amount, 'Importe').toBe('$ 1,00');
        expect.soft(ticket.originAccountLabel, 'Cuenta del emisor').toBe(originAccount);
        expect.soft(ticket.originAccountDocument, 'CUIL del emisor').toBe(session.customerCuil);
        expect.soft(ticket.targetAccountDenomination, 'Nombre del destinatario').toBe(destination.targetName);
        expect.soft(ticket.cbuTarget, 'CBU del destinatario').toBe(destination.cbu);
        expect.soft(ticket.targetAccountCuil, 'CUIL del destinatario').toBe(destination.cuit);
        expect.soft(ticket.conceptLabel, 'Concepto').toBe('Alquileres');
        expect((session.customerName ?? '').length, 'El nombre del emisor no debe estar vacío.').toBeGreaterThan(0);
      });
    });
  }

  for (const transferCase of SCHEDULED_TO_ADHERED_CONTACT) {
    test.describe(transferCase.id, () => {
      test.use({ testCaseId: transferCase.id });

      test(`${transferCase.id} - ${transferCase.title}`, async ({
        loginApi,
        loginCredentials,
        thirdPartyTransferWorkflow,
        thirdPartyTransfersApi,
        transferCaseData,
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
            transferCase.id,
            previous,
          ));
        const { debitCard, origin } = await test.step('Consultar los productos del usuario', () =>
          thirdPartyTransferWorkflow.loadOriginProducts(
            session,
            requiredTransferValue(transferCaseData.originAccount, 'ctaOrigen'),
            requiredTransferValue(transferCaseData.debitCardLastDigits, 'tarjetaDebito'),
          ));

        await test.step('Programar la transferencia al contacto agendado', () =>
          thirdPartyTransfersApi.scheduleToAdheredContact(session, destination, origin, debitCard, transferCase.id));
      });
    });
  }

  test.describe('TC0008BERSA', () => {
    test.use({ testCaseId: 'TC0008BERSA' });

    test('TC0008BERSA - Transferencia intrabancaria en pesos - propias - programada', async ({
      loginApi,
      loginCredentials,
      productWidgetsApi,
      thirdPartyTransfersApi,
      transferCaseData,
    }) => {
      const originAccount = requiredTransferValue(transferCaseData.originAccount, 'ctaOrigen');
      const session = await test.step('Autenticar al usuario', () => loginApi.authenticate(loginCredentials));
      const products = await test.step('Consultar los productos del usuario', () => productWidgetsApi.load(session));

      await test.step('Programar la transferencia entre cuentas propias', () =>
        thirdPartyTransfersApi.scheduleBetweenOwnAccounts(
          session,
          ProductWidgetsApi.accountId(products, originAccount),
          ProductWidgetsApi.accountId(
            products,
            requiredTransferValue(transferCaseData.destinationAccount, 'ctaDestino'),
          ),
          ProductWidgetsApi.debitCard(
            products,
            requiredTransferValue(transferCaseData.debitCardLastDigits, 'tarjetaDebito'),
          ),
          // El legado envía como cbuTarget el CBU de la cuenta origen.
          ProductWidgetsApi.accountCbu(products, originAccount),
        ));
    });
  });
});
