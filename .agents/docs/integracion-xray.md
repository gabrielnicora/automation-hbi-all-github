# Publicación futura de resultados en Xray — Banco Entre Ríos

## Propósito y autoridad

Definir el diseño y las decisiones necesarias para publicar resultados del framework BERSA en Xray.
Esta guía orienta el trabajo futuro desde `.agents`; no reemplaza
`SPEC.md` ni aprueba contratos de integración.
Antes de implementar, crear y aprobar la especificación correspondiente bajo
`specs/`, resolviendo las decisiones de este documento.

Actualmente el proyecto utiliza los proyectos Playwright `api` y `ui`, reportes
list, HTML y JUnit, y títulos con identificadores `TC<numero>BERSA`. No existen
comandos `xray:upload` ni configuración de publicación de resultados implementados.

El servidor opcional `mcp-xray/`, aprobado en `specs/tooling/xray-mcp.md`, administra casos en
Jira/Xray y es una capacidad distinta: no publica resultados de Playwright ni resuelve las
decisiones de esta guía.

## Diseño recomendado

Separar la ejecución funcional de la publicación de resultados:

```text
Tests API/UI → reporte local → validación y transformación → envío explícito a Xray
                                      ↓
                              vista previa sin red
```

- Mantener el envío apagado por defecto en local y CI. Conservar como propuesta
  `XRAY_UPLOAD_ENABLED=true` para habilitarlo expresamente.
- Con el envío deshabilitado, no autenticar contra Jira/Xray ni exigir secretos.
- Permitir generar y validar resultados localmente sin habilitar el envío.
- La vista previa debe funcionar sin credenciales y sin llamadas de red; validar
  estructura y configuración local no equivale a validar permisos en Jira.
- Ubicar la publicación fuera de Page Objects, fixtures y clientes del banco.
  Un script posterior a la ejecución es suficiente inicialmente; no crear un
  framework genérico, repositorio independiente o CI Component preventivamente.
- Mantener los reportes actuales. Si se elige JSON de Playwright, agregarlo con
  una ruta explícita y comprobar el formato de la versión instalada.

La transformación de JSON de Playwright a JSON de Xray permite controlar el vínculo
con casos existentes y la semántica de resultados. Es una opción razonable, pero
debe compararse con la importación JUnit disponible en Xray, dado que ya generamos
JUnit. Elegir según trazabilidad y estados requeridos; no añadir dos vías de envío.

## Trazabilidad propuesta

Mantener `TC<numero>BERSA` como identidad local y selector de datos privados.
El identificador Jira es una identidad distinta: no deducirlo del número local.

Los tags nativos con formato `@<PROJECT_KEY>-<numero>` son una opción sencilla para
vincular un test con un caso Xray confirmado. `PROJECT_KEY` corresponde a Jira,
no necesariamente al código BSC. No inventar claves provisionales
en tests reales. Tags funcionales y de suite son independientes de ese vínculo.

Antes de adoptar esta convención, definir qué hacer con casos sin vínculo, múltiples
claves en un test y una misma clave repetida entre tests o proyectos API/UI.
Validar la clave completa; buscar solamente un prefijo no valida el formato.
El filtrado por `--grep` usa expresiones regulares: evitar que una selección de
un caso también incluya claves con el mismo prefijo numérico.

## Consideraciones de diseño

| Punto               | Evaluación para este proyecto                                                                                                                                  |
| ------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Cloud y Server/DC   | Tienen contratos y autenticación diferentes. No asumir que cambiar una variable de tipo basta ni implementar ambos sin necesidad confirmada.                   |
| Generación JSON     | `--reporter=json` por sí solo no garantiza el archivo esperado. Configurar `outputFile` o la variable oficial de salida y preservar los otros reportes.        |
| Campos Jira         | Versión, Dispositivo, Banco, IDs de campos y opciones dependen de la instancia. Confirmar cuáles son obligatorios.                                             |
| Banco y proyectos   | El alcance es exclusivamente BERSA, con los proyectos Playwright `api` y `ui` y la estructura de fixtures vigente.                                             |
| Estados             | Confirmar los estados válidos de la instancia antes de definir el mapeo de resultados.                                                                         |
| Retries             | No alcanza con mapear el estado de un intento. Resolver resultado final, flaky, fallos esperados, interrupciones, skips y errores globales.                    |
| Seguridad TLS       | Mantener validación TLS y configurar la CA interna cuando corresponda; no copiar la desactivación de certificados como solución habitual.                      |
| CI                  | Los artefactos deben pertenecer a la corrida y proyecto correctos y conservarse aunque fallen los tests. El orden manual no sustituye dependencias explícitas. |
| Creación automática | Una clave vacía no debe habilitar creación hasta aprobar esa política y los campos requeridos.                                                                 |

## Decisiones pendientes antes de implementar

1. **Instancia:** Cloud o Server/DC, versión, URL, autenticación autorizada,
   proyecto Jira y casos reales de prueba. Confirmar endpoints y payloads.
2. **Formato y vínculo:** JSON transformado o JUnit; tags o mapeo externo;
   cardinalidad, claves duplicadas y política para tests sin vínculo.
3. **Resultados:** estados válidos y reglas para retries, flaky, fallos esperados,
   omitidos, interrumpidos, ausencia de resultados y fallos de hooks o del runner.
4. **Test Execution:** existente, nuevo o ambos; separación o consolidación de
   API/UI; Test Plan; metadatos y campos obligatorios de Jira.
5. **Reenvíos:** cómo identificar una publicación y recuperar una ejecución creada
   si falla el envío posterior. No repetir creaciones tras un timeout ambiguo.
6. **CI:** plataforma y activación; dependencias de artefactos; si un fallo de
   publicación falla el pipeline. Conservar por separado el resultado de los tests.
7. **Evidencias:** datos permitidos, limpieza y retención. No subir mensajes de error,
   stdout, screenshots o adjuntos completos sin revisar información sensible.

Estas preguntas no bloquean conservar esta guía. Sí bloquean implementar el
comportamiento que dependa de sus respuestas, conforme a `AGENTS.md` y `SPEC.md`.

## Etapas propuestas y criterios de aceptación

1. Aprobar la especificación y un ejemplo real de correspondencia local/Xray.
2. Preparar la salida local y la vista previa: sin red, sin secretos, validación de
   reporte ausente o inválido, claves y políticas de resultados. No interpretar un
   reporte vacío como una ejecución exitosa por defecto.
3. Implementar el envío al destino aprobado y verificarlo en una ejecución de prueba.
   Registrar la clave resultante y detectar respuestas fallidas sin exponer secretos.
4. Integrar CI con artefactos por corrida/proyecto, publicación explícita y la
   política de fallos acordada. Evitar resultados antiguos y sobrescrituras API/UI.

Las verificaciones deberán cubrir también reporte con tests fallidos, éxito tras
retry, skip, interrupción, vínculo ausente o duplicado y reenvío tras fallo parcial.
La suite funcional debe seguir ejecutándose sin disponibilidad de Xray.

Consultar Test Sets/Plans para decidir qué ejecutar y extraer un publicador reutilizable
quedan como posibilidades posteriores, sujetas a una necesidad y especificación nuevas.

## Fuentes técnicas consultadas

- [Reporters de Playwright](https://playwright.dev/docs/test-reporters): múltiples
  reporters y salida JSON explícita.
- [Importación de resultados en Xray Cloud](https://getxraydocs.atlassian.net/wiki/spaces/XRAYCLOUD/pages/44566897):
  formatos admitidos y referencia a Test Execution existente.
- [API de Xray Cloud](https://swagger.cloud.getxray.app/): contrato Cloud que deberá
  contrastarse con la instancia elegida.
- [Integración de Xray Server/DC](https://docs.getxray.app/space/XRAY/301435680/Integration%2Bwith%2BTeamCity):
  formatos de importación y distinción entre Server y Cloud.

La documentación pública respalda las opciones técnicas; no confirma la configuración,
permisos, campos ni estados de la futura instalación BSC.
