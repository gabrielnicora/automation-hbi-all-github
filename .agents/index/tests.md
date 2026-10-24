<!-- Generado por `pnpm run agent:index`. No editar a mano. -->

# Tests

Fuente: `playwright test --list` (títulos resueltos, incluidos los parametrizados) y las
fixtures que cada archivo pide en sus callbacks. Para un caso nuevo, buscar acá un test del
mismo patrón y la especificación de la capacidad.

## `tests/api/001-accounts/`

Especificaciones: `specs/accounts/withdrawal-order.api.plan.md`

### `tests/api/001-accounts/should-adhere-new-destination.api.spec.ts`

Fixtures: `accountTransferSearchApi`, `adheredAccountsApi`, `loginApi`, `loginCredentials`, `productWidgetsApi`

- L10: TC-001-1-0001-UNE - Adherir cuenta - nuevo destinatario de terceros con segundo factor por SMS

### `tests/api/001-accounts/should-generate-withdrawal-order.api.spec.ts`

Fixtures: `loginApi`, `loginCredentials`, `withdrawalOrderApi`, `withdrawalOrderData`

- L8: TC-001-1-0002-UNE - genera una orden de extracción con saldo disponible

### `tests/api/001-accounts/should-list-echeqs-by-status.api.spec.ts`

Fixtures: `echeqOperationsApi`, `loginApi`, `loginCredentials`

- L19: TC-001-1-0005-UNE - Validar el listado de eCheq en el estado 'Todos'
- L19: TC-001-1-0085-UNE - Consulta del detalle de un eCheq - Estado 'Emitir'
- L19: TC-001-1-0089-UNE - Consulta del detalle de un eCheq - Estado Anular

### `tests/api/001-accounts/should-operate-received-echeqs.api.spec.ts`

Fixtures: `echeqEndorsementData`, `echeqOperationsApi`, `loginApi`, `loginCredentials`, `originAccountData`, `productWidgetsApi`, `secondUserCredentials`

- L47: TC-001-1-0083-UNE - Consulta del detalle de un eCheq - Estado Aceptar
- L64: TC-001-1-0084-UNE - Consulta del detalle de un eCheq - Estado Endosar
- L86: TC-001-1-0086-UNE - Consulta del detalle de un eCheq - Estado Repudiar
- L109: TC-001-1-0087-UNE - Consulta del detalle de un eCheq - Estado Custodiar
- L132: TC-001-1-0088-UNE - Consulta del detalle de un eCheq - Custodiar y rescatar
- L157: TC-001-1-0091-UNE - Solicitar devolución de eCheq
- L190: TC-001-1-0090-UNE - Depositar eCheq - Variable encendida

### `tests/api/001-accounts/should-reject-invalid-account-transfer-search.api.spec.ts`

Fixtures: `accountTransferSearchApi`, `loginApi`, `loginCredentials`

- L325: TC-001-1-0006-UNE - Validar mensaje de error typeAccount con espacio
- L325: TC-001-1-0007-UNE - Validar error por typeAccount con acento
- L325: TC-001-1-0008-UNE - Validar error por typeAccount con guión
- L325: TC-001-1-0009-UNE - Validar error por typeAccount con longitud no permitida
- L325: TC-001-1-0010-UNE - Validar error por typeAccount con apóstrofe
- L325: TC-001-1-0011-UNE - Validar error por typeAccount vacío
- L325: TC-001-1-0012-UNE - Validar error por typeAccount null
- L325: TC-001-1-0013-UNE - Validar error por typeAccount alfabético no permitido
- L325: TC-001-1-0014-UNE - Validar error por typeAccount numérico
- L325: TC-001-1-0015-UNE - Validar error por typeAccount alfanumérico
- L325: TC-001-1-0016-UNE - Validar error por typeAccount con caracteres especiales
- L325: TC-001-1-0017-UNE - Validar error por typeAccount boolean true
- L325: TC-001-1-0018-UNE - Validar error por typeAccount boolean false
- L325: TC-001-1-0019-UNE - Validar error por typeAccount en minúsculas
- L325: TC-001-1-0020-UNE - Validar error por typeAccount con prefijo y espacio
- L325: TC-001-1-0021-UNE - Validar error por account con espacio
- L325: TC-001-1-0022-UNE - Validar error por account con acento
- L325: TC-001-1-0023-UNE - Validar error por account con guión
- L325: TC-001-1-0024-UNE - Validar error por account con longitud no permitida
- L325: TC-001-1-0025-UNE - Validar error por account con apóstrofe
- L325: TC-001-1-0026-UNE - Validar error por account vacío
- L325: TC-001-1-0027-UNE - Validar error por account null
- L325: TC-001-1-0028-UNE - Validar error por account CBU con longitud inválida (-1)
- L325: TC-001-1-0029-UNE - Validar error por account CBU con longitud inválida (+1)
- L325: TC-001-1-0030-UNE - Validar error por account alias con números adicionales
- L325: TC-001-1-0031-UNE - Validar error por typeAccount alfanumérico
- L325: TC-001-1-0032-UNE - Validar error por account con caracteres especiales
- L325: TC-001-1-0033-UNE - Validar error por account boolean true
- L325: TC-001-1-0034-UNE - Validar error por account boolean false
- L325: TC-001-1-0035-UNE - Validar error por account con formato mixto
- L325: TC-001-1-0036-UNE - Validar error cuando typeAccount y account están vacíos
- L325: TC-001-1-0037-UNE - Validar error cuando typeAccount y account son inválidos
- L325: TC-001-1-0038-UNE - Validar error por inconsistencia typeAccount CBU y account alias
- L325: TC-001-1-0039-UNE - Validar error por inconsistencia typeAccount alias y account CBU
- L325: TC-001-1-0040-UNE - Validar error por typeAccount CBU vacío
- L325: TC-001-1-0041-UNE - Validar error por typeAccount CBU null
- L325: TC-001-1-0042-UNE - Validar error por account con espacio
- L325: TC-001-1-0043-UNE - Validar error por account con guión
- L325: TC-001-1-0044-UNE - Validar error por account con longitud no permitida
- L325: TC-001-1-0045-UNE - Validar error por account con apóstrofe
- L325: TC-001-1-0046-UNE - Validar error por account vacío
- L325: TC-001-1-0047-UNE - Validar error por account null
- L325: TC-001-1-0048-UNE - Validar error por account CBU con longitud inválida (-1)
- L325: TC-001-1-0049-UNE - Validar error por account cbu con caracteres especiales
- L325: TC-001-1-0050-UNE - Validar error por account CBU boolean true
- L325: TC-001-1-0051-UNE - Validar error por account CBU boolean false
- L325: TC-001-1-0052-UNE - Validar error por typeAccount CVU vacío
- L325: TC-001-1-0053-UNE - Validar error por typeAccount CVU null
- L325: TC-001-1-0054-UNE - Validar error por account CVU con espacio
- L325: TC-001-1-0055-UNE - Validar error por account CVU con guión
- L325: TC-001-1-0056-UNE - Validar error por account CVU con longitud no permitida
- L325: TC-001-1-0057-UNE - Validar error por account CVU con apóstrofe
- L325: TC-001-1-0058-UNE - Validar error por account CVU vacío
- L325: TC-001-1-0059-UNE - Validar error por account CVU null
- L325: TC-001-1-0060-UNE - Validar error por account CVU con longitud inválida (-1)
- L325: TC-001-1-0061-UNE - Validar error por account CVU con longitud inválida (+1)
- L325: TC-001-1-0062-UNE - Validar error por account CVU con caracteres especiales
- L325: TC-001-1-0063-UNE - Validar error por account CVU boolean true
- L325: TC-001-1-0064-UNE - Validar error por account CVU boolean false

### `tests/api/001-accounts/should-reject-invalid-echeq-list-filters.api.spec.ts`

Fixtures: `echeqListApi`, `loginApi`, `loginCredentials`

- L113: TC-001-1-0065-UNE - Validar error por echeqNumber con espacio
- L113: TC-001-1-0066-UNE - Validar error por echeqNumber con caracteres especiales
- L113: TC-001-1-0067-UNE - Validar error por statement con espacio
- L113: TC-001-1-0068-UNE - Validar error por statement con caracteres especiales
- L113: TC-001-1-0069-UNE - Validar error por filterCuil con espacio
- L113: TC-001-1-0070-UNE - Validar error por filterCuil con caracteres especiales
- L113: TC-001-1-0071-UNE - Validar error por operationType con espacio
- L113: TC-001-1-0072-UNE - Validar error por operationType con caracteres especiales
- L113: TC-001-1-0073-UNE - Validar error por page con espacio
- L113: TC-001-1-0074-UNE - Validar error por page con caracteres especiales
- L113: TC-001-1-0075-UNE - Validar error por filterCuil boolean true
- L113: TC-001-1-0076-UNE - Validar error por filterCuil boolean false
- L113: TC-001-1-0077-UNE - Validar error por filterCuil null
- L113: TC-001-1-0078-UNE - Validar error por page con guión
- L113: TC-001-1-0079-UNE - Validar error por page alfabético no permitido
- L113: TC-001-1-0080-UNE - Validar error por page boolean true
- L113: TC-001-1-0081-UNE - Validar error por page boolean false
- L113: TC-001-1-0082-UNE - Validar error por page con prefijo y espacio

### `tests/api/001-accounts/should-request-salary-advance.api.spec.ts`

Fixtures: `loginApi`, `loginCredentials`, `originAccountData`, `productWidgetsApi`, `salaryAdvanceApi`

- L12: TC-001-1-0003-UNE - Adelantos de Sueldo - Realizar solicitud de adelanto de sueldo

### `tests/api/001-accounts/should-show-salary-advance-card.api.spec.ts`

Fixtures: `loginApi`, `loginCredentials`, `salaryAdvanceApi`

- L8: TC-001-1-0004-UNE - Adelantos de Sueldo - Mis adelantos - Validar card de adelantos de sueldo

## `tests/api/002-authentication/`

Especificaciones: `specs/authentication/api-login.plan.md`, `specs/authentication/login.plan.md`, `specs/authentication/second-factor-code-inbox.plan.md`

### `tests/api/002-authentication/should-login-and-capture-bearer.api.spec.ts`

Fixtures: `loginApi`, `loginCredentials`

- L8: TC-002-1-0001-UNE - inicia sesión y captura el Bearer token @regression

## `tests/api/003-cards/`

Especificaciones: `specs/cards/credit-card-other-amount-payment.api.plan.md`, `specs/cards/credit-card-total-payment.ui.plan.md`

### `tests/api/003-cards/should-pay-credit-card-minimum.api.spec.ts`

Fixtures: `creditCardPaymentData`, `creditCardPaymentsApi`, `loginApi`, `loginCredentials`, `productWidgetsApi`

- L18: TC-003-1-0002-UNE - Tarjeta de Crédito - Pagar mínimo de una Tarjeta de Crédito Visa
- L18: TC-003-1-0008-UNE - Tarjetas de crédito - Pagar el importe mínimo de una tarjeta de crédito Mastercard

### `tests/api/003-cards/should-pay-credit-card-other-amount.api.spec.ts`

Fixtures: `creditCardOtherAmountPaymentApi`, `creditCardOtherAmountPaymentData`, `loginApi`, `loginCredentials`

- L8: TC-003-1-0007-UNE - paga ARS 1 de una tarjeta Visa por otro importe @regression

### `tests/api/003-cards/should-pay-mastercard-other-amount.api.spec.ts`

Fixtures: `creditCardPaymentData`, `creditCardPaymentsApi`, `loginApi`, `loginCredentials`, `productWidgetsApi`

- L12: TC-003-1-0003-UNE - Pagar tarjeta de crédito Master - Otro importe

### `tests/api/003-cards/should-reject-credit-card-request-by-declaration.api.spec.ts`

Fixtures: `creditCardApplicationApi`, `loginApi`, `loginCredentials`, `optionalOriginAccountData`, `paymentLinkSyncApi`, `productWidgetsApi`

- L39: TC-003-1-0004-UNE - Tarjetas de crédito - Persona expuesta políticamente no permite continuar la solicitud
- L39: TC-003-1-0005-UNE - Tarjetas de crédito - Residencia fiscal en el exterior no permite continuar la solicitud
- L39: TC-003-1-0006-UNE - Tarjetas de crédito - Sujeto obligado UIF no permite continuar la solicitud

### `tests/api/003-cards/should-request-credit-card.api.spec.ts`

Fixtures: `creditCardApplicationApi`, `loginApi`, `loginCredentials`, `optionalOriginAccountData`, `paymentLinkSyncApi`, `productWidgetsApi`

- L9: TC-003-1-0001-UNE - Tarjeta de Crédito - Solicitar nueva tarjeta de crédito

### `tests/api/003-cards/should-send-applicant-cuil-in-credit-card-request.api.spec.ts`

Fixtures: `creditCardApplicationApi`, `loginApi`, `loginCredentials`, `optionalOriginAccountData`, `paymentLinkSyncApi`, `productWidgetsApi`

- L22: TC-003-1-0009-UNE - API - Tarjeta de Crédito - Verificar que el dato cuilSolicitante se envíe en application/create en nueva alta de TC
- L22: TC-003-1-0010-UNE - API - Tarjeta de Crédito - Verificar que el dato cuilSolicitante se envíe en application/update en alta de TC ya iniciada

## `tests/api/005-home/`

Especificaciones: `specs/home/terms-and-conditions.api.plan.md`

### `tests/api/005-home/should-get-terms-and-conditions.api.spec.ts`

Fixtures: `termsAndConditionsApi`

- L8: TC-005-1-0001-UNE - obtiene los términos y condiciones generales

## `tests/api/007-investments/`

Especificaciones: `specs/investments/fund-risk-filter.ui.plan.md`, `specs/investments/traditional-fixed-term.api.plan.md`

### `tests/api/007-investments/should-create-traditional-fixed-term.api.spec.ts`

Fixtures: `loginApi`, `loginCredentials`, `traditionalFixedTermApi`, `traditionalFixedTermData`

- L8: TC-007-1-0002-UNE - constituye un plazo fijo tradicional en ARS a 30 días

### `tests/api/007-investments/should-create-usd-fixed-term.api.spec.ts`

Fixtures: `loginApi`, `loginCredentials`, `originAccountData`, `productWidgetsApi`, `traditionalFixedTermApi`

- L9: TC-007-1-0008-UNE - Plazo Fijo Tradicional en Dólares

### `tests/api/007-investments/should-exchange-dollars.api.spec.ts`

Fixtures: `accountPairData`, `dollarExchangeApi`, `loginApi`, `loginCredentials`, `productWidgetsApi`

- L9: TC-007-1-0003-UNE - Compra de moneda extranjera (USD)
- L32: TC-007-1-0001-UNE - Venta de moneda extranjera (USD)

### `tests/api/007-investments/should-list-fund-movements.api.spec.ts`

Fixtures: `investmentFundsApi`, `loginApi`, `loginCredentials`

- L22: TC-007-1-0006-UNE - FCI - Movimientos
- L38: TC-007-1-0007-UNE - FCI - Movimientos con descarga PDF

### `tests/api/007-investments/should-open-investment-funds.api.spec.ts`

Fixtures: `investmentFundsApi`, `loginApi`, `loginCredentials`

- L9: TC-007-1-0004-UNE - FCI - Simular API (inicio a la funcionalidad)

### `tests/api/007-investments/should-show-commitent-account-closure-form.api.spec.ts`

Fixtures: `commitentAccountApi`, `loginApi`, `loginCredentials`

- L16: TC-007-1-0005-UNE - FCI - Solicitar baja cuenta comitente - Validación de pantalla

## `tests/api/009-messages/`

Especificaciones: `specs/messages/mark-message-unread.ui.plan.md`, `specs/messages/received-messages.api.plan.md`

### `tests/api/009-messages/should-filter-messages-by-read-state.api.spec.ts`

Fixtures: `loginApi`, `loginCredentials`, `receivedMessagesApi`

- L11: TC-009-1-0001-UNE - Mensajes - Sin Leer
- L26: TC-009-1-0002-UNE - Mensajes - Leídos

### `tests/api/009-messages/should-list-received-messages.api.spec.ts`

Fixtures: `loginApi`, `loginCredentials`, `receivedMessagesApi`

- L8: TC-009-1-0003-UNE - consulta los mensajes recibidos

## `tests/api/011-payments/`

Especificaciones: `specs/payments/debin-generation.api.plan.md`

### `tests/api/011-payments/should-accept-pending-debin.api.spec.ts`

Fixtures: `debinOperationsApi`, `debitCardData`, `loginApi`, `loginCredentials`, `paymentLinkSyncApi`, `productWidgetsApi`

- L10: TC-011-1-0004-UNE - Aceptación de DEBIN

### `tests/api/011-payments/should-adhere-tax-service.api.spec.ts`

Fixtures: `debitCardData`, `loginApi`, `loginCredentials`, `paymentLinkSyncApi`, `productWidgetsApi`, `taxServicesApi`

- L11: TC-011-1-0005-UNE - Adherir nuevo pago o servicio

### `tests/api/011-payments/should-download-payment-receipts-period.api.spec.ts`

Fixtures: `debitCardData`, `loginApi`, `loginCredentials`, `paymentLinkSyncApi`, `paymentReceiptsApi`, `productWidgetsApi`, `receivedMessagesApi`

- L26: TC-011-1-0007-UNE - Pagos - Comprobantes - Descargar periodo en PDF
- L26: TC-011-1-0008-UNE - Pagos - Comprobantes - Descargar periodo en XLS

### `tests/api/011-payments/should-generate-debin.api.spec.ts`

Fixtures: `debinGenerationApi`, `debinGenerationData`, `loginApi`, `loginCredentials`

- L8: TC-011-1-0002-UNE - genera una orden DEBIN seleccionando CBU

### `tests/api/011-payments/should-list-debin-history.api.spec.ts`

Fixtures: `debinOperationsApi`, `loginApi`, `loginCredentials`, `paymentLinkSyncApi`

- L8: TC-011-1-0001-UNE - DEBIN Comprador - Consulta DEBINes históricos - Listado

### `tests/api/011-payments/should-list-payment-receipts.api.spec.ts`

Fixtures: `debitCardData`, `loginApi`, `loginCredentials`, `paymentLinkSyncApi`, `paymentReceiptsApi`, `productWidgetsApi`

- L12: TC-011-1-0006-UNE - Pagos - Consulta de comprobantes

### `tests/api/011-payments/should-pay-tax-service-without-base.api.spec.ts`

Fixtures: `debitCardData`, `loginApi`, `loginCredentials`, `originAccountData`, `paymentLinkSyncApi`, `productWidgetsApi`, `taxServicesApi`

- L11: TC-011-1-0009-UNE - Pagar impuesto/servicio nuevo pago (sin base)

### `tests/api/011-payments/should-remove-tax-service.api.spec.ts`

Fixtures: `debitCardData`, `loginApi`, `loginCredentials`, `paymentLinkSyncApi`, `productWidgetsApi`, `taxServicesApi`

- L10: TC-011-1-0010-UNE - Pagar impuesto/servicio - Eliminar servicio

### `tests/api/011-payments/should-subscribe-recurring-debin.api.spec.ts`

Fixtures: `debinOperationsApi`, `loginApi`, `loginCredentials`, `originAccountData`, `paymentLinkSyncApi`, `productWidgetsApi`

- L11: TC-011-1-0003-UNE - Adherir DEBIN recurrente (Pesos) - segundo factor por SMS

## `tests/api/012-procedures/`

Especificaciones: `specs/procedures/current-online-procedures.api.plan.md`

### `tests/api/012-procedures/should-list-current-online-procedures.api.spec.ts`

Fixtures: `currentOnlineProceduresApi`, `loginApi`, `loginCredentials`

- L8: TC-012-1-0001-UNE - consulta los trámites vigentes

## `tests/api/013-profile/`

Especificaciones: `specs/profile/change-address.ui.plan.md`, `specs/profile/change-phone.api.plan.md`, `specs/profile/notification-preferences.api.plan.md`

### `tests/api/013-profile/should-change-phone.api.spec.ts`

Fixtures: `loginApi`, `loginCredentials`, `phoneChangeApi`

- L9: TC-013-1-0001-UNE - cambia el teléfono y verifica que quedó persistido

### `tests/api/013-profile/should-get-notification-preferences.api.spec.ts`

Fixtures: `loginApi`, `loginCredentials`, `notificationPreferencesApi`

- L8: TC-013-1-0002-UNE - consulta la configuración de notificaciones

## `tests/api/015-transfers/`

Especificaciones: `specs/transfers/own-account-transfer.api.plan.md`, `specs/transfers/own-account-transfer.ui.plan.md`

### `tests/api/015-transfers/should-generate-credin.api.spec.ts`

Fixtures: `credinApi`, `loginApi`, `loginCredentials`, `productWidgetsApi`, `transferCaseData`

- L56: TC-015-1-0002-UNE - Generar CREDIN - Cuenta propia $ (mismo Banco u otro) - Mas de una cuenta Debito - CBU - Segundo factor por SMS
- L56: TC-015-1-0031-UNE - TRANSFERENCIAS - Generar CREDIN - Dolares
- L73: TC-015-1-0032-UNE - TRANSFERENCIAS - Consultar CREDIN Originante
- L73: TC-015-1-0033-UNE - TRANSFERENCIAS - Consultar CREDIN destinatario

### `tests/api/015-transfers/should-list-transfer-history.api.spec.ts`

Fixtures: `loginApi`, `loginCredentials`, `thirdPartyTransfersApi`, `thirdPartyTransferWorkflow`, `transferCaseData`, `transferHistoryApi`

- L20: TC-015-1-0045-UNE - Transferencias programadas - Fecha específica agendada - Historial de transferencias
- L66: TC-015-1-0046-UNE - Transferencias a contacto agendado - Historial de transferencias
- L116: TC-015-1-0047-UNE - Transferencias a CVU - Historial de transferencias
- L158: TC-015-1-0036-UNE - Transferencias realizadas - Ver mis transferencias - Filtros
- L185: TC-015-1-0037-UNE - Transferencias realizadas - Ver mis transferencias - rango más nombre en buscador
- L185: TC-015-1-0038-UNE - Transferencias realizadas - Ver mis transferencias - rango más nombre en buscador
- L203: TC-015-1-0048-UNE - Transferencias realizadas - Ver mis transferencias - Búsqueda sin resultados

### `tests/api/015-transfers/should-manage-transfer-contacts.api.spec.ts`

Fixtures: `loginApi`, `loginCredentials`, `thirdPartyTransfersApi`, `thirdPartyTransferWorkflow`, `transferCaseData`

- L11: TC-015-1-0004-UNE - Transferencias - Añadir nuevo destinatario - Con cbu valido
- L38: TC-015-1-0035-UNE - Transferencias - Eliminar destinatario de agenda

### `tests/api/015-transfers/should-schedule-transfers.api.spec.ts`

Fixtures: `loginApi`, `loginCredentials`, `productWidgetsApi`, `thirdPartyTransfersApi`, `thirdPartyTransferWorkflow`, `transferCaseData`

- L47: TC-015-1-0018-UNE - Transferencias Programadas (Fecha especifica) CBU - terceros mismo banco USD - NO AGENDADOS
- L47: TC-015-1-0019-UNE - Transferencias Programadas (Fecha especifica) CBU - terceros otros bancos $ - NO AGENDADOS
- L47: TC-015-1-0020-UNE - Transferencias Programadas (Fecha especifica) CBU - terceros otros bancos USD - NO AGENDADOS
- L47: TC-015-1-0022-UNE - Transferencias Programadas (Fecha especifica) ALIAS - terceros mismo banco USD - NO AGENDADOS
- L47: TC-015-1-0023-UNE - Transferencias Programadas (Fecha especifica) ALIAS - terceros otros bancos $ - NO AGENDADOS
- L47: TC-015-1-0024-UNE - Transferencias Programadas (Fecha especifica) ALIAS - terceros otros bancos USD - NO AGENDADOS
- L47: TC-015-1-0026-UNE - Transferencias Programadas (Fecha especifica) CVU - terceros $ NO AGENDADOS
- L47: TC-015-1-0027-UNE - Transferencias Programadas (Fecha especifica) ALIAS de CVU - terceros $ NO AGENDADOS
- L84: TC-015-1-0010-UNE - Transferencias CBU - terceros otros bancos USD - NO AGENDADOS
- L129: TC-015-1-0040-UNE - Programada (fecha específica) CBU a cuentas propias otro banco $ - NO AGENDADO
- L129: TC-015-1-0042-UNE - Programada (fecha específica) CBU a Terceros otro banco $ - NO AGENDADOS
- L178: TC-015-1-0015-UNE - Programadas (fecha específica) - terceros mismo banco $ - AGENDADOS
- L178: TC-015-1-0016-UNE - Programadas (fecha específica) - terceros otros bancos $ - AGENDADOS
- L178: TC-015-1-0017-UNE - Programadas (fecha específica) - terceros otros bancos USD - AGENDADOS
- L178: TC-015-1-0025-UNE - Programadas (fecha específica) CVU - terceros $ AGENDADOS
- L215: TC-015-1-0001-UNE - Transferencia intrabancaria en pesos - propias - programada

### `tests/api/015-transfers/should-transfer-between-own-accounts.api.spec.ts`

Fixtures: `loginApi`, `loginCredentials`, `ownAccountTransferApi`, `ownAccountTransferData`

- L8: TC-015-1-0003-UNE - transfiere entre cuentas propias y valida el ticket @critical

### `tests/api/015-transfers/should-transfer-to-adhered-contact.api.spec.ts`

Fixtures: `loginApi`, `loginCredentials`, `thirdPartyTransfersApi`, `thirdPartyTransferWorkflow`, `transferCaseData`

- L20: TC-015-1-0006-UNE - TRANSFERENCIAS - terceros mismo banco $ - AGENDADOS
- L20: TC-015-1-0007-UNE - Transferencias - terceros mismo banco USD - AGENDADOS
- L20: TC-015-1-0008-UNE - Transferencias - terceros otros bancos $ - AGENDADOS
- L20: TC-015-1-0009-UNE - Transferencias - terceros mismo banco USD - AGENDADOS
- L20: TC-015-1-0013-UNE - Transferencias CVU - terceros $ AGENDADOS

### `tests/api/015-transfers/should-transfer-to-third-parties.api.spec.ts`

Fixtures: `loginApi`, `loginCredentials`, `thirdPartyTransfersApi`, `thirdPartyTransferWorkflow`, `transferCaseData`

- L117: TC-015-1-0005-UNE - A cuentas de terceros ALIAS - otros bancos - Pesos
- L117: TC-015-1-0011-UNE - ALIAS - terceros mismo banco USD - NO AGENDADOS
- L117: TC-015-1-0012-UNE - ALIAS - terceros otros bancos USD - NO AGENDADOS
- L117: TC-015-1-0014-UNE - ALIAS de CVU - terceros $ NO AGENDADOS
- L117: TC-015-1-0028-UNE - A cuentas de terceros CBU - mismo banco - Pesos
- L117: TC-015-1-0029-UNE - A cuentas de terceros ALIAS - mismo banco - Pesos
- L117: TC-015-1-0034-UNE - A cuentas de terceros CVU - otros bancos - Pesos
- L117: TC-015-1-0043-UNE - Saldo actualizado - Transferencias a un Alias CVU en pesos
- L117: TC-015-1-0044-UNE - Saldo actualizado - Transferencias a un Alias CBU en dólares
- L174: TC-015-1-0021-UNE - Transferencias Programadas (Fecha especifica) ALIAS - terceros mismo banco $ - NO AGENDADOS
- L174: TC-015-1-0030-UNE - Transferencias Programadas (Fecha especifica) CBU - terceros mismo banco $ - NO AGENDADOS
- L213: TC-015-1-0039-UNE - Transferencia a cuentas propias con CBU de otra entidad sin el campo Descripción al agendarse
- L213: TC-015-1-0041-UNE - Transferencia a Terceros con CBU de otra entidad sin el campo Descripción

## `tests/ui/001-accounts/`

Especificaciones: `specs/accounts/withdrawal-order.api.plan.md`

### `tests/ui/001-accounts/should-enable-new-account-request-continue.ui.spec.ts`

Fixtures: `accountsPage`, `loginCredentials`, `loginPage`, `startPage`

- L13: TC-001-2-0011-UNE - habilita Continuar al elegir Caja de Ahorro en Pesos
- L13: TC-001-2-0012-UNE - habilita Continuar al elegir Caja de Ahorro en Dólares
- L13: TC-001-2-0013-UNE - habilita Continuar al elegir Cuenta Corriente en Pesos

### `tests/ui/001-accounts/should-filter-echeqs.ui.spec.ts`

Fixtures: `echeqPage`, `loginCredentials`, `loginPage`, `startPage`

- L10: TC-001-2-0015-UNE - combina filtros de CUIT, estado y fecha de emisión

### `tests/ui/001-accounts/should-open-salary-advance-contract.ui.spec.ts`

Fixtures: `loginCredentials`, `loginPage`, `originAccountUiData`, `salaryAdvancePage`, `startPage`

- L9: TC-001-2-0007-UNE - redirige al documento del contrato al aceptar
- L33: TC-001-2-0008-UNE - vuelve a la solicitud de adelanto al cancelar la redirección

### `tests/ui/001-accounts/should-operate-echeqs.ui.spec.ts`

Fixtures: `echeqPage`, `loginApi`, `loginCredentials`, `loginPage`, `smsOperationOtpApi`, `startPage`

- L23: TC-001-2-0014-UNE - muestra los campos del filtro de eCheq
- L45: TC-001-2-0001-UNE - muestra el detalle del primer eCheq con todos los estados
- L70: TC-001-2-0016-UNE - acepta un eCheq emitido pendiente con segundo factor

### `tests/ui/001-accounts/should-show-account-movement-detail.ui.spec.ts`

Fixtures: `accountsPage`, `loginCredentials`, `loginPage`, `originAccountUiData`, `startPage`

- L9: TC-001-2-0002-UNE - muestra fecha, concepto y monto del primer movimiento

### `tests/ui/001-accounts/should-transfer-to-scheduled-contact-and-find-movement.ui.spec.ts`

Fixtures: `accountMovementsPage`, `accountMovementsUiData`, `loginCredentials`, `loginPage`, `startPage`

- L8: TC-001-2-0003-UNE - filtra movimientos por tipo de transacción en una cuenta

### `tests/ui/001-accounts/should-validate-account-alias.ui.spec.ts`

Fixtures: `accountAliasPage`, `accountAliasUiData`, `loginCredentials`, `loginPage`, `startPage`

- L11: TC-001-2-0017-UNE - Web - Modif. Alias - Validar controles del campo mandatorio Alias

### `tests/ui/001-accounts/should-validate-salary-advance-request.ui.spec.ts`

Fixtures: `homePage`, `loginCredentials`, `loginPage`, `originAccountUiData`, `salaryAdvancePage`, `startPage`

- L13: TC-001-2-0004-UNE - muestra la pantalla completa de solicitud de adelanto
- L35: TC-001-2-0005-UNE - vuelve a la solicitud al presionar Volver
- L58: TC-001-2-0006-UNE - exige aceptar términos y condiciones al confirmar
- L85: TC-001-2-0009-UNE - informa error con un importe inferior al mínimo
- L85: TC-001-2-0010-UNE - informa error con un importe superior al máximo

## `tests/ui/002-authentication/`

Especificaciones: `specs/authentication/api-login.plan.md`, `specs/authentication/login.plan.md`, `specs/authentication/second-factor-code-inbox.plan.md`

### `tests/ui/002-authentication/should-login-and-capture-bearer.ui.spec.ts`

Fixtures: `loginCredentials`, `loginPage`, `startPage`

- L9: TC-002-2-0001-UNE - inicia sesión y captura el Bearer token @regression

### `tests/ui/002-authentication/should-login-with-virtual-keyboard.ui.spec.ts`

Fixtures: `homePage`, `loginCredentials`, `loginPage`, `startPage`

- L9: TC-002-2-0003-UNE - inicia sesión con el teclado virtual y muestra el saludo

### `tests/ui/002-authentication/should-recover-username.ui.spec.ts`

Fixtures: `homePage`, `loginApi`, `loginCredentials`, `loginPage`, `profileMobilePhoneApi`, `secondFactorChallengeApi`, `startPage`

- L13: TC-002-2-0002-UNE - envía el usuario por correo y permite volver a ingresar
- L13: TC-002-2-0004-UNE - envía el usuario por correo y permite volver a ingresar

## `tests/ui/003-cards/`

Especificaciones: `specs/cards/credit-card-other-amount-payment.api.plan.md`, `specs/cards/credit-card-total-payment.ui.plan.md`

### `tests/ui/003-cards/should-consult-credit-cards.ui.spec.ts`

Fixtures: `creditCardDetailUiData`, `creditCardsPage`, `loginCredentials`, `loginPage`, `startPage`

- L8: TC-003-2-0001-UNE - abre el detalle de una tarjeta Visa por su número
- L31: TC-003-2-0002-UNE - abre el detalle de una tarjeta Mastercard
- L54: TC-003-2-0003-UNE - muestra descarga de resumen, movimientos del período y solicitud de adicional

### `tests/ui/003-cards/should-download-credit-card-statement.ui.spec.ts`

Fixtures: `creditCardDetailUiData`, `creditCardsPage`, `loginCredentials`, `loginPage`, `startPage`

- L10: TC-003-2-0004-UNE - Tarjeta de Crédito - Descarga de resumen
- L40: TC-003-2-0011-UNE - Tarjeta de Crédito - Consultar último resumen de una Tarjeta de Crédito

### `tests/ui/003-cards/should-open-total-credit-card-payment-review.ui.spec.ts`

Fixtures: `creditCardPaymentPage`, `creditCardTotalPaymentUiData`, `loginCredentials`, `loginPage`, `startPage`

- L18: TC-003-2-0010-UNE - muestra la revisión del pago total de una tarjeta Mastercard propia

### `tests/ui/003-cards/should-redirect-new-card-request-to-appointments.ui.spec.ts`

Fixtures: `creditCardsPage`, `loginCredentials`, `loginPage`, `newCreditCardRequestPage`, `optionalOriginAccountData`, `startPage`

- L8: TC-003-2-0012-UNE - Solicitud de tarjeta de crédito - Sujeto obligado UIF redirige a solicitar turno

### `tests/ui/003-cards/should-show-credit-card-detail-information.ui.spec.ts`

Fixtures: `creditCardPaymentData`, `creditCardsPage`, `loginCredentials`, `loginPage`, `startPage`

- L21: TC-003-2-0013-UNE - Web - Detalle de TC - Validar la información en Detalle de TC VISA y MASTER

### `tests/ui/003-cards/should-show-credit-card-tracking.ui.spec.ts`

Fixtures: `creditCardTrackingPage`

- L42: TC-003-2-0005-UNE - Tarjeta de Crédito - Seguimiento de Tarjetas de Crédito
- L52: TC-003-2-0006-UNE - Tarjeta de Crédito - Seguimiento de Tarjetas de Crédito - Detalles
- L68: TC-003-2-0007-UNE - Tarjeta de Crédito - Seguimiento de Tarjetas de Crédito - Descargar Detalles
- L85: TC-003-2-0008-UNE - Tarjeta de Crédito - Seguimiento de Tarjetas de Crédito - Imprimir Detalles

### `tests/ui/003-cards/should-start-credit-card-theft-report.ui.spec.ts`

Fixtures: `creditCardDetailUiData`, `creditCardsPage`, `loginCredentials`, `loginPage`, `startPage`

- L9: TC-003-2-0009-UNE - Tarjeta de Crédito - Denunciar robo o extravío de Tarjeta de Crédito

## `tests/ui/004-documents/`

### `tests/ui/004-documents/should-download-product-contract.ui.spec.ts`

Fixtures: `loginCredentials`, `loginPage`, `productDocumentsPage`, `startPage`

- L9: TC-004-2-0001-UNE - descarga el contrato del producto

## `tests/ui/005-home/`

Especificaciones: `specs/home/terms-and-conditions.api.plan.md`

### `tests/ui/005-home/should-show-warning-alert.ui.spec.ts`

Fixtures: `homePage`, `loginCredentials`, `loginPage`, `startPage`

- L12: TC-005-2-0001-UNE - muestra el aviso en formato advertencia

## `tests/ui/006-insurance/`

### `tests/ui/006-insurance/should-list-insurance-cards.ui.spec.ts`

Fixtures: `insurancePage`, `loginCredentials`, `loginPage`, `startPage`

- L9: TC-006-2-0001-UNE - muestra la tarjeta del seguro de Robo

### `tests/ui/006-insurance/should-open-robbery-insurance-detail.ui.spec.ts`

Fixtures: `insurancePage`, `loginCredentials`, `loginPage`, `startPage`

- L8: TC-006-2-0002-UNE - muestra el detalle del seguro de Robo

### `tests/ui/006-insurance/should-reach-robbery-insurance-confirmation.ui.spec.ts`

Fixtures: `insurancePage`, `loginCredentials`, `loginPage`, `startPage`

- L8: TC-006-2-0003-UNE - llega a la confirmación de la solicitud de seguro

## `tests/ui/007-investments/`

Especificaciones: `specs/investments/fund-risk-filter.ui.plan.md`, `specs/investments/traditional-fixed-term.api.plan.md`

### `tests/ui/007-investments/funds-by-risk-filter.ui.spec.ts`

Fixtures: `investmentFundsPage`, `loginCredentials`, `loginPage`, `startPage`

- L9: TC-007-2-0004-UNE - muestra únicamente fondos de riesgo moderado

### `tests/ui/007-investments/should-filter-funds.ui.spec.ts`

Fixtures: `investmentFundsPage`, `loginCredentials`, `loginPage`, `startPage`

- L11: TC-007-2-0003-UNE - FCI - Filtros - Mi Tenencia - Fondos por Riesgo - Conservador
- L47: TC-007-2-0005-UNE - FCI - Filtros: Fondos por moneda - Pesos
- L47: TC-007-2-0006-UNE - FCI - Filtros: Fondos por moneda - Dólar
- L73: TC-007-2-0007-UNE - FCI - Filtros: Mi Tenencia

### `tests/ui/007-investments/should-open-fund-more-information.ui.spec.ts`

Fixtures: `investmentFundsPage`, `loginCredentials`, `loginPage`, `startPage`

- L11: TC-007-2-0008-UNE - FCI - Más información - Aceptar modal
- L36: TC-007-2-0009-UNE - FCI - Más información - Rechazar modal

### `tests/ui/007-investments/should-query-fixed-term-rates.ui.spec.ts`

Fixtures: `fixedTermsPage`, `loginCredentials`, `loginPage`, `startPage`

- L23: TC-007-2-0001-UNE - PF - Consulta de Tasas - Tipo - Tradicional en Pesos
- L23: TC-007-2-0002-UNE - PF - Consulta de Tasas - Tipo - Tradicional en Dólares

### `tests/ui/007-investments/should-reach-commitent-account-confirmation.ui.spec.ts`

Fixtures: `commitentAccountOpeningPage`, `investmentFundsPage`, `loginCredentials`, `loginPage`, `startPage`

- L8: TC-007-2-0010-UNE - Inversiones - Alta cuenta comitente - Validar pantalla etapa 5 'Confirmación'

### `tests/ui/007-investments/should-reject-zero-amount-foreign-currency-purchase.ui.spec.ts`

Fixtures: `foreignCurrencyPurchasePage`, `foreignCurrencyPurchaseUiData`, `loginCredentials`, `loginPage`, `startPage`

- L8: TC-007-2-0011-UNE - rechaza la compra de dólares con importe cero

### `tests/ui/007-investments/should-validate-dollar-exchange-form.ui.spec.ts`

Fixtures: `foreignCurrencyPurchasePage`, `loginCredentials`, `loginPage`, `originAccountData`, `startPage`

- L27: TC-007-2-0012-UNE - Inversiones - Compra de moneda extranjera - Seguir flujo de compra de dólar sin aceptar check
- L27: TC-007-2-0016-UNE - Inversiones - Venta de moneda extranjera - Seguir flujo de venta de dólar sin aceptar check
- L60: TC-007-2-0015-UNE - Inversiones - Venta de moneda extranjera - Importe 0 - Validar mensaje
- L124: TC-007-2-0013-UNE - Inversiones - Compra - Desplegable Cuenta Origen sólo cuentas en pesos
- L124: TC-007-2-0014-UNE - Inversiones - Compra - Desplegable Cuenta Destino sólo cuentas en dólares
- L124: TC-007-2-0017-UNE - Inversiones - Venta - Desplegable Cuenta Destino sólo cuentas en pesos
- L124: TC-007-2-0018-UNE - Inversiones - Venta - Desplegable Cuenta Origen sólo cuentas en dólares

### `tests/ui/007-investments/should-validate-fixed-term-request-continue.ui.spec.ts`

Fixtures: `accountPairData`, `fixedTermsPage`, `loginCredentials`, `loginPage`, `startPage`

- L24: TC-007-2-0019-UNE - Web - Alta de PF - Verificar lógica del botón Continuar y mensaje de error en Simulación

## `tests/ui/008-loans/`

### `tests/ui/008-loans/should-request-loans.ui.spec.ts`

Fixtures: `loanRequestPage`, `loginCredentials`, `loginPage`, `startPage`

- L9: TC-008-2-0001-UNE - llega a la confirmación de un nuevo préstamo
- L48: TC-008-2-0002-UNE - llega a la confirmación de un préstamo para cancelar deudas
- L83: TC-008-2-0003-UNE - abre el resumen de contrato del préstamo para cancelar deudas

## `tests/ui/009-messages/`

Especificaciones: `specs/messages/mark-message-unread.ui.plan.md`, `specs/messages/received-messages.api.plan.md`

### `tests/ui/009-messages/should-delete-message.ui.spec.ts`

Fixtures: `loginCredentials`, `loginPage`, `messagesPage`, `startPage`

- L9: TC-009-2-0002-UNE - elimina el primer mensaje de la bandeja

### `tests/ui/009-messages/should-mark-message-unread.ui.spec.ts`

Fixtures: `loginCredentials`, `loginPage`, `messagesPage`, `startPage`

- L8: TC-009-2-0001-UNE - marca un mensaje como no leído

## `tests/ui/010-mis-products/`

Especificaciones: `specs/mis-products/should-open-cancellation-page.ui.plan.md`

### `tests/ui/010-mis-products/should-open-cancellation-page.ui.spec.ts`

Fixtures: `cancellationPage`, `loginCredentials`, `loginPage`, `startPage`

- L8: TC-010-2-0001-UNE - acepta la redirección a Baja y Arrepentimiento

## `tests/ui/011-payments/`

Especificaciones: `specs/payments/debin-generation.api.plan.md`

### `tests/ui/011-payments/should-pay-and-find-service-receipts.ui.spec.ts`

Fixtures: `loginApi`, `loginCredentials`, `loginPage`, `paymentReceiptsPage`, `paymentServicesPage`, `paymentServicesPreparationWorkflow`, `startPage`

- L12: TC-011-2-0004-UNE - muestra el estado de los pagos realizados en simultáneo
- L44: TC-011-2-0003-UNE - filtra comprobantes de pago por empresa y descripción

### `tests/ui/011-payments/should-require-debin-buyer-identifier.ui.spec.ts`

Fixtures: `debinSellerPage`, `loginCredentials`, `loginPage`, `originAccountUiData`, `startPage`

- L8: TC-011-2-0001-UNE - exige el CBU del comprador al buscar sin ingresarlo
- L36: TC-011-2-0002-UNE - exige el alias del comprador al buscar sin ingresarlo

## `tests/ui/012-procedures/`

Especificaciones: `specs/procedures/current-online-procedures.api.plan.md`

### `tests/ui/012-procedures/should-open-online-appointments.ui.spec.ts`

Fixtures: `appointmentsPage`, `loginCredentials`, `loginPage`, `startPage`

- L10: TC-012-2-0001-UNE - acepta la redirección para solicitar un turno

## `tests/ui/013-profile/`

Especificaciones: `specs/profile/change-address.ui.plan.md`, `specs/profile/change-phone.api.plan.md`, `specs/profile/notification-preferences.api.plan.md`

### `tests/ui/013-profile/should-change-address.ui.spec.ts`

Fixtures: `changeAddressPage`, `loginApi`, `loginCredentials`, `loginPage`, `profileMobilePhoneApi`, `secondFactorChallengeApi`, `startPage`

- L10: TC-013-2-0001-UNE - cambia el domicilio con un segundo factor SMS @regression

### `tests/ui/013-profile/should-change-security-seal.ui.spec.ts`

Fixtures: `loginApi`, `loginCredentials`, `loginPage`, `securitySealPage`, `smsOperationOtpApi`, `startPage`

- L9: TC-013-2-0002-UNE - cambia el avatar con segundo factor

## `tests/ui/014-start-page/`

### `tests/ui/014-start-page/should-open-registration.ui.spec.ts`

Fixtures: `identityValidationPage`, `startPage`

- L8: TC-014-2-0002-UNE - acceso directo ¿No sos cliente aún? Registrarme

### `tests/ui/014-start-page/should-open-security-information.ui.spec.ts`

Fixtures: `loginPage`, `startPage`

- L9: TC-014-2-0003-UNE - abre el enlace Información de seguridad

### `tests/ui/014-start-page/should-open-store-shortcut.ui.spec.ts`

Fixtures: `loginPage`, `startPage`

- L9: TC-014-2-0001-UNE - abre el acceso directo Tienda.Clic

## `tests/ui/015-transfers/`

Especificaciones: `specs/transfers/own-account-transfer.api.plan.md`, `specs/transfers/own-account-transfer.ui.plan.md`

### `tests/ui/015-transfers/should-reject-invalid-contact.ui.spec.ts`

Fixtures: `addContactPage`, `contactSearchUiData`, `loginCredentials`, `loginPage`, `startPage`

- L8: TC-015-2-0001-UNE - informa CBU inválido al agendar un contacto
- L31: TC-015-2-0002-UNE - informa alias inválido al agendar un contacto

### `tests/ui/015-transfers/should-send-device-validation-to-fraud-analysis.ui.spec.ts`

Fixtures: `contactAgendaPage`, `credinRequestPage`, `loginApi`, `loginCredentials`, `smsOperationOtpApi`, `transferCaseData`, `transferFormPage`, `utilLoggerPage`

- L50: TC-015-2-0010-UNE - VU Fraud - adhesionAccount envía metodoValidacionFP
- L87: TC-015-2-0011-UNE - VU Fraud - transfers.account.makeWithCaps envía metodoValidacionFP
- L130: TC-015-2-0012-UNE - VU Fraud - credin.generate envía metodoValidacionFP
- L178: TC-015-2-0013-UNE - VU Fraud - transfers.account.scheduleMakeWithCaps envía metodoValidacionFP

### `tests/ui/015-transfers/should-show-transfer-history.ui.spec.ts`

Fixtures: `loginApi`, `loginCredentials`, `loginPage`, `startPage`, `thirdPartyTransferWorkflow`, `transferCaseData`, `transferFormPage`, `transferHistoryPage`

- L11: TC-015-2-0003-UNE - Transferencias realizadas - Ver mis transferencias (movimientos de hace dos meses)
- L50: TC-015-2-0008-UNE - Transferencias realizadas - Ver mis transferencias (borrar filtros)
- L94: TC-015-2-0009-UNE - Transferencias Programadas - Nuevo historial - Visualización

### `tests/ui/015-transfers/should-transfer-and-show-ticket.ui.spec.ts`

Fixtures: `loginApi`, `loginCredentials`, `loginPage`, `smsOperationOtpApi`, `startPage`, `thirdPartyTransferWorkflow`, `transferCaseData`, `transferFormPage`, `transferHistoryPage`

- L18: TC-015-2-0006-UNE - Saldo actualizado - Transferencias a cuentas propias en pesos del mismo banco
- L59: TC-015-2-0007-UNE - Transferencias a CBU - Historial de transferencias
- L115: TC-015-2-0005-UNE - Cambios en comprobante - TF inmediatas a terceros - Todos los campos - CBU otra entidad

### `tests/ui/015-transfers/should-transfer-to-own-account-by-cbu.ui.spec.ts`

Fixtures: `loginCredentials`, `loginPage`, `ownAccountTransferPage`, `ownAccountTransferUiData`, `startPage`

- L8: TC-015-2-0004-UNE - transfiere a cuenta propia por CBU con todos los campos completos @critical
