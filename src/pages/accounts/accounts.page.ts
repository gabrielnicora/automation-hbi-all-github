import { expect, type Locator, type Page } from '@playwright/test';
import { SideMenu } from '../navigation/side-menu';
import { becomesVisible } from '../visibility';

/**
 * Cuentas: detalle de movimientos y solicitud de cuenta nueva (legado CuentasSteps, DetalleCuentaSteps,
 * SolicitarNuevaCuentaSteps y botones genéricos). Los locators reproducen el legado.
 */
export class AccountsPage {
  private readonly menu: SideMenu;

  public constructor(private readonly page: Page) {
    this.menu = new SideMenu(page);
  }

  /** Menú lateral → Cuentas; cierra el cartel con "Cancelar" si aparece (legado clickBtnCancelarSobreCartelEmpezaAOperar). */
  public async open(): Promise<void> {
    await this.menu.openAccounts();
    await expect(this.page.getByText('Cuentas', { exact: true }).first()).toBeVisible();
    const cancel = this.page.locator("xpath=//button[@aria-label='Cancelar']").first();

    if (await becomesVisible(cancel, 5_000)) {
      await cancel.click();
      await expect(cancel).toBeHidden();
    }
  }

  public async isTextShown(text: string): Promise<boolean> {
    return becomesVisible(this.page.locator(`xpath=//*[contains(text(), "${text}")]`).first());
  }

  /** "Ver más" de la cuenta (legado getVerMasXnroCuenta). */
  public async openAccountDetail(account: string): Promise<void> {
    const viewMore = this.page
      .locator(`xpath=//span[contains(text(),'${account}')]/ancestor::div[1]/following-sibling::div/a`)
      .first();
    await expect(viewMore).toBeVisible();
    await viewMore.click();
    await expect(this.movementRow(1)).toBeVisible();
  }

  /** Abre el detalle del movimiento de la fila indicada (`div[aria-label='fila'][n]/div[5]`). */
  public async openMovementDetail(row: number): Promise<void> {
    const movement = this.movementRow(row);
    await movement.scrollIntoViewIfNeeded();
    await movement.click();
  }

  public async startNewAccountRequest(): Promise<void> {
    await this.page.locator("xpath=//span[contains(text(), 'Solicitar nueva cuenta')]").first().click();
    await expect(this.continueButton).toBeVisible();
  }

  public async isContinueEnabled(): Promise<boolean> {
    return this.continueButton.isEnabled();
  }

  /** Indica si "Continuar" se muestra deshabilitado (legado getBotonesDiables, espera 2 s). */
  public async isContinueShownDisabled(): Promise<boolean> {
    return becomesVisible(this.page.locator("xpath=//button[@disabled and @aria-label='Continuar']").first(), 2_000);
  }

  public async selectAccountType(type: string): Promise<void> {
    await this.page.locator('#selectorDescribedBy_0').click();
    await this.page.locator(`xpath=//div[contains(text(), '${type}')]`).last().click();
  }

  public async selectAccountCurrency(currency: string): Promise<void> {
    await this.page.locator('#selectorDescribedBy_1').click();
    await this.page.locator(`xpath=//div[contains(text(), '${currency}')]`).last().click();
  }

  private get continueButton(): Locator {
    return this.page.locator("xpath=//button[@aria-label='Continuar']").first();
  }

  private movementRow(row: number): Locator {
    return this.page.locator(`xpath=//div[@aria-label='fila'][${row}]/div[5]`).first();
  }
}
