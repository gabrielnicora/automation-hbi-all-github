# TC-001-1-0002-UNE — API — orden de extracción

- Fuente: `java/api/GP_Individuos_API/src/test/java/EntreRios/cuentas/CuentasTest.java`,
  método `TC-001-1-0002-UNE`; `Steps/Cuentas/CuentasSteps.java`, métodos
  `atmGeneratePullOrderByAccounts` y `generarOrdenDeExtracion`; payload
  `Requests/Cuentas/atmGeneratePullOrderByAccounts.json`.
- Duda: el código legado toma el último OTP del teléfono que coincide por sufijo, no filtra
  tipo y reutiliza un `idOrder` y `ajax_uuid` literales. También sólo valida HTTP/código común;
  no comprueba un saldo previo ni un identificador de orden devuelto.
- Aplicado: se consulta la cuenta origen, se lee el teléfono actual, se usa la bandeja y política
  central de segundo factor existente y se conserva payload ARS 100, DNI, referencia, versión,
  `idOrder` y `ajax_uuid`. La mutación corre una sola vez por intento. Su respuesta conserva la
  validación de HTTP 200, JSON y códigos de éxito aceptados por el validador legado.
- Efecto: un reintento del runner puede volver a solicitar la orden. La respuesta exitosa no
  demuestra por sí sola un campo/estado funcional específico de la orden.
- Portado: completado.
- Ejecución: no ejecutada; no se solicitó acceso al banco en esta migración.
- Revisión humana: pendiente; confirmar disponibilidad de saldo, origen/tipo OTP, validez de
  identificadores estáticos y contrato/resultados vigentes en BERSA QA.
- Resolución posterior: pendiente; actualizar `specs/accounts/withdrawal-order.api.plan.md`.
