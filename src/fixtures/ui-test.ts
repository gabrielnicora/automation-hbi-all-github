import path from 'node:path';
import { request as playwrightRequest } from '@playwright/test';
import { SecondFactorChallengeApi } from '../api/authentication/second-factor-challenge.api';
import { SecondFactorCodeInboxApi } from '../api/authentication/second-factor-code-inbox.api';
import { LoginApi } from '../api/authentication/login.api';
import { ProfileMobilePhoneApi } from '../api/profile/profile-mobile-phone.api';
import { ReportingApiRequest } from '../api/reporting-api-request';
import { loadSecondFactorConfig } from '../config/second-factor';
import { LoginPage } from '../pages/authentication/login.page';
import { ChangeAddressPage } from '../pages/profile/change-address.page';
import { AppointmentsPage } from '../pages/procedures/appointments.page';
import { loadEnvironmentConfig } from '../config/environment';
import { InvestmentFundsPage } from '../pages/investments/investment-funds.page';
import { ForeignCurrencyPurchasePage } from '../pages/investments/foreign-currency-purchase.page';
import { InsurancePage } from '../pages/insurance/insurance.page';
import { MessagesPage } from '../pages/messages/messages.page';
import { CancellationPage } from '../pages/mis-products/cancellation.page';
import { CreditCardPaymentPage } from '../pages/cards/credit-card-payment.page';
import { IdentityValidationPage } from '../pages/registration/identity-validation.page';
import { StartPage } from '../pages/start-page/start.page';
import { OwnAccountTransferPage } from '../pages/transfers/own-account-transfer.page';
import { EcheqPage } from '../pages/accounts/echeq.page';
import { AccountAliasPage } from '../pages/accounts/account-alias.page';
import { AccountMovementsPage } from '../pages/accounts/account-movements.page';
import { OpinionSurveyPage } from '../pages/feedback/opinion-survey.page';
import {
  loadCreditCardTotalPaymentUiData,
  type CreditCardTotalPaymentUiData,
} from '../test-data/credit-card-total-payment-ui-data';
import { loadOwnAccountTransferUiData, type OwnAccountTransferUiData } from '../test-data/own-account-transfer-ui-data';
import { loadAccountAliasUiData, type AccountAliasUiData } from '../test-data/account-alias-ui-data';
import { loadAccountMovementsUiData, type AccountMovementsUiData } from '../test-data/account-movements-ui-data';
import {
  loadForeignCurrencyPurchaseUiData,
  type ForeignCurrencyPurchaseUiData,
} from '../test-data/foreign-currency-purchase-ui-data';
import { expect, test as testWithData } from './test-case.fixture';
import { UpsErrorTracker } from './ups-error-tracker';

interface UiFixtures {
  readonly appointmentsPage: AppointmentsPage;
  readonly accountAliasPage: AccountAliasPage;
  readonly accountAliasUiData: AccountAliasUiData;
  readonly accountMovementsPage: AccountMovementsPage;
  readonly accountMovementsUiData: AccountMovementsUiData;
  readonly cancellationPage: CancellationPage;
  readonly changeAddressPage: ChangeAddressPage;
  readonly creditCardPaymentPage: CreditCardPaymentPage;
  readonly echeqPage: EcheqPage;
  readonly creditCardTotalPaymentUiData: CreditCardTotalPaymentUiData;
  readonly identityValidationPage: IdentityValidationPage;
  readonly insurancePage: InsurancePage;
  readonly investmentFundsPage: InvestmentFundsPage;
  readonly foreignCurrencyPurchasePage: ForeignCurrencyPurchasePage;
  readonly foreignCurrencyPurchaseUiData: ForeignCurrencyPurchaseUiData;
  readonly loginPage: LoginPage;
  readonly loginApi: LoginApi;
  readonly messagesPage: MessagesPage;
  readonly profileMobilePhoneApi: ProfileMobilePhoneApi;
  readonly secondFactorChallengeApi: SecondFactorChallengeApi;
  readonly startPage: StartPage;
  readonly ownAccountTransferPage: OwnAccountTransferPage;
  readonly ownAccountTransferUiData: OwnAccountTransferUiData;
  readonly opinionSurveyHandler: void;
  readonly uiApiRequest: ReportingApiRequest;
  readonly upsErrorTracking: void;
}

export const test = testWithData.extend<UiFixtures>({
  opinionSurveyHandler: [
    async ({ page }, use) => {
      await new OpinionSurveyPage(page).dismissWhenShown();
      await use();
    },
    { auto: true },
  ],

  upsErrorTracking: [
    async ({ context, page }, use, testInfo) => {
      const tracker = new UpsErrorTracker();
      const stopWatching = tracker.watch(context, page);

      await use();
      stopWatching();
      await tracker.attachIfDetected(testInfo);
      tracker.throwIfDetected();
    },
    { auto: true },
  ],

  appointmentsPage: async ({ page }, use) => {
    await use(new AppointmentsPage(page));
  },

  accountAliasPage: async ({ page }, use) => {
    await use(new AccountAliasPage(page));
  },

  accountMovementsPage: async ({ page }, use) => {
    await use(new AccountMovementsPage(page));
  },

  accountMovementsUiData: async ({ testCaseId }, use) => {
    await use(await loadAccountMovementsUiData(testCaseId));
  },

  accountAliasUiData: async ({ testCaseId }, use) => {
    await use(await loadAccountAliasUiData(testCaseId));
  },

  cancellationPage: async ({ page }, use) => {
    await use(new CancellationPage(page));
  },

  changeAddressPage: async ({ page }, use) => {
    await use(new ChangeAddressPage(page));
  },

  creditCardPaymentPage: async ({ page }, use) => {
    await use(new CreditCardPaymentPage(page));
  },

  echeqPage: async ({ page }, use) => {
    await use(new EcheqPage(page));
  },

  creditCardTotalPaymentUiData: async ({ testCaseId }, use) => {
    await use(await loadCreditCardTotalPaymentUiData(testCaseId));
  },

  identityValidationPage: async ({ page }, use) => {
    await use(new IdentityValidationPage(page));
  },

  insurancePage: async ({ page }, use) => {
    await use(new InsurancePage(page));
  },

  investmentFundsPage: async ({ page }, use) => {
    const config = loadEnvironmentConfig();
    await use(new InvestmentFundsPage(page, config.uiBaseUrl));
  },

  foreignCurrencyPurchasePage: async ({ page }, use) => {
    await use(new ForeignCurrencyPurchasePage(page));
  },

  foreignCurrencyPurchaseUiData: async ({ testCaseId }, use) => {
    await use(await loadForeignCurrencyPurchaseUiData(testCaseId));
  },

  messagesPage: async ({ page }, use) => {
    await use(new MessagesPage(page));
  },

  uiApiRequest: async ({}, use, testInfo) => {
    const config = loadEnvironmentConfig();
    const apiContext = await playwrightRequest.newContext({
      baseURL: config.apiBaseUrl,
      extraHTTPHeaders: { 'Content-Type': 'application/json' },
      ignoreHTTPSErrors: true,
    });
    const apiRequest = new ReportingApiRequest(apiContext);

    await use(apiRequest);
    await apiRequest.attachIfTestFailed(testInfo);
    await apiContext.dispose();
  },

  profileMobilePhoneApi: async ({ uiApiRequest }, use) => {
    await use(new ProfileMobilePhoneApi(uiApiRequest));
  },

  secondFactorChallengeApi: async ({ uiApiRequest }, use) => {
    const privateDataDirectory = path.resolve(process.cwd(), 'test-data', 'private');
    const secondFactorConfig = await loadSecondFactorConfig(path.join(privateDataDirectory, 'second-factor.json'));
    const codeInbox = new SecondFactorCodeInboxApi(uiApiRequest);

    await use(new SecondFactorChallengeApi(codeInbox, secondFactorConfig));
  },

  loginPage: async ({ page }, use) => {
    await use(new LoginPage(page));
  },

  loginApi: async ({ uiApiRequest }, use) => {
    await use(new LoginApi(uiApiRequest));
  },

  startPage: async ({ page }, use) => {
    const config = loadEnvironmentConfig();
    await use(new StartPage(page, config.uiBaseUrl));
  },

  ownAccountTransferPage: async ({ page }, use) => {
    await use(new OwnAccountTransferPage(page));
  },

  ownAccountTransferUiData: async ({ testCaseId }, use) => {
    await use(await loadOwnAccountTransferUiData(testCaseId));
  },
});

export { expect };
