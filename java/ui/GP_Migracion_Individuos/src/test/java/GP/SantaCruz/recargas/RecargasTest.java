package GP.SantaCruz.recargas;

import GP.Tags.Alta;
import GP.Tags.Baja;
import GP.Tags.Criticos;
import GP.Tags.Media;
import io.qameta.allure.Allure;
import io.qameta.allure.junit4.DisplayName;
import org.junit.*;
import org.junit.experimental.categories.Category;
import org.junit.rules.TestWatcher;
import pages.botonesgenericos.BotonesGenericosPage;
import steps.botonesgenericos.BotonesGenericosSteps;
import steps.calendarios.CalendarioSteps;
import steps.doblefactor.DobleFactorSteps;
import steps.login.LoginSteps;
import steps.menulateral.MenuLateralSteps;
import steps.pagos.nuevopago.NuevoPagoSteps;
import steps.recargas.RecargasSteps;
import steps.recargas.consultarecargas.ConsultaRecargasSteps;
import steps.ticket.TicketSteps;
import utils.UtilsGP;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static db.ConexionDB.cerrarConexion;
import static utils.Constantes.*;

public class RecargasTest {
    LoginSteps loginSteps = new LoginSteps();
    MenuLateralSteps menuLateralSteps = new MenuLateralSteps();
    BotonesGenericosSteps botonesGenericosSteps = new  BotonesGenericosSteps();
    RecargasSteps recargasSteps = new RecargasSteps();
    DobleFactorSteps dobleFactorSteps = new DobleFactorSteps();
    TicketSteps ticketSteps = new TicketSteps();
    ConsultaRecargasSteps consultaRecargasSteps = new ConsultaRecargasSteps();
    CalendarioSteps calendario = new CalendarioSteps();
    NuevoPagoSteps nuevoPagoSteps = new NuevoPagoSteps();
    BotonesGenericosPage botonesGenericosPage = new BotonesGenericosPage();
    UtilsGP utilsGP = new UtilsGP();
    private String testActual;
    private final List<String> tcsAdhierenRecargas = new ArrayList<String>() {
        {
            add("TC0071BSC");
            //add("TC0072BSC");
        }
    };

    @Rule
    public final TestWatcher testWatcher = new TestWatcher() {
        @Override
        protected void starting(org.junit.runner.Description description) {
            System.out.println("Iniciando prueba: " + description.getMethodName());
            testActual = description.getMethodName();
        }
    };

    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0071-BSC: RECARGAS - Realizar recarga a Transporte Publico")
    public void TC0071BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(u21829785);
            menuLateralSteps.clickRecargas();
            recargasSteps.clickCardTransportePublico();
            recargasSteps.clickEmpresa();
            recargasSteps.keyDownToText("SUBE");
            recargasSteps.clickSeleccionarNumero();
            recargasSteps.keyDownXParametrosGenerico(0,"");
            recargasSteps.setInputNumero("6061267195495203");
            recargasSteps.setInputPerteneceA("AUTOMATION");
            dobleFactorSteps.clickSms();
            dobleFactorSteps.codigoSegundoFactorOtroMetodo();
            recargasSteps.clickSelectorMonto();
            recargasSteps.keyDownXParametrosGenerico(0,"");
            recargasSteps.clickAgregar();
            recargasSteps.clickDesplegableDebito();
            recargasSteps.keyDownXParametrosGenerico(0, "");
            recargasSteps.clickContinuar();
            recargasSteps.clickConfirmarGenerico();
            recargasSteps.clickIconoTicket();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            //TEARDOWN
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0072-BSC: Recargas - Adhesión para recarga")
    public void TC0072BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uBsc001);
            menuLateralSteps.clickRecargas();
            recargasSteps.clickCardTransportePublico();
            recargasSteps.clickEmpresa();
            recargasSteps.keyDownToText("SUBE");
            recargasSteps.clickSeleccionarNumero();
            recargasSteps.keyDownXParametrosGenerico(0,"");
            recargasSteps.setInputNumero("6061267195495203");
            recargasSteps.setInputPerteneceA("AUTOMATION");
            //dobleFactorSteps.clickSms();
            dobleFactorSteps.codigoSegundoFactorOtroMetodo();
            //POSTCONDICION
            recargasSteps.eliminarAdhesaionesdeServicioAPI("","618","6061267195495203","TC0072");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Alta.class})
    @DisplayName("TC-0106-BSC: Recargas - Consulta de recargas")
    public void TC0106BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uBsc13400488);
            menuLateralSteps.clickRecargas();
            recargasSteps.clickConsultasRecargas();
            recargasSteps.clickSelectorTarjetaDebito();
            recargasSteps.keyDownToText("0028");
            recargasSteps.clickContinuar();
            recargasSteps.validarTituloPageGenerico("Recargas realizadas");
            recargasSteps.clickCardTransportePublico();
            consultaRecargasSteps.clickEnte();
            consultaRecargasSteps.keyDownXParametrosGenerico(6,"SUBE");
            consultaRecargasSteps.clickCanal();
            consultaRecargasSteps.keyDownXParametrosGenerico(0,"Homebanking");
            consultaRecargasSteps.clickNumero();
            consultaRecargasSteps.keyDownXParametrosGenerico(0,"6061");
            consultaRecargasSteps.clickFechaCreacion();
            calendario.clickMesAnterior(5);
            calendario.clickEnDiaXParametro("1");
            calendario.clickBuscar();
            consultaRecargasSteps.validarRecargasRealizadas();

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Media.class})
    @DisplayName("TC-0107-BSC: Recargas - Consulta de recargas - Botón descarga de comprobante")
    public void TC0107BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uBsc13400488);
            menuLateralSteps.clickRecargas();
            recargasSteps.validarTituloPageGenerico("Recargas");
            recargasSteps.clickConsultasRecargas();
            recargasSteps.clickSelectorTarjetaDebito();
            recargasSteps.keyDownToText("0028");
            recargasSteps.clickContinuar();
            recargasSteps.clickCardTransportePublico();
            consultaRecargasSteps.clickEnte();
            consultaRecargasSteps.keyDownXParametrosGenerico(6,"SUBE");
            consultaRecargasSteps.clickCanal();
            consultaRecargasSteps.keyDownXParametrosGenerico(0,"Homebanking");
            consultaRecargasSteps.clickNumero();
            consultaRecargasSteps.keyDownXParametrosGenerico(0,"6061");
            consultaRecargasSteps.clickFechaCreacion();
            calendario.clickMesAnterior(5);
            calendario.clickEnDiaXParametro("1");
            calendario.clickBuscar();
            consultaRecargasSteps.validarDescargarPdfGenerico();
            consultaRecargasSteps.clickDescargarPdfGenerico();
            consultaRecargasSteps.clickVerTicket();
            consultaRecargasSteps.clickIconoDescargar();
            consultaRecargasSteps.validarDescarga("Ticket.pdf");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Criticos.class})
    @DisplayName("TC-12955-BSC: Realizar recarga a Tarjeta Recargable/Prepagada")
    public void TC12955BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(u21829785);
            menuLateralSteps.clickRecargas();
            recargasSteps.clickSelectorTarjetaDebito();
            consultaRecargasSteps.keyDownToText( "2012");
            recargasSteps.clickContinuar();
            recargasSteps.clickCardtarjetaRecargable();
            recargasSteps.clickEmpresa();
            consultaRecargasSteps.keyDownToText( "VISA REGALO");
            recargasSteps.clickSeleccionarNumero();
            consultaRecargasSteps.keyDownToText( "JUAN");
            recargasSteps.clickMontoRecarga("1");
            recargasSteps.clickAgregar();
            recargasSteps.clickDesplegableDebito();
            consultaRecargasSteps.keyDownToText( "Cuenta Corriente");
            recargasSteps.clickContinuar();
            recargasSteps.verificarDatosRecargaCuentaOrigen();
            recargasSteps.clickConfirmarGenerico();

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Criticos.class})
    @DisplayName("TC-12956-BSC: RECARGAS - Eliminar adhesión")
    public void TC12956BSC() {
        try {
            //PRECONDICION
            loginSteps.iniciarSessionAlEscritorio(u21829785);
            //recargasSteps.realizarAdhesionTelAPI("/api/v1/execute/recharge.new.adhesionWithSecondFactor","605","1133443344",
              //      "TC12956");

            //CASO
            menuLateralSteps.clickRecargas();
            recargasSteps.clickAdhesiones();
            recargasSteps.clickBotonSeleccionar();
            nuevoPagoSteps.keyDownToText("Celular");
            recargasSteps.clickBotonSeleccionar();
            recargasSteps.keyDownToText("Movistar");
            calendario.clickBuscar();
            botonesGenericosSteps.clickIconoEliminar();
            recargasSteps.clickConfirmarGenerico();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Criticos.class})
    @DisplayName("TC-12952-BSC: RECARGAS - Realizar recarga a Telefonía Celular")
    public void TC12952BSC() {
        try {
            //PRECONDICION
            loginSteps.iniciarSessionAlEscritorio(u23013315);
            menuLateralSteps.clickRecargas();
            recargasSteps.clickCardTelefoniaCelular();
            recargasSteps.clickEmpresa();
            recargasSteps.keyDownToText("Movistar");
            recargasSteps.clickSeleccionarNumero();
            recargasSteps.keyDownXParametrosGenerico(0,"");
            recargasSteps.setInputNumero("1133443344");
            recargasSteps.setInputPerteneceA("Juan");
            recargasSteps.clickSelectorMonto();
            recargasSteps.keyDownToText("$300.00");
            dobleFactorSteps.clickSms();
            dobleFactorSteps.codigoSegundoFactorOtroMetodo();
            recargasSteps.clickAgregar();
            Thread.sleep(1000);
            recargasSteps.clickDesplegableDebito();
            recargasSteps.keyDownToText("Cuenta Corriente $ 253558855");
            recargasSteps.clickContinuar();
            recargasSteps.clickConfirmarGenerico();
            Thread.sleep(1000);
            recargasSteps.verificarDatosRecargaTelefoniaCelular("Movistar","1133443344","$300.00","No pudimos procesar la operación");
            //POSTCONDICION ELIMINAR ADHESION
            menuLateralSteps.clickCuentas();
            menuLateralSteps.clickRecargas();
            recargasSteps.clickAdhesiones();
            recargasSteps.clickBotonSeleccionar();
            nuevoPagoSteps.keyDownToText("Telefonia Celular");
            recargasSteps.clickBotonSeleccionar();
            recargasSteps.keyDownToText("Movistar");
            calendario.clickBuscar();
            botonesGenericosSteps.clickIconoEliminar();
            recargasSteps.clickConfirmarGenerico();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Criticos.class})
    @DisplayName("TC-12954-BSC: RECARGAS - Realizar recarga a Billetera/Serv. Prepago")
    public void TC12954BSC() {
        try {
            //CASO
            loginSteps.iniciarSessionAlEscritorio(u21829785);
            menuLateralSteps.clickRecargas();
            recargasSteps.clickSelectorTarjetaDebito();
            recargasSteps.keyDownToText("2012");
            recargasSteps.clickContinuar();
            recargasSteps.clickCardBilletera();
            recargasSteps.clickEmpresa();
            recargasSteps.keyDownToText("PIM TU PLATA EN TU CELULAR");
            recargasSteps.clickSeleccionarNumero();
            recargasSteps.keyDownXParametrosGenerico(0,"");
            recargasSteps.setInputNumero("1155632688");
            recargasSteps.setInputPerteneceA("TC-12954");
            recargasSteps.clickMontoRecarga("1");
            //dobleFactorSteps.clickSms();
            dobleFactorSteps.codigoSegundoFactorOtroMetodo();
            recargasSteps.clickAgregar();
            Thread.sleep(1000);
            recargasSteps.clickDesplegableDebito();
            consultaRecargasSteps.keyDownToText( "Cuenta Corriente $ 200001906");
            recargasSteps.clickContinuar();
            recargasSteps.clickConfirmarGenerico();
            recargasSteps.verificarDatosRecargaTelefoniaCelular("PIM TU PLATA EN TU CELULAR","1155632688","1,00","No pudimos procesar la operación");
            //POSTCONDICION ELIMINAR ADHESION
            menuLateralSteps.clickCuentas();
            menuLateralSteps.clickRecargas();
            recargasSteps.clickAdhesiones();
            recargasSteps.clickBotonSeleccionar();
            nuevoPagoSteps.keyDownToText("BILLETERA/SERV PREPAGO");
            recargasSteps.clickBotonSeleccionar();
            recargasSteps.keyDownToText("PIM TU PLATA EN TU CELULAR");
            calendario.clickBuscar();
            botonesGenericosSteps.clickIconoEliminar();
            recargasSteps.clickConfirmarGenerico();
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
        cerrarConexion();
    }
}
