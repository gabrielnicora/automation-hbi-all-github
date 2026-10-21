import { expect, type Page } from '@playwright/test';
import { becomesVisible } from '../visibility';

const APPOINTMENT_TAB_TIMEOUT_MS = 15_000;

/** Solicitud de tarjeta de crédito nueva (legado TarjetasDeCreditoSteps / TarjetasDeCreditoPage). */
export class NewCreditCardRequestPage {
  public constructor(private readonly page: Page) {}

  /** Desde Tarjetas de crédito abre la solicitud (`/creditCards/newCreditCard`). */
  public async open(): Promise<void> {
    await this.page.locator("xpath=//a[@href='/creditCards/newCreditCard']").first().click();
    await expect(this.page).toHaveURL(/\/creditCards\/newCreditCard/);
  }

  public async isTextShown(text: string): Promise<boolean> {
    return becomesVisible(this.page.locator(`xpath=//*[contains(text(), "${text}")]`).first());
  }

  /** Elige la cuenta débito si el desplegable está visible (legado seleccionarCuentaDebitoSiEstaPresente). */
  public async selectDebitAccountIfShown(account: string): Promise<void> {
    const dropdown = this.page.locator("xpath=//span[contains(text(),'Cuenta débito')]//../../../div").first();

    if (await dropdown.isVisible()) {
      await dropdown.click();
      await this.page.locator(`xpath=//div[contains(text(), '${account}')]`).last().click();
    }
  }

  /** Marca la declaración "Sujeto Obligado UIF". */
  public async declareUifObligatedSubject(): Promise<void> {
    const checkbox = this.page
      .locator("xpath=//*[contains(@aria-label,'checkbox No chequeado Sujeto Obligado UIF')]")
      .first();
    await checkbox.scrollIntoViewIfNeeded();
    await checkbox.click();
  }

  public async continue(): Promise<void> {
    const continueButton = this.page.locator("xpath=//button[@aria-label='Continuar']").first();
    await continueButton.scrollIntoViewIfNeeded();
    await continueButton.click();
  }

  /**
   * "Solicitá tu turno acá." → Aceptar. Devuelve la pestaña que muestra Citas: la nueva si se abrió
   * dentro del límite, o la actual (legado ManageTab).
   */
  public async requestBranchAppointment(): Promise<Page> {
    const appointmentLink = this.page
      .locator("xpath=//a[@href='/appointmentMaker']/.//*[contains(text(), 'tu turno ac')]")
      .first();
    await expect(appointmentLink).toBeVisible();
    await appointmentLink.click();
    const newTab = this.page
      .context()
      .waitForEvent('page', { timeout: APPOINTMENT_TAB_TIMEOUT_MS })
      .catch(() => undefined);
    await this.page.locator("xpath=//button[@aria-label='Aceptar']").first().click();
    const appointmentPage = (await newTab) ?? this.page;
    await appointmentPage.waitForLoadState('domcontentloaded');
    return appointmentPage;
  }

  public async isTextShownIn(target: Page, text: string): Promise<boolean> {
    return becomesVisible(target.locator(`xpath=//*[contains(text(), "${text}")]`).first());
  }
}
