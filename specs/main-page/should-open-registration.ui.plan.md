# Registro desde la pantalla principal — Banco Entre Ríos

## Estado

Aprobada e implementada mediante `TC0156BERSA`. La decisión explícita del usuario
reemplaza el recorrido de ingreso y regreso que se había extraído del cuerpo de la referencia
legada. La ejecución UI en BERSA QA fue validada el 16 de septiembre de 2026.

## Objetivo

Comprobar que una persona puede abrir el flujo de registro desde la página inicial mediante
el enlace `¿No sos cliente aún? Registrarme`.

## Alcance

- Banco: Banco Entre Ríos (BERSA).
- Caso: `TC0156BERSA`; canal: UI.
- Recorrido: pantalla principal → enlace `¿No sos cliente aún? Registrarme` → validación de
  identidad.
- No requiere credenciales ni datos funcionales específicos.

## Comportamiento esperado

1. Abrir la página inicial de Home Banking BERSA.
2. Confirmar que el enlace `¿No sos cliente aún? Registrarme` está disponible.
3. Seleccionar el enlace.
4. Confirmar que se muestra el encabezado `Es necesario validar tu identidad para continuar`.

## Decisión de migración

El cuerpo de `TC0156BERSA` en la referencia legada ejecutaba `Ingresar` y luego `Volver`, lo
que contradecía su nombre. Por decisión explícita del usuario, `TC0156BERSA` debe validar el
acceso de registro que indica su nombre. Esta decisión reemplaza el recorrido de regreso al
ingreso y la interpretación previa de conservar el comportamiento ejecutable de la referencia.

La observación manual en BERSA QA confirmó que el enlace accesible se llama
`¿No sos cliente aún? Registrarme`, navega a `detectIdentityWebNotSupported` y muestra el
encabezado definido como resultado verificable. La URL de destino permanece encapsulada y no
se declara ni verifica directamente en el test.

## Diseño aprobado

- `src/pages/start-page/start.page.ts` contiene la navegación a `/startPage`, el botón
  `Ingresar` y el enlace de registro.
- `src/pages/registration/identity-validation.page.ts` contiene los locators y lecturas de
  la pantalla de validación de identidad.
- `src/fixtures/ui-test.ts` entrega `startPage` e `identityValidationPage`.
- `tests/ui/start-page/should-open-registration.ui.spec.ts` expresa el recorrido y sus
  expectativas funcionales.
- No se crea workflow ni se agregan datos de prueba: un workflow sólo delegaría el click
  entre dos Page Objects.

## Criterios de aceptación

- El título comienza con `TC0156BERSA`.
- El test no contiene URL ni selectores.
- `StartPage` utiliza la URL UI configurada y no contiene esperas fijas.
- La pantalla inicial muestra el enlace de registro.
- Al seleccionar el enlace, `IdentityValidationPage` muestra su encabezado.
- La prueba automatizada se ejecuta contra la pantalla inicial y valida la pantalla de
  validación de identidad posterior al acceso directo.

## Decisiones abiertas

- Ninguna para este recorrido. Cualquier ampliación del registro más allá de la pantalla de
  validación de identidad requiere una nueva especificación.
