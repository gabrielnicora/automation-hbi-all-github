import { expect, type Locator, type Page } from '@playwright/test';
import { becomesVisible } from '../visibility';

export interface EcheqRow {
  readonly emissionDate: string;
  readonly issuerCuit: string;
  readonly state: string;
}

export interface EcheqDateRange {
  readonly from: string;
  readonly to: string;
}

export class EcheqPage {
  public constructor(private readonly page: Page) {}

  public async openFromAccounts(): Promise<void> {
    await expect(this.accountsTitle).toBeVisible();
    if (await this.cancelButton.isVisible()) {
      await this.cancelButton.click();
    }
    await this.moreOptionsButton.click();
    await this.echeqLink.click();
    await expect(this.echeqTitle).toBeVisible();
  }

  public async filterByIssuerStateAndLastSixMonths(cuit: string): Promise<void> {
    await this.filterButton.click();
    await expect(this.issuerInput).toBeVisible();
    await this.issuerInput.fill(cuit);
    await this.stateDropdown.click();
    await this.page.getByText('Emitido pendiente', { exact: true }).last().click();

    await this.creationDateFrom.click();
    for (let month = 0; month < 6; month += 1) {
      await this.previousMonthButton.click();
    }
    await this.calendarDay('1').click();

    await this.creationDateTo.click();
    // The legacy flow advances six months from its selected start month to today.
    for (let month = 0; month < 6; month += 1) {
      await this.nextMonthButton.click();
    }
    await this.todayButton.click();
    await this.applyButton.click();
    await this.filterButton.click();
  }

  /** Abre "Filtrar" (legado clickbtnFiltrar). */
  public async openFilters(): Promise<void> {
    await this.filterButton.click();
    await expect(this.applyButton).toBeVisible();
  }

  public async isTextShown(text: string): Promise<boolean> {
    return becomesVisible(this.page.locator(`xpath=//*[contains(text(), "${text}")]`).first());
  }

  /** Indica si un `span` o `div` con el texto se ve (etiquetas de filtros del legado). */
  public async isLabelShown(text: string, element: 'div' | 'span'): Promise<boolean> {
    return becomesVisible(this.page.locator(`xpath=//${element}[contains(text(), '${text}')]`).first());
  }

  public async applyFilters(): Promise<void> {
    await this.page.locator("xpath=//span[contains(text(),'Aplicar')]").first().click();
  }

  /** Busca por número de eCheq y estado "Emitido pendiente" (legado TC124865). */
  public async searchByNumberAndPendingState(echeqNumber: string): Promise<void> {
    await this.page.locator("xpath=//input[contains(@id,'echeq')]").first().pressSequentially(echeqNumber);
    await this.page.locator("xpath=//div[contains(text(), 'Todos')]").first().click();
    await this.page.locator("xpath=//div[contains(text(), 'Emitido pendiente')]").last().click();
    await this.page.locator("xpath=//button[@aria-label='Buscar']").first().click();
  }

  public async openFirstDetail(): Promise<void> {
    const detail = this.page.locator("xpath=(//*[@aria-label='tabla']//*[contains(@aria-label,'flecha')])[1]");
    await expect(detail).toBeVisible();
    await detail.scrollIntoViewIfNeeded();
    await detail.click();
  }

  /** Aceptar el eCheq desde el detalle (botón genérico "Aceptar"). */
  public async startAcceptance(): Promise<void> {
    await this.page.locator("xpath=//button[@aria-label='Aceptar']").first().click();
  }

  /** Continuar la aceptación y esperar el segundo factor. */
  public async continueToSecondFactor(): Promise<void> {
    const continueButton = this.page.locator("xpath=//button[@aria-label='Continuar']").first();
    await continueButton.scrollIntoViewIfNeeded();
    await continueButton.click();
    await expect(this.page.locator('#secondFactor')).toBeVisible();
  }

  public async enterSecondFactorCode(code: string): Promise<void> {
    await this.page.locator('#secondFactor').fill(code);
    const confirm = this.page.locator("xpath=//button[@aria-label='Confirmar']").first();
    await confirm.scrollIntoViewIfNeeded();
    await confirm.click();
  }

  public async readVisibleRows(): Promise<readonly EcheqRow[]> {
    const rows = this.page.locator('div[aria-label="fila"]');
    const rowCount = Math.min(await rows.count(), 10);
    const visibleRows: EcheqRow[] = [];

    for (let index = 0; index < rowCount; index += 1) {
      const row = rows.nth(index);
      const emissionDateText = (await row.locator('div').nth(6).innerText()).trim();
      const issuerCuit = (await row.locator('div').nth(8).innerText()).trim();
      const rowState = (await row.locator('div').nth(13).innerText()).trim();
      visibleRows.push({ emissionDate: emissionDateText, issuerCuit, state: rowState });
    }

    return visibleRows;
  }

  public async readEmissionDateRange(): Promise<EcheqDateRange> {
    return {
      from: (await this.emissionFromValue.innerText()).trim(),
      to: (await this.emissionToValue.innerText()).trim(),
    };
  }

  private get accountsTitle(): Locator {
    return this.page.getByText('Cuentas', { exact: true }).first();
  }

  private get applyButton(): Locator {
    return this.page.getByText('Aplicar', { exact: true }).last();
  }

  private get cancelButton(): Locator {
    return this.page.getByRole('button', { name: 'Cancelar', exact: true });
  }

  private get creationDateFrom(): Locator {
    return this.page.locator("//div[contains(@aria-label,'esde')]/div").first();
  }

  private get creationDateTo(): Locator {
    return this.page.locator("//div[contains(@aria-label,'hasta')]/div").first();
  }

  private get echeqLink(): Locator {
    return this.page.locator('a[href="/accounts/echeqs"]');
  }

  private get echeqTitle(): Locator {
    return this.page.getByText('ECheq', { exact: true }).first();
  }

  private get emissionFromValue(): Locator {
    return this.page.locator("//div[contains(@aria-label,'n desde')]//div").last();
  }

  private get emissionToValue(): Locator {
    return this.page.locator("//div[contains(@aria-label,'n hasta')]/div").first();
  }

  private get filterButton(): Locator {
    return this.page.getByRole('button', { name: 'Filtrar', exact: true });
  }

  private get issuerInput(): Locator {
    return this.page.locator('input[name="cuitOriginator"]');
  }

  private get moreOptionsButton(): Locator {
    return this.page.locator("xpath=//span[contains(text(),'Opciones')]/../..");
  }

  private get nextMonthButton(): Locator {
    return this.page.getByRole('button', { name: 'Next Month' });
  }

  private get previousMonthButton(): Locator {
    return this.page.getByRole('button', { name: 'Previous Month' });
  }

  private get stateDropdown(): Locator {
    return this.page.locator("//input[@name='echeqStatus']/preceding-sibling::div");
  }

  private get todayButton(): Locator {
    return this.page.locator("//div[contains(@class,'day--today')]");
  }

  private calendarDay(day: string): Locator {
    return this.page
      .locator(`//div[text()='${day}' and contains(@class,'day') and not(contains(@class,'outside'))]`)
      .last();
  }
}
