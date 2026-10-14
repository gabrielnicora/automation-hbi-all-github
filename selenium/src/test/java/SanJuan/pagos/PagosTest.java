package SanJuan.pagos;

import Config.Settings;
import Config.TestCaseData;
import Steps.Compartidos.CompartidosAPI;
import Steps.Login.LoginApiSteps;
import Steps.Mensajes.MensajesSteps;
import Steps.Pagos.PagosSteps;
import org.GP_Individuos_API.ConexionDB;
import org.GP_Individuos_API.Utils;
import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.MethodName.class)
public class PagosTest {
    LoginApiSteps loginApiSteps = new LoginApiSteps();
    CompartidosAPI compartidosAPI = new CompartidosAPI();
    PagosSteps pagosSteps = new PagosSteps();
    TestCaseData testCaseData = new TestCaseData();
    MensajesSteps mensajesSteps = new MensajesSteps();
    Utils utils = new Utils();
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
    @DisplayName("TC-0012-BSJ: DEBIN Comprador - Consulta DEBINes históricos - Listado")
    public void TC0012BSJ(){
        //carga de los datos necesarios para realizar el test
        testCaseData.cargaDeDatosTest("TC0012BSJ");
        //login de usuarios
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        //Sincronizamos los nuevos servicos de pagos por API LINK
        pagosSteps.sincronizacionPagosLink();
        //ejecucion de servicio consulta de debines historicos
        pagosSteps.realizarConsultaDebinHistorico();
    }

    @Test
    @Order(3)
    @Tag("Criticos")
    @DisplayName("TC-0013-BSJ: Generar orden DEBIN - Seleccionando CBU")
    public void TC0013BSJ() {
        //carga de los datos necesarios para realizar el test
        testCaseData.cargaDeDatosTest("TC0013BSJ");
        //login de usuarios
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        //carga de servicios para extraer idProductos de Cuentas o Tarjetas - widgets.Account - widgets.CreditCards
        compartidosAPI.cargaServiciosCompartidos();
        //obtener idProducto de la cuenta de Origen para la generación del Debin
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        //Sincronizamos los nuevos servicos de pagos por API LINK
        pagosSteps.sincronizacionPagosLink();
        //Generación de Debin
        pagosSteps.generarDebin(testCaseData.cbu,testCaseData.cuitDestino);
    }
    @Test
    @Tag("Criticos")
    @DisplayName("TC-0015-BSJ: Adherir DEBIN recurrente (Pesos) -  2do factor por SMS - Descargar - Boton Volver")
    public void TC0015BSJ() {
        //carga de los datos necesarios para realizar el test
        testCaseData.cargaDeDatosTest("TC0015BSJ");
        //login de usuarios
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        //carga de servicios para extraer idProductos de Cuentas o Tarjetas - widgets.Account - widgets.CreditCards
        compartidosAPI.cargaServiciosCompartidos();
        //obtener idProducto de la cuenta de Origen para la adhesion de recurrencia
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        //Sincronizamos los nuevos servicos de pagos por API LINK
        pagosSteps.sincronizacionPagosLink();
        //Gneracion de recurrencia
        pagosSteps.adherirRecurrencia();
    }
    @Test
    @Order(1)
    @Tag("Criticos")
    @DisplayName("TC-0074-BSJ: ADHERIR NUEVO PAGO O SERVICIO")
    public void TC0074BSJ() {
        //carga de los datos necesarios para realizar el test
        testCaseData.cargaDeDatosTest("TC0074BSJ");
        //login de usuarios
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        //carga de servicios para extraer idProductos de Cuentas o Tarjetas - widgets.Account - widgets.CreditCards
        compartidosAPI.cargaServiciosCompartidos();
        //obtener id de la tarjeta de debito
        compartidosAPI.obtenerIdDeTarjeta(testCaseData.debitCard);
        //obtener el label de la tarjeta de debito
        compartidosAPI.obtenerLabelDeTarjeta(testCaseData.debitCard);
        //Sincronizamos los nuevos servicos de pagos por API LINK
        pagosSteps.sincronizacionPagosLink();
        //Generar la adhesion del servicio por api
        pagosSteps.adherirSerivicio();
    }
    @Test //se bloquea caso por bug - https://technisys.atlassian.net/browse/ARPETEREVO-10799
    @Order(4)
    @Tag("Criticos")
    @DisplayName("TC-0070-BSJ: Aceptación de DEBIN")
    public void TC0070BSJ(){
        //carga de los datos necesarios para realizar el test
        testCaseData.cargaDeDatosTest("TC0070BSJ");
        //login de usuarios
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        //carga de servicios para extraer idProductos de Cuentas o Tarjetas - widgets.Account - widgets.CreditCards
        compartidosAPI.cargaServiciosCompartidos();
        //obtener id de la tarjeta de debito
        compartidosAPI.obtenerIdDeTarjeta(testCaseData.debitCard);
        //Sincronizamos los nuevos servicos de pagos por API LINK
        pagosSteps.sincronizacionPagosLink();
        //Obtener el idDebin para poder aceptarlo
        pagosSteps.obtenerIdDebin();
        //obtener informacion del  idDebin con el servicio de Retrieve - Extración de fechas createDate - expirationDate
        pagosSteps.obtenerDatosIdDebin();
        //Aceptamos el debin
        pagosSteps.aceptarDebinGenerado(testCaseData.cbu);
    }
    @Test
    @Tag("Criticos")
    @DisplayName("TC-12964-BSJ: PAGOS - Pagar Impuesto/Servicio Nuevo Pago (Sin Base)")
    public void TC12964BSJ() {
        //carga de los datos necesarios para realizar el test
        testCaseData.cargaDeDatosTest("TC12964BSJ");
        //login de usuarios
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        //carga de servicios para extraer idProductos de Cuentas o Tarjetas - widgets.Account - widgets.CreditCards
        compartidosAPI.cargaServiciosCompartidos();
        //obtenemos el idProducto de la cuenta
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        //obtener el idProducto de la tarjeta de debito
        compartidosAPI.obtenerIdDeTarjeta(testCaseData.debitCard);
        //Sincronizamos los nuevos servicos de pagos por API LINK
        pagosSteps.sincronizacionPagosLink();
        //obtnemos el conceptCode - Adhesioncode
        pagosSteps.obtenerDatosPagos();
        //Pagamos el servicio
        pagosSteps.pagarServicio();
    }
    @Test
    @Order(2)
    @Tag("Criticos")
    @DisplayName("TC-12966-BSJ: PAGOS - Pagar Impuesto/Servicio Eliminar servicio")
    public void TC12966BSJ() {
        //carga de los datos necesarios para realizar el test
        testCaseData.cargaDeDatosTest("TC12966BSJ");
        //login de usuarios
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        //carga de servicios para extraer idProductos de Cuentas o Tarjetas - widgets.Account - widgets.CreditCards
        compartidosAPI.cargaServiciosCompartidos();
        //obtener el idProducto de la tarjeta de debito
        compartidosAPI.obtenerIdDeTarjeta(testCaseData.debitCard);
        //obtener el label de la tarjeta de debito
        compartidosAPI.obtenerLabelDeTarjeta(testCaseData.debitCard);
        //Sincronizamos los nuevos servicos de pagos por API LINK
        pagosSteps.sincronizacionPagosLink();
        //obtener el adhesion para eliminar el servicio
        pagosSteps.obtenerAdhesionCodeServicio();
        //Eliminamos el serivicio añadido
        pagosSteps.eliminarServicio();
    }

    @Test
    @DisplayName("TC-0108-BSJ: Pagos - Consulta de comprobantes")
    public void TC0108BSJ() {
        //carga de los datos necesarios para realizar el test
        testCaseData.cargaDeDatosTest("TC0108BSJ");
        //login de usuarios
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        //carga de servicios para extraer idProductos de Cuentas o Tarjetas - widgets.Account - widgets.CreditCards
        compartidosAPI.cargaServiciosCompartidos();
        //obtener el idProducto de la tarjeta de debito
        compartidosAPI.obtenerIdDeTarjeta(testCaseData.debitCard);
        //Sincronizamos los nuevos servicos de pagos por API LINK
        pagosSteps.sincronizacionPagosLink();
        //Consulta de comprobantes de pagos
        pagosSteps.comprobantePagos("false","");
    }

    @Test
    @DisplayName("TC-0137-BSJ: PAGOS -COMPROBANTES - Descargar periodo en PDF")
    public void TC0137BSJ() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0137BSJ");
        //login de usuarios
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        //carga de servicios para extraer idProductos de Cuentas o Tarjetas - widgets.Account - widgets.CreditCards
        compartidosAPI.cargaServiciosCompartidos();
        //obtener el idProducto de la tarjeta de debito
        compartidosAPI.obtenerIdDeTarjeta(testCaseData.debitCard);
        //Sincronizamos los nuevos servicos de pagos por API LINK
        pagosSteps.sincronizacionPagosLink();
        //Consulta de comprobantes de pagos
        pagosSteps.comprobantePagos("true","pdf");
        //obtener ID del periodo que se mando a Mensajes
        utils.fixedWait(8000);
        mensajesSteps.obtenerIdArchivoParaDescargaPagosPDF();
        //descarga de periodo
        pagosSteps.descargaDeComprobantePeriodo(Settings.PAGOS_PDF);
    }

    @Test
    @DisplayName("TC-0138-BSJ: PAGOS -COMPROBANTES - Descargar periodo en XLS")
    public void TC0138BSJ() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0138BSJ");
        //login de usuarios
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        //carga de servicios para extraer idProductos de Cuentas o Tarjetas - widgets.Account - widgets.CreditCards
        compartidosAPI.cargaServiciosCompartidos();
        //obtener el idProducto de la tarjeta de debito
        compartidosAPI.obtenerIdDeTarjeta(testCaseData.debitCard);
        //Sincronizamos los nuevos servicos de pagos por API LINK
        pagosSteps.sincronizacionPagosLink();
        //Consulta de comprobantes de pagos
        pagosSteps.comprobantePagos("true","xls");
        //obtener ID del periodo que se mando a Mensajes
        utils.fixedWait(8000);
        mensajesSteps.obtenerIdArchivoParaDescargaPagosXLS();
        //descarga de periodo
        pagosSteps.descargaDeComprobantePeriodo(Settings.PAGOS_XLS);
    }
}

