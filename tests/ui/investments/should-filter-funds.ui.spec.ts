import { FUND_RISK } from '../../../src/pages/investments/fund-risk';
import { expect, test } from '../../../src/fixtures/ui-test';

// Filtros de "Información de fondos" (legado FondoComunInversionSteps). Reutiliza el recorrido del
// testigo TC0119BERSA: login UI y menú Inversiones → Invertí en Fondos.

test.describe('FCI - filtros de fondos por UI', () => {
  test.describe('TC0118BERSA', () => {
    test.use({ testCaseId: 'TC0118BERSA' });

    test('TC0118BERSA - FCI - Filtros - Mi Tenencia - Fondos por Riesgo - Conservador', async ({
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
      await test.step('Seleccionar únicamente el riesgo conservador', async () => {
        await investmentFundsPage.open();
        await investmentFundsPage.selectOnlyRisk(FUND_RISK.CONSERVATIVE);
      });
      const visibleRisks = await test.step('Leer los riesgos visibles', () => investmentFundsPage.getVisibleRisks());

      expect(visibleRisks, 'Debe haber fondos para el filtro aplicado.').not.toHaveLength(0);
      expect(
        visibleRisks.every((risk) => risk === FUND_RISK.CONSERVATIVE),
        'Sólo deben verse fondos de riesgo conservador.',
      ).toBe(true);
    });
  });

  // El legado (validaFondosPorMonedaEnListado) sólo falla si el listado está vacío: su comparación de
  // moneda nunca puede fallar. Se conserva esa exigencia (ver lote-l7-ui.md).
  const CURRENCY_FILTERS = [
    { id: 'TC0126BERSA', title: 'FCI - Filtros: Fondos por moneda - Pesos', currency: 'pesos' },
    { id: 'TC0127BERSA', title: 'FCI - Filtros: Fondos por moneda - Dólar', currency: 'dollars' },
  ] as const;

  for (const filterCase of CURRENCY_FILTERS) {
    test.describe(filterCase.id, () => {
      test.use({ testCaseId: filterCase.id });

      test(`${filterCase.id} - ${filterCase.title}`, async ({
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
        await test.step('Seleccionar únicamente la moneda', async () => {
          await investmentFundsPage.open();
          await investmentFundsPage.selectOnlyCurrency(filterCase.currency);
        });
        const currencies = await test.step('Leer la moneda de los fondos visibles', () =>
          investmentFundsPage.getVisibleFundCurrencyTexts());

        expect(currencies, 'Debe haber fondos para el filtro aplicado.').not.toHaveLength(0);
      });
    });
  }

  test.describe('TC0128BERSA', () => {
    test.use({ testCaseId: 'TC0128BERSA' });

    test('TC0128BERSA - FCI - Filtros: Mi Tenencia', async ({
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
      await test.step('Mostrar "Mis fondos"', async () => {
        await investmentFundsPage.open();
        await investmentFundsPage.showMyFunds();
      });

      expect(
        await investmentFundsPage.isFundListShown(),
        'Debe mostrarse el listado de fondos para los filtros aplicados.',
      ).toBe(true);
    });
  });
});
