# Lote L8 — UI — Tarjetas

Portado el 21 de octubre de 2026 desde
`java/ui/GP_Migracion_Individuos/src/test/java/GP/EntreRios/front/FrontTest.java` (9 métodos) y sus
dependencias (`steps/tarjetasdecredito/TarjetasDeCreditoSteps`, `pages/tarjetasdecredito/TarjetasDeCreditoPage`,
`steps/botonesgenericos/BotonesGenericosSteps`, `pages/botonesgenericos/BotonesGenericosPage` y `utils/UtilsGP`).

Criterios comunes: login UI real; apertura del listado y del detalle con el testigo validado
`CreditCardsPage.open()`/`openDetail()` (TC0032/TC0033/TC0062BERSA); `isDisplayed()` sin assertion se
exige como visibilidad (criterio de `lote-base-ui.md`); el resto de los locators reproduce el legado y
queda pendiente de validación UI. Ningún caso se ejecutó contra BERSA QA.

Migrado en `tests/ui/cards/`:

| Caso(s)                        | Test                                                          | Page Object                       |
| ------------------------------ | ------------------------------------------------------------- | --------------------------------- |
| TC0104, TC0167                 | `should-download-credit-card-statement.ui.spec.ts`            | `credit-cards.page.ts` (ampliado) |
| TC0153, TC0155, TC0157, TC0158 | `should-show-credit-card-tracking.ui.spec.ts`                 | `credit-card-tracking.page.ts`    |
| TC0195                         | `should-redirect-new-card-request-to-appointments.ui.spec.ts` | `new-credit-card-request.page.ts` |
| TC0162                         | `should-start-credit-card-theft-report.ui.spec.ts`            | `credit-cards.page.ts`            |
| TC128242                       | `should-show-credit-card-detail-information.ui.spec.ts`       | `credit-cards.page.ts`            |

## TC0104/TC0167/TC0157BERSA — UI — validación de descargas

- Fuente: `validarDescarga(nombre)` espera 3 s y busca en `C:\Users\<usuario>\Downloads` un archivo cuyo
  nombre contenga el texto (`Estado` o `Detalle de seguimiento de tarjeta.pdf`) y lo borra.
- Aplicado: se espera el evento de descarga de Playwright al hacer clic y se exige que el nombre
  sugerido contenga el mismo texto. No depende de la carpeta del sistema ni de pausas.

## TC0158BERSA — UI — imprimir sin assertion

- Fuente: tras `clickImprimir`, `ManageTab` cambia de pestaña sin afirmar nada.
- Aplicado: se presiona imprimir y se cierra la pestaña nueva si aparece; el caso sólo valida que el
  seguimiento, su detalle y el icono de imprimir estén operables.

## TC0195BERSA — UI — cuenta débito y pestaña de Citas

- Fuente: `seleccionarCuentaDebitoSiEstaPresente(cuentaOrigen)` descarta cualquier error; el registro
  tiene `ctaOrigen` `sinDatos`. Tras "Aceptar", `ManageTab` cambia a la pestaña nueva si existe y
  luego se valida "Bienvenido a Citas".
- Aplicado: no se intenta elegir cuenta cuando el registro no la informa. Se espera hasta 15 s la
  pestaña nueva y, si no aparece, se busca el texto en la pestaña actual, como `ManageTab`.
- Revisión humana: pendiente; confirmar si la solicitud requiere cuenta débito.

## TC128242BERSA — UI — tarjetas identificadas por etiqueta

- Fuente: el caso abre la tarjeta `tarjetaAOperar` (`Tarjeta Master`) y luego la de `tarjetaDebito`
  (`Tarjeta Visa`), con el comentario legado de que ese campo contiene otra tarjeta de crédito.
- Aplicado: se usa el locator legado por texto (`//span[contains(text(),'…')]/ancestor::div[1]/following-sibling::div//a`)
  porque el dato no son dígitos. Se exigen los diez textos del detalle en ambas tarjetas.
- Revisión humana: pendiente; confirmar los datos del registro.

## TC0162BERSA — UI — denuncia sin confirmar

- Aplicado: detalle → Opciones → Denunciar robo o extravío → Robo → Continuar; se exige la pantalla
  "Confirmar denuncia por robo o extrav…". No se confirma la denuncia, como el legado.
