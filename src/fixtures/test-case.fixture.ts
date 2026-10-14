import { expect, test as baseTest } from '@playwright/test';
import { loadDebinGenerationData, type DebinGenerationData } from '../test-data/debin-generation-data';
import { loadLoginCredentials, type LoginCredentials } from '../test-data/login-credentials';
import { loadOwnAccountTransferData, type OwnAccountTransferData } from '../test-data/own-account-transfer-data';
import { loadTraditionalFixedTermData, type TraditionalFixedTermData } from '../test-data/traditional-fixed-term-data';
import {
  loadCreditCardOtherAmountPaymentData,
  type CreditCardOtherAmountPaymentData,
} from '../test-data/credit-card-other-amount-payment-data';
import { loadWithdrawalOrderData, type WithdrawalOrderData } from '../test-data/withdrawal-order-data';

interface TestCaseFixtures {
  readonly debinGenerationData: DebinGenerationData;
  readonly loginCredentials: LoginCredentials;
  readonly ownAccountTransferData: OwnAccountTransferData;
  readonly traditionalFixedTermData: TraditionalFixedTermData;
  readonly creditCardOtherAmountPaymentData: CreditCardOtherAmountPaymentData;
  readonly withdrawalOrderData: WithdrawalOrderData;
}

interface TestCaseOptions {
  readonly testCaseId: string;
}

export const test = baseTest.extend<TestCaseFixtures & TestCaseOptions>({
  testCaseId: ['', { option: true }],

  loginCredentials: async ({ testCaseId }, use, testInfo) => {
    const credentials = await loadLoginCredentials(testCaseId);
    testInfo.annotations.push({ description: credentials.username, type: 'Usuario' });
    await use(credentials);
  },

  debinGenerationData: async ({ testCaseId }, use) => {
    await use(await loadDebinGenerationData(testCaseId));
  },

  ownAccountTransferData: async ({ testCaseId }, use) => {
    await use(await loadOwnAccountTransferData(testCaseId));
  },

  traditionalFixedTermData: async ({ testCaseId }, use) => {
    await use(await loadTraditionalFixedTermData(testCaseId));
  },

  creditCardOtherAmountPaymentData: async ({ testCaseId }, use) => {
    await use(await loadCreditCardOtherAmountPaymentData(testCaseId));
  },

  withdrawalOrderData: async ({ testCaseId }, use) => {
    await use(await loadWithdrawalOrderData(testCaseId));
  },
});

export { expect };
