package EntreRios.cuentas;

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
    public void TC29450BERSA(){
        //carga de los datos necesarios para realizar el test
        testCaseData.cargaDeDatosTest("TCBERSAPRUEBA");
        //login de usuarios
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        //Consultamos CBU2
        cuentas.validarErroresAccountTransferSearch("VOLPATO.PESOS.BERSA", " A LIAS");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29451-BERSA - Validar error por typeAccount con acento")
    public void TC29451BERSA(){
        testCaseData.cargaDeDatosTest("TCBERSAPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("VOLPATO.PESOS.BERSA", "ÁLIAS");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29452-BERSA - Validar error por typeAccount con guión")
    public void TC29452BERSA(){
        testCaseData.cargaDeDatosTest("TCBERSAPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("VOLPATO.PESOS.BERSA", "A-LIAS");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29453-BERSA - Validar error por typeAccount con longitud no permitida")
    public void TC29453BERSA(){
        testCaseData.cargaDeDatosTest("TCBERSAPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("VOLPATO.PESOS.BERSA", "ALIASSSS");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29454-BERSA - Validar error por typeAccount con apóstrofe")
    public void TC29454BERSA(){
        testCaseData.cargaDeDatosTest("TCBERSAPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("VOLPATO.PESOS.BERSA", "A'LIAS");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29455-BERSA - Validar error por typeAccount vacío")
    public void TC29455BERSA(){
        testCaseData.cargaDeDatosTest("TCBERSAPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("VOLPATO.PESOS.BERSA", "");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29456-BERSA - Validar error por typeAccount null")
    public void TC29456BERSA(){
        testCaseData.cargaDeDatosTest("TCBERSAPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearchTipoNull("VOLPATO.PESOS.BERSA");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29457-BERSA - Validar error por typeAccount alfabético no permitido")
    public void TC29457BERSA(){
        testCaseData.cargaDeDatosTest("TCBERSAPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("VOLPATO.PESOS.BERSA", "ALIASUNO");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29458-BERSA - Validar error por typeAccount numérico")
    public void TC29458BERSA(){
        testCaseData.cargaDeDatosTest("TCBERSAPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("VOLPATO.PESOS.BERSA", "123456");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29459-BERSA - Validar error por typeAccount alfanumérico")
    public void TC29459BERSA(){
        testCaseData.cargaDeDatosTest("TCBERSAPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("VOLPATO.PESOS.BERSA", "ALIAS1");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29460-BERSA - Validar error por typeAccount con caracteres especiales")
    public void TC29460BERSA(){
        testCaseData.cargaDeDatosTest("TCBERSAPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("VOLPATO.PESOS.BERSA", "@#$%&_");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29461-BERSA - Validar error por typeAccount boolean true")
    public void TC29461BERSA(){
        testCaseData.cargaDeDatosTest("TCBERSAPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearchTrueFalseAliasCbu("VOLPATO.PESOS.BERSA", false);
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29462-BERSA - Validar error por typeAccount boolean false")
    public void TC29462BERSA(){
        testCaseData.cargaDeDatosTest("TCBERSAPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearchTrueFalseAliasCbu("VOLPATO.PESOS.BERSA", false);
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29463-BERSA - Validar error por typeAccount en minúsculas")
    public void TC29463BERSA(){
        testCaseData.cargaDeDatosTest("TCBERSAPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("VOLPATO.PESOS.BERSA", "alias");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29464-BERSA - Validar error por typeAccount con prefijo y espacio")
    public void TC29464BERSA(){
        testCaseData.cargaDeDatosTest("TCBERSAPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("VOLPATO.PESOS.BERSA", "15- Alias");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29465-BERSA - Validar error por account con espacio")
    public void TC29465BERSA(){
        testCaseData.cargaDeDatosTest("TCBERSAPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("1- VOLPATO .PESOS.BERSA", "ALIAS");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29466-BERSA - Validar error por account con acento")
    public void TC29466BERSA(){
        testCaseData.cargaDeDatosTest("TCBERSAPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("VÓLPATO.PESOS.BERSA", "ALIAS");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29467-BERSA - Validar error por account con guión")
    public void TC29467BERSA(){
        testCaseData.cargaDeDatosTest("TCBERSAPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("VOL-PATO.PESOS.BERSA", "ALIAS");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29468-BERSA - Validar error por account con longitud no permitida")
    public void TC29468BERSA(){
        testCaseData.cargaDeDatosTest("TCBERSAPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("VOLPATO.PESOS.BERSA......................................................................................", "ALIAS");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29469-BERSA - Validar error por account con apóstrofe")
    public void TC29469BERSA(){
        testCaseData.cargaDeDatosTest("TCBERSAPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("VO'LPATO.PESOS.BERSA", "ALIAS");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29470-BERSA - Validar error por account vacío")
    public void TC29470BERSA(){
        testCaseData.cargaDeDatosTest("TCBERSAPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("", "ALIAS");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29471-BERSA - Validar error por account null")
    public void TC29471BERSA(){
        testCaseData.cargaDeDatosTest("TCBERSAPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearchCbuNull("ALIAS");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29472-BERSA - Validar error por account CBU con longitud inválida (-1)")
    public void TC29472BERSA(){
        testCaseData.cargaDeDatosTest("TCBERSAPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("3300000620000125788076.............................................................................", "ALIAS");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29473-BERSA - Validar error por account CBU con longitud inválida (+1)")
    public void TC29473BERSA(){
        testCaseData.cargaDeDatosTest("TCBERSAPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("3300000620000125788076...............................................................................", "ALIAS");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29474-BERSA - Validar error por account alias con números adicionales")
    public void TC29474BERSA(){
        testCaseData.cargaDeDatosTest("TCBERSAPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("VOLPATO.PESOS.BERSA1234", "ALIAS");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29475-BERSA - Validar error por typeAccount alfanumérico")
    public void TC29475BERSA(){
        testCaseData.cargaDeDatosTest("TCBERSAPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("VOLPATO.PESOS.BERSA", "ALIAS1");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29476-BERSA - Validar error por account con caracteres especiales")
    public void TC29476BERSA(){
        testCaseData.cargaDeDatosTest("TCBERSAPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("(@#$%&_)", "ALIAS");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29477-BERSA - Validar error por account boolean true")
    public void TC29477BERSA(){
        testCaseData.cargaDeDatosTest("TCBERSAPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearchTrueFalsePerteneceA(true, "ALIAS");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29478-BERSA - Validar error por account boolean false")
    public void TC29478BERSA(){
        testCaseData.cargaDeDatosTest("TCBERSAPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearchTrueFalsePerteneceA(false, "ALIAS");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29480-BERSA - Validar error por account con formato mixto")
    public void TC29480BERSA(){
        testCaseData.cargaDeDatosTest("TCBERSAPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("15- Volpato.pesos.BERSA", "ALIAS");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29481-BERSA - Validar error cuando typeAccount y account están vacíos")
    public void TC29481BERSA(){
        testCaseData.cargaDeDatosTest("TCBERSAPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("", "");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29482-BERSA - Validar error cuando typeAccount y account son inválidos")
    public void TC29482BERSA(){
        testCaseData.cargaDeDatosTest("TCBERSAPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("VÓLPATO.PESOS.BERSA", "ÁLIAS");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29483-BERSA - Validar error por inconsistencia typeAccount CBU y account alias")
    public void TC29483BERSA(){
        testCaseData.cargaDeDatosTest("TCBERSAPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("ALIAS", "VOLPATO.PESOS.BERSA");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29484-BERSA - Validar error por inconsistencia typeAccount alias y account CBU")
    public void TC29484BERSA(){
        testCaseData.cargaDeDatosTest("TCBERSAPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("3300000620000125788076", "ALIAS");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29491-BERSA - Validar error por typeAccount CBU vacío")
    public void TC29491BERSA(){
        testCaseData.cargaDeDatosTest("TCBERSAPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("3300000620000035540021", "");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29492-BERSA - Validar error por typeAccount CBU null")
    public void TC29492BERSA(){
        testCaseData.cargaDeDatosTest("TCBERSAPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearchTipoNull("3300000620000035540021");
    }


    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29493-BERSA - Validar error por account con espacio")
    public void TC29493BERSA(){
        testCaseData.cargaDeDatosTest("TCBERSAPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch(" 33000006200000355400", "CBU");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29494-BERSA - Validar error por account con guión")
    public void TC29494BERSA(){
        testCaseData.cargaDeDatosTest("TCBERSAPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("3300-00620000035540021", "CBU");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29495-BERSA - Validar error por account con longitud no permitida")
    public void TC29495BERSA(){
        testCaseData.cargaDeDatosTest("TCBERSAPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("330000062000003554002100000000000000000000000000000000000000000000000000000000000000000000000", "CBU");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29496-BERSA - Validar error por account con apóstrofe")
    public void TC29496BERSA(){
        testCaseData.cargaDeDatosTest("TCBERSAPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("'30000062000003554002", "CBU");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29497-BERSA - Validar error por account vacío")
    public void TC29497BERSA(){
        testCaseData.cargaDeDatosTest("TCBERSAPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("", "CBU");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29498-BERSA - Validar error por account null")
    public void TC29498BERSA(){
        testCaseData.cargaDeDatosTest("TCBERSAPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearchCbuNull("CBU");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29499-BERSA - Validar error por account CBU con longitud inválida (-1)")
    public void TC29499BERSA(){
        testCaseData.cargaDeDatosTest("TCBERSAPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("3300000620000125788076.............................................................................", "ALIAS");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29500-BERSA - Validar error por account cbu con caracteres especiales")
    public void TC29500BERSA(){
        testCaseData.cargaDeDatosTest("TCBERSAPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("(@#$%&_)", "CBU");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29501-BERSA - Validar error por account CBU boolean true")
    public void TC29501BERSA(){
        testCaseData.cargaDeDatosTest("TCBERSAPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearchTrueFalsePerteneceA(true, "CBU");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29502-BERSA - Validar error por account CBU boolean false")
    public void TC29502BERSA(){
        testCaseData.cargaDeDatosTest("TCBERSAPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearchTrueFalsePerteneceA(false, "CBU");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29503-BERSA - Validar error por typeAccount CVU vacío")
    public void TC29503BERSA(){
        testCaseData.cargaDeDatosTest("TCBERSAPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("0000001700300000000313", "");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29504-BERSA - Validar error por typeAccount CVU null")
    public void TC29504BERSA(){
        testCaseData.cargaDeDatosTest("TCBERSAPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearchTipoNull("0000001700300000000313");
    }


    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29505-BERSA - Validar error por account CVU con espacio")
    public void TC29505BERSA(){
        testCaseData.cargaDeDatosTest("TCBERSAPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch(" 0000001700300000000313", "CVU");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29506-BERSA - Validar error por account CVU con guión")
    public void TC29506BERSA(){
        testCaseData.cargaDeDatosTest("TCBERSAPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("0000-001700300000000313", "CVU");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29507-BERSA - Validar error por account CVU con longitud no permitida")
    public void TC29507BERSA(){
        testCaseData.cargaDeDatosTest("TCBERSAPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("000000170030000000031300000000000000000000000000000000000000000000000000000000000000000000000", "CVU");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29508-BERSA - Validar error por account CVU con apóstrofe")
    public void TC29508BERSA(){
        testCaseData.cargaDeDatosTest("TCBERSAPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("'30000062000003554002", "CBU");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29509-BERSA - Validar error por account CVU vacío")
    public void TC29509BERSA(){
        testCaseData.cargaDeDatosTest("TCBERSAPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("", "CVU");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29510-BERSA - Validar error por account CVU null")
    public void TC29510BERSA(){
        testCaseData.cargaDeDatosTest("TCBERSAPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearchCbuNull("CVU");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29511-BERSA - Validar error por account CVU con longitud inválida (-1)")
    public void TC29511BERSA(){
        testCaseData.cargaDeDatosTest("TCBERSAPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("000000170030000000031", "CVU");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29512-BERSA - Validar error por account CVU con longitud inválida (+1)")
    public void TC29512BERSA(){
        testCaseData.cargaDeDatosTest("TCBERSAPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("00000017003000000003134", "CVU");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29513-BERSA - Validar error por account CVU con caracteres especiales")
    public void TC29513BERSA(){
        testCaseData.cargaDeDatosTest("TCBERSAPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearch("(@#$%&_)", "CVU");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29514-BERSA - Validar error por account CVU boolean true")
    public void TC29514BERSA(){
        testCaseData.cargaDeDatosTest("TCBERSAPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearchTrueFalsePerteneceA(true, "CVU");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29516-BERSA - Validar error por account CVU boolean false")
    public void TC29516BERSA(){
        testCaseData.cargaDeDatosTest("TCBERSAPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresAccountTransferSearchTrueFalsePerteneceA(false, "CVU");
    }

    //// Validar Servicio /list.echeck ////

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29517-BERSA - Validar error por echeqNumber con espacio")
    public void TC29517BERSA(){
        testCaseData.cargaDeDatosTest("TCBERSAPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresListEcheq("8510 9300", "", "", "", "1");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29518-BERSA - Validar error por echeqNumber con caracteres especiales")
    public void TC29518BERSA(){
        testCaseData.cargaDeDatosTest("TCBERSAPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresListEcheq("@#$%&_", "", "", "", "1");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29519-BERSA - Validar error por statement con espacio")
    public void TC29519BERSA(){
        testCaseData.cargaDeDatosTest("TCBERSAPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresListEcheq("", "RE CIBIDOS", "", "", "1");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29520-BERSA - Validar error por statement con caracteres especiales")
    public void TC29520BERSA(){
        testCaseData.cargaDeDatosTest("TCBERSAPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresListEcheq("", "@#$%&_", "", "", "1");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29521-BERSA - Validar error por filterCuil con espacio")
    public void TC29521BERSA(){
        testCaseData.cargaDeDatosTest("TCBERSAPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresListEcheq("", "", "271 71806742", "", "1");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29522-BERSA - Validar error por filterCuil con caracteres especiales")
    public void TC29522BERSA(){
        testCaseData.cargaDeDatosTest("TCBERSAPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresListEcheq("", "", "@#$%&_", "", "1");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29523-BERSA - Validar error por operationType con espacio")
    public void TC29523BERSA(){
        testCaseData.cargaDeDatosTest("TCBERSAPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresListEcheq("", "", "", "TO DOS", "1");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29524-BERSA - Validar error por operationType con caracteres especiales")
    public void TC29524BERSA(){
        testCaseData.cargaDeDatosTest("TCBERSAPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresListEcheq("", "", "", "@#$%&_", "1");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29525-BERSA - Validar error por page con espacio")
    public void TC29525BERSA(){
        testCaseData.cargaDeDatosTest("TCBERSAPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresListEcheq("", "", "", "", " 1");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29526-BERSA - Validar error por page con caracteres especiales")
    public void TC29526BERSA(){
        testCaseData.cargaDeDatosTest("TCBERSAPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresListEcheq("", "", "", "", "@#$%&_");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29527-BERSA - Validar error por filterCuil boolean true")
    public void TC29527BERSA(){
        testCaseData.cargaDeDatosTest("TCBERSAPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresListEcheqTrueFalseCuil("", "", true, "", "1");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29528-BERSA - Validar error por filterCuil boolean false")
    public void TC29528BERSA(){
        testCaseData.cargaDeDatosTest("TCBERSAPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresListEcheqTrueFalseCuil("", "", false, "", "1");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29529-BERSA - Validar error por filterCuil null")
    public void TC29529BERSA(){
        testCaseData.cargaDeDatosTest("TCBERSAPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresListEcheqNull("", "", "", "1");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29530-BERSA - Validar error por page con guión")
    public void TC29530BERSA(){
        testCaseData.cargaDeDatosTest("TCBERSAPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresListEcheq("", "", "", "", "_1");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29531-BERSA - Validar error por page alfabético no permitido")
    public void TC29531BERSA(){
        testCaseData.cargaDeDatosTest("TCBERSAPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresListEcheq("", "", "", "", "1PAGE");
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29532-BERSA - Validar error por page boolean true")
    public void TC29532BERSA(){
        testCaseData.cargaDeDatosTest("TCBERSAPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresListEcheqTrueFalsePage("", "", "", "", true);
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29533-BERSA - Validar error por page boolean false")
    public void TC29533BERSA(){
        testCaseData.cargaDeDatosTest("TCBERSAPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresListEcheqTrueFalsePage("", "", "", "", false);
    }

    @Test
    @Tag("ValidacionesAPITest")
    @DisplayName("TC-29534-BERSA - Validar error por page con prefijo y espacio")
    public void TC29534BERSA(){
        testCaseData.cargaDeDatosTest("TCBERSAPRUEBA");
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        cuentas.validarErroresListEcheq("", "", "", "", "1- page");
    }
}

