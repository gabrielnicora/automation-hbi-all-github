package SantaFe.bonosDeSalud;

import Config.Settings;
import Config.TestCaseData;
import Steps.BonosDeSalud.BonosDeSaludSteps;
import Steps.Compartidos.CompartidosAPI;
import Steps.Login.LoginApiSteps;
import org.GP_Individuos_API.ConexionDB;
import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.MethodName.class)
public class BonosDeSaludTest {
    LoginApiSteps loginApiSteps = new LoginApiSteps();
    CompartidosAPI compartidosAPI = new CompartidosAPI();
    TestCaseData testCaseData = new TestCaseData();
    BonosDeSaludSteps bonosDeSalud= new BonosDeSaludSteps();

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

    //@Test
    @DisplayName("TC- 0080-BSF - Bonos de salud- Comprar Bonos de salud - Ticket de transacción")
    public void TC0080BSF(){
        //carga de los datos necesarios para realizar el test
        testCaseData.cargaDeDatosTest("TC0080BSF");
        //login de usuarios
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        //carga de servicios para extraer idProductos de Cuentas o Tarjetas - widgets.Account - widgets.CreditCards
        compartidosAPI.cargaServiciosCompartidos();
        //obtenemos el idProducto de la cuenta
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        //
        bonosDeSalud.generarDatosBonoSalud();//Parametrizar para poder elegir que bono comprar
        //
        bonosDeSalud.comprarBonoSalud();//Parametrizar para poder elegir que bono comprar
    }

}

