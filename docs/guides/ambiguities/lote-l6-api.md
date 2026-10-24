# Lote L6 — API — Transferencias

Portado el 21 de octubre de 2026 desde
`java/api/GP_Individuos_API/src/test/java/EntreRios/transferencias/TransferenciasTest.java` (47 casos)
y sus dependencias (`Steps/Transferencias/TransferenciasSteps.java`,
`Steps/Compartidos/CompartidosAPI.java`, `Steps/Login/LoginApiSteps.java`, `Config/Settings.java`,
`org/GP_Individuos_API/Utils.java` y `Requests/Transferencias/*.json`).

Criterios comunes:

- Plantillas legadas copiadas sin cambios en `src/api/transfers/transfer-templates/`; los marcadores se
  reemplazan en el mismo orden que cada método Java (`src/api/legacy-template.ts`). Así se conservan
  también los marcadores que el legado no reemplaza y viajan literales (por ejemplo
  `_targetAccountTypeLabel_` en la transferencia sin descripción y en la programada, o
  `_TargetBrandTyoe_` y `_productType_` en la programada a contacto agendado).
- Códigos de éxito del validador legado, sin bucles de reintento, OTP por SMS con la política central.
- Preparación común en `src/workflows/transfers/third-party-transfer.workflow.ts`: buscar el destino
  y quitarlo de la agenda si estaba (`validar*Agendado` + `eliminarContactoAgendado`), agendar y volver
  a buscar, cargar productos y releer saldos. Coordina dos clientes y una decisión funcional
  (`SPEC.md` 6.3); las expectativas quedan en los tests.
- `AuthenticatedSession.customerName` (nuevo, opcional) toma `userFullName` del login, como
  `Settings.name`; el CUIL usa `customerCuil` (L4).
- Ningún caso se ejecutó contra BERSA QA. Todos los casos con transferencia, agenda o CREDIN mutan
  datos reales de los usuarios dummy.

Migrado en `tests/api/015-transfers/`:

| Sub-lote | Casos                                                                                                                                                                                                                                                                                                          | Test                                             |
| -------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------------------------------------ |
| L6a      | TC-015-1-0028-UNE, TC-015-1-0034-UNE, TC-015-1-0029-UNE, TC-015-1-0014-UNE, TC-015-1-0005-UNE, TC-015-1-0012-UNE, TC-015-1-0011-UNE, TC-015-1-0030-UNE, TC-015-1-0021-UNE, TC-015-1-0043-UNE, TC-015-1-0044-UNE, TC-015-1-0039-UNE, TC-015-1-0041-UNE                                                          | `should-transfer-to-third-parties.api.spec.ts`   |
| L6a      | TC-015-1-0006-UNE, TC-015-1-0008-UNE, TC-015-1-0013-UNE, TC-015-1-0007-UNE, TC-015-1-0009-UNE                                                                                                                                                                                                                  | `should-transfer-to-adhered-contact.api.spec.ts` |
| L6b/L6c  | TC-015-1-0019-UNE, TC-015-1-0026-UNE, TC-015-1-0018-UNE, TC-015-1-0020-UNE, TC-015-1-0027-UNE, TC-015-1-0022-UNE, TC-015-1-0023-UNE, TC-015-1-0024-UNE, TC-015-1-0010-UNE, TC-015-1-0040-UNE, TC-015-1-0042-UNE, TC-015-1-0015-UNE, TC-015-1-0017-UNE, TC-015-1-0016-UNE, TC-015-1-0025-UNE, TC-015-1-0001-UNE | `should-schedule-transfers.api.spec.ts`          |
| L6d      | TC-015-1-0002-UNE, TC-015-1-0031-UNE, TC-015-1-0032-UNE, TC-015-1-0033-UNE                                                                                                                                                                                                                                     | `should-generate-credin.api.spec.ts`             |
| L6e      | TC-015-1-0004-UNE, TC-015-1-0035-UNE                                                                                                                                                                                                                                                                           | `should-manage-transfer-contacts.api.spec.ts`    |
| L6e      | TC-015-1-0045-UNE, TC-015-1-0046-UNE, TC-015-1-0047-UNE, TC-015-1-0036-UNE, TC-015-1-0037-UNE, TC-015-1-0038-UNE, TC-015-1-0048-UNE                                                                                                                                                                            | `should-list-transfer-history.api.spec.ts`       |

## L6 — API — descuento de saldo sin la pausa legada

- Fuente: `CompartidosAPI.obtenerMontoActualizadoOrigen` espera `fixedWait(4000)` antes de releer
  `widgets.accounts`; `validarDescuentoCuenta` exige `saldoInicial − 1 ≥ saldoFinal`. El saldo se toma
  de `labelWithBalance` (tramo posterior al segundo `$ ` o `USD `, sin decimales ni puntos).
- Aplicado: se conserva la comparación y el cálculo; se eliminó la pausa (prohibida por `SPEC.md`
  6.2.1). No hay polling aprobado para la propagación del saldo.
- Efecto: si el saldo tarda en actualizarse, el test falla por la comparación.
- Revisión humana: pendiente; aprobar un `expect.poll` acotado o reemplazar la validación (como se
  decidió para TC-015-1-0003-UNE en `specs/transfers/own-account-transfer.api.plan.md`).

## TC-015-1-0043-UNE / TC-015-1-0044-UNE — API — incremento en destino nunca medido

- Fuente: `validarIncrementoEnCuenta` compara `Settings.montoActualDestino + 1 ≤
Settings.montoActualizadoDestino`, pero estos casos no leen saldos de destino: en ejecución aislada
  ambos valen 0 y la assertion falla; en una corrida completa pueden traer valores de otro test.
- Aplicado: se conserva la comparación con los valores iniciales (0) de forma explícita en el test.
- Efecto: ambos casos fallan en esa assertion.
- Revisión humana: pendiente; ¿medir el saldo de `ctaDestino` antes y después?

## TC-015-1-0031-UNE — API — tarjeta de débito no resuelta

- Fuente: `TC-015-1-0031-UNE` tiene comentada la línea `compartidosAPI.obtenerIdDeTarjeta(...)`, pero
  `credinGenerate.json` usa `Settings.idDebitCard`: en ejecución aislada el reemplazo con `null`
  lanza `NullPointerException`; en una corrida completa usa la tarjeta de un test anterior.
- Aplicado: se resuelve la tarjeta `tarjetaDebito` del propio registro (5015, mismo usuario).
- Revisión humana: pendiente; confirmar la tarjeta esperada para el CREDIN en dólares.

## Títulos que no coinciden con el recorrido ejecutable

- TC-015-1-0030-UNE y TC-015-1-0021-UNE se titulan "Programadas" pero ejecutan una transferencia inmediata.
- TC-015-1-0010-UNE se titula como inmediata "NO AGENDADOS" pero programa la transferencia y luego valida
  el descuento de saldo (que una programada para mañana no debería producir).
- TC-015-1-0012-UNE y TC-015-1-0044-UNE son USD pero usan la variante estándar; sólo TC-015-1-0011-UNE usa la USD.
- TC-015-1-0039-UNE dice "cuentas propias" pero ejecuta la transferencia a terceros sin descripción.
- Aplicado: se porta el recorrido ejecutable con el título legado. Revisión humana: pendiente.

## TC-015-1-0001-UNE — API — CBU destino de la cuenta origen

- Fuente: `trasnferenciasProgramadaEntreCuentaPropias` envía `cbuTarget` con
  `Settings.cbuTarget`, que el caso obtiene con `obtenerCbuCuenta(cuentaOrigen)` (CBU de la cuenta
  origen). La plantilla además fija labels, CUIT y banco de otra persona.
- Aplicado: se conserva. Revisión humana: pendiente.

## TC-015-1-0035-UNE / TC-015-1-0004-UNE — API — agenda sin assertion de estado final

- Fuente: tras eliminar, TC-015-1-0035-UNE vuelve a buscar el CBU pero no afirma que ya no esté agendado;
  TC-015-1-0004-UNE tampoco afirma que quedó agendado.
- Aplicado: se conservan los pasos sin agregar assertions.

## L6e — API — historial

- `validaHistorialDeTransferencias` lee `transfers[9]`: exige al menos diez transferencias en la
  página y no más de diez. Se expresa como assertions explícitas.
- TC-015-1-0045-UNE agenda el contacto pero luego programa una transferencia por CBU con OTP (no la
  variante a contacto agendado), como el legado.

## TC-015-1-0032-UNE / TC-015-1-0033-UNE — API — fecha de búsqueda CREDIN

- Fuente: `consultarCredin` usa el patrón `YYYY-MM-dd` (año de semana ISO en Java); difiere del año
  calendario sólo en los últimos/primeros días del año.
- Aplicado: se usa el año calendario. Revisión humana: no requerida salvo fallo en esas fechas.

## Etiquetas `Criticos`

Varios casos tienen `@Tag("Criticos")`; no se agregó `@critical` (ver `lote-l2-api.md`).
