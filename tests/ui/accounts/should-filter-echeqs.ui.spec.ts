import { expect, test } from '../../../src/fixtures/ui-test';

const TEST_CASE_ID = 'TC29414BERSA';
const ISSUER_CUIT = '20063078175';
const EXPECTED_STATE = 'Emitido-Pendiente';

test.describe('Búsqueda de eCheq UI de Banco Entre Ríos', () => {
  test.use({ testCaseId: TEST_CASE_ID });

  test(`${TEST_CASE_ID} - combina filtros de CUIT, estado y fecha de emisión`, async ({
    echeqPage,
    loginCredentials,
    loginPage,
    startPage,
  }) => {
    await startPage.open();
    await startPage.openLogin();
    await loginPage.authenticate(loginCredentials);

    await echeqPage.openFromAccounts();
    await echeqPage.filterByIssuerStateAndLastSixMonths(ISSUER_CUIT);
    const rows = await echeqPage.readVisibleRows();
    const dateRange = await echeqPage.readEmissionDateRange();
    const fromDate = parseDate(dateRange.from);
    const toDate = parseDate(dateRange.to);

    expect(rows, 'El filtro debe devolver al menos un eCheq.').not.toHaveLength(0);
    for (const row of rows) {
      const emissionDate = parseDate(row.emissionDate);
      expect(row.issuerCuit, 'El CUIT/CUIL emisor debe coincidir con el filtro.').toBe(ISSUER_CUIT);
      expect(row.state, 'El estado del eCheq debe coincidir con el filtro.').toBe(EXPECTED_STATE);
      expect(
        emissionDate >= fromDate && emissionDate <= toDate,
        `La fecha de emisión ${row.emissionDate} debe estar dentro del rango seleccionado.`,
      ).toBe(true);
    }
  });
});

function parseDate(value: string): number {
  const match = /^(\d{2})\/(\d{2})\/(\d{4})$/.exec(value);
  if (!match) {
    throw new Error(`Fecha de eCheq con formato inesperado: ${value}`);
  }
  return Date.UTC(Number(match[3]), Number(match[2]) - 1, Number(match[1]));
}
