# Accelerated legacy migration — agent rules

Moved from the root `AGENTS.md` on 2026-10-22 without changing their normative content
(`SPEC.md`, section 3, "Contexto para agentes de automatización"). Read this file only when
the task analyzes or ports a legacy case. The governing decision is `SPEC.md`, section 3,
"Migración acelerada de casos legados"; operational guide: `docs/guides/README.md`.

## Sources and eligibility

- Accelerated legacy migration follows the exception in `SPEC.md` section 3: a missing or
  unapproved per-case specification does not block translating existing code.
- Read legacy API sources from `java/api/GP_Individuos_API/` and legacy UI sources from
  `java/ui/GP_Migracion_Individuos/`, relative to the repository root. Use these local copies
  instead of external checkouts or remote repositories. For UI, only `FrontTest.java` methods
  define migratable cases; follow other legacy classes only as dependencies of those methods.
- `java/` and `web/` are excluded from default searches by `.ignore`. Search them with an
  explicit path, for example `rg <pattern> java/`.
- Before analyzing or porting a legacy case, check `specs/migration/migratable-cases.md`.
  Migrate only catalogued, active cases and apply its declared-channel precedence and
  inconsistency rules. Do not migrate uncatalogued cases or definitions marked ignored,
  disabled, obsolete or deprecated.
- Treat `WEB` as UI. Determine inactivity from the test method or containing class; a
  deprecated helper alone does not exclude an otherwise active case.
- Record per-case progress in `docs/guides/migration-status.md`.

## Porting

- Complete the port using the executable legacy case and its dependencies as the functional
  reference; use current witnesses for architecture. Preserve legacy assertions (URL, ticket,
  locator, etc.), data and effects unless an explicit approved decision already replaces that
  behavior for the same case.
- Apply `SPEC.md` section 6.2.2. Do not copy an existing witness's responsibility violations.
- Do not port legacy internal retry loops; use Playwright runner retries. Reuse existing
  approved second-factor polling/challenge resends without duplicating them.

## Ambiguities and missing sources

- Do not stop or ask about migration ambiguities: record the evidence and applied behavior in
  `docs/guides/ambiguities/` for later human review and continue. Do not require a per-case
  comparison table.
- For material conflicts during migration, preserve the legacy behavior provisionally, record
  the conflict for later human review, and continue.
- Missing source/dependencies must be reported precisely; translate what is available and
  continue the batch without inventing values or claiming false completion.
