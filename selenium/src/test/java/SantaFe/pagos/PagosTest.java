package SantaFe.pagos;

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
    @DisplayName("TC-0012-BSF: DEBIN Comprador - Consulta DEBINes históricos - Listado")
    public void TC0012BSF(){
        //carga de los datos necesarios para realizar el test
        testCaseData.cargaDeDatosTest("TC0012BSF");
        //login de usuarios
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        //Sincronizamos los nuevos servicos de pagos por API LINK
        //pagosSteps.sincronizacionPagosLink();
        //ejecucion de servicio consulta de debines historicos
        pagosSteps.realizarConsultaDebinHistorico();
    }

    @Test
    @Order(3)
    @Tag("Criticos")
    @DisplayName("TC-0013-BSF: Generar orden DEBIN - Seleccionando CBU")
    public void TC0013BSF() {
        //carga de los datos necesarios para realizar el test
        testCaseData.cargaDeDatosTest("TC0013BSF");
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
    @DisplayName("TC-0015-BSF: Adherir DEBIN recurrente (Pesos) -  2do factor por SMS - Descargar - Boton Volver")
    public void TC0015BSF() {
        //carga de los datos necesarios para realizar el test
        testCaseData.cargaDeDatosTest("TC0015BSF");
        //login de usuarios
        loginApiSteps.loginUsuario(testCaseData.usuario,testCaseData.password);
        //carga de servicios para extraer idProductos de Cuentas o Tarjetas - widgets.Account - widgets.CreditCards
        compartidosAPI.cargaServiciosCompartidos();
        //obtener idProducto de la cuenta de Origen para la adhesion de recurrencia
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        //Sincronizamos los nuevos servicos de pagos por API LINK
        //pagosSteps.sincronizacionPagosLink();
        //Gneracion de recurrencia
        pagosSteps.adherirRecurrencia();
    }
    @Test
    @Order(1)
    @Tag("Criticos")
    @DisplayName("TC-0074-BSF: ADHERIR NUEVO PAGO O SERVICIO")
    public void TC0074BSF() {
        //carga de los datos necesarios para realizar el test
        testCaseData.cargaDeDatosTest("TC0074BSF");
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
    @Test
    @Order(4)
    @Tag("Criticos")
    @DisplayName("TC-0070-BSF: Aceptación de DEBIN")
    public void TC0070BSF(){
        //carga de los datos necesarios para realizar el test
        testCaseData.cargaDeDatosTest("TC0070BSF");
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
    @DisplayName("TC-12964-BSF: PAGOS - Pagar Impuesto/Servicio Nuevo Pago (Sin Base)")
    public void TC12964BSF() {
        //carga de los datos necesarios para realizar el test
        testCaseData.cargaDeDatosTest("TC12964BSF");
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
    @DisplayName("TC-12966-BSF: PAGOS - Pagar Impuesto/Servicio Eliminar servicio")
    public void TC12966BSF() {
        //carga de los datos necesarios para realizar el test
        testCaseData.cargaDeDatosTest("TC12966BSF");
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
    @DisplayName("TC-0108-BSF: Pagos - Consulta de comprobantes")
    public void TC0108BSF() {
        //carga de los datos necesarios para realizar el test
        testCaseData.cargaDeDatosTest("TC0108BSF");
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
    @DisplayName("TC-0137-BSF: PAGOS -COMPROBANTES - Descargar periodo en PDF")
    public void TC0137BSF() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0137BSF");
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
        utils.fixedWait(7000);
        //obtener ID del periodo que se mando a Mensajes
        mensajesSteps.obtenerIdArchivoParaDescargaPagosPDF();
        //descarga de periodo
        pagosSteps.descargaDeComprobantePeriodo(Settings.PAGOS_PDF);
    }

    @Test
    @DisplayName("TC-0138-BSF: PAGOS -COMPROBANTES - Descargar periodo en XLS")
    public void TC0138BSF() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0138BSF");
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
        utils.fixedWait(7000);
        //obtener ID del periodo que se mando a Mensajes
        mensajesSteps.obtenerIdArchivoParaDescargaPagosXLS();
        //descarga de periodo
        pagosSteps.descargaDeComprobantePeriodo(Settings.PAGOS_XLS);
    }
}

