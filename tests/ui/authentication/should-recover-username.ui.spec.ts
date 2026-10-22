import { expect, test } from '../../../src/fixtures/ui-test';

const RECOVERY_CASES = [
  { testCaseId: 'TC0220002BERSA', cuil: '20136570820', checksContinueState: false },
  { testCaseId: 'TC0220004BERSA', cuil: '20080010045', checksContinueState: true },
] as const;

for (const { testCaseId, cuil, checksContinueState } of RECOVERY_CASES) {
  test.describe(`Recupero de usuario UI de Banco Entre Ríos — ${testCaseId}`, () => {
    test.use({ testCaseId });
    test.setTimeout(180_000);

    test(`${testCaseId} - envía el usuario por correo y permite volver a ingresar`, async ({
      homePage,
      loginApi,
      loginCredentials,
      loginPage,
      profileMobilePhoneApi,
      secondFactorChallengeApi,
      startPage,
    }) => {
      const apiSession = await test.step('Leer el email del usuario por API', () =>
        loginApi.authenticate(loginCredentials));
      const email = await profileMobilePhoneApi.readCurrentEmail(apiSession);

      await startPage.open();
      await startPage.openLogin();
      await loginPage.typeUsername(loginCredentials.username);
      await loginPage.openUserRecovery();

      if (checksContinueState) {
        expect(await loginPage.isRecoveryContinueEnabled(), 'Sin CUIL, Continuar debe estar deshabilitado.').toBe(
          false,
        );
      }

      await loginPage.fillRecoveryCuil(cuil);
      const recoveryMail = await secondFactorChallengeApi.requestSingleCode(
        apiSession,
        { origin: email, property: 'codeNoFormat' },
        async () => {
          await loginPage.continueUserRecovery();
          for (const text of ['Tu solicitud', 'Enviamos tu usuario a']) {
            expect(await loginPage.isTextShown(text), `El recupero debe mostrar "${text}".`).toBe(true);
          }
        },
      );

      expect(recoveryMail.startsWith('¡Hola'), 'El último correo debe ser el de recupero de usuario.').toBe(true);
      expect(recoveryMail.split('<br/>')[2], 'El correo debe informar el usuario.').toBe(
        `Tu usuario de Home Banking es: ${loginCredentials.username.toLowerCase()}`,
      );

      await loginPage.returnToSignIn();
      await startPage.openLogin();
      await loginPage.authenticate(loginCredentials);
      expect(await homePage.readGreeting(), 'Debe ingresar al escritorio.').toContain('Hola');
    });
  });
}
