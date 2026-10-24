# Catálogo canónico de casos migrables

> Estado: aprobado  
> Decisión: 19 de octubre de 2026  
> Alcance: migración acelerada de casos legados BERSA a Playwright/TypeScript

## Propósito

Este catálogo define el universo cerrado de casos que se pueden migrar. La presencia de un
caso en el código legado no alcanza para autorizar su portado: su ID debe figurar en este
documento y el caso debe continuar activo según las reglas siguientes.

Los 265 IDs de este catálogo fueron provistos por el usuario junto con su canal declarado.
El canal declarado expresa dónde debe intentarse la migración y no garantiza que el legado
esté ubicado correctamente.

## Regla de elegibilidad

Un caso es migrable solamente cuando cumple todas estas condiciones:

1. Su ID figura en el catálogo.
2. Existe una fuente legada activa que define el caso según las reglas de origen de
   `SPEC.md`, sección 3, y `docs/guides/README.md`.
3. El caso no tiene una marca efectiva de ignorado, deshabilitado, obsoleto o deprecado,
   incluidas anotaciones como `@Ignore` o `@Disabled` y equivalentes del legado.

Una definición está activa sólo si el método no está comentado, conserva una anotación `@Test`
activa y ni el método ni su clase tienen una marca de exclusión. Un `@Test` comentado o ausente y
comentarios inequívocos como `Caso obsoleto` o `SE ELIMINA EL CASO` excluyen la definición. La
deprecación de un helper llamado no excluye por sí sola al test. Si existen varias definiciones
del mismo ID, sólo las activas de la fuente autorizada participan en la resolución; más de una
definición activa se registra como ambigüedad antes de portar el caso.

No se migra un caso que no figure en el catálogo, aunque exista y sea ejecutable en el
legado. Tampoco se migra un ID catalogado si su única definición está ignorada,
deshabilitada, obsoleta o deprecada. En ambos casos se informa el motivo como exclusión, no
como portado pendiente.

La elegibilidad se comprueba antes de analizar o implementar el caso. Incluir un ID aquí no
declara que su fuente exista, esté activa, se haya portado ni se haya validado en QA.

Este catálogo gobierna la selección de portados desde su fecha de aprobación. No autoriza por
sí solo a borrar un test que ya estuviera implementado fuera del catálogo: esa situación se
registra como inconsistencia preexistente y requiere una decisión separada sobre su retiro.

## Resolución del canal

En este catálogo `WEB` es la denominación legada del canal `UI`; una migración WEB se implementa
bajo `tests/ui/` y se informa como UI efectivo.

- Para un caso declarado `API`, buscar primero una fuente API activa y migrarlo como API,
  aunque el mismo ID también exista en el legado UI.
- Si un caso declarado `API` no tiene ninguna definición en el legado API, pero sí una fuente
  UI activa válida —un método de `FrontTest.java`—, migrarlo como UI y registrar la diferencia
  entre canal declarado y canal encontrado en `docs/guides/ambiguities/`.
- Si existe la definición API pero está ignorada, deshabilitada, obsoleta o deprecada, el caso
  queda excluido: esa marca no habilita el fallback a UI. La excepción de canal sólo aplica
  cuando la definición API no existe y la fuente UI encontrada está activa.
- Para un caso declarado `WEB`, la fuente migrable es el método activo correspondiente de
  `FrontTest.java`, conforme a la regla UI vigente. Este catálogo no autoriza el fallback
  inverso de `WEB` a API.
- La columna `Tipo declarado` conserva la decisión de alcance original. No se modifica por
  una inconsistencia encontrada en el legado; el canal efectivamente migrado y la evidencia
  se registran en la entrega y, cuando difieran, en el registro de ambigüedades.

## Catálogo

| ID                | Tipo declarado |
| ----------------- | -------------- |
| TC-001-1-0070-UNE | API            |
| TC-001-1-0073-UNE | API            |
| TC-001-1-0049-UNE | API            |
| TC-011-1-0008-UNE | API            |
| TC-001-1-0072-UNE | API            |
| TC-015-1-0027-UNE | API            |
| TC-013-1-0001-UNE | API            |
| TC-015-1-0024-UNE | API            |
| TC-001-1-0010-UNE | API            |
| TC-005-1-0001-UNE | API            |
| TC-011-1-0007-UNE | API            |
| TC-015-1-0003-UNE | API            |
| TC-001-1-0032-UNE | API            |
| TC-001-1-0030-UNE | API            |
| TC0407BERSA       | API            |
| TC-001-1-0074-UNE | API            |
| TC-015-1-0035-UNE | API            |
| TC-001-1-0086-UNE | API            |
| TC-011-1-0005-UNE | API            |
| TC-001-1-0061-UNE | API            |
| TC-015-1-0005-UNE | API            |
| TC-015-1-0010-UNE | API            |
| TC-009-1-0002-UNE | API            |
| TC-015-1-0043-UNE | API            |
| TC-015-1-0028-UNE | API            |
| TC-015-1-0001-UNE | API            |
| TC-015-1-0042-UNE | API            |
| TC-001-1-0025-UNE | API            |
| TC-001-1-0037-UNE | API            |
| TC-001-1-0091-UNE | API            |
| TC-001-1-0039-UNE | API            |
| TC0111BERSA       | API            |
| TC-011-1-0002-UNE | API            |
| TC-015-1-0017-UNE | API            |
| TC-001-1-0047-UNE | API            |
| TC-001-1-0002-UNE | API            |
| TC-001-1-0035-UNE | API            |
| TC-001-1-0071-UNE | API            |
| TC-001-1-0050-UNE | API            |
| TC-003-1-0009-UNE | API            |
| TC-015-1-0002-UNE | API            |
| TC-007-1-0002-UNE | API            |
| TC-003-1-0008-UNE | API            |
| TC-009-1-0003-UNE | API            |
| TC-001-1-0020-UNE | API            |
| TC-001-1-0044-UNE | API            |
| TC-001-1-0040-UNE | API            |
| TC-015-1-0036-UNE | API            |
| TC-007-1-0007-UNE | API            |
| TC-013-1-0002-UNE | API            |
| TC-003-1-0010-UNE | API            |
| TC-007-1-0003-UNE | API            |
| TC-001-1-0022-UNE | API            |
| TC-001-1-0019-UNE | API            |
| TC-015-1-0037-UNE | API            |
| TC-007-1-0008-UNE | API            |
| TC-001-1-0079-UNE | API            |
| TC-015-1-0014-UNE | API            |
| TC-001-1-0011-UNE | API            |
| TC-015-1-0008-UNE | API            |
| TC-001-1-0026-UNE | API            |
| TC-001-1-0008-UNE | API            |
| TC-015-1-0030-UNE | API            |
| TC-001-1-0081-UNE | API            |
| TC-015-1-0033-UNE | API            |
| TC-001-1-0038-UNE | API            |
| TC-015-1-0013-UNE | API            |
| TC-001-1-0075-UNE | API            |
| TC-011-1-0003-UNE | API            |
| TC-001-1-0088-UNE | API            |
| TC-001-1-0080-UNE | API            |
| TC-003-1-0007-UNE | API            |
| TC-001-1-0033-UNE | API            |
| TC-001-1-0059-UNE | API            |
| TC-001-1-0055-UNE | API            |
| TC-015-1-0026-UNE | API            |
| TC-001-1-0016-UNE | API            |
| TC-001-1-0007-UNE | API            |
| TC-015-1-0016-UNE | API            |
| TC-001-1-0043-UNE | API            |
| TC-001-1-0063-UNE | API            |
| TC-007-1-0005-UNE | API            |
| TC-007-1-0001-UNE | API            |
| TC-001-1-0028-UNE | API            |
| TC-001-1-0048-UNE | API            |
| TC-001-1-0069-UNE | API            |
| TC-001-1-0058-UNE | API            |
| TC-001-1-0005-UNE | API            |
| TC-001-1-0082-UNE | API            |
| TC-015-1-0018-UNE | API            |
| TC-001-1-0009-UNE | API            |
| TC-001-1-0001-UNE | API            |
| TC-001-1-0064-UNE | API            |
| TC-001-1-0062-UNE | API            |
| TC-001-1-0051-UNE | API            |
| TC-001-1-0087-UNE | API            |
| TC-015-1-0040-UNE | API            |
| TC-001-1-0053-UNE | API            |
| TC-015-1-0009-UNE | API            |
| TC-001-1-0085-UNE | API            |
| TC-001-1-0076-UNE | API            |
| TC-001-1-0077-UNE | API            |
| TC-001-1-0023-UNE | API            |
| TC-015-1-0006-UNE | API            |
| TC-015-1-0020-UNE | API            |
| TC-015-1-0022-UNE | API            |
| TC-015-1-0032-UNE | API            |
| TC-001-1-0015-UNE | API            |
| TC-015-1-0004-UNE | API            |
| TC-001-1-0057-UNE | API            |
| TC-015-1-0019-UNE | API            |
| TC-015-1-0015-UNE | API            |
| TC-015-1-0038-UNE | API            |
| TC-011-1-0010-UNE | API            |
| TC-015-1-0041-UNE | API            |
| TC-011-1-0006-UNE | API            |
| TC-001-1-0066-UNE | API            |
| TC-003-1-0005-UNE | API            |
| TC-003-1-0001-UNE | API            |
| TC-001-1-0052-UNE | API            |
| TC-003-1-0004-UNE | API            |
| TC-015-1-0047-UNE | API            |
| TC-001-1-0045-UNE | API            |
| TC-015-1-0011-UNE | API            |
| TC-003-1-0002-UNE | API            |
| TC-003-1-0003-UNE | API            |
| TC-001-1-0031-UNE | API            |
| TC-001-1-0060-UNE | API            |
| TC-001-1-0006-UNE | API            |
| TC-001-1-0027-UNE | API            |
| TC-001-1-0089-UNE | API            |
| TC-007-1-0006-UNE | API            |
| TC-001-1-0017-UNE | API            |
| TC0463BERSA       | API            |
| TC-001-1-0036-UNE | API            |
| TC-015-1-0046-UNE | API            |
| TC-015-1-0044-UNE | API            |
| TC-001-1-0024-UNE | API            |
| TC-001-1-0042-UNE | API            |
| TC-015-1-0023-UNE | API            |
| TC-011-1-0001-UNE | API            |
| TC-001-1-0046-UNE | API            |
| TC0113BERSA       | API            |
| TC-001-1-0014-UNE | API            |
| TC-001-1-0054-UNE | API            |
| TC-001-1-0065-UNE | API            |
| TC-001-1-0090-UNE | API            |
| TC-015-1-0021-UNE | API            |
| TC-015-1-0048-UNE | API            |
| TC-009-1-0001-UNE | API            |
| TC-001-1-0056-UNE | API            |
| TC-001-1-0013-UNE | API            |
| TC-001-1-0004-UNE | API            |
| TC-015-1-0007-UNE | API            |
| TC-003-1-0006-UNE | API            |
| TC-012-1-0001-UNE | API            |
| TC-007-1-0004-UNE | API            |
| TC-001-1-0029-UNE | API            |
| TC-001-1-0018-UNE | API            |
| TC-011-1-0004-UNE | API            |
| TC-001-1-0083-UNE | API            |
| TC-001-1-0003-UNE | API            |
| TC-015-1-0039-UNE | API            |
| TC-015-1-0012-UNE | API            |
| TC-015-1-0034-UNE | API            |
| TC-001-1-0078-UNE | API            |
| TC-001-1-0084-UNE | API            |
| TC-015-1-0029-UNE | API            |
| TC-001-1-0034-UNE | API            |
| TC-001-1-0068-UNE | API            |
| TC-001-1-0041-UNE | API            |
| TC-015-1-0045-UNE | API            |
| TC-001-1-0067-UNE | API            |
| TC-011-1-0009-UNE | API            |
| TC-015-1-0031-UNE | API            |
| TC-001-1-0021-UNE | API            |
| TC-015-1-0025-UNE | API            |
| TC-001-1-0012-UNE | API            |
| TC-001-2-0002-UNE | WEB            |
| TC-007-2-0008-UNE | WEB            |
| TC-015-2-0001-UNE | WEB            |
| TC-007-2-0004-UNE | WEB            |
| TC-003-2-0007-UNE | WEB            |
| TC-007-2-0006-UNE | WEB            |
| TC-007-2-0017-UNE | WEB            |
| TC-001-2-0003-UNE | WEB            |
| TC-007-2-0001-UNE | WEB            |
| TC-007-2-0003-UNE | WEB            |
| TC-001-2-0016-UNE | WEB            |
| TC-011-2-0004-UNE | WEB            |
| TC-003-2-0006-UNE | WEB            |
| TC-015-2-0009-UNE | WEB            |
| TC-003-2-0009-UNE | WEB            |
| TC-007-2-0011-UNE | WEB            |
| TC-003-2-0005-UNE | WEB            |
| TC-002-2-0004-UNE | WEB            |
| TC-001-2-0010-UNE | WEB            |
| TC-001-2-0017-UNE | WEB            |
| TC-015-2-0007-UNE | WEB            |
| TC-009-2-0002-UNE | WEB            |
| TC-006-2-0001-UNE | WEB            |
| TC-007-2-0014-UNE | WEB            |
| TC-014-2-0003-UNE | WEB            |
| TC-001-2-0007-UNE | WEB            |
| TC-015-2-0006-UNE | WEB            |
| TC-006-2-0003-UNE | WEB            |
| TC-001-2-0006-UNE | WEB            |
| TC-001-2-0009-UNE | WEB            |
| TC-008-2-0003-UNE | WEB            |
| TC-007-2-0012-UNE | WEB            |
| TC-007-2-0013-UNE | WEB            |
| TC-002-2-0001-UNE | WEB            |
| TC-013-2-0001-UNE | WEB            |
| TC-015-2-0011-UNE | WEB            |
| TC-015-2-0002-UNE | WEB            |
| TC-001-2-0012-UNE | WEB            |
| TC-013-2-0002-UNE | WEB            |
| TC-007-2-0010-UNE | WEB            |
| TC-007-2-0015-UNE | WEB            |
| TC-015-2-0004-UNE | WEB            |
| TC-011-2-0003-UNE | WEB            |
| TC-014-2-0002-UNE | WEB            |
| TC-012-2-0001-UNE | WEB            |
| TC-008-2-0001-UNE | WEB            |
| TC-015-2-0008-UNE | WEB            |
| TC-003-2-0004-UNE | WEB            |
| TC-005-2-0001-UNE | WEB            |
| TC-007-2-0005-UNE | WEB            |
| TC-003-2-0012-UNE | WEB            |
| TC-002-2-0002-UNE | WEB            |
| TC-001-2-0014-UNE | WEB            |
| TC-010-2-0001-UNE | WEB            |
| TC-011-2-0001-UNE | WEB            |
| TC-007-2-0007-UNE | WEB            |
| TC-011-2-0002-UNE | WEB            |
| TC-003-2-0010-UNE | WEB            |
| TC-001-2-0008-UNE | WEB            |
| TC-015-2-0013-UNE | WEB            |
| TC-003-2-0013-UNE | WEB            |
| TC-009-2-0001-UNE | WEB            |
| TC-008-2-0002-UNE | WEB            |
| TC-007-2-0019-UNE | WEB            |
| TC-007-2-0018-UNE | WEB            |
| TC-003-2-0003-UNE | WEB            |
| TC-003-2-0001-UNE | WEB            |
| TC-001-2-0013-UNE | WEB            |
| TC-007-2-0009-UNE | WEB            |
| TC-015-2-0010-UNE | WEB            |
| TC110846BERSA     | WEB            |
| TC-003-2-0011-UNE | WEB            |
| TC-001-2-0001-UNE | WEB            |
| TC-004-2-0001-UNE | WEB            |
| TC-007-2-0002-UNE | WEB            |
| TC-015-2-0003-UNE | WEB            |
| TC-001-2-0005-UNE | WEB            |
| TC-001-2-0015-UNE | WEB            |
| TC-007-2-0016-UNE | WEB            |
| TC-015-2-0012-UNE | WEB            |
| TC-003-2-0002-UNE | WEB            |
| TC-015-2-0005-UNE | WEB            |
| TC-001-2-0011-UNE | WEB            |
| TC-003-2-0008-UNE | WEB            |
| TC-002-2-0003-UNE | WEB            |
| TC-001-2-0004-UNE | WEB            |
| TC-006-2-0002-UNE | WEB            |

## Control del catálogo

- Total declarado y esperado: **265 casos**.
- Los IDs no deben repetirse.
- Cualquier alta, baja o cambio de tipo requiere una nueva decisión explícita del usuario y
  la actualización de este documento antes de migrar con el criterio nuevo.
- Los estados de avance —encontrado, excluido, portado, con revisión pendiente o validado—
  no se mantienen en este catálogo para no confundir alcance con avance. Decisión del usuario,
  21 de octubre de 2026: se registran en `docs/guides/migration-status.md`, que enumera los mismos
  IDs como punto de entrada operativo y se actualiza al cierre de cada lote. Reemplaza el informe
  de avance sólo por lote. Ante una diferencia de IDs o canal, prevalece este catálogo.
- El total portable se calcula como catalogados menos excluidos, casos sin fuente y fuentes
  ambiguas. Una parametrización puede producir varias ejecuciones, pero cubre un solo ID.
- El orden de las filas no define prioridad de migración; los lotes se agrupan por familia y
  reutilización real.
