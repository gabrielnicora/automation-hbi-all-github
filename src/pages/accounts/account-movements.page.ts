import { expect, type Locator, type Page } from '@playwright/test';

export class AccountMovementsPage {
  public constructor(private readonly page: Page) {}

  public async openAccountDetails(account: string): Promise<void> {
    await expect(this.accountsMenuButton).toBeVisible();
    await this.accountsMenuButton.click();
    await expect(this.accountsTitle).toBeVisible();
    if (await this.cancelButton.isVisible()) {
      await this.cancelButton.click();
    }
    await expect(this.accountLabel(account)).toBeVisible();
    await this.viewMoreForAccount(account).click();
    await expect(this.moreFiltersButton).toBeVisible();
  }

  public async applyCreationDateFilter(monthsBack: number, day: string): Promise<void> {
    await this.moreFiltersButton.click();
    await expect(this.creationDateFrom).toBeVisible();
    await this.creationDateFrom.click();

    for (let month = 0; month < monthsBack; month += 1) {
      await this.previousMonthButton.click();
    }

    await this.calendarDay(day).click();
    await this.searchButton.click();
    await expect(this.movementDetailLink.first()).toBeVisible();
  }

  public async filterByTransactionType(): Promise<void> {
    await this.filterByDropdown.click();
    await expect(this.transactionTypeOption).toBeVisible();
    await this.transactionTypeOption.click();
    await this.searchButton.click();
    await expect(this.movementDetailLink.first()).toBeVisible();
  }

  public async readFirstMovementDetail(): Promise<string> {
    await expect(this.movementDetailLink.first()).toBeVisible();
    await this.movementDetailLink.first().click();
    await expect(this.movementDetails).toBeVisible();
    return (await this.movementDetails.innerText()).trim();
  }

  public async hasMovement(): Promise<boolean> {
    return this.movementDetailLink.first().isVisible();
  }

  private accountLabel(account: string): Locator {
    return this.page.getByText(account, { exact: true });
  }

  private viewMoreForAccount(account: string): Locator {
    return this.accountLabel(account).locator('xpath=ancestor::div[1]/following-sibling::div/a');
  }

  private calendarDay(day: string): Locator {
    return this.page
      .locator(`//div[text()='${day}' and contains(@class,'day') and not(contains(@class,'outside'))]`)
      .last();
  }

  private get accountsMenuButton(): Locator {
    return this.page.getByRole('button', { name: 'Cuentas', exact: true });
  }

  private get accountsTitle(): Locator {
    return this.page.getByText('Cuentas', { exact: true }).first();
  }

  private get cancelButton(): Locator {
    return this.page.getByRole('button', { name: 'Cancelar', exact: true });
  }

  private get creationDateFrom(): Locator {
    return this.page.locator("//div[contains(@aria-label,'esde')]/div").first();
  }

  private get filterByDropdown(): Locator {
    return this.page.locator("//span[contains(text(),'Buscar por')]/../../div");
  }

  private get moreFiltersButton(): Locator {
    return this.page.getByRole('button', { name: 'MAS FILTROS', exact: true });
  }

  private get movementDetailLink(): Locator {
    return this.page.locator("//div[@aria-label='fila']/div[5]");
  }

  private get movementDetails(): Locator {
    return this.page.locator('main');
  }

  private get previousMonthButton(): Locator {
    return this.page.getByRole('button', { name: 'Previous Month' });
  }

  private get searchButton(): Locator {
    return this.page.getByRole('button', { name: 'Buscar', exact: true });
  }

  private get transactionTypeOption(): Locator {
    return this.page.getByText('Tipo de transacción', { exact: true }).last();
  }
}
