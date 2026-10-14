package GP.SantaCruz.inversiones.FCIEsco;

import db.ConexionDB;
import io.qameta.allure.Allure;
import io.qameta.allure.junit4.DisplayName;
import org.junit.*;
import org.junit.runners.MethodSorters;
import pages.botonesgenericos.BotonesGenericosPage;
import steps.cuentas.CuentasSteps;
import steps.cuentas.detallecuenta.DetalleCuentaSteps;
import steps.inversiones.InversionesSteps;
import steps.inversiones.fci.FondoComunInversionSteps;
import steps.inversiones.fci.RescateFCISteps;
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
public class RescateDentroHorarioMercadoTest {

    LoginSteps loginSteps = new LoginSteps();
    MenuLateralSteps menuLateralSteps = new MenuLateralSteps();
    InversionesSteps inversionesSteps = new InversionesSteps();
    FondoComunInversionSteps fondoComunInversionSteps = new FondoComunInversionSteps();
    CuentasSteps cuentasSteps = new CuentasSteps();
    UtilsGP utilsGP = new UtilsGP();
    DetalleCuentaSteps detalleCuentaSteps = new DetalleCuentaSteps();
    TicketSteps ticketSteps = new TicketSteps();
    RescateFCISteps rescateFCISteps = new RescateFCISteps();

    BotonesGenericosPage botonesGenericosPage = new BotonesGenericosPage();

    TestCaseData testCaseData = new TestCaseData();

    private static String MENSAJE_ADVERTENCIA_T2="Recordá que tus fondos serán acreditados en tu cuenta dentro de las próximas 72 hs hábiles.";

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

    //DENTRO DEL HORARIO DE MERCADO
    @Test
    @DisplayName("TC-14894-BSC: Rescate Parcial dia hábil dentro del horario de mercado tipo : T0 desde Grilla")
    public void ATC14894BSC(){
        try{
            testCaseData.cargaDeDatosTests("ATC14877BSC");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.ESCRITORIO);
            inversionesSteps.validarTituloPageGenerico("Hola");
            menuLateralSteps.clickCuentas();
            cuentasSteps.capturarSaldoInicialEnCuenta(testCaseData.cuentaOrigen);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            fondoComunInversionSteps.clickRescatar(DELTA_PESOS_UNISYS);
            rescateFCISteps.clickParcial();
            rescateFCISteps.setImporteRescatar(importe);
            UtilsGP.fixedWait(4000);
            rescateFCISteps.clickContinuarGenerico();
            rescateFCISteps.clickCheckTerminosCondicionesGenerico();
            rescateFCISteps.clickConfirmarGenerico();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            menuLateralSteps.clickCuentas();
            cuentasSteps.capturarSaldoActualizadoEnCuenta(testCaseData.cuentaOrigen);
            cuentasSteps.verificarIncrementoDeSaldoEnCuenta(importe);
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-14885-BSC : Rescate Parcial dia hábil dentro del horario de mercado tipo : T0 desde detalle")
    public void BTC14885BSC(){
        try{
            testCaseData.cargaDeDatosTests("ATC14877BSC");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.ESCRITORIO);
            inversionesSteps.validarTituloPageGenerico("Hola");
            menuLateralSteps.clickCuentas();
            cuentasSteps.capturarSaldoInicialEnCuenta(testCaseData.cuentaOrigen);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            fondoComunInversionSteps.clickIconoAccionesDetalleFCI(DELTA_PESOS_UNISYS);
            fondoComunInversionSteps.clickRescate();
            rescateFCISteps.clickParcial();
            rescateFCISteps.setImporteRescatar(importe);
            UtilsGP.fixedWait(4000);
            rescateFCISteps.clickContinuarGenerico();
            rescateFCISteps.clickCheckTerminosCondicionesGenerico();
            rescateFCISteps.clickConfirmarGenerico();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            menuLateralSteps.clickCuentas();
            cuentasSteps.capturarSaldoActualizadoEnCuenta(testCaseData.cuentaOrigen);
            cuentasSteps.verificarIncrementoDeSaldoEnCuenta(importe);
        }catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-14897-BSC: Rescate Parcial dia hábil dentro del horario de mercado tipo : T1 desde Grilla")
    public void  CTC14897BSC(){
        try{
            testCaseData.cargaDeDatosTests("ATC14877BSC");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.ESCRITORIO);
            inversionesSteps.validarTituloPageGenerico("Hola");
            menuLateralSteps.clickCuentas();
            cuentasSteps.capturarSaldoInicialEnCuenta(testCaseData.cuentaOrigen);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            fondoComunInversionSteps.clickRescatar(DELTA_MONEDA_UNISYS);
            rescateFCISteps.clickParcial();
            rescateFCISteps.setImporteRescatar(importe);
            UtilsGP.fixedWait(4000);
            rescateFCISteps.clickContinuarGenerico();
            rescateFCISteps.clickCheckTerminosCondicionesGenerico();
            rescateFCISteps.clickConfirmarGenerico();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test //APAGADO HASTA QUE SE IMPLEMENTE V68
    @DisplayName("TC-14889-BSC: Rescate TOTAL dia hábil dentro del horario de mercado tipo : T1 desde detalle")
    public void DTC14889BSC(){
        try{
            testCaseData.cargaDeDatosTests("ATC14877BSC");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.ESCRITORIO);
            inversionesSteps.validarTituloPageGenerico("Hola");
            menuLateralSteps.clickCuentas();
            cuentasSteps.capturarSaldoInicialEnCuenta(testCaseData.cuentaOrigen);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            fondoComunInversionSteps.clickIconoAccionesDetalleFCI(DELTA_MONEDA_UNISYS);
            fondoComunInversionSteps.clickRescate();
            rescateFCISteps.clickTotal();
            UtilsGP.fixedWait(4000);
            rescateFCISteps.clickContinuarGenerico();
            rescateFCISteps.clickCheckTerminosCondicionesGenerico();
            rescateFCISteps.clickConfirmarGenerico();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
        }catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test //APAGADO HASTA IMPLEMENTAR V68
    @DisplayName("TC-14903-BSC: Rescate Total dia hábil dentro del horario de mercado tipo : T0 desde Grilla")
    public void ETC14903BSC(){
        try{
            importe = "0";
            testCaseData.cargaDeDatosTests("ATC14877BSC");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.ESCRITORIO);
            inversionesSteps.validarTituloPageGenerico("Hola");
            menuLateralSteps.clickCuentas();
            cuentasSteps.capturarSaldoInicialEnCuenta(testCaseData.cuentaOrigen);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            fondoComunInversionSteps.clickRescatar(DELTA_PESOS_UNISYS);
            rescateFCISteps.clickTotal();
            cuentasSteps.capturaImporteTotal("pesos");
            UtilsGP.fixedWait(4000);
            rescateFCISteps.clickContinuarGenerico();
            rescateFCISteps.clickCheckTerminosCondicionesGenerico();
            rescateFCISteps.clickConfirmarGenerico();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            menuLateralSteps.clickCuentas();
            cuentasSteps.capturarSaldoActualizadoEnCuenta(testCaseData.cuentaOrigen);
            cuentasSteps.verificarIncrementoDeSaldoEnCuenta(importe);
        } catch (Exception ex) {
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
