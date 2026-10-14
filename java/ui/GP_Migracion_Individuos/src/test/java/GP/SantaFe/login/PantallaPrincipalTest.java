package GP.SantaFe.login;

import GP.Tags.Alta;
import GP.Tags.Baja;
import GP.Tags.Media;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import steps.login.PantallaPrincipalSteps;
import steps.login.LoginSteps;

import java.io.IOException;

import static db.ConexionDB.cerrarConexion;

public class PantallaPrincipalTest {
    PantallaPrincipalSteps pantallaPrincipal = new PantallaPrincipalSteps();
    LoginSteps loginSteps = new LoginSteps();

    @Test
    @Category({Baja.class})
    @DisplayName("TC-0152-BSF: PANTALLA PRINCIPAL - Ingresar - Acceso directo 'Tienda.Clic'")
    public void TC0152BSF() {
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
    @DisplayName("TC-0154-BSF: PANTALLA PRINCIPAL - Ingresar - Acceso directo 'Teléfonos Utiles'")
    public void TC0154BSF() {
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
    @DisplayName("TC-0156-BSF: PANTALLA PRINCIPAL - Ingresar - Acceso directo '¿No sos cliente aún? Registrarme'")
    public void TC0156BSF() {
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
    @DisplayName("TC-0159-BSF: PANTALLA PRINCIPAL - Ingresar - Enlace Términos y Condiciones")
    public void TC0159BSF() {
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
    @DisplayName("TC-0161-BSF: PANTALLA PRINCIPAL - Ingresar - Enlace 'Información de seguridad'")
    public void TC0161BSF() {
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
    public void teardown() throws IOException, InterruptedException {
        global.practia.jemeinsamstark.web.core.WebDriver.closeDriver();
        cerrarConexion();
    }
}