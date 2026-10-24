# TC-001-2-0015-UNE — UI — detección de resultados de eCheq

- Fuente: `java/ui/GP_Migracion_Individuos/src/test/java/GP/EntreRios/front/FrontTest.java`, método `TC-001-2-0015-UNE`; validación en `steps/cuentas/echeq/EcheqSteps.java`, método `validarDatoBusquedaEhceqGrilla6meses`.
- Duda: el helper legado imprime “No se encontraron resultados” y retorna cuando la celda de fecha de emisión en fila 1, columna 7 está visible; a continuación, la misma celda se usa como condición para procesar filas. El selector no distingue claramente un estado vacío de una fila con resultados.
- Aplicado: el portado valida fecha dentro del rango, CUIT y estado para cada fila visible (hasta las diez filas que inspecciona el legado). No falla por grilla vacía, manteniendo el retorno temprano sin assertion de cantidad que presenta el método legado. El estado esperado conserva el literal `Emitido-Pendiente` pasado por el caso aunque el dropdown selecciona `Emitido pendiente`.
- Efecto: una búsqueda vacía puede terminar sin una assertion de resultado, igual que el helper legado; queda pendiente confirmar el selector inequívoco de estado vacío y la interacción real de los filtros en BERSA QA.
- Portado: completado según código disponible.
- Ejecución: no ejecutada contra BERSA QA.
- Revisión humana: pendiente; confirmar la condición de grilla vacía y si el caso debe exigir al menos un resultado.
- Resolución posterior: incorporar la decisión en la especificación de eCheq cuando se revise esta capacidad.
