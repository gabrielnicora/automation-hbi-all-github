import { ProductWidgetsApi } from '../../../src/api/accounts/product-widgets.api';
import { expect, test } from '../../../src/fixtures/api-test';

// TC128238 y TC128239 ejecutan en el legado el mismo recorrido (alta y confirmación);
// la diferencia de título (create/update) está registrada en lote-l4-api.md.
const CUIL_CASES = [
  {
    id: 'TC0310009BERSA',
    title: 'Verificar que el dato cuilSolicitante se envíe en application/create en nueva alta de TC',
  },
  {
    id: 'TC0310010BERSA',
    title: 'Verificar que el dato cuilSolicitante se envíe en application/update en alta de TC ya iniciada',
  },
] as const;

test.describe('Solicitud de tarjeta de crédito API de Banco Entre Ríos — CUIL del solicitante', () => {
  for (const cuilCase of CUIL_CASES) {
    test.describe(cuilCase.id, () => {
      test.use({ testCaseId: cuilCase.id });

      test(`${cuilCase.id} - API - Tarjeta de Crédito - ${cuilCase.title}`, async ({
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

        const offer = offers.firstOffer!;
        const applicationId = await test.step('Crear la solicitud de tarjeta', () =>
          creditCardApplicationApi.create(session, offer, debitIdProduct));
        await test.step('Confirmar la solicitud de tarjeta', () =>
          creditCardApplicationApi.confirm(session, loginCredentials.username, offer, debitIdProduct, applicationId));

        expect(offer.cuil, 'El CUIL de la oferta enviada debe ser el del cliente autenticado.').toBe(
          session.customerCuil,
        );
        expect(offer.dni, 'El DNI de la oferta enviada debe ser el del cliente autenticado.').toBe(
          session.documentNumber,
        );
      });
    });
  }
});
