import { InvestmentFundsApi, type FundMovement } from '../../../src/api/investments/investment-funds.api';
import { expect, test } from '../../../src/fixtures/api-test';

// Fondo y clave de descarga fijados por el legado (Settings.deltaPesosUnysis y keyLongValueUnisys).
const FUND_NAME = 'A-Delta Pesos';
const DOWNLOAD_KEY_LONG_VALUE = '11340';

function isFilled(value: unknown): boolean {
  return value !== null && value !== undefined && String(value).length > 0;
}

function expectFirstMovementFields(movements: readonly FundMovement[]): void {
  expect(movements.length, 'El usuario debe tener movimientos del fondo en los últimos 40 días.').toBeGreaterThan(0);
  expect(isFilled(movements[0].statusCode), 'El primer movimiento debe informar statusCode.').toBe(true);
  expect(isFilled(movements[0].fund), 'El primer movimiento debe informar el fondo.').toBe(true);
}

test.describe('Movimientos de fondos API de Banco Entre Ríos', () => {
  test.describe('TC0129BERSA', () => {
    test.use({ testCaseId: 'TC0129BERSA' });

    test('TC0129BERSA - FCI - Movimientos', async ({ investmentFundsApi, loginApi, loginCredentials }) => {
      const session = await test.step('Autenticar al usuario', () => loginApi.authenticate(loginCredentials));
      const commitentAccount = await test.step('Consultar la cuenta comitente', () =>
        investmentFundsApi.readCommitentAccount(session));
      const funds = await test.step('Consultar los fondos', () => investmentFundsApi.listFunds(session));
      const fund = InvestmentFundsApi.findFund(funds, FUND_NAME);
      const movements = await test.step('Consultar los movimientos del fondo', () =>
        investmentFundsApi.listMovements(session, fund, commitentAccount));

      expectFirstMovementFields(movements);
    });
  });

  test.describe('TC0130BERSA', () => {
    test.use({ testCaseId: 'TC0130BERSA' });

    test('TC0130BERSA - FCI - Movimientos con descarga PDF', async ({
      investmentFundsApi,
      loginApi,
      loginCredentials,
    }) => {
      const session = await test.step('Autenticar al usuario', () => loginApi.authenticate(loginCredentials));
      const commitentAccount = await test.step('Consultar la cuenta comitente', () =>
        investmentFundsApi.readCommitentAccount(session));
      const funds = await test.step('Consultar los fondos', () => investmentFundsApi.listFunds(session));
      const fund = InvestmentFundsApi.findFund(funds, FUND_NAME);
      const movements = await test.step('Consultar los movimientos del fondo', () =>
        investmentFundsApi.listMovements(session, fund, commitentAccount));

      expectFirstMovementFields(movements);

      const download = await test.step('Descargar el primer movimiento', () =>
        investmentFundsApi.downloadMovement(session, String(movements[0].requestTemplate), DOWNLOAD_KEY_LONG_VALUE));

      expect(isFilled(download.content), 'La descarga debe devolver un contenido cifrado no vacío.').toBe(true);
    });
  });
});
