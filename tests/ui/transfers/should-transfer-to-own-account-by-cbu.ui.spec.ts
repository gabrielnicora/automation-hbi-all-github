import { expect, test } from '../../../src/fixtures/ui-test';

const TEST_CASE_ID = 'TC1520004BERSA';

test.describe('Transferencias UI de Banco Entre Ríos', () => {
  test.use({ testCaseId: TEST_CASE_ID });

  test(
    `${TEST_CASE_ID} - transfiere a cuenta propia por CBU con todos los campos completos`,
    { tag: '@critical' },
    async ({ loginCredentials, loginPage, ownAccountTransferPage, ownAccountTransferUiData, startPage }) => {
      await startPage.open();
      await startPage.openLogin();
      await loginPage.authenticate(loginCredentials);
      await ownAccountTransferPage.open();

      await ownAccountTransferPage.selectRecipientByCbu(ownAccountTransferUiData.destinationCbu);
      await ownAccountTransferPage.completeDetails({ amount: '1', description: 'TC18088' });

      const review = await ownAccountTransferPage.readReview();
      expect(review).toContain(ownAccountTransferUiData.originAccount);
      expect(review).toContain(ownAccountTransferUiData.destinationAccount);
      expect(review).toContain('Varios');
      expect(review).toContain('$ 1,00');

      await ownAccountTransferPage.confirm();
      const ticket = await ownAccountTransferPage.readTicket();
      expect(ticket).toContain('Tu solicitud se realizó con éxito');
      expect(ticket).toContain(ownAccountTransferUiData.originAccount);
      expect(ticket).toContain(ownAccountTransferUiData.destinationAccount);
      expect(ticket).toContain('$ 1,00');
    },
  );
});
