package GP.SantaFe.inversiones;

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
import steps.inversiones.InversionesSteps;
import steps.inversiones.cuentacomitente.CuentaComitenteSteps;
import steps.inversiones.fci.FondoComunInversionSteps;
import steps.inversiones.fci.SimularPerfilInversorSteps;
import steps.login.LoginSteps;
import steps.menulateral.MenuLateralSteps;
import utils.UtilsGP;

import java.io.IOException;
import static db.ConexionDB.cerrarConexion;
import static utils.Constantes.*;

public class CuentaComitenteTest {
    LoginSteps loginSteps= new LoginSteps();
    MenuLateralSteps menuLateralSteps= new MenuLateralSteps();
    InversionesSteps inversionesSteps= new InversionesSteps();
    CuentaComitenteSteps cuentaComitenteSteps= new CuentaComitenteSteps();
   SimularPerfilInversorSteps simularPerfilInversorSteps = new SimularPerfilInversorSteps();
   BotonesGenericosPage botonesGenericosPage = new BotonesGenericosPage();
   BotonesGenericosSteps botonesGenericosSteps = new BotonesGenericosSteps();
   UtilsGP utilsGP = new UtilsGP();

    @Test
    @Category({Media.class})
    @DisplayName("TC- 0168- BSF-  Inversiones- Alta cuenta comitente- Solicitar cuenta comitente - Validar leyenda y boton 'Solicitar cuenta comitente' habilitado")
    public void TC0168BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uMercau);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickSolicitarNuevaCuentaComitente();
            cuentaComitenteSteps.validarTexto("Solicitá tu cuenta comitente y empezá a operar.");
            cuentaComitenteSteps.validarBotonSolicitarCuenta();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Media.class})
    @DisplayName("TC- 0169- BSF-  Inversiones- Alta cuenta comitente- Solicitar cuenta comitente - Validar pantalla etapa '1 - Cuentas'")
    public void TC0169BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uSantafe140);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickSolicitarNuevaCuentaComitente();
            cuentaComitenteSteps.clickSolicitarCuenta();
            cuentaComitenteSteps.validarTituloPageGenerico("Apertura de cuenta comitente");
            cuentaComitenteSteps.validarNumyTextDeEtapa("1", "Cuentas");
            cuentaComitenteSteps.validarTexto("Seleccioná las cuentas a las que quieras vincular tu Cuenta Comitente.");
            cuentaComitenteSteps.validarTexto("Seleccioná una cuenta en pesos");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Media.class})
    @DisplayName("TC- 0170- BSF-  Inversiones- Alta cuenta comitente- Solicitar cuenta comitente  - Validar pantalla - etapa '2 Validacion'")
    public void TC0170BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uSantafe140);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickSolicitarNuevaCuentaComitente();
            cuentaComitenteSteps.clickSolicitarCuenta();
            cuentaComitenteSteps.validarTituloPageGenerico("Apertura de cuenta comitente");
            cuentaComitenteSteps.clickContinuarGenerico();
            cuentaComitenteSteps.validarNumyTextDeEtapa("2", "Validaci");
            cuentaComitenteSteps.validacionCamposInfoPersonal();
            cuentaComitenteSteps.validarTexto("Situación del solicitante (solo si corresponde)");
            cuentaComitenteSteps.clickCheckboxNingunaDeLasAnteriores();

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Media.class})
    @DisplayName("TC- 0171- BSF-  Inversiones- Alta cuenta comitente- Solicitar cuenta comitente  - Validar pantalla etapa '3 Datos del solicitante'")
    public void TC0171BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uSantafe140);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickSolicitarNuevaCuentaComitente();
            cuentaComitenteSteps.clickSolicitarCuenta();
            cuentaComitenteSteps.validarTituloPageGenerico("Apertura de cuenta comitente");
            cuentaComitenteSteps.clickContinuarGenerico();
            cuentaComitenteSteps.clickCheckboxNingunaDeLasAnteriores();
            cuentaComitenteSteps.clickContinuarGenerico();
            cuentaComitenteSteps.validarNumyTextDeEtapa("3", "Datos del solicitante");
            cuentaComitenteSteps.validarTexto("Los siguientes datos que vas a completar, funcionan a modo de declaración jurada.");
            cuentaComitenteSteps.validacionPreguntas();
            cuentaComitenteSteps.clickRadioFormDeclaracionJurada();
            cuentaComitenteSteps.clickCheckboxTerminosyCondiciones();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Media.class})
    @DisplayName("TC- 0172- BSF-  Inversiones- Alta cuenta comitente- Solicitar cuenta comitente  - Validar pantalla etapa '4 Perfil inversor'")
    public void TC0172BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uSantafe140);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickSolicitarNuevaCuentaComitente();
            cuentaComitenteSteps.clickSolicitarCuenta();
            cuentaComitenteSteps.validarTituloPageGenerico("Apertura de cuenta comitente");
            cuentaComitenteSteps.clickContinuarGenerico();
            cuentaComitenteSteps.clickCheckboxNingunaDeLasAnteriores();
            cuentaComitenteSteps.clickContinuarGenerico();
            cuentaComitenteSteps.clickCheckboxTerminosyCondiciones();
            cuentaComitenteSteps.clickContinuarGenerico();
            cuentaComitenteSteps.validarNumyTextDeEtapa("4", "Perfil Inversor");
            //simularPerfilInversorSteps.completarFormularioDeSimulacion();
            //cuentaComitenteSteps.clickContinuarGenerico();
            cuentaComitenteSteps.validarTituloPageGenerico("Resultado del perfil de inversor");
            cuentaComitenteSteps.validarTexto("Podés continuar con este perfil actual o hacer de nuevo el");
            cuentaComitenteSteps.clickTestPerfilInversor();
            cuentaComitenteSteps.validarTituloPageGenerico("Simular perfil del inversor");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Criticos.class})
    @DisplayName("TC- 0173- BSF-  Inversiones- Alta cuenta comitente- Solicitar cuenta comitente  - Validar pantalla etapa '5 Confirmacion'")
    public void TC0173BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uSantafe140);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickSolicitarNuevaCuentaComitente();
            cuentaComitenteSteps.clickSolicitarCuenta();
            cuentaComitenteSteps.validarTituloPageGenerico("Apertura de cuenta comitente");
            cuentaComitenteSteps.clickContinuarGenerico();
            cuentaComitenteSteps.clickCheckboxNingunaDeLasAnteriores();
            cuentaComitenteSteps.clickContinuarGenerico();
            cuentaComitenteSteps.clickCheckboxTerminosyCondiciones();
            cuentaComitenteSteps.clickContinuarGenerico();
            //simularPerfilInversorSteps.completarFormularioDeSimulacion();
            //cuentaComitenteSteps.clickContinuarGenerico();
            cuentaComitenteSteps.clickConfirmarGenerico();
            cuentaComitenteSteps.validarNumyTextDeEtapa("5", "Confirmación");
            cuentaComitenteSteps.validarTituloPageGenerico("Apertura de cuenta comitente");
            cuentaComitenteSteps.validarTexto("Estás por confirmar la solicitud de Alta de Cuenta Comitente");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Alta.class})
    @DisplayName("TC- 0174- BSF-  Inversiones- Alta cuenta comitente- Click continuar sin aceptar Terminos y condiciones- Validar boton continuar inhabilitado")
    public void TC0174BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uSantafe140);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickSolicitarNuevaCuentaComitente();
            cuentaComitenteSteps.clickSolicitarCuenta();
            cuentaComitenteSteps.validarTituloPageGenerico("Apertura de cuenta comitente");
            cuentaComitenteSteps.clickContinuarGenerico();
            cuentaComitenteSteps.clickCheckboxNingunaDeLasAnteriores();
            cuentaComitenteSteps.clickContinuarGenerico();
            cuentaComitenteSteps.validarBotonInhabilitado();
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
