# Login API de Banco Entre Ríos con captura de Bearer token

## Estado

Adaptada a BERSA y validada en QA el 8 de septiembre de 2026 mediante `TC-002-1-0001-UNE`.

## Objetivo

Autenticar `TC-002-1-0001-UNE` mediante `APIRequestContext` y devolver una sesión mínima
reutilizable sin exponer credenciales, exchange tokens ni access tokens.

## Alcance

- Banco: Banco Entre Ríos (BERSA).
- Caso: `TC-002-1-0001-UNE`.
- Canal: API; tag: `@regression`. Comparte ID con el login UI por representar el mismo caso.
- Ambiente: QA.
- Base API: `https://apitest01.bancoentrerios.com`.
- Resultado: access token no vacío y header Bearer.

Banco Santa Cruz, Banco Santa Fe, otros bancos, inyección de sesión UI, login completo,
datos personales y casos negativos quedan fuera de alcance.

## Protocolo aprobado

1. Ejecutar `POST /api/v1/execute/session.login.step1` con el usuario.
2. Obtener `data._exchangeToken`.
3. Ejecutar `POST /api/v1/execute/session.login.step2` con usuario, contraseña y header
   `Authorization: exchange <token>`.
4. Validar el resultado de negocio y la continuidad exacta del exchange token.
5. Ejecutar `POST /api/v1/execute/session.login.step3` con el fingerprint aprobado.
6. Si existe `data.generalConditionsText`, ejecutar
   `POST /api/v1/execute/session.login.step4` con `acceptConditions: true`.
7. Obtener `data._accessToken` de la última respuesta y construir `Bearer <accessToken>`.

Cada paso exige HTTP 200 y código de negocio `COR000I`.

## Evidencia conservada

La configuración del contexto utiliza la URL API QA predeterminada de BERSA —reemplazable
por `API_BASE_URL`—, `ignoreHTTPSErrors: true` y `Content-Type: application/json`. Su valor
y el protocolo completo fueron validados en BERSA mediante el escenario de login.

Una ejecución sin el fingerprint aprobado llegó hasta step 3 y produjo `API949E`. El
significado del código continúa sin documentación. El fingerprint Base64 hardcodeado de la
implementación de referencia completó históricamente el login en BSC y fue validado en
BERSA mediante `TC-002-1-0001-UNE`.

El 8 de septiembre de 2026 se aprobó conservar ese valor hardcodeado: responde
correctamente en el alcance vigente y no se implementará generación dinámica sin una nueva
decisión explícita. Esto reemplaza su carácter temporal y la expectativa de sustituirlo por
una estrategia de generación.

## Diseño implementado

- `src/api/authentication/login.api.ts` conoce los cuatro endpoints, headers,
  payloads y validaciones del protocolo.
- La URL base API pertenece a `src/config/environment.ts`.
- `src/fixtures/api-test.ts` crea y libera `APIRequestContext` y entrega `loginApi`.
- `src/models/authenticated-session.ts` representa el resultado mínimo reutilizable.
- No se crea `AuthenticationGateway`: existe una única implementación API y no hay una
  necesidad de sustitución.
- No se crea una clase por step ni un workflow delegador.
- Los parsers validan `unknown` en la frontera sin casts que asuman tokens existentes.

El test de login utiliza `src/fixtures/api-test.ts` y `LoginApi`. Perfil reutiliza el mismo
cliente mediante la fixture pública; no existen `BankConfig`, gateways ni contratos
legacy.

## Criterios de aceptación

- El test no contiene URL, endpoints, payloads, credenciales ni fingerprint.
- El título comienza con `TC-002-1-0001-UNE`.
- Cada respuesta HTTP y campo requerido se valida antes de continuar.
- Cada step exige `COR000I`.
- El exchange token de step 2 coincide exactamente con el de step 1.
- Step 4 se ejecuta únicamente cuando step 3 contiene `generalConditionsText`.
- El access token proviene de la última respuesta ejecutada.
- Los errores identifican caso y step. Ante un test fallido, el diagnóstico incluye los
  cuerpos sanitizados, nunca credenciales ni tokens.
- El reporte identifica mediante una anotación el usuario dummy utilizado.
- El resultado contiene access token y header Bearer.
- Cambiar un endpoint requiere modificar solamente `login.api.ts`.
- El test llega al cliente API sin factories ni selección bancaria.
- Formato, lint, typecheck y el escenario API BERSA finalizan correctamente.

## Seguridad y datos

- Las credenciales se leen desde `test-data/private/bersa.json`.
- El export QA contiene datos dummy no productivos y se versiona para distribuir la versión
  ejecutable.
- El fingerprint hardcodeado y validado con BERSA queda aislado dentro de la funcionalidad
  de autenticación y no se imprime ni se expone desde los tests.
- Los errores no repiten passwords dummy. Ningún mensaje o adjunto contiene exchange
  tokens, access tokens ni cuerpos de autenticación sin sanitizar; el usuario permanece
  visible.
- La aceptación eventual de términos continúa considerándose una acción con efecto funcional.

## Decisiones abiertas

Ninguna para el alcance BERSA.
