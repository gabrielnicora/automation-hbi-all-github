import { expect, type FrameLocator, type Locator, type Page } from '@playwright/test';
import { becomesVisible } from '../visibility';

export interface InsuranceDetail {
  readonly title: string;
  readonly description: string;
}

export class InsurancePage {
  public constructor(private readonly page: Page) {}

  public async open(): Promise<void> {
    await this.page.getByRole('button', { name: 'Seguros', exact: true }).click();
    await expect(this.insuranceFrame).toBeVisible();
    await expect(this.insuranceApp.getByRole('heading', { name: 'Seguros', exact: true })).toBeVisible();
  }

  /** Indica si el listado de seguros muestra una tarjeta cuyo texto contiene el nombre indicado. */
  public async isInsuranceCardAvailable(name: string): Promise<boolean> {
    return becomesVisible(this.insuranceApp.getByText(name).first());
  }

  public async openRobberyInsuranceDetail(): Promise<void> {
    await this.page.getByRole('button', { name: 'Seguros', exact: true }).click();
    await expect(this.insuranceFrame).toBeVisible();
    await expect(this.insuranceApp.getByRole('heading', { name: 'Seguros', exact: true })).toBeVisible();

    await expect(this.robberyInsurance).toBeVisible();
    await this.robberyInsurance.click();

    await expect(this.detailTitle).toBeVisible();
    await expect(this.detailDescription).toBeVisible();
  }

  /** Continuar la solicitud del seguro (botón genérico "Continuar" dentro de la aplicación de seguros). */
  public async continueRequest(): Promise<void> {
    const continueButton = this.insuranceApp.locator("xpath=//button[@aria-label='Continuar']").first();
    await continueButton.scrollIntoViewIfNeeded();
    await continueButton.click();
  }

  /** Acepta términos y condiciones (legado clickCheckTerminosCondicionesGenerico). */
  public async acceptTerms(): Promise<void> {
    const terms = this.insuranceApp.locator("xpath=//*[@name='agreement']/following-sibling::span").first();
    await expect(terms).toBeVisible();
    await terms.scrollIntoViewIfNeeded();
    await terms.click();
  }

  /** Indica si se ve el botón con ese aria-label ("Confirmar", "Cancelar"). */
  public async isButtonShown(label: string): Promise<boolean> {
    return becomesVisible(this.insuranceApp.locator(`xpath=//button[@aria-label='${label}']`).first());
  }

  public async readDetail(): Promise<InsuranceDetail> {
    return {
      title: (await this.detailTitle.innerText()).trim(),
      description: (await this.detailDescription.innerText()).trim(),
    };
  }

  private get insuranceApp(): FrameLocator {
    return this.page.frameLocator('iframe[name="iframeQualia"]');
  }

  private get insuranceFrame(): Locator {
    return this.page.locator('iframe[name="iframeQualia"]');
  }

  private get robberyInsurance(): Locator {
    return this.insuranceApp.getByText('Bolso', { exact: true }).locator('..');
  }

  private get detailTitle(): Locator {
    return this.insuranceApp.getByRole('heading', { name: 'Seguro de Bolso', exact: true });
  }

  private get detailDescription(): Locator {
    return this.insuranceApp.getByText('Protección para tus pertenencias en la vía pública', { exact: true });
  }
}
