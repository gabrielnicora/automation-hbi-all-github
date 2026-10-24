# tests/ — scenario rules

Governing sources: `SPEC.md` sections 6.1, 6.2.2 and 10; `docs/test-authoring.md`; the
capability specification under `specs/<area>/`. Find existing cases in
`.agents/index/tests.md` and available fixtures in `.agents/index/fixtures.md`.

## Shape of a test

- Path: `tests/<api|ui>/<FFF>-<family>/<behavior-name>.<api|ui>.spec.ts` (for example
  `tests/ui/003-cards/`). The folder states the family; one file groups cases of the same
  behavior and channel.
- Import `test` and `expect` only from `src/fixtures/api-test.ts` or `src/fixtures/ui-test.ts`.
- Declare `const TEST_CASE_ID = 'TC-<FFF>-<C>-<NNNN>-UNE'`, call
  `test.use({ testCaseId: TEST_CASE_ID })` and start every title with the ID. The ID selects
  the data row.
- IDs follow `SPEC.md` section 10.1: family code of the folder, channel `1` API or `2` UI,
  sequence, suffix `UNE`; one ID per channel. Get a new one with
  `pnpm run case:new -- <family> <api|ui>` (family name without the code, such as `cards`);
  it registers the ID in `test-data/case-ids.json`. Never invent, compute or reuse an ID. To
  find the new ID of an old one (as cited in the legacy `java/` sources), look up `legacyId` in
  `test-data/case-ids.json`.
- Wrap each functional step in `test.step('<what the user does>', ...)`.
- Use the fixtures by name in the callback (`loginApi`, `loginCredentials`, `<area>Page`,
  `<area>Api`, `<case>Data`). Do not construct Page Objects or clients in the test.
- Tags: `@regression` and `@critical` exist with the meaning given in `SPEC.md` section 10.
  Do not add tags for smoke or Xray without an approved decision.

Reference files:

- API: `tests/api/013-profile/should-change-phone.api.spec.ts`.
- API parameterized by data: `tests/api/001-accounts/should-list-echeqs-by-status.api.spec.ts`.
- UI (login, form with scenario values, expectation in the test):
  `tests/ui/007-investments/should-reject-zero-amount-foreign-currency-purchase.ui.spec.ts`.

## What a test may contain

- Fixture setup, calls to Page Objects, API clients or workflows with functional intent,
  `test.step`, and business `expect` with a fixed explanatory message.
- Small values that define the variant (for example amount `0`) may be explicit in the test.
  Accounts, users and cards come from data fixtures.

Never: selectors, URLs, endpoints, payloads, credentials, polling, sleeps, `waitForTimeout`,
or conditions that pick which scenario to test from the observed state.

## Variants

- Different journey or different kind of expected result → separate tests.
- Only data changes and the validation rule stays → parameterize with an array of
  `{ id, title, ... }` and one `test.describe(id)` + `test.use({ testCaseId: id })` per item,
  so each variant keeps its own ID, data and result.
- The expected result (success or rejection) is fixed before running.

## Secrets in assertions

For runtime tokens or headers, assert a boolean (`token.trim().length > 0`) with a fixed
message; never pass the token to a matcher, because the failure message would print it.

## Before finishing

Run `pnpm exec playwright test --list` and `pnpm run check`; regenerate the catalog with
`pnpm run agent:index`. Run the spec with `PW_RETRIES=0` only when allowed (root `AGENTS.md`).
