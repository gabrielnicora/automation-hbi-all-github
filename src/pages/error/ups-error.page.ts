import type { Page } from '@playwright/test';

const UPS_ERROR_PATH = '/error';
const UPS_REFERENCE_PATTERN =
  /#([a-f0-9]{32})\s*\(\s*([a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12})\s*\)/i;

export interface UpsErrorDetails {
  readonly message: string;
  readonly referenceId?: string;
  readonly trackingId?: string;
  readonly url: string;
}

function isUpsErrorUrl(url: string): boolean {
  try {
    return new URL(url).pathname === UPS_ERROR_PATH;
  } catch {
    return false;
  }
}

export class UpsErrorPage {
  public constructor(private readonly page: Page) {}

  public async readIfPresent(): Promise<UpsErrorDetails | undefined> {
    const url = this.page.url();

    if (!isUpsErrorUrl(url)) {
      return undefined;
    }

    await this.page
      .getByText(/ups/i)
      .first()
      .waitFor({ state: 'visible', timeout: 5_000 })
      .catch(() => undefined);
    const message = await this.page
      .locator('body')
      .innerText({ timeout: 5_000 })
      .catch(() => '');
    const references = message.match(UPS_REFERENCE_PATTERN);

    return {
      message,
      referenceId: references?.[1],
      trackingId: references?.[2],
      url,
    };
  }
}
