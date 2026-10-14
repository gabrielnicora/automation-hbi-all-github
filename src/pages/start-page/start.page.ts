import { expect, type Locator, type Page } from '@playwright/test';

export class StartPage {
  public constructor(
    private readonly page: Page,
    private readonly baseUrl: string,
  ) {}

  public async open(): Promise<void> {
    await this.page.goto(this.baseUrl);
    await expect(this.enterButton).toBeVisible();
  }

  public async openLogin(): Promise<void> {
    await this.enterButton.click();
  }

  public async openRegistration(): Promise<void> {
    await this.registrationLink.click();
  }

  public async isRegistrationAvailable(): Promise<boolean> {
    try {
      await this.registrationLink.waitFor({ state: 'visible' });
      return true;
    } catch {
      return false;
    }
  }

  private get enterButton(): Locator {
    return this.page.getByRole('button', { name: 'Ingresar', exact: true });
  }

  private get registrationLink(): Locator {
    // BERSA renders this control as a clickable paragraph/generic element, not an
    // anchor. Text-based lookup keeps the Page Object independent of that markup.
    return this.page.getByText('¿No sos cliente aún? Registrarme', { exact: true });
  }
}
