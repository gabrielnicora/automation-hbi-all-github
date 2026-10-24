# Consulta de trámites vigentes por API — Banco Entre Ríos

## Estado

Aprobada para implementación inicial mediante `TC-012-1-0001-UNE`. Una lista vacía es válida; el caso
verifica `COR000I` y el objeto JSON de respuesta sin exigir un trámite vigente.

## Objetivo y alcance

Consultar los trámites online vigentes del usuario autenticado en BERSA QA. Altas, bajas,
modificaciones, detalle de un trámite y otros bancos quedan fuera.

## Recorrido observado en el legado

Carga `TC-012-1-0001-UNE`, inicia sesión y ejecuta
`POST /api/v1/execute/onlineProcedure.list` con un request vacío que contiene UUID y
`xAppVersion: 1.95.0`. Sólo aplica el validador técnico común.

## Diseño propuesto

- Cliente pequeño en `src/api/procedures/` y test individual en `tests/api/012-procedures/`.
- Sin fixture de datos específica, salvo que la aserción funcional dependa de un trámite known-good.
- Sin polling ni reintentos internos.

## Criterios de aceptación propuestos

- La consulta exige HTTP 200, JSON objeto y `COR000I`.
- El test no exige una lista no vacía ni un trámite de prueba estable.
- La evidencia de fallo excluye tokens y credenciales.

## Decisiones abiertas

La ejecución en BERSA QA debe confirmar estructura, versión y si resulta útil agregar una
aserción sobre un trámite conocido. Por ahora no requiere datos adicionales al usuario dummy.
