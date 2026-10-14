# Lote base UI — diferencias entre el legado y la UI actual de BERSA QA

## Resumen de ejecución (14 de octubre de 2026, BERSA QA, un worker, sin reintentos)

| Caso                                         | Resultado | Causa si falla                               |
| -------------------------------------------- | --------- | -------------------------------------------- |
| TC0151BERSA login con teclado virtual        | aprobada  | —                                            |
| TC0161BERSA información de seguridad         | aprobada  | —                                            |
| TC0032BERSA tarjeta Visa                     | aprobada  | —                                            |
| TC0033BERSA tarjeta Mastercard               | aprobada  | —                                            |
| TC0062BERSA detalle de tarjeta               | aprobada  | —                                            |
| TC0196BERSA card de seguro de Robo           | aprobada  | —                                            |
| TC0090BERSA eliminar mensaje                 | aprobada  | —                                            |
| TC0183BERSA / TC0184BERSA DEBIN vendedor     | aprobadas | —                                            |
| TC0152BERSA Tienda.Clic                      | fallida   | el ícono no existe en QA                     |
| TC0146BERSA / TC0148BERSA contacto inválido  | fallidas  | el CBU y el alias del registro existen en QA |
| TC128243BERSA aviso de advertencia           | fallida   | el aviso actual es informativo               |
| TC0227BERSA / TC0228BERSA adelanto de sueldo | fallidas  | URL `:` y texto ausente                      |

Nota de entorno: el sitio QA tarda más de 100 s en cargar desde algunas redes (el `goto` inicial
cayó en 3 intentos aislados que se repitieron sin cambios de código). No se modificó la
configuración de Playwright por este motivo.

Fuente común: `java/ui/GP_Migracion_Individuos/src/test/java/GP/EntreRios/front/FrontTest.java`
(un método por caso). Ejecución del 14 de octubre de 2026 contra BERSA QA, un worker y sin
reintentos. En todos los casos el legado entraba por sesión inyectada (`iniciarSessionFront`);
el port usa login UI y el menú lateral, igual que el resto de los casos ya migrados.

Ninguna sección cambia una assertion legada: los casos que fallan conservan su expectativa.

## TC0146BERSA — UI — CBU inválido al agendar un contacto

- Fuente: `FrontTest.TC0146BERSA`; `AgendaNuevoDestinatarioSteps.validarMensajeCbuInvalido`.
- Duda: el dato `cbuDestino` del registro es un CBU que BERSA QA reconoce (devuelve "Revisá los
  datos del contacto" con titular y CUIT), por lo que nunca aparece "CBU inválido". Además el
  legado llama `isDisplayed()` sin `assertTrue`; el port sí exige el mensaje.
- Aplicado: se busca el CBU del registro y se exige el texto "CBU inválido".
- Portado: completado. Ejecución: fallida (el CBU del registro es válido en QA).
- Revisión humana: pendiente; ¿se corrige el dato del registro por un CBU inexistente?

## TC0148BERSA — UI — alias inválido al agendar un contacto

- Fuente: `FrontTest.TC0148BERSA`; `AgendaNuevoDestinatarioSteps.validarMensajeAliasInvalido`.
- Duda: el alias del registro es el marcador `sindatos`, pero ese alias existe en QA (devuelve
  un titular). Mismo `isDisplayed()` sin assertion en el legado.
- Aplicado: se busca el alias del registro y se exige "Alias inválido".
- Portado: completado. Ejecución: fallida (el alias existe en QA).
- Revisión humana: pendiente; ¿se usa un alias inexistente explícito en el registro?

## TC0152BERSA — UI — acceso directo Tienda.Clic

- Fuente: `FrontTest.TC0152BERSA`; `PantallaPrincipalSteps.btnTiendaClic` y `TiendaClicTab`,
  locator `div[aria-label='Icono de compra']`.
- Duda: ni la pantalla inicial ni la de usuario de QA muestran ese ícono (se inspeccionó el
  árbol de accesibilidad de ambas).
- Aplicado: el port espera el ícono tras "Ingresar", abre la pestaña y exige el logo
  "Tienda Clic".
- Portado: completado. Ejecución: fallida (el control no existe en la UI actual).
- Revisión humana: pendiente; ¿se retiró el acceso directo del producto?

## TC0161BERSA — UI — Información de seguridad

- Fuente: `FrontTest.TC0161BERSA`; `PantallaPrincipalSteps.PreguntasFrecuentes` (marcado
  `@Deprecated` en el legado, pero invocado por el caso).
- Aplicado: se abre el enlace desde la pantalla de usuario y se exige el H1 "Seguridad para usar
  Home Banking / Office Banking" de la pestaña externa. Ejecución: aprobada.
- Revisión humana: pendiente sólo para confirmar que el método deprecado sigue vigente.

## TC128243BERSA — UI — aviso del inicio en formato advertencia

- Fuente: `FrontTest.TC128243BERSA`; `LoginSteps.validarLogoAdvertencia`.
- Duda: el aviso actual del inicio ("Mantenimiento de FCI y Mercado de Capitales en curso") es
  informativo, con fondo `rgb(230, 247, 255)`; el legado exige el amarillo de advertencia
  `rgba(255, 241, 184, 1)`. Chromium informa el mismo color opaco como `rgb(255, 241, 184)`.
- Aplicado: se exige el icono y el color de advertencia.
- Portado: completado. Ejecución: fallida (el aviso presente es informativo).
- Revisión humana: pendiente; el caso depende de configurar un aviso de tipo advertencia en QA.

## TC0033BERSA — UI — tarjeta Mastercard

- Fuente: `FrontTest.TC0033BERSA`; `TarjetasDeCreditoSteps.verificarTituloMastercard()`
  (H1 con `aria-label` que contiene "Master").
- Aplicado: se abre la tarjeta del registro (`tarjetaOperar`) y se exige que el título contenga
  "Master". Ejecución: aprobada.

## TC0090BERSA — UI — eliminar mensaje

- Fuente: `FrontTest.TC0090BERSA`; `MensajeSteps.validarEliminacionUltimoMensaje`.
- Duda: el legado compara con `!=` dos `String` (identidad de objeto), que siempre es verdadero,
  y toma un `<p>` por clase CSS generada. Además hay mensajes consecutivos con el mismo asunto.
- Aplicado: se identifica el primer mensaje por asunto y fecha y se exige que, tras eliminarlo,
  el primero de la bandeja sea otro. El caso elimina un mensaje real del usuario de QA.
- Revisión humana: pendiente; confirmar que el criterio asunto+fecha representa la intención.

## TC0196BERSA — UI — card de seguro de Robo

- Fuente: `FrontTest.TC0196BERSA`; `SegurosSteps.validacionCardRobo` (texto que contiene "Robo").
- Duda: igual que `tc0197bersa-ui.md`, la UI actual rotula el producto como "Bolso".
- Aplicado: se exige el texto "Robo" del legado, sin reemplazarlo por "Bolso".
- Revisión humana: pendiente; decidir junto con TC0197BERSA.

## TC0183BERSA / TC0184BERSA — UI — DEBIN vendedor sin CBU / alias

- Fuente: `FrontTest.TC0183BERSA` y `TC0184BERSA`; `DebinVendedorSteps`, `PagosSteps.validarCampoObligatorio`.
- Aplicado: cuenta destino del registro, importe 1, CBU o Alias, identificador " " y Buscar; se
  exige "Este campo es obligatorio". Se omitió el `fixedWait(5000)` del legado.
- Efecto: el recorrido llega por Pagos → Más Opciones → Debin vendedor.

## TC0227BERSA / TC0228BERSA — UI — resumen de contrato del adelanto de sueldo

- Fuente: `FrontTest.TC0227BERSA` y `TC0228BERSA`; `AdelantosDeSueldoSteps`,
  `BotonesGenericosSteps.validarFragmentoURL` (segunda pestaña, URL con "assets").
- Duda 1: el usuario `berhometest17` no tiene oferta ("No contamos con una oferta disponible
  para vos!"). `validacionPantallaSolicitudAH` decide su lista de textos según haya o no oferta;
  el port conserva esa decisión funcional.
- Duda 2 (TC0228): en el estado sin oferta el legado exige el texto "Adelanto de sueldo", pero
  la UI muestra "Adelanto de haberes".
- Duda 3 (TC0227): al aceptar, Home Banking abre una pestaña cuya URL es `:`, sin destino
  "assets"; no hay descarga ni navegación.
- Aplicado: se conservan ambas listas de textos y la URL con "assets".
- Ejecución: TC0227 fallida (URL `:`); TC0228 fallida (texto "Adelanto de sueldo" ausente).
- Efecto: los casos TC0222, TC0226, TC0237 y TC0238 del mismo módulo quedaron sin portar en este
  lote porque necesitan un usuario con oferta de adelanto; no se inventó una cuenta.
