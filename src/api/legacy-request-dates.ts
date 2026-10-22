// Formatos de fecha que el legado envía en los payloads (org.GP_Individuos_API.Utils).

function pad(value: number, length = 2): string {
  return String(value).padStart(length, '0');
}

/** `Utils.fechaTransferenciaMasAntigua(dias)`: fecha local de hace `days` días a las 00:00, sufijo `-03`. */
export function legacyStartOfDayDaysAgo(days: number, now: Date = new Date()): string {
  const start = new Date(now);
  start.setDate(start.getDate() - days);
  return `${start.getFullYear()}-${pad(start.getMonth() + 1)}-${pad(start.getDate())}T00:00:00.000-03`;
}

/** `Utils.fechaHoy()`: hora local con el literal `Z`, sin conversión a UTC. */
export function legacyNow(now: Date = new Date()): string {
  return (
    `${now.getFullYear()}-${pad(now.getMonth() + 1)}-${pad(now.getDate())}` +
    `T${pad(now.getHours())}:${pad(now.getMinutes())}:${pad(now.getSeconds())}.${pad(now.getMilliseconds(), 3)}Z`
  );
}
