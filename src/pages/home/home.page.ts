import { expect, type Locator, type Page } from '@playwright/test';
import { SideMenu } from '../navigation/side-menu';
import { becomesVisible } from '../visibility';

export interface HomeAlert {
  readonly hasIcon: boolean;
  readonly backgroundColor: string;
}

export class HomePage {
  private readonly menu: SideMenu;

  public constructor(private readonly page: Page) {
    this.menu = new SideMenu(page);
  }

  public async open(): Promise<void> {
    await this.menu.openHome();
    await expect(this.greeting).toBeVisible();
  }

  public async readGreeting(): Promise<string> {
    await expect(this.greeting).toBeVisible();
    return (await this.greeting.innerText()).trim();
  }

  /** Espera el aviso informativo del inicio y devuelve su icono y color de fondo renderizados. */
  public async readAlert(): Promise<HomeAlert> {
    await expect(this.alert).toBeVisible();
    return {
      hasIcon: await becomesVisible(this.alertIcon),
      backgroundColor: await this.alert.evaluate((element) => getComputedStyle(element).backgroundColor),
    };
  }

  private get alert(): Locator {
    return this.page.locator('#container');
  }

  private get alertIcon(): Locator {
    return this.alert.locator('svg').first();
  }

  private get greeting(): Locator {
    return this.page.getByRole('heading', { level: 2, name: /^Hola/ });
  }
}
