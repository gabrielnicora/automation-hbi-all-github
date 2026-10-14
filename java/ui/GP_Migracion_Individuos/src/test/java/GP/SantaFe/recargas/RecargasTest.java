
package GP.SantaFe.recargas;

import GP.Tags.Alta;
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
import utils.MetodosAPI;
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
    RecargasSteps recargasSteps = new RecargasSteps();
    DobleFactorSteps dobleFactorSteps = new DobleFactorSteps();
    TicketSteps ticketSteps = new TicketSteps();
    NuevoPagoSteps nuevoPagoSteps = new NuevoPagoSteps();
    ConsultaRecargasSteps consultaRecargasSteps = new ConsultaRecargasSteps();
    BotonesGenericosSteps botonesGenericosSteps = new BotonesGenericosSteps();
    CalendarioSteps calendario = new CalendarioSteps();
    BotonesGenericosPage botonesGenericosPage = new BotonesGenericosPage();
    UtilsGP utilsGP = new UtilsGP();
    private String testActual;
    MetodosAPI metodosAPI = new MetodosAPI();
    private static String CLARO_ID = "600";
    private static String SUBE_ID = "618";

    private final List<String> tcsAdhierenRecargas = new ArrayList<String>() {
        {
            add("TC0072BSF");
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
    @DisplayName("TC-0071-BSF: RECARGAS - Realizar recarga a Transporte Publico")
    public void TC0071BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uBenicio2022);
            menuLateralSteps.clickRecargas();
            recargasSteps.clickCardTransportePublico();
            recargasSteps.clickEmpresa();
            recargasSteps.keyDownToText("SUBE");
            recargasSteps.clickSeleccionarNumero();
            recargasSteps.keyDownToText("6061267159402278");
            recargasSteps.clickSelectorMonto();
            recargasSteps.keyDownXParametrosGenerico(0,"");
            recargasSteps.clickAgregar();
            recargasSteps.clickDesplegableDebito();
            recargasSteps.keyDownXParametrosGenerico(1, "Cuenta Corriente $ 000009000001");
            recargasSteps.clickContinuar();
            recargasSteps.clickConfirmarGenerico();
            recargasSteps.clickIconoTicket();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0072-BSF: Recargas - Adhesión para recarga")
    public void TC0072BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uBenicio2022);
            menuLateralSteps.clickRecargas();
            recargasSteps.clickCardTransportePublico();
            recargasSteps.clickEmpresa();
            recargasSteps.keyDownToText("SUBE");
            recargasSteps.clickSeleccionarNumero();
            recargasSteps.keyDownXParametrosGenerico(0,"");
            recargasSteps.setInputNumero("6061267195495204");
            recargasSteps.setInputPerteneceA("AUTOMATION3");
            //dobleFactorSteps.clickSms();
            // dobleFactorSteps.codigoSegundoFactorOtroMetodo(uBenicio2022);
            //POSTCONDICION
            recargasSteps.eliminarAdhesaionesdeServicioAPI(SUBE_ID,"6061267195495204","AUTOMATION3","9003");
            //TEARDOWN EN AFTER
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Alta.class})
    @DisplayName("TC-0106-BSF: Recargas - Consulta de recargas")
    public void TC0106BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uTranspull4);
            menuLateralSteps.clickRecargas();
            recargasSteps.clickConsultasRecargas();
            recargasSteps.validarTituloPageGenerico("Recargas realizadas");
            recargasSteps.clickCardTransportePublico();
            consultaRecargasSteps.clickEnte();
            consultaRecargasSteps.keyDownToText( "SUBE");
            consultaRecargasSteps.clickCanal();
            consultaRecargasSteps.keyDownToText(  "Homebanking");
            consultaRecargasSteps.clickNumero();
            consultaRecargasSteps.keyDownXParametrosGenerico( 0,"");
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
    @DisplayName("TC-0107-BSF: Recargas - Consulta de recargas - Botón descarga de comprobante")
    public void TC0107BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uTranspull4);
            menuLateralSteps.clickRecargas();
            recargasSteps.clickConsultasRecargas();
            recargasSteps.validarTituloPageGenerico("Recargas realizadas");
            recargasSteps.clickCardTransportePublico();
            consultaRecargasSteps.clickEnte();
            consultaRecargasSteps.keyDownToText( "SUBE");
            consultaRecargasSteps.clickCanal();
            consultaRecargasSteps.keyDownToText( "Homebanking");
            consultaRecargasSteps.clickNumero();
            consultaRecargasSteps.keyDownXParametrosGenerico( 0,"");
            consultaRecargasSteps.clickFechaCreacion();
            calendario.clickMesAnterior(10);
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
    @DisplayName("TC-12955-BSF: Realizar recarga a Tarjeta Recargable/Prepagada")
    public void TC12955BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uAbramcelia);
            menuLateralSteps.clickRecargas();
            recargasSteps.clickSelectorTarjetaDebito();
            consultaRecargasSteps.keyDownToText( "6007");
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
            //consultaRecargasSteps.keyDownToText( "Caja de Ahorro $ 000000869803");
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
    @DisplayName("TC-12956-BSF: RECARGAS - Eliminar adhesión")
    public void TC12956BSF() {
        try {
            //PRECONDICION
            loginSteps.iniciarSessionAlEscritorio(uAbramcelia);
            recargasSteps.realizarAdhesionAPI(CLARO_ID,"5557504183", "TC12956","8003");
            //CASO
            menuLateralSteps.clickRecargas();
            recargasSteps.clickAdhesiones();
            recargasSteps.clickBotonSeleccionar();
            nuevoPagoSteps.keyDownToText("8003");
            recargasSteps.clickBotonSeleccionar();
            nuevoPagoSteps.keyDownToText("Celular");
            recargasSteps.clickBotonSeleccionar();
            recargasSteps.keyDownToText("CLARO");
            calendario.clickBuscar();
            recargasSteps.clickIconoEliminarAdhesion("TC12956");
            recargasSteps.clickConfirmarGenerico();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Criticos.class})
    @DisplayName("TC-12952-BSF: RECARGAS - Realizar recarga a Telefonía Celular")
    public void TC12952BSF() {
        try {
            //PRECONDICION
            loginSteps.iniciarSessionAlEscritorio(uAbramcelia);
            menuLateralSteps.clickRecargas();
            recargasSteps.clickSelectorTarjetaDebito();
            recargasSteps.keyDownToText("8003");
            recargasSteps.clickContinuar();
            recargasSteps.clickCardTelefoniaCelular();
            recargasSteps.clickEmpresa();
            recargasSteps.keyDownToText("CLARO");
            recargasSteps.clickSeleccionarNumero();
            recargasSteps.keyDownXParametrosGenerico(0,"");
            recargasSteps.setInputNumero("5557504183");
            recargasSteps.setInputPerteneceA("Juan");
            recargasSteps.clickSelectorMonto();
            recargasSteps.keyDownToText("$500.00");
            dobleFactorSteps.clickSms();
            dobleFactorSteps.codigoSegundoFactor();
            recargasSteps.clickAgregar();
            recargasSteps.clickDesplegableDebito();
            recargasSteps.keyDownToText("Caja de Ahorro $ 000000869803");
            recargasSteps.clickContinuar();
            recargasSteps.clickConfirmarGenerico();
            recargasSteps.verificarDatosRecargaTelefoniaCelular("CLARO","5557504183","500.00","No pudimos procesar la operación");
            //POSTCONDICION ELIMINAR ADHESION
            recargasSteps.eliminarAdhesaionesdeServicioAPI(CLARO_ID,"5557504183","Juan","8003");

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Criticos.class})
    @DisplayName("TC-12954-BSF: RECARGAS - Realizar recarga a Billetera/Serv. Prepago")
    public void TC12954BSF() {
        try {
            //CASO
            loginSteps.iniciarSessionAlEscritorio(uAbramcelia);
            menuLateralSteps.clickRecargas();
            recargasSteps.clickSelectorTarjetaDebito();
            recargasSteps.keyDownToText("6007");
            recargasSteps.clickContinuar();
            recargasSteps.clickCardBilletera();
            recargasSteps.clickEmpresa();
            recargasSteps.keyDownToText("PIM TU PLATA EN TU CELULAR");
            recargasSteps.clickSeleccionarNumero();
            recargasSteps.keyDownXParametrosGenerico(0,"");
            recargasSteps.setInputNumero("1167262370");
            recargasSteps.setInputPerteneceA("TC-12954");
            recargasSteps.clickMontoRecarga("1");
            dobleFactorSteps.clickSms();
            dobleFactorSteps.codigoSegundoFactor();
            recargasSteps.clickAgregar();
            Thread.sleep(1000);
            recargasSteps.clickDesplegableDebito();
            consultaRecargasSteps.keyDownToText( "Caja de Ahorro $ 000000869803");
            recargasSteps.clickContinuar();
            recargasSteps.clickConfirmarGenerico();
            recargasSteps.verificarDatosRecargaTelefoniaCelular("PIM TU PLATA EN TU CELULAR","1167262379","1,00","No pudimos procesar la operación");
            //POSTCONDICION ELIMINAR ADHESION
            recargasSteps.eliminarAdhesaionesdeServicioAPI(SUBE_ID,"6061267195495203","TC0072","9003");

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

