package SantaFe.inversiones;

import Config.Settings;
import Config.TestCaseData;
import Steps.Compartidos.CompartidosAPI;
import Steps.Inversiones.InversionesSteps;
import Steps.Login.LoginApiSteps;
import org.GP_Individuos_API.ConexionDB;
import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.MethodName.class)
public class InversionesTest {
    LoginApiSteps loginApiSteps = new LoginApiSteps();
    CompartidosAPI compartidosAPI = new CompartidosAPI();
    TestCaseData testCaseData = new TestCaseData();
    InversionesSteps inversionesSteps = new InversionesSteps();

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
    @DisplayName("TC-0042-BSF: Alta de PF Tradicional en $ a 30 días")
    public void TC0042BSF(){
        //carga de los datos necesarios para realizar el test
        testCaseData.cargaDeDatosTest("TC0042BSF");
        //login de usuarios
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        //carga de servicios para extraer idProductos de Cuentas o Tarjetas - widgets.Account - widgets.CreditCards
        compartidosAPI.cargaServiciosCompartidos();
        //obtenemos el idProducto de la cuenta
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        //ingresar datos para plazo fijo (tradicional o UVA), moneda, acredita al terminar, monto y plazo
        inversionesSteps.generarPlazoFijo("ARS", "T", "5001", "30", "N");
    }

    @Test
    @Tag("Criticos")
    @DisplayName("TC-0043-BSF: Alta de PF UVA $ 350 días")
    public void TC0043BSF(){
        //carga de los datos necesarios para realizar el test
        testCaseData.cargaDeDatosTest("TC0043BSF");
        //login de usuarios
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        //carga de servicios para extraer idProductos de Cuentas o Tarjetas - widgets.Account - widgets.CreditCards
        compartidosAPI.cargaServiciosCompartidos();
        //obtenemos el idProducto de la cuenta
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        //ingresar datos para plazo fijo (tradicional o UVA), moneda, acredita al terminar, monto y plazo
        inversionesSteps.generarPlazoFijo("ARS", "U", "15001", "350", "N");
    }

    @Test
    @Tag("Criticos")
    @DisplayName("TC-12973-BSF: Plazo Fijo Tradicional en Dolares")
    public void TC12973BSF(){
        //carga de los datos necesarios para realizar el test
        testCaseData.cargaDeDatosTest("TC12973BSF");
        //login de usuarios
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        //carga de servicios para extraer idProductos de Cuentas o Tarjetas - widgets.Account - widgets.CreditCards
        compartidosAPI.cargaServiciosCompartidos();
        //obtenemos el idProducto de la cuenta
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        //ingresar datos para plazo fijo (tradicional o UVA), moneda, acredita al terminar, monto y plazo
        inversionesSteps.generarPlazoFijo("USD", "D", "1001", "30", "N");
    }

    @Test
    @Tag("Criticos")
    @DisplayName("TC-12974-BSF: Plazo Fijo UVA Precancelable")
    public void TC12974BSF(){
        //carga de los datos necesarios para realizar el test
        testCaseData.cargaDeDatosTest("TC12974BSF");
        //login de usuarios
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        //carga de servicios para extraer idProductos de Cuentas o Tarjetas - widgets.Account - widgets.CreditCards
        compartidosAPI.cargaServiciosCompartidos();
        //obtenemos el idProducto de la cuenta
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        //ingresar datos para plazo fijo (tradicional o UVA), moneda, acredita al terminar, monto y plazo
        inversionesSteps.generarPlazoFijo("ARS", "A", "15001", "180", "N");
    }

    @Test
    @Tag("Criticos")
    @DisplayName("TC-0111-BSF: Suscribir FCI INFERIOR  A 10 MILLONES")
    public void TC0111BSF(){
        //carga de los datos necesarios para realizar el test
        testCaseData.cargaDeDatosTest("TC0111BSF");
        //login de usuarios
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        //carga de servicios para extraer idProductos de Cuentas o Tarjetas - widgets.Account - widgets.CreditCards
        compartidosAPI.cargaServiciosCompartidos();
        //obtenemos el idProducto de la cuenta
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        //Obtenemos los datos necesarios para operar el rescate (quotation, quotationId, fundId, clientNumber, name)
        inversionesSteps.obtenerDatosFCI(Settings.deltaPesos);
        //Suscribe un monto al FCI especificado en los datos capturados del response del servicio
        inversionesSteps.suscribirFCI(1001);
    }

    @Test
    @Tag("Criticos")
    @DisplayName("TC-0113-BSF: FCI - Rescatar - Importe Parcial - Validación de pantalla")
    public void TC0113BSF(){
        //carga de los datos necesarios para realizar el test
        testCaseData.cargaDeDatosTest("TC0113BSF");
        //login de usuarios
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        //carga de servicios para extraer idProductos de Cuentas o Tarjetas - widgets.Account - widgets.CreditCards
        compartidosAPI.cargaServiciosCompartidos();
        //obtenemos el idProducto de la cuenta
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        //Obtenemos los datos necesarios para operar el rescate (quotation, quotationId, fundId, clientNumber, name)
        inversionesSteps.obtenerDatosFCI(Settings.deltaPesos);
        //Realiza el rescate del monto especificado del FCI, calcula la quantityPartQuote dividiendo amount y quotation
        inversionesSteps.rescatarFCI(1001);
    }
    @Test
    @DisplayName("TC-0115-BSF: FCI - Simular API  (inicio a la funcionalidad)")
    public void TC0115BSF(){
        //carga de los datos necesarios para realizar el test
        testCaseData.cargaDeDatosTest("TC0115BSF");
        //login de usuarios
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        //inicio a la sección
        inversionesSteps.ingresoALaSeccion();
    }

    @Test
    @DisplayName("TC- 0125- BSF - FCI - Solicitar baja cuenta comitente - Validación de pantalla - 'API'")
    public void TC0125BSF(){
        //carga de los datos necesarios para realizar el test
        testCaseData.cargaDeDatosTest("TC0125BSF");
        //login de usuarios
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        //inicio a la sección - Cuenta Comitente
        inversionesSteps.ingresoYValidacionBajaCuentaComitente();
    }

    @Test
    @DisplayName("TC - 0129 - BSF - FCI  - Movimientos")
    public void TC0129BSF(){
        //carga de los datos necesarios para realizar el test
        testCaseData.cargaDeDatosTest("TC0129BSF");
        //login de usuarios
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        //Seleccionar Cuenta Comitente
        inversionesSteps.obtenerDatosFCI(Settings.deltaPesos);
        //validamos pantalla y cmapos por api FCI
        inversionesSteps.movimientosFCI();
    }

    @Test
    @DisplayName("TC-0130-BSF - FCI  - Movimientos con descarga PDF")
    public void TC0130BSF(){
        //carga de los datos necesarios para realizar el test
        testCaseData.cargaDeDatosTest("TC0130BSF");
        //login de usuarios
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        //Seleccionar Cuenta Comitente
        inversionesSteps.obtenerDatosFCI(Settings.deltaPesos);
        //validamos pantalla y cmapos por api FCI
        inversionesSteps.movimientosFCI();
        //Se descarga el primer movimiento de FCI
        inversionesSteps.descargaMovimientoFCI(Settings.keyLongValueBSF);
    }

    //@Test
    @Tag("Criticos")
    @DisplayName("TC-29417-BSF: TC02-(WEB) Inversiones-Mercado de capitales-Validar que se muestra la tabla de tipo de Especie con sus respectivos campos")
    public void TC29417BSF(){
        //carga de los datos necesarios para realizar el test
        testCaseData.cargaDeDatosTest("TC29417BSF");
        //login de usuarios
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        //validar tabla de tipo de Especie con sus respectivos campos
        inversionesSteps.validarTablaEspecie();
    }
}

