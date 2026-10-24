# Familia funcional: Home Banking

## Regla de uso para Kiro

Aplicar la [guía general](../README.md) y los [principios](../migration-principles.md).
Los recorridos siguientes describen testigos, no límites para otras variantes: preservar
pasos, datos, efectos y assertions del código legado y sus helpers, salvo una decisión
explícita que los reemplace para ese mismo caso. Ante dudas, portar lo ejecutable, registrar
en [ambiguities](../ambiguities/README.md) y continuar sin consultar ni exigir tablas o una
especificación individual nueva. No portar retries internos; usar Playwright y reutilizar
la política central de segundo factor. No heredar automáticamente marcas de fallo esperado.

## Propósito

Migrar funcionalidades generales de la pantalla principal o consultas públicas de Home
Banking que no pertenecen a una familia transaccional más específica.

## Caso testigo

TC0159BERSA: consulta anónima de términos y condiciones generales por API.

Referencias:

- specs/home/terms-and-conditions.api.plan.md
- src/api/home/terms-and-conditions.api.ts
- tests/api/005-home/should-get-terms-and-conditions.api.spec.ts

## Consulta API testigo

1. No autenticar: el servicio es anónimo.
2. Ejecutar el endpoint aprobado de términos y condiciones.
3. Enviar headers y versión que pertenecen al cliente API.
4. Validar HTTP 200, objeto JSON, COR000I y contenido mínimo aprobado.
5. No mezclar esta consulta con login o navegación UI.

El endpoint, payload y parsing pertenecen a TermsAndConditionsApi. El test sólo expresa que
la consulta pública está disponible y que el resultado tiene la estructura esperada.

Superficie concreta: `termsAndConditionsApi.getGeneralConditions()` devuelve `hasContent`,
que el test exige en `true`: `data._termAndCondition` debe ser string no vacío tras `trim()`.
No se comprueba el texto legal completo ni una longitud mínima distinta de cero.

El cliente hace POST a `/api/v1/execute/get.temsAndConditions.anonymous.ForFunctionality`
(conservar la grafía `tems`). Envía `lang: 'es'`, `functionalityCode: 'GENERAL_CONDITIONS'`,
`channel: 'frontend'`, `xAppVersion: '1.88.0'` y `ajax_uuid` nuevo, sin Authorization.
Son datos del testigo: otra consulta conserva su payload y expectativas legadas.

## Variantes

Separar términos generales, términos específicos, acceso público, home autenticado y
errores de disponibilidad. No agregar credenciales sólo porque otras familias las utilizan.

## Ambigüedades

Registrar endpoint anónimo, versión, contenido mínimo, respuesta vacía y si una consulta
requiere autenticación. Si un caso legado consulta la pantalla pero el testigo usa API, la
diferencia de canal debe quedar explícita.

## Checklist

- [ ] Se confirmó si el caso es anónimo o autenticado.
- [ ] El test no contiene endpoint ni payload.
- [ ] La respuesta vacía tiene significado aprobado.
- [ ] No se mezclan términos con navegación de Home.
- [ ] El cliente contiene protocolo y sanitización.
