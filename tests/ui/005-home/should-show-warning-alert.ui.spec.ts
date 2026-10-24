import { expect, test } from '../../../src/fixtures/ui-test';

const TEST_CASE_ID = 'TC-005-2-0001-UNE';

// Chromium devuelve el color opaco como rgb(); equivale al rgba(255, 241, 184, 1) del legado.
const WARNING_BACKGROUND_COLOR = 'rgb(255, 241, 184)';

test.describe('Aviso del inicio UI de Banco Entre Ríos', () => {
  test.use({ testCaseId: TEST_CASE_ID });
  test.setTimeout(180_000);

  test(`${TEST_CASE_ID} - muestra el aviso en formato advertencia`, async ({
    homePage,
    loginCredentials,
    loginPage,
    startPage,
  }) => {
    await startPage.open();
    await startPage.openLogin();
    await loginPage.authenticate(loginCredentials);

    const alert = await homePage.readAlert();

    expect(alert.hasIcon, 'El aviso debe mostrar su icono.').toBe(true);
    expect(alert.backgroundColor, 'El aviso debe usar el color de advertencia.').toBe(WARNING_BACKGROUND_COLOR);
  });
});
