import { expect, type Locator, type Page } from '@playwright/test';
import { becomesVisible } from '../visibility';

/**
 * Apertura de cuenta comitente (legado CuentaComitenteSteps / CuentaComitentePage y botones genéricos).
 * Los locators reproducen los del legado; su vigencia en BERSA QA está pendiente de validación UI.
 */
export class CommitentAccountOpeningPage {
  public constructor(private readonly page: Page) {}

  public async requestAccount(): Promise<void> {
    const request = this.page.locator("xpath=//button[@aria-label='Solicitar cuenta']").first();
    await expect(request).toBeVisible();
    await request.click();
  }

  public async continue(): Promise<void> {
    const continueButton = this.page.locator("xpath=//button[@aria-label='Continuar']").first();
    await continueButton.scrollIntoViewIfNeeded();
    await continueButton.click();
  }

  /** Marca "Ninguna de las anteriores" en las declaraciones (`span#nao`). */
  public async declareNoneOfTheAbove(): Promise<void> {
    const checkbox = this.page.locator('span#nao').first();
    await checkbox.scrollIntoViewIfNeeded();
    await expect(checkbox).toBeVisible();
    await checkbox.click();
  }

  public async checkTermsAndConditions(): Promise<void> {
    const checkbox = this.page.locator("xpath=//span[contains(text(),'Términos y Condiciones')]/../../span").first();
    await checkbox.scrollIntoViewIfNeeded();
    await expect(checkbox).toBeVisible();
    await checkbox.click();
  }

  public async acceptTermsAndConditions(): Promise<void> {
    const accept = this.page.locator('[id="termsAndConditions.component.aceptar.label"]').first();
    await accept.scrollIntoViewIfNeeded();
    await accept.click();
  }

  public async confirm(): Promise<void> {
    const confirm = this.page.locator("xpath=//button[@aria-label='Confirmar']").first();
    await confirm.scrollIntoViewIfNeeded();
    await confirm.click();
  }

  public async isTextShown(text: string): Promise<boolean> {
    return becomesVisible(this.containingText(text));
  }

  /** Indica si se ve la etapa con su número y título (legado validarNumyTextDeEtapa). */
  public async isStepShown(stepNumber: string, stepTitle: string): Promise<boolean> {
    const number = this.page.locator(`xpath=//div[contains(text(), '${stepNumber}')]`).first();
    const title = this.page.locator(`xpath=//p[contains(text(), '${stepTitle}')]`).first();
    return (await becomesVisible(number)) && (await becomesVisible(title));
  }

  private containingText(text: string): Locator {
    return this.page.locator(`xpath=//*[contains(text(), '${text}')]`).first();
  }
}
