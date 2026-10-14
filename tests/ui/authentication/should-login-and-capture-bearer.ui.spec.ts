import { expect, test } from '../../../src/fixtures/ui-test';

const TEST_CASE_ID = 'TC0017BERSA';

test.describe('Login UI de Banco Entre Ríos', () => {
  test.use({ testCaseId: TEST_CASE_ID });
  test.setTimeout(180_000);

  test(
    `${TEST_CASE_ID} - inicia sesión y captura el Bearer token`,
    { tag: '@regression' },
    async ({ loginCredentials, loginPage, startPage }) => {
      await startPage.open();
      await startPage.openLogin();

      const authenticatedSession = await loginPage.authenticate(loginCredentials);

      expect(
        authenticatedSession.accessToken.trim().length > 0,
        'El login debe devolver un access token no vacío.',
      ).toBe(true);
      expect(
        authenticatedSession.authorizationHeader === `Bearer ${authenticatedSession.accessToken}`,
        'El header Bearer debe corresponder al access token recibido.',
      ).toBe(true);
    },
  );
});
