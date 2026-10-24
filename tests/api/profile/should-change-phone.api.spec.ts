import { expect, test } from '../../../src/fixtures/api-test';

const TEST_CASE_ID = 'TC0001BERSA';

test.describe('Cambio de teléfono por API de Banco Entre Ríos', () => {
  test.setTimeout(120000);
  test.use({ testCaseId: TEST_CASE_ID });

  test(`${TEST_CASE_ID} - cambia el teléfono y verifica que quedó persistido`, async ({
    loginApi,
    loginCredentials,
    phoneChangeApi,
  }) => {
    const session = await test.step('Autenticar al usuario', () => loginApi.authenticate(loginCredentials));

    const result = await test.step('Cambiar el teléfono y releer el perfil', () =>
      phoneChangeApi.changePhone(session, loginCredentials.username));

    expect(result.isPersisted, 'El teléfono nuevo debe quedar persistido en el perfil.').toBe(true);
  });
});
