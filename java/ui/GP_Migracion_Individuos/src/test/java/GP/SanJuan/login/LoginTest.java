package GP.SanJuan.login;

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
    UtilsGP utilsGP = new UtilsGP();

    BotonesGenericosPage botonesGenericosPage = new BotonesGenericosPage();
    BotonesGenericosSteps botonesGenericosSteps = new BotonesGenericosSteps();

    //APAGADOS A LA ESPERA DE USUARIOS PROPIOS //@Test
    @Category({Criticos.class})
    @DisplayName("TC-0005-BSJ: Blanqueo de contraseña autogestión")
    public void TC0005BSJ() {
        try {
            String user = uCuyo2021;
            String pass = "Hakuna29";
            loginSteps.waitGP();
            loginSteps.clickIngresarStartPage();
            loginSteps.setUsuario(user);
            loginSteps.clickContinuarGenerico();
            loginSteps.clickBlanquearContrasenia();
           //dobleFactorSteps.codigoVerificacionBlanqueo();
            validacionDeIdentidadSteps.setDigitosTarjeta("7504");
            validacionDeIdentidadSteps.clickPin();
            validacionDeIdentidadSteps.setPinPorNro("1");
            validacionDeIdentidadSteps.setPinPorNro("1");
            validacionDeIdentidadSteps.setPinPorNro("1");
            validacionDeIdentidadSteps.setPinPorNro("1");
            validacionDeIdentidadSteps.clickValidarGenerico();
            loginSteps.setPassword(pass);
            loginSteps.setRepetirPassword(pass);
            loginSteps.clickConfirmarGenerico();
            //VALIDA BLAQUEO
            loginSteps.validarTituloPageGenerico("Felicitaciones");
            loginSteps.clickBotonIniciarSesion();
            loginSteps.validarUsuarioCargado();
            loginSteps.setUsuario(user);
            loginSteps.clickContinuarGenerico();
            loginSteps.setPassword(pass);
            loginSteps.clickIngresarLogin();
            menuLateralSteps.clickInicio();
            loginSteps.validarTituloPageGenerico("Hola GONCDOHC, MXDQ DQGEL");
            utilsGP.actualizarDB("usuarios",user,"password",pass);
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Alta.class})
    @DisplayName("TC-0017-BSJ: Autenticación con usuario - Login Exitoso")
    public void TC0017BSJ() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uCuyo2021);
            menuLateralSteps.clickInicio();
            loginSteps.validarTituloPageGenerico("Hola ABAG, MXDQ PDQUEL");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0048-BSJ: Recupero de Usuario - Recuperar Usuario")
    public void TC0048BSJ() throws InterruptedException, SQLException {
        try {
            String randomStr = RandomStringUtils.randomAlphabetic(4);
            String mailNuevo = randomStr+"gpa@yopmail.com";
            String mailActual = "practiagp1@gmail.com";
            String user = uGdrio_12;
            String pass = utilsGP.consultaDB("usuarios", user, "password");
            //TEST
            loginSteps.waitGP();
            loginSteps.clickIngresarStartPage();
            loginSteps.setUsuario(user);
            loginSteps.clickRecuperarUsuario();
            //loginSteps.setNroDocumento("23638333");
            loginSteps.clickContinuarGenerico();
            loginSteps.validarTituloPageGenerico("Tu solicitud");
            loginSteps.validarTituloPageGenerico("Enviamos tu usuario a");
            loginSteps.loginForAPI(uGdrio_12);
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
    @DisplayName("TC-0151-BSJ: PANTALLA PRINCIPAL - Ingresar - Loguearse con teclado virtual")
    public void TC0151BSJ() {
        try {
            //loginSteps.iniciarSesionTecladoVirtual(uCarmend, );
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
