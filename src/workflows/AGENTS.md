# src/workflows/ — workflow rules

Governing sources: `SPEC.md` sections 5 (YAGNI) and 6.3; `docs/test-authoring.md`
("Cuándo crear un workflow"). Existing workflows are listed in `.agents/index/api.md`.

- Create a workflow only when the operation crosses two or more Page Objects or clients, or
  holds a reusable sequence with functional decisions used by more than one scenario.
- Never create one that delegates a single call. A short sequence can stay in the test.
- Path `src/workflows/<area>/<operation>.workflow.ts`, class `<Operation>Workflow`, plain
  TypeScript with no interface or factory.
- Receive data, return what was observed. Expectations stay in the test.
- No locators, endpoints or environment configuration: use Page Objects and clients.

Reference: `src/workflows/transfers/third-party-transfer.workflow.ts`.
