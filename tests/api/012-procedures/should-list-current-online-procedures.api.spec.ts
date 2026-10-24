import { expect, test } from '../../../src/fixtures/api-test';

const TEST_CASE_ID = 'TC-012-1-0001-UNE';

test.describe('Trámites API de Banco Entre Ríos', () => {
  test.use({ testCaseId: TEST_CASE_ID });

  test(`${TEST_CASE_ID} - consulta los trámites vigentes`, async ({
    currentOnlineProceduresApi,
    loginApi,
    loginCredentials,
  }) => {
    const session = await test.step('Autenticar al usuario', () => loginApi.authenticate(loginCredentials));
    const result = await test.step('Consultar los trámites vigentes', () => currentOnlineProceduresApi.list(session));

    expect(result.wasAccepted, 'La consulta de trámites vigentes debe ser aceptada por la API.').toBe(true);
  });
});
