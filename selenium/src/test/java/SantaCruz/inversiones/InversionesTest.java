package SantaCruz.inversiones;

import Config.Settings;
import Config.TestCaseData;
import Steps.Compartidos.CompartidosAPI;
import Steps.Inversiones.InversionesSteps;
import Steps.Login.LoginApiSteps;
import org.GP_Individuos_API.ConexionDB;
import org.junit.jupiter.api.*;

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
    @DisplayName("TC-0042-BSC: Alta de PF Tradicional en $ a 30 días")//**OK**
    public void TC0042BSC(){
        //carga de los datos necesarios para realizar el test
        testCaseData.cargaDeDatosTest("TC0042BSC");
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
    @DisplayName("TC-0043-BSC: Alta de PF UVA $ 350 días")//SE APAGA CASO POR BUG - 9240 - 8339
    public void TC0043BSC(){
        //carga de los datos necesarios para realizar el test
        testCaseData.cargaDeDatosTest("TC0043BSC");
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
    @DisplayName("TC-12973-BSC: Plazo Fijo Tradicional en Dolares")//**OK**
    public void TC12973BSC(){
        //carga de los datos necesarios para realizar el test
        testCaseData.cargaDeDatosTest("TC12973BSC");
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
    @DisplayName("TC-12974-BSC: Plazo Fijo UVA Precancelable")// (14-02-24) SE APAGA POR BUG RELACIONADOS 9240 - 8339
    public void TC12974BSC(){
        //carga de los datos necesarios para realizar el test
        testCaseData.cargaDeDatosTest("TC12974BSC");
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
    @DisplayName("TC-111-BSC: Suscribir FCI INFERIOR  A 10 MILLONES")//**OK**
    public void TC111BSC(){
        //carga de los datos necesarios para realizar el test
        testCaseData.cargaDeDatosTest("TC111BSC");
        //login de usuarios
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        //carga de servicios para extraer idProductos de Cuentas o Tarjetas - widgets.Account - widgets.CreditCards
        compartidosAPI.cargaServiciosCompartidos();
        //obtenemos el idProducto de la cuenta
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        //Obtenemos los datos necesarios para operar el rescate (quotation, quotationId, fundId, clientNumber, name)
        inversionesSteps.obtenerDatosFCI(Settings.deltaPesosUnysis);
        //Suscribe un monto al FCI especificado en los datos capturados del response del servicio
        inversionesSteps.suscribirFCI(1002);
    }

    @Test
    @Tag("Criticos")
    @DisplayName("TC-0113-BSC: FCI - Rescatar - Importe Parcial - Validación de pantalla")//**OK**
    public void TC0113BSC(){
        //carga de los datos necesarios para realizar el test
        testCaseData.cargaDeDatosTest("TC0113BSC");
        //login de usuarios
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        //carga de servicios para extraer idProductos de Cuentas o Tarjetas - widgets.Account - widgets.CreditCards
        compartidosAPI.cargaServiciosCompartidos();
        //obtenemos el idProducto de la cuenta
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        //Obtenemos los datos necesarios para operar el rescate (quotation, quotationId, fundId, clientNumber, name)
        inversionesSteps.obtenerDatosFCI(Settings.deltaPesosUnysis);
        //Realiza el rescate del monto especificado del FCI, calcula la quantityPartQuote dividiendo amount y quotation
        inversionesSteps.rescatarFCI(102);
    }
    @Test
    @DisplayName("TC-0115-BSC: FCI - Simular API  (inicio a la funcionalidad)")
    public void TC0115BSC(){
        //carga de los datos necesarios para realizar el test
        testCaseData.cargaDeDatosTest("TC0115BSC");
        //login de usuarios
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        //inicio a la sección
        inversionesSteps.ingresoALaSeccion();
    }
    @Test
    @DisplayName("TC- 0125- BSC - FCI - Solicitar baja cuenta comitente - Validación de pantalla - 'API'")
    public void TC0125BSC(){
        //carga de los datos necesarios para realizar el test
        testCaseData.cargaDeDatosTest("TC0125BSC");
        //login de usuarios
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        //inicio a la sección - Cuenta Comitente
        inversionesSteps.ingresoYValidacionBajaCuentaComitente();
    }

    @Test
    @DisplayName("TC-0129-BSC: FCI  - Movimientos")
    public void TC0129BSC(){
        //carga de los datos necesarios para realizar el test
        testCaseData.cargaDeDatosTest("TC0129BSC");
        //login de usuarios
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        //Seleccionar Cuenta Comitente
        inversionesSteps.obtenerDatosFCI(Settings.deltaPesosUnysis);
        //validamos pantalla y cmapos por api FCI
        inversionesSteps.movimientosFCI();
    }

    @Test
    @DisplayName("TC-0130-BSC - FCI  - Movimientos con descarga PDF")
    public void TC0130BSC(){
        //carga de los datos necesarios para realizar el test
        testCaseData.cargaDeDatosTest("TC0130BSC");
        //login de usuarios
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        //Seleccionar Cuenta Comitente
        inversionesSteps.obtenerDatosFCI(Settings.deltaAhorroPlusUnisys);
        //validamos pantalla y cmapos por api FCI
        inversionesSteps.movimientosFCI();
        //Se descarga el primer movimiento de FCI
        inversionesSteps.descargaMovimientoFCI(Settings.keyLongValueBSC);
    }

    @Test
    @Tag("Criticos")
    @DisplayName("TC-0069-BSC: Compra de moneda extranjera (USD)")
    public void  TC0069BSC(){
        //carga de los datos necesarios para realizar el test
        testCaseData.cargaDeDatosTest("TC0069BSC");
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
    @DisplayName("TC-0009-BSC: Venta de moneda extranjera (USD)")
    public void TC0009BSC(){
        //carga de los datos necesarios para realizar el test
        testCaseData.cargaDeDatosTest("TC0009BSC");
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