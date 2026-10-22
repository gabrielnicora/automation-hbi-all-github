import { expect, test } from '../../../src/fixtures/api-test';

const TEST_CASE_ID = 'TC0910003BERSA';

test.describe('Mensajes API de Banco Entre Ríos', () => {
  test.use({ testCaseId: TEST_CASE_ID });

  test(`${TEST_CASE_ID} - consulta los mensajes recibidos`, async ({
    loginApi,
    loginCredentials,
    receivedMessagesApi,
  }) => {
    const session = await test.step('Autenticar al usuario', () => loginApi.authenticate(loginCredentials));
    const result = await test.step('Consultar los mensajes recibidos', () => receivedMessagesApi.list(session));

    expect(result.hasDataObject, 'La consulta de mensajes recibidos debe devolver un objeto data.').toBe(true);
  });
});
