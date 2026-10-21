import { expect, type Locator, type Page } from '@playwright/test';
import { SideMenu } from '../navigation/side-menu';
import { becomesVisible } from '../visibility';

export interface ContractRedirectResult {
  readonly url: string;
}

export class SalaryAdvancePage {
  private readonly menu: SideMenu;

  public constructor(private readonly page: Page) {
    this.menu = new SideMenu(page);
  }

  /** Cuentas → detalle de la cuenta → Más Opciones → Solicitud Adelanto de Sueldo. */
  public async openRequest(account: string): Promise<void> {
    await this.menu.openAccounts();
    await expect(this.accountsHeading).toBeVisible();
    await this.dismissDollarAccountOffer();
    await this.accountCard(account).getByRole('link', { name: 'Mostrar más' }).click();
    await expect(this.accountDetailHeading).toBeVisible();
    await this.moreOptionsButton.click();
    await this.requestLink.click();
    await expect(this.page).toHaveURL(/\/salaryAdvance\/request\//);
    await expect(this.contractSummaryLink).toBeVisible({ timeout: 90_000 });
  }

  /** Indica si la pantalla muestra una oferta; el caso decide qué textos corresponden en cada estado. */
  public async hasOfferAvailable(): Promise<boolean> {
    return !(await becomesVisible(this.noOfferMessage, 5_000));
  }

  public async isTextShown(text: string): Promise<boolean> {
    return becomesVisible(this.page.getByText(text).first());
  }

  public async isContinueEnabled(): Promise<boolean> {
    return this.continueButton.isEnabled();
  }

  /** "¿Cuánto necesitás?" (`#amount`). */
  public async fillRequestedAmount(amount: string): Promise<void> {
    const input = this.page.locator('#amount');
    await expect(input).toBeVisible();
    await input.pressSequentially(amount);
  }

  public async pressContinue(): Promise<void> {
    await this.continueButton.scrollIntoViewIfNeeded();
    await this.continueButton.click();
  }

  public async pressConfirm(): Promise<void> {
    const confirm = this.page.locator("xpath=//button[@aria-label='Confirmar']").first();
    await confirm.scrollIntoViewIfNeeded();
    await confirm.click();
  }

  /** Flecha "Volver" (legado clickVolverFlechaGenerico); espera la solicitud de adelanto. */
  public async goBackWithArrow(): Promise<void> {
    await expect(this.page.locator('div.notification.notification-success.notification-visible')).toHaveCount(0);
    await this.page.locator("xpath=//span[normalize-space(.)='Volver']/ancestor::div[1]").first().click();
    await expect(this.contractSummaryLink).toBeVisible();
  }

  /** Indica si se ve la notificación de error de monto permitido (legado validarAlertMontoPermitido). */
  public async isAmountErrorShown(): Promise<boolean> {
    return becomesVisible(this.page.locator('div.notification.notification-error.notification-visible').first());
  }

  public async openContractSummary(): Promise<void> {
    await this.contractSummaryLink.click();
    await expect(this.redirectDialogHeading).toBeVisible();
  }

  public async acceptContractRedirect(): Promise<ContractRedirectResult> {
    const [contractPage] = await Promise.all([
      this.page.context().waitForEvent('page'),
      this.dialogButton('Aceptar').click(),
    ]);

    // El destino puede ser un documento (PDF) que no dispara domcontentloaded: se espera sólo que la pestaña
    // abandone about:blank, con un límite explícito.
    await expect
      .poll(() => contractPage.url(), { message: 'La pestaña del contrato debe recibir una URL.', timeout: 15_000 })
      .not.toBe('about:blank');
    const result = { url: contractPage.url() };
    await contractPage.close();
    return result;
  }

  public async cancelContractRedirect(): Promise<void> {
    await this.dialogButton('Cancelar').click();
    await expect(this.redirectDialogHeading).toBeHidden();
  }

  private get accountDetailHeading(): Locator {
    return this.page.getByRole('heading', { level: 1, name: /^Alias de producto/ });
  }

  private accountCard(account: string): Locator {
    return this.page
      .locator('div')
      .filter({ has: this.page.getByText(account, { exact: true }) })
      .filter({ has: this.page.getByRole('link', { name: 'Mostrar más' }) })
      .last();
  }

  private get accountsHeading(): Locator {
    return this.page.getByRole('heading', { level: 1, name: 'Cuentas' });
  }

  private get continueButton(): Locator {
    return this.page.getByRole('button', { name: 'Continuar', exact: true });
  }

  private get contractSummaryLink(): Locator {
    return this.page.getByText('Resumen de contrato').first();
  }

  private dialogButton(name: string): Locator {
    return this.page.getByRole('dialog').getByRole('button', { name, exact: true }).last();
  }

  private async dismissDollarAccountOffer(): Promise<void> {
    const cancelButton = this.page.getByRole('button', { name: 'Cancelar', exact: true });

    if (await becomesVisible(cancelButton, 5_000)) {
      await cancelButton.click();
      await expect(cancelButton).toBeHidden();
    }
  }

  private get moreOptionsButton(): Locator {
    return this.page.getByRole('button', { name: 'Más Opciones' });
  }

  private get noOfferMessage(): Locator {
    return this.page.getByText('No contamos con una oferta disponible para vos!');
  }

  private get redirectDialogHeading(): Locator {
    return this.page.getByRole('heading', { level: 2, name: 'Solicitud de Adelanto de Sueldo' });
  }

  private get requestLink(): Locator {
    return this.page.getByRole('link', { name: 'Solicitud Adelanto de Sueldo' });
  }
}
