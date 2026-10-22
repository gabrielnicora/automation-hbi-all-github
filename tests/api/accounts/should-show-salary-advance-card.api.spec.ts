import { expect, test } from '../../../src/fixtures/api-test';

const TEST_CASE_ID = 'TC0110004BERSA';

test.describe('Adelanto de sueldo API de Banco Entre Ríos', () => {
  test.use({ testCaseId: TEST_CASE_ID });

  test(`${TEST_CASE_ID} - Adelantos de Sueldo - Mis adelantos - Validar card de adelantos de sueldo`, async ({
    loginApi,
    loginCredentials,
    salaryAdvanceApi,
  }) => {
    const session = await test.step('Autenticar al usuario', () => loginApi.authenticate(loginCredentials));
    const offer = await test.step('Consultar la oferta de adelanto de sueldo', () =>
      salaryAdvanceApi.readOffer(session));

    expect(
      offer.accounts.length,
      'El usuario debe tener al menos una cuenta con oferta de adelanto (precalificación).',
    ).toBeGreaterThan(0);
  });
});
