# automation-hbi-all — agent instructions

You automate API and UI test cases for Banco Entre Ríos (BERSA) with Playwright Test and
TypeScript, inside the existing architecture. Most tasks add or maintain a case on a
capability that already has fixtures, Page Objects or API clients.

## Start here, every task

1. Read this file and `.agents/source-of-truth.md`. Read only the rows of its table that
   apply to the task, and read those documents completely.
2. Find what already exists in `.agents/index/` (fixtures, Page Objects, API clients,
   workflows, loaders, tests by case, data fields by case). Open a source file only when you
   will change it, or when you need behavior the catalog does not show.
3. Before editing a layer, read its `AGENTS.md` (table below).
4. The user's latest explicit decision wins. When it changes an approved decision, update the
   affected specification under `specs/` (or `SPEC.md`) before implementing.

## Map

```text
tests ──► fixtures ──┬──► pages ──► Playwright UI
                     ├──► api ────► APIRequestContext
                     └──► workflows (optional)
config, test-data ──► fixtures        models ──► pages / api / workflows
```

| Layer                          | Instructions              | Reference file                                             |
| ------------------------------ | ------------------------- | ---------------------------------------------------------- |
| `tests/`                       | `tests/AGENTS.md`         | `tests/api/profile/should-change-phone.api.spec.ts`        |
| `src/pages/`                   | `src/pages/AGENTS.md`     | `src/pages/investments/investment-funds.page.ts`           |
| `src/api/`                     | `src/api/AGENTS.md`       | `src/api/profile/notification-preferences.api.ts`          |
| `src/fixtures/`                | `src/fixtures/AGENTS.md`  | `src/fixtures/api-test.ts`, `src/fixtures/ui-test.ts`      |
| `src/workflows/`               | `src/workflows/AGENTS.md` | `src/workflows/transfers/third-party-transfer.workflow.ts` |
| `test-data/`, `src/test-data/` | `test-data/AGENTS.md`     | `src/test-data/account-selection-data.ts`                  |
| `src/config/`                  | this file                 | `src/config/environment.ts`                                |

`src/models/` holds small types shared by more than one layer. `src/config/` holds only
values that change per environment or run; never selectors, endpoints or payloads.

## Hard rules

Architecture (`SPEC.md` sections 5–7):

- BERSA is the only supported bank; `TEST_ENV=une-dev` only changes environment values.
  Never add `banks/`, `BankConfig`, bank factories, bank selectors, shared-bank profiles
  or workflow overrides, and
  never recreate `contracts/`, `features/`, `support/` or `test-setup/`.
- Tests contain no selectors, URLs, endpoints, payloads, credentials or polling. Selectors
  live in one Page Object; endpoints live in one API client.
- A workflow exists only when it coordinates two or more Page Objects or clients with
  meaningful decisions. A business layer exists only for a demonstrated reusable rule.
- Prefer reusing an existing fixture, Page Object or client over creating one. Apply SOLID,
  KISS, YAGNI and DRY without abstractions that have no proven use.
- Work incrementally and keep already migrated scenarios executable. Record material
  decisions, and the decisions they replace, in the affected specification.

Secrets and data (`SPEC.md` section 8):

- `test-data/private/*.json` is versioned dummy QA/DEV data. Use it only through the loaders
  in `src/test-data/` and refer to it by `testCaseId`. Do not copy passwords, master codes or
  codes into tests, commits, merge requests or answers; dummy usernames may be shown.
- Runtime tokens, Bearer headers, cookies and session ids are never printed, attached or
  passed to `expect` matchers (pass a boolean with a fixed message).
- Platform secrets (Jira/GitLab PATs, CI variables, `.env` files, local MCP configs) are
  never read, printed or committed. Do not run `printenv`, `env` or `cat .env`.
- Never add production URLs, credentials or personal data. Never invent users, accounts or
  cards: if `bersa.json` lacks the row or field a case needs, stop that case and report the
  exact missing fields.

Execution (`SPEC.md` section 9):

- Tests hit the real BERSA QA (or UNE DEV) environment. Run only the spec file you changed;
  do not run the full suite unless asked.
- Ask before running a case that mutates shared users (phone or address change, transfers,
  payments, DEBIN, fixed terms, withdrawal orders) unless the user already asked to run it.
- Keep one worker. Do not raise `PW_WORKERS` or enable `fullyParallel`.

## Commands

```bash
pnpm run check                                  # format, typecheck, lint, secrets, catalog
pnpm exec playwright test --list                # compiles and resolves fixtures; no bank calls
pnpm run agent:index                            # regenerate .agents/index/ after code changes
PW_RETRIES=0 pnpm exec playwright test <file>   # run one spec against QA
```

Environment variables: `TEST_ENV` (`qa` default, `une-dev`), `UI_BASE_URL`, `API_BASE_URL`,
`PW_WORKERS` (default 1), `PW_RETRIES` (default 1), `PW_BROWSER_CHANNEL`,
`SECOND_FACTOR_METHOD` (`common` default, `master-code`).

## Reading and evidence

- A search match, excerpt, catalog entry or summary is not a complete reading of a required
  document. For long files, read consecutive numbered ranges until every line is covered; if
  output is truncated, read the missing range before dependent work.
- Keep a compact checklist of sources actually read, applicable acceptance criteria,
  restrictions and open decisions, each linked to its canonical file and section.
- Read only the skills the task needs and their required references.
- After context compaction, a scope change or a source change, reopen the affected sources.
- Never claim to have read, run or complied with something you did not. Distinguish
  documented intent, observed implementation and verified behavior.
- If a required source cannot be read, report its exact path and stop only dependent work.
  Ask about material conflicts not resolved by precedence or by the user's decision.

## Specialized work

- Legacy migration: `.agents/docs/legacy-migration.md` (rules), `specs/migration/migratable-cases.md`.
- Playwright exploration, generation or debugging: `.agents/skills/playwright-cli/SKILL.md`.
  Do not commit exploration snapshots.
- Xray: `.agents/docs/integracion-xray.md` is a proposal, not an approved capability.
- New capabilities outside migration: resolve material decisions in `specs/` first. Examples
  and snippets alone do not approve behavior. Smoke selection and offline checks follow
  `SPEC.md` section 9 before adding tags, scripts or jobs.

## Done

Before the final answer: run `pnpm run check` (it fails if the catalog is stale; run
`pnpm run agent:index`), `playwright test --list`, and the affected spec when allowed. For
broad changes, compare concrete diagnostics (file, rule, message) before and after; equal
error counts do not prove there is no regression.
Report briefly: sources read, skills used, acceptance criteria, changed files, validation
results (including checks not run and why), and open assumptions or conflicts.
