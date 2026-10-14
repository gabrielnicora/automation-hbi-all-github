# Requirements Document

## Introduction

This feature adds a run identification variable (`RUN_NAME`) to the GitLab CI pipeline of the hbi-api project. The variable allows users to assign a meaningful name to each pipeline execution, enabling clear identification of artifacts (test results, Allure reports) produced by separate parallel pipelines. An external application connects to the GitLab API, downloads artifacts from each run, and unifies the reports; the pipeline itself is only responsible for declaring the variable, propagating it to tests, organizing artifacts by run name, and making `RUN_NAME` queryable via the GitLab API.

## Glossary

- **Pipeline**: A GitLab CI/CD pipeline instance triggered manually or by schedule for the hbi-api project
- **RUN_NAME**: A user-defined variable that identifies a specific pipeline execution with a human-readable name
- **Runner**: A GitLab Runner agent that executes pipeline jobs
- **Artifact**: A file or directory produced by a pipeline job (test results XML, Allure JSON results, HTML reports)
- **External_App**: The external application that queries the GitLab API, downloads pipeline artifacts by RUN_NAME, and generates unified reports outside of the pipeline
- **GitLab_API**: The GitLab REST API that exposes pipeline variables and artifacts for external consumption

## Requirements

### Requirement 1: Run Name Variable Declaration

**User Story:** As a QA engineer, I want to define a run name when triggering a pipeline, so that I can identify which parallel execution produced which artifacts.

#### Acceptance Criteria

1. THE Pipeline SHALL declare a `RUN_NAME` variable in the variables section with `value: "run-${CI_PIPELINE_ID}"` and a `description` field stating it identifies parallel test executions
2. WHEN a user triggers the pipeline manually through the GitLab web interface, THE Pipeline SHALL display the `RUN_NAME` field as an editable input pre-filled with the default value `"run-${CI_PIPELINE_ID}"`
3. IF the user provides an empty value for `RUN_NAME` when triggering manually, THEN THE Pipeline SHALL use the default value `"run-${CI_PIPELINE_ID}"`
4. THE Pipeline SHALL reference `${RUN_NAME}` in artifact paths so that output directories from parallel executions are distinguishable by their run name

### Requirement 2: Run Name Propagation to Gradle

**User Story:** As a QA engineer, I want the run name passed to the test execution, so that test reports and output files include the run identifier.

#### Acceptance Criteria

1. WHEN the `run_tests` job executes, THE Pipeline SHALL pass the `RUN_NAME` variable to Gradle as a system property `-DrunName=${RUN_NAME}` appended to the Gradle command arguments
2. WHEN the `run_tests` job executes, THE Pipeline SHALL write an `environment.properties` file into `build/allure-results/` containing the key `run.name` with the value of `RUN_NAME` before the Gradle test command is invoked, creating the `build/allure-results/` directory if it does not exist
3. WHEN the `run_tests` job executes, THE Pipeline SHALL echo the `RUN_NAME` value to the job log in the `script` section before the Gradle command, in the format `Run Name: <value>`
4. IF `RUN_NAME` is not set or is empty at pipeline execution time, THEN THE Pipeline SHALL assign a default value composed of the CI pipeline ID and the current date in ISO-8601 format (e.g., `12345-2024-01-15`)

### Requirement 3: Artifact Naming with Run Identifier

**User Story:** As a QA engineer, I want artifacts to be organized by run name, so that I can locate results from a specific parallel execution.

#### Acceptance Criteria

1. WHEN the `run_tests` job completes, THE Pipeline SHALL store test result artifacts under `build/test-results/${RUN_NAME}/`, allure results under `build/allure-results/${RUN_NAME}/`, and HTML reports under `build/reports/tests/${RUN_NAME}/`
2. WHEN the `generate_report` job completes, THE Pipeline SHALL read allure results from `build/allure-results/${RUN_NAME}/` and output the Allure report to `allure-report-${RUN_NAME}/`
3. THE Pipeline SHALL preserve the existing artifact expiration policy of 7 days for all run-identified artifacts
4. IF `RUN_NAME` is not provided or is empty, THEN THE Pipeline SHALL default to the value of `CI_PIPELINE_ID` so that artifacts always receive a unique path segment

### Requirement 4: Run Name Accessibility via GitLab API

**User Story:** As a QA engineer, I want the RUN_NAME to be accessible as a pipeline variable through the GitLab API, so that the external application can query and filter pipelines by their run name to download the correct artifacts.

#### Acceptance Criteria

1. THE Pipeline SHALL declare `RUN_NAME` as a top-level pipeline variable (in the `variables:` section of `.gitlab-ci.yml`) so that the GitLab API endpoint `GET /projects/:id/pipelines/:pipeline_id/variables` returns `RUN_NAME` with its assigned value
2. THE Pipeline SHALL NOT mark `RUN_NAME` as a masked or protected variable, so that the External_App can read its value through the GitLab API without restriction
3. WHEN multiple separate pipelines are triggered with the same `RUN_NAME` value, THE Pipeline SHALL preserve that value in each pipeline's variable set, enabling the External_App to filter pipelines sharing a common run name
4. THE Pipeline SHALL ensure `RUN_NAME` is included in the pipeline variable list regardless of whether the pipeline was triggered manually, via API, or by schedule

### Requirement 5: Run Name Validation

**User Story:** As a QA engineer, I want the run name to be validated, so that artifact paths remain safe and consistent across the file system.

#### Acceptance Criteria

1. WHEN the pipeline starts, THE Pipeline SHALL validate that `RUN_NAME` contains only alphanumeric characters (a-z, A-Z, 0-9), hyphens (-), underscores (_), and dots (.), with a minimum length of 1 character and a maximum length of 64 characters
2. IF `RUN_NAME` contains characters outside the allowed set (a-z, A-Z, 0-9, hyphen, underscore, dot), THEN THE Pipeline SHALL fail the job and output an error message listing the allowed character set
3. IF `RUN_NAME` exceeds 64 characters, THEN THE Pipeline SHALL fail the job and output an error message indicating the maximum allowed length of 64 characters
4. IF `RUN_NAME` is empty or unset, THEN THE Pipeline SHALL fail the job and output an error message indicating that `RUN_NAME` is required
