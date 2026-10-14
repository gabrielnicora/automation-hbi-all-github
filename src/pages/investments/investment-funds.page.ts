import { expect, type Locator, type Page } from '@playwright/test';
import { FUND_RISKS, type FundRisk } from './fund-risk';

const FILTER_RESULT_TIMEOUT_MS = 15_000;
const PAGE_STATE_TIMEOUT_MS = 30_000;
const FUNDS_PAGE_PATH = '/investment/fund';
const FUNDS_PAGE_TITLE = 'Información de fondos';
const INVESTMENTS_MENU_NAME = 'Inversiones';
const FUNDS_LINK_NAME = 'Invertí en Fondos';
const RISK_GROUP_LABEL = 'RIESGO:';
const RISK_COLUMN_LABEL = 'RIESGO';
const LOAD_ERROR_MESSAGE = 'Ocurrió un error al recuperar los fondos. Por favor, intentá nuevamente más tarde.';

const RISK_LABELS: Readonly<Record<FundRisk, string>> = {
  conservative: 'CONSERVADOR',
  moderate: 'MODERADO',
  dynamic: 'DINÁMICO',
};

type InvestmentFundsPageState = 'loading' | 'ready' | 'load-error';

export class InvestmentFundsUnavailableError extends Error {
  public constructor(displayedMessage: string) {
    super(`Inestabilidad de la aplicación al recuperar fondos. Mensaje mostrado: "${displayedMessage}"`);
    this.name = 'InvestmentFundsUnavailableError';
  }
}

export class InvestmentFundsTestDataUnavailableError extends Error {
  public constructor() {
    super('Datos de prueba no disponibles: la página no mostró fondos para los filtros seleccionados.');
    this.name = 'InvestmentFundsTestDataUnavailableError';
  }
}

export class InvestmentFundsPage {
  private readonly pageUrl: string;

  public constructor(
    private readonly page: Page,
    baseUrl: string,
  ) {
    this.pageUrl = new URL(FUNDS_PAGE_PATH, baseUrl).toString();
  }

  public async open(): Promise<void> {
    await this.page.getByText(INVESTMENTS_MENU_NAME, { exact: true }).first().click();
    await this.page.getByRole('link', { name: FUNDS_LINK_NAME, exact: true }).first().click();
    await expect(this.page).toHaveURL(this.pageUrl);
    await this.waitUntilReady();
  }

  public async selectOnlyRisk(selectedRisk: FundRisk): Promise<void> {
    for (const risk of FUND_RISKS) {
      await this.setRiskSelection(risk, risk === selectedRisk);
    }

    for (const risk of FUND_RISKS) {
      const shouldBeSelected = risk === selectedRisk;
      const isSelected = await this.isRiskSelected(risk);

      if (isSelected !== shouldBeSelected) {
        throw new Error(
          `El filtro ${RISK_LABELS[risk]} no quedó ${shouldBeSelected ? 'seleccionado' : 'deseleccionado'}.`,
        );
      }
    }
  }

  public async getVisibleRisks(): Promise<readonly FundRisk[]> {
    await this.waitForVisibleFundRows();
    const visibleFundRows = await this.visibleFundRows.all();
    const visibleLabels = await Promise.all(visibleFundRows.map((row) => this.readRiskLabel(row)));

    return visibleLabels.map((label) => this.toFundRisk(label));
  }

  private get loadError(): Locator {
    return this.page.getByText(LOAD_ERROR_MESSAGE, { exact: true }).first();
  }

  private get visibleFundRows(): Locator {
    return this.page.getByRole('row', { name: 'fila', exact: true });
  }

  private async waitUntilReady(): Promise<void> {
    await expect
      .poll(() => this.readPageState(), {
        message: 'La página de fondos no mostró los filtros ni un error conocido.',
        timeout: PAGE_STATE_TIMEOUT_MS,
      })
      .not.toBe('loading');

    await this.throwIfLoadErrorIsVisible();
    await expect(this.page.getByText(FUNDS_PAGE_TITLE, { exact: true }).first()).toBeVisible();
  }

  private async readPageState(): Promise<InvestmentFundsPageState> {
    if (await this.loadError.isVisible()) {
      return 'load-error';
    }

    if (await this.riskFilter(FUND_RISKS[0]).isVisible()) {
      return 'ready';
    }

    return 'loading';
  }

  private async setRiskSelection(risk: FundRisk, shouldBeSelected: boolean): Promise<void> {
    if ((await this.isRiskSelected(risk)) === shouldBeSelected) {
      return;
    }

    await this.withLoadErrorContext(async () => {
      await this.riskFilter(risk).click();
      await expect
        .poll(() => this.isRiskSelected(risk), {
          message: `El filtro ${RISK_LABELS[risk]} no cambió de estado.`,
        })
        .toBe(shouldBeSelected);
    });
  }

  private async isRiskSelected(risk: FundRisk): Promise<boolean> {
    return (await this.riskFilter(risk).locator('div[fill]').count()) > 0;
  }

  private riskFilter(risk: FundRisk): Locator {
    const riskLabel = RISK_LABELS[risk];

    return this.page
      .locator(
        `xpath=//*[normalize-space()="${RISK_GROUP_LABEL}"]/following-sibling::div[p[normalize-space()="${riskLabel}"]]`,
      )
      .first();
  }

  private async waitForVisibleFundRows(): Promise<void> {
    try {
      await expect
        .poll(
          async () => {
            if (await this.loadError.isVisible()) {
              return 'load-error';
            }

            return (await this.visibleFundRows.count()) > 0 ? 'rows-visible' : 'waiting';
          },
          {
            message: 'El listado de fondos no mostró resultados.',
            timeout: FILTER_RESULT_TIMEOUT_MS,
          },
        )
        .not.toBe('waiting');
    } catch {
      await this.throwIfLoadErrorIsVisible();
      throw new InvestmentFundsTestDataUnavailableError();
    }

    await this.throwIfLoadErrorIsVisible();
  }

  private async throwIfLoadErrorIsVisible(): Promise<void> {
    if (await this.loadError.isVisible()) {
      throw new InvestmentFundsUnavailableError((await this.loadError.innerText()).trim());
    }
  }

  private async readRiskLabel(row: Locator): Promise<string> {
    const table = row.locator('xpath=ancestor::*[self::table or @role="table"][1]');
    const headerLabels = await table.getByRole('columnheader').allInnerTexts();
    const riskColumnIndex = headerLabels.findIndex(
      (headerLabel) => this.normalizeText(headerLabel) === RISK_COLUMN_LABEL,
    );

    if (riskColumnIndex < 0) {
      throw new Error('Una tabla de fondos no contiene la columna Riesgo.');
    }

    const cells = row.getByRole('cell');

    if ((await cells.count()) <= riskColumnIndex) {
      throw new Error('Una fila de fondos no contiene una celda para la columna Riesgo.');
    }

    return cells.nth(riskColumnIndex).innerText();
  }

  private async withLoadErrorContext<T>(action: () => Promise<T>): Promise<T> {
    try {
      return await action();
    } catch (error: unknown) {
      await this.throwIfLoadErrorIsVisible();
      throw error;
    }
  }

  private toFundRisk(label: string): FundRisk {
    const normalizedLabel = this.normalizeText(label);
    const matchingRisk = FUND_RISKS.find((risk) => this.normalizeText(RISK_LABELS[risk]) === normalizedLabel);

    if (!matchingRisk) {
      throw new Error(`El listado mostró un riesgo no reconocido: "${label.trim()}".`);
    }

    return matchingRisk;
  }

  private normalizeText(value: string): string {
    return value
      .normalize('NFD')
      .replace(/[\u0300-\u036f]/g, '')
      .trim()
      .toUpperCase();
  }
}
