package GP.EntreRios.inversiones.FCIEsco;

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
public class SuscripcionDentroHorarioMercadoUSDTest {
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
    private String importe = "5000";

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

    //DIA HABIL DENTRO DEL HORARIO DE MERCADO USD
    @Test
    @DisplayName("TC-19024 - suscripcion USD Dia habil dentro del horario de mercado desde grilla T0")//**OK**
    public void ATC19024BERSA(){
        try{
            testCaseData.cargaDeDatosTests("ATC19024BERSA");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.ESCRITORIO);
            inversionesSteps.validarTituloPageGenerico("Hola");
            menuLateralSteps.clickCuentas();
            cuentasSteps.clickVerMas();
            cuentasSteps.capturarSaldoInicialEnCuentaUSD(testCaseData.cuentaOrigen);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            fondoComunInversionSteps.clickSuscribir(DELTA_DOLARES_UNISYS);
            suscripcionFCISteps.setImporteSuscribirFci(importe);
            UtilsGP.fixedWait(4000);
            suscripcionFCISteps.clickContinuarGenerico();
            suscripcionFCISteps.clickCheckbox();
            suscripcionFCISteps.clickConfirmarGenerico();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            menuLateralSteps.clickCuentas();
            cuentasSteps.capturarSaldoActualizadoEnCuentaUSD(testCaseData.cuentaOrigen);
            cuentasSteps.verificarDescuentoDeSaldoEnCuenta(importe);
        }catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-19016 - suscripcion USD Dia habil dentro del horario de mercado desde detalle T0")//**OK**
    public void BTC19016BERSA(){
        try{
            testCaseData.cargaDeDatosTests("ATC19024BERSA");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.ESCRITORIO);
            inversionesSteps.validarTituloPageGenerico("Hola");
            menuLateralSteps.clickCuentas();
            cuentasSteps.clickVerMas();
            cuentasSteps.capturarSaldoInicialEnCuentaUSD(testCaseData.cuentaOrigen);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            fondoComunInversionSteps.clickIconoAccionesDetalleFCI(DELTA_DOLARES_UNISYS);
            fondoComunInversionSteps.clickSuscribir();
            suscripcionFCISteps.setImporteSuscribirFci(importe);
            UtilsGP.fixedWait(4000);
            suscripcionFCISteps.clickContinuarGenerico();
            suscripcionFCISteps.clickCheckbox();
            suscripcionFCISteps.clickConfirmarGenerico();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            menuLateralSteps.clickCuentas();
            cuentasSteps.capturarSaldoActualizadoEnCuentaUSD(testCaseData.cuentaOrigen);
            cuentasSteps.verificarDescuentoDeSaldoEnCuenta(importe);
        }catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-19028 - suscripcion USD Dia habil dentro del horario de mercado desde grilla T2")//**OK**
    public void CTC19028BERSA(){
        try{
            testCaseData.cargaDeDatosTests("ATC19024BERSA");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.ESCRITORIO);
            inversionesSteps.validarTituloPageGenerico("Hola");
            menuLateralSteps.clickCuentas();
            cuentasSteps.clickVerMas();
            cuentasSteps.capturarSaldoInicialEnCuentaUSD(testCaseData.cuentaOrigen);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            fondoComunInversionSteps.clickSuscribir(DELTA_SHCRODER_INCOME_DOLARES_UNISYS);
            suscripcionFCISteps.setImporteSuscribirFci(importe);
            UtilsGP.fixedWait(4000);
            suscripcionFCISteps.clickContinuarGenerico();
            suscripcionFCISteps.clickCheckbox();
            suscripcionFCISteps.clickConfirmarGenerico();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            menuLateralSteps.clickCuentas();
            cuentasSteps.capturarSaldoActualizadoEnCuentaUSD(testCaseData.cuentaOrigen);
            cuentasSteps.verificarDescuentoDeSaldoEnCuenta(importe);
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-19020 - suscripcion USD Dia habil dentro del horario de mercado desde detalle T2")//**OK**
    public void DTC19020BERSA(){
        try{
            testCaseData.cargaDeDatosTests("ATC19024BERSA");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.ESCRITORIO);
            inversionesSteps.validarTituloPageGenerico("Hola");
            menuLateralSteps.clickCuentas();
            cuentasSteps.clickVerMas();
            cuentasSteps.capturarSaldoInicialEnCuentaUSD(testCaseData.cuentaOrigen);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            fondoComunInversionSteps.clickIconoAccionesDetalleFCI(DELTA_SHCRODER_INCOME_DOLARES_UNISYS);
            fondoComunInversionSteps.clickSuscribir();
            suscripcionFCISteps.setImporteSuscribirFci(importe);
            UtilsGP.fixedWait(4000);
            suscripcionFCISteps.clickContinuarGenerico();
            suscripcionFCISteps.clickCheckbox();
            suscripcionFCISteps.clickConfirmarGenerico();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            menuLateralSteps.clickCuentas();
            cuentasSteps.capturarSaldoActualizadoEnCuentaUSD(testCaseData.cuentaOrigen);
            cuentasSteps.verificarDescuentoDeSaldoEnCuenta(importe);
        }catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-10003 - Precondición - Suscripciones T2 Por API para dia/Horario NO hábil")//**OK**
    public void ETC10003BERSA(){
        try {
            //suscripcionFCISteps.generarFciAPI(testCaseData.usuario,testCaseData.cuentaComitente,DELTA_DOLARES_ID,10000,testCaseData.cuentaOrigen,testCaseData.password );
            suscripcionFCISteps.generarFciAPI(testCaseData.usuario,testCaseData.cuentaComitente,DELTA_SCHODER_INCOME_DOLARES_ID,10000,testCaseData.cuentaOrigen, testCaseData.password);
        }catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-10004 - Validación Tenencia Total - Verificación de Monto")//**OK**
    public void FTC10004BERSA(){
        try{
            testCaseData.cargaDeDatosTests("ATC19024BERSA");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.ESCRITORIO);
            inversionesSteps.validarTituloPageGenerico("Hola");
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            fondoComunInversionSteps.verificarTenenciaTotalUSD();
        }catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-10005 - Validación Tenencia Valorizada - Verificación de Monto")//**OK**
    public void GTC10005BERSA(){
        try{
            testCaseData.cargaDeDatosTests("ATC19024BERSA");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.ESCRITORIO);
            inversionesSteps.validarTituloPageGenerico("Hola");
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            fondoComunInversionSteps.clickIconoAccionesDetalleFCI(DELTA_DOLARES_UNISYS);
            fondoComunInversionSteps.capturarValorSuscripcionEnCursoActualizada();
            fondoComunInversionSteps.validarTenenciaUSD();
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
