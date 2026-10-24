import { expect, test } from '../../../src/fixtures/ui-test';

const TEST_CASE_ID = 'TC0090BERSA';

test.describe('Mensajes UI de Banco Entre Ríos', () => {
  test.use({ testCaseId: TEST_CASE_ID });
  test.setTimeout(180_000);

  test(`${TEST_CASE_ID} - elimina el primer mensaje de la bandeja`, async ({
    loginCredentials,
    loginPage,
    messagesPage,
    startPage,
  }) => {
    await startPage.open();
    await startPage.openLogin();
    await loginPage.authenticate(loginCredentials);

    await messagesPage.open();
    const deletedMessage = await messagesPage.readFirstMessage();

    await messagesPage.deleteFirstMessage();
    const firstMessageAfterDeletion = await messagesPage.readFirstMessage();

    expect(firstMessageAfterDeletion, 'El mensaje eliminado no debe seguir primero en la bandeja.').not.toEqual(
      deletedMessage,
    );
  });
});
