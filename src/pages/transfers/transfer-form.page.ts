import { expect, type Locator, type Page } from '@playwright/test';
import { SideMenu } from '../navigation/side-menu';
import { becomesVisible } from '../visibility';

const SHORT_STATE_TIMEOUT_MS = 5_000;

/** Tipos de transferencia del legado (TransferenciasInmediatasSteps.seleccionarTipoTransferencia). */
export const TRANSFER_TYPES = {
  ownAccount: 'A una cuenta propia',
  scheduledContact: 'A un contacto agendado',
  thirdParty: 'A un CBU, CVU o Alias',
} as const;
export type TransferType = keyof typeof TRANSFER_TYPES;

function stripAccents(value: string): string {
  return value.normalize('NFD').replace(/[̀-ͯ]/g, '');
}

/**
 * Formulario de transferencias inmediatas/programadas, ticket y segundo factor por UI (legado
 * TransferenciasInmediatasSteps, TicketSteps y DobleFactorSteps). Reutiliza los locators validados del
 * testigo TC18088BERSA para importe, descripción, búsqueda y botones; el resto reproduce el legado.
 */
export class TransferFormPage {
  private readonly menu: SideMenu;

  public constructor(private readonly page: Page) {
    this.menu = new SideMenu(page);
  }

  /** Menú lateral → Transferencias; descarta el aviso "Ahora no" si aparece (legado clickBtnAhoraNo). */
  public async open(): Promise<void> {
    await this.menu.openTransfers();
    const notNow = this.page.locator("xpath=//span[contains(text(), 'Ahora no')]").first();

    if (await becomesVisible(notNow, SHORT_STATE_TIMEOUT_MS)) {
      await notNow.click();
    }

    await expect(this.typeCard('thirdParty')).toBeVisible();
  }

  public async isTextShown(text: string): Promise<boolean> {
    return becomesVisible(this.page.locator(`xpath=//*[contains(text(), "${text}")]`).first());
  }

  public async selectTransferType(type: TransferType): Promise<void> {
    const card = this.typeCard(type);
    await card.scrollIntoViewIfNeeded();
    await expect(card).toBeVisible();
    await card.click();
  }

  /** Busca el CBU, CVU o alias y confirma "Son correctos" (recorrido del testigo TC18088BERSA). */
  public async searchRecipient(cbuOrAlias: string): Promise<void> {
    await this.page.getByPlaceholder('Ingresá CBU, CVU o Alias').fill(cbuOrAlias);
    await this.page.getByRole('button', { name: 'Buscar', exact: true }).click();
    await expect(this.page.getByRole('heading', { name: 'Revisá los datos del contacto', exact: true })).toBeVisible();
    await this.page.getByRole('button', { name: 'Son correctos', exact: true }).click();
    await expect(this.amountInput).toBeVisible();
  }

  /** Texto del saldo mostrado para la cuenta origen (legado getCuentaOrigenDesplegableSaldo1). */
  public async readOriginBalanceText(): Promise<string> {
    const balance = this.page
      .locator("xpath=//p[contains(text(),'Vas a transferir desde')]/../div//p[contains(text(),'$')][2]")
      .first();
    await expect(balance).toBeVisible();
    return (await balance.innerText()).trim();
  }

  /** Abre "Vas a transferir desde" y elige la primera cuenta del menú (legado seleccionarCtaOrigen). */
  public async selectFirstOriginAccount(): Promise<void> {
    await this.originDropdown.click();
    await this.page.locator("xpath=//div[@class = 'react-select__menu css-26l3qy-menu']//div[1]//p[1]").first().click();
  }

  /**
   * Elige la cuenta propia destino: por "Agenda completa" si está disponible o, si no, desde el
   * carrusel (legado seleccionarCuentaDestinoCuentasPropias).
   */
  public async selectOwnDestination(account: string): Promise<void> {
    const fullAgenda = this.page.locator("xpath=//span[contains(text(),'Agenda completa')]").first();

    if (await becomesVisible(fullAgenda, SHORT_STATE_TIMEOUT_MS)) {
      await fullAgenda.click();
      const search = this.page.locator('input#belongsTo').first();
      await search.click();
      await search.pressSequentially(account);
      await this.page.locator("xpath=//*[contains(text(),'Transferir')]//../parent::button").first().click();
    } else {
      await this.page.locator("xpath=(//button[@aria-label='siguiente'])[1]").click();
      await this.page.locator(`xpath=//*[contains(text(),'${account}')]`).first().click();
    }

    await expect(this.amountInput).toBeVisible();
  }

  public async fillAmount(amount: string): Promise<void> {
    await this.amountInput.fill(amount);
  }

  /** Abre el concepto y elige la opción que contiene el texto. */
  public async selectConcept(concept: string): Promise<void> {
    const conceptSelector = this.page.locator('#concept').first();
    await conceptSelector.scrollIntoViewIfNeeded();
    await conceptSelector.click();
    await this.page.locator(`xpath=//div[contains(text(), '${concept}')]`).last().click();
  }

  public async fillDescription(description: string): Promise<void> {
    await this.page.getByRole('textbox', { name: 'Descripción', exact: true }).fill(description);
  }

  public async transferNow(): Promise<void> {
    const transferNow = this.page.getByRole('button', { name: 'Transferir ahora', exact: true });
    await expect(transferNow).toBeEnabled();
    await transferNow.click();
    await expect(this.confirmButton).toBeVisible();
  }

  /** "Programar para otro día" → "En una fecha específica" → Continuar. */
  public async scheduleForSpecificDate(): Promise<void> {
    await this.page.locator("xpath=//span[contains(text(), 'Programar para otro día')]/../..").first().click();
    await this.page.locator("xpath=//p[contains(text(), 'En una fecha específica')]/..").first().click();
    const continueButton = this.page.locator("xpath=//button[@aria-label='Continuar']").first();
    await continueButton.scrollIntoViewIfNeeded();
    await continueButton.click();
    await expect(this.confirmButton).toBeVisible();
  }

  public async confirm(): Promise<void> {
    await this.confirmButton.scrollIntoViewIfNeeded();
    await this.confirmButton.click();
  }

  /** Confirma y espera el campo del segundo factor (el código se resuelve por la política central). */
  public async confirmAndWaitForSecondFactor(): Promise<void> {
    await this.confirm();
    await this.waitForSecondFactor();
  }

  public async waitForSecondFactor(): Promise<void> {
    const otherMethod = this.page.locator("xpath=//span[contains(text(), 'Intentar con otro')]").first();

    if (await becomesVisible(otherMethod, SHORT_STATE_TIMEOUT_MS)) {
      await otherMethod.click();
      await this.page.locator("xpath=//span[contains(text(),'SMS')]").first().click();
    }

    await expect(this.secondFactorInput).toBeVisible();
  }

  /** Ingresa el código y confirma (legado codigoSegundoFactor, sin su reintento interno). */
  public async enterSecondFactorCode(code: string): Promise<void> {
    await this.secondFactorInput.fill(code);
    await this.confirm();
  }

  /** Estado del ticket sin acentos (legado TicketSteps.validarTicket). */
  public async readTicketStatus(): Promise<string> {
    const status = this.page
      .locator("xpath=//span[contains(text(), 'Tu solicitud') or (contains(text(),'Tu transacci'))]")
      .first();
    await expect(status).toBeVisible();
    return stripAccents((await status.innerText()).trim());
  }

  /** Valor del campo del ticket sin acentos (legado validarCampoValorTicket). */
  public async readTicketField(label: string): Promise<string> {
    const value = this.page.locator(`xpath=(//span[text()='${label}']/../following::p)[1]`).first();
    await value.scrollIntoViewIfNeeded();
    return stripAccents((await value.innerText()).trim());
  }

  /** Descarga el ticket y devuelve el nombre del archivo. */
  public async downloadTicket(): Promise<string> {
    const icon = this.page.locator("xpath=//div[@aria-label='Icono de descargar']").first();
    await expect(icon).toBeVisible();
    const [download] = await Promise.all([this.page.waitForEvent('download'), icon.click()]);
    return download.suggestedFilename();
  }

  /** "Volver" cuando no queda notificación de éxito visible (legado clickVolverGenerico). */
  public async goBack(): Promise<void> {
    await expect(this.page.locator('div.notification.notification-success.notification-visible')).toHaveCount(0);
    const back = this.page.locator("xpath=//*[contains(text(), 'Volver')]").first();
    await back.scrollIntoViewIfNeeded();
    await back.click();
    await expect(this.typeCard('thirdParty')).toBeVisible();
  }

  /** Flecha "Volver" (legado clickVolverFlechaGenerico); espera las opciones de Transferencias. */
  public async goBackWithArrow(): Promise<void> {
    await expect(this.page.locator('div.notification.notification-success.notification-visible')).toHaveCount(0);
    await this.page.locator("xpath=//span[normalize-space(.)='Volver']/ancestor::div[1]").first().click();
    await expect(this.typeCard('thirdParty')).toBeVisible();
  }

  /** Abre "Ver mis transferencias". */
  public async openHistory(): Promise<void> {
    const history = this.page.locator("xpath=//span[contains(text(),'Ver mis transferencias')]/../..").first();
    await expect(history).toBeVisible();
    await history.click();
    await expect(this.page.locator("xpath=//button[@aria-label='Filtrar']").first()).toBeVisible();
  }

  /** Abre "Agenda completa" luego de elegir "A un contacto agendado". */
  public async openFullAgenda(): Promise<void> {
    await this.page.locator("xpath=//span[contains(text(),'Agenda completa')]").first().click();
    await expect(this.page.locator('input#belongsTo').first()).toBeVisible();
  }

  /** Opciones → Consultar CREDIN. */
  public async openCredins(): Promise<void> {
    await this.page.locator("xpath=//span[contains(text(),'Opciones')]/../..").first().click();
    await this.page.locator("xpath=(//*[@id='mainLayout']//a[@href='/diaryTransfer/credins?fromMenu=true'])").click();
    await expect(this.page).toHaveURL(/\/diaryTransfer\/credins/);
  }

  private get amountInput(): Locator {
    return this.page.locator('input[placeholder="0,00"]');
  }

  private get confirmButton(): Locator {
    return this.page.getByRole('button', { name: 'Confirmar', exact: true });
  }

  private get originDropdown(): Locator {
    return this.page.locator("xpath=//p[contains(text(),'Vas a transferir desde')]/../div").first();
  }

  private get secondFactorInput(): Locator {
    return this.page.locator('#secondFactor');
  }

  private typeCard(type: TransferType): Locator {
    return this.page.locator(`xpath=//h2[contains(text(), '${TRANSFER_TYPES[type]}')]/../..`).first();
  }
}
