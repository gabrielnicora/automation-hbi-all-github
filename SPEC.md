# Especificación principal — Framework de automatización de Banco Entre Ríos

> Estado: autenticación e inversiones validadas en BERSA QA; perfil API pendiente  
> Versión: 3.2
> Alcance: Banco Entre Ríos (BERSA), ambiente QA, automatización API y UI con Playwright

## 1. Decisión de alcance

El framework automatizará exclusivamente Banco Entre Ríos (BERSA). Banco Santa Cruz,
Banco Santa Fe y la incorporación simultánea de bancos adicionales quedan fuera de alcance.

Esta decisión del 8 de septiembre de 2026 reemplaza el alcance BSC vigente hasta esa fecha.
Mantiene la arquitectura de banco único que había reemplazado la arquitectura multibanco
anterior. Las carpetas, configuraciones y tests de BSC o BSF no deben reintroducirse ni
condicionar capacidades nuevas.

Si en el futuro se solicita automatizar otro banco, se evaluará como un proyecto separado.
No se conservarán factories, contratos, perfiles u overrides preventivos para esa
posibilidad.

### Decisión de transición BERSA → UNE

- Estado: acordada para el futuro; alcance actual exclusivamente BERSA.
- Decisión: cuando UNE esté disponible, este proyecto migrará de BERSA a UNE,
  manteniendo una sola entidad activa.
- Motivo: UNE es el destino definitivo informado para este proyecto.
  La equivalencia se verificará antes de completar la migración.
- Reemplaza: la transición BSC → UNE; BERSA será el banco inicial antes de UNE. No
  habilita soporte simultáneo de bancos.
- Impacto futuro: revisar URLs API/UI, credenciales y datos, identificadores, lector
  de datos y documentación; validar los escenarios existentes contra UNE.

### Ambiente de ejecución UNE DEV

- Estado: aprobado por el usuario el 21 de octubre de 2026; ejecución pendiente de validar.
- Decisión: durante la transición conviven dos ambientes de ejecución de la misma suite,
  elegidos con `TEST_ENV`: `qa` (BERSA QA, valor por defecto) y `une-dev` (UNE DEV).
- `une-dev` usa las mismas identidades, IDs `TC<numero>BERSA` y datos de
  `test-data/private/bersa.json`. Sólo cambian las URLs base y dos valores comunes a todos los
  usuarios —contraseña y código de segundo factor— versionados en
  `test-data/private/une-dev.json`.
- URLs por defecto de `une-dev`: UI `https://onlinebanking.dev.bancoune.com` y API
  `https://apii.dev.bancoune.com`; `UI_BASE_URL` y `API_BASE_URL` las reemplazan.
- Motivo: ejecutar la suite contra UNE antes de completar el reemplazo de BERSA.
- Reemplaza: la exigencia de una sola entidad activa mientras dure la transición. No habilita
  perfiles bancarios, factories, datos o tests por banco: la diferencia se limita a la
  configuración de ambiente y a esos dos valores comunes.

La migración a BERSA cambia los identificadores a sufijo `BERSA` y el origen de
credenciales a `test-data/private/bersa.json`. Las URLs QA aprobadas el 8 de septiembre de
2026 son `https://hbtest01.bancoentrerios.com/startPage` para UI y
`https://apitest01.bancoentrerios.com` para API. Se usan como defaults y pueden reemplazarse
con `UI_BASE_URL` y `API_BASE_URL`. Esto reemplaza la decisión temporal que exigía ambas
variables mientras las URLs no estaban informadas. La equivalencia de pantallas, endpoints,
payloads y resultados de negocio debe validarse antes de declarar las capacidades validadas
en BERSA.

## 2. Propósito

Construir un framework de automatización claro y mantenible para validar Banco Entre Ríos
mediante API y UI. Una persona que conoce Playwright y el comportamiento funcional debe
poder agregar un test sin comprender factories, contratos de sustitución ni composición
multibanco.

Los objetivos principales son:

- mantener los tests cercanos al lenguaje funcional;
- usar Page Object Model como límite estándar de interacción UI;
- coordinar recorridos de varias páginas mediante workflows pequeños y opcionales;
- ubicar selectores junto al Page Object y endpoints junto al cliente API;
- limitar la configuración a decisiones reales de ambiente y ejecución;
- mantener datos productivos fuera del proyecto y tokens de sesión fuera de resultados;
- producir fallos que expliquen con claridad qué comportamiento no se cumplió.

## 3. Forma de trabajo: Specification-Driven Development

Cada capacidad nueva seguirá este orden:

1. Describir el comportamiento esperado con ejemplos observables.
2. Definir criterios de aceptación verificables.
3. Resolver datos, endpoints, payloads, resultados y restricciones de seguridad.
4. Elegir el diseño mínimo necesario.
5. Escribir el test.
6. Implementar el Page Object, cliente API o workflow mínimo que lo haga posible.
7. Refactorizar sólo cuando exista duplicación o complejidad demostrable.

Fuera de la migración acelerada definida a continuación, una funcionalidad no comienza a
implementarse mientras existan decisiones materiales abiertas. Ejemplos y snippets no
constituyen contratos automáticamente aprobados.

### Migración acelerada de casos legados

Decisión del usuario, 18 de septiembre de 2026: completar la migración de los casos existentes
tomando su código legado como referencia funcional. Los testigos actuales orientan la
arquitectura Playwright/TypeScript y la reutilización de componentes. El motivo es la necesidad
de migrar con rapidez y revisar las dudas después, sin un análisis ni aprobación caso por caso.
Esta decisión reemplaza, para este alcance, la obligación de detenerse ante ambigüedades y la
exigencia de una especificación individual aprobada antes de portar un caso existente.

- Conservar pasos, precondiciones, datos, efectos y assertions ejecutables del caso legado y
  sus métodos llamados. Una validación de URL, ticket o locator debe tener su equivalente.
- No consultar al usuario por dudas de interpretación: completar el portado fiel, registrar
  la duda en `docs/guides/ambiguities/` y continuar el lote. No sustituir una assertion por otra
  más débil ni agregar una expectativa inventada porque el testigo tenga otro alcance.
- Respetar las decisiones explícitas que ya reemplazaron el legado para un caso concreto
  (por ejemplo, registro TC0156BERSA). Una diferencia no resuelta con documentación se conserva
  como comportamiento legado provisional y se registra para revisión; no cambia silenciosamente
  los testigos ya migrados.
- Adaptar lenguaje, arquitectura, esperas y seguridad. No trasladar bucles internos de retry de
  requests o escenarios: los fallos se reintentan mediante Playwright. Reutilizar el polling y
  los reenvíos de desafío ya aprobados de segundo factor; no duplicarlos ni reintentar negocio.
- Seguros y turnos permanecen como testigos implementados aunque presenten incidencias externas
  de QA. La indisponibilidad del ambiente no detiene el portado ni autoriza quitar assertions.
  Las marcas de fallo esperado de esos casos no se propagan automáticamente a variantes.
- No exigir tablas de equivalencia, análisis previos exhaustivos ni un Markdown por cada caso
  sin dudas. Registrar sólo excepciones con caso/canal, ubicación legada y decisión aplicada;
  varias dudas pueden compartir un documento por lote.
- Si falta físicamente código, una dependencia o datos indispensables, portar todo lo disponible
  y registrar el faltante concreto; continuar con el resto. No fabricar valores, vaciar el test
  ni declarar completado lo que no pudo traducirse.
- Distinguir portado completado, revisión humana pendiente y ejecución QA pendiente/fallida.
  Una incidencia externa o duda documentada no convierte la migración en validación funcional.
- Aplicar las fronteras de responsabilidad de la sección 6.2.2 también al código generado
  por IA. Un testigo existente puede contener desvíos; reutilizar sus piezas compatibles
  sin copiar datos del caso ni expectativas de negocio dentro de un Page Object.
- Para la adaptación de un control, usar el legado y la evidencia UI disponible. Un ID
  no demuestra que el elemento sea un `<select>` nativo; un texto legado no demuestra que
  siga visible en QA. Si falta evidencia actual, identificar la adaptación pendiente sin
  inventar controles ni declarar validación funcional. La exploración usa la skill del proyecto.
- Distinguir dudas funcionales de defectos de implementación. Registrar una ambigüedad no
  reemplaza corregir un locator incompatible con la evidencia disponible ni ejecutar el caso.

Esta autorización cubre migrar comportamientos ya existentes, incluso no especificados todavía;
las capacidades nuevas siguen el proceso normal. La revisión humana posterior consolida las
decisiones en la especificación correspondiente. Guía operativa: `docs/guides/README.md`.

### Ubicación de las fuentes legadas

Decisión del usuario, 13 de octubre de 2026: las fuentes funcionales para la migración
son las copias locales incluidas en este repositorio, con rutas relativas a su raíz:

- API: `java/api/GP_Individuos_API/`.
- UI: `java/ui/GP_Migracion_Individuos/`.

Estado: aprobado. Motivo: permitir que Kiro consulte el legado dentro del mismo proyecto.
Reemplaza las referencias operativas a los repositorios externos y a sus ubicaciones
anteriores bajo `C:\Develop\`. Leer el caso, sus dependencias, recursos y datos desde estas
copias; no recurrir automáticamente a otro checkout o repositorio remoto. Si falta una
fuente indispensable, registrar su ruta concreta y aplicar la política de faltantes anterior.
Para UI, los casos fuente siguen siendo exclusivamente los métodos de `FrontTest.java`;
las otras clases se consultan sólo como dependencias de esos métodos.

`java/` conserva el legado como referencia de migración. La implementación migrada sigue
ubicándose en las capas Playwright/TypeScript definidas en la sección 6.

### Catálogo cerrado de casos migrables

Decisión del usuario, 19 de octubre de 2026: el alcance de la migración acelerada queda
limitado a los 265 IDs enumerados en `specs/migration/migratable-cases.md`: 178 declarados
como `API` y 87 como `WEB`. Ese documento es el catálogo canónico de elegibilidad y canal.

- No migrar casos ausentes del catálogo, aunque existan y sean ejecutables en el legado.
- La presencia en el catálogo no basta: excluir toda definición efectiva marcada como
  ignorada, deshabilitada, obsoleta o deprecada, incluidas `@Ignore`, `@Disabled` y marcas
  equivalentes.
- Para un ID declarado `API`, priorizar su fuente API aunque también exista en UI. Sólo cuando
  no exista ninguna definición API y sí exista un método UI activo válido en `FrontTest.java`,
  migrarlo como UI y registrar la inconsistencia de canal en `docs/guides/ambiguities/`.
- Una definición API existente pero excluida por sus marcas no habilita el fallback a UI.
- Para un ID declarado `WEB`, aplicar la regla vigente de fuente UI exclusiva en
  `FrontTest.java`; esta decisión no autoriza fallback de `WEB` a API.

Esta decisión precisa qué significa «casos existentes» dentro de la migración acelerada y
reemplaza cualquier interpretación de que todo caso BERSA encontrado en el legado integra el
alcance. El catálogo distingue alcance de avance: un ID listado no queda por ello encontrado,
portado ni validado. Esta decisión tampoco ordena retirar tests ya implementados fuera del
catálogo; se registran como inconsistencias preexistentes hasta decidir su permanencia.

Decisión del usuario, 21 de octubre de 2026: el avance por caso —elegibilidad resuelta, fuente,
lote, portado, ejecución QA y revisión humana— se registra en `docs/guides/migration-status.md`,
punto de entrada operativo de la migración. El catálogo conserva la autoridad sobre alcance y
canal declarado. Reemplaza el informe de avance únicamente por lote.

Para determinar si un caso está activo se evalúan su método de test y su clase. Un helper
deprecado no excluye automáticamente un test activo; las marcas efectivas del método o de su
clase sí lo excluyen. `WEB` es la denominación legada del canal UI.

La suite permanente contiene escenarios funcionales API y UI. Pueden existir
verificaciones temporales durante el desarrollo, pero no se mantendrá una arquitectura de
tests unitarios que aumente la superficie de autoría sin una necesidad aprobada.

### Registro de decisiones

Registrar las decisiones materiales en la especificación afectada, junto con su estado,
motivo, impacto y la decisión anterior que reemplazan, cuando corresponda. Mantener la
regla vigente inequívoca y marcar como reemplazado el texto histórico que la contradiga.
No crear un registro paralelo que duplique las especificaciones.

### Validación de cambios amplios

Antes y después de cambios que afecten varias capacidades, configuración o dependencias,
registrar los resultados de formato, typecheck y lint y comparar los diagnósticos concretos
(archivo, regla y mensaje), no sólo la cantidad de errores. Distinguir problemas
preexistentes de los introducidos e informar cualquier comprobación que no pudo ejecutarse.
Un problema preexistente no convierte una validación fallida en aprobada ni reduce la
Definition of Done. Completar las pruebas funcionales correspondientes al impacto;
typecheck y lint no sustituyen esa verificación. Los cambios documentales requieren
revisión de contenido y coherencia, sin ejecutar el banco por ese motivo.

## 4. Tecnología

- Playwright Test como runner y herramienta de automatización API/UI.
- TypeScript en modo estricto.
- Node.js como entorno de ejecución.
- pnpm para dependencias y scripts.
- APIs nativas de Playwright: `Page`, `Locator`, `APIRequestContext`, fixtures, reportes,
  screenshots y videos.

No se incorporarán Selenium, Cucumber, Screenplay ni frameworks adicionales salvo una
necesidad concreta aprobada. TypeScript debe expresar contratos de datos en las fronteras,
no producir tipos genéricos difíciles de seguir.

## 5. Principios de diseño

### KISS

- Preferir código directo, local y explícito.
- Evitar factories, registries, service locators y configuración dinámica.
- Un autor de tests no debe conocer el cableado interno de las fixtures.
- Un cambio localizado debe afectar un archivo localizado.

### YAGNI

- No abstraer bancos, canales o implementaciones futuras.
- No crear una interfaz con una sola implementación salvo una necesidad real de
  sustitución.
- No crear un workflow que sólo delega una llamada.
- No crear una capa `business` hasta que exista una regla de negocio reutilizable.

### DRY

- Eliminar duplicación de conocimiento, no toda repetición visual.
- Repetir temporalmente unas pocas líneas es preferible a introducir una abstracción
  prematura.

### SOLID

- Cada módulo tiene una responsabilidad reconocible.
- Los Page Objects conocen Playwright y el DOM, no decisiones del escenario.
- Los workflows coordinan acciones funcionales, no implementan locators.
- Los tests conservan las expectativas de negocio.
- Los clientes API contienen el protocolo HTTP de su capacidad.

## 6. Arquitectura por capas

La organización física principal será por responsabilidad técnica. Dentro de `pages/`,
`api/` y `tests/` se usarán subcarpetas funcionales para evitar directorios planos cuando
crezca el proyecto.

```text
tests ──► fixtures ──┬──► pages ──► Playwright UI
                     ├──► api ────► APIRequestContext
                     └──► workflows opcionales

config ───────► fixtures
test-data ────► fixtures
models ───────► pages / api / workflows
```

Estructura adoptada:

```text
automation-hbi-all/
├── AGENTS.md
├── .agents/
│   ├── source-of-truth.md
│   ├── docs/
│   └── skills/
├── SPEC.md
├── specs/
│   ├── api/
│   ├── authentication/
│   ├── investments/
│   ├── home/
│   ├── main-page/
│   ├── profile/
│   └── transfers/
├── src/
│   ├── pages/
│   │   ├── authentication/
│   │   │   └── login.page.ts
│   │   ├── investments/
│   │       ├── fund-risk.ts
│   │       └── investment-funds.page.ts
│   │   ├── registration/
│   │   │   └── identity-validation.page.ts
│   │   └── start-page/
│   │       └── start.page.ts
│   ├── api/
│   │   ├── reporting-api-request.ts
│   │   ├── authentication/
│   │   │   ├── login.api.ts
│   │   │   └── login-fingerprint.ts
│   │   ├── home/
│   │   │   └── terms-and-conditions.api.ts
│   │   ├── profile/
│   │   │   └── phone-change.api.ts
│   │   └── transfers/
│   │       └── own-account-transfer.api.ts
│   ├── models/
│   │   └── authenticated-session.ts
│   ├── fixtures/
│   │   ├── test-case.fixture.ts
│   │   ├── ui-test.ts
│   │   └── api-test.ts
│   ├── config/
│   │   ├── environment.ts
│   │   └── second-factor.ts
│   ├── test-data/
│   │   ├── login-credentials.ts
│   │   ├── own-account-transfer-data.ts
│   │   └── phone-change-data.ts
│   └── workflows/                    Se crea sólo ante un workflow real
├── tests/
│   ├── api/
│   │   ├── authentication/
│   │   ├── profile/
│   │   ├── home/
│   │   └── transfers/
│   └── ui/
│       ├── authentication/
│       ├── investments/
│       └── main-page/
└── test-data/private/
    ├── bersa.json
    ├── phone-change.json
    └── second-factor.json
```

`AGENTS.md` y `.agents/` pertenecen a la raíz del repositorio para que las instrucciones,
fuentes auxiliares y skills del proyecto se versionen y acompañen cada clon. No dependen de
la carpeta contenedora usada como workspace local.

No se crearán carpetas vacías ni archivos de plantilla para capacidades inexistentes. Por
eso `src/workflows/` forma parte de la convención, pero aparecerá físicamente recién cuando
una capacidad aprobada coordine múltiples páginas o decisiones significativas.

### Responsabilidades de las carpetas

| Carpeta              | Responsabilidad                                                                            |
| -------------------- | ------------------------------------------------------------------------------------------ |
| `src/pages/`         | Page Objects UI: locators, selectores, acciones, esperas y lectura de estado.              |
| `src/api/`           | Clientes API: endpoints, headers, payloads, polling aprobado y validación de respuestas.   |
| `src/models/`        | Modelos pequeños compartidos por más de una capa, sin lógica de Playwright.                |
| `src/workflows/`     | Coordinación opcional de varios Page Objects o decisiones funcionales reutilizables.       |
| `src/fixtures/`      | Composición y ciclo de vida de Page Objects, clientes API, contexto y datos del test.      |
| `src/config/`        | Valores y políticas de ambiente o ejecución; nunca selectores ni endpoints de capacidades. |
| `src/test-data/`     | Lectura, selección y validación de datos de prueba versionados para los casos.             |
| `tests/ui/`          | Escenarios UI y expectativas funcionales; no contiene selectores.                          |
| `tests/api/`         | Escenarios API y expectativas funcionales; no contiene endpoints ni payloads.              |
| `specs/`             | Comportamiento aprobado, criterios de aceptación, seguridad y decisiones abiertas.         |
| `test-data/private/` | Datos dummy QA/DEV versionados; el nombre de la ruta se conserva por compatibilidad.       |

### 6.1 Tests

Los tests expresan el escenario y sus resultados observables. Pueden contener:

- preparación del caso mediante fixtures;
- llamadas a Page Objects, clientes API o workflows con intención funcional;
- `test.step` cuando mejore el reporte;
- `expect` de negocio.

No contienen selectores, URLs, endpoints, payloads HTTP, credenciales ni lógica de polling.

Separar casos cuando cambie el recorrido funcional o la naturaleza del resultado esperado.
Parametrizar cuando varíen sólo datos y se mantengan el recorrido y la regla de validación.
Cada variante debe conservar identidad, datos y resultado individual en el reporte.
Evitar condiciones que elijan qué escenario probar según el estado encontrado; esto no
prohíbe decisiones funcionales explícitas y aprobadas dentro de un recorrido.

### 6.2 Page Objects

Page Object Model es la regla para la frontera UI. Cada Page Object:

- contiene sus locators y selectores;
- realiza clicks, escritura, selección y lectura de estado;
- espera estados observables de la página;
- puede verificar que la página o componente esté disponible;
- devuelve datos simples que el test o workflow pueda evaluar;
- produce errores claros cuando la pantalla no alcanza un estado operable.

Un Page Object no debe conocer factories, códigos de banco, variables de entorno ni
configuraciones globales de selectores. Tampoco debe contener reglas de negocio que
coordinen páginas no relacionadas.

Los selectores permanecerán privados y próximos al comportamiento que los usa. Un cambio
de selector debe afectar solamente al Page Object correspondiente.

### 6.2.1 Política de esperas y sincronización UI

Las esperas deben representar una condición observable, no una cantidad arbitraria de
tiempo. Quedan prohibidos `Thread.sleep`, `waitForTimeout`, `fixedWait` y cualquier helper
equivalente que duerma el hilo para compensar una carga incierta. También queda prohibido
usar `networkidle` como señal general de que una pantalla está lista.

La política para cada acción es:

1. El Page Object espera que el control sea operable mediante un locator de Playwright y
   una aserción automática (`toBeVisible`, `toBeEnabled` o equivalente).
2. Ejecuta la acción.
3. Espera el postestado observable que demuestra que la acción terminó: ruta, título,
   control siguiente, estado seleccionado, fila, contador o mensaje de negocio.

`toBeVisible()` sólo demuestra que un elemento visible existe; no demuestra que una
consulta de datos terminó ni que el estado funcional se actualizó. Para operaciones
asíncronas, el Page Object debe esperar la respuesta relevante preparada antes de la
acción cuando el endpoint sea conocido, y después confirmar el estado renderizado. Si el
estado se propaga eventualmente sin una respuesta utilizable, se permite `expect.poll`
con timeout e intervalo explícitos y acotados, únicamente cuando la especificación de la
capacidad lo apruebe. No se agregan reintentos indefinidos ni pausas para ocultar una
condición de carrera.

Cada capacidad debe definir cuál es su señal de disponibilidad y cuál es su postestado.
Si no existe una señal estable, la decisión debe resolverse en la especificación antes de
implementar el test. Los Page Objects contienen estas esperas; los tests sólo expresan
expectativas funcionales. Un cambio de pestaña, contador o lista debe esperar el dato
estable que identifica el registro, no un texto mutable como fecha u hora.

### 6.2.2 Frontera entre pantalla y escenario

Decisión del usuario, 28 de septiembre de 2026: precisar la separación entre Page Objects,
datos, escenarios y workflows. Estado: criterio aprobado; adecuación del código existente
pendiente por capacidad. Motivo: evitar que la migración concentre casos completos en las
páginas y facilitar la autoría y el diagnóstico para personas nuevas en Playwright.
Reemplaza la interpretación de que un método de Page Object puede fijar los datos de un
caso y decidir su resultado esperado por contener toda la interacción UI.

La base sigue siendo Playwright Test, POM, clientes API por capacidad, fixtures y workflows
opcionales. La precisión cambia la distribución de responsabilidades, con estas reglas:

- El Page Object representa una pantalla o una parte coherente de ella. Puede agrupar acciones
  como `completeForm(data)`; su tamaño no se juzga por cantidad de líneas o métodos. Menús,
  formularios e iframes reutilizados pueden encapsularse como componentes bajo `src/pages/<area>/`
  cuando haya reutilización demostrable, sin crear una jerarquía de clases base.
- Las cuentas, importes, descripciones e IDs específicos del caso no se fijan en el Page Object.
  Se reciben como parámetros desde los datos entregados por las fixtures o desde el escenario.
  Credenciales y cuentas se cargan de los datos versionados; un valor funcional pequeño que
  define una variante, como importe `0`, puede ser explícito en el test. Los textos de controles
  necesarios para interactuar con la UI permanecen en el Page Object.
- El test elige la variante, los pasos funcionales y las expectativas de negocio, incluidas
  las comprobaciones intermedias que condicionan una confirmación. La página verifica
  operabilidad y sincronización: formulario disponible, opción aplicada o siguiente pantalla
  renderizada. El resultado financiero, los datos del ticket o el mensaje exacto de rechazo
  se contrastan con lo esperado en el test. La misma distinción aplica a los workflows.
- Los métodos de lectura esperan el estado renderizado pertinente antes de devolver valores
  simples. Localizan el contenedor o registro por su identidad y leen lo observado, sin buscar
  únicamente el valor esperado ni convertirlo anticipadamente en un resultado exitoso. Separar
  la comparación del test no autoriza lecturas prematuras que pierdan la sincronización UI.
- El Page Object permite ingresar los datos negativos previstos por el escenario. No rechaza
  ni corrige un monto `0` mediante una regla bancaria propia antes de que la aplicación lo reciba.
  La expectativa de éxito o rechazo se define antes de ejecutar; se conservan las decisiones
  funcionales explícitas aprobadas para el recorrido.
- Un método como `purchaseZeroDollars()` que selecciona datos fijos y exige un error específico
  representa un escenario; se descompone en acciones y lectura de la pantalla. Un workflow
  sólo aporta valor cuando cumple la coordinación de la sección 6.3. Los modelos describen
  datos; una capa de dominio requiere la regla reutilizable demostrada de la sección 6.5.

Aplicar estos criterios de forma incremental, preservando assertions, datos y efectos ya
aprobados. Al refactorizar una capacidad con especificación, actualizar su diseño antes de
la implementación. Para casos legados sin especificación individual sigue aplicando la
excepción de la sección 3. Documentar este criterio no declara refactorizados ni validados los casos.
La política de identidades, efectos y reintentos de las secciones 8 y 9 sigue gobernando la
ejecución bancaria; aislar el navegador no aísla el estado de una cuenta en el servidor.

### 6.3 Workflows

Un workflow representa una operación funcional que coordina dos o más Page Objects o una
secuencia con decisiones significativas. Por ejemplo, `transfer()` puede coordinar la
carga de datos, confirmación y comprobante.

Los workflows son opcionales. No se crea uno para login o para una capacidad contenida en
una única página si sólo delegaría métodos. No contienen locators ni importan configuración
de ambiente.

Una secuencia breve de llamadas puede permanecer en el test. Extraer un workflow cuando
encapsule coordinación significativa entre Page Objects o clientes, o decisiones funcionales
reutilizables. La cantidad de clases actuales no determina la necesidad: haber concentrado
formulario, desafío y comprobante en una sola clase no demuestra que sea una sola pantalla.
El workflow recibe datos y devuelve resultados observados; las expectativas del escenario
permanecen en el test. Se implementa con TypeScript directo, sin una interfaz o factory obligatoria.

### 6.4 Clientes API

Cada capacidad API posee un cliente o módulo con nombre funcional. El cliente conoce:

- endpoints y métodos HTTP;
- headers y payloads;
- validación de HTTP, JSON y códigos de negocio;
- polling acotado cuando esté aprobado;
- protección de tokens y sesiones generados en ejecución.

Los endpoints viven junto al cliente que los utiliza. La URL base de QA pertenece a la
configuración de ambiente.

No se crea un gateway o interfaz cuando sólo existe una implementación. Los modelos
compartidos entre API y UI, como una sesión autenticada, pueden conservarse como tipos
simples sin imponer polimorfismo.

### 6.5 Business o dominio

La capa de negocio no es obligatoria. Se crea únicamente cuando exista una regla funcional
reutilizada por más de un escenario o canal y que no pertenezca a un Page Object ni al
protocolo API.

Una clase que sólo renombra o delega métodos no constituye una capa de negocio válida.

### 6.6 Fixtures

Las fixtures son el único punto de composición técnica visible para Playwright. Crean y
liberan `Page`, `APIRequestContext`, Page Objects y clientes API.

El test importa `test` y `expect` desde `src/fixtures/ui-test.ts` o
`src/fixtures/api-test.ts`. El autor del escenario no necesita conocer cómo se construyen
las dependencias.

Se prefieren dos entradas pequeñas —API y UI— antes que una fixture universal. Una nueva
capacidad puede requerir una declaración explícita en la fixture, pero no modificar
factories, perfiles bancarios ni contratos globales.

### 6.7 Configuración

La configuración contiene únicamente valores que cambian por ambiente o ejecución:

- ambiente de ejecución (`TEST_ENV=qa|une-dev`, sección 1);
- URL base UI y API de cada ambiente;
- browser, workers y timeouts globales aprobados;
- mecanismo de segundo factor;
- rutas de datos de prueba versionados.

No contiene selectores, textos de botones, endpoints, estructuras de payload ni árboles de
capacidades. Se valida al comenzar la ejecución.

## 7. Regla de dependencias

- `tests` usa las entradas públicas de `fixtures` y modelos semánticos puntuales.
- `fixtures` compone `config`, `test-data`, Page Objects, clientes API y workflows.
- un workflow puede depender de Page Objects, clientes API y modelos;
- un Page Object puede depender de `Page`, `Locator` y `models`;
- un cliente API puede depender de `APIRequestContext`, `config`, `test-data` y `models`;
- `models` no depende de Playwright, fixtures, pages ni clientes API;
- `config` y `test-data` no dependen de UI, API ni tests;
- las dependencias circulares no están permitidas.

No existen ni se permiten dependencias hacia `banks/shared`, `BankConfig`,
`getBankConfig` o `createBankConfig`.

## 8. Datos de prueba y seguridad de ejecución

El origen de credenciales es `test-data/private/bersa.json`. El documento contiene el objeto
`testCases`, indexado directamente por cada `testCaseId` BERSA. Cada registro conserva `tcName`
con el mismo valor de su clave, y el lector valida esa coincidencia antes de devolverlo. Esta
decisión reemplaza el export legado indexado por el texto de una consulta SQL y evita recorridos
lineales para localizar un caso.

Con `TEST_ENV=une-dev`, la contraseña de todos los usuarios proviene de
`test-data/private/une-dev.json`; el usuario sigue proviniendo del caso.

El sufijo `BERSA` se conserva por trazabilidad, pero no selecciona un banco ni una factory.
Un sufijo diferente debe considerarse inválido para este proyecto.

El 8 de septiembre de 2026 se aprobó versionar `test-data/private/` para distribuir una
versión ejecutable con los datos necesarios. Esta decisión reemplaza la regla anterior que
mantenía toda la carpeta ignorada por Git.

Los usuarios, contraseñas, teléfonos, master codes y códigos almacenados son datos dummy
creados por el equipo, válidos sólo en QA o, si se habilita en el futuro, DEV. No son datos
productivos ni se clasifican como información confidencial. Nunca se incorporarán a estos
archivos credenciales, datos personales o valores provenientes de producción.

Por higiene de reportes, los tests y errores no repiten contraseñas ni códigos. Los access
tokens, exchange tokens, headers Bearer, cookies y cuerpos completos de autenticación se
generan en ejecución y no se imprimen ni adjuntan.

Los usuarios y datos funcionales QA/DEV son dummy y pueden mostrarse completos para
diagnóstico. Todo test que carga credenciales agrega el usuario como anotación de
Playwright. Los datos de sesión y autenticación indicados arriba continúan excluidos.

## 9. Evidencias, paralelismo y retries

- Los tests UI conservan video y screenshot únicamente cuando fallan.
- Los tests UI detectan transversalmente la navegación a `/error` con el mensaje `¡Ups!`. Si
  aparece, el intento falla y adjunta al reporte el mensaje, la URL y los identificadores de
  incidencia disponibles; el detalle de extracción se define en
  `specs/api/failure-diagnostics.plan.md`.
- Los traces permanecen desactivados hasta una decisión distinta.
- Los clientes API conservan cada intercambio en su propio `test.step`, con método, endpoint,
  caller funcional y un adjunto request/response sanitizado cuyo nombre incluye secuencia y
  endpoint. Si el test falla, además adjuntan al reporte HTML la secuencia completa del intento.
  La evidencia incluye datos funcionales e IDs de transacción completos, pero excluye
  credenciales, tokens, cookies y códigos. Base64 y fingerprints grandes se reemplazan por su
  longitud conforme a `specs/api/failure-diagnostics.plan.md`.
- `PW_WORKERS` controla el paralelismo de archivos y utiliza `1` por defecto.
- `fullyParallel` permanece desactivado mientras existan identidades compartidas.
- Dos escenarios que modifican el mismo usuario no se ejecutan simultáneamente.
- Los logins API y UI que reutilizan `TC0012BERSA` se ejecutan secuencialmente.
- Sólo se aplica polling a consultas de estado expresamente aprobadas.
- Se permite reintentar escenarios fallidos completos, incluido el cambio de teléfono,
  mediante el runner y con los intentos visibles en el reporte. Esto reemplaza la
  interpretación de que una mutación impide reintentar el escenario.
- `PW_RETRIES` define los reintentos del escenario: `1` por defecto en local y CI,
  `0` para desactivarlos. Debe ser un entero no negativo; un valor inválido falla al
  cargar la configuración. Un reintento implica hasta dos intentos totales.
- Dentro de un intento, las requests de mutación no se repiten automáticamente ante
  errores. El reenvío de SMS tras agotar la espera de un código es un recorrido aprobado
  de segundo factor, detallado en la especificación de perfil.

La validación histórica de BSC del 2 de septiembre de 2026 mostró que dos workers podían
solapar sesiones: una respuesta UI no incluyó el token esperado y el login previo a perfil
recibió `COR002E`. No se asignó significado ni se aceptó ese código. Con un worker, los
cuatro escenarios BSC finalizaron correctamente. Esa evidencia no valida BERSA, pero se
conserva un worker como política segura hasta probar aislamiento de datos en el banco activo.

### Regresión en CI por shards

Decisión del usuario, 21 de octubre de 2026: `.gitlab-ci.yml` ejecuta la regresión en tres
shards de Playwright encadenados (`shard_1` → `shard_2` → `shard_3`), con un worker cada uno,
y un job final que une sus reportes blob en el HTML y el JUnit habituales mediante
`merge.config.ts`. Estado: aprobado. Motivo: acotar la duración de cada job y conservar
evidencia parcial. Reemplaza el job manual único `ui:login:bersa`.

- Los shards no se ejecutan en simultáneo; la división no agrega paralelismo y conserva las
  reglas de identidades compartidas de esta sección.
- Decisión del usuario, 21 de octubre de 2026: un único pipeline con los mismos jobs en todo
  modo, en este orden: `install_deps`, `quality`, `shard_1` → `shard_2` → `shard_3` y
  `merge_reports`. Reemplaza el pipeline hijo de regresión y `RUN_REGRESSION`.
- En push y merge request todos los jobs son manuales y no bloquean el pipeline. En schedules
  y pipelines ad hoc, `REGRESSION_MODE=manual|auto` (por defecto `manual`) elige entre ese
  modo manual y la ejecución encadenada.
- En modo manual, `merge_reports` puede ejecutarse con al menos un shard corrido.
- En modo automático, `quality` corre si `install_deps` termina bien y la regresión si
  `quality` termina bien. Después, los shards corren en serie sin depender del resultado del
  anterior y `merge_reports` corre al final.
- `TEST_PATH` y `TEST_FILTER` (`--grep`) acotan la selección.
- Cada shard admite fallos para que la cadena y el reporte final se completen; el resultado
  de los tests se lee en el reporte unificado.

### Selección smoke

Al necesitar una ejecución breve de funcionalidades críticas, definir una selección smoke
de tests existentes. Antes de etiquetar casos o agregar comandos, acordar su objetivo,
casos incluidos, datos, duración esperada y efectos sobre el estado del usuario en la
especificación correspondiente. No duplicar tests ni considerar smoke equivalente a una
regresión completa. Por ahora no hay una selección smoke definida por esta política.

### Comprobación del entorno sin acceso al banco

Al preparar CI o una nueva máquina, evaluar una comprobación pequeña de dependencias,
carga de configuración y arranque del navegador, sin VPN, credenciales ni llamadas al
banco. Separarla de los escenarios funcionales y definir su alcance antes de implementarla.
Su éxito verifica el entorno local de ejecución, no la disponibilidad ni el comportamiento
del banco. Esta política no incorpora scripts, jobs ni una suite permanente adicional.

## 10. Convenciones

- Archivos y carpetas: `kebab-case`.
- Variables y funciones: `camelCase`.
- Clases, tipos e interfaces: `PascalCase`.
- Tests: `<behavior-name>.<channel>.spec.ts`.
- Cada título comienza con `TC<numero>BERSA`.
- El mismo caso funcional conserva su ID entre API y UI; la identidad del escenario
  se distingue por caso y canal. `testCaseId` también selecciona los datos de prueba.
- Ambos logins `TC0012BERSA` pertenecen a la selección `@regression` por su importancia.
  Esta etiqueta no implica pertenencia a smoke ni una vinculación con Xray.
- `TC0139BERSA` pertenece a `@critical`; la etiqueta expresa criticidad funcional y no
  define una selección smoke ni una vinculación con Xray.
- Un archivo agrupa casos del mismo comportamiento y canal.
- No usar `any` salvo una frontera externa justificada y validada inmediatamente.
- No usar estado global mutable.
- Evitar nombres arquitectónicos genéricos como `manager`, `service`, `helper`, `utils`,
  `base`, `shared`, `factory` o `gateway` cuando exista un nombre funcional más claro.

## 11. Prueba decisiva de simplicidad

La arquitectura debe seguir cumpliendo:

- agregar un caso de una capacidad existente requiere modificar sólo el test y sus datos;
- desde un test se llega al workflow, Page Object o cliente API en uno o dos saltos;
- cambiar un selector afecta un solo Page Object;
- cambiar un endpoint afecta un solo cliente API;
- una capacidad UI nueva no requiere contracts, gateways, factories ni configuración
  global de selectores;
- un workflow se crea únicamente cuando coordina más de una página o decisión;
- un QA Automation Jr puede seguir un escenario sin comprender composición multibanco;
- los tests no conocen el armado interno de las fixtures;
- la documentación incluye un ejemplo mínimo completo para UI y otro para API.
- las esperas UI se basan en estados observables y no contienen sleeps fijos, `networkidle`
  ni reintentos indefinidos.
- los Page Objects reciben los valores del escenario y exponen acciones y resultados,
  siguiendo la sección 6.2.2; no encapsulan un caso con datos y expectativas fijos.
- el test permite identificar las expectativas de negocio sin abrir un Page Object o workflow.

## 12. Resultado de la migración

La adaptación estructural a BERSA quedó completada; la validación funcional permanece
pendiente:

| Área                   | Resultado                                                                  |
| ---------------------- | -------------------------------------------------------------------------- |
| Configuración y datos  | BERSA-only bajo `src/config/` y `src/test-data/`                           |
| Autenticación UI/API   | Ubicada por capa y validada en BERSA QA                                    |
| Inversiones UI         | Ubicada en `pages/investments` y validada en BERSA QA                      |
| Perfil API             | Ubicado en `api/profile`; validación BERSA pendiente                       |
| Transferencias API     | Caso testigo entre cuentas propias implementado y validado en BERSA QA     |
| Pantalla principal API | Términos y condiciones generales implementados y validados en BERSA QA     |
| Pantalla principal UI  | Registro desde el enlace público implementado; validación manual pendiente |
| Mis Productos UI       | Redirección de baja y arrepentimiento implementada y validada en BERSA QA  |
| Modelos compartidos    | Ubicados explícitamente bajo `src/models/`                                 |
| Fixtures               | Entradas públicas bajo `src/fixtures/`                                     |
| Tests y scripts BSF    | Retirados del alcance activo                                               |
| Capas legacy           | `banks/`, `contracts/`, `support/` y `test-setup/` retiradas               |

## 13. Capacidades vigentes

El alcance funcional vigente es:

- login UI BERSA con captura segura del Bearer token;
- login API BERSA mediante protocolo multi-step;
- cambio de teléfono BERSA por API con segundo factor y verificación de persistencia;
- transferencia inmediata BERSA entre cuentas propias por API con recuperación de ticket;
- consulta anónima BERSA de términos y condiciones generales de Home Banking por API;
- acceso BERSA al registro desde el enlace `¿No sos cliente aún? Registrarme` mediante UI;
- filtrado BERSA de fondos comunes de inversión por riesgo moderado mediante UI;
- acceso BERSA a la página pública de baja y arrepentimiento mediante UI.
- pago total BERSA de tarjeta de crédito Mastercard propia mediante UI, pendiente de validación
  en QA.

Cada capacidad mantiene su especificación bajo `specs/`.

## 14. Fuera de alcance

- Banco Santa Cruz, Banco Santa Fe u otros bancos.
- Arquitectura extensible por plugins, perfiles u overrides bancarios.
- Inyección de sesión API en UI sin una especificación aprobada.
- Producción o pruebas destructivas no autorizadas.
- Paralelismo sobre identidades compartidas sin reserva de datos.
- Abstracciones creadas únicamente para una posibilidad futura.

## 15. Definition of Done

Un incremento está terminado cuando:

Para la migración acelerada, el portado puede entregarse con revisión humana o ejecución QA
pendientes, conforme a la sección 3. Informar esos estados por separado. Los criterios siguientes
siguen siendo necesarios para declarar la capacidad aprobada y funcionalmente validada:

- la especificación correspondiente está aprobada y actualizada;
- el comportamiento BERSA posee criterios de aceptación verificables;
- el código respeta POM, las dependencias y la organización por capas técnicas;
- no introduce factories, contratos o capas sin un uso demostrado;
- no incorpora datos productivos ni expone tokens o sesiones generados durante la ejecución;
- formato, lint, typecheck y pruebas aplicables finalizan correctamente;
- la solución puede comprenderse desde el test sin explicaciones externas.

## 16. Decisiones abiertas

Queda pendiente validar en BERSA el protocolo mutante de cambio de teléfono y resolver las
decisiones técnicas detalladas en las especificaciones de perfil e inversiones. Las
capacidades futuras deberán resolver en su propia especificación endpoints, datos,
resultados, limpieza, retries y restricciones de seguridad antes de implementarse.
