import { test } from '../../../src/fixtures/api-test';

const TEST_CASE_ID = 'TC0110001BERSA';
// Destinatario y tipo fijados por el caso legado CuentasTest.TC0110001BERSA.
const DESTINATION = { account: '3860006503000031964422', typeAccount: 'CBU' } as const;

test.describe('Agenda de destinatarios API de Banco Entre Ríos', () => {
  test.use({ testCaseId: TEST_CASE_ID });

  test(`${TEST_CASE_ID} - Adherir cuenta - nuevo destinatario de terceros con segundo factor por SMS`, async ({
    accountTransferSearchApi,
    adheredAccountsApi,
    loginApi,
    loginCredentials,
    productWidgetsApi,
  }) => {
    const session = await test.step('Autenticar al usuario', () => loginApi.authenticate(loginCredentials));
    await test.step('Consultar los productos del usuario', () => productWidgetsApi.load(session));

    await test.step('Eliminar el destinatario si ya estaba agendado', async () => {
      const current = await accountTransferSearchApi.findDestination(session, DESTINATION);

      if (current.isAdhered && current.idAdheredAccount) {
        await adheredAccountsApi.remove(session, current.idAdheredAccount);
      }
    });

    await test.step('Agendar el destinatario', async () => {
      const destination = await accountTransferSearchApi.findDestination(session, DESTINATION);
      await adheredAccountsApi.adhere(session, loginCredentials.username, {
        ...DESTINATION,
        ownAccount: destination.ownAccount,
      });
    });
  });
});
