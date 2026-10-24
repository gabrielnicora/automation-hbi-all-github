# Familias funcionales

Cada archivo agrupa una funcionalidad de negocio y enumera sus casos testigo actuales. Una
familia puede tener testigos API y UI, y puede orientar variantes que todavía no están
migradas.

## Familias actuales

- [Autenticación](authentication.md) — `TC-002-1-0001-UNE (API) / TC-002-2-0001-UNE (UI)` API/UI.
- [Transferencias](transfers.md) — `TC-015-1-0003-UNE` API y `TC-015-2-0004-UNE` UI.
- [Pago de tarjeta de crédito](credit-card-payment.md) — `TC-003-2-0010-UNE` UI.
- [DEBIN](debin.md) — `TC-011-1-0002-UNE` API.
- [Perfil](profile.md) — `TC-013-1-0001-UNE`, `TC-013-2-0001-UNE`, `TC-013-1-0002-UNE`.
- [Inversiones](investments.md) — `TC-007-1-0002-UNE`, `TC-007-2-0004-UNE`.
- [Mensajes](messages.md) — `TC-009-1-0003-UNE`, `TC-009-2-0001-UNE`.
- [Trámites](procedures.md) — `TC-012-1-0001-UNE`, `TC-012-2-0001-UNE`.
- [Registro](registration.md) — `TC-014-2-0002-UNE`.
- [Mis Productos](mis-products.md) — `TC-010-2-0001-UNE`.
- [Seguros](insurance.md) — `TC-006-2-0002-UNE`.
- [Home](home.md) — `TC-005-1-0001-UNE`.
- [Cuentas](accounts.md) — `TC-001-1-0002-UNE` API y `TC-001-2-0003-UNE` UI.

Leer primero la [guía general](../README.md). Las familias explican patrones y límites de
los testigos; no reemplazan las assertions del caso legado por las de un ejemplo más corto.
Las especificaciones conservan las decisiones aprobadas para cada caso. Para migración
acelerada rige SPEC.md sección 3: portar lo ejecutable, registrar dudas y continuar, sin
exigir aprobación, tabla ni especificación por cada caso. No copiar retries ni `test.fail`.
