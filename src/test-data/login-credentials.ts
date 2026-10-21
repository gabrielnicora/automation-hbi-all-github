import { loadBersaTestCaseRow, type BersaTestCaseRow } from './bersa-test-case';
import { loadFixedAccess } from './fixed-access';

export interface LoginCredentials {
  readonly password: string;
  readonly username: string;
}

function extractCredentials(row: BersaTestCaseRow, testCaseId: string): LoginCredentials {
  const username = row.user;
  const password = row.pass;

  if (typeof username !== 'string' || username.trim().length === 0) {
    throw new Error(`El caso ${testCaseId} no contiene un usuario válido.`);
  }

  if (typeof password !== 'string' || password.length === 0) {
    throw new Error(`El caso ${testCaseId} no contiene una contraseña válida.`);
  }

  return { username: username.trim(), password };
}

/** En un ambiente con acceso fijo (`une-dev`), todos los usuarios comparten la contraseña. */
async function applyFixedPassword(credentials: LoginCredentials): Promise<LoginCredentials> {
  const fixedAccess = await loadFixedAccess();
  return fixedAccess ? { ...credentials, password: fixedAccess.password } : credentials;
}

export async function loadLoginCredentials(testCaseId: string): Promise<LoginCredentials> {
  const row = await loadBersaTestCaseRow(testCaseId);
  return applyFixedPassword(extractCredentials(row, testCaseId.trim().toUpperCase()));
}

/** Segundo usuario del caso (`user1`/`pass1`; `usuario2`/`password2` en el legado). */
export async function loadSecondUserCredentials(testCaseId: string): Promise<LoginCredentials> {
  const row = await loadBersaTestCaseRow(testCaseId);
  return applyFixedPassword(
    extractCredentials({ user: row.user1, pass: row.pass1 }, `${testCaseId.trim().toUpperCase()} (segundo usuario)`),
  );
}
