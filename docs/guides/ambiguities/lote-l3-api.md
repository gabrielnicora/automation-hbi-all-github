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

Migrado en `tests/api/payments/`:

| Caso(s)        | Test                                                  |
| -------------- | ----------------------------------------------------- |
| TC0012         | `should-list-debin-history.api.spec.ts`               |
| TC0015         | `should-subscribe-recurring-debin.api.spec.ts`        |
| TC0070         | `should-accept-pending-debin.api.spec.ts`             |
| TC0074         | `should-adhere-tax-service.api.spec.ts`               |
| TC12964        | `should-pay-tax-service-without-base.api.spec.ts`     |
| TC12966        | `should-remove-tax-service.api.spec.ts`               |
| TC0108         | `should-list-payment-receipts.api.spec.ts`            |
| TC0137, TC0138 | `should-download-payment-receipts-period.api.spec.ts` |

## L1–L3 — API — `ajax_uuid` fijo o por request

- Fuente: `docs/guides/families/debin.md` indica generar el UUID por request y no trasladar el
  literal. Los lotes L1 y L2 conservaron los UUID literales de cada plantilla, siguiendo el
  precedente de TC12967BERSA (`tc12967-bersa-api.md`).
- Aplicado: en L3, las operaciones DEBIN (`debins.*`) y la sincronización Link usan UUID por
  request; pagos de servicios y comprobantes conservan el literal de su plantilla.
- Revisión humana: pendiente; decidir un único criterio para todos los clientes API.

## TC0108BERSA — API — consulta de comprobantes sin assertion de lista

- Fuente: `PagosTest.TC0108BERSA` llama `comprobantePagos("false", "")`; la assertion
  "SIN COMPROBANTE DE PAGO" sólo corre cuando `isExport` es `""`.
- Aplicado: se envía `isExport: false` y formato vacío; el caso valida sólo el código de éxito. No
  se agregó la assertion de lista no vacía.
- Revisión humana: pendiente; ¿la consulta debe exigir al menos un comprobante?

## TC0137BERSA / TC0138BERSA — API — espera fija antes de leer Mensajes

- Fuente: `utils.fixedWait(5000)` entre la exportación y
  `MensajesSteps.obtenerIdArchivoParaDescargaPagosPDF/XLS`.
- Aplicado: se eliminó la pausa (prohibida por `SPEC.md` 6.2.1) y se hace una sola lectura de
  Mensajes. No hay polling aprobado para esta propagación eventual.
- Efecto: si el archivo tarda en llegar a Mensajes, el test falla con "Mensajes no contiene el
  archivo".
- Revisión humana: pendiente; aprobar un `expect.poll` acotado en una especificación de comprobantes.

## TC0070BERSA — API — aceptación del primer DEBIN pendiente

- Fuente: `obtenerIdDebin`, `obtenerDatosIdDebin`, `aceptarDebinGenerado(testCaseData.cbu)`.
- Duda: el parámetro `cbu` no se usa en la request; el destino sale del vendedor del DEBIN
  (`targetCBU`). Se acepta el primer DEBIN `PENDIENTE` disponible, que depende de un DEBIN generado
  antes (por ejemplo, TC0013BERSA con otro usuario).
- Aplicado: `ticketData` se arma con el texto literal de la plantilla reemplazando sus marcadores,
  incluido el espacio final de `_holderSeller_ `. Los campos nulos del detalle hacen fallar el caso,
  como el `toString()` legado.
- Efecto: acepta un DEBIN real de ARS 1.
- Revisión humana: pendiente; confirmar la precondición de datos.

## TC12966BERSA — API — precondición de alta antes de eliminar

- Fuente: `obtenerAdhesionCodeServicio(...)` agrega el servicio Mercado Pago si no encuentra la
  adhesión (ente 970, usuario 00449535691, referencia TestAutomation) y luego lo elimina.
- Aplicado: la misma decisión explícita en el test; si tras el alta no aparece, falla.

## TC12964BERSA — API — pago real sin base

- Aplicado: alta del ente 969 con OTP, concepto `MLIBRE FACT`, `adhesionCode` de
  `resultWithoutBase` y pago de ARS 1 desde la cuenta `ctaOrigen` y la tarjeta de débito. La
  plantilla `firstPaymentRequest.json` no envía `ajax_uuid` ni `xAppVersion`; se respeta.
- Efecto: realiza un pago real.

## TC0012BERSA — API — identidad compartida con el login

- `SPEC.md`, sección 9, menciona logins con `TC0012BERSA`; este caso es la consulta de DEBIN
  histórico (rango fijo desde `2022-12-01T12:47:54.000Z` hasta ahora) y exige al menos un elemento.

## Etiquetas `Criticos`

TC0015, TC0074, TC0070, TC12964 y TC12966 tienen `@Tag("Criticos")`; no se agregó `@critical`
(ver `lote-l2-api.md`).
