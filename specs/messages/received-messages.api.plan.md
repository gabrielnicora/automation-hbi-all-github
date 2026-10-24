# Consulta de mensajes recibidos por API — Banco Entre Ríos

## Estado

Aprobada para implementación inicial mediante `TC0109BERSA`. Una casilla vacía es válida; el caso
verifica `COR000I` y un objeto `data` válido, sin exigir mensajes recibidos.

## Objetivo y alcance

Consultar los mensajes recibidos (`BANK_TO_CUSTOMER`) del usuario autenticado en BERSA QA. La
lectura de no leídos, mensajes enviados, cambios de estado y otros bancos quedan fuera.

## Recorrido observado en el legado

Tras login y una consulta de productos que no se usa después, llama
`POST /api/v1/execute/communications.list` con `direction: BANK_TO_CUSTOMER`, UUID y versión
`1.79.1`. El código sólo valida la respuesta transversal y reintenta hasta cuatro veces; el
comentario menciona cantidad de mensajes, pero no existe una aserción de cantidad.

## Diseño propuesto

- Cliente mínimo en `src/api/messages/` con una única consulta autenticada.
- Test individual en `tests/api/009-messages/`, sin datos funcionales adicionales salvo que el
  resultado aprobado los requiera.
- Sin polling ni reintentos internos.

## Criterios de aceptación propuestos

- La consulta exige HTTP 200, JSON objeto y `COR000I`.
- El test comprueba que `data` sea un objeto; no exige que exista al menos un mensaje.
- La evidencia de fallo conserva la request sanitizada y excluye sesión.

## Decisiones abiertas

La ejecución en BERSA QA debe confirmar la estructura de `data`. La consulta de productos del
legado no se migra porque no alimenta la request ni la expectativa del caso.
