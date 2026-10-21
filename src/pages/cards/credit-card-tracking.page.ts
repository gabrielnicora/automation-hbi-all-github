import { expect, type Page } from '@playwright/test';
import { becomesVisible } from '../visibility';
import { downloadFromFirstIcon } from './credit-cards.page';

/** Seguimiento de tarjetas de crédito (legado TarjetasDeCreditoSteps / TarjetasDeCreditoPage). */
export class CreditCardTrackingPage {
  public constructor(private readonly page: Page) {}

  /** Desde Tarjetas de crédito abre "Seguimiento de tarjetas" (`/creditCard/creditCardTracking`). */
  public async open(): Promise<void> {
    await this.page.locator("xpath=//a[@href='/creditCard/creditCardTracking']").first().click();
    await expect(this.page).toHaveURL(/\/creditCard\/creditCardTracking/);
  }

  public async isTextShown(text: string): Promise<boolean> {
    return becomesVisible(this.page.locator(`xpath=//*[contains(text(), "${text}")]`).first());
  }

  /** Textos de la lista que no se ven en pantalla. */
  public async findMissingTexts(texts: readonly string[]): Promise<readonly string[]> {
    const missing: string[] = [];

    for (const text of texts) {
      if (!(await this.isTextShown(text))) {
        missing.push(text);
      }
    }

    return missing;
  }

  /** Ingresa al detalle del primer seguimiento (icono de flecha derecha). */
  public async openFirstDetail(): Promise<void> {
    await this.page.locator("xpath=//div[@aria-label='Icono de flecha derecha ']").first().click();
    await expect(this.page.locator("xpath=(//div[@aria-label='Icono de descargar'])[1]")).toBeVisible();
  }

  /** Descarga el detalle del seguimiento y devuelve el nombre del archivo. */
  public async downloadDetail(): Promise<string> {
    return downloadFromFirstIcon(this.page);
  }

  /**
   * Presiona imprimir. Si se abre otra pestaña, la cierra (legado ManageTab, sin assertion).
   * Devuelve si se abrió una pestaña.
   */
  public async printDetail(): Promise<boolean> {
    const pagesBefore = this.page.context().pages().length;
    const printIcon = this.page.locator("xpath=//div[@aria-label='Icono de imprimir']").first();
    await expect(printIcon).toBeVisible();
    await printIcon.click();
    const extraPages = this.page.context().pages().slice(pagesBefore);
    await Promise.all(extraPages.map((extraPage) => extraPage.close()));
    return extraPages.length > 0;
  }
}
