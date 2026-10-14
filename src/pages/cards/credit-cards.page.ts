import { expect, type Locator, type Page } from '@playwright/test';
import { SideMenu } from '../navigation/side-menu';
import { becomesVisible } from '../visibility';

export class CreditCardsPage {
  private readonly menu: SideMenu;

  public constructor(private readonly page: Page) {
    this.menu = new SideMenu(page);
  }

  public async open(): Promise<void> {
    await this.menu.openCreditCards();
    await expect(this.listHeading).toBeVisible();
  }

  /** Abre el detalle de la tarjeta identificada por sus últimos dígitos. */
  public async openDetail(lastDigits: string): Promise<void> {
    await this.detailLink(lastDigits).click();
    await expect(this.detailHeading).toBeVisible();
  }

  /** Devuelve el título del detalle tal como lo anuncia la pantalla (alias y número de tarjeta). */
  public async readDetailTitle(): Promise<string> {
    await expect(this.detailHeading).toBeVisible();
    const label = await this.detailHeading.getAttribute('aria-label');
    return (label ?? (await this.detailHeading.innerText())).trim();
  }

  public async isStatementDownloadAvailable(): Promise<boolean> {
    return becomesVisible(this.page.getByRole('link', { name: /Descargar Res/ }));
  }

  public async isPeriodMovementsAvailable(): Promise<boolean> {
    return becomesVisible(this.page.getByText(/Movimientos del per/).first());
  }

  public async isAdditionalCardRequestAvailable(): Promise<boolean> {
    return becomesVisible(this.page.getByRole('link', { name: /Solicitar Adicional/ }));
  }

  private get detailHeading(): Locator {
    return this.page.getByRole('heading', { level: 1, name: /^Alias de producto/ });
  }

  private detailLink(lastDigits: string): Locator {
    return this.page.getByRole('link', { name: new RegExp(`${lastDigits}\\s+Mostrar más`) });
  }

  private get listHeading(): Locator {
    return this.page.getByRole('heading', { level: 1, name: 'Tarjetas de crédito' });
  }
}
