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

Migrado en `tests/api/cards/`:

| Caso(s)                | Test                                                            |
| ---------------------- | --------------------------------------------------------------- |
| TC0165                 | `should-pay-mastercard-other-amount.api.spec.ts`                |
| TC16039, TC0164        | `should-pay-credit-card-minimum.api.spec.ts`                    |
| TC0149                 | `should-request-credit-card.api.spec.ts`                        |
| TC0192, TC0193, TC0194 | `should-reject-credit-card-request-by-declaration.api.spec.ts`  |
| TC128238, TC128239     | `should-send-applicant-cuil-in-credit-card-request.api.spec.ts` |

Cambios compartidos: `AuthenticatedSession.customerCuil` (opcional) y su lectura en
`LoginApi.authenticate` desde `data.environments..productGroupId`, como `LoginApiSteps`. Es aditivo:
no cambia los campos que ya usan otros tests. TC0165 no reutiliza el cliente del testigo TC12967BERSA
porque éste exige una tarjeta Visa y busca productos por `productType`; se usa
`credit-card-payments.api.ts` con las búsquedas del legado y la misma plantilla de pago.

## TC128238BERSA / TC128239BERSA — API — comparación de CUIL y DNI por referencia

- Fuente: `TarjetaSteps.validarCuilYDNI` hace `Settings.cuil == cuil` y
  `Settings.documentNumber == dni`: en Java compara referencias de `String`, por lo que la assertion
  falla aunque los valores coincidan.
- Aplicado: se compara por valor (CUIL de la oferta = `customerCuil` del login; DNI de la oferta =
  `documentNumber`). Es la intención evidente; TypeScript no tiene igualdad por referencia de textos.
- Efecto: ambos casos crean y confirman una solicitud real de tarjeta antes de comparar.
- Revisión humana: pendiente; confirmar la comparación por valor.

## TC128239BERSA — API — título "update" con recorrido de alta

- Fuente: `TarjetasTest.TC128239BERSA` ejecuta exactamente los mismos pasos que TC128238BERSA
  (`application.create` + `confirm`); no llama `application.update`.
- Aplicado: se porta el recorrido ejecutable con los datos de su propio registro.
- Revisión humana: pendiente; ¿debe actualizar una solicitud ya iniciada?

## TC0192BERSA / TC0193BERSA / TC0194BERSA — API — actualización sin alta previa

- Fuente: `validarTipoDePersonaPedidoTarjeta` llama `creditCard.application.update` sin crear antes
  la solicitud y exige un código de éxito y que `message` contenga `Persona expuesta politicamente`,
  `Residencia en el exterior` o `Error UIF`.
- Aplicado: se conserva el orden legado y ambas comprobaciones.
- Revisión humana: pendiente; confirmar que QA responde éxito con ese mensaje.

## TC0149 / TC0192–TC0194 / TC128238 / TC128239 — API — cuenta de débito vacía

- Fuente: `CompartidosAPI.obtenerIdDeCuentaOrigen("sinDatos")` no falla y deja `debitIdProduct` vacío.
  TC0149, TC0193 y TC0194 tienen `ctaOrigen` `sinDatos`.
- Aplicado: `ProductWidgetsApi.legacyAccountIdOrEmpty` replica ese vacío sólo en las solicitudes de
  tarjeta.
- Otras réplicas literales: `debitCurrency` recibe `debitConcept` y `bonusPercentage` viaja `null`.

## TC0165BERSA / TC16039BERSA — API — datos de tarjeta y cuenta

- Duda: `tarjetaOperar` es `Tarjeta Master` (no son dígitos) y `ctaOrigen` usa la etiqueta larga
  `Cuenta Corriente $ 035000023427` (con espacio final en TC16039, que el lector recorta).
- Aplicado: las búsquedas fallan con "Datos de prueba inválidos" si no hay coincidencia; el legado
  enviaría ids vacíos.
- Revisión humana: pendiente; corregir los registros con los últimos dígitos y la etiqueta corta.

## Pagos — efectos y plantilla

- TC0165 paga ARS 1; TC16039 y TC0164 pagan el `minimumPaymentArs` informado por `creditCard.read`.
- Se conserva el `ticketData` literal de `creditCardMakePayment.json` (vencimiento 2025-05-08), igual
  que en TC12967BERSA.

## Etiquetas `Criticos`

TC0165 tiene `@Tag("Criticos")`; no se agregó `@critical` (ver `lote-l2-api.md`).
