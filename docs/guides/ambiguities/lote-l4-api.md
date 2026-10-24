# Lote L4 — API — Tarjetas

Portado el 21 de octubre de 2026 desde
`java/api/GP_Individuos_API/src/test/java/EntreRios/tarjetas/TarjetasTest.java` y sus dependencias
(`Steps/Tarjetas/TarjetaSteps.java`, `Steps/Compartidos/CompartidosAPI.java`,
`Steps/Pagos/PagosSteps.sincronizacionPagosLink`, `Steps/Login/LoginApiSteps.java`,
`Config/ValidarResponse.java`, `Config/Settings.java` y `Requests/Tarjetas/*.json`).

Criterios comunes: códigos de éxito del validador legado, sin bucles de reintento, metadatos fijos
de cada plantilla, OTP por SMS con la política central, y tarjetas buscadas como el legado:
débito por label `Visa Débito **** **** **** <tarjetaDebito>` y crédito por número
`**** **** **** <tarjetaOperar>`. Ningún caso se ejecutó contra BERSA QA.

Migrado en `tests/api/003-cards/`:

| Caso(s)                                                 | Test                                                            |
| ------------------------------------------------------- | --------------------------------------------------------------- |
| TC-003-1-0003-UNE                                       | `should-pay-mastercard-other-amount.api.spec.ts`                |
| TC-003-1-0008-UNE, TC-003-1-0002-UNE                    | `should-pay-credit-card-minimum.api.spec.ts`                    |
| TC-003-1-0001-UNE                                       | `should-request-credit-card.api.spec.ts`                        |
| TC-003-1-0004-UNE, TC-003-1-0005-UNE, TC-003-1-0006-UNE | `should-reject-credit-card-request-by-declaration.api.spec.ts`  |
| TC-003-1-0009-UNE, TC-003-1-0010-UNE                    | `should-send-applicant-cuil-in-credit-card-request.api.spec.ts` |

Cambios compartidos: `AuthenticatedSession.customerCuil` (opcional) y su lectura en
`LoginApi.authenticate` desde `data.environments..productGroupId`, como `LoginApiSteps`. Es aditivo:
no cambia los campos que ya usan otros tests. TC-003-1-0003-UNE no reutiliza el cliente del testigo TC-003-1-0007-UNE
porque éste exige una tarjeta Visa y busca productos por `productType`; se usa
`credit-card-payments.api.ts` con las búsquedas del legado y la misma plantilla de pago.

## TC-003-1-0009-UNE / TC-003-1-0010-UNE — API — comparación de CUIL y DNI por referencia

- Fuente: `TarjetaSteps.validarCuilYDNI` hace `Settings.cuil == cuil` y
  `Settings.documentNumber == dni`: en Java compara referencias de `String`, por lo que la assertion
  falla aunque los valores coincidan.
- Aplicado: se compara por valor (CUIL de la oferta = `customerCuil` del login; DNI de la oferta =
  `documentNumber`). Es la intención evidente; TypeScript no tiene igualdad por referencia de textos.
- Efecto: ambos casos crean y confirman una solicitud real de tarjeta antes de comparar.
- Revisión humana: pendiente; confirmar la comparación por valor.

## TC-003-1-0010-UNE — API — título "update" con recorrido de alta

- Fuente: `TarjetasTest.TC-003-1-0010-UNE` ejecuta exactamente los mismos pasos que TC-003-1-0009-UNE
  (`application.create` + `confirm`); no llama `application.update`.
- Aplicado: se porta el recorrido ejecutable con los datos de su propio registro.
- Revisión humana: pendiente; ¿debe actualizar una solicitud ya iniciada?

## TC-003-1-0004-UNE / TC-003-1-0005-UNE / TC-003-1-0006-UNE — API — actualización sin alta previa

- Fuente: `validarTipoDePersonaPedidoTarjeta` llama `creditCard.application.update` sin crear antes
  la solicitud y exige un código de éxito y que `message` contenga `Persona expuesta politicamente`,
  `Residencia en el exterior` o `Error UIF`.
- Aplicado: se conserva el orden legado y ambas comprobaciones.
- Revisión humana: pendiente; confirmar que QA responde éxito con ese mensaje.

## TC-003-1-0001-UNE / TC-003-1-0004-UNE–TC-003-1-0006-UNE / TC-003-1-0009-UNE / TC-003-1-0010-UNE — API — cuenta de débito vacía

- Fuente: `CompartidosAPI.obtenerIdDeCuentaOrigen("sinDatos")` no falla y deja `debitIdProduct` vacío.
  TC-003-1-0001-UNE, TC-003-1-0005-UNE y TC-003-1-0006-UNE tienen `ctaOrigen` `sinDatos`.
- Aplicado: `ProductWidgetsApi.legacyAccountIdOrEmpty` replica ese vacío sólo en las solicitudes de
  tarjeta.
- Otras réplicas literales: `debitCurrency` recibe `debitConcept` y `bonusPercentage` viaja `null`.

## TC-003-1-0003-UNE / TC-003-1-0008-UNE — API — datos de tarjeta y cuenta

- Duda: `tarjetaOperar` es `Tarjeta Master` (no son dígitos) y `ctaOrigen` usa la etiqueta larga
  `Cuenta Corriente $ 035000023427` (con espacio final en TC-003-1-0008-UNE, que el lector recorta).
- Aplicado: las búsquedas fallan con "Datos de prueba inválidos" si no hay coincidencia; el legado
  enviaría ids vacíos.
- Revisión humana: pendiente; corregir los registros con los últimos dígitos y la etiqueta corta.

## Pagos — efectos y plantilla

- TC-003-1-0003-UNE paga ARS 1; TC-003-1-0008-UNE y TC-003-1-0002-UNE pagan el `minimumPaymentArs` informado por `creditCard.read`.
- Se conserva el `ticketData` literal de `creditCardMakePayment.json` (vencimiento 2025-05-08), igual
  que en TC-003-1-0007-UNE.

## Etiquetas `Criticos`

TC-003-1-0003-UNE tiene `@Tag("Criticos")`; no se agregó `@critical` (ver `lote-l2-api.md`).
