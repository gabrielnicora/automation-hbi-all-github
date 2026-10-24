# Pago de tarjeta Visa por otro importe por API — Banco Entre Ríos

## Estado

Migración acelerada implementada para `TC12967BERSA`; el log legado aportado el 22 de septiembre de
2026 demuestra que el POST de pago con su payload histórico devuelve `COR000I`. Se alineó el cliente
migrado con esa request. La verificación QA del código actualizado está pendiente.

## Objetivo y alcance

Pagar ARS 1 como otro importe de una tarjeta de crédito Visa desde la cuenta y tarjeta de débito
del caso. Mastercard, importe mínimo, USD, pago total y consultas posteriores quedan fuera.

## Recorrido observado en el legado

Después de login consulta `/widgets.accounts` y `/widgets.creditCards` con `ajax_uuid` fijo
`274dd2b3-9fc7-4f82-b954-34a5d784ec9a` y `xAppVersion: 1.95.0`; resuelve cuenta origen, tarjeta
de débito y tarjeta de crédito a pagar. Ejecuta `POST /api/v1/execute/creditCard.make.payment` por
ARS 1 usando `Requests/Tarjetas/creditCardMakePayment.json`. Ese payload envía los tres IDs, importe,
moneda, `ticketData` como string JSON literal, `ajax_uuid` fijo
`54e704fe-84b5-4f71-b37e-762261f9e21a` y `xAppVersion: 1.81.1`; el `ticketData` contiene
`dueDate: 2025-05-08T00:00:00.000-03`, `balanceArs: 9049.47`, `balanceUsd: 0` y
`minimumPayment: 9049.47`. El log aportado muestra respuesta `COR000I`. El Java reintenta la
mutación hasta cuatro veces; no se migra.

## Diseño implementado

- Cliente en `src/api/cards/credit-card-other-amount-payment.api.ts` que resuelve y valida exactamente
  los tres productos configurados.
- Datos tipados en `src/test-data/credit-card-other-amount-payment-data.ts`; test individual en
  `tests/api/003-cards/should-pay-credit-card-other-amount.api.spec.ts`.
- Una única mutación por intento; no se consulta saldo ni se reintenta internamente.
- La mutación envía los IDs en los campos `accountId`, `debitCardId` y `creditCardId`, requeridos
  por la respuesta de validación observada en BERSA QA.
- Conserva el `ticketData` literal y los metadatos de request del JSON legado. No agrega
  `debitCardLabel`, que no forma parte de ese payload.
- Evidencia de la request migrada previa: los tres IDs eran idénticos a los del log legado, pero se
  omitía `ticketData`, se enviaba `xAppVersion: 1.79.1`, se agregaba `debitCardLabel` y se generaban
  `ajax_uuid` nuevos; la API respondió `COR020W`.

## Criterios de aceptación de la migración

- Los productos origen se resuelven de manera única y se valida que la tarjeta objetivo sea Visa.
- La request exige HTTP 200, JSON objeto, código de negocio aprobado y resultado de pago verificable.
- El importe se declara como dato del caso y la request no se reintenta internamente.
- El reporte conserva IDs funcionales, pero no sesión ni credenciales.

El resultado funcional queda limitado a la aceptación de la mutación (`COR000I`), porque el legado
disponible no aporta una comprobación posterior estable de acreditación.

## Decisiones abiertas

1. Confirmar en QA el mapeo de cuenta, tarjeta de débito y tarjeta Visa dummy de `TC12967BERSA`.
2. Definir si `ticketData` se obtiene de una consulta actual, se construye dinámicamente o ya no es
   requerido por el endpoint.
3. Definir evidencia funcional posterior al pago: ticket, transacción o estado de respuesta.
4. Confirmar si el contrato legado fijo sigue vigente después de la ejecución QA actualizada; no
   reemplazar su fecha histórica ni derivar otro `ticketData` sin evidencia del endpoint.
