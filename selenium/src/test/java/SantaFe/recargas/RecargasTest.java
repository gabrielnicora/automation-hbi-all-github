package SantaFe.recargas;

import Config.Settings;
import Config.TestCaseData;
import Steps.Compartidos.CompartidosAPI;
import Steps.Login.LoginApiSteps;
import Steps.Pagos.PagosSteps;
import Steps.Recargas.RecargasStep;

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
    @DisplayName(" TC0071-BSF - RECARGAS - Realizar recarga a Transporte Publico - SUBE")
    public void TC0071BSF(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0071BSF");
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
    @DisplayName(" TC0072-BSF - Recargas - Adhesion para recarga")
    public void TC0072BSF(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0072BSF");
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
    @DisplayName("TC12956-BSF - RECARGAS - Eliminar adhesión")
    public void TC12956BSF(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC12956BSF");
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
    @DisplayName("TC12952-BSF - RECARGAS - Realizar recarga a Telefonía Celular - PERSONAL LIGHT")
    public void TC12952BSF(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC12952BSF");
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
    @DisplayName("TC-12954-BSF - RECARGAS - Realizar recarga a Billetera/Serv. Prepago")
    public void TC12954BSF(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC12954BSF");
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
    @DisplayName("TC-0106-BSF: Recargas - Consulta de recargas")
    public void TC0106BSF() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0106BSF");
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
    @DisplayName("TC-0107-BSF: Recargas - Consulta de recargas - Botón descarga de comprobante")
    public void TC0107BSF() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0107BSF");
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
