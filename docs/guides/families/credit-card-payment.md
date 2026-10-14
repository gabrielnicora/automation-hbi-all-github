# Familia funcional: pago de tarjeta de crédito

## Regla de uso para Kiro

Aplicar la [guía general](../README.md) y los [principios](../migration-principles.md).
Los recorridos siguientes describen testigos, no límites para otras variantes: preservar
pasos, datos, efectos y assertions del código legado y sus helpers, salvo una decisión
explícita que los reemplace para ese mismo caso. Ante dudas, portar lo ejecutable, registrar
en [ambiguities](../ambiguities/README.md) y continuar sin consultar ni exigir tablas o una
especificación individual nueva. No portar retries internos; usar Playwright y reutilizar
la política central de segundo factor. No heredar automáticamente marcas de fallo esperado.

## Propósito

Migrar recorridos de pago de tarjetas propias manteniendo separados el tipo de tarjeta, el
tipo de pago, el importe y el canal. El testigo actual valida la pantalla de revisión; no
demuestra que el pago haya sido acreditado.

## Caso testigo

TC0163BERSA: pago total de una tarjeta Mastercard propia por UI.

Referencias:

- specs/cards/credit-card-total-payment.ui.plan.md
- src/pages/cards/credit-card-payment.page.ts
- src/test-data/credit-card-total-payment-ui-data.ts
- tests/ui/cards/should-open-total-credit-card-payment-review.ui.spec.ts

## Flujo del testigo

1. Login UI real.
2. Abrir Tarjetas y Tarjeta de crédito.
3. Verificar la sección Tarjetas de crédito.
4. Abrir la tarjeta indicada por tarjetaOperar.
5. Abrir Opciones y elegir Pagar mi tarjeta.
6. Verificar Pago de tarjeta propia.
7. Seleccionar tarjeta de débito por sus últimos cuatro dígitos.
8. Continuar.
9. Seleccionar ctaOrigen en Cuenta débito.
10. Elegir Pagar el total.
11. Presionar Pagar.
12. Verificar los rótulos de la pantalla de revisión.

El testigo no confirma acreditación, ticket, saldo posterior, pago mínimo, otro importe,
USD ni tarjeta de terceros.

La fixture entrega `creditCardPaymentPage` y `creditCardTotalPaymentUiData`. Después del
login se llaman `open()`, `openOwnCardPayment(data.creditCard)`,
`selectDebitCard(data.debitCardLastFour)`, `continueToPaymentDetails()`,
`selectDebitAccount(data.originAccount)`, `selectPaymentType('Pagar el total')` y
`openPaymentReview()`.

Resultado concreto: `getMissingPaymentReviewFields(...)` debe devolver `[]` para los rótulos
`Alias por defecto`, `Saldo en pesos`, `Pago m`, `Vencimiento`, `Cuenta d`, `Tipo de pago`
e `Importe a pagar`. Esas cadenas son las del test actual, incluidas las parciales; no
convertirlas en una comprobación de importes o acreditación que no existe. Para variantes,
portar también las comprobaciones adicionales que sí ejecute el legado.

## Datos del caso

El loader usa los datos del registro TC0163BERSA:

- tarjetaDebito: últimos cuatro dígitos de la tarjeta de débito;
- ctaOrigen: cuenta utilizada en Cuenta débito;
- tarjetaOperar: tarjeta de crédito que se abre.

El loader los expone como `debitCardLastFour`, `originAccount` y `creditCard`, respectivamente.

Si el legado usa tarjetaAOperar, comprobar el mapeo al nombre actual tarjetaOperar. No copiar
valores ni asumir que QA conserva una Mastercard disponible.

## Arquitectura

- Test: tests/ui/cards/.
- Page Object: src/pages/cards/credit-card-payment.page.ts.
- Datos: src/test-data/credit-card-total-payment-ui-data.ts.
- Sin workflow: el recorrido está contenido en un Page Object.
- Locators de menú, React Select y controles dentro del Page Object.
- No navegar directamente a /creditCards ni inyectar sesión API.

El control Cuenta débito es obligatorio en el HTML QA actual aunque el legado lo tratara como
opcional. La guía debe conservar la evidencia vigente, pero registrar cualquier diferencia
que aparezca en otro caso.

## Variantes

- pago mínimo: caso separado;
- otro importe: caso separado y dato amount;
- Visa/Mastercard: verificar tarjeta y opciones disponibles;
- tarjeta de terceros: revisar destinatario y reglas;
- API: no compartir selectores ni asumir que existe la misma pantalla de revisión.

No parametrizar total, mínimo y otro importe en un único test si cambia el recorrido o el
resultado esperado.

## Ambigüedades

Registrar si no se puede confirmar la tarjeta, la cuenta, el campo de importe, el significado
de un rótulo o si el caso sólo revisa la pantalla sin ejecutar el pago.

## Checklist

- [ ] Se confirmó tipo de tarjeta y titularidad.
- [ ] Se identificó si el caso paga total, mínimo u otro importe.
- [ ] Se validaron tarjeta operada, tarjeta débito y cuenta origen.
- [ ] Los selectores permanecen en el Page Object.
- [ ] No se afirma acreditación si el testigo sólo valida revisión.
- [ ] Se conserva la detección transversal de ¡Ups!.
