#!/usr/bin/env node
/**
 * Servidor MCP (stdio) para Jira Server + Xray.
 *
 * Transporte: stdio (el host lo lanza como proceso hijo, igual que playwright-mcp).
 * Autenticación: PAT de Jira por env var XRAY_JIRA_PAT (por usuario, nunca hardcodeado).
 *
 * Tools expuestas:
 *   - xray_verificar_conexion
 *   - xray_descubrir_campos_test
 *   - xray_listar_tests
 *   - xray_crear_test_manual
 *   - xray_actualizar_estado_automatizacion
 *
 * IMPORTANTE: stdout es el canal del protocolo (JSON-RPC). Todo log va a stderr.
 */

import { McpServer } from '@modelcontextprotocol/sdk/server/mcp.js';
import { StdioServerTransport } from '@modelcontextprotocol/sdk/server/stdio.js';
import { z } from 'zod';

import { loadConfig, ConfigError, type XrayServerConfig } from './config.js';
import { validateProjectKey, XrayServerClient } from './xray-client.js';

// ---------------------------------------------------------------------------
// Helpers de respuesta MCP
// ---------------------------------------------------------------------------

type ToolResult = {
  content: Array<{ type: 'text'; text: string }>;
  isError?: boolean;
};

function ok(text: string): ToolResult {
  return { content: [{ type: 'text', text }] };
}

function okJson(data: unknown): ToolResult {
  return { content: [{ type: 'text', text: JSON.stringify(data, null, 2) }] };
}

function fail(err: unknown): ToolResult {
  const message = err instanceof Error ? err.message : String(err);
  return { content: [{ type: 'text', text: `Error: ${message}` }], isError: true };
}

/** Resuelve el projectKey: el pasado por la tool o el default de la config. */
function resolveProjectKey(config: XrayServerConfig, provided?: string): string {
  const key = provided ?? config.defaultProjectKey;
  if (!key) {
    throw new Error(
      'No se indicó projectKey y no hay XRAY_PROJECT_KEY configurado. ' +
        'Pasá el parámetro projectKey o configurá el default.'
    );
  }
  return validateProjectKey(key);
}

// ---------------------------------------------------------------------------
// Registro de tools
// ---------------------------------------------------------------------------

function buildServer(config: XrayServerConfig): McpServer {
  const client = new XrayServerClient(config);
  const server = new McpServer({ name: 'mcp-xray', version: '0.2.0' });

  // --- Verificar conexión / validar PAT -----------------------------------
  server.registerTool(
    'xray_verificar_conexion',
    {
      title: 'Verificar conexión a Jira',
      description:
        'Valida el PAT y la URL configurados haciendo un request a /myself. ' +
        'Devuelve el usuario autenticado. Útil como primer diagnóstico.',
      inputSchema: {},
    },
    async () => {
      try {
        const user = await client.verificarConexion();
        return ok(
          `Conexión OK. Autenticado como ${user.displayName} (${user.name}) en ${config.jiraBaseUrl}.`
        );
      } catch (err) {
        return fail(err);
      }
    }
  );

  // --- Descubrir campos del tipo Test --------------------------------------
  server.registerTool(
    'xray_descubrir_campos_test',
    {
      title: 'Descubrir campos del tipo Test',
      description:
        'Lista los campos disponibles al crear un issue de tipo Test en el ' +
        'proyecto, marcando cuáles son obligatorios y sus valores permitidos. ' +
        'Sirve para identificar el custom field de estado de automatización ' +
        '(su id customfield_XXXXX y sus valores) y los campos requeridos.',
      inputSchema: {
        projectKey: z
          .string()
          .optional()
          .describe('Clave del proyecto Jira (ej: XT). Si se omite usa el default configurado.'),
      },
    },
    async ({ projectKey }) => {
      try {
        const key = resolveProjectKey(config, projectKey);
        const campos = await client.descubrirCamposTest(key);
        const obligatorios = campos.filter((c) => c.required);
        return okJson({
          projectKey: key,
          totalCampos: campos.length,
          obligatorios: obligatorios.map((c) => ({
            id: c.id,
            name: c.name,
            valores: c.allowedValues,
          })),
          camposConValores: campos
            .filter((c) => c.allowedValues && c.allowedValues.length > 0)
            .map((c) => ({ id: c.id, name: c.name, valores: c.allowedValues })),
          todos: campos,
        });
      } catch (err) {
        return fail(err);
      }
    }
  );

  // --- Listar tests (opcional: pendientes de automatizar) ------------------
  server.registerTool(
    'xray_listar_tests',
    {
      title: 'Listar tests del proyecto',
      description:
        'Lista los Test Cases del proyecto. Con estadoAutomatizacion="pendiente" ' +
        'filtra los tests candidatos a automatizar que aún NO están automatizados ' +
        '(Candidato a Automatización = Si Y Automatizado = No). Con "automatizado" ' +
        'lista los ya automatizados.',
      inputSchema: {
        projectKey: z.string().optional().describe('Clave del proyecto (ej: XT). Default configurado si se omite.'),
        estadoAutomatizacion: z
          .enum(['pendiente', 'automatizado', 'todos'])
          .optional()
          .describe('Filtro por estado de automatización. Default: todos.'),
        labels: z.array(z.string()).optional().describe('Filtrar por labels de Jira.'),
      },
    },
    async ({ projectKey, estadoAutomatizacion, labels }) => {
      try {
        const key = resolveProjectKey(config, projectKey);
        const tests = await client.listarTests({
          projectKey: key,
          soloPendientes: estadoAutomatizacion === 'pendiente',
          soloAutomatizados: estadoAutomatizacion === 'automatizado',
          labels,
        });
        return okJson({ projectKey: key, filtro: estadoAutomatizacion ?? 'todos', total: tests.length, tests });
      } catch (err) {
        return fail(err);
      }
    }
  );

  // --- Crear Test Manual con steps -----------------------------------------
  server.registerTool(
    'xray_crear_test_manual',
    {
      title: 'Crear Test Manual con pasos',
      description:
        'Crea un issue de tipo Test (Manual) en Jira con los campos obligatorios y ' +
        'le carga los pasos vía Xray. Cada paso tiene action (el paso), data ' +
        '(contexto/datos funcionales) y result (resultado esperado descriptivo). ' +
        'El campo Dispositivo es OBLIGATORIO: si la historia no lo aclara, ' +
        'preguntá al operador a qué canal aplica (Web, Mobile Android, Mobile iOS, API). ' +
        'Criticidad según análisis de la historia; si no es claro, Media. ' +
        'Devuelve la key y URL del test creado.',
      inputSchema: {
        projectKey: z.string().optional().describe('Clave del proyecto (ej: XT). Default configurado si se omite.'),
        summary: z.string().describe('Título del test case.'),
        dispositivo: z
          .enum(['Web', 'Mobile Android', 'Mobile iOS', 'API'])
          .describe('Canal al que aplica el test. Obligatorio. Si no está claro en la historia, preguntar al operador.'),
        criticidad: z
          .enum(['Alta', 'Media', 'Baja'])
          .optional()
          .describe('Criticidad según análisis de la historia. Default: Media.'),
        description: z.string().optional().describe('Descripción / objetivo del test.'),
        precondicion: z.string().optional().describe('Precondición del test. Default: N/A.'),
        equipo: z.enum(['Equipo A', 'Equipo B']).optional().describe('Equipo responsable. Default: Equipo A.'),
        labels: z.array(z.string()).optional().describe('Labels a asignar (ej: smoke, regresion).'),
        steps: z
          .array(
            z.object({
              action: z.string().describe('El paso a ejecutar.'),
              data: z.string().optional().describe('Datos de entrada / contexto funcional.'),
              result: z.string().optional().describe('Resultado esperado, descriptivo y funcional.'),
            })
          )
          .min(1)
          .describe('Lista de pasos del test manual (al menos uno).'),
      },
    },
    async ({ projectKey, summary, dispositivo, criticidad, description, precondicion, equipo, labels, steps }) => {
      try {
        const key = resolveProjectKey(config, projectKey);
        const result = await client.crearTestManual({
          projectKey: key,
          summary,
          dispositivo,
          criticidad,
          description,
          precondicion,
          equipo,
          labels,
          steps,
        });
        return ok(
          `Test creado: ${result.key} (${result.stepsCargados} paso(s) cargado(s)) — ${result.url}`
        );
      } catch (err) {
        return fail(err);
      }
    }
  );

  // --- Actualizar estado de automatización (flag "Automatizado") -----------
  server.registerTool(
    'xray_actualizar_estado_automatizacion',
    {
      title: 'Actualizar flag Automatizado',
      description:
        'Marca el flag "Automatizado" de un test (Si/No/Ninguno). Usalo cuando un ' +
        'test ya quedó automatizado (Si) o para revertir (No). No toca el flag ' +
        '"Candidato a Automatización".',
      inputSchema: {
        testKey: z.string().describe('Key del test a actualizar (ej: XT-123).'),
        automatizado: z
          .enum(['Si', 'No', 'Ninguno'])
          .describe('Nuevo valor del flag Automatizado.'),
      },
    },
    async ({ testKey, automatizado }) => {
      try {
        await client.marcarAutomatizado(testKey, automatizado);
        return ok(`Test ${testKey} actualizado: Automatizado = "${automatizado}".`);
      } catch (err) {
        return fail(err);
      }
    }
  );

  return server;
}

// ---------------------------------------------------------------------------
// Arranque
// ---------------------------------------------------------------------------

async function main(): Promise<void> {
  let config: XrayServerConfig;
  try {
    config = loadConfig();
  } catch (err) {
    if (err instanceof ConfigError) {
      console.error(`[mcp-xray] Configuración inválida: ${err.message}`);
      process.exit(1);
    }
    throw err;
  }

  const server = buildServer(config);
  const transport = new StdioServerTransport();
  await server.connect(transport);
  console.error(`[mcp-xray] servidor MCP corriendo en stdio (Jira: ${config.jiraBaseUrl})`);
}

main().catch((err) => {
  console.error('[mcp-xray] error fatal:', err instanceof Error ? err.message : err);
  process.exit(1);
});
