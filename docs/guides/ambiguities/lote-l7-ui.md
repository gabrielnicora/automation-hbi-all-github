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

Migrado en `tests/ui/investments/`:

| Caso(s)                                                | Test                                                     | Page Object                                    |
| ------------------------------------------------------ | -------------------------------------------------------- | ---------------------------------------------- |
| TC0118, TC0126, TC0127, TC0128                         | `should-filter-funds.ui.spec.ts`                         | `investment-funds.page.ts` (ampliado)          |
| TC0133, TC0134                                         | `should-open-fund-more-information.ui.spec.ts`           | `investment-funds.page.ts`                     |
| TC0173                                                 | `should-reach-commitent-account-confirmation.ui.spec.ts` | `commitent-account-opening.page.ts`            |
| TC0044, TC0045                                         | `should-query-fixed-term-rates.ui.spec.ts`               | `fixed-terms.page.ts`                          |
| TC128244                                               | `should-validate-fixed-term-request-continue.ui.spec.ts` | `fixed-terms.page.ts`                          |
| TC0179, TC0181, TC0182, TC0187, TC0188, TC0189, TC0190 | `should-validate-dollar-exchange-form.ui.spec.ts`        | `foreign-currency-purchase.page.ts` (ampliado) |

Los métodos que ya usaban los testigos TC0119BERSA y TC0178BERSA no se modificaron.

## L7 — UI — navegación a Plazo fijo sin evidencia

- Fuente: el legado inyecta la sesión y navega a `/fixedTerms`. `specs/investments/fund-risk-filter.ui.plan.md`
  indica que con login UI real se ingresa por el menú.
- Aplicado: Inversiones → Gestioná tus inversiones (recorrido del testigo TC0178BERSA) → entrada
  "Plazo fijo" con el locator legado `//span[contains(text(),'Plazo fijo')]/../..`; se espera la ruta.
- Revisión humana: pendiente; confirmar en QA el acceso a Plazo fijo.

## TC0118BERSA — UI — comparación de riesgo

- Fuente: `validaFondosPorRiesgoVisiblesEnListado("Conservador")` compara el texto sin acentos con
  `equals` (sensible a mayúsculas) sobre la cuarta celda de cada fila.
- Aplicado: se reutiliza la lectura aprobada del testigo TC0119BERSA (columna `Riesgo` por encabezado,
  comparación normalizada), exigiendo al menos un fondo y que todos sean conservadores.
- Revisión humana: pendiente sólo para confirmar que la normalización aprobada aplica a este caso.

## TC0126BERSA / TC0127BERSA — UI — validación de moneda sin efecto

- Fuente: `validaFondosPorMonedaEnListado` sólo afirma cuando el texto contiene la moneda; si no la
  contiene no falla (la comparación estricta está comentada). En la práctica sólo exige listado no vacío.
- Aplicado: se exige listado no vacío; la moneda se lee de la segunda celda (`div[2]/p` del legado).
- Revisión humana: pendiente; ¿exigir que todos los fondos sean de la moneda filtrada?

## TC0133BERSA / TC0134BERSA — UI — sin assertion posterior

- Fuente: tras "Aceptar", `ManageTab`/`backMainTap` cambian y cierran pestañas sin afirmar nada;
  `clickCancelarGenerico` sólo hace clic si "Cancelar" está visible.
- Aplicado: se conservan las acciones; si al aceptar se abre otra pestaña, se cierra. Los casos sólo
  validan que el detalle y el modal se puedan operar.

## TC128244BERSA — UI — importes que superan el saldo

- Fuente: `UtilsGP.parseMontoAR` suma 100000 al saldo leído y el resultado se escribe sin punto
  decimal (por ejemplo, saldo `1.234,56` → `10123456`). Aun así, el legado exige "Continuar"
  habilitado en pesos, dólares, UVA y UVA precancelable; luego, en la simulación, exige "Continuar"
  deshabilitado y el mensaje de error, y habilitado tras restar 4.000.000.
- Aplicado: se conservan cálculo, orden de pasos y expectativas. El mensaje de error usa el locator
  posicional del legado (`//*[@id='mainLayout']/div[1]/div[2]/div/div/div[2]/div/p`).
- Revisión humana: pendiente; confirmar la intención de los importes y el mensaje.

## TC0181/TC0182/TC0189/TC0190BERSA — UI — desplegables de cuentas

- Fuente: `validarDesplegableCuentasEn*` recorre el desplegable con flechas y, en cada vuelta,
  sólo exige que algún `div` contenga `$` o `USD`; las comparaciones "sólo en pesos/dólares" están
  vacías.
- Aplicado: se abre el desplegable y se exige una opción con la moneda; no se exige que todas lo sean.
- Revisión humana: pendiente; ¿validar que todas las opciones sean de esa moneda?

## TC0179/TC0188BERSA — UI — sin aceptar términos

- Aplicado: operación de 10 con la cuenta `ctaOrigen`, destino `USD` (compra) o `$` (venta),
  Continuar y Confirmar; se exige el mensaje de términos obligatorios. La selección por texto parcial
  replica `seleccionarDropdownGenerico` y queda pendiente de validación UI.
