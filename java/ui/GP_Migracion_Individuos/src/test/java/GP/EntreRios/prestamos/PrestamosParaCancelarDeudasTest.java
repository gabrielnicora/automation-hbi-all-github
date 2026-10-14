package GP.EntreRios.prestamos;

import GP.Tags.Alta;
import GP.Tags.Baja;
import GP.Tags.Criticos;
import GP.Tags.Media;
import db.ConexionDB;
import io.qameta.allure.Allure;
import io.qameta.allure.junit4.DisplayName;
import org.junit.*;
import org.junit.experimental.categories.Category;
import pages.botonesgenericos.BotonesGenericosPage;
import steps.botonesgenericos.BotonesGenericosSteps;
import steps.login.LoginSteps;
import steps.menulateral.MenuLateralSteps;
import steps.prestamos.PrestamosParaCancelarDeudasSteps;
import steps.prestamos.PrestamosSteps;
import utils.Constantes;
import utils.TestCaseData;
import utils.UtilsGP;

import java.io.IOException;

import static db.ConexionDB.cerrarConexion;
import static utils.Constantes.uEcarina27;

public class PrestamosParaCancelarDeudasTest {
    LoginSteps loginSteps = new LoginSteps();
    MenuLateralSteps menuLateralSteps = new MenuLateralSteps();
    PrestamosSteps prestamosSteps = new PrestamosSteps();
    PrestamosParaCancelarDeudasSteps prestamosParaCancelarDeudasSteps= new PrestamosParaCancelarDeudasSteps();
    BotonesGenericosSteps botonesGenericosSteps = new BotonesGenericosSteps();
    BotonesGenericosPage botonesGenericosPage = new BotonesGenericosPage();
    UtilsGP utilsGP = new UtilsGP();
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
    @Category({Alta.class})
    @DisplayName("TC-0211-BERSA: Prestamos- Validar que el boton 'Prestamo para cancelar deudas' sea clickeable")
    public void TC0211BERSA() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uEcarina27);
            menuLateralSteps.clickPrestamos();
            prestamosParaCancelarDeudasSteps.clickPrestamosParaCancelarDeudas();
            prestamosSteps.validarTituloPageGenerico("Solicitá tu préstamo para cancelar deudas");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Media.class})
    @DisplayName("TC-0212-BERSA: Prestamos- Prestamo para cancelar deudas - Validar pantalla etapa 1 'Solicitud'")
    public void TC0212BERSA() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uEcarina27);
            menuLateralSteps.clickPrestamos();
            prestamosParaCancelarDeudasSteps.clickPrestamosParaCancelarDeudas();
            prestamosSteps.validarTituloPageGenerico("Solicitá tu préstamo para cancelar deudas");
            prestamosSteps.validarTituloEtapa("Solicitud");
            prestamosParaCancelarDeudasSteps.validacionCamposEtapaSolicitud();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Media.class})
    @DisplayName("TC-0213-BERSA: Prestamos- Prestamo para cancelar deudas - No seleccionar ninguna card - Verificar que el botón Siguiente se encuentre inhabilitado")
    public void TC0213BERSA() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uEcarina27);
            menuLateralSteps.clickPrestamos();
            prestamosParaCancelarDeudasSteps.clickPrestamosParaCancelarDeudas();
            prestamosSteps.validarTituloPageGenerico("Solicitá tu préstamo para cancelar deudas");
            prestamosSteps.validarBotonSiguienteDeshabilitado();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Media.class})
    @DisplayName("TC-0214-BERSA: Prestamos- Prestamo para cancelar deudas - Seleccionar solo la card de Tarjeta de credito - Verificar que el botón Siguiente se encuentre inhabilitado")
    public void TC0214BERSA() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uEcarina27);
            menuLateralSteps.clickPrestamos();
            prestamosParaCancelarDeudasSteps.clickPrestamosParaCancelarDeudas();
            prestamosSteps.validarTituloPageGenerico("Solicitá tu préstamo para cancelar deudas");
            prestamosParaCancelarDeudasSteps.clickCheckboxTarjetaDeCredito();
            prestamosSteps.validarBotonSiguienteDeshabilitado();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Baja.class})
    @DisplayName("TC-0215-BERSA: Prestamos- Prestamo para cancelar deudas - Ingresar importe menor al permitido y verificar mensaje de advertencia")
    public void TC0215BERSA() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uEcarina27);
            menuLateralSteps.clickPrestamos();
            prestamosParaCancelarDeudasSteps.clickPrestamosParaCancelarDeudas();
            prestamosSteps.validarTituloPageGenerico("Solicitá tu préstamo para cancelar deudas");
            prestamosParaCancelarDeudasSteps.clickPrimerCheckbox();
            prestamosSteps.clickSiguiente();
            prestamosSteps.setMonto("1");
            prestamosParaCancelarDeudasSteps.clickContinuarGenerico();
            prestamosParaCancelarDeudasSteps.validarMensajeElMontoDebeSerEntre();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Baja.class})
    @DisplayName("TC-0216-BERSA: Prestamos- Prestamo para cancelar deudas -  Ingresar importe mayor al permitido y verificar mensaje de advertencia")
    public void TC0216BERSA() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uEcarina27);
            menuLateralSteps.clickPrestamos();
            prestamosParaCancelarDeudasSteps.clickPrestamosParaCancelarDeudas();
            prestamosSteps.validarTituloPageGenerico("Solicitá tu préstamo para cancelar deudas");
            prestamosParaCancelarDeudasSteps.clickPrimerCheckbox();
            prestamosSteps.clickSiguiente();
            prestamosSteps.setMonto("900000");
            prestamosParaCancelarDeudasSteps.clickContinuarGenerico();
            prestamosParaCancelarDeudasSteps.validarMensajeElMontoDebeSerEntre();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Media.class})
    @DisplayName("TC-0217-BERSA: Prestamos- Prestamo para cancelar deudas - Validar pantalla etapa 2 'Detalle'")
    public void TC0217BERSA() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uEcarina27);
            menuLateralSteps.clickPrestamos();
            prestamosParaCancelarDeudasSteps.clickPrestamosParaCancelarDeudas();
            prestamosParaCancelarDeudasSteps.clickPrimerCheckbox();
            prestamosSteps.clickSiguiente();
            prestamosSteps.setMonto("9000");
            prestamosParaCancelarDeudasSteps.clickContinuarGenerico();
            prestamosParaCancelarDeudasSteps.validarTituloPageGenerico("Detalle de tu Préstamo para cancelar deudas");
            prestamosSteps.validarTituloEtapa("Detalle");
            prestamosParaCancelarDeudasSteps.validacionCamposEtapaDetalle();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Media.class})
    @DisplayName("TC-0218-BERSA: Prestamos- Prestamo para cancelar deudas - Validar pantalla etapa 3 'Cuotas'")
    public void TC0218BERSA() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uEcarina27);
            menuLateralSteps.clickPrestamos();
            prestamosParaCancelarDeudasSteps.clickPrestamosParaCancelarDeudas();
            prestamosParaCancelarDeudasSteps.clickPrimerCheckbox();
            prestamosSteps.clickSiguiente();
            prestamosSteps.setMonto("9000");
            prestamosParaCancelarDeudasSteps.clickContinuarGenerico();
            prestamosParaCancelarDeudasSteps.clickContinuarGenerico();
            prestamosSteps.validarTituloEtapa("Cuotas");
            prestamosParaCancelarDeudasSteps.validarTituloPageGenerico("Seleccioná la cantidad de cuotas");
            prestamosSteps.seleccionarLabelCuotas();
            prestamosParaCancelarDeudasSteps.validacionCamposEtapaCuotas();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Media.class})
    @DisplayName("TC-0219-BERSA: Prestamos- Prestamo para cancelar deudas - Validar pantalla etapa 4 'Resumen'")
    public void TC0219BERSA() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uEcarina27);
            menuLateralSteps.clickPrestamos();
            prestamosParaCancelarDeudasSteps.clickPrestamosParaCancelarDeudas();
            prestamosParaCancelarDeudasSteps.clickPrimerCheckbox();
            prestamosSteps.clickSiguiente();
            prestamosSteps.setMonto("9000");
            prestamosParaCancelarDeudasSteps.clickContinuarGenerico();
            prestamosParaCancelarDeudasSteps.clickContinuarGenerico();
            prestamosSteps.seleccionarLabelCuotas();
            prestamosParaCancelarDeudasSteps.clickContinuarGenerico();
            prestamosParaCancelarDeudasSteps.validarTituloPageGenerico("Revisá que esté todo bien");
            prestamosParaCancelarDeudasSteps.validacionCamposEtapaResumen();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0220-BERSA: Prestamos- Prestamo para cancelar deudas - Validar pantalla etapa 5 'Confirmacion'")
    public void TC0220BERSA() {
        try {
            testCaseData.cargaDeDatosTest(uEcarina27);
            loginSteps.iniciarSessionAlEscritorioConPass(uEcarina27,testCaseData.password);
            menuLateralSteps.clickPrestamos();
            prestamosParaCancelarDeudasSteps.clickPrestamosParaCancelarDeudas();
            prestamosParaCancelarDeudasSteps.clickPrimerCheckbox();
            prestamosSteps.clickSiguiente();
            prestamosSteps.setMonto("9000");
            prestamosParaCancelarDeudasSteps.clickContinuarGenerico();
            prestamosParaCancelarDeudasSteps.clickContinuarGenerico();
            prestamosSteps.seleccionarLabelCuotas();
            prestamosParaCancelarDeudasSteps.clickContinuarGenerico();
            prestamosParaCancelarDeudasSteps.clickCheckTerminosCondicionesGenerico();
            prestamosParaCancelarDeudasSteps.clickContinuarGenerico();
            prestamosSteps.validarTituloEtapa("Confirmaci");
            prestamosParaCancelarDeudasSteps.validarTituloPageGenerico("Confirmá tu solicitud");
            prestamosParaCancelarDeudasSteps.validarTituloPageGenerico("La acreditación se verá reflejada en el transcurso del día.");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Alta.class})
    @DisplayName("TC-0221-BERSA: Prestamos- Prestamo para cancelar deudas - Descargar resumen de contrato - validar descarga archivo")
    public void TC0221BERSA() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uEcarina27);
            menuLateralSteps.clickPrestamos();
            prestamosParaCancelarDeudasSteps.clickPrestamosParaCancelarDeudas();
            prestamosParaCancelarDeudasSteps.clickPrimerCheckbox();
            prestamosSteps.clickSiguiente();
            prestamosSteps.descargarResumenContrato();
            prestamosParaCancelarDeudasSteps.validarFragmentoURL("assets");
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
