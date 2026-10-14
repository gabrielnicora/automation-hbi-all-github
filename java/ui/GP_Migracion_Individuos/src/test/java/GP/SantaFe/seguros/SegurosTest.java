package GP.SantaFe.seguros;

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
import steps.seguros.SegurosSteps;
import utils.UtilsGP;

import java.io.IOException;

import static db.ConexionDB.cerrarConexion;
import static utils.Constantes.u11107010;
import static utils.Constantes.uDiazleon;


public class SegurosTest {
    LoginSteps loginSteps = new LoginSteps();
    MenuLateralSteps menuLateralSteps = new MenuLateralSteps();
    SegurosSteps segurosSteps = new SegurosSteps();
    BotonesGenericosPage botonesGenericosPage = new BotonesGenericosPage();
    BotonesGenericosSteps botonesGenericosSteps = new BotonesGenericosSteps();
    UtilsGP utilsGP = new UtilsGP();
    @Test //sin usuarios con seguros
    @Category({Alta.class})
    @DisplayName("TC-0196-BSF: Seguros - Validar cards de Seguros disponibles")
    public void TC0196BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uDiazleon);
            menuLateralSteps.clickSeguros();
            segurosSteps.validarTituloPageGenerico("Seguros");
            segurosSteps.validacionDeCardsSeguros();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test //sin usuarios con seguros
    @Category({Media.class})
    @DisplayName("TC-0197-BSF: Seguros - Ingresar a un seguro - Validar detalle de seguro")
    public void TC0197BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uDiazleon);
            menuLateralSteps.clickSeguros();
            segurosSteps.validarTituloPageGenerico("Seguros");
            segurosSteps.clickCardSeguroNotebook();
            segurosSteps.validacionDetalleSeguroNotebook();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test //sin usuarios con seguros
    @Category({Baja.class})
    @DisplayName("TC-0198-BSF: Seguros - Ingresar a un seguro - Validar boton Continuar disponible")
    public void TC0198BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uDiazleon);
            menuLateralSteps.clickSeguros();
            segurosSteps.validarTituloPageGenerico("Seguros");
            segurosSteps.clickCardSeguroNotebook();
            segurosSteps.validarBotonContinuarDisponible();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test //sin usuarios con seguros
    @Category({Baja.class})
    @DisplayName("TC-0199-BSF: Seguros - Ingresar a un seguro - Validar boton Cancelar disponible")
    public void TC0199BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uDiazleon);
            menuLateralSteps.clickSeguros();
            segurosSteps.validarTituloPageGenerico("Seguros");
            segurosSteps.clickCardSeguroNotebook();
            segurosSteps.validarBotonCancelarDisponible();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test //sin usuarios con seguros
    @Category({Media.class})
    @DisplayName("TC-0200-BSF: Seguros - Ingresar a un seguro - Descargar resumen de contrato ")
    public void TC0200BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uDiazleon);
            menuLateralSteps.clickSeguros();
            segurosSteps.validarTituloPageGenerico("Seguros");
            segurosSteps.clickCardSeguroNotebook();
            segurosSteps.clickDescargarResumenContrato();
            segurosSteps.clickAceptarGenerico();
            segurosSteps.validarFragmentoURL("goanywhere");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Alta.class})
    @DisplayName("TC-0201-BSF: Seguros - Validar cards de seguros activos")
    public void TC0201BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uDiazleon);
            menuLateralSteps.clickSeguros();
            segurosSteps.validarTituloPageGenerico("Seguros");
            segurosSteps.validarTituloPageGenerico("Seguros activos");
            segurosSteps.validacionSeguroActivo();

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test //sin usuarios con oferta de seguros
    @Category({Criticos.class})
    @DisplayName("TC-12928-BSF: SEGUROS - Alta de seguros  - Acceso por Seguro - Menú lateral - Solicitud  y validación de pantalla")
    public void TC12928BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uDiazleon);
            menuLateralSteps.clickSeguros();
            segurosSteps.validarTituloPageGenerico("Seguros");
            segurosSteps.clickCardSeguroNotebook();
            segurosSteps.validacionDetalleSeguroNotebook();
            segurosSteps.clickContinuarGenerico();
            segurosSteps.clickCheckTerminosCondicionesGenerico();
            segurosSteps.validarBotonConfirmar();
            segurosSteps.validarBotonCancelarDisponible();
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
