package EntreRios.echeq;
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
    @DisplayName("TC-25610- BERSA: WEB - Web-Validar que al consultar el listado de echeq en la Estado: 'Todos'")
    public void TC25610BERSA() {
        //Test
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC25610BERSA");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Validaciones listado de Echeq completa estado "Todos"
        echeqSteps.validaEcheqs("Todos", "");
    }

    @Test
    @DisplayName("TC-124907-BERSA- Web-Consulta del detalle de un " +
            "eCHEQ-Servicios nuevos API rest-Estado Aceptar")
    public void TC124907BERSA() {
        testCaseData.cargaDeDatosTest("TC124907BERSA");
        //logueo en individuos
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        echeqSteps.generacionDeAceptaciondeEcheq("EMITIDO-PENDIENTE", utils.sumarFechaHoyPorDiasSinHora(0));
        //TEST
        echeqSteps.validaEcheqs("ACTIVO", "");
    }

    @Test
    @DisplayName("TC-124908-BERSA- Web-Consulta del detalle de un eCHEQ-Servicios nuevos API rest-Estado Endosar")
    public void TC124908BERSA() {
        testCaseData.cargaDeDatosTest("TC124908BERSA");
        //logueo en individuos
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        echeqSteps.generacionDeAceptaciondeEcheq("EMITIDO-PENDIENTE", utils.sumarFechaHoyPorDiasSinHora(0));
        echeqSteps.generacionDeEndosodeEcheq(testCaseData.cuitDestino);
        //TEST
        echeqSteps.validaEcheqs("ACTIVO-PENDIENTE", "");
    }

    @Test
    @DisplayName("TC-124909- BERSA: Web-Consulta del detalle de un eCHEQ-Servicios nuevos API rest-'Estado Emitir'")
    public void TC124909BERSA() {
        //Test
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC124909BERSA");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Validaciones listado de Echeq completa estado "Todos"
        echeqSteps.validaEcheqs("EMITIDO-PENDIENTE", "");
    }

    @Test
    @DisplayName("TC-124910-BERSA- Web-Consulta del detalle de un eCHEQ-Servicios nuevos API rest-Estado Repudiar")
    public void TC124910BERSA() {
        testCaseData.cargaDeDatosTest("TC124910BERSA");
        //logueo en individuos
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        echeqSteps.generacionRepudioDeEcheq("EMITIDO-PENDIENTE");
        //TEST
        echeqSteps.validaEcheqs("REPUDIADO", "");
    }

    @Test
    @DisplayName("TC-124911-BERSA: Web-Consulta del detalle de un eCHEQ-Servicios nuevos " +
            "API rest-Estado Custodiar")
    public void TC124911BERSA() {
        testCaseData.cargaDeDatosTest("TC124911BERSA");
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
    @DisplayName("TC-124912-BERSA: Web-Consulta del detalle de un eCHEQ-Servicios nuevos " +
            "API rest-Estado Custodiar")
    public void TC124912BERSA() {
        testCaseData.cargaDeDatosTest("TC124912BERSA");
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
    @DisplayName("TC-124913-BERSA: Web-Consulta del detalle de un eCHEQ-Servicios nuevos API rest-Estado Anular")
    public void TC124913BERSA() {
        testCaseData.cargaDeDatosTest("TC124913BERSA");
        //logueo en individuos
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //TEST
        echeqSteps.validaEcheqs("ANULADO", "");
    }

    @Test
    @DisplayName("TC-128237-BERSA- Web-Solicitar devolución ECHEQ-Servicio nuevo API rest")
    public void TC128237BERSA() {
        testCaseData.cargaDeDatosTest("TC128237BERSA");
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
    @DisplayName("TC-128231-BERSA: Web-Depositar eCHEQ-Servicios nuevos API rest-Variable Encendida")
    public void TC128231BERSA() {
        testCaseData.cargaDeDatosTest("TC128231BERSA");
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
