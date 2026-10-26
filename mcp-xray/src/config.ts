/**
 * Configuración del servidor MCP de Xray.
 *
 * Toda la config viene por variables de entorno inyectadas por el host MCP
 * (Kiro vía .kiro/settings/mcp.json, o la app en AWS vía su propio entorno).
 *
 * Regla de seguridad: el PAT NUNCA se hardcodea ni se lee del env/.env.xray del
 * repo. Cada usuario/despliegue inyecta su propio XRAY_JIRA_PAT.
 *
 * Los IDs de custom fields y sus valores por defecto tienen fallback a los
 * valores reales descubiertos en la instancia (Jira Server 9.4.13, proyecto XT),
 * pero son configurables por env para otras instancias.
 */

export interface XrayServerConfig {
  /** URL base de Jira, sin trailing slash. Ej: https://jira.empresa.com */
  jiraBaseUrl: string;
  /** Personal Access Token de Jira del usuario. Obligatorio. */
  jiraPat: string;
  /** Clave del proyecto por defecto. Ej: XT. Opcional (se puede pasar por tool). */
  defaultProjectKey?: string;
  /** Timeout máximo por request a Jira/Xray, en milisegundos. */
  requestTimeoutMs: number;

  /** IDs de custom fields de Xray/Jira (configurables por instancia). */
  fields: {
    /** Candidato a Automatización (option Si/No). Flag "¿se automatiza?". */
    candidato: string;
    /** Automatizado (option Si/No/Ninguno). Flag "¿ya está automatizado?". */
    automatizado: string;
    /** Incluido En Regresión (option Si/No). */
    regresion: string;
    /** Criticidad (option Alta/Media/Baja). */
    criticidad: string;
    /** Dispositivo (option Web/Mobile Android/Mobile iOS/API). */
    dispositivo: string;
    /** Equipo Responsable (option Equipo A/Equipo B). */
    equipo: string;
    /** Precondición (string). */
    precondicion: string;
    /** Tipo de Test (option Manual/Cucumber/Generic). */
    tipoTest: string;
  };

  /** Valores por defecto al crear un Test. */
  defaults: {
    candidato: string;
    automatizado: string;
    regresion: string;
    criticidad: string;
    equipo: string;
    precondicion: string;
    tipoTest: string;
  };

  /** Valores que definen los estados de automatización. */
  automation: {
    candidatoSi: string;
    automatizadoSi: string;
    automatizadoNo: string;
  };
}

/** Error de configuración con mensaje accionable para el usuario. */
export class ConfigError extends Error {
  constructor(message: string) {
    super(message);
    this.name = 'ConfigError';
  }
}

function requireEnv(name: string): string {
  const value = process.env[name];
  if (!value || value.trim() === '') {
    throw new ConfigError(
      `La variable de entorno ${name} es obligatoria y no está definida. ` +
        `Configurala en el host MCP (por ejemplo en .kiro/settings/mcp.json → env).`
    );
  }
  return value.trim();
}

function optionalEnv(name: string): string | undefined {
  const value = process.env[name];
  return value && value.trim() !== '' ? value.trim() : undefined;
}

function envOr(name: string, fallback: string): string {
  return optionalEnv(name) ?? fallback;
}

function positiveIntegerEnv(name: string, fallback: number): number {
  const raw = optionalEnv(name);
  if (raw === undefined) return fallback;

  if (!/^\d+$/.test(raw)) {
    throw new ConfigError(`${name} debe ser un entero positivo expresado en milisegundos.`);
  }

  const value = Number(raw);
  if (!Number.isSafeInteger(value) || value <= 0) {
    throw new ConfigError(`${name} debe ser un entero positivo expresado en milisegundos.`);
  }

  return value;
}

/**
 * Lee y valida la configuración desde el entorno.
 * Lanza ConfigError con mensaje claro si falta algo obligatorio.
 */
export function loadConfig(): XrayServerConfig {
  const jiraBaseUrl = requireEnv('XRAY_JIRA_BASE_URL').replace(/\/+$/, '');
  const jiraPat = requireEnv('XRAY_JIRA_PAT');

  return {
    jiraBaseUrl,
    jiraPat,
    defaultProjectKey: optionalEnv('XRAY_PROJECT_KEY'),
    requestTimeoutMs: positiveIntegerEnv('XRAY_REQUEST_TIMEOUT_MS', 30_000),

    fields: {
      candidato: envOr('XRAY_FIELD_CANDIDATO', 'customfield_18831'),
      automatizado: envOr('XRAY_FIELD_AUTOMATIZADO', 'customfield_19100'),
      regresion: envOr('XRAY_FIELD_REGRESION', 'customfield_18832'),
      criticidad: envOr('XRAY_FIELD_CRITICIDAD', 'customfield_19002'),
      dispositivo: envOr('XRAY_FIELD_DISPOSITIVO', 'customfield_18835'),
      equipo: envOr('XRAY_FIELD_EQUIPO', 'customfield_18833'),
      precondicion: envOr('XRAY_FIELD_PRECONDICION', 'customfield_18834'),
      tipoTest: envOr('XRAY_FIELD_TIPO_TEST', 'customfield_18800'),
    },

    defaults: {
      candidato: envOr('XRAY_DEFAULT_CANDIDATO', 'Si'),
      automatizado: envOr('XRAY_DEFAULT_AUTOMATIZADO', 'No'),
      regresion: envOr('XRAY_DEFAULT_REGRESION', 'No'),
      criticidad: envOr('XRAY_DEFAULT_CRITICIDAD', 'Media'),
      equipo: envOr('XRAY_DEFAULT_EQUIPO', 'Equipo A'),
      precondicion: envOr('XRAY_DEFAULT_PRECONDICION', 'N/A'),
      tipoTest: envOr('XRAY_DEFAULT_TIPO_TEST', 'Manual'),
    },

    automation: {
      candidatoSi: envOr('XRAY_AUTOMATION_CANDIDATO_SI', 'Si'),
      automatizadoSi: envOr('XRAY_AUTOMATION_AUTOMATIZADO_SI', 'Si'),
      automatizadoNo: envOr('XRAY_AUTOMATION_AUTOMATIZADO_NO', 'No'),
    },
  };
}
