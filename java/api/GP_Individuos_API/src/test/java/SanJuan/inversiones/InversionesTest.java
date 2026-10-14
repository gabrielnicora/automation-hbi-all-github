package SanJuan.inversiones;

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

    String deltaPesos = "A-DELTA PESOS";
    String deltaPesosUnysis = "A-Delta Pesos";

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
    @DisplayName("TC-0042-BSJ: Alta de PF Tradicional en $ a 30 días")//**OK**
    public void TC0042BSJ(){
        //carga de los datos necesarios para realizar el test
        testCaseData.cargaDeDatosTest("TC0042BSJ");
        //login de usuarios
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        //carga de servicios para extraer idProductos de Cuentas o Tarjetas - widgets.Account - widgets.CreditCards
        compartidosAPI.cargaServiciosCompartidos();
        //obtenemos el idProducto de la cuenta
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        //ingresar datos para plazo fijo (tradicional o UVA), moneda, acredita al terminar, monto y plazo
        inversionesSteps.generarPlazoFijo("ARS", "T", "20001", "30", "N");
    }

    //@Test Se comenta por problemas en los bancos unisys
    @DisplayName("TC-0043-BSJ: Alta de PF UVA $ 350 días")//(11-12-24) SE APAGA POR BUG RELACIONADOS 9240 - 8339
    public void TC0043BSJ(){
        //carga de los datos necesarios para realizar el test
        testCaseData.cargaDeDatosTest("TC0043BSJ");
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
    @DisplayName("TC-12973-BSJ: Plazo Fijo Tradicional en Dolares")//**OK**
    public void TC12973BSJ(){
        //carga de los datos necesarios para realizar el test
        testCaseData.cargaDeDatosTest("TC12973BSJ");
        //login de usuarios
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        //carga de servicios para extraer idProductos de Cuentas o Tarjetas - widgets.Account - widgets.CreditCards
        compartidosAPI.cargaServiciosCompartidos();
        //obtenemos el idProducto de la cuenta
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        //ingresar datos para plazo fijo (tradicional o UVA), moneda, acredita al terminar, monto y plazo
        inversionesSteps.generarPlazoFijo("USD", "D", "1001", "30", "N");
    }

    //@Test
    @DisplayName("TC-12974-BSJ: Plazo Fijo UVA Precancelable")//APAGADO POR BUG 9240 - 8339
    public void TC12974BSJ(){
        //carga de los datos necesarios para realizar el test
        testCaseData.cargaDeDatosTest("TC12974BSJ");
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
    @DisplayName("TC-0111-BSJ: Suscribir FCI INFERIOR  A 10 MILLONES")//Caso desarrollado, falta que configuren ambiente correctamente para probar
    public void TC0111BSJ(){
        //carga de los datos necesarios para realizar el test
        testCaseData.cargaDeDatosTest("TC0111BSJ");
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
    @DisplayName("TC-0113-BSJ: FCI - Rescatar - Importe Parcial - Validación de pantalla")//Se bloquea por BUG - https://technisys.atlassian.net/browse/ARPETEREVO-11015
    public void TC0113BSJ(){
        //carga de los datos necesarios para realizar el test
        testCaseData.cargaDeDatosTest("TC0113BSJ");
        //login de usuarios
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        //carga de servicios para extraer idProductos de Cuentas o Tarjetas - widgets.Account - widgets.CreditCards
        compartidosAPI.cargaServiciosCompartidos();
        //obtenemos el idProducto de la cuenta
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        //Obtenemos los datos necesarios para operar el rescate (quotation, quotationId, fundId, clientNumber, name)
        inversionesSteps.obtenerDatosFCI(Settings.deltaPesosUnysis);
        //Realiza el rescate del monto especificado del FCI, calcula la quantityPartQuote dividiendo amount y quotation
        inversionesSteps.rescatarFCI(1001);
    }

    @Test
    @Tag("Criticos")
    @DisplayName("TC-0069-BSJ: Compra de moneda extranjera (USD)")
    public void  TC0069BSJ(){
        //carga de los datos necesarios para realizar el test
        testCaseData.cargaDeDatosTest("TC0069BSJ");
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
    @DisplayName("TC-0009-BSJ: Venta de moneda extranjera (USD)")
    public void TC0009BSJ(){
        //carga de los datos necesarios para realizar el test
        testCaseData.cargaDeDatosTest("TC0009BSJ");
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
    @Test
    @DisplayName("TC-0115-BSJ: FCI - Simular API  (inicio a la funcionalidad)")
    public void TC0115BSJ(){
        //carga de los datos necesarios para realizar el test
        testCaseData.cargaDeDatosTest("TC0115BSJ");
        //login de usuarios
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        //inicio a la sección
        inversionesSteps.ingresoALaSeccion();
    }
    @Test
    @DisplayName("TC- 0125- BSJ - FCI - Solicitar baja cuenta comitente - Validación de pantalla - 'API'")
    public void TC0125BSJ(){
        //carga de los datos necesarios para realizar el test
        testCaseData.cargaDeDatosTest("TC0125BSJ");
        //login de usuarios
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        //inicio a la sección - Cuenta Comitente
        inversionesSteps.ingresoYValidacionBajaCuentaComitente();
    }

    @Test
    @DisplayName("TC-0129-BSJ: FCI  - Movimientos")
    public void TC0129BSJ(){
        //carga de los datos necesarios para realizar el test
        testCaseData.cargaDeDatosTest("TC0129BSJ");
        //login de usuarios
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        //Seleccionar Cuenta Comitente
        inversionesSteps.obtenerDatosFCI(Settings.deltaPesosUnysis);
        //validamos pantalla y cmapos por api FCI
        inversionesSteps.movimientosFCI();
    }

    @Test
    @DisplayName("TC-0130-BSJ - FCI  - Movimientos con descarga PDF")
    public void TC0130BSJ(){
        //carga de los datos necesarios para realizar el test
        testCaseData.cargaDeDatosTest("TC0130BSJ");
        //login de usuarios
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        //Seleccionar Cuenta Comitente
        inversionesSteps.obtenerDatosFCI(Settings.deltaPesosUnysis);
        //validamos pantalla y cmapos por api FCI
        inversionesSteps.movimientosFCI();
        //Se descarga el primer movimiento de FCI
        inversionesSteps.descargaMovimientoFCI(Settings.keyLongValueUnisys);
    }

    //@Test
    @Tag("Criticos")
    @DisplayName("TC-29417-BSJ: TC02-(WEB) Inversiones-Mercado de capitales-Validar que se muestra la tabla de tipo de Especie con sus respectivos campos")
    public void TC29417BSJ(){
        //carga de los datos necesarios para realizar el test
        testCaseData.cargaDeDatosTest("TC29417BSJ");
        //login de usuarios
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        //validar tabla de tipo de Especie con sus respectivos campos
        inversionesSteps.validarTablaEspecie();
    }


}

