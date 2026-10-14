
package GP.SantaFe.login;

import GP.Tags.Alta;
import GP.Tags.Baja;
import GP.Tags.Criticos;
import io.qameta.allure.Allure;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import pages.botonesgenericos.BotonesGenericosPage;
import steps.botonesgenericos.BotonesGenericosSteps;
import steps.doblefactor.DobleFactorSteps;
import steps.login.LoginSteps;
import steps.menulateral.MenuLateralSteps;
import steps.menusuperior.MenuSuperiorSteps;
import steps.perfil.PerfilSteps;
import steps.perfil.modificarsteps.ModificarPerfilSteps;
import steps.validaciondeidentidad.ValidacionDeIdentidadSteps;
import steps.yopmail.YopmailSteps;
import utils.UtilsGP;

import java.io.IOException;
import java.sql.SQLException;

import static db.ConexionDB.cerrarConexion;
import static utils.Constantes.*;

public class LoginTest {
    LoginSteps loginSteps = new LoginSteps();
    DobleFactorSteps dobleFactorSteps = new DobleFactorSteps();
    ValidacionDeIdentidadSteps validacionDeIdentidadSteps = new ValidacionDeIdentidadSteps();
    MenuLateralSteps menuLateralSteps = new MenuLateralSteps();
    MenuSuperiorSteps menuSuperiorSteps = new MenuSuperiorSteps();
    PerfilSteps perfilSteps = new PerfilSteps();
    ModificarPerfilSteps modificarPerfilSteps = new ModificarPerfilSteps();
    YopmailSteps yopmailSteps = new YopmailSteps();
    BotonesGenericosPage botonesGenericosPage = new BotonesGenericosPage();
    BotonesGenericosSteps botonesGenericosSteps = new BotonesGenericosSteps();
    UtilsGP utilsGP = new UtilsGP();

    //@Test
    @Category({Criticos.class})
    @DisplayName("TC-0005-BSF: Blanqueo de contraseña autogestión")
    public void TC0005BSF() throws InterruptedException {
        try {
            UtilsGP utilsGP = new UtilsGP();
            String pass = utilsGP.cambioDeClave(UtilsGP.consultaDB("usuarios",uBsfindconi23,"password"));
            loginSteps.waitGP();
            loginSteps.clickIngresarStartPage();
            loginSteps.setUsuario(uBsfindconi23);
            loginSteps.clickContinuarGenerico();
            loginSteps.clickBlanquearContrasenia();
            //dobleFactorSteps.codigoVerificacionBlanqueo();
            validacionDeIdentidadSteps.setDigitosTarjeta("9006");
            validacionDeIdentidadSteps.clickPin();
            validacionDeIdentidadSteps.setPinPorNro("1");
            validacionDeIdentidadSteps.setPinPorNro("1");
            validacionDeIdentidadSteps.setPinPorNro("1");
            validacionDeIdentidadSteps.setPinPorNro("1");
            validacionDeIdentidadSteps.clickValidarGenerico();
            loginSteps.setPassword(pass);
            loginSteps.setRepetirPassword(pass);
            loginSteps.clickConfirmarGenerico();
            //VALIDA BLANQUEO
            loginSteps.validarTituloPageGenerico("Felicitaciones");
            loginSteps.clickBotonIniciarSesion();
            loginSteps.validarUsuarioCargado();
            loginSteps.setUsuario(uBsfindconi23);
            loginSteps.clickContinuarGenerico();
            loginSteps.setPassword(pass);
            loginSteps.clickIngresarLogin();
            menuLateralSteps.clickInicio();
            loginSteps.validarTituloPageGenerico("Hola TORRES PARDO, CONSTANZA");
            UtilsGP.actualizarDB("usuarios", uBsfindconi23, "password", pass);
       } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Alta.class})
    @DisplayName("TC-0017-BSF: Autenticación con usuario - Login Exitoso")
    public void TC0017BSF() {
        try {
            loginSteps.iniciarSesion(uDiazleon);
            menuLateralSteps.clickInicio();
            loginSteps.validarTituloPageGenerico("Hola DIAZ, LEONIDES ELADIO");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0048-BSF: Recupero de Usuario - Recuperar Usuario")
    public void TC0048BSF() throws SQLException, InterruptedException {
        try {
            String randomStr = RandomStringUtils.randomAlphabetic(4);
            String mailNuevo = randomStr + "gpa@yopmail.com";
            String mailActual = "practiagp1@gmail.com";
            String user = "rolamario";
            String pass = UtilsGP.consultaDB("usuarios", user, "password");
            //TEST
            loginSteps.waitGP();
            loginSteps.clickIngresarStartPage();
            loginSteps.setUsuario(user);
            loginSteps.clickRecuperarUsuario();
            //loginSteps.setNroDocumento("08001004");
            loginSteps.clickContinuarGenerico();
            loginSteps.validarTituloPageGenerico("Tu solicitud");
            loginSteps.validarTituloPageGenerico("Enviamos tu usuario a");
            loginSteps.loginForAPI(user);
            loginSteps.validarMailRecibido(dobleFactorSteps.codigoVerificacionEmailRecuperoUsuario(),user);
            loginSteps.clickBotonIniciarSesion();
            loginSteps.clickIngresarLogin();
            loginSteps.setUsuario(user);
            loginSteps.clickContinuarLogin();
            loginSteps.setPassword(pass);
            loginSteps.clickIngresarLogin();
            loginSteps.verificarSaludo();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Baja.class})
    @DisplayName("TC-0151-BSF: PANTALLA PRINCIPAL - Ingresar - Loguearse con teclado virtual")
    public void TC0151BSF() {
        try {
            //loginSteps.iniciarSesionTecladoVirtual(uRolamario, );
            menuLateralSteps.clickInicio();
            loginSteps.verificarSaludo();
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
