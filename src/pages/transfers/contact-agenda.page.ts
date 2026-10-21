import { expect, type Locator, type Page } from '@playwright/test';
import { becomesVisible } from '../visibility';

const SECOND_FACTOR_INPUT = '#secondFactor';

/** Agenda completa de contactos (legado AgendaNuevoDestinatarioSteps / AgendaNuevoDestinatarioPage). */
export class ContactAgendaPage {
  public constructor(private readonly page: Page) {}

  /** "Agregar contacto nuevo" → CBU → Buscar → Son correctos; espera el segundo factor. */
  public async addContact(cbu: string): Promise<void> {
    const addContact = this.page.locator("xpath=//span[contains(text(),'Agregar contacto nuevo')]").first();
    await addContact.scrollIntoViewIfNeeded();
    await addContact.click();
    const search = this.page.locator('#cbuSearch');
    await expect(search).toBeEnabled();
    await search.click();
    await search.pressSequentially(cbu);
    await this.page.locator("xpath=//button[@aria-label='Buscar']").first().click();
    const correct = this.page.locator("xpath=//button[@aria-label='Son correctos']").first();
    await correct.scrollIntoViewIfNeeded();
    await correct.click();
    await expect(this.page.locator(SECOND_FACTOR_INPUT)).toBeVisible();
  }

  /** Ingresa el código y confirma el alta del contacto. */
  public async enterSecondFactorCode(code: string): Promise<void> {
    await this.page.locator(SECOND_FACTOR_INPUT).fill(code);
    const confirm = this.page.locator("xpath=//button[@aria-label='Confirmar']").first();
    await confirm.scrollIntoViewIfNeeded();
    await confirm.click();
  }

  /** "Transferir a este contacto" luego del alta. */
  public async transferToThisContact(): Promise<void> {
    const transfer = this.page.locator("xpath=//span[contains(text(), 'Transferir a este')]").first();
    await transfer.scrollIntoViewIfNeeded();
    await transfer.click();
    await expect(this.page.locator('input[placeholder="0,00"]')).toBeVisible();
  }

  /**
   * Busca el contacto y, si el listado no informa el texto de vacío, lo elimina desde el menú de
   * acciones (legado eliminaContactoEnAgenda). Devuelve si eliminó un contacto.
   */
  public async removeContactIfListed(cbu: string, emptyListText: string): Promise<boolean> {
    const search = this.page.locator('input#belongsTo').first();
    await expect(search).toBeVisible();
    await search.pressSequentially(cbu);

    if (await becomesVisible(this.page.locator(`xpath=//span[contains(text(), '${emptyListText}')]`).first())) {
      return false;
    }

    await this.page.locator("xpath=//p[contains(text(),'Pag 1')]").first().scrollIntoViewIfNeeded();
    await this.page.locator('#Grupo_11105').first().click();
    const remove = this.eliminar;
    await remove.scrollIntoViewIfNeeded();
    await remove.click();
    await this.page.locator("xpath=//span[contains(text(), 'Si, eliminar')]").first().click();
    return true;
  }

  private get eliminar(): Locator {
    return this.page.locator("xpath=//span[contains(text(), 'Eliminar')]").first();
  }
}
