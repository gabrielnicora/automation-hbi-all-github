import path from 'node:path';
import { request as playwrightRequest, type APIRequestContext } from '@playwright/test';
import { loadEnvironmentConfig } from '../config/environment';
import { loadSecondFactorConfig } from '../config/second-factor';
import { SecondFactorChallengeApi } from '../api/authentication/second-factor-challenge.api';
import { SecondFactorCodeInboxApi } from '../api/authentication/second-factor-code-inbox.api';
import { LoginApi } from '../api/authentication/login.api';
import { TermsAndConditionsApi } from '../api/home/terms-and-conditions.api';
import { CommitentAccountApi } from '../api/investments/commitent-account.api';
import { DollarExchangeApi } from '../api/investments/dollar-exchange.api';
import { InvestmentFundsApi } from '../api/investments/investment-funds.api';
import { TraditionalFixedTermApi } from '../api/investments/traditional-fixed-term.api';
import { ReceivedMessagesApi } from '../api/messages/received-messages.api';
import { DebinGenerationApi } from '../api/payments/debin-generation.api';
import { DebinOperationsApi } from '../api/payments/debin-operations.api';
import { PaymentLinkSyncApi } from '../api/payments/payment-link-sync.api';
import { PaymentReceiptsApi } from '../api/payments/payment-receipts.api';
import { TaxServicesApi } from '../api/payments/tax-services.api';
import { PhoneChangeApi } from '../api/profile/phone-change.api';
import { ProfileMobilePhoneApi } from '../api/profile/profile-mobile-phone.api';
import { NotificationPreferencesApi } from '../api/profile/notification-preferences.api';
import { CurrentOnlineProceduresApi } from '../api/procedures/current-online-procedures.api';
import { CredinApi } from '../api/transfers/credin.api';
import { OwnAccountTransferApi } from '../api/transfers/own-account-transfer.api';
import { ThirdPartyTransfersApi } from '../api/transfers/third-party-transfers.api';
import { TransferHistoryApi } from '../api/transfers/transfer-history.api';
import { ThirdPartyTransferWorkflow } from '../workflows/transfers/third-party-transfer.workflow';
import { CreditCardOtherAmountPaymentApi } from '../api/cards/credit-card-other-amount-payment.api';
import { CreditCardApplicationApi } from '../api/cards/credit-card-application.api';
import { CreditCardPaymentsApi } from '../api/cards/credit-card-payments.api';
import { AccountTransferSearchApi } from '../api/accounts/account-transfer-search.api';
import { AdheredAccountsApi } from '../api/accounts/adhered-accounts.api';
import { ProductWidgetsApi } from '../api/accounts/product-widgets.api';
import { SalaryAdvanceApi } from '../api/accounts/salary-advance.api';
import { SmsOperationOtpApi } from '../api/authentication/sms-operation-otp.api';
import { EcheqListApi } from '../api/accounts/echeq-list.api';
import { EcheqOperationsApi } from '../api/accounts/echeq-operations.api';
import { WithdrawalOrderApi } from '../api/accounts/withdrawal-order.api';
import { ReportingApiRequest } from '../api/reporting-api-request';
import { loadPhoneChangeTestData } from '../test-data/phone-change-data';
import { expect, test as testWithData } from './test-case.fixture';

interface ApiFixtures {
  readonly accountTransferSearchApi: AccountTransferSearchApi;
  readonly adheredAccountsApi: AdheredAccountsApi;
  readonly apiContext: APIRequestContext;
  readonly apiRequest: ReportingApiRequest;
  readonly commitentAccountApi: CommitentAccountApi;
  readonly currentOnlineProceduresApi: CurrentOnlineProceduresApi;
  readonly debinGenerationApi: DebinGenerationApi;
  readonly debinOperationsApi: DebinOperationsApi;
  readonly dollarExchangeApi: DollarExchangeApi;
  readonly echeqListApi: EcheqListApi;
  readonly echeqOperationsApi: EcheqOperationsApi;
  readonly investmentFundsApi: InvestmentFundsApi;
  readonly loginApi: LoginApi;
  readonly notificationPreferencesApi: NotificationPreferencesApi;
  readonly phoneChangeApi: PhoneChangeApi;
  readonly productWidgetsApi: ProductWidgetsApi;
  readonly receivedMessagesApi: ReceivedMessagesApi;
  readonly salaryAdvanceApi: SalaryAdvanceApi;
  readonly smsOperationOtpApi: SmsOperationOtpApi;
  readonly taxServicesApi: TaxServicesApi;
  readonly ownAccountTransferApi: OwnAccountTransferApi;
  readonly paymentLinkSyncApi: PaymentLinkSyncApi;
  readonly paymentReceiptsApi: PaymentReceiptsApi;
  readonly credinApi: CredinApi;
  readonly creditCardApplicationApi: CreditCardApplicationApi;
  readonly creditCardOtherAmountPaymentApi: CreditCardOtherAmountPaymentApi;
  readonly creditCardPaymentsApi: CreditCardPaymentsApi;
  readonly termsAndConditionsApi: TermsAndConditionsApi;
  readonly thirdPartyTransferWorkflow: ThirdPartyTransferWorkflow;
  readonly thirdPartyTransfersApi: ThirdPartyTransfersApi;
  readonly transferHistoryApi: TransferHistoryApi;
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

  accountTransferSearchApi: async ({ apiRequest }, use) => {
    await use(new AccountTransferSearchApi(apiRequest));
  },

  echeqListApi: async ({ apiRequest }, use) => {
    await use(new EcheqListApi(apiRequest));
  },

  productWidgetsApi: async ({ apiRequest }, use) => {
    await use(new ProductWidgetsApi(apiRequest));
  },

  smsOperationOtpApi: async ({ apiRequest }, use) => {
    const privateDataDirectory = path.resolve(process.cwd(), 'test-data', 'private');
    const secondFactorConfig = await loadSecondFactorConfig(path.join(privateDataDirectory, 'second-factor.json'));
    const secondFactor = new SecondFactorChallengeApi(new SecondFactorCodeInboxApi(apiRequest), secondFactorConfig);

    await use(new SmsOperationOtpApi(apiRequest, new ProfileMobilePhoneApi(apiRequest), secondFactor));
  },

  echeqOperationsApi: async ({ apiRequest, smsOperationOtpApi }, use) => {
    await use(new EcheqOperationsApi(apiRequest, smsOperationOtpApi));
  },

  adheredAccountsApi: async ({ apiRequest, smsOperationOtpApi }, use) => {
    await use(new AdheredAccountsApi(apiRequest, smsOperationOtpApi));
  },

  salaryAdvanceApi: async ({ apiRequest, smsOperationOtpApi }, use) => {
    await use(new SalaryAdvanceApi(apiRequest, smsOperationOtpApi));
  },

  thirdPartyTransfersApi: async ({ apiRequest, smsOperationOtpApi }, use) => {
    await use(new ThirdPartyTransfersApi(apiRequest, smsOperationOtpApi));
  },

  thirdPartyTransferWorkflow: async ({ productWidgetsApi, thirdPartyTransfersApi }, use) => {
    await use(new ThirdPartyTransferWorkflow(thirdPartyTransfersApi, productWidgetsApi));
  },

  credinApi: async ({ apiRequest, smsOperationOtpApi }, use) => {
    await use(new CredinApi(apiRequest, smsOperationOtpApi));
  },

  transferHistoryApi: async ({ apiRequest }, use) => {
    await use(new TransferHistoryApi(apiRequest));
  },

  paymentLinkSyncApi: async ({ apiRequest }, use) => {
    await use(new PaymentLinkSyncApi(apiRequest));
  },

  debinOperationsApi: async ({ apiRequest, smsOperationOtpApi }, use) => {
    await use(new DebinOperationsApi(apiRequest, smsOperationOtpApi));
  },

  paymentReceiptsApi: async ({ apiRequest }, use) => {
    await use(new PaymentReceiptsApi(apiRequest));
  },

  taxServicesApi: async ({ apiRequest, smsOperationOtpApi }, use) => {
    await use(new TaxServicesApi(apiRequest, smsOperationOtpApi));
  },

  debinGenerationApi: async ({ apiRequest }, use) => {
    await use(new DebinGenerationApi(apiRequest));
  },

  traditionalFixedTermApi: async ({ apiRequest }, use) => {
    await use(new TraditionalFixedTermApi(apiRequest));
  },

  investmentFundsApi: async ({ apiRequest }, use) => {
    await use(new InvestmentFundsApi(apiRequest));
  },

  commitentAccountApi: async ({ apiRequest }, use) => {
    await use(new CommitentAccountApi(apiRequest));
  },

  dollarExchangeApi: async ({ apiRequest }, use) => {
    await use(new DollarExchangeApi(apiRequest));
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

  creditCardPaymentsApi: async ({ apiRequest }, use) => {
    await use(new CreditCardPaymentsApi(apiRequest));
  },

  creditCardApplicationApi: async ({ apiRequest, smsOperationOtpApi }, use) => {
    await use(new CreditCardApplicationApi(apiRequest, smsOperationOtpApi));
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
