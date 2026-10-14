# Cambio de domicilio por UI — Banco Entre Ríos

## Estado

Comportamiento aprobado y validado para `TC0004BERSA`. La implementación contiene la
navegación, carga aleatoria del formulario y reutilización de la política transversal de
segundo factor. La ejecución del 16 de septiembre de 2026 completó el SMS, confirmó el
cambio y verificó el mensaje de éxito en QA. No duplica la consulta de `util.codes`.

## Objetivo

Automatizar el cambio de domicilio del perfil mediante Home Banking BERSA, usando datos
dummy generados durante el escenario y confirmando el desafío de segundo factor por SMS.

## Alcance

- Banco: BERSA; ambiente: QA; canal: UI.
- Caso: `TC0004BERSA`.
- Ruta funcional: Perfil → Modificar domicilio.
- Efecto persistente autorizado: el domicilio puede quedar modificado. No requiere
  restauración, porque el usuario autorizó datos aleatorios y el domicilio puede cambiar
  entre ejecuciones sin afectar otros escenarios.

## Recorrido esperado

1. Iniciar sesión UI con los datos del caso para operar la pantalla y autenticar además
   una sesión API independiente para consultar el teléfono y `util.codes`; la sesión API
   nunca se inyecta en el navegador.
2. Abrir Perfil y la opción `Modificar domicilio`.
3. Seleccionar `Modificar domicilio` en el selector de domicilio actual.
4. Completar calle, número, piso y departamento con datos dummy aleatorios válidos.
   Seleccionar la provincia válida del caso legado (`CORRIENTES`) y la primera localidad
   disponible en QA; el código postal se deriva de la localidad seleccionada.
5. Continuar, leer los datos renderizados en la revisión y verificarlos desde el test.
6. Obtener la línea base del código `smsCode` asociado al teléfono actual del perfil.
7. La acción `Continuar` dispara el desafío SMS mediante
   `/api/v1/execute/secondFactor.otp.sms.sendToken`; la UI debe mostrar el campo
   `SEGUNDO FACTOR` y el aviso de envío al celular.
8. Recuperar únicamente un código nuevo mediante la política de
   `specs/authentication/second-factor-code-inbox.plan.md`, ingresarlo y confirmar.
9. Leer el resultado renderizado y verificar desde el test el mensaje que confirma el cambio.

El tipo `smsCode` y el endpoint de envío fueron observados en QA el 15 de septiembre de 2026. El origen no se hardcodea: se obtiene del teléfono actual del perfil y se normaliza
en la frontera API.

## Segundo factor

Este caso referencia la política transversal de
`specs/authentication/second-factor-code-inbox.plan.md`.

- Desafío: SMS de autorización del cambio de domicilio.
- Tipo esperado: `smsCode`.
- Origen esperado: teléfono actual del perfil, normalizado a dígitos.
- El flujo obtiene la línea base antes de solicitar el SMS.
- Si el código no llega, sólo se reenvía el desafío SMS: hasta tres envíos totales; la
  primera espera consulta `util.codes` durante 30 segundos y cada reenvío durante 60
  segundos, siempre cada 2,5 segundos.
- Nunca se repite automáticamente la mutación de domicilio.
- El master code sólo puede usarse si la política central lo habilita explícitamente para
  este OTP de autorización; no sustituye otros códigos de verificación.

## Diseño

- `src/pages/profile/change-address.page.ts` contiene locators, carga del formulario,
  reenvío, sincronización de pantallas y lectura de los datos renderizados.
- El cliente de perfil obtiene el teléfono vigente necesario para construir el criterio
  de origen; el endpoint y su payload no viven en el test ni en el Page Object.
- La política central entrega el OTP sin exponerlo en mensajes ni reportes.
- Si la coordinación de Page Object, teléfono de perfil y segundo factor queda reutilizada
  por más de un caso UI, se crea un workflow sin locators ni endpoints; para este único
  recorrido se mantiene explícita en el test.

## Criterios de aceptación

- El test comienza con `TC0004BERSA` y no contiene selectores, endpoints ni polling.
- Calle, número, piso y departamento cambian por ejecución y satisfacen los campos
  obligatorios UI; la provincia se fija para el caso y la localidad se toma de las
  opciones disponibles, evitando depender de una localidad inexistente.
- El test verifica que la revisión refleje el domicilio generado antes de solicitar la confirmación.
- Sólo un `smsCode` nuevo, del teléfono esperado, puede satisfacer el desafío.
- La ausencia de código reporta tipo, origen normalizado, consultas y envíos, sin revelar
  el código, sesión ni contraseña.
- El reenvío se limita al SMS y no repite la confirmación de negocio.
- Si el control de reenvío no produce una respuesta HTTP, el caso informa explícitamente
  la falla de entrega SMS sin reintentar la mutación de domicilio.
- El test verifica el mensaje funcional final; el Page Object sólo espera el postestado
  observable y devuelve su texto.
- El test se ejecuta secuencialmente con otros casos que compartan identidad.
- Formato, typecheck y la prueba funcional del caso finalizan correctamente.

## Decisiones reemplazadas

La decisión del usuario de permitir domicilios aleatorios sin reversión reemplaza cualquier
supuesto previo de restaurar el estado. La política transversal reemplaza una lectura o
polling local de `util.codes` para este caso.

La decisión del 28 de septiembre de 2026 de mantener expectativas funcionales en el test
reemplaza la ubicación previa de las verificaciones de revisión y éxito dentro de
`ChangeAddressPage`. La página conserva las esperas de sincronización y devuelve el texto
renderizado para que el escenario lo contraste.
