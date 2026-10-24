# Lote L10 — UI — Cuentas, adelanto de sueldo, préstamos y eCheq

Portado el 21 de octubre de 2026 desde
`java/ui/GP_Migracion_Individuos/src/test/java/GP/EntreRios/front/FrontTest.java` (15 métodos) y sus
dependencias (`steps/cuentas/CuentasSteps`, `steps/cuentas/SolicitarNuevaCuentaSteps`,
`steps/adelantosdesueldo/AdelantosDeSueldoSteps`, `steps/prestamos/*`, `steps/echeq/*`,
`steps/botonesgenericos/BotonesGenericosSteps`, `steps/doblefactor/DobleFactorSteps`, `api/EcheqAPI` y las
pages correspondientes).

Criterios comunes: login UI real; `isDisplayed()` sin assertion se exige como visibilidad
(`lote-base-ui.md`); se reutilizan los testigos validados `SalaryAdvancePage.openRequest`
(TC-001-2-0007-UNE/TC-001-2-0008-UNE) y `EcheqPage.openFromAccounts` (TC-001-2-0015-UNE); el resto de los locators reproduce el
legado y queda pendiente de validación UI. Ningún caso se ejecutó contra BERSA QA.

Migrado:

| Caso(s)                                                                                       | Test                                                                    | Page Object                          |
| --------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------- | ------------------------------------ |
| TC-001-2-0002-UNE                                                                             | `ui/001-accounts/should-show-account-movement-detail.ui.spec.ts`        | `accounts.page.ts` (nuevo)           |
| TC-001-2-0011-UNE, TC-001-2-0012-UNE, TC-001-2-0013-UNE                                       | `ui/001-accounts/should-enable-new-account-request-continue.ui.spec.ts` | `accounts.page.ts`                   |
| TC-001-2-0004-UNE, TC-001-2-0005-UNE, TC-001-2-0006-UNE, TC-001-2-0009-UNE, TC-001-2-0010-UNE | `ui/001-accounts/should-validate-salary-advance-request.ui.spec.ts`     | `salary-advance.page.ts` (ampliado)  |
| TC-008-2-0001-UNE, TC-008-2-0002-UNE, TC-008-2-0003-UNE                                       | `ui/008-loans/should-request-loans.ui.spec.ts`                          | `loans/loan-request.page.ts` (nuevo) |
| TC-001-2-0014-UNE, TC-001-2-0001-UNE, TC-001-2-0016-UNE                                       | `ui/001-accounts/should-operate-echeqs.ui.spec.ts`                      | `echeq.page.ts` (ampliado)           |

Los textos de `validacionPantallaSolicitudAH` se movieron a `tests/ui/001-accounts/salary-advance-request-screen.ts`
y los comparte el testigo TC-001-2-0008-UNE (sin cambio de comportamiento).

## Adelanto de sueldo — usuario sin oferta

- Fuente: TC-001-2-0005-UNE, TC-001-2-0006-UNE, TC-001-2-0009-UNE y TC-001-2-0010-UNE cargan importe y presionan Continuar; necesitan oferta vigente.
  `berhometest17` no tiene oferta (ya registrado en `lote-base-ui.md`).
- Aplicado: se porta el recorrido; sin oferta el campo `#amount` no aparece y el caso falla allí.
- Revisión humana: pendiente; asignar un usuario con oferta.

## TC-001-2-0005-UNE — UI — volver

- Fuente: `clickVolverFlechaGenerico` (`//span[normalize-space(.)='Volver']/ancestor::div[1]`).
- Aplicado: mismo locator; se espera que reaparezca "Resumen de contrato" antes de validar la pantalla.

## TC-001-2-0011-UNE–TC-001-2-0013-UNE — UI — botón Continuar

- Fuente: los títulos dicen "Botón continuar deshabilitado", pero el recorrido exige deshabilitado al inicio
  (`validarBtnDeshabilitado`) y habilitado tras elegir tipo y moneda (`validarBotonHabilitado`: ausencia de
  `//button[@disabled and @aria-label='Continuar']` en 2 s).
- Aplicado: se conservan ambas comprobaciones con el título legado.

## Préstamos — navegación

- Fuente: el legado inyecta la sesión y navega a `loanRequest/1` o `loanRequest/cancellations`.
- Aplicado: menú lateral "Préstamos" (`//p[contains(text(),'stamos')]//ancestor::button`) y tarjeta
  "Nuevo préstamo" / "Préstamo para cancelar deudas"; se espera la misma ruta.
- Revisión humana: pendiente; confirmar el acceso por menú en QA.

## TC-008-2-0001-UNE/TC-008-2-0002-UNE — UI — préstamo sin confirmar

- Aplicado: se llega a la etapa de confirmación y se validan sus textos; no se ingresa el código ni se
  otorga el préstamo, como el legado. TC-008-2-0002-UNE requiere un préstamo vigente para cancelar.

## TC-008-2-0003-UNE — UI — contrato en pestaña nueva

- Fuente: `ManageTabPorParametro(2)` cambia a la segunda pestaña y exige `assets` en la URL.
- Aplicado: se espera la pestaña nueva (15 s), se lee su URL y se cierra.

## TC-001-2-0001-UNE — UI — estado "Todos" sin selección

- Fuente: `keyDownXParametrosGenerico` baja 13 veces en el desplegable y sólo exige ver "Todos"; no
  selecciona. Luego Aplicar y detalle del primer eCheq.
- Aplicado: se exige ver "Todos" sin seleccionarlo. Revisión humana: pendiente.

## TC-001-2-0016-UNE — UI — precondición de emisión no portada (parcial)

- Fuente: `echeqAPI.generacionDeEcheqAPI` emite el eCheq con el registro `TCECHEQBERSA` (login de
  empresas, ambiente y CUIT de emisor propios) y guarda su número.
- Faltante: el proyecto no soporta el login de empresas y la clave `TCECHEQBERSA` no cumple
  `/^TC\d+BERSA$/` (ya registrado en `lote-l5-api.md`).
- Aplicado: se porta toda la parte UI (búsqueda por número y "Emitido pendiente", detalle, aceptación,
  segundo factor con `SmsOperationOtpApi` y grilla final). La precondición falla con un error explícito,
  así que el caso falla siempre hasta resolverla.
- Revisión humana: pendiente; decidir cómo obtener un eCheq emitido pendiente para `berhometest20`.
