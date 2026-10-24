import { expect, test } from '../../../src/fixtures/ui-test';
import { requiredTransferValue } from '../../../src/test-data/transfer-case-data';

// VU Fraud: luego de la operación, utilLogger debe registrar en /FraudAnalysis/service/analyze el
// optionalParam metodoValidacionFP con valor DESCONOCIDO (legado UtilLoggerSteps.clickRequestSvc).
const FRAUD_SERVICE = '/FraudAnalysis/service/analyze';
const EMPTY_AGENDA_TEXT = 'No se registran cuentas agendadas';

interface OptionalParam {
  readonly name?: unknown;
  readonly value?: unknown;
}

/** Valor de metodoValidacionFP en el primer request que lo informa (`undefined` si ninguno). */
function deviceValidationValue(requestBodies: readonly string[]): unknown {
  for (const body of requestBodies) {
    let parsed: unknown;

    try {
      parsed = JSON.parse(body);
    } catch {
      continue;
    }

    const params = (parsed as { optionalParams?: unknown }).optionalParams;
    const match = Array.isArray(params)
      ? (params as OptionalParam[]).find((param) => param?.name === 'metodoValidacionFP')
      : undefined;

    if (match) {
      return match.value;
    }
  }

  return undefined;
}

test.describe('VU Fraud - validación del dispositivo por UI', () => {
  test.beforeEach(async ({ loginCredentials, loginPage, startPage }) => {
    await test.step('Autenticar al usuario', async () => {
      await startPage.open();
      await startPage.openLogin();
      await loginPage.authenticate(loginCredentials);
    });
  });

  test.describe('TC-015-2-0010-UNE', () => {
    test.use({ testCaseId: 'TC-015-2-0010-UNE' });

    test('TC-015-2-0010-UNE - VU Fraud - adhesionAccount envía metodoValidacionFP', async ({
      contactAgendaPage,
      loginApi,
      loginCredentials,
      smsOperationOtpApi,
      transferCaseData,
      transferFormPage,
      utilLoggerPage,
    }) => {
      const cbu = requiredTransferValue(transferCaseData.destinationCbu, 'cbuDestino');
      const apiSession = await loginApi.authenticate(loginCredentials);
      await test.step('Agendar el contacto', async () => {
        await transferFormPage.open();
        await transferFormPage.selectTransferType('scheduledContact');
        await transferFormPage.openFullAgenda();
        await contactAgendaPage.addContact(cbu);
        await contactAgendaPage.enterSecondFactorCode(
          await smsOperationOtpApi.resolve(apiSession, loginCredentials.username),
        );
      });
      await test.step('Volver y eliminar el contacto', async () => {
        await transferFormPage.goBack();
        await transferFormPage.selectTransferType('scheduledContact');
        await transferFormPage.openFullAgenda();
        await contactAgendaPage.removeContactIfListed(cbu, EMPTY_AGENDA_TEXT);
      });
      const requests = await test.step('Leer el análisis de fraude en utilLogger', () =>
        utilLoggerPage.readServiceRequests(FRAUD_SERVICE));

      expect(requests.length, `Debe haber requests para ${FRAUD_SERVICE}.`).toBeGreaterThan(0);
      expect(deviceValidationValue(requests), 'metodoValidacionFP debe informar DESCONOCIDO.').toBe('DESCONOCIDO');
    });
  });

  test.describe('TC-015-2-0011-UNE', () => {
    test.use({ testCaseId: 'TC-015-2-0011-UNE' });

    test('TC-015-2-0011-UNE - VU Fraud - transfers.account.makeWithCaps envía metodoValidacionFP', async ({
      contactAgendaPage,
      loginApi,
      loginCredentials,
      smsOperationOtpApi,
      transferCaseData,
      transferFormPage,
      utilLoggerPage,
    }) => {
      const cbu = requiredTransferValue(transferCaseData.destinationCbu, 'cbuDestino');
      const apiSession = await loginApi.authenticate(loginCredentials);
      await test.step('Agendar el contacto y transferirle 1', async () => {
        await transferFormPage.open();
        await transferFormPage.selectTransferType('scheduledContact');
        await transferFormPage.openFullAgenda();
        await contactAgendaPage.addContact(cbu);
        await contactAgendaPage.enterSecondFactorCode(
          await smsOperationOtpApi.resolve(apiSession, loginCredentials.username),
        );
        await contactAgendaPage.transferToThisContact();
        await transferFormPage.fillAmount('1');
        await transferFormPage.selectConcept('Alquileres');
        await transferFormPage.fillDescription('128247');
        await transferFormPage.transferNow();
        await transferFormPage.confirm();
      });
      await test.step('Volver y eliminar el contacto', async () => {
        await transferFormPage.goBack();
        await transferFormPage.selectTransferType('scheduledContact');
        await transferFormPage.openFullAgenda();
        await contactAgendaPage.removeContactIfListed(cbu, EMPTY_AGENDA_TEXT);
      });
      const requests = await test.step('Leer el análisis de fraude en utilLogger', () =>
        utilLoggerPage.readServiceRequests(FRAUD_SERVICE));

      expect(requests.length, `Debe haber requests para ${FRAUD_SERVICE}.`).toBeGreaterThan(0);
      expect(deviceValidationValue(requests), 'metodoValidacionFP debe informar DESCONOCIDO.').toBe('DESCONOCIDO');
    });
  });

  test.describe('TC-015-2-0012-UNE', () => {
    test.use({ testCaseId: 'TC-015-2-0012-UNE' });

    test('TC-015-2-0012-UNE - VU Fraud - credin.generate envía metodoValidacionFP', async ({
      credinRequestPage,
      loginApi,
      loginCredentials,
      smsOperationOtpApi,
      transferCaseData,
      transferFormPage,
      utilLoggerPage,
    }) => {
      const apiSession = await loginApi.authenticate(loginCredentials);
      await test.step('Abrir Generar CREDIN', async () => {
        await transferFormPage.open();
        await transferFormPage.openCredins();
        await credinRequestPage.open();
      });

      // El legado sólo comprueba que el desplegable ofrezca la cuenta y el concepto; no los selecciona.
      expect(
        await credinRequestPage.isDebitAccountOffered(
          requiredTransferValue(transferCaseData.originAccount, 'ctaOrigen'),
        ),
        'Debe ofrecerse la cuenta débito del caso.',
      ).toBe(true);

      await credinRequestPage.fillDestinationCbu(requiredTransferValue(transferCaseData.destinationCbu, 'cbuDestino'));

      expect(await credinRequestPage.isConceptOffered('Alquileres'), 'Debe ofrecerse el concepto Alquileres.').toBe(
        true,
      );

      await test.step('Completar y confirmar el CREDIN', async () => {
        await credinRequestPage.fillAmountAndDescription('1', 'Test0014');
        await credinRequestPage.continueAsOwnAccount();
        await credinRequestPage.enterSecondFactorCode(
          await smsOperationOtpApi.resolve(apiSession, loginCredentials.username),
        );
      });
      const requests = await test.step('Leer el análisis de fraude en utilLogger', () =>
        utilLoggerPage.readServiceRequests(FRAUD_SERVICE));

      expect(requests.length, `Debe haber requests para ${FRAUD_SERVICE}.`).toBeGreaterThan(0);
      expect(deviceValidationValue(requests), 'metodoValidacionFP debe informar DESCONOCIDO.').toBe('DESCONOCIDO');
    });
  });

  test.describe('TC-015-2-0013-UNE', () => {
    test.use({ testCaseId: 'TC-015-2-0013-UNE' });

    test('TC-015-2-0013-UNE - VU Fraud - transfers.account.scheduleMakeWithCaps envía metodoValidacionFP', async ({
      contactAgendaPage,
      loginApi,
      loginCredentials,
      smsOperationOtpApi,
      transferCaseData,
      transferFormPage,
      utilLoggerPage,
    }) => {
      const cbu = requiredTransferValue(transferCaseData.destinationCbu, 'cbuDestino');
      const apiSession = await loginApi.authenticate(loginCredentials);
      await test.step('Eliminar el contacto de la agenda si estaba', async () => {
        await transferFormPage.open();
        await transferFormPage.selectTransferType('scheduledContact');
        await transferFormPage.openFullAgenda();
        await contactAgendaPage.removeContactIfListed(cbu, EMPTY_AGENDA_TEXT);
        await transferFormPage.goBackWithArrow();
      });
      await test.step('Programar una transferencia de 100 al CBU', async () => {
        await transferFormPage.selectTransferType('thirdParty');
        await transferFormPage.searchRecipient(cbu);
        await transferFormPage.fillAmount('100');
        await transferFormPage.selectConcept('Alquileres');
        await transferFormPage.fillDescription('automation');
        await transferFormPage.scheduleForSpecificDate();
        await transferFormPage.confirmAndWaitForSecondFactor();
        await transferFormPage.enterSecondFactorCode(
          await smsOperationOtpApi.resolve(apiSession, loginCredentials.username),
        );
      });
      const requests = await test.step('Leer el análisis de fraude en utilLogger', () =>
        utilLoggerPage.readServiceRequests(FRAUD_SERVICE));

      expect(requests.length, `Debe haber requests para ${FRAUD_SERVICE}.`).toBeGreaterThan(0);
      expect(deviceValidationValue(requests), 'metodoValidacionFP debe informar DESCONOCIDO.').toBe('DESCONOCIDO');
    });
  });
});
