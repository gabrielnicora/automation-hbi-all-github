import { expect, type Locator, type Page } from '@playwright/test';
import { becomesVisible } from '../visibility';

const CONTRACT_TAB_TIMEOUT_MS = 15_000;

/** Solicitud de préstamos (legado PrestamosSteps / PrestamosParaCancelarDeudasSteps y sus pages). */
export class LoanRequestPage {
  public constructor(private readonly page: Page) {}

  /** Menú lateral "Préstamos" → "Nuevo préstamo". */
  public async openNewLoan(): Promise<void> {
    await this.openLoansMenu();
    await this.page.locator("xpath=//p[contains(text(),'Nuevo pr')]/..").first().click();
    await expect(this.page).toHaveURL(/\/loanRequest\/1/);
  }

  /** Menú lateral "Préstamos" → "Préstamo para cancelar deudas". */
  public async openDebtCancellationLoan(): Promise<void> {
    await this.openLoansMenu();
    await this.page.locator("xpath=//p[contains(text(),'tamo para cancelar deudas')]/..").first().click();
    await expect(this.page).toHaveURL(/\/loanRequest\/cancellations/);
  }

  public async isTextShown(text: string): Promise<boolean> {
    return becomesVisible(this.page.locator(`xpath=//*[contains(text(), "${text}")]`).first());
  }

  /** Indica si se ve la etapa con ese título (`//p[contains(text(), ...)]`). */
  public async isStepShown(title: string): Promise<boolean> {
    return becomesVisible(this.page.locator(`xpath=//p[contains(text(),'${title}')]`).first());
  }

  public async fillAmount(amount: string): Promise<void> {
    const input = this.page.locator("xpath=//*[@name='amount']").first();
    await input.scrollIntoViewIfNeeded();
    await expect(input).toBeVisible();
    await input.click();
    await input.pressSequentially(amount);
  }

  public async next(): Promise<void> {
    const next = this.page.locator("xpath=//button[@aria-label='Siguiente']").first();
    await next.scrollIntoViewIfNeeded();
    await next.click();
  }

  public async selectDestination(destination: string): Promise<void> {
    const selector = this.page.locator("xpath=//input[@name='loanDestination']/..").first();
    await expect(selector).toBeVisible();
    await selector.click();
    await this.page.locator(`xpath=//*[contains(text(), '${destination}')]`).last().click();
  }

  public async continue(): Promise<void> {
    await this.continueButton.scrollIntoViewIfNeeded();
    await this.continueButton.click();
  }

  /** Elige 1 cuota (`label[for='1']`). */
  public async selectOneInstallment(): Promise<void> {
    const label = this.page.locator("xpath=//label[@for='1']").first();
    await label.scrollIntoViewIfNeeded();
    await label.click();
  }

  public async acceptTerms(): Promise<void> {
    const terms = this.page.locator("xpath=//*[@name='agreement']/following-sibling::span").first();
    await terms.scrollIntoViewIfNeeded();
    await expect(terms).toBeVisible();
    await terms.click();
  }

  /** Marca el primer préstamo a cancelar. */
  public async selectFirstDebt(): Promise<void> {
    const checkbox = this.page.locator("xpath=(//p[contains(text(),'PRÉSTAMO')]//..//../div)[2]");
    await expect(checkbox).toBeVisible();
    await checkbox.click();
  }

  /** "Resumen de contrato" → Aceptar; devuelve la URL de la pestaña abierta (legado validarFragmentoURL). */
  public async openContractSummary(): Promise<string> {
    const summary = this.page.locator("xpath=//span[contains(text(),'Resumen de ')]").first();
    await expect(summary).toBeVisible();
    await summary.click();
    const [contractTab] = await Promise.all([
      this.page.context().waitForEvent('page', { timeout: CONTRACT_TAB_TIMEOUT_MS }),
      this.page.locator("xpath=//button[@aria-label='Aceptar']").first().click(),
    ]);
    await expect
      .poll(() => contractTab.url(), { message: 'La pestaña del contrato debe recibir una URL.', timeout: 15_000 })
      .not.toBe('about:blank');
    const url = contractTab.url();
    await contractTab.close();
    return url;
  }

  private async openLoansMenu(): Promise<void> {
    await this.page.locator("xpath=//p[contains(text(),'stamos')]//ancestor::button").first().click();
  }

  private get continueButton(): Locator {
    return this.page.locator("xpath=//button[@aria-label='Continuar']").first();
  }
}
