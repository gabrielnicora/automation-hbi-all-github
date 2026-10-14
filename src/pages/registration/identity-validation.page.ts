import { type Locator, type Page } from '@playwright/test';

export class IdentityValidationPage {
  public constructor(private readonly page: Page) {}

  public async isAvailable(): Promise<boolean> {
    try {
      await this.identityValidationHeading.waitFor({ state: 'visible' });
      return true;
    } catch {
      return false;
    }
  }

  private get identityValidationHeading(): Locator {
    return this.page.getByRole('heading', { name: 'Es necesario validar tu identidad para continuar', exact: true });
  }
}
