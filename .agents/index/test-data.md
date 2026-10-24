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
- `src/test-data/case-fields.ts`: `loadCaseRow(testCaseId: string): Promise<CaseRow>`
- `src/test-data/contact-search-ui-data.ts`: `loadContactSearchUiData(testCaseId: string): Promise<ContactSearchUiData>` → fixture `contactSearchUiData`
- `src/test-data/credit-card-detail-ui-data.ts`: `loadCreditCardDetailUiData(testCaseId: string): Promise<CreditCardDetailUiData>` → fixture `creditCardDetailUiData`
- `src/test-data/credit-card-other-amount-payment-data.ts`: `loadCreditCardOtherAmountPaymentData(testCaseId: string): Promise<CreditCardOtherAmountPaymentData>` → fixture `creditCardOtherAmountPaymentData`
- `src/test-data/credit-card-total-payment-ui-data.ts`: `loadCreditCardTotalPaymentUiData(testCaseId: string): Promise<CreditCardTotalPaymentUiData>` → fixture `creditCardTotalPaymentUiData`
- `src/test-data/debin-generation-data.ts`: `loadDebinGenerationData(testCaseId: string): Promise<DebinGenerationData>` → fixture `debinGenerationData`
- `src/test-data/fixed-access.ts`: `loadFixedAccess(): Promise<FixedAccess | undefined>`
- `src/test-data/foreign-currency-purchase-ui-data.ts`: `loadForeignCurrencyPurchaseUiData(testCaseId: string): Promise<ForeignCurrencyPurchaseUiData>` → fixture `foreignCurrencyPurchaseUiData`
- `src/test-data/login-credentials.ts`: `loadLoginCredentials(testCaseId: string): Promise<LoginCredentials>` → fixture `loginCredentials`, `secondUserCredentials`
- `src/test-data/login-credentials.ts`: `loadSecondUserCredentials(testCaseId: string): Promise<LoginCredentials>` → fixture `loginCredentials`, `secondUserCredentials`
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
| TC-001-1-0001-UNE | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, user | `api/001-accounts/should-adhere-new-destination.api.spec.ts` |
| TC-001-1-0002-UNE | ctaOrigen, idBanco, idSeccion, pass, user | `api/001-accounts/should-generate-withdrawal-order.api.spec.ts` |
| TC-001-1-0003-UNE | ctaOrigen, idBanco, idSeccion, pass, user | `api/001-accounts/should-request-salary-advance.api.spec.ts` |
| TC-001-1-0004-UNE | ctaOrigen, idBanco, idSeccion, pass, user | `api/001-accounts/should-show-salary-advance-card.api.spec.ts` |
| TC-001-1-0005-UNE | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/001-accounts/should-list-echeqs-by-status.api.spec.ts` |
| TC-001-1-0083-UNE | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/001-accounts/should-operate-received-echeqs.api.spec.ts` |
| TC-001-1-0084-UNE | cbuDestino, ctaDestino, ctaOrigen, cuitDestino, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/001-accounts/should-operate-received-echeqs.api.spec.ts` |
| TC-001-1-0085-UNE | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/001-accounts/should-list-echeqs-by-status.api.spec.ts` |
| TC-001-1-0086-UNE | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/001-accounts/should-operate-received-echeqs.api.spec.ts` |
| TC-001-1-0087-UNE | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/001-accounts/should-operate-received-echeqs.api.spec.ts` |
| TC-001-1-0088-UNE | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/001-accounts/should-operate-received-echeqs.api.spec.ts` |
| TC-001-1-0089-UNE | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/001-accounts/should-list-echeqs-by-status.api.spec.ts` |
| TC-001-1-0090-UNE | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/001-accounts/should-operate-received-echeqs.api.spec.ts` |
| TC-001-1-0091-UNE | cbuDestino, ctaDestino, ctaOrigen, cuitDestino, idBanco, idSeccion, pass, pass1, tarjetaDebito, tarjetaOperar, user, user1 | `api/001-accounts/should-operate-received-echeqs.api.spec.ts` |
| TC-001-2-0001-UNE | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/001-accounts/should-operate-echeqs.ui.spec.ts` |
| TC-001-2-0002-UNE | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/001-accounts/should-show-account-movement-detail.ui.spec.ts` |
| TC-001-2-0003-UNE | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/001-accounts/should-transfer-to-scheduled-contact-and-find-movement.ui.spec.ts` |
| TC-001-2-0004-UNE | ctaOrigen, idBanco, idSeccion, pass, user | `ui/001-accounts/should-validate-salary-advance-request.ui.spec.ts` |
| TC-001-2-0005-UNE | ctaOrigen, idBanco, idSeccion, pass, user | `ui/001-accounts/should-validate-salary-advance-request.ui.spec.ts` |
| TC-001-2-0006-UNE | ctaOrigen, idBanco, idSeccion, pass, user | `ui/001-accounts/should-validate-salary-advance-request.ui.spec.ts` |
| TC-001-2-0007-UNE | ctaOrigen, idBanco, idSeccion, pass, user | `ui/001-accounts/should-open-salary-advance-contract.ui.spec.ts` |
| TC-001-2-0008-UNE | ctaOrigen, idBanco, idSeccion, pass, user | `ui/001-accounts/should-open-salary-advance-contract.ui.spec.ts` |
| TC-001-2-0009-UNE | ctaOrigen, idBanco, idSeccion, pass, user | `ui/001-accounts/should-validate-salary-advance-request.ui.spec.ts` |
| TC-001-2-0010-UNE | ctaOrigen, idBanco, idSeccion, pass, user | `ui/001-accounts/should-validate-salary-advance-request.ui.spec.ts` |
| TC-001-2-0011-UNE | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/001-accounts/should-enable-new-account-request-continue.ui.spec.ts` |
| TC-001-2-0012-UNE | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/001-accounts/should-enable-new-account-request-continue.ui.spec.ts` |
| TC-001-2-0013-UNE | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/001-accounts/should-enable-new-account-request-continue.ui.spec.ts` |
| TC-001-2-0014-UNE | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/001-accounts/should-operate-echeqs.ui.spec.ts` |
| TC-001-2-0015-UNE | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/001-accounts/should-filter-echeqs.ui.spec.ts` |
| TC-001-2-0016-UNE | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/001-accounts/should-operate-echeqs.ui.spec.ts` |
| TC-001-2-0017-UNE | ctaOrigen, idBanco, idSeccion, pass, user | `ui/001-accounts/should-validate-account-alias.ui.spec.ts` |
| TC-002-1-0001-UNE | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/002-authentication/should-login-and-capture-bearer.api.spec.ts` |
| TC-002-2-0001-UNE | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/002-authentication/should-login-and-capture-bearer.ui.spec.ts` |
| TC-002-2-0002-UNE | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/002-authentication/should-recover-username.ui.spec.ts` |
| TC-002-2-0003-UNE | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/002-authentication/should-login-with-virtual-keyboard.ui.spec.ts` |
| TC-002-2-0004-UNE | ctaOrigen, idBanco, idSeccion, pass, user | `ui/002-authentication/should-recover-username.ui.spec.ts` |
| TC-003-1-0001-UNE | idBanco, idSeccion, pass, user | `api/003-cards/should-request-credit-card.api.spec.ts` |
| TC-003-1-0002-UNE | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/003-cards/should-pay-credit-card-minimum.api.spec.ts` |
| TC-003-1-0003-UNE | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/003-cards/should-pay-mastercard-other-amount.api.spec.ts` |
| TC-003-1-0004-UNE | ctaOrigen, idBanco, idSeccion, pass, user | `api/003-cards/should-reject-credit-card-request-by-declaration.api.spec.ts` |
| TC-003-1-0005-UNE | idBanco, idSeccion, pass, user | `api/003-cards/should-reject-credit-card-request-by-declaration.api.spec.ts` |
| TC-003-1-0006-UNE | idBanco, idSeccion, pass, user | `api/003-cards/should-reject-credit-card-request-by-declaration.api.spec.ts` |
| TC-003-1-0007-UNE | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/003-cards/should-pay-credit-card-other-amount.api.spec.ts` |
| TC-003-1-0008-UNE | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/003-cards/should-pay-credit-card-minimum.api.spec.ts` |
| TC-003-1-0009-UNE | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/003-cards/should-send-applicant-cuil-in-credit-card-request.api.spec.ts` |
| TC-003-1-0010-UNE | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/003-cards/should-send-applicant-cuil-in-credit-card-request.api.spec.ts` |
| TC-003-2-0001-UNE | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/003-cards/should-consult-credit-cards.ui.spec.ts` |
| TC-003-2-0002-UNE | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/003-cards/should-consult-credit-cards.ui.spec.ts` |
| TC-003-2-0003-UNE | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/003-cards/should-consult-credit-cards.ui.spec.ts` |
| TC-003-2-0004-UNE | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/003-cards/should-download-credit-card-statement.ui.spec.ts` |
| TC-003-2-0005-UNE | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/003-cards/should-show-credit-card-tracking.ui.spec.ts` |
| TC-003-2-0006-UNE | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/003-cards/should-show-credit-card-tracking.ui.spec.ts` |
| TC-003-2-0007-UNE | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/003-cards/should-show-credit-card-tracking.ui.spec.ts` |
| TC-003-2-0008-UNE | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/003-cards/should-show-credit-card-tracking.ui.spec.ts` |
| TC-003-2-0009-UNE | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/003-cards/should-start-credit-card-theft-report.ui.spec.ts` |
| TC-003-2-0010-UNE | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/003-cards/should-open-total-credit-card-payment-review.ui.spec.ts` |
| TC-003-2-0011-UNE | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/003-cards/should-download-credit-card-statement.ui.spec.ts` |
| TC-003-2-0012-UNE | idBanco, idSeccion, pass, user | `ui/003-cards/should-redirect-new-card-request-to-appointments.ui.spec.ts` |
| TC-003-2-0013-UNE | ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/003-cards/should-show-credit-card-detail-information.ui.spec.ts` |
| TC-004-2-0001-UNE | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/004-documents/should-download-product-contract.ui.spec.ts` |
| TC-005-2-0001-UNE | ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaOperar, user | `ui/005-home/should-show-warning-alert.ui.spec.ts` |
| TC-006-2-0001-UNE | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/006-insurance/should-list-insurance-cards.ui.spec.ts` |
| TC-006-2-0002-UNE | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/006-insurance/should-open-robbery-insurance-detail.ui.spec.ts` |
| TC-006-2-0003-UNE | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/006-insurance/should-reach-robbery-insurance-confirmation.ui.spec.ts` |
| TC-007-1-0001-UNE | ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/007-investments/should-exchange-dollars.api.spec.ts` |
| TC-007-1-0002-UNE | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/007-investments/should-create-traditional-fixed-term.api.spec.ts` |
| TC-007-1-0003-UNE | ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/007-investments/should-exchange-dollars.api.spec.ts` |
| TC-007-1-0004-UNE | idBanco, idSeccion, pass, user | `api/007-investments/should-open-investment-funds.api.spec.ts` |
| TC-007-1-0005-UNE | ctaComitente, idBanco, idSeccion, pass, user | `api/007-investments/should-show-commitent-account-closure-form.api.spec.ts` |
| TC-007-1-0006-UNE | idBanco, idSeccion, pass, user | `api/007-investments/should-list-fund-movements.api.spec.ts` |
| TC-007-1-0007-UNE | idBanco, idSeccion, pass, user | `api/007-investments/should-list-fund-movements.api.spec.ts` |
| TC-007-1-0008-UNE | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/007-investments/should-create-usd-fixed-term.api.spec.ts` |
| TC-007-2-0001-UNE | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/007-investments/should-query-fixed-term-rates.ui.spec.ts` |
| TC-007-2-0002-UNE | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/007-investments/should-query-fixed-term-rates.ui.spec.ts` |
| TC-007-2-0003-UNE | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/007-investments/should-filter-funds.ui.spec.ts` |
| TC-007-2-0004-UNE | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/007-investments/funds-by-risk-filter.ui.spec.ts` |
| TC-007-2-0005-UNE | idBanco, idSeccion, pass, user | `ui/007-investments/should-filter-funds.ui.spec.ts` |
| TC-007-2-0006-UNE | idBanco, idSeccion, pass, user | `ui/007-investments/should-filter-funds.ui.spec.ts` |
| TC-007-2-0007-UNE | idBanco, idSeccion, pass, user | `ui/007-investments/should-filter-funds.ui.spec.ts` |
| TC-007-2-0008-UNE | idBanco, idSeccion, pass, user | `ui/007-investments/should-open-fund-more-information.ui.spec.ts` |
| TC-007-2-0009-UNE | idBanco, idSeccion, pass, user | `ui/007-investments/should-open-fund-more-information.ui.spec.ts` |
| TC-007-2-0010-UNE | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/007-investments/should-reach-commitent-account-confirmation.ui.spec.ts` |
| TC-007-2-0011-UNE | ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, user | `ui/007-investments/should-reject-zero-amount-foreign-currency-purchase.ui.spec.ts` |
| TC-007-2-0012-UNE | ctaDestino, ctaOrigen, idBanco, idSeccion, pass, user | `ui/007-investments/should-validate-dollar-exchange-form.ui.spec.ts` |
| TC-007-2-0013-UNE | ctaOrigen, idBanco, idSeccion, pass, user | `ui/007-investments/should-validate-dollar-exchange-form.ui.spec.ts` |
| TC-007-2-0014-UNE | ctaOrigen, idBanco, idSeccion, pass, user | `ui/007-investments/should-validate-dollar-exchange-form.ui.spec.ts` |
| TC-007-2-0015-UNE | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, user | `ui/007-investments/should-validate-dollar-exchange-form.ui.spec.ts` |
| TC-007-2-0016-UNE | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, user | `ui/007-investments/should-validate-dollar-exchange-form.ui.spec.ts` |
| TC-007-2-0017-UNE | ctaOrigen, idBanco, idSeccion, pass, user | `ui/007-investments/should-validate-dollar-exchange-form.ui.spec.ts` |
| TC-007-2-0018-UNE | idBanco, idSeccion, pass, user | `ui/007-investments/should-validate-dollar-exchange-form.ui.spec.ts` |
| TC-007-2-0019-UNE | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, user | `ui/007-investments/should-validate-fixed-term-request-continue.ui.spec.ts` |
| TC-008-2-0001-UNE | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/008-loans/should-request-loans.ui.spec.ts` |
| TC-008-2-0002-UNE | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/008-loans/should-request-loans.ui.spec.ts` |
| TC-008-2-0003-UNE | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/008-loans/should-request-loans.ui.spec.ts` |
| TC-009-1-0001-UNE | idBanco, idSeccion, pass, user | `api/009-messages/should-filter-messages-by-read-state.api.spec.ts` |
| TC-009-1-0002-UNE | idBanco, idSeccion, pass, user | `api/009-messages/should-filter-messages-by-read-state.api.spec.ts` |
| TC-009-1-0003-UNE | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/009-messages/should-list-received-messages.api.spec.ts` |
| TC-009-2-0001-UNE | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/009-messages/should-mark-message-unread.ui.spec.ts` |
| TC-009-2-0002-UNE | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/009-messages/should-delete-message.ui.spec.ts` |
| TC-010-2-0001-UNE | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/010-mis-products/should-open-cancellation-page.ui.spec.ts` |
| TC-011-1-0001-UNE | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/011-payments/should-list-debin-history.api.spec.ts` |
| TC-011-1-0002-UNE | cbuDestino, ctaOrigen, cuitDestino, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/011-payments/should-generate-debin.api.spec.ts` |
| TC-011-1-0003-UNE | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/011-payments/should-subscribe-recurring-debin.api.spec.ts` |
| TC-011-1-0004-UNE | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/011-payments/should-accept-pending-debin.api.spec.ts` |
| TC-011-1-0005-UNE | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/011-payments/should-adhere-tax-service.api.spec.ts` |
| TC-011-1-0006-UNE | idBanco, idSeccion, pass, tarjetaDebito, user | `api/011-payments/should-list-payment-receipts.api.spec.ts` |
| TC-011-1-0007-UNE | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/011-payments/should-download-payment-receipts-period.api.spec.ts` |
| TC-011-1-0008-UNE | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/011-payments/should-download-payment-receipts-period.api.spec.ts` |
| TC-011-1-0009-UNE | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/011-payments/should-pay-tax-service-without-base.api.spec.ts` |
| TC-011-1-0010-UNE | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/011-payments/should-remove-tax-service.api.spec.ts` |
| TC-011-2-0001-UNE | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/011-payments/should-require-debin-buyer-identifier.ui.spec.ts` |
| TC-011-2-0002-UNE | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, user | `ui/011-payments/should-require-debin-buyer-identifier.ui.spec.ts` |
| TC-011-2-0003-UNE | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/011-payments/should-pay-and-find-service-receipts.ui.spec.ts` |
| TC-011-2-0004-UNE | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/011-payments/should-pay-and-find-service-receipts.ui.spec.ts` |
| TC-012-1-0001-UNE | idBanco, idSeccion, pass, user | `api/012-procedures/should-list-current-online-procedures.api.spec.ts` |
| TC-012-2-0001-UNE | idBanco, idSeccion, pass, user | `ui/012-procedures/should-open-online-appointments.ui.spec.ts` |
| TC-013-1-0001-UNE | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/013-profile/should-change-phone.api.spec.ts` |
| TC-013-1-0002-UNE | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/013-profile/should-get-notification-preferences.api.spec.ts` |
| TC-013-2-0001-UNE | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/013-profile/should-change-address.ui.spec.ts` |
| TC-013-2-0002-UNE | idBanco, idSeccion, pass, user | `ui/013-profile/should-change-security-seal.ui.spec.ts` |
| TC-015-1-0001-UNE | ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/015-transfers/should-schedule-transfers.api.spec.ts` |
| TC-015-1-0002-UNE | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/015-transfers/should-generate-credin.api.spec.ts` |
| TC-015-1-0003-UNE | cbuDestino, ctaDestino, ctaOrigen, cuitDestino, idBanco, idSeccion, nombreDestino, pass, tarjetaDebito, tarjetaOperar, user | `api/015-transfers/should-transfer-between-own-accounts.api.spec.ts` |
| TC-015-1-0004-UNE | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/015-transfers/should-manage-transfer-contacts.api.spec.ts` |
| TC-015-1-0005-UNE | aliasDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/015-transfers/should-transfer-to-third-parties.api.spec.ts` |
| TC-015-1-0006-UNE | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/015-transfers/should-transfer-to-adhered-contact.api.spec.ts` |
| TC-015-1-0007-UNE | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/015-transfers/should-transfer-to-adhered-contact.api.spec.ts` |
| TC-015-1-0008-UNE | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/015-transfers/should-transfer-to-adhered-contact.api.spec.ts` |
| TC-015-1-0009-UNE | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/015-transfers/should-transfer-to-adhered-contact.api.spec.ts` |
| TC-015-1-0010-UNE | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/015-transfers/should-schedule-transfers.api.spec.ts` |
| TC-015-1-0011-UNE | aliasDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/015-transfers/should-transfer-to-third-parties.api.spec.ts` |
| TC-015-1-0012-UNE | aliasDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/015-transfers/should-transfer-to-third-parties.api.spec.ts` |
| TC-015-1-0013-UNE | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/015-transfers/should-transfer-to-adhered-contact.api.spec.ts` |
| TC-015-1-0014-UNE | aliasDestino, cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/015-transfers/should-transfer-to-third-parties.api.spec.ts` |
| TC-015-1-0015-UNE | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/015-transfers/should-schedule-transfers.api.spec.ts` |
| TC-015-1-0016-UNE | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/015-transfers/should-schedule-transfers.api.spec.ts` |
| TC-015-1-0017-UNE | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/015-transfers/should-schedule-transfers.api.spec.ts` |
| TC-015-1-0018-UNE | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/015-transfers/should-schedule-transfers.api.spec.ts` |
| TC-015-1-0019-UNE | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/015-transfers/should-schedule-transfers.api.spec.ts` |
| TC-015-1-0020-UNE | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/015-transfers/should-schedule-transfers.api.spec.ts` |
| TC-015-1-0021-UNE | aliasDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/015-transfers/should-transfer-to-third-parties.api.spec.ts` |
| TC-015-1-0022-UNE | aliasDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/015-transfers/should-schedule-transfers.api.spec.ts` |
| TC-015-1-0023-UNE | aliasDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/015-transfers/should-schedule-transfers.api.spec.ts` |
| TC-015-1-0024-UNE | aliasDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/015-transfers/should-schedule-transfers.api.spec.ts` |
| TC-015-1-0025-UNE | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/015-transfers/should-schedule-transfers.api.spec.ts` |
| TC-015-1-0026-UNE | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/015-transfers/should-schedule-transfers.api.spec.ts` |
| TC-015-1-0027-UNE | aliasDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/015-transfers/should-schedule-transfers.api.spec.ts` |
| TC-015-1-0028-UNE | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/015-transfers/should-transfer-to-third-parties.api.spec.ts` |
| TC-015-1-0029-UNE | aliasDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/015-transfers/should-transfer-to-third-parties.api.spec.ts` |
| TC-015-1-0030-UNE | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/015-transfers/should-transfer-to-third-parties.api.spec.ts` |
| TC-015-1-0031-UNE | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/015-transfers/should-generate-credin.api.spec.ts` |
| TC-015-1-0032-UNE | cbuDestino, ctaOrigen, cuitDestino, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/015-transfers/should-generate-credin.api.spec.ts` |
| TC-015-1-0033-UNE | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/015-transfers/should-generate-credin.api.spec.ts` |
| TC-015-1-0034-UNE | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/015-transfers/should-transfer-to-third-parties.api.spec.ts` |
| TC-015-1-0035-UNE | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/015-transfers/should-manage-transfer-contacts.api.spec.ts` |
| TC-015-1-0036-UNE | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/015-transfers/should-list-transfer-history.api.spec.ts` |
| TC-015-1-0037-UNE | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/015-transfers/should-list-transfer-history.api.spec.ts` |
| TC-015-1-0038-UNE | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/015-transfers/should-list-transfer-history.api.spec.ts` |
| TC-015-1-0039-UNE | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/015-transfers/should-transfer-to-third-parties.api.spec.ts` |
| TC-015-1-0040-UNE | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/015-transfers/should-schedule-transfers.api.spec.ts` |
| TC-015-1-0041-UNE | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/015-transfers/should-transfer-to-third-parties.api.spec.ts` |
| TC-015-1-0042-UNE | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/015-transfers/should-schedule-transfers.api.spec.ts` |
| TC-015-1-0043-UNE | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/015-transfers/should-transfer-to-third-parties.api.spec.ts` |
| TC-015-1-0044-UNE | aliasDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/015-transfers/should-transfer-to-third-parties.api.spec.ts` |
| TC-015-1-0045-UNE | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/015-transfers/should-list-transfer-history.api.spec.ts` |
| TC-015-1-0046-UNE | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/015-transfers/should-list-transfer-history.api.spec.ts` |
| TC-015-1-0047-UNE | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/015-transfers/should-list-transfer-history.api.spec.ts` |
| TC-015-1-0048-UNE | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/015-transfers/should-list-transfer-history.api.spec.ts` |
| TC-015-2-0001-UNE | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/015-transfers/should-reject-invalid-contact.ui.spec.ts` |
| TC-015-2-0002-UNE | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/015-transfers/should-reject-invalid-contact.ui.spec.ts` |
| TC-015-2-0003-UNE | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/015-transfers/should-show-transfer-history.ui.spec.ts` |
| TC-015-2-0004-UNE | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/015-transfers/should-transfer-to-own-account-by-cbu.ui.spec.ts` |
| TC-015-2-0005-UNE | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/015-transfers/should-transfer-and-show-ticket.ui.spec.ts` |
| TC-015-2-0006-UNE | ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, user | `ui/015-transfers/should-transfer-and-show-ticket.ui.spec.ts` |
| TC-015-2-0007-UNE | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/015-transfers/should-transfer-and-show-ticket.ui.spec.ts` |
| TC-015-2-0008-UNE | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/015-transfers/should-show-transfer-history.ui.spec.ts` |
| TC-015-2-0009-UNE | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/015-transfers/should-show-transfer-history.ui.spec.ts` |
| TC-015-2-0010-UNE | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/015-transfers/should-send-device-validation-to-fraud-analysis.ui.spec.ts` |
| TC-015-2-0011-UNE | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/015-transfers/should-send-device-validation-to-fraud-analysis.ui.spec.ts` |
| TC-015-2-0012-UNE | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/015-transfers/should-send-device-validation-to-fraud-analysis.ui.spec.ts` |
| TC-015-2-0013-UNE | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/015-transfers/should-send-device-validation-to-fraud-analysis.ui.spec.ts` |
| TC0002BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TC0025BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TC0043BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TC0046BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TC0047BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TC0071BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TC0072BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TC0106BERSA | idBanco, idSeccion, pass, tarjetaDebito, user | — |
| TC0107BERSA | idBanco, idSeccion, pass, tarjetaDebito, user | — |
| TC0111BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TC0113BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TC0117BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TC0120BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TC0170BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TC0171BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TC0239BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TC0407BERSA | aliasDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TC0413BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TC0420BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TC0463BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TC0469BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TC107431BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TC1111BERSA | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, user | — |
| TC111BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TC113BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TC128242BBERSA | ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaOperar, user | — |
| TC128243BSBERSA | ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaOperar, user | — |
| TC12952BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TC12954BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, user | — |
| TC12956BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TC12974BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TC12978BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TC14250BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TC29415BERSA | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TCECHEQBERSA | ambiente, ctaOrigen, cuitDestino, idBanco, idSeccion, pass, user | — |
| TCECHEQCANCELBERSA | ambiente, ctaOrigen, idBanco, idSeccion, pass, user | — |
| TCTest259BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TCTestBERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TR110363BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TR110846BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
