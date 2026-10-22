import type { AuthenticatedSession } from '../../models/authenticated-session';
import { ReportingApiRequest, type ApiJsonObject } from '../reporting-api-request';

const ACCOUNTS_ENDPOINT = '/api/v1/execute/widgets.accounts';
const CREDIT_CARDS_ENDPOINT = '/api/v1/execute/widgets.creditCards';
const SUCCESS_CODES: readonly string[] = ['COR000I', 'COR013I', 'API690W', 'API691W', 'API692W'];
// Request común legado Requests/Compartidos/requestVacio.json.
const EMPTY_REQUEST = { ajax_uuid: '274dd2b3-9fc7-4f82-b954-34a5d784ec9a', xAppVersion: '1.95.0' } as const;

function isJsonObject(value: unknown): value is ApiJsonObject {
  return typeof value === 'object' && value !== null && !Array.isArray(value);
}

export interface UserProducts {
  readonly accounts: readonly ApiJsonObject[];
  readonly cards: readonly ApiJsonObject[];
}

export interface DebitCard {
  readonly idProduct: string;
  readonly label: string;
}

// Etiqueta con la que el legado busca la tarjeta de débito (CompartidosAPI.obtenerIdDeTarjeta).
const DEBIT_CARD_LABEL_PREFIX = 'Visa Débito **** **** **** ';
// Número enmascarado con el que el legado busca la tarjeta de crédito (CompartidosAPI.obtenerIdDeTarjetaCredito).
const CREDIT_CARD_NUMBER_PREFIX = '**** **** **** ';

/** Productos del usuario (legado CompartidosAPI.cargaServiciosCompartidos y obtenerIdDeCuenta*). */
export class ProductWidgetsApi {
  public constructor(private readonly api: ReportingApiRequest) {}

  /** Consulta cuentas y tarjetas de crédito, como el legado, y devuelve ambas listas. */
  public async load(session: AuthenticatedSession): Promise<UserProducts> {
    const accountsResponse = await this.post(session, ACCOUNTS_ENDPOINT, 'consulta de cuentas del usuario');
    const cardsResponse = await this.post(
      session,
      CREDIT_CARDS_ENDPOINT,
      'consulta de tarjetas de crédito del usuario',
    );
    const data = accountsResponse.data;

    if (!isJsonObject(data) || !Array.isArray(data.accounts)) {
      throw new Error('La consulta de cuentas no devolvió un arreglo data.accounts válido.');
    }

    const cardsData = cardsResponse.data;
    const cards = isJsonObject(cardsData) && Array.isArray(cardsData.creditCards) ? cardsData.creditCards : [];

    return { accounts: data.accounts.filter(isJsonObject), cards: cards.filter(isJsonObject) };
  }

  /** Vuelve a consultar sólo `widgets.accounts`, como `CompartidosAPI.obtenerMontoActualizado*`. */
  public async loadAccounts(session: AuthenticatedSession): Promise<UserProducts> {
    const response = await this.post(session, ACCOUNTS_ENDPOINT, 'consulta actualizada de cuentas del usuario');
    const data = response.data;

    if (!isJsonObject(data) || !Array.isArray(data.accounts)) {
      throw new Error('La consulta de cuentas no devolvió un arreglo data.accounts válido.');
    }

    return { accounts: data.accounts.filter(isJsonObject), cards: [] };
  }

  /**
   * Saldo entero de la cuenta como lo calcula el legado (`obtenerMontoCuenta*`): toma `labelWithBalance`,
   * el tramo posterior al segundo `$ ` (o `USD `), descarta decimales y quita separadores de miles.
   */
  public static legacyBalanceUnits(products: UserProducts, shortLabel: string): number {
    const account = ProductWidgetsApi.uniqueAccount(products, shortLabel);
    const label = String(account.labelWithBalance ?? '');
    const separator = shortLabel.includes('USD') ? 'USD ' : '$ ';
    const amountText = label.split(separator)[2]?.split(',')[0]?.replace(/\./g, '');
    const amount = Number(amountText);

    if (amountText === undefined || amountText.trim() === '' || !Number.isInteger(amount)) {
      throw new Error(`No se pudo leer el saldo de "${shortLabel}" desde labelWithBalance.`);
    }

    return amount;
  }

  /** Datos de la cuenta origen que usan las transferencias legadas: idProduct, currency y label. */
  public static transferOrigin(
    products: UserProducts,
    shortLabel: string,
  ): { readonly currency: string; readonly idProduct: string; readonly label: string } {
    const account = ProductWidgetsApi.uniqueAccount(products, shortLabel);

    return {
      currency: String(account.currency),
      idProduct: ProductWidgetsApi.accountId(products, shortLabel),
      label: String(account.label),
    };
  }

  /** CBU de la cuenta (`CompartidosAPI.obtenerCbuCuenta`). */
  public static accountCbu(products: UserProducts, shortLabel: string): string {
    return String(ProductWidgetsApi.uniqueAccount(products, shortLabel).cbu);
  }

  private static uniqueAccount(products: UserProducts, shortLabel: string): ApiJsonObject {
    const matches = products.accounts.filter((account) => account.shortLabel === shortLabel);

    if (matches.length !== 1) {
      throw new Error(
        matches.length === 0
          ? `Datos de prueba inválidos: la cuenta "${shortLabel}" no coincide con ninguna cuenta disponible.`
          : `Datos de prueba ambiguos: la cuenta "${shortLabel}" coincide con ${matches.length} cuentas disponibles.`,
      );
    }

    return matches[0];
  }

  /** Devuelve el idProduct de la única cuenta cuyo shortLabel coincide con el dato del caso. */
  public static accountId(products: UserProducts, shortLabel: string): string {
    const matches = products.accounts.filter((account) => account.shortLabel === shortLabel);

    if (matches.length !== 1) {
      throw new Error(
        matches.length === 0
          ? `Datos de prueba inválidos: la cuenta "${shortLabel}" no coincide con ninguna cuenta disponible.`
          : `Datos de prueba ambiguos: la cuenta "${shortLabel}" coincide con ${matches.length} cuentas disponibles.`,
      );
    }

    const idProduct = matches[0].idProduct;

    if (typeof idProduct !== 'string' || idProduct.trim().length === 0) {
      throw new Error(`La cuenta "${shortLabel}" no contiene un idProduct válido.`);
    }

    return idProduct;
  }

  /** Tarjeta de débito cuyo label termina con los últimos dígitos del caso (`tarjetaDebito`). */
  public static debitCard(products: UserProducts, lastDigits: string): DebitCard {
    const label = `${DEBIT_CARD_LABEL_PREFIX}${lastDigits}`;
    const matches = products.cards.filter((card) => card.label === label);

    if (matches.length !== 1) {
      throw new Error(
        matches.length === 0
          ? `Datos de prueba inválidos: no se encontró la tarjeta "${label}".`
          : `Datos de prueba ambiguos: la tarjeta "${label}" aparece ${matches.length} veces.`,
      );
    }

    const idProduct = matches[0].idProduct;

    if (typeof idProduct !== 'string' || idProduct.trim().length === 0) {
      throw new Error(`La tarjeta "${label}" no contiene un idProduct válido.`);
    }

    return { idProduct, label };
  }

  /** idProduct de la tarjeta de crédito cuyo número enmascarado termina con los dígitos del caso (`tarjetaOperar`). */
  public static creditCardId(products: UserProducts, lastDigits: string): string {
    const number = `${CREDIT_CARD_NUMBER_PREFIX}${lastDigits}`;
    const matches = products.cards.filter((card) => card.number === number);

    if (matches.length !== 1) {
      throw new Error(
        matches.length === 0
          ? `Datos de prueba inválidos: no se encontró la tarjeta de crédito "${number}".`
          : `Datos de prueba ambiguos: la tarjeta de crédito "${number}" aparece ${matches.length} veces.`,
      );
    }

    const idProduct = matches[0].idProduct;

    if (typeof idProduct !== 'string' || idProduct.trim().length === 0) {
      throw new Error(`La tarjeta de crédito "${number}" no contiene un idProduct válido.`);
    }

    return idProduct;
  }

  /**
   * idProduct de la cuenta como lo resuelve el legado sin validar: vacío si el caso no informa cuenta
   * o si no hay coincidencia (JSONPath sin resultados). Usado sólo donde el legado envía ese vacío.
   */
  public static legacyAccountIdOrEmpty(products: UserProducts, shortLabel: string | undefined): string {
    if (shortLabel === undefined) {
      return '';
    }

    return products.accounts
      .filter((account) => account.shortLabel === shortLabel)
      .map((account) => String(account.idProduct))
      .join(',');
  }

  private async post(session: AuthenticatedSession, endpoint: string, step: string): Promise<ApiJsonObject> {
    return this.api.post({
      data: { ...EMPTY_REQUEST },
      endpoint,
      expectedCodes: SUCCESS_CODES,
      headers: { Authorization: session.authorizationHeader },
      step,
    });
  }
}
