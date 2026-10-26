import assert from 'node:assert/strict';
import test from 'node:test';

import type { XrayServerConfig } from '../src/config.js';
import { validateProjectKey, validateTestKey, XrayServerClient } from '../src/xray-client.js';

function config(): XrayServerConfig {
  return {
    jiraBaseUrl: 'https://jira.example.test',
    jiraPat: 'secret-pat',
    defaultProjectKey: 'XT',
    requestTimeoutMs: 30_000,
    fields: {
      candidato: 'customfield_18831',
      automatizado: 'customfield_19100',
      regresion: 'customfield_18832',
      criticidad: 'customfield_19002',
      dispositivo: 'customfield_18835',
      equipo: 'customfield_18833',
      precondicion: 'customfield_18834',
      tipoTest: 'customfield_18800',
    },
    defaults: {
      candidato: 'Si',
      automatizado: 'No',
      regresion: 'No',
      criticidad: 'Media',
      equipo: 'Equipo A',
      precondicion: 'N/A',
      tipoTest: 'Manual',
    },
    automation: {
      candidatoSi: 'Si',
      automatizadoSi: 'Si',
      automatizadoNo: 'No',
    },
  };
}

function jsonResponse(body: unknown, status = 200, statusText = 'OK'): Response {
  return new Response(JSON.stringify(body), {
    status,
    statusText,
    headers: { 'content-type': 'application/json' },
  });
}

test('valida claves de proyecto y Test antes de construir requests', () => {
  assert.equal(validateProjectKey(' XT '), 'XT');
  assert.equal(validateTestKey(' XT-123 '), 'XT-123');
  assert.throws(() => validateProjectKey('XT" OR project = OPS'), /projectKey inválido/);
  assert.throws(() => validateTestKey('../XT-123'), /testKey inválido/);
});

test('escapa labels al construir el JQL', async () => {
  let requestBody: unknown;
  const fetchMock: typeof fetch = async (_input, init) => {
    requestBody = JSON.parse(String(init?.body));
    return jsonResponse({ total: 0, issues: [] });
  };
  const client = new XrayServerClient(config(), fetchMock);

  await client.listarTests({ projectKey: 'XT', labels: ['qa"critical', 'ruta\\uno'] });

  assert.deepEqual(requestBody, {
    jql:
      'project = "XT" AND issuetype = "Test" AND labels IN ("qa\\"critical", "ruta\\\\uno") ORDER BY key ASC',
    startAt: 0,
    maxResults: 50,
    fields: ['summary', 'status', 'labels', 'customfield_18831', 'customfield_19100'],
  });
});

test('sanitiza el body de errores HTTP', async () => {
  const fetchMock: typeof fetch = async () =>
    new Response('respuesta interna con datos sensibles', {
      status: 500,
      statusText: 'Internal Server Error',
    });
  const client = new XrayServerClient(config(), fetchMock);

  await assert.rejects(client.verificarConexion(), (error: Error) => {
    assert.match(error.message, /HTTP 500 Internal Server Error/);
    assert.doesNotMatch(error.message, /datos sensibles/);
    assert.doesNotMatch(error.message, /secret-pat/);
    return true;
  });
});

test('informa timeouts sin exponer detalles internos', async () => {
  const fetchMock: typeof fetch = async () => {
    const error = new Error('socket con secret-pat');
    error.name = 'TimeoutError';
    throw error;
  };
  const client = new XrayServerClient(config(), fetchMock);

  await assert.rejects(client.verificarConexion(), (error: Error) => {
    assert.match(error.message, /excedió el timeout de 30000 ms/);
    assert.doesNotMatch(error.message, /secret-pat/);
    return true;
  });
});

test('reporta la creación parcial y no repite el POST del issue', async () => {
  let issueCreations = 0;
  let stepRequests = 0;
  const fetchMock: typeof fetch = async (input, init) => {
    const url = String(input);
    if (url.endsWith('/rest/api/2/issue') && init?.method === 'POST') {
      issueCreations++;
      return jsonResponse({ id: '10001', key: 'XT-123' });
    }
    if (url.endsWith('/step') && init?.method === 'PUT') {
      stepRequests++;
      if (stepRequests === 1) return new Response(null, { status: 204 });
      return new Response('detalle interno de Jira', { status: 500, statusText: 'Error' });
    }
    throw new Error(`Request inesperado: ${init?.method} ${url}`);
  };
  const client = new XrayServerClient(config(), fetchMock);

  await assert.rejects(
    client.crearTestManual({
      projectKey: 'XT',
      summary: 'Caso parcial',
      dispositivo: 'API',
      steps: [{ action: 'Paso 1' }, { action: 'Paso 2' }],
    }),
    (error: Error) => {
      assert.match(error.message, /Test XT-123 fue creado/);
      assert.match(error.message, /paso 2 después de 1 paso/);
      assert.match(error.message, /No reintentar la creación automáticamente/);
      assert.doesNotMatch(error.message, /detalle interno de Jira/);
      return true;
    }
  );
  assert.equal(issueCreations, 1);
  assert.equal(stepRequests, 2);
});
