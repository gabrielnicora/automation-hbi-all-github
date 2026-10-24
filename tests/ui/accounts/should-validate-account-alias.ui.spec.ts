import { expect, test } from '../../../src/fixtures/ui-test';

const TEST_CASE_ID = 'TC128241BERSA';
const SHORT_ALIAS = 'test';
const LONG_ALIAS = 'qweryyhtfdsxggtjhgdeghii';
const INVALID_ALIAS = '!!!!!!!!!!!!!!!!';

test.describe('Validación de alias de cuenta UI de Banco Entre Ríos', () => {
  test.use({ testCaseId: TEST_CASE_ID });

  test(`${TEST_CASE_ID} - Web - Modif. Alias - Validar controles del campo mandatorio Alias`, async ({
    accountAliasPage,
    accountAliasUiData,
    loginCredentials,
    loginPage,
    startPage,
  }) => {
    await startPage.open();
    await startPage.openLogin();
    await loginPage.authenticate(loginCredentials);
    await accountAliasPage.openAliasEditor(accountAliasUiData.account);

    expect(await accountAliasPage.isSaveDisabled(), 'Guardar debe iniciar deshabilitado.').toBe(true);

    await accountAliasPage.enterAlias(SHORT_ALIAS);
    expect(
      await accountAliasPage.isSaveDisabled(),
      'Guardar debe permanecer deshabilitado con menos de 6 caracteres.',
    ).toBe(true);

    await accountAliasPage.enterAlias(LONG_ALIAS);
    await accountAliasPage.enterAlias(INVALID_ALIAS);

    expect(
      await accountAliasPage.isSaveDisabled(),
      'Guardar debe permanecer deshabilitado después de ingresar un alias inválido.',
    ).toBe(true);
  });
});
