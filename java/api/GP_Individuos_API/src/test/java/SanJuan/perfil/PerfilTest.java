package SanJuan.perfil;

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
    @DisplayName("TC- TC0001-BSJ - Cambio de Teléfono - Cambio de teléfono exitoso")
    public void TC0001BSJ(){
        //carga de los datos necesarios para realizar el test
        testCaseData.cargaDeDatosTest("TC0001BSJ");
        //login de usuarios
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        //Cambiamos el telefono actual por uno de reemplazo y luego cambiamos nuevamente el telefono con el del usuario
        perfil.cambioDeNumeroTelefonicoReemplazo();
        //Confirmamos el cambio de telefono
        perfil.confirmarCambioDeTelefono(Settings.telefonoDeReemplazo);

        //POST CONDICION
        //Cambiamos el telefono actual por uno de reemplazo y luego cambiamos nuevamente el telefono con el del usuario
        perfil.cambioDeNumeroTelefonicoOriginal();
        //Confirmamos el cambio de telefono
        perfil.confirmarCambioDeTelefono(Settings.telefono);
    }

    @Test
    @DisplayName("TC-0102-BSJ: Perfil - Config. Notificaciones")
    public void TC0102BSJ(){
        //carga de los datos necesarios para realizar el test
        testCaseData.cargaDeDatosTest("TC0102BSJ");
        //login de usuarios
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        //carga de servicios para extraer idProductos de Cuentas o Tarjetas - widgets.Account - widgets.CreditCards
        compartidosAPI.cargaServiciosCompartidos();
        //obtenemos el idProducto de la cuenta
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        //Ingresamos a la configuracion de notificaciones
        perfil.ingresarAnotificaciones();
        //*************VER QUE VALIDACION AGREGAR-EL RESPONSE TRAE MESSAGE:TRUE-ACCEPTED:TRUE-USERBLOCK:TRUE*************
        perfil.obtenerEstadoDeNotificacionesMenssage();
    }

}

