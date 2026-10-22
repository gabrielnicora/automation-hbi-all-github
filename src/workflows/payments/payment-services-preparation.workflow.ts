import type { SmsOperationOtpApi } from '../../api/authentication/sms-operation-otp.api';
import type { AuthenticatedSession } from '../../models/authenticated-session';
import type { PaymentServicesPage } from '../../pages/payments/payment-services.page';
import type { ServiceAdhesionPage } from '../../pages/payments/service-adhesion.page';

const SERVICE_CATEGORY = 'Cobranzas para Empresas y Comercios';
const SERVICE_COMPANY = 'Mercado Libre';
const SERVICE_CODE = '00449535691';
const SERVICE_DESCRIPTION = 'MLPAYMENT';
const PAYMENT_AMOUNT = '1';
const PAYMENT_REFERENCE = '1234567890';

export interface PaymentServicesUser {
  readonly session: AuthenticatedSession;
  readonly username: string;
}

/** Pago simultáneo de los dos primeros servicios Mercado Libre (recorrido de TC110847BERSA). */
export async function paySelectedMercadoLibreServices(payments: PaymentServicesPage): Promise<void> {
  await payments.selectServiceToPay(1);
  await payments.selectServiceToPay(2);
  await payments.pressPay();
  await expectText(payments, SERVICE_COMPANY);
  await payments.fillAmountAndReference(PAYMENT_AMOUNT, PAYMENT_REFERENCE);
  await payments.pressContinue();
  await expectText(payments, SERVICE_COMPANY);
  await payments.fillAmountAndReference(PAYMENT_AMOUNT, PAYMENT_REFERENCE);
  await payments.pressPay();
  await expectText(payments, 'Nuevo pago');
  await payments.pressPay();
  await payments.pressConfirm();
  await expectText(payments, 'Estado de tus pagos');
}

/**
 * Preparación legada de pagos (NuevoPagoSteps.validarBotonMostrarTodos y validarServiciosUnisys):
 * si faltan pagos o el servicio `MLPAYMENT`, los agenda, adhiere y paga antes del caso. Coordina el
 * listado de pagos, el formulario de adhesión y el segundo factor.
 */
export class PaymentServicesPreparationWorkflow {
  public constructor(
    private readonly payments: PaymentServicesPage,
    private readonly adhesion: ServiceAdhesionPage,
    private readonly smsOperationOtp: SmsOperationOtpApi,
  ) {}

  /** Legado validarBotonMostrarTodos: deja abierto "Mostrar todos". */
  public async showAllPayments(user: PaymentServicesUser): Promise<void> {
    if (!(await this.payments.isShowAllShown())) {
      for (let schedule = 0; schedule < 2; schedule += 1) {
        await this.registerService(user, 'Servicios agendados', 'Agendar servicio');
      }

      await this.payments.showAll();
      await paySelectedMercadoLibreServices(this.payments);
      await this.payments.open();
    }

    await this.payments.showAll();
  }

  /** Legado validarServiciosUnisys: agenda y adhiere el servicio si falta; termina con la flecha Volver. */
  public async ensureService(user: PaymentServicesUser): Promise<void> {
    if (!(await this.payments.isServiceShown(SERVICE_DESCRIPTION))) {
      await this.payments.goBackWithArrow();
      await this.registerService(user, 'Servicios adheridos', 'Agendar servicio');
      await this.registerService(user, 'Servicios adheridos', 'Adherir servicio');
    }

    await this.payments.goBackWithArrow();
  }

  private async registerService(user: PaymentServicesUser, section: string, action: string): Promise<void> {
    await this.payments.openSpanOption(section);
    await expectText(this.payments, section);
    await this.payments.openParagraphOption(action);
    await this.adhesion.submit(SERVICE_CATEGORY, SERVICE_COMPANY, SERVICE_CODE, SERVICE_DESCRIPTION);
    await this.adhesion.enterSecondFactorCode(await this.smsOperationOtp.resolve(user.session, user.username));
    const status = await this.adhesion.readTicketStatus();

    if (status !== 'Tu solicitud se realizo con exito') {
      throw new Error(`La preparación de pagos no pudo ${action.toLowerCase()}: ticket "${status}".`);
    }

    await this.payments.open();
  }
}

async function expectText(payments: PaymentServicesPage, text: string): Promise<void> {
  if (!(await payments.isTextShown(text))) {
    throw new Error(`La pantalla de pagos debe mostrar "${text}".`);
  }
}
