import { expect, test } from '../../../src/fixtures/ui-test';

const TEST_CASE_ID = 'TC0720010BERSA';

test.describe('Alta de cuenta comitente por UI', () => {
  test.use({ testCaseId: TEST_CASE_ID });

  test(`${TEST_CASE_ID} - Inversiones - Alta cuenta comitente - Validar pantalla etapa 5 'Confirmación'`, async ({
    commitentAccountOpeningPage,
    investmentFundsPage,
    loginCredentials,
    loginPage,
    startPage,
  }) => {
    await test.step('Autenticar al usuario', async () => {
      await startPage.open();
      await startPage.openLogin();
      await loginPage.authenticate(loginCredentials);
    });
    await test.step('Iniciar la solicitud de cuenta comitente', async () => {
      await investmentFundsPage.open();
      await investmentFundsPage.startCommitentAccountRequest();
      await commitentAccountOpeningPage.requestAccount();
    });

    expect(
      await commitentAccountOpeningPage.isTextShown('Apertura de cuenta comitente'),
      'Debe mostrarse la apertura de cuenta comitente.',
    ).toBe(true);

    await test.step('Completar declaraciones y términos', async () => {
      await commitentAccountOpeningPage.continue();
      await commitentAccountOpeningPage.declareNoneOfTheAbove();
      await commitentAccountOpeningPage.continue();
      await commitentAccountOpeningPage.checkTermsAndConditions();
      await commitentAccountOpeningPage.continue();
      await commitentAccountOpeningPage.acceptTermsAndConditions();
      await commitentAccountOpeningPage.confirm();
    });

    expect(
      await commitentAccountOpeningPage.isStepShown('5', 'Confirmación'),
      'Debe verse la etapa 5 "Confirmación".',
    ).toBe(true);
    expect(
      await commitentAccountOpeningPage.isTextShown('Apertura de cuenta comitente'),
      'Debe mantenerse el título de la apertura.',
    ).toBe(true);
    expect(
      await commitentAccountOpeningPage.isTextShown('Estás por confirmar la solicitud de Alta de Cuenta Comitente'),
      'Debe mostrarse el texto de confirmación de la solicitud.',
    ).toBe(true);
  });
});
