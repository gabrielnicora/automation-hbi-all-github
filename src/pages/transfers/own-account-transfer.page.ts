import { expect, type Page } from '@playwright/test';

export interface OwnAccountTransferDetails {
  readonly amount: string;
  readonly description: string;
}

export class OwnAccountTransferPage {
  public constructor(private readonly page: Page) {}

  public async open(): Promise<void> {
    await this.page.getByRole('link', { name: 'Transferir', exact: true }).click();
    await expect(this.transferHeading).toBeVisible();
  }

  public async selectRecipientByCbu(destinationCbu: string): Promise<void> {
    await this.cbuSectionHeading.click();
    await this.cbuInput.fill(destinationCbu);
    await this.searchButton.click();
    await expect(this.contactReviewHeading).toBeVisible();
    await this.correctContactButton.click();
    await expect(this.amountInput).toBeVisible();
  }

  public async completeDetails(details: OwnAccountTransferDetails): Promise<void> {
    await this.amountInput.fill(details.amount);
    await this.descriptionInput.fill(details.description);
    await expect(this.transferNowButton).toBeEnabled();
    await this.transferNowButton.click();
    await expect(this.transferReviewHeading).toBeVisible();
  }

  public async readReview(): Promise<string> {
    return (await this.transferReviewData.innerText()).trim();
  }

  public async confirm(): Promise<void> {
    await expect(this.confirmButton).toBeEnabled();
    await this.confirmButton.click();
    await expect(this.confirmButton).toBeHidden();
  }

  public async readTicket(): Promise<string> {
    return (await this.ticketData.innerText()).trim();
  }

  private get transferHeading() {
    return this.page.getByRole('heading', { name: 'Transferencias inmediatas', exact: true });
  }

  private get cbuSectionHeading() {
    return this.page.getByRole('heading', { name: 'A un CBU, CVU o Alias', exact: true });
  }

  private get cbuInput() {
    return this.page.getByPlaceholder('Ingresá CBU, CVU o Alias');
  }

  private get searchButton() {
    return this.page.getByRole('button', { name: 'Buscar', exact: true });
  }

  private get contactReviewHeading() {
    return this.page.getByRole('heading', { name: 'Revisá los datos del contacto', exact: true });
  }

  private get correctContactButton() {
    return this.page.getByRole('button', { name: 'Son correctos', exact: true });
  }

  private get amountInput() {
    return this.page.locator('input[placeholder="0,00"]');
  }

  private get descriptionInput() {
    return this.page.getByRole('textbox', { name: 'Descripción', exact: true });
  }

  private get transferNowButton() {
    return this.page.getByRole('button', { name: 'Transferir ahora', exact: true });
  }

  private get transferReviewHeading() {
    return this.page.getByRole('heading', { name: 'Revisá que esté todo bien', exact: true });
  }

  private get transferReviewData() {
    return this.page.locator('main');
  }

  private get confirmButton() {
    return this.page.getByRole('button', { name: 'Confirmar', exact: true });
  }

  private get ticketData() {
    return this.page.locator('main');
  }
}
