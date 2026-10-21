import { expect, test } from '../../../src/fixtures/ui-test';

const TEST_CASE_ID = 'TC0077BERSA';
const PRODUCT = 'Alta Cuenta Comitente desde Home Banking';

test.describe('Administración de productos UI de Banco Entre Ríos', () => {
  test.use({ testCaseId: TEST_CASE_ID });

  test(`${TEST_CASE_ID} - descarga el contrato del producto`, async ({
    loginCredentials,
    loginPage,
    productDocumentsPage,
    startPage,
  }) => {
    await startPage.open();
    await startPage.openLogin();
    await loginPage.authenticate(loginCredentials);

    await productDocumentsPage.open();
    await productDocumentsPage.selectProduct(PRODUCT);
    const download = await productDocumentsPage.downloadDocument();

    expect(download.suggestedFilename(), 'Debe descargar el contrato del producto.').toContain(`${PRODUCT} .pdf`);
  });
});
