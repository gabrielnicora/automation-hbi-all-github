# Familia funcional: inversiones

## Regla de uso para Kiro

Aplicar la [guía general](../README.md) y los [principios](../migration-principles.md).
Los recorridos siguientes describen testigos, no límites para otras variantes: preservar
pasos, datos, efectos y assertions del código legado y sus helpers, salvo una decisión
explícita que los reemplace para ese mismo caso. Ante dudas, portar lo ejecutable, registrar
en [ambiguities](../ambiguities/README.md) y continuar sin consultar ni exigir tablas o una
especificación individual nueva. No portar retries internos; usar Playwright y reutilizar
la política central de segundo factor. No heredar automáticamente marcas de fallo esperado.

## Propósito

Migrar operaciones y consultas de inversiones manteniendo separados los productos:
fondos comunes y plazo fijo tienen recorridos, datos y resultados distintos.

## Casos testigo

- TC0042BERSA: constitución de plazo fijo tradicional API.
- TC0119BERSA: filtro de fondos por riesgo moderado UI.

Referencias:

- specs/investments/traditional-fixed-term.api.plan.md
- specs/investments/fund-risk-filter.ui.plan.md
- src/api/investments/
- src/pages/investments/
- tests/api/007-investments/
- tests/ui/007-investments/

## Plazo fijo API — TC0042BERSA

Flujo:

1. Login API.
2. Resolver exactamente la cuenta origen.
3. Validar que los datos no sean ambiguos.
4. Ejecutar una vez fixedTerms.make.send.
5. Validar HTTP 200, objeto JSON y COR000I.

El caso es ARS, 30 días, importe 5002, tipo T y renovación N. No incluye UVA, USD,
precancelable, consulta posterior ni saldo. El retry interno de hasta cuatro intentos del
legado no se migra; si se habilita retry, pertenece al runner.

`traditionalFixedTermApi.create(session, traditionalFixedTermData)` devuelve `wasAccepted`,
que el test exige en `true`. La señal actual es COR000I: no inferir ticket, identificador ni
estado. Si otra variante legada sí los comprueba, migrar esas assertions sin esperar otra
definición de QA; registrar sólo las dudas de interpretación.

## Fondos UI — TC0119BERSA

Flujo:

1. Login UI real.
2. Abrir Inversiones y luego Invertí en Fondos.
3. Esperar Información de fondos.
4. Mantener la cuenta comitente por defecto.
5. Seleccionar sólo riesgo moderado.
6. Confirmar filtros activos e inactivos.
7. Esperar el listado con la implementación disponible (ver limitación de carga debajo).
8. Validar una o más filas.
9. Normalizar mayúsculas, minúsculas y acentos del riesgo.

InvestmentFundsPage contiene navegación, locators, filtros, estados de error y lectura de
filas. La columna Riesgo se resuelve por encabezado, no por posición fija. No reemplazar
carga eventual por sleep, networkidle o recarga automática.

El test usa `selectOnlyRisk` y `getVisibleRisks`: exige al menos una fila y que todos los
riesgos sean moderados. No basta comprobar que exista alguna fila moderada entre otras.
La especificación deja abierta una señal fiable de carga finalizada: la implementación
actual no distingue de forma fiable listado vacío de carga incompleta. No presentar ese
requisito como resuelto ni convertir un timeout en lista vacía; registrar la limitación y
continuar el portado conservando las comprobaciones del caso.

## Variantes

- otros riesgos: parametrizar sólo si recorrido y regla son iguales;
- fondos en otra cuenta: dato y precondición propios;
- plazo fijo USD/UVA/precancelable: casos separados si cambia payload o resultado;
- constitución versus consulta: no mezclarlas.

## Ambigüedades

Registrar listado vacío, error explícito, carga incompleta, persistencia de filtros, saldo,
señal de éxito del plazo fijo y disponibilidad de cuenta comitente. El estado vacío no se
confunde con una carga que nunca terminó.

## Checklist

- [ ] Se identificó producto y canal.
- [ ] Se separó consulta de mutación.
- [ ] Se validó moneda, importe, plazo y renovación.
- [ ] Se conserva la expectativa UI y se registra la limitación de vacío/carga si aplica.
- [ ] No hay retry interno de mutaciones.
- [ ] Los filtros y selectores viven en el Page Object.
- [ ] Se respetó la ejecución secuencial si se comparte identidad.
