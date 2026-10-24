# Pago total de tarjeta Mastercard propia por UI — Banco Entre Ríos

## Estado

Aprobada para implementación inicial mediante `TC0163BERSA`, pendiente de validación funcional
en BERSA QA una vez que el equipo confirme los datos disponibles.

## Objetivo y alcance

Validar la pantalla de revisión del pago total de una tarjeta de crédito Mastercard propia desde
una cuenta origen configurada. El caso no confirma la acreditación, no verifica un ticket y no
abarca Visa, pago mínimo, otro importe, USD ni tarjetas de terceros.

## Recorrido observado en el legado

El caso `TC0163BERSA` de `GP/EntreRios/front/FrontTest.java` ingresa a la sección `creditCards`,
valida `Tarjetas de crédito`, abre el detalle de `tarjetaCredito`, accede a `Opciones` y elige
`Pagar mi tarjeta`. En `Pago de tarjeta propia`, selecciona `cuentaOrigen` como tarjeta de débito,
continúa, vuelve a seleccionar `cuentaOrigen` como cuenta de débito sólo si ese control aparece,
elige `Pagar el total`, presiona `Pagar` y verifica los campos de la pantalla posterior.

## Datos de prueba

- Las credenciales se cargan desde el registro `TC0163BERSA` de `test-data/private/bersa.json`.
- `tarjetaDebito` provee los últimos cuatro dígitos de la tarjeta seleccionada en el primer
  control `Tarjeta de débito`.
- `ctaOrigen` provee la cuenta usada en el selector posterior obligatorio `Cuenta débito`.
- `tarjetaOperar` provee la tarjeta a abrir. Este campo reemplaza en el export BERSA actual el
  nombre legado `tarjetaAOperar`.
- Los datos deben identificar una Mastercard propia operable y una cuenta origen disponible. Su
  disponibilidad en QA es una precondición administrada por el equipo.

## Diseño aprobado

- Test UI individual bajo `tests/ui/cards/`, Page Object bajo `src/pages/cards/` y lector tipado
  bajo `src/test-data/`.
- La fixture UI compone el Page Object y los datos del caso; no se crea workflow porque el
  recorrido está contenido en una sola página funcional.
- El Page Object espera el cambio de pantalla después de `Pagar` y devuelve el texto
  renderizado de la revisión. El test compara allí los rótulos aprobados.
- Después del login UI normal, el Page Object espera el menú lateral, presiona `Tarjetas` y luego
  `Tarjeta de crédito`. Esta decisión reemplaza la navegación directa a `/creditCards` usada por
  el legado junto con inyección de sesión API, que está fuera de alcance en la arquitectura
  vigente. El HTML actual confirma ambos botones y sus nombres accesibles.
- Los botones se localizan por sus nombres accesibles cuando el legado expone `aria-label`.
  Los controles desplegables y el enlace de detalle conservan XPath local al Page Object: el
  legado sólo evidencia su relación DOM y no una alternativa accesible estable.
- El selector actual `Tarjeta de débito` se abre mediante el control
  `#selectorControlBy_cards.make.payment.making.debitCardSelector`; su `input[role=combobox]`
  interno es `readonly` y no es el objetivo de click. Sus opciones se identifican por el valor de
  `tarjetaDebito`, como `Visa Débito **** **** **** 8006`.
- El selector posterior `Cuenta débito` se identifica desde su rótulo visible y el control React
  Select que lo sigue en el mismo bloque. La ejecución QA de 2026-09-18 confirmó que el
  contenedor no expone de forma consistente `#cards.make.payment.making.account` y que el control
  interno puede indicar erróneamente `paymentType`; por eso no se usa ninguno de esos `id`. La
  opción se selecciona desde su menú React Select con el valor de `ctaOrigen`.
- El selector `Tipo de pago` se identifica desde su rótulo visible y el control React Select de
  su mismo bloque; la ejecución QA de 2026-09-18 confirmó que tampoco expone de forma consistente
  `#cards.make.payment.making.paymentType`. Elige `Pagar el total` en su menú React Select. No
  completa importe: el campo `input[name=amount]` sólo aparece para `Ingresar un importe`; también
  se oculta al elegir `Pagar el mínimo`.
- Luego de `Continuar`, el selector `Cuenta débito` es un paso obligatorio de este caso: se
  espera su contenedor, se abre y se selecciona `ctaOrigen` antes de interactuar con el tipo de
  pago. Esta decisión reemplaza la condición heredada de "si aparece": en el HTML QA actual el
  control está presente y omitirlo produce un pago incompleto.
- La fixture transversal existente detecta y adjunta cualquier página `¡Ups!` que aparezca.

## Criterios de aceptación

- El test se identifica como `TC0163BERSA` y carga exclusivamente sus credenciales, `ctaOrigen`,
  `tarjetaDebito` y `tarjetaOperar`.
- Tras autenticarse, el menú lateral permite abrir `Tarjetas` y `Tarjeta de crédito`; se
  visualiza la sección `Tarjetas de crédito` y el detalle de la tarjeta configurada permite
  acceder a `Pagar mi tarjeta` desde `Opciones`.
- Se visualiza `Pago de tarjeta propia`, se selecciona la tarjeta configurada por sus últimos
  cuatro dígitos, se espera y selecciona la cuenta configurada en `Cuenta débito`, y queda
  elegido `Pagar el total`.
- Después de presionar `Pagar`, la pantalla muestra los rótulos observados: `Alias por defecto`,
  `Saldo en pesos`, `Pago m`, `Vencimiento`, `Cuenta d`, `Tipo de pago` e `Importe a pagar`.
- El test contiene las expectativas de esos rótulos; el Page Object no recibe la lista
  esperada ni decide cuáles están ausentes.
- No hay sleeps fijos, `networkidle`, reintentos internos ni datos de sesión en el reporte.
- Formato, typecheck, lint y la ejecución funcional en QA finalizan correctamente antes de
  declarar validada la capacidad.

## Decisiones abiertas

La ejecución BERSA QA debe confirmar que los datos de `TC0163BERSA` siguen exponiendo una
Mastercard y una cuenta seleccionable, y que los selectores heredados continúan representando el
DOM actual.

Los recorridos de pago mínimo y de otro importe se modelarán como casos UI independientes cuando
se soliciten, con su propio identificador, datos aprobados y expectativa de importe. No se
parametrizan dentro de `TC0163BERSA` porque cambia el recorrido funcional y el resultado esperado.
El caso futuro de otro importe utilizará `input[name=amount]` con el importe específico aprobado.

## Decisión de arquitectura — 28 de septiembre de 2026

La lista esperada de rótulos de revisión se mantiene en el test. El Page Object espera que el
botón `Pagar` deje de estar visible después de la acción y devuelve el texto de la pantalla
renderizada. Esta decisión reemplaza el método que recibía los rótulos esperados y reportaba
cuáles no encontraba.
