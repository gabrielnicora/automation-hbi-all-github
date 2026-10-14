package EntreRios.cuentas;

import Config.Settings;
import Config.TestCaseData;
import Steps.Compartidos.CompartidosAPI;
import Steps.Cuentas.CuentasSteps;
import Steps.Login.LoginApiSteps;
import org.GP_Individuos_API.ConexionDB;
import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.MethodName.class)
public class CuentasTest {
    LoginApiSteps loginApiSteps = new LoginApiSteps();
    CompartidosAPI compartidosAPI = new CompartidosAPI();
    TestCaseData testCaseData = new TestCaseData();
    CuentasSteps cuentas = new CuentasSteps();

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

    @Test
    @Tag("Criticos")
    @DisplayName("TC- TC0006-BERSA - Adherir Cuenta  - NuevoAdherir Cuenta  - Nuevo destinatario  Alias Terceros Descarga e impresión del PDF2do factor De autenticación Vinculado a SMS")
    public void TC0006BERSA(){
        //carga de los datos necesarios para realizar el test
        testCaseData.cargaDeDatosTest("TC0006BERSA");
        //login de usuarios
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        //carga de servicios para extraer idProductos de Cuentas o Tarjetas - widgets.Account - widgets.CreditCards
        compartidosAPI.cargaServiciosCompartidos();
        //obtenemos el idProducto de la cuenta
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        //Consultamos CBU
        cuentas.consultarDestinatarioAgendaCBU("3860006503000031964422","CBU");
        //Elimina destinatario si esta agendado
        cuentas.eliminarDestinatarioAgendaCBU();
        //Adherimos cuenta nueva
        cuentas.agregarDestinatarioAgendaCBU("3860006503000031964422","CBU");
    }

    @Test
    @DisplayName("TC-0229-BERSA: Adelantos de Sueldo - Mis adelantos - Validar card de adelantos de sueldo")
    public void TC0229BERSA(){
        //carga de los datos necesarios para realizar el test
        testCaseData.cargaDeDatosTest("TC0229BERSA");
        //login de usuarios
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //validar card de adelanto de sueldo
        cuentas.validarCardAdelantoDeSueldo();
    }

    @Test
    @Tag("Criticos")
    @DisplayName("TC- TC0027-BERSA - Generar Orden de Extracción_Con Saldo disponible en Cuenta.")
    public void TC0027BERSA() {
        //carga de los datos necesarios para realizar el test
        testCaseData.cargaDeDatosTest("TC0027BERSA");
        //login de usuarios
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //carga de servicios para extraer idProductos de Cuentas o Tarjetas - widgets.Account - widgets.CreditCards
        compartidosAPI.cargaServiciosCompartidos();
        //Obtenemos el number de la cuenta limpio
        compartidosAPI.obtenerNumberCuenta(testCaseData.cuentaOrigen);
        //obtenemos el idProducto de la cuenta
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        //Generamos la orden de extracion
        cuentas.generarOrdenDeExtracion(testCaseData.cuentaOrigen);
    }

    @Test
    @DisplayName("TC-0223-BERSA: Adelantos de Sueldo - Realizar solicitud de adelanto de sueldo  - Validar pantalla de confimacion de solicitud.")
    public void TC0223BERSA() {
        //carga de los datos necesarios para realizar el test
        testCaseData.cargaDeDatosTest("TC0223BERSA");
        //login de usuarios
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //carga de servicios para extraer idProductos de Cuentas o Tarjetas - widgets.Account - widgets.CreditCards
        compartidosAPI.cargaServiciosCompartidos();
        //obtenemos el idProducto de la cuenta
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        //Obtenemos la segunda tanda de datos que necesitamos enviar para adelantar el sueldo el servicio salaryAdvancePre
        cuentas.obtenerDatosAdelantoDeSueldo(testCaseData.cuentaOrigen);
        //Obtenemos la primera tanda de datos que necesitamos enviar para adelantar el sueldo desde el servicio sealLoansByAmount
        cuentas.obtenerDatosMontosAcreditacionAdelantoDeSueldo();
        //Solicitamos el adelanto de sueldo
        cuentas.adelantoDeSueldo();

    }
}

