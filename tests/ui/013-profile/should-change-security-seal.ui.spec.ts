import { expect, test } from '../../../src/fixtures/ui-test';

const TEST_CASE_ID = 'TC-013-2-0002-UNE';

test.describe('Cambio de sello de seguridad UI de Banco Entre Ríos', () => {
  test.use({ testCaseId: TEST_CASE_ID });
  test.setTimeout(180_000);

  test(`${TEST_CASE_ID} - cambia el avatar con segundo factor`, async ({
    loginApi,
    loginCredentials,
    loginPage,
    securitySealPage,
    smsOperationOtpApi,
    startPage,
  }) => {
    const initialSealId = await loginApi.readSecuritySealId(loginCredentials.username);
    const apiSession = await test.step('Autenticar al usuario', async () => {
      await startPage.open();
      await startPage.openLogin();
      await loginPage.authenticate(loginCredentials);
      return loginApi.authenticate(loginCredentials);
    });

    await securitySealPage.open();
    await securitySealPage.openSealChange();
    await securitySealPage.selectSeal(initialSealId > 1 ? initialSealId - 1 : initialSealId + 1);
    await securitySealPage.chooseSmsIfOtherMethodOffered();
    await securitySealPage.enterSecondFactorCode(
      await smsOperationOtpApi.resolve(apiSession, loginCredentials.username),
    );

    expect(
      await loginApi.readSecuritySealId(loginCredentials.username),
      'El id del sello de seguridad debe cambiar.',
    ).not.toBe(initialSealId);
  });
});
