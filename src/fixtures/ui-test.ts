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
import { CommitentAccountOpeningPage } from '../pages/investments/commitent-account-opening.page';
import { FixedTermsPage } from '../pages/investments/fixed-terms.page';
import { InsurancePage } from '../pages/insurance/insurance.page';
import { MessagesPage } from '../pages/messages/messages.page';
import { CancellationPage } from '../pages/mis-products/cancellation.page';
import { CreditCardPaymentPage } from '../pages/cards/credit-card-payment.page';
import { IdentityValidationPage } from '../pages/registration/identity-validation.page';
import { StartPage } from '../pages/start-page/start.page';
import { OwnAccountTransferPage } from '../pages/transfers/own-account-transfer.page';
import { EcheqPage } from '../pages/accounts/echeq.page';
import { AccountsPage } from '../pages/accounts/accounts.page';
import { PaymentServicesPage } from '../pages/payments/payment-services.page';
import { ServiceAdhesionPage } from '../pages/payments/service-adhesion.page';
import { PaymentReceiptsPage } from '../pages/payments/payment-receipts.page';
import { SecuritySealPage } from '../pages/profile/security-seal.page';
import { ProductDocumentsPage } from '../pages/documents/product-documents.page';
import { PaymentServicesPreparationWorkflow } from '../workflows/payments/payment-services-preparation.workflow';
import { LoanRequestPage } from '../pages/loans/loan-request.page';
import { AccountAliasPage } from '../pages/accounts/account-alias.page';
import { AccountMovementsPage } from '../pages/accounts/account-movements.page';
import { OpinionSurveyPage } from '../pages/feedback/opinion-survey.page';
import { CreditCardsPage } from '../pages/cards/credit-cards.page';
import { CreditCardTrackingPage } from '../pages/cards/credit-card-tracking.page';
import { NewCreditCardRequestPage } from '../pages/cards/new-credit-card-request.page';
import { HomePage } from '../pages/home/home.page';
import { AddContactPage } from '../pages/transfers/add-contact.page';
import { ContactAgendaPage } from '../pages/transfers/contact-agenda.page';
import { CredinRequestPage } from '../pages/transfers/credin-request.page';
import { TransferFormPage } from '../pages/transfers/transfer-form.page';
import { TransferHistoryPage } from '../pages/transfers/transfer-history.page';
import { UtilLoggerPage } from '../pages/transfers/util-logger.page';
import { ProductWidgetsApi } from '../api/accounts/product-widgets.api';
import { SmsOperationOtpApi } from '../api/authentication/sms-operation-otp.api';
import { ThirdPartyTransfersApi } from '../api/transfers/third-party-transfers.api';
import { ThirdPartyTransferWorkflow } from '../workflows/transfers/third-party-transfer.workflow';
import { DebinSellerPage } from '../pages/payments/debin-seller.page';
import { SalaryAdvancePage } from '../pages/accounts/salary-advance.page';
import {
  loadCreditCardTotalPaymentUiData,
  type CreditCardTotalPaymentUiData,
} from '../test-data/credit-card-total-payment-ui-data';
import { loadOwnAccountTransferUiData, type OwnAccountTransferUiData } from '../test-data/own-account-transfer-ui-data';
import { loadAccountAliasUiData, type AccountAliasUiData } from '../test-data/account-alias-ui-data';
import { loadAccountMovementsUiData, type AccountMovementsUiData } from '../test-data/account-movements-ui-data';
import { loadCreditCardDetailUiData, type CreditCardDetailUiData } from '../test-data/credit-card-detail-ui-data';
import { loadContactSearchUiData, type ContactSearchUiData } from '../test-data/contact-search-ui-data';
import { loadOriginAccountUiData, type OriginAccountUiData } from '../test-data/origin-account-ui-data';
import {
  loadForeignCurrencyPurchaseUiData,
  type ForeignCurrencyPurchaseUiData,
} from '../test-data/foreign-currency-purchase-ui-data';
import { expect, test as testWithData } from './test-case.fixture';
import { UpsErrorTracker } from './ups-error-tracker';

interface UiFixtures {
  readonly accountsPage: AccountsPage;
  readonly addContactPage: AddContactPage;
  readonly paymentReceiptsPage: PaymentReceiptsPage;
  readonly paymentServicesPage: PaymentServicesPage;
  readonly paymentServicesPreparationWorkflow: PaymentServicesPreparationWorkflow;
  readonly productDocumentsPage: ProductDocumentsPage;
  readonly securitySealPage: SecuritySealPage;
  readonly serviceAdhesionPage: ServiceAdhesionPage;
  readonly loanRequestPage: LoanRequestPage;
  readonly contactAgendaPage: ContactAgendaPage;
  readonly credinRequestPage: CredinRequestPage;
  readonly smsOperationOtpApi: SmsOperationOtpApi;
  readonly thirdPartyTransferWorkflow: ThirdPartyTransferWorkflow;
  readonly transferFormPage: TransferFormPage;
  readonly transferHistoryPage: TransferHistoryPage;
  readonly utilLoggerPage: UtilLoggerPage;
  readonly commitentAccountOpeningPage: CommitentAccountOpeningPage;
  readonly fixedTermsPage: FixedTermsPage;
  readonly contactSearchUiData: ContactSearchUiData;
  readonly creditCardDetailUiData: CreditCardDetailUiData;
  readonly creditCardsPage: CreditCardsPage;
  readonly creditCardTrackingPage: CreditCardTrackingPage;
  readonly newCreditCardRequestPage: NewCreditCardRequestPage;
  readonly debinSellerPage: DebinSellerPage;
  readonly homePage: HomePage;
  readonly originAccountUiData: OriginAccountUiData;
  readonly salaryAdvancePage: SalaryAdvancePage;
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

  addContactPage: async ({ page }, use) => {
    await use(new AddContactPage(page));
  },

  paymentReceiptsPage: async ({ page }, use) => {
    await use(new PaymentReceiptsPage(page));
  },

  paymentServicesPage: async ({ page }, use) => {
    await use(new PaymentServicesPage(page));
  },

  paymentServicesPreparationWorkflow: async ({ paymentServicesPage, serviceAdhesionPage, smsOperationOtpApi }, use) => {
    await use(new PaymentServicesPreparationWorkflow(paymentServicesPage, serviceAdhesionPage, smsOperationOtpApi));
  },

  productDocumentsPage: async ({ page }, use) => {
    await use(new ProductDocumentsPage(page));
  },

  securitySealPage: async ({ page }, use) => {
    await use(new SecuritySealPage(page));
  },

  serviceAdhesionPage: async ({ page }, use) => {
    await use(new ServiceAdhesionPage(page));
  },

  accountsPage: async ({ page }, use) => {
    await use(new AccountsPage(page));
  },

  loanRequestPage: async ({ page }, use) => {
    await use(new LoanRequestPage(page));
  },

  contactAgendaPage: async ({ page }, use) => {
    await use(new ContactAgendaPage(page));
  },

  credinRequestPage: async ({ page }, use) => {
    await use(new CredinRequestPage(page));
  },

  transferFormPage: async ({ page }, use) => {
    await use(new TransferFormPage(page));
  },

  transferHistoryPage: async ({ page }, use) => {
    await use(new TransferHistoryPage(page));
  },

  utilLoggerPage: async ({ page }, use) => {
    await use(new UtilLoggerPage(page));
  },

  smsOperationOtpApi: async ({ profileMobilePhoneApi, secondFactorChallengeApi, uiApiRequest }, use) => {
    await use(new SmsOperationOtpApi(uiApiRequest, profileMobilePhoneApi, secondFactorChallengeApi));
  },

  thirdPartyTransferWorkflow: async ({ smsOperationOtpApi, uiApiRequest }, use) => {
    await use(
      new ThirdPartyTransferWorkflow(
        new ThirdPartyTransfersApi(uiApiRequest, smsOperationOtpApi),
        new ProductWidgetsApi(uiApiRequest),
      ),
    );
  },

  commitentAccountOpeningPage: async ({ page }, use) => {
    await use(new CommitentAccountOpeningPage(page));
  },

  fixedTermsPage: async ({ page }, use) => {
    await use(new FixedTermsPage(page));
  },

  contactSearchUiData: async ({ testCaseId }, use) => {
    await use(await loadContactSearchUiData(testCaseId));
  },

  creditCardDetailUiData: async ({ testCaseId }, use) => {
    await use(await loadCreditCardDetailUiData(testCaseId));
  },

  creditCardsPage: async ({ page }, use) => {
    await use(new CreditCardsPage(page));
  },

  creditCardTrackingPage: async ({ page }, use) => {
    await use(new CreditCardTrackingPage(page));
  },

  newCreditCardRequestPage: async ({ page }, use) => {
    await use(new NewCreditCardRequestPage(page));
  },

  debinSellerPage: async ({ page }, use) => {
    await use(new DebinSellerPage(page));
  },

  homePage: async ({ page }, use) => {
    await use(new HomePage(page));
  },

  originAccountUiData: async ({ testCaseId }, use) => {
    await use(await loadOriginAccountUiData(testCaseId));
  },

  salaryAdvancePage: async ({ page }, use) => {
    await use(new SalaryAdvancePage(page));
  },

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
