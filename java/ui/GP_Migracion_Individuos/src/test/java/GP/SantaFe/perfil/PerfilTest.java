package GP.SantaFe.perfil;

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

import static db.ConexionDB.cerrarConexion;
import static utils.Constantes.*;
import static utils.UtilsGP.generarString;

public class PerfilTest {
    LoginSteps loginSteps = new LoginSteps();
    LoginAPI loginAPI = new LoginAPI();
    MenuSuperiorSteps menuSuperiorSteps = new MenuSuperiorSteps();
    PerfilSteps perfilSteps = new PerfilSteps();
    ModificarPerfilSteps modificarPerfilSteps = new ModificarPerfilSteps();
    DobleFactorSteps dobleFactorSteps = new DobleFactorSteps();
    ConfigurarNotificacionesSteps configurarNotificacionesSteps = new ConfigurarNotificacionesSteps();
    ProductosFavoritosSteps productosFavoritosSteps = new ProductosFavoritosSteps();
    BotonesGenericosPage botonesGenericosPage = new BotonesGenericosPage();
    BotonesGenericosSteps botonesGenericosSteps = new BotonesGenericosSteps();
    UtilsGP utilsGP = new UtilsGP();

    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0001-BSF: Cambio de Teléfono - Cambio de teléfono exitoso")
    public void TC0001BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uTester1);
            menuSuperiorSteps.clickPerfil();
            perfilSteps.clickMiPerfil();
            perfilSteps.validarTituloPageGenerico("Mi Perfil");
            perfilSteps.clickModificarTelefono();
            modificarPerfilSteps.validarTituloPageGenerico("Modificar tel");
            modificarPerfilSteps.clickIconoEditar();
            modificarPerfilSteps.setCodArea("11");
            modificarPerfilSteps.setTelefono("62133031");
            modificarPerfilSteps.clickModificarGenerico();
            dobleFactorSteps.codigoSegundoFactorParaCambioTelefono();
            dobleFactorSteps.codigoVerificacionTelefono("1162133031");
            //POSCONDICION
            perfilSteps.setearNumeroTelefonoOriginalAPI();
            modificarPerfilSteps.validarCambioTelefono();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }

    }
    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0002-BSF: Cambio de Correo Electrónico - Cambio de correo electrónico exitoso")
    public void TC0002BSF() {
        try {
            String mailActual = "pruebastechnisysqa@gmail.com";
            String nuevoMail = "gpautomation@gmail.com";

            loginSteps.iniciarSessionAlEscritorio(uTranspull1);
            menuSuperiorSteps.clickPerfil();
            perfilSteps.clickMiPerfil();
            perfilSteps.validarTituloPageGenerico("Mi Perfil");
            perfilSteps.clickModificarCorreo();
            modificarPerfilSteps.validarTituloPageGenerico("Modificar Correo");
            modificarPerfilSteps.clickIconoEditar();
            modificarPerfilSteps.setEditarCorreo(nuevoMail);
            modificarPerfilSteps.clickModificarGenerico();
            dobleFactorSteps.codigoSegundoFactor();
            dobleFactorSteps.codigoVerificacionEmail(nuevoMail);

            //POSCONDICION
            perfilSteps.setearEmailOriginalAPI();
            modificarPerfilSteps.validarCambioEmail();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    //@Test//USUARIO PROPIO
    @Category({Criticos.class})
    @DisplayName("TC-0003-BSF: Cambio de clave - Modificar psw de cuenta dentro del Perfil de la cuenta")
    public void TC0003BSF() {
        try {
            String usuario = uBsfindconi23;
            String claveActual = utilsGP.consultaDB("usuarios",usuario,"password");
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
            //dobleFactorSteps.codigoSegundoFactorPassword();
            modificarPerfilSteps.validaNotificacionEmergenteGenerico("Transaccion realizada con exito, por favor espere...");
            loginSteps.clickIngresarStartPage();
            loginSteps.setUsuario(usuario);
            loginSteps.clickContinuarGenerico();
            loginSteps.setPassword(nuevaClave);
            loginSteps.clickIngresarLogin();
            loginSteps.verificarIngreso();
            UtilsGP.actualizarDB("usuarios", usuario, "password", nuevaClave);
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }

    }
    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0004-BSF: Cambio domicilio")
    public void TC0004BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uRuattar);
            menuSuperiorSteps.clickPerfil();
            perfilSteps.clickMiPerfil();
            perfilSteps.validarTituloPageGenerico("Mi Perfil");
            perfilSteps.clickModificarDomicilio();
            modificarPerfilSteps.validarTituloPageGenerico("Modificar Domicilio");
            modificarPerfilSteps.clickDomicilio();
            modificarPerfilSteps.keyDownXParametrosGenerico(1, "Modificar domicilio");
            modificarPerfilSteps.setCalle(modificarPerfilSteps.generarString(8, "abcdefghis"));
            modificarPerfilSteps.setNumero(generarString(2, "54454511"));
            modificarPerfilSteps.setPiso(generarString(1, "21556"));
            modificarPerfilSteps.setApartamento((generarString(1, "abcdefjh")));
            modificarPerfilSteps.clickProvincia();
            modificarPerfilSteps.keyDownToText("CORRIENTES");
            modificarPerfilSteps.clickLocalidad();
            modificarPerfilSteps.keyDownXParametrosGenerico(0, "LA CRUZ");
            modificarPerfilSteps.clickCodigoPostal();
            modificarPerfilSteps.keyDownXParametrosGenerico(0, "3346");
            modificarPerfilSteps.guardarDireccion();
            modificarPerfilSteps.clickContinuarGenerico();
           // dobleFactorSteps.codigoSegundoFactorOtroMetodo(uRuattar);
            //VALIDACION
            menuSuperiorSteps.clickPerfil();
            perfilSteps.clickMiPerfil();
            perfilSteps.clickModificarDomicilio();
            modificarPerfilSteps.validarTituloPageGenerico("Modificar Domicilio");
            modificarPerfilSteps.validarDomicilio();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Media.class})
    @DisplayName("TC-0035-BSF: Cambio de sello/avatar de seguridad - Cambio de avatar")
    public void TC0035BSF() {
        try {
            int idImagenActual;
            //PRECONDICION TOMAR ID IMAGEN ACTUAL
            loginSteps.iniciarSessionAlEscritorio(uTranspull4);
            idImagenActual = loginSteps.obtenerIdImagenPerfilUsuario(uTranspull4);

            //CASO
            menuSuperiorSteps.clickPerfil();
            perfilSteps.clickMiPerfil();
            perfilSteps.validarTituloPageGenerico("Mi Perfil");
            perfilSteps.clickCambiarImagen();
            modificarPerfilSteps.cambiarImagenAvatar(idImagenActual);
            dobleFactorSteps.codigoSegundoFactor();
            perfilSteps.compararIdImagen(uTranspull4,idImagenActual);
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Media.class})
    @DisplayName("TC-0101-BSF: Perfil - Config dispositivos push")
    public void TC0101BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uTranspull4);
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
    @DisplayName("TC-0102-BSF: Perfil - Config. Notificaciones")
    public void TC0102BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uTranspull4);
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
    @DisplayName("TC-0103-BSF: Perfil - Config. Productos favoritos")
    public void TC0103BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uTranspull4);
            menuSuperiorSteps.clickPerfil();
            perfilSteps.clickMiPerfil();
            perfilSteps.validarTituloPageGenerico("Mi Perfil");
            perfilSteps.clickProductosFavoritos();
            productosFavoritosSteps.clickCorazonPintadoODespintado("Caja de Ahorro $ 000021333006");
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
            global.practia.jemeinsamstark.web.core.WebDriver.getInstance().navigate().refresh();
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
