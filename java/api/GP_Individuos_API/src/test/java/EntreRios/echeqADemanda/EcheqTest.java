package EntreRios.echeqADemanda;

import Config.Settings;
import Config.TestCaseData;
import Steps.Compartidos.CompartidosAPI;
import Steps.Echeq.EcheqSteps;
import Steps.Login.LoginApiSteps;
import org.GP_Individuos_API.ConexionDB;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class EcheqTest {
    LoginApiSteps loginApiSteps = new LoginApiSteps();
    CompartidosAPI compartidosAPI = new CompartidosAPI();
    TestCaseData testCaseData = new TestCaseData();
    EcheqSteps echeqSteps = new EcheqSteps();

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
    @DisplayName("Generacion de Echeqs desde empresas para equipo manual")
    public void TCECHEQALOBERSA(){
        //carga de los datos necesarios para realizar el test
        testCaseData.cargaDeDatosTest("TCECHEQBERSA");
        //logeo en empresas
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        //carga de servicios para extraer idProductos de Cuentas o Tarjetas - widgets.Account - widgets.CreditCards
        compartidosAPI.cargaServiciosCompartidos();
        //obtenemos el idProducto de la cuenta
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        //OBtener la razon social del cuit a generar
        echeqSteps.consultaRazonSocial(System.getProperty("cuit"));
        //EMISION DE ECHEQ
        echeqSteps.emitirEcheq(System.getProperty("cuit"),Settings.aLaOrden);
    }

    @Test
    @DisplayName("Generacion de Echeqs desde empresas para equipo manual")
    public void TCECHEQNLOBERSA(){
        //carga de los datos necesarios para realizar el test
        testCaseData.cargaDeDatosTest("TCECHEQBERSA");
        //logeo en empresas
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password,testCaseData.ambiente);
        //carga de servicios para extraer idProductos de Cuentas o Tarjetas - widgets.Account - widgets.CreditCards
        compartidosAPI.cargaServiciosCompartidos();
        //obtenemos el idProducto de la cuenta
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        //OBtener la razon social del cuit a generar
        echeqSteps.consultaRazonSocial(System.getProperty("cuit"));
        //EMISION DE ECHEQ
        echeqSteps.emitirEcheq(System.getProperty("cuit"),Settings.noALaOrden);
    }
    
    @Test
    @DisplayName("Generacion de Anulacion de Echeqs desde empresas para equipo manual")
    public void TCECHEQCANCELBERSA(){
        //carga de los datos necesarios para realizar el test
        testCaseData.cargaDeDatosTest("TCECHEQCANCELBERSA");
        //logeo en empresas
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password,testCaseData.ambiente);
        //carga de servicios para extraer idProductos de Cuentas o Tarjetas - widgets.Account - widgets.CreditCards
        compartidosAPI.cargaServiciosCompartidos();
        //obtenemos el idProducto de la cuenta
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        //ANULACION DE ECHEQ
        echeqSteps.anularEcheq(System.getProperty("echeq"));
    }

}


