import { expect, test } from '../../../src/fixtures/ui-test';

const TEST_CASE_ID = 'TC-010-2-0001-UNE';

test.describe('Mis Productos UI de Banco Entre Ríos', () => {
  test.use({ testCaseId: TEST_CASE_ID });

  test(`${TEST_CASE_ID} - acepta la redirección a Baja y Arrepentimiento`, async ({
    cancellationPage,
    loginCredentials,
    loginPage,
    startPage,
  }) => {
    await startPage.open();
    await startPage.openLogin();
    await loginPage.authenticate(loginCredentials);

    await cancellationPage.open();

    expect(
      await cancellationPage.isConfirmationAvailable(),
      'Baja y Arrepentimiento debe mostrar el popup de confirmación.',
    ).toBe(true);

    const redirect = await cancellationPage.acceptExternalRedirect();
    expect(redirect.url).toBe('https://www.bancoentrerios.com.ar/baja-y-arrepentimiento');
    expect(redirect.title).toBe('Arrepentimiento y baja de productos | Banco Entre Rios');
  });
});
