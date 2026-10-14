package SantaFe.cuentas;

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
    @DisplayName("TC-29450-BSF - Validar mensaje de error typeAccount con espacio")
    public void TC29450BSF(){
        //carga de los datos necesarios para realizar el test
        testCaseData.cargaDeDatosTest("TCBSFPRUEBA");
        //login de usuarios
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        //Consultamos CBU2
        cuentas.validarErroresAccountTransferSearch("VOLPATO.PESOS.BSF", " A LIAS");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29451-BSF - Validar error por typeAccount con acento")
    public void TC29451BSF(){
        testCaseData.cargaDeDatosTest("TCBSFPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("VOLPATO.PESOS.BSF", "ÁLIAS");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29452-BSF - Validar error por typeAccount con guión")
    public void TC29452BSF(){
        testCaseData.cargaDeDatosTest("TCBSFPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("VOLPATO.PESOS.BSF", "A-LIAS");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29453-BSF - Validar error por typeAccount con longitud no permitida")
    public void TC29453BSF(){
        testCaseData.cargaDeDatosTest("TCBSFPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("VOLPATO.PESOS.BSF", "ALIASSSS");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29454-BSF - Validar error por typeAccount con apóstrofe")
    public void TC29454BSF(){
        testCaseData.cargaDeDatosTest("TCBSFPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("VOLPATO.PESOS.BSF", "A'LIAS");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29455-BSF - Validar error por typeAccount vacío")
    public void TC29455BSF(){
        testCaseData.cargaDeDatosTest("TCBSFPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("VOLPATO.PESOS.BSF", "");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29456-BSF - Validar error por typeAccount null")
    public void TC29456BSF(){
        testCaseData.cargaDeDatosTest("TCBSFPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearchTipoNull("VOLPATO.PESOS.BSF");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29457-BSF - Validar error por typeAccount alfabético no permitido")
    public void TC29457BSF(){
        testCaseData.cargaDeDatosTest("TCBSFPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("VOLPATO.PESOS.BSF", "ALIASUNO");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29458-BSF - Validar error por typeAccount numérico")
    public void TC29458BSF(){
        testCaseData.cargaDeDatosTest("TCBSFPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("VOLPATO.PESOS.BSF", "123456");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29459-BSF - Validar error por typeAccount alfanumérico")
    public void TC29459BSF(){
        testCaseData.cargaDeDatosTest("TCBSFPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("VOLPATO.PESOS.BSF", "ALIAS1");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29460-BSF - Validar error por typeAccount con caracteres especiales")
    public void TC29460BSF(){
        testCaseData.cargaDeDatosTest("TCBSFPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("VOLPATO.PESOS.BSF", "@#$%&_");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29461-BSF - Validar error por typeAccount boolean true")
    public void TC29461BSF(){
        testCaseData.cargaDeDatosTest("TCBSFPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearchTrueFalseAliasCbu("VOLPATO.PESOS.BSF", false);
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29462-BSF - Validar error por typeAccount boolean false")
    public void TC29462BSF(){
        testCaseData.cargaDeDatosTest("TCBSFPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearchTrueFalseAliasCbu("VOLPATO.PESOS.BSF", false);
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29463-BSF - Validar error por typeAccount en minúsculas")
    public void TC29463BSF(){
        testCaseData.cargaDeDatosTest("TCBSFPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("VOLPATO.PESOS.BSF", "alias");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29464-BSF - Validar error por typeAccount con prefijo y espacio")
    public void TC29464BSF(){
        testCaseData.cargaDeDatosTest("TCBSFPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("VOLPATO.PESOS.BSF", "15- Alias");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29465-BSF - Validar error por account con espacio")
    public void TC29465BSF(){
        testCaseData.cargaDeDatosTest("TCBSFPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("1- VOLPATO .PESOS.BSF", "ALIAS");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29466-BSF - Validar error por account con acento")
    public void TC29466BSF(){
        testCaseData.cargaDeDatosTest("TCBSFPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("VÓLPATO.PESOS.BSF", "ALIAS");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29467-BSF - Validar error por account con guión")
    public void TC29467BSF(){
        testCaseData.cargaDeDatosTest("TCBSFPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("VOL-PATO.PESOS.BSF", "ALIAS");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29468-BSF - Validar error por account con longitud no permitida")
    public void TC29468BSF(){
        testCaseData.cargaDeDatosTest("TCBSFPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("VOLPATO.PESOS.BSF......................................................................................", "ALIAS");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29469-BSF - Validar error por account con apóstrofe")
    public void TC29469BSF(){
        testCaseData.cargaDeDatosTest("TCBSFPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("VO'LPATO.PESOS.BSF", "ALIAS");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29470-BSF - Validar error por account vacío")
    public void TC29470BSF(){
        testCaseData.cargaDeDatosTest("TCBSFPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("", "ALIAS");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29471-BSF - Validar error por account null")
    public void TC29471BSF(){
        testCaseData.cargaDeDatosTest("TCBSFPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearchCbuNull("ALIAS");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29472-BSF - Validar error por account CBU con longitud inválida (-1)")
    public void TC29472BSF(){
        testCaseData.cargaDeDatosTest("TCBSFPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("330000062000012578807", "CBU");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29473-BSF - Validar error por account CBU con longitud inválida (+1)")
    public void TC29473BSF(){
        testCaseData.cargaDeDatosTest("TCBSFPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("33000006200001257880766", "CBU");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29474-BSF - Validar error por account alias con números adicionales")
    public void TC29474BSF(){
        testCaseData.cargaDeDatosTest("TCBSFPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("VOLPATO.PESOS.BSF1234", "ALIAS");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29475-BSF - Validar error por typeAccount alfanumérico")
    public void TC29475BSF(){
        testCaseData.cargaDeDatosTest("TCBSFPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("VOLPATO.PESOS.BSF", "ALIAS1");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29476-BSF - Validar error por account alias con caracteres especiales")
    public void TC29476BSF(){
        testCaseData.cargaDeDatosTest("TCBSFPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("(@#$%&_)", "ALIAS");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29477-BSF - Validar error por account ALIAS boolean true")
    public void TC29477BSF(){
        testCaseData.cargaDeDatosTest("TCBSFPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearchTrueFalsePerteneceA(true, "ALIAS");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29478-BSF - Validar error por account ALIAS boolean false")
    public void TC29478BSF(){
        testCaseData.cargaDeDatosTest("TCBSFPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearchTrueFalsePerteneceA(false, "ALIAS");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29480-BSF - Validar error por account con formato mixto")
    public void TC29480BSF(){
        testCaseData.cargaDeDatosTest("TCBSFPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("15- Volpato.pesos.bsf", "ALIAS");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29481-BSF - Validar error cuando typeAccount y account están vacíos")
    public void TC29481BSF(){
        testCaseData.cargaDeDatosTest("TCBSFPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("", "");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29482-BSF - Validar error cuando typeAccount y account son inválidos")
    public void TC29482BSF(){
        testCaseData.cargaDeDatosTest("TCBSFPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("VÓLPATO.PESOS.BSF", "ÁLIAS");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29483-BSF - Validar error por inconsistencia typeAccount CBU y account alias")
    public void TC29483BSF(){
        testCaseData.cargaDeDatosTest("TCBSFPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("ALIAS", "VOLPATO.PESOS.BSF");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29484-BSF - Validar error por inconsistencia typeAccount alias y account CBU")
    public void TC29484BSF(){
        testCaseData.cargaDeDatosTest("TCBSFPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("3300000620000125788076", "ALIAS");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29491-BSF - Validar error por typeAccount CBU vacío")
    public void TC29491BSF(){
        testCaseData.cargaDeDatosTest("TCBSFPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("3300000620000035540021", "");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29492-BSF - Validar error por typeAccount CBU null")
    public void TC29492BSF(){
        testCaseData.cargaDeDatosTest("TCBSFPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearchTipoNull("3300000620000035540021");
    }


    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29493-BSF - Validar error por account con espacio")
    public void TC29493BSF(){
        testCaseData.cargaDeDatosTest("TCBSFPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch(" 33000006200000355400", "CBU");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29494-BSF - Validar error por account con guión")
    public void TC29494BSF(){
        testCaseData.cargaDeDatosTest("TCBSFPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("3300-00620000035540021", "CBU");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29495-BSF - Validar error por account con longitud no permitida")
    public void TC29495BSF(){
        testCaseData.cargaDeDatosTest("TCBSFPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("330000062000003554002100000000000000000000000000000000000000000000000000000000000000000000000", "CBU");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29496-BSF - Validar error por account con apóstrofe")
    public void TC29496BSF(){
        testCaseData.cargaDeDatosTest("TCBSFPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("'30000062000003554002", "CBU");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29497-BSF - Validar error por account vacío")
    public void TC29497BSF(){
        testCaseData.cargaDeDatosTest("TCBSFPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("", "CBU");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29498-BSF - Validar error por account null")
    public void TC29498BSF(){
        testCaseData.cargaDeDatosTest("TCBSFPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearchCbuNull("CBU");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29499-BSF - Validar error por account CBU con longitud inválida (-1)")
    public void TC29499BSF(){
        testCaseData.cargaDeDatosTest("TCBSFPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("3300000620000125788076.............................................................................", "ALIAS");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29500-BSF - Validar error por account cbu con caracteres especiales")
    public void TC29500BSF(){
        testCaseData.cargaDeDatosTest("TCBSFPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("(@#$%&_)", "CBU");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29501-BSF - Validar error por account CBU boolean true")
    public void TC29501BSF(){
        testCaseData.cargaDeDatosTest("TCBSFPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearchTrueFalsePerteneceA(true, "CBU");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29502-BSF - Validar error por account CBU boolean false")
    public void TC29502BSF(){
        testCaseData.cargaDeDatosTest("TCBSFPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearchTrueFalsePerteneceA(false, "CBU");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29503-BSF - Validar error por typeAccount CVU vacío")
    public void TC29503BSF(){
        testCaseData.cargaDeDatosTest("TCBSFPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("0000001700300000000313", "");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29504-BSF - Validar error por typeAccount CVU null")
    public void TC29504BSF(){
        testCaseData.cargaDeDatosTest("TCBSFPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearchTipoNull("0000001700300000000313");
    }


    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29505-BSF - Validar error por account CVU con espacio")
    public void TC29505BSF(){
        testCaseData.cargaDeDatosTest("TCBSFPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch(" 0000001700300000000313", "CVU");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29506-BSF - Validar error por account CVU con guión")
    public void TC29506BSF(){
        testCaseData.cargaDeDatosTest("TCBSFPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("0000-001700300000000313", "CVU");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29507-BSF - Validar error por account CVU con longitud no permitida")
    public void TC29507BSF(){
        testCaseData.cargaDeDatosTest("TCBSFPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("000000170030000000031300000000000000000000000000000000000000000000000000000000000000000000000", "CVU");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29508-BSF - Validar error por account CVU con apóstrofe")
    public void TC29508BSF(){
        testCaseData.cargaDeDatosTest("TCBSFPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("'30000062000003554002", "CBU");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29509-BSF - Validar error por account CVU vacío")
    public void TC29509BSF(){
        testCaseData.cargaDeDatosTest("TCBSFPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("", "CVU");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29510-BSF - Validar error por account CVU null")
    public void TC29510BSF(){
        testCaseData.cargaDeDatosTest("TCBSFPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearchCbuNull("CVU");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29511-BSF - Validar error por account CVU con longitud inválida (-1)")
    public void TC29511BSF(){
        testCaseData.cargaDeDatosTest("TCBSFPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("000000170030000000031", "CVU");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29512-BSF - Validar error por account CVU con longitud inválida (+1)")
    public void TC29512BSF(){
        testCaseData.cargaDeDatosTest("TCBSFPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("00000017003000000003134", "CVU");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29513-BSF - Validar error por account CVU con caracteres especiales")
    public void TC29513BSF(){
        testCaseData.cargaDeDatosTest("TCBSFPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("(@#$%&_)", "CVU");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29514-BSF - Validar error por account CVU boolean true")
    public void TC29514BSF(){
        testCaseData.cargaDeDatosTest("TCBSFPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearchTrueFalsePerteneceA(true, "CVU");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29516-BSF - Validar error por account CVU boolean false")
    public void TC29516BSF(){
        testCaseData.cargaDeDatosTest("TCBSFPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearchTrueFalsePerteneceA(false, "CVU");
    }

    //// Validar Servicio /list.echeck ////

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29517-BSF - Validar error por echeqNumber con espacio")
    public void TC29517BSF(){
        testCaseData.cargaDeDatosTest("TCBSFPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresListEcheq("8510 9300", "", "", "", "1");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29518-BSF - Validar error por echeqNumber con caracteres especiales")
    public void TC29518BSF(){
        testCaseData.cargaDeDatosTest("TCBSFPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresListEcheq("@#$%&_", "", "", "", "1");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29519-BSF - Validar error por statement con espacio")
    public void TC29519BSF(){
        testCaseData.cargaDeDatosTest("TCBSFPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresListEcheq("", "RE CIBIDOS", "", "", "1");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29520-BSF - Validar error por statement con caracteres especiales")
    public void TC29520BSF(){
        testCaseData.cargaDeDatosTest("TCBSFPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresListEcheq("", "@#$%&_", "", "", "1");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29521-BSF - Validar error por filterCuil con espacio")
    public void TC29521BSF(){
        testCaseData.cargaDeDatosTest("TCBSFPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresListEcheq("", "", "271 71806742", "", "1");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29522-BSF - Validar error por filterCuil con caracteres especiales")
    public void TC29522BSF(){
        testCaseData.cargaDeDatosTest("TCBSFPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresListEcheq("", "", "@#$%&_", "", "1");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29523-BSF - Validar error por operationType con espacio")
    public void TC29523BSF(){
        testCaseData.cargaDeDatosTest("TCBSFPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresListEcheq("", "", "", "TO DOS", "1");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29524-BSF - Validar error por operationType con caracteres especiales")
    public void TC29524BSF(){
        testCaseData.cargaDeDatosTest("TCBSFPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresListEcheq("", "", "", "@#$%&_", "1");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29525-BSF - Validar error por page con espacio")
    public void TC29525BSF(){
        testCaseData.cargaDeDatosTest("TCBSFPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresListEcheq("", "", "", "", " 1");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29526-BSF - Validar error por page con caracteres especiales")
    public void TC29526BSF(){
        testCaseData.cargaDeDatosTest("TCBSFPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresListEcheq("", "", "", "", "@#$%&_");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29527-BSF - Validar error por filterCuil boolean true")
    public void TC29527BSF(){
        testCaseData.cargaDeDatosTest("TCBSFPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresListEcheqTrueFalseCuil("", "", true, "", "1");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29528-BSF - Validar error por filterCuil boolean false")
    public void TC29528BSF(){
        testCaseData.cargaDeDatosTest("TCBSFPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresListEcheqTrueFalseCuil("", "", false, "", "1");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29529-BSF - Validar error por filterCuil null")
    public void TC29529BSF(){
        testCaseData.cargaDeDatosTest("TCBSFPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresListEcheqNull("", "", "", "1");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29530-BSF - Validar error por page con guión")
    public void TC29530BSF(){
        testCaseData.cargaDeDatosTest("TCBSFPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresListEcheq("", "", "", "", "_1");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29531-BSF - Validar error por page alfabético no permitido")
    public void TC29531BSF(){
        testCaseData.cargaDeDatosTest("TCBSFPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresListEcheq("", "", "", "", "1PAGE");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29532-BSF - Validar error por page boolean true")
    public void TC29532BSF(){
        testCaseData.cargaDeDatosTest("TCBSFPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresListEcheqTrueFalsePage("", "", "", "", true);
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29533-BSF - Validar error por page boolean false")
    public void TC29533BSF(){
        testCaseData.cargaDeDatosTest("TCBSFPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresListEcheqTrueFalsePage("", "", "", "", false);
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29534-BSF - Validar error por page con prefijo y espacio")
    public void TC29534BSF(){
        testCaseData.cargaDeDatosTest("TCBSFPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresListEcheq("", "", "", "", "1- page");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29535-BSF - Validar mensaje de error typeAccount con espacio")
    public void TC29535BSF(){
        //carga de los datos necesarios para realizar el test
        testCaseData.cargaDeDatosTest("TCBSFPRUEBA");
        //login de usuarios
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        //Consultamos CBU2
        cuentas.validarErroresAccountTransferSearch("VOLPATO.PESOS.BSF", " A LIAS");
    }


}

