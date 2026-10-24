# TC-006-2-0002-UNE — UI — detalle actual del seguro de Robo

- Fuente: `java/ui/GP_Migracion_Individuos/src/test/java/GP/EntreRios/front/FrontTest.java`, caso `TC-006-2-0002-UNE`; `src/main/java/steps/seguros/SegurosSteps.java`, métodos `clickCardSeguroRobo()` y `validacionDetalleSeguroRobo()`; `src/main/java/pages/seguros/SegurosPage.java`, localizador `getCardSeguroRobo()`.
- Duda: el legado localiza la opción “Robo Bolso” y comprueba cuatro textos (“ESTIMADO CLIENTE”, “LE OFRECE”, “UN SEGURO DE”, “BOLSO PROTEGIDO”), pero la UI QA actual muestra la tarjeta “Bolso” y abre una página de planes titulada “Seguro de Bolso”. Los cuatro textos no aparecen en esa pantalla.
- Aplicado: se hace clic en la tarjeta visible “Bolso” y se comprueban el encabezado “Seguro de Bolso” y su descripción actual “Protección para tus pertenencias en la vía pública”. Se quitó el `test.fail` provisional que no existe en el legado.
- Efecto: se valida el detalle actual sin intentar contratar un plan. La assertion de textos legados queda reemplazada provisionalmente por la identidad observable del detalle actual.
- Portado: completado con esta diferencia documentada.
- Ejecución: una ejecución real llegó al detalle y falló al buscar “ESTIMADO CLIENTE”; pendiente rerun con las expectativas actuales.
- Revisión humana: pendiente; confirmar si la nueva pantalla y el cambio de textos son el comportamiento BERSA aceptado para este caso.
- Resolución posterior: actualizar la especificación de seguros al resolver la revisión.
