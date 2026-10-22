import { expect, type Download, type Page } from '@playwright/test';

const PRODUCT_DOCUMENTS_PATH = '/digitalFilesConsult';

/** Administración de productos: consulta y descarga de contratos (legado AdministracionDeProductosSteps). */
export class ProductDocumentsPage {
  public constructor(private readonly page: Page) {}

  /** Navega a la ruta legada `digitalFilesConsult` dentro de la sesión iniciada. */
  public async open(): Promise<void> {
    await this.page.goto(new URL(PRODUCT_DOCUMENTS_PATH, this.page.url()).toString());
    await expect(this.page.locator("xpath=//*[contains(text(), 'Administraci')]").first()).toBeVisible();
  }

  /** Abre `#contractsSelector` y elige el producto por texto (legado keyDownToText). */
  public async selectProduct(product: string): Promise<void> {
    const selector = this.page.locator('#contractsSelector');
    await expect(selector).toBeVisible();
    await selector.click();
    await this.page.locator(`xpath=//*[contains(text(), '${product}')]`).last().click();
  }

  /** "Consultar" (legado descargarArchivo) y devuelve la descarga producida. */
  public async downloadDocument(): Promise<Download> {
    const [download] = await Promise.all([
      this.page.waitForEvent('download'),
      this.page.locator("xpath=//button[@aria-label='Consultar']").first().click(),
    ]);
    return download;
  }
}
