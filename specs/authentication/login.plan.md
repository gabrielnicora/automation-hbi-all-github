# Login UI de Banco Entre Ríos con captura de Bearer token

## Estado

Adaptada a BERSA y validada en QA el 8 de septiembre de 2026 mediante `TC0017BERSA`.

## Objetivo

Validar que la identidad autorizada `TC0017BERSA` puede iniciar sesión en Banco Entre Ríos
mediante la UI y capturar de forma segura el access token emitido durante el login.

## Alcance

- Banco: Banco Entre Ríos (BERSA).
- Código de trazabilidad: `BERSA`.
- Caso: `TC0017BERSA`.
- Ambiente: QA.
- Base UI: `https://hbtest01.bancoentrerios.com/startPage`.
- Canal: UI.
- Tag: `@regression`.
- Captura: respuesta HTTP 200 de `/api/v1/execute/session.login.step3`.
- Token: propiedad `data._accessToken`.

Banco Santa Cruz, Banco Santa Fe, otros bancos, casos negativos y el uso efectivo del token
en otra request quedan fuera de alcance.

## Comportamiento esperado

1. Seleccionar `TC0017BERSA` desde el export JSON privado de BERSA.
2. Validar que existe exactamente un registro con usuario y contraseña no vacíos.
3. Abrir el home banking BERSA de QA.
4. Seleccionar `Ingresar`.
5. Completar el usuario y continuar.
6. Preparar la captura de la respuesta de autenticación.
7. Completar la contraseña e ingresar.
8. Confirmar HTTP 200 y access token no vacío.
9. Esperar que la aplicación persista `persist:session` en `sessionStorage`.
10. Construir una sesión autenticada con access token y header Bearer.

El framework observa la sesión creada por la aplicación; no escribe ni inyecta el
`sessionStorage`.

## Diseño implementado

- `src/pages/start-page/start.page.ts` contiene la apertura de la pantalla inicial y la
  acción `Ingresar` que muestra el formulario de autenticación.
- `src/pages/authentication/login.page.ts` contiene los locators, acciones, esperas,
  captura de respuesta y comprobación de sesión desde el campo de usuario en adelante.
- Los selectores y textos de los controles viven en los Page Objects, no en configuración.
- La URL base UI QA de BERSA vive en `src/config/environment.ts`; `UI_BASE_URL` permite
  reemplazarla para una ejecución puntual.
- `src/fixtures/ui-test.ts` entrega `startPage`, `loginPage` y las credenciales del caso.
- El test conserva las expectativas funcionales sobre la sesión resultante.
- `src/models/authenticated-session.ts` contiene el modelo simple compartido por UI y API.
- No se utiliza `AuthenticationGateway`, `BankConfig`, factory bancaria ni workflow de
  login: un workflow sólo delegaría la transición inicial y la autenticación.

El test de login utiliza `StartPage` y `LoginPage` mediante `src/fixtures/ui-test.ts`.
Inversiones reutiliza la misma entrada de autenticación; no existe una implementación legacy.

## Criterios de aceptación

- El test no contiene URL, selectores, endpoints ni credenciales.
- El título comienza con `TC0017BERSA`.
- Las credenciales se leen del export privado BERSA por `tcName`.
- La captura se prepara antes de la acción que dispara la respuesta.
- El login no finaliza hasta que la aplicación persiste la sesión.
- Una respuesta sin `data._accessToken` falla con un mensaje claro sin mostrar el body.
- El resultado contiene access token y header Bearer reutilizable.
- Password dummy no se repite en errores; token, cookies y header Bearer no se imprimen ni
  se adjuntan.
- El reporte identifica mediante una anotación el usuario dummy utilizado.
- Un JSON ausente o inválido, caso inexistente o credenciales vacías falla explícitamente.
- Cambiar un selector requiere modificar solamente `login.page.ts`.
- Desde el test se llega al Page Object sin atravesar factories o configuración bancaria.
- Formato, lint, typecheck y el escenario UI BERSA finalizan correctamente.

## Seguridad y datos

- Las credenciales pertenecen a `TC0017BERSA` y se leen desde
  `test-data/private/bersa.json`.
- El export QA contiene datos dummy no productivos y se versiona bajo `test-data/private/`
  para distribuir la versión ejecutable.
- Los errores validan estructura y contexto sin repetir la contraseña ni incluir tokens o
  cookies de ejecución.
- El usuario dummy utilizado permanece visible en el reporte para facilitar el diagnóstico.

## Decisiones abiertas

Ninguna para el alcance BERSA.
