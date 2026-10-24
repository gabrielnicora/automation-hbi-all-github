# Familia funcional: registro

## Regla de uso para Kiro

Aplicar la [guía general](../README.md) y los [principios](../migration-principles.md).
Los recorridos siguientes describen testigos, no límites para otras variantes: preservar
pasos, datos, efectos y assertions del código legado y sus helpers, salvo una decisión
explícita que los reemplace para ese mismo caso. Ante dudas, portar lo ejecutable, registrar
en [ambiguities](../ambiguities/README.md) y continuar sin consultar ni exigir tablas o una
especificación individual nueva. No portar retries internos; usar Playwright y reutilizar
la política central de segundo factor. No heredar automáticamente marcas de fallo esperado.

## Propósito

Migrar accesos públicos al flujo de registro desde la pantalla inicial. Esta familia no
demuestra el alta completa de un cliente en su testigo; una variante legada de alta completa
debe conservar su recorrido y comprobaciones propios.

## Caso testigo

TC-014-2-0002-UNE: abrir el registro desde el enlace público y llegar a validación de identidad.

Referencias:

- specs/main-page/should-open-registration.ui.plan.md
- src/pages/start-page/start.page.ts
- src/pages/registration/identity-validation.page.ts
- tests/ui/014-start-page/should-open-registration.ui.spec.ts

## Flujo testigo

1. Abrir la pantalla inicial BERSA.
2. Esperar el enlace ¿No sos cliente aún? Registrarme.
3. Seleccionar el enlace.
4. Esperar el encabezado Es necesario validar tu identidad para continuar.

El test no usa credenciales ni datos funcionales. StartPage y IdentityValidationPage encapsulan
navegación, locators y lectura de estado.

## Decisión importante

El legado ejecutaba Ingresar y Volver aunque el nombre del caso indicaba registro. La decisión
aprobada prioriza el comportamiento funcional descrito por el caso y la observación QA:
acceso directo al registro. Esta decisión reemplaza el recorrido legado contradictorio.

Para futuras ambigüedades, registrar si el nombre, objetivo y cuerpo legado difieren. En una
migración acelerada se conserva el comportamiento legado salvo que exista una decisión explícita
que lo reemplace, como ocurre aquí.

## Variantes

- acceso al registro;
- validación de identidad;
- formulario de alta;
- rechazo o error de identidad.

No extender este testigo hacia un formulario que no comprueba. Un caso legado que sí lo
completa se porta por separado sin exigir una especificación nueva como prerrequisito.

## Checklist

- [ ] No se usan credenciales si el caso es público.
- [ ] El test no verifica URL interna directamente.
- [ ] El encabezado de destino es el resultado funcional.
- [ ] La contradicción entre nombre y legado quedó documentada.
- [ ] El Page Object contiene locators y esperas.
