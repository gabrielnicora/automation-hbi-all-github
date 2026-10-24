# Lote L7 — UI — Inversiones

Portado el 21 de octubre de 2026 desde
`java/ui/GP_Migracion_Individuos/src/test/java/GP/EntreRios/front/FrontTest.java` (17 métodos) y sus
dependencias (`steps/inversiones/fci/FondoComunInversionSteps`, `steps/inversiones/plazosfijos/*`,
`steps/inversiones/cuentacomitente/CuentaComitenteSteps`, `steps/inversiones/compraventadedolares/*`,
`steps/botonesgenericos/BotonesGenericosSteps`, `utils/UtilsGP` y las pages correspondientes).

Criterios comunes:

- Login UI real en lugar de la sesión inyectada del legado (`iniciarSessionFront`), como el resto
  de los casos UI migrados. Las esperas fijas (`fixedWait`) se reemplazaron por estados observables.
- Las comprobaciones `isDisplayed()` sin `assertTrue` del legado se portan como exigencias de
  visibilidad, siguiendo el criterio ya aplicado en `lote-base-ui.md`.
- Los locators reproducen los del legado (XPath incluidos) porque no hubo evidencia UI actual: todos
  quedan pendientes de validación en BERSA QA. Ningún caso se ejecutó.

Migrado en `tests/ui/007-investments/`:

| Caso(s)                                                                                                                             | Test                                                     | Page Object                                    |
| ----------------------------------------------------------------------------------------------------------------------------------- | -------------------------------------------------------- | ---------------------------------------------- |
| TC-007-2-0003-UNE, TC-007-2-0005-UNE, TC-007-2-0006-UNE, TC-007-2-0007-UNE                                                          | `should-filter-funds.ui.spec.ts`                         | `investment-funds.page.ts` (ampliado)          |
| TC-007-2-0008-UNE, TC-007-2-0009-UNE                                                                                                | `should-open-fund-more-information.ui.spec.ts`           | `investment-funds.page.ts`                     |
| TC-007-2-0010-UNE                                                                                                                   | `should-reach-commitent-account-confirmation.ui.spec.ts` | `commitent-account-opening.page.ts`            |
| TC-007-2-0001-UNE, TC-007-2-0002-UNE                                                                                                | `should-query-fixed-term-rates.ui.spec.ts`               | `fixed-terms.page.ts`                          |
| TC-007-2-0019-UNE                                                                                                                   | `should-validate-fixed-term-request-continue.ui.spec.ts` | `fixed-terms.page.ts`                          |
| TC-007-2-0012-UNE, TC-007-2-0013-UNE, TC-007-2-0014-UNE, TC-007-2-0015-UNE, TC-007-2-0016-UNE, TC-007-2-0017-UNE, TC-007-2-0018-UNE | `should-validate-dollar-exchange-form.ui.spec.ts`        | `foreign-currency-purchase.page.ts` (ampliado) |

Los métodos que ya usaban los testigos TC-007-2-0004-UNE y TC-007-2-0011-UNE no se modificaron.

## L7 — UI — navegación a Plazo fijo sin evidencia

- Fuente: el legado inyecta la sesión y navega a `/fixedTerms`. `specs/investments/fund-risk-filter.ui.plan.md`
  indica que con login UI real se ingresa por el menú.
- Aplicado: Inversiones → Gestioná tus inversiones (recorrido del testigo TC-007-2-0011-UNE) → entrada
  "Plazo fijo" con el locator legado `//span[contains(text(),'Plazo fijo')]/../..`; se espera la ruta.
- Revisión humana: pendiente; confirmar en QA el acceso a Plazo fijo.

## TC-007-2-0003-UNE — UI — comparación de riesgo

- Fuente: `validaFondosPorRiesgoVisiblesEnListado("Conservador")` compara el texto sin acentos con
  `equals` (sensible a mayúsculas) sobre la cuarta celda de cada fila.
- Aplicado: se reutiliza la lectura aprobada del testigo TC-007-2-0004-UNE (columna `Riesgo` por encabezado,
  comparación normalizada), exigiendo al menos un fondo y que todos sean conservadores.
- Revisión humana: pendiente sólo para confirmar que la normalización aprobada aplica a este caso.

## TC-007-2-0005-UNE / TC-007-2-0006-UNE — UI — validación de moneda sin efecto

- Fuente: `validaFondosPorMonedaEnListado` sólo afirma cuando el texto contiene la moneda; si no la
  contiene no falla (la comparación estricta está comentada). En la práctica sólo exige listado no vacío.
- Aplicado: se exige listado no vacío; la moneda se lee de la segunda celda (`div[2]/p` del legado).
- Revisión humana: pendiente; ¿exigir que todos los fondos sean de la moneda filtrada?

## TC-007-2-0008-UNE / TC-007-2-0009-UNE — UI — sin assertion posterior

- Fuente: tras "Aceptar", `ManageTab`/`backMainTap` cambian y cierran pestañas sin afirmar nada;
  `clickCancelarGenerico` sólo hace clic si "Cancelar" está visible.
- Aplicado: se conservan las acciones; si al aceptar se abre otra pestaña, se cierra. Los casos sólo
  validan que el detalle y el modal se puedan operar.

## TC-007-2-0019-UNE — UI — importes que superan el saldo

- Fuente: `UtilsGP.parseMontoAR` suma 100000 al saldo leído y el resultado se escribe sin punto
  decimal (por ejemplo, saldo `1.234,56` → `10123456`). Aun así, el legado exige "Continuar"
  habilitado en pesos, dólares, UVA y UVA precancelable; luego, en la simulación, exige "Continuar"
  deshabilitado y el mensaje de error, y habilitado tras restar 4.000.000.
- Aplicado: se conservan cálculo, orden de pasos y expectativas. El mensaje de error usa el locator
  posicional del legado (`//*[@id='mainLayout']/div[1]/div[2]/div/div/div[2]/div/p`).
- Revisión humana: pendiente; confirmar la intención de los importes y el mensaje.

## TC-007-2-0013-UNE/TC-007-2-0014-UNE/TC-007-2-0017-UNE/TC-007-2-0018-UNE — UI — desplegables de cuentas

- Fuente: `validarDesplegableCuentasEn*` recorre el desplegable con flechas y, en cada vuelta,
  sólo exige que algún `div` contenga `$` o `USD`; las comparaciones "sólo en pesos/dólares" están
  vacías.
- Aplicado: se abre el desplegable y se exige una opción con la moneda; no se exige que todas lo sean.
- Revisión humana: pendiente; ¿validar que todas las opciones sean de esa moneda?

## TC-007-2-0012-UNE/TC-007-2-0016-UNE — UI — sin aceptar términos

- Aplicado: operación de 10 con la cuenta `ctaOrigen`, destino `USD` (compra) o `$` (venta),
  Continuar y Confirmar; se exige el mensaje de términos obligatorios. La selección por texto parcial
  replica `seleccionarDropdownGenerico` y queda pendiente de validación UI.
