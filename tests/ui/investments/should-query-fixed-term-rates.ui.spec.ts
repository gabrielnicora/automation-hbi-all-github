import { expect, test } from '../../../src/fixtures/ui-test';

// Consulta de tasas de plazo fijo (legado ConsultaDeTasasSteps): tipo, importe 120000 y Buscar;
// se exigen las columnas Plazo Desde, Plazo Hasta y Tasa TNA pizarra.
const RATE_QUERIES = [
  {
    id: 'TC0720001BERSA',
    title: 'PF - Consulta de Tasas - Tipo - Tradicional en Pesos',
    type: 'Plazo Fijo Tradicional en Pesos',
  },
  {
    id: 'TC0720002BERSA',
    title: 'PF - Consulta de Tasas - Tipo - Tradicional en Dólares',
    type: 'Plazo Fijo Tradicional en D',
  },
] as const;

test.describe('Plazo fijo - consulta de tasas por UI', () => {
  for (const query of RATE_QUERIES) {
    test.describe(query.id, () => {
      test.use({ testCaseId: query.id });

      test(`${query.id} - ${query.title}`, async ({ fixedTermsPage, loginCredentials, loginPage, startPage }) => {
        await test.step('Autenticar al usuario', async () => {
          await startPage.open();
          await startPage.openLogin();
          await loginPage.authenticate(loginCredentials);
        });
        await test.step('Abrir Plazo fijo', () => fixedTermsPage.open());

        expect(await fixedTermsPage.isTextShown('Plazo fijo'), 'Debe mostrarse la sección Plazo fijo.').toBe(true);

        await test.step('Abrir la consulta de tasas', () => fixedTermsPage.openRatesQuery());

        expect(await fixedTermsPage.isTextShown('Consulta de Tasas'), 'Debe mostrarse la consulta de tasas.').toBe(
          true,
        );

        await test.step('Buscar tasas', async () => {
          await fixedTermsPage.selectRateQueryType(query.type);
          await fixedTermsPage.fillRateQueryAmount('120000');
          await fixedTermsPage.searchRates();
        });

        expect(await fixedTermsPage.readVisibleRateColumns(), 'Deben verse las columnas del resultado.').toEqual([
          'Plazo Desde',
          'Plazo Hasta',
          'Tasa TNA pizarra',
        ]);
      });
    });
  }
});
