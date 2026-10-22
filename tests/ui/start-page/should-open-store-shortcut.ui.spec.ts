import { expect, test } from '../../../src/fixtures/ui-test';

const TEST_CASE_ID = 'TC1420001BERSA';

test.describe('Pantalla principal UI de Banco Entre Ríos', () => {
  test.use({ testCaseId: TEST_CASE_ID });
  test.setTimeout(180_000);

  test(`${TEST_CASE_ID} - abre el acceso directo Tienda.Clic`, async ({ loginPage, startPage }) => {
    await startPage.open();
    await startPage.openLogin();

    const storePage = await loginPage.openStoreShortcut();

    expect(storePage.hasStoreLogo, 'La página de Tienda.Clic debe mostrar su logo.').toBe(true);
  });
});
