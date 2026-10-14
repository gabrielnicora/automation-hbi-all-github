package GP.EntreRios.login;

import GP.Tags.Alta;
import GP.Tags.Baja;
import GP.Tags.Criticos;
import db.ConexionDB;
import io.qameta.allure.Allure;
import io.qameta.allure.junit4.DisplayName;
import org.junit.*;
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
import utils.Constantes;
import utils.TestCaseData;
import utils.UtilsGP;

import java.io.IOException;

import static utils.Constantes.*;

public class LoginTest {
    LoginSteps loginSteps = new LoginSteps();
    MenuLateralSteps menuLateralSteps = new MenuLateralSteps();
    DobleFactorSteps dobleFactorSteps = new DobleFactorSteps();
    ValidacionDeIdentidadSteps validacionDeIdentidadSteps = new ValidacionDeIdentidadSteps();
    MenuSuperiorSteps menuSuperiorSteps = new MenuSuperiorSteps();
    PerfilSteps perfilSteps = new PerfilSteps();
    ModificarPerfilSteps modificarPerfilSteps = new ModificarPerfilSteps();
    YopmailSteps yopmailSteps = new YopmailSteps();
    UtilsGP utilsGP = new UtilsGP();
    BotonesGenericosPage botonesGenericosPage = new BotonesGenericosPage();
    BotonesGenericosSteps botonesGenericosSteps = new BotonesGenericosSteps();
    TestCaseData testCaseData = new TestCaseData();
    @BeforeClass
    public static void extraerDatosBD(){
        System.out.println("============= BEFORE CLASS ==========================");
        if(Constantes.usuarios == null){
            System.out.println("============= Array de datos vacio extraer desde la BD ==============");
            Constantes.usuarios = ConexionDB.extraerUsuariosDB();
            System.out.println("============= se completo el array de datos ==========================");
        }else {
            System.out.println("================ EL ARRAY CONTIENE DATOS ===================");
        }
    }

    //@Test APAGADOS A LA ESPERA DE USUARIOS PROPIOS - NECESITA VALIDACION DE IDENTIDAD PARA CONTINUAR
    @Category({Criticos.class})
    @DisplayName("TC-0005-BERSA: Blanqueo de contraseña autogestión")
    public void TC0005BERSA() {
        try {
            String user = uUattesthb14;
            String pass = "Hakuna31";
            loginSteps.waitGP();
            loginSteps.clickIngresarStartPage();
            loginSteps.setUsuario(user);
            loginSteps.clickContinuarGenerico();
            loginSteps.clickBlanquearContrasenia();
            //obleFactorSteps.codigoVerificacionBlanqueo();
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
            loginSteps.validarTituloPageGenerico("Hola");
            utilsGP.actualizarDB("usuarios",user,"password",pass);
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Alta.class})
    @DisplayName("TC-0017-BERSA: Autenticación con usuario - Login Exitoso")
    public void TC0017BERSA(){
        try {
            loginSteps.iniciarSesion(uUattesthb14);
            menuLateralSteps.clickInicio();
            loginSteps.validarTituloPageGenerico("Hola COMDV, RVFDU DQLBAL");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0048-BERSA: Recupero de Usuario - Recuperar Usuario")
    public void TC0048BERSA() {
        try {
            testCaseData.cargaDeDatosTest(uUattesthb14);
            String mailActual = "practiagp1@gmail.com";
            String user = uUattesthb14;
            String pass = testCaseData.password;
            loginSteps.waitGP();
            loginSteps.clickIngresarStartPage();
            loginSteps.setUsuario(user);
            loginSteps.clickRecuperarUsuario();
            //loginSteps.setNroDocumento("17597399");
            loginSteps.clickContinuarGenerico();
            loginSteps.validarTituloPageGenerico("Tu solicitud");
            loginSteps.validarTituloPageGenerico("Enviamos tu usuario a");
            loginSteps.loginForAPI(uUattesthb14);
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
    @DisplayName("TC-0151-BERSA: PANTALLA PRINCIPAL - Ingresar - Loguearse con teclado virtual")
    public void TC0151BERSA() {
        try {
            //loginSteps.iniciarSesionTecladoVirtual(u11107010, );
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
        //cerrarConexion();
    }
}
