package GP.SanJuan.mensajes;

import GP.Tags.Alta;
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
import steps.login.LoginSteps;
import steps.mensaje.MensajeSteps;
import steps.menusuperior.MenuSuperiorSteps;
import utils.UtilsGP;

import java.io.IOException;

import static db.ConexionDB.cerrarConexion;
import static utils.Constantes.uCuyo2021;


public class MensajesTest {
    LoginSteps loginSteps = new LoginSteps();
    MenuSuperiorSteps menuSuperiorSteps = new MenuSuperiorSteps();
    MensajeSteps mensajeSteps = new MensajeSteps();
    BotonesGenericosSteps botonesGenericosSteps = new BotonesGenericosSteps();
    BotonesGenericosPage botonesGenericosPage = new BotonesGenericosPage();
    UtilsGP utilsGP = new UtilsGP();

    @Test
    @Category({Alta.class})
    @DisplayName("TC-0109-BSJ: Mensajes - Recibidos")
    public void TC0109BSJ(){
        try {
            loginSteps.iniciarSessionAlEscritorio(uCuyo2021);
            menuSuperiorSteps.clickMensaje();
            mensajeSteps.clickRecibidos();
            mensajeSteps.validarTipoMsjElegido();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Media.class})
    @DisplayName("TC-0100-BSJ: Mensajes - Enviados")
    public void TC0100BSJ() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uCuyo2021);
            menuSuperiorSteps.clickMensaje();
            mensajeSteps.clickEnviados();
            mensajeSteps.validarTipoMsjElegido();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Media.class})
    @DisplayName("TC-0098-BSJ: Mensajes - Leídos")
    public void TC0098BSJ() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uCuyo2021);
            menuSuperiorSteps.clickMensaje();
            mensajeSteps.clickLeidos();
            mensajeSteps.validarTipoMsjElegido();
        }catch (Exception ex){
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Media.class})
    @DisplayName("TC-0094-BSJ: Mensajes - Sin Leer")
    public void TC0094BSJ() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uCuyo2021);
            menuSuperiorSteps.clickMensaje();
            mensajeSteps.clickSinLeer();
            mensajeSteps.validarTipoMsjElegido();
        }catch (Exception ex){
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Alta.class})
    @DisplayName("TC-0090-BSJ: Mensajes - Eliminar msj")
    public void TC0090BSJ() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uCuyo2021);
            menuSuperiorSteps.clickMensaje();
            mensajeSteps.clickEnTresPuntos();
            mensajeSteps.clickEliminarMsj();
            mensajeSteps.validarEliminacionUltimoMensaje();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }

    }
    @Test
    @Category({Alta.class})
    @DisplayName("TC-0086-BSJ: Mensajes - Marcar no leido")
    public void TC0086BSJ() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uCuyo2021);
            menuSuperiorSteps.clickMensaje();
            //PRECONDICION
            mensajeSteps.clickSinLeer();
            mensajeSteps.clickPrimerMensaje();
            //FIN PRECONDICION
            mensajeSteps.clickLeidos();
            mensajeSteps.validarTipoMsjElegido();
            mensajeSteps.clickEnTresPuntos();
            mensajeSteps.clickMarcarComoLeido();
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
