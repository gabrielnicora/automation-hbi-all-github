import type { Locator } from '@playwright/test';

const VISIBILITY_TIMEOUT_MS = 15_000;

/**
 * Espera a que un elemento sea visible y devuelve el resultado como dato simple.
 * Permite que el test exprese la expectativa sin conocer el locator ni capturar timeouts.
 */
export async function becomesVisible(locator: Locator, timeout = VISIBILITY_TIMEOUT_MS): Promise<boolean> {
  try {
    await locator.waitFor({ state: 'visible', timeout });
    return true;
  } catch {
    return false;
  }
}
