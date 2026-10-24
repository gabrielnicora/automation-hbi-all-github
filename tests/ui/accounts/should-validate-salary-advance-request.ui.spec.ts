import { expect, test } from '../../../src/fixtures/ui-test';
import { expectSalaryAdvanceRequestScreen } from './salary-advance-request-screen';

const REQUESTED_AMOUNT = '10000';
const TERMS_REQUIRED_TEXT = 'Leer y aceptar los términos y condiciones es obligatorio para continuar';

test.describe('Solicitud de adelanto de sueldo UI de Banco Entre Ríos', () => {
  test.setTimeout(180_000);

  test.describe('TC0222BERSA', () => {
    test.use({ testCaseId: 'TC0222BERSA' });

    test('TC0222BERSA - muestra la pantalla completa de solicitud de adelanto', async ({
      homePage,
      loginCredentials,
      loginPage,
      originAccountUiData,
      salaryAdvancePage,
      startPage,
    }) => {
      await startPage.open();
      await startPage.openLogin();
      await loginPage.authenticate(loginCredentials);
      expect(await homePage.readGreeting(), 'El escritorio debe saludar con "Hola".').toContain('Hola');

      await salaryAdvancePage.openRequest(originAccountUiData.originAccount);

      await expectSalaryAdvanceRequestScreen(salaryAdvancePage);
    });
  });

  test.describe('TC0225BERSA', () => {
    test.use({ testCaseId: 'TC0225BERSA' });

    test('TC0225BERSA - vuelve a la solicitud al presionar Volver', async ({
      loginCredentials,
      loginPage,
      originAccountUiData,
      salaryAdvancePage,
      startPage,
    }) => {
      await startPage.open();
      await startPage.openLogin();
      await loginPage.authenticate(loginCredentials);

      await salaryAdvancePage.openRequest(originAccountUiData.originAccount);
      await salaryAdvancePage.fillRequestedAmount(REQUESTED_AMOUNT);
      await salaryAdvancePage.pressContinue();
      await salaryAdvancePage.goBackWithArrow();

      await expectSalaryAdvanceRequestScreen(salaryAdvancePage);
    });
  });

  test.describe('TC0226BERSA', () => {
    test.use({ testCaseId: 'TC0226BERSA' });

    test('TC0226BERSA - exige aceptar términos y condiciones al confirmar', async ({
      loginCredentials,
      loginPage,
      originAccountUiData,
      salaryAdvancePage,
      startPage,
    }) => {
      await startPage.open();
      await startPage.openLogin();
      await loginPage.authenticate(loginCredentials);

      await salaryAdvancePage.openRequest(originAccountUiData.originAccount);
      await salaryAdvancePage.fillRequestedAmount(REQUESTED_AMOUNT);
      await salaryAdvancePage.pressContinue();
      await salaryAdvancePage.pressConfirm();

      expect(await salaryAdvancePage.isTextShown(TERMS_REQUIRED_TEXT), 'Debe exigir aceptar los términos.').toBe(true);
    });
  });

  for (const { testCaseId, amount, description } of [
    { testCaseId: 'TC0237BERSA', amount: '0', description: 'inferior al mínimo' },
    { testCaseId: 'TC0238BERSA', amount: '9999999', description: 'superior al máximo' },
  ] as const) {
    test.describe(testCaseId, () => {
      test.use({ testCaseId });

      test(`${testCaseId} - informa error con un importe ${description}`, async ({
        loginCredentials,
        loginPage,
        originAccountUiData,
        salaryAdvancePage,
        startPage,
      }) => {
        await startPage.open();
        await startPage.openLogin();
        await loginPage.authenticate(loginCredentials);

        await salaryAdvancePage.openRequest(originAccountUiData.originAccount);
        await salaryAdvancePage.fillRequestedAmount(amount);
        await salaryAdvancePage.pressContinue();

        expect(await salaryAdvancePage.isAmountErrorShown(), 'Debe informar el importe no permitido.').toBe(true);
      });
    });
  }
});
