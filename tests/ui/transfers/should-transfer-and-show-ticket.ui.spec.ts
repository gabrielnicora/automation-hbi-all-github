import { expect, test } from '../../../src/fixtures/ui-test';
import { requiredTransferValue } from '../../../src/test-data/transfer-case-data';

// Transferencias por UI con ticket (legado FrontTest + TransferenciasInmediatasSteps y TicketSteps).
const SUCCESS_STATUS = 'Tu solicitud se realizo con exito';

function todayAsLegacyDate(): string {
  const now = new Date();
  const pad = (value: number): string => String(value).padStart(2, '0');
  return `${pad(now.getDate())}/${pad(now.getMonth() + 1)}/${now.getFullYear()}`;
}

test.describe('Transferencias por UI con comprobante', () => {
  test.describe('TC1520006BERSA', () => {
    test.use({ testCaseId: 'TC1520006BERSA' });

    // El legado lee el saldo antes y después de transferir, pero no los compara.
    test('TC1520006BERSA - Saldo actualizado - Transferencias a cuentas propias en pesos del mismo banco', async ({
      loginCredentials,
      loginPage,
      startPage,
      transferCaseData,
      transferFormPage,
    }) => {
      await test.step('Autenticar al usuario', async () => {
        await startPage.open();
        await startPage.openLogin();
        await loginPage.authenticate(loginCredentials);
      });
      await test.step('Transferir 1000 a una cuenta propia', async () => {
        await transferFormPage.open();
        await transferFormPage.readOriginBalanceText();
        await transferFormPage.selectTransferType('ownAccount');
        await transferFormPage.selectFirstOriginAccount();
        await transferFormPage.selectOwnDestination(
          requiredTransferValue(transferCaseData.destinationAccount, 'ctaDestino'),
        );
        await transferFormPage.fillAmount('1000');
        await transferFormPage.selectConcept('Alquileres');
        await transferFormPage.fillDescription('TC18096');
        await transferFormPage.transferNow();
        await transferFormPage.confirm();
      });

      expect(await transferFormPage.readTicketStatus(), 'La transferencia debe finalizar con éxito.').toBe(
        SUCCESS_STATUS,
      );

      await test.step('Volver y releer el saldo', async () => {
        await transferFormPage.goBack();
        await transferFormPage.readOriginBalanceText();
      });
    });
  });

  test.describe('TC1520007BERSA', () => {
    test.use({ testCaseId: 'TC1520007BERSA' });

    test('TC1520007BERSA - Transferencias a CBU - Historial de transferencias', async ({
      loginApi,
      loginCredentials,
      loginPage,
      smsOperationOtpApi,
      startPage,
      thirdPartyTransferWorkflow,
      transferCaseData,
      transferFormPage,
      transferHistoryPage,
    }) => {
      const cbu = requiredTransferValue(transferCaseData.destinationCbu, 'cbuDestino');
      const apiSession = await test.step('Autenticar al usuario', async () => {
        await startPage.open();
        await startPage.openLogin();
        await loginPage.authenticate(loginCredentials);
        return loginApi.authenticate(loginCredentials);
      });
      await test.step('Quitar el CBU de la agenda por API', () =>
        thirdPartyTransferWorkflow.findDestinationOutsideAgenda(apiSession, cbu, 'CBU'));
      await test.step('Transferir 1 al CBU', async () => {
        await transferFormPage.open();
        await transferFormPage.selectTransferType('thirdParty');
        await transferFormPage.searchRecipient(cbu);
        await transferFormPage.fillAmount('1');
        await transferFormPage.selectConcept('Alquileres');
        await transferFormPage.fillDescription('TC18099');
        await transferFormPage.transferNow();
        await transferFormPage.confirmAndWaitForSecondFactor();
        await transferFormPage.enterSecondFactorCode(
          await smsOperationOtpApi.resolve(apiSession, loginCredentials.username),
        );
      });

      expect(await transferFormPage.readTicketStatus(), 'La transferencia debe finalizar con éxito.').toBe(
        SUCCESS_STATUS,
      );
      expect(await transferFormPage.downloadTicket(), 'Debe descargarse el ticket.').toContain('Ticket.pdf');

      await test.step('Abrir el historial filtrado', async () => {
        await transferFormPage.goBack();
        await transferFormPage.openHistory();
        await transferHistoryPage.openFilters();
        await transferHistoryPage.readDateFilters();
      });

      expect(await transferHistoryPage.countRows(), 'La grilla no debe superar diez registros.').toBeLessThanOrEqual(
        10,
      );
      expect(await transferHistoryPage.isPaginatorShown(), 'Debe verse el paginador.').toBe(true);
    });
  });

  test.describe('TC1520005BERSA', () => {
    test.use({ testCaseId: 'TC1520005BERSA' });

    test('TC1520005BERSA - Cambios en comprobante - TF inmediatas a terceros - Todos los campos - CBU otra entidad', async ({
      loginApi,
      loginCredentials,
      loginPage,
      smsOperationOtpApi,
      startPage,
      thirdPartyTransferWorkflow,
      transferCaseData,
      transferFormPage,
    }) => {
      const cbu = requiredTransferValue(transferCaseData.destinationCbu, 'cbuDestino');
      const apiSession = await test.step('Autenticar al usuario', async () => {
        await startPage.open();
        await startPage.openLogin();
        await loginPage.authenticate(loginCredentials);
        return loginApi.authenticate(loginCredentials);
      });
      await test.step('Quitar el CBU de la agenda por API', () =>
        thirdPartyTransferWorkflow.findDestinationOutsideAgenda(apiSession, cbu, 'CBU'));
      const transferDate = await test.step('Transferir 1 al CBU', async () => {
        await transferFormPage.open();
        await transferFormPage.selectTransferType('thirdParty');
        await transferFormPage.searchRecipient(cbu);
        await transferFormPage.fillAmount('1');
        await transferFormPage.selectConcept('Alquileres');
        await transferFormPage.fillDescription('TC18092');
        const date = todayAsLegacyDate();
        await transferFormPage.transferNow();
        await transferFormPage.confirmAndWaitForSecondFactor();
        await transferFormPage.enterSecondFactorCode(
          await smsOperationOtpApi.resolve(apiSession, loginCredentials.username),
        );
        return date;
      });

      expect(await transferFormPage.readTicketStatus(), 'La transferencia debe finalizar con éxito.').toBe(
        SUCCESS_STATUS,
      );

      // Valores fijos del legado para el usuario y destino de este caso.
      const expectedFields: readonly (readonly [string, string])[] = [
        ['Fecha', transferDate],
        ['Monto', '$ 1,00'],
        ['Emisor', 'REYES, JOSE LUIS'],
        ['Cuenta de origen', 'CC $ 035003401876'],
        ['CUIT/CUIL emisor', '20119120269'],
        ['Destinatario', 'PEREZ, ROQUE'],
        ['CBU/CVU destino', '3300000620000035540021'],
        ['CUIT/CUIL destino', '20025000014'],
        ['Entidad destino', 'NUEVO BANCO DE SANTA FE SOCIEDAD ANONIMA'],
        ['Concepto', 'Alquileres'],
        ['Descripción', 'TC18092'],
      ];

      for (const [label, value] of expectedFields) {
        expect(await transferFormPage.readTicketField(label), `Campo "${label}" del ticket`).toContain(value);
      }
    });
  });
});
