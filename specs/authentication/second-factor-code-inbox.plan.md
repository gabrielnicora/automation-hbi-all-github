# Bandeja de códigos de segundo factor — BERSA

## Estado

Política aprobada para implementación y reutilización por capacidades API y UI. Reemplaza
la lectura, filtrado y polling particulares de cada caso para códigos entregados por
`util.codes`.

## Objetivo

Proveer una única forma segura y diagnóstica de recuperar códigos de segundo factor que
BERSA publica en la bandeja `util.codes`, sin inyectar sesión API en UI ni duplicar polling
en perfiles, transferencias, préstamos, tarjetas u otras capacidades.

## Alcance

- Banco: BERSA; ambiente: QA.
- Endpoint de consulta: `/api/v1/execute/util.codes`.
- Payload aprobado: el request común de BERSA con `ajax_uuid` y `xAppVersion`; el Bearer
  de la sesión se envía únicamente en el header `Authorization`. Este contrato coincide
  con el flujo legado validado para TC0001BERSA.
- Canales: SMS y email, identificados por `channel` (normalizado internamente como tipo) y
  `origen`.
- El código se mantiene sólo en memoria y se entrega al flujo que debe ingresarlo o usarlo.

No abarca el endpoint o control que dispara cada desafío: esa acción pertenece al Page
Object o cliente API de la capacidad que la solicita.

## Comportamiento aprobado

1. Antes de solicitar un código, el flujo obtiene una línea base de códigos que coinciden
   con el tipo y origen esperados.
2. El flujo específico dispara el envío inicial mediante UI o su endpoint aprobado.
3. La bandeja consulta `util.codes` cada 2,5 segundos: hasta 12 veces (30 segundos)
   después del envío inicial y hasta 24 veces (60 segundos) después de cada reenvío.
4. Un código es válido sólo si es nuevo respecto de la línea base, su `create` es posterior
   al último registro de esa línea base y coincide con canal/tipo y origen normalizados:
   email en minúsculas y teléfono sólo con dígitos.
5. Si no llega, el flujo puede reenviar el desafío y repetir la espera de 60 segundos.
   Se permiten como máximo tres envíos totales por intento del escenario.
6. Si se agotan los envíos, falla con un diagnóstico de indisponibilidad del código.

El reenvío sólo repite el desafío de segundo factor. Nunca repite automáticamente una
transferencia, pago, cambio de domicilio, préstamo ni ninguna otra mutación de negocio.

## Master code

- `SECOND_FACTOR_METHOD=common|master-code`, con `common` como valor por defecto.
- La configuración privada mantiene el master code y los usuarios dummy habilitados.
- Con `master-code`, sólo los usuarios habilitados lo reciben; los demás siguen el
  recorrido común.
- El master code puede reemplazar el OTP de autorización de una operación, pero no un
  código de verificación específicamente emitido por otro desafío, salvo que la
  especificación de la capacidad lo apruebe.

## Diseño aprobado

- `SecondFactorCodeInboxApi` es dueño del contrato de `util.codes`, normalización,
  línea base, filtrado, espera y error de código ausente.
- Una política de segundo factor central es dueña de la elección common/master-code y del
  límite de reenvíos.
- Los Page Objects conservan botones `Enviar`/`Reenviar`, campos y postestados UI.
- Los clientes de capacidad conservan únicamente endpoints y payloads propios.
- Un workflow puede coordinar el Page Object y la bandeja cuando un recorrido UI lo
  requiera; no contiene locators ni endpoints.
- Desafío sin reenvío (`requestSingleCode`, migración acelerada L11, 21 de octubre de 2026):
  cuando el disparo no puede repetirse desde la misma pantalla (correo de recupero de
  usuario, TC0048/TC128240BERSA), la bandeja toma la línea base, el flujo dispara una sola vez
  y se esperan las 12 consultas iniciales; si no llega, falla con el mismo diagnóstico. El
  criterio puede pedir la propiedad `codeNoFormat` cuando el registro es un mensaje y no un
  código. Detalle en `docs/guides/ambiguities/lote-l11-ui.md`.

## Diagnóstico y seguridad

- Los errores informan tipo esperado, origen normalizado, envíos realizados, consultas y
  timeout; no incluyen el valor del código, password, token ni cookies.
- Respuestas HTTP, JSON o contratos inválidos se diferencian de un código no entregado.
- Los fallos transitorios de transporte al consultar la bandeja se reintentan dentro de la
  misma consulta; no reintentan el envío ni la mutación de negocio.
- No hay pausas fijas: el único polling autorizado es el de `util.codes` con intervalos y
  límites explícitos (30 segundos iniciales y 60 segundos por reenvío).
- Los escenarios permanecen secuenciales mientras compartan identidad o estado.

## Uso en especificaciones de capacidad

Cada Markdown que use segundo factor debe referenciar esta especificación y definir sólo:

- acción que dispara el desafío;
- tipo y origen esperados;
- si el reenvío es aplicable;
- postestado después de ingresar el código;
- limpieza o efecto persistente de su operación.

## Criterios de aceptación

- No hay polling ni lectura de `util.codes` duplicados fuera de la capacidad central.
- Un código previo nunca satisface un desafío nuevo, aunque `util.codes` devuelva una
  ventana de registros distinta o desordenada después del envío.
- El reenvío está limitado a tres solicitudes totales por intento.
- Una mutación de negocio no se reintenta desde segundo factor.
- Los reportes preservan el contexto de diagnóstico sin revelar valores de sesión o código.

## Decisiones abiertas

Antes de conectar una nueva capacidad, verificar en QA el nombre exacto de `tipo`, el
origen y el control/endpoint que dispara su desafío. La ausencia de un registro coincidente
debe diagnosticarse como datos o entrega de código insuficientes, no suponerse como éxito.
