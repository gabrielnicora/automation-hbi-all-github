# test-data/ and src/test-data/ — test data rules

Governing source: `SPEC.md` section 8. Which fields each case has is listed in
`.agents/index/test-data.md` (field names only). Read the JSON files only when you need a
value to diagnose a failure, and do not repeat passwords or codes in answers.

## Files

| File                                   | Content                                                                                          |
| -------------------------------------- | ------------------------------------------------------------------------------------------------ |
| `test-data/private/bersa.json`         | `testCases`, keyed by case ID (`TC-<FFF>-<C>-<NNNN>-UNE`); each row repeats the key in `tcName`. |
| `test-data/private/une-dev.json`       | Password and second factor code common to all users in `TEST_ENV=une-dev`.                       |
| `test-data/private/second-factor.json` | Master code and users enabled for it.                                                            |
| `test-data/private/phone-change.json`  | Candidate phone numbers for the phone change case.                                               |

Everything here is dummy QA/DEV data created by the team. It is versioned on purpose. Never
add production credentials, real personal data, tokens or cookies.

## Row fields in `bersa.json`

`user`, `pass` (main user); `user1`, `pass1` (second user); `ctaOrigen`, `ctaDestino`,
`ctaComitente` (accounts); `cbuDestino`, `aliasDestino`, `cuitDestino`, `nombreDestino`
(destination); `tarjetaDebito`, `tarjetaOperar` (cards); `idSeccion`, `idBanco`, `ambiente`.
`null` or `sinDatos` means the field is not available for that case. Loaders accept only IDs in the
`TC-<FFF>-<C>-<NNNN>-UNE` format; rows with previous IDs (`TC<number>BERSA`, `TR…`) have no
test and cannot be loaded.

## Loaders (`src/test-data/`)

- One loader per data shape: `load<Shape>Data(testCaseId)` returning a small readonly
  interface with functional names (`originAccount`, not `ctaOrigen`).
- Read rows with `loadCaseRow` and `readRequiredField` / `readRawField` from
  `src/test-data/case-fields.ts`; they validate the ID and report the missing field.
- Reuse an existing loader when the shape matches (`account-selection-data.ts` covers origin
  account, account pair, debit card, credit card payment and eCheq endorsement).
- Register the loader as a fixture in `src/fixtures/test-case.fixture.ts` (UI-only data may
  live in `ui-test.ts`).

## Adding a case

- Get the ID first with `pnpm run case:new -- <family> <api|ui>` (`SPEC.md` section 10.1).
  `test-data/case-ids.json` maps each ID to its family, channel and previous ID (`legacyId`).
- New case on existing data shape: add the row to `bersa.json` with the fields the loader
  reads. Only use values the team provided; never invent users, accounts or cards.
- If the needed row or field does not exist, stop that case and report the exact `testCaseId`
  and missing fields.

Reference loader: `src/test-data/account-selection-data.ts`.
