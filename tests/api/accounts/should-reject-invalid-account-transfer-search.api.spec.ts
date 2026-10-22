import type { AccountTransferSearchRequest } from '../../../src/api/accounts/account-transfer-search.api';
import { expect, test } from '../../../src/fixtures/api-test';

// Códigos de rechazo que acepta el legado (CuentasSteps.validarErroresAccountTransferSearch*).
const REJECTION_CODES = ['COR020W', 'API946W'];

interface InvalidSearchCase {
  readonly id: string;
  readonly title: string;
  readonly request: AccountTransferSearchRequest;
}

// Entradas de java/api/GP_Individuos_API/.../EntreRios/cuentas/ValidacionesAPITest.java, conservadas
// tal cual, incluidas las diferencias entre título y valor de docs/guides/ambiguities/lote-l1-validaciones-api.md.
const INVALID_SEARCHES: readonly InvalidSearchCase[] = [
  {
    id: 'TC0110006BERSA',
    title: 'Validar mensaje de error typeAccount con espacio',
    request: { typeAccount: ' A LIAS', account: 'VOLPATO.PESOS.BERSA' },
  },
  {
    id: 'TC0110007BERSA',
    title: 'Validar error por typeAccount con acento',
    request: { typeAccount: 'ÁLIAS', account: 'VOLPATO.PESOS.BERSA' },
  },
  {
    id: 'TC0110008BERSA',
    title: 'Validar error por typeAccount con guión',
    request: { typeAccount: 'A-LIAS', account: 'VOLPATO.PESOS.BERSA' },
  },
  {
    id: 'TC0110009BERSA',
    title: 'Validar error por typeAccount con longitud no permitida',
    request: { typeAccount: 'ALIASSSS', account: 'VOLPATO.PESOS.BERSA' },
  },
  {
    id: 'TC0110010BERSA',
    title: 'Validar error por typeAccount con apóstrofe',
    request: { typeAccount: "A'LIAS", account: 'VOLPATO.PESOS.BERSA' },
  },
  {
    id: 'TC0110011BERSA',
    title: 'Validar error por typeAccount vacío',
    request: { typeAccount: '', account: 'VOLPATO.PESOS.BERSA' },
  },
  {
    id: 'TC0110012BERSA',
    title: 'Validar error por typeAccount null',
    request: { typeAccount: null, account: 'VOLPATO.PESOS.BERSA' },
  },
  {
    id: 'TC0110013BERSA',
    title: 'Validar error por typeAccount alfabético no permitido',
    request: { typeAccount: 'ALIASUNO', account: 'VOLPATO.PESOS.BERSA' },
  },
  {
    id: 'TC0110014BERSA',
    title: 'Validar error por typeAccount numérico',
    request: { typeAccount: '123456', account: 'VOLPATO.PESOS.BERSA' },
  },
  {
    id: 'TC0110015BERSA',
    title: 'Validar error por typeAccount alfanumérico',
    request: { typeAccount: 'ALIAS1', account: 'VOLPATO.PESOS.BERSA' },
  },
  {
    id: 'TC0110016BERSA',
    title: 'Validar error por typeAccount con caracteres especiales',
    request: { typeAccount: '@#$%&_', account: 'VOLPATO.PESOS.BERSA' },
  },
  {
    id: 'TC0110017BERSA',
    title: 'Validar error por typeAccount boolean true',
    request: { typeAccount: false, account: 'VOLPATO.PESOS.BERSA' },
  },
  {
    id: 'TC0110018BERSA',
    title: 'Validar error por typeAccount boolean false',
    request: { typeAccount: false, account: 'VOLPATO.PESOS.BERSA' },
  },
  {
    id: 'TC0110019BERSA',
    title: 'Validar error por typeAccount en minúsculas',
    request: { typeAccount: 'alias', account: 'VOLPATO.PESOS.BERSA' },
  },
  {
    id: 'TC0110020BERSA',
    title: 'Validar error por typeAccount con prefijo y espacio',
    request: { typeAccount: '15- Alias', account: 'VOLPATO.PESOS.BERSA' },
  },
  {
    id: 'TC0110021BERSA',
    title: 'Validar error por account con espacio',
    request: { typeAccount: 'ALIAS', account: '1- VOLPATO .PESOS.BERSA' },
  },
  {
    id: 'TC0110022BERSA',
    title: 'Validar error por account con acento',
    request: { typeAccount: 'ALIAS', account: 'VÓLPATO.PESOS.BERSA' },
  },
  {
    id: 'TC0110023BERSA',
    title: 'Validar error por account con guión',
    request: { typeAccount: 'ALIAS', account: 'VOL-PATO.PESOS.BERSA' },
  },
  {
    id: 'TC0110024BERSA',
    title: 'Validar error por account con longitud no permitida',
    request: {
      typeAccount: 'ALIAS',
      account:
        'VOLPATO.PESOS.BERSA......................................................................................',
    },
  },
  {
    id: 'TC0110025BERSA',
    title: 'Validar error por account con apóstrofe',
    request: { typeAccount: 'ALIAS', account: "VO'LPATO.PESOS.BERSA" },
  },
  { id: 'TC0110026BERSA', title: 'Validar error por account vacío', request: { typeAccount: 'ALIAS', account: '' } },
  { id: 'TC0110027BERSA', title: 'Validar error por account null', request: { typeAccount: 'ALIAS', account: null } },
  {
    id: 'TC0110028BERSA',
    title: 'Validar error por account CBU con longitud inválida (-1)',
    request: {
      typeAccount: 'ALIAS',
      account: '3300000620000125788076.............................................................................',
    },
  },
  {
    id: 'TC0110029BERSA',
    title: 'Validar error por account CBU con longitud inválida (+1)',
    request: {
      typeAccount: 'ALIAS',
      account: '3300000620000125788076...............................................................................',
    },
  },
  {
    id: 'TC0110030BERSA',
    title: 'Validar error por account alias con números adicionales',
    request: { typeAccount: 'ALIAS', account: 'VOLPATO.PESOS.BERSA1234' },
  },
  {
    id: 'TC0110031BERSA',
    title: 'Validar error por typeAccount alfanumérico',
    request: { typeAccount: 'ALIAS1', account: 'VOLPATO.PESOS.BERSA' },
  },
  {
    id: 'TC0110032BERSA',
    title: 'Validar error por account con caracteres especiales',
    request: { typeAccount: 'ALIAS', account: '(@#$%&_)' },
  },
  {
    id: 'TC0110033BERSA',
    title: 'Validar error por account boolean true',
    request: { typeAccount: 'ALIAS', account: true },
  },
  {
    id: 'TC0110034BERSA',
    title: 'Validar error por account boolean false',
    request: { typeAccount: 'ALIAS', account: false },
  },
  {
    id: 'TC0110035BERSA',
    title: 'Validar error por account con formato mixto',
    request: { typeAccount: 'ALIAS', account: '15- Volpato.pesos.BERSA' },
  },
  {
    id: 'TC0110036BERSA',
    title: 'Validar error cuando typeAccount y account están vacíos',
    request: { typeAccount: '', account: '' },
  },
  {
    id: 'TC0110037BERSA',
    title: 'Validar error cuando typeAccount y account son inválidos',
    request: { typeAccount: 'ÁLIAS', account: 'VÓLPATO.PESOS.BERSA' },
  },
  {
    id: 'TC0110038BERSA',
    title: 'Validar error por inconsistencia typeAccount CBU y account alias',
    request: { typeAccount: 'VOLPATO.PESOS.BERSA', account: 'ALIAS' },
  },
  {
    id: 'TC0110039BERSA',
    title: 'Validar error por inconsistencia typeAccount alias y account CBU',
    request: { typeAccount: 'ALIAS', account: '3300000620000125788076' },
  },
  {
    id: 'TC0110040BERSA',
    title: 'Validar error por typeAccount CBU vacío',
    request: { typeAccount: '', account: '3300000620000035540021' },
  },
  {
    id: 'TC0110041BERSA',
    title: 'Validar error por typeAccount CBU null',
    request: { typeAccount: null, account: '3300000620000035540021' },
  },
  {
    id: 'TC0110042BERSA',
    title: 'Validar error por account con espacio',
    request: { typeAccount: 'CBU', account: ' 33000006200000355400' },
  },
  {
    id: 'TC0110043BERSA',
    title: 'Validar error por account con guión',
    request: { typeAccount: 'CBU', account: '3300-00620000035540021' },
  },
  {
    id: 'TC0110044BERSA',
    title: 'Validar error por account con longitud no permitida',
    request: {
      typeAccount: 'CBU',
      account: '330000062000003554002100000000000000000000000000000000000000000000000000000000000000000000000',
    },
  },
  {
    id: 'TC0110045BERSA',
    title: 'Validar error por account con apóstrofe',
    request: { typeAccount: 'CBU', account: "'30000062000003554002" },
  },
  { id: 'TC0110046BERSA', title: 'Validar error por account vacío', request: { typeAccount: 'CBU', account: '' } },
  { id: 'TC0110047BERSA', title: 'Validar error por account null', request: { typeAccount: 'CBU', account: null } },
  {
    id: 'TC0110048BERSA',
    title: 'Validar error por account CBU con longitud inválida (-1)',
    request: {
      typeAccount: 'ALIAS',
      account: '3300000620000125788076.............................................................................',
    },
  },
  {
    id: 'TC0110049BERSA',
    title: 'Validar error por account cbu con caracteres especiales',
    request: { typeAccount: 'CBU', account: '(@#$%&_)' },
  },
  {
    id: 'TC0110050BERSA',
    title: 'Validar error por account CBU boolean true',
    request: { typeAccount: 'CBU', account: true },
  },
  {
    id: 'TC0110051BERSA',
    title: 'Validar error por account CBU boolean false',
    request: { typeAccount: 'CBU', account: false },
  },
  {
    id: 'TC0110052BERSA',
    title: 'Validar error por typeAccount CVU vacío',
    request: { typeAccount: '', account: '0000001700300000000313' },
  },
  {
    id: 'TC0110053BERSA',
    title: 'Validar error por typeAccount CVU null',
    request: { typeAccount: null, account: '0000001700300000000313' },
  },
  {
    id: 'TC0110054BERSA',
    title: 'Validar error por account CVU con espacio',
    request: { typeAccount: 'CVU', account: ' 0000001700300000000313' },
  },
  {
    id: 'TC0110055BERSA',
    title: 'Validar error por account CVU con guión',
    request: { typeAccount: 'CVU', account: '0000-001700300000000313' },
  },
  {
    id: 'TC0110056BERSA',
    title: 'Validar error por account CVU con longitud no permitida',
    request: {
      typeAccount: 'CVU',
      account: '000000170030000000031300000000000000000000000000000000000000000000000000000000000000000000000',
    },
  },
  {
    id: 'TC0110057BERSA',
    title: 'Validar error por account CVU con apóstrofe',
    request: { typeAccount: 'CBU', account: "'30000062000003554002" },
  },
  { id: 'TC0110058BERSA', title: 'Validar error por account CVU vacío', request: { typeAccount: 'CVU', account: '' } },
  { id: 'TC0110059BERSA', title: 'Validar error por account CVU null', request: { typeAccount: 'CVU', account: null } },
  {
    id: 'TC0110060BERSA',
    title: 'Validar error por account CVU con longitud inválida (-1)',
    request: { typeAccount: 'CVU', account: '000000170030000000031' },
  },
  {
    id: 'TC0110061BERSA',
    title: 'Validar error por account CVU con longitud inválida (+1)',
    request: { typeAccount: 'CVU', account: '00000017003000000003134' },
  },
  {
    id: 'TC0110062BERSA',
    title: 'Validar error por account CVU con caracteres especiales',
    request: { typeAccount: 'CVU', account: '(@#$%&_)' },
  },
  {
    id: 'TC0110063BERSA',
    title: 'Validar error por account CVU boolean true',
    request: { typeAccount: 'CVU', account: true },
  },
  {
    id: 'TC0110064BERSA',
    title: 'Validar error por account CVU boolean false',
    request: { typeAccount: 'CVU', account: false },
  },
];

test.describe('Búsqueda de cuenta destino API de Banco Entre Ríos — datos inválidos', () => {
  for (const testCase of INVALID_SEARCHES) {
    test.describe(testCase.id, () => {
      test.use({ testCaseId: testCase.id });

      test(`${testCase.id} - ${testCase.title}`, async ({ accountTransferSearchApi, loginApi, loginCredentials }) => {
        const session = await test.step('Autenticar al usuario', () => loginApi.authenticate(loginCredentials));
        const result = await test.step('Buscar la cuenta destino con datos inválidos', () =>
          accountTransferSearchApi.search(session, testCase.request, REJECTION_CODES));

        expect(REJECTION_CODES, 'La búsqueda con datos inválidos debe ser rechazada.').toContain(result.code);
      });
    });
  }
});
