# Filtro de fondos comunes de inversión por riesgo — UI BERSA

## Estado

Adaptada a BERSA bajo `src/pages/investments/` y validada en QA el 8 de septiembre de 2026
mediante `TC-007-2-0004-UNE`.

## Objetivo

Validar que, al dejar seleccionado únicamente el riesgo moderado en “Información de
fondos”, el listado muestre uno o más fondos y todos correspondan al riesgo moderado.

## Alcance

- Banco: Banco Entre Ríos (BERSA).
- Caso: `TC-007-2-0004-UNE`.
- Ambiente: QA.
- Canal: UI.
- Riesgo esperado: moderado.

Banco Santa Cruz, Banco Santa Fe, otros bancos y la parametrización multibanco quedan fuera
de alcance.

## Comportamiento esperado

1. Seleccionar las credenciales mediante `TC-007-2-0004-UNE`.
2. Autenticar al usuario mediante el login UI real.
3. Ingresar por `Inversiones` y luego `Invertí en Fondos`.
4. Confirmar que “Información de fondos” está disponible.
5. Utilizar la cuenta comitente seleccionada por defecto.
6. Dejar activo únicamente el filtro de riesgo moderado.
7. Confirmar que los demás filtros están inactivos.
8. Esperar la finalización observable de la carga del listado antes de evaluar si hay
   datos. Distinguir listado disponible, listado vacío confirmado y error explícito.
9. Confirmar que existe al menos un fondo.
10. Confirmar que cada fondo visible informa riesgo moderado, normalizando mayúsculas,
    minúsculas y acentos.

La navegación directa a `investment/fund` no forma parte del recorrido: la sesión UI real
requiere ingresar mediante el menú.

## Diseño implementado

- `src/pages/investments/investment-funds.page.ts` contiene navegación, locators,
  filtros, espera de estado y lectura del listado.
- Los textos `CONSERVADOR`, `MODERADO` y `DINÁMICO`, así como los selectores, viven en el
  Page Object o en un modelo local de la misma funcionalidad.
- `src/fixtures/ui-test.ts` entrega `investmentFundsPage`.
- El test expresa el riesgo esperado y conserva los `expect` sobre cantidad y contenido.
- Las filas se localizan por su rol accesible y la celda de riesgo por la posición del
  encabezado `Riesgo` dentro de cada tabla; no se asume una columna fija entre secciones.
- No se crea gateway, factory, configuración bancaria ni workflow: el comportamiento está
  contenido en un único Page Object.
- XPath se admite sólo mientras no exista un locator accesible estable mejor y la decisión
  permanezca documentada.

El test utiliza `src/fixtures/ui-test.ts`, `StartPage`, `LoginPage` e
`InvestmentFundsPage`. Los selectores y textos pertenecen al Page Object; no existe
configuración bancaria.

## Estados y errores

La automatización distingue:

- página o filtros no disponibles;
- mensaje explícito de recuperación de fondos;
- listado que no termina de cargar;
- listado vacío para el riesgo seleccionado;
- filtro que no queda en el estado esperado;
- fila con un riesgo diferente o desconocido.

Si aparece:

> Ocurrió un error al recuperar los fondos. Por favor, intentá nuevamente más tarde.

el Page Object falla inmediatamente con una causa de inestabilidad de la aplicación y
conserva el texto visible. No se espera que una acción posterior termine por timeout.

Si el listado carga sin fondos moderados, el test falla con una causa de datos de prueba no
disponibles. No se recarga la página ni se repite internamente la operación.

La ejecución histórica de BSC del 1 de septiembre de 2026 completó correctamente el
filtrado y validó el locator contextual del grupo `RIESGO:`, el estado seleccionado basado
en `fill` y la normalización de riesgos. Esa evidencia no valida BERSA.

La primera ejecución BERSA del 8 de septiembre de 2026 mostró fondos moderados, pero el
locator heredado no los reconoció porque asumía una cuarta celda y una estructura `div`
específica. La evidencia accesible mostró que las tablas pueden ubicar `Riesgo` en otra
columna. Se reemplazó esa suposición por la resolución de la columna según su encabezado y
el escenario finalizó correctamente.

## Criterios de aceptación

- `TC-007-2-0004-UNE` ejecuta el comportamiento aprobado.
- La página muestra el título esperado.
- Sólo el filtro moderado queda seleccionado.
- El listado contiene al menos una fila.
- Todas las filas visibles corresponden a riesgo moderado.
- El test produce mensajes diferentes para página no disponible, error de recuperación,
  filtro incorrecto, listado vacío y riesgo incorrecto.
- Los selectores se modifican únicamente en `investment-funds.page.ts`.
- El test no conoce configuración bancaria ni composición de fixtures.
- La contraseña dummy no se repite en errores; tokens y cookies de sesión no se imprimen ni
  se adjuntan.
- Formato, lint, typecheck y `TC-007-2-0004-UNE` finalizan correctamente.

## Seguridad y datos

- Las credenciales se leen desde `test-data/private/bersa.json`.
- El export QA contiene datos dummy no productivos y se versiona para distribuir la versión
  ejecutable.
- Screenshots y videos se conservan únicamente ante fallo.
- El test no adjunta tokens, cookies ni contenido de sesión generado durante la ejecución.

## Paralelismo

No está confirmado si el estado de los filtros se guarda como preferencia del usuario.
Mientras no se compruebe, el caso se ejecuta secuencialmente respecto de otros escenarios
que reutilicen la misma identidad.

## Decisiones abiertas

Identificar y verificar en la UI autenticada la señal de carga finalizada, también
después de filtrar. Un timeout sin filas no demuestra que el listado esté vacío.
La implementación actual aún no distingue esos estados de forma fiable; la decisión
funcional está aprobada, pero su selector o señal técnica requiere evidencia antes
de modificar la espera. No sustituirla por una pausa fija ni asumir que la carga del
documento HTML confirma la carga de los fondos.

Confirmar si los filtros persisten como preferencia antes de habilitar paralelismo con
otros casos de la misma identidad. Esta decisión no afecta la arquitectura implementada.
