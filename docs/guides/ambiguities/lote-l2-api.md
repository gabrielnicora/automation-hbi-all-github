# Lote L2 — API — Mensajes, Cuentas e Inversiones

Portado el 21 de octubre de 2026 desde `java/api/GP_Individuos_API/src/test/java/EntreRios/`
(`mensajes/MensajesTest.java`, `cuentas/CuentasTest.java`, `inversiones/InversionesTest.java`) y sus
steps (`Steps/Mensajes/MensajesSteps.java`, `Steps/Cuentas/CuentasSteps.java`,
`Steps/Inversiones/InversionesSteps.java`, `Steps/Compartidos/CompartidosAPI.java`,
`Steps/DobleFactor/DobleFactor.java`, `Config/ValidarResponse.java`) y plantillas de `Requests/`.

Criterios comunes aplicados:

- Cada request exige HTTP 200 y uno de los códigos de éxito de `ValidarResponse.validateResponses`
  (`COR000I`, `COR013I`, `API690W`, `API691W`, `API692W`). Los bucles `flagServicioReejecucion`
  no se portaron: reintenta el runner.
- Se conservan los `ajax_uuid` y `xAppVersion` fijos de cada plantilla legada.
- El OTP por SMS (`DobleFactor.getSecondFactor`) se resuelve con la política central de
  `specs/authentication/second-factor-code-inbox.plan.md` mediante
  `src/api/authentication/sms-operation-otp.api.ts`: dispara `secondFactor.otp.sms.sendToken` y
  busca el código por el teléfono actual del perfil, igual que TC0027BERSA.
- Ningún caso se ejecutó contra BERSA QA en este lote.

Migrado en:

| Caso(s)        | Test                                                                           |
| -------------- | ------------------------------------------------------------------------------ |
| TC0094, TC0098 | `tests/api/messages/should-filter-messages-by-read-state.api.spec.ts`          |
| TC0006         | `tests/api/accounts/should-adhere-new-destination.api.spec.ts`                 |
| TC0229         | `tests/api/accounts/should-show-salary-advance-card.api.spec.ts`               |
| TC0223         | `tests/api/accounts/should-request-salary-advance.api.spec.ts`                 |
| TC12973        | `tests/api/investments/should-create-usd-fixed-term.api.spec.ts`               |
| TC0115         | `tests/api/investments/should-open-investment-funds.api.spec.ts`               |
| TC0125         | `tests/api/investments/should-show-commitent-account-closure-form.api.spec.ts` |
| TC0129, TC0130 | `tests/api/investments/should-list-fund-movements.api.spec.ts`                 |
| TC0069, TC0009 | `tests/api/investments/should-exchange-dollars.api.spec.ts`                    |

## TC0094BERSA / TC0098BERSA — API — comparación de `userRead` con texto

- Fuente: `MensajesSteps.validarMensajesNoLeidos` y `validarMensajesLeidos`.
- Duda: el legado hace `a.get("userRead").equals("false")` (no leídos) y `.equals("true")`
  (leídos). Si `userRead` llega como booleano JSON, `equals` contra un texto nunca es verdadero y
  la assertion no puede fallar. Si llega como texto, la consulta de no leídos falla con mensajes
  `"false"`, lo que parece invertido respecto de su mensaje "un mensaje leído está en los no leídos".
- Aplicado: se conserva la comparación literal con los textos `"false"` y `"true"`. No se inventó
  una assertion booleana.
- Revisión humana: pendiente; confirmar el tipo de `userRead` en QA y la expectativa correcta.

## TC0006BERSA — API — agenda de destinatario

- Fuente: `CuentasTest.TC0006BERSA`; `consultarDestinatarioAgendaCBU`, `eliminarDestinatarioAgendaCBU`,
  `agregarDestinatarioAgendaCBU`.
- Aplicado: carga widgets de cuentas y tarjetas; busca el CBU fijo `3860006503000031964422` tipo
  `CBU`; si está agendado lo elimina (precondición legada); vuelve a buscarlo y lo agenda con el
  `ownAccount` informado y OTP SMS. El legado resuelve el `idProduct` de `ctaOrigen` pero no lo usa
  ni falla si falta: no se portó esa lectura.
- Efecto: modifica la agenda del usuario `berhometest17`.
- Revisión humana: pendiente sólo para confirmar que omitir la lectura de `idProduct` es aceptable.

## TC0223BERSA — API — solicitud real de adelanto de sueldo

- Fuente: `CuentasTest.TC0223BERSA`; `obtenerDatosAdelantoDeSueldo`,
  `obtenerDatosMontosAcreditacionAdelantoDeSueldo`, `adelantoDeSueldo`.
- Aplicado: resuelve `idProduct` de `ctaOrigen` (`CC $ 009000026939`), lee tasas de la oferta por
  `shortLabel`, consulta montos por ARS 10000 y confirma el adelanto con OTP SMS. Se conservan las
  conversiones legadas a entero de `tea`, `cft`, `idSalaryAdvance` y montos; `tna` queda decimal.
- Duda: según `lote-base-ui.md`, `berhometest17` no tiene oferta de adelanto en QA; el caso fallaría
  al no encontrar la cuenta en la oferta. El legado tampoco lo pasaría (convierte un valor vacío).
- Efecto: si hay oferta, solicita un adelanto real en el usuario.
- Revisión humana: pendiente; usuario con oferta y aceptación del efecto persistente.

## TC0069BERSA — API — etiqueta de cuenta de datos

- Fuente: `InversionesTest.TC0069BERSA`; `compraUSD`, `CompartidosAPI.obtenerIdDeCuenta*`.
- Duda: el registro tiene `ctaOrigen` `Cuenta Corriente $ 009000026939` y `ctaDestino`
  `Caja de Ahorro USD 009006735969`, con etiquetas largas; el resto de los casos usa `shortLabel`
  (`CC $ …`, `CA USD …`). El legado buscaría por `shortLabel` y enviaría ids vacíos.
- Aplicado: se busca por `shortLabel` y el test falla con "Datos de prueba inválidos" si no coincide.
  Compra: dólares = cuenta destino, pesos = cuenta origen, importe `"10"` (texto, como la plantilla).
- Revisión humana: pendiente; ¿corregir el registro con las etiquetas cortas?

## TC0009BERSA — API — importe y comentario de la venta

- Fuente: `InversionesTest.TC0009BERSA`; `ventaUSD`, plantilla `sellDollarTransfer.json`.
- Duda: el comentario del caso dice "Compra 10 USD", pero la plantilla vende `100` (numérico).
- Aplicado: venta de 100 con dólares = cuenta origen y pesos = cuenta destino.
- Revisión humana: pendiente; confirmar el importe esperado.

## TC0130BERSA — API — clave de descarga fija

- Fuente: `InversionesTest.TC0130BERSA` llama `descargaMovimientoFCI(Settings.keyLongValueUnisys)`.
- Duda: `movimientosFCI` captura `keyLongValue` del primer movimiento, pero la descarga usa la
  constante `11340`; `formName` sí sale del primer movimiento (`requestTemplate`).
- Aplicado: se conserva la constante `11340`.
- Revisión humana: pendiente; ¿debe usarse el `keyLongValue` del movimiento?

## TC0129BERSA / TC0130BERSA — API — fechas del rango de movimientos

- Fuente: `Utils.fechaTransferenciaMasAntigua("40")` y `Utils.fechaHoy()`.
- Aplicado: inicio = hace 40 días a las 00:00 con sufijo `-03`; fin = hora local con el literal `Z`
  (sin convertir a UTC), como el legado. El fondo buscado es `A-Delta Pesos` y `fundId` se envía con
  el tipo que informa `idInstrument`.
- Revisión humana: no requerida; registrar si QA rechaza el formato.

## TC12973BERSA — API — plazo fijo USD

- Aplicado: nuevo método `createFixedTerm` en `traditional-fixed-term.api.ts`, sin modificar el
  testigo TC0042BERSA: USD, tipo `D`, `amount` numérico 10001, `term` `"30"`, renovación `T`, con el
  `ajax_uuid` fijo de la plantilla. La cuenta se resuelve tras cargar widgets de cuentas y tarjetas.
- Diferencia detectada: el testigo TC0042BERSA envía `amount` como texto y `ajax_uuid` aleatorio,
  mientras la plantilla legada envía número y UUID fijo. No se cambió el testigo.
- Revisión humana: pendiente; decidir si el testigo se alinea con la plantilla.

## TC0125BERSA — API — comparación de `order`

- Aplicado: los campos con `order` 1 y 2 se comparan por valor numérico; varios resultados se unen
  por coma, como la lectura JSONPath sin corchetes del legado.

## Etiquetas `Criticos`

TC0006, TC12973, TC0069 y TC0009 tienen `@Tag("Criticos")` en el legado. No se agregó `@critical`
porque `SPEC.md`, sección 10, sólo la asigna a TC0139BERSA. Revisión humana: pendiente.

## Reutilización pendiente

`WithdrawalOrderApi` (TC0027BERSA) conserva su envío privado de OTP SMS, equivalente a
`SmsOperationOtpApi`. No se refactorizó para no alterar un testigo sin ejecución QA; unificarlo
cuando el testigo pueda validarse.
