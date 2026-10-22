import { expect, type Page } from '@playwright/test';

/**
 * Herramienta de diagnóstico "utilLogger" de QA (legado DobleFactorSteps.clickBtnUtilLogger y
 * UtilLoggerSteps). Lee los requests registrados para un servicio.
 */
export class UtilLoggerPage {
  public constructor(private readonly page: Page) {}

  /**
   * Abre utilLogger en la pestaña nueva, actualiza y devuelve el cuerpo de cada request del servicio.
   * Cierra la pestaña al terminar (legado switchTab + backMainTap).
   */
  public async readServiceRequests(service: string): Promise<readonly string[]> {
    const [loggerPage] = await Promise.all([
      this.page.context().waitForEvent('page'),
      this.page.locator("xpath=//span[contains(text(), 'utilLogger')]").first().click(),
    ]);

    try {
      await loggerPage.waitForLoadState('domcontentloaded');
      await loggerPage.locator("xpath=//button[contains(text(),'Actualizar')]").first().click();
      const requestButtons = loggerPage.locator(
        `xpath=//h5[normalize-space(.)='${service}']/ancestor::div[2]/following-sibling::div//button[normalize-space(.)='Request']`,
      );
      await expect(requestButtons.first()).toBeVisible();
      const bodies: string[] = [];

      for (let index = 0; index < (await requestButtons.count()); index += 1) {
        await requestButtons.nth(index).scrollIntoViewIfNeeded();
        await requestButtons.nth(index).click();
        const body = loggerPage
          .locator("xpath=//button[contains(text(), 'Meta')]/ancestor::div[2]/following-sibling::div[1]")
          .first();
        await expect(body).toBeVisible();
        bodies.push((await body.innerText()).trim());
        const close = loggerPage.locator(
          "xpath=//button[.//*[local-name()='svg']/*[local-name()='path' and contains(@d, 'M4.293 4.293')]]",
        );

        if (await close.first().isVisible()) {
          await close.first().click();
        }
      }

      return bodies;
    } finally {
      await loggerPage.close();
    }
  }
}
