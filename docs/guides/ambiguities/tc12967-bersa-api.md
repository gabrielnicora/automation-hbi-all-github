# TC-003-1-0007-UNE — API — pago Visa por otro importe

- Fuentes: `java/api/GP_Individuos_API/src/test/java/EntreRios/tarjetas/TarjetasTest.java`
  (`TC-003-1-0007-UNE`), `java/api/GP_Individuos_API/src/test/java/Steps/Tarjetas/TarjetaSteps.java`
  (`creditCardMakePayment`, `realizarPagoOtroImporte`), `java/api/GP_Individuos_API/Requests/Tarjetas/creditCardMakePayment.json`,
  `java/api/GP_Individuos_API/Requests/Compartidos/requestVacio.json` y el log exitoso
  entregado por el usuario.
- Duda inicial resuelta por el log: el payload real no sólo requiere los tres IDs; incluye además
  `ticketData` como string JSON y usa `xAppVersion: 1.81.1` en la mutación.
- Aplicado: se portó el recorrido disponible: login, consulta de cuentas y tarjetas, selección única
  de cuenta origen, tarjeta de débito y tarjeta Visa, y una mutación por ARS 1.
- Comparación: cuenta, débito y crédito seleccionados producen los mismos `idProduct` que el log
  legado. La request migrada omitía `ticketData`, usaba `xAppVersion: 1.79.1`, agregaba
  `debitCardLabel` y generaba `ajax_uuid` dinámicos. El legado usa el JSON literal de la plantilla,
  sin `debitCardLabel`, versión `1.81.1` para el pago y UUID fijo.
- Observación QA: la primera ejecución respondió HTTP 200 con `COR020W` y señaló requeridos
  `accountId`, `debitCardId` y `creditCardId`; esos nombres se aplicaron al payload.
- Observación QA posterior: la request ya envía los tres nombres requeridos y el endpoint sigue
  respondiendo HTTP 200 con `COR020W`, pero ahora devuelve `data: {}` sin indicar el campo inválido.
- Evidencia legacy: el POST con `ticketData`, los mismos IDs y `xAppVersion: 1.81.1` terminó en
  `COR000I` y el build Gradle resultó exitoso.
- Aplicado ahora: replicar la plantilla del pago y los metadatos de los endpoints de widgets; se
  preserva una sola mutación por intento y no se portan retries Java.
- Pendiente: volver a ejecutar el test migrado en QA para comprobar que el payload alineado también
  sea aceptado.
- Portado: payload y flujo legado recuperados; validación QA de la implementación migrada pendiente.
- Seguridad: el log adjunto contiene un header Bearer sin sanitizar; no se copió al proyecto ni se
  reproduce en este documento.
