# automation-hbi-all repository instructions

## Source of truth

- Before substantive repository analysis, planning or changes, read this file and
  `.agents/source-of-truth.md` completely. Discover and read applicable nested
  `AGENTS.md` and `AGENTS.override.md` files along the paths being worked on;
  an override takes precedence over AGENTS.md in the same directory.
- Before changing architecture, structure, tests or implementation in this repository,
  read `SPEC.md` completely.
- Treat `SPEC.md` as the main Specification-Driven Development source
  of truth for this framework.
- Before implementing a capability, also read its applicable specification under `specs/`.
  Accelerated legacy migration follows the exception in SPEC.md section 3: a missing or
  unapproved per-case specification does not block translating existing code.
- The user's latest explicit decision takes precedence. When it changes an approved
  decision, update the corresponding specification before implementing the change.

## Complete reading and evidence

- Read the applicable canonical documents selected by `.agents/source-of-truth.md`
  before proposing a repository plan. Read only relevant skills and their required
  references; never load all skills or all specifications indiscriminately.
- A search match, heading list, first/last excerpt, conversation summary or previous
  assistant answer does not count as a complete reading of a required document.
- For long files, obtain the line count and read consecutive, numbered ranges until
  every line is covered. Keep each output small enough to inspect without truncation.
  If any tool output is truncated, reduce the range and read the missing content
  before proceeding with dependent work. A successful command exit is not proof of
  a complete reading. Do not combine large documents into one output.
- Keep a compact task checklist of sources actually read (complete or exact ranges),
  applicable acceptance criteria, restrictions, and unresolved decisions. Connect
  each material criterion to its canonical file and section. This checklist helps
  navigation; it does not replace the sources or become a second specification.
- Before editing, reread the applicable behavior and restrictions in their sources.
  Before validation, reread the acceptance criteria and required checks. Before the
  final answer, compare the changes and observed results with those criteria and
  reread the governing sections. Unchanged unrelated sections need not be reloaded.
- After context compaction, a scope change, or a source change, reopen the affected
  canonical sources before continuing. If prior full coverage is uncertain, read
  those documents completely again. Do not infer requirements from remembered context.
- Never claim to have read or complied with content that was not actually inspected.
  Distinguish documented intent, observed implementation and verified behavior.
- If a required source cannot be read, report its exact path and stop only the work
  that depends on it. For material conflicts not resolved by instruction precedence
  or the user's explicit decision, explain the conflict and ask before implementing, except
  for accelerated legacy migration: preserve the legacy behavior provisionally, record the
  conflict for later human review, and continue as authorized in SPEC.md section 3.
- For repository tasks, report briefly: sources actually read, selected skills (or
  none), acceptance criteria, changed files (or none), validation results (including
  checks not run), and unresolved assumptions/conflicts (or none). Scale detail to
  the task; do not print whole documents or claim that this protocol guarantees recall.

## Active scope and target architecture

- Banco Entre Ríos (BERSA) is the only currently supported bank. UNE will replace BERSA in
  this project when available and validated, as recorded in `SPEC.md`.
  Simultaneous bank support and Banco Santa Fe remain out of scope.
- The current capabilities use the single-bank, layer-oriented architecture defined in
  `SPEC.md`. Technical layers use functional subfolders when needed.
- Do not add new dependencies on `banks/`, `BankConfig`, bank factories, bank selectors,
  shared-bank profiles or workflow overrides.
- For UI, use Page Object Model: selectors and UI mechanics live in the corresponding
  Page Object under `src/pages/<area>/`.
- Apply `SPEC.md` section 6.2.2: Page Objects receive scenario values, expose UI actions and
  synchronized observations, and keep case-specific data and business expectations in tests
  and test data. Do not copy an existing witness's responsibility violations during migration.
- Use workflows only when they coordinate multiple Page Objects or meaningful functional
  decisions. A workflow that only delegates one call must not be created.
- Keep endpoints and payload protocol in the API client under `src/api/<area>/`.
- Keep cross-layer models small and explicit under `src/models/`.
- Keep fixture composition under `src/fixtures/`, execution configuration under
  `src/config/`, and versioned test-data loaders under `src/test-data/`.
- Create a business/domain layer only for a demonstrated reusable business rule.
- Do not recreate legacy `banks/`, `contracts/`, `features/`, `support/` or `test-setup/`
  layers.

## Working rules

- For future Xray integration work, read `.agents/docs/integracion-xray.md` first.
  It records the proposed design and unresolved decisions, not an implemented or
  approved capability. Resolve those decisions in `specs/`
  before changing tests, reporters, scripts or CI for Xray.

- Use Playwright Test with TypeScript for API and UI automation.
- Treat the versioned data under `test-data/private/` as team-created dummy QA/DEV data,
  not as production secrets. Never add production credentials or production personal data;
  keep runtime session tokens and cookies out of logs, reports and attachments.
- Work incrementally and keep already migrated BERSA scenarios executable.
- Read legacy API sources from `java/api/GP_Individuos_API/` and legacy UI sources
  from `java/ui/GP_Migracion_Individuos/`, relative to this repository root, as approved
  in `SPEC.md` section 3. Use these local copies instead of external checkouts or remote
  repositories. For UI, only `FrontTest.java` methods define migratable cases; follow
  other legacy classes only as dependencies of those methods. Report missing local
  sources precisely and apply the accelerated migration missing-source policy.
- In accelerated migration, complete the port using the executable legacy case and its
  dependencies as the functional reference; use current witnesses for architecture.
  Preserve legacy assertions (URL, ticket, locator, etc.), data and effects unless an
  explicit approved decision already replaces that behavior for the same case.
- Do not stop or ask about migration ambiguities: record the evidence and applied behavior
  in docs/guides/ambiguities/ for later human review and continue. Do not require a per-case
  comparison table. Missing source/dependencies must be reported precisely; translate what
  is available and continue the batch without inventing values or claiming false completion.
- Do not port legacy internal retry loops; use Playwright runner retries. Reuse existing
  approved second-factor polling/challenge resends without duplicating them.
- For genuinely new capabilities outside migration, resolve material specification decisions
  before implementation. Examples and snippets alone do not approve new behavior.
- Apply SOLID, KISS, YAGNI and DRY without introducing abstractions that have no proven
  use.
- Keep tests readable for a QA Automation engineer who does not know the internal fixture
  composition.
- Follow the case-design and validation guidance in `docs/test-authoring.md`.
  Separate functional variants; parameterize data-only differences with individual results.
- Record material decisions and the decisions they replace in the affected specification.
- For broad changes, compare concrete validation diagnostics before and after the change;
  do not treat equal error counts as proof of no regression.
- Smoke selection and offline environment checks follow the policies in `SPEC.md`.
  Define their scope before adding tags, tests, scripts or CI jobs.
- Keep the default Playwright execution sequential while API and UI scenarios reuse test
  identities. Increase workers only with documented data isolation.
- For Playwright exploration, generation or debugging, use the repository skill at
  `.agents/skills/playwright-cli/SKILL.md` and read the relevant references it routes to.
