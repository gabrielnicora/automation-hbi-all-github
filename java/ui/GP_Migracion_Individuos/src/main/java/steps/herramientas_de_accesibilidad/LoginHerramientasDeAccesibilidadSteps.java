package steps.herramientas_de_accesibilidad;

import global.practia.jemeinsamstark.web.core.WebDriver;
import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.botonesgenericos.BotonesGenericosPage;
import pages.doblefactor.DoblefactorPage;
import pages.login.LoginPage;
import steps.botonesgenericos.BotonesGenericosSteps;
import utils.UtilsGP;

import java.nio.charset.StandardCharsets;

public class LoginHerramientasDeAccesibilidadSteps extends BotonesGenericosSteps {
    Actions actions = new Actions(WebDriver.getInstance());
    LoginPage loginPage = new LoginPage();
    BotonesGenericosPage botonesGenericosPage = new BotonesGenericosPage();
    DoblefactorPage doblefactorPage = new DoblefactorPage();
    WebDriverWait wait = new WebDriverWait(global.practia.jemeinsamstark.web.core.WebDriver.getInstance(), 30);

    @Step("Validar que sea visible el input usuario")
    public void validarInputUsuario() throws InterruptedException {
        Assert.assertTrue(loginPage.getInputUser().isDisplayed());
    }

    @Step("Validar que sea visible el boton Ingresar")
    public void validarBotonIngresar() throws InterruptedException {
        Assert.assertTrue(loginPage.getIngresarStartPage().isDisplayed());
    }

    @Step("Validar los elementos de la pantalla Login sean visibles")
    public void validarPantallaLogin() throws InterruptedException {
        Assert.assertTrue("El campo de entrada de usuario no es visible.", loginPage.getInputUser().isEnabled());
        Assert.assertTrue("El botón Continuar no es visible.", botonesGenericosPage.getContinuar().isEnabled());
        Assert.assertTrue("El botón Recuperar Usuario no es visible.", loginPage.getRecuperarUsuario().isEnabled());
        Assert.assertTrue("El botón Desbloquear Usuario no es visible.", loginPage.getDesbloquearUsuario().isEnabled());
        Assert.assertTrue("El botón Crear Nuevo Usuario no es visible.", loginPage.getCrearNuevoUsuario().isEnabled());
    }

    @Step("Validar elementos de la pantalla de Crear usuario")
    public void validarPantallaCrearUsuario() throws InterruptedException {
        Assert.assertTrue("El campo CUIL no es visible.", loginPage.getInputCuil().isEnabled());
        Assert.assertTrue("El boton Tengo el codigo de invitacion no es visible.", loginPage.getBotonTengoElCodigoInvitacion().isEnabled());
        Assert.assertTrue("El boton Aceptar no es visible.", botonesGenericosPage.getAceptar().isEnabled());

    }

    @Step("Validar que sea visible el boton Volver")
    public void validarBotonVolver() throws InterruptedException {
        Assert.assertTrue(botonesGenericosPage.getVolver().isDisplayed());
        Assert.assertTrue(botonesGenericosPage.getVolver().isEnabled());
    }

    @Step("Validar que sea visible el texto 'No sos cliente aun, registrate'")
    public void validarTextoNoSosClienteAun() throws InterruptedException {
        String elemento = "¿No sos cliente aún?";
        String text = new String(elemento.getBytes(),
                StandardCharsets.UTF_8);
        Assert.assertTrue("", loginPage.text(text).isDisplayed());
    }

    @Step("Validar que sea visible el boton Volver")
    public void validarIconosDeDescargasApps() throws InterruptedException {
        if (loginPage.imagenDescargaGooglePlay().isDisplayed() && loginPage.imagenDescargaAppStore().isDisplayed()) {
            System.out.println("Campos y botones validados con exito");
        } else {
            Assert.fail("Alguno de los campos o botones no es visible");
        }
    }

    @Step("Iniciar sesion con usuario y contrasenia")
    public void ingresarUsuarioContrasenia(String texto) throws InterruptedException {
        Thread.sleep(2000);
        actions.sendKeys(texto).build().perform();
    }

    @Step("Validar boton blanquear contrasenia")
    public void validarBotonBlanquearContrasenia() throws InterruptedException {
        if (loginPage.getBlanquearContrasenia().isDisplayed() && loginPage.getBlanquearContrasenia().isEnabled()) {
            System.out.println("Campos y botones validados con exito");
        } else {
            Assert.fail("Alguno de los campos o botones no es visible");
        }

    }

    @Step("Validacion de Boton Desbloquear Usuario")
    public void validarBotonDesbloquearUsuario() throws InterruptedException {
        if (loginPage.getDesbloquearUsuario().isDisplayed() && loginPage.getDesbloquearUsuario().isEnabled()) {
            System.out.println("Campos y botones validados con exito");
        } else {
            Assert.fail("Alguno de los campos o botones no es visible");
        }

    }

    @Step("Validacion de pantalla Blanquear Contrasenia")
    public void validarPantallaBlanquearContrasenia() throws InterruptedException {
        if (doblefactorPage.getCodigoVerificacion().isDisplayed() && botonesGenericosPage.getContinuar().isEnabled() && loginPage.getBotonModificarDatos().isEnabled()) {
            System.out.println("Campos y botones validados con exito");
        } else {
            Assert.fail("Alguno de los campos o botones no es visible");
        }
    }

    @Step("Validacion de pantalla Desbloquear Usuario")
    public void validarPantallaDesbloquearUsuario() throws InterruptedException {
        if (loginPage.getInputUser().isDisplayed() && loginPage.getInputPassword().isDisplayed() && botonesGenericosPage.getContinuar().isDisplayed() && botonesGenericosPage.getContinuar().isEnabled()) {
            System.out.println("Campos y botones validados con exito");
        } else {
            Assert.fail("Alguno de los campos o botones no es visible");
        }
    }
    @Step("Validacion de pantalla Recuperar Usuario")
    public void validarPantallaRecuperarUsuario() throws InterruptedException {
        if (loginPage.getInputDocumento().isDisplayed() &&  botonesGenericosPage.getContinuar().isDisplayed() && botonesGenericosPage.getContinuar().isEnabled()) {
            System.out.println("Campos y botones validados con exito");
        } else {
            Assert.fail("Alguno de los campos o botones no es visible");
        }

    }


}
