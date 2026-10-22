import { ProductWidgetsApi } from '../../../src/api/accounts/product-widgets.api';
import { SalaryAdvanceApi } from '../../../src/api/accounts/salary-advance.api';
import { test } from '../../../src/fixtures/api-test';

const TEST_CASE_ID = 'TC0110003BERSA';
// Importe fijo de las plantillas legadas sealLoansByAmount.json y salaryAdvanceConfirm.json.
const ADVANCE_AMOUNT = 10000;

test.describe('Adelanto de sueldo API de Banco Entre Ríos', () => {
  test.use({ testCaseId: TEST_CASE_ID });

  test(`${TEST_CASE_ID} - Adelantos de Sueldo - Realizar solicitud de adelanto de sueldo`, async ({
    loginApi,
    loginCredentials,
    originAccountData,
    productWidgetsApi,
    salaryAdvanceApi,
  }) => {
    const session = await test.step('Autenticar al usuario', () => loginApi.authenticate(loginCredentials));
    const products = await test.step('Consultar los productos del usuario', () => productWidgetsApi.load(session));
    const idProduct = ProductWidgetsApi.accountId(products, originAccountData.originAccount);
    const offer = await test.step('Consultar la oferta de adelanto', () => salaryAdvanceApi.readOffer(session));
    const rates = SalaryAdvanceApi.ratesFor(offer, originAccountData.originAccount);
    const amounts = await test.step('Consultar los montos del adelanto', () =>
      salaryAdvanceApi.readAmounts(session, ADVANCE_AMOUNT));

    await test.step('Confirmar el adelanto de sueldo', () =>
      salaryAdvanceApi.confirm(session, loginCredentials.username, {
        amount: ADVANCE_AMOUNT,
        amounts,
        idProduct,
        rates,
      }));
  });
});
