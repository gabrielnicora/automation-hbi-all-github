import { expect, test } from '../../../src/fixtures/api-test';

// Consultas del legado EcheqSteps.validaEcheqs(estado, operationType): exige elementos en la página 1.
const STATUS_QUERIES = [
  { id: 'TC25610BERSA', title: "Validar el listado de eCheq en el estado 'Todos'", statement: 'Todos' },
  { id: 'TC124909BERSA', title: "Consulta del detalle de un eCheq - Estado 'Emitir'", statement: 'EMITIDO-PENDIENTE' },
  { id: 'TC124913BERSA', title: 'Consulta del detalle de un eCheq - Estado Anular', statement: 'ANULADO' },
] as const;

test.describe('eCheq API de Banco Entre Ríos — consulta por estado', () => {
  for (const query of STATUS_QUERIES) {
    test.describe(query.id, () => {
      test.use({ testCaseId: query.id });

      test(`${query.id} - ${query.title}`, async ({ echeqOperationsApi, loginApi, loginCredentials }) => {
        const session = await test.step('Autenticar al usuario', () => loginApi.authenticate(loginCredentials));
        const echeqs = await test.step(`Listar eCheq en estado ${query.statement}`, () =>
          echeqOperationsApi.list(session, query.statement, '1', ''));

        expect(echeqs.length, `Debe haber eCheq en el estado ${query.statement}.`).toBeGreaterThan(0);
      });
    });
  }
});
