export type BearerAuthorization = `Bearer ${string}`;

export interface AuthenticatedSession {
  readonly accessToken: string;
  readonly authorizationHeader: BearerAuthorization;
  readonly documentNumber?: string;
  /** CUIL del cliente informado por el login API (`environments..productGroupId`), cuando existe. */
  readonly customerCuil?: string;
  /** Nombre completo del cliente informado por el login API (`userFullName`), cuando existe. */
  readonly customerName?: string;
}

export function createAuthenticatedSession(
  accessToken: string,
  documentNumber?: string,
  customerCuil?: string,
  customerName?: string,
): AuthenticatedSession {
  const normalizedAccessToken = accessToken.trim();

  if (!normalizedAccessToken) {
    throw new Error('El login no devolvió un access token válido.');
  }

  return {
    accessToken: normalizedAccessToken,
    authorizationHeader: `Bearer ${normalizedAccessToken}`,
    ...(documentNumber ? { documentNumber } : {}),
    ...(customerCuil ? { customerCuil } : {}),
    ...(customerName ? { customerName } : {}),
  };
}
