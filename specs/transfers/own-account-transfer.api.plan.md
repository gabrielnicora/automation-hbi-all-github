# Transferencia inmediata entre cuentas propias por API — Banco Entre Ríos

## Estado

Implementada y validada en BERSA QA el 9 de septiembre de 2026 mediante `TC0139BERSA`.
Este caso es la referencia de arquitectura y autoría para la migración posterior de
transferencias API.

## Objetivo

Autenticar al usuario, transferir ARS 1 entre dos cuentas propias y recuperar el comprobante
PDF de la operación mediante API, sin depender de la actualización posterior de los saldos.

## Alcance

- Banco: Banco Entre Ríos (BERSA).
- Caso: `TC0139BERSA`; canal: API; tag: `@critical`.
- Tipo: transferencia inmediata `betweenOwnAccounts`.
- Moneda: ARS; importe: 1.
- Concepto: `VAR` / `Varios`; referencia: `Otros`.
- Resultado: transferencia aceptada y ticket finalizado disponible.

Transferencias a terceros, programadas, en USD, validación de saldos, segundo factor y otros
bancos quedan fuera de alcance.

## Comportamiento esperado

1. Cargar credenciales y datos de `TC0139BERSA` desde el export BERSA versionado.
2. Realizar login API y obtener una sesión autenticada.
3. Consultar `POST /api/v1/execute/widgets.accounts` y localizar exactamente las cuentas
   origen y destino por `shortLabel`.
4. Consultar `POST /api/v1/execute/widgets.creditCards` y localizar exactamente la tarjeta
   de débito por sus últimos cuatro dígitos.
5. Comprobar que ambas cuentas y la tarjeta operan en ARS, y que el CBU configurado coincide
   con el CBU devuelto para la cuenta destino.
6. Ejecutar una sola vez `POST /api/v1/execute/account.transfer.make` con el payload vigente.
7. Obtener el `idTransaction` devuelto por la transferencia.
8. Ejecutar una sola vez `POST /api/v1/execute/ticket.get` para ese identificador.
9. Confirmar que el ticket corresponde a la transferencia, está `FINISHED` y contiene un
   archivo `Ticket.pdf` codificado en Base64.

Cada request exige HTTP 200, JSON objeto y código de negocio `COR000I`.

## Payload y metadata aprobados

El cliente construye el payload de transferencia con:

- IDs, labels, número, moneda y CBU obtenidos de los widgets;
- titular y CUIT dummy del caso obtenidos del lector de datos;
- nombre legal de BERSA y constantes funcionales aprobadas en esta especificación;
- `xAppVersion: 2.04.0`;
- un `ajax_uuid` UUID v4 nuevo por request, compartido entre header y body;
- un `_bcCuil` UUID v4 por instancia del cliente, reutilizado durante el recorrido.

La evidencia del frontend mostró que `_bcCuil` coincide con el `csid` de la sesión
antifraude y se reutiliza en transferencia, ticket y requests de sesión. No es el CUIL real,
no se versiona y no se reutiliza entre tests. El cliente API no reproduce requests externas
de telemetría, analítica o antifraude observadas junto al fetch.

Se conservan los nombres de campos requeridos por el protocolo, incluidos los literales
históricos `tranferType`, `emailMessagge` y `_targetAccountNumberLabel`.

## Resultado funcional

El cliente devuelve indicadores simples para que el test compruebe:

- que el comprobante está finalizado;
- que corresponde al identificador de la transferencia;
- que el PDF informado es `Ticket.pdf` y posee Base64 no vacío.

Los identificadores y cuerpos se conservan para el diagnóstico transversal únicamente si
el test falla. El PDF Base64 se reemplaza por su longitud y ningún valor se entrega a
matchers ni se incluye directamente en mensajes de error.

## Retries y consistencia

- No se consultan saldos antes ni después de transferir.
- No existe polling en este escenario.
- La mutación `account.transfer.make` se ejecuta una sola vez dentro de cada intento.
- `ticket.get` también se ejecuta una sola vez dentro de cada intento.
- Un fallo puede reintentar el escenario completo mediante `PW_RETRIES`, con cada intento
  visible en el reporte. Esta política asume el efecto de una nueva transferencia de ARS 1.
- El test permanece secuencial porque usa una identidad y cuentas compartidas.

Esto reemplaza el comportamiento legado que reintentaba internamente la mutación hasta
cuatro veces y que esperaba saldos para comprobar débitos y créditos.

## Tag crítico

`@critical` expresa criticidad funcional y permite seleccionar este caso. No define una
suite smoke, no implica integración con Xray y no modifica la política smoke pendiente.

## Diseño aprobado

- `src/api/transfers/own-account-transfer.api.ts` contiene endpoints, payloads, selección
  de productos, metadata y validación técnica.
- `src/test-data/own-account-transfer-data.ts` valida los datos funcionales del caso.
- `src/fixtures/api-test.ts` entrega el cliente y la fixture de datos.
- `tests/api/transfers/should-transfer-between-own-accounts.api.spec.ts` conserva las
  expectativas funcionales.
- No se crea workflow, gateway, factory, perfil bancario ni capa compartida genérica.

## Criterios de aceptación

- El título comienza con `TC0139BERSA` y el caso lleva `@critical`.
- El test contiene pasos y expectativas funcionales, sin endpoints ni payloads.
- Cuentas, tarjeta y CBU se resuelven y validan antes de mutar.
- Cada request exige HTTP 200, JSON válido y `COR000I`.
- La transferencia no se reintenta dentro de un intento.
- El resultado no depende de saldos ni de polling.
- El ticket está finalizado, corresponde a la transferencia y contiene un PDF Base64.
- Cambiar endpoint o payload afecta sólo al cliente API.
- En fallos, el diagnóstico muestra IDs de transacción, `_bcCuil`, `ajax_uuid` y bodies;
  excluye tokens y reemplaza Base64 por su longitud.
- Formato, typecheck, lint y `TC0139BERSA` finalizan correctamente.

## Datos aprobados

El registro `TC0139BERSA` utiliza:

- origen `CA $ 020000052210`;
- destino `CC $ 001004555432`;
- CBU destino `3860001005000045554325`;
- tarjeta de débito terminada en `3009`;
- titular y CUIT dummy informados por el fetch BERSA validado.

Son datos QA dummy creados por el equipo. Ningún dato de producción se incorpora.

## Decisiones abiertas

Ninguna para el alcance implementado. La eventual necesidad de esperar la generación del
ticket o de integrar explícitamente el proveedor antifraude requiere evidencia y una nueva
decisión antes de agregar polling o requests externas.
