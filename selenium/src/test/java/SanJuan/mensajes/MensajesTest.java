package SanJuan.mensajes;

import Config.Settings;
import Config.TestCaseData;
import Steps.Compartidos.CompartidosAPI;
import Steps.Login.LoginApiSteps;
import Steps.Mensajes.MensajesSteps;
import org.GP_Individuos_API.ConexionDB;
import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.MethodName.class)
public class MensajesTest {
    LoginApiSteps loginApiSteps = new LoginApiSteps();
    CompartidosAPI compartidosAPI = new CompartidosAPI();
    TestCaseData testCaseData = new TestCaseData();
    MensajesSteps mensajes = new MensajesSteps();

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
    @DisplayName("TC- 0109-BSJ - Mensajes - Recibidos")
    public void TC0109BSJ(){
        //carga de los datos necesarios para realizar el test
        testCaseData.cargaDeDatosTest("TC0109BSJ");
        //login de usuarios
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        //carga de servicios para extraer idProductos de Cuentas o Tarjetas - widgets.Account - widgets.CreditCards
        compartidosAPI.cargaServiciosCompartidos();
        //obtenemos el idProducto de la cuenta
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        //Valida la cantidad de mensajes que contiene la cuenta
        mensajes.validarCantidadMensajes();
    }
    @Test
    @DisplayName("TC-0094-BSJ - Mensajes - Sin Leer")
    public void TC0094BSJ(){
        //carga de los datos necesarios para realizar el test
        testCaseData.cargaDeDatosTest("TC0094BSJ");
        //login de usuarios
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        //Validar los mensajes no leidos
        mensajes.validarMensajesNoLeidos();
    }
    @Test
    @DisplayName("TC-0098-BSJ - Mensajes - Leídos")
    public void TC0098BSJ(){
        //carga de los datos necesarios para realizar el test
        testCaseData.cargaDeDatosTest("TC0098BSJ");
        //login de usuarios
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        //Validar los mensajes leidos
        mensajes.validarMensajesLeidos();
    }

}

