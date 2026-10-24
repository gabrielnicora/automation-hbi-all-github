// Datos fijos de las plantillas legadas de pagos (java/api/GP_Individuos_API/Requests/Pagos/).
// Son datos dummy de QA compartidos por varios casos; los IDs de producto se resuelven en ejecución.

/** Servicio Mercado Pago que agendan y eliminan TC-011-1-0005-UNE y TC-011-1-0010-UNE. */
export const MERCADO_PAGO_SERVICE = {
  belongsTo: 'TestAutomation',
  categoryDesc: 'Cobranzas para Empresas y Comercios',
  entityCode: '970',
  entityDesc: 'Mercado Pago',
  paymentCode: '00449535691',
} as const;

/** Pago sin base de Mercado Libre de TC-011-1-0009-UNE y filtro de comprobantes de TC-011-1-0006-UNE/TC-011-1-0007-UNE/TC-011-1-0008-UNE. */
export const MERCADO_LIBRE_PAYMENT = {
  amount: 1,
  categoryCode: '15',
  conceptDescription: 'MLIBRE FACT',
  enteCode: '969',
  enteDescription: 'Mercado Libre',
  paymentCode: '00449535691',
  reference: '1234567878',
} as const;

/** Adhesión a DEBIN recurrente de TC-011-1-0003-UNE (plantilla debinsRecurrencySubscriptionSubscribe.json). */
export const RECURRING_DEBIN_SUBSCRIPTION = {
  concept: 'ALQ',
  description: 'TestAutomation',
  entitlement: 'EDENOR',
  reference: 'TestAutomation',
  sellerCuit: '20179326591',
  ticketData:
    '{"bankName":"BSF","buyerName":"ABRAM, CELIA MARIA ","buyerCuit":"27114486952","sellerName":"TALEB, OMAR FABIO"}',
} as const;

export type MercadoPagoService = typeof MERCADO_PAGO_SERVICE;
export type MercadoLibrePayment = typeof MERCADO_LIBRE_PAYMENT;
export type RecurringDebinSubscription = typeof RECURRING_DEBIN_SUBSCRIPTION;
