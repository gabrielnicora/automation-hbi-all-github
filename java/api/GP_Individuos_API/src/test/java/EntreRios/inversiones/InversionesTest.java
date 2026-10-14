package EntreRios.inversiones;

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
    @DisplayName("TC-0042-BERSA: Alta de PF Tradicional en $ a 30 días")//**OK**
    public void TC0042BERSA(){
        //carga de los datos necesarios para realizar el test
        testCaseData.cargaDeDatosTest("TC0042BERSA");
        //login de usuarios
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        //carga de servicios para extraer idProductos de Cuentas o Tarjetas - widgets.Account - widgets.CreditCards
        compartidosAPI.cargaServiciosCompartidos();
        //obtenemos el idProducto de la cuenta
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        //ingresar datos para plazo fijo (tradicional o UVA), moneda, acredita al terminar, monto y plazo
        inversionesSteps.generarPlazoFijo("ARS", "T", "5002", "30", "N");
    }

    //@Test
    @DisplayName("TC-0043-BERSA: Alta de PF UVA $ 350 días")// (14-02-24) SE APAGA POR BUG RELACIONADOS 9240 - 8339
    public void TC0043BERSA(){
        //carga de los datos necesarios para realizar el test
        testCaseData.cargaDeDatosTest("TC0043BERSA");
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
    @DisplayName("TC-12973-BERSA: Plazo Fijo Tradicional en Dolares")//**OK**
    public void TC12973BERSA(){
        //carga de los datos necesarios para realizar el test
        testCaseData.cargaDeDatosTest("TC12973BERSA");
        //login de usuarios
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        //carga de servicios para extraer idProductos de Cuentas o Tarjetas - widgets.Account - widgets.CreditCards
        compartidosAPI.cargaServiciosCompartidos();
        //obtenemos el idProducto de la cuenta
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        //ingresar datos para plazo fijo (tradicional o UVA), moneda, acredita al terminar, monto y plazo
        //Se utiliza el renewalType (ultimo parametro a ingresar) como T para los Unysis y N para el resto.
        inversionesSteps.generarPlazoFijo("USD", "D", "10001", "30", "T");
    }

    //@Test
    @DisplayName("TC-12974-BERSA: Plazo Fijo UVA Precancelable")// (14-02-24) SE APAGA POR BUG RELACIONADOS 9240 - 8339
    public void TC12974BERSA(){
        //carga de los datos necesarios para realizar el test
        testCaseData.cargaDeDatosTest("TC12974BERSA");
        //login de usuarios
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        //carga de servicios para extraer idProductos de Cuentas o Tarjetas - widgets.Account - widgets.CreditCards
        compartidosAPI.cargaServiciosCompartidos();
        //obtenemos el idProducto de la cuenta
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        //ingresar datos para plazo fijo (tradicional o UVA), moneda, acredita al terminar, monto y plazo
        inversionesSteps.generarPlazoFijo("ARS", "A", "15001", "180", "T");
    }

    @Test
    @Tag("Criticos")
    @DisplayName("TC-111-BERSA: Suscribir FCI INFERIOR  A 10 MILLONES")// falta que configuren ambiente correctamente para probar
    public void TC111BERSA(){
        //carga de los datos necesarios para realizar el test
        testCaseData.cargaDeDatosTest("TC111BERSA");
        //login de usuarios
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        //carga de servicios para extraer idProductos de Cuentas o Tarjetas - widgets.Account - widgets.CreditCards
        compartidosAPI.cargaServiciosCompartidos();
        //obtenemos el idProducto de la cuenta
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        //Obtenemos los datos necesarios para operar el rescate (quotation, quotationId, fundId, clientNumber, name)
        inversionesSteps.obtenerDatosFCI(Settings.deltaPesosUnysis);
        //Suscribe un monto al FCI especificado en los datos capturados del response del servicio
        inversionesSteps.suscribirFCI(1001);
    }

    @Test
    @Tag("Criticos")
    @DisplayName("TC-113-BERSA: FCI - Rescatar - Importe Parcial - Validación de pantalla")// falta que configuren ambiente correctamente para probar
    public void TC113BERSA(){
        //carga de los datos necesarios para realizar el test
        testCaseData.cargaDeDatosTest("TC113BERSA");
        //login de usuarios
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        //carga de servicios para extraer idProductos de Cuentas o Tarjetas - widgets.Account - widgets.CreditCards
        compartidosAPI.cargaServiciosCompartidos();
        //obtenemos el idProducto de la cuenta
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        //Obtenemos los datos necesarios para operar el rescate (quotation, quotationId, fundId, clientNumber, name)
        inversionesSteps.obtenerDatosFCI(Settings.deltaPesosUnysis);
        //Realiza el rescate del monto especificado del FCI, calcula la quantityPartQuote dividiendo amount y quotation
        inversionesSteps.rescatarFCI(101);
    }
    
    @Test
    @DisplayName("TC-0115-BERSA: FCI - Simular API  (inicio a la funcionalidad)")
    public void TC0115BERSA(){
        //carga de los datos necesarios para realizar el test
        testCaseData.cargaDeDatosTest("TC0115BERSA");
        //login de usuarios
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        //inicio a la sección
        inversionesSteps.ingresoALaSeccion();
    }

    @Test
    @DisplayName("TC- 0125- BERSA - FCI - Solicitar baja cuenta comitente - Validación de pantalla - 'API'")
    public void TC0125BERSA(){
        //carga de los datos necesarios para realizar el test
        testCaseData.cargaDeDatosTest("TC0125BERSA");
        //login de usuarios
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        //inicio a la sección - Cuenta Comitente
        inversionesSteps.ingresoYValidacionBajaCuentaComitente();
    }

    @Test
    @DisplayName("TC-0129-BERSA: FCI  - Movimientos")
    public void TC0129BERSA(){
        //carga de los datos necesarios para realizar el test
        testCaseData.cargaDeDatosTest("TC0129BERSA");
        //login de usuarios
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        //Seleccionar Cuenta Comitente
        inversionesSteps.obtenerDatosFCI(Settings.deltaPesosUnysis);
        //validamos pantalla y cmapos por api FCI
        inversionesSteps.movimientosFCI();
    }

    @Test
    @DisplayName("TC-0130-BERSA - FCI  - Movimientos con descarga PDF")
    public void TC0130BERSA(){
        //carga de los datos necesarios para realizar el test
        testCaseData.cargaDeDatosTest("TC0130BERSA");
        //login de usuarios
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        //Seleccionar Cuenta Comitente
        inversionesSteps.obtenerDatosFCI(Settings.deltaPesosUnysis);
        //validamos pantalla y cmapos por api FCI
        inversionesSteps.movimientosFCI();
        //Se descarga el primer movimiento de FCI
        inversionesSteps.descargaMovimientoFCI(Settings.keyLongValueUnisys);
    }
    @Test
    @Tag("Criticos")
    @DisplayName("TC-0069-BERSA: Compra de moneda extranjera (USD)")
    public void  TC0069BERSA(){
        //carga de los datos necesarios para realizar el test
        testCaseData.cargaDeDatosTest("TC0069BERSA");
        //login de usuarios
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        //carga de servicios para extraer idProductos de Cuentas o Tarjetas - widgets.Account - widgets.CreditCards
        compartidosAPI.cargaServiciosCompartidos();
        //obtenemos el idProducto de la cuenta en dolares
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        //obtenemos el idProducto de la cuenta en pesos
        compartidosAPI.obtenerIdDeCuentaDestino(testCaseData.cuentaDestino);
        //Compra 10 USD
        inversionesSteps.compraUSD();
    }

    @Test
    @Tag("Criticos")
    @DisplayName("TC-0009-BERSA: Venta de moneda extranjera (USD)")
    public void TC0009BERSA(){
        //carga de los datos necesarios para realizar el test
        testCaseData.cargaDeDatosTest("TC0009BERSA");
        //login de usuarios
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        //carga de servicios para extraer idProductos de Cuentas o Tarjetas - widgets.Account - widgets.CreditCards
        compartidosAPI.cargaServiciosCompartidos();
        //obtenemos el idProducto de la cuenta en dolares
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        //obtenemos el idProducto de la cuenta en pesos
        compartidosAPI.obtenerIdDeCuentaDestino(testCaseData.cuentaDestino);
        //Compra 10 USD
        inversionesSteps.ventaUSD();
    }
}

