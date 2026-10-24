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
    id: 'TC-001-1-0006-UNE',
    title: 'Validar mensaje de error typeAccount con espacio',
    request: { typeAccount: ' A LIAS', account: 'VOLPATO.PESOS.BERSA' },
  },
  {
    id: 'TC-001-1-0007-UNE',
    title: 'Validar error por typeAccount con acento',
    request: { typeAccount: 'ÁLIAS', account: 'VOLPATO.PESOS.BERSA' },
  },
  {
    id: 'TC-001-1-0008-UNE',
    title: 'Validar error por typeAccount con guión',
    request: { typeAccount: 'A-LIAS', account: 'VOLPATO.PESOS.BERSA' },
  },
  {
    id: 'TC-001-1-0009-UNE',
    title: 'Validar error por typeAccount con longitud no permitida',
    request: { typeAccount: 'ALIASSSS', account: 'VOLPATO.PESOS.BERSA' },
  },
  {
    id: 'TC-001-1-0010-UNE',
    title: 'Validar error por typeAccount con apóstrofe',
    request: { typeAccount: "A'LIAS", account: 'VOLPATO.PESOS.BERSA' },
  },
  {
    id: 'TC-001-1-0011-UNE',
    title: 'Validar error por typeAccount vacío',
    request: { typeAccount: '', account: 'VOLPATO.PESOS.BERSA' },
  },
  {
    id: 'TC-001-1-0012-UNE',
    title: 'Validar error por typeAccount null',
    request: { typeAccount: null, account: 'VOLPATO.PESOS.BERSA' },
  },
  {
    id: 'TC-001-1-0013-UNE',
    title: 'Validar error por typeAccount alfabético no permitido',
    request: { typeAccount: 'ALIASUNO', account: 'VOLPATO.PESOS.BERSA' },
  },
  {
    id: 'TC-001-1-0014-UNE',
    title: 'Validar error por typeAccount numérico',
    request: { typeAccount: '123456', account: 'VOLPATO.PESOS.BERSA' },
  },
  {
    id: 'TC-001-1-0015-UNE',
    title: 'Validar error por typeAccount alfanumérico',
    request: { typeAccount: 'ALIAS1', account: 'VOLPATO.PESOS.BERSA' },
  },
  {
    id: 'TC-001-1-0016-UNE',
    title: 'Validar error por typeAccount con caracteres especiales',
    request: { typeAccount: '@#$%&_', account: 'VOLPATO.PESOS.BERSA' },
  },
  {
    id: 'TC-001-1-0017-UNE',
    title: 'Validar error por typeAccount boolean true',
    request: { typeAccount: false, account: 'VOLPATO.PESOS.BERSA' },
  },
  {
    id: 'TC-001-1-0018-UNE',
    title: 'Validar error por typeAccount boolean false',
    request: { typeAccount: false, account: 'VOLPATO.PESOS.BERSA' },
  },
  {
    id: 'TC-001-1-0019-UNE',
    title: 'Validar error por typeAccount en minúsculas',
    request: { typeAccount: 'alias', account: 'VOLPATO.PESOS.BERSA' },
  },
  {
    id: 'TC-001-1-0020-UNE',
    title: 'Validar error por typeAccount con prefijo y espacio',
    request: { typeAccount: '15- Alias', account: 'VOLPATO.PESOS.BERSA' },
  },
  {
    id: 'TC-001-1-0021-UNE',
    title: 'Validar error por account con espacio',
    request: { typeAccount: 'ALIAS', account: '1- VOLPATO .PESOS.BERSA' },
  },
  {
    id: 'TC-001-1-0022-UNE',
    title: 'Validar error por account con acento',
    request: { typeAccount: 'ALIAS', account: 'VÓLPATO.PESOS.BERSA' },
  },
  {
    id: 'TC-001-1-0023-UNE',
    title: 'Validar error por account con guión',
    request: { typeAccount: 'ALIAS', account: 'VOL-PATO.PESOS.BERSA' },
  },
  {
    id: 'TC-001-1-0024-UNE',
    title: 'Validar error por account con longitud no permitida',
    request: {
      typeAccount: 'ALIAS',
      account:
        'VOLPATO.PESOS.BERSA......................................................................................',
    },
  },
  {
    id: 'TC-001-1-0025-UNE',
    title: 'Validar error por account con apóstrofe',
    request: { typeAccount: 'ALIAS', account: "VO'LPATO.PESOS.BERSA" },
  },
  { id: 'TC-001-1-0026-UNE', title: 'Validar error por account vacío', request: { typeAccount: 'ALIAS', account: '' } },
  {
    id: 'TC-001-1-0027-UNE',
    title: 'Validar error por account null',
    request: { typeAccount: 'ALIAS', account: null },
  },
  {
    id: 'TC-001-1-0028-UNE',
    title: 'Validar error por account CBU con longitud inválida (-1)',
    request: {
      typeAccount: 'ALIAS',
      account: '3300000620000125788076.............................................................................',
    },
  },
  {
    id: 'TC-001-1-0029-UNE',
    title: 'Validar error por account CBU con longitud inválida (+1)',
    request: {
      typeAccount: 'ALIAS',
      account: '3300000620000125788076...............................................................................',
    },
  },
  {
    id: 'TC-001-1-0030-UNE',
    title: 'Validar error por account alias con números adicionales',
    request: { typeAccount: 'ALIAS', account: 'VOLPATO.PESOS.BERSA1234' },
  },
  {
    id: 'TC-001-1-0031-UNE',
    title: 'Validar error por typeAccount alfanumérico',
    request: { typeAccount: 'ALIAS1', account: 'VOLPATO.PESOS.BERSA' },
  },
  {
    id: 'TC-001-1-0032-UNE',
    title: 'Validar error por account con caracteres especiales',
    request: { typeAccount: 'ALIAS', account: '(@#$%&_)' },
  },
  {
    id: 'TC-001-1-0033-UNE',
    title: 'Validar error por account boolean true',
    request: { typeAccount: 'ALIAS', account: true },
  },
  {
    id: 'TC-001-1-0034-UNE',
    title: 'Validar error por account boolean false',
    request: { typeAccount: 'ALIAS', account: false },
  },
  {
    id: 'TC-001-1-0035-UNE',
    title: 'Validar error por account con formato mixto',
    request: { typeAccount: 'ALIAS', account: '15- Volpato.pesos.BERSA' },
  },
  {
    id: 'TC-001-1-0036-UNE',
    title: 'Validar error cuando typeAccount y account están vacíos',
    request: { typeAccount: '', account: '' },
  },
  {
    id: 'TC-001-1-0037-UNE',
    title: 'Validar error cuando typeAccount y account son inválidos',
    request: { typeAccount: 'ÁLIAS', account: 'VÓLPATO.PESOS.BERSA' },
  },
  {
    id: 'TC-001-1-0038-UNE',
    title: 'Validar error por inconsistencia typeAccount CBU y account alias',
    request: { typeAccount: 'VOLPATO.PESOS.BERSA', account: 'ALIAS' },
  },
  {
    id: 'TC-001-1-0039-UNE',
    title: 'Validar error por inconsistencia typeAccount alias y account CBU',
    request: { typeAccount: 'ALIAS', account: '3300000620000125788076' },
  },
  {
    id: 'TC-001-1-0040-UNE',
    title: 'Validar error por typeAccount CBU vacío',
    request: { typeAccount: '', account: '3300000620000035540021' },
  },
  {
    id: 'TC-001-1-0041-UNE',
    title: 'Validar error por typeAccount CBU null',
    request: { typeAccount: null, account: '3300000620000035540021' },
  },
  {
    id: 'TC-001-1-0042-UNE',
    title: 'Validar error por account con espacio',
    request: { typeAccount: 'CBU', account: ' 33000006200000355400' },
  },
  {
    id: 'TC-001-1-0043-UNE',
    title: 'Validar error por account con guión',
    request: { typeAccount: 'CBU', account: '3300-00620000035540021' },
  },
  {
    id: 'TC-001-1-0044-UNE',
    title: 'Validar error por account con longitud no permitida',
    request: {
      typeAccount: 'CBU',
      account: '330000062000003554002100000000000000000000000000000000000000000000000000000000000000000000000',
    },
  },
  {
    id: 'TC-001-1-0045-UNE',
    title: 'Validar error por account con apóstrofe',
    request: { typeAccount: 'CBU', account: "'30000062000003554002" },
  },
  { id: 'TC-001-1-0046-UNE', title: 'Validar error por account vacío', request: { typeAccount: 'CBU', account: '' } },
  { id: 'TC-001-1-0047-UNE', title: 'Validar error por account null', request: { typeAccount: 'CBU', account: null } },
  {
    id: 'TC-001-1-0048-UNE',
    title: 'Validar error por account CBU con longitud inválida (-1)',
    request: {
      typeAccount: 'ALIAS',
      account: '3300000620000125788076.............................................................................',
    },
  },
  {
    id: 'TC-001-1-0049-UNE',
    title: 'Validar error por account cbu con caracteres especiales',
    request: { typeAccount: 'CBU', account: '(@#$%&_)' },
  },
  {
    id: 'TC-001-1-0050-UNE',
    title: 'Validar error por account CBU boolean true',
    request: { typeAccount: 'CBU', account: true },
  },
  {
    id: 'TC-001-1-0051-UNE',
    title: 'Validar error por account CBU boolean false',
    request: { typeAccount: 'CBU', account: false },
  },
  {
    id: 'TC-001-1-0052-UNE',
    title: 'Validar error por typeAccount CVU vacío',
    request: { typeAccount: '', account: '0000001700300000000313' },
  },
  {
    id: 'TC-001-1-0053-UNE',
    title: 'Validar error por typeAccount CVU null',
    request: { typeAccount: null, account: '0000001700300000000313' },
  },
  {
    id: 'TC-001-1-0054-UNE',
    title: 'Validar error por account CVU con espacio',
    request: { typeAccount: 'CVU', account: ' 0000001700300000000313' },
  },
  {
    id: 'TC-001-1-0055-UNE',
    title: 'Validar error por account CVU con guión',
    request: { typeAccount: 'CVU', account: '0000-001700300000000313' },
  },
  {
    id: 'TC-001-1-0056-UNE',
    title: 'Validar error por account CVU con longitud no permitida',
    request: {
      typeAccount: 'CVU',
      account: '000000170030000000031300000000000000000000000000000000000000000000000000000000000000000000000',
    },
  },
  {
    id: 'TC-001-1-0057-UNE',
    title: 'Validar error por account CVU con apóstrofe',
    request: { typeAccount: 'CBU', account: "'30000062000003554002" },
  },
  {
    id: 'TC-001-1-0058-UNE',
    title: 'Validar error por account CVU vacío',
    request: { typeAccount: 'CVU', account: '' },
  },
  {
    id: 'TC-001-1-0059-UNE',
    title: 'Validar error por account CVU null',
    request: { typeAccount: 'CVU', account: null },
  },
  {
    id: 'TC-001-1-0060-UNE',
    title: 'Validar error por account CVU con longitud inválida (-1)',
    request: { typeAccount: 'CVU', account: '000000170030000000031' },
  },
  {
    id: 'TC-001-1-0061-UNE',
    title: 'Validar error por account CVU con longitud inválida (+1)',
    request: { typeAccount: 'CVU', account: '00000017003000000003134' },
  },
  {
    id: 'TC-001-1-0062-UNE',
    title: 'Validar error por account CVU con caracteres especiales',
    request: { typeAccount: 'CVU', account: '(@#$%&_)' },
  },
  {
    id: 'TC-001-1-0063-UNE',
    title: 'Validar error por account CVU boolean true',
    request: { typeAccount: 'CVU', account: true },
  },
  {
    id: 'TC-001-1-0064-UNE',
    title: 'Validar error por account CVU boolean false',
    request: { typeAccount: 'CVU', account: false },
  },
];

test.describe('Búsqueda de cuenta destino API de Banco Entre Ríos — datos inválidos', () => {
  test.use({ credentialsRef: 'TCBERSAPRUEBA' });

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
