import { ProductWidgetsApi } from '../../../src/api/accounts/product-widgets.api';
import {
  legacyEcheqPaymentDate,
  type EcheqOperationsApi,
  type EcheqReference,
} from '../../../src/api/accounts/echeq-operations.api';
import type { AuthenticatedSession } from '../../../src/models/authenticated-session';
import { expect, test } from '../../../src/fixtures/api-test';

// Recorridos del legado EcheqIndividuosTest: buscar el eCheq a operar, operarlo con OTP y
// verificar que el listado del estado resultante tenga elementos (EcheqSteps.validaEcheqs).

async function findPendingEcheq(
  echeqOperationsApi: EcheqOperationsApi,
  session: AuthenticatedSession,
  daysFromToday: number,
): Promise<EcheqReference> {
  const echeq = await test.step('Buscar un eCheq EMITIDO-PENDIENTE del cliente', () =>
    echeqOperationsApi.findEcheq(session, {
      beneficiaryCuil: session.customerCuil,
      paymentDate: legacyEcheqPaymentDate(daysFromToday),
      statement: 'EMITIDO-PENDIENTE',
    }));

  expect(echeq.found, 'Debe existir un eCheq EMITIDO-PENDIENTE para el cliente con la fecha de pago esperada.').toBe(
    true,
  );
  return echeq;
}

async function expectEcheqsInStatus(
  echeqOperationsApi: EcheqOperationsApi,
  session: AuthenticatedSession,
  statement: string,
  operationType: string,
): Promise<void> {
  const echeqs = await test.step(`Listar eCheq en estado ${statement}`, () =>
    echeqOperationsApi.list(session, statement, '1', operationType));

  expect(echeqs.length, `Debe haber eCheq en el estado ${statement}.`).toBeGreaterThan(0);
}

test.describe('eCheq API de Banco Entre Ríos — operaciones', () => {
  test.describe('TC0110083BERSA', () => {
    test.use({ testCaseId: 'TC0110083BERSA' });

    test('TC0110083BERSA - Consulta del detalle de un eCheq - Estado Aceptar', async ({
      echeqOperationsApi,
      loginApi,
      loginCredentials,
    }) => {
      const session = await test.step('Autenticar al usuario', () => loginApi.authenticate(loginCredentials));
      const echeq = await findPendingEcheq(echeqOperationsApi, session, 0);
      await test.step('Aceptar el eCheq', () =>
        echeqOperationsApi.operate(session, loginCredentials.username, 'accept', echeq));

      await expectEcheqsInStatus(echeqOperationsApi, session, 'Activo', '');
    });
  });

  test.describe('TC0110084BERSA', () => {
    test.use({ testCaseId: 'TC0110084BERSA' });

    test('TC0110084BERSA - Consulta del detalle de un eCheq - Estado Endosar', async ({
      echeqOperationsApi,
      loginApi,
      loginCredentials,
      echeqEndorsementData,
    }) => {
      const session = await test.step('Autenticar al usuario', () => loginApi.authenticate(loginCredentials));
      const echeq = await findPendingEcheq(echeqOperationsApi, session, 0);
      await test.step('Aceptar el eCheq', () =>
        echeqOperationsApi.operate(session, loginCredentials.username, 'accept', echeq));
      await test.step('Endosar el eCheq', () =>
        echeqOperationsApi.operate(session, loginCredentials.username, 'endorse', echeq, {
          endorseDocumentNumber: echeqEndorsementData.endorseCuit,
        }));

      await expectEcheqsInStatus(echeqOperationsApi, session, 'ACTIVO-PENDIENTE', '');
    });
  });

  test.describe('TC0110086BERSA', () => {
    test.use({ testCaseId: 'TC0110086BERSA' });

    test('TC0110086BERSA - Consulta del detalle de un eCheq - Estado Repudiar', async ({
      echeqOperationsApi,
      loginApi,
      loginCredentials,
    }) => {
      const session = await test.step('Autenticar al usuario', () => loginApi.authenticate(loginCredentials));
      // Como el legado, el repudio no exige haber encontrado el eCheq (ver lote-l5-api.md).
      const echeq = await test.step('Buscar un eCheq EMITIDO-PENDIENTE con pago hoy', () =>
        echeqOperationsApi.findEcheq(session, {
          beneficiaryCuil: session.customerCuil,
          paymentDate: legacyEcheqPaymentDate(0),
          statement: 'EMITIDO-PENDIENTE',
        }));
      await test.step('Repudiar el eCheq', () =>
        echeqOperationsApi.operate(session, loginCredentials.username, 'repudiate', echeq));

      await expectEcheqsInStatus(echeqOperationsApi, session, 'REPUDIADO', '');
    });
  });

  test.describe('TC0110087BERSA', () => {
    test.use({ testCaseId: 'TC0110087BERSA' });

    test('TC0110087BERSA - Consulta del detalle de un eCheq - Estado Custodiar', async ({
      echeqOperationsApi,
      loginApi,
      loginCredentials,
      originAccountData,
      productWidgetsApi,
    }) => {
      const session = await test.step('Autenticar al usuario', () => loginApi.authenticate(loginCredentials));
      const products = await test.step('Consultar los productos del usuario', () => productWidgetsApi.load(session));
      const idProduct = ProductWidgetsApi.accountId(products, originAccountData.originAccount);
      const echeq = await findPendingEcheq(echeqOperationsApi, session, 60);
      await test.step('Aceptar el eCheq', () =>
        echeqOperationsApi.operate(session, loginCredentials.username, 'accept', echeq));
      await test.step('Custodiar el eCheq', () =>
        echeqOperationsApi.operate(session, loginCredentials.username, 'keep', echeq, { idProduct }));

      await expectEcheqsInStatus(echeqOperationsApi, session, 'CUSTODIA', '');
    });
  });

  test.describe('TC0110088BERSA', () => {
    test.use({ testCaseId: 'TC0110088BERSA' });

    test('TC0110088BERSA - Consulta del detalle de un eCheq - Custodiar y rescatar', async ({
      echeqOperationsApi,
      loginApi,
      loginCredentials,
      originAccountData,
      productWidgetsApi,
    }) => {
      const session = await test.step('Autenticar al usuario', () => loginApi.authenticate(loginCredentials));
      const products = await test.step('Consultar los productos del usuario', () => productWidgetsApi.load(session));
      const idProduct = ProductWidgetsApi.accountId(products, originAccountData.originAccount);
      const echeq = await findPendingEcheq(echeqOperationsApi, session, 60);
      await test.step('Aceptar el eCheq', () =>
        echeqOperationsApi.operate(session, loginCredentials.username, 'accept', echeq));
      await test.step('Custodiar el eCheq', () =>
        echeqOperationsApi.operate(session, loginCredentials.username, 'keep', echeq, { idProduct }));
      await test.step('Rescatar el eCheq', () =>
        echeqOperationsApi.operate(session, loginCredentials.username, 'rescue', echeq));

      await expectEcheqsInStatus(echeqOperationsApi, session, 'ACTIVO', 'Recibidos');
    });
  });

  test.describe('TC0110091BERSA', () => {
    test.use({ testCaseId: 'TC0110091BERSA' });

    test('TC0110091BERSA - Solicitar devolución de eCheq', async ({
      echeqOperationsApi,
      loginApi,
      loginCredentials,
      secondUserCredentials,
      echeqEndorsementData,
    }) => {
      const session = await test.step('Autenticar al usuario', () => loginApi.authenticate(loginCredentials));
      const echeq = await findPendingEcheq(echeqOperationsApi, session, 0);
      await test.step('Aceptar el eCheq', () =>
        echeqOperationsApi.operate(session, loginCredentials.username, 'accept', echeq));
      await test.step('Endosar el eCheq al segundo usuario', () =>
        echeqOperationsApi.operate(session, loginCredentials.username, 'endorse', echeq, {
          endorseDocumentNumber: echeqEndorsementData.endorseCuit,
        }));

      const secondSession = await test.step('Autenticar al segundo usuario', () =>
        loginApi.authenticate(secondUserCredentials));
      await test.step('Aceptar el eCheq endosado con el segundo usuario', () =>
        echeqOperationsApi.operate(secondSession, secondUserCredentials.username, 'accept', echeq));

      const returnSession = await test.step('Autenticar nuevamente al usuario', () =>
        loginApi.authenticate(loginCredentials));
      await test.step('Solicitar la devolución del eCheq', () =>
        echeqOperationsApi.operate(returnSession, loginCredentials.username, 'requestReturn', echeq));

      await expectEcheqsInStatus(echeqOperationsApi, returnSession, 'DEVOLUCION-PENDIENTE', 'Recibidos');
    });
  });

  test.describe('TC0110090BERSA', () => {
    test.use({ testCaseId: 'TC0110090BERSA' });

    test('TC0110090BERSA - Depositar eCheq - Variable encendida', async ({
      echeqOperationsApi,
      loginApi,
      loginCredentials,
      originAccountData,
      productWidgetsApi,
    }) => {
      const session = await test.step('Autenticar al usuario', () => loginApi.authenticate(loginCredentials));
      const products = await test.step('Consultar los productos del usuario', () => productWidgetsApi.load(session));
      const idProduct = ProductWidgetsApi.accountId(products, originAccountData.originAccount);
      const echeq = await findPendingEcheq(echeqOperationsApi, session, 0);
      await test.step('Aceptar el eCheq', () =>
        echeqOperationsApi.operate(session, loginCredentials.username, 'accept', echeq));
      await test.step('Depositar el eCheq', () =>
        echeqOperationsApi.operate(session, loginCredentials.username, 'deposit', echeq, { idProduct }));

      await expectEcheqsInStatus(echeqOperationsApi, session, 'DEPOSITADO', '');
    });
  });
});
