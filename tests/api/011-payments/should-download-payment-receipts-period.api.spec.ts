import { ProductWidgetsApi } from '../../../src/api/accounts/product-widgets.api';
import { expect, test } from '../../../src/fixtures/api-test';
import { MERCADO_LIBRE_PAYMENT } from '../../../src/test-data/payment-services-data';

// Nombres de archivo del legado (Settings.PAGOS_PDF y PAGOS_XLS).
const PERIOD_DOWNLOADS = [
  {
    id: 'TC-011-1-0007-UNE',
    title: 'Descargar periodo en PDF',
    format: 'pdf',
    fileName: 'Pagos de impuestos o servicios realizados.pdf',
  },
  {
    id: 'TC-011-1-0008-UNE',
    title: 'Descargar periodo en XLS',
    format: 'xls',
    fileName: 'Pagos de impuestos o servicios realizados.xls',
  },
] as const;

test.describe('Comprobantes de pagos API de Banco Entre Ríos — descarga por período', () => {
  for (const download of PERIOD_DOWNLOADS) {
    test.describe(download.id, () => {
      test.use({ testCaseId: download.id });

      test(`${download.id} - Pagos - Comprobantes - ${download.title}`, async ({
        debitCardData,
        loginApi,
        loginCredentials,
        paymentLinkSyncApi,
        paymentReceiptsApi,
        productWidgetsApi,
        receivedMessagesApi,
      }) => {
        const session = await test.step('Autenticar al usuario', () => loginApi.authenticate(loginCredentials));
        const products = await test.step('Consultar los productos del usuario', () => productWidgetsApi.load(session));
        const debitCard = ProductWidgetsApi.debitCard(products, debitCardData.debitCardLastDigits);
        await test.step('Sincronizar pagos Link', () => paymentLinkSyncApi.synchronize(session));
        await test.step('Enviar el período a Mensajes', () =>
          paymentReceiptsApi.list(session, debitCard.idProduct, MERCADO_LIBRE_PAYMENT, {
            exportFormat: download.format,
            isExport: true,
          }));
        // El legado espera 5 s fijos antes de esta lectura; no hay polling aprobado (ver lote-l3-api.md).
        const idFile = await test.step('Buscar el archivo en Mensajes', () =>
          receivedMessagesApi.findAttachmentFileId(session, download.fileName));
        const result = await test.step('Descargar el período', () => paymentReceiptsApi.download(session, idFile));

        expect(result.fileName, 'La descarga debe devolver el archivo del período solicitado.').toBe(download.fileName);
      });
    });
  }
});
