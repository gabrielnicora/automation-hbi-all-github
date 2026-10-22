import { expect, test } from '../../../src/fixtures/ui-test';

test.describe('Alta de contacto con CBU inválido UI de Banco Entre Ríos', () => {
  const TEST_CASE_ID = 'TC1520001BERSA';
  test.use({ testCaseId: TEST_CASE_ID });
  test.setTimeout(180_000);

  test(`${TEST_CASE_ID} - informa CBU inválido al agendar un contacto`, async ({
    addContactPage,
    contactSearchUiData,
    loginCredentials,
    loginPage,
    startPage,
  }) => {
    await startPage.open();
    await startPage.openLogin();
    await loginPage.authenticate(loginCredentials);

    await addContactPage.open();
    await addContactPage.search(contactSearchUiData.cbu);

    expect(await addContactPage.isMessageShown('CBU inválido'), 'Debe informar CBU inválido.').toBe(true);
  });
});

test.describe('Alta de contacto con alias inválido UI de Banco Entre Ríos', () => {
  const TEST_CASE_ID = 'TC1520002BERSA';
  test.use({ testCaseId: TEST_CASE_ID });
  test.setTimeout(180_000);

  test(`${TEST_CASE_ID} - informa alias inválido al agendar un contacto`, async ({
    addContactPage,
    contactSearchUiData,
    loginCredentials,
    loginPage,
    startPage,
  }) => {
    await startPage.open();
    await startPage.openLogin();
    await loginPage.authenticate(loginCredentials);

    await addContactPage.open();
    await addContactPage.search(contactSearchUiData.alias);

    expect(await addContactPage.isMessageShown('Alias inválido'), 'Debe informar alias inválido.').toBe(true);
  });
});
