import { expect, type Locator, type Page } from '@playwright/test';
import { becomesVisible } from '../visibility';

const RATE_RESULT_COLUMNS = ['Plazo Desde', 'Plazo Hasta', 'Tasa TNA pizarra'] as const;
export type RateResultColumn = (typeof RATE_RESULT_COLUMNS)[number];

/**
 * Plazo fijo: consulta de tasas y formulario de solicitud (legado PlazosFijosPage, ConsultaDeTasasPage,
 * SolicitarPlazoFijoPage y botones genéricos). Los locators reproducen los del legado; la navegación
 * por menú y su vigencia en BERSA QA están pendientes de validación UI.
 */
export class FixedTermsPage {
  public constructor(private readonly page: Page) {}

  /** Inversiones → Gestioná tus inversiones → Plazo fijo; espera la ruta `/fixedTerms`. */
  public async open(): Promise<void> {
    await this.page.getByRole('button', { name: 'Inversiones', exact: true }).click();
    const manageInvestments = this.page.getByRole('button', { name: 'Gestioná tus inversiones', exact: true });
    await expect(manageInvestments).toBeVisible();
    await manageInvestments.click();
    const fixedTermsEntry = this.page.locator("xpath=//span[contains(text(),'Plazo fijo')]/../..").first();
    await expect(fixedTermsEntry).toBeVisible();
    await fixedTermsEntry.click();
    await expect(this.page).toHaveURL(/\/fixedTerms/);
  }

  public async isTextShown(text: string): Promise<boolean> {
    return becomesVisible(this.page.locator(`xpath=//*[contains(text(), '${text}')]`).first());
  }

  public async openRatesQuery(): Promise<void> {
    await this.page.locator("xpath=//a[@href='/fixedTerms/rates']").first().click();
    await expect(this.page).toHaveURL(/\/fixedTerms\/rates/);
  }

  /** Elige el tipo de plazo fijo en la consulta de tasas (opción que contiene el texto). */
  public async selectRateQueryType(option: string): Promise<void> {
    await this.page.locator('[id="investments.rate.query.fixedTermType"]').first().click();
    await this.optionContaining(option).click();
  }

  public async fillRateQueryAmount(amount: string): Promise<void> {
    const input = this.page.locator('#amount').first();
    await expect(input).toBeVisible();
    await input.pressSequentially(amount);
  }

  public async searchRates(): Promise<void> {
    const search = this.page.locator("xpath=//button[@aria-label='Buscar']").first();
    await search.scrollIntoViewIfNeeded();
    await search.click();
  }

  /** Columnas del resultado de la consulta que se ven en pantalla. */
  public async readVisibleRateColumns(): Promise<readonly RateResultColumn[]> {
    const visible: RateResultColumn[] = [];

    for (const column of RATE_RESULT_COLUMNS) {
      const header = this.page.locator(`xpath=//*[@id='mainLayout']//span[contains(text(),'${column}')]`).first();

      if (await becomesVisible(header, 10_000)) {
        visible.push(column);
      }
    }

    return visible;
  }

  public async openRequest(): Promise<void> {
    await this.page.locator("xpath=//span[contains(text(),'Solicitar plazo fijo')]/../..").first().click();
    await expect(this.continueButton).toBeVisible();
  }

  public async isContinueEnabled(): Promise<boolean> {
    return this.continueButton.isEnabled();
  }

  /** Abre "Cuenta débito" y elige la cuenta (`#account`). */
  public async selectDebitAccount(account: string): Promise<void> {
    await this.page.locator('#account').first().click();
    await this.optionContaining(account).click();
  }

  /** Texto mostrado para la cuenta débito seleccionada, que incluye su saldo. */
  public async readSelectedDebitAccountText(): Promise<string> {
    const selected = this.page.locator("xpath=(//span[contains(text(),'Cuenta débito')]/../../..//div)[1]").first();
    await expect(selected).toBeVisible();
    return selected.innerText();
  }

  /** Abre el selector de tipo y elige la opción que contiene el texto. */
  public async selectType(option: string): Promise<void> {
    await this.typeSelector.scrollIntoViewIfNeeded();
    await this.typeSelector.click();
    await this.optionContaining(option).click();
  }

  /** Abre el selector de tipo y elige exactamente "Plazo Fijo UVA" (legado clickPlazoFijoUVA). */
  public async selectUvaType(): Promise<void> {
    await this.typeSelector.scrollIntoViewIfNeeded();
    await this.typeSelector.click();
    await this.page.locator("xpath=//div[text()='Plazo Fijo UVA']").first().click();
  }

  public async typeAmount(amount: string): Promise<void> {
    await this.amountInput.click();
    await this.amountInput.pressSequentially(amount);
  }

  /** Borra el importe con retrocesos, como el legado (clearCampoImporte). */
  public async clearAmount(): Promise<void> {
    await this.amountInput.click();
    const length = (await this.amountInput.inputValue()).length;

    for (let index = 0; index < length; index += 1) {
      await this.amountInput.press('Backspace');
    }

    await expect(this.amountInput).toHaveValue('');
  }

  public async typeTermDays(days: string): Promise<void> {
    await this.page.locator('#term').first().pressSequentially(days);
  }

  public async selectMaturityAction(option: string): Promise<void> {
    await this.page.locator('#selectorDescribedBy_2').first().click();
    await this.optionContaining(option).click();
  }

  /** Presiona Continuar si está disponible (legado clickContinuarGenerico, que no falla si no avanza). */
  public async pressContinue(): Promise<void> {
    await this.continueButton.scrollIntoViewIfNeeded();
    await this.continueButton.click({ trial: !(await this.continueButton.isEnabled()) });
  }

  /** Indica si se muestra el mensaje de error del formulario (locator posicional del legado). */
  public async isFormErrorShown(): Promise<boolean> {
    return becomesVisible(this.page.locator("xpath=//*[@id='mainLayout']/div[1]/div[2]/div/div/div[2]/div/p").first());
  }

  /** Vuelve con la flecha "Volver" cuando no hay notificación de éxito visible. */
  public async goBack(): Promise<void> {
    await expect(this.page.locator('div.notification.notification-success.notification-visible')).toHaveCount(0);
    const back = this.page.locator("xpath=//span[normalize-space(.)='Volver']/ancestor::div[1]").first();
    await expect(back).toBeEnabled();
    await back.click();
    await expect(this.solicitButton).toBeVisible();
  }

  private get amountInput(): Locator {
    return this.page.locator('#amount').first();
  }

  private get continueButton(): Locator {
    return this.page.locator("xpath=//button[@aria-label='Continuar']").first();
  }

  private get solicitButton(): Locator {
    return this.page.locator("xpath=//span[contains(text(),'Solicitar plazo fijo')]/../..").first();
  }

  private get typeSelector(): Locator {
    return this.page.locator("xpath=//*[@id='selectorControlBy_account']/div[1]").first();
  }

  /** Opción visible cuyo texto contiene el valor (legado `//div[contains(text(), ...)]`). */
  private optionContaining(text: string): Locator {
    return this.page.locator(`xpath=//div[contains(text(), '${text}')]`).last();
  }
}
