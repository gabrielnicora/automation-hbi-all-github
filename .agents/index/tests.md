<!-- Generado por `pnpm run agent:index`. No editar a mano. -->

# Tests

Fuente: `playwright test --list` (títulos resueltos, incluidos los parametrizados) y las
fixtures que cada archivo pide en sus callbacks. Para un caso nuevo, buscar acá un test del
mismo patrón y la especificación de la capacidad.

## `tests/api/accounts/`

Especificaciones: `specs/accounts/withdrawal-order.api.plan.md`

### `tests/api/accounts/should-adhere-new-destination.api.spec.ts`

Fixtures: `accountTransferSearchApi`, `adheredAccountsApi`, `loginApi`, `loginCredentials`, `productWidgetsApi`

- L10: TC0006BERSA - Adherir cuenta - nuevo destinatario de terceros con segundo factor por SMS

### `tests/api/accounts/should-generate-withdrawal-order.api.spec.ts`

Fixtures: `loginApi`, `loginCredentials`, `withdrawalOrderApi`, `withdrawalOrderData`

- L8: TC0027BERSA - genera una orden de extracción con saldo disponible

### `tests/api/accounts/should-list-echeqs-by-status.api.spec.ts`

Fixtures: `echeqOperationsApi`, `loginApi`, `loginCredentials`

- L15: TC124909BERSA - Consulta del detalle de un eCheq - Estado 'Emitir'
- L15: TC124913BERSA - Consulta del detalle de un eCheq - Estado Anular
- L15: TC25610BERSA - Validar el listado de eCheq en el estado 'Todos'

### `tests/api/accounts/should-operate-received-echeqs.api.spec.ts`

Fixtures: `echeqEndorsementData`, `echeqOperationsApi`, `loginApi`, `loginCredentials`, `originAccountData`, `productWidgetsApi`, `secondUserCredentials`

- L47: TC124907BERSA - Consulta del detalle de un eCheq - Estado Aceptar
- L64: TC124908BERSA - Consulta del detalle de un eCheq - Estado Endosar
- L86: TC124910BERSA - Consulta del detalle de un eCheq - Estado Repudiar
- L109: TC124911BERSA - Consulta del detalle de un eCheq - Estado Custodiar
- L132: TC124912BERSA - Consulta del detalle de un eCheq - Custodiar y rescatar
- L157: TC128237BERSA - Solicitar devolución de eCheq
- L190: TC128231BERSA - Depositar eCheq - Variable encendida

### `tests/api/accounts/should-reject-invalid-account-transfer-search.api.spec.ts`

Fixtures: `accountTransferSearchApi`, `loginApi`, `loginCredentials`

- L315: TC29450BERSA - Validar mensaje de error typeAccount con espacio
- L315: TC29451BERSA - Validar error por typeAccount con acento
- L315: TC29452BERSA - Validar error por typeAccount con guión
- L315: TC29453BERSA - Validar error por typeAccount con longitud no permitida
- L315: TC29454BERSA - Validar error por typeAccount con apóstrofe
- L315: TC29455BERSA - Validar error por typeAccount vacío
- L315: TC29456BERSA - Validar error por typeAccount null
- L315: TC29457BERSA - Validar error por typeAccount alfabético no permitido
- L315: TC29458BERSA - Validar error por typeAccount numérico
- L315: TC29459BERSA - Validar error por typeAccount alfanumérico
- L315: TC29460BERSA - Validar error por typeAccount con caracteres especiales
- L315: TC29461BERSA - Validar error por typeAccount boolean true
- L315: TC29462BERSA - Validar error por typeAccount boolean false
- L315: TC29463BERSA - Validar error por typeAccount en minúsculas
- L315: TC29464BERSA - Validar error por typeAccount con prefijo y espacio
- L315: TC29465BERSA - Validar error por account con espacio
- L315: TC29466BERSA - Validar error por account con acento
- L315: TC29467BERSA - Validar error por account con guión
- L315: TC29468BERSA - Validar error por account con longitud no permitida
- L315: TC29469BERSA - Validar error por account con apóstrofe
- L315: TC29470BERSA - Validar error por account vacío
- L315: TC29471BERSA - Validar error por account null
- L315: TC29472BERSA - Validar error por account CBU con longitud inválida (-1)
- L315: TC29473BERSA - Validar error por account CBU con longitud inválida (+1)
- L315: TC29474BERSA - Validar error por account alias con números adicionales
- L315: TC29475BERSA - Validar error por typeAccount alfanumérico
- L315: TC29476BERSA - Validar error por account con caracteres especiales
- L315: TC29477BERSA - Validar error por account boolean true
- L315: TC29478BERSA - Validar error por account boolean false
- L315: TC29480BERSA - Validar error por account con formato mixto
- L315: TC29481BERSA - Validar error cuando typeAccount y account están vacíos
- L315: TC29482BERSA - Validar error cuando typeAccount y account son inválidos
- L315: TC29483BERSA - Validar error por inconsistencia typeAccount CBU y account alias
- L315: TC29484BERSA - Validar error por inconsistencia typeAccount alias y account CBU
- L315: TC29491BERSA - Validar error por typeAccount CBU vacío
- L315: TC29492BERSA - Validar error por typeAccount CBU null
- L315: TC29493BERSA - Validar error por account con espacio
- L315: TC29494BERSA - Validar error por account con guión
- L315: TC29495BERSA - Validar error por account con longitud no permitida
- L315: TC29496BERSA - Validar error por account con apóstrofe
- L315: TC29497BERSA - Validar error por account vacío
- L315: TC29498BERSA - Validar error por account null
- L315: TC29499BERSA - Validar error por account CBU con longitud inválida (-1)
- L315: TC29500BERSA - Validar error por account cbu con caracteres especiales
- L315: TC29501BERSA - Validar error por account CBU boolean true
- L315: TC29502BERSA - Validar error por account CBU boolean false
- L315: TC29503BERSA - Validar error por typeAccount CVU vacío
- L315: TC29504BERSA - Validar error por typeAccount CVU null
- L315: TC29505BERSA - Validar error por account CVU con espacio
- L315: TC29506BERSA - Validar error por account CVU con guión
- L315: TC29507BERSA - Validar error por account CVU con longitud no permitida
- L315: TC29508BERSA - Validar error por account CVU con apóstrofe
- L315: TC29509BERSA - Validar error por account CVU vacío
- L315: TC29510BERSA - Validar error por account CVU null
- L315: TC29511BERSA - Validar error por account CVU con longitud inválida (-1)
- L315: TC29512BERSA - Validar error por account CVU con longitud inválida (+1)
- L315: TC29513BERSA - Validar error por account CVU con caracteres especiales
- L315: TC29514BERSA - Validar error por account CVU boolean true
- L315: TC29516BERSA - Validar error por account CVU boolean false

### `tests/api/accounts/should-reject-invalid-echeq-list-filters.api.spec.ts`

Fixtures: `echeqListApi`, `loginApi`, `loginCredentials`

- L115: TC29517BERSA - Validar error por echeqNumber con espacio
- L115: TC29518BERSA - Validar error por echeqNumber con caracteres especiales
- L115: TC29519BERSA - Validar error por statement con espacio
- L115: TC29520BERSA - Validar error por statement con caracteres especiales
- L115: TC29521BERSA - Validar error por filterCuil con espacio
- L115: TC29522BERSA - Validar error por filterCuil con caracteres especiales
- L115: TC29523BERSA - Validar error por operationType con espacio
- L115: TC29524BERSA - Validar error por operationType con caracteres especiales
- L115: TC29525BERSA - Validar error por page con espacio
- L115: TC29526BERSA - Validar error por page con caracteres especiales
- L115: TC29527BERSA - Validar error por filterCuil boolean true
- L115: TC29528BERSA - Validar error por filterCuil boolean false
- L115: TC29529BERSA - Validar error por filterCuil null
- L115: TC29530BERSA - Validar error por page con guión
- L115: TC29531BERSA - Validar error por page alfabético no permitido
- L115: TC29532BERSA - Validar error por page boolean true
- L115: TC29533BERSA - Validar error por page boolean false
- L115: TC29534BERSA - Validar error por page con prefijo y espacio

### `tests/api/accounts/should-request-salary-advance.api.spec.ts`

Fixtures: `loginApi`, `loginCredentials`, `originAccountData`, `productWidgetsApi`, `salaryAdvanceApi`

- L12: TC0223BERSA - Adelantos de Sueldo - Realizar solicitud de adelanto de sueldo

### `tests/api/accounts/should-show-salary-advance-card.api.spec.ts`

Fixtures: `loginApi`, `loginCredentials`, `salaryAdvanceApi`

- L8: TC0229BERSA - Adelantos de Sueldo - Mis adelantos - Validar card de adelantos de sueldo

## `tests/api/authentication/`

Especificaciones: `specs/authentication/api-login.plan.md`, `specs/authentication/login.plan.md`, `specs/authentication/second-factor-code-inbox.plan.md`

### `tests/api/authentication/should-login-and-capture-bearer.api.spec.ts`

Fixtures: `loginApi`, `loginCredentials`

- L8: TC0017BERSA - inicia sesión y captura el Bearer token @regression

## `tests/api/cards/`

Especificaciones: `specs/cards/credit-card-other-amount-payment.api.plan.md`, `specs/cards/credit-card-total-payment.ui.plan.md`

### `tests/api/cards/should-pay-credit-card-minimum.api.spec.ts`

Fixtures: `creditCardPaymentData`, `creditCardPaymentsApi`, `loginApi`, `loginCredentials`, `productWidgetsApi`

- L18: TC0164BERSA - Tarjeta de Crédito - Pagar mínimo de una Tarjeta de Crédito Visa
- L18: TC16039BERSA - Tarjetas de crédito - Pagar el importe mínimo de una tarjeta de crédito Mastercard

### `tests/api/cards/should-pay-credit-card-other-amount.api.spec.ts`

Fixtures: `creditCardOtherAmountPaymentApi`, `creditCardOtherAmountPaymentData`, `loginApi`, `loginCredentials`

- L8: TC12967BERSA - paga ARS 1 de una tarjeta Visa por otro importe @regression

### `tests/api/cards/should-pay-mastercard-other-amount.api.spec.ts`

Fixtures: `creditCardPaymentData`, `creditCardPaymentsApi`, `loginApi`, `loginCredentials`, `productWidgetsApi`

- L12: TC0165BERSA - Pagar tarjeta de crédito Master - Otro importe

### `tests/api/cards/should-reject-credit-card-request-by-declaration.api.spec.ts`

Fixtures: `creditCardApplicationApi`, `loginApi`, `loginCredentials`, `optionalOriginAccountData`, `paymentLinkSyncApi`, `productWidgetsApi`

- L39: TC0192BERSA - Tarjetas de crédito - Persona expuesta políticamente no permite continuar la solicitud
- L39: TC0193BERSA - Tarjetas de crédito - Residencia fiscal en el exterior no permite continuar la solicitud
- L39: TC0194BERSA - Tarjetas de crédito - Sujeto obligado UIF no permite continuar la solicitud

### `tests/api/cards/should-request-credit-card.api.spec.ts`

Fixtures: `creditCardApplicationApi`, `loginApi`, `loginCredentials`, `optionalOriginAccountData`, `paymentLinkSyncApi`, `productWidgetsApi`

- L9: TC0149BERSA - Tarjeta de Crédito - Solicitar nueva tarjeta de crédito

### `tests/api/cards/should-send-applicant-cuil-in-credit-card-request.api.spec.ts`

Fixtures: `creditCardApplicationApi`, `loginApi`, `loginCredentials`, `optionalOriginAccountData`, `paymentLinkSyncApi`, `productWidgetsApi`

- L22: TC128238BERSA - API - Tarjeta de Crédito - Verificar que el dato cuilSolicitante se envíe en application/create en nueva alta de TC
- L22: TC128239BERSA - API - Tarjeta de Crédito - Verificar que el dato cuilSolicitante se envíe en application/update en alta de TC ya iniciada

## `tests/api/home/`

Especificaciones: `specs/home/terms-and-conditions.api.plan.md`

### `tests/api/home/should-get-terms-and-conditions.api.spec.ts`

Fixtures: `termsAndConditionsApi`

- L8: TC0159BERSA - obtiene los términos y condiciones generales

## `tests/api/investments/`

Especificaciones: `specs/investments/fund-risk-filter.ui.plan.md`, `specs/investments/traditional-fixed-term.api.plan.md`

### `tests/api/investments/should-create-traditional-fixed-term.api.spec.ts`

Fixtures: `loginApi`, `loginCredentials`, `traditionalFixedTermApi`, `traditionalFixedTermData`

- L8: TC0042BERSA - constituye un plazo fijo tradicional en ARS a 30 días

### `tests/api/investments/should-create-usd-fixed-term.api.spec.ts`

Fixtures: `loginApi`, `loginCredentials`, `originAccountData`, `productWidgetsApi`, `traditionalFixedTermApi`

- L9: TC12973BERSA - Plazo Fijo Tradicional en Dólares

### `tests/api/investments/should-exchange-dollars.api.spec.ts`

Fixtures: `accountPairData`, `dollarExchangeApi`, `loginApi`, `loginCredentials`, `productWidgetsApi`

- L9: TC0069BERSA - Compra de moneda extranjera (USD)
- L32: TC0009BERSA - Venta de moneda extranjera (USD)

### `tests/api/investments/should-list-fund-movements.api.spec.ts`

Fixtures: `investmentFundsApi`, `loginApi`, `loginCredentials`

- L22: TC0129BERSA - FCI - Movimientos
- L38: TC0130BERSA - FCI - Movimientos con descarga PDF

### `tests/api/investments/should-open-investment-funds.api.spec.ts`

Fixtures: `investmentFundsApi`, `loginApi`, `loginCredentials`

- L9: TC0115BERSA - FCI - Simular API (inicio a la funcionalidad)

### `tests/api/investments/should-show-commitent-account-closure-form.api.spec.ts`

Fixtures: `commitentAccountApi`, `loginApi`, `loginCredentials`

- L16: TC0125BERSA - FCI - Solicitar baja cuenta comitente - Validación de pantalla

## `tests/api/messages/`

Especificaciones: `specs/messages/mark-message-unread.ui.plan.md`, `specs/messages/received-messages.api.plan.md`

### `tests/api/messages/should-filter-messages-by-read-state.api.spec.ts`

Fixtures: `loginApi`, `loginCredentials`, `receivedMessagesApi`

- L11: TC0094BERSA - Mensajes - Sin Leer
- L26: TC0098BERSA - Mensajes - Leídos

### `tests/api/messages/should-list-received-messages.api.spec.ts`

Fixtures: `loginApi`, `loginCredentials`, `receivedMessagesApi`

- L8: TC0109BERSA - consulta los mensajes recibidos

## `tests/api/payments/`

Especificaciones: `specs/payments/debin-generation.api.plan.md`

### `tests/api/payments/should-accept-pending-debin.api.spec.ts`

Fixtures: `debinOperationsApi`, `debitCardData`, `loginApi`, `loginCredentials`, `paymentLinkSyncApi`, `productWidgetsApi`

- L10: TC0070BERSA - Aceptación de DEBIN

### `tests/api/payments/should-adhere-tax-service.api.spec.ts`

Fixtures: `debitCardData`, `loginApi`, `loginCredentials`, `paymentLinkSyncApi`, `productWidgetsApi`, `taxServicesApi`

- L11: TC0074BERSA - Adherir nuevo pago o servicio

### `tests/api/payments/should-download-payment-receipts-period.api.spec.ts`

Fixtures: `debitCardData`, `loginApi`, `loginCredentials`, `paymentLinkSyncApi`, `paymentReceiptsApi`, `productWidgetsApi`, `receivedMessagesApi`

- L26: TC0137BERSA - Pagos - Comprobantes - Descargar periodo en PDF
- L26: TC0138BERSA - Pagos - Comprobantes - Descargar periodo en XLS

### `tests/api/payments/should-generate-debin.api.spec.ts`

Fixtures: `debinGenerationApi`, `debinGenerationData`, `loginApi`, `loginCredentials`

- L8: TC0013BERSA - genera una orden DEBIN seleccionando CBU

### `tests/api/payments/should-list-debin-history.api.spec.ts`

Fixtures: `debinOperationsApi`, `loginApi`, `loginCredentials`, `paymentLinkSyncApi`

- L8: TC0012BERSA - DEBIN Comprador - Consulta DEBINes históricos - Listado

### `tests/api/payments/should-list-payment-receipts.api.spec.ts`

Fixtures: `debitCardData`, `loginApi`, `loginCredentials`, `paymentLinkSyncApi`, `paymentReceiptsApi`, `productWidgetsApi`

- L12: TC0108BERSA - Pagos - Consulta de comprobantes

### `tests/api/payments/should-pay-tax-service-without-base.api.spec.ts`

Fixtures: `debitCardData`, `loginApi`, `loginCredentials`, `originAccountData`, `paymentLinkSyncApi`, `productWidgetsApi`, `taxServicesApi`

- L11: TC12964BERSA - Pagar impuesto/servicio nuevo pago (sin base)

### `tests/api/payments/should-remove-tax-service.api.spec.ts`

Fixtures: `debitCardData`, `loginApi`, `loginCredentials`, `paymentLinkSyncApi`, `productWidgetsApi`, `taxServicesApi`

- L10: TC12966BERSA - Pagar impuesto/servicio - Eliminar servicio

### `tests/api/payments/should-subscribe-recurring-debin.api.spec.ts`

Fixtures: `debinOperationsApi`, `loginApi`, `loginCredentials`, `originAccountData`, `paymentLinkSyncApi`, `productWidgetsApi`

- L11: TC0015BERSA - Adherir DEBIN recurrente (Pesos) - segundo factor por SMS

## `tests/api/procedures/`

Especificaciones: `specs/procedures/current-online-procedures.api.plan.md`

### `tests/api/procedures/should-list-current-online-procedures.api.spec.ts`

Fixtures: `currentOnlineProceduresApi`, `loginApi`, `loginCredentials`

- L8: TC0078BERSA - consulta los trámites vigentes

## `tests/api/profile/`

Especificaciones: `specs/profile/change-address.ui.plan.md`, `specs/profile/change-phone.api.plan.md`, `specs/profile/notification-preferences.api.plan.md`

### `tests/api/profile/should-change-phone.api.spec.ts`

Fixtures: `loginApi`, `loginCredentials`, `phoneChangeApi`

- L9: TC0001BERSA - cambia el teléfono y verifica que quedó persistido

### `tests/api/profile/should-get-notification-preferences.api.spec.ts`

Fixtures: `loginApi`, `loginCredentials`, `notificationPreferencesApi`

- L8: TC0102BERSA - consulta la configuración de notificaciones

## `tests/api/transfers/`

Especificaciones: `specs/transfers/own-account-transfer.api.plan.md`, `specs/transfers/own-account-transfer.ui.plan.md`

### `tests/api/transfers/should-generate-credin.api.spec.ts`

Fixtures: `credinApi`, `loginApi`, `loginCredentials`, `productWidgetsApi`, `transferCaseData`

- L56: TC0014BERSA - Generar CREDIN - Cuenta propia $ (mismo Banco u otro) - Mas de una cuenta Debito - CBU - Segundo factor por SMS
- L56: TC12938BERSA - TRANSFERENCIAS - Generar CREDIN - Dolares
- L73: TC12939BERSA - TRANSFERENCIAS - Consultar CREDIN Originante
- L73: TC12940BERSA - TRANSFERENCIAS - Consultar CREDIN destinatario

### `tests/api/transfers/should-list-transfer-history.api.spec.ts`

Fixtures: `loginApi`, `loginCredentials`, `thirdPartyTransfersApi`, `thirdPartyTransferWorkflow`, `transferCaseData`, `transferHistoryApi`

- L20: TC18100BERSA - Transferencias programadas - Fecha específica agendada - Historial de transferencias
- L66: TC18101BERSA - Transferencias a contacto agendado - Historial de transferencias
- L116: TC18102BERSA - Transferencias a CVU - Historial de transferencias
- L158: TC16759BERSA - Transferencias realizadas - Ver mis transferencias - Filtros
- L185: TC16765BERSA - Transferencias realizadas - Ver mis transferencias - rango más nombre en buscador
- L185: TC16768BERSA - Transferencias realizadas - Ver mis transferencias - rango más nombre en buscador
- L203: TC18104BERSA - Transferencias realizadas - Ver mis transferencias - Búsqueda sin resultados

### `tests/api/transfers/should-manage-transfer-contacts.api.spec.ts`

Fixtures: `loginApi`, `loginCredentials`, `thirdPartyTransfersApi`, `thirdPartyTransferWorkflow`, `transferCaseData`

- L11: TC0145BERSA - Transferencias - Añadir nuevo destinatario - Con cbu valido
- L38: TC12981BERSA - Transferencias - Eliminar destinatario de agenda

### `tests/api/transfers/should-schedule-transfers.api.spec.ts`

Fixtures: `loginApi`, `loginCredentials`, `productWidgetsApi`, `thirdPartyTransfersApi`, `thirdPartyTransferWorkflow`, `transferCaseData`

- L47: TC0472BERSA - Transferencias Programadas (Fecha especifica) CBU - terceros mismo banco USD - NO AGENDADOS
- L47: TC0473BERSA - Transferencias Programadas (Fecha especifica) CBU - terceros otros bancos $ - NO AGENDADOS
- L47: TC0474BERSA - Transferencias Programadas (Fecha especifica) CBU - terceros otros bancos USD - NO AGENDADOS
- L47: TC0478BERSA - Transferencias Programadas (Fecha especifica) ALIAS - terceros mismo banco USD - NO AGENDADOS
- L47: TC0479BERSA - Transferencias Programadas (Fecha especifica) ALIAS - terceros otros bancos $ - NO AGENDADOS
- L47: TC0480BERSA - Transferencias Programadas (Fecha especifica) ALIAS - terceros otros bancos USD - NO AGENDADOS
- L47: TC0484BERSA - Transferencias Programadas (Fecha especifica) CVU - terceros $ NO AGENDADOS
- L47: TC0485BERSA - Transferencias Programadas (Fecha especifica) ALIAS de CVU - terceros $ NO AGENDADOS
- L84: TC0418BERSA - Transferencias CBU - terceros otros bancos USD - NO AGENDADOS
- L129: TC18090BERSA - Programada (fecha específica) CBU a cuentas propias otro banco $ - NO AGENDADO
- L129: TC18094BERSA - Programada (fecha específica) CBU a Terceros otro banco $ - NO AGENDADOS
- L178: TC0465BERSA - Programadas (fecha específica) - terceros mismo banco $ - AGENDADOS
- L178: TC0467BERSA - Programadas (fecha específica) - terceros otros bancos $ - AGENDADOS
- L178: TC0468BERSA - Programadas (fecha específica) - terceros otros bancos USD - AGENDADOS
- L178: TC0483BERSA - Programadas (fecha específica) CVU - terceros $ AGENDADOS
- L215: TC0008BERSA - Transferencia intrabancaria en pesos - propias - programada

### `tests/api/transfers/should-transfer-between-own-accounts.api.spec.ts`

Fixtures: `loginApi`, `loginCredentials`, `ownAccountTransferApi`, `ownAccountTransferData`

- L8: TC0139BERSA - transfiere entre cuentas propias y valida el ticket @critical

### `tests/api/transfers/should-transfer-to-adhered-contact.api.spec.ts`

Fixtures: `loginApi`, `loginCredentials`, `thirdPartyTransfersApi`, `thirdPartyTransferWorkflow`, `transferCaseData`

- L20: TC0409BERSA - TRANSFERENCIAS - terceros mismo banco $ - AGENDADOS
- L20: TC0410BERSA - Transferencias - terceros mismo banco USD - AGENDADOS
- L20: TC0411BERSA - Transferencias - terceros otros bancos $ - AGENDADOS
- L20: TC0412BERSA - Transferencias - terceros mismo banco USD - AGENDADOS
- L20: TC0427BERSA - Transferencias CVU - terceros $ AGENDADOS

### `tests/api/transfers/should-transfer-to-third-parties.api.spec.ts`

Fixtures: `loginApi`, `loginCredentials`, `thirdPartyTransfersApi`, `thirdPartyTransferWorkflow`, `transferCaseData`

- L112: TC0344BERSA - A cuentas de terceros ALIAS - otros bancos - Pesos
- L112: TC0422BERSA - ALIAS - terceros mismo banco USD - NO AGENDADOS
- L112: TC0424BERSA - ALIAS - terceros otros bancos USD - NO AGENDADOS
- L112: TC0429BERSA - ALIAS de CVU - terceros $ NO AGENDADOS
- L112: TC12930BERSA - A cuentas de terceros CBU - mismo banco - Pesos
- L112: TC12932BERSA - A cuentas de terceros ALIAS - mismo banco - Pesos
- L112: TC12969BERSA - A cuentas de terceros CVU - otros bancos - Pesos
- L112: TC18097BERSA - Saldo actualizado - Transferencias a un Alias CVU en pesos
- L112: TC18098BERSA - Saldo actualizado - Transferencias a un Alias CBU en dólares
- L169: TC0477BERSA - Transferencias Programadas (Fecha especifica) ALIAS - terceros mismo banco $ - NO AGENDADOS
- L169: TC12935BERSA - Transferencias Programadas (Fecha especifica) CBU - terceros mismo banco $ - NO AGENDADOS
- L208: TC18089BERSA - Transferencia a cuentas propias con CBU de otra entidad sin el campo Descripción al agendarse
- L208: TC18093BERSA - Transferencia a Terceros con CBU de otra entidad sin el campo Descripción

## `tests/ui/accounts/`

Especificaciones: `specs/accounts/withdrawal-order.api.plan.md`

### `tests/ui/accounts/should-enable-new-account-request-continue.ui.spec.ts`

Fixtures: `accountsPage`, `loginCredentials`, `loginPage`, `startPage`

- L13: TC29410BERSA - habilita Continuar al elegir Caja de Ahorro en Pesos
- L13: TC29411BERSA - habilita Continuar al elegir Caja de Ahorro en Dólares
- L13: TC29412BERSA - habilita Continuar al elegir Cuenta Corriente en Pesos

### `tests/ui/accounts/should-filter-echeqs.ui.spec.ts`

Fixtures: `echeqPage`, `loginCredentials`, `loginPage`, `startPage`

- L10: TC29414BERSA - combina filtros de CUIT, estado y fecha de emisión

### `tests/ui/accounts/should-open-salary-advance-contract.ui.spec.ts`

Fixtures: `loginCredentials`, `loginPage`, `originAccountUiData`, `salaryAdvancePage`, `startPage`

- L9: TC0227BERSA - redirige al documento del contrato al aceptar
- L33: TC0228BERSA - vuelve a la solicitud de adelanto al cancelar la redirección

### `tests/ui/accounts/should-operate-echeqs.ui.spec.ts`

Fixtures: `echeqPage`, `loginApi`, `loginCredentials`, `loginPage`, `smsOperationOtpApi`, `startPage`

- L23: TC29413BERSA - muestra los campos del filtro de eCheq
- L45: TC0026BERSA - muestra el detalle del primer eCheq con todos los estados
- L70: TC124865BERSA - acepta un eCheq emitido pendiente con segundo factor

### `tests/ui/accounts/should-show-account-movement-detail.ui.spec.ts`

Fixtures: `accountsPage`, `loginCredentials`, `loginPage`, `originAccountUiData`, `startPage`

- L9: TC0040BERSA - muestra fecha, concepto y monto del primer movimiento

### `tests/ui/accounts/should-transfer-to-scheduled-contact-and-find-movement.ui.spec.ts`

Fixtures: `accountMovementsPage`, `accountMovementsUiData`, `loginCredentials`, `loginPage`, `startPage`

- L8: TC0041BERSA - filtra movimientos por tipo de transacción en una cuenta

### `tests/ui/accounts/should-validate-account-alias.ui.spec.ts`

Fixtures: `accountAliasPage`, `accountAliasUiData`, `loginCredentials`, `loginPage`, `startPage`

- L11: TC128241BERSA - Web - Modif. Alias - Validar controles del campo mandatorio Alias

### `tests/ui/accounts/should-validate-salary-advance-request.ui.spec.ts`

Fixtures: `homePage`, `loginCredentials`, `loginPage`, `originAccountUiData`, `salaryAdvancePage`, `startPage`

- L13: TC0222BERSA - muestra la pantalla completa de solicitud de adelanto
- L35: TC0225BERSA - vuelve a la solicitud al presionar Volver
- L58: TC0226BERSA - exige aceptar términos y condiciones al confirmar
- L85: TC0237BERSA - informa error con un importe inferior al mínimo
- L85: TC0238BERSA - informa error con un importe superior al máximo

## `tests/ui/authentication/`

Especificaciones: `specs/authentication/api-login.plan.md`, `specs/authentication/login.plan.md`, `specs/authentication/second-factor-code-inbox.plan.md`

### `tests/ui/authentication/should-login-and-capture-bearer.ui.spec.ts`

Fixtures: `loginCredentials`, `loginPage`, `startPage`

- L9: TC0017BERSA - inicia sesión y captura el Bearer token @regression

### `tests/ui/authentication/should-login-with-virtual-keyboard.ui.spec.ts`

Fixtures: `homePage`, `loginCredentials`, `loginPage`, `startPage`

- L9: TC0151BERSA - inicia sesión con el teclado virtual y muestra el saludo

### `tests/ui/authentication/should-recover-username.ui.spec.ts`

Fixtures: `homePage`, `loginApi`, `loginCredentials`, `loginPage`, `profileMobilePhoneApi`, `secondFactorChallengeApi`, `startPage`

- L13: TC0048BERSA - envía el usuario por correo y permite volver a ingresar
- L13: TC128240BERSA - envía el usuario por correo y permite volver a ingresar

## `tests/ui/cards/`

Especificaciones: `specs/cards/credit-card-other-amount-payment.api.plan.md`, `specs/cards/credit-card-total-payment.ui.plan.md`

### `tests/ui/cards/should-consult-credit-cards.ui.spec.ts`

Fixtures: `creditCardDetailUiData`, `creditCardsPage`, `loginCredentials`, `loginPage`, `startPage`

- L8: TC0032BERSA - abre el detalle de una tarjeta Visa por su número
- L31: TC0033BERSA - abre el detalle de una tarjeta Mastercard
- L54: TC0062BERSA - muestra descarga de resumen, movimientos del período y solicitud de adicional

### `tests/ui/cards/should-download-credit-card-statement.ui.spec.ts`

Fixtures: `creditCardDetailUiData`, `creditCardsPage`, `loginCredentials`, `loginPage`, `startPage`

- L10: TC0104BERSA - Tarjeta de Crédito - Descarga de resumen
- L40: TC0167BERSA - Tarjeta de Crédito - Consultar último resumen de una Tarjeta de Crédito

### `tests/ui/cards/should-open-total-credit-card-payment-review.ui.spec.ts`

Fixtures: `creditCardPaymentPage`, `creditCardTotalPaymentUiData`, `loginCredentials`, `loginPage`, `startPage`

- L18: TC0163BERSA - muestra la revisión del pago total de una tarjeta Mastercard propia

### `tests/ui/cards/should-redirect-new-card-request-to-appointments.ui.spec.ts`

Fixtures: `creditCardsPage`, `loginCredentials`, `loginPage`, `newCreditCardRequestPage`, `optionalOriginAccountData`, `startPage`

- L8: TC0195BERSA - Solicitud de tarjeta de crédito - Sujeto obligado UIF redirige a solicitar turno

### `tests/ui/cards/should-show-credit-card-detail-information.ui.spec.ts`

Fixtures: `creditCardPaymentData`, `creditCardsPage`, `loginCredentials`, `loginPage`, `startPage`

- L21: TC128242BERSA - Web - Detalle de TC - Validar la información en Detalle de TC VISA y MASTER

### `tests/ui/cards/should-show-credit-card-tracking.ui.spec.ts`

Fixtures: `creditCardTrackingPage`

- L42: TC0153BERSA - Tarjeta de Crédito - Seguimiento de Tarjetas de Crédito
- L52: TC0155BERSA - Tarjeta de Crédito - Seguimiento de Tarjetas de Crédito - Detalles
- L68: TC0157BERSA - Tarjeta de Crédito - Seguimiento de Tarjetas de Crédito - Descargar Detalles
- L85: TC0158BERSA - Tarjeta de Crédito - Seguimiento de Tarjetas de Crédito - Imprimir Detalles

### `tests/ui/cards/should-start-credit-card-theft-report.ui.spec.ts`

Fixtures: `creditCardDetailUiData`, `creditCardsPage`, `loginCredentials`, `loginPage`, `startPage`

- L9: TC0162BERSA - Tarjeta de Crédito - Denunciar robo o extravío de Tarjeta de Crédito

## `tests/ui/documents/`

### `tests/ui/documents/should-download-product-contract.ui.spec.ts`

Fixtures: `loginCredentials`, `loginPage`, `productDocumentsPage`, `startPage`

- L9: TC0077BERSA - descarga el contrato del producto

## `tests/ui/home/`

Especificaciones: `specs/home/terms-and-conditions.api.plan.md`

### `tests/ui/home/should-show-warning-alert.ui.spec.ts`

Fixtures: `homePage`, `loginCredentials`, `loginPage`, `startPage`

- L12: TC128243BERSA - muestra el aviso en formato advertencia

## `tests/ui/insurance/`

### `tests/ui/insurance/should-list-insurance-cards.ui.spec.ts`

Fixtures: `insurancePage`, `loginCredentials`, `loginPage`, `startPage`

- L9: TC0196BERSA - muestra la tarjeta del seguro de Robo

### `tests/ui/insurance/should-open-robbery-insurance-detail.ui.spec.ts`

Fixtures: `insurancePage`, `loginCredentials`, `loginPage`, `startPage`

- L8: TC0197BERSA - muestra el detalle del seguro de Robo

### `tests/ui/insurance/should-reach-robbery-insurance-confirmation.ui.spec.ts`

Fixtures: `insurancePage`, `loginCredentials`, `loginPage`, `startPage`

- L8: TC12928BERSA - llega a la confirmación de la solicitud de seguro

## `tests/ui/investments/`

Especificaciones: `specs/investments/fund-risk-filter.ui.plan.md`, `specs/investments/traditional-fixed-term.api.plan.md`

### `tests/ui/investments/funds-by-risk-filter.ui.spec.ts`

Fixtures: `investmentFundsPage`, `loginCredentials`, `loginPage`, `startPage`

- L9: TC0119BERSA - muestra únicamente fondos de riesgo moderado

### `tests/ui/investments/should-filter-funds.ui.spec.ts`

Fixtures: `investmentFundsPage`, `loginCredentials`, `loginPage`, `startPage`

- L11: TC0118BERSA - FCI - Filtros - Mi Tenencia - Fondos por Riesgo - Conservador
- L47: TC0126BERSA - FCI - Filtros: Fondos por moneda - Pesos
- L47: TC0127BERSA - FCI - Filtros: Fondos por moneda - Dólar
- L73: TC0128BERSA - FCI - Filtros: Mi Tenencia

### `tests/ui/investments/should-open-fund-more-information.ui.spec.ts`

Fixtures: `investmentFundsPage`, `loginCredentials`, `loginPage`, `startPage`

- L11: TC0133BERSA - FCI - Más información - Aceptar modal
- L36: TC0134BERSA - FCI - Más información - Rechazar modal

### `tests/ui/investments/should-query-fixed-term-rates.ui.spec.ts`

Fixtures: `fixedTermsPage`, `loginCredentials`, `loginPage`, `startPage`

- L23: TC0044BERSA - PF - Consulta de Tasas - Tipo - Tradicional en Pesos
- L23: TC0045BERSA - PF - Consulta de Tasas - Tipo - Tradicional en Dólares

### `tests/ui/investments/should-reach-commitent-account-confirmation.ui.spec.ts`

Fixtures: `commitentAccountOpeningPage`, `investmentFundsPage`, `loginCredentials`, `loginPage`, `startPage`

- L8: TC0173BERSA - Inversiones - Alta cuenta comitente - Validar pantalla etapa 5 'Confirmación'

### `tests/ui/investments/should-reject-zero-amount-foreign-currency-purchase.ui.spec.ts`

Fixtures: `foreignCurrencyPurchasePage`, `foreignCurrencyPurchaseUiData`, `loginCredentials`, `loginPage`, `startPage`

- L8: TC0178BERSA - rechaza la compra de dólares con importe cero

### `tests/ui/investments/should-validate-dollar-exchange-form.ui.spec.ts`

Fixtures: `foreignCurrencyPurchasePage`, `loginCredentials`, `loginPage`, `originAccountData`, `startPage`

- L27: TC0179BERSA - Inversiones - Compra de moneda extranjera - Seguir flujo de compra de dólar sin aceptar check
- L27: TC0188BERSA - Inversiones - Venta de moneda extranjera - Seguir flujo de venta de dólar sin aceptar check
- L60: TC0187BERSA - Inversiones - Venta de moneda extranjera - Importe 0 - Validar mensaje
- L124: TC0181BERSA - Inversiones - Compra - Desplegable Cuenta Origen sólo cuentas en pesos
- L124: TC0182BERSA - Inversiones - Compra - Desplegable Cuenta Destino sólo cuentas en dólares
- L124: TC0189BERSA - Inversiones - Venta - Desplegable Cuenta Destino sólo cuentas en pesos
- L124: TC0190BERSA - Inversiones - Venta - Desplegable Cuenta Origen sólo cuentas en dólares

### `tests/ui/investments/should-validate-fixed-term-request-continue.ui.spec.ts`

Fixtures: `accountPairData`, `fixedTermsPage`, `loginCredentials`, `loginPage`, `startPage`

- L24: TC128244BERSA - Web - Alta de PF - Verificar lógica del botón Continuar y mensaje de error en Simulación

## `tests/ui/loans/`

### `tests/ui/loans/should-request-loans.ui.spec.ts`

Fixtures: `loanRequestPage`, `loginCredentials`, `loginPage`, `startPage`

- L9: TC0206BERSA - llega a la confirmación de un nuevo préstamo
- L48: TC0220BERSA - llega a la confirmación de un préstamo para cancelar deudas
- L83: TC0221BERSA - abre el resumen de contrato del préstamo para cancelar deudas

## `tests/ui/messages/`

Especificaciones: `specs/messages/mark-message-unread.ui.plan.md`, `specs/messages/received-messages.api.plan.md`

### `tests/ui/messages/should-delete-message.ui.spec.ts`

Fixtures: `loginCredentials`, `loginPage`, `messagesPage`, `startPage`

- L9: TC0090BERSA - elimina el primer mensaje de la bandeja

### `tests/ui/messages/should-mark-message-unread.ui.spec.ts`

Fixtures: `loginCredentials`, `loginPage`, `messagesPage`, `startPage`

- L8: TC0086BERSA - marca un mensaje como no leído

## `tests/ui/mis-products/`

Especificaciones: `specs/mis-products/should-open-cancellation-page.ui.plan.md`

### `tests/ui/mis-products/should-open-cancellation-page.ui.spec.ts`

Fixtures: `cancellationPage`, `loginCredentials`, `loginPage`, `startPage`

- L8: TC0075BERSA - acepta la redirección a Baja y Arrepentimiento

## `tests/ui/payments/`

Especificaciones: `specs/payments/debin-generation.api.plan.md`

### `tests/ui/payments/should-pay-and-find-service-receipts.ui.spec.ts`

Fixtures: `loginApi`, `loginCredentials`, `loginPage`, `paymentReceiptsPage`, `paymentServicesPage`, `paymentServicesPreparationWorkflow`, `startPage`

- L12: TC110847BERSA - muestra el estado de los pagos realizados en simultáneo
- L44: TC110363BERSA - filtra comprobantes de pago por empresa y descripción

### `tests/ui/payments/should-require-debin-buyer-identifier.ui.spec.ts`

Fixtures: `debinSellerPage`, `loginCredentials`, `loginPage`, `originAccountUiData`, `startPage`

- L8: TC0183BERSA - exige el CBU del comprador al buscar sin ingresarlo
- L36: TC0184BERSA - exige el alias del comprador al buscar sin ingresarlo

## `tests/ui/procedures/`

Especificaciones: `specs/procedures/current-online-procedures.api.plan.md`

### `tests/ui/procedures/should-open-online-appointments.ui.spec.ts`

Fixtures: `appointmentsPage`, `loginCredentials`, `loginPage`, `startPage`

- L10: TC0052BERSA - acepta la redirección para solicitar un turno

## `tests/ui/profile/`

Especificaciones: `specs/profile/change-address.ui.plan.md`, `specs/profile/change-phone.api.plan.md`, `specs/profile/notification-preferences.api.plan.md`

### `tests/ui/profile/should-change-address.ui.spec.ts`

Fixtures: `changeAddressPage`, `loginApi`, `loginCredentials`, `loginPage`, `profileMobilePhoneApi`, `secondFactorChallengeApi`, `startPage`

- L10: TC0004BERSA - cambia el domicilio con un segundo factor SMS @regression

### `tests/ui/profile/should-change-security-seal.ui.spec.ts`

Fixtures: `loginApi`, `loginCredentials`, `loginPage`, `securitySealPage`, `smsOperationOtpApi`, `startPage`

- L9: TC0035BERSA - cambia el avatar con segundo factor

## `tests/ui/start-page/`

### `tests/ui/start-page/should-open-registration.ui.spec.ts`

Fixtures: `identityValidationPage`, `startPage`

- L8: TC0156BERSA - acceso directo ¿No sos cliente aún? Registrarme

### `tests/ui/start-page/should-open-security-information.ui.spec.ts`

Fixtures: `loginPage`, `startPage`

- L9: TC0161BERSA - abre el enlace Información de seguridad

### `tests/ui/start-page/should-open-store-shortcut.ui.spec.ts`

Fixtures: `loginPage`, `startPage`

- L9: TC0152BERSA - abre el acceso directo Tienda.Clic

## `tests/ui/transfers/`

Especificaciones: `specs/transfers/own-account-transfer.api.plan.md`, `specs/transfers/own-account-transfer.ui.plan.md`

### `tests/ui/transfers/should-reject-invalid-contact.ui.spec.ts`

Fixtures: `addContactPage`, `contactSearchUiData`, `loginCredentials`, `loginPage`, `startPage`

- L8: TC0146BERSA - informa CBU inválido al agendar un contacto
- L31: TC0148BERSA - informa alias inválido al agendar un contacto

### `tests/ui/transfers/should-send-device-validation-to-fraud-analysis.ui.spec.ts`

Fixtures: `contactAgendaPage`, `credinRequestPage`, `loginApi`, `loginCredentials`, `smsOperationOtpApi`, `transferCaseData`, `transferFormPage`, `utilLoggerPage`

- L50: TC128246BERSA - VU Fraud - adhesionAccount envía metodoValidacionFP
- L87: TC128247BERSA - VU Fraud - transfers.account.makeWithCaps envía metodoValidacionFP
- L130: TC128248BERSA - VU Fraud - credin.generate envía metodoValidacionFP
- L178: TC128249BERSA - VU Fraud - transfers.account.scheduleMakeWithCaps envía metodoValidacionFP

### `tests/ui/transfers/should-show-transfer-history.ui.spec.ts`

Fixtures: `loginApi`, `loginCredentials`, `loginPage`, `startPage`, `thirdPartyTransferWorkflow`, `transferCaseData`, `transferFormPage`, `transferHistoryPage`

- L11: TC16756BERSA - Transferencias realizadas - Ver mis transferencias (movimientos de hace dos meses)
- L50: TC18103BERSA - Transferencias realizadas - Ver mis transferencias (borrar filtros)
- L94: TC29408BERSA - Transferencias Programadas - Nuevo historial - Visualización

### `tests/ui/transfers/should-transfer-and-show-ticket.ui.spec.ts`

Fixtures: `loginApi`, `loginCredentials`, `loginPage`, `smsOperationOtpApi`, `startPage`, `thirdPartyTransferWorkflow`, `transferCaseData`, `transferFormPage`, `transferHistoryPage`

- L18: TC18096BERSA - Saldo actualizado - Transferencias a cuentas propias en pesos del mismo banco
- L59: TC18099BERSA - Transferencias a CBU - Historial de transferencias
- L115: TC18092BERSA - Cambios en comprobante - TF inmediatas a terceros - Todos los campos - CBU otra entidad

### `tests/ui/transfers/should-transfer-to-own-account-by-cbu.ui.spec.ts`

Fixtures: `loginCredentials`, `loginPage`, `ownAccountTransferPage`, `ownAccountTransferUiData`, `startPage`

- L8: TC18088BERSA - transfiere a cuenta propia por CBU con todos los campos completos @critical
