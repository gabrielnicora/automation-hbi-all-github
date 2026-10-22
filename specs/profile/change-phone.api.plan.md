# Cambio de teléfono de perfil por API — Banco Entre Ríos

## Estado

Adaptada y validada en BERSA mediante `TC0001BERSA`. La consulta central de segundo factor
está alineada con el contrato observado en QA (`channel: smsCode`, `code`/`codeNoFormat`) y
el cambio de teléfono se completa correctamente. La validación funcional fue confirmada por
el usuario el 16 de septiembre de 2026.

## Objetivo

Automatizar `TC0001BERSA`: autenticar al usuario, seleccionar un teléfono autorizado
diferente al actual, completar el segundo factor y confirmar el cambio mediante API.

## Alcance

- Banco: Banco Entre Ríos (BERSA).
- Caso: `TC0001BERSA`.
- Ambiente: QA.
- Canal: API.
- Resultado: el nuevo teléfono queda persistido.

Banco Santa Cruz, Banco Santa Fe, otros bancos y una abstracción multibanco quedan fuera
de alcance.

## Comportamiento esperado

1. Cargar las credenciales BERSA del caso.
2. Realizar login API y obtener una sesión autenticada.
3. Consultar `/api/v1/execute/util.logger.read.transactions` para obtener
   `data.userData.mobileNumber`.
4. Seleccionar el siguiente teléfono privado autorizado en rotación, evitando volver
   inmediatamente al número anterior cuando el caso se ejecuta varias veces.
5. Solicitar el OTP de la operación mediante el mecanismo efectivo de segundo factor.
6. Ejecutar `/api/v1/execute/preferences.userData.mobilePhone.sendCode`.
7. Recuperar desde `util.codes` el código de confirmación asociado al teléfono nuevo.
8. Ejecutar `/api/v1/execute/preferences.userData.mobilePhone.update`.
9. Releer el perfil y comprobar que el teléfono quedó persistido.

Si BERSA representa `mobileNumber` y `origin` como números JSON, se normalizan a string en
la frontera del cliente API, como ya hacía la implementación heredada.

## Segundo factor aprobado

Este caso referencia la política transversal de
`specs/authentication/second-factor-code-inbox.plan.md`. Define sólo sus dos desafíos
propios: OTP de autorización de operación y código de verificación del teléfono nuevo.

- `SECOND_FACTOR_METHOD=common|master-code`, con `common` por defecto.
- `test-data/private/second-factor.json` contiene master code y usuarios habilitados.
- Si se solicita `master-code` y el usuario está habilitado, se utiliza master code.
- Si el usuario no está habilitado, se selecciona el recorrido común antes de mutar.
- El master code reemplaza sólo al OTP de la operación.
- El código de verificación del teléfono proviene de `util.codes`, salvo en `TEST_ENV=une-dev`,
  donde se usa el código fijo del ambiente (ver la política transversal).
- El polling se aplica únicamente al esperar códigos.
- La solicitud `sendCode` puede reintentarse de forma acotada cuando BERSA devuelve
  `COR020W`, que el flujo legado clasifica como respuesta reintentable de datos/servicio.
  Cada reintento obtiene un OTP de operación nuevo; la mutación `update` no tiene retry
  automático dentro de un intento.
- Se solicita un SMS y se espera un código nuevo conforme a la política transversal:
  30 segundos para el envío inicial y 60 segundos después de cada reenvío, consultando
  cada 2,5 segundos y con hasta tres envíos totales. Un error HTTP o de contrato falla el
  intento; no se interpreta como ausencia de SMS.

## Diseño implementado

- `src/api/profile/phone-change.api.ts` contiene endpoints, payloads y validación del
  protocolo propios; la bandeja, polling y selección common/master-code son transversales.
- El cliente recibe `APIRequestContext`, sesión, usuario y datos de prueba necesarios.
- La política transversal de segundo factor se carga desde `src/config/` o
  `src/test-data/` y se entrega mediante la fixture.
- `src/fixtures/api-test.ts` entrega `phoneChangeApi`.
- No se crea gateway, clase base, perfil bancario ni workflow que delegue una sola llamada.
- Los teléfonos candidatos permanecen en `test-data/private/phone-change.json`.
- El cliente relee el perfil y devuelve `isPersisted: boolean`, sin exponer teléfonos
  ni lanzar por una discrepancia de persistencia. El test exige que ese resultado sea
  verdadero mediante un mensaje seguro. Esto reemplaza la validación funcional dentro
  del cliente, que devolvía únicamente el literal `true`.

El test utiliza `src/fixtures/api-test.ts`, `LoginApi` y `PhoneChangeApi`. No existen capas
bancarias, factories, gateways ni configuración de capacidades globales.

## Códigos de negocio aprobados

Cada request exige HTTP 200 y acepta, según el paso:

- `COR000I`;
- `COR013I`;
- `API690W`;
- `API691W`;
- `API692W`.

Los valores históricos no vacíos de `ajax_uuid` y `xAppVersion` se conservan dentro del
cliente API mientras el backend los requiera.

## Criterios de aceptación

- El test comienza con `TC0001BERSA` y contiene sólo pasos funcionales.
- El timeout del escenario contempla las dos etapas de segundo factor y permite distinguir
  una indisponibilidad del código de un timeout genérico del runner.
- El teléfono elegido difiere del actual, pertenece al conjunto privado autorizado y se
  selecciona en rotación para no reusar inmediatamente un número rechazado o recién usado.
- La espera de OTP tiene timeout claro.
- Ausencia de código, HTTP inválido, JSON inválido y código de negocio inesperado producen
  errores diferentes.
- Las requests de mutación no se reintentan dentro de un intento; el escenario fallido
  completo puede repetirse según la política del runner.
- El cliente relee el perfil y el test confirma la persistencia mediante una assertion.
- El caso no se ejecuta simultáneamente con otro escenario que use la misma identidad.
- Endpoints y payloads se modifican sólo en `phone-change.api.ts`.
- El test no atraviesa factories ni configuración bancaria.
- Formato, lint, typecheck y `TC0001BERSA` finalizan correctamente.

## Datos de prueba y resultados

- Teléfonos, usuarios, passwords, OTP, master code y códigos de verificación son datos dummy
  no productivos creados por el equipo.
- Los errores no repiten passwords ni códigos dummy. Los tokens y cuerpos de autenticación
  sin sanitizar no se imprimen ni se adjuntan. Los datos funcionales dummy se conservan en
  el diagnóstico transversal de tests fallidos.
- Los endpoints internos involucrados están autorizados exclusivamente en QA.
- Los datos QA bajo `test-data/private/` se versionan para distribuir la versión ejecutable.
  Nunca se incorporan datos provenientes de producción.

## Errores y retries

- Un timeout de OTP se reporta como indisponibilidad del segundo factor.
- HTTP inesperado, JSON inválido o ausencia de campos se reportan como error de contrato.
- Un código de negocio inesperado identifica endpoint y paso; el body sanitizado queda en
  el adjunto transversal del intento fallido.
- `COR020W` durante `sendCode` se informa como solicitud de código reintentable y, si se
  agotan cuatro intentos, como error específico de entrega del código de verificación.
- Si `COR020W` identifica el campo `mobilePhone`, el caso falla inmediatamente como dato
  QA inválido y no reintenta, porque el OTP ya fue validado y repetirlo no modifica ese dato.
- El polling espera únicamente la aparición de códigos.
- Las requests de mutación fallan inmediatamente.
- Los reintentos del escenario completo están autorizados, incluso si un intento previo
  alcanzó a modificar el teléfono. Cada intento vuelve a leer el estado actual y selecciona
  un teléfono autorizado diferente. Los intentos quedan visibles en el reporte.

Durante una ejecución integral con dos workers, el login previo recibió `COR002E`. El
código no se agregó a los resultados aceptados ni se reintentó la operación. La suite
completa de BSC finalizó correctamente al aplicar la política secuencial definida en
`SPEC.md`. Esa evidencia histórica no valida BERSA.

## Decisiones abiertas

Queda pendiente analizar y confirmar qué códigos de negocio se aceptan en cada endpoint.
Hasta resolverlo se conserva la lista actual; no se agregan códigos ni se restringen por
paso suponiendo significados. También se debe confirmar la equivalencia de endpoints,
payloads y segundo factor en BERSA. Estas revisiones no bloquean la configuración del
runner, pero sí impiden declarar validado el escenario en BERSA.

Decisión del usuario, 21 de octubre de 2026: en `une-dev` el código fijo del ambiente se usa tal
cual como código de verificación del teléfono nuevo. En `qa` se conserva la validación legado:
el código leído de `util.codes` debe contener `-`. Reemplaza la decisión abierta sobre el
formato del código de verificación en `une-dev`.
