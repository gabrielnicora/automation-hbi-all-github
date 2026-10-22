import { expect, type Page } from '@playwright/test';
import { becomesVisible } from '../visibility';

/** Generar CREDIN por UI (legado ConsultaCredinSteps / GenerarCredinSteps y sus pages). */
export class CredinRequestPage {
  public constructor(private readonly page: Page) {}

  public async open(): Promise<void> {
    await this.page.locator("xpath=(//*[@id='mainLayout']//span[contains(text(),'Generar CREDIN')])").first().click();
    await expect(this.page.locator("xpath=//input[@name='accountSelector']/..").first()).toBeVisible();
  }

  /**
   * Abre "Cuenta débito" y comprueba que se muestre la cuenta, sin elegirla
   * (legado clickCuentaDebito + keyDownXParametrosGenerico(0, cuenta)).
   */
  public async isDebitAccountOffered(account: string): Promise<boolean> {
    await this.page.locator("xpath=//input[@name='accountSelector']/..").first().click();
    return becomesVisible(this.page.locator(`xpath=//div[contains(text(),'${account}')]`).first());
  }

  public async fillDestinationCbu(cbu: string): Promise<void> {
    await this.page.locator("xpath=//div[contains(@aria-label,'CBU/CVU')]//label[@for='CBU']/div").first().click();
    await this.page.locator('#aliasCbu').pressSequentially(cbu);
  }

  /** Abre el concepto y comprueba que se ofrezca la opción, sin elegirla (mismo patrón legado). */
  public async isConceptOffered(concept: string): Promise<boolean> {
    const selector = this.page.locator('#selectorDescribedBy_1');
    await selector.scrollIntoViewIfNeeded();
    await selector.click();
    return becomesVisible(this.page.locator(`xpath=//div[contains(text(),'${concept}')]`).first());
  }

  public async fillAmountAndDescription(amount: string, description: string): Promise<void> {
    await this.page.locator('#amount').pressSequentially(amount);
    await this.page.locator('[name="description"]').pressSequentially(description);
  }

  /** Marca "Es cuenta propia", Continuar y acepta términos; espera el segundo factor. */
  public async continueAsOwnAccount(): Promise<void> {
    const checkbox = this.page.locator("xpath=//input[@type='checkbox']/following-sibling::span").first();
    await checkbox.scrollIntoViewIfNeeded();
    await checkbox.click();
    const continueButton = this.page.locator("xpath=//button[@aria-label='Continuar']").first();
    await continueButton.scrollIntoViewIfNeeded();
    await continueButton.click();
    const terms = this.page.locator("xpath=//*[@type='checkbox']/following-sibling::span").first();
    await terms.scrollIntoViewIfNeeded();
    await terms.click();
    await expect(this.page.locator('#secondFactor')).toBeVisible();
  }

  public async enterSecondFactorCode(code: string): Promise<void> {
    await this.page.locator('#secondFactor').fill(code);
    const confirm = this.page.locator("xpath=//button[@aria-label='Confirmar']").first();
    await confirm.scrollIntoViewIfNeeded();
    await confirm.click();
  }
}
