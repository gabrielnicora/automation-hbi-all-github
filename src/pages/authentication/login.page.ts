import { expect, type Locator, type Page, type Response } from '@playwright/test';
import type { LoginCredentials } from '../../test-data/login-credentials';
import { createAuthenticatedSession, type AuthenticatedSession } from '../../models/authenticated-session';

type JsonObject = Record<string, unknown>;

const ACCESS_TOKEN_PATH = ['data', '_accessToken'] as const;
const AUTHENTICATED_STORAGE_KEY = 'persist:session';
const AUTHENTICATION_RESPONSE_PATH = '/api/v1/execute/session.login.step3';

function readStringAtPath(value: unknown, path: readonly string[]): string | undefined {
  let currentValue = value;

  for (const propertyName of path) {
    if (currentValue === null || typeof currentValue !== 'object' || Array.isArray(currentValue)) {
      return undefined;
    }

    currentValue = (currentValue as JsonObject)[propertyName];
  }

  return typeof currentValue === 'string' && currentValue.trim() ? currentValue : undefined;
}

function isAuthenticationResponse(response: Response): boolean {
  return new URL(response.url()).pathname === AUTHENTICATION_RESPONSE_PATH;
}

export class LoginPage {
  public constructor(private readonly page: Page) {}

  public async authenticate(credentials: LoginCredentials): Promise<AuthenticatedSession> {
    await expect(this.usernameInput).toBeVisible();

    await this.usernameInput.fill(credentials.username);
    await this.continueButton.click();
    await expect(this.passwordInput).toBeVisible();

    const authenticationResponsePromise = this.page.waitForResponse(isAuthenticationResponse);

    await this.passwordInput.fill(credentials.password);
    await this.submitButton.click();

    const authenticationResponse = await authenticationResponsePromise;

    if (authenticationResponse.status() !== 200) {
      throw new Error(`El login UI respondió con HTTP ${authenticationResponse.status()} en autenticación.`);
    }

    const responseBody: unknown = await authenticationResponse.json();
    const accessToken = readStringAtPath(responseBody, ACCESS_TOKEN_PATH);

    if (!accessToken) {
      throw new Error('La respuesta de login UI no contiene un access token válido.');
    }

    await this.page.waitForFunction(
      (storageKey) => window.sessionStorage.getItem(storageKey) !== null,
      AUTHENTICATED_STORAGE_KEY,
    );

    return createAuthenticatedSession(accessToken);
  }

  private get continueButton(): Locator {
    return this.page.getByRole('button', { name: 'Continuar', exact: true });
  }

  private get passwordInput(): Locator {
    return this.page.locator('#password');
  }

  private get submitButton(): Locator {
    return this.page.getByRole('button', { name: 'Ingresar', exact: true });
  }

  private get usernameInput(): Locator {
    return this.page.locator('#username');
  }
}
