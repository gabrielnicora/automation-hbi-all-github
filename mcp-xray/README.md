# mcp-xray — Servidor MCP para Jira Server + Xray

Servidor MCP (transporte **stdio**) que le da a un agente/LLM la capacidad de
trabajar contra **Jira Server/DC con el plugin Xray**:

- descubrir los campos del tipo Test de tu proyecto,
- listar los Test Cases (incluyendo los **pendientes de automatizar**),
- crear Test Cases **manuales** con pasos (action / data / expected result),
- marcar un test como automatizado.

El caso de uso: un chat le pide al agente *"revisá los tests pendientes de
automatizar del proyecto X"* o *"creá estos casos de prueba en Xray"*, y el
agente usa estas tools para hacerlo.

> **Autenticación:** cada usuario usa **su propio PAT de Jira**. El token NO se
> hardcodea en el código: se inyecta por variable de entorno desde el host MCP.

> Endpoints validados contra **Jira Server 9.4.13** con Xray (plugin Raven).

---

## 1. Requisitos

- Node.js 20+
- pnpm
- Un **Personal Access Token (PAT)** de tu cuenta de Jira Server con permisos
  para crear/editar issues en el proyecto.

### Cómo generar el PAT en Jira Server/DC

1. En Jira, arriba a la derecha: avatar → **Profile**.
2. Menú lateral → **Personal Access Tokens**.
3. **Create token** → nombre (ej: `mcp-xray`) → definí expiración → **Create**.
4. Copiá el token (se muestra una sola vez).

---

## 2. Instalación

Desde la raíz del repo:

```bash
pnpm --dir mcp-xray install --ignore-workspace
```

(Se instala aislado porque el paquete es autocontenido y no forma parte del
workspace de la suite de tests.)

---

## 3. Configuración en Kiro (`.kiro/settings/mcp.json`)

Agregá el server `xray` dentro de `mcpServers`. **Este archivo está en
`.gitignore`**, así que tu PAT no se commitea.

```json
{
  "mcpServers": {
    "xray": {
      "command": "pnpm",
      "args": ["exec", "tsx", "mcp-xray/src/server.ts"],
      "env": {
        "XRAY_JIRA_BASE_URL": "https://jira.tu-empresa.com",
        "XRAY_JIRA_PAT": "PEGA_AQUI_TU_PAT_DE_JIRA",
        "XRAY_PROJECT_KEY": "XT",
        "XRAY_TLS_REJECT_UNAUTHORIZED": "false"
      },
      "disabled": false,
      "autoApprove": [
        "xray_verificar_conexion",
        "xray_descubrir_campos_test",
        "xray_listar_tests"
      ]
    }
  }
}
```

`autoApprove` solo incluye las tools de **lectura**. Las que escriben en Jira
(`xray_crear_test_manual`, `xray_actualizar_estado_automatizacion`) piden
confirmación cada vez, a propósito.

Después de guardar, reconectá el server desde el panel de MCP de Kiro.

---

## 4. Variables de entorno

### Conexión (obligatorias)

| Variable | Descripción |
|----------|-------------|
| `XRAY_JIRA_BASE_URL` | URL de Jira, sin `/` final. |
| `XRAY_JIRA_PAT` | Tu Personal Access Token. **Por usuario.** |

### Opcionales de conexión

| Variable | Default | Descripción |
|----------|---------|-------------|
| `XRAY_PROJECT_KEY` | — | Proyecto por defecto (ej: `XT`). Se puede pasar por tool. |
| `XRAY_TLS_REJECT_UNAUTHORIZED` | — | `false` si tu Jira usa cert self-signed / CA interna. |

### IDs de custom fields (solo si tu instancia difiere)

Los defaults corresponden a la instancia validada. Si tu Jira usa otros ids,
sobreescribilos. Descubrilos con `xray_descubrir_campos_test`.

| Variable | Default | Campo |
|----------|---------|-------|
| `XRAY_FIELD_CANDIDATO` | `customfield_18831` | Candidato a Automatización |
| `XRAY_FIELD_AUTOMATIZADO` | `customfield_19100` | Automatizado |
| `XRAY_FIELD_REGRESION` | `customfield_18832` | Incluido En Regresión |
| `XRAY_FIELD_CRITICIDAD` | `customfield_19002` | Criticidad |
| `XRAY_FIELD_DISPOSITIVO` | `customfield_18835` | Dispositivo |
| `XRAY_FIELD_EQUIPO` | `customfield_18833` | Equipo Responsable |
| `XRAY_FIELD_PRECONDICION` | `customfield_18834` | Precondición |
| `XRAY_FIELD_TIPO_TEST` | `customfield_18800` | Tipo de Test |

### Valores por defecto al crear un Test

| Variable | Default |
|----------|---------|
| `XRAY_DEFAULT_CANDIDATO` | `Si` |
| `XRAY_DEFAULT_AUTOMATIZADO` | `No` |
| `XRAY_DEFAULT_REGRESION` | `No` |
| `XRAY_DEFAULT_CRITICIDAD` | `Media` |
| `XRAY_DEFAULT_EQUIPO` | `Equipo A` |
| `XRAY_DEFAULT_PRECONDICION` | `N/A` |
| `XRAY_DEFAULT_TIPO_TEST` | `Manual` |

---

## 5. Modelo de "estado de automatización"

En esta instancia el estado se expresa con **dos flags** separados, no con un
único campo:

- **Candidato a Automatización** (`Si` / `No`) → ¿este caso se va a automatizar?
  El "no automatizar" es `No`.
- **Automatizado** (`Si` / `No` / `Ninguno`) → ¿ya está automatizado?

Por lo tanto:

| Estado | Candidato | Automatizado |
|--------|:---------:|:------------:|
| Pendiente de automatizar | `Si` | `No` |
| Automatizado | (cualquiera) | `Si` |
| No automatizar | `No` | — |

`xray_listar_tests` con `estadoAutomatizacion: "pendiente"` filtra exactamente
`Candidato = Si AND Automatizado = No`.

---

## 6. Primer uso (orden recomendado)

1. **Verificá la conexión.** `xray_verificar_conexion` → debe responder tu
   usuario de Jira.
2. **Revisá los campos.** `xray_descubrir_campos_test` → confirma los campos
   obligatorios y sus valores. Si tu instancia usa ids distintos a los defaults,
   ajustá las variables `XRAY_FIELD_*`.
3. **Listá pendientes.** `xray_listar_tests` con `estadoAutomatizacion:
   "pendiente"`.
4. **Creá casos.** `xray_crear_test_manual`.

---

## 7. Tools disponibles

### `xray_verificar_conexion`
Valida PAT + URL contra `/myself`. Sin parámetros.

### `xray_descubrir_campos_test`
Lista los campos del tipo Test del proyecto (obligatorios y valores permitidos).
- `projectKey` (opcional): usa el default si se omite.

### `xray_listar_tests`
Lista Test Cases del proyecto.
- `projectKey` (opcional)
- `estadoAutomatizacion`: `pendiente` | `automatizado` | `todos` (default `todos`)
- `labels` (opcional)

### `xray_crear_test_manual`
Crea un Test (Manual) con los campos obligatorios + sus pasos.
- `projectKey` (opcional)
- `summary` (obligatorio)
- `dispositivo` (**obligatorio**): `Web` | `Mobile Android` | `Mobile iOS` | `API`.
  Si la historia no lo aclara, el agente le pregunta al operador.
- `criticidad` (opcional): `Alta` | `Media` | `Baja`. Default `Media`.
- `description` (opcional)
- `precondicion` (opcional): default `N/A`.
- `equipo` (opcional): `Equipo A` | `Equipo B`. Default `Equipo A`.
- `labels` (opcional)
- `steps` (obligatorio, al menos uno): lista de `{ action, data?, result? }`
  - `action`: el paso a ejecutar
  - `data`: contexto funcional / datos de entrada
  - `result`: resultado esperado (descriptivo, funcional)

El resto de campos obligatorios se completan con los defaults (Candidato=`Si`,
Automatizado=`No`, Regresión=`No`, Tipo de Test=`Manual`).

### `xray_actualizar_estado_automatizacion`
Marca el flag **Automatizado** de un test.
- `testKey` (obligatorio, ej: `XT-123`)
- `automatizado`: `Si` | `No` | `Ninguno`

No toca "Candidato a Automatización".

---

## 8. Ejemplo de prompt al agente

> "Verificá la conexión a Xray. Listame los tests pendientes de automatizar del
> proyecto XT. Después, para la historia de login: creá un test manual 'Login
> exitoso con usuario válido', aplica a Web, criticidad Alta. Pasos: 1) abrir el
> home banking (resultado: se muestra la pantalla de login); 2) ingresar usuario
> y clave válidos y presionar Ingresar (resultado: se muestra el saludo 'Hola' y
> el desktop con las cuentas)."

---

## 9. Notas técnicas de la integración (Jira Server 9.x + Xray)

- **createmeta**: en Jira 9 el endpoint viejo con query params fue removido. Se
  usa `GET /rest/api/2/issue/createmeta/{projectKey}/issuetypes` y
  `.../issuetypes/{typeId}`.
- **Pasos del Test Manual**: se cargan con
  `PUT /rest/raven/1.0/api/test/{key}/step` (uno por paso), con body
  `{ step, data, result }`. El custom field de pasos es de solo lectura vía la
  API REST de Jira.
- **Búsqueda por custom field** en JQL: `cf[NNNNN] = "valor"` (sin el prefijo
  `customfield_`).

---

## 10. Seguridad

- El PAT vive solo en `.kiro/settings/mcp.json` (ignorado por git) o en el
  entorno del despliegue. **Nunca en el código ni en el repo.**
- Cada persona/despliegue usa su propio PAT — así los permisos y la trazabilidad
  en Jira quedan a nombre de quien ejecuta.
- Las tools de escritura no están en `autoApprove`: se confirman manualmente.

---

## 11. Despliegue fuera de Kiro (app en AWS)

Este server usa transporte **stdio**, ideal para que un host lo lance como
proceso hijo. Para exponerlo como endpoint HTTP remoto (varios clientes), hay
que cambiar `StdioServerTransport` por el transporte HTTP (Streamable) del SDK y
resolver el PAT **por request/sesión** en vez de por env global. La lógica de
`xray-client.ts` no cambia: ya recibe la config por parámetro, así que soporta
un PAT por usuario sin reescritura.
