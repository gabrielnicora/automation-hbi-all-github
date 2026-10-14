package SantaCruz.tramites;

import Config.Settings;
import Config.TestCaseData;
import Steps.Login.LoginApiSteps;
import Steps.Tramites.TramitesSteps;
import org.GP_Individuos_API.ConexionDB;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TramitesTest {

    LoginApiSteps loginApiSteps = new LoginApiSteps();
    TestCaseData testCaseData = new TestCaseData();
    TramitesSteps tramitesSteps = new TramitesSteps();

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
    @DisplayName("TC-0078-BSC: Mis trámites - Consulta Tramites Vigentes")
    public void TC0078BSC(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0078BSC");
        //login del usuario
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //ejecucion de servicios tramites online
        tramitesSteps.tramitesOnline();
    }
}
