package GP.SantaCruz.cuentas;
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
    BotonesGenericosPage botonesGenericosPage= new BotonesGenericosPage();
    BotonesGenericosSteps botonesGenericosSteps = new BotonesGenericosSteps();
    UtilsGP utilsGP = new UtilsGP();
    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0006-BSC: Adherir Cuenta  - Nuevo destinatario - por Alias - Descarga e impresión del PDF")
    public void TC0006BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(u23013315);
            menuLateralSteps.clickCuentas();
            cuentasSteps.clickMasOpcionesGenerico();
            cuentasSteps.clickAgendaDeDestinararios();
            agendaDeDesetinatariosSteps.clickAdherirNuevoDestinatario();
            adherirNuevoDestinatarioSteps.clickAlias();
            adherirNuevoDestinatarioSteps.setCbuAlias("TRIGO-PERST-357");
            adherirNuevoDestinatarioSteps.clickBotonBuscar();
            adherirNuevoDestinatarioSteps.setPerteneceA("TC0006");
            adherirNuevoDestinatarioSteps.setEmail("practiagp1@gmail.com");
            adherirNuevoDestinatarioSteps.clickContinuarGenerico();
            adherirNuevoDestinatarioSteps.validarTituloPageGenerico("Adherir nuevo destinatario");
            dobleFactorSteps.codigoSegundoFactorOtroMetodo();
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
            agendaDeDesetinatariosSteps.setPertenceA("TC0006");
            agendaDeDesetinatariosSteps.clickIconoEliminar();
            agendaDeDesetinatariosSteps.clickConfirmarGenerico();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0027-BSC: Generar Orden de Extracción_Con Saldo disponible en Cuenta.")
    public void TC0027BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uTranspull1);
            menuLateralSteps.clickCuentas();
            cuentasSteps.clickOrdenesDeExtraccion();
            ordenDeExtraccionConsulta_generarSteps.clickGenerarOrdenDeExtraccion();
            ordenDeExtraccionConsulta_generarSteps.validarTituloPageGenerico("Generar orden de extracc");
            ordenDeExtraccionConsulta_generarSteps.setDni("24482566");
            ordenDeExtraccionConsulta_generarSteps.setImporteGenerico("100");
            ordenDeExtraccionConsulta_generarSteps.setReferencia("TC27");
            ordenDeExtraccionConsulta_generarSteps.clickBotonAgregar();
            ordenDeExtraccionConsulta_generarSteps.clickContinuarGenerico();
            dobleFactorSteps.codigoSegundoFactorOtroMetodo();
            ordenDeExtraccionConsulta_generarSteps.validarTituloPageGenerico("Destinatario");
            ordenDeExtraccionConsulta_generarSteps.validarTituloPageGenerico("DNI 24482566");
            ordenDeExtraccionConsulta_generarSteps.clickIconoComprobante();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            ticketSteps.clickVolverGenerico();
            ordenDeExtraccionConsulta_generarSteps.validarTituloPageGenerico("Destinatario");
            ordenDeExtraccionConsulta_generarSteps.validarTituloPageGenerico("DNI 24482566");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Alta.class})
    @DisplayName("TC-0034-BSC: Consulta y modificación de Alias de CBU - editar alias CA en Pesos")
    public void TC0034BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uBsc13400488);
            menuLateralSteps.clickCuentas();
            cuentasSteps.clickVerMasXnroCuenta("CC $ 550001362");
            detalleCuentaSteps.clickModificarAlias();
            detalleCuentaSteps.setModificarAlias("abcdefghijklmnopqrstuvwxyz");
            detalleCuentaSteps.clickGuardarGenerico();
            menuLateralSteps.clickCuentas();
            cuentasSteps.clickVerMasXnroCuenta("CC $ 550001362");
            detalleCuentaSteps.clickVerAlias();
            detalleCuentaSteps.verAlias();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Alta.class})
    @DisplayName("TC-0036-BSC: Solicitud de chequera - Normal -25 cheques - 1 chequera")
    public void TC0036BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uBsc13400488);
            menuLateralSteps.clickCuentas();
            cuentasSteps.clickVerMasXnroCuenta("CC $ 550001362");
            detalleCuentaSteps.clickMasOpcionesGenerico();
            detalleCuentaSteps.clickSolicitudDeChequera();
            solicitudDeChequeraSteps.validarTituloPageGenerico("Solicitud de Chequera");
            solicitudDeChequeraSteps.clickDesplegableTipoDeCheque();
            solicitudDeChequeraSteps.keyDownToText("Cheque normal");
            solicitudDeChequeraSteps.clickDesplegableCantidadDeCheques();
            solicitudDeChequeraSteps.keyDownXParametrosGenerico(0, "25");
            solicitudDeChequeraSteps.clickDesplegableCantidadDeChequeras();
            solicitudDeChequeraSteps.keyDownXParametrosGenerico(0, "1");
            solicitudDeChequeraSteps.clickContinuarGenerico();
            dobleFactorSteps.codigoSegundoFactor();
            TicketSteps ticketSteps = new TicketSteps();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0040-BSC: Acceder a Consulta de movimientos de cuenta")
    public void TC0040BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(u5404364);
            menuLateralSteps.clickCuentas();
            cuentasSteps.clickVerMasXnroCuenta("EDUPRUEBA");
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

    @Test //error track2
    @Category({Media.class})
    @DisplayName("TC-0041-BSC: Filtrar Movimientos por Tipo en una cuenta. Elegir Todos")
    public void TC0041BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(u10027480);
            menuLateralSteps.clickCuentas();
            cuentasSteps.clickMasOpcionesGenerico();
            cuentasSteps.clickTransferencias();
            transferenciasInmediatasSteps.seleccionarCuentaOrigen("CA $ 202123145");
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
            cuentasSteps.clickVerMasXnroCuenta("CA $ 202123145");
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
    @DisplayName("TC-0049-BSC: Cliente con 1 cuenta")
    public void TC0049BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(u26538177);
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
    @DisplayName("TC-0050-BSC: Solicitar un Alta de Cuenta desde cuentas")
    public void TC0050BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(u5404364);
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
    @Category({Media.class})
    @DisplayName("TC-0060-BSC: Consulta y descarga Impuestos a la Renta Financiera - Resultado exitoso")
    public void TC0060BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uPruebaqaBSC03);
            menuLateralSteps.clickCuentas();
            cuentasSteps.clickMasOpcionesGenerico();
            cuentasSteps.clickRentaFinanciera();
            rentaFinancieraSteps.clickEnDesplegableAnio("2021");
            rentaFinancieraSteps.clickBotonDescargaPorFecha("23/07/2021");
            rentaFinancieraSteps.validarDescarga("Estado-23072021.pdf");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0067-BSC: Solicitud de TIS_ Realizar Aumento Temporal del Límite de Transferencia de una Cuenta - Caso exitoso - Cliente con una TD y una cuenta- 2do factor por SMS")
    public void TC0067BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(u5404364);
            menuLateralSteps.clickCuentas();
            cuentasSteps.clickMasOpcionesGenerico();
            cuentasSteps.clickLimiteDeTransferencia();
            limiteDeTransferenciaSteps.clickCuentaOrigen();
            limiteDeTransferenciaSteps.keyDownToText("250008759");
            String importe = limiteDeTransferenciaSteps.setImporteLimite("santacruz");
            limiteDeTransferenciaSteps.clickContinuarGenerico();
            limiteDeTransferenciaSteps.clickCheckTerminosCondicionesGenerico();
            dobleFactorSteps.codigoSegundoFactorOtroMetodo();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            limiteDeTransferenciaSteps.actualizarLimiteDb("santacruz", importe);
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
