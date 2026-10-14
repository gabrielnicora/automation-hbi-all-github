package SanJuan.cuentas;

import Config.Settings;
import Config.TestCaseData;
import Steps.Compartidos.CompartidosAPI;
import Steps.Cuentas.CuentasSteps;
import Steps.Login.LoginApiSteps;
import org.GP_Individuos_API.ConexionDB;
import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.MethodName.class)
public class ValidacionesAPITest {
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

    //// Validar Servicio /account.transfer.search ////

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("Validar mensaje de error typeAccount con espacio")
    public void TC29450BSJ(){
        //carga de los datos necesarios para realizar el test
        testCaseData.cargaDeDatosTest("TCBSJPRUEBA");
        //login de usuarios
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        //Consultamos CBU2
        cuentas.validarErroresAccountTransferSearch("VOLPATO.PESOS.BSJ", " A LIAS");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29451-BSJ - Validar error por typeAccount con acento")
    public void TC29451BSJ(){
        testCaseData.cargaDeDatosTest("TCBSJPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("VOLPATO.PESOS.BSJ", "ÁLIAS");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29452-BSJ - Validar error por typeAccount con guión")
    public void TC29452BSJ(){
        testCaseData.cargaDeDatosTest("TCBSJPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("VOLPATO.PESOS.BSJ", "A-LIAS");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29453-BSJ - Validar error por typeAccount con longitud no permitida")
    public void TC29453BSJ(){
        testCaseData.cargaDeDatosTest("TCBSJPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("VOLPATO.PESOS.BSJ", "ALIASSSS");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29454-BSJ - Validar error por typeAccount con apóstrofe")
    public void TC29454BSJ(){
        testCaseData.cargaDeDatosTest("TCBSJPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("VOLPATO.PESOS.BSJ", "A'LIAS");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29455-BSJ - Validar error por typeAccount vacío")
    public void TC29455BSJ(){
        testCaseData.cargaDeDatosTest("TCBSJPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("VOLPATO.PESOS.BSJ", "");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29456-BSJ - Validar error por typeAccount null")
    public void TC29456BSJ(){
        testCaseData.cargaDeDatosTest("TCBSJPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearchTipoNull("VOLPATO.PESOS.BSJ");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29457-BSJ - Validar error por typeAccount alfabético no permitido")
    public void TC29457BSJ(){
        testCaseData.cargaDeDatosTest("TCBSJPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("VOLPATO.PESOS.BSJ", "ALIASUNO");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29458-BSJ - Validar error por typeAccount numérico")
    public void TC29458BSJ(){
        testCaseData.cargaDeDatosTest("TCBSJPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("VOLPATO.PESOS.BSJ", "123456");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29459-BSJ - Validar error por typeAccount alfanumérico")
    public void TC29459BSJ(){
        testCaseData.cargaDeDatosTest("TCBSJPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("VOLPATO.PESOS.BSJ", "ALIAS1");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29460-BSJ - Validar error por typeAccount con caracteres especiales")
    public void TC29460BSJ(){
        testCaseData.cargaDeDatosTest("TCBSJPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("VOLPATO.PESOS.BSJ", "@#$%&_");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29461-BSJ - Validar error por typeAccount boolean true")
    public void TC29461BSJ(){
        testCaseData.cargaDeDatosTest("TCBSJPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearchTrueFalseAliasCbu("VOLPATO.PESOS.BSJ", false);
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29462-BSJ - Validar error por typeAccount boolean false")
    public void TC29462BSJ(){
        testCaseData.cargaDeDatosTest("TCBSJPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearchTrueFalseAliasCbu("VOLPATO.PESOS.BSJ", false);
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29463-BSJ - Validar error por typeAccount en minúsculas")
    public void TC29463BSJ(){
        testCaseData.cargaDeDatosTest("TCBSJPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("VOLPATO.PESOS.BSJ", "alias");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29464-BSJ - Validar error por typeAccount con prefijo y espacio")
    public void TC29464BSJ(){
        testCaseData.cargaDeDatosTest("TCBSJPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("VOLPATO.PESOS.BSJ", "15- Alias");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29465-BSJ - Validar error por account con espacio")
    public void TC29465BSJ(){
        testCaseData.cargaDeDatosTest("TCBSJPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("1- VOLPATO .PESOS.BSJ", "ALIAS");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29466-BSJ - Validar error por account con acento")
    public void TC29466BSJ(){
        testCaseData.cargaDeDatosTest("TCBSJPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("VÓLPATO.PESOS.BSJ", "ALIAS");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29467-BSJ - Validar error por account con guión")
    public void TC29467BSJ(){
        testCaseData.cargaDeDatosTest("TCBSJPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("VOL-PATO.PESOS.BSJ", "ALIAS");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29468-BSJ - Validar error por account con longitud no permitida")
    public void TC29468BSJ(){
        testCaseData.cargaDeDatosTest("TCBSJPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("VOLPATO.PESOS.BSJ......................................................................................", "ALIAS");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29469-BSJ - Validar error por account con apóstrofe")
    public void TC29469BSJ(){
        testCaseData.cargaDeDatosTest("TCBSJPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("VO'LPATO.PESOS.BSJ", "ALIAS");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29470-BSJ - Validar error por account vacío")
    public void TC29470BSJ(){
        testCaseData.cargaDeDatosTest("TCBSJPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("", "ALIAS");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29471-BSJ - Validar error por account null")
    public void TC29471BSJ(){
        testCaseData.cargaDeDatosTest("TCBSJPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearchCbuNull("ALIAS");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29472-BSJ - Validar error por account CBU con longitud inválida (-1)")
    public void TC29472BSJ(){
        testCaseData.cargaDeDatosTest("TCBSJPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("3300000620000125788076.............................................................................", "ALIAS");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29473-BSJ - Validar error por account CBU con longitud inválida (+1)")
    public void TC29473BSJ(){
        testCaseData.cargaDeDatosTest("TCBSJPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("3300000620000125788076...............................................................................", "ALIAS");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29474-BSJ - Validar error por account alias con números adicionales")
    public void TC29474BSJ(){
        testCaseData.cargaDeDatosTest("TCBSJPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("VOLPATO.PESOS.BSJ1234", "ALIAS");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29475-BSJ - Validar error por typeAccount alfanumérico")
    public void TC29475BSJ(){
        testCaseData.cargaDeDatosTest("TCBSJPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("VOLPATO.PESOS.BSJ", "ALIAS1");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29476-BSJ - Validar error por account con caracteres especiales")
    public void TC29476BSJ(){
        testCaseData.cargaDeDatosTest("TCBSJPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("(@#$%&_)", "ALIAS");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29477-BSJ - Validar error por account boolean true")
    public void TC29477BSJ(){
        testCaseData.cargaDeDatosTest("TCBSJPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearchTrueFalsePerteneceA(true, "ALIAS");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29478-BSJ - Validar error por account boolean false")
    public void TC29478BSJ(){
        testCaseData.cargaDeDatosTest("TCBSJPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearchTrueFalsePerteneceA(false, "ALIAS");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29480-BSJ - Validar error por account con formato mixto")
    public void TC29480BSJ(){
        testCaseData.cargaDeDatosTest("TCBSJPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("15- Volpato.pesos.BSJ", "ALIAS");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29481-BSJ - Validar error cuando typeAccount y account están vacíos")
    public void TC29481BSJ(){
        testCaseData.cargaDeDatosTest("TCBSJPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("", "");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29482-BSJ - Validar error cuando typeAccount y account son inválidos")
    public void TC29482BSJ(){
        testCaseData.cargaDeDatosTest("TCBSJPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("VÓLPATO.PESOS.BSJ", "ÁLIAS");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29483-BSJ - Validar error por inconsistencia typeAccount CBU y account alias")
    public void TC29483BSJ(){
        testCaseData.cargaDeDatosTest("TCBSJPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("ALIAS", "VOLPATO.PESOS.BSJ");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29484-BSJ - Validar error por inconsistencia typeAccount alias y account CBU")
    public void TC29484BSJ(){
        testCaseData.cargaDeDatosTest("TCBSJPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("3300000620000125788076", "ALIAS");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29491-BSJ - Validar error por typeAccount CBU vacío")
    public void TC29491BSJ(){
        testCaseData.cargaDeDatosTest("TCBSJPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("3300000620000035540021", "");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29492-BSJ - Validar error por typeAccount CBU null")
    public void TC29492BSJ(){
        testCaseData.cargaDeDatosTest("TCBSJPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearchTipoNull("3300000620000035540021");
    }


    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29493-BSJ - Validar error por account con espacio")
    public void TC29493BSJ(){
        testCaseData.cargaDeDatosTest("TCBSJPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch(" 33000006200000355400", "CBU");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29494-BSJ - Validar error por account con guión")
    public void TC29494BSJ(){
        testCaseData.cargaDeDatosTest("TCBSJPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("3300-00620000035540021", "CBU");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29495-BSJ - Validar error por account con longitud no permitida")
    public void TC29495BSJ(){
        testCaseData.cargaDeDatosTest("TCBSJPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("330000062000003554002100000000000000000000000000000000000000000000000000000000000000000000000", "CBU");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29496-BSJ - Validar error por account con apóstrofe")
    public void TC29496BSJ(){
        testCaseData.cargaDeDatosTest("TCBSJPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("'30000062000003554002", "CBU");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29497-BSJ - Validar error por account vacío")
    public void TC29497BSJ(){
        testCaseData.cargaDeDatosTest("TCBSJPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("", "CBU");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29498-BSJ - Validar error por account null")
    public void TC29498BSJ(){
        testCaseData.cargaDeDatosTest("TCBSJPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearchCbuNull("CBU");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29499-BSJ - Validar error por account CBU con longitud inválida (-1)")
    public void TC29499BSJ(){
        testCaseData.cargaDeDatosTest("TCBSJPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("3300000620000125788076.............................................................................", "ALIAS");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29500-BSJ - Validar error por account cbu con caracteres especiales")
    public void TC29500BSJ(){
        testCaseData.cargaDeDatosTest("TCBSJPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("(@#$%&_)", "CBU");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29501-BSJ - Validar error por account CBU boolean true")
    public void TC29501BSJ(){
        testCaseData.cargaDeDatosTest("TCBSJPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearchTrueFalsePerteneceA(true, "CBU");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29502-BSJ - Validar error por account CBU boolean false")
    public void TC29502BSJ(){
        testCaseData.cargaDeDatosTest("TCBSJPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearchTrueFalsePerteneceA(false, "CBU");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29503-BSJ - Validar error por typeAccount CVU vacío")
    public void TC29503BSJ(){
        testCaseData.cargaDeDatosTest("TCBSJPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("0000001700300000000313", "");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29504-BSJ - Validar error por typeAccount CVU null")
    public void TC29504BSJ(){
        testCaseData.cargaDeDatosTest("TCBSJPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearchTipoNull("0000001700300000000313");
    }


    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29505-BSJ - Validar error por account CVU con espacio")
    public void TC29505BSJ(){
        testCaseData.cargaDeDatosTest("TCBSJPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch(" 0000001700300000000313", "CVU");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29506-BSJ - Validar error por account CVU con guión")
    public void TC29506BSJ(){
        testCaseData.cargaDeDatosTest("TCBSJPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("0000-001700300000000313", "CVU");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29507-BSJ - Validar error por account CVU con longitud no permitida")
    public void TC29507BSJ(){
        testCaseData.cargaDeDatosTest("TCBSJPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("000000170030000000031300000000000000000000000000000000000000000000000000000000000000000000000", "CVU");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29508-BSJ - Validar error por account CVU con apóstrofe")
    public void TC29508BSJ(){
        testCaseData.cargaDeDatosTest("TCBSJPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("'30000062000003554002", "CBU");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29509-BSJ - Validar error por account CVU vacío")
    public void TC29509BSJ(){
        testCaseData.cargaDeDatosTest("TCBSJPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("", "CVU");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29510-BSJ - Validar error por account CVU null")
    public void TC29510BSJ(){
        testCaseData.cargaDeDatosTest("TCBSJPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearchCbuNull("CVU");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29511-BSJ - Validar error por account CVU con longitud inválida (-1)")
    public void TC29511BSJ(){
        testCaseData.cargaDeDatosTest("TCBSJPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("000000170030000000031", "CVU");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29512-BSJ - Validar error por account CVU con longitud inválida (+1)")
    public void TC29512BSJ(){
        testCaseData.cargaDeDatosTest("TCBSJPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("00000017003000000003134", "CVU");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29513-BSJ - Validar error por account CVU con caracteres especiales")
    public void TC29513BSJ(){
        testCaseData.cargaDeDatosTest("TCBSJPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("(@#$%&_)", "CVU");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29514-BSJ - Validar error por account CVU boolean true")
    public void TC29514BSJ(){
        testCaseData.cargaDeDatosTest("TCBSJPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearchTrueFalsePerteneceA(true, "CVU");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29516-BSJ - Validar error por account CVU boolean false")
    public void TC29516BSJ(){
        testCaseData.cargaDeDatosTest("TCBSJPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearchTrueFalsePerteneceA(false, "CVU");
    }

    //// Validar Servicio /list.echeck ////

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29517-BSJ - Validar error por echeqNumber con espacio")
    public void TC29517BSJ(){
        testCaseData.cargaDeDatosTest("TCBSJPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresListEcheq("8510 9300", "", "", "", "1");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29518-BSJ - Validar error por echeqNumber con caracteres especiales")
    public void TC29518BSJ(){
        testCaseData.cargaDeDatosTest("TCBSJPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresListEcheq("@#$%&_", "", "", "", "1");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29519-BSJ - Validar error por statement con espacio")
    public void TC29519BSJ(){
        testCaseData.cargaDeDatosTest("TCBSJPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresListEcheq("", "RE CIBIDOS", "", "", "1");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29520-BSJ - Validar error por statement con caracteres especiales")
    public void TC29520BSJ(){
        testCaseData.cargaDeDatosTest("TCBSJPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresListEcheq("", "@#$%&_", "", "", "1");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29521-BSJ - Validar error por filterCuil con espacio")
    public void TC29521BSJ(){
        testCaseData.cargaDeDatosTest("TCBSJPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresListEcheq("", "", "271 71806742", "", "1");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29522-BSJ - Validar error por filterCuil con caracteres especiales")
    public void TC29522BSJ(){
        testCaseData.cargaDeDatosTest("TCBSJPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresListEcheq("", "", "@#$%&_", "", "1");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29523-BSJ - Validar error por operationType con espacio")
    public void TC29523BSJ(){
        testCaseData.cargaDeDatosTest("TCBSJPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresListEcheq("", "", "", "TO DOS", "1");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29524-BSJ - Validar error por operationType con caracteres especiales")
    public void TC29524BSJ(){
        testCaseData.cargaDeDatosTest("TCBSJPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresListEcheq("", "", "", "@#$%&_", "1");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29525-BSJ - Validar error por page con espacio")
    public void TC29525BSJ(){
        testCaseData.cargaDeDatosTest("TCBSJPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresListEcheq("", "", "", "", " 1");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29526-BSJ - Validar error por page con caracteres especiales")
    public void TC29526BSJ(){
        testCaseData.cargaDeDatosTest("TCBSJPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresListEcheq("", "", "", "", "@#$%&_");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29527-BSJ - Validar error por filterCuil boolean true")
    public void TC29527BSJ(){
        testCaseData.cargaDeDatosTest("TCBSJPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresListEcheqTrueFalseCuil("", "", true, "", "1");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29528-BSJ - Validar error por filterCuil boolean false")
    public void TC29528BSJ(){
        testCaseData.cargaDeDatosTest("TCBSJPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresListEcheqTrueFalseCuil("", "", false, "", "1");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29529-BSJ - Validar error por filterCuil null")
    public void TC29529BSJ(){
        testCaseData.cargaDeDatosTest("TCBSJPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresListEcheqNull("", "", "", "1");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29530-BSJ - Validar error por page con guión")
    public void TC29530BSJ(){
        testCaseData.cargaDeDatosTest("TCBSJPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresListEcheq("", "", "", "", "_1");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29531-BSJ - Validar error por page alfabético no permitido")
    public void TC29531BSJ(){
        testCaseData.cargaDeDatosTest("TCBSJPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresListEcheq("", "", "", "", "1PAGE");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29532-BSJ - Validar error por page boolean true")
    public void TC29532BSJ(){
        testCaseData.cargaDeDatosTest("TCBSJPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresListEcheqTrueFalsePage("", "", "", "", true);
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29533-BSJ - Validar error por page boolean false")
    public void TC29533BSJ(){
        testCaseData.cargaDeDatosTest("TCBSJPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresListEcheqTrueFalsePage("", "", "", "", false);
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29534-BSJ - Validar error por page con prefijo y espacio")
    public void TC29534BSJ(){
        testCaseData.cargaDeDatosTest("TCBSJPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresListEcheq("", "", "", "", "1- page");
    }


}

