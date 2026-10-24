# Transferencia WEB a cuenta propia por CBU — Banco Entre Ríos

## Estado

Aprobada, implementada y validada mediante `TC18088BERSA`. La ejecución UI en BERSA QA
completó la transferencia y verificó el ticket exitoso. El caso reutiliza el CBU propio ya
disponible en los datos BERSA; no agrega ni elimina destinatarios.

## Objetivo

Comprobar que una persona autenticada puede realizar una transferencia inmediata a una
cuenta propia identificada mediante CBU de otra entidad, completando todos los campos del
formulario y validando el ticket final exitoso.

## Alcance

- Banco: Banco Entre Ríos (BERSA); ambiente: QA; canal: UI.
- Caso: `TC18088BERSA`.
- Cuenta origen y CBU destino: los definidos en `test-data/private/bersa.json`.
- Importe dummy: ARS 1.
- Concepto: `Varios`; descripción: `TC18088`.
- El caso no elimina destinatarios mediante API porque el CBU ya está marcado como cuenta propia.

## Comportamiento esperado

1. Autenticar al usuario por UI.
2. Abrir Transferencias y seleccionar la búsqueda por CBU.
3. Buscar el CBU configurado y confirmar que los datos del contacto son correctos.
4. Completar importe, concepto y descripción.
5. Revisar y confirmar la transferencia.
6. Verificar el mensaje `Tu solicitud se realizó con éxito` y los datos principales del ticket.

## Diseño aprobado

- `src/pages/transfers/own-account-transfer.page.ts` contiene navegación, locators,
  mecánica del formulario, sincronización de pantallas y lectura de la revisión y el ticket.
- `src/test-data/own-account-transfer-ui-data.ts` carga y valida los campos UI del caso.
- `tests/ui/transfers/should-transfer-to-own-account-by-cbu.ui.spec.ts` expresa el flujo.
- No se crea workflow: el recorrido está contenido en un único Page Object.
- No se duplica la política transversal de segundo factor; si BERSA agrega un desafío al
  recorrido, deberá integrarse mediante la política común ya aprobada.

## Criterios de aceptación

- El test comienza con `TC18088BERSA` y no contiene credenciales, URLs ni selectores.
- El CBU se lee desde el export privado y se valida con formato de 22 dígitos.
- Se completan importe, concepto y descripción antes de confirmar.
- El ticket muestra éxito, cuenta origen, cuenta destino y monto esperado.
- Las comparaciones del importe, las cuentas y el mensaje de éxito viven en el test; el
  Page Object devuelve el contenido renderizado después de esperar cada pantalla.
- Los errores identifican el paso funcional sin exponer tokens ni cookies.

## Decisión de arquitectura — 28 de septiembre de 2026

Las expectativas funcionales de la revisión y del ticket se trasladan al test, que ya define
los datos aprobados del caso. `OwnAccountTransferPage` mantiene la interacción, las esperas
de sincronización y la lectura del contenido renderizado. Esta decisión reemplaza el diseño
previo que validaba dentro del Page Object las cuentas, el importe y el éxito.
