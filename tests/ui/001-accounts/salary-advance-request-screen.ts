import { expect } from '../../../src/fixtures/ui-test';
import type { SalaryAdvancePage } from '../../../src/pages/accounts/salary-advance.page';

/** Textos de la solicitud de adelanto con oferta (legado validacionPantallaSolicitudAH). */
export const OFFER_TEXTS = [
  'Solicitud de adelanto',
  'solicitar entre $',
  'El importe solicitado más el interés se devolverá en la próxima acreditación de sueldo.',
  'El monto de interés se calculará según la cantidad de días que transcurran desde que solicites el adelanto hasta que cobres tu próximo sueldo.',
  'Resumen de contrato',
  'Mis adelantos',
];

/** Textos de la solicitud de adelanto sin oferta (legado validacionPantallaSolicitudAH). */
export const NO_OFFER_TEXTS = [
  'Adelanto de sueldo',
  'Resumen de contrato',
  'Mis adelantos',
  'En este momento no podemos procesar tu solicitud',
  'No contamos con una oferta disponible para vos!',
];

/** Exige los textos de la solicitud según haya oferta o no y, con oferta, Continuar habilitado. */
export async function expectSalaryAdvanceRequestScreen(salaryAdvancePage: SalaryAdvancePage): Promise<void> {
  const hasOffer = await salaryAdvancePage.hasOfferAvailable();
  const expectedTexts = hasOffer ? OFFER_TEXTS : NO_OFFER_TEXTS;

  for (const expectedText of expectedTexts) {
    expect(await salaryAdvancePage.isTextShown(expectedText), `Debe mostrar "${expectedText}".`).toBe(true);
  }

  if (hasOffer) {
    expect(await salaryAdvancePage.isContinueEnabled(), 'Con oferta, Continuar debe estar habilitado.').toBe(true);
  }
}
