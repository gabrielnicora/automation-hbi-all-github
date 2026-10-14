import { expect, test } from '../../../src/fixtures/api-test';

const TEST_CASE_ID = 'TC0139BERSA';

test.describe('Transferencias API de Banco Entre Ríos', () => {
  test.use({ testCaseId: TEST_CASE_ID });

  test(
    `${TEST_CASE_ID} - transfiere entre cuentas propias y valida el ticket`,
    { tag: '@critical' },
    async ({ loginApi, loginCredentials, ownAccountTransferApi, ownAccountTransferData }) => {
      const session = await test.step('Autenticar al usuario', () => loginApi.authenticate(loginCredentials));

      const result = await test.step('Transferir entre cuentas propias y recuperar el ticket', () =>
        ownAccountTransferApi.transfer(session, ownAccountTransferData));

      expect(result.isTicketFinished, 'El ticket de la transferencia debe estar finalizado.').toBe(true);
      expect(result.ticketMatchesTransfer, 'El ticket debe corresponder a la transferencia realizada.').toBe(true);
      expect(result.isOwnAccountTransfer, 'El ticket debe pertenecer a una transferencia entre cuentas propias.').toBe(
        true,
      );
      expect(result.hasReceiptPdf, 'La transferencia debe generar un comprobante PDF no vacío.').toBe(true);
    },
  );
});
