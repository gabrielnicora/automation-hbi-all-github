# Familia funcional: perfil

## Regla de uso para Kiro

Aplicar la [guía general](../README.md) y los [principios](../migration-principles.md).
Los recorridos siguientes describen testigos, no límites para otras variantes: preservar
pasos, datos, efectos y assertions del código legado y sus helpers, salvo una decisión
explícita que los reemplace para ese mismo caso. Ante dudas, portar lo ejecutable, registrar
en [ambiguities](../ambiguities/README.md) y continuar sin consultar ni exigir tablas o una
especificación individual nueva. No portar retries internos; usar Playwright y reutilizar
la política central de segundo factor. No heredar automáticamente marcas de fallo esperado.

## Propósito

Agrupar cambios y consultas del perfil sin mezclar mutaciones con lecturas. La familia
actual contiene teléfono API, domicilio UI y preferencias API.

## Casos testigo

- TC-013-1-0001-UNE: cambio de teléfono API con segundo factor y persistencia.
- TC-013-2-0001-UNE: cambio de domicilio UI con segundo factor.
- TC-013-1-0002-UNE: consulta de preferencias de notificaciones API.

Referencias:

- specs/profile/change-phone.api.plan.md
- specs/profile/change-address.ui.plan.md
- specs/profile/notification-preferences.api.plan.md
- src/api/profile/
- src/pages/profile/
- tests/api/013-profile/
- tests/ui/013-profile/

## Cambio de teléfono API — TC-013-1-0001-UNE

Flujo:

1. Login API.
2. Leer el teléfono actual.
3. Elegir el siguiente teléfono autorizado sin volver inmediatamente al anterior.
4. Solicitar autorización OTP.
5. Enviar código al teléfono nuevo.
6. Leer util.codes con criterio de operación, usuario y teléfono.
7. Actualizar mobilePhone.
8. Releer el perfil.
9. Confirmar persistencia.

Usa la política de segundo factor. El loader de teléfono y el cliente deben normalizar
números JSON a strings cuando corresponda. La mutación no se reintenta internamente; los
reintentos completos del runner tienen efectos sobre el dato.

La superficie actual es `phoneChangeApi.changePhone(session, username)` y el test exige
`result.isPersisted === true`. Reutilizar la rotación de `test-data/private/phone-change.json`.
No confundir la autorización de operación con la verificación del teléfono nuevo: son
desafíos distintos. El master code autorizado para un OTP no reemplaza automáticamente la
verificación del nuevo teléfono. Leer la política central antes de extenderlos.

## Cambio de domicilio UI — TC-013-2-0001-UNE

Flujo:

1. Generar datos dummy con `createRandomAddress()` (calle, número, piso y departamento).
2. Login UI real con `startPage` y `loginPage`.
3. Autenticar además una sesión API independiente mediante `loginApi.authenticate`.
4. Leer el teléfono vigente mediante `profileMobilePhoneApi.readCurrentPhone(apiSession)`.
5. Abrir con `changeAddressPage.open()`; `completeAddress(address)` devuelve el domicilio
   cargado: provincia CORRIENTES, primera localidad válida y código postal derivado de ella.
6. Resolver el SMS obligatorio de este caso con `secondFactorChallengeApi.resolveOperationOtp`,
   la sesión API, el usuario y `criteria: { origin: phone, type: 'smsCode' }`.
7. Entregar a esa política los callbacks `requestInitialSecondFactorCode()` y
   `requestReplacementSecondFactorCode()` del Page Object. La política obtiene la línea base
   antes del envío; sólo admite un código nuevo del origen y tipo esperados.
8. `expectReviewedAddress(submittedAddress)` comprueba los datos de revisión.
9. `enterSecondFactorCode(code)` confirma y `expectAddressChangeConfirmation()` verifica el éxito.

Los selectores están en ChangeAddressPage y los datos en change-address-data. No usar API
para inyectar la sesión UI ni agregar una capa workflow sólo para delegar.

No implementar consultas de `util.codes` en el test. La política existente limita a tres
envíos del desafío, con espera inicial de 30 segundos y de 60 por reenvío, cada 2,5 segundos.
Esto no permite repetir la mutación. El testigo tiene timeout de 240 segundos y deja el
domicilio modificado por decisión explícita; no agregar una restauración por analogía con
mensajes. En variantes, conservar los datos y efectos de su propio legado.

## Preferencias API — TC-013-1-0002-UNE

Es una consulta de preferencias. Separar lectura de cualquier futura mutación. El cliente
contiene endpoints y parsing; el test valida la estructura y el resultado funcional aprobado.

`notificationPreferencesApi.getMessagePreferences(session)` ejecuta primero
`preferences.notifications.configuration.pre` (payload funcional vacío, versión `1.95.0`)
y luego `preferences.notifications.configuration.modify.pre` (`communicationType: 'message'`,
versión `1.79.1`). Ambos usan el prefijo `/api/v1/execute/`, sesión y UUID por request.

El cliente exige `data.communicationConfigurations` como objeto y devuelve `hasSmsPreference`,
`hasMailPreference` y `hasPushPreference`. El test exige los tres indicadores en `true` porque
comprueban que `subscribedSMS`, `subscribedMail` y `subscribedPush` son booleanos, no que las
suscripciones estén activas. Un valor de suscripción `false` es válido. Esta decisión para
TC-013-1-0002-UNE reemplaza la expectativa histórica; no inventar campos `message/accepted/userBlock`.

## Reutilización y límites

Compartir la política de login, segundo factor, datos privados y diagnóstico. No compartir
payloads entre teléfono, domicilio y preferencias. Si una variante cambia de lectura a
mutación, conservar las comprobaciones de su legado y separarla de la consulta; no exigir
una aprobación o especificación nueva antes de portarla.

## Ambigüedades

Registrar dudas sobre datos autorizados, rotación, criterios del OTP (nunca su valor), persistencia, campos de domicilio,
localidad, respuestas vacías y códigos de negocio. Si el legado muta un dato pero el testigo
sólo consulta, conservar el legado provisionalmente y dejar la duda visible.

## Checklist

- [ ] Se distinguió lectura de mutación.
- [ ] Se identificó el canal.
- [ ] Se definió segundo factor y criterio del código.
- [ ] Se consideraron efectos de retry sobre datos persistentes.
- [ ] Los tests no contienen endpoints ni selectores.
- [ ] La persistencia se verifica sólo cuando pertenece al caso.
