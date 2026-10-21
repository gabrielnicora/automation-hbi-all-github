# Inventario del catálogo — casos sin fuente o con dudas de identificación

Inventario del 21 de octubre de 2026 contra las copias locales de `java/`. Estado consolidado en
[migration-status.md](../migration-status.md).

## TC110846BERSA — UI — método legado con prefijo `TR`

- Fuente: `java/ui/GP_Migracion_Individuos/src/test/java/GP/EntreRios/front/FrontTest.java`,
  método `TR110846BERSA` (`@DisplayName("TR-110846-BERSA: Web-Detalle de los pagos realizados en
simultaneo-...")`). No existe un método `TC110846BERSA` en `FrontTest.java`.
- Duda: el método `TR110846BERSA` podría ser el caso catalogado con un error de tipeo en el
  prefijo, o un caso distinto.
- Aplicado: decisión del usuario del 21 de octubre de 2026: no se toma `TR110846BERSA` como fuente;
  el caso queda `sin fuente` y no se porta.
- Portado: no aplica. Ejecución: no aplica.
- Revisión humana: pendiente; confirmar si `TR110846BERSA` es el caso `TC110846BERSA`. De ser así,
  registrar la decisión en el catálogo y portarlo en el lote UI de pagos (L11).
