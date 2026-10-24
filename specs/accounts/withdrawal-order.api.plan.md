# Orden de extracción por API — Banco Entre Ríos

## Estado

Portado provisionalmente desde el caso legado `TC0027BERSA` bajo la migración acelerada de
`SPEC.md` sección 3. La revisión humana y la validación funcional en BERSA QA están pendientes;
las dudas están registradas en `docs/guides/ambiguities/tc0027bersa-api.md`.

## Objetivo y alcance

Generar una orden de extracción ARS de 100 desde una cuenta con saldo disponible. El alcance es
BERSA QA, API y una sola orden por intento. Consulta de saldo, otras monedas y reintentos internos
quedan fuera.

## Recorrido observado en el legado

1. Carga `TC0027BERSA`, inicia sesión y consulta productos.
2. Resuelve el número e identificador de la cuenta origen.
3. Obtiene un código de segundo factor y ejecuta
   `POST /api/v1/execute/atm.generatePullOrder.byAccounts`.

El payload legado envía una orden ARS de 100, DNI, cuenta y producto origen, un `idOrder` literal,
`_otp` con el código y versión `1.79.1`. El portado conserva estos valores, solicita el desafío
SMS y ejecuta la mutación una sola vez por intento; no traslada el retry interno legado.

## Diseño propuesto

- Cliente funcional en `src/api/accounts/`, con la obtención de productos necesaria y la mutación.
- Datos tipados del caso y configuración de segundo factor mediante las superficies existentes.
- Test en `tests/api/accounts/`, sin workflow, gateway ni reintento interno.

## Criterios de aceptación propuestos

- Cuenta y producto origen se resuelven antes de solicitar/generar la orden.
- La mutación conserva el `idOrder` literal legado y se ejecuta una única vez por intento.
- La request exige HTTP 200, JSON objeto y uno de los códigos de negocio aceptados por el
  validador legado.
- OTP, tokens y cookies no aparecen en mensajes ni evidencia.

## Decisiones abiertas

1. Confirmar que la cuenta dummy de `TC0027BERSA` posee saldo suficiente.
2. Confirmar tipo/origen exactos del código SMS en `util.codes`; el legado filtra sólo por el
   sufijo telefónico y la implementación central ahora normaliza el teléfono completo.
3. Confirmar si `idOrder` literal y `ajax_uuid` estáticos siguen siendo válidos para solicitudes
   repetidas.
4. Validar payload, versión y aceptación/campo de respuesta contra BERSA QA. El legado no
   comprueba un identificador o estado específico de la orden.
