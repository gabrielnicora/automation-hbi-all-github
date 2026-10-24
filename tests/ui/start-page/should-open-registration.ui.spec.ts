import { expect, test } from '../../../src/fixtures/ui-test';

const TEST_CASE_ID = 'TC0156BERSA';

test.describe('Pantalla inicial UI de Banco Entre Ríos', () => {
  test.use({ testCaseId: TEST_CASE_ID });

  test(`${TEST_CASE_ID} - acceso directo ¿No sos cliente aún? Registrarme`, async ({
    identityValidationPage,
    startPage,
  }) => {
    await test.step('Abrir el registro desde la pantalla inicial', async () => {
      await startPage.open();

      expect(
        await startPage.isRegistrationAvailable(),
        'La pantalla inicial debe mostrar el enlace para registrarse.',
      ).toBe(true);

      await startPage.openRegistration();
    });

    expect(
      await identityValidationPage.isAvailable(),
      'La pantalla de registro debe solicitar la validación de identidad.',
    ).toBe(true);
  });
});
