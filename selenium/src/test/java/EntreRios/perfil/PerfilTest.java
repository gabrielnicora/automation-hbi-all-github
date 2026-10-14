package EntreRios.perfil;

import Config.Settings;
import Config.TestCaseData;
import Steps.Compartidos.CompartidosAPI;
import Steps.Login.LoginApiSteps;
import Steps.Perfil.PerfilSteps;
import org.GP_Individuos_API.ConexionDB;
import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.MethodName.class)
public class PerfilTest {
    LoginApiSteps loginApiSteps = new LoginApiSteps();
    CompartidosAPI compartidosAPI = new CompartidosAPI();
    TestCaseData testCaseData = new TestCaseData();
    PerfilSteps perfil = new PerfilSteps();

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
    @DisplayName("TC-0001-BERSA - Cambio de Teléfono - Cambio de teléfono exitoso")
    public void TC0001BERSA(){
        //carga de los datos necesarios para realizar el test
        testCaseData.cargaDeDatosTest("TC0001BERSA");
        //login de usuarios
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        //Cambiamos el telefono actual por uno de reemplazo distinto
        String telefonoNuevo = perfil.cambioDeNumeroTelefonicoReemplazo();
        //Confirmamos el cambio de telefono
        perfil.confirmarCambioDeTelefono(telefonoNuevo);
    }

    @Test
    @DisplayName("TC-0102-BERSA: Perfil - Config. Notificaciones")
    public void TC0102BERSA(){
        //carga de los datos necesarios para realizar el test
        testCaseData.cargaDeDatosTest("TC0102BERSA");
        //login de usuarios
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        //carga de servicios para extraer idProductos de Cuentas o Tarjetas - widgets.Account - widgets.CreditCards
        //compartidosAPI.cargaServiciosCompartidos();
        //obtenemos el idProducto de la cuenta
       // compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        //Ingresamos a la configuracion de notificaciones
        perfil.ingresarAnotificaciones();
        //*************VER QUE VALIDACION AGREGAR-EL RESPONSE TRAE MESSAGE:TRUE-ACCEPTED:TRUE-USERBLOCK:TRUE*************
        perfil.obtenerEstadoDeNotificacionesMenssage();
    }

}

