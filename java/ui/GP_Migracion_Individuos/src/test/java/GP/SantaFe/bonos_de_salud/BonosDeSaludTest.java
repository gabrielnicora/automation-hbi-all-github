package GP.SantaFe.bonos_de_salud;
import GP.Tags.Alta;
import GP.Tags.Criticos;
import GP.Tags.Media;
import io.qameta.allure.Allure;
import io.qameta.allure.junit4.DisplayName;
import org.junit.*;
import org.junit.experimental.categories.Category;
import org.junit.rules.TestWatcher;
import org.openqa.selenium.By;
import pages.botonesgenericos.BotonesGenericosPage;
import steps.bonos_de_salud.BonosDeSaludSteps;
import steps.botonesgenericos.BotonesGenericosSteps;
import steps.calendarios.CalendarioSteps;
import steps.login.LoginSteps;
import steps.menulateral.MenuLateralSteps;
import steps.ticket.TicketSteps;
import utils.MetodosAPI;
import utils.UtilsGP;

import java.awt.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static db.ConexionDB.cerrarConexion;
import static utils.Constantes.uTranspull4;
import static utils.Constantes.uVolpatomonica;


public class BonosDeSaludTest {
    LoginSteps loginSteps = new LoginSteps();
    MenuLateralSteps menuLateralSteps = new MenuLateralSteps();
    BonosDeSaludSteps bonos = new BonosDeSaludSteps();
    CalendarioSteps calendario = new CalendarioSteps();
    TicketSteps ticketSteps = new TicketSteps();
    MetodosAPI metodosAPI = new MetodosAPI();
    BotonesGenericosPage botonesGenericosPage = new BotonesGenericosPage();
    BotonesGenericosSteps botonesGenericosSteps = new BotonesGenericosSteps();
    UtilsGP utilsGP = new UtilsGP();
    private String testActual;
    private final List<String> tcsAdhierenAfiliados = new ArrayList<String>() {
        {
            add("TC0076BSF");
            add("TC0091BSF");
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
    @Category({Alta.class})
    @DisplayName("TC -0076 BSF - Bonos de salud- Compra de bonos de salud - Nuevo Afiliado")
    public void TC0076BSF() {
        try {

            loginSteps.iniciarSessionAlEscritorio(uTranspull4);
            menuLateralSteps.clickBonosDeSalud();
            bonos.validarTituloPageGenerico("Compra de bonos de salud");
            bonos.clickDesplegableEnte();
            bonos.keyDownToText("IAPOS");
            bonos.clickDesplegableNumeroDeAfiliado();
            bonos.keyDownXParametrosGenerico(0, "");
            bonos.clickNumero();
            bonos.setInputNumero("30379884");
            bonos.clickPeterneceA();
            bonos.setInputPerteneceA("AUTOMATIZACION");
            bonos.clickIconoConfirmar();
            //POSTCONDICION
            bonos.eliminarAdhesionAPI("IAPOS","30379884","AUTOMATIZACION");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test //NO PUDIMOS PROCESAR LA TRANSACCION
    @Category({Criticos.class})
    @DisplayName("TC- 0080-BSF - Bonos de salud- Comprar Bonos de salud - Ticket de transacción")
    public void TC0080BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uTranspull4);
            menuLateralSteps.clickBonosDeSalud();
            bonos.validarTituloPageGenerico("Compra de bonos de salud");
            Thread.sleep(3000);
            bonos.clickDesplegableEnte();
            bonos.keyDownToText("IAPOS");
            bonos.clickDesplegableNumeroDeAfiliado();
            bonos.keyDownXParametrosGenerico(0, "");
            bonos.setInputNumero("1234");
            bonos.setInputPerteneceA("Prueba");
            bonos.clickIconoConfirmar();
            bonos.keyDownXParametrosGenerico(1, "");
            bonos.clickAgregarBonos();
            bonos.clickDesplegableCuentaOrigen();
            bonos.keyDownToText("000021333202");
            bonos.clickContinuarGenerico();
            bonos.clickConfirmarGenerico();
            ticketSteps.validarTicket("Transaccion realizada con exito");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test //No se encuentran compras registradas
    @Category({Criticos.class})
    @DisplayName("TC -0081 BSF - Bonos de salud-  Consultar compras")
    public void TC0081BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uVolpatomonica);
            menuLateralSteps.clickBonosDeSalud();
            bonos.validarTituloPageGenerico("Compra de bonos de salud");
            bonos.clickConsultarCompras();
            bonos.clickDesplegableTarjetaDebito();
            bonos.keyDownToText("9008");
            Thread.sleep(3000);
            bonos.clickDesplegableEnte();
            bonos.keyDownToText("IAPOS");
            bonos.clickDesplegableCanal();
            bonos.keyDownToText("HOMEBANKING");
            bonos.clickCalendarioDesde();
            calendario.clickMesAnterior(6);
            calendario.clickEnDiaXParametro("1");
            bonos.clickDesplegableNumeroDeAfiliado();
            bonos.keyDownXParametrosGenerico(0, "");
            calendario.clickBuscar();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Media.class})
    @DisplayName("TC-0091 BSF - Bonos de salud - Adhesiones - Afiliados adheridos-")
    public void TC0091BSF() {
        try {
            //PRECONDICION
            loginSteps.iniciarSessionAlEscritorio(uTranspull4);
            bonos.realizarAdhesionAPI("IAPOS","30379884","AUTOMATIZACION");
            /*
            menuLateralSteps.clickBonosDeSalud();
            bonos.clickDesplegableEnte();
            bonos.keyDownToText("IAPOS");
            bonos.clickDesplegableNumeroDeAfiliado();
            bonos.keyDownXParametrosGenerico(0, "");
            bonos.clickNumero();
            bonos.setInputNumero("30379884");
            bonos.clickPeterneceA();
            bonos.setInputPerteneceA("AUTOMATIZACION");
            bonos.clickIconoConfirmar();
             */
            //TEST
            menuLateralSteps.clickBonosDeSalud();
            Thread.sleep(5000);
            bonos.clickAdhesiones();
            bonos.validarTituloPageGenerico("Afiliados adheridos");
            bonos.clickDesplegableEnte();
            bonos.keyDownToText("IAPOS");
            bonos.clickBuscar();
            bonos.validarTablaAfiliados();
            //POSTCONDICION
            bonos.eliminarAdhesionAPI("IAPOS","30379884","AUTOMATIZACION");


        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Alta.class})
    @DisplayName("TC-0095 BSF - Bonos de salud - Adhesiones - Eliminar Afiliado adherido ")
    public void TC0095BSF() {
        try {
            //PRECONDICION
            loginSteps.iniciarSessionAlEscritorio(uTranspull4);
            bonos.realizarAdhesionAPI("IAPOS","30379884","AUTOMATIZACION");
            //CASO
            menuLateralSteps.clickBonosDeSalud();
            bonos.clickAdhesiones();
            bonos.eliminarAfiliado("IAPOS");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test //No se encuentran compras registrada
    @Category({Media.class})
    @DisplayName("TC-110 - BSF: Bonos de salud-  Consultar compras - Descarga de archivo")
    public void TC0110BSF() throws UnsupportedEncodingException {
        try {
            loginSteps.iniciarSessionAlEscritorio(uVolpatomonica);
            menuLateralSteps.clickBonosDeSalud();
            bonos.validarTituloPageGenerico("Compra de bonos de salud");
            bonos.clickConsultarCompras();
            bonos.clickDesplegableTarjetaDebito();
            bonos.keyDownXParametrosGenerico(0, "");
            bonos.clickDesplegableEnte();
            bonos.keyDownXParametrosGenerico(0, "");
            bonos.clickDesplegableNumeroDeAfiliado();
            bonos.keyDownXParametrosGenerico(0, "");
            bonos.clickDesplegableCanal();
            bonos.keyDownXParametrosGenerico(0, "");
            bonos.setImporteGenerico("1");
            calendario.clickEnFechaDeCreacionDesde();
            calendario.retrocederSegunMes(8);
            calendario.clickEnDiaXParametro("1");
            bonos.clickBuscar();
            bonos.clickDescargarPdfGenerico();
            //falta terminar por falta de datos
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