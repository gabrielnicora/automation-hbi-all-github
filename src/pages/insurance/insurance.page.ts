import { expect, type FrameLocator, type Locator, type Page } from '@playwright/test';

export interface InsuranceDetail {
  readonly title: string;
  readonly description: string;
}

export class InsurancePage {
  public constructor(private readonly page: Page) {}

  public async openRobberyInsuranceDetail(): Promise<void> {
    await this.page.getByRole('button', { name: 'Seguros', exact: true }).click();
    await expect(this.insuranceFrame).toBeVisible();
    await expect(this.insuranceApp.getByRole('heading', { name: 'Seguros', exact: true })).toBeVisible();

    await expect(this.robberyInsurance).toBeVisible();
    await this.robberyInsurance.click();

    await expect(this.detailTitle).toBeVisible();
    await expect(this.detailDescription).toBeVisible();
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
