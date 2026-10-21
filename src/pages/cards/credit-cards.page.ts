import { expect, type Locator, type Page } from '@playwright/test';
import { SideMenu } from '../navigation/side-menu';
import { becomesVisible } from '../visibility';

/** Descarga desde el primer "Icono de descargar" y devuelve el nombre sugerido del archivo. */
export async function downloadFromFirstIcon(page: Page): Promise<string> {
  const icon = page.locator("xpath=(//div[@aria-label='Icono de descargar'])[1]");
  await expect(icon).toBeVisible();
  const [download] = await Promise.all([page.waitForEvent('download'), icon.click()]);
  return download.suggestedFilename();
}

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

  /** Abre el detalle desde la tarjeta cuyo texto contiene la etiqueta (locator legado getVerMasXnroTarjeta). */
  public async openDetailByLabel(label: string): Promise<void> {
    await this.page
      .locator(`xpath=//span[contains(text(),'${label}')]/ancestor::div[1]/following-sibling::div//a`)
      .first()
      .click();
    await expect(this.detailHeading).toBeVisible();
  }

  /** Vuelve al listado con la flecha "Volver" (legado clickVolverFlechaGenerico). */
  public async goBackToList(): Promise<void> {
    await expect(this.page.locator('div.notification.notification-success.notification-visible')).toHaveCount(0);
    await this.page.locator("xpath=//span[normalize-space(.)='Volver']/ancestor::div[1]").first().click();
    await expect(this.listHeading).toBeVisible();
  }

  /** Textos de la lista que no se ven en la pantalla actual (legado `//*[contains(text(), ...)]`). */
  public async findMissingTexts(texts: readonly string[]): Promise<readonly string[]> {
    const missing: string[] = [];

    for (const text of texts) {
      if (!(await this.isTextShown(text))) {
        missing.push(text);
      }
    }

    return missing;
  }

  public async isTextShown(text: string): Promise<boolean> {
    return becomesVisible(this.page.locator(`xpath=//*[contains(text(), "${text}")]`).first());
  }

  /** Abre "Descargar Resúmenes" desde el detalle y espera el primer período. */
  public async openStatementDownloads(): Promise<void> {
    const download = this.page.locator("xpath=//span[contains(text(), 'Descargar Res')]").first();
    await expect(download).toBeVisible();
    await download.click();
    await expect(this.firstPeriod).toBeVisible();
  }

  /** Despliega el primer período y descarga su resumen; devuelve el nombre del archivo descargado. */
  public async downloadFirstPeriodStatement(): Promise<string> {
    await this.firstPeriod.click();
    return downloadFromFirstIcon(this.page);
  }

  /** Detalle → Opciones → Denunciar robo o extravío → Robo → Continuar. */
  public async startTheftReport(): Promise<void> {
    await this.page.locator("xpath=//span[contains(text(),'Opciones')]/../..").first().click();
    await this.page.locator("xpath=//span[contains(text(), 'Denunciar robo o')]").first().click();
    await this.page.locator('label[for="Robo"]').first().click();
    const continueButton = this.page.locator("xpath=//button[@aria-label='Continuar']").first();
    await continueButton.scrollIntoViewIfNeeded();
    await continueButton.click();
  }

  private get firstPeriod(): Locator {
    return this.page.locator("xpath=//div[@role='button'][1]").first();
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
