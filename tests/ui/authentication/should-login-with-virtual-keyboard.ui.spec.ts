import { expect, test } from '../../../src/fixtures/ui-test';

const TEST_CASE_ID = 'TC0220003BERSA';

test.describe('Login UI con teclado virtual de Banco Entre Ríos', () => {
  test.use({ testCaseId: TEST_CASE_ID });
  test.setTimeout(180_000);

  test(`${TEST_CASE_ID} - inicia sesión con el teclado virtual y muestra el saludo`, async ({
    homePage,
    loginCredentials,
    loginPage,
    startPage,
  }) => {
    await startPage.open();
    await startPage.openLogin();
    await loginPage.authenticateWithVirtualKeyboard(loginCredentials);

    await homePage.open();
    const greeting = await homePage.readGreeting();

    expect(greeting, 'Tras el login el inicio debe saludar al cliente.').toMatch(/^Hola\b/);
  });
});
