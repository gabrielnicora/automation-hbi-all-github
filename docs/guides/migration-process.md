# Proceso de migración acelerada

## 1. Elegir referencia y portar

Comprobar primero el ID en el
[catálogo canónico](../../specs/migration/migratable-cases.md). Si no figura o su definición
aplicable está ignorada, deshabilitada, obsoleta o deprecada, excluirlo del lote e informar el
motivo; no analizarlo como caso pendiente de portado. Resolver el canal con la precedencia y la
excepción definidas en ese catálogo.

Una definición requiere método y `@Test` activos. Las marcas sobre el método o su clase la
excluyen; un helper deprecado no excluye por sí solo al caso. `WEB` se informa e implementa como
UI.

Localizar el caso en la copia del canal indicada en las
[rutas locales del legado](README.md#ubicación-del-legado-dentro-del-repositorio).

Identificar familia y canal mientras se lee el caso legado y sus dependencias. Reutilizar la
fixture y los métodos compatibles del testigo, extender sólo lo necesario y conservar el
identificador y las comprobaciones del caso.

Para UI, elegir exclusivamente el método del ID correspondiente en `FrontTest.java`. Los
métodos UI con ID duplicado en otras clases están deprecados y quedan fuera del portado; otras
clases legacy se consultan sólo para leer helpers/dependencias invocados desde `FrontTest`.

No hay etapa obligatoria de análisis previo por caso, tabla comparativa ni aprobación humana.
La comparación ocurre al traducir el código.

## 2. Conservar la intención de cada comprobación

Traducir URL, locator, ticket, código de negocio, mensaje y valor esperado que el legado
comprueba. Mantener sus precondiciones, limpieza y efectos, con las adaptaciones técnicas de
migration-principles.md. Las decisiones explícitas que reemplazaron un comportamiento para
ese mismo caso siguen vigentes.

No sustituir assertions por ausencia de excepciones, catch silencioso, skip o resultado
siempre verdadero. No copiar automáticamente una marca test.fail de otro testigo.

## 3. Resolver dudas operativamente

Si el propósito o un comentario contradice el código, portar lo ejecutable y registrar la
duda para después. Si existe una decisión explícita vigente para ese caso, aplicarla. Si falta
físicamente una dependencia, registrar el faltante, portar lo disponible y continuar el lote.

Usar un documento por lote o caso en ambiguities/; una sección por duda con caso/canal,
ruta y método legado, comportamiento conservado y pregunta pendiente es suficiente.

## 4. Comprobar el lote

Ejecutar los checks estáticos del proyecto y las pruebas funcionales aplicables que el ambiente
permita. Revisar que cada assertion legada siga representada; no producir una tabla por caso.
Verificar los testigos afectados cuando cambie un componente compartido.

El resumen del lote distingue catalogados, elegibles, excluidos, sin fuente, portados y
validados. El número de IDs catalogados no equivale a migraciones terminadas.

Un fallo de QA se informa conservando el test. Un resultado no ejecutado, esperado fallido o
con datos faltantes no se declara validado.

## 5. Entregar y continuar

Informar casos portados, estado de comprobaciones y ubicación de dudas/faltantes. La revisión
humana posterior resuelve esas dudas y actualiza la especificación y el código cuando proceda.
