package GP.EntreRios.perfil;

import GP.Tags.Baja;
import GP.Tags.Criticos;
import GP.Tags.Media;
import api.LoginAPI;
import db.ConexionDB;
import io.qameta.allure.Allure;
import io.qameta.allure.junit4.DisplayName;
import org.junit.*;
import org.junit.experimental.categories.Category;
import pages.botonesgenericos.BotonesGenericosPage;
import steps.botonesgenericos.BotonesGenericosSteps;
import steps.doblefactor.DobleFactorSteps;
import steps.login.LoginSteps;
import steps.menusuperior.MenuSuperiorSteps;
import steps.perfil.PerfilSteps;
import steps.perfil.configurar_notificaciones.ConfigurarNotificacionesSteps;
import steps.perfil.modificarsteps.ModificarPerfilSteps;
import steps.perfil.productofavoritos.ProductosFavoritosSteps;
import utils.Constantes;
import utils.MetodosAPI;
import utils.TestCaseData;
import utils.UtilsGP;

import java.io.IOException;

import static db.ConexionDB.cerrarConexion;
import static utils.Constantes.*;

public class
PerfilTest {
    LoginSteps loginSteps = new LoginSteps();
    LoginAPI loginAPI = new LoginAPI();
    MenuSuperiorSteps menuSuperiorSteps = new MenuSuperiorSteps();
    PerfilSteps perfilSteps = new PerfilSteps();
    BotonesGenericosSteps botonesGenerricosSteps = new BotonesGenericosSteps();
    ModificarPerfilSteps modificarPerfilSteps = new ModificarPerfilSteps();
    DobleFactorSteps dobleFactorSteps = new DobleFactorSteps();
    ConfigurarNotificacionesSteps configurarNotificacionesSteps = new ConfigurarNotificacionesSteps();
    ProductosFavoritosSteps productosFavoritosSteps = new ProductosFavoritosSteps();
    BotonesGenericosPage botonesGenericosPage = new BotonesGenericosPage();
    BotonesGenericosSteps botonesGenericosSteps = new BotonesGenericosSteps();
    UtilsGP utilsGP = new UtilsGP();
    MetodosAPI metodosAPI = new MetodosAPI();
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
    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0001-BERS: Cambio de Teléfono - Cambio de teléfono exitoso")
    public void TC0001BERSA() {
        try {
            testCaseData.cargaDeDatosTest(uTranspull1);
            loginSteps.iniciarSessionAlEscritorioConPass(uTranspull1,testCaseData.password);
            menuSuperiorSteps.clickPerfil();
            perfilSteps.clickMiPerfil();
            perfilSteps.validarTituloPageGenerico("Mi Perfil");
            perfilSteps.clickModificarTelefono();
            modificarPerfilSteps.validarTituloPageGenerico("Modificar tel");
            modificarPerfilSteps.clickIconoEditar();
            modificarPerfilSteps.setCodArea("55");
            modificarPerfilSteps.setTelefono("55840283");
            modificarPerfilSteps.clickModificarGenerico();
            //dobleFactorSteps.codigoSegundoFactor("email");
            dobleFactorSteps.codigoVerificacionTelefono("5555840283");//1154353254
            //TEARDOWN
            /*menuSuperiorSteps.clickPerfil();
            perfilSteps.clickMiPerfil();
            perfilSteps.validarTituloPageGenerico("Mi Perfil");
            perfilSteps.clickModificarTelefono();
            modificarPerfilSteps.clickIconoEditar();
            modificarPerfilSteps.setCodArea("11");
            modificarPerfilSteps.setTelefono("65694195");
            modificarPerfilSteps.clickModificarGenerico();
            dobleFactorSteps.codigoSegundoFactor("practiagp1@gmail.com");
            dobleFactorSteps.codigoVerificacionTelefono(uUattesthb14);
            //VERIFICACIÓN
            menuSuperiorSteps.clickPerfil();
            perfilSteps.clickMiPerfil();
            perfilSteps.validarTituloPageGenerico("Mi Perfil");
            perfilSteps.clickModificarTelefono();
            modificarPerfilSteps.validarTelefono("11 ****195");*/

            //POSCONDICION
            loginSteps.loginForAPI(uTranspull1);
            //perfilSteps.setearNumeroTelefonoOriginalAPI();
            //modificarPerfilSteps.validarCambioTelefono();

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0002-BERSA: Cambio de Correo Electrónico - Cambio de correo electrónico exitoso")
    public void TC0002BERSA() {
        try {
            String mailActual = "practiagp1@gmail.com";
            String nuevoMail = "gpautomation@yopmail.com";
            //loginSteps.iniciarSesion(uUattesthb14);
            testCaseData.cargaDeDatosTest(uTranspull1);
            loginSteps.iniciarSessionAlEscritorioConPass(uTranspull1,testCaseData.password);
            menuSuperiorSteps.clickPerfil();
            perfilSteps.clickMiPerfil();
            perfilSteps.validarTituloPageGenerico("Mi Perfil");
            perfilSteps.clickModificarCorreo();
            modificarPerfilSteps.validarTituloPageGenerico("Modificar correo electr");
            modificarPerfilSteps.clickIconoEditar();
            modificarPerfilSteps.setEditarCorreo(nuevoMail);
            modificarPerfilSteps.clickModificarGenerico();
            //dobleFactorSteps.codigoSegundoFactorOtroMetodo(uTranspull1);
            //dobleFactorSteps.codigoVerificacionEmail(nuevoMail);
            //POSCONDICION
            loginSteps.loginForAPI(uTranspull1);
            perfilSteps.setearEmailOriginalAPI();
            //modificarPerfilSteps.validarCambioEmail();

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    //APAGADOS A LA ESPERA DE USUARIOS PROPIOS - USUARIO BLOQUEADO @Test
    @Category({Criticos.class})
    @DisplayName("TC-0003-BERSA: Cambio de clave - Modificar psw de cuenta dentro del Perfil de la cuenta")
    public void TC0003BERSA() {
        try {
            String usuario = uUattesthb14;
            String claveActual = UtilsGP.consultaDB("usuarios",usuario,"password");
            String nuevaClave = utilsGP.cambioDeClave(claveActual);
            loginSteps.iniciarSessionAlEscritorio(usuario);
            menuSuperiorSteps.clickPerfil();
            perfilSteps.clickMiPerfil();
            perfilSteps.validarTituloPageGenerico("Mi Perfil");
            perfilSteps.clickModificarContrasenia();
            modificarPerfilSteps.validarTituloPageGenerico("Modificar contra");
            //FALTAN DATOS PARA CARGAR EL USUARIO
            modificarPerfilSteps.setContrasenia(claveActual);
            modificarPerfilSteps.setContrasenianew(nuevaClave);
            modificarPerfilSteps.setContraseniaConfirmar(nuevaClave);
            //dobleFactorSteps.codigoSegundoFactorPassword();
            utilsGP.actualizarDB("usuarios", usuario, "password", nuevaClave);
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0004-BERSA: Cambio domicilio")
    public void TC0004BERSA() {
        try {
            testCaseData.cargaDeDatosTest(uTranspull1);
            loginSteps.iniciarSessionAlEscritorioConPass(uTranspull1,testCaseData.password);
            menuSuperiorSteps.clickPerfil();
            perfilSteps.clickMiPerfil();
            perfilSteps.validarTituloPageGenerico("Mi Perfil");
            perfilSteps.clickModificarDomicilio();
            modificarPerfilSteps.validarTituloPageGenerico("Modificar Domicilio");
            modificarPerfilSteps.clickDomicilio();
            modificarPerfilSteps.keyDownXParametrosGenerico(1, "Modificar domicilio");
            modificarPerfilSteps.setCalle(modificarPerfilSteps.generarString(8, "abcdefghias"));
            modificarPerfilSteps.setNumero(UtilsGP.generarString(2, "54454511"));
            modificarPerfilSteps.setPiso(UtilsGP.generarString(1, "21556"));
            modificarPerfilSteps.setApartamento((UtilsGP.generarString(1, "abcploa")));
            modificarPerfilSteps.clickProvincia();
            modificarPerfilSteps.keyDownXParametrosGenerico(4, "CORRIENTES");
            modificarPerfilSteps.clickLocalidad();
            modificarPerfilSteps.keyDownXParametrosGenerico(0, "");
            modificarPerfilSteps.clickCodigoPostal();
            modificarPerfilSteps.keyDownXParametrosGenerico(0, "");
            modificarPerfilSteps.guardarDireccion();
            modificarPerfilSteps.clickContinuarGenerico();
            //dobleFactorSteps.codigoSegundoFactorOtroMetodo(uTranspull1);
            //VALIDACION
            menuSuperiorSteps.clickPerfil();
            perfilSteps.clickMiPerfil();
            perfilSteps.clickModificarDomicilio();
            modificarPerfilSteps.validarDomicilio();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Media.class})
    @DisplayName("TC-0035-BERSA: Cambio de sello/avatar de seguridad - Cambio de avatar")
    public void TC0035BERSA() {
        try {
            //PRECONDICION TOMAR ID IMAGEN ACTUAL
            loginSteps.iniciarSessionAlEscritorio(uUattesthb14);
            int idImagenActual = loginAPI.tomarIdImagen(uUattesthb14);
            //CASO
            menuSuperiorSteps.clickPerfil();
            perfilSteps.clickMiPerfil();
            perfilSteps.validarTituloPageGenerico("Mi Perfil");
            perfilSteps.clickCambiarImagen();
            modificarPerfilSteps.cambiarImagenAvatar(idImagenActual);
            //dobleFactorSteps.codigoSegundoFactorOtroMetodo(uUattesthb14);
            perfilSteps.compararIdImagen(uUattesthb14,idImagenActual);
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Media.class})
    @DisplayName("TC-0101-BERSA: Perfil - Config dispositivos push")
    public void TC0101BERSA() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uEcarina27);
            menuSuperiorSteps.clickPerfil();
            perfilSteps.clickMiPerfil();
            perfilSteps.validarTituloPageGenerico("Mi Perfil");
            perfilSteps.clickDispositivosPush();
            perfilSteps.validarTituloPageGenerico("Configurar dispositivos push");
            perfilSteps.validarTituloPageGenerico("Dispositivos configurados");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Media.class})
    @DisplayName("TC-0102-BERSA: Perfil - Config. Notificaciones")
    public void TC0102BERSA() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uEcarina27);
            menuSuperiorSteps.clickPerfil();
            perfilSteps.clickMiPerfil();
            perfilSteps.validarTituloPageGenerico("Mi Perfil");
            perfilSteps.clickNotificaciones();
            perfilSteps.validarTituloPageGenerico("Configurar notificaciones");
            configurarNotificacionesSteps.clickMensajesDelBanco();
            configurarNotificacionesSteps.validarTituloPageGenerico("Todas las notificaciones te ser");
            configurarNotificacionesSteps.validarTituloPageGenerico("Correo electr");
            configurarNotificacionesSteps.validarTituloPageGenerico("Aplicaci");
            configurarNotificacionesSteps.validarTituloPageGenerico("Mensaje de texto");
            configurarNotificacionesSteps.validacionDeSwitchActivado();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Baja.class})
    @DisplayName("TC-0103-BERSA: Perfil - Config. Productos favoritos")
    public void TC0103BERSA() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uUattesthb14);
            menuSuperiorSteps.clickPerfil();
            perfilSteps.clickMiPerfil();
            perfilSteps.validarTituloPageGenerico("Mi Perfil");
            perfilSteps.clickProductosFavoritos();
            productosFavoritosSteps.clickCorazonPintadoODespintado("Caja de Ahorro $ 006003196442");
            productosFavoritosSteps.clickContinuarGenerico();

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
