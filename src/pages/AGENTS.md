# src/pages/ — Page Object rules

Governing sources: `SPEC.md` sections 6.2, 6.2.1 and 6.2.2; `docs/test-authoring.md`
("Page Object Model", "Política general de esperas UI"). Check `.agents/index/pages.md`
before creating a Page Object: extend the existing one for the same screen.

## Responsibilities

- One Page Object per screen or coherent part of one, under `src/pages/<area>/`, named
  `<screen>.page.ts` with class `<Screen>Page`.
- Selectors, control texts, paths and timeouts are private constants or private members of
  the file. A selector change touches only this file.
- Prefer accessible locators (`getByRole`, `getByLabel`, `getByText`). XPath only when nothing
  stable exists, with the reason in a comment.
- Receive scenario values as parameters (`completeForm(data)`); never hard-code accounts,
  amounts or IDs of a case. Allow invalid input the scenario wants to send.
- Expose actions and reads that return simple data. The test owns business expectations;
  the page only verifies it is operable and synchronized.
- No environment variables, bank codes, config imports or coordination of unrelated pages.
  Constructors receive `Page` and, when needed, the base URL from the fixture.

## Waits (mandatory pattern)

1. Wait until the control is operable (`expect(locator).toBeVisible()` / `toBeEnabled()`).
2. Act.
3. Wait for the observable post-state: URL, title, next control, selected state, row, counter
   or business message. For a known endpoint, prepare `page.waitForResponse(...)` before the
   click, then wait for the rendered state.

Forbidden: `waitForTimeout`, sleeps, `networkidle` as a readiness signal, unbounded retries.
`expect.poll` only with explicit timeout and interval and approval in the capability spec.
Read methods wait for the rendered container and locate records by a stable identity, never
by the value the test expects.

## Shared pieces

- `src/pages/visibility.ts` → `becomesVisible(locator)` returns a boolean for tests.
- `src/pages/error/` and `src/pages/feedback/` are used by automatic fixtures (`¡Ups!` error
  tracking and the optional satisfaction survey, `specs/ui/optional-satisfaction-survey.md`).
  Do not handle them again inside a Page Object.

## Exploring the UI

Use `.agents/skills/playwright-cli/SKILL.md` (or the Playwright MCP) against QA to get
locators from the accessibility tree. Do not commit snapshots (`.playwright-mcp/` is ignored).

Reference: `src/pages/investments/investment-funds.page.ts`. After adding or changing public
methods, register the fixture (`src/fixtures/AGENTS.md`) and run `pnpm run agent:index`.
