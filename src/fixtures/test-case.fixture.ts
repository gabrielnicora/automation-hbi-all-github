import { expect, test as baseTest } from '@playwright/test';
import {
  loadAccountPairData,
  loadCreditCardPaymentData,
  loadDebitCardData,
  loadEcheqEndorsementData,
  loadOptionalOriginAccountData,
  loadOriginAccountData,
  type AccountPairData,
  type CreditCardPaymentData,
  type DebitCardData,
  type EcheqEndorsementData,
  type OptionalOriginAccountData,
  type OriginAccountData,
} from '../test-data/account-selection-data';
import { loadDebinGenerationData, type DebinGenerationData } from '../test-data/debin-generation-data';
import { loadTransferCaseData, type TransferCaseData } from '../test-data/transfer-case-data';
import { loadLoginCredentials, loadSecondUserCredentials, type LoginCredentials } from '../test-data/login-credentials';
import { loadOwnAccountTransferData, type OwnAccountTransferData } from '../test-data/own-account-transfer-data';
import { loadTraditionalFixedTermData, type TraditionalFixedTermData } from '../test-data/traditional-fixed-term-data';
import {
  loadCreditCardOtherAmountPaymentData,
  type CreditCardOtherAmountPaymentData,
} from '../test-data/credit-card-other-amount-payment-data';
import { loadWithdrawalOrderData, type WithdrawalOrderData } from '../test-data/withdrawal-order-data';

interface TestCaseFixtures {
  readonly accountPairData: AccountPairData;
  readonly creditCardPaymentData: CreditCardPaymentData;
  readonly debinGenerationData: DebinGenerationData;
  readonly debitCardData: DebitCardData;
  readonly echeqEndorsementData: EcheqEndorsementData;
  readonly loginCredentials: LoginCredentials;
  readonly optionalOriginAccountData: OptionalOriginAccountData;
  readonly originAccountData: OriginAccountData;
  readonly secondUserCredentials: LoginCredentials;
  readonly transferCaseData: TransferCaseData;
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

  secondUserCredentials: async ({ testCaseId }, use, testInfo) => {
    const credentials = await loadSecondUserCredentials(testCaseId);
    testInfo.annotations.push({ description: credentials.username, type: 'Segundo usuario' });
    await use(credentials);
  },

  transferCaseData: async ({ testCaseId }, use) => {
    await use(await loadTransferCaseData(testCaseId));
  },

  originAccountData: async ({ testCaseId }, use) => {
    await use(await loadOriginAccountData(testCaseId));
  },

  accountPairData: async ({ testCaseId }, use) => {
    await use(await loadAccountPairData(testCaseId));
  },

  debitCardData: async ({ testCaseId }, use) => {
    await use(await loadDebitCardData(testCaseId));
  },

  echeqEndorsementData: async ({ testCaseId }, use) => {
    await use(await loadEcheqEndorsementData(testCaseId));
  },

  creditCardPaymentData: async ({ testCaseId }, use) => {
    await use(await loadCreditCardPaymentData(testCaseId));
  },

  optionalOriginAccountData: async ({ testCaseId }, use) => {
    await use(await loadOptionalOriginAccountData(testCaseId));
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
