# Automation HBI — Banco Entre Ríos

Framework de automatización API y UI de Banco Entre Ríos (BERSA) con Playwright Test y
TypeScript.

> Estado: arquitectura simplificada implementada y adaptada a BERSA. Login API, login UI,
> inversiones y el caso testigo de transferencias API están validados en QA; perfil API
> permanece pendiente.

El desarrollo sigue Specification-Driven Development. `SPEC.md` define el alcance, la
arquitectura y sus responsabilidades. Cada capacidad posee además una
especificación verificable bajo `specs/`.

## Alcance

Banco Entre Ríos (BERSA) es el único banco soportado antes de la futura migración a UNE.
Banco Santa Cruz, Banco Santa Fe y el soporte simultáneo de bancos quedan fuera de alcance.

Las capacidades vigentes son:

- login UI BERSA con captura segura del Bearer token;
- login API BERSA mediante el protocolo multi-step;
- cambio de teléfono BERSA por API con segundo factor;
- transferencia inmediata BERSA entre cuentas propias por API con recuperación de ticket;
- filtro UI de fondos comunes de inversión por riesgo moderado.

## Arquitectura por capas

La organización principal es por responsabilidad técnica y usa subcarpetas funcionales
dentro de `pages/`, `api/` y `tests/`:

```text
src/
├── pages/
│   ├── authentication/   Page Objects de autenticación
│   └── investments/      Page Objects y modelos UI de inversiones
├── api/
│   ├── authentication/   Protocolo API de login
│   ├── profile/          Operaciones API de perfil
│   └── transfers/        Operaciones API de transferencias
├── models/               Modelos pequeños compartidos entre capas
├── fixtures/             Composición pública para los tests
├── config/               Ambiente y políticas de ejecución
├── test-data/            Lectura y validación de datos de prueba
└── workflows/            Se crea sólo cuando exista coordinación real

tests/
├── api/                  Escenarios y expectativas API
└── ui/                   Escenarios y expectativas UI
```

Responsabilidades:

| Carpeta          | Responsabilidad                                                      |
| ---------------- | -------------------------------------------------------------------- |
| `src/pages/`     | Selectores, locators, acciones y estados observables de UI.          |
| `src/api/`       | Endpoints, headers, payloads y validación de respuestas.             |
| `src/models/`    | Datos compartidos entre capas sin dependencias de Playwright.        |
| `src/workflows/` | Coordinación opcional de varias páginas o decisiones funcionales.    |
| `src/fixtures/`  | Creación y ciclo de vida de capacidades entregadas a los tests.      |
| `src/config/`    | URLs, ambiente y políticas transversales de ejecución.               |
| `src/test-data/` | Lectura y validación segura de datos de casos.                       |
| `tests/`         | Escenarios y `expect` funcionales; no contiene detalles técnicos.    |
| `specs/`         | Contrato funcional aprobado y decisiones abiertas de cada capacidad. |

No se crean carpetas vacías por anticipado. `workflows/` aparecerá físicamente con el
primer recorrido que coordine múltiples Page Objects o decisiones significativas.

## Datos de prueba versionados

Las credenciales de BERSA se leen desde:

```text
test-data/private/bersa.json
```

Los datos de cambio de teléfono y segundo factor permanecen en sus archivos específicos.
`test-data/private/` se versiona para que la versión compartida incluya todo lo necesario
para ejecutarla. El nombre de la carpeta se conserva por compatibilidad.

Los valores son datos dummy creados por el equipo para QA y una eventual ejecución en DEV.
No son datos productivos ni información confidencial. No deben copiarse a esta carpeta
credenciales o datos personales provenientes de producción.

Cada test declara un identificador como `TC0012BERSA`. El sufijo BERSA se conserva por
trazabilidad, pero ya no seleccionará un banco ni una factory.

Por claridad de los resultados, passwords y códigos dummy no se repiten en errores. Los
tokens y cookies generados durante la ejecución no se imprimen ni se adjuntan al reporte.

## Ejecución

Las URLs QA aprobadas de BERSA se usan como valores predeterminados. `UI_BASE_URL` y
`API_BASE_URL` permiten reemplazarlas para una ejecución puntual; el framework no reutiliza
las URLs anteriores de BSC.

Validaciones estáticas:

```powershell
pnpm run check
```

Todos los tests:

```powershell
pnpm test
```

Todos los tests UI:

```powershell
pnpm run test:ui
```

Login UI:

```powershell
pnpm run test:ui:login
```

Fondos UI:

```powershell
pnpm run test:ui:investments
```

Todos los tests API:

```powershell
pnpm run test:api
```

Las esperas UI se basan en estados observables y en el auto-wait de Playwright. No se usan
`Thread.sleep`, `waitForTimeout`, `fixedWait` ni `networkidle` como esperas generales. Cada
Page Object espera el postestado funcional de sus acciones; los casos con propagación
eventual sólo usan polling acotado cuando su especificación lo aprueba.

Login API:

```powershell
pnpm run test:api:login
```

Cambio de teléfono API:

```powershell
pnpm run test:api:profile
```

Transferencias API:

```powershell
pnpm run test:api:transfers
```

Los videos y screenshots se conservan únicamente cuando un test UI falla. Los resultados
se guardan bajo `test-results/`, fuera del control de versiones.

Cuando un test API falla, el reporte HTML adjunta un JSON con la secuencia de requests y
responses del intento: método, path, headers de aplicación, bodies, duración y status. Los
datos funcionales dummy y los IDs de transacción permanecen visibles; passwords, tokens,
cookies y códigos se excluyen, y los campos Base64 grandes se reemplazan por su longitud.
Todo test API o UI que carga credenciales muestra el usuario dummy como anotación.

La suite usa un worker por defecto porque login API y UI reutilizan la identidad
`TC0012BERSA`. `PW_WORKERS` sólo debe aumentarse cuando los escenarios seleccionados tengan
identidades independientes y no modifiquen estado compartido.

Los escenarios fallidos tienen un reintento por defecto en local y CI (hasta dos
intentos totales). Configurar `PW_RETRIES` como entero no negativo para cambiarlo;
`0` desactiva los reintentos. Por ejemplo, en PowerShell:

```powershell
$env:PW_RETRIES = '2'
pnpm run test:ui:login
Remove-Item Env:PW_RETRIES
```

La variable debe estar en el entorno del proceso o configurada en CI. El archivo
`.env.example` sirve de referencia; no se carga automáticamente como configuración.

## Cómo contribuir

La guía [docs/test-authoring.md](./docs/test-authoring.md) describe la superficie objetivo
para escribir tests. Antes de crear una capacidad, se debe leer su especificación bajo
`specs/` y confirmar que no existan decisiones materiales abiertas.
