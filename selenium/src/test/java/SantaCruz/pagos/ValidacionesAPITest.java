package SantaCruz.pagos;

import Config.Settings;
import Config.TestCaseData;
import Steps.Compartidos.CompartidosAPI;
import Steps.Cuentas.CuentasSteps;
import Steps.Login.LoginApiSteps;
import org.GP_Individuos_API.ConexionDB;
import org.junit.jupiter.api.*;
import Steps.Pagos.PagosSteps;

@TestMethodOrder(MethodOrderer.MethodName.class)
public class ValidacionesAPITest {
    LoginApiSteps loginApiSteps = new LoginApiSteps();
    CompartidosAPI compartidosAPI = new CompartidosAPI();
    TestCaseData testCaseData = new TestCaseData();
    CuentasSteps cuentas = new CuentasSteps();
    PagosSteps pagos = new PagosSteps();

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

    //// Validar Servicio /account.transfer.search ////

//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("Validar mensaje de error typeAccount con espacio")
//    public void TC29450BSC(){
//        //carga de los datos necesarios para realizar el test
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        //login de usuarios
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        //Consultamos CBU2
//        cuentas.validarErroresAccountTransferSearch("VOLPATO.PESOS.BSC", " A LIAS");
//    }
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29451-BSC - Validar error por typeAccount con acento")
//    public void TC29451BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        cuentas.validarErroresAccountTransferSearch("VOLPATO.PESOS.BSC", "ÁLIAS");
//    }
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29452-BSC - Validar error por typeAccount con guión")
//    public void TC29452BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        cuentas.validarErroresAccountTransferSearch("VOLPATO.PESOS.BSC", "A-LIAS");
//    }
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29453-BSC - Validar error por typeAccount con longitud no permitida")
//    public void TC29453BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        cuentas.validarErroresAccountTransferSearch("VOLPATO.PESOS.BSC", "ALIASSSS");
//    }
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29454-BSC - Validar error por typeAccount con apóstrofe")
//    public void TC29454BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        cuentas.validarErroresAccountTransferSearch("VOLPATO.PESOS.BSC", "A'LIAS");
//    }
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29455-BSC - Validar error por typeAccount vacío")
//    public void TC29455BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        cuentas.validarErroresAccountTransferSearch("VOLPATO.PESOS.BSC", "");
//    }
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29456-BSC - Validar error por typeAccount null")
//    public void TC29456BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        cuentas.validarErroresAccountTransferSearchTipoNull("VOLPATO.PESOS.BSC");
//    }
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29457-BSC - Validar error por typeAccount alfabético no permitido")
//    public void TC29457BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        cuentas.validarErroresAccountTransferSearch("VOLPATO.PESOS.BSC", "ALIASUNO");
//    }
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29458-BSC - Validar error por typeAccount numérico")
//    public void TC29458BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        cuentas.validarErroresAccountTransferSearch("VOLPATO.PESOS.BSC", "123456");
//    }
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29459-BSC - Validar error por typeAccount alfanumérico")
//    public void TC29459BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        cuentas.validarErroresAccountTransferSearch("VOLPATO.PESOS.BSC", "ALIAS1");
//    }
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29460-BSC - Validar error por typeAccount con caracteres especiales")
//    public void TC29460BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        cuentas.validarErroresAccountTransferSearch("VOLPATO.PESOS.BSC", "@#$%&_");
//    }
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29461-BSC - Validar error por typeAccount boolean true")
//    public void TC29461BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        cuentas.validarErroresAccountTransferSearchTrueFalseAliasCbu("VOLPATO.PESOS.BSC", false);
//    }
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29462-BSC - Validar error por typeAccount boolean false")
//    public void TC29462BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        cuentas.validarErroresAccountTransferSearchTrueFalseAliasCbu("VOLPATO.PESOS.BSC", false);
//    }
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29463-BSC - Validar error por typeAccount en minúsculas")
//    public void TC29463BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        cuentas.validarErroresAccountTransferSearch("VOLPATO.PESOS.BSC", "alias");
//    }
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29464-BSC - Validar error por typeAccount con prefijo y espacio")
//    public void TC29464BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        cuentas.validarErroresAccountTransferSearch("VOLPATO.PESOS.BSC", "15- Alias");
//    }
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29465-BSC - Validar error por account con espacio")
//    public void TC29465BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        cuentas.validarErroresAccountTransferSearch("1- VOLPATO .PESOS.BSC", "ALIAS");
//    }
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29466-BSC - Validar error por account con acento")
//    public void TC29466BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        cuentas.validarErroresAccountTransferSearch("VÓLPATO.PESOS.BSC", "ALIAS");
//    }
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29467-BSC - Validar error por account con guión")
//    public void TC29467BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        cuentas.validarErroresAccountTransferSearch("VOL-PATO.PESOS.BSC", "ALIAS");
//    }
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29468-BSC - Validar error por account con longitud no permitida")
//    public void TC29468BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        cuentas.validarErroresAccountTransferSearch("VOLPATO.PESOS.BSC......................................................................................", "ALIAS");
//    }
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29469-BSC - Validar error por account con apóstrofe")
//    public void TC29469BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        cuentas.validarErroresAccountTransferSearch("VO'LPATO.PESOS.BSC", "ALIAS");
//    }
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29470-BSC - Validar error por account vacío")
//    public void TC29470BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        cuentas.validarErroresAccountTransferSearch("", "ALIAS");
//    }
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29471-BSC - Validar error por account null")
//    public void TC29471BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        cuentas.validarErroresAccountTransferSearchCbuNull("ALIAS");
//    }
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29472-BSC - Validar error por account CBU con longitud inválida (-1)")
//    public void TC29472BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        cuentas.validarErroresAccountTransferSearch("3300000620000125788076.............................................................................", "ALIAS");
//    }
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29473-BSC - Validar error por account CBU con longitud inválida (+1)")
//    public void TC29473BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        cuentas.validarErroresAccountTransferSearch("3300000620000125788076...............................................................................", "ALIAS");
//    }
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29474-BSC - Validar error por account alias con números adicionales")
//    public void TC29474BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        cuentas.validarErroresAccountTransferSearch("VOLPATO.PESOS.BSC1234", "ALIAS");
//    }
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29475-BSC - Validar error por typeAccount alfanumérico")
//    public void TC29475BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        cuentas.validarErroresAccountTransferSearch("VOLPATO.PESOS.BSC", "ALIAS1");
//    }
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29476-BSC - Validar error por account con caracteres especiales")
//    public void TC29476BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        cuentas.validarErroresAccountTransferSearch("(@#$%&_)", "ALIAS");
//    }
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29477-BSC - Validar error por account boolean true")
//    public void TC29477BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        cuentas.validarErroresAccountTransferSearchTrueFalsePerteneceA(true, "ALIAS");
//    }
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29478-BSC - Validar error por account boolean false")
//    public void TC29478BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        cuentas.validarErroresAccountTransferSearchTrueFalsePerteneceA(false, "ALIAS");
//    }
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29480-BSC - Validar error por account con formato mixto")
//    public void TC29480BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        cuentas.validarErroresAccountTransferSearch("15- Volpato.pesos.BSC", "ALIAS");
//    }
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29481-BSC - Validar error cuando typeAccount y account están vacíos")
//    public void TC29481BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        cuentas.validarErroresAccountTransferSearch("", "");
//    }
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29482-BSC - Validar error cuando typeAccount y account son inválidos")
//    public void TC29482BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        cuentas.validarErroresAccountTransferSearch("VÓLPATO.PESOS.BSC", "ÁLIAS");
//    }
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29483-BSC - Validar error por inconsistencia typeAccount CBU y account alias")
//    public void TC29483BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        cuentas.validarErroresAccountTransferSearch("ALIAS", "VOLPATO.PESOS.BSC");
//    }
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29484-BSC - Validar error por inconsistencia typeAccount alias y account CBU")
//    public void TC29484BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        cuentas.validarErroresAccountTransferSearch("3300000620000125788076", "ALIAS");
//    }
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29491-BSC - Validar error por typeAccount CBU vacío")
//    public void TC29491BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        cuentas.validarErroresAccountTransferSearch("3300000620000035540021", "");
//    }
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29492-BSC - Validar error por typeAccount CBU null")
//    public void TC29492BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        cuentas.validarErroresAccountTransferSearchTipoNull("3300000620000035540021");
//    }
//
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29493-BSC - Validar error por account CBU con espacio")
//    public void TC29493BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        cuentas.validarErroresAccountTransferSearch(" 33000006200000355400", "CBU");
//    }
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29494-BSC - Validar error por account CBU con guión")
//    public void TC29494BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        cuentas.validarErroresAccountTransferSearch("3300-00620000035540021", "CBU");
//    }
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29495-BSC - Validar error por account CBU con longitud no permitida")
//    public void TC29495BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        cuentas.validarErroresAccountTransferSearch("330000062000003554002100000000000000000000000000000000000000000000000000000000000000000000000", "CBU");
//    }
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29496-BSC - Validar error por account CBU con apóstrofe")
//    public void TC29496BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        cuentas.validarErroresAccountTransferSearch("'30000062000003554002", "CBU");
//    }
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29497-BSC - Validar error por account CBU vacío")
//    public void TC29497BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        cuentas.validarErroresAccountTransferSearch("", "CBU");
//    }
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29498-BSC - Validar error por account CBU null")
//    public void TC29498BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        cuentas.validarErroresAccountTransferSearchCbuNull("CBU");
//    }
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29499-BSC - Validar error por account CBU con longitud inválida (-1)")
//    public void TC29499BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        cuentas.validarErroresAccountTransferSearch("3300000620000125788076.............................................................................", "CBU");
//    }
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29500-BSC - Validar error por account cbu con caracteres especiales")
//    public void TC29500BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        cuentas.validarErroresAccountTransferSearch("(@#$%&_)", "CBU");
//    }
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29501-BSC - Validar error por account CBU boolean true")
//    public void TC29501BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        cuentas.validarErroresAccountTransferSearchTrueFalsePerteneceA(true, "CBU");
//    }
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29502-BSC - Validar error por account CBU boolean false")
//    public void TC29502BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        cuentas.validarErroresAccountTransferSearchTrueFalsePerteneceA(false, "CBU");
//    }
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29503-BSC - Validar error por typeAccount CVU vacío")
//    public void
//    BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        cuentas.validarErroresAccountTransferSearch("0000001700300000000313", "");
//    }
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29504-BSC - Validar error por typeAccount CVU null")
//    public void TC29504BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        cuentas.validarErroresAccountTransferSearchTipoNull("0000001700300000000313");
//    }
//
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29505-BSC - Validar error por account CVU con espacio")
//    public void TC29505BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        cuentas.validarErroresAccountTransferSearch(" 0000001700300000000313", "CVU");
//    }
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29506-BSC - Validar error por account CVU con guión")
//    public void TC29506BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        cuentas.validarErroresAccountTransferSearch("0000-001700300000000313", "CVU");
//    }
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29507-BSC - Validar error por account CVU con longitud no permitida")
//    public void TC29507BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        cuentas.validarErroresAccountTransferSearch("000000170030000000031300000000000000000000000000000000000000000000000000000000000000000000000", "CVU");
//    }
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29508-BSC - Validar error por account CVU con apóstrofe")
//    public void TC29508BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        cuentas.validarErroresAccountTransferSearch("'30000062000003554002", "CBU");
//    }
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29509-BSC - Validar error por account CVU vacío")
//    public void TC29509BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        cuentas.validarErroresAccountTransferSearch("", "CVU");
//    }
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29510-BSC - Validar error por account CVU null")
//    public void TC29510BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        cuentas.validarErroresAccountTransferSearchCbuNull("CVU");
//    }
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29511-BSC - Validar error por account CVU con longitud inválida (-1)")
//    public void TC29511BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        cuentas.validarErroresAccountTransferSearch("000000170030000000031", "CVU");
//    }
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29512-BSC - Validar error por account CVU con longitud inválida (+1)")
//    public void TC29512BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        cuentas.validarErroresAccountTransferSearch("00000017003000000003134", "CVU");
//    }
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29513-BSC - Validar error por account CVU con caracteres especiales")
//    public void TC29513BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        cuentas.validarErroresAccountTransferSearch("(@#$%&_)", "CVU");
//    }
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29514-BSC - Validar error por account CVU boolean true")
//    public void TC29514BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        cuentas.validarErroresAccountTransferSearchTrueFalsePerteneceA(true, "CVU");
//    }
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29516-BSC - Validar error por account CVU boolean false")
//    public void TC29516BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        cuentas.validarErroresAccountTransferSearchTrueFalsePerteneceA(false, "CVU");
//    }
//
//    //// Validar Servicio /list.echeck ////
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29517-BSC - Validar error por echeqNumber con espacio")
//    public void TC29517BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        cuentas.validarErroresListEcheq("8510 9300", "", "", "", "1");
//    }
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29518-BSC - Validar error por echeqNumber con caracteres especiales")
//    public void TC29518BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        cuentas.validarErroresListEcheq("@#$%&_", "", "", "", "1");
//    }
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29519-BSC - Validar error por statement con espacio")
//    public void TC29519BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        cuentas.validarErroresListEcheq("", "RE CIBIDOS", "", "", "1");
//    }
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29520-BSC - Validar error por statement con caracteres especiales")
//    public void TC29520BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        cuentas.validarErroresListEcheq("", "@#$%&_", "", "", "1");
//    }
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29521-BSC - Validar error por filterCuil con espacio")
//    public void TC29521BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        cuentas.validarErroresListEcheq("", "", "271 71806742", "", "1");
//    }
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29522-BSC - Validar error por filterCuil con caracteres especiales")
//    public void TC29522BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        cuentas.validarErroresListEcheq("", "", "@#$%&_", "", "1");
//    }
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29523-BSC - Validar error por operationType con espacio")
//    public void TC29523BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        cuentas.validarErroresListEcheq("", "", "", "TO DOS", "1");
//    }
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29524-BSC - Validar error por operationType con caracteres especiales")
//    public void TC29524BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        cuentas.validarErroresListEcheq("", "", "", "@#$%&_", "1");
//    }
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29525-BSC - Validar error por page con espacio")
//    public void TC29525BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        cuentas.validarErroresListEcheq("", "", "", "", " 1");
//    }
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29526-BSC - Validar error por page con caracteres especiales")
//    public void TC29526BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        cuentas.validarErroresListEcheq("", "", "", "", "@#$%&_");
//    }
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29527-BSC - Validar error por filterCuil boolean true")
//    public void TC29527BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        cuentas.validarErroresListEcheqTrueFalseCuil("", "", true, "", "1");
//    }
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29528-BSC - Validar error por filterCuil boolean false")
//    public void TC29528BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        cuentas.validarErroresListEcheqTrueFalseCuil("", "", false, "", "1");
//    }
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29529-BSC - Validar error por filterCuil null")
//    public void TC29529BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        cuentas.validarErroresListEcheqNull("", "", "", "1");
//    }
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29530-BSC - Validar error por page con guión")
//    public void TC29530BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        cuentas.validarErroresListEcheq("", "", "", "", "_1");
//    }
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29531-BSC - Validar error por page alfabético no permitido")
//    public void TC29531BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        cuentas.validarErroresListEcheq("", "", "", "", "1PAGE");
//    }
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29532-BSC - Validar error por page boolean true")
//    public void TC29532BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        cuentas.validarErroresListEcheqTrueFalsePage("", "", "", "", true);
//    }
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29533-BSC - Validar error por page boolean false")
//    public void TC29533BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        cuentas.validarErroresListEcheqTrueFalsePage("", "", "", "", false);
//    }
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29534-BSC - Validar error por page con prefijo y espacio")
//    public void TC29534BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        cuentas.validarErroresListEcheq("", "", "", "", "1- page");
//    }
//
//    //// Validar Servicio /debin.generate.search ////
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29535-BSC - Validar error por typeSelector con espacio")
//    public void TC29535BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        pagos.validarErroresDebinGenerateSearch("3300000620000035540021", " CBU");
//    }
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29451-BSC - Validar error por typeSelector con acento")
//    public void TC29536BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        pagos.validarErroresDebinGenerateSearch("3300000620000035540021", "CBÚ");
//    }
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29537-BSC - Validar error por typeSelector con guión")
//    public void TC29537BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        pagos.validarErroresDebinGenerateSearch("3300000620000035540021", "CBU_");
//    }
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29538-BSC - Validar error por typeSelector con longitud no permitida")
//    public void TC29538BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        pagos.validarErroresDebinGenerateSearch("3300000620000035540021", "CBUUU");
//    }
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29539-BSC - Validar error por typeSelector con apóstrofe")
//    public void  TC29539BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        pagos.validarErroresDebinGenerateSearch("3300000620000035540021", "C'BU");
//    }
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29540-BSC - Validar error por typeSelector vacío")
//    public void TC29540BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        pagos.validarErroresDebinGenerateSearch("3300000620000035540021", "");
//    }
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29541-BSC - Validar error por typesSelector null")
//    public void TC29541BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        pagos.validarErroresGenerateDebinSearchTipoNull("3300000620000035540021");
//    }
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29542-BSC - Validar error por typeSelector alfabético no permitido")
//    public void TC29542BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        pagos.validarErroresDebinGenerateSearch("3300000620000035540021", "CBUUNO");
//    }
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29543-BSC - Validar error por typeSelector numérico")
//    public void TC29543BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        pagos.validarErroresDebinGenerateSearch("3300000620000035540021", "CBU");
//    }
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29544-BSC - Validar error por typeSelector alfanumérico")
//    public void TC29544BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        pagos.validarErroresDebinGenerateSearch("3300000620000035540021", "CBU1");
//    }
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29545-BSC - Validar error por typeSelector con caracteres especiales")
//    public void TC29545BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        pagos.validarErroresDebinGenerateSearch("3300000620000035540021", "@#$%&_");
//    }
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29546-BSC - Validar error por typeSelector boolean true")
//    public void TC29546BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        pagos.validarErroresDebinGenerateSearchTrueFalseAliasCbu("3300000620000035540021", true);
//    }
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29547-BSC - Validar error por typeSelector boolean false")
//    public void TC29547BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        pagos.validarErroresDebinGenerateSearchTrueFalseAliasCbu("3300000620000035540021", false);
//    }
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29548-BSC - Validar error por typeAccount en minúsculas")
//    public void TC29548BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        pagos.validarErroresDebinGenerateSearchTrueFalseAliasCbu("3300000620000035540021", true);
//    }
//
//    @Test
//    @Tag("ValidacionesAPITest")
//    @DisplayName("TC-29549-BSC - Validar error por typeSelector con prefijo y espacio")
//    public void TC29549BSC(){
//        testCaseData.cargaDeDatosTest("TCBSCPRUEBA");
//        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
//        pagos.validarErroresDebinGenerateSearch("3300000620000035540021", "15- CBU");
//    }


}

