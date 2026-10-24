import { ProductWidgetsApi } from '../../../src/api/accounts/product-widgets.api';
import type { ThirdPartyTransferVariant } from '../../../src/api/transfers/third-party-transfers.api';
import { expect, test } from '../../../src/fixtures/api-test';
import { requiredTransferValue, type TransferCaseData } from '../../../src/test-data/transfer-case-data';

// Transferencias inmediatas de ARS/USD 1 a terceros por CBU, CVU o alias, con OTP por SMS
// (legado TransferenciasTest + TransferenciasSteps.transferenciaATerceros*). Antes de transferir,
// el destino se quita de la agenda si estaba agendado.

type DestinationType = 'ALIAS' | 'CBU';

interface ImmediateTransferCase {
  readonly id: string;
  readonly title: string;
  readonly destinationType: DestinationType;
  readonly variant: ThirdPartyTransferVariant;
}

function destinationValue(data: TransferCaseData, type: DestinationType): string {
  return type === 'CBU'
    ? requiredTransferValue(data.destinationCbu, 'cbuDestino')
    : requiredTransferValue(data.destinationAlias, 'aliasDestino');
}

// Casos que además validan el descuento en la cuenta origen (validarDescuentoCuenta).
const WITH_ORIGIN_DEBIT: readonly ImmediateTransferCase[] = [
  {
    id: 'TC-015-1-0028-UNE',
    title: 'A cuentas de terceros CBU - mismo banco - Pesos',
    destinationType: 'CBU',
    variant: 'standard',
  },
  {
    id: 'TC-015-1-0034-UNE',
    title: 'A cuentas de terceros CVU - otros bancos - Pesos',
    destinationType: 'CBU',
    variant: 'standard',
  },
  {
    id: 'TC-015-1-0029-UNE',
    title: 'A cuentas de terceros ALIAS - mismo banco - Pesos',
    destinationType: 'ALIAS',
    variant: 'standard',
  },
  {
    id: 'TC-015-1-0014-UNE',
    title: 'ALIAS de CVU - terceros $ NO AGENDADOS',
    destinationType: 'ALIAS',
    variant: 'standard',
  },
  {
    id: 'TC-015-1-0005-UNE',
    title: 'A cuentas de terceros ALIAS - otros bancos - Pesos',
    destinationType: 'ALIAS',
    variant: 'standard',
  },
  {
    id: 'TC-015-1-0012-UNE',
    title: 'ALIAS - terceros otros bancos USD - NO AGENDADOS',
    destinationType: 'ALIAS',
    variant: 'standard',
  },
  {
    id: 'TC-015-1-0011-UNE',
    title: 'ALIAS - terceros mismo banco USD - NO AGENDADOS',
    destinationType: 'ALIAS',
    variant: 'usd',
  },
];

// Casos que sólo validan la aceptación de la transferencia.
const WITHOUT_BALANCE_CHECK: readonly ImmediateTransferCase[] = [
  {
    id: 'TC-015-1-0030-UNE',
    title: 'Transferencias Programadas (Fecha especifica) CBU - terceros mismo banco $ - NO AGENDADOS',
    destinationType: 'CBU',
    variant: 'standard',
  },
  {
    id: 'TC-015-1-0021-UNE',
    title: 'Transferencias Programadas (Fecha especifica) ALIAS - terceros mismo banco $ - NO AGENDADOS',
    destinationType: 'ALIAS',
    variant: 'standard',
  },
];

// Casos de "saldo actualizado" que además validan el incremento en destino (validarIncrementoEnCuenta).
const WITH_DESTINATION_CREDIT: readonly ImmediateTransferCase[] = [
  {
    id: 'TC-015-1-0043-UNE',
    title: 'Saldo actualizado - Transferencias a un Alias CVU en pesos',
    destinationType: 'ALIAS',
    variant: 'standard',
  },
  {
    id: 'TC-015-1-0044-UNE',
    title: 'Saldo actualizado - Transferencias a un Alias CBU en dólares',
    destinationType: 'ALIAS',
    variant: 'standard',
  },
];

// Casos sin descripción que validan el comprobante.
const WITHOUT_DESCRIPTION: readonly { readonly id: string; readonly title: string }[] = [
  {
    id: 'TC-015-1-0039-UNE',
    title: 'Transferencia a cuentas propias con CBU de otra entidad sin el campo Descripción al agendarse',
  },
  { id: 'TC-015-1-0041-UNE', title: 'Transferencia a Terceros con CBU de otra entidad sin el campo Descripción' },
];

test.describe('Transferencias inmediatas a terceros API de Banco Entre Ríos', () => {
  for (const transferCase of [...WITH_ORIGIN_DEBIT, ...WITH_DESTINATION_CREDIT]) {
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
        const destination = await test.step('Buscar el destino y quitarlo de la agenda', () =>
          thirdPartyTransferWorkflow.findDestinationOutsideAgenda(
            session,
            destinationValue(transferCaseData, transferCase.destinationType),
            transferCase.destinationType,
          ));
        const { debitCard, origin, products } = await test.step('Consultar los productos del usuario', () =>
          thirdPartyTransferWorkflow.loadOriginProducts(
            session,
            originAccount,
            requiredTransferValue(transferCaseData.debitCardLastDigits, 'tarjetaDebito'),
          ));
        const balanceBefore = ProductWidgetsApi.legacyBalanceUnits(products, originAccount);

        await test.step('Transferir al destinatario', () =>
          thirdPartyTransfersApi.transferToThirdParty(
            session,
            loginCredentials.username,
            transferCase.variant,
            destination,
            origin,
            debitCard,
          ));
        const balanceAfter = await test.step('Consultar el saldo actualizado', () =>
          thirdPartyTransferWorkflow.readBalanceAfter(session, originAccount));

        expect(
          balanceBefore - 1,
          'La cuenta origen debe reflejar el descuento de la transferencia.',
        ).toBeGreaterThanOrEqual(balanceAfter);

        if (WITH_DESTINATION_CREDIT.includes(transferCase)) {
          // El legado nunca mide el saldo destino en estos casos: compara sus valores iniciales (0).
          const destinationBefore = 0;
          const destinationAfter = 0;
          expect(
            destinationBefore + 1,
            'La cuenta destino debe reflejar el crédito (comparación legada).',
          ).toBeLessThanOrEqual(destinationAfter);
        }
      });
    });
  }

  for (const transferCase of WITHOUT_BALANCE_CHECK) {
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

        // El título legado dice "Programadas", pero el caso ejecuta una transferencia inmediata.
        await test.step('Transferir al destinatario', () =>
          thirdPartyTransfersApi.transferToThirdParty(
            session,
            loginCredentials.username,
            transferCase.variant,
            destination,
            origin,
            debitCard,
          ));
      });
    });
  }

  for (const transferCase of WITHOUT_DESCRIPTION) {
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
        const { debitCard, origin, products } = await test.step('Consultar los productos del usuario', () =>
          thirdPartyTransferWorkflow.loadOriginProducts(
            session,
            originAccount,
            requiredTransferValue(transferCaseData.debitCardLastDigits, 'tarjetaDebito'),
          ));
        // El legado lee el saldo inicial aunque este caso no lo compara.
        ProductWidgetsApi.legacyBalanceUnits(products, originAccount);
        const idTransaction = await test.step('Transferir sin descripción', () =>
          thirdPartyTransfersApi.transferToThirdParty(
            session,
            loginCredentials.username,
            'withoutDescription',
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
      });
    });
  }
});
