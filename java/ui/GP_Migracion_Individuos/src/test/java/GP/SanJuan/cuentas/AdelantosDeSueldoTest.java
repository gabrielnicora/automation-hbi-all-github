package GP.SanJuan.cuentas;

import GP.Tags.Alta;
import GP.Tags.Baja;
import GP.Tags.Criticos;
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
import steps.cuentas.CuentasSteps;
import steps.cuentas.adelantosDeSueldo.AdelantosDeSueldoSteps;
import steps.cuentas.detallecuenta.DetalleCuentaSteps;
import steps.cuentas.detallecuenta.DetalleCuentaSteps;
import steps.doblefactor.DobleFactorSteps;
import steps.login.LoginSteps;
import steps.menulateral.MenuLateralSteps;
import steps.prestamos.PrestamosParaCancelarDeudasSteps;
import utils.UtilsGP;

import java.io.IOException;

import static db.ConexionDB.cerrarConexion;
import static utils.Constantes.*;

public class AdelantosDeSueldoTest {

    MenuLateralSteps menuLateralSteps = new MenuLateralSteps();
    AdelantosDeSueldoSteps adelantosDeSueldoSteps = new AdelantosDeSueldoSteps();
    LoginSteps loginSteps = new LoginSteps();
    PrestamosParaCancelarDeudasSteps prestamosSteps = new PrestamosParaCancelarDeudasSteps();
    BotonesGenericosPage botonesGenericosPage = new BotonesGenericosPage();
    BotonesGenericosSteps botonesGenericosSteps = new BotonesGenericosSteps();
    UtilsGP utilsGP = new UtilsGP();

    @Test
    @Category({Alta.class})
    @DisplayName("TC-0222-BSJ: Adelantos de Sueldo - Solicitud de Adelanto de Sueldo - Validar Pantalla completa")
    public void TC0222BSJ() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uGdrio_12);
            menuLateralSteps.clickCuentas();
            adelantosDeSueldoSteps.clickVerMasXnroCuenta("CA $ 15008057945");
            adelantosDeSueldoSteps.clickMasOpcionesGenerico();
            adelantosDeSueldoSteps.clickSolicitudAdelantoDeSueldo();
            adelantosDeSueldoSteps.validacionPantallaSolicitudAH();

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0223-BSJ: Adelantos de Sueldo - Realizar solicitud de adelanto de sueldo  - Validar pantalla de confimacion de solicitud")
    public void TC0223BSJ() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uNazarena3);
            menuLateralSteps.clickCuentas();
            adelantosDeSueldoSteps.clickVerMasXnroCuenta("CA $ 90016842907");
            adelantosDeSueldoSteps.clickMasOpcionesGenerico();
            adelantosDeSueldoSteps.clickSolicitudAdelantoDeSueldo();
            adelantosDeSueldoSteps.setCuantoNecesitas("200");
            adelantosDeSueldoSteps.clickContinuarGenerico();
            adelantosDeSueldoSteps.validacionCamposConfirmacion();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Baja.class})
    @DisplayName("TC-0225-BSJ: Adelantos de Sueldo - Realizar Solicitud de adelanto de sueldo - Cancelar operacion al presionar volver - Validar pantalla de solicitud de adelanto de sueldo")
    public void TC0225BSJ() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uGdrio_12);
            menuLateralSteps.clickCuentas();
            adelantosDeSueldoSteps.clickVerMasXnroCuenta("CA $ 15008057945");
            adelantosDeSueldoSteps.clickMasOpcionesGenerico();
            adelantosDeSueldoSteps.clickSolicitudAdelantoDeSueldo();
            adelantosDeSueldoSteps.setCuantoNecesitas("200");
            adelantosDeSueldoSteps.clickContinuarGenerico();
            adelantosDeSueldoSteps.clickVolverFlechaGenerico();
            adelantosDeSueldoSteps.validacionPantallaSolicitudAH();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Baja.class})
    @DisplayName("TC-0226-BSJ: Adelantos de Sueldo - Pantalla de confirmacion de solicitud - Confirmar sin seleccionar valores requeridos - validar campos obligatorios")
    public void TC0226BSJ() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uGdrio_12);
            menuLateralSteps.clickCuentas();
            adelantosDeSueldoSteps.clickVerMasXnroCuenta("CA $ 15008057945");
            adelantosDeSueldoSteps.clickMasOpcionesGenerico();
            adelantosDeSueldoSteps.clickSolicitudAdelantoDeSueldo();
            adelantosDeSueldoSteps.setCuantoNecesitas("200");
            adelantosDeSueldoSteps.clickContinuarGenerico();
            Thread.sleep(2000);
            adelantosDeSueldoSteps.clickConfirmarGenerico();
            adelantosDeSueldoSteps.validarCamposRequeridos("Leer y aceptar los términos y condiciones es obligatorio para continuar");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Media.class})
    @DisplayName("TC-0227-BSJ: Adelantos de Sueldo - Solicitud de Adelanto de Sueldo - Resumen de contrato - Validar redireccion a documento pdf")
    public void TC0227BSJ() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uGdrio_12);
            menuLateralSteps.clickCuentas();
            adelantosDeSueldoSteps.clickVerMasXnroCuenta("CA $ 15008057945");
            adelantosDeSueldoSteps.clickMasOpcionesGenerico();
            adelantosDeSueldoSteps.clickSolicitudAdelantoDeSueldo();
            adelantosDeSueldoSteps.clickResumenDeContrato();
            prestamosSteps.clickAceptarGenerico();
            prestamosSteps.validarFragmentoURL("assets.ctfassets");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Baja.class})
    @DisplayName("TC-0228-BSJ: Adelantos de Sueldo - Resumen de contrato - Cancelar redireccion a documento pdf - validar volver a la pagina de solicitud de adelanto de sueldo")
    public void TC0228BSJ() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uGdrio_12);
            menuLateralSteps.clickCuentas();
            adelantosDeSueldoSteps.clickVerMasXnroCuenta("CA $ 15008057945");
            adelantosDeSueldoSteps.clickMasOpcionesGenerico();
            adelantosDeSueldoSteps.clickSolicitudAdelantoDeSueldo();
            adelantosDeSueldoSteps.clickResumenDeContrato();
            adelantosDeSueldoSteps.clickCancelarGenerico();
            adelantosDeSueldoSteps.validacionPantallaSolicitudAH();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Alta.class})
    @DisplayName("TC-0229-BSJ: Adelantos de Sueldo - Mis adelantos - Validar card de adelantos de sueldo")
    public void TC0229BSJ() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uGdrio_12);
            menuLateralSteps.clickCuentas();
            adelantosDeSueldoSteps.clickVerMasXnroCuenta("CA $ 15008057945");
            adelantosDeSueldoSteps.clickMasOpcionesGenerico();
            adelantosDeSueldoSteps.clickSolicitudAdelantoDeSueldo();
            adelantosDeSueldoSteps.clickMisAdelantos();
            loginSteps.clickSacarPublicidadTenemosOtraPropuesta();
            adelantosDeSueldoSteps.validacionCardMisAdelantos();

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Baja.class})
    @DisplayName("TC-0237-BSJ: Adelantos de Sueldo - Realizar solicitud de adelanto de sueldo  - Validar mensaje de error ingreso de monto MENOR al minimo")
    public void TC0237BSJ() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uGdrio_12);
            menuLateralSteps.clickCuentas();
            adelantosDeSueldoSteps.clickVerMasXnroCuenta("CA $ 15008057945");
            adelantosDeSueldoSteps.clickMasOpcionesGenerico();
            adelantosDeSueldoSteps.clickSolicitudAdelantoDeSueldo();
            adelantosDeSueldoSteps.setCuantoNecesitas("0");
            adelantosDeSueldoSteps.clickContinuarGenerico();
            adelantosDeSueldoSteps.validarAlertMontoPermitido();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Baja.class})
    @DisplayName("TC-0238-BSJ: Adelantos de Sueldo - Realizar solicitud de adelanto de sueldo  - Validar mensaje de error ingreso de monto MAYOR al maximo")
    public void TC0238BSJ() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uGdrio_12);
            menuLateralSteps.clickCuentas();
            adelantosDeSueldoSteps.clickVerMasXnroCuenta("CA $ 15008057945");
            adelantosDeSueldoSteps.clickMasOpcionesGenerico();
            adelantosDeSueldoSteps.clickSolicitudAdelantoDeSueldo();
            adelantosDeSueldoSteps.setCuantoNecesitas("9999999");
            adelantosDeSueldoSteps.clickContinuarGenerico();
            adelantosDeSueldoSteps.validarAlertMontoPermitido();

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Baja.class})
    @DisplayName("TC-0239-BSJ: Adelantos de Sueldo - Realizar solicitud de adelanto de sueldo - Validar campos requeridos")
    public void TC0239BSJ() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uGdrio_12);
            menuLateralSteps.clickCuentas();
            adelantosDeSueldoSteps.clickVerMasXnroCuenta("CA $ 15008057945");
            adelantosDeSueldoSteps.clickMasOpcionesGenerico();
            adelantosDeSueldoSteps.clickSolicitudAdelantoDeSueldo();
            adelantosDeSueldoSteps.clickContinuarGenerico();
            adelantosDeSueldoSteps.validarCampoObligatorioGenerico();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Criticos.class})
    @DisplayName("TC-12978-BSJ: OFERTA - Adelanto de Sueldo - Acceso Directo")
    public void TC12978BSJ() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uGdrio_12);
            menuLateralSteps.clickInicio();
            adelantosDeSueldoSteps.clickAccesoDirectoAdelantoSueldo();
            adelantosDeSueldoSteps.validarTituloPageGenerico("Adelanto de sueldo");
            adelantosDeSueldoSteps.clickCardAdelantoSueldo();
            adelantosDeSueldoSteps.seleccionarDesplegableCuentaOrigen();
            adelantosDeSueldoSteps.setCuantoNecesitas("100");
            adelantosDeSueldoSteps.clickContinuarGenerico();
            adelantosDeSueldoSteps.validacionCamposConfirmacion();
            adelantosDeSueldoSteps.clickCheckTyC();
            adelantosDeSueldoSteps.validarBotonConfirmar();
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
