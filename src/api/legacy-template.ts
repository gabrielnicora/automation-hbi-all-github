import type { ApiJsonObject } from './reporting-api-request';

/** Par marcador → valor; `raw` reemplaza también las comillas del marcador (p. ej. `"_ownAccount_"`). */
export type LegacyReplacement = readonly [marker: string, value: string, options?: { readonly raw?: boolean }];

/**
 * Replica el armado legado de requests: toma la plantilla JSON de `Requests/`, aplica cada
 * `String.replace` en el mismo orden y devuelve el body resultante. Los valores comunes se escapan
 * para el contexto de texto JSON; los `raw` se insertan tal cual (booleanos o números).
 */
export function fillLegacyTemplate(template: unknown, replacements: readonly LegacyReplacement[]): ApiJsonObject {
  const text = replacements.reduce((body, [marker, value, options]) => {
    if (options?.raw) {
      return body.split(`"${marker}"`).join(value);
    }

    return body.split(marker).join(JSON.stringify(value).slice(1, -1));
  }, JSON.stringify(template));

  return JSON.parse(text) as ApiJsonObject;
}

/** `Utils.validarNullParametro`: el texto del valor o `"null"` si no existe. */
export function legacyNullable(value: unknown): string {
  return value === null || value === undefined ? 'null' : String(value);
}

/** `Utils.quitarCochetesJsonPath`: quita corchetes y comillas del texto. */
export function legacyStripBrackets(value: unknown): string {
  return String(value).replace(/\[/g, '').replace(/]/g, '').replace(/"/g, '');
}
