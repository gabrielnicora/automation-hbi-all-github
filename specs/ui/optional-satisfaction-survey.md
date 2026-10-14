# Encuesta de satisfacción opcional en pruebas UI

## Estado

Aprobada para implementación en el fixture UI compartido. La aplicación muestra esta
encuesta de forma intermitente según la configuración del ambiente.

## Objetivo y alcance

Evitar que la encuesta opcional de satisfacción y la invitación opcional a operar en USD
bloqueen cualquier prueba UI que use `src/fixtures/ui-test.ts`. No cambia las pruebas API.

## Comportamiento esperado

- Detectar solamente la encuesta cuyo encabezado sea `Gracias, tu opinión nos ayuda a
mejorar`.
- Si aparece, pulsar el botón de cierre del diálogo Qualtrics externo y esperar a que el
  diálogo desaparezca. El nombre accesible observado puede ser `Close` o `Cerrar` según el
  idioma del widget.
- Si aparece el mensaje `¡Empezá a operar en USD!`, pulsar `Cancelar` dentro de esa invitación
  y esperar a que desaparezca. No pulsar `Solicitar cuenta`.
- No elegir una calificación ni pulsar `Finalizar`; responder la encuesta no forma parte
  del objetivo de los casos.
- Si no aparece, continuar sin espera fija ni alterar otros diálogos.

## Diseño

- Un Page Object de `src/pages/feedback/` encapsula los locators y cierres de estos overlays
  opcionales.
- Un fixture automático de `src/fixtures/ui-test.ts` instala `page.addLocatorHandler` para
  que Playwright la cierre cuando aparezca durante cualquier prueba UI.
- El contenido de la encuesta vive en un `iframe`; el botón para cerrarla vive en el diálogo
  externo. El handler identifica ese diálogo por contener un `iframe` y limita la acción al
  botón con nombre `Close` o `Cerrar`; no registra handlers genéricos para overlays.
- La invitación a operar en USD se identifica por su encabezado exacto. El botón `Cancelar` se
  busca en el contenedor inmediato que agrupa el texto de la invitación y sus acciones, para no
  afectar otros botones `Cancelar` de la aplicación. Cada handler ejecuta la misma rutina que
  cierra primero el survey si está visible y luego la invitación si está visible; así, uno no
  depende de que Playwright invoque el otro handler.

## Evidencia y validación pendiente

La captura compartida muestra el encabezado exacto y la X de cierre. El HTML suministrado nombra
su botón `Cerrar`. El snapshot del fallo de Playwright muestra la encuesta dentro de un `iframe`
y el botón del diálogo externo con nombre accesible `Close`. El reporte confirma que la capa
`QSIWebResponsive` interceptó el clic en la cuenta. Por eso el handler se registra sobre el
diálogo Qualtrics externo (identificado por su `iframe`) y usa el nombre accesible del botón, en
vez de buscar la clase dentro del documento principal.

El mismo snapshot muestra la invitación `¡Empezá a operar en USD!` y sus botones `Cancelar` y
`Solicitar cuenta` detrás del survey. Aunque el usuario la describió como iframe, el snapshot la
muestra en el DOM principal; solo el survey está dentro del iframe. La automatización cierra
ambas capas en ese orden y usa `Cancelar` en la invitación.
