import { expect, test } from '../../../src/fixtures/ui-test';
import { createRandomAddress } from '../../../src/test-data/change-address-data';

const TEST_CASE_ID = 'TC1320001BERSA';

test.describe('Cambio de domicilio UI de Banco Entre Ríos', () => {
  test.setTimeout(240_000);
  test.use({ testCaseId: TEST_CASE_ID });

  test(
    `${TEST_CASE_ID} - cambia el domicilio con un segundo factor SMS`,
    { tag: '@regression' },
    async ({
      changeAddressPage,
      loginApi,
      loginCredentials,
      loginPage,
      profileMobilePhoneApi,
      secondFactorChallengeApi,
      startPage,
    }) => {
      const address = createRandomAddress();

      await startPage.open();
      await startPage.openLogin();
      await loginPage.authenticate(loginCredentials);
      const apiSession = await loginApi.authenticate(loginCredentials);
      const phone = await profileMobilePhoneApi.readCurrentPhone(apiSession);

      await changeAddressPage.open();
      const submittedAddress = await changeAddressPage.completeAddress(address);

      const code = await secondFactorChallengeApi.resolveOperationOtp(apiSession, loginCredentials.username, {
        criteria: { origin: phone, type: 'smsCode' },
        requestInitialCode: () => changeAddressPage.requestInitialSecondFactorCode(),
        requestReplacementCode: () => changeAddressPage.requestReplacementSecondFactorCode(),
      });

      const review = await changeAddressPage.readReviewedAddress();
      for (const value of Object.values(submittedAddress)) {
        expect(review).toContain(value);
      }

      await changeAddressPage.enterSecondFactorCode(code);
      expect(await changeAddressPage.readAddressChangeResult()).toContain('Tu solicitud se realizó con éxito');
    },
  );
});
