# Fuentes canónicas del workspace

Las rutas de esta guía son relativas a la raíz del workspace. Este archivo dirige la
lectura; no duplica requisitos ni reemplaza las especificaciones.

## Prioridad y alcance

Respetar las instrucciones de sistema y de la herramienta. Dentro de las decisiones
del proyecto, la última decisión explícita del usuario prevalece: actualizar la
especificación afectada antes de implementar un cambio de criterio.

`AGENTS.md` y las instrucciones anidadas aplicables definen cómo trabajar.
`SPEC.md` define el contrato general y las especificaciones de
capacidad precisan su comportamiento. Un conflicto material sin resolución explícita
se consulta fuera de la migración acelerada. Para migrar casos existentes aplica la decisión
de `SPEC.md`, sección 3: conservar el código legado, registrar la duda y continuar sin consulta.

El código describe lo implementado; los resultados de validación describen lo observado.
Si difieren de la especificación, registrar la diferencia. Ejemplos, historial de chat
y resúmenes sirven para localizar fuentes, pero no constituyen contratos aprobados. En migración
acelerada el código legado ejecutable es la referencia funcional autorizada para el portado.

Las ubicaciones vigentes del legado están definidas en `SPEC.md`, sección 3, y detalladas
en `docs/guides/README.md`. Resolverlas desde la raíz de este repositorio.

## Selección de lecturas

Leer sólo lo que la tabla asigna al trabajo, y leerlo completo. Para archivos largos, seguir
el protocolo de rangos consecutivos y control de truncación de `AGENTS.md`. Antes de abrir
código, buscar en el catálogo generado `.agents/index/` (`SPEC.md`, sección 3).

| Alcance del trabajo                                   | Lecturas requeridas                                                                                                                 |
| ----------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------- |
| Cualquier tarea del repositorio                       | `AGENTS.md`, este índice e instrucciones anidadas aplicables.                                                                       |
| Ubicar fixtures, páginas, clientes, tests o datos     | `.agents/index/README.md` y el archivo del catálogo que corresponda.                                                                |
| Agregar o modificar tests                             | `tests/AGENTS.md`, `docs/test-authoring.md` y la especificación de la capacidad.                                                    |
| Crear o modificar Page Objects                        | `src/pages/AGENTS.md`.                                                                                                              |
| Crear o modificar clientes API                        | `src/api/AGENTS.md` y `specs/api/failure-diagnostics.plan.md`.                                                                      |
| Registrar fixtures                                    | `src/fixtures/AGENTS.md`.                                                                                                           |
| Crear o modificar workflows                           | `src/workflows/AGENTS.md`.                                                                                                          |
| Datos de prueba y sus loaders                         | `test-data/AGENTS.md`.                                                                                                              |
| Arquitectura, dependencias, configuración global o CI | `SPEC.md` completo.                                                                                                                 |
| Cierre transversal de encuesta opcional por UI        | `specs/ui/optional-satisfaction-survey.md`.                                                                                         |
| Autoría o revisión de tests                           | `docs/test-authoring.md` y especificaciones de las capacidades afectadas.                                                           |
| Diagnóstico transversal de API y errores UI `¡Ups!`   | `specs/api/failure-diagnostics.plan.md`.                                                                                            |
| Login UI                                              | `specs/authentication/login.plan.md`.                                                                                               |
| Segundo factor por API/UI                             | `specs/authentication/second-factor-code-inbox.plan.md`.                                                                            |
| Login API                                             | `specs/authentication/api-login.plan.md`.                                                                                           |
| Cambio de teléfono por API                            | `specs/profile/change-phone.api.plan.md`.                                                                                           |
| Cambio de domicilio por UI                            | `specs/profile/change-address.ui.plan.md`.                                                                                          |
| Transferencia entre cuentas propias por API           | `specs/transfers/own-account-transfer.api.plan.md`.                                                                                 |
| Términos y condiciones de Home Banking por API        | `specs/home/terms-and-conditions.api.plan.md`.                                                                                      |
| Generación de DEBIN por API                           | `specs/payments/debin-generation.api.plan.md`.                                                                                      |
| Orden de extracción por API                           | `specs/accounts/withdrawal-order.api.plan.md`.                                                                                      |
| Plazo fijo tradicional por API                        | `specs/investments/traditional-fixed-term.api.plan.md`.                                                                             |
| Mensajes recibidos por API                            | `specs/messages/received-messages.api.plan.md`.                                                                                     |
| Preferencias de notificaciones por API                | `specs/profile/notification-preferences.api.plan.md`.                                                                               |
| Pago de tarjeta Visa por otro importe por API         | `specs/cards/credit-card-other-amount-payment.api.plan.md`.                                                                         |
| Pago total de tarjeta Mastercard por UI               | `specs/cards/credit-card-total-payment.ui.plan.md`.                                                                                 |
| Trámites vigentes por API                             | `specs/procedures/current-online-procedures.api.plan.md`.                                                                           |
| Registro desde pantalla principal por UI              | `specs/main-page/should-open-registration.ui.plan.md`.                                                                              |
| Filtro de fondos por UI                               | `specs/investments/fund-risk-filter.ui.plan.md`.                                                                                    |
| Baja y arrepentimiento por UI                         | `specs/mis-products/should-open-cancellation-page.ui.plan.md`.                                                                      |
| Instalación o ejecución del proyecto                  | `README.md` y configuración real pertinente.                                                                                        |
| Evaluación del agente de automatización               | `evals/README.md` y `SPEC.md`, sección 3.                                                                                           |
| Integración futura con Xray                           | `.agents/docs/integracion-xray.md`; es una propuesta, no una capacidad aprobada.                                                    |
| Exploración, generación o depuración con Playwright   | `.agents/skills/playwright-cli/SKILL.md` y las referencias que correspondan según esa skill.                                        |
| Migración acelerada de casos legados                  | `.agents/docs/legacy-migration.md`, `SPEC.md` sección 3, `docs/guides/README.md` y la guía de la familia funcional correspondiente. |
| Elegibilidad y canal de casos a migrar                | `specs/migration/migratable-cases.md`; catálogo cerrado previo a leer o portar cada caso.                                           |
| Avance y lote de cada caso a migrar                   | `docs/guides/migration-status.md`; punto de entrada operativo, subordinado al catálogo.                                             |

Si una capacidad usa otra como precondición y el cambio afecta esa dependencia, leer
también su especificación. Para capacidades nuevas o rutas renombradas, buscar en
`specs/` y actualizar este índice cuando corresponda. La ausencia
de una especificación no autoriza inventar el comportamiento.

Seleccionar otras skills únicamente si el pedido o su alcance las requieren. Este
índice no obliga a leer documentos de capacidades ajenas al trabajo actual.

## Comprobación de cierre

Vincular los criterios aplicables con archivos cambiados y validaciones observadas.
Informar fuentes leídas, skills, criterios, cambios, resultados y asuntos pendientes
de forma breve. Una lectura parcial se declara como parcial; una prueba no ejecutada
se declara como no ejecutada. Ante una fuente obligatoria inaccesible, indicar la ruta
exacta y detener el trabajo dependiente.
