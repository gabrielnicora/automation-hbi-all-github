# Lote L9 — UI — Transferencias

Portado el 21 de octubre de 2026 desde
`java/ui/GP_Migracion_Individuos/src/test/java/GP/EntreRios/front/FrontTest.java` (10 métodos) y sus
dependencias (`steps/transferencias/transferenciasinmediatas_programadas/TransferenciasInmediatasSteps`,
`steps/transferencias/agenda_nuevo_destinatario/AgendaNuevoDestinatarioSteps`,
`steps/transferencias/generarcredin/GenerarCredinSteps`, `steps/transferencias/consultacredin/ConsultaCredinSteps`,
`steps/ticket/TicketSteps`, `steps/doblefactor/DobleFactorSteps`, `steps/utillogger/UtilLoggerSteps`,
`api/DobleFactorAPI`, `api/TransferenciasAPI` y las pages correspondientes).

Criterios comunes:

- Login UI real. Para importe, descripción, búsqueda por CBU, "Transferir ahora" y "Confirmar" se usan
  los locators validados del testigo TC-015-2-0004-UNE; el resto reproduce el legado y queda pendiente de
  validación UI.
- Segundo factor: el legado UI pide el SMS por API (`DobleFactorAPI.getSecondFactor` → `sendTokenSms` +
  `util.codes`) cuando aparece el campo `#secondFactor`. El port hace lo mismo con
  `SmsOperationOtpApi` y la política central; no se portó el reintento con un segundo código.
- La limpieza previa por API (`eliminarDestinatarioAgendadoAPI`) reutiliza
  `ThirdPartyTransferWorkflow.findDestinationOutsideAgenda` (L6) con una sesión API del mismo usuario.
- Ningún caso se ejecutó contra BERSA QA. TC-015-2-0006-UNE, TC-015-2-0007-UNE, TC-015-2-0005-UNE, TC-015-2-0011-UNE, TC-015-2-0012-UNE y TC-015-2-0013-UNE
  mueven dinero o generan CREDIN; TC-015-2-0010-UNE/TC-015-2-0011-UNE agendan y eliminan contactos.

Migrado en `tests/ui/015-transfers/`:

| Caso(s)                                                                    | Test                                                         | Page Objects                                                                                       |
| -------------------------------------------------------------------------- | ------------------------------------------------------------ | -------------------------------------------------------------------------------------------------- |
| TC-015-2-0006-UNE, TC-015-2-0007-UNE, TC-015-2-0005-UNE                    | `should-transfer-and-show-ticket.ui.spec.ts`                 | `transfer-form.page.ts`, `transfer-history.page.ts`                                                |
| TC-015-2-0003-UNE, TC-015-2-0008-UNE, TC-015-2-0009-UNE                    | `should-show-transfer-history.ui.spec.ts`                    | `transfer-form.page.ts`, `transfer-history.page.ts`                                                |
| TC-015-2-0010-UNE, TC-015-2-0011-UNE, TC-015-2-0012-UNE, TC-015-2-0013-UNE | `should-send-device-validation-to-fraud-analysis.ui.spec.ts` | `transfer-form.page.ts`, `contact-agenda.page.ts`, `credin-request.page.ts`, `util-logger.page.ts` |

## TC-015-2-0006-UNE — UI — saldo actualizado sin comparación

- Fuente: `obtenerSaldoInicial` y `saldoActualizado` leen el saldo antes y después y sólo lo adjuntan.
- Aplicado: se leen ambos valores sin compararlos; el caso exige el ticket "Tu solicitud se realizo con
  exito" (comparación exacta sin acentos, `TicketSteps.validarTicket`).
- Revisión humana: pendiente; ¿debe exigirse el descuento de 1000?

## TC-015-2-0005-UNE — UI — valores fijos del ticket

- Fuente: el legado exige emisor `REYES, JOSE LUIS`, cuenta `CC $ 035003401876`, CUIT `20119120269`,
  destinatario `PEREZ, ROQUE`, CBU, CUIT y entidad destino fijos, además de fecha `dd/MM/yyyy`.
- Aplicado: se conservan como valores literales del caso en el test.
- Revisión humana: pendiente; confirmar que siguen correspondiendo al usuario `berhometest15`.

## TC-015-2-0003-UNE — UI — búsqueda sin límite

- Fuente: `buscarMovimientosConAntiguedadDeDosMeses` avanza páginas en un `while` hasta encontrar
  `/MM/` de hace dos meses, sin condición de salida.
- Aplicado: se avanza mientras exista "Ir a la siguiente página"; si se agotan las páginas, el caso falla
  con "Debe haber movimientos con dos meses de antigüedad".

## TC-015-2-0010-UNE–TC-015-2-0013-UNE — UI — utilLogger

- Fuente: `clickBtnUtilLogger` abre la herramienta `utilLogger` (pestaña nueva), "Actualizar", y
  `clickRequestSvc` recorre los requests de `/FraudAnalysis/service/analyze` hasta encontrar el
  `optionalParam` `metodoValidacionFP` y exige `DESCONOCIDO`; falla si no hay requests o no aparece.
- Aplicado: misma lógica; los cuerpos se leen en el Page Object y la comparación queda en el test.
- Dependencia: requiere el acceso `utilLogger` habilitado en QA para estos usuarios.

## TC-015-2-0012-UNE — UI — CREDIN sin selección explícita

- Fuente: `keyDownXParametrosGenerico(0, valor)` sólo exige que el desplegable muestre la cuenta y el
  concepto; no presiona Enter ni selecciona.
- Aplicado: se conserva (se comprueba la oferta sin seleccionar). Revisión humana: pendiente; ¿debe
  seleccionarse explícitamente cuenta y concepto?

## TC-015-2-0013-UNE — UI — importe 100 y eliminación condicional

- Aplicado: se elimina el contacto de la agenda sólo si el listado no informa "No se registran cuentas
  agendadas" (legado `eliminaContactoEnAgenda`), luego se programa una transferencia de 100 para una
  fecha específica con OTP.
