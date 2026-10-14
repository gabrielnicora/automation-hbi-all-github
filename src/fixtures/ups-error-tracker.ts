import type { BrowserContext, Page, TestInfo } from '@playwright/test';
import { UpsErrorPage, type UpsErrorDetails } from '../pages/error/ups-error.page';

export class UpsErrorTracker {
  private readonly observations: UpsErrorDetails[] = [];
  private readonly pendingCaptures = new Set<Promise<void>>();

  public watch(context: BrowserContext, initialPage: Page): () => void {
    const watchPage = (page: Page) => {
      const onNavigation = () => this.capture(page);

      page.on('framenavigated', onNavigation);
      this.capture(page);

      return () => page.off('framenavigated', onNavigation);
    };
    const pageCleanups = new Map<Page, () => void>();
    const addPage = (page: Page) => pageCleanups.set(page, watchPage(page));

    addPage(initialPage);
    const onPage = (page: Page) => addPage(page);
    context.on('page', onPage);

    return () => {
      context.off('page', onPage);
      pageCleanups.forEach((cleanup) => cleanup());
    };
  }

  public async attachIfDetected(testInfo: TestInfo): Promise<void> {
    await Promise.all(this.pendingCaptures);

    if (this.observations.length === 0) {
      return;
    }

    const report = {
      attempt: testInfo.retry + 1,
      errors: this.observations,
      test: testInfo.title,
    };

    await testInfo.attach(`error-ups-intento-${testInfo.retry + 1}`, {
      body: Buffer.from(JSON.stringify(report, null, 2), 'utf8'),
      contentType: 'application/json',
    });
  }

  public throwIfDetected(): void {
    if (this.observations.length === 0) {
      return;
    }

    const references = this.observations
      .map((observation) => {
        const ids = [
          observation.referenceId ? `#${observation.referenceId}` : undefined,
          observation.trackingId ? `(${observation.trackingId})` : undefined,
        ]
          .filter((value): value is string => value !== undefined)
          .join(' ');

        return ids.length > 0 ? ids : observation.url;
      })
      .join('; ');

    throw new Error(`Se detectó la página de error ¡Ups! durante el recorrido. Referencias: ${references}.`);
  }

  private capture(page: Page): void {
    const capture = this.captureFrom(page)
      .catch(() => undefined)
      .finally(() => this.pendingCaptures.delete(capture));

    this.pendingCaptures.add(capture);
  }

  private async captureFrom(page: Page): Promise<void> {
    const details = await new UpsErrorPage(page).readIfPresent();

    if (!details || this.wasAlreadyObserved(details)) {
      return;
    }

    this.observations.push(details);
  }

  private wasAlreadyObserved(details: UpsErrorDetails): boolean {
    return this.observations.some(
      (observation) =>
        observation.url === details.url &&
        observation.referenceId === details.referenceId &&
        observation.trackingId === details.trackingId,
    );
  }
}
