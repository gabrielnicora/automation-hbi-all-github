import { expect, test } from '../../../src/fixtures/ui-test';
import { paySelectedMercadoLibreServices } from '../../../src/workflows/payments/payment-services-preparation.workflow';

const SERVICE_CODE = '00449535691';

test.describe('Pago de servicios UI de Banco Entre Ríos', () => {
  test.setTimeout(300_000);

  test.describe('TC110847BERSA', () => {
    test.use({ testCaseId: 'TC110847BERSA' });

    test('TC110847BERSA - muestra el estado de los pagos realizados en simultáneo', async ({
      loginApi,
      loginCredentials,
      loginPage,
      paymentServicesPage,
      paymentServicesPreparationWorkflow,
      startPage,
    }) => {
      const session = await test.step('Autenticar al usuario', async () => {
        await startPage.open();
        await startPage.openLogin();
        await loginPage.authenticate(loginCredentials);
        return loginApi.authenticate(loginCredentials);
      });
      const user = { session, username: loginCredentials.username };

      await paymentServicesPage.open();
      await test.step('Preparar pagos y servicio MLPAYMENT', async () => {
        await paymentServicesPreparationWorkflow.showAllPayments(user);
        await paymentServicesPreparationWorkflow.ensureService(user);
      });

      await paySelectedMercadoLibreServices(paymentServicesPage);
      await paymentServicesPage.goBack();

      expect(await paymentServicesPage.isTextShown('Pago de servicios'), 'Debe volver a Pago de servicios.').toBe(true);
    });
  });

  test.describe('TC110363BERSA', () => {
    test.use({ testCaseId: 'TC110363BERSA' });

    test('TC110363BERSA - filtra comprobantes de pago por empresa y descripción', async ({
      loginApi,
      loginCredentials,
      loginPage,
      paymentReceiptsPage,
      paymentServicesPage,
      paymentServicesPreparationWorkflow,
      startPage,
    }) => {
      const session = await test.step('Autenticar al usuario', async () => {
        await startPage.open();
        await startPage.openLogin();
        await loginPage.authenticate(loginCredentials);
        return loginApi.authenticate(loginCredentials);
      });
      const user = { session, username: loginCredentials.username };

      await paymentServicesPage.open();
      await test.step('Preparar pagos y servicio MLPAYMENT', async () => {
        await paymentServicesPreparationWorkflow.showAllPayments(user);
        await paymentServicesPreparationWorkflow.ensureService(user);
      });
      await paymentServicesPage.goBackWithArrow();
      await paymentServicesPage.openSpanOption('Mis Comprobantes');
      expect(await paymentReceiptsPage.isTitleShown(), 'Debe mostrar Comprobantes de pago.').toBe(true);

      await paymentReceiptsPage.selectOriginAndContinue('4002');
      expect(await paymentReceiptsPage.isTitleShown(), 'Debe seguir en Comprobantes de pago.').toBe(true);
      await paymentReceiptsPage.searchByCompanyAndDescription('Mercado Libre', SERVICE_CODE);

      expect(await paymentReceiptsPage.isReceiptShown(SERVICE_CODE), 'Debe haber resultados para la búsqueda.').toBe(
        true,
      );
    });
  });
});
