import { FUND_RISK } from '../../../src/pages/investments/fund-risk';
import { expect, test } from '../../../src/fixtures/ui-test';

const TEST_CASE_ID = 'TC-007-2-0004-UNE';

test.describe('FCI - filtros de fondos por riesgo', () => {
  test.use({ testCaseId: TEST_CASE_ID });

  test(`${TEST_CASE_ID} - muestra únicamente fondos de riesgo moderado`, async ({
    investmentFundsPage,
    loginCredentials,
    loginPage,
    startPage,
  }) => {
    await test.step('Autenticar al usuario', async () => {
      await startPage.open();
      await startPage.openLogin();
      await loginPage.authenticate(loginCredentials);
    });

    await test.step('Seleccionar únicamente el riesgo moderado', async () => {
      await investmentFundsPage.open();
      await investmentFundsPage.selectOnlyRisk(FUND_RISK.MODERATE);
    });

    const visibleRisks = await test.step('Leer los riesgos visibles', () => investmentFundsPage.getVisibleRisks());

    expect(visibleRisks).not.toHaveLength(0);
    expect(visibleRisks.every((risk) => risk === FUND_RISK.MODERATE)).toBe(true);
  });
});
