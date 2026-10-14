package GP.SantaFe.tarjetas_de_credito;

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
    MenuLateralSteps menuLateralSteps = new MenuLateralSteps();
    TarjetasDeCreditoSteps tarjetasDeCreditoSteps = new TarjetasDeCreditoSteps();
    LoginSteps loginSteps = new LoginSteps();
    NuevoPagoSteps nuevoPagoSteps = new NuevoPagoSteps();
    BotonesGenericosSteps botonesGenericosSteps = new BotonesGenericosSteps();
    BotonesGenericosPage botonesGenericosPage = new BotonesGenericosPage();
    UtilsGP utilsGP = new UtilsGP();
    TicketSteps ticketSteps = new TicketSteps();

    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0032-BSF: Consulta de Tarjetas de Crédito - Visa")
    public void TC0032BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uSantafe036);
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.clickVerMasXnroDeTarjeta("3159");
            tarjetasDeCreditoSteps.verificarTituloVisa("3159");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0033-BSF: Consulta de Tarjetas de Crédito - Mastercard")
    public void TC0033BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uPrueba8240);
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.clickVerMasXnroDeTarjeta("8770");
            tarjetasDeCreditoSteps.verificarTituloMastercard();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Alta.class})
    @DisplayName("TC-0062-BSF: Detalle de tarjeta de crédito - Mostrar datos tarjeta y movimientos del período")
    public void TC0062BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uVolpatomonica);
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.clickVerMasXnroDeTarjeta("1209");
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
    @DisplayName("TC-0063-BSF: Listado de tarjetas de crédito - Cliente con tarjetas de crédito asociadas - Formato Corto (Formato por defecto)")
    public void TC0063BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uVolpatomonica);
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.clickVerMasXnroDeTarjeta("VISA");
            tarjetasDeCreditoSteps.verificarTituloVisa("VISA");
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
    @DisplayName("TC-0104-BSF: Tarjeta de Crédito - Descarga de resumen")
    public void TC0104BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uVolpatomonica);
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.validarTituloPageGenerico("Tarjetas de cr");
            tarjetasDeCreditoSteps.clickVerMasXnroDeTarjeta("VISA");
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
    @DisplayName("TC-0149-BSF: Tarjeta de Crédito - Solicitar Nueva Tarjeta de Crédito - WEB.")
    public void TC0149BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uSantafe036);
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.validarTituloPageGenerico("Tarjetas de cr");
            tarjetasDeCreditoSteps.clickSolicitarTarjetaNueva();
            tarjetasDeCreditoSteps.validarTituloPageGenerico("Solicitud de tarjeta de cr");
            tarjetasDeCreditoSteps.seleccionarCuentaDebitoSiEstaPresente(0, "");
            tarjetasDeCreditoSteps.clickContinuarGenerico();
            tarjetasDeCreditoSteps.clickCheckTerminosCondicionesGenerico();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Alta.class})
    @DisplayName("TC-0153-BSF: Tarjeta de Crédito - Segumiento de Tarjetas de Crédito - WEB.")
    public void TC0153BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uTalebuat);
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
    @Category({Media.class})
    @DisplayName("TC-0155-BSF: Tarjeta de Crédito - Seguimiento de Tarjetas de Crédito - Detalles - WEB.")
    public void TC0155BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uTalebuat);
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
    @DisplayName("TC-0157-BSF: Tarjeta de Crédito - Seguimiento de Tarjetas de Crédito - Descargar Detalles - WEB.")
    public void TC0157BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uTalebuat);
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
    @Category({Baja.class})
    @DisplayName("TC-0158-BSF: Tarjeta de Crédito - Seguimiento de Tarjetas de Crédito - Imprimir Detalles - WEB")
    public void TC0158BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uTalebuat);
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.clickSeguimientoDeTarjetas();
            tarjetasDeCreditoSteps.validarTituloPageGenerico("Seguimiento de tarjetas");
            tarjetasDeCreditoSteps.validacionCamposSeguimientoTarjeta();
            tarjetasDeCreditoSteps.ingresarADetalleSeguimientoTarjeta();
            tarjetasDeCreditoSteps.clickImprimir();
            utilsGP.ManageTab();
           // utilsGP.backMainTap();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Alta.class})
    @DisplayName("TC-0160-BSF: Tarjeta de Crédito - Solicitar Tarjeta de Crédito adicional - WEB")
    public void TC0160BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uSantafe036);
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.validarTituloPageGenerico("Tarjetas de cr");
            tarjetasDeCreditoSteps.clickVerMasXnroDeTarjeta("3159");
            tarjetasDeCreditoSteps.clickSolicitarAdicional();
            tarjetasDeCreditoSteps.formularioSolicitudAdicional("3159");
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
    @DisplayName("TC-0162-BSF: Tarjeta de Crédito - Denunciar robo o extravío de Tarjeta de Crédito - WEB.")
    public void TC0162BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uSantafe036);
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.validarTituloPageGenerico("Tarjetas de cr");
            tarjetasDeCreditoSteps.clickVerMasXnroDeTarjeta("9875");
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
    @DisplayName("TC-0163-BSF: Tarjeta de Crédito - Pagar el total de una Tarjeta de Crédito Mastercard - Validación de pantalla")
    public void TC0163BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uSantafe038);
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.clickVerMasXnroDeTarjeta("1304");
            tarjetasDeCreditoSteps.clickMasOpcionesGenerico();
            tarjetasDeCreditoSteps.clickPagarTarjeta();
            tarjetasDeCreditoSteps.validarTituloPageGenerico("Pago de tarjeta propia");;
            tarjetasDeCreditoSteps.seleccionarTarjetaDebito("2000");
            tarjetasDeCreditoSteps.clickContinuarGenerico();
            tarjetasDeCreditoSteps.seleccionarCuentaDebitoSiEstaPresente("Caja de Ahorro $ 005001003005");
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
    @DisplayName("TC-0164-BSF: Tarjeta de Crédito - Pagar mínimo de una Tarjeta de Crédito Visa - Validación de pantalla")
    public void TC0164BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uVolpatomonica);
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.clickVerMasXnroDeTarjeta("VISA");
            tarjetasDeCreditoSteps.clickMasOpcionesGenerico();
            tarjetasDeCreditoSteps.clickPagarTarjeta();
            tarjetasDeCreditoSteps.validarTituloPageGenerico("Pago de tarjeta propia");
            tarjetasDeCreditoSteps.seleccionarTarjetaDebito("6004");
            tarjetasDeCreditoSteps.clickContinuarGenerico();
            tarjetasDeCreditoSteps.seleccionarCuentaDebitoSiEstaPresente("Caja de Ahorro $ 000003554209");
            tarjetasDeCreditoSteps.seleccionarTipoDePago( "Pagar el m");
            tarjetasDeCreditoSteps.clickBotonPagar();
            tarjetasDeCreditoSteps.validacionDeCamposPagoDeTarjeta();

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0165-BSF: PAGOS - Pagar Tarjeta de Crédito Master - Otro importe")
    public void TC0165BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uSantafe038);
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.clickVerMasXnroDeTarjeta("1304");
            tarjetasDeCreditoSteps.clickMasOpcionesGenerico();
            tarjetasDeCreditoSteps.clickPagarTarjeta();
            //tarjetasDeCreditoSteps.seleccionarCuentaDebito("Caja de Ahorro $ 005001003005");
            //tarjetasDeCreditoSteps.clickContinuarGenerico();
            tarjetasDeCreditoSteps.seleccionarCuentaDebitoSiEstaPresente("005001003005");
            tarjetasDeCreditoSteps.seleccionarTipoDePago( "Ingresar un importe");
            tarjetasDeCreditoSteps.setImporte("1");
            tarjetasDeCreditoSteps.clickBotonPagar();
            tarjetasDeCreditoSteps.validarTituloPageGenerico("Pago de tarjeta propia");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Alta.class})
    @DisplayName("TC-0166-BSF: Tarjeta de Crédito - Solicitar baja de Tarjeta de Crédito - WEB.")
    public void TC0166BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uSantafe036);
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.validarTituloPageGenerico("Tarjetas de cr");
            tarjetasDeCreditoSteps.clickVerMasXnroDeTarjeta("9875");
            tarjetasDeCreditoSteps.clickMasOpcionesGenerico();
            tarjetasDeCreditoSteps.clickSolicitarBajaTarjeta();
            tarjetasDeCreditoSteps.completarEmailBERSAyBSF("gp@gmail.com");
            tarjetasDeCreditoSteps.completarTelefono("1123454355");
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
    @DisplayName("TC-0167-BSF: Tarjeta de Crédito - Consultar último resumen de una Tarjeta de Crédito - WEB.")
    public void TC0167BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uVolpatomonica);
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.clickVerMasXnroDeTarjeta("VISA");
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
    @DisplayName("TC-0191-BSF: Tarjetas de credito - Solicitud de tarjeta de credito- Al no contar con oferta de TC se debe validar mensaje 'No contás con oferta de Tarjeta de Crédito en este momento. '")
    public void TC0191BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uCallelu);
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
    @DisplayName("TC-0192-BSF: Tarjetas de credito - Solicitud de tarjeta de credito- Al solicitar una nueva TC y clickear el checkbox Persona expuesta políticamente no se debe permitir continuar con la solicitud-  validar pantalla mensaje")
    public void TC0192BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uSantafe036);
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.validarTituloPageGenerico("Tarjetas de cr");
            tarjetasDeCreditoSteps.clickSolicitarTarjetaNueva();
            tarjetasDeCreditoSteps.validarTituloPageGenerico("Solicitud de tarjeta de cr");
            tarjetasDeCreditoSteps.seleccionarCuentaDebitoSiEstaPresente("5304");
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
    @DisplayName("TC-0193-BSF: Tarjetas de credito - Solicitud de tarjeta de credito- Al solicitar una nueva TC y clickear el checkbox 'residencia fiscar en el exterior'  no se debe permitir continuar con la solicitud-  validar pantalla mensaje")
    public void TC0193BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uSantafe036);
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.validarTituloPageGenerico("Tarjetas de cr");
            tarjetasDeCreditoSteps.clickSolicitarTarjetaNueva();
            tarjetasDeCreditoSteps.validarTituloPageGenerico("Solicitud de tarjeta de cr");
            tarjetasDeCreditoSteps.seleccionarCuentaDebitoSiEstaPresente("5304");
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
    @DisplayName("TC-0194-BSF: Tarjetas de credito - Solicitud de tarjeta de credito- Al solicitar una nueva TC y clickear el checkbox \"sujeto obligado iuf\"  no se debe permitir continuar con la solicitud-  validar pantalla mensaje")
    public void TC0194BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uSantafe036);
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.validarTituloPageGenerico("Tarjetas de cr");
            tarjetasDeCreditoSteps.clickSolicitarTarjetaNueva();
            tarjetasDeCreditoSteps.validarTituloPageGenerico("Solicitud de tarjeta de cr");
            tarjetasDeCreditoSteps.seleccionarCuentaDebitoSiEstaPresente("5304");
            tarjetasDeCreditoSteps.clickCheckboxSujetoObligadoIUF();
            tarjetasDeCreditoSteps.clickContinuarGenerico();
            tarjetasDeCreditoSteps.validarMensajeSolicitaTurnoEnSucursal();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Criticos.class})
    @DisplayName("TC-12967-BSF: PAGOS - Pagar Tarjeta de Crédito Visa - Otro importe")
    public void TC12967BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uSantafe033);
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.clickVerMasXnroDeTarjeta("9087");
            botonesGenericosSteps.clickMasOpcionesGenerico();
            tarjetasDeCreditoSteps.clickPagarTarjeta();
            tarjetasDeCreditoSteps.seleccionarCuentaDebito("Caja de Ahorro $ 001000009500 ");
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
    @Test
    @Category({Alta.class})
    @DisplayName("TC-0195-BSF: Tarjetas de credito - Solicitud de tarjeta de credito- Al solicitar una nueva TC y verificar mensaje de solicitar turno, clickear en  'Solicitá tu turno acá.' y validar redireccion de pantalla")
    public void TC0195BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uSantafe036);
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.validarTituloPageGenerico("Tarjetas de cr");
            tarjetasDeCreditoSteps.clickSolicitarTarjetaNueva();
            tarjetasDeCreditoSteps.validarTituloPageGenerico("Solicitud de tarjeta de cr");
            tarjetasDeCreditoSteps.seleccionarCuentaDebitoSiEstaPresente("5304");
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
