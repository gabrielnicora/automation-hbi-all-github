# Términos y condiciones de Home Banking por API — Banco Entre Ríos

## Estado

Aprobada para implementación mediante `TC0159BERSA` y validada contra BERSA QA el 10 de
septiembre de 2026.

## Objetivo

Comprobar que el servicio anónimo usado por el enlace de Términos y Condiciones de la
pantalla principal devuelve el contenido general vigente en español.

## Alcance

- Banco: Banco Entre Ríos (BERSA).
- Caso: `TC0159BERSA`; canal: API.
- Funcionalidad: condiciones generales de Home Banking.
- Acceso: anónimo, sin login ni header `Authorization`.

La navegación UI del enlace, otros idiomas, otras funcionalidades y otros bancos quedan
fuera de alcance.

## Comportamiento esperado

1. Ejecutar una vez `POST /api/v1/execute/get.temsAndConditions.anonymous.ForFunctionality`.
2. Enviar `lang: es`, `functionalityCode: GENERAL_CONDITIONS`, `channel: frontend`,
   `xAppVersion: 1.88.0` y un `ajax_uuid` UUID v4 nuevo.
3. Exigir HTTP 200, un objeto JSON y código de negocio `COR000I`.
4. Confirmar que `data._termAndCondition` es texto no vacío.

El nombre `temsAndConditions` se conserva porque forma parte del endpoint observado y no
es una corrección ortográfica pendiente en el cliente.

## Evidencia y decisiones

El caso Java de referencia `TC0159BERSA` invoca el servicio anónimo con ese endpoint y
payload, y acepta `COR000I` mediante su validador transversal. La comprobación directa del
10 de septiembre de 2026 confirmó HTTP 200, `COR000I` y `_termAndCondition` como texto.

Se genera un UUID por request en lugar de reutilizar el literal versionado del proyecto
legado. No se agrega polling ni reintento interno; los reintentos pertenecen al runner.

## Diseño aprobado

- `src/api/home/terms-and-conditions.api.ts` contiene endpoint, payload y lectura de la
  respuesta.
- `src/fixtures/api-test.ts` entrega el cliente.
- `tests/api/005-home/should-get-terms-and-conditions.api.spec.ts` conserva la expectativa
  funcional.
- No se agregan credenciales, datos específicos del caso, workflow ni capas genéricas.

## Criterios de aceptación

- El título comienza con `TC0159BERSA`.
- El test expresa la operación y el resultado sin incluir endpoint ni payload.
- La solicitud no contiene `Authorization`.
- El cliente exige HTTP 200, JSON objeto y `COR000I`.
- El resultado confirma que los términos y condiciones son texto no vacío.
- La request se ejecuta una vez por intento y no usa polling.
- Formato, typecheck, lint y `TC0159BERSA` finalizan correctamente.

## Decisiones abiertas

Ninguna para el alcance implementado.
