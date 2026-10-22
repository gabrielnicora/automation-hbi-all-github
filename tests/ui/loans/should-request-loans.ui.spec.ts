import { expect, test } from '../../../src/fixtures/ui-test';

test.describe('Solicitud de préstamos UI de Banco Entre Ríos', () => {
  test.setTimeout(180_000);

  test.describe('TC0206BERSA', () => {
    test.use({ testCaseId: 'TC0206BERSA' });

    test('TC0206BERSA - llega a la confirmación de un nuevo préstamo', async ({
      loanRequestPage,
      loginCredentials,
      loginPage,
      startPage,
    }) => {
      await startPage.open();
      await startPage.openLogin();
      await loginPage.authenticate(loginCredentials);

      await loanRequestPage.openNewLoan();
      expect(await loanRequestPage.isTextShown('Solicitá tu préstamo'), 'Debe mostrar el título.').toBe(true);
      await loanRequestPage.fillAmount('10000');
      await loanRequestPage.next();
      await loanRequestPage.selectDestination('INMUEBLES');
      await loanRequestPage.continue();
      expect(await loanRequestPage.isStepShown('Cuotas'), 'Debe mostrar la etapa Cuotas.').toBe(true);
      expect(
        await loanRequestPage.isTextShown('Seleccioná la cantidad de cuotas'),
        'Debe pedir la cantidad de cuotas.',
      ).toBe(true);
      await loanRequestPage.selectOneInstallment();
      await loanRequestPage.continue();
      await loanRequestPage.acceptTerms();
      await loanRequestPage.continue();

      expect(await loanRequestPage.isStepShown('Confirmaci'), 'Debe mostrar la etapa Confirmación.').toBe(true);
      for (const text of [
        'Confirmá tu solicitud',
        'Para terminar, ingresá el código que recibiste por mensaje de texto.',
      ]) {
        expect(await loanRequestPage.isTextShown(text), `Debe mostrar "${text}".`).toBe(true);
      }
    });
  });

  test.describe('TC0220BERSA', () => {
    test.use({ testCaseId: 'TC0220BERSA' });

    test('TC0220BERSA - llega a la confirmación de un préstamo para cancelar deudas', async ({
      loanRequestPage,
      loginCredentials,
      loginPage,
      startPage,
    }) => {
      await startPage.open();
      await startPage.openLogin();
      await loginPage.authenticate(loginCredentials);

      await loanRequestPage.openDebtCancellationLoan();
      expect(
        await loanRequestPage.isTextShown('Solicitá tu préstamo para cancelar deudas'),
        'Debe mostrar el título.',
      ).toBe(true);
      await loanRequestPage.selectFirstDebt();
      await loanRequestPage.next();
      await loanRequestPage.fillAmount('500000');
      await loanRequestPage.continue();
      await loanRequestPage.continue();
      await loanRequestPage.selectOneInstallment();
      await loanRequestPage.continue();
      await loanRequestPage.acceptTerms();
      await loanRequestPage.continue();

      expect(await loanRequestPage.isStepShown('Confirmaci'), 'Debe mostrar la etapa Confirmación.').toBe(true);
      for (const text of ['Confirmá tu solicitud', 'La acreditación se verá reflejada en el transcurso del día.']) {
        expect(await loanRequestPage.isTextShown(text), `Debe mostrar "${text}".`).toBe(true);
      }
    });
  });

  test.describe('TC0221BERSA', () => {
    test.use({ testCaseId: 'TC0221BERSA' });

    test('TC0221BERSA - abre el resumen de contrato del préstamo para cancelar deudas', async ({
      loanRequestPage,
      loginCredentials,
      loginPage,
      startPage,
    }) => {
      await startPage.open();
      await startPage.openLogin();
      await loginPage.authenticate(loginCredentials);

      await loanRequestPage.openDebtCancellationLoan();
      expect(
        await loanRequestPage.isTextShown('Solicitá tu préstamo para cancelar deudas'),
        'Debe mostrar el título.',
      ).toBe(true);
      await loanRequestPage.selectFirstDebt();
      await loanRequestPage.next();

      expect(await loanRequestPage.openContractSummary(), 'El contrato debe abrirse en assets.').toContain('assets');
    });
  });
});
