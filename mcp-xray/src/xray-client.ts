/**
 * Cliente HTTP para Jira Server + Xray Server (plugin Raven).
 *
 * Endpoints confirmados contra la instancia real (Jira Server 9.4.13):
 *   - Jira core:        /rest/api/2
 *   - createmeta:       /rest/api/2/issue/createmeta/{projectKey}/issuetypes[/{typeId}]
 *   - Xray steps:       PUT /rest/raven/1.0/api/test/{key}/step   (uno por paso)
 *
 * Autenticación por PAT (Bearer). El PAT viene de la config (env), nunca
 * hardcodeado. Una instancia = un PAT (el del usuario que la invoca).
 */

import type { XrayServerConfig } from './config.js';

// ---------------------------------------------------------------------------
// Tipos de dominio
// ---------------------------------------------------------------------------

/** Un paso de un Test Manual de Xray. */
export interface TestStep {
  /** Acción a ejecutar (obligatorio). */
  action: string;
  /** Datos de entrada / contexto funcional. */
  data?: string;
  /** Resultado esperado (descriptivo, funcional). */
  result?: string;
}

/** Datos para crear un Test Manual. */
export interface CrearTestManualInput {
  projectKey: string;
  summary: string;
  description?: string;
  labels?: string[];
  steps: TestStep[];
  /** Dispositivo (obligatorio en Jira): Web / Mobile Android / Mobile iOS / API. */
  dispositivo: string;
  /** Criticidad: Alta / Media / Baja. Default de config si se omite. */
  criticidad?: string;
  /** Precondición (texto). Default de config si se omite. */
  precondicion?: string;
  /** Equipo Responsable. Default de config si se omite. */
  equipo?: string;
}

/** Resultado de crear un Test. */
export interface CrearTestResult {
  key: string;
  id: string;
  url: string;
  stepsCargados: number;
}

/** Info resumida de un Test para listados. */
export interface TestResumen {
  key: string;
  summary: string;
  status: string;
  labels: string[];
  candidato?: string;
  automatizado?: string;
}

/** Campo de la metadata de creación (createmeta). */
export interface CampoCreacion {
  id: string;
  name: string;
  required: boolean;
  schemaType: string;
  allowedValues?: string[];
}

// ---------------------------------------------------------------------------
// Cliente
// ---------------------------------------------------------------------------

export class XrayServerClient {
  private readonly baseUrl: string;
  private readonly authHeader: string;

  constructor(private readonly config: XrayServerConfig) {
    this.baseUrl = config.jiraBaseUrl;
    this.authHeader = `Bearer ${config.jiraPat}`;
  }

  private headers(): Record<string, string> {
    return {
      Authorization: this.authHeader,
      'Content-Type': 'application/json',
      Accept: 'application/json',
    };
  }

  private async request<T>(method: string, path: string, body?: unknown): Promise<T> {
    const url = `${this.baseUrl}${path}`;
    const res = await fetch(url, {
      method,
      headers: this.headers(),
      body: body !== undefined ? JSON.stringify(body) : undefined,
    });

    if (!res.ok) {
      const text = await res.text().catch(() => '');
      throw new Error(`${method} ${path} falló (HTTP ${res.status}): ${text || res.statusText}`);
    }

    if (res.status === 204) return undefined as T;
    const contentType = res.headers.get('content-type') ?? '';
    if (!contentType.includes('application/json')) {
      return (await res.text()) as unknown as T;
    }
    return (await res.json()) as T;
  }

  /** Verifica la conexión y devuelve el usuario autenticado (valida el PAT). */
  async verificarConexion(): Promise<{ name: string; displayName: string }> {
    const user = await this.request<{ name: string; displayName: string }>(
      'GET',
      '/rest/api/2/myself'
    );
    return { name: user.name, displayName: user.displayName };
  }

  // -------------------------------------------------------------------------
  // Descubrimiento de campos del tipo "Test"
  // -------------------------------------------------------------------------

  /** Resuelve el id del tipo de issue "Test" en el proyecto. */
  private async obtenerTipoTestId(projectKey: string): Promise<string> {
    interface IssueTypesResponse {
      values: Array<{ id: string; name: string; subtask: boolean }>;
    }
    const data = await this.request<IssueTypesResponse>(
      'GET',
      `/rest/api/2/issue/createmeta/${encodeURIComponent(projectKey)}/issuetypes`
    );
    const test = data.values.find((t) => t.name === 'Test' && !t.subtask);
    if (!test) {
      throw new Error(
        `El proyecto ${projectKey} no tiene el tipo de issue "Test" (o no es visible con este PAT).`
      );
    }
    return test.id;
  }

  /**
   * Obtiene los campos disponibles al crear un Test en un proyecto, marcando
   * cuáles son obligatorios. Usa el endpoint nuevo de createmeta (Jira 9+).
   */
  async descubrirCamposTest(projectKey: string): Promise<CampoCreacion[]> {
    const typeId = await this.obtenerTipoTestId(projectKey);

    interface FieldsResponse {
      values: Array<{
        fieldId: string;
        name: string;
        required: boolean;
        schema?: { type: string };
        allowedValues?: Array<{ value?: string; name?: string }>;
      }>;
    }

    const data = await this.request<FieldsResponse>(
      'GET',
      `/rest/api/2/issue/createmeta/${encodeURIComponent(projectKey)}/issuetypes/${typeId}`
    );

    return data.values.map((f) => ({
      id: f.fieldId,
      name: f.name,
      required: f.required,
      schemaType: f.schema?.type ?? 'unknown',
      allowedValues: f.allowedValues
        ?.map((v) => v.value ?? v.name)
        .filter((v): v is string => typeof v === 'string'),
    }));
  }

  // -------------------------------------------------------------------------
  // Listado de tests (JQL)
  // -------------------------------------------------------------------------

  /**
   * Lista Tests de un proyecto. Si `soloPendientes` es true, filtra por los dos
   * flags de automatización: Candidato = Si AND Automatizado = No.
   */
  async listarTests(params: {
    projectKey: string;
    soloPendientes?: boolean;
    soloAutomatizados?: boolean;
    labels?: string[];
  }): Promise<TestResumen[]> {
    const { projectKey, soloPendientes, soloAutomatizados, labels } = params;
    const f = this.config.fields;
    const a = this.config.automation;

    const cf = (id: string) => `cf[${id.replace(/^customfield_/, '')}]`;

    let jql = `project = "${projectKey}" AND issuetype = "Test"`;
    if (soloPendientes) {
      jql += ` AND ${cf(f.candidato)} = "${a.candidatoSi}" AND ${cf(f.automatizado)} = "${a.automatizadoNo}"`;
    } else if (soloAutomatizados) {
      jql += ` AND ${cf(f.automatizado)} = "${a.automatizadoSi}"`;
    }
    if (labels && labels.length > 0) {
      jql += ` AND labels IN (${labels.map((l) => `"${l}"`).join(', ')})`;
    }
    jql += ' ORDER BY key ASC';

    const fields = ['summary', 'status', 'labels', f.candidato, f.automatizado];

    interface SearchResponse {
      total: number;
      issues: Array<{
        key: string;
        fields: Record<string, unknown> & {
          summary: string;
          status: { name: string };
          labels: string[];
        };
      }>;
    }

    const all: TestResumen[] = [];
    let startAt = 0;
    const maxResults = 50;

    for (;;) {
      const data = await this.request<SearchResponse>('POST', '/rest/api/2/search', {
        jql,
        startAt,
        maxResults,
        fields,
      });

      for (const issue of data.issues) {
        all.push({
          key: issue.key,
          summary: issue.fields.summary,
          status: issue.fields.status?.name ?? 'Unknown',
          labels: issue.fields.labels ?? [],
          candidato: extractSelectValue(issue.fields[f.candidato]),
          automatizado: extractSelectValue(issue.fields[f.automatizado]),
        });
      }

      if (all.length >= data.total || data.issues.length === 0) break;
      startAt += maxResults;
    }

    return all;
  }

  // -------------------------------------------------------------------------
  // Creación de Test Manual + steps
  // -------------------------------------------------------------------------

  /**
   * Crea un issue de tipo Test (Manual) en Jira con los campos obligatorios y
   * luego carga los pasos vía la API de Xray Server (Raven 1.0).
   */
  async crearTestManual(input: CrearTestManualInput): Promise<CrearTestResult> {
    if (!input.steps || input.steps.length === 0) {
      throw new Error('Un Test Manual requiere al menos un paso (step).');
    }
    if (!input.dispositivo) {
      throw new Error('El campo Dispositivo es obligatorio (ej: Web, Mobile Android, Mobile iOS, API).');
    }

    const f = this.config.fields;
    const d = this.config.defaults;

    // 1) Crear el issue Test en Jira con los campos obligatorios
    const fields: Record<string, unknown> = {
      project: { key: input.projectKey },
      summary: input.summary,
      issuetype: { name: 'Test' },
      [f.equipo]: { value: input.equipo ?? d.equipo },
      [f.candidato]: { value: d.candidato },
      [f.automatizado]: { value: d.automatizado },
      [f.regresion]: { value: d.regresion },
      [f.criticidad]: { value: input.criticidad ?? d.criticidad },
      [f.dispositivo]: { value: input.dispositivo },
      [f.precondicion]: input.precondicion ?? d.precondicion,
      [f.tipoTest]: { value: d.tipoTest },
      ...(input.description ? { description: input.description } : {}),
    };

    const created = await this.request<{ id: string; key: string }>('POST', '/rest/api/2/issue', {
      fields,
    });

    // Los labels no están en la pantalla de creación del tipo Test en esta
    // instancia, así que se setean en un update posterior (best-effort: si la
    // instancia tampoco los permite editar, no se rompe la creación).
    if (input.labels && input.labels.length > 0) {
      try {
        await this.request('PUT', `/rest/api/2/issue/${created.key}`, {
          fields: { labels: input.labels },
        });
      } catch {
        // labels no editables en esta instancia: se ignora sin fallar
      }
    }

    // 2) Cargar los steps vía Xray Server (Raven 1.0): PUT por cada paso.
    let stepsCargados = 0;
    for (const step of input.steps) {
      await this.request('PUT', `/rest/raven/1.0/api/test/${created.key}/step`, {
        step: step.action,
        data: step.data ?? '',
        result: step.result ?? '',
      });
      stepsCargados++;
    }

    return {
      key: created.key,
      id: created.id,
      url: `${this.baseUrl}/browse/${created.key}`,
      stepsCargados,
    };
  }

  // -------------------------------------------------------------------------
  // Actualizar estado de automatización (flag "Automatizado")
  // -------------------------------------------------------------------------

  /**
   * Marca el flag "Automatizado" de un test (Si/No/Ninguno). No toca el flag
   * "Candidato a Automatización".
   */
  async marcarAutomatizado(testKey: string, value: string): Promise<void> {
    await this.request('PUT', `/rest/api/2/issue/${testKey}`, {
      fields: {
        [this.config.fields.automatizado]: { value },
      },
    });
  }
}

// ---------------------------------------------------------------------------
// Helpers
// ---------------------------------------------------------------------------

/** Extrae el valor legible de un custom field select/option de Jira. */
function extractSelectValue(raw: unknown): string | undefined {
  if (raw == null) return undefined;
  if (typeof raw === 'string') return raw;
  if (typeof raw === 'object') {
    const obj = raw as { value?: string; name?: string };
    return obj.value ?? obj.name;
  }
  return undefined;
}
