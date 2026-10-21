# Checklist de cierre por lote

Usar esta lista durante el trabajo y en el cierre del lote; no exigir una tabla por caso.

- [ ] Todos los casos portados figuran en `specs/migration/migratable-cases.md`, estaban activos
      en el legado y respetan la precedencia de canal; las exclusiones e inconsistencias se
      informaron con su motivo.
- [ ] Se portaron los casos elegibles del alcance del lote con su identidad y canal efectivo.
- [ ] Se conservaron las assertions del legado y de sus helpers, salvo decisiones explícitas
      que ya las reemplazaron. No se omitieron comprobaciones para evitar fallos externos.
- [ ] Se reutilizaron los testigos y fixtures compatibles; no se agregaron capas delegadoras.
- [ ] Selectores/esperas están en Page Objects y protocolo en clientes. Credenciales y cuentas
      vienen de los datos versionados; los valores específicos no están fijados en la página.
- [ ] Las expectativas funcionales se entienden desde el test; acciones y lectura sincronizada
      respetan `SPEC.md`, sección 6.2.2, sin impedir ingresar los datos negativos del caso.
- [ ] La adaptación de los controles tiene evidencia o se identifica como pendiente de
      validación UI; no se infiere un `<select>` nativo sólo por su ID.
- [ ] Se preservaron precondiciones, limpieza y efectos propios del caso.
- [ ] Se eliminaron retries internos legados; los reintentos pertenecen a Playwright.
- [ ] Se reutilizó segundo factor central sin duplicar polling ni repetir negocio.
- [ ] Tokens, cookies y códigos no aparecen en registros de dudas ni diagnósticos sin sanitizar.
- [ ] Las dudas no detuvieron el portado; los faltantes materiales están identificados.
- [ ] Se informan checks estáticos y ejecución funcional por separado, incluyendo fallos o
      pruebas no ejecutadas. Documentar una prueba no ejecutada no equivale a aprobarla.
- [ ] Se verificaron los testigos afectados por cambios compartidos cuando el ambiente lo permitió.
- [ ] Se distingue código portado, revisión humana pendiente y validación QA pendiente/fallida.
- [ ] El cierre informa catalogados, elegibles, excluidos, sin fuente y portados; no usa 265 como
      sinónimo de migraciones terminadas.

Los datos QA faltantes o errores externos de seguros/turnos no invalidan esos ejemplos como
referencia. Las marcas de fallo esperado existentes no se heredan automáticamente.
