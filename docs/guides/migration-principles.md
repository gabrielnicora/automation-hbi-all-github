# Principios de migración

## Prioridad de implementación

Portar el comportamiento ejecutable del legado: pasos, precondiciones, datos, assertions y
efectos. El testigo orienta cómo implementarlo en la arquitectura actual. Las decisiones
explícitas ya aprobadas para el mismo caso siguen aplicando; las diferencias no resueltas
se documentan conservando provisionalmente el código legado.

Ejemplo: si el legado valida una URL después de un clic, conservar esa comprobación mediante
el Page Object; abrir la página sin comprobar su destino no es equivalente. Lo mismo aplica
a tickets, locators, mensajes, cantidades, valores y estados de negocio.

## Qué es un caso testigo

Es un caso implementado en el framework elegido como referencia. Puede tener una validación
QA confirmada, pendiente o impedida por una incidencia externa. Su utilidad arquitectónica
no depende de que el ambiente funcione en esta ejecución.

No copiar automáticamente sus limitaciones, datos, importes ni test.fail/skip. Si el legado
contiene más comprobaciones que el testigo, migrarlas. Si contiene menos, conservar su alcance
y registrar una posible mejora para después; no inventar una expectativa para completar la guía.

## Dudas sin bloqueo

Leer el código legado y los métodos que llama, trasladar lo que ejecutan y anotar la duda en
ambiguities/. Continuar el caso y el lote sin pedir decisión humana previa. No requiere una
especificación individual nueva ni una matriz de análisis.

Si falta una dependencia indispensable, portar lo disponible y registrar la ruta o dato
faltante. No crear un test vacío, un valor ficticio ni una assertion siempre verdadera para
declarar el trabajo terminado. La ausencia material de código no se resuelve con una suposición.

## Adaptaciones técnicas obligatorias

- Playwright Test y TypeScript; banco activo BERSA.
- Reutilizar fixtures y métodos compatibles antes de crear componentes.
- Tests con intención funcional; selectores/esperas en Page Objects y protocolo en clientes API.
- Mantener expectativas de negocio visibles desde el test. Cuando un testigo las encapsula,
  describirlo como implementación actual, sin convertirlo en regla universal.
- Aplicar `SPEC.md`, sección 6.2.2, al código migrado por IA: acciones y lectura sincronizada
  en páginas; valores del caso por parámetros; expectativas funcionales visibles en el test.
  Reutilizar un testigo no autoriza copiar sus desvíos de responsabilidad.
- Adaptar controles con el legado y la evidencia disponible. Un ID no demuestra que haya
  un `<select>` nativo ni habilita `selectOption` por sí solo. Identificar validación pendiente
  cuando falte evidencia UI actual; no inventar una selección ni afirmar equivalencia observada.
- Corregir defectos de implementación identificados con evidencia; un registro de ambigüedades
  documenta dudas funcionales o diferencias, pero no reemplaza esa corrección.
- No reintroducir banks, factories, gateways o capas delegadoras.
- Reemplazar sleeps por estados observables; no agregar networkidle como espera universal.
- Eliminar bucles de retry de requests y escenarios legados: Playwright reintenta el escenario.
- Reutilizar el polling y reenvíos de desafíos de segundo factor ya aprobados. Son coordinación
  de códigos, no un permiso para repetir la mutación ni copiar retries internos.
- Mantener ejecución secuencial mientras existan identidades compartidas.
- Preservar credenciales, tokens, cookies y códigos fuera de diagnósticos no sanitizados.

## Estados separados

Portado completado significa código trasladado con sus comprobaciones. No equivale a ejecución
QA aprobada. Una duda para revisión, falta de datos QA o error externo debe quedar identificada
sin debilitar las assertions ni impedir portar los demás casos.
