package GP.SanJuan.inversiones.FCIEsco;

import db.ConexionDB;
import io.qameta.allure.Allure;
import io.qameta.allure.junit4.DisplayName;
import org.junit.*;
import org.junit.runners.MethodSorters;
import pages.botonesgenericos.BotonesGenericosPage;
import steps.botonesgenericos.BotonesGenericosSteps;
import steps.cuentas.CuentasSteps;
import steps.cuentas.detallecuenta.DetalleCuentaSteps;
import steps.inversiones.InversionesSteps;
import steps.inversiones.fci.FondoComunInversionSteps;
import steps.inversiones.fci.RescateFCISteps;
import steps.inversiones.fci.SuscripcionFCISteps;
import steps.login.LoginSteps;
import steps.menulateral.MenuLateralSteps;
import steps.ticket.TicketSteps;
import utils.Constantes;
import utils.Settings;
import utils.TestCaseData;
import utils.UtilsGP;

import java.io.IOException;

import static db.ConexionDB.cerrarConexion;
import static utils.Settings.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SuscripcionFueraHorarioMercadoTest {

    LoginSteps loginSteps = new LoginSteps();
    MenuLateralSteps menuLateralSteps = new MenuLateralSteps();
    InversionesSteps inversionesSteps = new InversionesSteps();
    FondoComunInversionSteps fondoComunInversionSteps = new FondoComunInversionSteps();
    SuscripcionFCISteps suscripcionFCISteps = new SuscripcionFCISteps();
    CuentasSteps cuentasSteps = new CuentasSteps();
    UtilsGP utilsGP = new UtilsGP();
    DetalleCuentaSteps detalleCuentaSteps = new DetalleCuentaSteps();
    TicketSteps ticketSteps = new TicketSteps();

    RescateFCISteps rescateFCISteps = new RescateFCISteps();

    BotonesGenericosSteps botonesGenericosSteps = new BotonesGenericosSteps();
    BotonesGenericosPage botonesGenericosPage = new BotonesGenericosPage();
    TestCaseData testCaseData = new TestCaseData();

    //private static String MENSAJE_ADVERTENCIA_T1="Recordá que tus suscripciones serán acreditados en tu cuenta dentro de las próximas 48 hs hábiles.";
    //private static String MENSAJE_ADVERTENCIA_T0= "Recordá que tu suscripción se procesará el próximo día hábil.";

    /**
     *Metodo que realiza la conexión a BD y seteo de datos para los casos de pruebas.
     *
     *@return objeto Json con los datos necesarios para cada uno de los test.
     */
    @BeforeClass
    public static void extraerDatosBD(){
        System.out.println("============= BEFORE CLASS ==========================");
        if(Constantes.usuarios == null){
            System.out.println("============= Array de datos vacio extraer desde la BD ==============");
            Settings.dataTestCase = ConexionDB.usuariosDB();
            System.out.println("============= se completo el array de datos ==========================");
        }else {
            System.out.println("================ EL ARRAY CONTIENE DATOS ===================");
        }
    }

    //DIA HÁBIL FUERA DEL HORARIO DE MERCADO
    @Test
    @DisplayName("TC-14878-BSJ: Suscripción día hábil fuera del Horario Mercado tipo : T0 desde Grilla")
    public void ATC14878BSJ(){
        try{
            testCaseData.cargaDeDatosTests("ATC14877BSJ");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.ESCRITORIO);
            inversionesSteps.validarTituloPageGenerico("Hola");
            menuLateralSteps.clickCuentas();
            cuentasSteps.capturarSaldoInicialEnCuenta(testCaseData.cuentaOrigen);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            fondoComunInversionSteps.clickSuscribir(DELTA_PESOS_UNISYS);
            fondoComunInversionSteps.validarModalFCI("Agendá tu suscripción cuando quieras");
            fondoComunInversionSteps.clickContinuarGenerico();
            suscripcionFCISteps.setImporteSuscribirFci(importe);
            fondoComunInversionSteps.validarMensajeAdvertencia(MENSAJE_ADVERTENCIA_T0);
            UtilsGP.fixedWait(4000);
            suscripcionFCISteps.clickContinuarGenerico();
            fondoComunInversionSteps.validarMensajeAdvertencia(MENSAJE_ADVERTENCIA_T0);
            suscripcionFCISteps.clickCheckbox();
            suscripcionFCISteps.clickConfirmarGenerico();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            fondoComunInversionSteps.validarMensajeAdvertencia(MENSAJE_ADVERTENCIA_T0);
            menuLateralSteps.clickCuentas();
            cuentasSteps.capturarSaldoActualizadoEnCuenta(testCaseData.cuentaOrigen);
            cuentasSteps.verificarDescuentoDeSaldoEnCuenta(importe);
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-14870-BSJ : Suscripción día hábil fuera del Horario Mercado tipo : T0 desde detalle")
    public void BTC14870BSJ(){
        try{
            testCaseData.cargaDeDatosTests("ATC14877BSJ");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.ESCRITORIO);
            inversionesSteps.validarTituloPageGenerico("Hola");
            menuLateralSteps.clickCuentas();
            cuentasSteps.capturarSaldoInicialEnCuenta(testCaseData.cuentaOrigen);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            fondoComunInversionSteps.clickIconoAccionesDetalleFCI(DELTA_PESOS_UNISYS);
            fondoComunInversionSteps.clickSuscribir();
            fondoComunInversionSteps.validarModalFCI("Agendá tu suscripción cuando quieras");
            fondoComunInversionSteps.clickContinuarGenerico();
            suscripcionFCISteps.setImporteSuscribirFci(importe);
            fondoComunInversionSteps.validarMensajeAdvertencia(MENSAJE_ADVERTENCIA_T0);
            UtilsGP.fixedWait(4000);
            suscripcionFCISteps.clickContinuarGenerico();
            fondoComunInversionSteps.validarMensajeAdvertencia(MENSAJE_ADVERTENCIA_T0);
            suscripcionFCISteps.clickCheckbox();
            suscripcionFCISteps.clickConfirmarGenerico();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            fondoComunInversionSteps.validarMensajeAdvertencia(MENSAJE_ADVERTENCIA_T0);
            menuLateralSteps.clickCuentas();
            cuentasSteps.capturarSaldoActualizadoEnCuenta(testCaseData.cuentaOrigen);
            cuentasSteps.verificarDescuentoDeSaldoEnCuenta(importe);
        }catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-14882-BSJ: Suscripción día hábil fuera del Horario Mercado tipo : T1  desde Grilla")
    public void CTC14882BSJ(){
        try{
            testCaseData.cargaDeDatosTests("ATC14877BSJ");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.ESCRITORIO);
            inversionesSteps.validarTituloPageGenerico("Hola");
            menuLateralSteps.clickCuentas();
            cuentasSteps.capturarSaldoInicialEnCuenta(testCaseData.cuentaOrigen);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            fondoComunInversionSteps.clickSuscribir(AHORRO_PLUS_UNISYS);
            fondoComunInversionSteps.validarModalFCI("Agendá tu suscripción cuando quieras");
            fondoComunInversionSteps.clickContinuarGenerico();
            suscripcionFCISteps.setImporteSuscribirFci(importe);
            fondoComunInversionSteps.validarMensajeAdvertencia(MENSAJE_ADVERTENCIA_T0);
            UtilsGP.fixedWait(4000);
            suscripcionFCISteps.clickContinuarGenerico();
            fondoComunInversionSteps.validarMensajeAdvertencia(MENSAJE_ADVERTENCIA_T0);
            suscripcionFCISteps.clickCheckbox();
            suscripcionFCISteps.clickConfirmarGenerico();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            fondoComunInversionSteps.validarMensajeAdvertencia(MENSAJE_ADVERTENCIA_T0);
            menuLateralSteps.clickCuentas();
            cuentasSteps.capturarSaldoActualizadoEnCuenta(testCaseData.cuentaOrigen);
            cuentasSteps.verificarDescuentoDeSaldoEnCuenta(importe);
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-14874-BSJ: Suscripción día hábil fuera del Horario Mercado tipo : T1 desde detalle")
    public void DTC14874BSJ(){
        try{
            testCaseData.cargaDeDatosTests("ATC14877BSJ");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.ESCRITORIO);
            inversionesSteps.validarTituloPageGenerico("Hola");
            menuLateralSteps.clickCuentas();
            cuentasSteps.capturarSaldoInicialEnCuenta(testCaseData.cuentaOrigen);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            fondoComunInversionSteps.clickIconoAccionesDetalleFCI(AHORRO_PLUS_UNISYS);
            fondoComunInversionSteps.clickSuscribir();
            fondoComunInversionSteps.validarModalFCI("Agendá tu suscripción cuando quieras");
            fondoComunInversionSteps.clickContinuarGenerico();
            suscripcionFCISteps.setImporteSuscribirFci(importe);
            fondoComunInversionSteps.validarMensajeAdvertencia(MENSAJE_ADVERTENCIA_T0);
            UtilsGP.fixedWait(4000);
            suscripcionFCISteps.clickContinuarGenerico();
            fondoComunInversionSteps.validarMensajeAdvertencia(MENSAJE_ADVERTENCIA_T0);
            suscripcionFCISteps.clickCheckbox();
            suscripcionFCISteps.clickConfirmarGenerico();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            fondoComunInversionSteps.validarMensajeAdvertencia(MENSAJE_ADVERTENCIA_T0);
            menuLateralSteps.clickCuentas();
            cuentasSteps.capturarSaldoActualizadoEnCuenta(testCaseData.cuentaOrigen);
            cuentasSteps.verificarDescuentoDeSaldoEnCuenta(importe);
        }catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @After
    public void cerrarSesionCapturarUPS(){
        if(utilsGP.estaPresente(botonesGenericosPage.getCodigoUps(),2) && botonesGenericosPage.getCodigoUps().getText().startsWith("#")) {
            String ups = botonesGenericosPage.getCodigoUps().getText();
            global.practia.jemeinsamstark.web.core.WebDriver.getInstance().navigate().to(System.getProperty("appUrl"));
            System.out.println("Se Espera que aparezca la pagina de nuevo");
            loginSteps.waitGP();
            Assert.fail("UPS CODIGO : " + ups);
        }else{
            System.out.println("=================== NO PUDO INICIAR SESION REFRESCAR NAVEGADOR ==============================");
            Allure.addAttachment("INICIO SESSION,","=================== NO PUDO INICIAR SESION REFRESCAR NAVEGADOR ==============================");
            global.practia.jemeinsamstark.web.core.WebDriver.getInstance().navigate().to(System.getProperty("appUrl"));
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
