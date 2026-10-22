<!-- Generado por `pnpm run agent:index`. No editar a mano. -->

# Fixtures

Los tests importan `test` y `expect` de `src/fixtures/api-test.ts` o `src/fixtures/ui-test.ts`.
Ambas entradas incluyen las fixtures de `test-case.fixture.ts`, que dependen de `testCaseId`
(`test.use({ testCaseId })`). Las fixtures automáticas se aplican solas a cada test UI.
Además están las nativas de Playwright (`page`, `context`, `request`).

## `src/fixtures/test-case.fixture.ts`

| Fixture | Tipo | Definido en |
| --- | --- | --- |
| `accountPairData` | `AccountPairData` | `src/test-data/account-selection-data.ts` |
| `creditCardOtherAmountPaymentData` | `CreditCardOtherAmountPaymentData` | `src/test-data/credit-card-other-amount-payment-data.ts` |
| `creditCardPaymentData` | `CreditCardPaymentData` | `src/test-data/account-selection-data.ts` |
| `debinGenerationData` | `DebinGenerationData` | `src/test-data/debin-generation-data.ts` |
| `debitCardData` | `DebitCardData` | `src/test-data/account-selection-data.ts` |
| `echeqEndorsementData` | `EcheqEndorsementData` | `src/test-data/account-selection-data.ts` |
| `loginCredentials` | `LoginCredentials` | `src/test-data/login-credentials.ts` |
| `optionalOriginAccountData` | `OptionalOriginAccountData` | `src/test-data/account-selection-data.ts` |
| `originAccountData` | `OriginAccountData` | `src/test-data/account-selection-data.ts` |
| `ownAccountTransferData` | `OwnAccountTransferData` | `src/test-data/own-account-transfer-data.ts` |
| `secondUserCredentials` | `LoginCredentials` | `src/test-data/login-credentials.ts` |
| `testCaseId` | `string` | — |
| `traditionalFixedTermData` | `TraditionalFixedTermData` | `src/test-data/traditional-fixed-term-data.ts` |
| `transferCaseData` | `TransferCaseData` | `src/test-data/transfer-case-data.ts` |
| `withdrawalOrderData` | `WithdrawalOrderData` | `src/test-data/withdrawal-order-data.ts` |

## `src/fixtures/api-test.ts`

| Fixture | Tipo | Definido en |
| --- | --- | --- |
| `accountTransferSearchApi` | `AccountTransferSearchApi` | `src/api/accounts/account-transfer-search.api.ts` |
| `adheredAccountsApi` | `AdheredAccountsApi` | `src/api/accounts/adhered-accounts.api.ts` |
| `apiContext` | `APIRequestContext` | `@playwright/test` |
| `apiRequest` | `ReportingApiRequest` | `src/api/reporting-api-request.ts` |
| `commitentAccountApi` | `CommitentAccountApi` | `src/api/investments/commitent-account.api.ts` |
| `credinApi` | `CredinApi` | `src/api/transfers/credin.api.ts` |
| `creditCardApplicationApi` | `CreditCardApplicationApi` | `src/api/cards/credit-card-application.api.ts` |
| `creditCardOtherAmountPaymentApi` | `CreditCardOtherAmountPaymentApi` | `src/api/cards/credit-card-other-amount-payment.api.ts` |
| `creditCardPaymentsApi` | `CreditCardPaymentsApi` | `src/api/cards/credit-card-payments.api.ts` |
| `currentOnlineProceduresApi` | `CurrentOnlineProceduresApi` | `src/api/procedures/current-online-procedures.api.ts` |
| `debinGenerationApi` | `DebinGenerationApi` | `src/api/payments/debin-generation.api.ts` |
| `debinOperationsApi` | `DebinOperationsApi` | `src/api/payments/debin-operations.api.ts` |
| `dollarExchangeApi` | `DollarExchangeApi` | `src/api/investments/dollar-exchange.api.ts` |
| `echeqListApi` | `EcheqListApi` | `src/api/accounts/echeq-list.api.ts` |
| `echeqOperationsApi` | `EcheqOperationsApi` | `src/api/accounts/echeq-operations.api.ts` |
| `investmentFundsApi` | `InvestmentFundsApi` | `src/api/investments/investment-funds.api.ts` |
| `loginApi` | `LoginApi` | `src/api/authentication/login.api.ts` |
| `notificationPreferencesApi` | `NotificationPreferencesApi` | `src/api/profile/notification-preferences.api.ts` |
| `ownAccountTransferApi` | `OwnAccountTransferApi` | `src/api/transfers/own-account-transfer.api.ts` |
| `paymentLinkSyncApi` | `PaymentLinkSyncApi` | `src/api/payments/payment-link-sync.api.ts` |
| `paymentReceiptsApi` | `PaymentReceiptsApi` | `src/api/payments/payment-receipts.api.ts` |
| `phoneChangeApi` | `PhoneChangeApi` | `src/api/profile/phone-change.api.ts` |
| `productWidgetsApi` | `ProductWidgetsApi` | `src/api/accounts/product-widgets.api.ts` |
| `receivedMessagesApi` | `ReceivedMessagesApi` | `src/api/messages/received-messages.api.ts` |
| `salaryAdvanceApi` | `SalaryAdvanceApi` | `src/api/accounts/salary-advance.api.ts` |
| `smsOperationOtpApi` | `SmsOperationOtpApi` | `src/api/authentication/sms-operation-otp.api.ts` |
| `taxServicesApi` | `TaxServicesApi` | `src/api/payments/tax-services.api.ts` |
| `termsAndConditionsApi` | `TermsAndConditionsApi` | `src/api/home/terms-and-conditions.api.ts` |
| `thirdPartyTransfersApi` | `ThirdPartyTransfersApi` | `src/api/transfers/third-party-transfers.api.ts` |
| `thirdPartyTransferWorkflow` | `ThirdPartyTransferWorkflow` | `src/workflows/transfers/third-party-transfer.workflow.ts` |
| `traditionalFixedTermApi` | `TraditionalFixedTermApi` | `src/api/investments/traditional-fixed-term.api.ts` |
| `transferHistoryApi` | `TransferHistoryApi` | `src/api/transfers/transfer-history.api.ts` |
| `withdrawalOrderApi` | `WithdrawalOrderApi` | `src/api/accounts/withdrawal-order.api.ts` |

## `src/fixtures/ui-test.ts`

| Fixture | Tipo | Definido en |
| --- | --- | --- |
| `accountAliasPage` | `AccountAliasPage` | `src/pages/accounts/account-alias.page.ts` |
| `accountAliasUiData` | `AccountAliasUiData` | `src/test-data/account-alias-ui-data.ts` |
| `accountMovementsPage` | `AccountMovementsPage` | `src/pages/accounts/account-movements.page.ts` |
| `accountMovementsUiData` | `AccountMovementsUiData` | `src/test-data/account-movements-ui-data.ts` |
| `accountsPage` | `AccountsPage` | `src/pages/accounts/accounts.page.ts` |
| `addContactPage` | `AddContactPage` | `src/pages/transfers/add-contact.page.ts` |
| `appointmentsPage` | `AppointmentsPage` | `src/pages/procedures/appointments.page.ts` |
| `cancellationPage` | `CancellationPage` | `src/pages/mis-products/cancellation.page.ts` |
| `changeAddressPage` | `ChangeAddressPage` | `src/pages/profile/change-address.page.ts` |
| `commitentAccountOpeningPage` | `CommitentAccountOpeningPage` | `src/pages/investments/commitent-account-opening.page.ts` |
| `contactAgendaPage` | `ContactAgendaPage` | `src/pages/transfers/contact-agenda.page.ts` |
| `contactSearchUiData` | `ContactSearchUiData` | `src/test-data/contact-search-ui-data.ts` |
| `credinRequestPage` | `CredinRequestPage` | `src/pages/transfers/credin-request.page.ts` |
| `creditCardDetailUiData` | `CreditCardDetailUiData` | `src/test-data/credit-card-detail-ui-data.ts` |
| `creditCardPaymentPage` | `CreditCardPaymentPage` | `src/pages/cards/credit-card-payment.page.ts` |
| `creditCardsPage` | `CreditCardsPage` | `src/pages/cards/credit-cards.page.ts` |
| `creditCardTotalPaymentUiData` | `CreditCardTotalPaymentUiData` | `src/test-data/credit-card-total-payment-ui-data.ts` |
| `creditCardTrackingPage` | `CreditCardTrackingPage` | `src/pages/cards/credit-card-tracking.page.ts` |
| `debinSellerPage` | `DebinSellerPage` | `src/pages/payments/debin-seller.page.ts` |
| `echeqPage` | `EcheqPage` | `src/pages/accounts/echeq.page.ts` |
| `fixedTermsPage` | `FixedTermsPage` | `src/pages/investments/fixed-terms.page.ts` |
| `foreignCurrencyPurchasePage` | `ForeignCurrencyPurchasePage` | `src/pages/investments/foreign-currency-purchase.page.ts` |
| `foreignCurrencyPurchaseUiData` | `ForeignCurrencyPurchaseUiData` | `src/test-data/foreign-currency-purchase-ui-data.ts` |
| `homePage` | `HomePage` | `src/pages/home/home.page.ts` |
| `identityValidationPage` | `IdentityValidationPage` | `src/pages/registration/identity-validation.page.ts` |
| `insurancePage` | `InsurancePage` | `src/pages/insurance/insurance.page.ts` |
| `investmentFundsPage` | `InvestmentFundsPage` | `src/pages/investments/investment-funds.page.ts` |
| `loanRequestPage` | `LoanRequestPage` | `src/pages/loans/loan-request.page.ts` |
| `loginApi` | `LoginApi` | `src/api/authentication/login.api.ts` |
| `loginPage` | `LoginPage` | `src/pages/authentication/login.page.ts` |
| `messagesPage` | `MessagesPage` | `src/pages/messages/messages.page.ts` |
| `newCreditCardRequestPage` | `NewCreditCardRequestPage` | `src/pages/cards/new-credit-card-request.page.ts` |
| `opinionSurveyHandler` (automática) | `void` | — |
| `originAccountUiData` | `OriginAccountUiData` | `src/test-data/origin-account-ui-data.ts` |
| `ownAccountTransferPage` | `OwnAccountTransferPage` | `src/pages/transfers/own-account-transfer.page.ts` |
| `ownAccountTransferUiData` | `OwnAccountTransferUiData` | `src/test-data/own-account-transfer-ui-data.ts` |
| `paymentReceiptsPage` | `PaymentReceiptsPage` | `src/pages/payments/payment-receipts.page.ts` |
| `paymentServicesPage` | `PaymentServicesPage` | `src/pages/payments/payment-services.page.ts` |
| `paymentServicesPreparationWorkflow` | `PaymentServicesPreparationWorkflow` | `src/workflows/payments/payment-services-preparation.workflow.ts` |
| `productDocumentsPage` | `ProductDocumentsPage` | `src/pages/documents/product-documents.page.ts` |
| `profileMobilePhoneApi` | `ProfileMobilePhoneApi` | `src/api/profile/profile-mobile-phone.api.ts` |
| `salaryAdvancePage` | `SalaryAdvancePage` | `src/pages/accounts/salary-advance.page.ts` |
| `secondFactorChallengeApi` | `SecondFactorChallengeApi` | `src/api/authentication/second-factor-challenge.api.ts` |
| `securitySealPage` | `SecuritySealPage` | `src/pages/profile/security-seal.page.ts` |
| `serviceAdhesionPage` | `ServiceAdhesionPage` | `src/pages/payments/service-adhesion.page.ts` |
| `smsOperationOtpApi` | `SmsOperationOtpApi` | `src/api/authentication/sms-operation-otp.api.ts` |
| `startPage` | `StartPage` | `src/pages/start-page/start.page.ts` |
| `thirdPartyTransferWorkflow` | `ThirdPartyTransferWorkflow` | `src/workflows/transfers/third-party-transfer.workflow.ts` |
| `transferFormPage` | `TransferFormPage` | `src/pages/transfers/transfer-form.page.ts` |
| `transferHistoryPage` | `TransferHistoryPage` | `src/pages/transfers/transfer-history.page.ts` |
| `uiApiRequest` | `ReportingApiRequest` | `src/api/reporting-api-request.ts` |
| `upsErrorTracking` (automática) | `void` | — |
| `utilLoggerPage` | `UtilLoggerPage` | `src/pages/transfers/util-logger.page.ts` |
