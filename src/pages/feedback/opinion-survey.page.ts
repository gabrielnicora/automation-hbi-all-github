import { expect, type Locator, type Page } from '@playwright/test';

const SURVEY_CLOSE_BUTTON_NAME = /^(Close|Cerrar)$/;
const USD_INVITATION_TITLE = '¡Empezá a operar en USD!';

export class OpinionSurveyPage {
  public constructor(private readonly page: Page) {}

  public async dismissWhenShown(): Promise<void> {
    await this.page.addLocatorHandler(this.surveyDialog, () => this.dismissVisibleOverlays());
    await this.page.addLocatorHandler(this.usdInvitationTitle, () => this.dismissVisibleOverlays());
  }

  private async dismissVisibleOverlays(): Promise<void> {
    if (await this.surveyDialog.isVisible()) {
      await this.surveyDialog.getByRole('button', { name: SURVEY_CLOSE_BUTTON_NAME }).click();
      await expect(this.surveyDialog).toBeHidden();
    }

    if (await this.usdInvitationTitle.isVisible()) {
      await this.usdInvitationCancelButton.click();
      await expect(this.usdInvitationTitle).toBeHidden();
    }
  }

  private get surveyDialog(): Locator {
    return this.page.getByRole('dialog').filter({ has: this.page.locator('iframe') });
  }

  private get usdInvitationTitle(): Locator {
    return this.page.getByText(USD_INVITATION_TITLE, { exact: true });
  }

  private get usdInvitationCancelButton(): Locator {
    return this.usdInvitationTitle
      .locator('xpath=ancestor::div[2]')
      .getByRole('button', { name: 'Cancelar', exact: true });
  }
}
