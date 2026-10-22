import { expect, type Locator, type Page } from '@playwright/test';
import { SideMenu } from '../navigation/side-menu';
import { becomesVisible } from '../visibility';

const OPTIONAL_CONTROL_TIMEOUT_MS = 5_000;

/** Pago de servicios (legado PagosSteps, NuevoPagoSteps y botones genéricos de pagos). */
export class PaymentServicesPage {
  private readonly menu: SideMenu;

  public constructor(private readonly page: Page) {
    this.menu = new SideMenu(page);
  }

  /** Menú lateral → Pagos y servicios → Pagos; espera "Pago de servicios". */
  public async open(): Promise<void> {
    await this.menu.openPayments();
    await expect(this.text('Pago de servicios')).toBeVisible();
  }

  public async isTextShown(text: string): Promise<boolean> {
    return becomesVisible(this.text(text));
  }

  public async isShowAllShown(): Promise<boolean> {
    return becomesVisible(this.showAllLink, OPTIONAL_CONTROL_TIMEOUT_MS);
  }

  public async showAll(): Promise<void> {
    await this.showAllLink.scrollIntoViewIfNeeded();
    await this.showAllLink.click();
  }

  /** Indica si el listado muestra el servicio con esa descripción (`//p[contains(text(), …)]`). */
  public async isServiceShown(service: string): Promise<boolean> {
    return becomesVisible(this.paragraph(service), OPTIONAL_CONTROL_TIMEOUT_MS);
  }

  /** Abre una sección o acción con texto en `span` (por ejemplo "Servicios agendados", "Mis Comprobantes"). */
  public async openSpanOption(text: string): Promise<void> {
    await this.page.locator(`xpath=//span[contains(text(), '${text}')]`).first().click();
  }

  /** Abre una acción con texto en `p` (por ejemplo "Agendar servicio", "Adherir servicio"). */
  public async openParagraphOption(text: string): Promise<void> {
    await this.paragraph(text).click();
  }

  /** Marca el servicio a pagar en esa posición (locator legado por "Mercado Pago"). */
  public async selectServiceToPay(position: number): Promise<void> {
    const checkbox = this.page.locator(`xpath=(//p[contains(text(),'Mercado Pago')]//..//..//input/..)[${position}]`);
    await checkbox.scrollIntoViewIfNeeded();
    await checkbox.click();
  }

  public async pressPay(): Promise<void> {
    await this.page.locator("xpath=//span[contains(text(),'Pagar')]").first().click();
  }

  public async fillAmountAndReference(amount: string, reference: string): Promise<void> {
    const amountInput = this.page.locator('#amount');
    await expect(amountInput).toBeVisible();
    await amountInput.pressSequentially(amount);
    const referenceInput = this.page.locator('#reference');
    await expect(referenceInput).toBeVisible();
    await referenceInput.pressSequentially(reference);
  }

  public async pressContinue(): Promise<void> {
    const continueButton = this.page.locator("xpath=//button[@aria-label='Continuar']").first();
    await continueButton.scrollIntoViewIfNeeded();
    await continueButton.click();
  }

  public async pressConfirm(): Promise<void> {
    const confirm = this.page.locator("xpath=//button[@aria-label='Confirmar']").first();
    await confirm.scrollIntoViewIfNeeded();
    await confirm.click();
  }

  /** "Volver" con texto (legado clickVolverGenerico). */
  public async goBack(): Promise<void> {
    await expect(this.successNotification).toHaveCount(0);
    const back = this.page.locator("xpath=//*[contains(text(), 'Volver')]").first();
    await back.scrollIntoViewIfNeeded();
    await back.click();
  }

  /** Flecha "Volver" (legado clickVolverFlechaGenerico). */
  public async goBackWithArrow(): Promise<void> {
    await expect(this.successNotification).toHaveCount(0);
    await this.page.locator("xpath=//span[normalize-space(.)='Volver']/ancestor::div[1]").first().click();
  }

  private paragraph(text: string): Locator {
    return this.page.locator(`xpath=//p[contains(text(), '${text}')]`).first();
  }

  private text(text: string): Locator {
    return this.page.locator(`xpath=//*[contains(text(), '${text}')]`).first();
  }

  private get showAllLink(): Locator {
    return this.page.locator("xpath=//a[contains(text(),'Mostrar todos')]").first();
  }

  private get successNotification(): Locator {
    return this.page.locator("xpath=//div[@class='notification notification-success notification-visible']");
  }
}
