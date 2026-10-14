export const FUND_RISK = {
  CONSERVATIVE: 'conservative',
  MODERATE: 'moderate',
  DYNAMIC: 'dynamic',
} as const;

export type FundRisk = (typeof FUND_RISK)[keyof typeof FUND_RISK];

export const FUND_RISKS: readonly FundRisk[] = [FUND_RISK.CONSERVATIVE, FUND_RISK.MODERATE, FUND_RISK.DYNAMIC];
