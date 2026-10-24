# Familia funcional: mensajes

## Regla de uso para Kiro

Aplicar la [guía general](../README.md) y los [principios](../migration-principles.md).
Los recorridos siguientes describen testigos, no límites para otras variantes: preservar
pasos, datos, efectos y assertions del código legado y sus helpers, salvo una decisión
explícita que los reemplace para ese mismo caso. Ante dudas, portar lo ejecutable, registrar
en [ambiguities](../ambiguities/README.md) y continuar sin consultar ni exigir tablas o una
especificación individual nueva. No portar retries internos; usar Playwright y reutilizar
la política central de segundo factor. No heredar automáticamente marcas de fallo esperado.

## Propósito

Migrar lecturas de mensajes y cambios de estado sin confundir una consulta API con una
mutación UI. El testigo de no leído restaura el estado; no generalizar esa limpieza a
variantes cuyo legado autoriza otro efecto persistente.

## Casos testigo

- TC-009-1-0003-UNE: consulta de mensajes recibidos API.
- TC-009-2-0001-UNE: marcar un mensaje leído como no leído UI y restaurarlo.

Referencias:

- specs/messages/received-messages.api.plan.md
- specs/messages/mark-message-unread.ui.plan.md
- src/api/messages/received-messages.api.ts
- src/pages/messages/messages.page.ts
- tests/api/009-messages/
- tests/ui/009-messages/

## Consulta API — TC-009-1-0003-UNE

Después del login, ejecutar communications.list con direction BANK_TO_CUSTOMER,
UUID y versión aprobada. La respuesta exige HTTP 200, objeto JSON y COR000I. data debe ser
objeto; una lista vacía es válida. `receivedMessagesApi.list(session)` devuelve `hasDataObject`,
que el test exige en `true`. La omisión de la consulta histórica de productos corresponde
a este caso aprobado; no eliminar llamadas de otras variantes sin revisar sus helpers,
validaciones y efectos. Ante dudas, conservarlas y registrar para después.

No exigir cantidad de mensajes si el test legado no lo verifica. No agregar retry interno.

## Cambio UI — TC-009-2-0001-UNE

Flujo:

1. Login UI real.
2. Abrir Mensajes.
3. Garantizar al menos un mensaje leído; si sólo hay no leídos, marcar el primero como leído.
4. Capturar el asunto del primer leído.
5. Abrir menú y elegir Marcar como no leído.
6. Abrir Sin leer.
7. Confirmar el mismo asunto.
8. En finally, marcarlo nuevamente como leído.

MessagesPage contiene locators, acciones y esperas. El texto exacto de la acción debe evitar
coincidencias genéricas. La restauración es obligatoria para este testigo. Su superficie es
`open()`, `ensureReadMessageAvailable()`, `markFirstReadMessageAsUnread()` (devuelve el asunto),
`isMessageAvailableInUnread(subject)` (esperado `true`) y `restoreMessageAsRead(subject)`
en `finally` cuando se capturó el asunto. Preservar la limpieza propia de cada variante.

## Variantes

- mensajes recibidos, enviados o no leídos: consultas y expectativas separadas;
- leer, no leer, borrar o archivar: mutaciones diferentes;
- bandeja vacía: precondición explícita y diagnóstico de datos insuficientes.

## Ambigüedades

Registrar si no hay mensajes en ninguna bandeja, si el asunto no es estable, si la acción
cambia de texto o si la API devuelve data vacía. No inventar un mensaje ni recorrer múltiples
casos dentro de un único test.

## Checklist

- [ ] Se distinguió lectura de cambio de estado.
- [ ] El mismo mensaje se identifica mediante un campo estable.
- [ ] La mutación UI se restaura en finally.
- [ ] La lista vacía tiene significado aprobado.
- [ ] No hay selectores ni URLs en el test.
- [ ] No se imprimen tokens, cookies ni passwords.
