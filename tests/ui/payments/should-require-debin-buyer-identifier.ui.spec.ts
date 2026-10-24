import { expect, test } from '../../../src/fixtures/ui-test';

test.describe('Generación de DEBIN vendedor por CBU UI de Banco Entre Ríos', () => {
  const TEST_CASE_ID = 'TC0183BERSA';
  test.use({ testCaseId: TEST_CASE_ID });
  test.setTimeout(180_000);

  test(`${TEST_CASE_ID} - exige el CBU del comprador al buscar sin ingresarlo`, async ({
    debinSellerPage,
    loginCredentials,
    loginPage,
    originAccountUiData,
    startPage,
  }) => {
    await startPage.open();
    await startPage.openLogin();
    await loginPage.authenticate(loginCredentials);

    await debinSellerPage.open();
    await debinSellerPage.openOrderGeneration();
    await debinSellerPage.selectDestinationAccount(originAccountUiData.originAccount);
    await debinSellerPage.enterAmount('1');
    await debinSellerPage.selectBuyerIdentifierType('CBU');
    await debinSellerPage.enterBuyerIdentifier(' ');
    await debinSellerPage.search();

    expect(await debinSellerPage.isRequiredFieldMessageShown(), 'Debe indicar que el campo es obligatorio.').toBe(true);
  });
});

test.describe('Generación de DEBIN vendedor por alias UI de Banco Entre Ríos', () => {
  const TEST_CASE_ID = 'TC0184BERSA';
  test.use({ testCaseId: TEST_CASE_ID });
  test.setTimeout(180_000);

  test(`${TEST_CASE_ID} - exige el alias del comprador al buscar sin ingresarlo`, async ({
    debinSellerPage,
    loginCredentials,
    loginPage,
    originAccountUiData,
    startPage,
  }) => {
    await startPage.open();
    await startPage.openLogin();
    await loginPage.authenticate(loginCredentials);

    await debinSellerPage.open();
    await debinSellerPage.openOrderGeneration();
    await debinSellerPage.selectDestinationAccount(originAccountUiData.originAccount);
    await debinSellerPage.enterAmount('1');
    await debinSellerPage.selectBuyerIdentifierType('ALIAS');
    await debinSellerPage.enterBuyerIdentifier(' ');
    await debinSellerPage.search();

    expect(await debinSellerPage.isRequiredFieldMessageShown(), 'Debe indicar que el campo es obligatorio.').toBe(true);
  });
});
