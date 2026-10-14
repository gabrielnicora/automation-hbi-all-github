# Diagnóstico de fallos API y UI

## Estado

Implementada y validada el 10 de septiembre de 2026 como patrón transversal para todos los
tests API. Actualización del 22 de septiembre de 2026: cada request API se muestra como paso
secuencial con un adjunto request/response propio; los fallos además conservan un JSON agregado
al nivel del test para compartir la secuencia completa. Desde el 17 de septiembre de 2026,
los tests UI también detectan la página de error `/error` con el mensaje `¡Ups!`; esta ampliación
queda pendiente de validación en BERSA QA.

## Objetivo

Conservar en el reporte HTML de Playwright evidencia suficiente para investigar cambios de
contrato, errores confusos del backend y fallos de inestabilidad UI, sin agregar ruido ni datos
de sesión a ejecuciones exitosas.

## Comportamiento esperado

- Cada request realizada por un cliente API registra en memoria el paso, método, path,
  headers de aplicación, body, duración, HTTP status, headers y body de respuesta.
- Cada request aparece como un `test.step` independiente, numerado en orden de inicio y con su
  método, endpoint y nombre funcional. Los pasos conservan la jerarquía del paso funcional que
  invocó al cliente. El adjunto JSON de ese step presenta juntos request, response o error de
  transporte, para inspeccionar un endpoint sin buscarlo en una secuencia plana. El archivo usa un
  nombre único con número de secuencia y endpoint; el step señala el archivo y línea del cliente
  funcional que originó la llamada, no el transporte transversal.
- El registro conserva todas las requests del intento para poder reconstruir la secuencia
  que llevó al fallo, incluso cuando la última respuesta fue HTTP 200 y el error aparece en
  una validación posterior.
- El detalle sanitizado de cada intercambio se adjunta dentro de su step para mostrar juntos el
  endpoint, request y response/error. El JSON agregado de toda la secuencia se adjunta únicamente
  cuando el estado final del test difiere del esperado.
- Cada retry genera su propia evidencia e informa el número de intento.
- Las ejecuciones exitosas no generan el JSON agregado; conservan el detalle individual dentro
  de cada step API.
- Errores de transporte sin respuesta también quedan registrados.
- En todos los tests UI, la fixture observa la página principal y las ventanas emergentes. Si una
  navegación llega a `/error`, registra la evidencia de la página `¡Ups!` del intento, incluidos
  el identificador hexadecimal precedido por `#` y el UUID entre paréntesis cuando estén
  presentes.
- La detección de `¡Ups!` falla explícitamente el intento, aunque la acción posterior no hubiese
  generado otro error. El runner puede aplicar únicamente los retries de escenario configurados;
  no hay retries internos.

## Datos visibles

Los datos funcionales QA/DEV son dummy. No se enmascaran cuentas, CBU/CUIT, teléfonos,
IDs de producto, `_bcCuil`, `ajax_uuid` ni `idTransaction`. El usuario utilizado se agrega
como anotación de Playwright en todo test API o UI que cargue credenciales.

## Exclusiones obligatorias

No se incluyen valores que permitan reutilizar una sesión o completar autenticación:

- `Authorization`, `Cookie`, `Set-Cookie` y headers equivalentes;
- passwords;
- access tokens, exchange tokens e identificadores usados como access token;
- OTP, master code y códigos de verificación;
- cuerpos de autenticación sin sanitizar.

El usuario de login permanece visible porque es un dato dummy necesario para el análisis.
Los campos Base64 grandes, incluidos el PDF del ticket, el fingerprint de login y valores
codificados como URI `data:*;base64,`, se reemplazan por una marca que informa su cantidad de
caracteres. Un Base64 ocupa
aproximadamente un 33 % más que su binario original; omitirlo evita multiplicar el tamaño
del reporte cuando fallan muchos casos.

## Diseño aprobado

- `src/api/reporting-api-request.ts` centraliza la ejecución HTTP, validación común y
  recopilación sanitizada de intercambios; cada request se presenta como paso Playwright numerado
  con un adjunto request/response individual.
- `src/fixtures/api-test.ts` crea una instancia por test y adjunta la evidencia durante el
  teardown sólo si el test falló.
- Los clientes funcionales conservan endpoints, payloads y códigos de negocio esperados.
- `src/fixtures/test-case.fixture.ts` agrega la anotación de usuario al cargar credenciales.
- `src/pages/error/ups-error.page.ts` reconoce la ruta y extrae el mensaje y sus identificadores;
  `src/fixtures/ups-error-tracker.ts` observa cada página UI, adjunta el diagnóstico y fuerza el
  fallo del intento cuando corresponde.
- No se habilitan traces ni se imprimen bodies mediante `console.log`.

## Criterios de aceptación

- Login, perfil, transferencias y clientes API futuros utilizan el ejecutor transversal.
- Un test exitoso no contiene el adjunto agregado de diagnóstico; los steps API mantienen sus
  adjuntos individuales de request/response.
- Un test fallido contiene la secuencia completa de requests del intento.
- El adjunto muestra request y response, HTTP status, código de negocio e `idTransaction`
  cuando existen.
- El reporte de Playwright identifica cada request por número, método, endpoint y paso funcional;
  el adjunto JSON del step agrupa lo enviado y recibido para ese endpoint, y el adjunto agregado
  conserva toda la secuencia en el mismo orden. Los nombres descargables de los adjuntos por step
  son únicos dentro del intento e incluyen número y endpoint; el título y el campo `source` del
  JSON corresponden al caller API funcional, no al ejecutor transversal.
- El adjunto nunca contiene Bearer/exchange/access tokens, cookies, passwords, OTP, master
  code, códigos de verificación ni Base64 completo.
- El reemplazo de Base64 informa la longitud omitida.
- El reporte de login API y UI identifica el usuario utilizado.
- Todo test UI que navegue a la pantalla `¡Ups!` adjunta `error-ups-intento-<n>.json` con la URL,
  el mensaje y ambos identificadores disponibles, y muestra esas referencias en el error del
  intento.
- Una página que no navega a `/error` no agrega evidencia de `¡Ups!`.
- Formato, typecheck, lint y las regresiones aplicables finalizan correctamente.

## Decisiones abiertas

Ninguna para el alcance implementado.
