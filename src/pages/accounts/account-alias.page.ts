import { expect, type Locator, type Page } from '@playwright/test';

export class AccountAliasPage {
  public constructor(private readonly page: Page) {}

  public async openAliasEditor(account: string): Promise<void> {
    await expect(this.accountsMenuButton).toBeVisible();
    await this.accountsMenuButton.click();
    await expect(this.page).toHaveURL(/\/accounts\/?$/);
    await expect(this.accountsTitle).toBeVisible();

    if (await this.cancelButton.isVisible()) {
      await this.cancelButton.click();
    }

    await expect(this.accountLabel(account)).toBeVisible();
    await this.viewMoreForAccount(account).click();
    await this.modifyAliasLink.click();
    await expect(this.aliasTitle).toBeVisible();
    await expect(this.aliasInput).toBeVisible();
  }

  public async isSaveDisabled(): Promise<boolean> {
    await expect(this.saveButton).toBeVisible();
    return this.saveButton.isDisabled();
  }

  public async enterAlias(alias: string): Promise<void> {
    await expect(this.aliasInput).toBeVisible();
    await this.aliasInput.fill(alias);
  }

  private accountLabel(account: string): Locator {
    return this.page.getByText(account, { exact: true });
  }

  private viewMoreForAccount(account: string): Locator {
    return this.accountLabel(account).locator('xpath=ancestor::div[1]/following-sibling::div/a');
  }

  private get accountsTitle(): Locator {
    return this.page.getByText('Cuentas', { exact: true }).first();
  }

  private get accountsMenuButton(): Locator {
    return this.page.getByRole('button', { name: 'Cuentas', exact: true });
  }

  private get aliasInput(): Locator {
    return this.page.locator('#cbuAlias');
  }

  private get aliasTitle(): Locator {
    return this.page.getByText('Modificar Alias').first();
  }

  private get cancelButton(): Locator {
    return this.page.getByRole('button', { name: 'Cancelar', exact: true });
  }

  private get modifyAliasLink(): Locator {
    return this.page.getByRole('link', { name: 'Modificar Alias' });
  }

  private get saveButton(): Locator {
    return this.page.getByRole('button', { name: 'Guardar', exact: true });
  }
}
