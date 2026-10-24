import { expect, test } from '../../../src/fixtures/ui-test';

test.describe('Consulta de tarjetas de crédito Visa UI de Banco Entre Ríos', () => {
  const TEST_CASE_ID = 'TC0032BERSA';
  test.use({ testCaseId: TEST_CASE_ID });
  test.setTimeout(180_000);

  test(`${TEST_CASE_ID} - abre el detalle de una tarjeta Visa por su número`, async ({
    creditCardDetailUiData,
    creditCardsPage,
    loginCredentials,
    loginPage,
    startPage,
  }) => {
    await startPage.open();
    await startPage.openLogin();
    await loginPage.authenticate(loginCredentials);

    await creditCardsPage.open();
    await creditCardsPage.openDetail(creditCardDetailUiData.creditCard);

    expect(await creditCardsPage.readDetailTitle()).toContain(creditCardDetailUiData.creditCard);
  });
});

test.describe('Consulta de tarjetas de crédito Mastercard UI de Banco Entre Ríos', () => {
  const TEST_CASE_ID = 'TC0033BERSA';
  test.use({ testCaseId: TEST_CASE_ID });
  test.setTimeout(180_000);

  test(`${TEST_CASE_ID} - abre el detalle de una tarjeta Mastercard`, async ({
    creditCardDetailUiData,
    creditCardsPage,
    loginCredentials,
    loginPage,
    startPage,
  }) => {
    await startPage.open();
    await startPage.openLogin();
    await loginPage.authenticate(loginCredentials);

    await creditCardsPage.open();
    await creditCardsPage.openDetail(creditCardDetailUiData.creditCard);

    expect(await creditCardsPage.readDetailTitle()).toContain('Master');
  });
});

test.describe('Detalle de tarjeta de crédito UI de Banco Entre Ríos', () => {
  const TEST_CASE_ID = 'TC0062BERSA';
  test.use({ testCaseId: TEST_CASE_ID });
  test.setTimeout(180_000);

  test(`${TEST_CASE_ID} - muestra descarga de resumen, movimientos del período y solicitud de adicional`, async ({
    creditCardDetailUiData,
    creditCardsPage,
    loginCredentials,
    loginPage,
    startPage,
  }) => {
    await startPage.open();
    await startPage.openLogin();
    await loginPage.authenticate(loginCredentials);

    await creditCardsPage.open();
    await creditCardsPage.openDetail(creditCardDetailUiData.creditCard);

    expect(await creditCardsPage.isStatementDownloadAvailable(), 'Debe ofrecer Descargar Resúmenes.').toBe(true);
    expect(await creditCardsPage.isPeriodMovementsAvailable(), 'Debe mostrar Movimientos del período.').toBe(true);
    expect(await creditCardsPage.isAdditionalCardRequestAvailable(), 'Debe ofrecer Solicitar Adicional.').toBe(true);
  });
});
