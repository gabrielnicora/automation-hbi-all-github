# Familia funcional: cuentas

Aplicar la [guía general](../README.md), los [principios](../migration-principles.md) y el
proceso de migración acelerada. El legado determina pasos, datos y expectativas; registrar las
dudas en `ambiguities/` sin detener el portado.

## Caso testigo API — `TC0027BERSA`

Generación de una orden de extracción ARS 100 desde la cuenta origen configurada, con OTP SMS.
El caso está portado provisionalmente; quedan pendientes revisión humana y validación en BERSA
QA.

Archivos actuales:

- Especificación: `specs/accounts/withdrawal-order.api.plan.md`.
- Cliente: `src/api/accounts/withdrawal-order.api.ts`.
- Datos: `src/test-data/withdrawal-order-data.ts`.
- Test: `tests/api/accounts/should-generate-withdrawal-order.api.spec.ts`.
- Dudas: `docs/guides/ambiguities/tc0027bersa-api.md`.

El testigo consulta `widgets.accounts`, resuelve por `ctaOrigen` el `idProduct` y `number`,
solicita el código mediante el segundo factor compartido y ejecuta una vez
`atm.generatePullOrder.byAccounts`. El payload conserva del legado ARS 100, DNI, referencia
`TestAutomation`, versión `1.79.1` y los identificadores literales enviados por el caso.
El test no afirma saldo previo ni un estado de orden no comprobado por el legado.

Al migrar otros casos de cuentas, no asumir que usan la misma operación, monto, moneda,
segundo factor, cuenta o contrato de respuesta. Conservar sus aserciones legadas y comparar sus
payloads antes de reutilizar el cliente.

## Caso UI — `TC0041BERSA`

El método vigente para este ID está en `FrontTest.java`; métodos homónimos de otros archivos
legacy están deprecados y no se migran. El escenario Playwright aprobado en BERSA QA usa la
cuenta `CC $ 001003501497`, amplía los filtros de movimientos, retrocede dos meses, selecciona
el día 8, elige “Tipo de transacción” en “Buscar por” y verifica que exista la primera fila de
movimientos. El usuario confirmó que la cuenta tiene movimientos y la ejecución UI completó
el recorrido.

La cuenta se lee desde el registro `TC0041BERSA` del export privado. No reutilizar cuentas de
otros usuarios ni asumir que una cuenta homónima de otro caso es equivalente. El método Java
`validarMovimiento()` sólo exige que la primera fila esté visible; el test no afirma campos o
datos concretos del movimiento.

El título legacy se corrigió para describir el filtro ejecutado: “Filtrar movimientos por tipo
de transacción en una cuenta”. La diferencia entre el antiguo “Elegir Todos” y el paso
ejecutable “Tipo de transacción” queda resuelta a favor del comportamiento del método en
`FrontTest.java`.
