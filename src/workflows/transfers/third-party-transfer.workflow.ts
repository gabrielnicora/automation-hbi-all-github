import { ProductWidgetsApi, type UserProducts } from '../../api/accounts/product-widgets.api';
import type {
  ThirdPartyTransfersApi,
  TransferDebitCard,
  TransferDestination,
  TransferOrigin,
} from '../../api/transfers/third-party-transfers.api';
import type { AuthenticatedSession } from '../../models/authenticated-session';

export interface TransferOriginProducts {
  readonly debitCard: TransferDebitCard;
  readonly origin: TransferOrigin;
  /** Productos consultados antes de transferir; sirven para leer el saldo inicial. */
  readonly products: UserProducts;
}

/**
 * Preparación común de las transferencias legadas a terceros (TransferenciasTest): coordina la
 * agenda de destinatarios y los productos del usuario. Devuelve lo observado; las expectativas
 * quedan en cada test.
 */
export class ThirdPartyTransferWorkflow {
  public constructor(
    private readonly transfers: ThirdPartyTransfersApi,
    private readonly productWidgets: ProductWidgetsApi,
  ) {}

  /** Legado validar*Agendado + eliminarContactoAgendado: busca el destino y lo quita de la agenda si estaba. */
  public async findDestinationOutsideAgenda(
    session: AuthenticatedSession,
    accountOrAlias: string,
    typeAccount: 'ALIAS' | 'CBU',
  ): Promise<TransferDestination> {
    const destination = await this.transfers.findDestination(session, accountOrAlias, typeAccount);

    if (destination.isAdhered && destination.idAdheredAccount !== undefined) {
      await this.transfers.removeDestination(session, destination.idAdheredAccount);
    }

    return destination;
  }

  /**
   * Legado agendarNuevoContacto + validarCbuAgendado: agenda el CBU con la referencia indicada y
   * vuelve a buscarlo. Devuelve el destino actualizado y el id de agenda vigente.
   */
  public async adhereAndFindDestination(
    session: AuthenticatedSession,
    username: string,
    belongsTo: string,
    previous: TransferDestination,
  ): Promise<{ readonly destination: TransferDestination; readonly idAdheredAccount: string }> {
    const createdId = await this.transfers.adhereDestination(
      session,
      username,
      belongsTo,
      previous.cbu,
      previous.ownAccount,
    );
    const destination = await this.transfers.findDestination(session, previous.cbu, 'CBU');

    return { destination, idAdheredAccount: destination.idAdheredAccount ?? createdId };
  }

  /** Legado cargaServiciosCompartidos + obtenerIdDeCuentaOrigen/Currency/Label + tarjeta de débito. */
  public async loadOriginProducts(
    session: AuthenticatedSession,
    originAccount: string,
    debitCardLastDigits: string,
  ): Promise<TransferOriginProducts> {
    const products = await this.productWidgets.load(session);

    return {
      debitCard: ProductWidgetsApi.debitCard(products, debitCardLastDigits),
      origin: ProductWidgetsApi.transferOrigin(products, originAccount),
      products,
    };
  }

  /** Legado obtenerMontoActualizadoOrigen: vuelve a consultar cuentas y lee el saldo (sin la pausa fija). */
  public async readBalanceAfter(session: AuthenticatedSession, originAccount: string): Promise<number> {
    return ProductWidgetsApi.legacyBalanceUnits(await this.productWidgets.loadAccounts(session), originAccount);
  }
}
