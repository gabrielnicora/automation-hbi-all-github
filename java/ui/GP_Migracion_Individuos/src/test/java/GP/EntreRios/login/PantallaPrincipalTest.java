package GP.EntreRios.login;

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
import steps.login.PantallaPrincipalSteps;
import steps.login.LoginSteps;
import utils.UtilsGP;

import java.io.IOException;

import static db.ConexionDB.cerrarConexion;

public class PantallaPrincipalTest {
    PantallaPrincipalSteps pantallaPrincipal = new PantallaPrincipalSteps();
    LoginSteps loginSteps = new LoginSteps();
    BotonesGenericosSteps botonesGenericosSteps = new BotonesGenericosSteps();
    BotonesGenericosPage botonesGenericosPage = new BotonesGenericosPage();
    UtilsGP utilsGP = new UtilsGP();


    @Test
    @Category({Baja.class})
    @DisplayName("TC-0152-BERSA: PANTALLA PRINCIPAL - Ingresar - Acceso directo 'Tienda.Clic'")
    public void TC0152BERSA() {
        try {
            loginSteps.waitGP();
            loginSteps.clickIngresarStartPage();
            pantallaPrincipal.btnTiendaClic();
            pantallaPrincipal.TiendaClicTab();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Baja.class})
    @DisplayName("TC-0154-BERSA: PANTALLA PRINCIPAL - Ingresar - Acceso directo 'Teléfonos Utiles'")
    public void TC0154BERSA() {
        try {
            loginSteps.waitGP();
            loginSteps.clickIngresarStartPage();
            pantallaPrincipal.TelefonosUtiles();
            pantallaPrincipal.TelefonosUtilesTab();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Alta.class})
    @DisplayName("TC-0156-BERSA: PANTALLA PRINCIPAL - Ingresar - Acceso directo '¿No sos cliente aún? Registrarme'")
    public void TC0156BERSA() {
        try {
            loginSteps.waitGP();
            loginSteps.clickIngresarStartPage();
            pantallaPrincipal.CrearNuevoUsuario();
            pantallaPrincipal.clickVolverFlechaGenerico();
        } catch (Exception ex){
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Media.class})
    @DisplayName("TC-0159-BERSA: PANTALLA PRINCIPAL - Ingresar - Enlace Términos y Condiciones")
    public void TC0159BERSA() {
        try {
            loginSteps.waitGP();
            loginSteps.clickIngresarStartPage();
            pantallaPrincipal.TerminosYCondiciones();
            pantallaPrincipal.TituloTerminosYcondiciones();
            pantallaPrincipal.clickVolverFlechaGenerico();
        } catch (Exception ex){
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Media.class})
    @DisplayName("TC-0161-BERSA: PANTALLA PRINCIPAL - Ingresar - Enlace 'Información de seguridad'")
    public void TC0161BERSA() {
        try {
            loginSteps.waitGP();
            loginSteps.clickIngresarStartPage();
            pantallaPrincipal.ClickInfoDeSeguridad();
            pantallaPrincipal.PreguntasFrecuentes("Seguridad para usar Home Banking / Office Banking");
        } catch (Exception ex){
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
