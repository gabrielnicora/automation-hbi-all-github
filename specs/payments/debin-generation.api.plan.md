# Generación de orden DEBIN por API — Banco Entre Ríos

## Estado

Aprobada para implementación inicial mediante `TC0013BERSA`. La señal de éxito vigente es
`COR000I`; se revisará tras la ejecución en BERSA QA si el response expone un identificador o
estado estable de DEBIN.

## Objetivo y alcance

Autenticar al usuario, resolver la cuenta de origen y generar una orden DEBIN en ARS seleccionando
un CBU destino. El alcance se limita a BERSA QA, canal API y al importe de ARS 1 observado en el
legado. DEBIN recurrente, aceptación, consulta de históricos y otros bancos quedan fuera.

## Recorrido observado en el legado

1. Carga `TC0013BERSA`, inicia sesión y consulta productos compartidos.
2. Resuelve la cuenta origen y sincroniza pagos Link.
3. Ejecuta `POST /api/v1/execute/debin.generate` con cuenta origen, CBU y CUIT destino.

El payload legado contiene `typeSelector: CBU`, `amount: 1`, `currency: ARS`,
`conceptSelector: ALQ`, `reference: TestAutomation`, `validitySelector: 720` y versión `1.74.0`.
Usa un UUID literal y hasta cuatro reintentos internos; ese comportamiento no se migra.

## Diseño propuesto

- Cliente bajo `src/api/payments/` con endpoint, payload, UUID por request y validación técnica.
- Datos tipados bajo `src/test-data/`, leídos del registro versionado del caso.
- Fixture API y test en `tests/api/payments/`; sin workflow ni capa genérica.
- Una única mutación por intento; los reintentos, si se habilitan, pertenecen al runner.

## Criterios de aceptación propuestos

- El test se titula con `TC0013BERSA`, usa la fixture API y no contiene endpoint ni payload.
- Cuenta origen, CBU y CUIT se validan antes de la mutación.
- La request exige HTTP 200, JSON objeto y `COR000I`.
- La mutación se ejecuta una vez por intento; el éxito inicial se valida mediante `COR000I`.
- La evidencia de fallo excluye sesión y credenciales.

## Decisiones abiertas

La ejecución en BERSA QA debe confirmar el payload, versión y si la respuesta permite reforzar la
validación con un identificador, comprobante o estado estable. Un hallazgo reemplazará la señal
provisional basada en `COR000I`.
