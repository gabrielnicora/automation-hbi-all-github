import { expect, type Locator, type Page } from '@playwright/test';

export interface CancellationRedirectResult {
  readonly url: string;
  readonly title: string;
}

export class CancellationPage {
  public constructor(private readonly page: Page) {}

  public async open(): Promise<void> {
    await this.misProductsButton.click();
    await expect(this.cancellationButton).toBeVisible();
    await this.cancellationButton.click();
    await expect(this.page).toHaveURL(/\/unsuscribe$/);
    await expect(this.confirmationHeading).toBeVisible();
  }

  public async isConfirmationAvailable(): Promise<boolean> {
    return this.confirmationHeading.isVisible();
  }

  public async acceptExternalRedirect(): Promise<CancellationRedirectResult> {
    const publicPagePromise = this.page.context().waitForEvent('page');

    await this.acceptButton.click();

    const publicPage = await publicPagePromise;
    await publicPage.waitForLoadState('domcontentloaded');
    const result = { url: publicPage.url(), title: await publicPage.title() };
    await publicPage.close();
    return result;
  }

  private get acceptButton(): Locator {
    return this.page.getByRole('button', { name: 'Aceptar', exact: true });
  }

  private get cancellationButton(): Locator {
    return this.page.getByRole('button', { name: 'Baja / Arrepentimiento', exact: true });
  }

  private get confirmationHeading(): Locator {
    return this.page.getByRole('heading', { name: 'Baja y arrepentimiento', exact: true });
  }

  private get misProductsButton(): Locator {
    return this.page.getByRole('button', { name: 'Mis Productos', exact: true });
  }
}
