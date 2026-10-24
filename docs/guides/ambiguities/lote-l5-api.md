# Lote L5 — API — eCheq

Portado el 21 de octubre de 2026 desde
`java/api/GP_Individuos_API/src/test/java/EntreRios/echeq/EcheqIndividuosTest.java` y sus
dependencias (`Steps/Echeq/EcheqSteps.java`, `Steps/Compartidos/CompartidosAPI.java`,
`Steps/Login/LoginApiSteps.java`, `Config/Settings.java`, `Config/TestCaseData.java`,
`org/GP_Individuos_API/Utils.java` y `Requests/Echeq/*.json`).

Criterios comunes: códigos de éxito del validador legado, sin bucles de reintento, OTP por SMS con la
política central y plantillas legadas copiadas sin cambios en `src/api/accounts/echeq-templates/`
(sólo se reemplazan los marcadores, como hace el legado sobre el body). Ningún caso se ejecutó contra
BERSA QA.

Migrado en `tests/api/001-accounts/`:

| Caso(s)                                                              | Test                                         |
| -------------------------------------------------------------------- | -------------------------------------------- |
| TC25610, TC124909, TC124913                                          | `should-list-echeqs-by-status.api.spec.ts`   |
| TC124907, TC124908, TC124910, TC124911, TC124912, TC128237, TC128231 | `should-operate-received-echeqs.api.spec.ts` |

Cliente: `src/api/accounts/echeq-operations.api.ts`. Datos nuevos: segundo usuario (`user1`/`pass1`,
equivalente a `usuario2`/`password2` del legado) y `cuitDestino` como CUIT de endoso.

## L5 — API — valores fijos de las plantillas de operación

- Fuente: `echeqOperation*.json`. Además de `checkId`, `nroEcheq` y OTP, cada plantilla fija estado,
  importe, fecha de pago, CUIT y nombre del librador y del beneficiario de un eCheq concreto
  (por ejemplo, `paymentDate: 2025-10-23` en la aceptación). Custodia y depósito fijan una cuenta
  `CA $ 062003146320` completa en `account`, distinta de la cuenta del caso.
- Aplicado: se envían esos valores tal cual; sólo `_idProduct_` toma la cuenta `ctaOrigen` del caso.
  En custodia el marcador `_label_` no se reemplaza en el legado y viaja literal.
- Revisión humana: pendiente; confirmar si el backend toma sólo `checkId`/`nroEcheq`.

## TC124912BERSA — API — rescate con la plantilla de repudio

- Fuente: `EcheqSteps.generaEcheqRescatado` llama `echeq.operation.rescue` con
  `echeqOperationRepudiate.json` (incluye `reason`/`label` `prueba`).
- Aplicado: se conserva esa plantilla. El título dice "Estado Custodiar" pero el recorrido acepta,
  custodia, rescata y valida `ACTIVO` en `Recibidos`.

## TC124910BERSA — API — repudio sin exigir el eCheq buscado

- Fuente: `generacionRepudioDeEcheq` busca un eCheq `EMITIDO-PENDIENTE` con pago hoy, pero no afirma
  haberlo encontrado: si falta, envía `checkId`/`nroEcheq` `"null"`.
- Aplicado: se conserva (no se agregó la assertion que sí existe en la aceptación).
- Revisión humana: pendiente; ¿debe exigir el eCheq?

## L5 — API — validación final débil

- Fuente: `validaEcheqs(estado, operationType)` sólo exige que la página 1 del estado tenga algún
  elemento; busca el eCheq operado pero sólo adjunta sus datos, sin afirmar que esté en la lista.
- Aplicado: se conserva la assertion de lista no vacía; no se agregó la del eCheq operado.
- Revisión humana: pendiente; posible mejora para exigir el `checkNumber` operado.

## L5 — API — búsqueda por CUIL del login

- Fuente: la búsqueda filtra `beneficiary.identificationNumber == Settings.cuil`, que el login legado
  arma con `environments..productGroupId`.
- Aplicado: se usa `session.customerCuil` (agregado en L4). Si el login no lo informa, la búsqueda
  no encuentra el eCheq y la aceptación falla.

## TC128237BERSA — API — dos usuarios y estado compartido

- Aplicado: acepta y endosa con `berhometest17`, acepta el endoso con `ecarina27` (segundo usuario,
  OTP de su propio teléfono o master code habilitado), vuelve a autenticar al primero y solicita la
  devolución sobre el mismo eCheq.
- Efecto: varias mutaciones sobre eCheq reales de dos usuarios.

## Efectos

TC124907, TC124908, TC124910, TC124911, TC124912, TC128237 y TC128231 operan eCheq reales y
consumen eCheq `EMITIDO-PENDIENTE` con fechas de pago específicas (hoy o hoy + 60 días); necesitan
datos preparados en QA en cada ejecución.
