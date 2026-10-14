package GP.SantaCruz.login;

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
    MenuLateralSteps menuLateralSteps = new MenuLateralSteps();
    MenuSuperiorSteps menuSuperiorSteps = new MenuSuperiorSteps();
    PerfilSteps perfilSteps = new PerfilSteps();
    ModificarPerfilSteps modificarPerfilSteps = new ModificarPerfilSteps();
    DobleFactorSteps dobleFactorSteps = new DobleFactorSteps();
    YopmailSteps yopmailSteps = new YopmailSteps();
    ValidacionDeIdentidadSteps validacionDeIdentidadSteps = new ValidacionDeIdentidadSteps();
    BotonesGenericosPage botonesGenericosPage = new BotonesGenericosPage();
    BotonesGenericosSteps botonesGenericosSteps = new BotonesGenericosSteps();
    private UtilsGP utilsGP = new UtilsGP();

    //APAGADOS A LA ESPERA DE USUARIOS PROPIOS - USUARIO SIN CLAVE ////Test
    //@Category({Criticos.class})
    @DisplayName("TC-0005-BSC: Blanqueo de contraseña autogestión")
    public void TC0005BSC() {
        try {
            String user = u22455772;
            String pass = "???";
            loginSteps.waitGP();
            loginSteps.clickIngresarStartPage();
            loginSteps.setUsuario(user);
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
            //VALIDA BLAQUEO
            loginSteps.validarTituloPageGenerico("Felicitaciones");
            loginSteps.clickBotonIniciarSesion();
            loginSteps.validarUsuarioCargado();
            loginSteps.setUsuario(user);
            loginSteps.clickContinuarGenerico();
            loginSteps.setPassword(pass);
            loginSteps.clickIngresarLogin();
            menuLateralSteps.clickInicio();
            loginSteps.validarTituloPageGenerico("Hola TORRES PARDO, CONSTANZA");
            UtilsGP.actualizarDB("usuarios",user,"password",pass);
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Alta.class})
    @DisplayName("TC-0017-BSC: Autenticación con usuario - Login Exitoso")
    public void TC0017BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(u22455772);
            menuLateralSteps.clickInicio();
            loginSteps.validarTituloPageGenerico("Hola SUAUHV, KHFWRU GDQIEL");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0048-BSC: Recupero de Usuario - Recuperar Usuario")
    public void TC0048BSC() throws SQLException, InterruptedException {
        try {
            String randomStr = RandomStringUtils.randomAlphabetic(4);
            String mailNuevo = randomStr+"gpa@yopmail.com";
            String mailActual = "practiagp1@gmail.com";
            String user = u26538177;
            String pass = utilsGP.consultaDB("usuarios", user, "password");
            //TEST
            loginSteps.waitGP();
            loginSteps.clickIngresarStartPage();
            loginSteps.setUsuario(user);
            loginSteps.clickRecuperarUsuario();
            //loginSteps.setNroDocumento(u26538177);
            loginSteps.clickContinuarGenerico();
            loginSteps.validarTituloPageGenerico("Tu solicitud");
            loginSteps.validarTituloPageGenerico("Enviamos tu usuario a");
            loginSteps.loginForAPI(u26538177);
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
    @DisplayName("TC-0151-BSC: PANTALLA PRINCIPAL - Ingresar - Loguearse con teclado virtual")
    public void TC0151BSC() {
        try {
            //loginSteps.iniciarSesionTecladoVirtual(u22455772, );
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
