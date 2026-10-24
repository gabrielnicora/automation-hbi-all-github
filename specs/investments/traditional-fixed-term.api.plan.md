# Alta de plazo fijo tradicional por API — Banco Entre Ríos

## Estado

Aprobada para implementación inicial mediante `TC0042BERSA`. La señal de éxito vigente es
`COR000I`; se revisará tras la ejecución en BERSA QA si el response expone un identificador,
ticket o estado de constitución estable.

## Objetivo y alcance

Crear un plazo fijo tradicional en ARS a 30 días desde una cuenta origen. El legado usa importe
5002 y renovación `N`; UVA, USD, precancelables y consultas posteriores quedan fuera.

## Recorrido observado en el legado

Tras login y resolución de la cuenta origen ejecuta una vez el método de generación, que llama
`POST /api/v1/execute/fixedTerms.make.send`. El body histórico contiene `currency: ARS`,
`fixedTermType: T`, `amount: 5002`, `term: 30`, `renewalType: N`, cuenta origen y versión `1.78.0`.
El Java reintenta la mutación hasta cuatro veces; no se traslada.

## Diseño propuesto

- Cliente en `src/api/investments/` y datos específicos en `src/test-data/`.
- Test individual en `tests/api/007-investments/`, marcado crítico sólo si se aprueba ese criterio.
- Una mutación por intento, sin polling ni consulta de saldos salvo nueva decisión.

## Criterios de aceptación propuestos

- La cuenta origen se resuelve y valida antes de crear el plazo fijo.
- Si `ctaOrigen` no coincide con ninguna cuenta, el cliente informa datos de prueba inválidos;
  si coincide con más de una, informa datos ambiguos. En ambos casos no ejecuta la mutación.
- La request exige HTTP 200, JSON objeto y `COR000I`.
- El éxito inicial se valida mediante `COR000I`; no se infiere un identificador, ticket o estado
  de una estructura que todavía no fue confirmada.
- El cambio no incluye reintentos internos ni datos de sesión en el reporte.

## Decisiones abiertas

La ejecución en BERSA QA debe confirmar saldo, payload, versión y una evidencia funcional que
pueda reemplazar la señal provisional basada en `COR000I`.
