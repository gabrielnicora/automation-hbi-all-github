import { expect, test } from '../../../src/fixtures/ui-test';

const OFFER_TEXTS = [
  'Solicitud de adelanto',
  'solicitar entre $',
  'El importe solicitado más el interés se devolverá en la próxima acreditación de sueldo.',
  'El monto de interés se calculará según la cantidad de días que transcurran desde que solicites el adelanto hasta que cobres tu próximo sueldo.',
  'Resumen de contrato',
  'Mis adelantos',
];

const NO_OFFER_TEXTS = [
  'Adelanto de sueldo',
  'Resumen de contrato',
  'Mis adelantos',
  'En este momento no podemos procesar tu solicitud',
  'No contamos con una oferta disponible para vos!',
];

test.describe('Resumen de contrato de adelanto de sueldo UI de Banco Entre Ríos', () => {
  const TEST_CASE_ID = 'TC0227BERSA';
  test.use({ testCaseId: TEST_CASE_ID });
  test.setTimeout(180_000);

  test(`${TEST_CASE_ID} - redirige al documento del contrato al aceptar`, async ({
    loginCredentials,
    loginPage,
    originAccountUiData,
    salaryAdvancePage,
    startPage,
  }) => {
    await startPage.open();
    await startPage.openLogin();
    await loginPage.authenticate(loginCredentials);

    await salaryAdvancePage.openRequest(originAccountUiData.originAccount);
    await salaryAdvancePage.openContractSummary();
    const contractPage = await salaryAdvancePage.acceptContractRedirect();

    expect(contractPage.url).toContain('assets');
  });
});

test.describe('Cancelación de redirección al contrato UI de Banco Entre Ríos', () => {
  const TEST_CASE_ID = 'TC0228BERSA';
  test.use({ testCaseId: TEST_CASE_ID });
  test.setTimeout(180_000);

  test(`${TEST_CASE_ID} - vuelve a la solicitud de adelanto al cancelar la redirección`, async ({
    loginCredentials,
    loginPage,
    originAccountUiData,
    salaryAdvancePage,
    startPage,
  }) => {
    await startPage.open();
    await startPage.openLogin();
    await loginPage.authenticate(loginCredentials);

    await salaryAdvancePage.openRequest(originAccountUiData.originAccount);
    await salaryAdvancePage.openContractSummary();
    await salaryAdvancePage.cancelContractRedirect();

    const hasOffer = await salaryAdvancePage.hasOfferAvailable();
    const expectedTexts = hasOffer ? OFFER_TEXTS : NO_OFFER_TEXTS;

    for (const expectedText of expectedTexts) {
      expect(await salaryAdvancePage.isTextShown(expectedText), `Debe mostrar "${expectedText}".`).toBe(true);
    }

    if (hasOffer) {
      expect(await salaryAdvancePage.isContinueEnabled(), 'Con oferta, Continuar debe estar habilitado.').toBe(true);
    }
  });
});
