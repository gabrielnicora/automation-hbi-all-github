import path from 'node:path';
import { request as playwrightRequest, type APIRequestContext } from '@playwright/test';
import { loadEnvironmentConfig } from '../config/environment';
import { loadSecondFactorConfig } from '../config/second-factor';
import { SecondFactorChallengeApi } from '../api/authentication/second-factor-challenge.api';
import { SecondFactorCodeInboxApi } from '../api/authentication/second-factor-code-inbox.api';
import { LoginApi } from '../api/authentication/login.api';
import { TermsAndConditionsApi } from '../api/home/terms-and-conditions.api';
import { TraditionalFixedTermApi } from '../api/investments/traditional-fixed-term.api';
import { ReceivedMessagesApi } from '../api/messages/received-messages.api';
import { DebinGenerationApi } from '../api/payments/debin-generation.api';
import { PhoneChangeApi } from '../api/profile/phone-change.api';
import { ProfileMobilePhoneApi } from '../api/profile/profile-mobile-phone.api';
import { NotificationPreferencesApi } from '../api/profile/notification-preferences.api';
import { CurrentOnlineProceduresApi } from '../api/procedures/current-online-procedures.api';
import { OwnAccountTransferApi } from '../api/transfers/own-account-transfer.api';
import { CreditCardOtherAmountPaymentApi } from '../api/cards/credit-card-other-amount-payment.api';
import { WithdrawalOrderApi } from '../api/accounts/withdrawal-order.api';
import { ReportingApiRequest } from '../api/reporting-api-request';
import { loadPhoneChangeTestData } from '../test-data/phone-change-data';
import { expect, test as testWithData } from './test-case.fixture';

interface ApiFixtures {
  readonly apiContext: APIRequestContext;
  readonly apiRequest: ReportingApiRequest;
  readonly currentOnlineProceduresApi: CurrentOnlineProceduresApi;
  readonly debinGenerationApi: DebinGenerationApi;
  readonly loginApi: LoginApi;
  readonly notificationPreferencesApi: NotificationPreferencesApi;
  readonly phoneChangeApi: PhoneChangeApi;
  readonly receivedMessagesApi: ReceivedMessagesApi;
  readonly ownAccountTransferApi: OwnAccountTransferApi;
  readonly creditCardOtherAmountPaymentApi: CreditCardOtherAmountPaymentApi;
  readonly termsAndConditionsApi: TermsAndConditionsApi;
  readonly traditionalFixedTermApi: TraditionalFixedTermApi;
  readonly withdrawalOrderApi: WithdrawalOrderApi;
}

export const test = testWithData.extend<ApiFixtures>({
  apiContext: async ({}, use) => {
    const config = loadEnvironmentConfig();
    const apiContext = await playwrightRequest.newContext({
      baseURL: config.apiBaseUrl,
      ignoreHTTPSErrors: true,
      extraHTTPHeaders: {
        'Content-Type': 'application/json',
      },
    });

    await use(apiContext);
    await apiContext.dispose();
  },

  apiRequest: async ({ apiContext }, use, testInfo) => {
    const apiRequest = new ReportingApiRequest(apiContext);

    await use(apiRequest);
    await apiRequest.attachIfTestFailed(testInfo);
  },

  loginApi: async ({ apiRequest }, use) => {
    await use(new LoginApi(apiRequest));
  },

  debinGenerationApi: async ({ apiRequest }, use) => {
    await use(new DebinGenerationApi(apiRequest));
  },

  traditionalFixedTermApi: async ({ apiRequest }, use) => {
    await use(new TraditionalFixedTermApi(apiRequest));
  },

  receivedMessagesApi: async ({ apiRequest }, use) => {
    await use(new ReceivedMessagesApi(apiRequest));
  },

  notificationPreferencesApi: async ({ apiRequest }, use) => {
    await use(new NotificationPreferencesApi(apiRequest));
  },

  currentOnlineProceduresApi: async ({ apiRequest }, use) => {
    await use(new CurrentOnlineProceduresApi(apiRequest));
  },

  phoneChangeApi: async ({ apiRequest }, use) => {
    const privateDataDirectory = path.resolve(process.cwd(), 'test-data', 'private');
    const [testData, secondFactorConfig] = await Promise.all([
      loadPhoneChangeTestData(path.join(privateDataDirectory, 'phone-change.json')),
      loadSecondFactorConfig(path.join(privateDataDirectory, 'second-factor.json')),
    ]);

    const codeInbox = new SecondFactorCodeInboxApi(apiRequest);
    const secondFactor = new SecondFactorChallengeApi(codeInbox, secondFactorConfig);
    const profileMobilePhone = new ProfileMobilePhoneApi(apiRequest);

    await use(new PhoneChangeApi(apiRequest, testData, profileMobilePhone, secondFactor));
  },

  ownAccountTransferApi: async ({ apiRequest }, use) => {
    await use(new OwnAccountTransferApi(apiRequest));
  },

  creditCardOtherAmountPaymentApi: async ({ apiRequest }, use) => {
    await use(new CreditCardOtherAmountPaymentApi(apiRequest));
  },

  withdrawalOrderApi: async ({ apiRequest }, use) => {
    const privateDataDirectory = path.resolve(process.cwd(), 'test-data', 'private');
    const secondFactorConfig = await loadSecondFactorConfig(path.join(privateDataDirectory, 'second-factor.json'));
    const codeInbox = new SecondFactorCodeInboxApi(apiRequest);
    const secondFactor = new SecondFactorChallengeApi(codeInbox, secondFactorConfig);
    const profileMobilePhone = new ProfileMobilePhoneApi(apiRequest);

    await use(new WithdrawalOrderApi(apiRequest, profileMobilePhone, secondFactor));
  },

  termsAndConditionsApi: async ({ apiRequest }, use) => {
    await use(new TermsAndConditionsApi(apiRequest));
  },
});

export { expect };
