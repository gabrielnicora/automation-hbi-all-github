package GP.SantaFe.inversiones;



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
import steps.inversiones.InversionesSteps;
import steps.inversiones.plazosfijos.ConsultaDeTasasSteps;
import steps.inversiones.plazosfijos.PlazosFijosSteps;
import steps.inversiones.plazosfijos.SolicitarPlazoFijoSteps;
import steps.login.LoginSteps;
import steps.menulateral.MenuLateralSteps;
import steps.ticket.TicketSteps;
import utils.UtilsGP;

import java.io.IOException;

import static db.ConexionDB.cerrarConexion;
import static utils.Constantes.*;

public class PlazoFijosTest {
    LoginSteps loginSteps = new LoginSteps();
    MenuLateralSteps menuLateralSteps = new MenuLateralSteps();
    InversionesSteps inversionesSteps = new InversionesSteps();
    PlazosFijosSteps plazosFijosSteps = new PlazosFijosSteps();
    SolicitarPlazoFijoSteps solicitarPlazoFijoSteps = new SolicitarPlazoFijoSteps();
    TicketSteps ticketSteps = new TicketSteps();
    ConsultaDeTasasSteps consultaDeTasasSteps = new ConsultaDeTasasSteps();
    BotonesGenericosPage botonesGenericosPage = new BotonesGenericosPage();
    BotonesGenericosSteps botonesGenericosSteps = new BotonesGenericosSteps();
    UtilsGP utilsGP = new UtilsGP();
    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0042-BSF: Alta de PF Tradicional en $ a 30 días")
    public void TC0042BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uTranspull1);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickPlazosFijos();
            plazosFijosSteps.clickSolicitarPlazoFijo();
            solicitarPlazoFijoSteps.clickSelectorTipoPF();
            solicitarPlazoFijoSteps.keyDownToText("Plazo Fijo Tradicional en Pesos");
            solicitarPlazoFijoSteps.setImporteGenerico("120000");
            solicitarPlazoFijoSteps.setPlazoEnDias("30");
            solicitarPlazoFijoSteps.clickSelectorCuentaDebito();
            solicitarPlazoFijoSteps.keyDownXParametrosGenerico(0, "");
            solicitarPlazoFijoSteps.clickSelectorAccionVencimiento();
            solicitarPlazoFijoSteps.keyDownToText("al vencimiento");
            solicitarPlazoFijoSteps.clickContinuarGenerico();
            solicitarPlazoFijoSteps.clickCheckTerminosCondicionesGenerico();
            solicitarPlazoFijoSteps.clickConfirmarGenerico();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0043-BSF: Alta de PF UVA $ 350 días ")
    public void TC0043BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uPerezR);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickPlazosFijos();
            plazosFijosSteps.clickSolicitarPlazoFijo();
            solicitarPlazoFijoSteps.clickSelectorTipoPF("Plazo Fijo UVA");
            solicitarPlazoFijoSteps.setImporteGenerico("15000");
            solicitarPlazoFijoSteps.setPlazoEnDias("350");
            solicitarPlazoFijoSteps.clickSelectorCuentaDebito();
            solicitarPlazoFijoSteps.keyDownXParametrosGenerico(0, "");
            solicitarPlazoFijoSteps.clickSelectorAccionVencimiento();
            solicitarPlazoFijoSteps.keyDownToText("al vencimiento");
            solicitarPlazoFijoSteps.clickContinuarGenerico();
            solicitarPlazoFijoSteps.clickCheckTerminosCondicionesGenerico();
            solicitarPlazoFijoSteps.clickConfirmarGenerico();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Media.class})
    @DisplayName("TC-0044-BSF: PF - Consulta de Tasas Tipo - Tradicional en Pesos")
    public void TC0044BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uTranspull4);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickPlazosFijos();
            plazosFijosSteps.clickConsultaTasas();
            consultaDeTasasSteps.validarTituloPageGenerico("Consulta de Tasas");
            consultaDeTasasSteps.clickSelectorTipoPlazoFijo();
            consultaDeTasasSteps.keyDownXParametrosGenerico(2, "Plazo Fijo Tradicional en Pesos");
            consultaDeTasasSteps.clickSelectorTipoConsulta();
            consultaDeTasasSteps.keyDownXParametrosGenerico(0,"Monto");
            consultaDeTasasSteps.setImporteGenerico("120000");
            consultaDeTasasSteps.clickBuscar();
            consultaDeTasasSteps.validarPlazoDesde();
            consultaDeTasasSteps.validarPlazoHasta();
            consultaDeTasasSteps.validarTasaTNA();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Media.class})
    @DisplayName("TC-0045-BSF: PF - Consulta de Tasas - Tipo - Tradicional en Dólares")
    public void TC0045BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uTranspull4);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickPlazosFijos();
            plazosFijosSteps.clickConsultaTasas();
            consultaDeTasasSteps.validarTituloPageGenerico("Consulta de Tasas");
            consultaDeTasasSteps.clickSelectorTipoPlazoFijo();
            consultaDeTasasSteps.keyDownXParametrosGenerico(1, "Plazo Fijo Tradicional en D");
            consultaDeTasasSteps.clickSelectorTipoConsulta();
            consultaDeTasasSteps.keyDownXParametrosGenerico(0,"Monto");
            consultaDeTasasSteps.setImporteGenerico("120000");
            consultaDeTasasSteps.clickBuscar();
            consultaDeTasasSteps.validarPlazoDesde();
            consultaDeTasasSteps.validarPlazoHasta();
            consultaDeTasasSteps.validarTasaTNA();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }

    }
    @Test
    @DisplayName("TC-0046 BSF - PF - Consulta de Tasas - Tipo - UVA")
    public void TC0046BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uVolpatomonica);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickPlazosFijos();
            plazosFijosSteps.clickConsultaTasas();
            consultaDeTasasSteps.validarTituloPageGenerico("Consulta de Tasas");
            consultaDeTasasSteps.clickSelectorTipoPlazoFijo();
            consultaDeTasasSteps.keyDownXParametrosGenerico(3, "Plazo Fijo UVA");
            consultaDeTasasSteps.clickSelectorTipoConsulta();
            consultaDeTasasSteps.keyDownXParametrosGenerico(0,"Monto");
            consultaDeTasasSteps.setImporteGenerico("120000");
            consultaDeTasasSteps.clickBuscar();
            consultaDeTasasSteps.validarPlazoDesde();
            consultaDeTasasSteps.validarPlazoHasta();
            consultaDeTasasSteps.validarTasaTNA();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Media.class})
    @DisplayName("TC-0047-BSF - PF - Consulta de Tasas - Tipo - UVA Precancelable")
    public void TC0047BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uTranspull4);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickPlazosFijos();
            plazosFijosSteps.clickConsultaTasas();
            consultaDeTasasSteps.validarTituloPageGenerico("Consulta de Tasas");
            consultaDeTasasSteps.clickSelectorTipoPlazoFijo();
            consultaDeTasasSteps.keyDownXParametrosGenerico(0, "Plazo Fijo UVA Precancelable");
            consultaDeTasasSteps.clickSelectorTipoConsulta();
            consultaDeTasasSteps.keyDownXParametrosGenerico(0,"Monto");
            consultaDeTasasSteps.setImporteGenerico("120000");
            consultaDeTasasSteps.clickBuscar();
            consultaDeTasasSteps.validarPlazoDesde();
            consultaDeTasasSteps.validarPlazoHasta();
            consultaDeTasasSteps.validarTasaTNA();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Criticos.class})
    @DisplayName("TC-12974-BSF INVERSIONES - Plazo Fijo UVA Precancelable")
    public void TC12974BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uPerezR);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickPlazosFijos();
            plazosFijosSteps.clickSolicitarPlazoFijo();
            solicitarPlazoFijoSteps.clickSelectorTipoPF();
            solicitarPlazoFijoSteps.keyDownToText("Plazo Fijo UVA Precancelable");
            solicitarPlazoFijoSteps.setImporteGenerico("15000");
            solicitarPlazoFijoSteps.setPlazoEnDias("350");
            solicitarPlazoFijoSteps.clickSelectorCuentaDebito();
            solicitarPlazoFijoSteps.keyDownXParametrosGenerico(0, "");
            solicitarPlazoFijoSteps.clickSelectorAccionVencimiento();
            solicitarPlazoFijoSteps.keyDownToText( "al vencimiento");
            solicitarPlazoFijoSteps.clickContinuarGenerico();
            solicitarPlazoFijoSteps.validarSiExisteMensajeEmergente();
            solicitarPlazoFijoSteps.clickCheckTerminosCondicionesGenerico();
            solicitarPlazoFijoSteps.clickConfirmarGenerico();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test //SE APAGO EL CASO PORQUE SE ENCUENTRA ACTIVADA LA VARIABLE PLAZO FIJO(CEDIP)
    @Category({Criticos.class})
    @DisplayName("TC-12973-BSF: INVERSIONES - Plazo Fijo Tradicional en Dolares")
    public void TC12973BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uTranspull1);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickPlazosFijos();
            plazosFijosSteps.clickSolicitarPlazoFijo();
            solicitarPlazoFijoSteps.clickSelectorTipoPF();
            solicitarPlazoFijoSteps.keyDownToText("Plazo Fijo Tradicional en D");
            solicitarPlazoFijoSteps.setImporteGenerico("1000");
            solicitarPlazoFijoSteps.setPlazoEnDias("30");
            solicitarPlazoFijoSteps.clickSelectorCuentaDebito();
            solicitarPlazoFijoSteps.keyDownToText("2609");
            solicitarPlazoFijoSteps.clickSelectorAccionVencimiento();
            solicitarPlazoFijoSteps.keyDownToText( "al vencimiento");
            solicitarPlazoFijoSteps.clickContinuarGenerico();
            solicitarPlazoFijoSteps.clickCheckTerminosCondicionesGenerico();
            solicitarPlazoFijoSteps.clickConfirmarGenerico();
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
