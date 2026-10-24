# Lote L3 — API — Pagos y DEBIN

Portado el 21 de octubre de 2026 desde
`java/api/GP_Individuos_API/src/test/java/EntreRios/pagos/PagosTest.java` y sus dependencias
(`Steps/Pagos/PagosSteps.java`, `Steps/Mensajes/MensajesSteps.java`,
`Steps/Compartidos/CompartidosAPI.java`, `Steps/DobleFactor/DobleFactor.java`,
`Config/ValidarResponse.java`, `Config/Settings.java` y `Requests/Pagos/*.json`).

Criterios comunes aplicados:

- Cada request exige HTTP 200 y un código de éxito de `ValidarResponse.validateResponses`
  (`COR000I`, `COR013I`, `API690W`, `API691W`, `API692W`). No se portaron los bucles
  `flagServicioReejecucion`.
- Todos los casos sincronizan pagos Link (`payment.idCard.link`) como el legado.
- OTP por SMS mediante `SmsOperationOtpApi` y la política central de segundo factor.
- Tarjeta de débito: se busca en `widgets.creditCards` por el label
  `Visa Débito **** **** **** <tarjetaDebito>`, como `CompartidosAPI.obtenerIdDeTarjeta`. Si no
  existe, el test falla por datos (el legado enviaría un id vacío).
- Datos fijos de las plantillas (servicio Mercado Pago, pago Mercado Libre, DEBIN recurrente) en
  `src/test-data/payment-services-data.ts`.
- Ningún caso se ejecutó contra BERSA QA.

Migrado en `tests/api/011-payments/`:

| Caso(s)                              | Test                                                  |
| ------------------------------------ | ----------------------------------------------------- |
| TC-011-1-0001-UNE                    | `should-list-debin-history.api.spec.ts`               |
| TC-011-1-0003-UNE                    | `should-subscribe-recurring-debin.api.spec.ts`        |
| TC-011-1-0004-UNE                    | `should-accept-pending-debin.api.spec.ts`             |
| TC-011-1-0005-UNE                    | `should-adhere-tax-service.api.spec.ts`               |
| TC-011-1-0009-UNE                    | `should-pay-tax-service-without-base.api.spec.ts`     |
| TC-011-1-0010-UNE                    | `should-remove-tax-service.api.spec.ts`               |
| TC-011-1-0006-UNE                    | `should-list-payment-receipts.api.spec.ts`            |
| TC-011-1-0007-UNE, TC-011-1-0008-UNE | `should-download-payment-receipts-period.api.spec.ts` |

## L1–L3 — API — `ajax_uuid` fijo o por request

- Fuente: `docs/guides/families/debin.md` indica generar el UUID por request y no trasladar el
  literal. Los lotes L1 y L2 conservaron los UUID literales de cada plantilla, siguiendo el
  precedente de TC-003-1-0007-UNE (`tc12967-bersa-api.md`).
- Aplicado: en L3, las operaciones DEBIN (`debins.*`) y la sincronización Link usan UUID por
  request; pagos de servicios y comprobantes conservan el literal de su plantilla.
- Revisión humana: pendiente; decidir un único criterio para todos los clientes API.

## TC-011-1-0006-UNE — API — consulta de comprobantes sin assertion de lista

- Fuente: `PagosTest.TC-011-1-0006-UNE` llama `comprobantePagos("false", "")`; la assertion
  "SIN COMPROBANTE DE PAGO" sólo corre cuando `isExport` es `""`.
- Aplicado: se envía `isExport: false` y formato vacío; el caso valida sólo el código de éxito. No
  se agregó la assertion de lista no vacía.
- Revisión humana: pendiente; ¿la consulta debe exigir al menos un comprobante?

## TC-011-1-0007-UNE / TC-011-1-0008-UNE — API — espera fija antes de leer Mensajes

- Fuente: `utils.fixedWait(5000)` entre la exportación y
  `MensajesSteps.obtenerIdArchivoParaDescargaPagosPDF/XLS`.
- Aplicado: se eliminó la pausa (prohibida por `SPEC.md` 6.2.1) y se hace una sola lectura de
  Mensajes. No hay polling aprobado para esta propagación eventual.
- Efecto: si el archivo tarda en llegar a Mensajes, el test falla con "Mensajes no contiene el
  archivo".
- Revisión humana: pendiente; aprobar un `expect.poll` acotado en una especificación de comprobantes.

## TC-011-1-0004-UNE — API — aceptación del primer DEBIN pendiente

- Fuente: `obtenerIdDebin`, `obtenerDatosIdDebin`, `aceptarDebinGenerado(testCaseData.cbu)`.
- Duda: el parámetro `cbu` no se usa en la request; el destino sale del vendedor del DEBIN
  (`targetCBU`). Se acepta el primer DEBIN `PENDIENTE` disponible, que depende de un DEBIN generado
  antes (por ejemplo, TC-011-1-0002-UNE con otro usuario).
- Aplicado: `ticketData` se arma con el texto literal de la plantilla reemplazando sus marcadores,
  incluido el espacio final de `_holderSeller_ `. Los campos nulos del detalle hacen fallar el caso,
  como el `toString()` legado.
- Efecto: acepta un DEBIN real de ARS 1.
- Revisión humana: pendiente; confirmar la precondición de datos.

## TC-011-1-0010-UNE — API — precondición de alta antes de eliminar

- Fuente: `obtenerAdhesionCodeServicio(...)` agrega el servicio Mercado Pago si no encuentra la
  adhesión (ente 970, usuario 00449535691, referencia TestAutomation) y luego lo elimina.
- Aplicado: la misma decisión explícita en el test; si tras el alta no aparece, falla.

## TC-011-1-0009-UNE — API — pago real sin base

- Aplicado: alta del ente 969 con OTP, concepto `MLIBRE FACT`, `adhesionCode` de
  `resultWithoutBase` y pago de ARS 1 desde la cuenta `ctaOrigen` y la tarjeta de débito. La
  plantilla `firstPaymentRequest.json` no envía `ajax_uuid` ni `xAppVersion`; se respeta.
- Efecto: realiza un pago real.

## TC-011-1-0001-UNE — API — identidad distinta de los logins

- Este caso es la consulta de DEBIN histórico (rango fijo desde
  `2022-12-01T12:47:54.000Z` hasta ahora) y exige al menos un elemento.
- Resolución del 23 de octubre de 2026: `SPEC.md` y `README.md` se corrigieron para identificar
  los logins `TC-002-1-0001-UNE` (API) y `TC-002-2-0001-UNE` (UI) como la identidad compartida.
  `TC-011-1-0001-UNE` permanece exclusivamente asociado a este escenario DEBIN.

## Etiquetas `Criticos`

TC-011-1-0003-UNE, TC-011-1-0005-UNE, TC-011-1-0004-UNE, TC-011-1-0009-UNE y TC-011-1-0010-UNE tienen `@Tag("Criticos")`; no se agregó `@critical`
(ver `lote-l2-api.md`).
