# Registro de dudas abiertas para revisión humana posterior

Registrar dudas mientras se completa la migración, sin detenerse a consultar. Se permite un
archivo por lote (por ejemplo lote-perfil.md) con varias secciones, o uno por caso. Cada sección
debe identificar caso y canal para no confundir API y UI del mismo identificador.

Plantilla breve:

```markdown
## TC-<FFF>-<C>-<NNNN>-UNE — API/UI — duda breve

- Fuente: ruta legada y método, más línea o revisión cuando esté disponible.
- Duda: diferencia o intención incierta.
- Aplicado: comportamiento y assertion conservados; archivo migrado.
- Efecto: consecuencia conocida o limitación.
- Portado: completado / parcial por faltante concreto.
- Ejecución: aprobada / fallida (causa) / no ejecutada (motivo).
- Revisión humana: pendiente; pregunta a resolver.
- Resolución posterior: decisión y enlace a la especificación al resolverla.
```

No hace falta crear archivos para casos sin dudas. Al resolver una duda, consolida la decisión
vigente en su especificación o guía canónica, conserva sólo el historial que ayude a entender
qué decisión reemplazó y elimina el registro de `ambiguities/`. Esta carpeta contiene únicamente
decisiones todavía abiertas o validaciones pendientes, no el historial completo de migración.
No copiar credenciales, OTP, tokens o cookies. Un error externo puede registrarse junto a la duda
y no detiene el lote. Un registro aquí no declara aprobado un resultado.
