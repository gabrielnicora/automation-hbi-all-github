# Marcar mensaje como no leído — UI BERSA

## Estado

Aprobada para implementación mediante `TC0086BERSA`.

## Objetivo

Comprobar que un mensaje abierto desde la bandeja de mensajes leídos puede volver a marcarse como no leído.

## Alcance

- Banco: Banco Entre Ríos (BERSA).
- Caso: `TC0086BERSA`; canal: UI.
- Ambiente: QA.
- Precondición: la bandeja debe poder recuperar al menos un mensaje para normalizar la
  existencia de un mensaje leído; el test convierte temporalmente uno a no leído.
- Autenticación: login UI real; no se inyecta sesión API.

## Comportamiento esperado

1. Cargar las credenciales dummy de `TC0086BERSA`.
2. Iniciar sesión mediante `StartPage` y `LoginPage`.
3. Abrir Mensajes y garantizar que exista un mensaje en la bandeja `Leídos`; si está vacía,
   usar el menú de tres puntos del primero de `Sin leer` y seleccionar `Marcar como leído`.
4. Capturar el asunto del primer mensaje leído disponible y abrir su menú de tres puntos.
5. Seleccionar la acción visible `Marcar como no leído`.
6. Seleccionar `Sin leer` y confirmar que el mismo asunto aparece allí.
7. Usar el menú de tres puntos de ese mensaje en `Sin leer` y seleccionar `Marcar como leído`
   para restaurarlo, incluso si falla la expectativa, y mantener el dato idempotente.

## Diseño aprobado

- `src/pages/messages/messages.page.ts` contiene los locators, acciones, esperas y lecturas de la bandeja.
- El test conserva las expectativas de estado y no contiene selectores, URLs ni credenciales.
- No se crea workflow: el escenario coordina dos Page Objects existentes para login y una pantalla de mensajes, sin una regla reutilizable adicional.
- El texto de la acción se localiza exactamente como `Marcar como no leído`; no se utiliza un selector genérico que coincida con varias acciones.

## Criterios de aceptación

- El título comienza con `TC0086BERSA`.
- Se realiza login UI real, sin inyección de sesión API.
- El mismo mensaje seleccionado queda visible en `Sin leer` después de la acción y se abre de nuevo para restaurar su estado leído.
- La precondición recupera un mensaje leído cuando la bandeja `Leídos` está vacía.
- La restauración se ejecuta mediante `finally` para no dejar mensajes en `Sin leer` ante un fallo.
- El Page Object no usa esperas fijas.
- El test no imprime contraseña, token ni cookies.
- Formato, lint, typecheck y la prueba UI aplicable finalizan correctamente.

## Decisiones abiertas

- Si el ambiente no dispone de ningún mensaje en ninguna bandeja, el test debe fallar con un
  diagnóstico explícito de datos de prueba insuficientes.
