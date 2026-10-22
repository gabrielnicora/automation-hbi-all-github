import { ProductWidgetsApi } from '../../../src/api/accounts/product-widgets.api';
import type { CredinApi, CredinParty } from '../../../src/api/transfers/credin.api';
import type { AuthenticatedSession } from '../../../src/models/authenticated-session';
import { expect, test } from '../../../src/fixtures/api-test';
import { requiredTransferValue, type TransferCaseData } from '../../../src/test-data/transfer-case-data';

// CREDIN desde la cuenta origen hacia el CBU del caso con OTP por SMS
// (legado TransferenciasSteps.obtenerDatosDebitos/Credito, generarCredin y consultarCredin).

async function generateCredin(
  credinApi: CredinApi,
  productWidgetsApi: ProductWidgetsApi,
  session: AuthenticatedSession,
  username: string,
  data: TransferCaseData,
): Promise<{ readonly credit: CredinParty; readonly debit: CredinParty }> {
  const originAccount = requiredTransferValue(data.originAccount, 'ctaOrigen');
  const creditCbu = requiredTransferValue(data.destinationCbu, 'cbuDestino');
  const products = await test.step('Consultar los productos del usuario', () => productWidgetsApi.load(session));
  const debitCbu = ProductWidgetsApi.accountCbu(products, originAccount);
  const currency = ProductWidgetsApi.transferOrigin(products, originAccount).currency;
  const debitCardId = ProductWidgetsApi.debitCard(
    products,
    requiredTransferValue(data.debitCardLastDigits, 'tarjetaDebito'),
  ).idProduct;
  const debit = await test.step('Buscar la cuenta débito', () => credinApi.findParty(session, debitCbu, 'true'));
  const credit = await test.step('Buscar la cuenta crédito', () => credinApi.findParty(session, creditCbu, ''));

  await test.step('Generar el CREDIN', () =>
    credinApi.generate(session, username, { credit, creditCbu, currency, debit, debitCbu, debitCardId }));

  return { credit, debit };
}

const GENERATIONS = [
  {
    id: 'TC0014BERSA',
    title:
      'Generar CREDIN - Cuenta propia $ (mismo Banco u otro) - Mas de una cuenta Debito - CBU - Segundo factor por SMS',
  },
  // El legado no resuelve la tarjeta de débito en este caso (ver lote-l6-api.md); se usa la del registro.
  { id: 'TC12938BERSA', title: 'TRANSFERENCIAS - Generar CREDIN - Dolares' },
] as const;

const QUERIES = [
  { id: 'TC12939BERSA', title: 'TRANSFERENCIAS - Consultar CREDIN Originante', searchAs: 'Originante' },
  { id: 'TC12940BERSA', title: 'TRANSFERENCIAS - Consultar CREDIN destinatario', searchAs: 'Destinatario' },
] as const;

test.describe('CREDIN API de Banco Entre Ríos', () => {
  for (const credinCase of GENERATIONS) {
    test.describe(credinCase.id, () => {
      test.use({ testCaseId: credinCase.id });

      // El legado sólo valida que la generación responda con un código de éxito.
      test(`${credinCase.id} - ${credinCase.title}`, async ({
        credinApi,
        loginApi,
        loginCredentials,
        productWidgetsApi,
        transferCaseData,
      }) => {
        const session = await test.step('Autenticar al usuario', () => loginApi.authenticate(loginCredentials));
        await generateCredin(credinApi, productWidgetsApi, session, loginCredentials.username, transferCaseData);
      });
    });
  }

  for (const credinCase of QUERIES) {
    test.describe(credinCase.id, () => {
      test.use({ testCaseId: credinCase.id });

      test(`${credinCase.id} - ${credinCase.title}`, async ({
        credinApi,
        loginApi,
        loginCredentials,
        productWidgetsApi,
        transferCaseData,
      }) => {
        const session = await test.step('Autenticar al usuario', () => loginApi.authenticate(loginCredentials));
        const { credit, debit } = await generateCredin(
          credinApi,
          productWidgetsApi,
          session,
          loginCredentials.username,
          transferCaseData,
        );
        const found = await test.step(`Consultar los CREDIN del día como ${credinCase.searchAs}`, () =>
          credinApi.countToday(session, credinCase.searchAs, debit.cuil, credit.cuil));

        expect(found, 'La consulta debe encontrar los CREDIN generados.').toBeGreaterThan(0);
      });
    });
  }
});
