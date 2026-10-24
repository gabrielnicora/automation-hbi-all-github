# Familia funcional: autenticación

## Regla de uso para Kiro

Aplicar la [guía general](../README.md) y los [principios](../migration-principles.md).
Los recorridos siguientes describen testigos, no límites para otras variantes: preservar
pasos, datos, efectos y assertions del código legado y sus helpers, salvo una decisión
explícita que los reemplace para ese mismo caso. Ante dudas, portar lo ejecutable, registrar
en [ambiguities](../ambiguities/README.md) y continuar sin consultar ni exigir tablas o una
especificación individual nueva. No portar retries internos; usar Playwright y reutilizar
la política central de segundo factor. No heredar automáticamente marcas de fallo esperado.

## Propósito

Migrar casos que inician o validan una sesión BERSA. La familia cubre login API y UI; el
segundo factor es una capacidad transversal que se reutiliza cuando una operación lo exige.

## Casos testigo

- TC0017BERSA API: login multi-step y sesión Bearer.
- TC0017BERSA UI: login real y captura segura de la sesión.

Referencias:

- specs/authentication/api-login.plan.md
- specs/authentication/login.plan.md
- specs/authentication/second-factor-code-inbox.plan.md
- src/api/authentication/login.api.ts
- src/pages/authentication/login.page.ts
- tests/api/authentication/should-login-and-capture-bearer.api.spec.ts
- tests/ui/authentication/should-login-and-capture-bearer.ui.spec.ts

## Flujo API testigo

1. Leer credenciales del caso.
2. Ejecutar session.login.step1 con el usuario.
3. Extraer exchange token.
4. Ejecutar step2 con contraseña y Authorization exchange.
5. Confirmar continuidad exacta del exchange token.
6. Ejecutar step3 con fingerprint aprobado.
7. Ejecutar step4 sólo si existe generalConditionsText.
8. Obtener access token de la última respuesta.
9. Construir la sesión mínima con accessToken y Bearer.

Cada respuesta exige HTTP 200, JSON válido y COR000I. El fingerprint aprobado permanece
encapsulado en el cliente y no debe aparecer en el test ni en reportes.

## Flujo UI testigo

1. Abrir StartPage.
2. Seleccionar Ingresar.
3. Completar usuario y continuar.
4. Preparar la captura de session.login.step3 antes de enviar contraseña.
5. Completar contraseña.
6. Esperar respuesta HTTP 200 y access token.
7. Esperar que la aplicación persista persist:session.
8. Devolver la sesión observada sin escribir sessionStorage.

Los locators, la captura de respuesta y las esperas viven en StartPage y LoginPage.

## Qué reutilizar

- testCaseId y loader de credenciales;
- modelo AuthenticatedSession;
- LoginApi para consumidores API;
- LoginPage para consumidores UI;
- anotación del usuario dummy;
- sanitización de tokens, cookies, passwords y exchange tokens.

## Qué comparar por variante

- login API o UI;
- pasos adicionales de términos;
- fingerprint o headers;
- respuesta que contiene el token;
- necesidad real de reutilizar la sesión;
- casos negativos, con sus expectativas legadas propias; no convertir el cliente de éxito
  en un validador que acepte cualquier respuesta ni exigir especificación previa al portado.

## Segundo factor

Si el legado contiene OTP o master code, no implementar polling dentro del test. Usar la
política de second-factor-code-inbox y registrar canal, operación, criterio del código,
reenvíos permitidos y limpieza. El login no debe asumir segundo factor si el legado no lo
muestra.

## Ambigüedades frecuentes

- interpretar API949E sin evidencia;
- generar dinámicamente el fingerprint aprobado;
- escribir la sesión UI desde API;
- imprimir el Bearer para diagnosticar;
- tratar aceptación de términos como una lectura sin efecto.

## Checklist

- [ ] El test no contiene credenciales, URLs, endpoints ni tokens.
- [ ] El access token se valida sin entregarlo a matchers.
- [ ] La captura UI se prepara antes de la acción.
- [ ] La sesión persiste antes de continuar.
- [ ] API y UI conservan superficies separadas.
- [ ] Toda duda sobre OTP queda registrada.
