import { expect, type Locator, type Page } from '@playwright/test';
import { becomesVisible } from '../visibility';

/** Cambio de sello de seguridad desde "Mi Perfil" (legado PerfilSteps / ModificarPerfilSteps). */
export class SecuritySealPage {
  public constructor(private readonly page: Page) {}

  /** Perfil (`#user`) → "Ir a mi perfil"; cierra la publicidad de préstamo si aparece. */
  public async open(): Promise<void> {
    await this.page.locator('#user').click();
    await this.page.locator("xpath=//a[contains(@href, 'settings')][contains(text(), 'Ir a mi perfil')]").click();
    const exitLoanAd = this.page.locator("xpath=//button[@aria-label='Salir']").first();

    if (await becomesVisible(exitLoanAd, 1_000)) {
      await exitLoanAd.click();
    }

    await expect(this.page.locator("xpath=//*[contains(text(), 'Mi Perfil')]").first()).toBeVisible();
  }

  public async openSealChange(): Promise<void> {
    await this.page.locator("xpath=//a[contains(@href, 'settings/changeSecuritySeal')]//img").first().click();
    await expect(this.seal(1)).toBeVisible();
  }

  /** Elige el sello en esa posición (`(//div[@class='is-current']/../div)[n]`) y espera el segundo factor. */
  public async selectSeal(position: number): Promise<void> {
    await this.seal(position).click();
    await expect(this.secondFactorInput.or(this.otherMethodOption).first()).toBeVisible();
  }

  /** Si se ofrece "Intentar con otro" método, elige SMS (legado codigoSegundoFactor). */
  public async chooseSmsIfOtherMethodOffered(): Promise<void> {
    if (await becomesVisible(this.otherMethodOption, 2_000)) {
      await this.otherMethodOption.scrollIntoViewIfNeeded();
      await this.otherMethodOption.click();
      await this.page.locator("xpath=//span[contains(text(),'SMS')]").first().click();
    }

    await expect(this.secondFactorInput).toBeVisible();
  }

  public async enterSecondFactorCode(code: string): Promise<void> {
    await this.secondFactorInput.fill(code);
    const confirm = this.page.locator("xpath=//button[@aria-label='Confirmar']").first();
    await confirm.scrollIntoViewIfNeeded();
    await confirm.click();
    await expect(this.secondFactorInput).toBeHidden();
  }

  private seal(position: number): Locator {
    return this.page.locator(`xpath=(//div[@class='is-current']/../div)[${position}]`);
  }

  private get otherMethodOption(): Locator {
    return this.page.locator("xpath=//span[contains(text(), 'Intentar con otro')]").first();
  }

  private get secondFactorInput(): Locator {
    return this.page.locator('#secondFactor');
  }
}
