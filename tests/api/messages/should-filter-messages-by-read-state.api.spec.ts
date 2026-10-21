import { expect, test } from '../../../src/fixtures/api-test';

// El legado (MensajesSteps.validarMensajesNoLeidos/Leidos) compara `userRead` con los textos
// "false" y "true" mediante equals. Se conserva esa comparación literal; la duda sobre su
// intención está en docs/guides/ambiguities/lote-l2-api.md.

test.describe('Mensajes API de Banco Entre Ríos — filtro por estado de lectura', () => {
  test.describe('TC0094BERSA', () => {
    test.use({ testCaseId: 'TC0094BERSA' });

    test('TC0094BERSA - Mensajes - Sin Leer', async ({ loginApi, loginCredentials, receivedMessagesApi }) => {
      const session = await test.step('Autenticar al usuario', () => loginApi.authenticate(loginCredentials));
      const result = await test.step('Consultar los mensajes sin leer', () =>
        receivedMessagesApi.listByReadState(session, true));

      expect(
        result.userReadValues.filter((userRead) => userRead === 'false'),
        'Ningún mensaje de la consulta sin leer debe informar userRead "false" (comparación legada).',
      ).toEqual([]);
    });
  });

  test.describe('TC0098BERSA', () => {
    test.use({ testCaseId: 'TC0098BERSA' });

    test('TC0098BERSA - Mensajes - Leídos', async ({ loginApi, loginCredentials, receivedMessagesApi }) => {
      const session = await test.step('Autenticar al usuario', () => loginApi.authenticate(loginCredentials));
      const result = await test.step('Consultar los mensajes leídos', () =>
        receivedMessagesApi.listByReadState(session, false));

      expect(
        result.userReadValues.filter((userRead) => userRead === 'true'),
        'Ningún mensaje de la consulta de leídos debe informar userRead "true" (comparación legada).',
      ).toEqual([]);
    });
  });
});
