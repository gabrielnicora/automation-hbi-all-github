package SanJuan.recargas;

import Config.Settings;
import Config.TestCaseData;
import Steps.Compartidos.CompartidosAPI;
import Steps.Login.LoginApiSteps;
import Steps.Pagos.PagosSteps;
import Steps.Recargas.RecargasStep;
import Steps.Transferencias.TransferenciasSteps;
import jdk.jfr.Description;

import javax.print.attribute.standard.Media;

import org.GP_Individuos_API.ConexionDB;
import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.MethodName.class)
public class RecargasTest {

    LoginApiSteps loginApiSteps = new LoginApiSteps();
    RecargasStep recargasStep = new RecargasStep();
    CompartidosAPI compartidosAPI = new CompartidosAPI();
    TestCaseData testCaseData = new TestCaseData();
    PagosSteps pagosSteps = new PagosSteps();

    private static  String SUBE_ID= "618";
    private static  String NUM_SUBE = "6061267195495203";
    private static  String PERSONAL_LIGHT_ID = "602";
    private static  String NUMERO_TEL = "1168203779";

    @BeforeAll
    public static void cagarDatos(){
        if(Settings.dataTestCase == null){
            System.out.println("============= Array de datos vacio extraer desde la BD ==============");
            Settings.dataTestCase = ConexionDB.extraerUsuariosDB();
            System.out.println("============= se completo el array de datos ==========================");
        }else {
            System.out.println("================ El Array contiene datos ===================");
        }
    }

    //@Test
    @DisplayName(" TC0071-BSJ - RECARGAS - Realizar recarga a Transporte Publico - SUBE")
    public void TC0071BSJ(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0071BSJ");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //ejecucion de servicios compartidos datos de cuentas y datos de tarjetas
        compartidosAPI.cargaServiciosCompartidos();
        //obtenemos el idProducto de la cuenta
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        //obtener el idProducto de la tarjeta de debito
        compartidosAPI.obtenerIdDeTarjeta(testCaseData.debitCard);
        //Sincronizamos los nuevos servicos de pagos por API LINK
        pagosSteps.sincronizacionPagosLink();
        //realizar la recarga de la tarjeta sube
        recargasStep.recargaSube();
    }

    //@Test
    @Order(1)
    @DisplayName(" TC0072-BSJ - Recargas - Adhesion para recarga")
    public void TC0072BSJ(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0072BSJ");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //ejecucion de servicios compartidos datos de cuentas y datos de tarjetas
        compartidosAPI.cargaServiciosCompartidos();
        //obtener el idProducto de la tarjeta de debito
        compartidosAPI.obtenerIdDeTarjeta(testCaseData.debitCard);
        //Sincronizamos los nuevos servicos de pagos por API LINK
        pagosSteps.sincronizacionPagosLink();
        //CONSULTAR SI EL SERVICIO APARECE ADHERIDO - EN CASO DE QUE ESTE LO ELIMNA
        recargasStep.consultarAdhesionServicioAPI("618","6061267195495203");
        //ADHERIR SERVICIO API
        recargasStep.adhesionServicioSUBEAPI(SUBE_ID,NUM_SUBE,"JUAN");
    }
    //@Test
    @Order(2)
    @DisplayName("TC12956-BSJ - RECARGAS - Eliminar adhesión")
    public void TC12956BSJ(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC12956BSJ");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //ejecucion de servicios compartidos datos de cuentas y datos de tarjetas
        compartidosAPI.cargaServiciosCompartidos();
        //obtener el idProducto de la tarjeta de debito
        compartidosAPI.obtenerIdDeTarjeta(testCaseData.debitCard);
        //Sincronizamos los nuevos servicos de pagos por API LINK
        pagosSteps.sincronizacionPagosLink();
        //eliminamos el servicio adherido
        recargasStep.eliminarAdhesionSUBEAPI(SUBE_ID,NUM_SUBE,"JUAN");
    }
    //@Test
    @DisplayName("TC12952-BSJ - RECARGAS - Realizar recarga a Telefonía Celular - PERSONAL LIGHT")
    public void TC12952BSJ(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC12952BSJ");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //ejecucion de servicios compartidos datos de cuentas y datos de tarjetas
        compartidosAPI.cargaServiciosCompartidos();
        //obtenemos el idProducto de la cuenta
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        //obtener el idProducto de la tarjeta de debito
        compartidosAPI.obtenerIdDeTarjeta(testCaseData.debitCard);
        //Sincronizamos los nuevos servicos de pagos por API LINK
        pagosSteps.sincronizacionPagosLink();
        //realizar la recarga de la tarjeta sube
        recargasStep.recargaPersonalLight();
    }

    //@Test
    @DisplayName("TC-12954-BSJ - RECARGAS - Realizar recarga a Billetera/Serv. Prepago")
    public void TC12954BSJ(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC12954BSJ");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //ejecucion de servicios compartidos datos de cuentas y datos de tarjetas
        compartidosAPI.cargaServiciosCompartidos();
        //obtenemos el idProducto de la cuenta
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        //obtener el idProducto de la tarjeta de debito
        compartidosAPI.obtenerIdDeTarjeta(testCaseData.debitCard);
        //Sincronizamos los nuevos servicos de pagos por API LINK
        pagosSteps.sincronizacionPagosLink();
        //realizar la recarga de la tarjeta de DirectTV
        recargasStep.recargaDirectTV();
    }
    //@Test
    @DisplayName("TC-0106-BSJ: Recargas - Consulta de recargas")
    public void TC0106BSJ() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0106BSJ");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //ejecucion de servicios compartidos datos de cuentas y datos de tarjetas
        compartidosAPI.cargaServiciosCompartidos();
        //obtener el idProducto de la tarjeta de debito
        compartidosAPI.obtenerIdDeTarjeta(testCaseData.debitCard);
        //Sincronizamos los nuevos servicos de pagos por API LINK
        pagosSteps.sincronizacionPagosLink();
        //Consultas de recargas de sube
        recargasStep.consultaRecargasRealizadas();
    }

    //@Test
    @DisplayName("TC-0107-BSJ: Recargas - Consulta de recargas - Botón descarga de comprobante")
    public void TC0107BSJ() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0107BSJ");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //ejecucion de servicios compartidos datos de cuentas y datos de tarjetas
        compartidosAPI.cargaServiciosCompartidos();
        //obtener el idProducto de la tarjeta de debito
        compartidosAPI.obtenerIdDeTarjeta(testCaseData.debitCard);
        //Sincronizamos los nuevos servicos de pagos por API LINK
        pagosSteps.sincronizacionPagosLink();
        //Obtener datos de la primera recarga del listado
        recargasStep.consultaRecargasRealizadas();
        //descarga del primer comprobante
        recargasStep.descargaPrimerTicketRecarga();
    }
}
