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
| TC0002BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TC0025BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TC0043BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TC0046BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TC0047BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TC0071BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TC0072BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TC0106BERSA | idBanco, idSeccion, pass, tarjetaDebito, user | — |
| TC0107BERSA | idBanco, idSeccion, pass, tarjetaDebito, user | — |
| TC0110001BERSA | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, user | `api/accounts/should-adhere-new-destination.api.spec.ts` |
| TC0110002BERSA | ctaOrigen, idBanco, idSeccion, pass, user | `api/accounts/should-generate-withdrawal-order.api.spec.ts` |
| TC0110003BERSA | ctaOrigen, idBanco, idSeccion, pass, user | `api/accounts/should-request-salary-advance.api.spec.ts` |
| TC0110004BERSA | ctaOrigen, idBanco, idSeccion, pass, user | `api/accounts/should-show-salary-advance-card.api.spec.ts` |
| TC0110005BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/accounts/should-list-echeqs-by-status.api.spec.ts` |
| TC0110083BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/accounts/should-operate-received-echeqs.api.spec.ts` |
| TC0110084BERSA | cbuDestino, ctaDestino, ctaOrigen, cuitDestino, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/accounts/should-operate-received-echeqs.api.spec.ts` |
| TC0110085BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/accounts/should-list-echeqs-by-status.api.spec.ts` |
| TC0110086BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/accounts/should-operate-received-echeqs.api.spec.ts` |
| TC0110087BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/accounts/should-operate-received-echeqs.api.spec.ts` |
| TC0110088BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/accounts/should-operate-received-echeqs.api.spec.ts` |
| TC0110089BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/accounts/should-list-echeqs-by-status.api.spec.ts` |
| TC0110090BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/accounts/should-operate-received-echeqs.api.spec.ts` |
| TC0110091BERSA | cbuDestino, ctaDestino, ctaOrigen, cuitDestino, idBanco, idSeccion, pass, pass1, tarjetaDebito, tarjetaOperar, user, user1 | `api/accounts/should-operate-received-echeqs.api.spec.ts` |
| TC0111BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TC0113BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TC0117BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TC0120001BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/accounts/should-operate-echeqs.ui.spec.ts` |
| TC0120002BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/accounts/should-show-account-movement-detail.ui.spec.ts` |
| TC0120003BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/accounts/should-transfer-to-scheduled-contact-and-find-movement.ui.spec.ts` |
| TC0120004BERSA | ctaOrigen, idBanco, idSeccion, pass, user | `ui/accounts/should-validate-salary-advance-request.ui.spec.ts` |
| TC0120005BERSA | ctaOrigen, idBanco, idSeccion, pass, user | `ui/accounts/should-validate-salary-advance-request.ui.spec.ts` |
| TC0120006BERSA | ctaOrigen, idBanco, idSeccion, pass, user | `ui/accounts/should-validate-salary-advance-request.ui.spec.ts` |
| TC0120007BERSA | ctaOrigen, idBanco, idSeccion, pass, user | `ui/accounts/should-open-salary-advance-contract.ui.spec.ts` |
| TC0120008BERSA | ctaOrigen, idBanco, idSeccion, pass, user | `ui/accounts/should-open-salary-advance-contract.ui.spec.ts` |
| TC0120009BERSA | ctaOrigen, idBanco, idSeccion, pass, user | `ui/accounts/should-validate-salary-advance-request.ui.spec.ts` |
| TC0120010BERSA | ctaOrigen, idBanco, idSeccion, pass, user | `ui/accounts/should-validate-salary-advance-request.ui.spec.ts` |
| TC0120011BERSA | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/accounts/should-enable-new-account-request-continue.ui.spec.ts` |
| TC0120012BERSA | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/accounts/should-enable-new-account-request-continue.ui.spec.ts` |
| TC0120013BERSA | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/accounts/should-enable-new-account-request-continue.ui.spec.ts` |
| TC0120014BERSA | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/accounts/should-operate-echeqs.ui.spec.ts` |
| TC0120015BERSA | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/accounts/should-filter-echeqs.ui.spec.ts` |
| TC0120016BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/accounts/should-operate-echeqs.ui.spec.ts` |
| TC0120017BERSA | ctaOrigen, idBanco, idSeccion, pass, user | `ui/accounts/should-validate-account-alias.ui.spec.ts` |
| TC0120BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TC0170BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TC0171BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TC0210001BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/authentication/should-login-and-capture-bearer.api.spec.ts` |
| TC0220001BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/authentication/should-login-and-capture-bearer.ui.spec.ts` |
| TC0220002BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/authentication/should-recover-username.ui.spec.ts` |
| TC0220003BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/authentication/should-login-with-virtual-keyboard.ui.spec.ts` |
| TC0220004BERSA | ctaOrigen, idBanco, idSeccion, pass, user | `ui/authentication/should-recover-username.ui.spec.ts` |
| TC0239BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TC0310001BERSA | idBanco, idSeccion, pass, user | `api/cards/should-request-credit-card.api.spec.ts` |
| TC0310002BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/cards/should-pay-credit-card-minimum.api.spec.ts` |
| TC0310003BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/cards/should-pay-mastercard-other-amount.api.spec.ts` |
| TC0310004BERSA | ctaOrigen, idBanco, idSeccion, pass, user | `api/cards/should-reject-credit-card-request-by-declaration.api.spec.ts` |
| TC0310005BERSA | idBanco, idSeccion, pass, user | `api/cards/should-reject-credit-card-request-by-declaration.api.spec.ts` |
| TC0310006BERSA | idBanco, idSeccion, pass, user | `api/cards/should-reject-credit-card-request-by-declaration.api.spec.ts` |
| TC0310007BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/cards/should-pay-credit-card-other-amount.api.spec.ts` |
| TC0310008BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/cards/should-pay-credit-card-minimum.api.spec.ts` |
| TC0310009BERSA | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/cards/should-send-applicant-cuil-in-credit-card-request.api.spec.ts` |
| TC0310010BERSA | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/cards/should-send-applicant-cuil-in-credit-card-request.api.spec.ts` |
| TC0320001BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/cards/should-consult-credit-cards.ui.spec.ts` |
| TC0320002BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/cards/should-consult-credit-cards.ui.spec.ts` |
| TC0320003BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/cards/should-consult-credit-cards.ui.spec.ts` |
| TC0320004BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/cards/should-download-credit-card-statement.ui.spec.ts` |
| TC0320005BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/cards/should-show-credit-card-tracking.ui.spec.ts` |
| TC0320006BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/cards/should-show-credit-card-tracking.ui.spec.ts` |
| TC0320007BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/cards/should-show-credit-card-tracking.ui.spec.ts` |
| TC0320008BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/cards/should-show-credit-card-tracking.ui.spec.ts` |
| TC0320009BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/cards/should-start-credit-card-theft-report.ui.spec.ts` |
| TC0320010BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/cards/should-open-total-credit-card-payment-review.ui.spec.ts` |
| TC0320011BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/cards/should-download-credit-card-statement.ui.spec.ts` |
| TC0320012BERSA | idBanco, idSeccion, pass, user | `ui/cards/should-redirect-new-card-request-to-appointments.ui.spec.ts` |
| TC0320013BERSA | ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/cards/should-show-credit-card-detail-information.ui.spec.ts` |
| TC0407BERSA | aliasDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TC0413BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TC0420001BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/documents/should-download-product-contract.ui.spec.ts` |
| TC0420BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TC0463BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TC0469BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TC0520001BERSA | ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaOperar, user | `ui/home/should-show-warning-alert.ui.spec.ts` |
| TC0620001BERSA | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/insurance/should-list-insurance-cards.ui.spec.ts` |
| TC0620002BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/insurance/should-open-robbery-insurance-detail.ui.spec.ts` |
| TC0620003BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/insurance/should-reach-robbery-insurance-confirmation.ui.spec.ts` |
| TC0710001BERSA | ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/investments/should-exchange-dollars.api.spec.ts` |
| TC0710002BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/investments/should-create-traditional-fixed-term.api.spec.ts` |
| TC0710003BERSA | ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/investments/should-exchange-dollars.api.spec.ts` |
| TC0710004BERSA | idBanco, idSeccion, pass, user | `api/investments/should-open-investment-funds.api.spec.ts` |
| TC0710005BERSA | ctaComitente, idBanco, idSeccion, pass, user | `api/investments/should-show-commitent-account-closure-form.api.spec.ts` |
| TC0710006BERSA | idBanco, idSeccion, pass, user | `api/investments/should-list-fund-movements.api.spec.ts` |
| TC0710007BERSA | idBanco, idSeccion, pass, user | `api/investments/should-list-fund-movements.api.spec.ts` |
| TC0710008BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/investments/should-create-usd-fixed-term.api.spec.ts` |
| TC0720001BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/investments/should-query-fixed-term-rates.ui.spec.ts` |
| TC0720002BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/investments/should-query-fixed-term-rates.ui.spec.ts` |
| TC0720003BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/investments/should-filter-funds.ui.spec.ts` |
| TC0720004BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/investments/funds-by-risk-filter.ui.spec.ts` |
| TC0720005BERSA | idBanco, idSeccion, pass, user | `ui/investments/should-filter-funds.ui.spec.ts` |
| TC0720006BERSA | idBanco, idSeccion, pass, user | `ui/investments/should-filter-funds.ui.spec.ts` |
| TC0720007BERSA | idBanco, idSeccion, pass, user | `ui/investments/should-filter-funds.ui.spec.ts` |
| TC0720008BERSA | idBanco, idSeccion, pass, user | `ui/investments/should-open-fund-more-information.ui.spec.ts` |
| TC0720009BERSA | idBanco, idSeccion, pass, user | `ui/investments/should-open-fund-more-information.ui.spec.ts` |
| TC0720010BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/investments/should-reach-commitent-account-confirmation.ui.spec.ts` |
| TC0720011BERSA | ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, user | `ui/investments/should-reject-zero-amount-foreign-currency-purchase.ui.spec.ts` |
| TC0720012BERSA | ctaDestino, ctaOrigen, idBanco, idSeccion, pass, user | `ui/investments/should-validate-dollar-exchange-form.ui.spec.ts` |
| TC0720013BERSA | ctaOrigen, idBanco, idSeccion, pass, user | `ui/investments/should-validate-dollar-exchange-form.ui.spec.ts` |
| TC0720014BERSA | ctaOrigen, idBanco, idSeccion, pass, user | `ui/investments/should-validate-dollar-exchange-form.ui.spec.ts` |
| TC0720015BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, user | `ui/investments/should-validate-dollar-exchange-form.ui.spec.ts` |
| TC0720016BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, user | `ui/investments/should-validate-dollar-exchange-form.ui.spec.ts` |
| TC0720017BERSA | ctaOrigen, idBanco, idSeccion, pass, user | `ui/investments/should-validate-dollar-exchange-form.ui.spec.ts` |
| TC0720018BERSA | idBanco, idSeccion, pass, user | `ui/investments/should-validate-dollar-exchange-form.ui.spec.ts` |
| TC0720019BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, user | `ui/investments/should-validate-fixed-term-request-continue.ui.spec.ts` |
| TC0820001BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/loans/should-request-loans.ui.spec.ts` |
| TC0820002BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/loans/should-request-loans.ui.spec.ts` |
| TC0820003BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/loans/should-request-loans.ui.spec.ts` |
| TC0910001BERSA | idBanco, idSeccion, pass, user | `api/messages/should-filter-messages-by-read-state.api.spec.ts` |
| TC0910002BERSA | idBanco, idSeccion, pass, user | `api/messages/should-filter-messages-by-read-state.api.spec.ts` |
| TC0910003BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/messages/should-list-received-messages.api.spec.ts` |
| TC0920001BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/messages/should-mark-message-unread.ui.spec.ts` |
| TC0920002BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/messages/should-delete-message.ui.spec.ts` |
| TC1020001BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/mis-products/should-open-cancellation-page.ui.spec.ts` |
| TC107431BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TC1110001BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/payments/should-list-debin-history.api.spec.ts` |
| TC1110002BERSA | cbuDestino, ctaOrigen, cuitDestino, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/payments/should-generate-debin.api.spec.ts` |
| TC1110003BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/payments/should-subscribe-recurring-debin.api.spec.ts` |
| TC1110004BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/payments/should-accept-pending-debin.api.spec.ts` |
| TC1110005BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/payments/should-adhere-tax-service.api.spec.ts` |
| TC1110006BERSA | idBanco, idSeccion, pass, tarjetaDebito, user | `api/payments/should-list-payment-receipts.api.spec.ts` |
| TC1110007BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/payments/should-download-payment-receipts-period.api.spec.ts` |
| TC1110008BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/payments/should-download-payment-receipts-period.api.spec.ts` |
| TC1110009BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/payments/should-pay-tax-service-without-base.api.spec.ts` |
| TC1110010BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/payments/should-remove-tax-service.api.spec.ts` |
| TC1111BERSA | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, user | — |
| TC111BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TC1120001BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/payments/should-require-debin-buyer-identifier.ui.spec.ts` |
| TC1120002BERSA | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, user | `ui/payments/should-require-debin-buyer-identifier.ui.spec.ts` |
| TC1120003BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/payments/should-pay-and-find-service-receipts.ui.spec.ts` |
| TC1120004BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/payments/should-pay-and-find-service-receipts.ui.spec.ts` |
| TC113BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TC1210001BERSA | idBanco, idSeccion, pass, user | `api/procedures/should-list-current-online-procedures.api.spec.ts` |
| TC1220001BERSA | idBanco, idSeccion, pass, user | `ui/procedures/should-open-online-appointments.ui.spec.ts` |
| TC128242BBERSA | ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaOperar, user | — |
| TC128243BSBERSA | ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaOperar, user | — |
| TC12952BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TC12954BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, user | — |
| TC12956BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TC12974BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TC12978BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TC1310001BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/profile/should-change-phone.api.spec.ts` |
| TC1310002BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/profile/should-get-notification-preferences.api.spec.ts` |
| TC1320001BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/profile/should-change-address.ui.spec.ts` |
| TC1320002BERSA | idBanco, idSeccion, pass, user | `ui/profile/should-change-security-seal.ui.spec.ts` |
| TC14250BERSA | ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TC1510001BERSA | ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-schedule-transfers.api.spec.ts` |
| TC1510002BERSA | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-generate-credin.api.spec.ts` |
| TC1510003BERSA | cbuDestino, ctaDestino, ctaOrigen, cuitDestino, idBanco, idSeccion, nombreDestino, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-transfer-between-own-accounts.api.spec.ts` |
| TC1510004BERSA | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-manage-transfer-contacts.api.spec.ts` |
| TC1510005BERSA | aliasDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-transfer-to-third-parties.api.spec.ts` |
| TC1510006BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-transfer-to-adhered-contact.api.spec.ts` |
| TC1510007BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-transfer-to-adhered-contact.api.spec.ts` |
| TC1510008BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-transfer-to-adhered-contact.api.spec.ts` |
| TC1510009BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-transfer-to-adhered-contact.api.spec.ts` |
| TC1510010BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-schedule-transfers.api.spec.ts` |
| TC1510011BERSA | aliasDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-transfer-to-third-parties.api.spec.ts` |
| TC1510012BERSA | aliasDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-transfer-to-third-parties.api.spec.ts` |
| TC1510013BERSA | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-transfer-to-adhered-contact.api.spec.ts` |
| TC1510014BERSA | aliasDestino, cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-transfer-to-third-parties.api.spec.ts` |
| TC1510015BERSA | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-schedule-transfers.api.spec.ts` |
| TC1510016BERSA | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-schedule-transfers.api.spec.ts` |
| TC1510017BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-schedule-transfers.api.spec.ts` |
| TC1510018BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-schedule-transfers.api.spec.ts` |
| TC1510019BERSA | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-schedule-transfers.api.spec.ts` |
| TC1510020BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-schedule-transfers.api.spec.ts` |
| TC1510021BERSA | aliasDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-transfer-to-third-parties.api.spec.ts` |
| TC1510022BERSA | aliasDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-schedule-transfers.api.spec.ts` |
| TC1510023BERSA | aliasDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-schedule-transfers.api.spec.ts` |
| TC1510024BERSA | aliasDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-schedule-transfers.api.spec.ts` |
| TC1510025BERSA | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-schedule-transfers.api.spec.ts` |
| TC1510026BERSA | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-schedule-transfers.api.spec.ts` |
| TC1510027BERSA | aliasDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-schedule-transfers.api.spec.ts` |
| TC1510028BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-transfer-to-third-parties.api.spec.ts` |
| TC1510029BERSA | aliasDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-transfer-to-third-parties.api.spec.ts` |
| TC1510030BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-transfer-to-third-parties.api.spec.ts` |
| TC1510031BERSA | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-generate-credin.api.spec.ts` |
| TC1510032BERSA | cbuDestino, ctaOrigen, cuitDestino, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-generate-credin.api.spec.ts` |
| TC1510033BERSA | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-generate-credin.api.spec.ts` |
| TC1510034BERSA | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-transfer-to-third-parties.api.spec.ts` |
| TC1510035BERSA | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-manage-transfer-contacts.api.spec.ts` |
| TC1510036BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-list-transfer-history.api.spec.ts` |
| TC1510037BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-list-transfer-history.api.spec.ts` |
| TC1510038BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-list-transfer-history.api.spec.ts` |
| TC1510039BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-transfer-to-third-parties.api.spec.ts` |
| TC1510040BERSA | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-schedule-transfers.api.spec.ts` |
| TC1510041BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-transfer-to-third-parties.api.spec.ts` |
| TC1510042BERSA | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-schedule-transfers.api.spec.ts` |
| TC1510043BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-transfer-to-third-parties.api.spec.ts` |
| TC1510044BERSA | aliasDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-transfer-to-third-parties.api.spec.ts` |
| TC1510045BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-list-transfer-history.api.spec.ts` |
| TC1510046BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-list-transfer-history.api.spec.ts` |
| TC1510047BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-list-transfer-history.api.spec.ts` |
| TC1510048BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `api/transfers/should-list-transfer-history.api.spec.ts` |
| TC1520001BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/transfers/should-reject-invalid-contact.ui.spec.ts` |
| TC1520002BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/transfers/should-reject-invalid-contact.ui.spec.ts` |
| TC1520003BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/transfers/should-show-transfer-history.ui.spec.ts` |
| TC1520004BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/transfers/should-transfer-to-own-account-by-cbu.ui.spec.ts` |
| TC1520005BERSA | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/transfers/should-transfer-and-show-ticket.ui.spec.ts` |
| TC1520006BERSA | ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, user | `ui/transfers/should-transfer-and-show-ticket.ui.spec.ts` |
| TC1520007BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/transfers/should-transfer-and-show-ticket.ui.spec.ts` |
| TC1520008BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/transfers/should-show-transfer-history.ui.spec.ts` |
| TC1520009BERSA | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/transfers/should-show-transfer-history.ui.spec.ts` |
| TC1520010BERSA | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/transfers/should-send-device-validation-to-fraud-analysis.ui.spec.ts` |
| TC1520011BERSA | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/transfers/should-send-device-validation-to-fraud-analysis.ui.spec.ts` |
| TC1520012BERSA | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/transfers/should-send-device-validation-to-fraud-analysis.ui.spec.ts` |
| TC1520013BERSA | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | `ui/transfers/should-send-device-validation-to-fraud-analysis.ui.spec.ts` |
| TC29415BERSA | cbuDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TCECHEQBERSA | ambiente, ctaOrigen, cuitDestino, idBanco, idSeccion, pass, user | — |
| TCECHEQCANCELBERSA | ambiente, ctaOrigen, idBanco, idSeccion, pass, user | — |
| TCTest259BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TCTestBERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TR110363BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
| TR110846BERSA | cbuDestino, ctaDestino, ctaOrigen, idBanco, idSeccion, pass, tarjetaDebito, tarjetaOperar, user | — |
