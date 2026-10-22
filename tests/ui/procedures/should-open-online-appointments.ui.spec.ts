import { expect, test } from '../../../src/fixtures/ui-test';

const TEST_CASE_ID = 'TC1220001BERSA';

test.describe('Turnos online UI de Banco Entre Ríos', () => {
  test.use({ testCaseId: TEST_CASE_ID });

  test.fail(true, 'BUG BERSA QA: Turnos online redirige a una página externa no encontrada.');

  test(`${TEST_CASE_ID} - acepta la redirección para solicitar un turno`, async ({
    appointmentsPage,
    loginCredentials,
    loginPage,
    startPage,
  }) => {
    await startPage.open();
    await startPage.openLogin();
    await loginPage.authenticate(loginCredentials);
    const externalPageTitle = await appointmentsPage.openExternalAppointments();
    expect(externalPageTitle).not.toBe('Página no encontrada | Banco Entre Rios');
  });
});
