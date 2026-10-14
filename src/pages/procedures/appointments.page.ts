import { expect, type Page } from '@playwright/test';

export class AppointmentsPage {
  public constructor(private readonly page: Page) {}

  public async openExternalAppointments(): Promise<string> {
    await this.page.getByRole('button', { name: 'Trámites', exact: true }).click();
    await this.page.getByRole('button', { name: 'Turnos online', exact: true }).click();
    await expect(this.page.getByRole('heading', { name: 'Turnos online', exact: true })).toBeVisible();
    const appointmentPagePromise = this.page.context().waitForEvent('page');
    await this.page.getByRole('button', { name: 'Aceptar', exact: true }).click();

    const appointmentPage = await appointmentPagePromise;
    await appointmentPage.waitForLoadState('domcontentloaded');
    const title = await appointmentPage.title();
    await appointmentPage.close();
    return title;
  }
}
