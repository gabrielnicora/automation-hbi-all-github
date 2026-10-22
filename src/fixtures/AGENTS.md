# src/fixtures/ — composition rules

Governing source: `SPEC.md` section 6.6. Current fixtures are listed in
`.agents/index/fixtures.md`.

## Entry points

| File                   | Provides                                                                 |
| ---------------------- | ------------------------------------------------------------------------ |
| `test-case.fixture.ts` | `testCaseId` option, credentials and data fixtures shared by API and UI. |
| `api-test.ts`          | API context, `apiRequest`, API clients and API workflows.                |
| `ui-test.ts`           | Page Objects, UI workflows, UI data, automatic `¡Ups!` and survey hooks. |

Tests import only `api-test.ts` or `ui-test.ts`. Do not create a universal fixture or a new
entry point.

## Registering a new Page Object, client, workflow or data loader

1. Import the class or loader at the top of the entry file.
2. Add a `readonly <camelCaseName>: <Type>;` property to the fixtures interface, using the
   class name in camelCase (`ChangeAddressPage` → `changeAddressPage`).
3. Add the implementation: `name: async ({ page }, use) => { await use(new X(page)); }` for UI;
   `async ({ apiRequest }, use) => { await use(new X(apiRequest)); }` for API. Data loaders go
   in `test-case.fixture.ts` and depend on `testCaseId`.
4. Run `pnpm exec playwright test --list` and `pnpm run agent:index`.

## Rules

- Fixtures compose; they hold no selectors, endpoints or business logic.
- Base URLs and environment come from `src/config/environment.ts`; second factor settings
  from `src/config/second-factor.ts`. No other code reads `process.env`.
- A fixture that loads credentials adds the dummy username as a Playwright annotation and
  nothing else about the credentials.
- Automatic fixtures (`{ auto: true }`) apply to every UI test; add one only with an approved
  specification.
