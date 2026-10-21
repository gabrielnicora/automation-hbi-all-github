# Guía de migración masiva de casos legados

## Instrucción operativa para Kiro

Completar el portado de los casos existentes a Playwright/TypeScript. Usar el código legado
ejecutable para determinar qué hace y qué valida cada caso, y los testigos actuales para
organizar tests, fixtures, datos, clientes API y Page Objects. Conservar las validaciones
legadas aunque el ambiente falle. Registrar dudas para revisión humana posterior y continuar
la migración sin pedir aclaración ni aprobación por cada caso.

Antes de leer o portar un caso, comprobar su elegibilidad y canal en el
[catálogo canónico de casos migrables](../../specs/migration/migratable-cases.md). El catálogo
es cerrado: no migrar IDs ausentes ni definiciones ignoradas, deshabilitadas, obsoletas o
deprecadas. Para los casos declarados API, aplicar allí la precedencia API y la excepción
documentada cuando la única fuente existente sea un método UI activo de `FrontTest.java`.
`WEB` equivale a UI y se implementa bajo `tests/ui/`.

La decisión está aprobada en SPEC.md, sección 3, y alineada con AGENTS.md. Una familia no
autoriza copiar moneda, payload, datos, assertions ni marcas de fallo esperado de su testigo
a todos los demás casos. Respetar las decisiones explícitas que ya reemplazaron el legado
para un caso concreto.

### Ubicación del legado dentro del repositorio

Usar las copias locales aprobadas en SPEC.md, sección 3. Todas estas rutas son relativas
a la raíz de `automation-hbi-all/`:

| Canal | Raíz del proyecto legado           | Casos BERSA                                                                       |
| ----- | ---------------------------------- | --------------------------------------------------------------------------------- |
| API   | `java/api/GP_Individuos_API/`      | `java/api/GP_Individuos_API/src/test/java/EntreRios/`                             |
| UI    | `java/ui/GP_Migracion_Individuos/` | `java/ui/GP_Migracion_Individuos/src/test/java/GP/EntreRios/front/FrontTest.java` |

Seguir los métodos llamados, recursos, requests y datos dentro de la raíz legada del canal.
No usar automáticamente checkouts externos ni consultar repositorios remotos para sustituir estas
copias. Si falta código o una dependencia indispensable, registrar la ruta local faltante,
portar lo disponible y continuar el lote conforme a SPEC.md. El resultado migrado se
implementa en las capas Playwright/TypeScript actuales.

### Fuente legacy para casos UI

Para migrar UI, los únicos casos fuente son los métodos definidos en los `FrontTest.java`
legados, aunque existan métodos con el mismo ID en otros archivos o carpetas. Esos métodos
homónimos fuera de `FrontTest` están deprecados y no se migran. Consultar otras clases legacy
únicamente para seguir dependencias/helpers llamados por un método de `FrontTest`; no usarlas
para agregar recorridos ni assertions al caso UI. Si un ID sólo aparece fuera de `FrontTest`,
no constituye un caso UI migrable bajo esta regla.

## Lectura dirigida

1. AGENTS.md, .agents/source-of-truth.md y SPEC.md.
2. [Catálogo de casos migrables](../../specs/migration/migratable-cases.md); detener el caso
   individual si no es elegible.
3. [Principios](migration-principles.md) y [proceso](migration-process.md).
4. [Familia correspondiente](families/README.md), especificaciones disponibles y código del
   testigo necesario. No cargar todas las familias para migrar un solo comportamiento.
5. Código legado del caso, métodos llamados y datos que realmente usa, desde las rutas
   locales del canal indicadas arriba.
6. [Checklist](validation-checklist.md) y [registro de dudas](ambiguities/README.md).

[Análisis de fuentes](source-analysis.md) explica cómo leer el legado durante el portado.
La ausencia de una especificación individual no bloquea migrar un caso ya existente.

## Uso de estas guías

Con estas guías, legado y código actual se puede orientar el portado. Las guías no contienen
todos los payloads, selectores o métodos del framework; una prueba que use sólo Markdown
debe registrar esas limitaciones, sin completar contratos por imaginación.

Las reglas comunes de esta carpeta aplican a todas las familias. Sus flujos describen el
testigo específico; las variantes conservan su propio comportamiento legado.

## Entrega por lote

- Código migrado y datos necesarios, integrados en las fixtures existentes.
- Comprobaciones estáticas y resultados de ejecución disponibles.
- Resumen breve de casos portados, pendientes de ejecución y faltantes concretos.
- Resumen del inventario del catálogo: elegibles, excluidos, sin fuente y desvíos detectados.
- Dudas o diferencias registradas sólo cuando existan, agrupables por lote.

No exigir tablas de equivalencia ni un documento por cada caso. No trasladar retries internos:
usar el runner Playwright y reutilizar la política existente de segundo factor.
