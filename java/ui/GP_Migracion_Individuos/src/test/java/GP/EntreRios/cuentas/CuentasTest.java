package GP.EntreRios.cuentas;
import GP.Tags.Alta;
import GP.Tags.Baja;
import GP.Tags.Criticos;
import GP.Tags.Media;
import db.ConexionDB;
import io.qameta.allure.Allure;
import io.qameta.allure.junit4.DisplayName;
import org.junit.*;
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
import utils.Constantes;
import utils.TestCaseData;
import utils.UtilsGP;

import java.io.IOException;

import static db.ConexionDB.cerrarConexion;
import static utils.Constantes.*;

public class CuentasTest {
    MenuLateralSteps menuLateralSteps = new MenuLateralSteps();
    CuentasSteps cuentasSteps = new CuentasSteps();
    CalendarioSteps calendarioSteps= new CalendarioSteps();
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
    BotonesGenericosPage botonesGenericosPage = new BotonesGenericosPage();
    BotonesGenericosSteps botonesGenericosSteps = new BotonesGenericosSteps();
    UtilsGP utilsGP = new UtilsGP();
    TestCaseData testCaseData = new TestCaseData();
    @BeforeClass
    public static void extraerDatosBD(){
        System.out.println("============= BEFORE CLASS ==========================");
        if(Constantes.usuarios == null){
            System.out.println("============= Array de datos vacio extraer desde la BD ==============");
            Constantes.usuarios = ConexionDB.extraerUsuariosDB();
            System.out.println("============= se completo el array de datos ==========================");
        }else {
            System.out.println("================ EL ARRAY CONTIENE DATOS ===================");
        }
    }

    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0006-BERSA: Adherir Cuenta  - Nuevo destinatario - por Alias - Descarga e impresión del PDF")
    public void TC0006BERSA() {
        try {
            //Precondicion
            testCaseData.cargaDeDatosTest(uBerhometest15);
            loginSteps.iniciarSessionAlEscritorioConPass(uBerhometest15,testCaseData.password);
            menuLateralSteps.clickCuentas();
            cuentasSteps.clickMasOpcionesGenerico();
            cuentasSteps.clickAgendaDeDestinararios();
            agendaDeDesetinatariosSteps.clickCuentasTerceros();
            agendaDeDesetinatariosSteps.setPertenceA("BAEZ WALDO HECTOR YAMIL");
            if (botonesGenericosPage.getIconoEliminar().isDisplayed()) {
                agendaDeDesetinatariosSteps.clickIconoEliminar();
                agendaDeDesetinatariosSteps.clickConfirmarGenerico();
                ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            }
            //Test
            menuLateralSteps.clickCuentas();
            cuentasSteps.clickMasOpcionesGenerico();
            cuentasSteps.clickAgendaDeDestinararios();
            agendaDeDesetinatariosSteps.clickAdherirNuevoDestinatario();
            adherirNuevoDestinatarioSteps.clickAlias();
            adherirNuevoDestinatarioSteps.setCbuAlias("rusia.pista.cepa");
            adherirNuevoDestinatarioSteps.clickBotonBuscar();
            adherirNuevoDestinatarioSteps.setEmail("practiagp1@gmail.com");
            adherirNuevoDestinatarioSteps.clickContinuarGenerico();
            //adherirNuevoDestinatarioSteps.setCodigoSeguridad("111");
            //adherirNuevoDestinatarioSteps.setMesVencimiento("07");
            //adherirNuevoDestinatarioSteps.setAnioVencimiento("29");
            //dobleFactorSteps.codigoSegundoFactorOtroMetodo(uBerhometest15);
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
            agendaDeDesetinatariosSteps.setPertenceA("BAEZ WALDO HECTOR YAMIL");
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
    @DisplayName("TC-0027-BERSA: Generar Orden de Extracción_Con Saldo disponible en Cuenta.")
    public void TC0027BERSA() {
        try {
            testCaseData.cargaDeDatosTest(uTranspull1);
            loginSteps.iniciarSessionAlEscritorioConPass(uTranspull1,testCaseData.password);
            menuLateralSteps.clickCuentas();
            cuentasSteps.clickOrdenesDeExtraccion();
            ordenDeExtraccionConsulta_generarSteps.clickGenerarOrdenDeExtraccion();
            ordenDeExtraccionConsulta_generarSteps.validarTituloPageGenerico("Generar orden de extracci");
            ordenDeExtraccionConsulta_generarSteps.clickCuentaOrigen();
            ordenDeExtraccionConsulta_generarSteps.keyDownToText("Caja de Ahorro $ 001005620072");
            ordenDeExtraccionConsulta_generarSteps.setDni("24482566");
            ordenDeExtraccionConsulta_generarSteps.setImporteGenerico("100");
            ordenDeExtraccionConsulta_generarSteps.setReferencia("T27");
            ordenDeExtraccionConsulta_generarSteps.clickBotonAgregar();
            ordenDeExtraccionConsulta_generarSteps.clickContinuarGenerico();
            //dobleFactorSteps.codigoSegundoFactorOtroMetodo(uTranspull1);
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
    @DisplayName("TC-0034-BERSA: Consulta y modificación de Alias de CBU - editar alias CA en Pesos")
    public void TC0034BERSA() {
        try {
            //loginSteps.iniciarSesion(uEcarina27);
            loginSteps.iniciarSessionAlEscritorio(uEcarina27);
            menuLateralSteps.clickCuentas();
            cuentasSteps.clickVerMasXnroCuenta("CA $ 020000052210");
            detalleCuentaSteps.clickModificarAlias();
            detalleCuentaSteps.setModificarAlias("abcdefghijklmnopqrstuvwxyz");
            detalleCuentaSteps.clickGuardarGenerico();
            menuLateralSteps.clickCuentas();
            cuentasSteps.clickVerMasXnroCuenta("CA $ 020000052210");
            detalleCuentaSteps.clickVerAlias();
            detalleCuentaSteps.verAlias();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Alta.class})
    @DisplayName("TC-0036-BERSA: Solicitud de chequera - Normal -25 cheques - 1 chequera")
    public void TC0036BERSA() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uUathb04);
            menuLateralSteps.clickCuentas();
            cuentasSteps.clickVerMasXnroCuenta("CC $ 035000023427");
            detalleCuentaSteps.clickMasOpcionesGenerico();
            detalleCuentaSteps.clickSolicitudDeChequera();
            solicitudDeChequeraSteps.validarTituloPageGenerico("Solicitud de Chequera");
            solicitudDeChequeraSteps.clickDesplegableTipoDeCheque();
            solicitudDeChequeraSteps.keyDownToText("Cheque normal");
            solicitudDeChequeraSteps.clickDesplegableCantidadDeCheques();
            solicitudDeChequeraSteps.keyDownXParametrosGenerico(0,"25");
            solicitudDeChequeraSteps.clickDesplegableCantidadDeChequeras();
            solicitudDeChequeraSteps.keyDownXParametrosGenerico(0, "1");
            solicitudDeChequeraSteps.clickContinuarGenerico();
            //dobleFactorSteps.codigoSegundoFactorOtroMetodo(uUathb04);
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0040--BERSA: Acceder a Consulta de movimientos de cuenta")
    public void TC0040BERSA(){
        try {
            testCaseData.cargaDeDatosTest(uEcarina27);
            loginSteps.iniciarSessionAlEscritorioConPass(uEcarina27,testCaseData.password);
            menuLateralSteps.clickCuentas();
            cuentasSteps.clickVerMasXnroCuenta("CC $ 001003691907");
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
    @DisplayName("TC-0041-BERSA: Filtrar Movimientos por Tipo en una cuenta. Elegir Todos")
    public void TC0041BERSA(){
        try {
            loginSteps.iniciarSessionAlEscritorio(uBerhometest15);
            menuLateralSteps.clickCuentas();
            cuentasSteps.clickMasOpcionesGenerico();
            cuentasSteps.clickTransferencias();
            transferenciasInmediatasSteps.seleccionarCuentaOrigen("CC $ 035003401876");
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
            cuentasSteps.clickVerMasXnroCuenta("CC $ 035003401876");
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
    @DisplayName("TC-0049-BERSA: Cliente con 1 cuenta")
    public void TC0049BERSA(){
        try {
            loginSteps.iniciarSessionAlEscritorio(uUathbb04);
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
    @DisplayName("TC-0050-BERSA: Solicitar un Alta de Cuenta desde cuentas")
    public void TC0050BERSA(){
        try {
            loginSteps.iniciarSessionAlEscritorio(uEcarina27);
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
    @Test //SE NECESITA USUARIO CON OPCION -> RENTA FINANCIERA
    @Category({Media.class})
    @DisplayName("TC-0060-BERSA: Consulta y descarga Impuestos a la Renta Financiera - Resultado exitoso")
    public void TC0060BERSA() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uBerhometest15);
            menuLateralSteps.clickCuentas();
            cuentasSteps.clickMasOpcionesGenerico();
            cuentasSteps.clickRentaFinanciera();
            rentaFinancieraSteps.clickEnDesplegableAnio("2021");
            rentaFinancieraSteps.clickBotonDescargaPorFecha("29/05/2021");
            rentaFinancieraSteps.validarDescarga("Estado");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    //@Test //SE NECESITA VALIDACION DE IDENTIDAD
    @Category({Criticos.class})
    @DisplayName("TC-0067-BERSA: Solicitud de TIS_ Realizar Aumento Temporal del Límite de Transferencia de una Cuenta - Caso exitoso - Cliente con una TD y una cuenta- 2do factor por SMS")
    public void TC0067BERSA() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uBerhometest15);
            menuLateralSteps.clickCuentas();
            cuentasSteps.clickMasOpcionesGenerico();
            cuentasSteps.clickLimiteDeTransferencia();
            limiteDeTransferenciaSteps.clickCuentaOrigen();
            limiteDeTransferenciaSteps.keyDownToText( "035003401876");
            String importe = limiteDeTransferenciaSteps.setImporteLimite("entrerios");
            limiteDeTransferenciaSteps.clickContinuarGenerico();
            limiteDeTransferenciaSteps.clickCheckTerminosCondicionesGenerico();
            //dobleFactorSteps.codigoSegundoFactorOtroMetodo(uBerhometest15);
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            limiteDeTransferenciaSteps.actualizarLimiteDb("entrerios", importe);
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
        //cerrarConexion();
    }
}