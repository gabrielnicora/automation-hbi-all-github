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
import steps.prestamos.PrestamosSteps;
import utils.Constantes;
import utils.TestCaseData;
import utils.UtilsGP;

import java.io.IOException;

import static db.ConexionDB.cerrarConexion;
import static utils.Constantes.uEcarina27;

public class PrestamosTest {

    LoginSteps loginSteps = new LoginSteps();
    MenuLateralSteps menuLateralSteps = new MenuLateralSteps();
    PrestamosSteps prestamosSteps = new PrestamosSteps();
    BotonesGenericosPage botonesGenericosPage = new BotonesGenericosPage();
    BotonesGenericosSteps botonesGenericosSteps = new BotonesGenericosSteps();
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
    @DisplayName("TC-0202-BERSA: Prestamos - Validar que el boton 'Nuevo prestamo' sea clickeable")
    public void TC0202BERSA() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uEcarina27);
            menuLateralSteps.clickPrestamos();
            prestamosSteps.clickNuevoPrestamo();
            prestamosSteps.validarTituloPageGenerico("Solicitá tu préstamo");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Media.class})
    @DisplayName("TC-0203-BERSA: Prestamos - Nuevo prestamo - Validar pantalla etapa 1 'Solicitud'")
    public void TC0203BERSA() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uEcarina27);
            menuLateralSteps.clickPrestamos();
            prestamosSteps.clickNuevoPrestamo();
            prestamosSteps.validarTituloPageGenerico("Solicitá tu préstamo");
            prestamosSteps.validarTituloEtapa("Solicitud");
            prestamosSteps.validacionCamposEtapaSolicitud();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }

    }

    @Test
    @Category({Media.class})
    @DisplayName("TC-0204-BERSA: Prestamos - Nuevo prestamo - Validar pantalla etapa 2 'Cuotas'")
    public void TC0204BERSA() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uEcarina27);
            menuLateralSteps.clickPrestamos();
            prestamosSteps.clickNuevoPrestamo();
            prestamosSteps.setMonto("10000");
            prestamosSteps.clickSiguiente();
            prestamosSteps.seleccionDestino("INMUEBLES");
            prestamosSteps.clickContinuarGenerico();
            prestamosSteps.validarTituloEtapa("Cuotas");
            prestamosSteps.validarTituloPageGenerico("Seleccioná la cantidad de cuotas");
            prestamosSteps.seleccionarLabelCuotas();
            prestamosSteps.validacionCamposEtapaCuotas();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Media.class})
    @DisplayName("TC-0205-BERSA: Prestamos - Nuevo prestamo - Validar pantalla etapa 3 'Resumen'")
    public void TC0205BERSA() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uEcarina27);
            menuLateralSteps.clickPrestamos();
            prestamosSteps.clickNuevoPrestamo();
            prestamosSteps.setMonto("10000");
            prestamosSteps.clickSiguiente();
            prestamosSteps.seleccionDestino("INMUEBLES");
            prestamosSteps.clickContinuarGenerico();
            prestamosSteps.validarTituloEtapa("Cuotas");
            prestamosSteps.validarTituloPageGenerico("Seleccioná la cantidad de cuotas");
            prestamosSteps.seleccionarLabelCuotas();
            prestamosSteps.clickContinuarGenerico();
            prestamosSteps.validarTituloEtapa("Resumen");
            prestamosSteps.validarTituloPageGenerico("Revisá que esté todo bien");
            prestamosSteps.validacionCamposEtapaResumen();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0206-BERSA: Prestamos - Nuevo prestamo - Validar pantalla etapa 4 'Confirmacion'")
    public void TC0206BERSA() {
        try {
            testCaseData.cargaDeDatosTest(uEcarina27);
            loginSteps.iniciarSessionAlEscritorioConPass(uEcarina27,testCaseData.password);
            menuLateralSteps.clickPrestamos();
            prestamosSteps.clickNuevoPrestamo();
            prestamosSteps.setMonto("10000");
            prestamosSteps.clickSiguiente();
            prestamosSteps.seleccionDestino("INMUEBLES");
            prestamosSteps.clickContinuarGenerico();
            prestamosSteps.validarTituloEtapa("Cuotas");
            prestamosSteps.validarTituloPageGenerico("Seleccioná la cantidad de cuotas");
            prestamosSteps.seleccionarLabelCuotas();
            prestamosSteps.clickContinuarGenerico();
            prestamosSteps.clickCheckTerminosCondicionesGenerico();
            prestamosSteps.clickContinuarGenerico();
            prestamosSteps.validarTituloEtapa("Confirmaci");
            prestamosSteps.validarTituloPageGenerico("Confirmá tu solicitud");
            prestamosSteps.validarTituloPageGenerico("Para terminar, ingresá el código que recibiste por mensaje de texto.");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Baja.class})
    @DisplayName("TC-0207-BERSA: Prestamos - Nuevo prestamo - Ingresar monto mayor al permitido y validar mensaje 'El monto debe ser mayor o igual a $ y menor o igual a $ '")
    public void TC0207BERSA() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uEcarina27);
            menuLateralSteps.clickPrestamos();
            prestamosSteps.clickNuevoPrestamo();
            prestamosSteps.setMonto("181000");
            prestamosSteps.clickSiguiente();
            prestamosSteps.validarMensajeMontoMayorIgual("");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Baja.class})
    @DisplayName("TC-0208-BERSA: Prestamos - Nuevo prestamo - Ingresar monto menor al permitido y validar mensaje 'El monto debe ser mayor o igual a $  y menor o igual a $'")
    public void TC0208BERSA() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uEcarina27);
            menuLateralSteps.clickPrestamos();
            prestamosSteps.clickNuevoPrestamo();
            prestamosSteps.setMonto("9000");
            prestamosSteps.clickSiguiente();
            prestamosSteps.validarMensajeMontoMayorIgual("");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Alta.class})
    @DisplayName("TC-0209-BERSA: Prestamos - Nuevo prestamo - Descargar resumen de contrato - validar archivo")
    public void TC0209BERSA() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uEcarina27);
            menuLateralSteps.clickPrestamos();
            prestamosSteps.clickNuevoPrestamo();
            prestamosSteps.descargarResumenContrato();
            prestamosSteps.validarDescarga("NBERSA RESU-27");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Media.class})
    @DisplayName("TC-0210-BERSA: Prestamos - Nuevo prestamo -Etapa 1 'Solicitud' - Monto vacío, validar boton 'Siguiente' grisado")
    public void TC0210BERSA() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uEcarina27);
            menuLateralSteps.clickPrestamos();
            prestamosSteps.clickNuevoPrestamo();
            prestamosSteps.validarBotonSiguienteDeshabilitado();
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