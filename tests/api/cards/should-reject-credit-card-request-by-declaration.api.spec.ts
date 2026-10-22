import { ProductWidgetsApi } from '../../../src/api/accounts/product-widgets.api';
import type { PersonDeclarations } from '../../../src/api/cards/credit-card-application.api';
import { expect, test } from '../../../src/fixtures/api-test';

interface DeclarationCase {
  readonly id: string;
  readonly title: string;
  readonly declarations: PersonDeclarations;
  /** Texto del legado (Settings.PEP / EXTERIOR / UIF) que debe contener el mensaje. */
  readonly expectedMessage: string;
}

const DECLARATION_CASES: readonly DeclarationCase[] = [
  {
    id: 'TC0310004BERSA',
    title: 'Persona expuesta políticamente no permite continuar la solicitud',
    declarations: { exteriorResidence: false, pep: true, soUIF: false },
    expectedMessage: 'Persona expuesta politicamente',
  },
  {
    id: 'TC0310005BERSA',
    title: 'Residencia fiscal en el exterior no permite continuar la solicitud',
    declarations: { exteriorResidence: true, pep: false, soUIF: false },
    expectedMessage: 'Residencia en el exterior',
  },
  {
    id: 'TC0310006BERSA',
    title: 'Sujeto obligado UIF no permite continuar la solicitud',
    declarations: { exteriorResidence: false, pep: false, soUIF: true },
    expectedMessage: 'Error UIF',
  },
];

test.describe('Solicitud de tarjeta de crédito API de Banco Entre Ríos — declaraciones', () => {
  for (const declarationCase of DECLARATION_CASES) {
    test.describe(declarationCase.id, () => {
      test.use({ testCaseId: declarationCase.id });

      test(`${declarationCase.id} - Tarjetas de crédito - ${declarationCase.title}`, async ({
        creditCardApplicationApi,
        loginApi,
        loginCredentials,
        optionalOriginAccountData,
        paymentLinkSyncApi,
        productWidgetsApi,
      }) => {
        const session = await test.step('Autenticar al usuario', () => loginApi.authenticate(loginCredentials));
        const products = await test.step('Consultar los productos del usuario', () => productWidgetsApi.load(session));
        const debitIdProduct = ProductWidgetsApi.legacyAccountIdOrEmpty(
          products,
          optionalOriginAccountData.originAccount,
        );
        await test.step('Sincronizar pagos Link', () => paymentLinkSyncApi.synchronize(session));
        const offers = await test.step('Consultar las ofertas de tarjeta', () =>
          creditCardApplicationApi.readOffers(session));

        expect(offers.offersCount, 'El usuario debe tener ofertas de tarjeta de crédito.').toBeGreaterThan(0);

        // Como el legado, se actualiza la solicitud sin crearla antes en este caso.
        const message = await test.step('Declarar la condición de la persona', () =>
          creditCardApplicationApi.updateDeclarations(
            session,
            offers.firstOffer!,
            debitIdProduct,
            declarationCase.declarations,
          ));

        expect(message, 'La respuesta debe informar la condición que impide continuar.').toContain(
          declarationCase.expectedMessage,
        );
      });
    });
  }
});
