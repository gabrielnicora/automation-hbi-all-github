export type BearerAuthorization = `Bearer ${string}`;

export interface AuthenticatedSession {
  readonly accessToken: string;
  readonly authorizationHeader: BearerAuthorization;
  readonly documentNumber?: string;
}

export function createAuthenticatedSession(accessToken: string, documentNumber?: string): AuthenticatedSession {
  const normalizedAccessToken = accessToken.trim();

  if (!normalizedAccessToken) {
    throw new Error('El login no devolvió un access token válido.');
  }

  return {
    accessToken: normalizedAccessToken,
    authorizationHeader: `Bearer ${normalizedAccessToken}`,
    ...(documentNumber ? { documentNumber } : {}),
  };
}
