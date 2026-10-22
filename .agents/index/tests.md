<!-- Generado por `pnpm run agent:index`. No editar a mano. -->

# Tests

Fuente: `playwright test --list` (títulos resueltos, incluidos los parametrizados) y las
fixtures que cada archivo pide en sus callbacks. Para un caso nuevo, buscar acá un test del
mismo patrón y la especificación de la capacidad.

## `tests/api/accounts/`

Especificaciones: `specs/accounts/withdrawal-order.api.plan.md`

### `tests/api/accounts/should-adhere-new-destination.api.spec.ts`

Fixtures: `accountTransferSearchApi`, `adheredAccountsApi`, `loginApi`, `loginCredentials`, `productWidgetsApi`

- L10: TC0110001BERSA - Adherir cuenta - nuevo destinatario de terceros con segundo factor por SMS

### `tests/api/accounts/should-generate-withdrawal-order.api.spec.ts`

Fixtures: `loginApi`, `loginCredentials`, `withdrawalOrderApi`, `withdrawalOrderData`

- L8: TC0110002BERSA - genera una orden de extracción con saldo disponible

### `tests/api/accounts/should-list-echeqs-by-status.api.spec.ts`

Fixtures: `echeqOperationsApi`, `loginApi`, `loginCredentials`

- L15: TC0110005BERSA - Validar el listado de eCheq en el estado 'Todos'
- L15: TC0110085BERSA - Consulta del detalle de un eCheq - Estado 'Emitir'
- L15: TC0110089BERSA - Consulta del detalle de un eCheq - Estado Anular

### `tests/api/accounts/should-operate-received-echeqs.api.spec.ts`

Fixtures: `echeqEndorsementData`, `echeqOperationsApi`, `loginApi`, `loginCredentials`, `originAccountData`, `productWidgetsApi`, `secondUserCredentials`

- L47: TC0110083BERSA - Consulta del detalle de un eCheq - Estado Aceptar
- L64: TC0110084BERSA - Consulta del detalle de un eCheq - Estado Endosar
- L86: TC0110086BERSA - Consulta del detalle de un eCheq - Estado Repudiar
- L109: TC0110087BERSA - Consulta del detalle de un eCheq - Estado Custodiar
- L132: TC0110088BERSA - Consulta del detalle de un eCheq - Custodiar y rescatar
- L157: TC0110091BERSA - Solicitar devolución de eCheq
- L190: TC0110090BERSA - Depositar eCheq - Variable encendida

### `tests/api/accounts/should-reject-invalid-account-transfer-search.api.spec.ts`

Fixtures: `accountTransferSearchApi`, `loginApi`, `loginCredentials`

- L313: TC0110006BERSA - Validar mensaje de error typeAccount con espacio
- L313: TC0110007BERSA - Validar error por typeAccount con acento
- L313: TC0110008BERSA - Validar error por typeAccount con guión
- L313: TC0110009BERSA - Validar error por typeAccount con longitud no permitida
- L313: TC0110010BERSA - Validar error por typeAccount con apóstrofe
- L313: TC0110011BERSA - Validar error por typeAccount vacío
- L313: TC0110012BERSA - Validar error por typeAccount null
- L313: TC0110013BERSA - Validar error por typeAccount alfabético no permitido
- L313: TC0110014BERSA - Validar error por typeAccount numérico
- L313: TC0110015BERSA - Validar error por typeAccount alfanumérico
- L313: TC0110016BERSA - Validar error por typeAccount con caracteres especiales
- L313: TC0110017BERSA - Validar error por typeAccount boolean true
- L313: TC0110018BERSA - Validar error por typeAccount boolean false
- L313: TC0110019BERSA - Validar error por typeAccount en minúsculas
- L313: TC0110020BERSA - Validar error por typeAccount con prefijo y espacio
- L313: TC0110021BERSA - Validar error por account con espacio
- L313: TC0110022BERSA - Validar error por account con acento
- L313: TC0110023BERSA - Validar error por account con guión
- L313: TC0110024BERSA - Validar error por account con longitud no permitida
- L313: TC0110025BERSA - Validar error por account con apóstrofe
- L313: TC0110026BERSA - Validar error por account vacío
- L313: TC0110027BERSA - Validar error por account null
- L313: TC0110028BERSA - Validar error por account CBU con longitud inválida (-1)
- L313: TC0110029BERSA - Validar error por account CBU con longitud inválida (+1)
- L313: TC0110030BERSA - Validar error por account alias con números adicionales
- L313: TC0110031BERSA - Validar error por typeAccount alfanumérico
- L313: TC0110032BERSA - Validar error por account con caracteres especiales
- L313: TC0110033BERSA - Validar error por account boolean true
- L313: TC0110034BERSA - Validar error por account boolean false
- L313: TC0110035BERSA - Validar error por account con formato mixto
- L313: TC0110036BERSA - Validar error cuando typeAccount y account están vacíos
- L313: TC0110037BERSA - Validar error cuando typeAccount y account son inválidos
- L313: TC0110038BERSA - Validar error por inconsistencia typeAccount CBU y account alias
- L313: TC0110039BERSA - Validar error por inconsistencia typeAccount alias y account CBU
- L313: TC0110040BERSA - Validar error por typeAccount CBU vacío
- L313: TC0110041BERSA - Validar error por typeAccount CBU null
- L313: TC0110042BERSA - Validar error por account con espacio
- L313: TC0110043BERSA - Validar error por account con guión
- L313: TC0110044BERSA - Validar error por account con longitud no permitida
- L313: TC0110045BERSA - Validar error por account con apóstrofe
- L313: TC0110046BERSA - Validar error por account vacío
- L313: TC0110047BERSA - Validar error por account null
- L313: TC0110048BERSA - Validar error por account CBU con longitud inválida (-1)
- L313: TC0110049BERSA - Validar error por account cbu con caracteres especiales
- L313: TC0110050BERSA - Validar error por account CBU boolean true
- L313: TC0110051BERSA - Validar error por account CBU boolean false
- L313: TC0110052BERSA - Validar error por typeAccount CVU vacío
- L313: TC0110053BERSA - Validar error por typeAccount CVU null
- L313: TC0110054BERSA - Validar error por account CVU con espacio
- L313: TC0110055BERSA - Validar error por account CVU con guión
- L313: TC0110056BERSA - Validar error por account CVU con longitud no permitida
- L313: TC0110057BERSA - Validar error por account CVU con apóstrofe
- L313: TC0110058BERSA - Validar error por account CVU vacío
- L313: TC0110059BERSA - Validar error por account CVU null
- L313: TC0110060BERSA - Validar error por account CVU con longitud inválida (-1)
- L313: TC0110061BERSA - Validar error por account CVU con longitud inválida (+1)
- L313: TC0110062BERSA - Validar error por account CVU con caracteres especiales
- L313: TC0110063BERSA - Validar error por account CVU boolean true
- L313: TC0110064BERSA - Validar error por account CVU boolean false

### `tests/api/accounts/should-reject-invalid-echeq-list-filters.api.spec.ts`

Fixtures: `echeqListApi`, `loginApi`, `loginCredentials`

- L113: TC0110065BERSA - Validar error por echeqNumber con espacio
- L113: TC0110066BERSA - Validar error por echeqNumber con caracteres especiales
- L113: TC0110067BERSA - Validar error por statement con espacio
- L113: TC0110068BERSA - Validar error por statement con caracteres especiales
- L113: TC0110069BERSA - Validar error por filterCuil con espacio
- L113: TC0110070BERSA - Validar error por filterCuil con caracteres especiales
- L113: TC0110071BERSA - Validar error por operationType con espacio
- L113: TC0110072BERSA - Validar error por operationType con caracteres especiales
- L113: TC0110073BERSA - Validar error por page con espacio
- L113: TC0110074BERSA - Validar error por page con caracteres especiales
- L113: TC0110075BERSA - Validar error por filterCuil boolean true
- L113: TC0110076BERSA - Validar error por filterCuil boolean false
- L113: TC0110077BERSA - Validar error por filterCuil null
- L113: TC0110078BERSA - Validar error por page con guión
- L113: TC0110079BERSA - Validar error por page alfabético no permitido
- L113: TC0110080BERSA - Validar error por page boolean true
- L113: TC0110081BERSA - Validar error por page boolean false
- L113: TC0110082BERSA - Validar error por page con prefijo y espacio

### `tests/api/accounts/should-request-salary-advance.api.spec.ts`

Fixtures: `loginApi`, `loginCredentials`, `originAccountData`, `productWidgetsApi`, `salaryAdvanceApi`

- L12: TC0110003BERSA - Adelantos de Sueldo - Realizar solicitud de adelanto de sueldo

### `tests/api/accounts/should-show-salary-advance-card.api.spec.ts`

Fixtures: `loginApi`, `loginCredentials`, `salaryAdvanceApi`

- L8: TC0110004BERSA - Adelantos de Sueldo - Mis adelantos - Validar card de adelantos de sueldo

## `tests/api/authentication/`

Especificaciones: `specs/authentication/api-login.plan.md`, `specs/authentication/login.plan.md`, `specs/authentication/second-factor-code-inbox.plan.md`

### `tests/api/authentication/should-login-and-capture-bearer.api.spec.ts`

Fixtures: `loginApi`, `loginCredentials`

- L8: TC0210001BERSA - inicia sesión y captura el Bearer token @regression

## `tests/api/cards/`

Especificaciones: `specs/cards/credit-card-other-amount-payment.api.plan.md`, `specs/cards/credit-card-total-payment.ui.plan.md`

### `tests/api/cards/should-pay-credit-card-minimum.api.spec.ts`

Fixtures: `creditCardPaymentData`, `creditCardPaymentsApi`, `loginApi`, `loginCredentials`, `productWidgetsApi`

- L18: TC0310002BERSA - Tarjeta de Crédito - Pagar mínimo de una Tarjeta de Crédito Visa
- L18: TC0310008BERSA - Tarjetas de crédito - Pagar el importe mínimo de una tarjeta de crédito Mastercard

### `tests/api/cards/should-pay-credit-card-other-amount.api.spec.ts`

Fixtures: `creditCardOtherAmountPaymentApi`, `creditCardOtherAmountPaymentData`, `loginApi`, `loginCredentials`

- L8: TC0310007BERSA - paga ARS 1 de una tarjeta Visa por otro importe @regression

### `tests/api/cards/should-pay-mastercard-other-amount.api.spec.ts`

Fixtures: `creditCardPaymentData`, `creditCardPaymentsApi`, `loginApi`, `loginCredentials`, `productWidgetsApi`

- L12: TC0310003BERSA - Pagar tarjeta de crédito Master - Otro importe

### `tests/api/cards/should-reject-credit-card-request-by-declaration.api.spec.ts`

Fixtures: `creditCardApplicationApi`, `loginApi`, `loginCredentials`, `optionalOriginAccountData`, `paymentLinkSyncApi`, `productWidgetsApi`

- L39: TC0310004BERSA - Tarjetas de crédito - Persona expuesta políticamente no permite continuar la solicitud
- L39: TC0310005BERSA - Tarjetas de crédito - Residencia fiscal en el exterior no permite continuar la solicitud
- L39: TC0310006BERSA - Tarjetas de crédito - Sujeto obligado UIF no permite continuar la solicitud

### `tests/api/cards/should-request-credit-card.api.spec.ts`

Fixtures: `creditCardApplicationApi`, `loginApi`, `loginCredentials`, `optionalOriginAccountData`, `paymentLinkSyncApi`, `productWidgetsApi`

- L9: TC0310001BERSA - Tarjeta de Crédito - Solicitar nueva tarjeta de crédito

### `tests/api/cards/should-send-applicant-cuil-in-credit-card-request.api.spec.ts`

Fixtures: `creditCardApplicationApi`, `loginApi`, `loginCredentials`, `optionalOriginAccountData`, `paymentLinkSyncApi`, `productWidgetsApi`

- L22: TC0310009BERSA - API - Tarjeta de Crédito - Verificar que el dato cuilSolicitante se envíe en application/create en nueva alta de TC
- L22: TC0310010BERSA - API - Tarjeta de Crédito - Verificar que el dato cuilSolicitante se envíe en application/update en alta de TC ya iniciada

## `tests/api/home/`

Especificaciones: `specs/home/terms-and-conditions.api.plan.md`

### `tests/api/home/should-get-terms-and-conditions.api.spec.ts`

Fixtures: `termsAndConditionsApi`

- L8: TC0510001BERSA - obtiene los términos y condiciones generales

## `tests/api/investments/`

Especificaciones: `specs/investments/fund-risk-filter.ui.plan.md`, `specs/investments/traditional-fixed-term.api.plan.md`

### `tests/api/investments/should-create-traditional-fixed-term.api.spec.ts`

Fixtures: `loginApi`, `loginCredentials`, `traditionalFixedTermApi`, `traditionalFixedTermData`

- L8: TC0710002BERSA - constituye un plazo fijo tradicional en ARS a 30 días

### `tests/api/investments/should-create-usd-fixed-term.api.spec.ts`

Fixtures: `loginApi`, `loginCredentials`, `originAccountData`, `productWidgetsApi`, `traditionalFixedTermApi`

- L9: TC0710008BERSA - Plazo Fijo Tradicional en Dólares

### `tests/api/investments/should-exchange-dollars.api.spec.ts`

Fixtures: `accountPairData`, `dollarExchangeApi`, `loginApi`, `loginCredentials`, `productWidgetsApi`

- L9: TC0710003BERSA - Compra de moneda extranjera (USD)
- L32: TC0710001BERSA - Venta de moneda extranjera (USD)

### `tests/api/investments/should-list-fund-movements.api.spec.ts`

Fixtures: `investmentFundsApi`, `loginApi`, `loginCredentials`

- L22: TC0710006BERSA - FCI - Movimientos
- L38: TC0710007BERSA - FCI - Movimientos con descarga PDF

### `tests/api/investments/should-open-investment-funds.api.spec.ts`

Fixtures: `investmentFundsApi`, `loginApi`, `loginCredentials`

- L9: TC0710004BERSA - FCI - Simular API (inicio a la funcionalidad)

### `tests/api/investments/should-show-commitent-account-closure-form.api.spec.ts`

Fixtures: `commitentAccountApi`, `loginApi`, `loginCredentials`

- L16: TC0710005BERSA - FCI - Solicitar baja cuenta comitente - Validación de pantalla

## `tests/api/messages/`

Especificaciones: `specs/messages/mark-message-unread.ui.plan.md`, `specs/messages/received-messages.api.plan.md`

### `tests/api/messages/should-filter-messages-by-read-state.api.spec.ts`

Fixtures: `loginApi`, `loginCredentials`, `receivedMessagesApi`

- L11: TC0910001BERSA - Mensajes - Sin Leer
- L26: TC0910002BERSA - Mensajes - Leídos

### `tests/api/messages/should-list-received-messages.api.spec.ts`

Fixtures: `loginApi`, `loginCredentials`, `receivedMessagesApi`

- L8: TC0910003BERSA - consulta los mensajes recibidos

## `tests/api/payments/`

Especificaciones: `specs/payments/debin-generation.api.plan.md`

### `tests/api/payments/should-accept-pending-debin.api.spec.ts`

Fixtures: `debinOperationsApi`, `debitCardData`, `loginApi`, `loginCredentials`, `paymentLinkSyncApi`, `productWidgetsApi`

- L10: TC1110004BERSA - Aceptación de DEBIN

### `tests/api/payments/should-adhere-tax-service.api.spec.ts`

Fixtures: `debitCardData`, `loginApi`, `loginCredentials`, `paymentLinkSyncApi`, `productWidgetsApi`, `taxServicesApi`

- L11: TC1110005BERSA - Adherir nuevo pago o servicio

### `tests/api/payments/should-download-payment-receipts-period.api.spec.ts`

Fixtures: `debitCardData`, `loginApi`, `loginCredentials`, `paymentLinkSyncApi`, `paymentReceiptsApi`, `productWidgetsApi`, `receivedMessagesApi`

- L26: TC1110007BERSA - Pagos - Comprobantes - Descargar periodo en PDF
- L26: TC1110008BERSA - Pagos - Comprobantes - Descargar periodo en XLS

### `tests/api/payments/should-generate-debin.api.spec.ts`

Fixtures: `debinGenerationApi`, `debinGenerationData`, `loginApi`, `loginCredentials`

- L8: TC1110002BERSA - genera una orden DEBIN seleccionando CBU

### `tests/api/payments/should-list-debin-history.api.spec.ts`

Fixtures: `debinOperationsApi`, `loginApi`, `loginCredentials`, `paymentLinkSyncApi`

- L8: TC1110001BERSA - DEBIN Comprador - Consulta DEBINes históricos - Listado

### `tests/api/payments/should-list-payment-receipts.api.spec.ts`

Fixtures: `debitCardData`, `loginApi`, `loginCredentials`, `paymentLinkSyncApi`, `paymentReceiptsApi`, `productWidgetsApi`

- L12: TC1110006BERSA - Pagos - Consulta de comprobantes

### `tests/api/payments/should-pay-tax-service-without-base.api.spec.ts`

Fixtures: `debitCardData`, `loginApi`, `loginCredentials`, `originAccountData`, `paymentLinkSyncApi`, `productWidgetsApi`, `taxServicesApi`

- L11: TC1110009BERSA - Pagar impuesto/servicio nuevo pago (sin base)

### `tests/api/payments/should-remove-tax-service.api.spec.ts`

Fixtures: `debitCardData`, `loginApi`, `loginCredentials`, `paymentLinkSyncApi`, `productWidgetsApi`, `taxServicesApi`

- L10: TC1110010BERSA - Pagar impuesto/servicio - Eliminar servicio

### `tests/api/payments/should-subscribe-recurring-debin.api.spec.ts`

Fixtures: `debinOperationsApi`, `loginApi`, `loginCredentials`, `originAccountData`, `paymentLinkSyncApi`, `productWidgetsApi`

- L11: TC1110003BERSA - Adherir DEBIN recurrente (Pesos) - segundo factor por SMS

## `tests/api/procedures/`

Especificaciones: `specs/procedures/current-online-procedures.api.plan.md`

### `tests/api/procedures/should-list-current-online-procedures.api.spec.ts`

Fixtures: `currentOnlineProceduresApi`, `loginApi`, `loginCredentials`

- L8: TC1210001BERSA - consulta los trámites vigentes

## `tests/api/profile/`

Especificaciones: `specs/profile/change-address.ui.plan.md`, `specs/profile/change-phone.api.plan.md`, `specs/profile/notification-preferences.api.plan.md`

### `tests/api/profile/should-change-phone.api.spec.ts`

Fixtures: `loginApi`, `loginCredentials`, `phoneChangeApi`

- L9: TC1310001BERSA - cambia el teléfono y verifica que quedó persistido

### `tests/api/profile/should-get-notification-preferences.api.spec.ts`

Fixtures: `loginApi`, `loginCredentials`, `notificationPreferencesApi`

- L8: TC1310002BERSA - consulta la configuración de notificaciones

## `tests/api/transfers/`

Especificaciones: `specs/transfers/own-account-transfer.api.plan.md`, `specs/transfers/own-account-transfer.ui.plan.md`

### `tests/api/transfers/should-generate-credin.api.spec.ts`

Fixtures: `credinApi`, `loginApi`, `loginCredentials`, `productWidgetsApi`, `transferCaseData`

- L56: TC1510002BERSA - Generar CREDIN - Cuenta propia $ (mismo Banco u otro) - Mas de una cuenta Debito - CBU - Segundo factor por SMS
- L56: TC1510031BERSA - TRANSFERENCIAS - Generar CREDIN - Dolares
- L73: TC1510032BERSA - TRANSFERENCIAS - Consultar CREDIN Originante
- L73: TC1510033BERSA - TRANSFERENCIAS - Consultar CREDIN destinatario

### `tests/api/transfers/should-list-transfer-history.api.spec.ts`

Fixtures: `loginApi`, `loginCredentials`, `thirdPartyTransfersApi`, `thirdPartyTransferWorkflow`, `transferCaseData`, `transferHistoryApi`

- L20: TC1510045BERSA - Transferencias programadas - Fecha específica agendada - Historial de transferencias
- L66: TC1510046BERSA - Transferencias a contacto agendado - Historial de transferencias
- L116: TC1510047BERSA - Transferencias a CVU - Historial de transferencias
- L158: TC1510036BERSA - Transferencias realizadas - Ver mis transferencias - Filtros
- L185: TC1510037BERSA - Transferencias realizadas - Ver mis transferencias - rango más nombre en buscador
- L185: TC1510038BERSA - Transferencias realizadas - Ver mis transferencias - rango más nombre en buscador
- L203: TC1510048BERSA - Transferencias realizadas - Ver mis transferencias - Búsqueda sin resultados

### `tests/api/transfers/should-manage-transfer-contacts.api.spec.ts`

Fixtures: `loginApi`, `loginCredentials`, `thirdPartyTransfersApi`, `thirdPartyTransferWorkflow`, `transferCaseData`

- L11: TC1510004BERSA - Transferencias - Añadir nuevo destinatario - Con cbu valido
- L38: TC1510035BERSA - Transferencias - Eliminar destinatario de agenda

### `tests/api/transfers/should-schedule-transfers.api.spec.ts`

Fixtures: `loginApi`, `loginCredentials`, `productWidgetsApi`, `thirdPartyTransfersApi`, `thirdPartyTransferWorkflow`, `transferCaseData`

- L47: TC1510018BERSA - Transferencias Programadas (Fecha especifica) CBU - terceros mismo banco USD - NO AGENDADOS
- L47: TC1510019BERSA - Transferencias Programadas (Fecha especifica) CBU - terceros otros bancos $ - NO AGENDADOS
- L47: TC1510020BERSA - Transferencias Programadas (Fecha especifica) CBU - terceros otros bancos USD - NO AGENDADOS
- L47: TC1510022BERSA - Transferencias Programadas (Fecha especifica) ALIAS - terceros mismo banco USD - NO AGENDADOS
- L47: TC1510023BERSA - Transferencias Programadas (Fecha especifica) ALIAS - terceros otros bancos $ - NO AGENDADOS
- L47: TC1510024BERSA - Transferencias Programadas (Fecha especifica) ALIAS - terceros otros bancos USD - NO AGENDADOS
- L47: TC1510026BERSA - Transferencias Programadas (Fecha especifica) CVU - terceros $ NO AGENDADOS
- L47: TC1510027BERSA - Transferencias Programadas (Fecha especifica) ALIAS de CVU - terceros $ NO AGENDADOS
- L84: TC1510010BERSA - Transferencias CBU - terceros otros bancos USD - NO AGENDADOS
- L129: TC1510040BERSA - Programada (fecha específica) CBU a cuentas propias otro banco $ - NO AGENDADO
- L129: TC1510042BERSA - Programada (fecha específica) CBU a Terceros otro banco $ - NO AGENDADOS
- L178: TC1510015BERSA - Programadas (fecha específica) - terceros mismo banco $ - AGENDADOS
- L178: TC1510016BERSA - Programadas (fecha específica) - terceros otros bancos $ - AGENDADOS
- L178: TC1510017BERSA - Programadas (fecha específica) - terceros otros bancos USD - AGENDADOS
- L178: TC1510025BERSA - Programadas (fecha específica) CVU - terceros $ AGENDADOS
- L215: TC1510001BERSA - Transferencia intrabancaria en pesos - propias - programada

### `tests/api/transfers/should-transfer-between-own-accounts.api.spec.ts`

Fixtures: `loginApi`, `loginCredentials`, `ownAccountTransferApi`, `ownAccountTransferData`

- L8: TC1510003BERSA - transfiere entre cuentas propias y valida el ticket @critical

### `tests/api/transfers/should-transfer-to-adhered-contact.api.spec.ts`

Fixtures: `loginApi`, `loginCredentials`, `thirdPartyTransfersApi`, `thirdPartyTransferWorkflow`, `transferCaseData`

- L20: TC1510006BERSA - TRANSFERENCIAS - terceros mismo banco $ - AGENDADOS
- L20: TC1510007BERSA - Transferencias - terceros mismo banco USD - AGENDADOS
- L20: TC1510008BERSA - Transferencias - terceros otros bancos $ - AGENDADOS
- L20: TC1510009BERSA - Transferencias - terceros mismo banco USD - AGENDADOS
- L20: TC1510013BERSA - Transferencias CVU - terceros $ AGENDADOS

### `tests/api/transfers/should-transfer-to-third-parties.api.spec.ts`

Fixtures: `loginApi`, `loginCredentials`, `thirdPartyTransfersApi`, `thirdPartyTransferWorkflow`, `transferCaseData`

- L117: TC1510005BERSA - A cuentas de terceros ALIAS - otros bancos - Pesos
- L117: TC1510011BERSA - ALIAS - terceros mismo banco USD - NO AGENDADOS
- L117: TC1510012BERSA - ALIAS - terceros otros bancos USD - NO AGENDADOS
- L117: TC1510014BERSA - ALIAS de CVU - terceros $ NO AGENDADOS
- L117: TC1510028BERSA - A cuentas de terceros CBU - mismo banco - Pesos
- L117: TC1510029BERSA - A cuentas de terceros ALIAS - mismo banco - Pesos
- L117: TC1510034BERSA - A cuentas de terceros CVU - otros bancos - Pesos
- L117: TC1510043BERSA - Saldo actualizado - Transferencias a un Alias CVU en pesos
- L117: TC1510044BERSA - Saldo actualizado - Transferencias a un Alias CBU en dólares
- L174: TC1510021BERSA - Transferencias Programadas (Fecha especifica) ALIAS - terceros mismo banco $ - NO AGENDADOS
- L174: TC1510030BERSA - Transferencias Programadas (Fecha especifica) CBU - terceros mismo banco $ - NO AGENDADOS
- L213: TC1510039BERSA - Transferencia a cuentas propias con CBU de otra entidad sin el campo Descripción al agendarse
- L213: TC1510041BERSA - Transferencia a Terceros con CBU de otra entidad sin el campo Descripción

## `tests/ui/accounts/`

Especificaciones: `specs/accounts/withdrawal-order.api.plan.md`

### `tests/ui/accounts/should-enable-new-account-request-continue.ui.spec.ts`

Fixtures: `accountsPage`, `loginCredentials`, `loginPage`, `startPage`

- L13: TC0120011BERSA - habilita Continuar al elegir Caja de Ahorro en Pesos
- L13: TC0120012BERSA - habilita Continuar al elegir Caja de Ahorro en Dólares
- L13: TC0120013BERSA - habilita Continuar al elegir Cuenta Corriente en Pesos

### `tests/ui/accounts/should-filter-echeqs.ui.spec.ts`

Fixtures: `echeqPage`, `loginCredentials`, `loginPage`, `startPage`

- L10: TC0120015BERSA - combina filtros de CUIT, estado y fecha de emisión

### `tests/ui/accounts/should-open-salary-advance-contract.ui.spec.ts`

Fixtures: `loginCredentials`, `loginPage`, `originAccountUiData`, `salaryAdvancePage`, `startPage`

- L9: TC0120007BERSA - redirige al documento del contrato al aceptar
- L33: TC0120008BERSA - vuelve a la solicitud de adelanto al cancelar la redirección

### `tests/ui/accounts/should-operate-echeqs.ui.spec.ts`

Fixtures: `echeqPage`, `loginApi`, `loginCredentials`, `loginPage`, `smsOperationOtpApi`, `startPage`

- L23: TC0120014BERSA - muestra los campos del filtro de eCheq
- L45: TC0120001BERSA - muestra el detalle del primer eCheq con todos los estados
- L70: TC0120016BERSA - acepta un eCheq emitido pendiente con segundo factor

### `tests/ui/accounts/should-show-account-movement-detail.ui.spec.ts`

Fixtures: `accountsPage`, `loginCredentials`, `loginPage`, `originAccountUiData`, `startPage`

- L9: TC0120002BERSA - muestra fecha, concepto y monto del primer movimiento

### `tests/ui/accounts/should-transfer-to-scheduled-contact-and-find-movement.ui.spec.ts`

Fixtures: `accountMovementsPage`, `accountMovementsUiData`, `loginCredentials`, `loginPage`, `startPage`

- L8: TC0120003BERSA - filtra movimientos por tipo de transacción en una cuenta

### `tests/ui/accounts/should-validate-account-alias.ui.spec.ts`

Fixtures: `accountAliasPage`, `accountAliasUiData`, `loginCredentials`, `loginPage`, `startPage`

- L11: TC0120017BERSA - Web - Modif. Alias - Validar controles del campo mandatorio Alias

### `tests/ui/accounts/should-validate-salary-advance-request.ui.spec.ts`

Fixtures: `homePage`, `loginCredentials`, `loginPage`, `originAccountUiData`, `salaryAdvancePage`, `startPage`

- L13: TC0120004BERSA - muestra la pantalla completa de solicitud de adelanto
- L35: TC0120005BERSA - vuelve a la solicitud al presionar Volver
- L58: TC0120006BERSA - exige aceptar términos y condiciones al confirmar
- L85: TC0120009BERSA - informa error con un importe inferior al mínimo
- L85: TC0120010BERSA - informa error con un importe superior al máximo

## `tests/ui/authentication/`

Especificaciones: `specs/authentication/api-login.plan.md`, `specs/authentication/login.plan.md`, `specs/authentication/second-factor-code-inbox.plan.md`

### `tests/ui/authentication/should-login-and-capture-bearer.ui.spec.ts`

Fixtures: `loginCredentials`, `loginPage`, `startPage`

- L9: TC0220001BERSA - inicia sesión y captura el Bearer token @regression

### `tests/ui/authentication/should-login-with-virtual-keyboard.ui.spec.ts`

Fixtures: `homePage`, `loginCredentials`, `loginPage`, `startPage`

- L9: TC0220003BERSA - inicia sesión con el teclado virtual y muestra el saludo

### `tests/ui/authentication/should-recover-username.ui.spec.ts`

Fixtures: `homePage`, `loginApi`, `loginCredentials`, `loginPage`, `profileMobilePhoneApi`, `secondFactorChallengeApi`, `startPage`

- L13: TC0220002BERSA - envía el usuario por correo y permite volver a ingresar
- L13: TC0220004BERSA - envía el usuario por correo y permite volver a ingresar

## `tests/ui/cards/`

Especificaciones: `specs/cards/credit-card-other-amount-payment.api.plan.md`, `specs/cards/credit-card-total-payment.ui.plan.md`

### `tests/ui/cards/should-consult-credit-cards.ui.spec.ts`

Fixtures: `creditCardDetailUiData`, `creditCardsPage`, `loginCredentials`, `loginPage`, `startPage`

- L8: TC0320001BERSA - abre el detalle de una tarjeta Visa por su número
- L31: TC0320002BERSA - abre el detalle de una tarjeta Mastercard
- L54: TC0320003BERSA - muestra descarga de resumen, movimientos del período y solicitud de adicional

### `tests/ui/cards/should-download-credit-card-statement.ui.spec.ts`

Fixtures: `creditCardDetailUiData`, `creditCardsPage`, `loginCredentials`, `loginPage`, `startPage`

- L10: TC0320004BERSA - Tarjeta de Crédito - Descarga de resumen
- L40: TC0320011BERSA - Tarjeta de Crédito - Consultar último resumen de una Tarjeta de Crédito

### `tests/ui/cards/should-open-total-credit-card-payment-review.ui.spec.ts`

Fixtures: `creditCardPaymentPage`, `creditCardTotalPaymentUiData`, `loginCredentials`, `loginPage`, `startPage`

- L18: TC0320010BERSA - muestra la revisión del pago total de una tarjeta Mastercard propia

### `tests/ui/cards/should-redirect-new-card-request-to-appointments.ui.spec.ts`

Fixtures: `creditCardsPage`, `loginCredentials`, `loginPage`, `newCreditCardRequestPage`, `optionalOriginAccountData`, `startPage`

- L8: TC0320012BERSA - Solicitud de tarjeta de crédito - Sujeto obligado UIF redirige a solicitar turno

### `tests/ui/cards/should-show-credit-card-detail-information.ui.spec.ts`

Fixtures: `creditCardPaymentData`, `creditCardsPage`, `loginCredentials`, `loginPage`, `startPage`

- L21: TC0320013BERSA - Web - Detalle de TC - Validar la información en Detalle de TC VISA y MASTER

### `tests/ui/cards/should-show-credit-card-tracking.ui.spec.ts`

Fixtures: `creditCardTrackingPage`

- L42: TC0320005BERSA - Tarjeta de Crédito - Seguimiento de Tarjetas de Crédito
- L52: TC0320006BERSA - Tarjeta de Crédito - Seguimiento de Tarjetas de Crédito - Detalles
- L68: TC0320007BERSA - Tarjeta de Crédito - Seguimiento de Tarjetas de Crédito - Descargar Detalles
- L85: TC0320008BERSA - Tarjeta de Crédito - Seguimiento de Tarjetas de Crédito - Imprimir Detalles

### `tests/ui/cards/should-start-credit-card-theft-report.ui.spec.ts`

Fixtures: `creditCardDetailUiData`, `creditCardsPage`, `loginCredentials`, `loginPage`, `startPage`

- L9: TC0320009BERSA - Tarjeta de Crédito - Denunciar robo o extravío de Tarjeta de Crédito

## `tests/ui/documents/`

### `tests/ui/documents/should-download-product-contract.ui.spec.ts`

Fixtures: `loginCredentials`, `loginPage`, `productDocumentsPage`, `startPage`

- L9: TC0420001BERSA - descarga el contrato del producto

## `tests/ui/home/`

Especificaciones: `specs/home/terms-and-conditions.api.plan.md`

### `tests/ui/home/should-show-warning-alert.ui.spec.ts`

Fixtures: `homePage`, `loginCredentials`, `loginPage`, `startPage`

- L12: TC0520001BERSA - muestra el aviso en formato advertencia

## `tests/ui/insurance/`

### `tests/ui/insurance/should-list-insurance-cards.ui.spec.ts`

Fixtures: `insurancePage`, `loginCredentials`, `loginPage`, `startPage`

- L9: TC0620001BERSA - muestra la tarjeta del seguro de Robo

### `tests/ui/insurance/should-open-robbery-insurance-detail.ui.spec.ts`

Fixtures: `insurancePage`, `loginCredentials`, `loginPage`, `startPage`

- L8: TC0620002BERSA - muestra el detalle del seguro de Robo

### `tests/ui/insurance/should-reach-robbery-insurance-confirmation.ui.spec.ts`

Fixtures: `insurancePage`, `loginCredentials`, `loginPage`, `startPage`

- L8: TC0620003BERSA - llega a la confirmación de la solicitud de seguro

## `tests/ui/investments/`

Especificaciones: `specs/investments/fund-risk-filter.ui.plan.md`, `specs/investments/traditional-fixed-term.api.plan.md`

### `tests/ui/investments/funds-by-risk-filter.ui.spec.ts`

Fixtures: `investmentFundsPage`, `loginCredentials`, `loginPage`, `startPage`

- L9: TC0720004BERSA - muestra únicamente fondos de riesgo moderado

### `tests/ui/investments/should-filter-funds.ui.spec.ts`

Fixtures: `investmentFundsPage`, `loginCredentials`, `loginPage`, `startPage`

- L11: TC0720003BERSA - FCI - Filtros - Mi Tenencia - Fondos por Riesgo - Conservador
- L47: TC0720005BERSA - FCI - Filtros: Fondos por moneda - Pesos
- L47: TC0720006BERSA - FCI - Filtros: Fondos por moneda - Dólar
- L73: TC0720007BERSA - FCI - Filtros: Mi Tenencia

### `tests/ui/investments/should-open-fund-more-information.ui.spec.ts`

Fixtures: `investmentFundsPage`, `loginCredentials`, `loginPage`, `startPage`

- L11: TC0720008BERSA - FCI - Más información - Aceptar modal
- L36: TC0720009BERSA - FCI - Más información - Rechazar modal

### `tests/ui/investments/should-query-fixed-term-rates.ui.spec.ts`

Fixtures: `fixedTermsPage`, `loginCredentials`, `loginPage`, `startPage`

- L23: TC0720001BERSA - PF - Consulta de Tasas - Tipo - Tradicional en Pesos
- L23: TC0720002BERSA - PF - Consulta de Tasas - Tipo - Tradicional en Dólares

### `tests/ui/investments/should-reach-commitent-account-confirmation.ui.spec.ts`

Fixtures: `commitentAccountOpeningPage`, `investmentFundsPage`, `loginCredentials`, `loginPage`, `startPage`

- L8: TC0720010BERSA - Inversiones - Alta cuenta comitente - Validar pantalla etapa 5 'Confirmación'

### `tests/ui/investments/should-reject-zero-amount-foreign-currency-purchase.ui.spec.ts`

Fixtures: `foreignCurrencyPurchasePage`, `foreignCurrencyPurchaseUiData`, `loginCredentials`, `loginPage`, `startPage`

- L8: TC0720011BERSA - rechaza la compra de dólares con importe cero

### `tests/ui/investments/should-validate-dollar-exchange-form.ui.spec.ts`

Fixtures: `foreignCurrencyPurchasePage`, `loginCredentials`, `loginPage`, `originAccountData`, `startPage`

- L27: TC0720012BERSA - Inversiones - Compra de moneda extranjera - Seguir flujo de compra de dólar sin aceptar check
- L27: TC0720016BERSA - Inversiones - Venta de moneda extranjera - Seguir flujo de venta de dólar sin aceptar check
- L60: TC0720015BERSA - Inversiones - Venta de moneda extranjera - Importe 0 - Validar mensaje
- L124: TC0720013BERSA - Inversiones - Compra - Desplegable Cuenta Origen sólo cuentas en pesos
- L124: TC0720014BERSA - Inversiones - Compra - Desplegable Cuenta Destino sólo cuentas en dólares
- L124: TC0720017BERSA - Inversiones - Venta - Desplegable Cuenta Destino sólo cuentas en pesos
- L124: TC0720018BERSA - Inversiones - Venta - Desplegable Cuenta Origen sólo cuentas en dólares

### `tests/ui/investments/should-validate-fixed-term-request-continue.ui.spec.ts`

Fixtures: `accountPairData`, `fixedTermsPage`, `loginCredentials`, `loginPage`, `startPage`

- L24: TC0720019BERSA - Web - Alta de PF - Verificar lógica del botón Continuar y mensaje de error en Simulación

## `tests/ui/loans/`

### `tests/ui/loans/should-request-loans.ui.spec.ts`

Fixtures: `loanRequestPage`, `loginCredentials`, `loginPage`, `startPage`

- L9: TC0820001BERSA - llega a la confirmación de un nuevo préstamo
- L48: TC0820002BERSA - llega a la confirmación de un préstamo para cancelar deudas
- L83: TC0820003BERSA - abre el resumen de contrato del préstamo para cancelar deudas

## `tests/ui/messages/`

Especificaciones: `specs/messages/mark-message-unread.ui.plan.md`, `specs/messages/received-messages.api.plan.md`

### `tests/ui/messages/should-delete-message.ui.spec.ts`

Fixtures: `loginCredentials`, `loginPage`, `messagesPage`, `startPage`

- L9: TC0920002BERSA - elimina el primer mensaje de la bandeja

### `tests/ui/messages/should-mark-message-unread.ui.spec.ts`

Fixtures: `loginCredentials`, `loginPage`, `messagesPage`, `startPage`

- L8: TC0920001BERSA - marca un mensaje como no leído

## `tests/ui/mis-products/`

Especificaciones: `specs/mis-products/should-open-cancellation-page.ui.plan.md`

### `tests/ui/mis-products/should-open-cancellation-page.ui.spec.ts`

Fixtures: `cancellationPage`, `loginCredentials`, `loginPage`, `startPage`

- L8: TC1020001BERSA - acepta la redirección a Baja y Arrepentimiento

## `tests/ui/payments/`

Especificaciones: `specs/payments/debin-generation.api.plan.md`

### `tests/ui/payments/should-pay-and-find-service-receipts.ui.spec.ts`

Fixtures: `loginApi`, `loginCredentials`, `loginPage`, `paymentReceiptsPage`, `paymentServicesPage`, `paymentServicesPreparationWorkflow`, `startPage`

- L12: TC1120004BERSA - muestra el estado de los pagos realizados en simultáneo
- L44: TC1120003BERSA - filtra comprobantes de pago por empresa y descripción

### `tests/ui/payments/should-require-debin-buyer-identifier.ui.spec.ts`

Fixtures: `debinSellerPage`, `loginCredentials`, `loginPage`, `originAccountUiData`, `startPage`

- L8: TC1120001BERSA - exige el CBU del comprador al buscar sin ingresarlo
- L36: TC1120002BERSA - exige el alias del comprador al buscar sin ingresarlo

## `tests/ui/procedures/`

Especificaciones: `specs/procedures/current-online-procedures.api.plan.md`

### `tests/ui/procedures/should-open-online-appointments.ui.spec.ts`

Fixtures: `appointmentsPage`, `loginCredentials`, `loginPage`, `startPage`

- L10: TC1220001BERSA - acepta la redirección para solicitar un turno

## `tests/ui/profile/`

Especificaciones: `specs/profile/change-address.ui.plan.md`, `specs/profile/change-phone.api.plan.md`, `specs/profile/notification-preferences.api.plan.md`

### `tests/ui/profile/should-change-address.ui.spec.ts`

Fixtures: `changeAddressPage`, `loginApi`, `loginCredentials`, `loginPage`, `profileMobilePhoneApi`, `secondFactorChallengeApi`, `startPage`

- L10: TC1320001BERSA - cambia el domicilio con un segundo factor SMS @regression

### `tests/ui/profile/should-change-security-seal.ui.spec.ts`

Fixtures: `loginApi`, `loginCredentials`, `loginPage`, `securitySealPage`, `smsOperationOtpApi`, `startPage`

- L9: TC1320002BERSA - cambia el avatar con segundo factor

## `tests/ui/start-page/`

### `tests/ui/start-page/should-open-registration.ui.spec.ts`

Fixtures: `identityValidationPage`, `startPage`

- L8: TC1420002BERSA - acceso directo ¿No sos cliente aún? Registrarme

### `tests/ui/start-page/should-open-security-information.ui.spec.ts`

Fixtures: `loginPage`, `startPage`

- L9: TC1420003BERSA - abre el enlace Información de seguridad

### `tests/ui/start-page/should-open-store-shortcut.ui.spec.ts`

Fixtures: `loginPage`, `startPage`

- L9: TC1420001BERSA - abre el acceso directo Tienda.Clic

## `tests/ui/transfers/`

Especificaciones: `specs/transfers/own-account-transfer.api.plan.md`, `specs/transfers/own-account-transfer.ui.plan.md`

### `tests/ui/transfers/should-reject-invalid-contact.ui.spec.ts`

Fixtures: `addContactPage`, `contactSearchUiData`, `loginCredentials`, `loginPage`, `startPage`

- L8: TC1520001BERSA - informa CBU inválido al agendar un contacto
- L31: TC1520002BERSA - informa alias inválido al agendar un contacto

### `tests/ui/transfers/should-send-device-validation-to-fraud-analysis.ui.spec.ts`

Fixtures: `contactAgendaPage`, `credinRequestPage`, `loginApi`, `loginCredentials`, `smsOperationOtpApi`, `transferCaseData`, `transferFormPage`, `utilLoggerPage`

- L50: TC1520010BERSA - VU Fraud - adhesionAccount envía metodoValidacionFP
- L87: TC1520011BERSA - VU Fraud - transfers.account.makeWithCaps envía metodoValidacionFP
- L130: TC1520012BERSA - VU Fraud - credin.generate envía metodoValidacionFP
- L178: TC1520013BERSA - VU Fraud - transfers.account.scheduleMakeWithCaps envía metodoValidacionFP

### `tests/ui/transfers/should-show-transfer-history.ui.spec.ts`

Fixtures: `loginApi`, `loginCredentials`, `loginPage`, `startPage`, `thirdPartyTransferWorkflow`, `transferCaseData`, `transferFormPage`, `transferHistoryPage`

- L11: TC1520003BERSA - Transferencias realizadas - Ver mis transferencias (movimientos de hace dos meses)
- L50: TC1520008BERSA - Transferencias realizadas - Ver mis transferencias (borrar filtros)
- L94: TC1520009BERSA - Transferencias Programadas - Nuevo historial - Visualización

### `tests/ui/transfers/should-transfer-and-show-ticket.ui.spec.ts`

Fixtures: `loginApi`, `loginCredentials`, `loginPage`, `smsOperationOtpApi`, `startPage`, `thirdPartyTransferWorkflow`, `transferCaseData`, `transferFormPage`, `transferHistoryPage`

- L18: TC1520006BERSA - Saldo actualizado - Transferencias a cuentas propias en pesos del mismo banco
- L59: TC1520007BERSA - Transferencias a CBU - Historial de transferencias
- L115: TC1520005BERSA - Cambios en comprobante - TF inmediatas a terceros - Todos los campos - CBU otra entidad

### `tests/ui/transfers/should-transfer-to-own-account-by-cbu.ui.spec.ts`

Fixtures: `loginCredentials`, `loginPage`, `ownAccountTransferPage`, `ownAccountTransferUiData`, `startPage`

- L8: TC1520004BERSA - transfiere a cuenta propia por CBU con todos los campos completos @critical
