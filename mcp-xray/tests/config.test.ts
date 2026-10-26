import assert from 'node:assert/strict';
import test from 'node:test';

import { ConfigError, loadConfig } from '../src/config.js';

const ENV_KEYS = [
  'XRAY_JIRA_BASE_URL',
  'XRAY_JIRA_PAT',
  'XRAY_REQUEST_TIMEOUT_MS',
] as const;

function withRequiredEnvironment(): void {
  process.env.XRAY_JIRA_BASE_URL = 'https://jira.example.test/';
  process.env.XRAY_JIRA_PAT = 'test-pat';
  delete process.env.XRAY_REQUEST_TIMEOUT_MS;
}

test.afterEach(() => {
  for (const key of ENV_KEYS) delete process.env[key];
});

test('usa 30 segundos como timeout por defecto', () => {
  withRequiredEnvironment();

  const config = loadConfig();

  assert.equal(config.jiraBaseUrl, 'https://jira.example.test');
  assert.equal(config.requestTimeoutMs, 30_000);
});

test('rechaza un timeout que no sea entero positivo', () => {
  withRequiredEnvironment();
  process.env.XRAY_REQUEST_TIMEOUT_MS = '0';

  assert.throws(() => loadConfig(), ConfigError);
});
