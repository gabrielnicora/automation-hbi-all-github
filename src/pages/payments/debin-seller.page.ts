import { expect, type Locator, type Page } from '@playwright/test';
import { SideMenu } from '../navigation/side-menu';
import { becomesVisible } from '../visibility';

export type BuyerIdentifierType = 'CBU' | 'ALIAS';

export class DebinSellerPage {
  private readonly menu: SideMenu;

  public constructor(private readonly page: Page) {
    this.menu = new SideMenu(page);
  }

  /** Pagos → Más Opciones → Debin vendedor. */
  public async open(): Promise<void> {
    await this.menu.openPayments();
    await expect(this.paymentsHeading).toBeVisible();
    await this.moreOptionsButton.click();
    await this.sellerLink.click();
    await expect(this.sellerHeading).toBeVisible();
  }

  public async openOrderGeneration(): Promise<void> {
    await this.generateOrderLink.click();
    await expect(this.generateOrderHeading).toBeVisible();
  }

  public async selectDestinationAccount(account: string): Promise<void> {
    await this.destinationAccountSelector.click();
    const option = this.page.locator('[id*="-option-"]').filter({ hasText: account });
    await expect(option).toBeVisible();
    await option.click();
    await expect(option).toBeHidden();
  }

  public async enterAmount(amount: string): Promise<void> {
    await this.amountInput.fill(amount);
  }

  public async selectBuyerIdentifierType(type: BuyerIdentifierType): Promise<void> {
    await this.page.locator(`label[for="${type}"]`).click();
    await expect(this.buyerIdentifierInput).toBeVisible();
  }

  public async enterBuyerIdentifier(value: string): Promise<void> {
    await this.buyerIdentifierInput.fill(value);
  }

  public async search(): Promise<void> {
    await this.searchButton.click();
  }

  public async isRequiredFieldMessageShown(): Promise<boolean> {
    return becomesVisible(this.page.getByText('Este campo es obligatorio').first());
  }

  private get amountInput(): Locator {
    return this.page.getByRole('textbox', { name: /^Importe/ });
  }

  private get buyerIdentifierInput(): Locator {
    return this.page.locator('#aliasCbu');
  }

  private get destinationAccountSelector(): Locator {
    return this.page.locator('#accountSelector');
  }

  private get generateOrderHeading(): Locator {
    return this.page.getByRole('heading', { level: 1, name: 'Generar orden de DEBIN' });
  }

  private get generateOrderLink(): Locator {
    return this.page.getByRole('link', { name: 'Generar orden de DEBIN' });
  }

  private get moreOptionsButton(): Locator {
    return this.page.getByRole('button', { name: 'Más Opciones' });
  }

  private get paymentsHeading(): Locator {
    return this.page.getByRole('heading', { level: 1, name: 'Pago de servicios' });
  }

  private get searchButton(): Locator {
    return this.page.getByRole('button', { name: 'Buscar', exact: true });
  }

  private get sellerHeading(): Locator {
    return this.page.getByRole('heading', { level: 1, name: /DEBIN vendedor/i });
  }

  private get sellerLink(): Locator {
    return this.page.getByRole('link', { name: 'Debin vendedor' });
  }
}
