<!-- Generado por `pnpm run agent:index`. No editar a mano. -->

# Clientes API y workflows

Clases públicas de `src/api/` y `src/workflows/`. Reglas en `src/api/AGENTS.md` y `src/workflows/AGENTS.md`.

## `src/api/accounts/`

### `src/api/accounts/account-transfer-search.api.ts`

**AccountTransferSearchApi** — fixture `accountTransferSearchApi`

- `constructor(api: ReportingApiRequest)`
- `search(session: AuthenticatedSession, request: AccountTransferSearchRequest, acceptedCodes: readonly string[]): Promise<AccountTransferSearchResult>` — Busca una cuenta destino por CBU, CVU o alias. `acceptedCodes` son los códigos de negocio que el escenario espera; cualquier otro código o un HTTP distinto de 200 hace fallar la request.
- `findDestination(session: AuthenticatedSession, request: AccountTransferSearchRequest): Promise<TransferDestination>` — Busca un destino válido y devuelve si ya está agendado (legado CuentasSteps.consultarDestinatarioAgendaCBU).

Otros exports: `AccountTransferSearchRequest`, `AccountTransferSearchResult`, `AccountTransferSearchValue`, `TransferDestination`

### `src/api/accounts/adhered-accounts.api.ts`

**AdheredAccountsApi** — fixture `adheredAccountsApi`
: Agenda de destinatarios (legado CuentasSteps: adhesionAccountNew y deleteAdheredAccount).

- `constructor(api: ReportingApiRequest, smsOperationOtp: SmsOperationOtpApi)`
- `remove(session: AuthenticatedSession, idAdheredAccount: string): Promise<void>`
- `adhere(session: AuthenticatedSession, username: string, destination: NewAdheredAccount): Promise<void>` — Agenda el destinatario con OTP por SMS. Ejecuta la mutación una sola vez por intento.

Otros exports: `NewAdheredAccount`

### `src/api/accounts/echeq-list.api.ts`

**EcheqListApi** — fixture `echeqListApi`

- `constructor(api: ReportingApiRequest)`
- `list(session: AuthenticatedSession, request: EcheqListRequest, acceptedCodes: readonly string[]): Promise<EcheqListResult>` — Consulta el listado de eCheq con los filtros recibidos. Replica la plantilla legada Requests/Echeq/listEcheqValidaciones.json, que no incluye ajax_uuid ni xAppVersion. `acceptedCodes` son los códigos de negocio que el escenario espera.

Otros exports: `EcheqListFilterValue`, `EcheqListRequest`, `EcheqListResult`

### `src/api/accounts/echeq-operations.api.ts`

**EcheqOperationsApi** — fixture `echeqOperationsApi`
: eCheq de individuos (legado EcheqSteps). Cada operación es una mutación por intento con OTP SMS.

- `constructor(api: ReportingApiRequest, smsOperationOtp: SmsOperationOtpApi)`
- `list(session: AuthenticatedSession, statement: string, page: string, operationType: string): Promise<readonly ApiJsonObject[]>` — Lista los eCheq del estado y la página indicados (`list.echeck`).
- `findEcheq(session: AuthenticatedSession, search: EcheqSearch): Promise<EcheqReference>` — Busca, página por página, el primer eCheq del estado con el beneficiario y la fecha de pago indicados (legado generacionDeAceptaciondeEcheq / generacionRepudioDeEcheq).
- `operate(session: AuthenticatedSession, username: string, operation: EcheqOperation, echeq: EcheqReference, options?: { readonly endorseDocumentNumber?: string; readonly idProduct?: string }): Promise<string>` — Ejecuta una operación sobre el eCheq con OTP por SMS y devuelve el `message` de la respuesta. `idProduct` aplica a custodia y depósito; `endorseDocumentNumber`, al endoso.

- `legacyEcheqPaymentDate(daysFromToday: number, now?: Date): string` — Fecha de pago con el formato del legado: `yyyy-MM-dd` local más `T00:00:00.000-03`.

Otros exports: `EcheqOperation`, `EcheqReference`, `EcheqSearch`

### `src/api/accounts/product-widgets.api.ts`

**ProductWidgetsApi** — fixture `productWidgetsApi`
: Productos del usuario (legado CompartidosAPI.cargaServiciosCompartidos y obtenerIdDeCuenta*).

- `constructor(api: ReportingApiRequest)`
- `load(session: AuthenticatedSession): Promise<UserProducts>` — Consulta cuentas y tarjetas de crédito, como el legado, y devuelve ambas listas.
- `loadAccounts(session: AuthenticatedSession): Promise<UserProducts>` — Vuelve a consultar sólo `widgets.accounts`, como `CompartidosAPI.obtenerMontoActualizado*`.
- `legacyBalanceUnits(products: UserProducts, shortLabel: string): number` — Saldo entero de la cuenta como lo calcula el legado (`obtenerMontoCuenta*`): toma `labelWithBalance`, el tramo posterior al segundo `$ ` (o `USD `), descarta decimales y quita separadores de miles.
- `transferOrigin(products: UserProducts, shortLabel: string): { readonly currency: string; readonly idProduct: string; readonly label: string }` — Datos de la cuenta origen que usan las transferencias legadas: idProduct, currency y label.
- `accountCbu(products: UserProducts, shortLabel: string): string` — CBU de la cuenta (`CompartidosAPI.obtenerCbuCuenta`).
- `accountId(products: UserProducts, shortLabel: string): string` — Devuelve el idProduct de la única cuenta cuyo shortLabel coincide con el dato del caso.
- `debitCard(products: UserProducts, lastDigits: string): DebitCard` — Tarjeta de débito cuyo label termina con los últimos dígitos del caso (`tarjetaDebito`).
- `creditCardId(products: UserProducts, lastDigits: string): string` — idProduct de la tarjeta de crédito cuyo número enmascarado termina con los dígitos del caso (`tarjetaOperar`).
- `legacyAccountIdOrEmpty(products: UserProducts, shortLabel: string | undefined): string` — idProduct de la cuenta como lo resuelve el legado sin validar: vacío si el caso no informa cuenta o si no hay coincidencia (JSONPath sin resultados). Usado sólo donde el legado envía ese vacío.

Otros exports: `DebitCard`, `UserProducts`

### `src/api/accounts/salary-advance.api.ts`

**SalaryAdvanceApi** — fixture `salaryAdvanceApi`
: Adelanto de sueldo (legado CuentasSteps: salaryAdvancePre, sealLoansByAmount y salaryAdvanceConfirm).

- `constructor(api: ReportingApiRequest, smsOperationOtp: SmsOperationOtpApi)`
- `readOffer(session: AuthenticatedSession): Promise<SalaryAdvanceOffer>`
- `ratesFor(offer: SalaryAdvanceOffer, shortLabel: string): SalaryAdvanceRates` — Tasas e identificador de la oferta para la cuenta del caso, buscada por shortLabel.
- `readAmounts(session: AuthenticatedSession, amount: number): Promise<SalaryAdvanceAmounts>`
- `confirm(session: AuthenticatedSession, username: string, confirmation: SalaryAdvanceConfirmation): Promise<void>` — Confirma el adelanto con OTP por SMS. Ejecuta la mutación una sola vez por intento.

Otros exports: `SalaryAdvanceAmounts`, `SalaryAdvanceConfirmation`, `SalaryAdvanceOffer`, `SalaryAdvanceRates`

### `src/api/accounts/withdrawal-order.api.ts`

**WithdrawalOrderApi** — fixture `withdrawalOrderApi`

- `constructor(api: ReportingApiRequest, profileMobilePhone: ProfileMobilePhoneApi, secondFactor: SecondFactorChallengeApi)`
- `generate(session: AuthenticatedSession, username: string, data: WithdrawalOrderData): Promise<void>`

## `src/api/authentication/`

### `src/api/authentication/login.api.ts`

**LoginApi** — fixture `loginApi`

- `constructor(api: ReportingApiRequest)`
- `authenticate(credentials: LoginCredentials): Promise<AuthenticatedSession>`
- `readSecuritySealId(username: string): Promise<number>` — Id del sello de seguridad del usuario (`step1` → `data._securitySealId`, legado `tomarIdImagen`).

### `src/api/authentication/second-factor-challenge.api.ts`

**SecondFactorChallengeApi** — fixture `secondFactorChallengeApi`

- `constructor(codeInbox: SecondFactorCodeInboxApi, config: SecondFactorConfig)`
- `get usesFixedCode: boolean` — Indica si el ambiente usa un código fijo, que ya es el valor final a ingresar.
- `resolveOperationOtp(session: AuthenticatedSession, username: string, request: SecondFactorCodeRequest): Promise<string>`
- `requestSingleCode(session: AuthenticatedSession, criteria: SecondFactorCodeCriteria, requestCode: () => Promise<void>): Promise<string>` — Espera el código de un único envío (sin reenvíos), para desafíos cuyo disparo no puede repetirse desde la misma pantalla (por ejemplo, el correo de recupero de usuario).
- `requestNewCode(session: AuthenticatedSession, request: SecondFactorCodeRequest): Promise<string>`

Otros exports: `SecondFactorCodeRequest`

### `src/api/authentication/second-factor-code-inbox.api.ts`

**SecondFactorCodeInboxApi**

- `constructor(api: ReportingApiRequest)`
- `snapshot(session: AuthenticatedSession, criteria: SecondFactorCodeCriteria): Promise<SecondFactorCodeSnapshot>`
- `waitForNewCode(session: AuthenticatedSession, criteria: SecondFactorCodeCriteria, previousSnapshot: SecondFactorCodeSnapshot, pollAttempts: number): Promise<string | undefined>`

- `normalizeSecondFactorOrigin(value: string): string`

Otros exports: `SECOND_FACTOR_POLL_INTERVAL_MS`, `SecondFactorCodeCriteria`, `SecondFactorCodeEntry`, `SecondFactorCodeProperty`, `SecondFactorCodeSnapshot`, `SecondFactorCodeUnavailableError`

### `src/api/authentication/sms-operation-otp.api.ts`

**SmsOperationOtpApi** — fixture `smsOperationOtpApi`
: OTP de autorización de operaciones enviado por SMS al teléfono actual del perfil (legado DobleFactor.getSecondFactor). La espera y los reenvíos pertenecen a la política central de segundo factor; este cliente sólo dispara el envío.

- `constructor(api: ReportingApiRequest, profileMobilePhone: ProfileMobilePhoneApi, secondFactor: SecondFactorChallengeApi)`
- `resolve(session: AuthenticatedSession, username: string): Promise<string>`

## `src/api/cards/`

### `src/api/cards/credit-card-application.api.ts`

**CreditCardApplicationApi** — fixture `creditCardApplicationApi`
: Solicitud de nueva tarjeta de crédito (legado TarjetaSteps). Una mutación por intento.

- `constructor(api: ReportingApiRequest, smsOperationOtp: SmsOperationOtpApi)`
- `readOffers(session: AuthenticatedSession): Promise<CreditCardOffers>`
- `create(session: AuthenticatedSession, offer: CreditCardOffer, debitIdProduct: string): Promise<string>` — Crea la solicitud y devuelve `data.__applicationId`.
- `updateDeclarations(session: AuthenticatedSession, offer: CreditCardOffer, debitIdProduct: string, declarations: PersonDeclarations): Promise<string>` — Actualiza la solicitud con las declaraciones de la persona y devuelve el `message` de la respuesta.
- `confirm(session: AuthenticatedSession, username: string, offer: CreditCardOffer, debitIdProduct: string, applicationId: string): Promise<void>` — Confirma la solicitud con OTP por SMS.

Otros exports: `CreditCardOffer`, `CreditCardOffers`, `PersonDeclarations`

### `src/api/cards/credit-card-other-amount-payment.api.ts`

**CreditCardOtherAmountPaymentApi** — fixture `creditCardOtherAmountPaymentApi`

- `constructor(api: ReportingApiRequest)`
- `pay(session: AuthenticatedSession, testData: CreditCardOtherAmountPaymentData): Promise<CreditCardOtherAmountPaymentResult>`

Otros exports: `CreditCardOtherAmountPaymentResult`

### `src/api/cards/credit-card-payments.api.ts`

**CreditCardPaymentsApi** — fixture `creditCardPaymentsApi`
: Pagos de tarjeta de crédito (legado TarjetaSteps.realizarPagoOtroImporte / realizarPagoMinimo).

- `constructor(api: ReportingApiRequest)`
- `readMinimumPaymentArs(session: AuthenticatedSession, creditCardId: string): Promise<number>` — Pago mínimo en pesos informado por `creditCard.read` (`data.info.minimumPaymentArs`).
- `pay(session: AuthenticatedSession, products: CreditCardPaymentProducts, amount: number): Promise<void>` — Paga la tarjeta por el importe indicado. Ejecuta la mutación una sola vez por intento.

Otros exports: `CreditCardPaymentProducts`

## `src/api/home/`

### `src/api/home/terms-and-conditions.api.ts`

**TermsAndConditionsApi** — fixture `termsAndConditionsApi`

- `constructor(api: ReportingApiRequest)`
- `getGeneralConditions(): Promise<TermsAndConditionsResult>`

Otros exports: `TermsAndConditionsResult`

## `src/api/investments/`

### `src/api/investments/commitent-account.api.ts`

**CommitentAccountApi** — fixture `commitentAccountApi`
: Formulario de baja de cuenta comitente (legado InversionesSteps.ingresoYValidacionBajaCuentaComitente).

- `constructor(api: ReportingApiRequest)`
- `readClosureFormFields(session: AuthenticatedSession): Promise<readonly ClosureFormField[]>` — Devuelve los campos dinámicos del formulario en el orden de la respuesta.

Otros exports: `ClosureFormField`

### `src/api/investments/dollar-exchange.api.ts`

**DollarExchangeApi** — fixture `dollarExchangeApi`
: Compra y venta de dólares (legado InversionesSteps.compraUSD y ventaUSD). Una mutación por intento.

- `constructor(api: ReportingApiRequest)`
- `buy(session: AuthenticatedSession, request: DollarExchangeRequest): Promise<void>`
- `sell(session: AuthenticatedSession, request: DollarExchangeRequest): Promise<void>`

Otros exports: `DollarExchangeRequest`

### `src/api/investments/investment-funds.api.ts`

**InvestmentFundsApi** — fixture `investmentFundsApi`
: Fondos comunes de inversión (legado InversionesSteps: listFund.pre, data.investor y movimientos).

- `constructor(api: ReportingApiRequest)`
- `listFunds(session: AuthenticatedSession): Promise<readonly ApiJsonObject[]>` — Ingreso a la sección de fondos: devuelve los fondos listados por `investment.listFund.pre`.
- `readCommitentAccount(session: AuthenticatedSession): Promise<string>` — Número de la primera cuenta comitente del inversor (`data.accounts[0].number`).
- `findFund(funds: readonly ApiJsonObject[], name: string): InvestmentFund` — Busca el único fondo con el nombre indicado.
- `listMovements(session: AuthenticatedSession, fund: InvestmentFund, commitentAccount: string): Promise<readonly FundMovement[]>`
- `downloadMovement(session: AuthenticatedSession, formName: string, keyLongValue: string): Promise<{ readonly content: unknown }>` — Descarga un movimiento y devuelve el contenido cifrado informado en `data.content`.

Otros exports: `FundMovement`, `InvestmentFund`

### `src/api/investments/traditional-fixed-term.api.ts`

**TraditionalFixedTermApi** — fixture `traditionalFixedTermApi`

- `constructor(api: ReportingApiRequest)`
- `create(session: AuthenticatedSession, testData: TraditionalFixedTermData): Promise<TraditionalFixedTermResult>`
- `createFixedTerm(session: AuthenticatedSession, accountId: string, request: FixedTermRequest): Promise<TraditionalFixedTermResult>` — Constituye un plazo fijo con las condiciones del caso (legado InversionesSteps.generarPlazoFijo). Ejecuta la mutación una sola vez por intento.

Otros exports: `FixedTermRequest`, `TraditionalFixedTermResult`

### `src/api/legacy-request-dates.ts`

- `legacyStartOfDayDaysAgo(days: number, now?: Date): string` — `Utils.fechaTransferenciaMasAntigua(dias)`: fecha local de hace `days` días a las 00:00, sufijo `-03`.
- `legacyNow(now?: Date): string` — `Utils.fechaHoy()`: hora local con el literal `Z`, sin conversión a UTC.

### `src/api/legacy-template.ts`

- `fillLegacyTemplate(template: unknown, replacements: readonly LegacyReplacement[]): ApiJsonObject` — Replica el armado legado de requests: toma la plantilla JSON de `Requests/`, aplica cada `String.replace` en el mismo orden y devuelve el body resultante. Los valores comunes se escapan para el contexto de texto JSON; los `raw` se insertan tal cual (booleanos o números).
- `legacyNullable(value: unknown): string` — `Utils.validarNullParametro`: el texto del valor o `"null"` si no existe.
- `legacyStripBrackets(value: unknown): string` — `Utils.quitarCochetesJsonPath`: quita corchetes y comillas del texto.

Otros exports: `LegacyReplacement`

## `src/api/messages/`

### `src/api/messages/received-messages.api.ts`

**ReceivedMessagesApi** — fixture `receivedMessagesApi`

- `constructor(api: ReportingApiRequest)`
- `list(session: AuthenticatedSession): Promise<ReceivedMessagesResult>`
- `listByReadState(session: AuthenticatedSession, onlyUnread: boolean): Promise<MessagesByReadStateResult>` — Lista los mensajes recibidos filtrando por `onlyUnread`, como la consulta legada de leídos/no leídos.
- `findAttachmentFileId(session: AuthenticatedSession, fileName: string): Promise<unknown>` — Id del primer archivo adjunto con el nombre indicado en los mensajes recibidos (legado MensajesSteps.obtenerIdArchivoParaDescargaPagos*). Hace una sola consulta.

Otros exports: `MessagesByReadStateResult`, `ReceivedMessagesResult`

## `src/api/payments/`

### `src/api/payments/debin-generation.api.ts`

**DebinGenerationApi** — fixture `debinGenerationApi`

- `constructor(api: ReportingApiRequest)`
- `generate(session: AuthenticatedSession, testData: DebinGenerationData): Promise<DebinGenerationResult>`

Otros exports: `DebinGenerationResult`

### `src/api/payments/debin-operations.api.ts`

**DebinOperationsApi** — fixture `debinOperationsApi`
: Operaciones DEBIN posteriores a la generación (legado PagosSteps). Una mutación por intento.

- `constructor(api: ReportingApiRequest, smsOperationOtp: SmsOperationOtpApi)`
- `countHistory(session: AuthenticatedSession): Promise<number>` — Consulta de DEBINes históricos; devuelve la cantidad de elementos listados.
- `subscribeRecurrency(session: AuthenticatedSession, username: string, accountId: string, subscription: RecurringDebinSubscription): Promise<void>` — Adhiere un DEBIN recurrente desde la cuenta indicada con OTP por SMS.
- `findFirstPendingDebinId(session: AuthenticatedSession): Promise<string>` — Id del primer DEBIN con estado PENDIENTE del listado de pendientes.
- `readDetails(session: AuthenticatedSession, debinId: string): Promise<DebinDetails>`
- `accept(session: AuthenticatedSession, username: string, details: DebinDetails, debitCardId: string): Promise<void>` — Acepta el DEBIN con OTP por SMS, debitando desde la tarjeta de débito indicada.

Otros exports: `DebinDetails`, `DebinParty`

### `src/api/payments/payment-link-sync.api.ts`

**PaymentLinkSyncApi** — fixture `paymentLinkSyncApi`
: Sincronización de pagos Link previa a cada operación de pagos (legado PagosSteps.sincronizacionPagosLink).

- `constructor(api: ReportingApiRequest)`
- `synchronize(session: AuthenticatedSession): Promise<void>`

### `src/api/payments/payment-receipts.api.ts`

**PaymentReceiptsApi** — fixture `paymentReceiptsApi`
: Comprobantes de pagos de servicios (legado PagosSteps.comprobantePagos y descargaDeComprobantePeriodo).

- `constructor(api: ReportingApiRequest)`
- `list(session: AuthenticatedSession, debitCardId: string, payment: MercadoLibrePayment, query: PaymentReceiptsQuery): Promise<{ readonly listedCount: number | undefined }>` — Consulta o exporta los comprobantes del período; devuelve la cantidad listada si la respuesta la trae.
- `download(session: AuthenticatedSession, idFile: unknown): Promise<{ readonly fileName: unknown }>` — Descarga un archivo enviado a Mensajes y devuelve el nombre informado por la respuesta.

Otros exports: `PaymentReceiptsQuery`

### `src/api/payments/tax-services.api.ts`

**TaxServicesApi** — fixture `taxServicesApi`
: Pago de impuestos y servicios con tarjeta de débito (legado PagosSteps). Una mutación por intento.

- `constructor(api: ReportingApiRequest, smsOperationOtp: SmsOperationOtpApi)`
- `adhere(session: AuthenticatedSession, username: string, debitCard: DebitCard, service: MercadoPagoService): Promise<void>` — Agenda el servicio para la tarjeta de débito con OTP por SMS (`payments.addTaxService`).
- `findAdhesionCode(session: AuthenticatedSession, debitCardId: string, service: MercadoPagoService): Promise<string | undefined>` — Código de la adhesión que coincide con ente, usuario y referencia del servicio, si existe.
- `unsubscribe(session: AuthenticatedSession, debitCardId: string, adhesionCode: string, service: MercadoPagoService): Promise<void>`
- `prepareWithoutBasePayment(session: AuthenticatedSession, username: string, debitCardId: string, payment: MercadoLibrePayment): Promise<WithoutBasePaymentData>` — Prepara un pago sin base con OTP por SMS y devuelve el concepto y la adhesión a pagar.
- `payWithoutBase(session: AuthenticatedSession, accountId: string, debitCardId: string, prepared: WithoutBasePaymentData, payment: MercadoLibrePayment): Promise<void>`

Otros exports: `WithoutBasePaymentData`

## `src/api/procedures/`

### `src/api/procedures/current-online-procedures.api.ts`

**CurrentOnlineProceduresApi** — fixture `currentOnlineProceduresApi`

- `constructor(api: ReportingApiRequest)`
- `list(session: AuthenticatedSession): Promise<CurrentOnlineProceduresResult>`

Otros exports: `CurrentOnlineProceduresResult`

## `src/api/profile/`

### `src/api/profile/notification-preferences.api.ts`

**NotificationPreferencesApi** — fixture `notificationPreferencesApi`

- `constructor(api: ReportingApiRequest)`
- `getMessagePreferences(session: AuthenticatedSession): Promise<NotificationPreferencesResult>`

Otros exports: `NotificationPreferencesResult`

### `src/api/profile/phone-change.api.ts`

**PhoneChangeApi** — fixture `phoneChangeApi`

- `constructor(api: ReportingApiRequest, testData: PhoneChangeTestData, profileMobilePhone: ProfileMobilePhoneApi, secondFactor: SecondFactorChallengeApi)`
- `changePhone(session: AuthenticatedSession, username: string): Promise<PhoneChangeResult>`

Otros exports: `PhoneChangeResult`

### `src/api/profile/profile-mobile-phone.api.ts`

**ProfileMobilePhoneApi** — fixture `profileMobilePhoneApi`

- `constructor(api: ReportingApiRequest)`
- `readCurrentPhone(session: AuthenticatedSession): Promise<string>`
- `readCurrentEmail(session: AuthenticatedSession): Promise<string>` — Email del perfil (`userData.email`), origen de los códigos enviados por correo (legado `LoginAPI.email`).

- `normalizeProfileMobilePhone(value: string): string`

### `src/api/reporting-api-request.ts`

**ReportingApiRequest** — fixture `apiRequest`, `uiApiRequest`

- `constructor(api: APIRequestContext)`
- `post(options: ApiPostOptions): Promise<ApiJsonObject>`
- `attachIfTestFailed(testInfo: TestInfo): Promise<void>`

Otros exports: `ApiJsonObject`

## `src/api/transfers/`

### `src/api/transfers/credin.api.ts`

**CredinApi** — fixture `credinApi`
: CREDIN (legado TransferenciasSteps.obtenerDatosDebitos/Credito, generarCredin, consultarCredin).

- `constructor(api: ReportingApiRequest, smsOperationOtp: SmsOperationOtpApi)`
- `findParty(session: AuthenticatedSession, cbu: string, owner: 'true' | ''): Promise<CredinParty>` — `owner` es `"true"` para la cuenta débito y `""` para la de crédito, como el legado.
- `generate(session: AuthenticatedSession, username: string, credin: CredinGeneration): Promise<void>` — Genera el CREDIN con OTP por SMS. Ejecuta la mutación una sola vez por intento.
- `countToday(session: AuthenticatedSession, searchAs: 'Destinatario' | 'Originante', originatorCuit: string, recipientCuit: string): Promise<number>` — Cantidad de CREDIN del día buscando como `Originante` o `Destinatario`.

Otros exports: `CredinGeneration`, `CredinParty`

### `src/api/transfers/own-account-transfer.api.ts`

**OwnAccountTransferApi** — fixture `ownAccountTransferApi`

- `constructor(api: ReportingApiRequest)`
- `transfer(session: AuthenticatedSession, testData: OwnAccountTransferData): Promise<OwnAccountTransferResult>`

Otros exports: `OwnAccountTransferResult`

### `src/api/transfers/third-party-transfers.api.ts`

**ThirdPartyTransfersApi** — fixture `thirdPartyTransfersApi`
: Transferencias a terceros, agenda de destinatarios y comprobantes (legado TransferenciasSteps). Cada mutación se ejecuta una sola vez por intento; el OTP usa la política central de segundo factor.

- `constructor(api: ReportingApiRequest, smsOperationOtp: SmsOperationOtpApi)`
- `findDestination(session: AuthenticatedSession, accountOrAlias: string, typeAccount: 'ALIAS' | 'CBU'): Promise<TransferDestination>`
- `removeDestination(session: AuthenticatedSession, idAdheredAccount: string): Promise<void>`
- `adhereDestination(session: AuthenticatedSession, username: string, belongsTo: string, cbu: string, ownAccount: unknown): Promise<string>` — Agenda el CBU con referencia `belongsTo` (el legado usa el ID del caso) y OTP por SMS.
- `transferToThirdParty(session: AuthenticatedSession, username: string, variant: ThirdPartyTransferVariant, destination: TransferDestination, origin: TransferOrigin, debitCard: TransferDebitCard): Promise<string>` — Transferencia inmediata a terceros con OTP; devuelve `idTransaction`.
- `scheduleToThirdParty(session: AuthenticatedSession, username: string, destination: TransferDestination, origin: TransferOrigin, debitCard: TransferDebitCard): Promise<string>` — Transferencia a terceros programada para mañana, con OTP; devuelve `idTransaction`.
- `transferToAdheredContact(session: AuthenticatedSession, destination: TransferDestination, idAdheredAccount: string, origin: TransferOrigin, debitCard: TransferDebitCard, belongsTo: string): Promise<void>` — Transferencia inmediata a un contacto agendado (sin OTP en el legado).
- `scheduleToAdheredContact(session: AuthenticatedSession, destination: TransferDestination, origin: TransferOrigin, debitCard: TransferDebitCard, belongsTo: string): Promise<void>` — Transferencia programada para mañana a un contacto agendado (sin OTP en el legado).
- `scheduleBetweenOwnAccounts(session: AuthenticatedSession, originAccountId: string, targetAccountId: string, debitCard: TransferDebitCard, cbuTarget: string): Promise<void>` — Transferencia programada entre cuentas propias (legado trasnferenciasProgramadaEntreCuentaPropias). `cbuTarget` es el CBU que el legado toma de la cuenta origen (`obtenerCbuCuenta(cuentaOrigen)`).
- `readTicket(session: AuthenticatedSession, idTransaction: string): Promise<TransferTicket>`

Otros exports: `ThirdPartyTransferVariant`, `TransferDebitCard`, `TransferDestination`, `TransferOrigin`, `TransferTicket`

### `src/api/transfers/transfer-history.api.ts`

**TransferHistoryApi** — fixture `transferHistoryApi`
: Historial de transferencias (legado TransferenciasSteps.validaHistorial*).

- `constructor(api: ReportingApiRequest)`
- `listLastYear(session: AuthenticatedSession): Promise<TransferHistoryPage>` — Último año hasta ahora (`historialTransferencias`: hasta `fechaHoy`).
- `listOneYearRange(session: AuthenticatedSession): Promise<TransferHistoryPage>` — Último año con ambos extremos calculados por días (`historialTransferenciasUnAño`).
- `searchLastSemester(session: AuthenticatedSession, searchedText: string): Promise<TransferHistoryPage>` — Últimos 180 días filtrando por nombre o descripción.

Otros exports: `TransferHistoryPage`

## `src/workflows/payments/`

### `src/workflows/payments/payment-services-preparation.workflow.ts`

**PaymentServicesPreparationWorkflow** — fixture `paymentServicesPreparationWorkflow`
: Preparación legada de pagos (NuevoPagoSteps.validarBotonMostrarTodos y validarServiciosUnisys): si faltan pagos o el servicio `MLPAYMENT`, los agenda, adhiere y paga antes del caso. Coordina el listado de pagos, el formulario de adhesión y el segundo factor.

- `constructor(payments: PaymentServicesPage, adhesion: ServiceAdhesionPage, smsOperationOtp: SmsOperationOtpApi)`
- `showAllPayments(user: PaymentServicesUser): Promise<void>` — Legado validarBotonMostrarTodos: deja abierto "Mostrar todos".
- `ensureService(user: PaymentServicesUser): Promise<void>` — Legado validarServiciosUnisys: agenda y adhiere el servicio si falta; termina con la flecha Volver.

- `paySelectedMercadoLibreServices(payments: PaymentServicesPage): Promise<void>` — Pago simultáneo de los dos primeros servicios Mercado Libre (recorrido de TC110847BERSA).

Otros exports: `PaymentServicesUser`

## `src/workflows/transfers/`

### `src/workflows/transfers/third-party-transfer.workflow.ts`

**ThirdPartyTransferWorkflow** — fixture `thirdPartyTransferWorkflow`
: Preparación común de las transferencias legadas a terceros (TransferenciasTest): coordina la agenda de destinatarios y los productos del usuario. Devuelve lo observado; las expectativas quedan en cada test.

- `constructor(transfers: ThirdPartyTransfersApi, productWidgets: ProductWidgetsApi)`
- `findDestinationOutsideAgenda(session: AuthenticatedSession, accountOrAlias: string, typeAccount: 'ALIAS' | 'CBU'): Promise<TransferDestination>` — Legado validar*Agendado + eliminarContactoAgendado: busca el destino y lo quita de la agenda si estaba.
- `adhereAndFindDestination(session: AuthenticatedSession, username: string, belongsTo: string, previous: TransferDestination): Promise<{ readonly destination: TransferDestination; readonly idAdheredAccount: string }>` — Legado agendarNuevoContacto + validarCbuAgendado: agenda el CBU con la referencia indicada y vuelve a buscarlo. Devuelve el destino actualizado y el id de agenda vigente.
- `loadOriginProducts(session: AuthenticatedSession, originAccount: string, debitCardLastDigits: string): Promise<TransferOriginProducts>` — Legado cargaServiciosCompartidos + obtenerIdDeCuentaOrigen/Currency/Label + tarjeta de débito.
- `readBalanceAfter(session: AuthenticatedSession, originAccount: string): Promise<number>` — Legado obtenerMontoActualizadoOrigen: vuelve a consultar cuentas y lee el saldo (sin la pausa fija).

Otros exports: `TransferOriginProducts`
