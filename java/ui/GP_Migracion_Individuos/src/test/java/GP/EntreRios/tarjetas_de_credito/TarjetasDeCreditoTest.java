package GP.EntreRios.tarjetas_de_credito;

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
import steps.login.LoginSteps;
import steps.menulateral.MenuLateralSteps;
import steps.pagos.nuevopago.NuevoPagoSteps;
import steps.tarjetasdecredito.TarjetasDeCreditoSteps;
import steps.ticket.TicketSteps;
import utils.Constantes;
import utils.TestCaseData;
import utils.UtilsGP;

import java.io.IOException;

import static db.ConexionDB.cerrarConexion;
import static utils.Constantes.*;

public class TarjetasDeCreditoTest {
    LoginSteps loginSteps = new LoginSteps();
    MenuLateralSteps menuLateralSteps = new MenuLateralSteps();
    TarjetasDeCreditoSteps tarjetasDeCreditoSteps = new TarjetasDeCreditoSteps();
    UtilsGP utilsGP = new UtilsGP();
    BotonesGenericosSteps botonesGenericosSteps = new BotonesGenericosSteps();
    BotonesGenericosPage botonesGenericosPage = new BotonesGenericosPage();
    NuevoPagoSteps nuevoPagoSteps = new NuevoPagoSteps();
    TicketSteps ticketSteps = new TicketSteps();
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
    @DisplayName("TC-0032-BERSA: Consulta de Tarjetas de Crédito - Visa")
    public void TC0032BERSA() {
        try {
            testCaseData.cargaDeDatosTest(uBerhometest15);
            loginSteps.iniciarSessionAlEscritorioConPass(uBerhometest15,testCaseData.password);
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.clickVerMasXnroDeTarjeta("3837");
            tarjetasDeCreditoSteps.verificarTituloVisa(testCaseData.tarjetaCredito);
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0033-BERSA: Consulta de Tarjetas de Crédito - Mastercard")
    public void TC0033BERSA() {
        try {
            testCaseData.cargaDeDatosTest(uUathb04);
            loginSteps.iniciarSessionAlEscritorioConPass(uUathb04,testCaseData.password);
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.clickVerMasXnroDeTarjeta("Mastercard");
            tarjetasDeCreditoSteps.verificarTituloMastercard();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Alta.class})
    @DisplayName("TC-0062-BERSA: Detalle de tarjeta de crédito - Mostrar datos tarjeta y movimientos del período")
    public void TC0062BERSA() {
        try {
            loginSteps.iniciarSessionAlEscritorio(u11107010);
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.clickVerMasXnroDeTarjeta("8507");
            tarjetasDeCreditoSteps.verificarTituloVisa(testCaseData.tarjetaCredito);
            tarjetasDeCreditoSteps.verificarDescargarResumen();
            tarjetasDeCreditoSteps.verificarMovimientoPeriodico();
            tarjetasDeCreditoSteps.verificarSolicitarAdicional();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Baja.class})
    @DisplayName("TC-0063-BERSA: Listado de tarjetas de crédito - Cliente con tarjetas de crédito asociadas - Formato Corto (Formato por defecto)")
    public void TC0063BERSA() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uUathb04);
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.clickVerMasXnroDeTarjeta("Mastercard");
            tarjetasDeCreditoSteps.verificarTituloMastercard();
            tarjetasDeCreditoSteps.verificarDescargarResumen();
            tarjetasDeCreditoSteps.verificarMovimientoPeriodico();
            tarjetasDeCreditoSteps.verificarTablaMovimientos();
            tarjetasDeCreditoSteps.verificarSolicitarAdicional();
            tarjetasDeCreditoSteps.verificarLimiteDisponible();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Alta.class})
    @DisplayName("TC-0104-BERSA: Tarjeta de Crédito - Descarga de resumen")
    public void TC0104BERSA() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uEcarina27);
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.validarTituloPageGenerico("Tarjetas de cr");
            tarjetasDeCreditoSteps.clickVerMasXnroDeTarjeta("0107");
            tarjetasDeCreditoSteps.clickDescargarResumen();
            tarjetasDeCreditoSteps.validarTituloPageGenerico("Descargar resumen");
            tarjetasDeCreditoSteps.clickPrimerPeriodo();
            tarjetasDeCreditoSteps.clickDescargaPrimerPeriodo();
            tarjetasDeCreditoSteps.validarDescarga("Estado");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Alta.class})
    @DisplayName("TC-0149-BERSA: Tarjeta de Crédito - Solicitar Nueva Tarjeta de Crédito - WEB.")
    public void TC0149BERSA() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uEcarina27);
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.validarTituloPageGenerico("Tarjetas de cr");
            tarjetasDeCreditoSteps.clickSolicitarTarjetaNueva();
            tarjetasDeCreditoSteps.validarTituloPageGenerico("Solicitud de tarjeta de cr");
            tarjetasDeCreditoSteps.seleccionarMarcaTarjeta();
            tarjetasDeCreditoSteps.clickContinuarGenerico();
            tarjetasDeCreditoSteps.clickCheckTerminosCondicionesGenerico();

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test //SIN usuario que contenga BOTON para Seguimiento de tarjetas
    @Category({Media.class})
    @DisplayName("TC-0155-BERSA: Tarjeta de Crédito - Seguimiento de Tarjetas de Crédito - Detalles - WEB.")
    public void TC0155BERSA() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uEcarina27);
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.clickSeguimientoDeTarjetas();
            tarjetasDeCreditoSteps.validarTituloPageGenerico("Seguimiento de tarjetas");
            tarjetasDeCreditoSteps.validacionCamposSeguimientoTarjeta();
            tarjetasDeCreditoSteps.ingresarADetalleSeguimientoTarjeta();
            tarjetasDeCreditoSteps.validacionDentroDeTarjeta();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test //SIN usuario que contenga BOTON para Seguimiento de tarjetas
    @Category({Media.class})
    @DisplayName("TC-0157-BERSA: Tarjeta de Crédito - Seguimiento de Tarjetas de Crédito - Descargar Detalles - WEB.")
    public void TC0157BERSA() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uEcarina27);
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.clickSeguimientoDeTarjetas();
            tarjetasDeCreditoSteps.validarTituloPageGenerico("Seguimiento de tarjetas");
            tarjetasDeCreditoSteps.validacionCamposSeguimientoTarjeta();
            tarjetasDeCreditoSteps.ingresarADetalleSeguimientoTarjeta();
            tarjetasDeCreditoSteps.clickDescargaPrimerPeriodo();
            tarjetasDeCreditoSteps.validarDescarga("Detalle de seguimiento de tarjeta.pdf");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test //SIN usuario que contenga BOTON para Seguimiento de tarjetas
    @Category({Alta.class})
    @DisplayName("TC-0153-BERSA: Tarjeta de Crédito - Segumiento de Tarjetas de Crédito - WEB.")
    public void TC0153BERSA() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uEcarina27);
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.clickSeguimientoDeTarjetas();
            tarjetasDeCreditoSteps.validarTituloPageGenerico("Seguimiento de tarjetas");
            tarjetasDeCreditoSteps.validacionCamposSeguimientoTarjeta();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test //SIN usuario que contenga BOTON para Seguimiento de tarjetas
    @Category({Baja.class})
    @DisplayName("TC-0158-BERSA: Tarjeta de Crédito - Seguimiento de Tarjetas de Crédito - Imprimir Detalles - WEB")
    public void TC0158BERSA() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uEcarina27);
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.clickSeguimientoDeTarjetas();
            tarjetasDeCreditoSteps.validarTituloPageGenerico("Seguimiento de tarjetas");
            tarjetasDeCreditoSteps.validacionCamposSeguimientoTarjeta();
            tarjetasDeCreditoSteps.ingresarADetalleSeguimientoTarjeta();
            tarjetasDeCreditoSteps.clickImprimir();
            utilsGP.ManageTab();
            utilsGP.backMainTap();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Alta.class})
    @DisplayName("TC-0160-BERSA: Tarjeta de Crédito - Solicitar Tarjeta de Crédito adicional - WEB")
    public void TC0160BERSA() {
        try {
            loginSteps.iniciarSessionAlEscritorio(u11107010);
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.validarTituloPageGenerico("Tarjetas de cr");
            tarjetasDeCreditoSteps.clickVerMasXnroDeTarjeta("8507");
            tarjetasDeCreditoSteps.clickSolicitarAdicional();
            tarjetasDeCreditoSteps.formularioSolicitudAdicional("8507");
            tarjetasDeCreditoSteps.clickContinuarGenerico();
            tarjetasDeCreditoSteps.validarTablaDatosPersonales();
            //Teardown
            tarjetasDeCreditoSteps.clickVolverFlechaGenerico();
            tarjetasDeCreditoSteps.clickCancelarGenerico();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0162-BERSA: Tarjeta de Crédito - Denunciar robo o extravío de Tarjeta de Crédito - WEB.")
    public void TC0162BERSA() {
        try {
            testCaseData.cargaDeDatosTest(u11107010);
            loginSteps.iniciarSessionAlEscritorioConPass(u11107010,testCaseData.password);
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.validarTituloPageGenerico("Tarjetas de cr");
            tarjetasDeCreditoSteps.clickVerMasXnroDeTarjeta("8507");
            tarjetasDeCreditoSteps.clickMasOpcionesGenerico();
            tarjetasDeCreditoSteps.clickDenunciarRoboExtravio();
            tarjetasDeCreditoSteps.checkRobo();
            tarjetasDeCreditoSteps.clickContinuarGenerico();
            tarjetasDeCreditoSteps.validarTituloPageGenerico("Confirmar denuncia por robo o extrav");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Alta.class})
    @DisplayName("TC-0163-BERSA: Tarjeta de Crédito - Tarjeta de Crédito - Pagar el total de una Tarjeta de Crédito Mastercard - Validación de pantalla")
    public void TC0163BERSA() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uEcarina27);
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.clickVerMasXnroDeTarjeta("0107");
            tarjetasDeCreditoSteps.clickMasOpcionesGenerico();
            tarjetasDeCreditoSteps.clickPagarTarjeta();
            tarjetasDeCreditoSteps.validarTituloPageGenerico("Pago de tarjeta propia");
            tarjetasDeCreditoSteps.seleccionarTarjetaDebitoSiEstaPresente();
            tarjetasDeCreditoSteps.seleccionarCuentaDebitoSiEstaPresente(0, "");
            tarjetasDeCreditoSteps.seleccionarTipoDePago( "Pagar el total");
            tarjetasDeCreditoSteps.clickBotonPagar();
            tarjetasDeCreditoSteps.validacionDeCamposPagoDeTarjeta();

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Alta.class})
    @DisplayName("TC-0164-BERSA: Tarjeta de Crédito - Pagar mínimo de una Tarjeta de Crédito Visa - Validación de pantalla")
    public void TC0164BERSA() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uUattesthb14);
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.clickVerMasXnroDeTarjeta("Visa");
            tarjetasDeCreditoSteps.clickMasOpcionesGenerico();
            tarjetasDeCreditoSteps.clickPagarTarjeta();
            tarjetasDeCreditoSteps.validarTituloPageGenerico("Pago de tarjeta propia");
            tarjetasDeCreditoSteps.seleccionarCuentaDebitoSiEstaPresente(0, "");
            tarjetasDeCreditoSteps.seleccionarTipoDePago( "Pagar el m");
            tarjetasDeCreditoSteps.clickBotonPagar();
            tarjetasDeCreditoSteps.validacionDeCamposPagoDeTarjeta();

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    //@Test
    @Category({Criticos.class})
    @DisplayName("TC-0165-BERSA: PAGOS - Pagar Tarjeta de Crédito Master - Otro importe")
    public void TC0165BERSA() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uEcarina27);
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.clickVerMasXnroDeTarjeta("0107");
            tarjetasDeCreditoSteps.clickMasOpcionesGenerico();
            tarjetasDeCreditoSteps.clickPagarTarjeta();
            tarjetasDeCreditoSteps.validarTituloPageGenerico("Pago de tarjeta propia");
            tarjetasDeCreditoSteps.seleccionarTarjetaDebitoSiEstaPresente();
            tarjetasDeCreditoSteps.seleccionarCuentaDebitoSiEstaPresente("2210");
            tarjetasDeCreditoSteps.seleccionarTipoDePago( "Ingresar un importe");
            tarjetasDeCreditoSteps.setImporte("1");
            tarjetasDeCreditoSteps.clickBotonPagar();
            tarjetasDeCreditoSteps.validacionDeCamposPagoDeTarjeta();
            nuevoPagoSteps.clickConfirmarGenerico();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Alta.class})
    @DisplayName("TC-0166-BERSA: Tarjeta de Crédito - Solicitar baja de Tarjeta de Crédito - WEB.")
    public void TC0166BERSA() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uEcarina27);
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.validarTituloPageGenerico("Tarjetas de cr");
            tarjetasDeCreditoSteps.clickVerMasXnroDeTarjeta("0107");
            tarjetasDeCreditoSteps.clickMasOpcionesGenerico();
            tarjetasDeCreditoSteps.clickSolicitarBajaTarjeta();
            tarjetasDeCreditoSteps.completarTelefono("1123454355");
            tarjetasDeCreditoSteps.completarEmailBERSAyBSF("gp@gmail.com");
            tarjetasDeCreditoSteps.clickContinuarGenerico();
            tarjetasDeCreditoSteps.validarTituloPageGenerico("Baja de tarjeta");
            tarjetasDeCreditoSteps.clickCheckTerminosCondicionesGenerico();

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0167-BERSA: Tarjeta de Crédito - Consultar último resumen de una Tarjeta de Crédito - WEB.")
    public void TC0167BERSA() {
        try {
            testCaseData.cargaDeDatosTest(uEcarina27);
            loginSteps.iniciarSessionAlEscritorioConPass(uEcarina27,testCaseData.password);
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.clickVerMasXnroDeTarjeta("0107");
            tarjetasDeCreditoSteps.clickMasOpcionesGenerico();
            tarjetasDeCreditoSteps.consultaUltimoResumen();
            tarjetasDeCreditoSteps.validacionCamposUltimoResumen();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Baja.class})
    @DisplayName("TC-0191-BERSA: Tarjetas de credito - Solicitud de tarjeta de credito- Al no contar con oferta de TC se debe validar mensaje 'No contás con oferta de Tarjeta de Crédito en este momento. '")
    public void TC0191BERSA() {
        try {
            loginSteps.iniciarSessionAlEscritorio(u11107010);
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.clickSolicitarTarjetaNueva();
            tarjetasDeCreditoSteps.validarMensajeNoContasConOfertas();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Media.class})
    @DisplayName("TC-0192-BERSA: Tarjetas de credito - Solicitud de tarjeta de credito- Al solicitar una nueva TC y clickear el checkbox Persona expuesta políticamente no se debe permitir continuar con la solicitud-  validar pantalla mensaje")
    public void TC0192BERSA() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uEcarina27);
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.validarTituloPageGenerico("Tarjetas de cr");
            tarjetasDeCreditoSteps.clickSolicitarTarjetaNueva();
            tarjetasDeCreditoSteps.validarTituloPageGenerico("Solicitud de tarjeta de cr");
            tarjetasDeCreditoSteps.clickCheckboxPersonaExpuesta();
            tarjetasDeCreditoSteps.clickContinuarGenerico();
            tarjetasDeCreditoSteps.validarMensajeSolicitaTurnoEnSucursal();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Media.class})
    @DisplayName("TC-0193-BERSA: Tarjetas de credito - Solicitud de tarjeta de credito- Al solicitar una nueva TC y clickear el checkbox 'residencia fiscar en el exterior'  no se debe permitir continuar con la solicitud-  validar pantalla mensaje")
    public void TC0193BERSA() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uEcarina27);
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.validarTituloPageGenerico("Tarjetas de cr");
            tarjetasDeCreditoSteps.clickSolicitarTarjetaNueva();
            tarjetasDeCreditoSteps.validarTituloPageGenerico("Solicitud de tarjeta de cr");
            tarjetasDeCreditoSteps.clickCheckboxResidenciaFiscalExterior();
            tarjetasDeCreditoSteps.clickContinuarGenerico();
            tarjetasDeCreditoSteps.validarMensajeSolicitaTurnoEnSucursal();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Media.class})
    @DisplayName("TC-0194-BERSA: Tarjetas de credito - Solicitud de tarjeta de credito- Al solicitar una nueva TC y clickear el checkbox \"sujeto obligado iuf\"  no se debe permitir continuar con la solicitud-  validar pantalla mensaje")
    public void TC0194BERSA() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uEcarina27);
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.validarTituloPageGenerico("Tarjetas de cr");
            tarjetasDeCreditoSteps.clickSolicitarTarjetaNueva();
            tarjetasDeCreditoSteps.validarTituloPageGenerico("Solicitud de tarjeta de cr");
            tarjetasDeCreditoSteps.clickCheckboxSujetoObligadoIUF();
            tarjetasDeCreditoSteps.clickContinuarGenerico();
            tarjetasDeCreditoSteps.validarMensajeSolicitaTurnoEnSucursal();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Alta.class})
    @DisplayName("TC-0195-BERSA: Tarjetas de credito - Solicitud de tarjeta de credito- Al solicitar una nueva TC y verificar mensaje de solicitar turno, clickear en  'Solicitá tu turno acá.' y validar redireccion de pantalla")
    public void TC0195BERSA() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uEcarina27);
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.validarTituloPageGenerico("Tarjetas de cr");
            tarjetasDeCreditoSteps.clickSolicitarTarjetaNueva();
            tarjetasDeCreditoSteps.validarTituloPageGenerico("Solicitud de tarjeta de cr");
            tarjetasDeCreditoSteps.seleccionarMarcaTarjeta();
            tarjetasDeCreditoSteps.clickCheckboxSujetoObligadoIUF();
            tarjetasDeCreditoSteps.clickContinuarGenerico();
            tarjetasDeCreditoSteps.clickSolicitaTurnoEnSucursal();
            loginSteps.clickSalirPrestamo();
            tarjetasDeCreditoSteps.clickAceptarGenerico();
            utilsGP.ManageTab();
            tarjetasDeCreditoSteps.validarMensaje("Bienvenido a Citas");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Criticos.class})
    @DisplayName("TC-12967-BERSA: PAGOS - Pagar Tarjeta de Crédito Visa - Otro importe")
    public void TC12967BERSA() {
        try {
            //PRECONDICIÓN
            testCaseData.cargaDeDatosTest(u20200960948);
            loginSteps.iniciarSessionAlEscritorioConPass(u20200960948,testCaseData.password);
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.clickVerMasXnroDeTarjeta("2605");
            Thread.sleep(10000);
            botonesGenericosSteps.clickMasOpcionesGenerico();
            tarjetasDeCreditoSteps.clickPagarTarjeta();
            Thread.sleep(5000);
            tarjetasDeCreditoSteps.seleccionarCuentaDebitoSiEstaPresente("Caja de Ahorro $ 062003146320");
            tarjetasDeCreditoSteps.seleccionarTipoDePago("Ingresar un importe");
            tarjetasDeCreditoSteps.setImporte("1");
            tarjetasDeCreditoSteps.clickBotonPagar();
            nuevoPagoSteps.clickConfirmarGenerico();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
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
