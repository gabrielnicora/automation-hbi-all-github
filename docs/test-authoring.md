# Guía de autoría de tests — arquitectura objetivo BERSA

> Todas las capacidades vigentes de BERSA utilizan esta superficie pública. Las capas
> multibanco anteriores fueron retiradas y no deben reintroducirse.

Un autor de tests no necesita conocer factories, contratos de sustitución, configuración
bancaria ni la composición interna de las fixtures.

La suite ejecuta un archivo por vez de manera predeterminada. Antes de aumentar
`PW_WORKERS`, se debe confirmar que los escenarios seleccionados no reutilicen identidades
ni estado mutable.

## Camino de lectura

Para comprender un escenario UI se debe poder seguir:

```text
test → workflow opcional → Page Object → Playwright
```

Para API:

```text
test → cliente API → APIRequestContext
```

No debe haber más de uno o dos saltos entre el test y el código que realiza la operación.

## Conceptos mínimos

- Banco Entre Ríos (BERSA) es el único banco soportado.
- Cada título comienza con el ID del caso, `TC-<FFF>-<C>-<NNNN>-UNE` (`SPEC.md`, sección 10.1).
- Cada caso declara su propio `testCaseId`.
- El identificador selecciona los datos propios del caso; no selecciona banco ni factory.
- Cuando el legado comparte deliberadamente una identidad, el test declara `credentialsRef` de
  forma explícita. La referencia sólo cambia el origen de las credenciales de login.
- Los tests contienen expectativas funcionales.
- Los Page Objects contienen selectores, acciones UI y comprobaciones de disponibilidad.
- Los clientes API contienen endpoints, payloads y validación del protocolo.
- Los clientes API usan el ejecutor transversal para mostrar request/response sanitizados por
  step e incluir la secuencia completa en fallos.
- Un workflow se utiliza sólo cuando coordina varias páginas o decisiones significativas.
- Los datos dummy QA/DEV se versionan; passwords y códigos no se repiten en errores, y los
  tokens o cookies generados en ejecución no se imprimen.

## Dónde vive cada responsabilidad

| Carpeta                 | Qué se espera encontrar                                               |
| ----------------------- | --------------------------------------------------------------------- |
| `src/pages/<area>/`     | Page Objects, locators y modelos UI locales.                          |
| `src/api/<area>/`       | Clientes API y detalles privados de sus protocolos.                   |
| `src/models/`           | Modelos simples compartidos entre UI, API o workflows.                |
| `src/workflows/<area>/` | Recorridos que coordinan varias páginas; sólo cuando sean necesarios. |
| `src/fixtures/`         | Objetos que Playwright entrega a los tests.                           |
| `src/config/`           | Configuración de ambiente y políticas de ejecución.                   |
| `src/test-data/`        | Lectores y validadores de datos de prueba versionados.                |
| `tests/ui/<area>/`      | Escenarios UI y expectativas funcionales.                             |
| `tests/api/<area>/`     | Escenarios API y expectativas funcionales.                            |

La capa técnica se identifica antes que la funcionalidad: login UI está en
`pages/authentication/`, mientras que login API está en `api/authentication/`.

## Test UI de una capacidad existente

Ejemplo de login UI con las fixtures y métodos disponibles:

```ts
import { expect, test } from '../../../src/fixtures/ui-test';

const TEST_CASE_ID = 'TC-002-2-0001-UNE';

test.describe('Login UI de Banco Entre Ríos', () => {
  test.use({ testCaseId: TEST_CASE_ID });

  test(
    `${TEST_CASE_ID} - inicia sesión y captura el Bearer token`,
    { tag: '@regression' },
    async ({ loginCredentials, loginPage }) => {
      const authenticatedSession = await loginPage.authenticate(loginCredentials);

      expect(
        authenticatedSession.accessToken.trim().length > 0,
        'El login debe devolver un access token no vacío.',
      ).toBe(true);
      expect(
        authenticatedSession.authorizationHeader === `Bearer ${authenticatedSession.accessToken}`,
        'El header Bearer debe corresponder al access token recibido.',
      ).toBe(true);
    },
  );
});
```

El Page Object conoce sus locators, clicks y esperas. El test conserva el resultado de
negocio esperado.

Si una operación futura coordina varias páginas, la fixture podría entregar un workflow.
El siguiente fragmento es ilustrativo: `transferWorkflow` no existe actualmente.

```ts
test(`${TEST_CASE_ID} - transfiere entre cuentas`, async ({ transferWorkflow }) => {
  const result = await transferWorkflow.transfer({
    amount: 1000,
    destinationAlias: 'DESTINO.AUTORIZADO',
  });

  expect(result.status).toBe('confirmed');
});
```

El alias y el importe son ilustrativos; una especificación real debe aprobar los datos y
su origen antes de implementar el escenario.

## Test API de una capacidad existente

Ejemplo de login API con las fixtures y métodos disponibles:

```ts
import { expect, test } from '../../../src/fixtures/api-test';

const TEST_CASE_ID = 'TC-002-1-0001-UNE';

test.describe('Login API de Banco Entre Ríos', () => {
  test.use({ testCaseId: TEST_CASE_ID });

  test(
    `${TEST_CASE_ID} - inicia sesión y captura el Bearer token`,
    { tag: '@regression' },
    async ({ loginApi, loginCredentials }) => {
      const authenticatedSession = await loginApi.authenticate(loginCredentials);

      expect(
        authenticatedSession.accessToken.trim().length > 0,
        'El login debe devolver un access token no vacío.',
      ).toBe(true);
      expect(
        authenticatedSession.authorizationHeader === `Bearer ${authenticatedSession.accessToken}`,
        'El header Bearer debe corresponder al access token recibido.',
      ).toBe(true);
    },
  );
});
```

Los endpoints, headers y payloads pertenecen al cliente API. El test sólo expresa la
operación y su resultado.

## Responsabilidad de los expects

Las reglas para diseñar variantes, registrar decisiones y validar cambios se definen en
`SPEC.md`. Los siguientes ejemplos orientan su aplicación al escribir tests.

Pueden existir comprobaciones técnicas dentro de un Page Object o cliente API para
garantizar que la página esté operable o que una respuesta posea la estructura mínima.

Las expectativas funcionales permanecen en el test. Ejemplos:

- todos los fondos visibles tienen riesgo moderado;
- la transferencia quedó confirmada;
- el teléfono nuevo quedó persistido;
- la sesión contiene un Bearer válido.

Para UI, aplicar la frontera de `SPEC.md`, sección 6.2.2. Esperar que un formulario esté
disponible o que una selección haya quedado aplicada es sincronización de la página.
Exigir que una compra con importe cero muestre el mensaje exacto de rechazo es una
expectativa del escenario. Una comprobación dentro de un método no cambia de responsabilidad
por llamarse `validate`, `expect` o `wait`.

Al verificar tokens o headers generados en ejecución, pasar a `expect` únicamente el
resultado booleano de la comprobación y utilizar un mensaje fijo. Compararlos directamente
con un matcher puede incluirlos en el diagnóstico de fallo. Los ejemplos de login verifican
contenido no vacío y correspondencia del Bearer sin entregarlos al matcher.

Todo test que carga credenciales muestra el usuario dummy como anotación. En API, cada step
muestra su request y response sanitizados en un JSON cuyo nombre incluye secuencia y endpoint;
el step apunta al cliente funcional que originó la llamada. Si el test falla, además se adjunta
un JSON con la secuencia completa para compartirla. Se excluyen credenciales, tokens, cookies y
códigos, y Base64 grande se reemplaza por su longitud.

## Casos separados o parametrización

- Si cambia el recorrido o la naturaleza del resultado esperado, usar casos separados.
  Por ejemplo, login exitoso y rechazo de credenciales inválidas son escenarios distintos.
- Si sólo cambian datos y se mantiene la regla de validación, se puede parametrizar.
  Por ejemplo, distintos riesgos de fondos podrían compartir la secuencia de filtrado
  si cada variante verifica que las filas correspondan al riesgo seleccionado.
- Cada variante funcional debe producir un test individual, con título, identificador
  y datos aprobados. El mismo caso en API y UI conserva el ID y se distingue por canal.
  No recorrer varios casos dentro de un único test.
- No elegir entre éxito y error según lo que devuelva la aplicación: el resultado
  esperado se define antes de ejecutar. Las decisiones funcionales previstas por la
  especificación siguen siendo válidas.

Los ejemplos no amplían la cobertura aprobada: actualmente el escenario de fondos
especifica riesgo moderado. Agregar otros riesgos requiere definir sus casos y datos.

## Decisiones y validación del trabajo

Documentar cada decisión material en la especificación correspondiente con estado,
motivo, impacto y qué decisión reemplaza. Consultar esa regla vigente antes de modificar
el escenario; no duplicar decisiones en otra guía.

Para cambios amplios, comparar los diagnósticos de las verificaciones antes y después,
identificando archivo, regla y mensaje. Mantener la distinción entre problemas
preexistentes y nuevos, y ejecutar las pruebas funcionales pertinentes al cambio.
Una cantidad igual de errores no demuestra que sean los mismos.

La selección smoke y la comprobación del entorno sin acceso al banco se definirán cuando
se necesiten, conforme a `SPEC.md`. Smoke reutilizará escenarios acordados; la comprobación
del entorno verificará herramientas sin acceder al banco. Ninguna reemplaza la validación
funcional del cambio ni autoriza agregar casos o jobs sin definir su alcance.

## Dónde realizar cada cambio

| Necesidad                                  | Ubicación objetivo                                                 |
| ------------------------------------------ | ------------------------------------------------------------------ |
| Agregar un caso de una capacidad existente | `tests/<canal>/<capacidad>/` y sus datos                           |
| Cambiar un selector                        | Page Object de `src/pages/<area>/`                                 |
| Cambiar un endpoint o payload              | Cliente API de `src/api/<area>/`                                   |
| Coordinar varias páginas                   | Workflow bajo `src/workflows/<area>/`                              |
| Agregar un modelo compartido               | `src/models/`, sólo si más de una capa lo utiliza                  |
| Cambiar URL o política de ejecución        | `src/config/`                                                      |
| Agregar una fixture pública                | `src/fixtures/ui-test.ts` o `api-test.ts`                          |
| Agregar una capacidad                      | Comenzar por `specs/`, luego implementar la mínima pieza necesaria |

Una capacidad nueva no debe modificar configuración global de selectores, factories,
perfiles bancarios, gateways ni contratos generales.

## Page Object Model

Un Page Object:

- mantiene locators privados;
- expone acciones con nombres funcionales;
- espera estados observables y evita sleeps fijos;
- puede comprobar que su página o componente esté disponible;
- devuelve datos simples para que el test pueda evaluarlos;
- no lee variables de entorno ni códigos de banco;
- no coordina páginas no relacionadas.

Se prefieren locators accesibles de Playwright. XPath se admite únicamente cuando la
aplicación no ofrece un locator estable mejor y la decisión queda documentada.

## Separación aplicada a un caso

El siguiente ejemplo ilustra la precisión aprobada en `SPEC.md`, sección 6.2.2.
`currencyPurchaseData` y los métodos mostrados son una propuesta de autoría; no describen
fixtures ni métodos implementados actualmente. Se omite el login para mostrar la operación.

```ts
await purchasePage.open();
await purchasePage.completeForm({
  ...currencyPurchaseData,
  amount: '0',
});
await purchasePage.continue();

const displayedError = await purchasePage.readAmountError();
expect(displayedError).toBe('El monto ingresado debe ser mayor a 0');
```

La fixture entrega los datos de las cuentas. El test hace visible el importe que define
la variante y el mensaje exigido. `completeForm` agrupa la interacción y espera las
selecciones aplicadas; `readAmountError` espera el contenedor de error y devuelve su texto
renderizado. El locator de lectura no se construye a partir del mensaje esperado por el test.
El Page Object permite que la aplicación reciba el importe inválido.

Usar acciones del formulario, como `completeForm(data)`, `continue()` y `readReview()`.
Evitar un método que fije todos los datos y el resultado de un caso, como
`purchaseZeroDollars()`. Si el recorrido coordina otras páginas o clientes con decisiones
significativas, evaluar un workflow conforme a la sección siguiente.

## Política general de esperas UI

No usar `Thread.sleep`, `waitForTimeout`, `fixedWait` ni pausas equivalentes. Una demora
fija puede ocultar una carrera en una máquina rápida y fallar en una lenta, además de no
probar que el estado funcional haya terminado.

Cada método de Page Object debe seguir este patrón:

1. Esperar que el control sea visible y operable.
2. Ejecutar la acción.
3. Esperar una señal observable del resultado, como una ruta, título, fila, contador,
   estado seleccionado o mensaje de negocio.

`toBeVisible()` tiene auto-reintento y es correcto para comprobar disponibilidad, pero no
significa que una lista o consulta de datos esté actualizada. Para una acción con endpoint
conocido, preparar `page.waitForResponse(...)` antes del click y luego esperar el estado UI.
Para propagación eventual sin respuesta utilizable, usar `expect.poll` sólo con límites
explícitos y aprobación en la especificación de la capacidad. No usar `networkidle` como
espera universal.

Cuando un registro cambia de estado, identificarlo con un campo estable. No incluir fechas,
horas, contadores u otros valores que la aplicación pueda actualizar durante el recorrido.
Las esperas viven en el Page Object; el test conserva únicamente la expectativa funcional.

## Cuándo crear un workflow

Evaluar un workflow cuando:

- la operación atraviesa dos o más Page Objects;
- existe una secuencia reutilizable con decisiones funcionales;
- la coordinación de un recorrido aparece en más de un escenario.

No crear un workflow si sólo llama un método de un Page Object o cliente API.
Una secuencia breve puede permanecer en el test. Un workflow recibe datos y devuelve lo
observado; el test conserva las expectativas. La cantidad de clases existentes no prueba
que todo el recorrido pertenezca a una sola pantalla: aplicar `SPEC.md`, sección 6.3.

## Prueba de simplicidad antes de entregar

Antes de considerar terminada una capacidad, comprobar:

- ¿el test se entiende sin abrir las fixtures?
- ¿desde el test se llega a la implementación en uno o dos saltos?
- ¿un cambio de selector afecta sólo un Page Object?
- ¿un cambio de endpoint afecta sólo un cliente API?
- ¿se evitó crear una capa que únicamente delega?
- ¿el Page Object recibe los valores del caso y permite ingresar sus datos negativos?
- ¿las expectativas funcionales, incluidas las previas a confirmar, se entienden desde el test?
- ¿la lectura del resultado espera su estado renderizado sin filtrar por el valor esperado?
- ¿se mantuvieron fuera del proyecto los datos productivos y fuera del reporte los tokens
  o cookies de ejecución?

## Comandos frecuentes

Los logins API y UI llevan el tag nativo `@regression`. Esta selección destaca ambos
casos y no equivale a smoke ni contiene claves Xray. Ejecutarla desde la raíz del proyecto:

```powershell
pnpm exec playwright test --grep '@regression'
```

```powershell
pnpm run check
pnpm test
pnpm run test:api
pnpm run test:api:login
pnpm run test:api:profile
pnpm run test:api:transfers
pnpm run test:ui
pnpm run test:ui:login
pnpm run test:ui:investments
```
