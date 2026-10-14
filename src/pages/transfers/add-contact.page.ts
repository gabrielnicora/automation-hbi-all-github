import { expect, type Locator, type Page } from '@playwright/test';
import { SideMenu } from '../navigation/side-menu';
import { becomesVisible } from '../visibility';

export class AddContactPage {
  private readonly menu: SideMenu;

  public constructor(private readonly page: Page) {
    this.menu = new SideMenu(page);
  }

  /** Llega a "Agendar contacto" desde Transferencias → A un contacto agendado → Agregar contacto. */
  public async open(): Promise<void> {
    await this.menu.openTransfers();
    await expect(this.scheduledContactsSection).toBeVisible();
    await this.scheduledContactsSection.click();
    await this.addContactButton.click();
    await expect(this.addContactHeading).toBeVisible();
  }

  /** Busca un CBU, CVU o alias tal como lo recibe el escenario. */
  public async search(cbuOrAlias: string): Promise<void> {
    await expect(this.searchInput).toBeVisible();
    await this.searchInput.fill(cbuOrAlias);
    await expect(this.searchButton).toBeEnabled();
    await this.searchButton.click();
  }

  public async isMessageShown(message: string): Promise<boolean> {
    return becomesVisible(this.page.getByText(message).first());
  }

  private get addContactButton(): Locator {
    return this.page.getByRole('button', { name: 'Agregar contacto', exact: true });
  }

  private get addContactHeading(): Locator {
    return this.page.getByRole('heading', { level: 1, name: 'Agendar contacto' });
  }

  private get scheduledContactsSection(): Locator {
    return this.page.getByRole('heading', { name: 'A un contacto agendado' });
  }

  private get searchButton(): Locator {
    return this.page.getByRole('button', { name: 'Buscar', exact: true });
  }

  private get searchInput(): Locator {
    return this.page.getByPlaceholder('Ingresá CBU, CVU o Alias');
  }
}
