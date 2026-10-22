# src/api/ — API client rules

Governing sources: `SPEC.md` sections 6.4, 8 and 9; `specs/api/failure-diagnostics.plan.md`;
the capability specification. Check `.agents/index/api.md` before creating a client.

## Responsibilities

- One client per capability under `src/api/<area>/<capability>.api.ts`, class
  `<Capability>Api`. Endpoints, headers, payloads, app versions and expected business codes
  are private constants of that file.
- Every request goes through `ReportingApiRequest.post(...)` from
  `src/api/reporting-api-request.ts`, with `data`, `endpoint`, `expectedCodes`, `headers` and
  `step`. Never call `APIRequestContext` directly: the wrapper creates the report step and
  sanitizes request/response attachments.
- `step` is a short functional description in Spanish (it appears in the report).
- Authenticated calls receive an `AuthenticatedSession` (`src/models/`) from
  `loginApi.authenticate(...)` and send `session.authorizationHeader`.
- Return small typed results (`{ isPersisted: boolean }`, lists of simple records). Validate
  the JSON shape and throw a clear error message when it is missing; leave business
  expectations to the test.
- Legacy request bodies may live as JSON templates beside the client (for example
  `src/api/transfers/transfer-templates/`) filled with `fillLegacyTemplate` from
  `src/api/legacy-template.ts`.

## Second factor and polling

- Reuse `SmsOperationOtpApi`, `SecondFactorChallengeApi` and `SecondFactorCodeInboxApi`
  (`src/api/authentication/`); they implement the approved polling and resends. Do not
  duplicate them.
- No other polling unless the capability spec approves it. Mutation requests are not
  repeated inside an attempt; scenario retries belong to the Playwright runner.

## Secrets

- If a new request or response carries a credential-like field that is not covered by
  `SECRET_FIELD_NAMES` in `reporting-api-request.ts`, add it there in the same change.
- Never log, `console.log` or attach tokens, cookies, `idSession`, OTPs or passwords.
- Error messages may include dummy usernames and transaction IDs, never codes or passwords.

Reference: `src/api/profile/notification-preferences.api.ts`. After adding a client, register
its fixture (`src/fixtures/AGENTS.md`) and run `pnpm run agent:index`.
