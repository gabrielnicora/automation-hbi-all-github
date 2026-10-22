import { expect, type Locator, type Page } from '@playwright/test';
import { becomesVisible } from '../visibility';

/** Comprobantes de pago (legado ComprobantesSteps). */
export class PaymentReceiptsPage {
  public constructor(private readonly page: Page) {}

  public async isTitleShown(): Promise<boolean> {
    return becomesVisible(this.page.locator("xpath=//*[contains(text(), 'Comprobantes de pago')]").first());
  }

  /** Despliega "Seleccionar", elige la opción y continúa (legado clickListaDesplegableGenerico). */
  public async selectOriginAndContinue(option: string): Promise<void> {
    await this.option('Seleccionar').first().click();
    await this.option(option).last().click();
    const continueButton = this.page.locator("xpath=//button[@aria-label='Continuar']").first();
    await continueButton.scrollIntoViewIfNeeded();
    await continueButton.click();
  }

  public async searchByCompanyAndDescription(company: string, description: string): Promise<void> {
    await this.page.locator("xpath=//input[@name='ente']/parent::div").click();
    const companyOption = this.option(company).last();
    await companyOption.scrollIntoViewIfNeeded();
    await companyOption.click();
    await this.page.locator("xpath=//input[@name='payment']/parent::div").click();
    await this.option(description).last().click();
    await this.page.locator("xpath=//span[contains(text(),'Buscar')]").first().click();
  }

  /** Indica si el resultado muestra el comprobante (legado validarComprobante). */
  public async isReceiptShown(code: string): Promise<boolean> {
    const receipt = this.page.locator(`xpath=//p[contains(text(), '${code}')]`).first();
    await expect(receipt).toBeAttached();
    return receipt.isVisible();
  }

  private option(text: string): Locator {
    return this.page.locator(`xpath=//div[contains(text(), '${text}')]`);
  }
}
