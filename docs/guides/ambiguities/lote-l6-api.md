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

Migrado en `tests/api/transfers/`:

| Sub-lote | Casos                                                                                                                            | Test                                             |
| -------- | -------------------------------------------------------------------------------------------------------------------------------- | ------------------------------------------------ |
| L6a      | TC12930, TC12969, TC12932, TC0429, TC0344, TC0424, TC0422, TC12935, TC0477, TC18097, TC18098, TC18089, TC18093                   | `should-transfer-to-third-parties.api.spec.ts`   |
| L6a      | TC0409, TC0411, TC0427, TC0410, TC0412                                                                                           | `should-transfer-to-adhered-contact.api.spec.ts` |
| L6b/L6c  | TC0473, TC0484, TC0472, TC0474, TC0485, TC0478, TC0479, TC0480, TC0418, TC18090, TC18094, TC0465, TC0468, TC0467, TC0483, TC0008 | `should-schedule-transfers.api.spec.ts`          |
| L6d      | TC0014, TC12938, TC12939, TC12940                                                                                                | `should-generate-credin.api.spec.ts`             |
| L6e      | TC0145, TC12981                                                                                                                  | `should-manage-transfer-contacts.api.spec.ts`    |
| L6e      | TC18100, TC18101, TC18102, TC16759, TC16765, TC16768, TC18104                                                                    | `should-list-transfer-history.api.spec.ts`       |

## L6 — API — descuento de saldo sin la pausa legada

- Fuente: `CompartidosAPI.obtenerMontoActualizadoOrigen` espera `fixedWait(4000)` antes de releer
  `widgets.accounts`; `validarDescuentoCuenta` exige `saldoInicial − 1 ≥ saldoFinal`. El saldo se toma
  de `labelWithBalance` (tramo posterior al segundo `$ ` o `USD `, sin decimales ni puntos).
- Aplicado: se conserva la comparación y el cálculo; se eliminó la pausa (prohibida por `SPEC.md`
  6.2.1). No hay polling aprobado para la propagación del saldo.
- Efecto: si el saldo tarda en actualizarse, el test falla por la comparación.
- Revisión humana: pendiente; aprobar un `expect.poll` acotado o reemplazar la validación (como se
  decidió para TC0139BERSA en `specs/transfers/own-account-transfer.api.plan.md`).

## TC18097BERSA / TC18098BERSA — API — incremento en destino nunca medido

- Fuente: `validarIncrementoEnCuenta` compara `Settings.montoActualDestino + 1 ≤
Settings.montoActualizadoDestino`, pero estos casos no leen saldos de destino: en ejecución aislada
  ambos valen 0 y la assertion falla; en una corrida completa pueden traer valores de otro test.
- Aplicado: se conserva la comparación con los valores iniciales (0) de forma explícita en el test.
- Efecto: ambos casos fallan en esa assertion.
- Revisión humana: pendiente; ¿medir el saldo de `ctaDestino` antes y después?

## TC12938BERSA — API — tarjeta de débito no resuelta

- Fuente: `TC12938BERSA` tiene comentada la línea `compartidosAPI.obtenerIdDeTarjeta(...)`, pero
  `credinGenerate.json` usa `Settings.idDebitCard`: en ejecución aislada el reemplazo con `null`
  lanza `NullPointerException`; en una corrida completa usa la tarjeta de un test anterior.
- Aplicado: se resuelve la tarjeta `tarjetaDebito` del propio registro (5015, mismo usuario).
- Revisión humana: pendiente; confirmar la tarjeta esperada para el CREDIN en dólares.

## Títulos que no coinciden con el recorrido ejecutable

- TC12935BERSA y TC0477BERSA se titulan "Programadas" pero ejecutan una transferencia inmediata.
- TC0418BERSA se titula como inmediata "NO AGENDADOS" pero programa la transferencia y luego valida
  el descuento de saldo (que una programada para mañana no debería producir).
- TC0424BERSA y TC18098BERSA son USD pero usan la variante estándar; sólo TC0422BERSA usa la USD.
- TC18089BERSA dice "cuentas propias" pero ejecuta la transferencia a terceros sin descripción.
- Aplicado: se porta el recorrido ejecutable con el título legado. Revisión humana: pendiente.

## TC0008BERSA — API — CBU destino de la cuenta origen

- Fuente: `trasnferenciasProgramadaEntreCuentaPropias` envía `cbuTarget` con
  `Settings.cbuTarget`, que el caso obtiene con `obtenerCbuCuenta(cuentaOrigen)` (CBU de la cuenta
  origen). La plantilla además fija labels, CUIT y banco de otra persona.
- Aplicado: se conserva. Revisión humana: pendiente.

## TC12981BERSA / TC0145BERSA — API — agenda sin assertion de estado final

- Fuente: tras eliminar, TC12981 vuelve a buscar el CBU pero no afirma que ya no esté agendado;
  TC0145 tampoco afirma que quedó agendado.
- Aplicado: se conservan los pasos sin agregar assertions.

## L6e — API — historial

- `validaHistorialDeTransferencias` lee `transfers[9]`: exige al menos diez transferencias en la
  página y no más de diez. Se expresa como assertions explícitas.
- TC18100BERSA agenda el contacto pero luego programa una transferencia por CBU con OTP (no la
  variante a contacto agendado), como el legado.

## TC12939BERSA / TC12940BERSA — API — fecha de búsqueda CREDIN

- Fuente: `consultarCredin` usa el patrón `YYYY-MM-dd` (año de semana ISO en Java); difiere del año
  calendario sólo en los últimos/primeros días del año.
- Aplicado: se usa el año calendario. Revisión humana: no requerida salvo fallo en esas fechas.

## Etiquetas `Criticos`

Varios casos tienen `@Tag("Criticos")`; no se agregó `@critical` (ver `lote-l2-api.md`).
