import { expect, type Locator, type Page, type Response } from '@playwright/test';
import type { AddressChangeData } from '../../models/change-address';

const SMS_OTP_PATH = '/api/v1/execute/secondFactor.otp.sms.sendToken';
const LOCATION_PATH = '/api/v1/execute/location.province';
const SMS_REQUEST_TIMEOUT_MS = 60_000;

function isSmsOtpResponse(response: Response): boolean {
  return new URL(response.url()).pathname === SMS_OTP_PATH;
}

function isLocationResponse(response: Response): boolean {
  return new URL(response.url()).pathname === LOCATION_PATH;
}

function normalizeLocalityLabel(value: string): string {
  return value.replace(/^\d+\s*-\s*/, '').trim();
}

export class ChangeAddressPage {
  public constructor(private readonly page: Page) {}

  public async open(): Promise<void> {
    await this.profileButton.click();
    await expect(this.profileLink).toBeVisible();
    await this.profileLink.click();
    await expect(this.modifyAddressLink).toBeVisible();
    const initialLocationsResponsePromise = this.page.waitForResponse(isLocationResponse);
    await this.modifyAddressLink.click();
    await expect(this.page).toHaveURL(/\/settings\/changeAddress$/);
    await expect(this.heading).toBeVisible();
    await expect(this.currentAddressSelector).toBeVisible();
    await initialLocationsResponsePromise;
  }

  public async completeAddress(address: AddressChangeData): Promise<AddressChangeData> {
    await this.currentAddressSelector.click();
    await expect(this.modifyAddressOption).toBeVisible();
    await this.modifyAddressOption.click();

    await this.streetInput.fill(address.street);
    await this.numberInput.fill(address.number);
    await this.floorInput.fill(address.floor);
    await this.apartmentInput.fill(address.apartment);
    const localitiesResponsePromise = this.page.waitForResponse(isLocationResponse);

    await this.selectOption(this.provinceInput, address.province);
    await localitiesResponsePromise;
    try {
      await expect(this.localityInput).toBeEnabled();
    } catch {
      throw new Error(
        `No hay localidades disponibles para la provincia ${address.province}; el caso requiere datos geográficos QA válidos.`,
      );
    }
    const locality = address.locality || (await this.selectFirstLocality());
    await expect(this.continueButton).toBeEnabled();

    return { ...address, locality };
  }

  public async requestInitialSecondFactorCode(): Promise<void> {
    await this.requestSmsCode(this.continueButton);
    await expect(this.reviewText).toBeVisible();
    await expect(this.secondFactorInput).toBeVisible();
  }

  public async requestReplacementSecondFactorCode(): Promise<void> {
    await expect(this.resendCodeControl).toBeVisible();
    await this.requestSmsCode(this.resendCodeControl);
    await expect(this.secondFactorInput).toBeVisible();
  }

  public async readReviewedAddress(): Promise<string> {
    await expect(this.reviewText).toBeVisible();
    return (await this.page.locator('#mainLayout').innerText()).trim();
  }

  public async enterSecondFactorCode(code: string): Promise<void> {
    await this.secondFactorInput.fill(code);
    await expect(this.confirmButton).toBeEnabled();
    await this.confirmButton.click();
  }

  public async readAddressChangeResult(): Promise<string> {
    await expect(this.addressChangeSuccessHeading).toBeVisible();
    return (await this.page.locator('#mainLayout').innerText()).trim();
  }

  private get apartmentInput(): Locator {
    return this.page.locator('#apartment');
  }

  private get addressChangeSuccessHeading(): Locator {
    return this.page.getByRole('heading', { name: 'Tu solicitud se realizó con éxito', exact: true });
  }

  private get confirmButton(): Locator {
    return this.page.getByRole('button', { name: 'Confirmar', exact: true });
  }

  private get continueButton(): Locator {
    return this.page.getByRole('button', { name: 'Continuar', exact: true });
  }

  private get currentAddressSelector(): Locator {
    return this.page.locator('[aria-describedby="selectorDescribedBy_0"]').locator('xpath=../..');
  }

  private get floorInput(): Locator {
    return this.page.locator('#floor');
  }

  private get heading(): Locator {
    return this.page.getByRole('heading', { name: 'Modificar Domicilio', exact: true });
  }

  private get localityInput(): Locator {
    return this.selectInputs.last();
  }

  private get modifyAddressLink(): Locator {
    return this.page.getByRole('link', { name: /Modificar domicilio/ });
  }

  private get modifyAddressOption(): Locator {
    return this.page.getByText('Modificar domicilio', { exact: true });
  }

  private get numberInput(): Locator {
    return this.page.getByRole('textbox', { name: 'campo caracteres permitidos solo numerico' });
  }

  private get profileButton(): Locator {
    return this.page.getByText('Perfil', { exact: true });
  }

  private get profileLink(): Locator {
    return this.page.getByRole('link', { name: 'Ir a mi perfil', exact: true });
  }

  private get provinceInput(): Locator {
    return this.selectInputs.nth(1);
  }

  private get resendCodeControl(): Locator {
    return this.page.getByText('Solicitar nuevo código por SMS', { exact: true });
  }

  private get reviewText(): Locator {
    return this.page.getByText('Verificá que todos los datos sean correctos antes de confirmar el cambio.', {
      exact: true,
    });
  }

  private get secondFactorInput(): Locator {
    return this.page.getByRole('textbox', { name: 'campo caracteres permitidos solo numerico' });
  }

  private get streetInput(): Locator {
    return this.page.getByRole('textbox', { name: 'Calle', exact: true });
  }

  private async requestSmsCode(control: Locator): Promise<void> {
    await expect(control).toBeEnabled();
    const smsResponsePromise = this.page.waitForResponse(isSmsOtpResponse, { timeout: SMS_REQUEST_TIMEOUT_MS });

    await control.click();

    let smsResponse: Response;
    try {
      smsResponse = await smsResponsePromise;
    } catch {
      throw new Error('La solicitud SMS de segundo factor para domicilio no produjo respuesta HTTP.');
    }
    if (smsResponse.status() !== 200) {
      throw new Error(`La solicitud de segundo factor para domicilio respondió con HTTP ${smsResponse.status()}.`);
    }
  }

  private async selectOption(input: Locator, option: string): Promise<void> {
    await expect(input).toBeEnabled();
    const describedBy = await input.getAttribute('aria-describedby');
    if (describedBy) {
      const selectorControl = this.page.locator(`#${describedBy}`);
      if (await selectorControl.isVisible()) {
        await selectorControl.click();
      }
    }
    try {
      const optionLocator = this.page.getByText(option, { exact: true }).last();
      await expect(optionLocator).toBeVisible();
      await optionLocator.click();
    } catch {
      throw new Error(`No hay opciones disponibles para seleccionar ${option} en el formulario de domicilio.`);
    }
    await expect(input).toHaveValue(new RegExp(option, 'i'));
  }

  private async selectFirstLocality(): Promise<string> {
    const input = this.localityInput;
    const describedBy = await input.getAttribute('aria-describedby');
    if (describedBy) {
      const selectorControl = this.page.locator(`#${describedBy}`);
      if (await selectorControl.isVisible()) {
        await selectorControl.click();
      }
    }

    const firstOption = this.page.locator('[id^="react-select-"][id*="-option-"]:visible').first();
    try {
      await expect(firstOption).toBeVisible();
    } catch {
      throw new Error('La provincia seleccionada no devolvió localidades QA disponibles.');
    }

    const locality = normalizeLocalityLabel(await firstOption.innerText());
    await firstOption.click();
    await expect(input).toHaveAttribute('aria-expanded', 'false');
    return locality;
  }

  private get selectInputs(): Locator {
    return this.page.locator('input[id^="react-select-"][id$="-input"]');
  }
}
