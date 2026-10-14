package GP.SantaCruz.tarjetas_de_credito;

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
import steps.login.LoginSteps;
import steps.menulateral.MenuLateralSteps;
import steps.pagos.nuevopago.NuevoPagoSteps;
import steps.tarjetasdecredito.TarjetasDeCreditoSteps;
import steps.ticket.TicketSteps;
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
    NuevoPagoSteps nuevoPagoSteps = new NuevoPagoSteps();
    TicketSteps ticketSteps = new TicketSteps();
    BotonesGenericosPage botonesGenericosPage = new BotonesGenericosPage();
    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0032-BSC: Consulta de Tarjetas de Crédito - Visa")
    public void TC0032BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(u5404364);
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.clickVerMasXnroDeTarjeta("0758");
            tarjetasDeCreditoSteps.verificarTituloVisa("0758");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0033-BSC: Consulta de Tarjetas de Crédito - Mastercard")
    public void TC0033BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uPruebaqabsc01);
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.clickVerMasXnroDeTarjeta("3555");
            tarjetasDeCreditoSteps.verificarTituloMastercard();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Alta.class})
    @DisplayName("TC-0062-BSC: Detalle de tarjeta de crédito - Mostrar datos tarjeta y movimientos del período")
    public void TC0062BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uPruebaqabsc01);
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.clickVerMasXnroDeTarjeta("3555");
            tarjetasDeCreditoSteps.verificarTituloMastercard();
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
    @DisplayName("TC-0063-BSC: Listado de tarjetas de crédito - Cliente con tarjetas de crédito asociadas - Formato Corto (Formato por defecto)")
    public void TC0063BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(u5404364);
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.clickVerMasXnroDeTarjeta("0758");
            tarjetasDeCreditoSteps.verificarTituloVisa("0758");
            tarjetasDeCreditoSteps.verificarDescargarResumen();
            tarjetasDeCreditoSteps.verificarMovimientoPeriodico();
            tarjetasDeCreditoSteps.verificarSolicitarAdicional();
            tarjetasDeCreditoSteps.verificarLimiteDisponible();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Alta.class})
    @DisplayName("TC-0104-BSC: Tarjeta de Crédito - Descarga de resumen")
    public void TC0104BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uPruebaqabsc01);
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.validarTituloPageGenerico("Tarjetas de cr");
            tarjetasDeCreditoSteps.clickVerMasXnroDeTarjeta("3555");
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
    @DisplayName("TC-0149-BSC: Tarjeta de Crédito - Solicitar Nueva Tarjeta de Crédito - WEB.")
    public void TC0149BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(u24295762);
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.validarTituloPageGenerico("Tarjetas de cr");
            tarjetasDeCreditoSteps.clickSolicitarTarjetaNueva();
            tarjetasDeCreditoSteps.validarTituloPageGenerico("Solicitud de tarjeta de cr");
            tarjetasDeCreditoSteps.clickContinuarGenerico();
            tarjetasDeCreditoSteps.clickCheckTerminosCondicionesGenerico();

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Media.class})
    @DisplayName("TC-0155-BSC: Tarjeta de Crédito - Seguimiento de Tarjetas de Crédito - Detalles - WEB.")
    public void TC0155BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(u24295762);
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

    @Test
    @Category({Media.class})
    @DisplayName("TC-0157-BSC: Tarjeta de Crédito - Seguimiento de Tarjetas de Crédito - Descargar Detalles - WEB.")
    public void TC0157BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(u24295762);
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
    @Test
    @Category({Alta.class})
    @DisplayName("TC-0153-BSC: Tarjeta de Crédito - Segumiento de Tarjetas de Crédito - WEB.")
    public void TC0153BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(u24295762);
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.clickSeguimientoDeTarjetas();
            tarjetasDeCreditoSteps.validarTituloPageGenerico("Seguimiento de tarjetas");
            tarjetasDeCreditoSteps.validacionCamposSeguimientoTarjeta();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Baja.class})
    @DisplayName("TC-0158-BSC: Tarjeta de Crédito - Seguimiento de Tarjetas de Crédito - Imprimir Detalles - WEB")
    public void TC0158BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(u24295762);
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.clickSeguimientoDeTarjetas();
            tarjetasDeCreditoSteps.validarTituloPageGenerico("Seguimiento de tarjetas");
            tarjetasDeCreditoSteps.validacionCamposSeguimientoTarjeta();
            tarjetasDeCreditoSteps.ingresarADetalleSeguimientoTarjeta();
            tarjetasDeCreditoSteps.clickImprimir();
            utilsGP.ManageTab();
            Thread.sleep(2000);
            utilsGP.backMainTap();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Alta.class})
    @DisplayName("TC-0160-BSC: Tarjeta de Crédito - Solicitar Tarjeta de Crédito adicional - WEB")
    public void TC0160BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uPruebaqabsc01);
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.validarTituloPageGenerico("Tarjetas de cr");
            tarjetasDeCreditoSteps.clickVerMasXnroDeTarjeta("3555");
            tarjetasDeCreditoSteps.clickSolicitarAdicional();
            tarjetasDeCreditoSteps.formularioSolicitudAdicional("3555");
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
    @DisplayName("TC-0162-BSC: Tarjeta de Crédito - Denunciar robo o extravío de Tarjeta de Crédito - WEB.")
    public void TC0162BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uPruebaqabsc01);
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.validarTituloPageGenerico("Tarjetas de cr");
            tarjetasDeCreditoSteps.clickVerMasXnroDeTarjeta("3555");
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
    @DisplayName("TC-0163-BSC: Tarjeta de Crédito - Tarjeta de Crédito - Pagar el total de una Tarjeta de Crédito Mastercard - Validación de pantalla")
    public void TC0163BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(u21829785);
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.clickVerMasXnroDeTarjeta("6193");
            tarjetasDeCreditoSteps.clickMasOpcionesGenerico();
            tarjetasDeCreditoSteps.clickPagarTarjeta();
            tarjetasDeCreditoSteps.validarTituloPageGenerico("Pago de tarjeta propia");
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
    @DisplayName("TC-0164-BSC: Tarjeta de Crédito - Pagar mínimo de una Tarjeta de Crédito Visa - Validación de pantalla")
    public void TC0164BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uSantacruz02);
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.clickVerMasXnroDeTarjeta("0568");
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
    @DisplayName("TC-0165-BSC: PAGOS - Pagar Tarjeta de Crédito Master - Otro importe")
    public void TC0165BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uTranspull1);
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.clickVerMasXnroDeTarjeta("8216");
            tarjetasDeCreditoSteps.clickMasOpcionesGenerico();
            tarjetasDeCreditoSteps.clickPagarTarjeta();
            tarjetasDeCreditoSteps.validarTituloPageGenerico("Pago de tarjeta propia");
            //tarjetasDeCreditoSteps.seleccionarCuentaDebitoSiEstaPresente(0, "");
            //tarjetasDeCreditoSteps.keyDownToText("Caja de Ahorro $ 012333479");
            //tarjetasDeCreditoSteps.seleccionarTarjetaDebitoSiEstaPresente();
            Thread.sleep(5000);
            tarjetasDeCreditoSteps.seleccionarCuentaDebitoSiEstaPresente("012333479");
            tarjetasDeCreditoSteps.seleccionarTipoDePago( "Ingresar un importe");
            tarjetasDeCreditoSteps.setImporte("1");
            tarjetasDeCreditoSteps.clickBotonPagar();
            tarjetasDeCreditoSteps.validacionDeCamposPagoDeTarjeta();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Alta.class})
    @DisplayName("TC-0166-BSC: Tarjeta de Crédito - Solicitar baja de Tarjeta de Crédito - WEB.")
    public void TC0166BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(u21829785);
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.validarTituloPageGenerico("Tarjetas de cr");
            tarjetasDeCreditoSteps.clickVerMasXnroDeTarjeta("6193");
            tarjetasDeCreditoSteps.clickMasOpcionesGenerico();
            tarjetasDeCreditoSteps.clickSolicitarBajaTarjeta();
            tarjetasDeCreditoSteps.completarTelefono("1123454355");
            tarjetasDeCreditoSteps.completarEmail("gp@gmail.com");
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
    @DisplayName("TC-0167-BSC: Tarjeta de Crédito - Consultar último resumen de una Tarjeta de Crédito - WEB.")
    public void TC0167BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(u21829785);
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.clickVerMasXnroDeTarjeta("6193");
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
    @DisplayName("TC-0191-BSC: Tarjetas de credito - Solicitud de tarjeta de credito- Al no contar con oferta de TC se debe validar mensaje 'No contás con oferta de Tarjeta de Crédito en este momento. '")
    public void TC0191BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(u21829785);
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
    @DisplayName("TC-0192-BSC: Tarjetas de credito - Solicitud de tarjeta de credito- Al solicitar una nueva TC y clickear el checkbox Persona expuesta políticamente no se debe permitir continuar con la solicitud-  validar pantalla mensaje")
    public void TC0192BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(u24295762);
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
    @DisplayName("TC-0193-BSC: Tarjetas de credito - Solicitud de tarjeta de credito- Al solicitar una nueva TC y clickear el checkbox 'residencia fiscar en el exterior'  no se debe permitir continuar con la solicitud-  validar pantalla mensaje")
    public void TC0193BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(u24295762);
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
    @DisplayName("TC-0194-BSC: Tarjetas de credito - Solicitud de tarjeta de credito- Al solicitar una nueva TC y clickear el checkbox \"sujeto obligado iuf\"  no se debe permitir continuar con la solicitud-  validar pantalla mensaje")
    public void TC0194BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(u24295762);
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
    @DisplayName("TC-0195-BSC: Tarjetas de credito - Solicitud de tarjeta de credito- Al solicitar una nueva TC y verificar mensaje de solicitar turno, clickear en  'Solicitá tu turno acá.' y validar redireccion de pantalla")
    public void TC0195BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(u24295762);
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.validarTituloPageGenerico("Tarjetas de cr");
            tarjetasDeCreditoSteps.clickSolicitarTarjetaNueva();
            tarjetasDeCreditoSteps.validarTituloPageGenerico("Solicitud de tarjeta de cr");
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
    @DisplayName("TC-12967-BSC: PAGOS - Pagar Tarjeta de Crédito Visa - Otro importe")
    public void TC12967BSC() {
        try {
            //PRECONDICIÓN
            loginSteps.iniciarSessionAlEscritorio(uTranspull1);
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.clickVerMasXnroDeTarjeta("0568");
            //Thread.sleep(10000);
            botonesGenericosSteps.clickMasOpcionesGenerico();
            tarjetasDeCreditoSteps.clickPagarTarjeta();
            tarjetasDeCreditoSteps.validarTituloPageGenerico("Pago de tarjeta propia");
            Thread.sleep(5000);
            tarjetasDeCreditoSteps.seleccionarCuentaDebitoSiEstaPresente("606033880");
            tarjetasDeCreditoSteps.seleccionarTipoDePago("Ingresar un importe");
            tarjetasDeCreditoSteps.setImporte("1");
            tarjetasDeCreditoSteps.clickBotonPagar();
            tarjetasDeCreditoSteps.validacionDeCamposPagoDeTarjeta();
            //nuevoPagoSteps.clickConfirmarGenerico();
            //ticketSteps.validarTicket("Tu solicitud se realizo con exito");
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
