import { expect, type Locator, type Page } from '@playwright/test';

/** Formulario para agendar o adherir un servicio (legado AdherirServicioSteps y TicketSteps). */
export class ServiceAdhesionPage {
  public constructor(private readonly page: Page) {}

  /** Completa rubro, empresa, código y descripción; Continuar y Confirmar hasta el segundo factor. */
  public async submit(category: string, company: string, companyCode: string, description: string): Promise<void> {
    await this.page.locator('#selectorControlBy_serviceCompany').click();
    await this.option(category).click();
    const companySelector = this.page.locator("xpath=//input[@name='entity']/preceding-sibling::div/div[1]");
    await expect(companySelector, 'El desplegable Empresa debe estar habilitado.').toBeEnabled();
    await companySelector.click();
    await this.option(company).click();
    await this.page.locator("xpath=//input[@aria-describedby='paymentCodeDescription']").pressSequentially(companyCode);
    await this.page.locator("xpath=//input[@name='belongsTo']").pressSequentially(description);
    await this.button('Continuar').scrollIntoViewIfNeeded();
    await this.button('Continuar').click();
    await this.button('Confirmar').scrollIntoViewIfNeeded();
    await this.button('Confirmar').click();
    await expect(this.secondFactorInput).toBeVisible();
  }

  public async enterSecondFactorCode(code: string): Promise<void> {
    await this.secondFactorInput.fill(code);
    await this.button('Confirmar').click();
  }

  /** Estado del ticket sin acentos (legado TicketSteps.validarTicket). */
  public async readTicketStatus(): Promise<string> {
    const status = this.page
      .locator("xpath=//span[contains(text(), 'Tu solicitud') or (contains(text(),'Tu transacci'))]")
      .first();
    await expect(status).toBeVisible();
    return (await status.innerText()).trim().normalize('NFD').replace(/[̀-ͯ]/g, '');
  }

  private button(label: string): Locator {
    return this.page.locator(`xpath=//button[@aria-label='${label}']`).first();
  }

  private option(text: string): Locator {
    return this.page.locator(`xpath=//div[contains(text(), '${text}')]`).last();
  }

  private get secondFactorInput(): Locator {
    return this.page.locator('#secondFactor');
  }
}
