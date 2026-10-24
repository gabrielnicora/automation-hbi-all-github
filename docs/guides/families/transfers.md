# Familia funcional: transferencias

## Regla de uso para Kiro

Aplicar la [guía general](../README.md) y los [principios](../migration-principles.md).
Los recorridos siguientes describen testigos, no límites para otras variantes: preservar
pasos, datos, efectos y assertions del código legado y sus helpers, salvo una decisión
explícita que los reemplace para ese mismo caso. Ante dudas, portar lo ejecutable, registrar
en [ambiguities](../ambiguities/README.md) y continuar sin consultar ni exigir tablas o una
especificación individual nueva. No portar retries internos; usar Playwright y reutilizar
la política central de segundo factor. No heredar automáticamente marcas de fallo esperado.

Esta guía indica cómo migrar casos legados de transferencias a la arquitectura actual de
Playwright con TypeScript. Está escrita a partir de los testigos vigentes de API y UI, y puede
repetir información de sus especificaciones para que una herramienta de migración tenga una
receta operativa completa.

La guía es orientativa para migración. Las especificaciones bajo `specs/transfers/` siguen
siendo la fuente del comportamiento aprobado de cada caso.

## 1. Casos testigo vigentes

### 1.1 Testigo API — `TC-015-1-0003-UNE`

Transferencia inmediata de ARS 1 entre dos cuentas propias mediante API, con recuperación y
validación del ticket final.

Archivos de referencia:

- Especificación: [own-account-transfer.api.plan.md](../../../specs/transfers/own-account-transfer.api.plan.md).
- Test: [should-transfer-between-own-accounts.api.spec.ts](../../../tests/api/015-transfers/should-transfer-between-own-accounts.api.spec.ts).
- Cliente: [own-account-transfer.api.ts](../../../src/api/transfers/own-account-transfer.api.ts).
- Datos: [own-account-transfer-data.ts](../../../src/test-data/own-account-transfer-data.ts).
- Datos privados: registro `TC-015-1-0003-UNE` en `test-data/private/bersa.json`.
- Fixture pública: `src/fixtures/api-test.ts`.

### 1.2 Testigo UI — `TC-015-2-0004-UNE`

Transferencia inmediata de ARS 1 a una cuenta propia buscada por CBU mediante UI, completando
el formulario, revisando y confirmando la operación, y verificando el ticket exitoso.

Archivos de referencia:

- Especificación: [own-account-transfer.ui.plan.md](../../../specs/transfers/own-account-transfer.ui.plan.md).
- Test: [should-transfer-to-own-account-by-cbu.ui.spec.ts](../../../tests/ui/015-transfers/should-transfer-to-own-account-by-cbu.ui.spec.ts).
- Page Object: [own-account-transfer.page.ts](../../../src/pages/transfers/own-account-transfer.page.ts).
- Datos: [own-account-transfer-ui-data.ts](../../../src/test-data/own-account-transfer-ui-data.ts).
- Datos privados: registro `TC-015-2-0004-UNE` en `test-data/private/bersa.json`.
- Fixture pública: `src/fixtures/ui-test.ts`.

Esta guía describe el comportamiento aprobado y la implementación actual. Consultar el
legado local para completar las diferencias: `TC-015-1-0003-UNE` está en
`java/api/GP_Individuos_API/src/test/java/EntreRios/transferencias/TransferenciasTest.java`
y `TC-015-2-0004-UNE` en
`java/ui/GP_Migracion_Individuos/src/test/java/GP/EntreRios/front/FrontTest.java`.
Las rutas parten de la raíz del repositorio. No asumir que los testigos cubren todos los
tipos de transferencia ni que la presencia de estas fuentes demuestra equivalencia validada.

## 2. Qué significa pertenecer a esta familia

Un caso pertenece a transferencias cuando su comportamiento principal consiste en mover dinero
desde una cuenta origen hacia un destino y verificar la aceptación, el comprobante, el ticket,
el estado o el efecto funcional de esa operación.

Pueden pertenecer a esta familia:

- transferencias entre cuentas propias;
- transferencias a cuentas de terceros;
- transferencias por CBU, CVU o alias;
- transferencias en ARS o USD;
- transferencias inmediatas o programadas;
- recorridos API o UI;
- casos que verifican ticket, comprobante, saldo, estado o mensaje de éxito.

No se debe concluir que dos casos tienen el mismo flujo sólo porque ambos se llaman
“transferencia”. La familia agrupa el dominio; el tipo de transferencia determina la variante.

## 3. Mapa de arquitectura

| Necesidad del caso             | Ubicación actual                                       | Regla para migrar                                            |
| ------------------------------ | ------------------------------------------------------ | ------------------------------------------------------------ |
| Escenario y expectativas       | `tests/api/015-transfers/` o `tests/ui/015-transfers/` | Mantener lenguaje funcional y `testCaseId`                   |
| Protocolo HTTP                 | `src/api/transfers/`                                   | Endpoints, headers, payloads y respuestas                    |
| Mecánica UI                    | `src/pages/transfers/`                                 | Locators, acciones y esperas observables                     |
| Datos del caso                 | `src/test-data/`                                       | Loader tipado y validaciones de formato                      |
| Composición técnica            | `src/fixtures/`                                        | Cliente, Page Object y datos públicos                        |
| Coordinación de varias páginas | `src/workflows/transfers/`                             | Crear sólo si el recorrido cruza páginas o decisiones reales |
| Comportamiento aprobado        | `specs/transfers/`                                     | Registrar criterios y decisiones específicas                 |
| Dudas de migración rápida      | `docs/guides/ambiguities/`                             | Registrar la decisión provisional y su evidencia             |

No trasladar las capas legacy `banks/`, `contracts/`, `features/`, `support/` o
`test-setup/`. Tampoco crear un `TransferManager`, `TransferHelper`, gateway o factory sólo
para reunir métodos.

## 4. Recorrido común de una transferencia

El recorrido conceptual que Kiro debe buscar en cada caso es:

```text
identidad autenticada
        ↓
selección o carga de cuenta origen
        ↓
resolución del destino
        ↓
validación de datos y moneda
        ↓
carga de importe, concepto y referencia
        ↓
revisión de la operación
        ↓
confirmación
        ↓
resultado: ticket, comprobante, estado, saldo o mensaje
```

Una variante puede omitir o agregar pasos. Esa diferencia debe documentarse y no ocultarse
mediante condiciones que elijan dinámicamente el flujo.

## 5. Testigo API en detalle

### 5.1 Entrada del test

El test actual:

1. fija `TEST_CASE_ID = 'TC-015-1-0003-UNE'`;
2. configura `test.use({ testCaseId })`;
3. recibe `loginApi`, `loginCredentials`, `ownAccountTransferApi` y
   `ownAccountTransferData` desde la fixture;
4. autentica al usuario;
5. llama una única operación funcional del cliente;
6. valida cuatro resultados de negocio.

El test no conoce endpoints, payloads, headers, selección de cuentas, tokens ni parsing de
respuestas.

Fragmento del testigo API, dentro del test que recibe las fixtures indicadas arriba:

```ts
const session = await test.step('Autenticar al usuario', () => loginApi.authenticate(loginCredentials));

const result = await test.step('Ejecutar la transferencia y obtener el resultado', () =>
  ownAccountTransferApi.transfer(session, ownAccountTransferData));

expect(result.isTicketFinished).toBe(true);
expect(result.ticketMatchesTransfer).toBe(true);
expect(result.isOwnAccountTransfer).toBe(true);
expect(result.hasReceiptPdf).toBe(true);
```

El nombre concreto del cliente y del resultado puede cambiar según la variante, pero la
intención funcional debe permanecer visible en el test.

### 5.2 Secuencia HTTP del testigo

El cliente actual ejecuta esta secuencia:

1. `POST /api/v1/execute/widgets.accounts`.
2. Selección única de cuenta origen mediante `shortLabel`.
3. Selección única de cuenta destino mediante `shortLabel`.
4. `POST /api/v1/execute/widgets.creditCards`.
5. Filtrado de productos de tipo tarjeta de débito (`productType === 'TD'`).
6. Selección única de tarjeta por los últimos cuatro dígitos de `label`.
7. Validación de cuentas distintas, moneda ARS y CBU destino.
8. `POST /api/v1/execute/account.transfer.make` una sola vez.
9. Lectura de `idTransaction`.
10. `POST /api/v1/execute/ticket.get` una sola vez para ese identificador.
11. Validación del ticket y del comprobante PDF.

Cada request pasa por `ReportingApiRequest`, exige HTTP 200, objeto JSON y código de negocio
`COR000I`. Los metadatos actuales incluyen:

- `xAppVersion: 2.04.0`;
- `ajax_uuid` nuevo por request y compartido entre header y body;
- `_bcCuil` nuevo por instancia del cliente y reutilizado durante el recorrido;
- nombres históricos de campos como `tranferType`, `emailMessagge` y
  `_targetAccountNumberLabel`.

Estos valores son propios del protocolo observado del testigo. Para otra variante, Kiro debe
confirmar si el legado usa los mismos campos y valores antes de reutilizarlos.

### 5.3 Datos API del testigo

El loader de `TC-015-1-0003-UNE` exige:

| Campo lógico      | Campo del registro | Validación actual                    |
| ----------------- | ------------------ | ------------------------------------ |
| Cuenta origen     | `ctaOrigen`        | Texto no vacío                       |
| Cuenta destino    | `ctaDestino`       | Texto no vacío y distinto del origen |
| CBU destino       | `cbuDestino`       | Exactamente 22 dígitos               |
| CUIT/CUIL destino | `cuitDestino`      | Exactamente 11 dígitos               |
| Nombre destino    | `nombreDestino`    | Texto no vacío                       |
| Tarjeta de débito | `tarjetaDebito`    | Exactamente 4 dígitos                |

El loader normaliza el `testCaseId`, carga la fila BERSA y rechaza valores vacíos o
`sinDatos`. No debe copiarse esta forma de datos si la variante tiene CVU, alias, otra moneda,
otro tipo de destinatario o no utiliza tarjeta de débito.

### 5.4 Validaciones funcionales API

El resultado actual expone indicadores simples:

- `isTicketFinished`: el ticket está en `FINISHED`;
- `ticketMatchesTransfer`: el ticket corresponde al `idTransaction` generado;
- `isOwnAccountTransfer`: la actividad es `account.transfer.make` y el tipo es
  `betweenOwnAccounts`;
- `hasReceiptPdf`: el archivo es `Ticket.pdf` y el Base64 no está vacío.

Una variante debe conservar sólo las expectativas que correspondan a su comportamiento. Por
ejemplo, una transferencia de terceros no debe afirmar `isOwnAccountTransfer === true` sólo
porque el testigo lo hace.

### 5.5 Payload API actual

El testigo envía, entre otros, estos grupos de datos:

- tipo: `betweenOwnAccounts` y etiqueta “Entre cuentas propias”;
- monto y moneda: ARS 1;
- IDs de origen y destino obtenidos de widgets;
- concepto `VAR`, etiqueta `Varios` y referencia `Otros`;
- labels, número, CBU, titular y CUIT/CUIL del destino;
- banco destino y tipo de cuenta;
- tarjeta de débito y moneda de la tarjeta;
- flags de cuenta adherida, cuenta propia y flujo nuevo;
- campos nulos del protocolo que el endpoint espera.

No copiar el payload completo como plantilla universal. Para cada variante, separar:

1. campos comunes comprobados;
2. campos derivados de los widgets;
3. campos de datos del caso;
4. flags dependientes del tipo de transferencia;
5. campos históricos cuyo uso debe verificarse;
6. campos que el legado envía pero que el protocolo actual ya no necesita.

## 6. Testigo UI en detalle

### 6.1 Secuencia funcional

El test UI actual realiza:

1. abrir la pantalla inicial;
2. abrir el login;
3. autenticar por UI;
4. abrir Transferencias;
5. seleccionar “A un CBU, CVU o Alias”;
6. ingresar el CBU;
7. buscar el contacto;
8. esperar “Revisá los datos del contacto”;
9. confirmar “Son correctos”;
10. completar importe;
11. comprobar que el concepto visible es “Varios”;
12. completar descripción;
13. seleccionar “Transferir ahora”;
14. esperar “Revisá que esté todo bien”;
15. comprobar origen, destino y monto en la revisión;
16. confirmar;
17. esperar “Tu solicitud se realizó con éxito”;
18. comprobar origen, destino y monto en el ticket.

### 6.2 Responsabilidad del Page Object

`OwnAccountTransferPage` encapsula todos los locators y la mecánica del recorrido. Utiliza
locators de rol, texto, placeholder y un locator CSS puntual para el importe. El test sólo
abre páginas, autentica y llama a `transferByCbu`.

Actualmente también encapsula assertions de revisión y ticket. Es una descripción del
testigo, no una obligación de ocultar expectativas: en variantes nuevas mantenerlas visibles
desde el test mediante resultados o métodos de comprobación explícitos. No quitar ninguna
assertion legada ni refactorizar el testigo sólo para copiar su forma.

Las esperas actuales se basan en estados observables:

- encabezado de Transferencias;
- encabezado de revisión del contacto;
- campo de importe visible;
- encabezado de revisión final;
- mensaje de éxito;
- datos visibles del ticket.

Una variante UI debe conservar este principio. No reemplazar estas señales por sleeps,
`waitForTimeout` o `networkidle`.

### 6.3 Datos UI actuales

El loader UI sólo entrega:

- `destinationCbu`;
- `destinationAccount`;
- `originAccount`.

En cambio, el Page Object mantiene como constantes:

- importe `1`;
- descripción `TC-015-2-0004-UNE`;
- concepto visible `Varios`;
- monto mostrado `$ 1,00`.

Esto es una característica del testigo, no una regla universal. Si el caso legado tiene
importe, concepto, referencia o descripción diferentes, esos valores deben pasar a un loader
de datos específico en lugar de quedar hardcodeados dentro de un Page Object compartido.

### 6.4 Alcance UI que no debe inferirse

Aunque la pantalla muestra “CBU, CVU o Alias”, el testigo UI implementa únicamente el método
`transferByCbu`. No demuestra que CVU y alias tengan el mismo flujo o los mismos selectores.

Aunque el destino está marcado como cuenta propia, el testigo UI no agrega ni elimina
destinatarios mediante API. Si una variante legada sí los gestiona, conservar esa precondición;
no esperar una especificación nueva para portarla.

El testigo UI tampoco incorpora segundo factor. Si el legado de una variante contiene un
desafío, debe aplicar la política aprobada de segundo factor y registrarse la diferencia.

## 7. Qué reutilizar y qué comparar

| Elemento                                    |     Reutilizar como patrón |                         Comparar obligatoriamente |
| ------------------------------------------- | -------------------------: | ------------------------------------------------: |
| Fixture de login                            |                         Sí |                Canal y mecanismo de autenticación |
| `testCaseId`                                |                         Sí |                          ID real del caso migrado |
| Separación test/datos/cliente o Page Object |                         Sí |                             Necesidad de workflow |
| Resolución de cuentas                       |               Parcialmente | Identificador, moneda y cantidad de coincidencias |
| Tarjeta de débito                           |   Sólo si el legado la usa |         Tipo de producto y requisito del endpoint |
| CBU                                         |        Sólo para casos CBU |                     CVU, alias o número de cuenta |
| `betweenOwnAccounts`                        |         Sólo cuenta propia |                        Tipo real del destinatario |
| ARS                                         |   Sólo si el legado es ARS |                                 USD u otra moneda |
| Importe 1                                   |                         No |                  Importe real y reglas de formato |
| Concepto `Varios`                           |                         No |                  Concepto y referencia del legado |
| Ticket PDF                                  |             Sólo si existe |                           Resultado real esperado |
| Consulta de saldos                          |      No asumir su ausencia |              Si el legado la usa como expectativa |
| Reintentos                                  | No copiar retries internos |            Efectos de mutación y política vigente |

## 8. Variantes de la familia

### 8.1 Transferencia en USD

Reutilizar autenticación, estructura del test y selección de productos sólo después de
confirmar que las cuentas, tarjeta, payload y endpoint soportan USD. Revisar:

- moneda de origen y destino;
- tarjeta habilitada para USD;
- importe y formato;
- campos de moneda del payload;
- concepto y límites;
- resultado esperado del ticket;
- datos de prueba específicos.

No cambiar `CURRENCY = 'ARS'` por `USD` sin evidencia del legado o especificación aprobada.

### 8.2 Transferencia a cuenta de terceros

No reutilizar automáticamente:

- `betweenOwnAccounts`;
- `isOwnSynchronisedAccount: true`;
- `isOwnAccountTransfer`;
- `extraValues.isOwnAccount: true`;
- selección del destino como cuenta propia;
- nombre, CUIT/CUIL y flags del destinatario propio.

Analizar alta o selección de destinatario, banco receptor, CBU/CVU/alias, validaciones,
posible segundo factor y resultado de negocio.

### 8.3 Transferencia por CVU o alias

El testigo UI sólo prueba CBU aunque la pantalla agrupe las tres opciones. Crear un método o
Page Object separado si el flujo, los controles o el postestado cambian. Registrar como
variante de datos sólo si el recorrido y la validación son realmente iguales.

### 8.4 Transferencia programada

No tratarla como parametrización de la transferencia inmediata si agrega fecha, frecuencia,
confirmación distinta, endpoint diferente o resultado distinto. Debe tener caso separado,
datos propios y expectativas propias.

### 8.5 Variantes API y UI

Pueden compartir la familia y conceptos de negocio, pero no deben compartir selectores,
payloads ni detalles de protocolo. API y UI conservan tests y superficies técnicas separadas.

## 9. Regla de migración rápida para ambigüedades

Cuando Kiro encuentre una diferencia entre el legado y el testigo:

1. tomar el legado como evidencia primaria, salvo una decisión explícita que ya lo reemplace para ese mismo caso;
2. conservar el comportamiento observable del legado;
3. adaptar ese comportamiento a la arquitectura actual;
4. no copiar automáticamente una decisión del testigo que contradiga el legado;
5. registrar la duda en `docs/guides/ambiguities/`, en un archivo por lote o caso;
6. escribir la evidencia, la decisión provisional, el riesgo y la pregunta pendiente;
7. completar la traducción de lo ejecutable sin consultar por dudas de interpretación;
8. si falta físicamente una dependencia o dato indispensable, portar lo disponible, indicar
   el faltante exacto y continuar el lote sin inventar valores ni declarar completo ese caso.

Ejemplos de ambigüedades que deben registrarse:

- el legado consulta saldos pero el testigo no;
- el legado reintenta una mutación;
- la respuesta puede ser ticket o mensaje de éxito;
- el destino parece propio, pero no hay evidencia suficiente;
- el endpoint o campo histórico cambió de nombre;
- el segundo factor aparece sólo en una variante;
- el importe, concepto o moneda no coinciden con el testigo.

## 10. Entregables esperados de una migración

Para cada caso de transferencia migrado, Kiro debe dejar:

- test API o UI en `tests/`;
- datos tipados en `src/test-data/`;
- cliente API, Page Object o workflow mínimo;
- integración en fixtures compatibles sin exigir una especificación nueva como prerrequisito;
- archivo de ambigüedades si hubo decisiones provisionales;
- evidencia de validación o explicación de lo que no pudo ejecutarse.

La entrega puede resumirse por lote: no exigir tabla comparativa ni documento individual
por caso. Las decisiones pendientes se consolidan en especificaciones en la revisión humana.

## 11. Checklist específico

- [ ] Se identificó si el caso es API, UI o ambos.
- [ ] Se seleccionó el testigo más cercano.
- [ ] Se confirmó si es propia o tercera persona.
- [ ] Se confirmó moneda e importe.
- [ ] Se confirmó CBU, CVU, alias u otro identificador.
- [ ] Se documentó el origen y el destino.
- [ ] Se documentaron concepto, referencia y descripción.
- [ ] Se identificó si usa tarjeta de débito.
- [ ] Se identificó si requiere segundo factor.
- [ ] Se identificó la mutación y su política de reintento.
- [ ] Se identificó el resultado: ticket, comprobante, saldo, estado o mensaje.
- [ ] API: cada request está dentro del cliente y no del test.
- [ ] UI: cada selector y espera está dentro del Page Object.
- [ ] El test no contiene credenciales, endpoints, payloads ni selectores.
- [ ] La variante no afirma que es cuenta propia sólo por parecerse al testigo.
- [ ] Las ambigüedades tienen registro propio.
- [ ] El caso testigo sigue siendo ejecutable.
- [ ] Se ejecutaron las validaciones aplicables.

## 12. Decisiones que no deben tomarse automáticamente

Kiro no debe decidir por similitud superficial:

- que USD funciona igual que ARS;
- que terceros funciona igual que cuenta propia;
- que CVU y alias tienen el mismo flujo que CBU;
- que una mutación se puede reintentar internamente;
- que un ticket siempre existe;
- que consultar saldos es innecesario;
- que un segundo factor no aplica;
- que los campos históricos del payload pueden eliminarse;
- que todos los casos pueden parametrizarse en un único test.

La migración puede avanzar con una decisión provisional basada en el legado, pero la duda y
el riesgo deben quedar visibles para la revisión posterior.
