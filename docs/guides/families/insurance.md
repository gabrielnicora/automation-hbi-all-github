# Familia funcional: seguros

## Regla de uso para Kiro

Aplicar la [guía general](../README.md) y los [principios](../migration-principles.md).
Los recorridos siguientes describen testigos, no límites para otras variantes: preservar
pasos, datos, efectos y assertions del código legado y sus helpers, salvo una decisión
explícita que los reemplace para ese mismo caso. Ante dudas, portar lo ejecutable, registrar
en [ambiguities](../ambiguities/README.md) y continuar sin consultar ni exigir tablas o una
especificación individual nueva. No portar retries internos; usar Playwright y reutilizar
la política central de segundo factor. No heredar automáticamente marcas de fallo esperado.

## Propósito

Migrar accesos y consultas de seguros desde Home Banking. El test actual es un caso UI de
navegación a detalle y no demuestra contratación, cotización ni siniestro.

## Caso testigo

TC-006-2-0002-UNE: abrir la tarjeta Bolso (seguro de Robo) y validar el detalle de Bolso Protegido.

Referencias:

- src/pages/insurance/insurance.page.ts
- tests/ui/006-insurance/should-open-robbery-insurance-detail.ui.spec.ts
- src/fixtures/ui-test.ts

No hay especificación específica en specs/. Esto no bloquea portar casos existentes:
conservar su código ejecutable y registrar sólo dudas para formalizarlas después.

## Flujo actual observable

1. Login UI real con el caso.
2. Abrir la sección de seguros.
3. Esperar el iframe `iframeQualia` y el encabezado Seguros dentro de él.
4. Esperar la tarjeta Bolso, correspondiente al seguro Robo, y hacer clic.
5. Validar que el detalle muestre el encabezado “Seguro de Bolso” y la descripción
   “Protección para tus pertenencias en la vía pública”.

El legado de Entre Ríos implementa `clickCardSeguroRobo()` y
`validacionDetalleSeguroRobo()`, que comprueba “ESTIMADO CLIENTE”, “LE OFRECE”,
“UN SEGURO DE” y “BOLSO PROTEGIDO”. En la pantalla QA actual la tarjeta aparece como
“Bolso” y abre un detalle con planes; esos textos legados no aparecen. El port usa el
encabezado y la descripción visibles en el detalle actual, y registra la diferencia en
`docs/guides/ambiguities/tc0197bersa-ui.md`. No contiene `test.fail`; el caso debe fallar
normalmente si no encuentra la tarjeta o el detalle.

InsurancePage contiene la mecánica, FrameLocator y locators. Espera que el detalle se
renderice y devuelve sus textos observados; el test verifica el encabezado y la descripción
esperados sin conocer la estructura del iframe.

## Variantes

Separar detalle, contratación, cotización, renovación, baja y siniestro. Separar productos
si cambian los controles o el resultado.

## Ambigüedades

Registrar nombre exacto del producto, disponibilidad de datos, iframe, postestado, si el
detalle es externo y qué campos constituyen la expectativa. Ante dudas, portar las comprobaciones
ejecutables del legado y continuar; sólo una fuente material ausente impide completar esa parte.

## Checklist

- [x] Se preservó el alcance del legado: tarjeta de Robo y validación del detalle.
- [x] El iframe está encapsulado en el Page Object.
- [x] TC-006-2-0002-UNE valida el detalle visible actual y registra la diferencia con las aserciones legadas.
- [x] No se afirma contratación ni cobertura si el caso no las comprueba.
- [x] Las dudas están registradas sin exigir especificación previa para el portado.
