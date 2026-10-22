<!-- Generado por `pnpm run agent:index`. No editar a mano. -->

# Page Objects

Clases públicas de `src/pages/`. Reglas en `src/pages/AGENTS.md`.

## `src/pages/accounts/`

### `src/pages/accounts/account-alias.page.ts`

**AccountAliasPage** — fixture `accountAliasPage`

- `constructor(page: Page)`
- `openAliasEditor(account: string): Promise<void>`
- `isSaveDisabled(): Promise<boolean>`
- `enterAlias(alias: string): Promise<void>`

### `src/pages/accounts/account-movements.page.ts`

**AccountMovementsPage** — fixture `accountMovementsPage`

- `constructor(page: Page)`
- `openAccountDetails(account: string): Promise<void>`
- `applyCreationDateFilter(monthsBack: number, day: string): Promise<void>`
- `filterByTransactionType(): Promise<void>`
- `readFirstMovementDetail(): Promise<string>`
- `hasMovement(): Promise<boolean>`

### `src/pages/accounts/accounts.page.ts`

**AccountsPage** — fixture `accountsPage`
: Cuentas: detalle de movimientos y solicitud de cuenta nueva (legado CuentasSteps, DetalleCuentaSteps, SolicitarNuevaCuentaSteps y botones genéricos). Los locators reproducen el legado.

- `constructor(page: Page)`
- `open(): Promise<void>` — Menú lateral → Cuentas; cierra el cartel con "Cancelar" si aparece (legado clickBtnCancelarSobreCartelEmpezaAOperar).
- `isTextShown(text: string): Promise<boolean>`
- `openAccountDetail(account: string): Promise<void>` — "Ver más" de la cuenta (legado getVerMasXnroCuenta).
- `openMovementDetail(row: number): Promise<void>` — Abre el detalle del movimiento de la fila indicada (`div[aria-label='fila'][n]/div[5]`).
- `startNewAccountRequest(): Promise<void>`
- `isContinueEnabled(): Promise<boolean>`
- `isContinueShownDisabled(): Promise<boolean>` — Indica si "Continuar" se muestra deshabilitado (legado getBotonesDiables, espera 2 s).
- `selectAccountType(type: string): Promise<void>`
- `selectAccountCurrency(currency: string): Promise<void>`

### `src/pages/accounts/echeq.page.ts`

**EcheqPage** — fixture `echeqPage`

- `constructor(page: Page)`
- `openFromAccounts(): Promise<void>`
- `filterByIssuerStateAndLastSixMonths(cuit: string): Promise<void>`
- `openFilters(): Promise<void>` — Abre "Filtrar" (legado clickbtnFiltrar).
- `isTextShown(text: string): Promise<boolean>`
- `isLabelShown(text: string, element: 'div' | 'span'): Promise<boolean>` — Indica si un `span` o `div` con el texto se ve (etiquetas de filtros del legado).
- `applyFilters(): Promise<void>`
- `searchByNumberAndPendingState(echeqNumber: string): Promise<void>` — Busca por número de eCheq y estado "Emitido pendiente" (legado TC124865).
- `openFirstDetail(): Promise<void>`
- `startAcceptance(): Promise<void>` — Aceptar el eCheq desde el detalle (botón genérico "Aceptar").
- `continueToSecondFactor(): Promise<void>` — Continuar la aceptación y esperar el segundo factor.
- `enterSecondFactorCode(code: string): Promise<void>`
- `readVisibleRows(): Promise<readonly EcheqRow[]>`
- `readEmissionDateRange(): Promise<EcheqDateRange>`

Otros exports: `EcheqDateRange`, `EcheqRow`

### `src/pages/accounts/salary-advance.page.ts`

**SalaryAdvancePage** — fixture `salaryAdvancePage`

- `constructor(page: Page)`
- `openRequest(account: string): Promise<void>` — Cuentas → detalle de la cuenta → Más Opciones → Solicitud Adelanto de Sueldo.
- `hasOfferAvailable(): Promise<boolean>` — Indica si la pantalla muestra una oferta; el caso decide qué textos corresponden en cada estado.
- `isTextShown(text: string): Promise<boolean>`
- `isContinueEnabled(): Promise<boolean>`
- `fillRequestedAmount(amount: string): Promise<void>` — "¿Cuánto necesitás?" (`#amount`).
- `pressContinue(): Promise<void>`
- `pressConfirm(): Promise<void>`
- `goBackWithArrow(): Promise<void>` — Flecha "Volver" (legado clickVolverFlechaGenerico); espera la solicitud de adelanto.
- `isAmountErrorShown(): Promise<boolean>` — Indica si se ve la notificación de error de monto permitido (legado validarAlertMontoPermitido).
- `openContractSummary(): Promise<void>`
- `acceptContractRedirect(): Promise<ContractRedirectResult>`
- `cancelContractRedirect(): Promise<void>`

Otros exports: `ContractRedirectResult`

## `src/pages/authentication/`

### `src/pages/authentication/login.page.ts`

**LoginPage** — fixture `loginPage`

- `constructor(page: Page)`
- `authenticate(credentials: LoginCredentials): Promise<AuthenticatedSession>`
- `authenticateWithVirtualKeyboard(credentials: LoginCredentials): Promise<AuthenticatedSession>` — Igual que `authenticate`, pero ingresa la contraseña con el teclado virtual de la pantalla.
- `typeUsername(username: string): Promise<void>` — Carga el usuario en la pantalla de login sin continuar (legado setUsuario).
- `openUserRecovery(): Promise<void>` — "Recuperar usuario" (legado clickRecuperarUsuario); espera el campo CUIL.
- `fillRecoveryCuil(cuil: string): Promise<void>`
- `isRecoveryContinueEnabled(): Promise<boolean>` — Estado del botón submit del recupero (legado getContinuarLogin).
- `continueUserRecovery(): Promise<void>` — Continuar del recupero (legado clickContinuarGenerico).
- `isTextShown(text: string): Promise<boolean>`
- `returnToSignIn(): Promise<void>` — "Iniciar sesión" tras el recupero (legado clickBotonIniciarSesion).
- `openSecurityInformation(): Promise<ExternalPageSummary>` — Abre "Información de seguridad" desde la pantalla de usuario y lee el título de la página externa.
- `openStoreShortcut(): Promise<StoreShortcutResult>` — Abre el acceso directo "Tienda.Clic" y confirma que la página externa muestra su logo.

Otros exports: `ExternalPageSummary`, `StoreShortcutResult`

## `src/pages/cards/`

### `src/pages/cards/credit-card-payment.page.ts`

**CreditCardPaymentPage** — fixture `creditCardPaymentPage`

- `constructor(page: Page)`
- `open(): Promise<void>`
- `openOwnCardPayment(creditCard: string): Promise<void>`
- `selectDebitCard(debitCardLastFour: string): Promise<void>`
- `continueToPaymentDetails(): Promise<void>`
- `selectDebitAccount(originAccount: string): Promise<void>`
- `selectPaymentType(paymentType: string): Promise<void>`
- `openPaymentReview(): Promise<void>`
- `readPaymentReview(): Promise<string>`

### `src/pages/cards/credit-card-tracking.page.ts`

**CreditCardTrackingPage** — fixture `creditCardTrackingPage`
: Seguimiento de tarjetas de crédito (legado TarjetasDeCreditoSteps / TarjetasDeCreditoPage).

- `constructor(page: Page)`
- `open(): Promise<void>` — Desde Tarjetas de crédito abre "Seguimiento de tarjetas" (`/creditCard/creditCardTracking`).
- `isTextShown(text: string): Promise<boolean>`
- `findMissingTexts(texts: readonly string[]): Promise<readonly string[]>` — Textos de la lista que no se ven en pantalla.
- `openFirstDetail(): Promise<void>` — Ingresa al detalle del primer seguimiento (icono de flecha derecha).
- `downloadDetail(): Promise<string>` — Descarga el detalle del seguimiento y devuelve el nombre del archivo.
- `printDetail(): Promise<boolean>` — Presiona imprimir. Si se abre otra pestaña, la cierra (legado ManageTab, sin assertion). Devuelve si se abrió una pestaña.

### `src/pages/cards/credit-cards.page.ts`

**CreditCardsPage** — fixture `creditCardsPage`

- `constructor(page: Page)`
- `open(): Promise<void>`
- `openDetail(lastDigits: string): Promise<void>` — Abre el detalle de la tarjeta identificada por sus últimos dígitos.
- `readDetailTitle(): Promise<string>` — Devuelve el título del detalle tal como lo anuncia la pantalla (alias y número de tarjeta).
- `isStatementDownloadAvailable(): Promise<boolean>`
- `isPeriodMovementsAvailable(): Promise<boolean>`
- `isAdditionalCardRequestAvailable(): Promise<boolean>`
- `openDetailByLabel(label: string): Promise<void>` — Abre el detalle desde la tarjeta cuyo texto contiene la etiqueta (locator legado getVerMasXnroTarjeta).
- `goBackToList(): Promise<void>` — Vuelve al listado con la flecha "Volver" (legado clickVolverFlechaGenerico).
- `findMissingTexts(texts: readonly string[]): Promise<readonly string[]>` — Textos de la lista que no se ven en la pantalla actual (legado `//*[contains(text(), ...)]`).
- `isTextShown(text: string): Promise<boolean>`
- `openStatementDownloads(): Promise<void>` — Abre "Descargar Resúmenes" desde el detalle y espera el primer período.
- `downloadFirstPeriodStatement(): Promise<string>` — Despliega el primer período y descarga su resumen; devuelve el nombre del archivo descargado.
- `startTheftReport(): Promise<void>` — Detalle → Opciones → Denunciar robo o extravío → Robo → Continuar.

- `downloadFromFirstIcon(page: Page): Promise<string>` — Descarga desde el primer "Icono de descargar" y devuelve el nombre sugerido del archivo.

### `src/pages/cards/new-credit-card-request.page.ts`

**NewCreditCardRequestPage** — fixture `newCreditCardRequestPage`
: Solicitud de tarjeta de crédito nueva (legado TarjetasDeCreditoSteps / TarjetasDeCreditoPage).

- `constructor(page: Page)`
- `open(): Promise<void>` — Desde Tarjetas de crédito abre la solicitud (`/creditCards/newCreditCard`).
- `isTextShown(text: string): Promise<boolean>`
- `selectDebitAccountIfShown(account: string): Promise<void>` — Elige la cuenta débito si el desplegable está visible (legado seleccionarCuentaDebitoSiEstaPresente).
- `declareUifObligatedSubject(): Promise<void>` — Marca la declaración "Sujeto Obligado UIF".
- `continue(): Promise<void>`
- `requestBranchAppointment(): Promise<Page>` — "Solicitá tu turno acá." → Aceptar. Devuelve la pestaña que muestra Citas: la nueva si se abrió dentro del límite, o la actual (legado ManageTab).
- `isTextShownIn(target: Page, text: string): Promise<boolean>`

## `src/pages/documents/`

### `src/pages/documents/product-documents.page.ts`

**ProductDocumentsPage** — fixture `productDocumentsPage`
: Administración de productos: consulta y descarga de contratos (legado AdministracionDeProductosSteps).

- `constructor(page: Page)`
- `open(): Promise<void>` — Navega a la ruta legada `digitalFilesConsult` dentro de la sesión iniciada.
- `selectProduct(product: string): Promise<void>` — Abre `#contractsSelector` y elige el producto por texto (legado keyDownToText).
- `downloadDocument(): Promise<Download>` — "Consultar" (legado descargarArchivo) y devuelve la descarga producida.

## `src/pages/error/`

### `src/pages/error/ups-error.page.ts`

**UpsErrorPage**

- `constructor(page: Page)`
- `readIfPresent(): Promise<UpsErrorDetails | undefined>`

Otros exports: `UpsErrorDetails`

## `src/pages/feedback/`

### `src/pages/feedback/opinion-survey.page.ts`

**OpinionSurveyPage**

- `constructor(page: Page)`
- `dismissWhenShown(): Promise<void>`

## `src/pages/home/`

### `src/pages/home/home.page.ts`

**HomePage** — fixture `homePage`

- `constructor(page: Page)`
- `open(): Promise<void>`
- `readGreeting(): Promise<string>`
- `readAlert(): Promise<HomeAlert>` — Espera el aviso informativo del inicio y devuelve su icono y color de fondo renderizados.

Otros exports: `HomeAlert`

## `src/pages/insurance/`

### `src/pages/insurance/insurance.page.ts`

**InsurancePage** — fixture `insurancePage`

- `constructor(page: Page)`
- `open(): Promise<void>`
- `isInsuranceCardAvailable(name: string): Promise<boolean>` — Indica si el listado de seguros muestra una tarjeta cuyo texto contiene el nombre indicado.
- `openRobberyInsuranceDetail(): Promise<void>`
- `continueRequest(): Promise<void>` — Continuar la solicitud del seguro (botón genérico "Continuar" dentro de la aplicación de seguros).
- `acceptTerms(): Promise<void>` — Acepta términos y condiciones (legado clickCheckTerminosCondicionesGenerico).
- `isButtonShown(label: string): Promise<boolean>` — Indica si se ve el botón con ese aria-label ("Confirmar", "Cancelar").
- `readDetail(): Promise<InsuranceDetail>`

Otros exports: `InsuranceDetail`

## `src/pages/investments/`

### `src/pages/investments/commitent-account-opening.page.ts`

**CommitentAccountOpeningPage** — fixture `commitentAccountOpeningPage`
: Apertura de cuenta comitente (legado CuentaComitenteSteps / CuentaComitentePage y botones genéricos). Los locators reproducen los del legado; su vigencia en BERSA QA está pendiente de validación UI.

- `constructor(page: Page)`
- `requestAccount(): Promise<void>`
- `continue(): Promise<void>`
- `declareNoneOfTheAbove(): Promise<void>` — Marca "Ninguna de las anteriores" en las declaraciones (`span#nao`).
- `checkTermsAndConditions(): Promise<void>`
- `acceptTermsAndConditions(): Promise<void>`
- `confirm(): Promise<void>`
- `isTextShown(text: string): Promise<boolean>`
- `isStepShown(stepNumber: string, stepTitle: string): Promise<boolean>` — Indica si se ve la etapa con su número y título (legado validarNumyTextDeEtapa).

### `src/pages/investments/fixed-terms.page.ts`

**FixedTermsPage** — fixture `fixedTermsPage`
: Plazo fijo: consulta de tasas y formulario de solicitud (legado PlazosFijosPage, ConsultaDeTasasPage, SolicitarPlazoFijoPage y botones genéricos). Los locators reproducen los del legado; la navegación por menú y su vigencia en BERSA QA están pendientes de validación UI.

- `constructor(page: Page)`
- `open(): Promise<void>` — Inversiones → Gestioná tus inversiones → Plazo fijo; espera la ruta `/fixedTerms`.
- `isTextShown(text: string): Promise<boolean>`
- `openRatesQuery(): Promise<void>`
- `selectRateQueryType(option: string): Promise<void>` — Elige el tipo de plazo fijo en la consulta de tasas (opción que contiene el texto).
- `fillRateQueryAmount(amount: string): Promise<void>`
- `searchRates(): Promise<void>`
- `readVisibleRateColumns(): Promise<readonly RateResultColumn[]>` — Columnas del resultado de la consulta que se ven en pantalla.
- `openRequest(): Promise<void>`
- `isContinueEnabled(): Promise<boolean>`
- `selectDebitAccount(account: string): Promise<void>` — Abre "Cuenta débito" y elige la cuenta (`#account`).
- `readSelectedDebitAccountText(): Promise<string>` — Texto mostrado para la cuenta débito seleccionada, que incluye su saldo.
- `selectType(option: string): Promise<void>` — Abre el selector de tipo y elige la opción que contiene el texto.
- `selectUvaType(): Promise<void>` — Abre el selector de tipo y elige exactamente "Plazo Fijo UVA" (legado clickPlazoFijoUVA).
- `typeAmount(amount: string): Promise<void>`
- `clearAmount(): Promise<void>` — Borra el importe con retrocesos, como el legado (clearCampoImporte).
- `typeTermDays(days: string): Promise<void>`
- `selectMaturityAction(option: string): Promise<void>`
- `pressContinue(): Promise<void>` — Presiona Continuar si está disponible (legado clickContinuarGenerico, que no falla si no avanza).
- `isFormErrorShown(): Promise<boolean>` — Indica si se muestra el mensaje de error del formulario (locator posicional del legado).
- `goBack(): Promise<void>` — Vuelve con la flecha "Volver" cuando no hay notificación de éxito visible.

Otros exports: `RateResultColumn`

### `src/pages/investments/foreign-currency-purchase.page.ts`

**ForeignCurrencyPurchasePage** — fixture `foreignCurrencyPurchasePage`

- `constructor(page: Page)`
- `open(): Promise<void>`
- `completeForm(form: ForeignCurrencyPurchaseForm): Promise<void>`
- `continue(): Promise<void>`
- `readValidationMessage(): Promise<string>`
- `selectOperationType(label: string): Promise<void>` — Elige el tipo de operación cuyo texto contiene el valor (legado seleccionarDropdownGenerico).
- `selectOriginAccount(label: string): Promise<void>` — Elige la cuenta origen cuyo texto contiene el valor.
- `selectDestinationAccount(label: string): Promise<void>` — Elige la cuenta destino cuyo texto contiene el valor (por ejemplo `USD` o `$`).
- `typeAmount(amount: string): Promise<void>` — Escribe el importe como el legado (`sendKeys` sobre `#userAmount`).
- `pressContinue(): Promise<void>` — Presiona Continuar sin exigir un mensaje de validación (legado clickContinuarGenerico).
- `pressConfirm(): Promise<void>`
- `isMessageShown(text: string): Promise<boolean>` — Indica si algún elemento muestra el texto (legado `//*[contains(text(), ...)]`).
- `accountOptionsInclude(field: 'destination' | 'origin', text: string): Promise<boolean>` — Abre el desplegable de cuenta indicado e informa si muestra alguna opción que contiene el texto (legado validarDesplegableCuentasEn* + keyDownXParametrosGenerico). Cierra el desplegable al terminar.

Otros exports: `ForeignCurrencyPurchaseForm`

### `src/pages/investments/investment-funds.page.ts`

**InvestmentFundsPage** — fixture `investmentFundsPage`

- `constructor(page: Page, baseUrl: string)`
- `open(): Promise<void>`
- `selectOnlyRisk(selectedRisk: FundRisk): Promise<void>`
- `getVisibleRisks(): Promise<readonly FundRisk[]>`
- `selectOnlyCurrency(selectedCurrency: FundCurrency): Promise<void>` — Deja seleccionado únicamente el filtro de moneda indicado (legado seleccionarUnicamenteFiltrosPorMoneda).
- `getVisibleFundCurrencyTexts(): Promise<readonly string[]>` — Texto de la segunda celda de cada fila visible, donde el legado lee la moneda del fondo (`//*[@aria-label='fila']/div[2]/p`).
- `showMyFunds(): Promise<void>` — Activa el selector "Mis fondos" (legado clickLabelMisFondos).
- `isFundListShown(): Promise<boolean>` — Indica si se muestra el listado de la tabla de fondos (legado validaListadoFCI).
- `openMyFundDetail(fundName: string): Promise<void>` — Abre el detalle de un fondo desde su menú de acciones y elige "Mi fondo" (legado clickIconoAccionesDetalleFCI).
- `openMoreInformation(): Promise<void>` — Abre "Más Información" del fondo y espera su botón Aceptar.
- `acceptMoreInformation(): Promise<boolean>` — Acepta "Más Información". Si se abre una pestaña nueva, la cierra y vuelve a la principal (legado UtilsGP.ManageTab + backMainTap). Devuelve si se abrió una pestaña.
- `cancelIfShown(): Promise<void>` — Presiona Cancelar si está visible (legado clickCancelarGenerico, sin assertion).
- `startCommitentAccountRequest(): Promise<void>` — Inicia la solicitud de una nueva cuenta comitente desde Información de fondos.

Otros exports: `FUND_CURRENCIES`, `FundCurrency`, `InvestmentFundsTestDataUnavailableError`, `InvestmentFundsUnavailableError`

## `src/pages/loans/`

### `src/pages/loans/loan-request.page.ts`

**LoanRequestPage** — fixture `loanRequestPage`
: Solicitud de préstamos (legado PrestamosSteps / PrestamosParaCancelarDeudasSteps y sus pages).

- `constructor(page: Page)`
- `openNewLoan(): Promise<void>` — Menú lateral "Préstamos" → "Nuevo préstamo".
- `openDebtCancellationLoan(): Promise<void>` — Menú lateral "Préstamos" → "Préstamo para cancelar deudas".
- `isTextShown(text: string): Promise<boolean>`
- `isStepShown(title: string): Promise<boolean>` — Indica si se ve la etapa con ese título (`//p[contains(text(), ...)]`).
- `fillAmount(amount: string): Promise<void>`
- `next(): Promise<void>`
- `selectDestination(destination: string): Promise<void>`
- `continue(): Promise<void>`
- `selectOneInstallment(): Promise<void>` — Elige 1 cuota (`label[for='1']`).
- `acceptTerms(): Promise<void>`
- `selectFirstDebt(): Promise<void>` — Marca el primer préstamo a cancelar.
- `openContractSummary(): Promise<string>` — "Resumen de contrato" → Aceptar; devuelve la URL de la pestaña abierta (legado validarFragmentoURL).

## `src/pages/messages/`

### `src/pages/messages/messages.page.ts`

**MessagesPage** — fixture `messagesPage`

- `constructor(page: Page)`
- `open(): Promise<void>`
- `readFirstMessage(): Promise<MessageSummary>` — Lee asunto y fecha del primer mensaje de la bandeja Recibidos, que identifican a ese mensaje.
- `deleteFirstMessage(): Promise<void>` — Elimina el primer mensaje y espera a que la bandeja deje de mostrarlo en primer lugar.
- `ensureReadMessageAvailable(): Promise<void>`
- `markFirstReadMessageAsUnread(): Promise<string>`
- `readUnreadMessageSubjects(): Promise<readonly string[]>`
- `restoreMessageAsRead(messageSubject: string): Promise<void>`

Otros exports: `MessageSummary`

## `src/pages/mis-products/`

### `src/pages/mis-products/cancellation.page.ts`

**CancellationPage** — fixture `cancellationPage`

- `constructor(page: Page)`
- `open(): Promise<void>`
- `isConfirmationAvailable(): Promise<boolean>`
- `acceptExternalRedirect(): Promise<CancellationRedirectResult>`

Otros exports: `CancellationRedirectResult`

## `src/pages/navigation/`

### `src/pages/navigation/side-menu.ts`

**SideMenu**
: Menú lateral de Home Banking. Sólo contiene la mecánica de navegación: cada Page Object de destino espera su propio encabezado como señal de pantalla lista.

- `constructor(page: Page)`
- `openHome(): Promise<void>`
- `openAccounts(): Promise<void>`
- `openTransfers(): Promise<void>`
- `openPayments(): Promise<void>`
- `openCreditCards(): Promise<void>`
- `openInsurance(): Promise<void>`

## `src/pages/payments/`

### `src/pages/payments/debin-seller.page.ts`

**DebinSellerPage** — fixture `debinSellerPage`

- `constructor(page: Page)`
- `open(): Promise<void>` — Pagos → Más Opciones → Debin vendedor.
- `openOrderGeneration(): Promise<void>`
- `selectDestinationAccount(account: string): Promise<void>`
- `enterAmount(amount: string): Promise<void>`
- `selectBuyerIdentifierType(type: BuyerIdentifierType): Promise<void>`
- `enterBuyerIdentifier(value: string): Promise<void>`
- `search(): Promise<void>`
- `isRequiredFieldMessageShown(): Promise<boolean>`

Otros exports: `BuyerIdentifierType`

### `src/pages/payments/payment-receipts.page.ts`

**PaymentReceiptsPage** — fixture `paymentReceiptsPage`
: Comprobantes de pago (legado ComprobantesSteps).

- `constructor(page: Page)`
- `isTitleShown(): Promise<boolean>`
- `selectOriginAndContinue(option: string): Promise<void>` — Despliega "Seleccionar", elige la opción y continúa (legado clickListaDesplegableGenerico).
- `searchByCompanyAndDescription(company: string, description: string): Promise<void>`
- `isReceiptShown(code: string): Promise<boolean>` — Indica si el resultado muestra el comprobante (legado validarComprobante).

### `src/pages/payments/payment-services.page.ts`

**PaymentServicesPage** — fixture `paymentServicesPage`
: Pago de servicios (legado PagosSteps, NuevoPagoSteps y botones genéricos de pagos).

- `constructor(page: Page)`
- `open(): Promise<void>` — Menú lateral → Pagos y servicios → Pagos; espera "Pago de servicios".
- `isTextShown(text: string): Promise<boolean>`
- `isShowAllShown(): Promise<boolean>`
- `showAll(): Promise<void>`
- `isServiceShown(service: string): Promise<boolean>` — Indica si el listado muestra el servicio con esa descripción (`//p[contains(text(), …)]`).
- `openSpanOption(text: string): Promise<void>` — Abre una sección o acción con texto en `span` (por ejemplo "Servicios agendados", "Mis Comprobantes").
- `openParagraphOption(text: string): Promise<void>` — Abre una acción con texto en `p` (por ejemplo "Agendar servicio", "Adherir servicio").
- `selectServiceToPay(position: number): Promise<void>` — Marca el servicio a pagar en esa posición (locator legado por "Mercado Pago").
- `pressPay(): Promise<void>`
- `fillAmountAndReference(amount: string, reference: string): Promise<void>`
- `pressContinue(): Promise<void>`
- `pressConfirm(): Promise<void>`
- `goBack(): Promise<void>` — "Volver" con texto (legado clickVolverGenerico).
- `goBackWithArrow(): Promise<void>` — Flecha "Volver" (legado clickVolverFlechaGenerico).

### `src/pages/payments/service-adhesion.page.ts`

**ServiceAdhesionPage** — fixture `serviceAdhesionPage`
: Formulario para agendar o adherir un servicio (legado AdherirServicioSteps y TicketSteps).

- `constructor(page: Page)`
- `submit(category: string, company: string, companyCode: string, description: string): Promise<void>` — Completa rubro, empresa, código y descripción; Continuar y Confirmar hasta el segundo factor.
- `enterSecondFactorCode(code: string): Promise<void>`
- `readTicketStatus(): Promise<string>` — Estado del ticket sin acentos (legado TicketSteps.validarTicket).

## `src/pages/procedures/`

### `src/pages/procedures/appointments.page.ts`

**AppointmentsPage** — fixture `appointmentsPage`

- `constructor(page: Page)`
- `openExternalAppointments(): Promise<string>`

## `src/pages/profile/`

### `src/pages/profile/change-address.page.ts`

**ChangeAddressPage** — fixture `changeAddressPage`

- `constructor(page: Page)`
- `open(): Promise<void>`
- `completeAddress(address: AddressChangeData): Promise<AddressChangeData>`
- `requestInitialSecondFactorCode(): Promise<void>`
- `requestReplacementSecondFactorCode(): Promise<void>`
- `readReviewedAddress(): Promise<string>`
- `enterSecondFactorCode(code: string): Promise<void>`
- `readAddressChangeResult(): Promise<string>`

### `src/pages/profile/security-seal.page.ts`

**SecuritySealPage** — fixture `securitySealPage`
: Cambio de sello de seguridad desde "Mi Perfil" (legado PerfilSteps / ModificarPerfilSteps).

- `constructor(page: Page)`
- `open(): Promise<void>` — Perfil (`#user`) → "Ir a mi perfil"; cierra la publicidad de préstamo si aparece.
- `openSealChange(): Promise<void>`
- `selectSeal(position: number): Promise<void>` — Elige el sello en esa posición (`(//div[@class='is-current']/../div)[n]`) y espera el segundo factor.
- `chooseSmsIfOtherMethodOffered(): Promise<void>` — Si se ofrece "Intentar con otro" método, elige SMS (legado codigoSegundoFactor).
- `enterSecondFactorCode(code: string): Promise<void>`

## `src/pages/registration/`

### `src/pages/registration/identity-validation.page.ts`

**IdentityValidationPage** — fixture `identityValidationPage`

- `constructor(page: Page)`
- `isAvailable(): Promise<boolean>`

## `src/pages/start-page/`

### `src/pages/start-page/start.page.ts`

**StartPage** — fixture `startPage`

- `constructor(page: Page, baseUrl: string)`
- `open(): Promise<void>`
- `openLogin(): Promise<void>`
- `openRegistration(): Promise<void>`
- `isRegistrationAvailable(): Promise<boolean>`

## `src/pages/transfers/`

### `src/pages/transfers/add-contact.page.ts`

**AddContactPage** — fixture `addContactPage`

- `constructor(page: Page)`
- `open(): Promise<void>` — Llega a "Agendar contacto" desde Transferencias → A un contacto agendado → Agregar contacto.
- `search(cbuOrAlias: string): Promise<void>` — Busca un CBU, CVU o alias tal como lo recibe el escenario.
- `isMessageShown(message: string): Promise<boolean>`

### `src/pages/transfers/contact-agenda.page.ts`

**ContactAgendaPage** — fixture `contactAgendaPage`
: Agenda completa de contactos (legado AgendaNuevoDestinatarioSteps / AgendaNuevoDestinatarioPage).

- `constructor(page: Page)`
- `addContact(cbu: string): Promise<void>` — "Agregar contacto nuevo" → CBU → Buscar → Son correctos; espera el segundo factor.
- `enterSecondFactorCode(code: string): Promise<void>` — Ingresa el código y confirma el alta del contacto.
- `transferToThisContact(): Promise<void>` — "Transferir a este contacto" luego del alta.
- `removeContactIfListed(cbu: string, emptyListText: string): Promise<boolean>` — Busca el contacto y, si el listado no informa el texto de vacío, lo elimina desde el menú de acciones (legado eliminaContactoEnAgenda). Devuelve si eliminó un contacto.

### `src/pages/transfers/credin-request.page.ts`

**CredinRequestPage** — fixture `credinRequestPage`
: Generar CREDIN por UI (legado ConsultaCredinSteps / GenerarCredinSteps y sus pages).

- `constructor(page: Page)`
- `open(): Promise<void>`
- `isDebitAccountOffered(account: string): Promise<boolean>` — Abre "Cuenta débito" y comprueba que se muestre la cuenta, sin elegirla (legado clickCuentaDebito + keyDownXParametrosGenerico(0, cuenta)).
- `fillDestinationCbu(cbu: string): Promise<void>`
- `isConceptOffered(concept: string): Promise<boolean>` — Abre el concepto y comprueba que se ofrezca la opción, sin elegirla (mismo patrón legado).
- `fillAmountAndDescription(amount: string, description: string): Promise<void>`
- `continueAsOwnAccount(): Promise<void>` — Marca "Es cuenta propia", Continuar y acepta términos; espera el segundo factor.
- `enterSecondFactorCode(code: string): Promise<void>`

### `src/pages/transfers/own-account-transfer.page.ts`

**OwnAccountTransferPage** — fixture `ownAccountTransferPage`

- `constructor(page: Page)`
- `open(): Promise<void>`
- `selectRecipientByCbu(destinationCbu: string): Promise<void>`
- `completeDetails(details: OwnAccountTransferDetails): Promise<void>`
- `readReview(): Promise<string>`
- `confirm(): Promise<void>`
- `readTicket(): Promise<string>`

Otros exports: `OwnAccountTransferDetails`

### `src/pages/transfers/transfer-form.page.ts`

**TransferFormPage** — fixture `transferFormPage`
: Formulario de transferencias inmediatas/programadas, ticket y segundo factor por UI (legado TransferenciasInmediatasSteps, TicketSteps y DobleFactorSteps). Reutiliza los locators validados del testigo TC18088BERSA para importe, descripción, búsqueda y botones; el resto reproduce el legado.

- `constructor(page: Page)`
- `open(): Promise<void>` — Menú lateral → Transferencias; descarta el aviso "Ahora no" si aparece (legado clickBtnAhoraNo).
- `isTextShown(text: string): Promise<boolean>`
- `selectTransferType(type: TransferType): Promise<void>`
- `searchRecipient(cbuOrAlias: string): Promise<void>` — Busca el CBU, CVU o alias y confirma "Son correctos" (recorrido del testigo TC18088BERSA).
- `readOriginBalanceText(): Promise<string>` — Texto del saldo mostrado para la cuenta origen (legado getCuentaOrigenDesplegableSaldo1).
- `selectFirstOriginAccount(): Promise<void>` — Abre "Vas a transferir desde" y elige la primera cuenta del menú (legado seleccionarCtaOrigen).
- `selectOwnDestination(account: string): Promise<void>` — Elige la cuenta propia destino: por "Agenda completa" si está disponible o, si no, desde el carrusel (legado seleccionarCuentaDestinoCuentasPropias).
- `fillAmount(amount: string): Promise<void>`
- `selectConcept(concept: string): Promise<void>` — Abre el concepto y elige la opción que contiene el texto.
- `fillDescription(description: string): Promise<void>`
- `transferNow(): Promise<void>`
- `scheduleForSpecificDate(): Promise<void>` — "Programar para otro día" → "En una fecha específica" → Continuar.
- `confirm(): Promise<void>`
- `confirmAndWaitForSecondFactor(): Promise<void>` — Confirma y espera el campo del segundo factor (el código se resuelve por la política central).
- `waitForSecondFactor(): Promise<void>`
- `enterSecondFactorCode(code: string): Promise<void>` — Ingresa el código y confirma (legado codigoSegundoFactor, sin su reintento interno).
- `readTicketStatus(): Promise<string>` — Estado del ticket sin acentos (legado TicketSteps.validarTicket).
- `readTicketField(label: string): Promise<string>` — Valor del campo del ticket sin acentos (legado validarCampoValorTicket).
- `downloadTicket(): Promise<string>` — Descarga el ticket y devuelve el nombre del archivo.
- `goBack(): Promise<void>` — "Volver" cuando no queda notificación de éxito visible (legado clickVolverGenerico).
- `goBackWithArrow(): Promise<void>` — Flecha "Volver" (legado clickVolverFlechaGenerico); espera las opciones de Transferencias.
- `openHistory(): Promise<void>` — Abre "Ver mis transferencias".
- `openFullAgenda(): Promise<void>` — Abre "Agenda completa" luego de elegir "A un contacto agendado".
- `openCredins(): Promise<void>` — Opciones → Consultar CREDIN.

Otros exports: `TRANSFER_TYPES`, `TransferType`

### `src/pages/transfers/transfer-history.page.ts`

**TransferHistoryPage** — fixture `transferHistoryPage`
: "Ver mis transferencias" (legado TransferenciasInmediatasSteps: filtros, grilla y paginador).

- `constructor(page: Page)`
- `openFilters(): Promise<void>`
- `readDateFilters(): Promise<HistoryDateFilters>`
- `changeDateFilters(): Promise<void>` — Cambia ambas fechas eligiendo el primer día visible del calendario (legado getdatePicker).
- `clearFilters(): Promise<void>`
- `countRows(): Promise<number>` — Cantidad de filas visibles de la grilla (`div[aria-label='fila']`).
- `isPaginatorShown(): Promise<boolean>`
- `findMovementDateOfMonth(month: string): Promise<string | undefined>` — Recorre las páginas hasta encontrar un movimiento del mes indicado (`/MM/`) y devuelve su fecha, o `undefined` si no hay más páginas (legado buscarMovimientosConAntiguedadDeDosMeses).
- `goBack(): Promise<void>` — Vuelve con la flecha "Volver" a Transferencias.

Otros exports: `HistoryDateFilters`

### `src/pages/transfers/util-logger.page.ts`

**UtilLoggerPage** — fixture `utilLoggerPage`
: Herramienta de diagnóstico "utilLogger" de QA (legado DobleFactorSteps.clickBtnUtilLogger y UtilLoggerSteps). Lee los requests registrados para un servicio.

- `constructor(page: Page)`
- `readServiceRequests(service: string): Promise<readonly string[]>` — Abre utilLogger en la pestaña nueva, actualiza y devuelve el cuerpo de cada request del servicio. Cierra la pestaña al terminar (legado switchTab + backMainTap).

### `src/pages/visibility.ts`

- `becomesVisible(locator: Locator, timeout?): Promise<boolean>` — Espera a que un elemento sea visible y devuelve el resultado como dato simple. Permite que el test exprese la expectativa sin conocer el locator ni capturar timeouts.
