# Evaluador del agente de automatización

Mide si un agente automatiza correctamente con este framework y si `AGENTS.md` y el catálogo
`.agents/index/` lo ayudan. La decisión y sus límites están en `SPEC.md`, sección 3,
"Evaluación del agente de automatización".

```bash
pnpm run eval -- tc0033-mastercard-detail-ui
pnpm run eval -- tc0033-mastercard-detail-ui --model sonnet --budget 3 --keep
```

Requiere el CLI `claude` autenticado y acceso a UAT (ambiente `qa`, URLs por defecto).

## Qué hace cada corrida

1. Ejecuta el login UI `TC0220001BERSA` contra UAT. Si falla, informa "ambiente no disponible".
2. Crea una copia descartable de `HEAD` sin historial de Git (`git archive`), quita el caso de la tarea y regenera el
   catálogo para que la respuesta no quede en `.agents/index/`.
3. Corre `claude -p` con el prompt de la tarea, herramientas acotadas y presupuesto máximo.
   El agente no ejecuta escenarios contra el banco.
4. Corrige: `pnpm run check`, caso en `--list`, título, rutas modificadas, ausencia de
   selectores/URLs/esperas en el test, fixtures esperadas y escaneo de secretos.
5. Ejecuta el caso producido contra UAT con `PW_RETRIES=0`.

Resultados en `evals/results/<fecha>-<tarea>/` (no se versiona): `report.md`, `report.json`,
`diff.patch`, `transcript.jsonl` y los logs de cada paso.

## Tareas de reparación

Una tarea con `replace` rompe un texto exacto del código en la copia (por ejemplo un locator de
un Page Object, simulando un cambio de pantalla) y pide repararlo. Sólo en estas tareas el
agente puede ejecutar contra UAT los specs listados en `agentMayRun`; la corrección verifica
además que no haya ejecutado otros, que `unchangedPaths` (el test) siga intacto y, con
`locatorRepair`, que el locator nuevo no use XPath ni esperas fijas y use `getBy*`.

## Agregar una tarea

Crear `evals/tasks/<id>.json` con `remove` (archivo y `describeContaining` del caso a quitar),
`prompt` (descripción funcional, como en Xray) y `expected` (`testCaseId`, `project`,
`allowedPaths`, `fixtures`, `run`). Para reparación: `replace`, `agentMayRun` y en `expected`
`unchangedPaths` y `locatorRepair` (`file`, `brokenMarker`). Usar sólo casos de consulta: una tarea con un caso que
modifica usuarios requiere una decisión previa en la especificación.
