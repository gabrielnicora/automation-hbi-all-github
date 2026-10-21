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

| ID            | Tipo declarado |
| ------------- | -------------- |
| TC29522BERSA  | API            |
| TC29525BERSA  | API            |
| TC29500BERSA  | API            |
| TC0138BERSA   | API            |
| TC29524BERSA  | API            |
| TC0485BERSA   | API            |
| TC0001BERSA   | API            |
| TC0480BERSA   | API            |
| TC29454BERSA  | API            |
| TC0159BERSA   | API            |
| TC0137BERSA   | API            |
| TC0139BERSA   | API            |
| TC29476BERSA  | API            |
| TC29474BERSA  | API            |
| TC0407BERSA   | API            |
| TC29526BERSA  | API            |
| TC12981BERSA  | API            |
| TC124910BERSA | API            |
| TC0074BERSA   | API            |
| TC29512BERSA  | API            |
| TC0344BERSA   | API            |
| TC0418BERSA   | API            |
| TC0098BERSA   | API            |
| TC18097BERSA  | API            |
| TC12930BERSA  | API            |
| TC0008BERSA   | API            |
| TC18094BERSA  | API            |
| TC29469BERSA  | API            |
| TC29482BERSA  | API            |
| TC128237BERSA | API            |
| TC29484BERSA  | API            |
| TC0111BERSA   | API            |
| TC0013BERSA   | API            |
| TC0468BERSA   | API            |
| TC29498BERSA  | API            |
| TC0027BERSA   | API            |
| TC29480BERSA  | API            |
| TC29523BERSA  | API            |
| TC29501BERSA  | API            |
| TC128238BERSA | API            |
| TC0014BERSA   | API            |
| TC0042BERSA   | API            |
| TC16039BERSA  | API            |
| TC0109BERSA   | API            |
| TC29464BERSA  | API            |
| TC29495BERSA  | API            |
| TC29491BERSA  | API            |
| TC16759BERSA  | API            |
| TC0130BERSA   | API            |
| TC0102BERSA   | API            |
| TC128239BERSA | API            |
| TC0069BERSA   | API            |
| TC29466BERSA  | API            |
| TC29463BERSA  | API            |
| TC16765BERSA  | API            |
| TC12973BERSA  | API            |
| TC29531BERSA  | API            |
| TC0429BERSA   | API            |
| TC29455BERSA  | API            |
| TC0411BERSA   | API            |
| TC29470BERSA  | API            |
| TC29452BERSA  | API            |
| TC12935BERSA  | API            |
| TC29533BERSA  | API            |
| TC12940BERSA  | API            |
| TC29483BERSA  | API            |
| TC0427BERSA   | API            |
| TC29527BERSA  | API            |
| TC0015BERSA   | API            |
| TC124912BERSA | API            |
| TC29532BERSA  | API            |
| TC12967BERSA  | API            |
| TC29477BERSA  | API            |
| TC29510BERSA  | API            |
| TC29506BERSA  | API            |
| TC0484BERSA   | API            |
| TC29460BERSA  | API            |
| TC29451BERSA  | API            |
| TC0467BERSA   | API            |
| TC29494BERSA  | API            |
| TC29514BERSA  | API            |
| TC0125BERSA   | API            |
| TC0009BERSA   | API            |
| TC29472BERSA  | API            |
| TC29499BERSA  | API            |
| TC29521BERSA  | API            |
| TC29509BERSA  | API            |
| TC25610BERSA  | API            |
| TC29534BERSA  | API            |
| TC0472BERSA   | API            |
| TC29453BERSA  | API            |
| TC0006BERSA   | API            |
| TC29516BERSA  | API            |
| TC29513BERSA  | API            |
| TC29502BERSA  | API            |
| TC124911BERSA | API            |
| TC18090BERSA  | API            |
| TC29504BERSA  | API            |
| TC0412BERSA   | API            |
| TC124909BERSA | API            |
| TC29528BERSA  | API            |
| TC29529BERSA  | API            |
| TC29467BERSA  | API            |
| TC0409BERSA   | API            |
| TC0474BERSA   | API            |
| TC0478BERSA   | API            |
| TC12939BERSA  | API            |
| TC29459BERSA  | API            |
| TC0145BERSA   | API            |
| TC29508BERSA  | API            |
| TC0473BERSA   | API            |
| TC0465BERSA   | API            |
| TC16768BERSA  | API            |
| TC12966BERSA  | API            |
| TC18093BERSA  | API            |
| TC0108BERSA   | API            |
| TC29518BERSA  | API            |
| TC0193BERSA   | API            |
| TC0149BERSA   | API            |
| TC29503BERSA  | API            |
| TC0192BERSA   | API            |
| TC18102BERSA  | API            |
| TC29496BERSA  | API            |
| TC0422BERSA   | API            |
| TC0164BERSA   | API            |
| TC0165BERSA   | API            |
| TC29475BERSA  | API            |
| TC29511BERSA  | API            |
| TC29450BERSA  | API            |
| TC29471BERSA  | API            |
| TC124913BERSA | API            |
| TC0129BERSA   | API            |
| TC29461BERSA  | API            |
| TC0463BERSA   | API            |
| TC29481BERSA  | API            |
| TC18101BERSA  | API            |
| TC18098BERSA  | API            |
| TC29468BERSA  | API            |
| TC29493BERSA  | API            |
| TC0479BERSA   | API            |
| TC0012BERSA   | API            |
| TC29497BERSA  | API            |
| TC0113BERSA   | API            |
| TC29458BERSA  | API            |
| TC29505BERSA  | API            |
| TC29517BERSA  | API            |
| TC128231BERSA | API            |
| TC0477BERSA   | API            |
| TC18104BERSA  | API            |
| TC0094BERSA   | API            |
| TC29507BERSA  | API            |
| TC29457BERSA  | API            |
| TC0229BERSA   | API            |
| TC0410BERSA   | API            |
| TC0194BERSA   | API            |
| TC0078BERSA   | API            |
| TC0115BERSA   | API            |
| TC29473BERSA  | API            |
| TC29462BERSA  | API            |
| TC0070BERSA   | API            |
| TC124907BERSA | API            |
| TC0223BERSA   | API            |
| TC18089BERSA  | API            |
| TC0424BERSA   | API            |
| TC12969BERSA  | API            |
| TC29530BERSA  | API            |
| TC124908BERSA | API            |
| TC12932BERSA  | API            |
| TC29478BERSA  | API            |
| TC29520BERSA  | API            |
| TC29492BERSA  | API            |
| TC18100BERSA  | API            |
| TC29519BERSA  | API            |
| TC12964BERSA  | API            |
| TC12938BERSA  | API            |
| TC29465BERSA  | API            |
| TC0483BERSA   | API            |
| TC29456BERSA  | API            |
| TC0040BERSA   | WEB            |
| TC0133BERSA   | WEB            |
| TC0146BERSA   | WEB            |
| TC0119BERSA   | WEB            |
| TC0157BERSA   | WEB            |
| TC0127BERSA   | WEB            |
| TC0189BERSA   | WEB            |
| TC0041BERSA   | WEB            |
| TC0044BERSA   | WEB            |
| TC0118BERSA   | WEB            |
| TC124865BERSA | WEB            |
| TC110847BERSA | WEB            |
| TC0155BERSA   | WEB            |
| TC29408BERSA  | WEB            |
| TC0162BERSA   | WEB            |
| TC0178BERSA   | WEB            |
| TC0153BERSA   | WEB            |
| TC128240BERSA | WEB            |
| TC0238BERSA   | WEB            |
| TC128241BERSA | WEB            |
| TC18099BERSA  | WEB            |
| TC0090BERSA   | WEB            |
| TC0196BERSA   | WEB            |
| TC0182BERSA   | WEB            |
| TC0161BERSA   | WEB            |
| TC0227BERSA   | WEB            |
| TC18096BERSA  | WEB            |
| TC12928BERSA  | WEB            |
| TC0226BERSA   | WEB            |
| TC0237BERSA   | WEB            |
| TC0221BERSA   | WEB            |
| TC0179BERSA   | WEB            |
| TC0181BERSA   | WEB            |
| TC0017BERSA   | WEB            |
| TC0004BERSA   | WEB            |
| TC128247BERSA | WEB            |
| TC0148BERSA   | WEB            |
| TC29411BERSA  | WEB            |
| TC0035BERSA   | WEB            |
| TC0173BERSA   | WEB            |
| TC0187BERSA   | WEB            |
| TC18088BERSA  | WEB            |
| TC110363BERSA | WEB            |
| TC0156BERSA   | WEB            |
| TC0052BERSA   | WEB            |
| TC0206BERSA   | WEB            |
| TC18103BERSA  | WEB            |
| TC0104BERSA   | WEB            |
| TC128243BERSA | WEB            |
| TC0126BERSA   | WEB            |
| TC0195BERSA   | WEB            |
| TC0048BERSA   | WEB            |
| TC29413BERSA  | WEB            |
| TC0075BERSA   | WEB            |
| TC0183BERSA   | WEB            |
| TC0128BERSA   | WEB            |
| TC0184BERSA   | WEB            |
| TC0163BERSA   | WEB            |
| TC0228BERSA   | WEB            |
| TC128249BERSA | WEB            |
| TC128242BERSA | WEB            |
| TC0086BERSA   | WEB            |
| TC0220BERSA   | WEB            |
| TC128244BERSA | WEB            |
| TC0190BERSA   | WEB            |
| TC0062BERSA   | WEB            |
| TC0032BERSA   | WEB            |
| TC29412BERSA  | WEB            |
| TC0134BERSA   | WEB            |
| TC128246BERSA | WEB            |
| TC110846BERSA | WEB            |
| TC0167BERSA   | WEB            |
| TC0026BERSA   | WEB            |
| TC0077BERSA   | WEB            |
| TC0045BERSA   | WEB            |
| TC16756BERSA  | WEB            |
| TC0225BERSA   | WEB            |
| TC29414BERSA  | WEB            |
| TC0188BERSA   | WEB            |
| TC128248BERSA | WEB            |
| TC0033BERSA   | WEB            |
| TC18092BERSA  | WEB            |
| TC29410BERSA  | WEB            |
| TC0158BERSA   | WEB            |
| TC0151BERSA   | WEB            |
| TC0222BERSA   | WEB            |
| TC0197BERSA   | WEB            |

## Control del catálogo

- Total declarado y esperado: **265 casos**.
- Los IDs no deben repetirse.
- Cualquier alta, baja o cambio de tipo requiere una nueva decisión explícita del usuario y
  la actualización de este documento antes de migrar con el criterio nuevo.
- Los estados de avance —encontrado, excluido, portado, con revisión pendiente o validado—
  no se mantienen en este catálogo; se informan por lote para no confundir alcance con avance.
- El total portable se calcula como catalogados menos excluidos, casos sin fuente y fuentes
  ambiguas. Una parametrización puede producir varias ejecuciones, pero cubre un solo ID.
- El orden de las filas no define prioridad de migración; los lotes se agrupan por familia y
  reutilización real.
