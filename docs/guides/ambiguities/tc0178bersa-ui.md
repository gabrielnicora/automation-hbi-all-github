# TC-007-2-0011-UNE — UI — compra de USD con importe cero

- Fuente: `java/ui/GP_Migracion_Individuos/src/test/java/GP/EntreRios/inversiones/CompraYVentaUSDTest.java`, caso `TC-007-2-0011-UNE` (también aparece en `FrontTest.java` como `TC-0178-BERSA`); pasos `CompraVentaDeDolaresSteps` y locators de `CompraVentaDeDolaresPage`.
- ID resuelto: el código ejecutable de `FrontTest.java` asigna la compra de USD con monto cero a `TC-007-2-0011-UNE`; `TC-006-2-0002-UNE` corresponde al caso de seguros. Se conserva el ID del caso de compra como `TC-007-2-0011-UNE`.
- Comportamiento resuelto: la descripción menciona validar “No se pudo realizar la operación”, pero el código ejecutable no confirma la compra ni valida ese título; comprueba “El monto ingresado debe ser mayor a 0”.
- Aplicado: se preserva login del caso, compra de dólares, cuenta origen y destino cargadas desde `ctaOrigen` y `ctaDestino`, monto `0`, continuar y el mensaje exacto del monto. El recorrido usa el botón observado “Gestioná tus inversiones” y la opción exacta “Comprar dólares” del desplegable. La cuenta destino se localiza por su etiqueta del caso dentro de la opción, que también muestra el saldo disponible. No se confirma una operación ni se agrega una aserción de título que no aparezca en el flujo ejecutable.
- Efecto: verifica el rechazo de la entrada inválida antes de confirmación.
- Portado: completado; la separación entre test y Page Object se ajustó el 28 de septiembre de 2026.
- Ejecución: pendiente tras la corrección de navegación y arquitectura.
- Revisión humana: pendiente sólo para la discrepancia entre el nombre/descripción y el mensaje realmente validado.
- Resolución posterior: actualizar la especificación de inversiones cuando se resuelva la revisión.
