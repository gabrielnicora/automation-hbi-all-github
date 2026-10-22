import { expect, test } from '../../../src/fixtures/api-test';

const TEST_CASE_ID = 'TC0710005BERSA';

function placeHoldersWithOrder(fields: readonly { order: unknown; placeHolder: unknown }[], order: number): string {
  // El legado lee el filtro JSONPath y quita corchetes y comillas: varios resultados quedan unidos por coma.
  return fields
    .filter((field) => Number(field.order) === order)
    .map((field) => String(field.placeHolder))
    .join(',');
}

test.describe('Cuenta comitente API de Banco Entre Ríos', () => {
  test.use({ testCaseId: TEST_CASE_ID });

  test(`${TEST_CASE_ID} - FCI - Solicitar baja cuenta comitente - Validación de pantalla`, async ({
    commitentAccountApi,
    loginApi,
    loginCredentials,
  }) => {
    const session = await test.step('Autenticar al usuario', () => loginApi.authenticate(loginCredentials));
    const fields = await test.step('Consultar el formulario de baja', () =>
      commitentAccountApi.readClosureFormFields(session));
    const mail = placeHoldersWithOrder(fields, 1);
    const phone = placeHoldersWithOrder(fields, 2);

    expect(mail.includes('Mail') || mail === 'Email', `El campo 1 debe ser el correo; se informó "${mail}".`).toBe(
      true,
    );
    expect(['Telefono', 'Teléfono'], 'El campo 2 debe ser el teléfono.').toContain(phone);
    expect(fields[0]?.required, 'El primer campo del formulario debe ser obligatorio.').toBe(true);
    expect(fields[1]?.required, 'El segundo campo del formulario debe ser obligatorio.').toBe(true);
  });
});
