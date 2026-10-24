# Baja y arrepentimiento desde Mis Productos — UI BERSA

## Estado

Aprobada para implementar mediante `TC-010-2-0001-UNE`. La ejecución exploratoria del 15 de
septiembre de 2026 en QA confirmó el modal y la apertura de la página pública.

## Objetivo

Comprobar que una persona autenticada puede aceptar la redirección desde `Baja /
Arrepentimiento` hacia la página pública de baja y arrepentimiento de BERSA.

## Alcance

- Banco: Banco Entre Ríos (BERSA).
- Caso: `TC-010-2-0001-UNE`; canal: UI; ambiente: QA.
- Precondición: las credenciales dummy del caso permiten iniciar una sesión UI real.
- Recorrido: Inicio → Mis Productos → Baja / Arrepentimiento → Aceptar.
- Resultado: se abre una nueva pestaña con la página pública de baja y arrepentimiento.

No se completa ni se envía un formulario de baja. El escenario sólo valida la navegación.

## Comportamiento esperado

1. Cargar las credenciales de `TC-010-2-0001-UNE` e iniciar sesión mediante la UI.
2. Abrir `Mis Productos` y seleccionar `Baja / Arrepentimiento`.
3. Confirmar que se muestra el modal `Baja y arrepentimiento` y su advertencia de salida
   del Home Banking.
4. Aceptar la advertencia.
5. Confirmar que la nueva pestaña muestra la página pública `Arrepentimiento y baja de
productos` de Banco Entre Ríos.

## Diseño aprobado

- `src/pages/mis-products/cancellation.page.ts` contiene los locators del menú y popup,
  apertura de pestaña, sincronización de carga y lectura de URL y título observados.
- `src/fixtures/ui-test.ts` compone `cancellationPage`.
- `tests/ui/010-mis-products/should-open-cancellation-page.ui.spec.ts` expresa login,
  disponibilidad del popup y resultado funcional; contiene la URL estable y el título
  esperados, sin selectores.
- No se crea workflow: no existe una secuencia reutilizable con decisiones funcionales
  adicionales.

## Criterios de aceptación

- El título comienza con `TC-010-2-0001-UNE` y declara su `testCaseId`.
- Se realiza login UI real, sin inyección de sesión API.
- El menú y el popup se esperan mediante estados observables, sin pausas fijas ni
  `networkidle`.
- Aceptar espera la apertura de una nueva pestaña y devuelve su URL y título observados; el
  test los compara con los valores esperados.
- El test no contiene selectores, credenciales ni tokens.
- La pestaña externa se cierra tras su verificación y no se solicita ni modifica una baja.
- Formato, typecheck, lint y `TC-010-2-0001-UNE` finalizan correctamente.

## Seguridad y datos

- Las credenciales son datos dummy QA versionados bajo `test-data/private/bersa.json`.
- La contraseña, tokens, cookies y URL con parámetros temporales no se imprimen ni se
  adjuntan.
- Screenshots y videos se conservan sólo ante fallo conforme a la configuración global.

## Decisiones

- La validación observa la página pública estable por URL y título, no por los parámetros
  ni la implementación interna del sitio externo.
- Esta decisión conserva el comportamiento observado y reemplaza la comprobación legada
  basada únicamente en una redirección genérica.
- Las URL y el título esperados pertenecen al test; el Page Object devuelve los valores
  observados tras sincronizar la nueva pestaña.

## Decisiones abiertas

Ninguna para este alcance.

## Decisión de arquitectura — 28 de septiembre de 2026

La comparación de URL y título esperados se traslada al test para mantener allí la
expectativa funcional. El Page Object conserva la apertura de la pestaña, espera su carga,
lee los valores observados y la cierra. Esta decisión reemplaza la comparación previa dentro
de `CancellationPage`.
