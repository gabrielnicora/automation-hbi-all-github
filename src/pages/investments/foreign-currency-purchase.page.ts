import { expect, type Locator, type Page } from '@playwright/test';
import { becomesVisible } from '../visibility';

export interface ForeignCurrencyPurchaseForm {
  readonly operationType: string;
  readonly originAccount: string;
  readonly destinationAccount: string;
  readonly amount: string;
}

export class ForeignCurrencyPurchasePage {
  public constructor(private readonly page: Page) {}

  public async open(): Promise<void> {
    await this.page.getByRole('button', { name: 'Inversiones', exact: true }).click();
    await expect(this.manageInvestmentsButton).toBeVisible();
    await this.manageInvestmentsButton.click();
    await expect(this.purchaseLink).toBeVisible();
    await this.purchaseLink.click();
    await expect(this.pageTitle).toBeVisible();
  }

  public async completeForm(form: ForeignCurrencyPurchaseForm): Promise<void> {
    await this.selectOption(this.operationType, form.operationType);
    await this.selectOption(this.originAccount, form.originAccount);
    await this.selectOption(this.destinationAccount, form.destinationAccount, false);

    await this.amountInput.fill(form.amount);
    await expect(this.amountInput).toHaveValue(form.amount);
  }

  public async continue(): Promise<void> {
    await expect(this.continueButton).toBeEnabled();
    await this.continueButton.click();
    await expect(this.validationMessage).toHaveText(/\S/);
  }

  public async readValidationMessage(): Promise<string> {
    return (await this.validationMessage.innerText()).trim();
  }

  /** Elige el tipo de operación cuyo texto contiene el valor (legado seleccionarDropdownGenerico). */
  public async selectOperationType(label: string): Promise<void> {
    await this.selectOption(this.operationType, label, false);
  }

  /** Elige la cuenta origen cuyo texto contiene el valor. */
  public async selectOriginAccount(label: string): Promise<void> {
    await this.selectOption(this.originAccount, label, false);
  }

  /** Elige la cuenta destino cuyo texto contiene el valor (por ejemplo `USD` o `$`). */
  public async selectDestinationAccount(label: string): Promise<void> {
    await this.selectOption(this.destinationAccount, label, false);
  }

  /** Escribe el importe como el legado (`sendKeys` sobre `#userAmount`). */
  public async typeAmount(amount: string): Promise<void> {
    await expect(this.amountInput).toBeVisible();
    await this.amountInput.pressSequentially(amount);
    await expect(this.amountInput).toHaveValue(amount);
  }

  /** Presiona Continuar sin exigir un mensaje de validación (legado clickContinuarGenerico). */
  public async pressContinue(): Promise<void> {
    await this.continueButton.scrollIntoViewIfNeeded();
    await this.continueButton.click();
  }

  public async pressConfirm(): Promise<void> {
    const confirm = this.page.locator("xpath=//button[@aria-label='Confirmar']").first();
    await confirm.scrollIntoViewIfNeeded();
    await confirm.click();
  }

  /** Indica si algún elemento muestra el texto (legado `//*[contains(text(), ...)]`). */
  public async isMessageShown(text: string): Promise<boolean> {
    return becomesVisible(this.page.locator(`xpath=//*[contains(text(), "${text}")]`).first());
  }

  /**
   * Abre el desplegable de cuenta indicado e informa si muestra alguna opción que contiene el texto
   * (legado validarDesplegableCuentasEn* + keyDownXParametrosGenerico). Cierra el desplegable al terminar.
   */
  public async accountOptionsInclude(field: 'destination' | 'origin', text: string): Promise<boolean> {
    const container = field === 'origin' ? this.originAccount : this.destinationAccount;
    await expect(container).toBeVisible();
    await container.click();
    const shown = await becomesVisible(this.page.locator(`xpath=//div[contains(text(),"${text}")]`).first());
    await this.page.keyboard.press('Escape');
    return shown;
  }

  private async selectOption(container: Locator, label: string, exactMatch = true): Promise<void> {
    await expect(container).toBeVisible();
    const input = container.getByRole('combobox');
    const describedBy = await input.getAttribute('aria-describedby');
    const visibleControl = describedBy ? this.page.locator(`#${describedBy}`) : container;
    if (await visibleControl.isVisible()) {
      await visibleControl.click();
    } else {
      await container.click();
    }
    const option = this.page.getByText(label, { exact: exactMatch }).last();
    await expect(option).toBeVisible();
    await option.click();
    await expect(input).toHaveAttribute('aria-expanded', 'false');
    await expect(container).toContainText(label);
  }

  private get amountInput(): Locator {
    return this.page.locator('#userAmount');
  }

  private get continueButton(): Locator {
    return this.page.getByRole('button', { name: 'Continuar', exact: true });
  }

  private get destinationAccount(): Locator {
    return this.page.locator('#targetAccount');
  }

  private get manageInvestmentsButton(): Locator {
    return this.page.getByRole('button', { name: 'Gestioná tus inversiones', exact: true });
  }

  private get operationType(): Locator {
    return this.page.locator('#transferType');
  }

  private get originAccount(): Locator {
    return this.page.locator('#originAccount');
  }

  private get pageTitle(): Locator {
    return this.page.getByText('Compra venta de dólares', { exact: true });
  }

  private get purchaseLink(): Locator {
    return this.page.getByText('Compra venta de dólares', { exact: true });
  }

  private get validationMessage(): Locator {
    return this.page.getByRole('status').last();
  }
}
