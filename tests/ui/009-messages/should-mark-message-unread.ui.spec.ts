import { expect, test } from '../../../src/fixtures/ui-test';

const TEST_CASE_ID = 'TC-009-2-0001-UNE';

test.describe('Mensajes UI de Banco Entre Ríos', () => {
  test.use({ testCaseId: TEST_CASE_ID });

  test(`${TEST_CASE_ID} - marca un mensaje como no leído`, async ({
    loginCredentials,
    loginPage,
    messagesPage,
    startPage,
  }) => {
    await startPage.open();
    await startPage.openLogin();
    await loginPage.authenticate(loginCredentials);

    await messagesPage.open();
    await messagesPage.ensureReadMessageAvailable();

    let selectedSubject: string | undefined;

    try {
      const messageSubject = await messagesPage.markFirstReadMessageAsUnread();
      selectedSubject = messageSubject;

      const unreadSubjects = await messagesPage.readUnreadMessageSubjects();
      expect(unreadSubjects, 'El mensaje marcado como no leído debe aparecer en la bandeja Sin leer.').toContain(
        messageSubject,
      );
    } finally {
      if (selectedSubject) {
        await messagesPage.restoreMessageAsRead(selectedSubject);
      }
    }
  });
});
