import { expect, type Locator, type Page, type Response } from '@playwright/test';
import type { LoginCredentials } from '../../test-data/login-credentials';
import { createAuthenticatedSession, type AuthenticatedSession } from '../../models/authenticated-session';
import { becomesVisible } from '../visibility';

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

export interface ExternalPageSummary {
  readonly heading: string;
  readonly url: string;
}

export interface StoreShortcutResult {
  readonly hasStoreLogo: boolean;
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

    return this.readAuthenticatedSession(authenticationResponsePromise);
  }

  /** Igual que `authenticate`, pero ingresa la contraseña con el teclado virtual de la pantalla. */
  public async authenticateWithVirtualKeyboard(credentials: LoginCredentials): Promise<AuthenticatedSession> {
    await expect(this.usernameInput).toBeVisible();

    await this.usernameInput.fill(credentials.username);
    await this.continueButton.click();
    await expect(this.passwordInput).toBeVisible();

    await this.virtualKeyboardToggle.click();
    await expect(this.virtualKeyboardKey('{enter}')).toBeVisible();

    const authenticationResponsePromise = this.page.waitForResponse(isAuthenticationResponse);

    await this.typeOnVirtualKeyboard(credentials.password);
    await this.virtualKeyboardKey('{enter}').click();

    return this.readAuthenticatedSession(authenticationResponsePromise);
  }

  /** Abre "Información de seguridad" desde la pantalla de usuario y lee el título de la página externa. */
  public async openSecurityInformation(): Promise<ExternalPageSummary> {
    await expect(this.usernameInput).toBeVisible();
    return this.readExternalPage(this.page.getByRole('link', { name: 'Información de seguridad' }));
  }

  /** Abre el acceso directo "Tienda.Clic" y confirma que la página externa muestra su logo. */
  public async openStoreShortcut(): Promise<StoreShortcutResult> {
    await expect(this.usernameInput).toBeVisible();
    await expect(this.storeShortcut, 'La pantalla debe ofrecer el acceso directo Tienda.Clic.').toBeVisible();

    const [storePage] = await Promise.all([this.page.context().waitForEvent('page'), this.storeShortcut.click()]);
    await storePage.waitForLoadState('domcontentloaded');
    const hasStoreLogo = await becomesVisible(storePage.getByAltText('Tienda Clic').first());
    await storePage.close();
    return { hasStoreLogo };
  }

  private async readExternalPage(trigger: Locator): Promise<ExternalPageSummary> {
    const [externalPage] = await Promise.all([this.page.context().waitForEvent('page'), trigger.click()]);
    await externalPage.waitForLoadState('domcontentloaded');
    const heading = externalPage.getByRole('heading', { level: 1 }).first();
    await expect(heading).toBeVisible();
    const summary = { heading: (await heading.innerText()).trim(), url: externalPage.url() };
    await externalPage.close();
    return summary;
  }

  private async readAuthenticatedSession(
    authenticationResponsePromise: Promise<Response>,
  ): Promise<AuthenticatedSession> {
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

  private async typeOnVirtualKeyboard(password: string): Promise<void> {
    // El teclado se abre con mayúsculas activas: la primera tecla "shift" pasa a minúsculas.
    await this.virtualKeyboardKey('{shift}').click();

    for (const character of password) {
      if (/\p{L}/u.test(character)) {
        if (character === character.toUpperCase()) {
          await this.virtualKeyboardKey('{shift}').click();
          await this.virtualKeyboardKey(character).click();
          await this.virtualKeyboardKey('{shift}').click();
        } else {
          await this.virtualKeyboardKey(character).click();
        }
      } else {
        await this.virtualKeyboardKey('{?123}').click();
        await this.virtualKeyboardKey(character).click();
        await this.virtualKeyboardKey('{ABC}').click();
      }
    }
  }

  private virtualKeyboardKey(key: string): Locator {
    return this.page.locator(`div[data-skbtn=${JSON.stringify(key)}]`);
  }

  private get storeShortcut(): Locator {
    return this.page.getByLabel('Icono de compra');
  }

  private get virtualKeyboardToggle(): Locator {
    return this.page.getByText('teclado virtual', { exact: true });
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
