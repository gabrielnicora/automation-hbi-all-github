import { expect, test } from '../../../src/fixtures/api-test';

const TEST_CASE_ID = 'TC0102BERSA';

test.describe('Perfil API de Banco Entre Ríos', () => {
  test.use({ testCaseId: TEST_CASE_ID });

  test(`${TEST_CASE_ID} - consulta la configuración de notificaciones`, async ({
    loginApi,
    loginCredentials,
    notificationPreferencesApi,
  }) => {
    const session = await test.step('Autenticar al usuario', () => loginApi.authenticate(loginCredentials));
    const result = await test.step('Consultar las preferencias de notificaciones', () =>
      notificationPreferencesApi.getMessagePreferences(session));

    expect(result.hasSmsPreference, 'La configuración debe informar la preferencia SMS.').toBe(true);
    expect(result.hasMailPreference, 'La configuración debe informar la preferencia de mail.').toBe(true);
    expect(result.hasPushPreference, 'La configuración debe informar la preferencia push.').toBe(true);
  });
});
