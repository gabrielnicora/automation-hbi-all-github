package SantaCruz.pantallaPrincipal;

import Config.Settings;
import Config.TestCaseData;
import Steps.Compartidos.CompartidosAPI;
import Steps.Login.LoginApiSteps;
import org.GP_Individuos_API.ConexionDB;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PantallaPrincipalTest {

    LoginApiSteps loginApiSteps = new LoginApiSteps();
    CompartidosAPI compartidosAPI = new CompartidosAPI();

    TestCaseData testCaseData = new TestCaseData();

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
    @DisplayName("TC - 0159 - BSC - PANTALLA PRINCIPAL - Ingresar - Enlace Términos y Condiciones")
    public void TC0159BSC(){
        //Terminos y condiciones
        compartidosAPI.terminosyCondicionesHB();
    }
}
