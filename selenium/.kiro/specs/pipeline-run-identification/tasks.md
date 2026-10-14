# Implementation Plan: Pipeline Run Identification

## Overview

This plan modifies `.gitlab-ci.yml` to declare, validate, and propagate a `RUN_NAME` variable through the existing pipeline jobs. Artifacts are reorganized under RUN_NAME-scoped directories. No new jobs are added — the existing `run_tests` and `generate_report` jobs are extended. A shell-based test suite validates the `RUN_NAME` validation logic.

## Tasks

- [ ] 1. Declare RUN_NAME variable and add validation function
  - [ ] 1.1 Add RUN_NAME to the top-level variables section of `.gitlab-ci.yml`
    - Add `RUN_NAME` with `value: "run-${CI_PIPELINE_ID}"` and a `description` field
    - Ensure it is NOT marked as `masked` or `protected`
    - Place it alongside existing variables like `GRADLE_TASK`, `ENVIRONMENT`, etc.
    - _Requirements: 1.1, 1.2, 1.3, 4.1, 4.2, 4.4_

  - [ ] 1.2 Add RUN_NAME validation logic to the `run_tests` job `before_script`
    - Insert a validation block at the beginning of `before_script` (before DNS setup)
    - Check that `RUN_NAME` is not empty/unset — fail with: `ERROR: RUN_NAME is required. Cannot be empty or unset.`
    - Check length does not exceed 64 characters — fail with: `ERROR: RUN_NAME exceeds maximum length of 64 characters (got <length>).`
    - Check value matches `^[a-zA-Z0-9._-]+$` using `grep -qE` — fail with: `ERROR: RUN_NAME contains invalid characters. Allowed: a-z, A-Z, 0-9, hyphen (-), underscore (_), dot (.)`
    - Exit with code 1 on any validation failure
    - _Requirements: 5.1, 5.2, 5.3, 5.4_

  - [ ]* 1.3 Write property test for RUN_NAME validation (bats-core)
    - **Property 1: RUN_NAME Validation Correctness**
    - Extract the validation logic into a standalone shell function in `ci/validate_run_name.sh`
    - Create `ci/tests/validate_run_name.bats` using bats-core
    - Generate random valid strings (alphanumeric, hyphen, underscore, dot, 1-64 chars) and verify acceptance
    - Generate random invalid strings (spaces, slashes, special chars, empty, >64 chars) and verify rejection
    - Minimum 100 generated inputs per category
    - **Validates: Requirements 5.1, 5.2, 5.3, 5.4**

- [ ] 2. Propagate RUN_NAME to Gradle and write Allure metadata
  - [ ] 2.1 Add RUN_NAME propagation to the `run_tests` job `script` section
    - Echo `Run Name: ${RUN_NAME}` to the job log before the Gradle command
    - Create `build/allure-results/` directory with `mkdir -p build/allure-results/`
    - Write `run.name=${RUN_NAME}` to `build/allure-results/environment.properties`
    - Append `-DrunName=${RUN_NAME}` to the ARGS array passed to Gradle
    - _Requirements: 2.1, 2.2, 2.3_

  - [ ]* 2.2 Write unit tests for Gradle propagation logic
    - Create `ci/tests/propagation.bats` using bats-core
    - Test that `environment.properties` is created with correct `run.name=<value>` content
    - Test that ARGS array includes `-DrunName=<value>`
    - Test that echo output contains `Run Name: <value>`
    - _Requirements: 2.1, 2.2, 2.3_

- [ ] 3. Checkpoint - Validate variable declaration and propagation
  - Ensure all tests pass, ask the user if questions arise.

- [ ] 4. Organize artifacts by RUN_NAME
  - [ ] 4.1 Add artifact relocation logic to `run_tests` job `after_script`
    - Add an `after_script` block to the `run_tests` job
    - Move test results: `build/test-results/` contents → `build/test-results/${RUN_NAME}/`
    - Move allure results: `build/allure-results/` files → `build/allure-results/${RUN_NAME}/`
    - Move HTML reports: `build/reports/tests/` contents → `build/reports/tests/${RUN_NAME}/`
    - Use `mkdir -p` for target directories, `2>/dev/null || true` for graceful failure on empty sources
    - Preserve the `environment.properties` file in the relocated allure-results directory
    - _Requirements: 3.1, 3.4_

  - [ ] 4.2 Update artifact paths in `run_tests` job
    - Update `artifacts.paths` to reference RUN_NAME-scoped directories:
      - `build/allure-results/${RUN_NAME}/`
      - `build/test-results/${RUN_NAME}/`
      - `build/reports/tests/${RUN_NAME}/`
    - Update `artifacts.reports.junit` glob to `build/test-results/${RUN_NAME}/**/*.xml`
    - Keep `expire_in: 7 days` unchanged
    - _Requirements: 3.1, 3.3_

  - [ ] 4.3 Update `generate_report` job to use RUN_NAME-scoped paths
    - Add RUN_NAME validation at the start of the `generate_report` script (reuse same logic)
    - Change allure input from `build/allure-results` to `build/allure-results/${RUN_NAME}`
    - Change allure output from `allure-report` to `allure-report-${RUN_NAME}/`
    - Update `artifacts.paths` to `allure-report-${RUN_NAME}/`
    - Keep `expire_in: 7 days` unchanged
    - _Requirements: 3.2, 3.3_

  - [ ]* 4.4 Write property test for artifact path correctness
    - **Property 2: RUN_NAME Propagation Correctness**
    - Create `ci/tests/artifact_paths.bats` using bats-core
    - For generated valid RUN_NAME values, simulate artifact relocation and verify:
      - Test results land under `build/test-results/<RUN_NAME>/`
      - Allure results land under `build/allure-results/<RUN_NAME>/`
      - HTML reports land under `build/reports/tests/<RUN_NAME>/`
      - `environment.properties` contains `run.name=<RUN_NAME>`
    - Minimum 100 generated inputs
    - **Validates: Requirements 2.2, 3.1**

- [ ] 5. Final checkpoint - Validate complete pipeline configuration
  - Ensure all tests pass, ask the user if questions arise.
  - Validate `.gitlab-ci.yml` syntax using `gitlab-ci-lint` or equivalent
  - Verify RUN_NAME appears as a top-level variable (not nested inside a job)

## Notes

- Tasks marked with `*` are optional and can be skipped for faster MVP
- Each task references specific requirements for traceability
- Checkpoints ensure incremental validation
- The design uses shell/YAML — no pseudocode language selection needed
- Property tests validate universal correctness properties from the design document
- Unit tests validate specific examples and edge cases
- Report unification is handled by an external application — no pipeline tasks for it
- The validation function is extracted to `ci/validate_run_name.sh` for testability, then sourced in `.gitlab-ci.yml`

## Task Dependency Graph

```json
{
  "waves": [
    { "id": 0, "tasks": ["1.1", "1.2"] },
    { "id": 1, "tasks": ["1.3", "2.1"] },
    { "id": 2, "tasks": ["2.2", "4.1"] },
    { "id": 3, "tasks": ["4.2", "4.3"] },
    { "id": 4, "tasks": ["4.4"] }
  ]
}
```
