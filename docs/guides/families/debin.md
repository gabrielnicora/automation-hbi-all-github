# Familia funcional: DEBIN

## Regla de uso para Kiro

Aplicar la [guía general](../README.md) y los [principios](../migration-principles.md).
Los recorridos siguientes describen testigos, no límites para otras variantes: preservar
pasos, datos, efectos y assertions del código legado y sus helpers, salvo una decisión
explícita que los reemplace para ese mismo caso. Ante dudas, portar lo ejecutable, registrar
en [ambiguities](../ambiguities/README.md) y continuar sin consultar ni exigir tablas o una
especificación individual nueva. No portar retries internos; usar Playwright y reutilizar
la política central de segundo factor. No heredar automáticamente marcas de fallo esperado.

## Propósito

Migrar órdenes DEBIN y distinguir la generación de la orden de su aceptación, consulta
posterior o modalidad recurrente.

## Caso testigo

TC-011-1-0002-UNE: generación de una orden DEBIN por API seleccionando CBU.

Referencias:

- specs/payments/debin-generation.api.plan.md
- src/api/payments/debin-generation.api.ts
- src/test-data/debin-generation-data.ts
- tests/api/011-payments/should-generate-debin.api.spec.ts

## Flujo testigo

1. Cargar credenciales y datos.
2. Autenticar por API.
3. Consultar y resolver la cuenta origen.
4. Ejecutar `payment.idCard.link`; el testigo siempre incluye esa sincronización.
5. Ejecutar debin.generate con CBU y CUIT destino.
6. Validar HTTP 200, objeto JSON y COR000I.

El alcance actual es ARS 1. No incluye DEBIN recurrente, aceptación, históricos ni otros
bancos.

La operación es `debinGenerationApi.generate(session, debinGenerationData)` y el test exige
`result.wasAccepted === true`. El cliente encadena `widgets.accounts`, `payment.idCard.link`
y `debin.generate`; reutilizarlo sólo si la variante conserva ese protocolo.

## Datos y payload

El legado aporta cuenta origen, CBU y CUIT destino. El payload observado incluye tipo CBU,
importe 1, moneda ARS, concepto ALQ, referencia TestAutomation, vigencia 720 y versión
1.74.0. Estos valores son del testigo; confirmar cada uno para otra orden.

El UUID debe generarse por request según la arquitectura actual. No trasladar UUID literal ni
hasta cuatro reintentos internos del legado.

## Arquitectura

El endpoint y payload pertenecen al cliente API. Los datos pertenecen a src/test-data y el
test sólo expresa generación y resultado. No crear workflow ni gateway para una sola operación.

## Variantes

Separar generación, aceptación, consulta de históricos y recurrente. Si cambia el destinatario,
la vigencia, el concepto, el importe o la moneda, crear datos y criterios propios.

## Ambigüedades

La respuesta actual sólo tiene éxito técnico COR000I como señal provisional. Si el legado
espera identificador, estado o comprobante, portar esa assertion además del éxito técnico.
Si no los comprueba, no inventarlos; registrar una posible mejora para revisión posterior.

## Checklist

- [ ] Cuenta, CBU y CUIT se validan antes de mutar.
- [ ] La mutación ocurre una vez por intento.
- [ ] No hay retry interno heredado.
- [ ] No se confunde generación con aceptación.
- [ ] El reporte excluye sesión y credenciales.
