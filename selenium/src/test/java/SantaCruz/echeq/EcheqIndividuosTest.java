package SantaCruz.echeq;
import Config.Settings;
import Config.TestCaseData;
import Steps.Compartidos.CompartidosAPI;
import Steps.Echeq.EcheqSteps;
import Steps.Login.LoginApiSteps;
import org.GP_Individuos_API.ConexionDB;
import org.GP_Individuos_API.Utils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class EcheqIndividuosTest {

    LoginApiSteps loginApiSteps = new LoginApiSteps();
    CompartidosAPI compartidosAPI = new CompartidosAPI();
    TestCaseData testCaseData = new TestCaseData();
    EcheqSteps echeqSteps = new EcheqSteps();
    Utils utils = new Utils();

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
    @DisplayName("TC-25610- BSC: WEB - Web-Validar que al consultar el listado de echeq en la Estado: 'Todos'")
    public void TC25610BSC() {
        //Test
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC25610BSC");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Validaciones listado de Echeq completa estado "Todos"
        echeqSteps.validaEcheqs("Todos", "");
    }

    @Test
    @DisplayName("TC-124907-BSC- Web-Consulta del detalle de un " +
            "eCHEQ-Servicios nuevos API rest-Estado Aceptar")
    public void TC124907BSC() {
        testCaseData.cargaDeDatosTest("TC124907BSC");
        //logueo en individuos
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        echeqSteps.generacionDeAceptaciondeEcheq("EMITIDO-PENDIENTE", utils.sumarFechaHoyPorDiasSinHora(0));
        //TEST
        echeqSteps.validaEcheqs("ACTIVO", "");
    }

    @Test
    @DisplayName("TC-124908-BSC- Web-Consulta del detalle de un eCHEQ-Servicios nuevos API rest-Estado Endosar")
    public void TC124908BSC() {
        testCaseData.cargaDeDatosTest("TC124908BSC");
        //logueo en individuos
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        echeqSteps.generacionDeAceptaciondeEcheq("EMITIDO-PENDIENTE", utils.sumarFechaHoyPorDiasSinHora(0));
        echeqSteps.generacionDeEndosodeEcheq(testCaseData.cuitDestino);
        //TEST
        echeqSteps.validaEcheqs("ACTIVO-PENDIENTE", "");
    }

    @Test
    @DisplayName("TC-124909- BSC: Web-Consulta del detalle de un eCHEQ-Servicios nuevos API rest-'Estado Emitir'")
    public void TC124909BSC() {
        //Test
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC124909BSC");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Validaciones listado de Echeq completa estado "Todos"
        echeqSteps.validaEcheqs("EMITIDO-PENDIENTE", "");
    }

    @Test
    @DisplayName("TC-124910-BSC- Web-Consulta del detalle de un eCHEQ-Servicios nuevos API rest-Estado Repudiar")
    public void TC124910BSC() {
        testCaseData.cargaDeDatosTest("TC124910BSC");
        //logueo en individuos
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        echeqSteps.generacionRepudioDeEcheq("EMITIDO-PENDIENTE");
        //TEST
        echeqSteps.validaEcheqs("REPUDIADO", "");
    }

    @Test
    @DisplayName("TC-124911-BSC: Web-Consulta del detalle de un eCHEQ-Servicios nuevos " +
            "API rest-Estado Custodiar")
    public void TC124911BSC() {
        testCaseData.cargaDeDatosTest("TC124911BSC");
        //logueo en individuos
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //ejecucion de servicios compartidos datos de cuentas y datos de tarjetas
        compartidosAPI.cargaServiciosCompartidos();
        //obtencion de datos para realizar la transferencia
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        //Genera aceptación y custodio del Echeq
        echeqSteps.generacionDeAceptaciondeEcheq("EMITIDO-PENDIENTE", utils.sumarFechaHoyPorDiasSinHora(60));
        echeqSteps.generacionCustodioDeEcheq("ACTIVO");
        //TEST
        echeqSteps.validaEcheqs("CUSTODIA", "");
    }

    @Test
    @DisplayName("TC-124912-BSC: Web-Consulta del detalle de un eCHEQ-Servicios nuevos " +
            "API rest-Estado Custodiar")
    public void TC124912BSC() {
        testCaseData.cargaDeDatosTest("TC124912BSC");
        //logueo en individuos
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //ejecucion de servicios compartidos datos de cuentas y datos de tarjetas
        compartidosAPI.cargaServiciosCompartidos();
        //obtencion de datos
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        //Generación de estados del Echeq
        echeqSteps.generacionDeAceptaciondeEcheq("EMITIDO-PENDIENTE", utils.sumarFechaHoyPorDiasSinHora(60));
        echeqSteps.generacionCustodioDeEcheq("Todos");
        echeqSteps.generacionRescateDeEcheq("Todos");
        //TEST
        echeqSteps.validaEcheqs("ACTIVO", "Recibidos");
    }

    @Test
    @DisplayName("TC-124913-BSC: Web-Consulta del detalle de un eCHEQ-Servicios nuevos API rest-Estado Anular")
    public void TC124913BSC() {
        testCaseData.cargaDeDatosTest("TC124913BSC");
        //logueo en individuos
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //TEST
        echeqSteps.validaEcheqs("ANULADO", "");
    }

    @Test
    @DisplayName("TC-128237-BSC- Web-Solicitar devolución ECHEQ-Servicio nuevo API rest")
    public void TC128237BSC() {
        testCaseData.cargaDeDatosTest("TC128237BSC");
        //logueo en individuos
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        echeqSteps.generacionDeAceptaciondeEcheq("EMITIDO-PENDIENTE", utils.sumarFechaHoyPorDiasSinHora(0));
        echeqSteps.generacionDeEndosodeEcheq(testCaseData.cuitDestino);
        loginApiSteps.loginUsuario(testCaseData.usuario2,testCaseData.password2);
        echeqSteps.aceptarEcheqEndosado("ACTIVO-PENDIENTE");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        echeqSteps.generacionDevolucionDelEcheq();
        //TEST
        echeqSteps.validaEcheqs("DEVOLUCION-PENDIENTE", "Recibidos");
    }

    @Test
    @DisplayName("TC-128231-BSC: Web-Depositar eCHEQ-Servicios nuevos API rest-Variable Encendida")
    public void TC128231BSC() {
        testCaseData.cargaDeDatosTest("TC128231BSC");
        //logueo en individuos
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        compartidosAPI.cargaServiciosCompartidos();
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        echeqSteps.generacionDeAceptaciondeEcheq("EMITIDO-PENDIENTE", utils.sumarFechaHoyPorDiasSinHora(0));
        echeqSteps.generacionDepositoDeEcheq("ACTIVO");
        //TEST
        echeqSteps.validaEcheqs("DEPOSITADO", "");
    }
}
