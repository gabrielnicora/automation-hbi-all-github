import { ProductWidgetsApi } from '../../../src/api/accounts/product-widgets.api';
import { expect, test } from '../../../src/fixtures/api-test';
import { requiredTransferValue } from '../../../src/test-data/transfer-case-data';

// Legado: quitar el CBU de la agenda, agendarlo con el ID del caso como referencia, transferir
// ARS/USD 1 al contacto agendado (sin OTP) y validar el descuento en la cuenta origen.
const ADHERED_CONTACT_TRANSFERS = [
  { id: 'TC1510006BERSA', title: 'TRANSFERENCIAS - terceros mismo banco $ - AGENDADOS' },
  { id: 'TC1510008BERSA', title: 'Transferencias - terceros otros bancos $ - AGENDADOS' },
  { id: 'TC1510013BERSA', title: 'Transferencias CVU - terceros $ AGENDADOS' },
  { id: 'TC1510007BERSA', title: 'Transferencias - terceros mismo banco USD - AGENDADOS' },
  { id: 'TC1510009BERSA', title: 'Transferencias - terceros mismo banco USD - AGENDADOS' },
] as const;

test.describe('Transferencias a contactos agendados API de Banco Entre Ríos', () => {
  for (const transferCase of ADHERED_CONTACT_TRANSFERS) {
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
            transferCase.id,
            previous,
          ));
        const { debitCard, origin, products } = await test.step('Consultar los productos del usuario', () =>
          thirdPartyTransferWorkflow.loadOriginProducts(
            session,
            originAccount,
            requiredTransferValue(transferCaseData.debitCardLastDigits, 'tarjetaDebito'),
          ));
        const balanceBefore = ProductWidgetsApi.legacyBalanceUnits(products, originAccount);

        await test.step('Transferir al contacto agendado', () =>
          thirdPartyTransfersApi.transferToAdheredContact(
            session,
            destination,
            idAdheredAccount,
            origin,
            debitCard,
            transferCase.id,
          ));
        const balanceAfter = await test.step('Consultar el saldo actualizado', () =>
          thirdPartyTransferWorkflow.readBalanceAfter(session, originAccount));

        expect(
          balanceBefore - 1,
          'La cuenta origen debe reflejar el descuento de la transferencia.',
        ).toBeGreaterThanOrEqual(balanceAfter);
      });
    });
  }
});
