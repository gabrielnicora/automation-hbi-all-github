# Lote base UI — diferencias entre el legado y la UI actual de BERSA QA

## Resumen de ejecución (14 de octubre de 2026, BERSA QA, un worker, sin reintentos)

| Caso                                                     | Resultado | Causa si falla                               |
| -------------------------------------------------------- | --------- | -------------------------------------------- |
| TC-002-2-0003-UNE login con teclado virtual              | aprobada  | —                                            |
| TC-014-2-0003-UNE información de seguridad               | aprobada  | —                                            |
| TC-003-2-0001-UNE tarjeta Visa                           | aprobada  | —                                            |
| TC-003-2-0002-UNE tarjeta Mastercard                     | aprobada  | —                                            |
| TC-003-2-0003-UNE detalle de tarjeta                     | aprobada  | —                                            |
| TC-006-2-0001-UNE card de seguro de Robo                 | aprobada  | —                                            |
| TC-009-2-0002-UNE eliminar mensaje                       | aprobada  | —                                            |
| TC-011-2-0001-UNE / TC-011-2-0002-UNE DEBIN vendedor     | aprobadas | —                                            |
| TC-014-2-0001-UNE Tienda.Clic                            | fallida   | el ícono no existe en QA                     |
| TC-015-2-0001-UNE / TC-015-2-0002-UNE contacto inválido  | fallidas  | el CBU y el alias del registro existen en QA |
| TC-005-2-0001-UNE aviso de advertencia                   | fallida   | el aviso actual es informativo               |
| TC-001-2-0007-UNE / TC-001-2-0008-UNE adelanto de sueldo | fallidas  | URL `:` y texto ausente                      |

Nota de entorno: el sitio QA tarda más de 100 s en cargar desde algunas redes (el `goto` inicial
cayó en 3 intentos aislados que se repitieron sin cambios de código). No se modificó la
configuración de Playwright por este motivo.

Fuente común: `java/ui/GP_Migracion_Individuos/src/test/java/GP/EntreRios/front/FrontTest.java`
(un método por caso). Ejecución del 14 de octubre de 2026 contra BERSA QA, un worker y sin
reintentos. En todos los casos el legado entraba por sesión inyectada (`iniciarSessionFront`);
el port usa login UI y el menú lateral, igual que el resto de los casos ya migrados.

Ninguna sección cambia una assertion legada: los casos que fallan conservan su expectativa.

## TC-015-2-0001-UNE — UI — CBU inválido al agendar un contacto

- Fuente: `FrontTest.TC-015-2-0001-UNE`; `AgendaNuevoDestinatarioSteps.validarMensajeCbuInvalido`.
- Duda: el dato `cbuDestino` del registro es un CBU que BERSA QA reconoce (devuelve "Revisá los
  datos del contacto" con titular y CUIT), por lo que nunca aparece "CBU inválido". Además el
  legado llama `isDisplayed()` sin `assertTrue`; el port sí exige el mensaje.
- Aplicado: se busca el CBU del registro y se exige el texto "CBU inválido".
- Portado: completado. Ejecución: fallida (el CBU del registro es válido en QA).
- Revisión humana: pendiente; ¿se corrige el dato del registro por un CBU inexistente?

## TC-015-2-0002-UNE — UI — alias inválido al agendar un contacto

- Fuente: `FrontTest.TC-015-2-0002-UNE`; `AgendaNuevoDestinatarioSteps.validarMensajeAliasInvalido`.
- Duda: el alias del registro es el marcador `sindatos`, pero ese alias existe en QA (devuelve
  un titular). Mismo `isDisplayed()` sin assertion en el legado.
- Aplicado: se busca el alias del registro y se exige "Alias inválido".
- Portado: completado. Ejecución: fallida (el alias existe en QA).
- Revisión humana: pendiente; ¿se usa un alias inexistente explícito en el registro?

## TC-014-2-0001-UNE — UI — acceso directo Tienda.Clic

- Fuente: `FrontTest.TC-014-2-0001-UNE`; `PantallaPrincipalSteps.btnTiendaClic` y `TiendaClicTab`,
  locator `div[aria-label='Icono de compra']`.
- Duda: ni la pantalla inicial ni la de usuario de QA muestran ese ícono (se inspeccionó el
  árbol de accesibilidad de ambas).
- Aplicado: el port espera el ícono tras "Ingresar", abre la pestaña y exige el logo
  "Tienda Clic".
- Portado: completado. Ejecución: fallida (el control no existe en la UI actual).
- Alcance posterior: el catálogo aprobado el 19 de octubre de 2026 no incluye `TC-014-2-0001-UNE`.
  El portado del 14 de octubre queda identificado como implementación preexistente fuera del
  alcance migrable; el cambio documental no lo elimina ni lo cuenta entre los 265 casos.
- Revisión humana: pendiente; ¿se retiró el acceso directo del producto?
  Resolver por separado si el test preexistente debe permanecer en la suite.

## TC-014-2-0003-UNE — UI — Información de seguridad

- Fuente: `FrontTest.TC-014-2-0003-UNE`; `PantallaPrincipalSteps.PreguntasFrecuentes` (marcado
  `@Deprecated` en el legado, pero invocado por el caso).
- Aplicado: se abre el enlace desde la pantalla de usuario y se exige el H1 "Seguridad para usar
  Home Banking / Office Banking" de la pestaña externa. Ejecución: aprobada.
- Revisión humana: pendiente sólo para confirmar que el método deprecado sigue vigente.

## TC-005-2-0001-UNE — UI — aviso del inicio en formato advertencia

- Fuente: `FrontTest.TC-005-2-0001-UNE`; `LoginSteps.validarLogoAdvertencia`.
- Duda: el aviso actual del inicio ("Mantenimiento de FCI y Mercado de Capitales en curso") es
  informativo, con fondo `rgb(230, 247, 255)`; el legado exige el amarillo de advertencia
  `rgba(255, 241, 184, 1)`. Chromium informa el mismo color opaco como `rgb(255, 241, 184)`.
- Aplicado: se exige el icono y el color de advertencia.
- Portado: completado. Ejecución: fallida (el aviso presente es informativo).
- Revisión humana: pendiente; el caso depende de configurar un aviso de tipo advertencia en QA.

## TC-003-2-0002-UNE — UI — tarjeta Mastercard

- Fuente: `FrontTest.TC-003-2-0002-UNE`; `TarjetasDeCreditoSteps.verificarTituloMastercard()`
  (H1 con `aria-label` que contiene "Master").
- Aplicado: se abre la tarjeta del registro (`tarjetaOperar`) y se exige que el título contenga
  "Master". Ejecución: aprobada.

## TC-009-2-0002-UNE — UI — eliminar mensaje

- Fuente: `FrontTest.TC-009-2-0002-UNE`; `MensajeSteps.validarEliminacionUltimoMensaje`.
- Duda: el legado compara con `!=` dos `String` (identidad de objeto), que siempre es verdadero,
  y toma un `<p>` por clase CSS generada. Además hay mensajes consecutivos con el mismo asunto.
- Aplicado: se identifica el primer mensaje por asunto y fecha y se exige que, tras eliminarlo,
  el primero de la bandeja sea otro. El caso elimina un mensaje real del usuario de QA.
- Revisión humana: pendiente; confirmar que el criterio asunto+fecha representa la intención.

## TC-006-2-0001-UNE — UI — card de seguro de Robo

- Fuente: `FrontTest.TC-006-2-0001-UNE`; `SegurosSteps.validacionCardRobo` (texto que contiene "Robo").
- Duda: igual que `tc0197bersa-ui.md`, la UI actual rotula el producto como "Bolso".
- Aplicado: se exige el texto "Robo" del legado, sin reemplazarlo por "Bolso".
- Revisión humana: pendiente; decidir junto con TC-006-2-0002-UNE.

## TC-011-2-0001-UNE / TC-011-2-0002-UNE — UI — DEBIN vendedor sin CBU / alias

- Fuente: `FrontTest.TC-011-2-0001-UNE` y `TC-011-2-0002-UNE`; `DebinVendedorSteps`, `PagosSteps.validarCampoObligatorio`.
- Aplicado: cuenta destino del registro, importe 1, CBU o Alias, identificador " " y Buscar; se
  exige "Este campo es obligatorio". Se omitió el `fixedWait(5000)` del legado.
- Efecto: el recorrido llega por Pagos → Más Opciones → Debin vendedor.

## TC-001-2-0007-UNE / TC-001-2-0008-UNE — UI — resumen de contrato del adelanto de sueldo

- Fuente: `FrontTest.TC-001-2-0007-UNE` y `TC-001-2-0008-UNE`; `AdelantosDeSueldoSteps`,
  `BotonesGenericosSteps.validarFragmentoURL` (segunda pestaña, URL con "assets").
- Duda 1: el usuario `berhometest17` no tiene oferta ("No contamos con una oferta disponible
  para vos!"). `validacionPantallaSolicitudAH` decide su lista de textos según haya o no oferta;
  el port conserva esa decisión funcional.
- Duda 2 (TC-001-2-0008-UNE): en el estado sin oferta el legado exige el texto "Adelanto de sueldo", pero
  la UI muestra "Adelanto de haberes".
- Duda 3 (TC-001-2-0007-UNE): al aceptar, Home Banking abre una pestaña cuya URL es `:`, sin destino
  "assets"; no hay descarga ni navegación.
- Aplicado: se conservan ambas listas de textos y la URL con "assets".
- Ejecución: TC-001-2-0007-UNE fallida (URL `:`); TC-001-2-0008-UNE fallida (texto "Adelanto de sueldo" ausente).
- Efecto: los casos TC-001-2-0004-UNE, TC-001-2-0006-UNE, TC-001-2-0009-UNE y TC-001-2-0010-UNE del mismo módulo quedaron sin portar en este
  lote porque necesitan un usuario con oferta de adelanto; no se inventó una cuenta.
