# Lote L1 — API — validaciones de `account.transfer.search` y `list.echeck`

Portado el 21 de octubre de 2026. Fuente común:
`java/api/GP_Individuos_API/src/test/java/EntreRios/cuentas/ValidacionesAPITest.java` (77 métodos
activos), `src/test/java/Steps/Cuentas/CuentasSteps.java` (`validarErroresAccountTransferSearch*`,
`validarErroresListEcheq*` y sus mapeos privados) y las plantillas `Requests/Cuentas/accountTransferSearch*.json`
y `Requests/Echeq/listEcheqValidaciones.json`. La copia de `pagos/ValidacionesAPITest.java` está
íntegramente comentada y no participa.

Migrado en:

- `tests/api/accounts/should-reject-invalid-account-transfer-search.api.spec.ts` (L1a, 59 casos) con
  `src/api/accounts/account-transfer-search.api.ts`.
- `tests/api/accounts/should-reject-invalid-echeq-list-filters.api.spec.ts` (L1b, 18 casos) con
  `src/api/accounts/echeq-list.api.ts`.

Comportamiento conservado: login API del usuario, una request por caso con las entradas exactas del
legado, HTTP 200 y código de negocio `COR020W` o `API946W` (búsqueda) / `COR020W` o `BAK001E`
(listado), como en los validadores legados. Se conservan `ajax_uuid` fijo y `xAppVersion: 1.66.0` de
la plantilla de búsqueda; la plantilla del listado no envía esos campos. Cada ID es un test
individual con datos explícitos de la variante.

## L1a/L1b — API — datos de usuario `TCBERSAPRUEBA` ausentes

- Fuente: los 77 métodos llaman `testCaseData.cargaDeDatosTest("TCBERSAPRUEBA")`.
- Faltante: `test-data/private/bersa.json` no contiene `TCBERSAPRUEBA` ni filas para los 77 IDs. El
  lector actual exige claves `TC<numero>BERSA` y `tcName` coincidente, por lo que cada test carga su
  propio ID, conforme a `SPEC.md`, sección 10.
- Aplicado: no se inventaron credenciales. Los tests fallan al cargar datos con
  `No se encontró el caso TC…BERSA en el export JSON de BERSA.` (observado para TC29517BERSA, sin
  llamar al banco).
- Portado: completado. Ejecución: bloqueada por datos.
- Revisión humana: pendiente; aportar el usuario QA dummy de `TCBERSAPRUEBA` y agregarlo como fila de
  cada ID (con `tcName` igual a la clave), o decidir en `SPEC.md` una clave de datos compartida.

## TC29461BERSA — API — "typeAccount boolean true" envía `false`

- Fuente: `ValidacionesAPITest.java:136`, llama `validarErroresAccountTransferSearchTrueFalseAliasCbu(..., false)`.
- Aplicado: se envía `typeAccount: false`, igual que TC29462BERSA.
- Revisión humana: pendiente; ¿debe enviar `true`?

## TC29529BERSA — API — "filterCuil null" envía el literal `_filterCuil_`

- Fuente: `ValidacionesAPITest.java:680`; `CuentasSteps.listEcheqNull` no reemplaza `_filterCuil_` en la
  plantilla, por lo que el campo viaja como el string `"_filterCuil_"`, no como `null`.
- Aplicado: se envía `filterCuil: "_filterCuil_"`.
- Revisión humana: pendiente; ¿debe enviar `null`?

## TC29508BERSA — API — "account CVU con apóstrofe" envía datos de CBU

- Fuente: `ValidacionesAPITest.java:498`; usa `"'30000062000003554002"` y tipo `CBU`, idénticos a
  TC29496BERSA.
- Aplicado: se conservan esos valores.
- Revisión humana: pendiente; ¿debe usar un CVU y tipo `CVU`?

## TC29472/TC29473/TC29499BERSA — API — longitudes de CBU con puntos y tipo `ALIAS`

- Fuente: líneas 235, 244 y 416. Los títulos hablan de CBU de longitud ±1, pero las cuentas son el CBU
  seguido de muchos puntos y el tipo es `ALIAS`. TC29499BERSA repite exactamente la entrada de
  TC29472BERSA.
- Aplicado: se conservan las entradas.
- Revisión humana: pendiente; confirmar las entradas previstas.

## Observaciones menores

- TC29475BERSA repite la entrada de TC29459BERSA (`typeAccount: "ALIAS1"`).
- TC29450BERSA no tiene ID en su `DisplayName`; el título usa el ID del método.
- TC29483BERSA envía `typeAccount: "VOLPATO.PESOS.BERSA"` y `account: "ALIAS"` por el orden de
  argumentos del helper; se conserva.
- `RequestSteps.executeRequest` sólo existe compilado en `dependencies/oslo.2.5.2-SNAPSHOT.jar`; no se
  pudo revisar si reintenta. No se portaron reintentos: aplica el runner de Playwright.
- El legado imprime el mensaje de error sin validarlo; el cliente lo devuelve y el reporte API lo
  muestra, sin agregar una assertion sobre su texto.
