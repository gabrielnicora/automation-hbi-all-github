# Lote L11 — UI — Login/recupero, perfil, seguros y pagos

Portado el 21 de octubre de 2026 desde
`java/ui/GP_Migracion_Individuos/src/test/java/GP/EntreRios/front/FrontTest.java` (7 métodos) y sus
dependencias (`steps/login/LoginSteps`, `api/LoginAPI`, `api/DobleFactorAPI`, `steps/doblefactor/DobleFactorSteps`,
`steps/perfil/*`, `steps/seguros/SegurosSteps`, `steps/pagos/*`, `steps/administraciondeproductos/*`,
`steps/botonesgenericos/BotonesGenericosSteps`, `utils/UtilsGP` y las pages correspondientes).

Criterios comunes: login UI real; `isDisplayed()`/`isEnabled()` sin assertion se exigen como visibilidad
(`lote-base-ui.md`); los locators reproducen el legado y quedan pendientes de validación UI, salvo la
apertura de Seguros (testigo `InsurancePage`). Ningún caso se ejecutó contra BERSA QA.

| Caso(s)            | Test                                                                  | Page Object / workflow                                                                                                     |
| ------------------ | --------------------------------------------------------------------- | -------------------------------------------------------------------------------------------------------------------------- |
| TC0048, TC128240   | `ui/authentication/should-recover-username.ui.spec.ts`                | `login.page.ts` (ampliado)                                                                                                 |
| TC0035             | `ui/profile/should-change-security-seal.ui.spec.ts`                   | `profile/security-seal.page.ts` (nuevo)                                                                                    |
| TC12928            | `ui/insurance/should-reach-robbery-insurance-confirmation.ui.spec.ts` | `insurance.page.ts` (ampliado)                                                                                             |
| TC0077             | `ui/documents/should-download-product-contract.ui.spec.ts`            | `documents/product-documents.page.ts` (nuevo)                                                                              |
| TC110847, TC110363 | `ui/payments/should-pay-and-find-service-receipts.ui.spec.ts`         | `payment-services`, `service-adhesion`, `payment-receipts` y `workflows/payments/payment-services-preparation.workflow.ts` |

## TC0048/TC128240BERSA — UI — correo de recupero

- Fuente: tras el recupero, `loginAPI` y `DobleFactorAPI.getSecondFactorMailUser` leen `util.codes`,
  toman el último registro con `origin` igual al email del usuario (`util.logger.read.transactions`),
  exigen que `codeNoFormat` empiece con `¡Hola` y comparan el tercer tramo separado por `<br/>` con
  `Tu usuario de Home Banking es: <usuario en minúsculas>`. Bucle propio de 4×4 consultas cada 2,5 s.
- Aplicado: se usa la bandeja central (`SecondFactorChallengeApi.requestSingleCode`, nuevo: un solo
  envío, 12 consultas, sin reenvío porque el recupero no puede repetirse desde la misma pantalla). La
  línea base se toma antes de "Continuar", por eso la sesión API se abre al inicio y no después.
- Revisión humana: pendiente; confirmar que el correo de recupero se publica en `util.codes`.

## TC128240BERSA — UI — Continuar habilitado sin efecto

- Fuente: `validarBtnContinuarHabilitado` sólo afirma si `getText()` del input CUIL mide 11; en un
  `input` devuelve vacío, así que nunca afirma. `validarBtnContinuarDeshabilitado` sí exige deshabilitado
  (la comprobación de color siempre pasa).
- Aplicado: se exige deshabilitado sin CUIL; no se agregó la assertion de habilitado.
- Revisión humana: pendiente; ¿exigir Continuar habilitado con CUIL de 11 dígitos?

## TC0048/TC128240BERSA — UI — CUIL distintos para el mismo usuario

- Fuente: ambos usan `berhometest17`, con CUIL literales `20136570820` y `20080010045`.
- Aplicado: se conservan. Revisión humana: pendiente; confirmar cuál corresponde al usuario.

## TC0035BERSA — UI — sello de seguridad

- Fuente: `LoginAPI.tomarIdImagen` lee `data._securitySealId` de `session.login.step1`; se elige el sello
  anterior (o el siguiente si es 1) con `(//div[@class='is-current']/../div)[n]`; segundo factor común
  (con "Intentar con otro" → SMS si aparece) y `assert` Java de id distinto (activo en Surefire).
- Aplicado: `LoginApi.readSecuritySealId` (nuevo) y OTP con `SmsOperationOtpApi`; sin el reintento con
  segundo código. Usuario `Transpull4`. Efecto: cambia el sello del usuario en cada ejecución.

## TC12928BERSA — UI — seguros dentro del iframe

- Fuente: el legado busca la tarjeta `Robo Bolso`, "Continuar", términos, y sólo mira `Confirmar`
  (`isDisplayed`) y `Cancelar` (`isEnabled`) sin assertion.
- Aplicado: apertura validada del testigo (`iframeQualia`, tarjeta "Bolso"); el resto se busca dentro
  del iframe con los locators legados. Se exige ver ambos botones; no se confirma la solicitud.
- Revisión humana: pendiente; confirmar los controles dentro del iframe.

## TC0077BERSA — UI — ruta directa y descarga

- Fuente: el legado inyecta la sesión en `digitalFilesConsult`; elige el producto con flechas
  (`keyDownAndSelectElement`) y valida en `C:\Users\<usuario>\Downloads` un archivo que contenga
  `Alta Cuenta Comitente desde Home Banking .pdf` (con espacio antes de `.pdf`).
- Aplicado: tras el login real se navega a la misma ruta (no hay evidencia del acceso por menú); se elige
  la opción por texto y se valida el nombre sugerido del evento de descarga, con el mismo texto.
- Revisión humana: pendiente; confirmar el acceso por menú y el nombre del archivo.

## TC110847/TC110363BERSA — UI — preparación condicional de pagos

- Fuente: `validarBotonMostrarTodos` agenda dos veces el servicio Mercado Libre (`00449535691`,
  `MLPAYMENT`) y paga dos servicios si no hay "Mostrar todos"; `validarServiciosUnisys` agenda y adhiere
  el servicio si no se ve `MLPAYMENT`, y siempre vuelve con la flecha. Cada alta usa segundo factor y
  exige el ticket "Tu solicitud se realizo con exito".
- Aplicado: `PaymentServicesPreparationWorkflow` reproduce ambas decisiones (coordina listado, formulario
  de adhesión y OTP). El doble agendado idéntico se conserva. Navegación inicial por menú lateral
  Pagos y servicios → Pagos en lugar de la ruta `payments`.
- Efectos: pagos reales de 1 y altas de servicios para `berhometest17` y `berhometest15`.
- Revisión humana: pendiente; ¿es intencional agendar dos veces el mismo servicio?

## TC110847BERSA — UI — checkboxes por "Mercado Pago"

- Fuente: `getSeleccinaServicioAPagar` busca `(//p[contains(text(),'Mercado Pago')]//..//..//input/..)[n]`
  aunque el servicio preparado es Mercado Libre. El pago no pide segundo factor.
- Aplicado: se conserva el locator. Revisión humana: pendiente.

## TC110363BERSA — UI — doble Volver

- Fuente: tras `validarServiciosUnisys` (que ya termina con la flecha Volver) el caso vuelve a presionar
  la flecha antes de "Mis Comprobantes". Origen `4002` y búsqueda por `00449535691`.
- Aplicado: se conserva. `validarComprobante` exige que el comprobante exista y sea visible.
