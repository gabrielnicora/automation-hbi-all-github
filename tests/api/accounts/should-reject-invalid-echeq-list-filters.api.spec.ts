import type { EcheqListRequest } from '../../../src/api/accounts/echeq-list.api';
import { expect, test } from '../../../src/fixtures/api-test';

// Códigos de rechazo que acepta el legado (CuentasSteps.validarErroresListEcheq*).
const REJECTION_CODES = ['COR020W', 'BAK001E'];

interface InvalidFilterCase {
  readonly id: string;
  readonly title: string;
  readonly request: EcheqListRequest;
}

// Entradas de java/api/GP_Individuos_API/.../EntreRios/cuentas/ValidacionesAPITest.java, conservadas
// tal cual, incluidas las diferencias entre título y valor de docs/guides/ambiguities/lote-l1-validaciones-api.md.
const INVALID_FILTERS: readonly InvalidFilterCase[] = [
  {
    id: 'TC0110065BERSA',
    title: 'Validar error por echeqNumber con espacio',
    request: { echeqNumber: '8510 9300', statement: '', filterCuil: '', operationType: '', page: '1' },
  },
  {
    id: 'TC0110066BERSA',
    title: 'Validar error por echeqNumber con caracteres especiales',
    request: { echeqNumber: '@#$%&_', statement: '', filterCuil: '', operationType: '', page: '1' },
  },
  {
    id: 'TC0110067BERSA',
    title: 'Validar error por statement con espacio',
    request: { echeqNumber: '', statement: 'RE CIBIDOS', filterCuil: '', operationType: '', page: '1' },
  },
  {
    id: 'TC0110068BERSA',
    title: 'Validar error por statement con caracteres especiales',
    request: { echeqNumber: '', statement: '@#$%&_', filterCuil: '', operationType: '', page: '1' },
  },
  {
    id: 'TC0110069BERSA',
    title: 'Validar error por filterCuil con espacio',
    request: { echeqNumber: '', statement: '', filterCuil: '271 71806742', operationType: '', page: '1' },
  },
  {
    id: 'TC0110070BERSA',
    title: 'Validar error por filterCuil con caracteres especiales',
    request: { echeqNumber: '', statement: '', filterCuil: '@#$%&_', operationType: '', page: '1' },
  },
  {
    id: 'TC0110071BERSA',
    title: 'Validar error por operationType con espacio',
    request: { echeqNumber: '', statement: '', filterCuil: '', operationType: 'TO DOS', page: '1' },
  },
  {
    id: 'TC0110072BERSA',
    title: 'Validar error por operationType con caracteres especiales',
    request: { echeqNumber: '', statement: '', filterCuil: '', operationType: '@#$%&_', page: '1' },
  },
  {
    id: 'TC0110073BERSA',
    title: 'Validar error por page con espacio',
    request: { echeqNumber: '', statement: '', filterCuil: '', operationType: '', page: ' 1' },
  },
  {
    id: 'TC0110074BERSA',
    title: 'Validar error por page con caracteres especiales',
    request: { echeqNumber: '', statement: '', filterCuil: '', operationType: '', page: '@#$%&_' },
  },
  {
    id: 'TC0110075BERSA',
    title: 'Validar error por filterCuil boolean true',
    request: { echeqNumber: '', statement: '', filterCuil: true, operationType: '', page: '1' },
  },
  {
    id: 'TC0110076BERSA',
    title: 'Validar error por filterCuil boolean false',
    request: { echeqNumber: '', statement: '', filterCuil: false, operationType: '', page: '1' },
  },
  {
    id: 'TC0110077BERSA',
    title: 'Validar error por filterCuil null',
    request: { echeqNumber: '', statement: '', filterCuil: '_filterCuil_', operationType: '', page: '1' },
  },
  {
    id: 'TC0110078BERSA',
    title: 'Validar error por page con guión',
    request: { echeqNumber: '', statement: '', filterCuil: '', operationType: '', page: '_1' },
  },
  {
    id: 'TC0110079BERSA',
    title: 'Validar error por page alfabético no permitido',
    request: { echeqNumber: '', statement: '', filterCuil: '', operationType: '', page: '1PAGE' },
  },
  {
    id: 'TC0110080BERSA',
    title: 'Validar error por page boolean true',
    request: { echeqNumber: '', statement: '', filterCuil: '', operationType: '', page: true },
  },
  {
    id: 'TC0110081BERSA',
    title: 'Validar error por page boolean false',
    request: { echeqNumber: '', statement: '', filterCuil: '', operationType: '', page: false },
  },
  {
    id: 'TC0110082BERSA',
    title: 'Validar error por page con prefijo y espacio',
    request: { echeqNumber: '', statement: '', filterCuil: '', operationType: '', page: '1- page' },
  },
];

test.describe('Listado de eCheq API de Banco Entre Ríos — filtros inválidos', () => {
  for (const testCase of INVALID_FILTERS) {
    test.describe(testCase.id, () => {
      test.use({ testCaseId: testCase.id });

      test(`${testCase.id} - ${testCase.title}`, async ({ echeqListApi, loginApi, loginCredentials }) => {
        const session = await test.step('Autenticar al usuario', () => loginApi.authenticate(loginCredentials));
        const result = await test.step('Consultar el listado de eCheq con filtros inválidos', () =>
          echeqListApi.list(session, testCase.request, REJECTION_CODES));

        expect(REJECTION_CODES, 'La consulta con filtros inválidos debe ser rechazada.').toContain(result.code);
      });
    });
  }
});
