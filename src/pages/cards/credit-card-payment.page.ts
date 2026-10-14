import { expect, type Locator, type Page } from '@playwright/test';

const CREDIT_CARDS_TITLE = 'Tarjetas de crédito';
const OWN_CARD_PAYMENT_TITLE = 'Pago de tarjeta propia';

export class CreditCardPaymentPage {
  public constructor(private readonly page: Page) {}

  public async open(): Promise<void> {
    await expect(this.cardsMenuButton).toBeVisible();
    await this.cardsMenuButton.click();
    await expect(this.creditCardsMenuButton).toBeVisible();
    await this.creditCardsMenuButton.click();
    await expect(this.creditCardsTitle).toBeVisible();
  }

  public async openOwnCardPayment(creditCard: string): Promise<void> {
    await expect(this.cardDetailsLink(creditCard)).toBeVisible();
    await this.cardDetailsLink(creditCard).click();
    await expect(this.optionsButton).toBeVisible();
    await this.optionsButton.click();
    await expect(this.payMyCardOption).toBeVisible();
    await this.payMyCardOption.click();
    await expect(this.ownCardPaymentTitle).toBeVisible();
  }

  public async selectDebitCard(debitCardLastFour: string): Promise<void> {
    await expect(this.debitCardSelector).toBeVisible();
    await this.debitCardSelector.click();
    await expect(this.reactSelectOption(debitCardLastFour)).toBeVisible();
    await this.reactSelectOption(debitCardLastFour).click();
    await expect(this.debitCardSelector).toContainText(debitCardLastFour);
    await expect(this.continueButton).toBeVisible();
  }

  public async continueToPaymentDetails(): Promise<void> {
    await expect(this.continueButton).toBeEnabled();
    await this.continueButton.click();
    await expect(this.debitAccountSelector).toBeVisible();
  }

  public async selectDebitAccount(originAccount: string): Promise<void> {
    await expect(this.debitAccountSelector).toBeVisible();
    await this.debitAccountSelector.click();
    await this.selectDropdownOption(originAccount);
    await expect(this.debitAccountSelector).toContainText(originAccount);
    await expect(this.paymentTypeSelector).toBeVisible();
  }

  public async selectPaymentType(paymentType: string): Promise<void> {
    await expect(this.paymentTypeSelector).toBeVisible();
    await this.paymentTypeSelector.click();
    await this.selectDropdownOption(paymentType);
    await expect(this.paymentTypeSelector).toContainText(paymentType);
    await expect(this.payButton).toBeVisible();
  }

  public async openPaymentReview(): Promise<void> {
    await expect(this.payButton).toBeEnabled();
    await this.payButton.click();
    await expect(this.payButton).toBeHidden();
  }

  public async readPaymentReview(): Promise<string> {
    return (await this.page.locator('main').innerText()).trim();
  }

  private get creditCardsTitle(): Locator {
    return this.page.getByText(CREDIT_CARDS_TITLE, { exact: true }).first();
  }

  private get cardsMenuButton(): Locator {
    return this.page.getByRole('button', { name: 'Tarjetas', exact: true });
  }

  private get creditCardsMenuButton(): Locator {
    return this.page.getByRole('button', { name: 'Tarjeta de crédito', exact: true });
  }

  private get optionsButton(): Locator {
    return this.page.locator("xpath=//span[contains(text(), 'Opciones')]/../..").first();
  }

  private get payMyCardOption(): Locator {
    return this.page.locator("xpath=//span[contains(text(), 'Pagar mi ta')]").first();
  }

  private get ownCardPaymentTitle(): Locator {
    return this.page.getByText(OWN_CARD_PAYMENT_TITLE, { exact: true }).first();
  }

  private get debitCardSelector(): Locator {
    return this.page.locator('[id="selectorControlBy_cards.make.payment.making.debitCardSelector"]');
  }

  private get continueButton(): Locator {
    return this.page.getByRole('button', { name: 'Continuar', exact: true });
  }

  private get debitAccountSelector(): Locator {
    return this.page
      .getByText('Cuenta débito', { exact: true })
      .locator('xpath=ancestor::div[.//*[@role="combobox"]][1]');
  }

  private get paymentTypeSelector(): Locator {
    return this.page
      .getByText('Tipo de pago', { exact: true })
      .locator('xpath=ancestor::div[.//*[@role="combobox"]][1]');
  }

  private get payButton(): Locator {
    return this.page.getByRole('button', { name: 'Pagar', exact: true });
  }

  private cardDetailsLink(creditCard: string): Locator {
    return this.page
      .locator('span')
      .filter({ hasText: creditCard })
      .first()
      .locator('xpath=ancestor::div[1]/following-sibling::div//a')
      .first();
  }

  private reactSelectOption(option: string): Locator {
    return this.page.locator('.react-select__menu .react-select__option').filter({ hasText: option }).first();
  }

  private async selectDropdownOption(option: string): Promise<void> {
    await expect(this.reactSelectOption(option)).toBeVisible();
    await this.reactSelectOption(option).click();
  }
}
