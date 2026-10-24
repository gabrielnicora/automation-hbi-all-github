# Consulta de preferencias de notificaciones por API — Banco Entre Ríos

## Estado

Aprobada para implementación inicial mediante `TC-013-1-0002-UNE`. Se acepta `COR000I` y se exige que
la configuración exponga `subscribedSMS`, `subscribedMail` y `subscribedPush` como booleanos,
sin imponer que estén activos.

## Objetivo y alcance

Consultar la configuración de notificaciones de mensajes del usuario autenticado en BERSA QA.
No modifica preferencias, no cubre otros tipos de comunicación ni cambio de teléfono.

## Recorrido observado en el legado

1. Ejecuta `POST /api/v1/execute/preferences.notifications.configuration.pre` con body vacío.
2. Ejecuta `POST /api/v1/execute/preferences.notifications.configuration.modify.pre` con
   `communicationType: message`.

Ambas requests históricas usan UUID y versiones `1.95.0`/`1.79.1`, validan sólo éxito técnico y
reintentan hasta cuatro veces. Un comentario no ejecutable menciona `message`, `accepted` y
`userBlock` en `true`.

## Diseño propuesto

- Cliente en `src/api/profile/`, separado del de cambio de teléfono salvo duplicación demostrada.
- Test en `tests/api/013-profile/`, sin mutaciones, polling ni reintentos internos.
- UUID nuevo por request y cabecera de autorización proveniente de la sesión.

## Criterios de aceptación propuestos

- Cada consulta exige HTTP 200, JSON objeto y `COR000I`.
- El test verifica que existan `subscribedSMS`, `subscribedMail` y `subscribedPush` como booleanos.
- El test no asume `true` para esos campos.
- La evidencia excluye tokens, cookies y credenciales.

## Decisiones abiertas

La ejecución en BERSA QA debe confirmar que ambos endpoints, payloads y paths continúan vigentes.
La regla histórica que exigía las tres suscripciones en `true` queda reemplazada por presencia y
tipo booleano hasta recibir una definición de producto distinta.
