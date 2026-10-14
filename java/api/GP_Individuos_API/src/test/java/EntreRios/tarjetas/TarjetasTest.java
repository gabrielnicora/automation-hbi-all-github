package EntreRios.tarjetas;

import Config.Settings;
import Config.TestCaseData;
import Steps.Compartidos.CompartidosAPI;
import Steps.Login.LoginApiSteps;
import Steps.Pagos.PagosSteps;
import Steps.Tarjetas.TarjetaSteps;
import org.GP_Individuos_API.ConexionDB;
import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.MethodName.class)
public class TarjetasTest {
    LoginApiSteps loginApiSteps = new LoginApiSteps();
    CompartidosAPI compartidosAPI = new CompartidosAPI();
    TestCaseData testCaseData = new TestCaseData();
    TarjetaSteps tarjetaSteps = new TarjetaSteps();
    PagosSteps pagosSteps = new PagosSteps();
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
    @Order(1)
    @Tag("Criticos")
    @DisplayName("TC-0165-BERSA: PAGOS - Pagar Tarjeta de Crédito Master - Otro importe")
    public void TC0165BERSA() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0165BERSA");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //ejecucion de servicios compartidos datos de cuentas y datos de tarjetas
        compartidosAPI.cargaServiciosCompartidos();
        //obtener idProducto de la cuenta de Origen para el pago de tarjeta
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        //obtener idProducto de la cuenta debito para el pago de tarjeta
        compartidosAPI.obtenerIdDeTarjeta(testCaseData.debitCard);
        //obtener idProducto de la tarjeta a pagar
        compartidosAPI.obtenerIdDeTarjetaCredito(testCaseData.tarjetaAOperar);
        //se REaliza el pago de tarjeta
        tarjetaSteps.realizarPagoOtroImporte();
    }

    @Test
    @Order(2)
    @Tag("Criticos")
    @DisplayName("TC-12967-BERSA: PAGOS - Pagar Tarjeta de Crédito Visa - Otro importe")
    public void TC12967BERSA() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC12967BERSA");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //ejecucion de servicios compartidos datos de cuentas y datos de tarjetas
        compartidosAPI.cargaServiciosCompartidos();
        //obtener idProducto de la cuenta de Origen para el pago de tarjeta
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        //obtener idProducto de la cuenta debito para el pago de tarjeta
        compartidosAPI.obtenerIdDeTarjeta(testCaseData.debitCard);
        //obtener idProducto de la tarjeta a pagar
        compartidosAPI.obtenerIdDeTarjetaCredito(testCaseData.tarjetaAOperar);
        //se REaliza el pago de tarjeta
        tarjetaSteps.realizarPagoOtroImporte();
    }

    @Test
    @Order(3)
    @DisplayName("TC-16039-BERSA: Tarjetas de crédito - Pagar el importe mínimo de una tarjeta de crédito - Mastercard-Realizar el pago del importe mínimo de una tarjeta de crédito Mastercard.")
    public void TC16039BERSA(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC16039BERSA");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //ejecucion de servicios compartidos datos de cuentas y datos de tarjetas
        compartidosAPI.cargaServiciosCompartidos();
        //obtener idProducto de la cuenta de Origen para el pago de tarjeta
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        //obtener idProducto de la cuenta debito para el pago de tarjeta
        compartidosAPI.obtenerIdDeTarjeta(testCaseData.debitCard);
        //obtener idProducto de la tarjeta a pagar
        compartidosAPI.obtenerIdDeTarjetaCredito(testCaseData.tarjetaAOperar);
        //obtener el valor minimo a pagar desde el infoCreditread service
        tarjetaSteps.obtenerMontoMinimoAPagar();
        //se REaliza el pago de tarjeta
        tarjetaSteps.realizarPagoMinimo();
    }
    @Test
    @Order(4)
    @DisplayName("TC-0164-BERSA: Tarjeta de Crédito - Pagar mínimo de una Tarjeta de Crédito Visa - Validación de pantalla")
    public void TC0164BERSA(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0164BERSA");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //ejecucion de servicios compartidos datos de cuentas y datos de tarjetas
        compartidosAPI.cargaServiciosCompartidos();
        //obtener idProducto de la cuenta de Origen para el pago de tarjeta
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        //obtener idProducto de la cuenta debito para el pago de tarjeta
        compartidosAPI.obtenerIdDeTarjeta(testCaseData.debitCard);
        //obtener idProducto de la tarjeta a pagar
        compartidosAPI.obtenerIdDeTarjetaCredito(testCaseData.tarjetaAOperar);
        //obtener el valor minimo a pagar desde el infoCreditread service
        tarjetaSteps.obtenerMontoMinimoAPagar();
        //se REaliza el pago de tarjeta
        tarjetaSteps.realizarPagoMinimo();
    }

    @Test
    @DisplayName("TC-0149-BERSA - Tarjeta de Crédito - Solicitar Nueva Tarjeta de Crédito")
    public void TC0149BERSA(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0149BERSA");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //ejecucion de servicios compartidos datos de cuentas y datos de tarjetas
        compartidosAPI.cargaServiciosCompartidos();
        //obtener idProducto de la cuenta de Origen para el pago de tarjeta
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        //servicios link sincronizacion
        pagosSteps.sincronizacionPagosLink();
        //obtener datos para crear la tarjeta
        tarjetaSteps.obtenerDatosProductoTarjeta();
        //Soilictud de nueva tarjeta
        tarjetaSteps.generacionPedidoDeTarjeta();
    }

    @Test
    @DisplayName("TC-0192-BERSA - Tarjetas de credito - Solicitud de tarjeta de credito- Al solicitar una nueva TC y clickear el checkbox Persona expuesta políticamente no se debe permitir continuar con la solicitud-  validar pantalla mensaje")
    public void TC0192BERSA(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0192BERSA");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //ejecucion de servicios compartidos datos de cuentas y datos de tarjetas
        compartidosAPI.cargaServiciosCompartidos();
        //obtener idProducto de la cuenta de Origen para el pago de tarjeta
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        //servicios link sincronizacion
        pagosSteps.sincronizacionPagosLink();
        //obtener datos para crear la tarjeta
        tarjetaSteps.obtenerDatosProductoTarjeta();
        //Validar tipo de persona
        tarjetaSteps.validarTipoDePersonaPedidoTarjeta(true, false, false, Settings.PEP);
    }

    @Test
    @DisplayName("TC-0193-BERSA - Tarjetas de credito - Solicitud de tarjeta de credito- Al solicitar una nueva TC y clickear el checkbox \"residencia fiscar en el exterior\"  no se debe permitir continuar con la solicitud-  validar pantalla mensaje")
    public void TC0193BERSA(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0193BERSA");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //ejecucion de servicios compartidos datos de cuentas y datos de tarjetas
        compartidosAPI.cargaServiciosCompartidos();
        //obtener idProducto de la cuenta de Origen para el pago de tarjeta
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        //servicios link sincronizacion
        pagosSteps.sincronizacionPagosLink();
        //obtener datos para crear la tarjeta
        tarjetaSteps.obtenerDatosProductoTarjeta();
        //Validar tipo de persona
        tarjetaSteps.validarTipoDePersonaPedidoTarjeta(false, true, false, Settings.EXTERIOR);
    }

    @Test
    @DisplayName("TC-0194-BERSA - Tarjetas de credito - Solicitud de tarjeta de credito- Al solicitar una nueva TC y clickear el checkbox \"sujeto obligado iuf\"  no se debe permitir continuar con la solicitud-  validar pantalla mensaje")
    public void TC0194BERSA(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0194BERSA");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //ejecucion de servicios compartidos datos de cuentas y datos de tarjetas
        compartidosAPI.cargaServiciosCompartidos();
        //obtener idProducto de la cuenta de Origen para el pago de tarjeta
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        //servicios link sincronizacion
        pagosSteps.sincronizacionPagosLink();
        //obtener datos para crear la tarjeta
        tarjetaSteps.obtenerDatosProductoTarjeta();
        //Validar tipo de persona
        tarjetaSteps.validarTipoDePersonaPedidoTarjeta(false, false, true, Settings.UIF);
    }

    @Test
    @DisplayName("TC-128238-BERSA - API - Tarjeta de Crédito - Verificar que el dato cuilSolicitante se envíe " +
            "en el request al servicio application/create en nueva alta de TC")
    public void TC128238BERSA(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC128238BERSA");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //ejecucion de servicios compartidos datos de cuentas y datos de tarjetas
        compartidosAPI.cargaServiciosCompartidos();
        //obtener idProducto de la cuenta de Origen para el pago de tarjeta
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        //servicios link sincronizacion
        pagosSteps.sincronizacionPagosLink();
        //obtener datos para crear la tarjeta
        tarjetaSteps.obtenerDatosProductoTarjeta();
        //se GENERO EL IDAPPLICATION PARA EL PEDIDO DE TARJETA
        tarjetaSteps.generacionPedidoDeTarjeta();
        //CONFIRMACION DE TARJETA
        tarjetaSteps.confirmacionPedidoDeTarjeta();
        tarjetaSteps.validarCuilYDNI();
    }

    @Test
    @DisplayName("TC-128239-BERSA - API - Tarjeta de Crédito - Verificar que el dato cuilSolicitante " +
            "se envíe en el request al servicio application/update en alta de TC ya iniciada")
    public void TC128239BERSA(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC128239BERSA");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //ejecucion de servicios compartidos datos de cuentas y datos de tarjetas
        compartidosAPI.cargaServiciosCompartidos();
        //obtener idProducto de la cuenta de Origen para el pago de tarjeta
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        //servicios link sincronizacion
        pagosSteps.sincronizacionPagosLink();
        //obtener datos para crear la tarjeta
        tarjetaSteps.obtenerDatosProductoTarjeta();
        //se GENERO EL IDAPPLICATION PARA EL PEDIDO DE TARJETA
        tarjetaSteps.generacionPedidoDeTarjeta();
        //CONFIRMACION DE TARJETA
        tarjetaSteps.confirmacionPedidoDeTarjeta();
        tarjetaSteps.validarCuilYDNI();
    }


}
