# Lectura de fuentes durante el portado

| Fuente                           | Uso                                                         |
| -------------------------------- | ----------------------------------------------------------- |
| SPEC.md y decisiones explícitas  | Arquitectura, políticas y excepciones aprobadas             |
| Catálogo de casos migrables      | Elegibilidad, canal declarado y precedencia de búsqueda     |
| Código legado y métodos llamados | Pasos, datos y assertions que se deben trasladar            |
| Testigos y código actual         | APIs disponibles, composición y patrones de implementación  |
| Especificaciones de capacidades  | Contratos concretos y decisiones que reemplazaron el legado |
| Guías de familia                 | Orientación y límites de reutilización                      |

Comprobar primero `specs/migration/migratable-cases.md`. Estas fuentes se consultan durante la
traducción; no se exige una ficha o tabla previa por caso.
El código actual puede tener incidencias externas o validación pendiente: no asumir que todo
componente está funcionalmente validado por existir en el repositorio.

## Qué seguir en el legado

Resolver el origen API o UI desde las [rutas locales del legado](README.md#ubicación-del-legado-dentro-del-repositorio).
Las referencias a archivos internos se resuelven dentro del proyecto legado del canal,
no en otro checkout ni en un repositorio remoto.

Leer el cuerpo del caso, helpers llamados, validadores compartidos y datos usados. Una assertion
puede estar en un helper y debe migrarse igual. Conservar las precondiciones y restauraciones
que tienen efecto, no sólo los clics o requests del cuerpo principal.

Si el título o un comentario promete algo que el código no comprueba, portar el código y anotar
la diferencia. No inventar la comprobación ni eliminar una existente.

## Qué transformar

Ubicar protocolo HTTP en clientes, mecánica UI en Page Objects, datos en loaders y composición
en fixtures. Elegir métodos existentes por compatibilidad real, no sólo por nombre parecido.
Eliminar retries internos legados y usar los del runner; reutilizar la política central de OTP.
Adaptar las esperas fijas a señales observables del mismo resultado.

## Qué registrar

Sólo dudas, diferencias relevantes y faltantes: caso/canal, ruta y método legado, qué se
conservó, efecto conocido y pregunta para revisión humana. Compartir un registro por lote es
válido. No copiar secretos ni cuerpos completos de autenticación al registro.
