# Servidor MCP para Jira/Xray

## Estado y alcance

Decisión del usuario, 22 de octubre de 2026: se conserva `mcp-xray/` como herramienta opcional
para que un agente autorizado trabaje con casos de prueba en Jira Server/DC con Xray.

Esta capacidad permite:

- verificar la conexión y el usuario autenticado;
- descubrir campos del tipo de issue `Test`;
- listar casos y filtrar su estado de automatización;
- crear casos manuales con pasos;
- actualizar el campo `Automatizado`.

No publica resultados de Playwright, no modifica reporters ni vincula automáticamente los IDs
`TC<numero>BERSA` con claves Jira. Esa integración continúa fuera de alcance y se documenta en
`.agents/docs/integracion-xray.md`.

## Configuración y seguridad

- El servidor es opcional y no se inicia durante los tests ni en CI.
- Cada usuario crea `.kiro/settings/mcp.json` a partir del ejemplo versionado. El archivo real se
  ignora y nunca contiene valores compartidos por Git.
- `XRAY_JIRA_PAT` es obligatorio en ejecución, pertenece al usuario y sólo se inyecta mediante el
  host MCP o el entorno del despliegue.
- El ejemplo permanece `disabled: true`, no contiene un PAT real y no deshabilita TLS.
- La validación TLS permanece activa por defecto. Para una CA interna se usa
  `NODE_EXTRA_CA_CERTS`. El servidor no ofrece una variable para desactivarla.
- Sólo las operaciones de lectura pueden figurar en `autoApprove`. Crear o actualizar casos
  requiere confirmación del host MCP.
- Las claves de proyecto y de Test se validan antes de construir JQL o paths. Los valores de
  labels se escapan como literales JQL.
- Cada request tiene un timeout configurable mediante `XRAY_REQUEST_TIMEOUT_MS`, con 30 segundos
  por defecto. Los errores HTTP no devuelven el body completo de Jira.
- Si Jira crea un Test pero falla la carga de un paso, el error conserva la clave creada y la
  cantidad de pasos cargados. El servidor no reintenta automáticamente la creación.

## Instalación y ejecución

`mcp-xray` es un paquete del workspace y utiliza el `pnpm-lock.yaml` único de la raíz. Se instala
junto con el framework mediante:

```powershell
pnpm install --frozen-lockfile
```

`tsx`, usado para ejecutar los tests del paquete, depende de `esbuild`. pnpm `11.24.0` tiene
`strictDepBuilds` activo por defecto, por lo que `pnpm-workspace.yaml` autoriza exclusivamente el
script de instalación de `esbuild` mediante `allowBuilds`. No se permite globalmente la ejecución
de scripts de dependencias.

El host lo ejecuta desde su paquete, no desde las dependencias de la raíz:

```text
pnpm --dir mcp-xray run dev
```

Los directorios generados `mcp-xray/node_modules/` y `mcp-xray/dist/` no se versionan.

## Validación y CI

- `pnpm --dir mcp-xray run typecheck` y `pnpm --dir mcp-xray test` deben completar sin errores.
- El job `quality` ejecuta ambos chequeos además de los del framework.
- El typecheck no autentica contra Jira ni requiere PAT, VPN o acceso de red a la instancia.
- La conexión real y las operaciones de escritura no se ejecutan automáticamente en CI.

## Criterios de aceptación

1. El código fuente permanece versionado bajo `mcp-xray/` y sus dependencias quedan resueltas en
   el `pnpm-lock.yaml` único de la raíz.
2. La instalación congelada permite el build requerido por `esbuild` y rechaza scripts de otras
   dependencias que no tengan una aprobación explícita.
3. `.kiro/settings/mcp.json` y los artefactos locales quedan ignorados.
4. Existe un ejemplo seguro, deshabilitado y sin secretos, con el comando de paquete correcto.
5. El README distingue el MCP de la futura publicación de resultados.
6. CI ejecuta typecheck y tests unitarios sin requerir credenciales ni conectarse a Jira.
7. Las entradas inválidas, timeouts, errores HTTP y creaciones parciales producen errores
   accionables sin exponer el PAT ni el body completo de Jira.
