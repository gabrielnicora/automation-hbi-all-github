import { expect, type Locator, type Page } from '@playwright/test';
import { becomesVisible } from '../visibility';

export interface HistoryDateFilters {
  readonly from: string;
  readonly to: string;
}

/** "Ver mis transferencias" (legado TransferenciasInmediatasSteps: filtros, grilla y paginador). */
export class TransferHistoryPage {
  public constructor(private readonly page: Page) {}

  public async openFilters(): Promise<void> {
    await this.filterButton.click();
    await expect(this.fromFilter).toBeVisible();
  }

  public async readDateFilters(): Promise<HistoryDateFilters> {
    await expect(this.fromFilter).toBeVisible();
    return { from: (await this.fromFilter.innerText()).trim(), to: (await this.toFilter.innerText()).trim() };
  }

  /** Cambia ambas fechas eligiendo el primer día visible del calendario (legado getdatePicker). */
  public async changeDateFilters(): Promise<void> {
    for (const filter of [this.fromFilter, this.toFilter]) {
      await filter.click();
      const firstDay = this.page.locator("xpath=//div[@class='react-datepicker__month']/div").first();
      await expect(firstDay).toBeVisible();
      await firstDay.click();
    }
  }

  public async clearFilters(): Promise<void> {
    await this.page.locator("xpath=//span[contains(text(),'Borrar filtros')]").first().click();
  }

  /** Cantidad de filas visibles de la grilla (`div[aria-label='fila']`). */
  public async countRows(): Promise<number> {
    return this.page.locator("xpath=//div[@aria-label='fila']").count();
  }

  public async isPaginatorShown(): Promise<boolean> {
    const paginator = this.page.locator("xpath=//p[contains(text(), 'Pag ' )]").first();
    await paginator.scrollIntoViewIfNeeded().catch(() => undefined);
    return becomesVisible(paginator);
  }

  /**
   * Recorre las páginas hasta encontrar un movimiento del mes indicado (`/MM/`) y devuelve su fecha,
   * o `undefined` si no hay más páginas (legado buscarMovimientosConAntiguedadDeDosMeses).
   */
  public async findMovementDateOfMonth(month: string): Promise<string | undefined> {
    const movement = this.page.locator(`xpath=(//div[@type='grid']//p[contains(text(), '/${month}/')])[1]`);
    const nextPage = this.page.locator("xpath=//div[@aria-label='Ir a la siguiente página']").first();

    while (!(await becomesVisible(movement, 2_000))) {
      if (!(await nextPage.isVisible())) {
        return undefined;
      }

      await nextPage.scrollIntoViewIfNeeded();
      await nextPage.click();
    }

    await movement.scrollIntoViewIfNeeded();
    return (await movement.innerText()).trim();
  }

  /** Vuelve con la flecha "Volver" a Transferencias. */
  public async goBack(): Promise<void> {
    await this.page.locator("xpath=//span[normalize-space(.)='Volver']/ancestor::div[1]").first().click();
    await expect(this.filterButton).toBeHidden();
  }

  private get filterButton(): Locator {
    return this.page.locator("xpath=//button[@aria-label='Filtrar']").first();
  }

  private get fromFilter(): Locator {
    return this.page.locator("xpath=//div[contains(@aria-label, 'Desde')]/div").first();
  }

  private get toFilter(): Locator {
    return this.page.locator("xpath=//div[contains(@aria-label, 'Hasta')]/div").first();
  }
}
