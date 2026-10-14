# Familias funcionales

Cada archivo agrupa una funcionalidad de negocio y enumera sus casos testigo actuales. Una
familia puede tener testigos API y UI, y puede orientar variantes que todavía no están
migradas.

## Familias actuales

- [Autenticación](authentication.md) — `TC0017BERSA` API/UI.
- [Transferencias](transfers.md) — `TC0139BERSA` API y `TC18088BERSA` UI.
- [Pago de tarjeta de crédito](credit-card-payment.md) — `TC0163BERSA` UI.
- [DEBIN](debin.md) — `TC0013BERSA` API.
- [Perfil](profile.md) — `TC0001BERSA`, `TC0004BERSA`, `TC0102BERSA`.
- [Inversiones](investments.md) — `TC0042BERSA`, `TC0119BERSA`.
- [Mensajes](messages.md) — `TC0109BERSA`, `TC0086BERSA`.
- [Trámites](procedures.md) — `TC0078BERSA`, `TC0052BERSA`.
- [Registro](registration.md) — `TC0156BERSA`.
- [Mis Productos](mis-products.md) — `TC0075BERSA`.
- [Seguros](insurance.md) — `TC0197BERSA`.
- [Home](home.md) — `TC0159BERSA`.
- [Cuentas](accounts.md) — `TC0027BERSA` API y `TC0041BERSA` UI.

Leer primero la [guía general](../README.md). Las familias explican patrones y límites de
los testigos; no reemplazan las assertions del caso legado por las de un ejemplo más corto.
Las especificaciones conservan las decisiones aprobadas para cada caso. Para migración
acelerada rige SPEC.md sección 3: portar lo ejecutable, registrar dudas y continuar, sin
exigir aprobación, tabla ni especificación por cada caso. No copiar retries ni `test.fail`.
