import type { Locator, Page } from '@playwright/test';

/**
 * Menú lateral de Home Banking. Sólo contiene la mecánica de navegación: cada Page Object
 * de destino espera su propio encabezado como señal de pantalla lista.
 */
export class SideMenu {
  public constructor(private readonly page: Page) {}

  public async openHome(): Promise<void> {
    await this.item('Inicio').click();
  }

  public async openAccounts(): Promise<void> {
    await this.item('Cuentas').click();
  }

  public async openTransfers(): Promise<void> {
    await this.item('Transferencias').click();
  }

  public async openPayments(): Promise<void> {
    await this.item('Pagos y servicios').click();
    await this.item('Pagos').click();
  }

  public async openCreditCards(): Promise<void> {
    await this.item('Tarjetas').click();
    await this.item('Tarjeta de crédito').click();
  }

  public async openInsurance(): Promise<void> {
    await this.item('Seguros').click();
  }

  private item(name: string): Locator {
    return this.page.getByRole('button', { name, exact: true });
  }
}
