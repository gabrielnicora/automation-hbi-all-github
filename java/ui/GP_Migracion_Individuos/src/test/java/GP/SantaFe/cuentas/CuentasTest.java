package GP.SantaFe.cuentas;

import GP.Tags.Alta;
import GP.Tags.Baja;
import GP.Tags.Criticos;
import GP.Tags.Media;
import io.qameta.allure.Allure;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import pages.botonesgenericos.BotonesGenericosPage;
import steps.botonesgenericos.BotonesGenericosSteps;
import steps.calendarios.CalendarioSteps;
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
import steps.transferencias.limitedetransferencia.LimiteDeTransferenciaSteps;
import steps.transferencias.transferenciasinmediatas_programadas.TransferenciasInmediatasSteps;
import utils.UtilsGP;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
    CalendarioSteps calendarioSteps= new CalendarioSteps();
    BotonesGenericosSteps botonesGenericosSteps = new BotonesGenericosSteps();
    BotonesGenericosPage botonesGenericosPage = new BotonesGenericosPage();
    UtilsGP utilsGP = new UtilsGP();

    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0006-BSF: Adherir Cuenta  - Nuevo destinatario - por Alias - Descarga e impresión del PDF")
    public void TC0006BSF() throws InterruptedException {
        try {
            //PRECONDICION
            loginSteps.iniciarSessionAlEscritorio(uTranspull4);

            /*menuLateralSteps.clickCuentas();
            cuentasSteps.clickMasOpcionesGenerico();
            cuentasSteps.clickAgendaDeDestinararios();
            agendaDeDesetinatariosSteps.clickCuentasTerceros();
            agendaDeDesetinatariosSteps.eliminarContacto("BAEZ WALDO HECTOR YAMIL");*/

            //TEST
            menuLateralSteps.clickCuentas();
            cuentasSteps.clickMasOpcionesGenerico();
            cuentasSteps.clickAgendaDeDestinararios();
            agendaDeDesetinatariosSteps.clickAdherirNuevoDestinatario();
            adherirNuevoDestinatarioSteps.clickAlias();
            adherirNuevoDestinatarioSteps.setCbuAlias("rusia.pista.cepa");
            adherirNuevoDestinatarioSteps.clickBotonBuscar();
            adherirNuevoDestinatarioSteps.clickContinuarGenerico();
            dobleFactorSteps.codigoSegundoFactor();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            ticketSteps.clickDescargas();
            ticketSteps.clickImprimir();
            ticketSteps.validarDescarga("Ticket.pdf");
            ticketSteps.clickVolverGenerico();
            //POSTCONDICION
            menuLateralSteps.clickCuentas();
            cuentasSteps.clickMasOpcionesGenerico();
            cuentasSteps.clickAgendaDeDestinararios();
            agendaDeDesetinatariosSteps.clickCuentasTerceros();
            agendaDeDesetinatariosSteps.eliminarContacto("BAEZ WALDO HECTOR YAMIL");
        } catch (Exception ex) {

            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0027-BSF: Generar Orden de Extracción_Con Saldo disponible en Cuenta.")
    public void TC0027BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uPrueba2734);
            menuLateralSteps.clickCuentas();
            cuentasSteps.clickOrdenesDeExtraccion();
            ordenDeExtraccionConsulta_generarSteps.clickGenerarOrdenDeExtraccion();
            ordenDeExtraccionConsulta_generarSteps.validarTituloPageGenerico("Generar orden de extracc");
            //ordenDeExtraccionConsulta_generarSteps.clickCuentaOrigen();
            //ordenDeExtraccionConsulta_generarSteps.keyDownToText( "Caja de Ahorro $ 000006339303");
            ordenDeExtraccionConsulta_generarSteps.setDni("2500001");
            ordenDeExtraccionConsulta_generarSteps.setImporteGenerico("100");
            ordenDeExtraccionConsulta_generarSteps.setReferencia("T27");
            ordenDeExtraccionConsulta_generarSteps.clickBotonAgregar();
            ordenDeExtraccionConsulta_generarSteps.clickContinuarGenerico();
            //dobleFactorSteps.clickSms();
            dobleFactorSteps.codigoSegundoFactor();
            ordenDeExtraccionConsulta_generarSteps.validarTituloPageGenerico("Destinatario");
            ordenDeExtraccionConsulta_generarSteps.validarTituloPageGenerico("DNI 2500001");
            ordenDeExtraccionConsulta_generarSteps.clickIconoComprobante();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            ticketSteps.clickVolverGenerico();
            ordenDeExtraccionConsulta_generarSteps.validarTituloPageGenerico("Destinatario");
            ordenDeExtraccionConsulta_generarSteps.validarTituloPageGenerico("DNI 2500001");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Alta.class})
    @DisplayName("TC-0034-BSF: Consulta y modificación de Alias de CBU - editar alias CA en Pesos")
    public void TC0034BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uTranspull4);
            menuLateralSteps.clickCuentas();
            cuentasSteps.clickVerMasXnroCuenta("CA $ 000021333006");
            detalleCuentaSteps.clickModificarAlias();
            detalleCuentaSteps.setModificarAlias("abcdefghijklmnopqrstuvwxyz");
            detalleCuentaSteps.clickGuardarGenerico();
            menuLateralSteps.clickCuentas();
            cuentasSteps.clickVerMasXnroCuenta("CA $ 000021333006");
            detalleCuentaSteps.clickVerAlias();
            detalleCuentaSteps.verAlias();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Alta.class})
    @DisplayName("TC-0036-BSF: Solicitud de chequera - Normal -25 cheques - 1 chequera")
    public void TC0036BSF() throws SQLException, InterruptedException, UnsupportedEncodingException {
        try {
            loginSteps.iniciarSessionAlEscritorio(uTranspull4);
            menuLateralSteps.clickCuentas();
            cuentasSteps.clickVerMasXnroCuenta("CC $ 000021333202");
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
            dobleFactorSteps.codigoSegundoFactor();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
        
    }
    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0040-BSF: Acceder a Consulta de movimientos de cuenta")
    public void TC0040BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uTranspull4);
            menuLateralSteps.clickCuentas();
            cuentasSteps.clickVerMasXnroCuenta("CC $ 000021333202");
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
    @DisplayName("TC-0041-BSF: Filtrar Movimientos por Tipo en una cuenta. Elegir Todos")
    public void TC0041BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uTranspull4);
            menuLateralSteps.clickCuentas();
            cuentasSteps.clickMasOpcionesGenerico();
            cuentasSteps.clickTransferencias();
            transferenciasInmediatasSteps.seleccionarCuentaOrigen("CA $ 000021333006");
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
            cuentasSteps.clickVerMasXnroCuenta("CA $ 000021333006");
            detalleCuentaSteps.clickMasFiltros();
            calendarioSteps.clickEnFechaDeCreacionDesde();
            calendarioSteps.clickMesAnterior(3);
            calendarioSteps.clickEnDiaXParametro("8");
            detalleCuentaSteps.clickBuscar();
            detalleCuentaSteps.validarMovimiento();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Baja.class})
    @DisplayName("TC-0049-BSF: Cliente con 1 cuenta")
    public void TC0049BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uSantafe038);
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
    @DisplayName("TC-0050-BSF: Solicitar un Alta de Cuenta desde cuentas")
    public void TC0050BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uTranspull4);
            menuLateralSteps.clickCuentas();
            cuentasSteps.validarTituloPageGenerico("Cuentas");
            cuentasSteps.clickSolicitarNuevaCuenta();
            solicitarNuevaCuentaSteps.validarTituloPageGenerico("Solicitar nueva cuenta");
            solicitarNuevaCuentaSteps.validarTituloPageGenerico("Tipo de Cuenta");
            solicitarNuevaCuentaSteps.clickTipoDeCuenta();
            solicitarNuevaCuentaSteps.keyDownToText( "Caja de Ahorro");
            solicitarNuevaCuentaSteps.validarTituloPageGenerico("Moneda de la Cuenta");
            solicitarNuevaCuentaSteps.clickMonedaDeLaCuenta();
            solicitarNuevaCuentaSteps.keyDownToText("Pesos");
            solicitarNuevaCuentaSteps.validarBotonContinuar();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Media.class})
    @DisplayName("TC-0060-BSF: Consulta y descarga Impuestos a la Renta Financiera - Resultado exitoso")
    public void TC0060BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uVolpatomonica);
            menuLateralSteps.clickCuentas();
            cuentasSteps.clickMasOpcionesGenerico();
            cuentasSteps.clickRentaFinanciera();
            rentaFinancieraSteps.clickEnDesplegableAnio("2020");
            rentaFinancieraSteps.clickBotonDescargaPorFecha("31/12/2020");
            rentaFinancieraSteps.validarDescarga("Estado-31122020.pdf");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    //@Test
    @Category({Criticos.class})
    @DisplayName("TC-0067-BSF: Solicitud de TIS_ Realizar Aumento Temporal del Límite de Transferencia de una Cuenta - Caso exitoso - Cliente con una TD y una cuenta- 2do factor por SMS")
    public void TC0067BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uTranspull4);
            menuLateralSteps.clickCuentas();
            cuentasSteps.clickMasOpcionesGenerico();
            cuentasSteps.clickLimiteDeTransferencia();
            limiteDeTransferenciaSteps.clickCuentaOrigen();
            limiteDeTransferenciaSteps.keyDownToText("Caja de Ahorro $ 000021333006");
            String importe = limiteDeTransferenciaSteps.setImporteLimite("santafe");
            limiteDeTransferenciaSteps.clickContinuarGenerico();
            limiteDeTransferenciaSteps.clickCheckTerminosCondicionesGenerico();
            dobleFactorSteps.codigoSegundoFactor();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            limiteDeTransferenciaSteps.actualizarLimiteDb("santafe", importe);
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @After
    public void cerrarSesionCapturarUPS(){
        if(utilsGP.estaPresente(botonesGenericosPage.getCodigoUps(),2) && botonesGenericosPage.getCodigoUps().getText().startsWith("#")) {
            String ups = botonesGenericosPage.getCodigoUps().getText();
            botonesGenericosSteps.clickVolverGenerico();
            loginSteps.clickSalirDelaSesion();
            loginSteps.waitGP();
            Assert.fail("UPS CODIGO : " + ups);
        }else if(utilsGP.estaPresente(botonesGenericosPage.getSalirSesion(),2))
        {
            loginSteps.clickSalirDelaSesion();
            loginSteps.waitGP();
        }else{
            System.out.println("=================== NO PUDO INICIAR SESION REFRESCAR NAVEGADOR ==============================");
            Allure.addAttachment("INICIO SESSION,","=================== NO PUDO INICIAR SESION REFRESCAR NAVEGADOR ==============================");
            global.practia.jemeinsamstark.web.core.WebDriver.getInstance().navigate().to(System.getProperty("bank"));
            System.out.println("Se Espera que aparezca la pagina de nuevo");
            loginSteps.waitGP();
        }
    }
    @AfterClass
    public static void teardown() throws IOException, InterruptedException {
        global.practia.jemeinsamstark.web.core.WebDriver.closeDriver();
        cerrarConexion();
    }

}