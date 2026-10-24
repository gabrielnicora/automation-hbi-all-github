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
    id: 'TC29517BERSA',
    title: 'Validar error por echeqNumber con espacio',
    request: { echeqNumber: '8510 9300', statement: '', filterCuil: '', operationType: '', page: '1' },
  },
  {
    id: 'TC29518BERSA',
    title: 'Validar error por echeqNumber con caracteres especiales',
    request: { echeqNumber: '@#$%&_', statement: '', filterCuil: '', operationType: '', page: '1' },
  },
  {
    id: 'TC29519BERSA',
    title: 'Validar error por statement con espacio',
    request: { echeqNumber: '', statement: 'RE CIBIDOS', filterCuil: '', operationType: '', page: '1' },
  },
  {
    id: 'TC29520BERSA',
    title: 'Validar error por statement con caracteres especiales',
    request: { echeqNumber: '', statement: '@#$%&_', filterCuil: '', operationType: '', page: '1' },
  },
  {
    id: 'TC29521BERSA',
    title: 'Validar error por filterCuil con espacio',
    request: { echeqNumber: '', statement: '', filterCuil: '271 71806742', operationType: '', page: '1' },
  },
  {
    id: 'TC29522BERSA',
    title: 'Validar error por filterCuil con caracteres especiales',
    request: { echeqNumber: '', statement: '', filterCuil: '@#$%&_', operationType: '', page: '1' },
  },
  {
    id: 'TC29523BERSA',
    title: 'Validar error por operationType con espacio',
    request: { echeqNumber: '', statement: '', filterCuil: '', operationType: 'TO DOS', page: '1' },
  },
  {
    id: 'TC29524BERSA',
    title: 'Validar error por operationType con caracteres especiales',
    request: { echeqNumber: '', statement: '', filterCuil: '', operationType: '@#$%&_', page: '1' },
  },
  {
    id: 'TC29525BERSA',
    title: 'Validar error por page con espacio',
    request: { echeqNumber: '', statement: '', filterCuil: '', operationType: '', page: ' 1' },
  },
  {
    id: 'TC29526BERSA',
    title: 'Validar error por page con caracteres especiales',
    request: { echeqNumber: '', statement: '', filterCuil: '', operationType: '', page: '@#$%&_' },
  },
  {
    id: 'TC29527BERSA',
    title: 'Validar error por filterCuil boolean true',
    request: { echeqNumber: '', statement: '', filterCuil: true, operationType: '', page: '1' },
  },
  {
    id: 'TC29528BERSA',
    title: 'Validar error por filterCuil boolean false',
    request: { echeqNumber: '', statement: '', filterCuil: false, operationType: '', page: '1' },
  },
  {
    id: 'TC29529BERSA',
    title: 'Validar error por filterCuil null',
    request: { echeqNumber: '', statement: '', filterCuil: '_filterCuil_', operationType: '', page: '1' },
  },
  {
    id: 'TC29530BERSA',
    title: 'Validar error por page con guión',
    request: { echeqNumber: '', statement: '', filterCuil: '', operationType: '', page: '_1' },
  },
  {
    id: 'TC29531BERSA',
    title: 'Validar error por page alfabético no permitido',
    request: { echeqNumber: '', statement: '', filterCuil: '', operationType: '', page: '1PAGE' },
  },
  {
    id: 'TC29532BERSA',
    title: 'Validar error por page boolean true',
    request: { echeqNumber: '', statement: '', filterCuil: '', operationType: '', page: true },
  },
  {
    id: 'TC29533BERSA',
    title: 'Validar error por page boolean false',
    request: { echeqNumber: '', statement: '', filterCuil: '', operationType: '', page: false },
  },
  {
    id: 'TC29534BERSA',
    title: 'Validar error por page con prefijo y espacio',
    request: { echeqNumber: '', statement: '', filterCuil: '', operationType: '', page: '1- page' },
  },
];

test.describe('Listado de eCheq API de Banco Entre Ríos — filtros inválidos', () => {
  test.use({ credentialsRef: 'TCBERSAPRUEBA' });

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
