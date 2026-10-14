package GP.EntreRios.colocaciones_a_plazo;

import GP.Tags.Alta;
import GP.Tags.Baja;
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
import steps.colocaciones_a_plazo.ColocacionesAPlazoSteps;
import steps.doblefactor.DobleFactorSteps;
import steps.login.LoginSteps;
import steps.menulateral.MenuLateralSteps;
import steps.pagos.PagosSteps;
import steps.pagos.debinvendedor.DebinVendedorSteps;
import steps.ticket.TicketSteps;
import utils.UtilsGP;

import java.io.IOException;

import static db.ConexionDB.cerrarConexion;
import static utils.Constantes.*;

public class ColocacionesAPlazoTest {
    LoginSteps loginSteps = new LoginSteps();
    CalendarioSteps calendarioSteps = new CalendarioSteps();
    MenuLateralSteps menuLateralSteps = new MenuLateralSteps();
    ColocacionesAPlazoSteps colocacionesAPlazoSteps= new ColocacionesAPlazoSteps();
    PagosSteps pagosSteps = new PagosSteps();
    DebinVendedorSteps debinVendedorSteps = new DebinVendedorSteps();
    TicketSteps ticketSteps = new TicketSteps();
    DobleFactorSteps dobleFactorSteps= new DobleFactorSteps();
    BotonesGenericosSteps botonesGenericosSteps = new BotonesGenericosSteps();
    BotonesGenericosPage botonesGenericosPage = new BotonesGenericosPage();
    UtilsGP utilsGP = new UtilsGP();

    @Test
    @Category({Media.class})
    @DisplayName("TC-0084-BERSA: Colocaciones a plazo - Consultar debines históricos ")
    public void TC0084BERSA() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uEcarina27);
            menuLateralSteps.clickColocacionAPlazosFijos();
            colocacionesAPlazoSteps.validarTituloPageGenerico("Gesti");
            colocacionesAPlazoSteps.clickConsultarDebinDesplegable();
            colocacionesAPlazoSteps.keyDownToText("Hist");
            colocacionesAPlazoSteps.clickFechaCreacionDesde();
            calendarioSteps.clickMesAnterior(6);
            calendarioSteps.clickEnDiaXParametro("1");
            colocacionesAPlazoSteps.clickBuscar();
            colocacionesAPlazoSteps.validarTablaDeHistoricoyPendientes();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Media.class})
    @DisplayName("TC-0088-BERSA: Colocaciones a plazo - Consultar debines Pendientes")
    public void TC0088BERSA() {
        try {
            //GENERACION DEL DEBIN
            loginSteps.iniciarSessionAlEscritorio(uEcarina27);
            menuLateralSteps.clickPagos();
            pagosSteps.clickMasOpcionesGenerico();
            pagosSteps.clickDebinVendedor();
            debinVendedorSteps.clickOrdenDebin();
            debinVendedorSteps.clickCuentaDestinoDesplegable();
            debinVendedorSteps.keyDownToText( "2210");
            debinVendedorSteps.setImporteGenerico("1");
            debinVendedorSteps.clickCbu();
            debinVendedorSteps.setCbuOAlias("3860001005000040218693");
            debinVendedorSteps.clickBuscar();
            debinVendedorSteps.clickConcepto();
            debinVendedorSteps.keyDownToText("Alquileres");
            debinVendedorSteps.setReferencia("Test0013");
            debinVendedorSteps.clickVigencia();
            debinVendedorSteps.keyDownToText( "10 Minutos");
            debinVendedorSteps.clickContinuarGenerico();
            debinVendedorSteps.clickContinuarGenerico();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            debinVendedorSteps.clickSalirDelaSesion();
            //CONSULTA DE DEBIN PENDIENTE
            loginSteps.iniciarSessionAlEscritorio(u11107010);
            menuLateralSteps.clickColocacionAPlazosFijos();
            colocacionesAPlazoSteps.validarTituloPageGenerico("Gesti");
            colocacionesAPlazoSteps.clickConsultarDebinDesplegable();
            colocacionesAPlazoSteps.keyDownToText("Pendientes");
            colocacionesAPlazoSteps.clickBuscar();
            colocacionesAPlazoSteps.validarTablaDeHistoricoyPendientes();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Alta.class})
    @DisplayName("TC-0093-BERSA: Colocaciones a plazo - Adherir recurrencia")
    public void TC0093BERSA() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uEcarina27);
            menuLateralSteps.clickColocacionAPlazosFijos();
            colocacionesAPlazoSteps.clickAdherirRecurrencia();
            colocacionesAPlazoSteps.validarTituloPageGenerico("Adherir recurrencia");
            colocacionesAPlazoSteps.clickDesplegableCuentaDebito();
            colocacionesAPlazoSteps.keyDownToText("2210");
            colocacionesAPlazoSteps.clickCuitVendedor();
            colocacionesAPlazoSteps.setCuitVendedor("20179326591");
            colocacionesAPlazoSteps.clickConsultarGenerico();
            colocacionesAPlazoSteps.clickDesplegablePrestacion();
            colocacionesAPlazoSteps.keyDownToText( "cualquiera");
            colocacionesAPlazoSteps.clickDesplegableConcepto();
            colocacionesAPlazoSteps.keyDownToText("Alquileres");
            colocacionesAPlazoSteps.setReferencia("TC0093");
            colocacionesAPlazoSteps.setDescripcion("Test");
            colocacionesAPlazoSteps.clickContinuar();
            colocacionesAPlazoSteps.clickCheckTerminosCondicionesGenerico();
            //dobleFactorSteps.codigoSegundoFactor(uEcarina27);
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Baja.class})
    @DisplayName("TC-0097-BERSA: Colocaciones a plazo - Consultar recurrencia")
    public void TC0097BERSA() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uUathb04);
            menuLateralSteps.clickColocacionAPlazosFijos();
            colocacionesAPlazoSteps.clickConsultarRecurrencias();
            colocacionesAPlazoSteps.validarTituloPageGenerico("Consulta de recurrencias");
            colocacionesAPlazoSteps.clickDesplegableCuentaDebito();
            colocacionesAPlazoSteps.keyDownToText("3427");
            colocacionesAPlazoSteps.clickDesplegableEstado();
            colocacionesAPlazoSteps.keyDownToText("Activa");
            calendarioSteps.clickCalendario();
            calendarioSteps.clickMesAnterior(10);
            calendarioSteps.clickEnDiaXParametro("1");
            colocacionesAPlazoSteps.clickBuscar();
            colocacionesAPlazoSteps.validarTablaDeHistoricoyPendientes();
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
