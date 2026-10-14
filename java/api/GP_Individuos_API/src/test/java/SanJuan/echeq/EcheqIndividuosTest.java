package SanJuan.echeq;
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
    TestCaseData testCaseData = new TestCaseData();
    EcheqSteps echeqSteps = new EcheqSteps();
    CompartidosAPI compartidosAPI = new CompartidosAPI();
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
    @DisplayName("TC-25610- BSJ: WEB - Web-Validar que al consultar el listado de echeq en la Estado: 'Todos'")
    public void TC25610BSJ() {
        //Test
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC25610BSJ");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Validaciones listado de Echeq completa estado "Todos"
        echeqSteps.validaEcheqs("Todos", "");
    }

    @Test
    @DisplayName("TC-124907-BSJ- Web-Consulta del detalle de un " +
            "eCHEQ-Servicios nuevos API rest-Estado Aceptar")
    public void TC124907BSJ() {
        testCaseData.cargaDeDatosTest("TC124907BSJ");
        //logueo en individuos
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        echeqSteps.generacionDeAceptaciondeEcheq("EMITIDO-PENDIENTE", utils.sumarFechaHoyPorDiasSinHora(0));
        //TEST
        echeqSteps.validaEcheqs("ACTIVO", "");
    }

    @Test
    @DisplayName("TC-124908-BSJ- Web-Consulta del detalle de un eCHEQ-Servicios nuevos API rest-Estado Endosar")
    public void TC124908BSJ() {
        testCaseData.cargaDeDatosTest("TC124908BSJ");
        //logueo en individuos
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        echeqSteps.generacionDeAceptaciondeEcheq("EMITIDO-PENDIENTE", utils.sumarFechaHoyPorDiasSinHora(0));
        echeqSteps.generacionDeEndosodeEcheq(testCaseData.cuitDestino);
        //TEST
        echeqSteps.validaEcheqs("ACTIVO-PENDIENTE", "");
    }

    @Test
    @DisplayName("TC-124909- BSJ: Web-Consulta del detalle de un eCHEQ-Servicios nuevos API rest-'Estado Emitir'")
    public void TC124909BSJ() {
        //Test
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC124909BSJ");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Validaciones listado de Echeq completa estado "Todos"
        echeqSteps.validaEcheqs("EMITIDO-PENDIENTE", "");
    }

    @Test
    @DisplayName("TC-124910-BSJ- Web-Consulta del detalle de un eCHEQ-Servicios nuevos API rest-Estado Repudiar")
    public void TC124910BSJ() {
        testCaseData.cargaDeDatosTest("TC124910BSJ");
        //logueo en individuos
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        echeqSteps.generacionRepudioDeEcheq("EMITIDO-PENDIENTE");
        //TEST
        echeqSteps.validaEcheqs("REPUDIADO", "");
    }

    @Test
    @DisplayName("TC-124911-BSJ: Web-Consulta del detalle de un eCHEQ-Servicios nuevos " +
            "API rest-Estado Custodiar")
    public void TC124911BSJ() {
        testCaseData.cargaDeDatosTest("TC124911BSJ");
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
    @DisplayName("TC-124912-BSJ: Web-Consulta del detalle de un eCHEQ-Servicios nuevos " +
            "API rest-Estado Custodiar")
    public void TC124912BSJ() {
        testCaseData.cargaDeDatosTest("TC124912BSJ");
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
    @DisplayName("TC-124913-BSJ: Web-Consulta del detalle de un eCHEQ-Servicios nuevos API rest-Estado Anular")
    public void TC124913BSJ() {
        testCaseData.cargaDeDatosTest("TC124913BSJ");
        //logueo en individuos
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //TEST
        echeqSteps.validaEcheqs("ANULADO", "");
    }

    @Test
    @DisplayName("TC-128237-BSJ- Web-Solicitar devolución ECHEQ-Servicio nuevo API rest")
    public void TC128237BSJ() {
        testCaseData.cargaDeDatosTest("TC128237BSJ");
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
    @DisplayName("TC-128231-BSJ: Web-Depositar eCHEQ-Servicios nuevos API rest-Variable Encendida")
    public void TC128231BSJ() {
        testCaseData.cargaDeDatosTest("TC128231BSJ");
        //logueo en individuos
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        compartidosAPI.cargaServiciosCompartidos();
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        echeqSteps.generacionDeAceptaciondeEcheq("EMITIDO-PENDIENTE", utils.sumarFechaHoyPorDiasSinHora(0));
        echeqSteps.generacionDepositoDeEcheq("ACTIVO");
        //TEST
        echeqSteps.validaEcheqs("DEPOSITADO", "");
    }

    @Test
    @DisplayName("TC-29486- BSF: Web-Tabla Echeq-Modificaciones-Echeqs del cliente por default")
    public void TC29486BSJ() {
        //Test
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC29486BSJ");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Validaciones listado de Echeq completa estado "Todos"
        echeqSteps.validaEcheqsPorDefaultRecibidos("Todos", "Recibidos");
    }

    @Test
    @DisplayName("TC-29490- BSJ: WEB-  Consultar en tabla  ECheq tab Emitidos  ECheq generado")
    public void TC29490BSJ() {
        //Test
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC29490BSJ");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Validaciones listado de Echeq completa estado "Todos"
        echeqSteps.validaEcheqsPorDefaultEmitidos("Todos", "Emitidos");
    }

}
