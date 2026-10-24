<!-- Generado por `pnpm run agent:index`. No editar a mano. -->

# Datos de prueba

Campos con valor de cada caso en `test-data/private/bersa.json` (sólo nombres; los valores
se leen con los loaders). Un campo ausente es `null`, vacío o `sinDatos`. Reglas en
`test-data/AGENTS.md`.

## Loaders

- `src/test-data/account-alias-ui-data.ts`: `loadAccountAliasUiData(testCaseId: string): Promise<AccountAliasUiData>` → fixture `accountAliasUiData`
- `src/test-data/account-movements-ui-data.ts`: `loadAccountMovementsUiData(testCaseId: string): Promise<AccountMovementsUiData>` → fixture `accountMovementsUiData`
- `src/test-data/account-selection-data.ts`: `loadOriginAccountData(testCaseId: string): Promise<OriginAccountData>` → fixture `originAccountData`
- `src/test-data/account-selection-data.ts`: `loadAccountPairData(testCaseId: string): Promise<AccountPairData>` → fixture `accountPairData`
- `src/test-data/account-selection-data.ts`: `loadDebitCardData(testCaseId: string): Promise<DebitCardData>` → fixture `debitCardData`
- `src/test-data/account-selection-data.ts`: `loadCreditCardPaymentData(testCaseId: string): Promise<CreditCardPaymentData>` → fixture `creditCardPaymentData`
- `src/test-data/account-selection-data.ts`: `loadOptionalOriginAccountData(testCaseId: string): Promise<OptionalOriginAccountData>` → fixture `optionalOriginAccountData`
- `src/test-data/account-selection-data.ts`: `loadEcheqEndorsementData(testCaseId: string): Promise<EcheqEndorsementData>` → fixture `echeqEndorsementData`
- `src/test-data/bersa-test-case.ts`: `loadBersaTestCaseRow(testCaseId: string): Promise<BersaTestCaseRow>`
- `src/test-data/bersa-test-case.ts`: `loadBersaCredentialProfile(credentialsRef: string): Promise<BersaTestCaseRow>`
- `src/test-data/case-fields.ts`: `loadCaseRow(testCaseId: string): Promise<CaseRow>`
- `src/test-data/contact-search-ui-data.ts`: `loadContactSearchUiData(testCaseId: string): Promise<ContactSearchUiData>` → fixture `contactSearchUiData`
- `src/test-data/credit-card-detail-ui-data.ts`: `loadCreditCardDetailUiData(testCaseId: string): Promise<CreditCardDetailUiData>` → fixture `creditCardDetailUiData`
- `src/test-data/credit-card-other-amount-payment-data.ts`: `loadCreditCardOtherAmountPaymentData(testCaseId: string): Promise<CreditCardOtherAmountPaymentData>` → fixture `creditCardOtherAmountPaymentData`
- `src/test-data/credit-card-total-payment-ui-data.ts`: `loadCreditCardTotalPaymentUiData(testCaseId: string): Promise<CreditCardTotalPaymentUiData>` → fixture `creditCardTotalPaymentUiData`
- `src/test-data/debin-generation-data.ts`: `loadDebinGenerationData(testCaseId: string): Promise<DebinGenerationData>` → fixture `debinGenerationData`
- `src/test-data/fixed-access.ts`: `loadFixedAccess(): Promise<FixedAccess | undefined>`
- `src/test-data/foreign-currency-purchase-ui-data.ts`: `loadForeignCurrencyPurchaseUiData(testCaseId: string): Promise<ForeignCurrencyPurchaseUiData>` → fixture `foreignCurrencyPurchaseUiData`
- `src/test-data/login-credentials.ts`: `loadLoginCredentials(testCaseId: string, credentialsRef?: string): Promise<LoginCredentials>` → fixture `loginCredentials`, `secondUserCredentials`
- `src/test-data/login-credentials.ts`: `loadSecondUserCredentials(testCaseId: string, credentialsRef?: string): Promise<LoginCredentials>` → fixture `loginCredentials`, `secondUserCredentials`
- `src/test-data/origin-account-ui-data.ts`: `loadOriginAccountUiData(testCaseId: string): Promise<OriginAccountUiData>` → fixture `originAccountUiData`
- `src/test-data/own-account-transfer-data.ts`: `loadOwnAccountTransferData(testCaseId: string): Promise<OwnAccountTransferData>` → fixture `ownAccountTransferData`
- `src/test-data/own-account-transfer-ui-data.ts`: `loadOwnAccountTransferUiData(testCaseId: string): Promise<OwnAccountTransferUiData>` → fixture `ownAccountTransferUiData`
- `src/test-data/phone-change-data.ts`: `loadPhoneChangeTestData(filePath: string): Promise<PhoneChangeTestData>`
- `src/test-data/traditional-fixed-term-data.ts`: `loadTraditionalFixedTermData(testCaseId: string): Promise<TraditionalFixedTermData>` → fixture `traditionalFixedTermData`
- `src/test-data/transfer-case-data.ts`: `loadTransferCaseData(testCaseId: string): Promise<TransferCaseData>` → fixture `transferCaseData`
- `src/test-data/withdrawal-order-data.ts`: `loadWithdrawalOrderData(testCaseId: string): Promise<WithdrawalOrderData>` → fixture `withdrawalOrderData`

## Casos

| Caso | Campos con valor | Tests |
| --- | --- | --- |
| TC0001BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/profile/should-change-phone.api.spec.ts` |
| TC0002BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TC0004BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/profile/should-change-address.ui.spec.ts` |
| TC0006BERSA | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, user | `api/accounts/should-adhere-new-destination.api.spec.ts` |
| TC0008BERSA | ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-schedule-transfers.api.spec.ts` |
| TC0009BERSA | ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/investments/should-exchange-dollars.api.spec.ts` |
| TC0012BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/payments/should-list-debin-history.api.spec.ts` |
| TC0013BERSA | cbuDestino, ctaOrigen, cuitDestino, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/payments/should-generate-debin.api.spec.ts` |
| TC0014BERSA | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-generate-credin.api.spec.ts` |
| TC0015BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/payments/should-subscribe-recurring-debin.api.spec.ts` |
| TC0017BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/authentication/should-login-and-capture-bearer.api.spec.ts`<br>`ui/authentication/should-login-and-capture-bearer.ui.spec.ts` |
| TC0025BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TC0026BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/accounts/should-operate-echeqs.ui.spec.ts` |
| TC0027BERSA | ctaOrigen, idBanco, idSeccion, pass, user | `api/accounts/should-generate-withdrawal-order.api.spec.ts` |
| TC0032BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/cards/should-consult-credit-cards.ui.spec.ts` |
| TC0033BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/cards/should-consult-credit-cards.ui.spec.ts` |
| TC0035BERSA | idBanco, idSeccion, pass, user | `ui/profile/should-change-security-seal.ui.spec.ts` |
| TC0040BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/accounts/should-show-account-movement-detail.ui.spec.ts` |
| TC0041BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/accounts/should-transfer-to-scheduled-contact-and-find-movement.ui.spec.ts` |
| TC0042BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/investments/should-create-traditional-fixed-term.api.spec.ts` |
| TC0043BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TC0044BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/investments/should-query-fixed-term-rates.ui.spec.ts` |
| TC0045BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/investments/should-query-fixed-term-rates.ui.spec.ts` |
| TC0046BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TC0047BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TC0048BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/authentication/should-recover-username.ui.spec.ts` |
| TC0052BERSA | idBanco, idSeccion, pass, user | `ui/procedures/should-open-online-appointments.ui.spec.ts` |
| TC0062BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/cards/should-consult-credit-cards.ui.spec.ts` |
| TC0069BERSA | ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/investments/should-exchange-dollars.api.spec.ts` |
| TC0070BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/payments/should-accept-pending-debin.api.spec.ts` |
| TC0071BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TC0072BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TC0074BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/payments/should-adhere-tax-service.api.spec.ts` |
| TC0075BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/mis-products/should-open-cancellation-page.ui.spec.ts` |
| TC0077BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/documents/should-download-product-contract.ui.spec.ts` |
| TC0078BERSA | idBanco, idSeccion, pass, user | `api/procedures/should-list-current-online-procedures.api.spec.ts` |
| TC0086BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/messages/should-mark-message-unread.ui.spec.ts` |
| TC0090BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/messages/should-delete-message.ui.spec.ts` |
| TC0094BERSA | idBanco, idSeccion, pass, user | `api/messages/should-filter-messages-by-read-state.api.spec.ts` |
| TC0098BERSA | idBanco, idSeccion, pass, user | `api/messages/should-filter-messages-by-read-state.api.spec.ts` |
| TC0102BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/profile/should-get-notification-preferences.api.spec.ts` |
| TC0104BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/cards/should-download-credit-card-statement.ui.spec.ts` |
| TC0106BERSA | idBanco, idSeccion, pass, tarjetaDebito, user | — |
| TC0107BERSA | idBanco, idSeccion, pass, tarjetaDebito, user | — |
| TC0108BERSA | idBanco, idSeccion, pass, tarjetaDebito, user | `api/payments/should-list-payment-receipts.api.spec.ts` |
| TC0109BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/messages/should-list-received-messages.api.spec.ts` |
| TC0111BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TC0113BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TC0115BERSA | idBanco, idSeccion, pass, user | `api/investments/should-open-investment-funds.api.spec.ts` |
| TC0117BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TC0118BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/investments/should-filter-funds.ui.spec.ts` |
| TC0119BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/investments/funds-by-risk-filter.ui.spec.ts` |
| TC0120BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TC0125BERSA | ctaComitente, idBanco, idSeccion, pass, user | `api/investments/should-show-commitent-account-closure-form.api.spec.ts` |
| TC0126BERSA | idBanco, idSeccion, pass, user | `ui/investments/should-filter-funds.ui.spec.ts` |
| TC0127BERSA | idBanco, idSeccion, pass, user | `ui/investments/should-filter-funds.ui.spec.ts` |
| TC0128BERSA | idBanco, idSeccion, pass, user | `ui/investments/should-filter-funds.ui.spec.ts` |
| TC0129BERSA | idBanco, idSeccion, pass, user | `api/investments/should-list-fund-movements.api.spec.ts` |
| TC0130BERSA | idBanco, idSeccion, pass, user | `api/investments/should-list-fund-movements.api.spec.ts` |
| TC0133BERSA | idBanco, idSeccion, pass, user | `ui/investments/should-open-fund-more-information.ui.spec.ts` |
| TC0134BERSA | idBanco, idSeccion, pass, user | `ui/investments/should-open-fund-more-information.ui.spec.ts` |
| TC0137BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/payments/should-download-payment-receipts-period.api.spec.ts` |
| TC0138BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/payments/should-download-payment-receipts-period.api.spec.ts` |
| TC0139BERSA | cbuDestino, ctaDestino, ctaOrigen, cuitDestino, idBanco, idSeccion, nombreDestino, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-transfer-between-own-accounts.api.spec.ts` |
| TC0145BERSA | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-manage-transfer-contacts.api.spec.ts` |
| TC0146BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/transfers/should-reject-invalid-contact.ui.spec.ts` |
| TC0148BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/transfers/should-reject-invalid-contact.ui.spec.ts` |
| TC0149BERSA | idBanco, idSeccion, pass, user | `api/cards/should-request-credit-card.api.spec.ts` |
| TC0151BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/authentication/should-login-with-virtual-keyboard.ui.spec.ts` |
| TC0153BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/cards/should-show-credit-card-tracking.ui.spec.ts` |
| TC0155BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/cards/should-show-credit-card-tracking.ui.spec.ts` |
| TC0157BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/cards/should-show-credit-card-tracking.ui.spec.ts` |
| TC0158BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/cards/should-show-credit-card-tracking.ui.spec.ts` |
| TC0162BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/cards/should-start-credit-card-theft-report.ui.spec.ts` |
| TC0163BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/cards/should-open-total-credit-card-payment-review.ui.spec.ts` |
| TC0164BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/cards/should-pay-credit-card-minimum.api.spec.ts` |
| TC0165BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/cards/should-pay-mastercard-other-amount.api.spec.ts` |
| TC0167BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/cards/should-download-credit-card-statement.ui.spec.ts` |
| TC0170BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TC0171BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TC0173BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/investments/should-reach-commitent-account-confirmation.ui.spec.ts` |
| TC0178BERSA | ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, user | `ui/investments/should-reject-zero-amount-foreign-currency-purchase.ui.spec.ts` |
| TC0179BERSA | ctaDestino, ctaOrigen, idBanco, idSeccion, pass, user | `ui/investments/should-validate-dollar-exchange-form.ui.spec.ts` |
| TC0181BERSA | ctaOrigen, idBanco, idSeccion, pass, user | `ui/investments/should-validate-dollar-exchange-form.ui.spec.ts` |
| TC0182BERSA | ctaOrigen, idBanco, idSeccion, pass, user | `ui/investments/should-validate-dollar-exchange-form.ui.spec.ts` |
| TC0183BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/payments/should-require-debin-buyer-identifier.ui.spec.ts` |
| TC0184BERSA | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, user | `ui/payments/should-require-debin-buyer-identifier.ui.spec.ts` |
| TC0187BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, user | `ui/investments/should-validate-dollar-exchange-form.ui.spec.ts` |
| TC0188BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, user | `ui/investments/should-validate-dollar-exchange-form.ui.spec.ts` |
| TC0189BERSA | ctaOrigen, idBanco, idSeccion, pass, user | `ui/investments/should-validate-dollar-exchange-form.ui.spec.ts` |
| TC0190BERSA | idBanco, idSeccion, pass, user | `ui/investments/should-validate-dollar-exchange-form.ui.spec.ts` |
| TC0192BERSA | ctaOrigen, idBanco, idSeccion, pass, user | `api/cards/should-reject-credit-card-request-by-declaration.api.spec.ts` |
| TC0193BERSA | idBanco, idSeccion, pass, user | `api/cards/should-reject-credit-card-request-by-declaration.api.spec.ts` |
| TC0194BERSA | idBanco, idSeccion, pass, user | `api/cards/should-reject-credit-card-request-by-declaration.api.spec.ts` |
| TC0195BERSA | idBanco, idSeccion, pass, user | `ui/cards/should-redirect-new-card-request-to-appointments.ui.spec.ts` |
| TC0196BERSA | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/insurance/should-list-insurance-cards.ui.spec.ts` |
| TC0197BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/insurance/should-open-robbery-insurance-detail.ui.spec.ts` |
| TC0206BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/loans/should-request-loans.ui.spec.ts` |
| TC0220BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/loans/should-request-loans.ui.spec.ts` |
| TC0221BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/loans/should-request-loans.ui.spec.ts` |
| TC0222BERSA | ctaOrigen, idBanco, idSeccion, pass, user | `ui/accounts/should-validate-salary-advance-request.ui.spec.ts` |
| TC0223BERSA | ctaOrigen, idBanco, idSeccion, pass, user | `api/accounts/should-request-salary-advance.api.spec.ts` |
| TC0225BERSA | ctaOrigen, idBanco, idSeccion, pass, user | `ui/accounts/should-validate-salary-advance-request.ui.spec.ts` |
| TC0226BERSA | ctaOrigen, idBanco, idSeccion, pass, user | `ui/accounts/should-validate-salary-advance-request.ui.spec.ts` |
| TC0227BERSA | ctaOrigen, idBanco, idSeccion, pass, user | `ui/accounts/should-open-salary-advance-contract.ui.spec.ts` |
| TC0228BERSA | ctaOrigen, idBanco, idSeccion, pass, user | `ui/accounts/should-open-salary-advance-contract.ui.spec.ts` |
| TC0229BERSA | ctaOrigen, idBanco, idSeccion, pass, user | `api/accounts/should-show-salary-advance-card.api.spec.ts` |
| TC0237BERSA | ctaOrigen, idBanco, idSeccion, pass, user | `ui/accounts/should-validate-salary-advance-request.ui.spec.ts` |
| TC0238BERSA | ctaOrigen, idBanco, idSeccion, pass, user | `ui/accounts/should-validate-salary-advance-request.ui.spec.ts` |
| TC0239BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TC0344BERSA | aliasDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-transfer-to-third-parties.api.spec.ts` |
| TC0407BERSA | aliasDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TC0409BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-transfer-to-adhered-contact.api.spec.ts` |
| TC0410BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-transfer-to-adhered-contact.api.spec.ts` |
| TC0411BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-transfer-to-adhered-contact.api.spec.ts` |
| TC0412BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-transfer-to-adhered-contact.api.spec.ts` |
| TC0413BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TC0418BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-schedule-transfers.api.spec.ts` |
| TC0420BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TC0422BERSA | aliasDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-transfer-to-third-parties.api.spec.ts` |
| TC0424BERSA | aliasDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-transfer-to-third-parties.api.spec.ts` |
| TC0427BERSA | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-transfer-to-adhered-contact.api.spec.ts` |
| TC0429BERSA | aliasDestino, cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-transfer-to-third-parties.api.spec.ts` |
| TC0463BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TC0465BERSA | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-schedule-transfers.api.spec.ts` |
| TC0467BERSA | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-schedule-transfers.api.spec.ts` |
| TC0468BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-schedule-transfers.api.spec.ts` |
| TC0469BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TC0472BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-schedule-transfers.api.spec.ts` |
| TC0473BERSA | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-schedule-transfers.api.spec.ts` |
| TC0474BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-schedule-transfers.api.spec.ts` |
| TC0477BERSA | aliasDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-transfer-to-third-parties.api.spec.ts` |
| TC0478BERSA | aliasDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-schedule-transfers.api.spec.ts` |
| TC0479BERSA | aliasDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-schedule-transfers.api.spec.ts` |
| TC0480BERSA | aliasDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-schedule-transfers.api.spec.ts` |
| TC0483BERSA | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-schedule-transfers.api.spec.ts` |
| TC0484BERSA | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-schedule-transfers.api.spec.ts` |
| TC0485BERSA | aliasDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-schedule-transfers.api.spec.ts` |
| TC107431BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TC110363BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/payments/should-pay-and-find-service-receipts.ui.spec.ts` |
| TC110847BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/payments/should-pay-and-find-service-receipts.ui.spec.ts` |
| TC1111BERSA | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, user | — |
| TC111BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TC113BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TC124865BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/accounts/should-operate-echeqs.ui.spec.ts` |
| TC124907BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/accounts/should-operate-received-echeqs.api.spec.ts` |
| TC124908BERSA | cbuDestino, ctaDestino, ctaOrigen, cuitDestino, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/accounts/should-operate-received-echeqs.api.spec.ts` |
| TC124909BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/accounts/should-list-echeqs-by-status.api.spec.ts` |
| TC124910BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/accounts/should-operate-received-echeqs.api.spec.ts` |
| TC124911BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/accounts/should-operate-received-echeqs.api.spec.ts` |
| TC124912BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/accounts/should-operate-received-echeqs.api.spec.ts` |
| TC124913BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/accounts/should-list-echeqs-by-status.api.spec.ts` |
| TC128231BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/accounts/should-operate-received-echeqs.api.spec.ts` |
| TC128237BERSA | cbuDestino, ctaDestino, ctaOrigen, cuitDestino, idBanco, idSeccion, pass, pass1, tarjetaDebito, tarjetaOperar, user, user1 | `api/accounts/should-operate-received-echeqs.api.spec.ts` |
| TC128238BERSA | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/cards/should-send-applicant-cuil-in-credit-card-request.api.spec.ts` |
| TC128239BERSA | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/cards/should-send-applicant-cuil-in-credit-card-request.api.spec.ts` |
| TC128240BERSA | ctaOrigen, idBanco, idSeccion, pass, user | `ui/authentication/should-recover-username.ui.spec.ts` |
| TC128241BERSA | ctaOrigen, idBanco, idSeccion, pass, user | `ui/accounts/should-validate-account-alias.ui.spec.ts` |
| TC128242BBERSA | ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaOperar, user | — |
| TC128242BERSA | ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/cards/should-show-credit-card-detail-information.ui.spec.ts` |
| TC128243BERSA | ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaOperar, user | `ui/home/should-show-warning-alert.ui.spec.ts` |
| TC128243BSBERSA | ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaOperar, user | — |
| TC128244BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, user | `ui/investments/should-validate-fixed-term-request-continue.ui.spec.ts` |
| TC128246BERSA | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/transfers/should-send-device-validation-to-fraud-analysis.ui.spec.ts` |
| TC128247BERSA | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/transfers/should-send-device-validation-to-fraud-analysis.ui.spec.ts` |
| TC128248BERSA | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/transfers/should-send-device-validation-to-fraud-analysis.ui.spec.ts` |
| TC128249BERSA | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/transfers/should-send-device-validation-to-fraud-analysis.ui.spec.ts` |
| TC12928BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/insurance/should-reach-robbery-insurance-confirmation.ui.spec.ts` |
| TC12930BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-transfer-to-third-parties.api.spec.ts` |
| TC12932BERSA | aliasDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-transfer-to-third-parties.api.spec.ts` |
| TC12935BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-transfer-to-third-parties.api.spec.ts` |
| TC12938BERSA | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-generate-credin.api.spec.ts` |
| TC12939BERSA | cbuDestino, ctaOrigen, cuitDestino, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-generate-credin.api.spec.ts` |
| TC12940BERSA | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-generate-credin.api.spec.ts` |
| TC12952BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TC12954BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, user | — |
| TC12956BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TC12964BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/payments/should-pay-tax-service-without-base.api.spec.ts` |
| TC12966BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/payments/should-remove-tax-service.api.spec.ts` |
| TC12967BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/cards/should-pay-credit-card-other-amount.api.spec.ts` |
| TC12969BERSA | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-transfer-to-third-parties.api.spec.ts` |
| TC12973BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/investments/should-create-usd-fixed-term.api.spec.ts` |
| TC12974BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TC12978BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TC12981BERSA | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-manage-transfer-contacts.api.spec.ts` |
| TC14250BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TC16039BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/cards/should-pay-credit-card-minimum.api.spec.ts` |
| TC16756BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/transfers/should-show-transfer-history.ui.spec.ts` |
| TC16759BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-list-transfer-history.api.spec.ts` |
| TC16765BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-list-transfer-history.api.spec.ts` |
| TC16768BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-list-transfer-history.api.spec.ts` |
| TC18088BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/transfers/should-transfer-to-own-account-by-cbu.ui.spec.ts` |
| TC18089BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-transfer-to-third-parties.api.spec.ts` |
| TC18090BERSA | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-schedule-transfers.api.spec.ts` |
| TC18092BERSA | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/transfers/should-transfer-and-show-ticket.ui.spec.ts` |
| TC18093BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-transfer-to-third-parties.api.spec.ts` |
| TC18094BERSA | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-schedule-transfers.api.spec.ts` |
| TC18096BERSA | ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, user | `ui/transfers/should-transfer-and-show-ticket.ui.spec.ts` |
| TC18097BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-transfer-to-third-parties.api.spec.ts` |
| TC18098BERSA | aliasDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-transfer-to-third-parties.api.spec.ts` |
| TC18099BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/transfers/should-transfer-and-show-ticket.ui.spec.ts` |
| TC18100BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-list-transfer-history.api.spec.ts` |
| TC18101BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-list-transfer-history.api.spec.ts` |
| TC18102BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-list-transfer-history.api.spec.ts` |
| TC18103BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/transfers/should-show-transfer-history.ui.spec.ts` |
| TC18104BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-list-transfer-history.api.spec.ts` |
| TC25610BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/accounts/should-list-echeqs-by-status.api.spec.ts` |
| TC29408BERSA | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/transfers/should-show-transfer-history.ui.spec.ts` |
| TC29410BERSA | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/accounts/should-enable-new-account-request-continue.ui.spec.ts` |
| TC29411BERSA | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/accounts/should-enable-new-account-request-continue.ui.spec.ts` |
| TC29412BERSA | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/accounts/should-enable-new-account-request-continue.ui.spec.ts` |
| TC29413BERSA | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/accounts/should-operate-echeqs.ui.spec.ts` |
| TC29414BERSA | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/accounts/should-filter-echeqs.ui.spec.ts` |
| TC29415BERSA | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TCECHEQBERSA | ambiente, ctaOrigen, cuitDestino, idBanco, idSeccion, pass, user | — |
| TCECHEQCANCELBERSA | ambiente, ctaOrigen, idBanco, idSeccion, pass, user | — |
| TCTest259BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TCTestBERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TR110363BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TR110846BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
