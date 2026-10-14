package GP.SanJuan.perfil;

import GP.Tags.Baja;
import GP.Tags.Criticos;
import GP.Tags.Media;
import api.LoginAPI;
import io.qameta.allure.Allure;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
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
import utils.MetodosAPI;
import utils.UtilsGP;

import java.io.IOException;
import java.sql.SQLException;

import static db.ConexionDB.cerrarConexion;
import static utils.Constantes.*;

public class PerfilTest {
    LoginSteps loginSteps = new LoginSteps();
    LoginAPI loginAPI = new LoginAPI();
    MenuSuperiorSteps menuSuperiorSteps = new MenuSuperiorSteps();
    PerfilSteps perfilSteps = new PerfilSteps();
    ModificarPerfilSteps modificarPerfilSteps = new ModificarPerfilSteps();
    DobleFactorSteps dobleFactorSteps = new DobleFactorSteps();
    UtilsGP utilsGP = new UtilsGP();
    ConfigurarNotificacionesSteps configurarNotificacionesSteps = new ConfigurarNotificacionesSteps();
    BotonesGenericosSteps botonesGenericosSteps = new BotonesGenericosSteps();
    BotonesGenericosPage botonesGenericosPage = new BotonesGenericosPage();

    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0001-BSJ: Cambio de Teléfono - Cambio de teléfono exitoso")
    public void TC0001BSJ() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uGdrio_12);//1136636902 -1162133032
            menuSuperiorSteps.clickPerfil();
            perfilSteps.clickMiPerfil();
            perfilSteps.validarTituloPageGenerico("Mi Perfil");
            perfilSteps.clickModificarTelefono();
            modificarPerfilSteps.validarTituloPageGenerico("Modificar tel");
            modificarPerfilSteps.clickIconoEditar();
            modificarPerfilSteps.setCodArea("11");
            modificarPerfilSteps.setTelefono("62133032");
            modificarPerfilSteps.clickModificarGenerico();
            dobleFactorSteps.codigoSegundoFactor();
            dobleFactorSteps.codigoVerificacionTelefono("1162133032");
            //POSCONDICION

            loginSteps.loginForAPI(uGdrio_12);
            perfilSteps.setearNumeroTelefonoOriginalAPI();
            modificarPerfilSteps.validarCambioTelefono();

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }


    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0002-BSJ: Cambio de Correo Electrónico - Cambio de correo electrónico exitoso")
    public void TC0002BSJ() throws SQLException, InterruptedException {
        try {
            String mailActual = "gpautomation@yopmail.com";
            String nuevoMail = "practiagp1@gmail.com";
            loginSteps.iniciarSessionAlEscritorio(uTranspull1);
            menuSuperiorSteps.clickPerfil();
            perfilSteps.clickMiPerfil();
            perfilSteps.validarTituloPageGenerico("Mi Perfil");
            perfilSteps.clickModificarCorreo();
            modificarPerfilSteps.validarTituloPageGenerico("Modificar correo electr");
            modificarPerfilSteps.clickIconoEditar();
            modificarPerfilSteps.setEditarCorreo(nuevoMail);
            modificarPerfilSteps.clickModificarGenerico();
            dobleFactorSteps.codigoSegundoFactor();
            dobleFactorSteps.codigoVerificacionEmail(nuevoMail);
            //POSCONDICION
            loginSteps.loginForAPI(uTranspull1);
            perfilSteps.setearEmailOriginalAPI();
            modificarPerfilSteps.validarCambioEmail();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    //APAGADOS A LA ESPERA DE USUARIOS PROPIOS @Test
    @Category({Criticos.class})
    @DisplayName("TC-0003-BSJ: Cambio de clave - Modificar psw de cuenta dentro del Perfil de la cuenta")
    public void TC0003BSJ() {
        try {
            String usuario = uCuyo2021;
            String claveActual = UtilsGP.consultaDB("usuarios",usuario,"password");
            String nuevaClave = utilsGP.cambioDeClave(claveActual);
            loginSteps.iniciarSessionAlEscritorio(usuario);
            menuSuperiorSteps.clickPerfil();
            perfilSteps.clickMiPerfil();
            perfilSteps.validarTituloPageGenerico("Mi Perfil");
            perfilSteps.clickModificarContrasenia();
            modificarPerfilSteps.validarTituloPageGenerico("Modificar contra");
            modificarPerfilSteps.setContrasenia(claveActual);
            modificarPerfilSteps.setContrasenianew(nuevaClave);
            modificarPerfilSteps.setContraseniaConfirmar(nuevaClave);
            //dobleFactorSteps.codigoSegundoFactor();
            utilsGP.actualizarDB("usuarios", usuario, "password", nuevaClave);
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0004-BSJ: Cambio domicilio")
    public void TC0004BSJ() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uTranspull1);
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
            modificarPerfilSteps.setApartamento((UtilsGP.generarString(1, "abcdefjh")));
            modificarPerfilSteps.clickProvincia();
            modificarPerfilSteps.keyDownXParametrosGenerico(4, "CORRIENTES");
            modificarPerfilSteps.clickLocalidad();
            modificarPerfilSteps.keyDownXParametrosGenerico(0, "");
            modificarPerfilSteps.clickCodigoPostal();
            modificarPerfilSteps.keyDownXParametrosGenerico(0, "");
            modificarPerfilSteps.guardarDireccion();
            modificarPerfilSteps.clickContinuarGenerico();
            dobleFactorSteps.codigoSegundoFactorOtroMetodo();
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
    @DisplayName("TC-0035-BSJ: Cambio de sello/avatar de seguridad - Cambio de avatar")
    public void TC0035BSJ() {
        try {
            //PRECONDICION TOMAR ID IMAGEN ACTUAL
            loginSteps.iniciarSessionAlEscritorio(uHOENA_2021);
            int idImagenActual = loginAPI.tomarIdImagen(uHOENA_2021);
            //CASO
            menuSuperiorSteps.clickPerfil();
            perfilSteps.clickMiPerfil();
            perfilSteps.validarTituloPageGenerico("Mi Perfil");
            perfilSteps.clickCambiarImagen();
            modificarPerfilSteps.cambiarImagenAvatar(idImagenActual);
            dobleFactorSteps.codigoSegundoFactorOtroMetodo();
            perfilSteps.compararIdImagen(uHOENA_2021,idImagenActual);
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Media.class})
    @DisplayName("TC-0101-BSJ: Perfil - Config dispositivos push")
    public void TC0101BSJ() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uCarmend);
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
    @DisplayName("TC-0102-BSJ: Perfil - Config. Notificaciones")
    public void TC0102BSJ() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uCarmend);
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
    @DisplayName("TC-0103-BSJ: Perfil - Config. Productos favoritos")
    public void TC0103BSJ() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uCarmend);
            menuSuperiorSteps.clickPerfil();
            perfilSteps.clickMiPerfil();
            perfilSteps.validarTituloPageGenerico("Mi Perfil");
            perfilSteps.clickProductosFavoritos();
            ProductosFavoritosSteps productosFavoritosSteps = new ProductosFavoritosSteps();
            productosFavoritosSteps.clickCorazonPintadoODespintado("Caja de Ahorro $ 90180707235");
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
        cerrarConexion();
    }
}
