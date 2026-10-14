/*
package GP.SanJuan.cuentas;

import GP.Tags.Alta;
import GP.Tags.Baja;
import GP.Tags.Criticos;
import GP.Tags.Media;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import pages.botonesgenericos.BotonesGenericosPage;
import steps.botonesgenericos.BotonesGenericosSteps;
import steps.cuentas.CuentasSteps;
import steps.cuentas.adherirnuevodestinatario.AdherirNuevoDestinatarioSteps;
import steps.cuentas.agendadedestinatarios.AgendaDeDesetinatariosSteps;
import steps.cuentas.detallecuenta.DetalleCuentaSteps;
import steps.cuentas.ordendeextraccionconsulta_generar.OrdenDeExtraccionConsulta_GenerarSteps;
import steps.cuentas.rentafinanciera.RentaFinancieraSteps;
import steps.cuentas.solicitarnuevacuenta.SolicitarNuevaCuentaSteps;
import steps.cuentas.solicituddechequera.SolicitudDeChequeraSteps;
import steps.doblefactor.DobleFactorSteps;
import steps.login.LoginSteps;
import steps.menulateral.MenuLateralSteps;
import steps.ticket.TicketSteps;
import steps.transferencias.agenda_nuevo_destinatario.AgendaNuevoDestinatarioSteps;
import steps.transferencias.limitedetransferencia.LimiteDeTransferenciaSteps;
import steps.transferencias.transferenciasinmediatas_programadas.TransferenciasInmediatasSteps;

import java.io.IOException;
import java.sql.SQLException;

import static db.ConexionDB.cerrarConexion;
import static utils.Constantes.*;

public class CuentasTest {
    MenuLateralSteps menuLateralSteps = new MenuLateralSteps();
    CuentasSteps cuentasSteps = new CuentasSteps();
    AgendaDeDesetinatariosSteps agendaDeDesetinatariosSteps = new AgendaDeDesetinatariosSteps();
    AdherirNuevoDestinatarioSteps adherirNuevoDestinatarioSteps = new AdherirNuevoDestinatarioSteps();
    LoginSteps loginSteps = new LoginSteps();
    DobleFactorSteps dobleFactorSteps = new DobleFactorSteps();
    TicketSteps ticketSteps = new TicketSteps();
    OrdenDeExtraccionConsulta_GenerarSteps ordenDeExtraccionConsulta_generarSteps = new OrdenDeExtraccionConsulta_GenerarSteps();
    DetalleCuentaSteps detalleCuentaSteps = new DetalleCuentaSteps();
    SolicitudDeChequeraSteps solicitudDeChequeraSteps = new SolicitudDeChequeraSteps();
    TransferenciasInmediatasSteps transferenciasInmediatasSteps = new TransferenciasInmediatasSteps();
    SolicitarNuevaCuentaSteps solicitarNuevaCuentaSteps = new SolicitarNuevaCuentaSteps();
    RentaFinancieraSteps rentaFinancieraSteps = new RentaFinancieraSteps();
    LimiteDeTransferenciaSteps limiteDeTransferenciaSteps = new LimiteDeTransferenciaSteps();
    BotonesGenericosSteps botonesGenericosSteps = new BotonesGenericosSteps();

    AgendaNuevoDestinatarioSteps agendaNuevoDestinatarioSteps = new AgendaNuevoDestinatarioSteps();

    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0006-BSJ: Adherir Cuenta  - Nuevo destinatario - por Alias - Descarga e impresión del PDF")
    public void TC0006BSJ() throws SQLException, InterruptedException {
        try {
            //Precondicion
            loginSteps.iniciarSessionAlEscritorio(uNazarena3);
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("","","rusia.pista.cepa");//0860001102800032106237
            //Test
            menuLateralSteps.clickCuentas();
            cuentasSteps.clickMasOpcionesGenerico();
            cuentasSteps.clickAgendaDeDestinararios();
            agendaNuevoDestinatarioSteps.clickAdherirNuevoDestinatario();
            adherirNuevoDestinatarioSteps.setCbuAlias("rusia.pista.cepa");
            adherirNuevoDestinatarioSteps.clickBotonBuscar();
            adherirNuevoDestinatarioSteps.setPerteneceA("BAEZ WALDO HECTOR YAMIL");
            adherirNuevoDestinatarioSteps.setEmail("practiagp1@gmail.com");
            transferenciasInmediatasSteps.botonSonCorrectos();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            ticketSteps.clickDescargas();
            ticketSteps.clickImprimir();
            ticketSteps.validarDescarga("Ticket.pdf");
            ticketSteps.clickVolverGenerico();
            //POSTCONDICION
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("","","rusia.pista.cepa");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }


    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0027-BSJ: Generar Orden de Extracción_Con Saldo disponible en Cuenta.")
    public void TC0027BSJ() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uCarmend);
            menuLateralSteps.clickCuentas();
            cuentasSteps.clickOrdenesDeExtraccion();
            ordenDeExtraccionConsulta_generarSteps.clickGenerarOrdenDeExtraccion();
            ordenDeExtraccionConsulta_generarSteps.validarTituloPageGenerico("Generar orden de extracci");
            ordenDeExtraccionConsulta_generarSteps.clickCuentaOrigenConSaldoSiExiste("ARS");
            ordenDeExtraccionConsulta_generarSteps.setDni("13666217");
            ordenDeExtraccionConsulta_generarSteps.setImporteGenerico("100");
            ordenDeExtraccionConsulta_generarSteps.setReferencia("T27");
            ordenDeExtraccionConsulta_generarSteps.clickBotonAgregar();
            ordenDeExtraccionConsulta_generarSteps.clickContinuarGenerico();
            dobleFactorSteps.codigoSegundoFactorOtroMetodo(uCarmend);
            ordenDeExtraccionConsulta_generarSteps.validarTituloPageGenerico("Destinatario");
            ordenDeExtraccionConsulta_generarSteps.validarTituloPageGenerico("DNI 13666217");
            ordenDeExtraccionConsulta_generarSteps.clickIconoComprobante();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            ticketSteps.clickVolverGenerico();
            ordenDeExtraccionConsulta_generarSteps.validarTituloPageGenerico("Destinatario");
            ordenDeExtraccionConsulta_generarSteps.validarTituloPageGenerico("DNI 13666217");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Alta.class})
    @DisplayName("TC-0034-BSJ: Consulta y modificación de Alias de CBU - editar alias CA en Pesos")
    public void TC0034BSJ() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uCuyo2021);
            menuLateralSteps.clickCuentas();
            cuentasSteps.clickVerMasXnroCuenta("CC $ 07856338766");
            detalleCuentaSteps.clickModificarAlias();
            detalleCuentaSteps.setModificarAlias("abcdefghijklmnopqrstuvwxyz");
            detalleCuentaSteps.clickGuardarGenerico();
            menuLateralSteps.clickCuentas();
            cuentasSteps.clickVerMasXnroCuenta("CC $ 07856338766");
            detalleCuentaSteps.clickVerAlias();
            detalleCuentaSteps.verAlias();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Alta.class})
    @DisplayName("TC-0036-BSJ: Solicitud de chequera - Normal -25 cheques - 1 chequera")
    public void TC0036BSJ() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uCuyo2021);
            menuLateralSteps.clickCuentas();
            cuentasSteps.clickVerMasXnroCuenta("CC $ 07856338766");
            detalleCuentaSteps.clickMasOpcionesGenerico();
            detalleCuentaSteps.clickSolicitudDeChequera();
            solicitudDeChequeraSteps.validarTituloPageGenerico("Solicitud de Chequera");
            solicitudDeChequeraSteps.clickDesplegableTipoDeCheque();
            solicitudDeChequeraSteps.keyDownToText( "Cheque normal");
            solicitudDeChequeraSteps.clickDesplegableCantidadDeCheques();
            solicitudDeChequeraSteps.keyDownXParametrosGenerico(0, "25");
            solicitudDeChequeraSteps.clickDesplegableCantidadDeChequeras();
            solicitudDeChequeraSteps.keyDownXParametrosGenerico(0, "1");
            solicitudDeChequeraSteps.clickContinuarGenerico();
            dobleFactorSteps.codigoSegundoFactor(uCuyo2021);
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0040-BSJ: Acceder a Consulta de movimientos de cuenta")
    public void TC0040BSJ() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uCuyo2021);
            menuLateralSteps.clickCuentas();
            cuentasSteps.clickVerMasXnroCuenta("CC $ 07856338766");
            detalleCuentaSteps.clickDetalleMovimientoPorNroFila("1");
            detalleCuentaSteps.validarTituloPageGenerico("Fecha");
            detalleCuentaSteps.validarTituloPageGenerico("de Comprobante");
            detalleCuentaSteps.validarTituloPageGenerico("Concepto");
            detalleCuentaSteps.validarTituloPageGenerico("Monto");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Media.class})
    @DisplayName("TC-0041-BSJ: Filtrar Movimientos por Tipo en una cuenta. Elegir Todos")
    public void TC0041BSJ() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uGdrio_12);
            menuLateralSteps.clickCuentas();
            cuentasSteps.clickMasOpcionesGenerico();
            cuentasSteps.clickTransferencias();
            transferenciasInmediatasSteps.seleccionarCuentaOrigen("CA $ 15008057945");
            transferenciasInmediatasSteps.seleccionarTipoTransferencia("Contacto Agendado");
            transferenciasInmediatasSteps.seleccionarPrimerContactoAgendado();
            transferenciasInmediatasSteps.setImporteGenerico("1");
            transferenciasInmediatasSteps.clickConcepto();
            transferenciasInmediatasSteps.keyDownToText("Alquileres");
            transferenciasInmediatasSteps.setDescripcion("TC0041");
            transferenciasInmediatasSteps.clickBotonTransferirAhora();
            transferenciasInmediatasSteps.clickBotonConfimar();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            //TEST
            menuLateralSteps.clickCuentas();
            cuentasSteps.validarTituloPageGenerico("Cuentas");
            cuentasSteps.clickVerMasXnroCuenta("CA $ 15008057945");
            detalleCuentaSteps.clickMasFiltros();
            detalleCuentaSteps.clickBuscar();
            detalleCuentaSteps.validarMovimiento();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Baja.class})
    @DisplayName("TC-0049-BSJ: Cliente con 1 cuenta")
    public void TC0049BSJ() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uCuyo2021);
            menuLateralSteps.clickCuentas();
            cuentasSteps.validarTituloPageGenerico("Cuentas");
            cuentasSteps.validarClienteConUnaCuenta();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Alta.class})
    @DisplayName("TC-0050-BSJ: Solicitar un Alta de Cuenta desde cuentas")
    public void TC0050BSJ() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uHOENA_2021);
            menuLateralSteps.clickCuentas();
            cuentasSteps.validarTituloPageGenerico("Cuentas");
            cuentasSteps.clickSolicitarNuevaCuenta();
            solicitarNuevaCuentaSteps.validarTituloPageGenerico("Solicitar nueva cuenta");
            solicitarNuevaCuentaSteps.validarTituloPageGenerico("Tipo de Cuenta");
            solicitarNuevaCuentaSteps.clickTipoDeCuenta();
            solicitarNuevaCuentaSteps.keyDownToText("Caja de Ahorro");
            solicitarNuevaCuentaSteps.validarTituloPageGenerico("Moneda de la Cuenta");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0067-BSJ: Solicitud de TIS_ Realizar Aumento Temporal del Límite de Transferencia de una Cuenta - Caso exitoso - Cliente con una TD y una cuenta- 2do factor por SMS")
    public void TC0067BSJ() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uGdrio_12);
            menuLateralSteps.clickCuentas();
            cuentasSteps.clickMasOpcionesGenerico();
            cuentasSteps.clickLimiteDeTransferencia();
            limiteDeTransferenciaSteps.clickCuentaOrigen();
            limiteDeTransferenciaSteps.keyDownToText( "4165");
            String importe = limiteDeTransferenciaSteps.setImporteLimite("sanjuan");
            limiteDeTransferenciaSteps.clickContinuarGenerico();
            limiteDeTransferenciaSteps.clickCheckTerminosCondicionesGenerico();
            dobleFactorSteps.codigoSegundoFactorOtroMetodo(uGdrio_12);
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            limiteDeTransferenciaSteps.actualizarLimiteDb("sanjuan", importe);
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test //Sin usuario con Renta Financiera para descargar
    @Category({Media.class})
    @DisplayName("TC-0060-BSJ: Consulta y descarga Impuestos a la Renta Financiera - Resultado exitoso")
    public void TC0060BSJ() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uCuyo2021);
            menuLateralSteps.clickCuentas();
            cuentasSteps.clickMasOpcionesGenerico();
            cuentasSteps.clickRentaFinanciera();
            rentaFinancieraSteps.clickEnDesplegableAnio("2020");
            rentaFinancieraSteps.clickBotonDescargaPorFecha("31/12/2020");
            rentaFinancieraSteps.validarDescarga("Estado-");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @After
    public void teardown() throws IOException, InterruptedException {
        global.practia.jemeinsamstark.web.core.WebDriver.closeDriver();
        cerrarConexion();
    }


}*/
