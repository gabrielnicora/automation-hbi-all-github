# Familia funcional: trámites y turnos

## Regla de uso para Kiro

Aplicar la [guía general](../README.md) y los [principios](../migration-principles.md).
Los recorridos siguientes describen testigos, no límites para otras variantes: preservar
pasos, datos, efectos y assertions del código legado y sus helpers, salvo una decisión
explícita que los reemplace para ese mismo caso. Ante dudas, portar lo ejecutable, registrar
en [ambiguities](../ambiguities/README.md) y continuar sin consultar ni exigir tablas o una
especificación individual nueva. No portar retries internos; usar Playwright y reutilizar
la política central de segundo factor. No heredar automáticamente marcas de fallo esperado.

## Propósito

Agrupar consultas y accesos a trámites sin confundir una lista API con la solicitud UI de
un turno. Cada variante debe conservar su resultado observable.

## Casos testigo

- TC0078BERSA: lista de trámites online vigentes API.
- TC0052BERSA: acceso UI a turnos online.

Referencias:

- specs/procedures/current-online-procedures.api.plan.md
- src/api/procedures/current-online-procedures.api.ts
- src/pages/procedures/appointments.page.ts
- tests/api/procedures/should-list-current-online-procedures.api.spec.ts
- tests/ui/procedures/should-open-online-appointments.ui.spec.ts

## Consulta API — TC0078BERSA

1. Login API.
2. Ejecutar onlineProcedure.list con request vacío, UUID y versión aprobada.
3. Validar HTTP 200, objeto JSON y COR000I.
4. `currentOnlineProceduresApi.list(session)` devuelve `wasAccepted`; el test exige `true`.
   El testigo no exige una lista con elementos. Una variante conserva lo que su legado comprueba.

El testigo no agrega detalle, alta, baja ni modificación. Portar esas acciones si pertenecen
a otra variante legada; no portar retries internos.

## Turnos UI — TC0052BERSA

Después del login UI, `appointmentsPage.openExternalAppointments()` abre Trámites → Turnos
online, comprueba el encabezado y prepara la espera de nueva página antes de Aceptar.
Espera `domcontentloaded`, rechaza el título `Página no encontrada | Banco Entre Rios` con
`ExternalAppointmentsUnavailableError` y cierra la pestaña. Actualmente no valida una URL
positiva de destino ni solicita un turno: no afirmar que esas comprobaciones existen.

El testigo está implementado y tiene `test.fail` por la incidencia externa conocida. Sigue
siendo referencia; no heredar automáticamente esa marca ni debilitar el caso migrado. Si
el legado comprueba URL, título, locator o ticket, conservar exactamente esa expectativa
mediante el Page Object, aunque QA falle. La falta de especificación UI no detiene el portado.

## Variantes

Separar consulta de lista, detalle, alta, cancelación y turnos. Parametrizar sólo diferencias
de datos cuando el recorrido y la expectativa sean idénticos.

## Ambigüedades

Registrar estructura de data, lista vacía, versión del endpoint, señal de carga, destino del
turno y si el trámite requiere datos adicionales. No eliminar llamadas del legado sólo porque
el retorno no se usa: pueden validar respuestas o preparar estado en helpers. Mantenerlas
ante dudas y registrar la diferencia; aplicar las sustituciones explícitas del mismo caso.

## Checklist

- [ ] Se definió lista o mutación.
- [ ] API no exige elementos que el contrato no garantiza.
- [ ] UI conserva las comprobaciones de destino del legado, incluso ante fallos externos.
- [ ] No se confunden trámites con turnos.
- [ ] No se portaron retries internos ni se agregó polling para repetir el negocio.
