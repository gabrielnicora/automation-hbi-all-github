# Familia funcional: Mis Productos

## Regla de uso para Kiro

Aplicar la [guía general](../README.md) y los [principios](../migration-principles.md).
Los recorridos siguientes describen testigos, no límites para otras variantes: preservar
pasos, datos, efectos y assertions del código legado y sus helpers, salvo una decisión
explícita que los reemplace para ese mismo caso. Ante dudas, portar lo ejecutable, registrar
en [ambiguities](../ambiguities/README.md) y continuar sin consultar ni exigir tablas o una
especificación individual nueva. No portar retries internos; usar Playwright y reutilizar
la política central de segundo factor. No heredar automáticamente marcas de fallo esperado.

## Propósito

Migrar accesos y acciones sobre productos contratados. El caso actual valida una redirección
externa, no la baja efectiva de un producto.

## Caso testigo

TC0075BERSA: abrir Baja / Arrepentimiento, aceptar la advertencia y validar la página pública.

Referencias:

- specs/mis-products/should-open-cancellation-page.ui.plan.md
- src/pages/mis-products/cancellation.page.ts
- tests/ui/010-mis-products/should-open-cancellation-page.ui.spec.ts

## Flujo testigo

1. Login UI real.
2. Abrir Mis Productos.
3. Seleccionar Baja / Arrepentimiento.
4. Esperar el modal Baja y arrepentimiento.
5. Confirmar la advertencia de salida.
6. Esperar una nueva pestaña.
7. Validar URL y título de la página pública.
8. Cerrar la pestaña externa.

El Page Object encapsula popup, nueva pestaña y validación. El test no contiene URL, selector
ni credencial. No se completa ni se envía una baja.

## Variantes

Separar acceso, confirmación, formulario, envío, arrepentimiento y cancelación. No asumir que
la apertura de la página pública significa que el producto fue dado de baja.

## Ambigüedades

Registrar si se abre nueva pestaña o la misma, si cambia el título, si la URL tiene parámetros
temporales y si existe un formulario posterior. Validar la superficie estable aprobada, no
detalles internos del sitio externo.

## Checklist

- [ ] Login UI real.
- [ ] Modal y advertencia se esperan por estado observable.
- [ ] Nueva pestaña se gestiona desde el Page Object.
- [ ] La pestaña externa se cierra.
- [ ] No se afirma una baja que el caso no ejecuta.
- [ ] Tokens, cookies y URLs temporales no se adjuntan.
