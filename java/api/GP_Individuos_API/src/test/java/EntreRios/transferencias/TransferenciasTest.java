package EntreRios.transferencias;

import Config.Settings;
import Config.TestCaseData;
import Steps.Compartidos.CompartidosAPI;
import Steps.Login.LoginApiSteps;
import Steps.Transferencias.TransferenciasSteps;
import org.GP_Individuos_API.ConexionDB;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;


public class TransferenciasTest {
    LoginApiSteps loginApiSteps = new LoginApiSteps();
    TransferenciasSteps transferenciasSteps = new TransferenciasSteps();
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
    @Tag("Criticos")
    @DisplayName("TC-0139-BERSA: Transferencias Inmediatas - Entre cuentas propias - Flujo completo- Validar ticket con exito")
    public void TC0139BERSA(){
            //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
            testCaseData.cargaDeDatosTest("TC0139BERSA");
            loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
            //ejecucion de servicios compartidos datos de cuentas y datos de tarjetas
            compartidosAPI.cargaServiciosCompartidos();
            //Obtener Monto Actual de la cuenta Origen y Destino
            compartidosAPI.obtenerMontoCuentaActualOrigen(testCaseData.cuentaOrigen);
            compartidosAPI.obtenerMontoCuentaActualDestino(testCaseData.cuentaDestino);
            //obtencion de datos para realizar la transferencia
            compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
            compartidosAPI.obtenerIdDeCuentaDestino(testCaseData.cuentaDestino);
            compartidosAPI.obtenerIdDeTarjeta(testCaseData.debitCard);
            compartidosAPI.obtenerLabelDeTarjeta(testCaseData.debitCard);
            compartidosAPI.obtenerCbuCuenta(testCaseData.cuentaOrigen);
            //realiza la transferencia
            transferenciasSteps.trasnferenciasEntreCuentaPropias();
            //Obtener Monto Actualizado de cuenta Origen y Destino
            compartidosAPI.obtenerMontoActualizadoOrigen(testCaseData.cuentaOrigen);
            compartidosAPI.obtenerMontoActualizadoDestino(testCaseData.cuentaDestino);
            //Validaciones en Cuenta Origen y Destino
            transferenciasSteps.validarDescuentoCuenta();
            transferenciasSteps.validarIncrementoEnCuenta();
    }
    @Test
    @Tag("Criticos")
    @DisplayName("TC-0145-BERSA: Transferencias - Añadir nuevo destinatario - Con cbu valido")
    public void TC0145BERSA(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0145BERSA");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        transferenciasSteps.eliminarContactoAgendado();
        //Test
        transferenciasSteps.agendarNuevoContacto("TC0145BERSA",testCaseData.cbu);
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
    }
    @Test
    @Tag("Criticos")
    @DisplayName("TC-12930-BERSA: TRANSFERENCIAS - A cuentas de terceros CBU - mismo banco - Pesos")
    public void TC12930BERSA(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC12930BERSA");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        transferenciasSteps.eliminarContactoAgendado();
        //TEST
        //ejecucion de servicios compartidos datos de cuentas y datos de tarjetas
        compartidosAPI.cargaServiciosCompartidos();
        //Obtener Monto Actual de la cuenta Origen y Destino
        compartidosAPI.obtenerMontoCuentaActualOrigen(testCaseData.cuentaOrigen);
        //obtencion de datos para realizar la transferencia
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerCurrencyCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerLabelCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerIdDeTarjeta(testCaseData.debitCard);
        compartidosAPI.obtenerLabelDeTarjeta(testCaseData.debitCard);
        //realiza la transferencia a terceros
        transferenciasSteps.transferenciaATerceros();
        //Obtener Monto Actualizado de cuenta Origen y Destino
        compartidosAPI.obtenerMontoActualizadoOrigen(testCaseData.cuentaOrigen);
        //Validaciones en Cuenta Origen y Destino
        transferenciasSteps.validarDescuentoCuenta();
    }
    @Test
    @Tag("Criticos")
    @DisplayName("TC-12932-BERSA: TRANSFERENCIAS - A cuentas de terceros ALIAS - mismo banco - Pesos")
    public void TC12932BERSA() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC12932BERSA");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarAliasAgendado(testCaseData.alias);
        transferenciasSteps.eliminarContactoAgendado();
        //TEST
        //ejecucion de servicios compartidos datos de cuentas y datos de tarjetas
        compartidosAPI.cargaServiciosCompartidos();
        //Obtener Monto Actual de la cuenta Origen y Destino
        compartidosAPI.obtenerMontoCuentaActualOrigen(testCaseData.cuentaOrigen);
        //obtencion de datos para realizar la transferencia
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerCurrencyCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerLabelCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerIdDeTarjeta(testCaseData.debitCard);
        compartidosAPI.obtenerLabelDeTarjeta(testCaseData.debitCard);
        //realiza la transferencia a terceros
        transferenciasSteps.transferenciaATerceros();
        //Obtener Monto Actualizado de cuenta Origen y Destino
        compartidosAPI.obtenerMontoActualizadoOrigen(testCaseData.cuentaOrigen);
        //Validaciones en Cuenta Origen y Destino
        transferenciasSteps.validarDescuentoCuenta();
    }
    @Test
    @Tag("Criticos")
    @DisplayName("TC-12935-BERSA: Transferencias Programadas (Fecha especifica) CBU - terceros mismo banco  $ - NO AGENDADOS")
    public void TC12935BERSA() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC12935BERSA");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        transferenciasSteps.eliminarContactoAgendado();
        //TEST
        //ejecucion de servicios compartidos datos de cuentas y datos de tarjetas
        compartidosAPI.cargaServiciosCompartidos();
        //obtencion de datos para realizar la transferencia
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerCurrencyCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerLabelCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerIdDeTarjeta(testCaseData.debitCard);
        compartidosAPI.obtenerLabelDeTarjeta(testCaseData.debitCard);
        //Realiza la transferencia programada
        transferenciasSteps.transferenciaATerceros();
    }
    @Test
    @Tag("Criticos")
    @DisplayName("TC-12969-BERSA: TRANSFERENCIAS - A cuentas de terceros CVU - otros bancos - Pesos")
    public void TC12969BERSA() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC12969BERSA");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        transferenciasSteps.eliminarContactoAgendado();
        //TEST
        //ejecucion de servicios compartidos datos de cuentas y datos de tarjetas
        compartidosAPI.cargaServiciosCompartidos();
        //Obtener Monto Actual de la cuenta Origen y Destino
        compartidosAPI.obtenerMontoCuentaActualOrigen(testCaseData.cuentaOrigen);
        //obtencion de datos para realizar la transferencia
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerCurrencyCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerLabelCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerIdDeTarjeta(testCaseData.debitCard);
        compartidosAPI.obtenerLabelDeTarjeta(testCaseData.debitCard);
        //realiza la transferencia a terceros
        transferenciasSteps.transferenciaATerceros();
        //Obtener Monto Actualizado de cuenta Origen y Destino
        compartidosAPI.obtenerMontoActualizadoOrigen(testCaseData.cuentaOrigen);
        //Validaciones en Cuenta Origen y Destino
        transferenciasSteps.validarDescuentoCuenta();
    }
    @Test
    @Tag("Criticos")
    @DisplayName("TC-12981-BERSA: Transferencias - Eliminar destinatario de agenda")
    public void TC12981BERSA() {
        //PRECONDICION
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC12981BERSA");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        transferenciasSteps.eliminarContactoAgendado();
        transferenciasSteps.agendarNuevoContacto("TC12981BERSA",testCaseData.cbu);
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        //TEST
        transferenciasSteps.eliminarContactoAgendado();
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
    }
    @Test
    @Tag("Criticos")
    @DisplayName("TC-0014-BERSA: Generar CREDIN - Cuenta propia $ (mismo Banco u otro) - Mas de una cuenta Debito - CBU - Segundo factor por SMS")
    public void TC0014BERSA() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0014BERSA");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //ejecucion de servicios compartidos datos de cuentas y datos de tarjetas
        compartidosAPI.cargaServiciosCompartidos();
        //obtencion de datos para realizar el credin
        compartidosAPI.obtenerIdDeTarjeta(testCaseData.debitCard);
        compartidosAPI.obtenerCbuCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerCurrencyCuenta(testCaseData.cuentaOrigen);
        //obtener datos de cuenta de debito
        transferenciasSteps.obtenerDatosDebitos();
        //obtener datos de cuenta credito
        transferenciasSteps.obtenerDatosCredito(testCaseData.cbu);
        //realizar credin
        transferenciasSteps.generarCredin(testCaseData.cbu);
    }
    @Test
    @Tag("Criticos")
    @DisplayName("TC-12939-BERSA: TRANSFERENCIAS - Consultar CREDIN Originante")
    public void TC12939BERSA() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC12939BERSA");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //ejecucion de servicios compartidos datos de cuentas y datos de tarjetas
        compartidosAPI.cargaServiciosCompartidos();
        //obtencion de datos para realizar el credin
        compartidosAPI.obtenerIdDeTarjeta(testCaseData.debitCard);
        compartidosAPI.obtenerCbuCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerCurrencyCuenta(testCaseData.cuentaOrigen);
        //obtener datos de cuenta de debito
        transferenciasSteps.obtenerDatosDebitos();
        //obtener datos de cuenta credito
        transferenciasSteps.obtenerDatosCredito(testCaseData.cbu);
        //realizar credin
        transferenciasSteps.generarCredin(testCaseData.cbu);
        //TEST
        //consulta de credin originante
        transferenciasSteps.consultarCredin("Originante");
    }
    @Test
    @Tag("Criticos")
    @DisplayName("TC-12940-BERSA: TRANSFERENCIAS - Consultar CREDIN destinatario")
    public void TC12940BERSA() {
        //PRECONDICION
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC12940BERSA");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //ejecucion de servicios compartidos datos de cuentas y datos de tarjetas
        compartidosAPI.cargaServiciosCompartidos();
        //obtencion de datos para realizar el credin
        compartidosAPI.obtenerIdDeTarjeta(testCaseData.debitCard);
        compartidosAPI.obtenerCbuCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerCurrencyCuenta(testCaseData.cuentaOrigen);
        //obtener datos de cuenta de debito
        transferenciasSteps.obtenerDatosDebitos();
        //obtener datos de cuenta credito
        transferenciasSteps.obtenerDatosCredito(testCaseData.cbu);
        //realizar credin
        transferenciasSteps.generarCredin(testCaseData.cbu);
        //TEST
        transferenciasSteps.consultarCredin("Destinatario");
    }
    //@Test NO SE CUENTA CON USUARIO CROSS EN LOS BANCOS
    @Tag("Criticos")
    @DisplayName("TC-14250-BERSA: Transferencias CBU a cuentas propias otro banco $ - NO AGENDADOS")
    public void TC14250BERSA(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC14250BERSA");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        transferenciasSteps.eliminarContactoAgendado();
        //TEST
        //ejecucion de servicios compartidos datos de cuentas y datos de tarjetas
        compartidosAPI.cargaServiciosCompartidos();
        //Obtener Monto Actual de la cuenta Origen y Destino
        compartidosAPI.obtenerMontoCuentaActualOrigen(testCaseData.cuentaOrigen);
        //obtencion de datos para realizar la transferencia
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerCurrencyCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerLabelCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerIdDeTarjeta(testCaseData.debitCard);
        compartidosAPI.obtenerLabelDeTarjeta(testCaseData.debitCard);
        //realiza la transferencia a terceros
        transferenciasSteps.transferenciaATerceros();
        //Obtener Monto Actualizado de cuenta Origen y Destino
        compartidosAPI.obtenerMontoActualizadoOrigen(testCaseData.cuentaOrigen);
        //Validaciones en Cuenta Origen y Destino
        transferenciasSteps.validarDescuentoCuenta();
    }
    @Test
    @Tag("Criticos")
    @DisplayName("TC-0409-BERSA: TRANSFERENCIAS - terceros mismo banco  $ - AGENDADOS")
    public void TC0409BERSA(){
        //PREOCNDICION
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0409BERSA");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        transferenciasSteps.eliminarContactoAgendado();
        transferenciasSteps.agendarNuevoContacto("TC0409BERSA",testCaseData.cbu);
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        //TEST
        //ejecucion de servicios compartidos datos de cuentas y datos de tarjetas
        compartidosAPI.cargaServiciosCompartidos();
        //Obtener Monto Actual de la cuenta Origen y Destino
        compartidosAPI.obtenerMontoCuentaActualOrigen(testCaseData.cuentaOrigen);
        //obtencion de datos para realizar la transferencia
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerCurrencyCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerLabelCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerIdDeTarjeta(testCaseData.debitCard);
        compartidosAPI.obtenerLabelDeTarjeta(testCaseData.debitCard);
        //Transferencias a contacto agendado
        transferenciasSteps.trasnferenciaContactoAgendado("TC0409BERSA");
        //Obtener Monto Actualizado de cuenta Origen y Destino
        compartidosAPI.obtenerMontoActualizadoOrigen(testCaseData.cuentaOrigen);
        //Validaciones en Cuenta Origen y Destino
        transferenciasSteps.validarDescuentoCuenta();

    }
    //@Test NO SE CUENTA CON USUARIOS CROSS EN LOS BANCOS
    @Tag("Criticos")
    @DisplayName("TC-0407-BERSA: Transferencias ALIAS a cuentas propias otro banco $ - NO AGENDADOS")
    public void TC0407BERSA(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0407BERSA");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarAliasAgendado(testCaseData.alias);
        transferenciasSteps.eliminarContactoAgendado();
        //TEST
        //ejecucion de servicios compartidos datos de cuentas y datos de tarjetas
        compartidosAPI.cargaServiciosCompartidos();
        //Obtener Monto Actual de la cuenta Origen y Destino
        compartidosAPI.obtenerMontoCuentaActualOrigen(testCaseData.cuentaOrigen);
        //obtencion de datos para realizar la transferencia
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerCurrencyCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerLabelCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerIdDeTarjeta(testCaseData.debitCard);
        compartidosAPI.obtenerLabelDeTarjeta(testCaseData.debitCard);
        //realiza la transferencia a terceros
        transferenciasSteps.transferenciaATerceros();
        //Obtener Monto Actualizado de cuenta Origen y Destino
        compartidosAPI.obtenerMontoActualizadoOrigen(testCaseData.cuentaOrigen);
        //Validaciones en Cuenta Origen y Destino
        transferenciasSteps.validarDescuentoCuenta();
    }
    //@Test NO SE CUENTA CON USUARIOS
    @Tag("Criticos")
    @DisplayName("TC-0463-BERSA: Transferencias Programadas (Fecha especifica) ALIAS a cuentas propias otro banco $ - NO AGENDADOS")
    public void TC0463BERSA() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0463BERSA");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarAliasAgendado(testCaseData.alias);
        transferenciasSteps.eliminarContactoAgendado();
        //TEST
        //ejecucion de servicios compartidos datos de cuentas y datos de tarjetas
        compartidosAPI.cargaServiciosCompartidos();
        //obtencion de datos para realizar la transferencia
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerCurrencyCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerLabelCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerIdDeTarjeta(testCaseData.debitCard);
        compartidosAPI.obtenerLabelDeTarjeta(testCaseData.debitCard);
        //Realiza la transferencia programada
        transferenciasSteps.transferenciaATerceros();
    }
    @Test //revisar CBU
    @Tag("Criticos")
    @DisplayName("TC-0473-BERSA: Transferencias Programadas (Fecha especifica) CBU - terceros otros bancos $ - NO AGENDADOS")
    public void TC0473BERSA() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0473BERSA");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        transferenciasSteps.eliminarContactoAgendado();
        //TEST
        //ejecucion de servicios compartidos datos de cuentas y datos de tarjetas
        compartidosAPI.cargaServiciosCompartidos();
        //obtencion de datos para realizar la transferencia
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerCurrencyCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerLabelCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerIdDeTarjeta(testCaseData.debitCard);
        compartidosAPI.obtenerLabelDeTarjeta(testCaseData.debitCard);
        //Realiza la transferencia programada
        transferenciasSteps.transferenciaProgramadaTerceros();
    }
    @Test
    @Tag("Criticos")
    @DisplayName("TC-0411-BERSA: Transferencias - terceros otros bancos $ - AGENDADOS")
    public void TC0411BERSA(){
        //PREOCNDICION
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0411BERSA");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        transferenciasSteps.eliminarContactoAgendado();
        transferenciasSteps.agendarNuevoContacto("TC0411BERSA",testCaseData.cbu);
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        //TEST
        //ejecucion de servicios compartidos datos de cuentas y datos de tarjetas
        compartidosAPI.cargaServiciosCompartidos();
        //Obtener Monto Actual de la cuenta Origen y Destino
        compartidosAPI.obtenerMontoCuentaActualOrigen(testCaseData.cuentaOrigen);
        //obtencion de datos para realizar la transferencia
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerCurrencyCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerLabelCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerIdDeTarjeta(testCaseData.debitCard);
        compartidosAPI.obtenerLabelDeTarjeta(testCaseData.debitCard);
        //Transferencias a contacto agendado
        transferenciasSteps.trasnferenciaContactoAgendado("TC0411BERSA");
        //Obtener Monto Actualizado de cuenta Origen y Destino
        compartidosAPI.obtenerMontoActualizadoOrigen(testCaseData.cuentaOrigen);
        //Validaciones en Cuenta Origen y Destino
        transferenciasSteps.validarDescuentoCuenta();
    }
    @Test
    @Tag("Criticos")
    @DisplayName("TC-0427-BERSA: Transferencias CVU - terceros $ AGENDADOS")
    public void TC0427BERSA(){
        //PREOCNDICION
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0427BERSA");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        transferenciasSteps.eliminarContactoAgendado();
        transferenciasSteps.agendarNuevoContacto("TC0427BERSA",testCaseData.cbu);
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        //TEST
        //ejecucion de servicios compartidos datos de cuentas y datos de tarjetas
        compartidosAPI.cargaServiciosCompartidos();
        //Obtener Monto Actual de la cuenta Origen y Destino
        compartidosAPI.obtenerMontoCuentaActualOrigen(testCaseData.cuentaOrigen);
        //obtencion de datos para realizar la transferencia
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerCurrencyCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerLabelCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerIdDeTarjeta(testCaseData.debitCard);
        compartidosAPI.obtenerLabelDeTarjeta(testCaseData.debitCard);
        //Transferencias a contacto agendado
        transferenciasSteps.trasnferenciaContactoAgendado("TC0427BERSA");
        //Obtener Monto Actualizado de cuenta Origen y Destino
        compartidosAPI.obtenerMontoActualizadoOrigen(testCaseData.cuentaOrigen);
        //Validaciones en Cuenta Origen y Destino
        transferenciasSteps.validarDescuentoCuenta();
    }
    @Test
    @Tag("Criticos")
    @DisplayName("TC-0429-BERSA: Transferencias ALIAS de CVU - terceros $ NO AGENDADOS")
    public void TC0429BERSA(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0429BERSA");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarAliasAgendado(testCaseData.alias);
        transferenciasSteps.eliminarContactoAgendado();
        //TEST
        //ejecucion de servicios compartidos datos de cuentas y datos de tarjetas
        compartidosAPI.cargaServiciosCompartidos();
        //Obtener Monto Actual de la cuenta Origen y Destino
        compartidosAPI.obtenerMontoCuentaActualOrigen(testCaseData.cuentaOrigen);
        //obtencion de datos para realizar la transferencia
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerCurrencyCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerLabelCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerIdDeTarjeta(testCaseData.debitCard);
        compartidosAPI.obtenerLabelDeTarjeta(testCaseData.debitCard);
        //realiza la transferencia a terceros
        transferenciasSteps.transferenciaATerceros();
        //Obtener Monto Actualizado de cuenta Origen y Destino
        compartidosAPI.obtenerMontoActualizadoOrigen(testCaseData.cuentaOrigen);
        //Validaciones en Cuenta Origen y Destino
        transferenciasSteps.validarDescuentoCuenta();
    }
    @Test
    @Tag("Criticos")
    @DisplayName("TC-0465-BERSA: Transferencias Programadas (Fecha especifica) - terceros mismo banco  $ - AGENDADOS")
    public void TC0465BERSA(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0465BERSA");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        transferenciasSteps.eliminarContactoAgendado();
        transferenciasSteps.agendarNuevoContacto("TC0465BERSA",testCaseData.cbu);
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        //TEST
        //ejecucion de servicios compartidos datos de cuentas y datos de tarjetas
        compartidosAPI.cargaServiciosCompartidos();
        //obtencion de datos para realizar la transferencia
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerCurrencyCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerLabelCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerIdDeTarjeta(testCaseData.debitCard);
        compartidosAPI.obtenerLabelDeTarjeta(testCaseData.debitCard);
        //Realiza la transferencia programada
        transferenciasSteps.trasnferenciaProgramadaContactoAgendado("TC0465BERSA");
    }
    @Test
    @Tag("Criticos")
    @DisplayName("TC-0477-BERSA: Transferencias Programadas (Fecha especifica) ALIAS - terceros mismo banco  $ - NO AGENDADOS")
    public void TC0477BERSA() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0477BERSA");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarAliasAgendado(testCaseData.alias);
        transferenciasSteps.eliminarContactoAgendado();
        //TEST
        //ejecucion de servicios compartidos datos de cuentas y datos de tarjetas
        compartidosAPI.cargaServiciosCompartidos();
        //obtencion de datos para realizar la transferencia
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerCurrencyCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerLabelCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerIdDeTarjeta(testCaseData.debitCard);
        compartidosAPI.obtenerLabelDeTarjeta(testCaseData.debitCard);
        //Realiza la transferencia programada
        transferenciasSteps.transferenciaATerceros();
    }
    @Test
    @Tag("Criticos")
    @DisplayName("TC-0467-BERSA: Transferencias Programadas (Fecha especifica) - terceros otros bancos $ - AGENDADOS")
    public void TC0467BERSA(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0467BERSA");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        transferenciasSteps.eliminarContactoAgendado();
        transferenciasSteps.agendarNuevoContacto("TC0467BERSA",testCaseData.cbu);
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        //TEST
        //ejecucion de servicios compartidos datos de cuentas y datos de tarjetas
        compartidosAPI.cargaServiciosCompartidos();
        //obtencion de datos para realizar la transferencia
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerCurrencyCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerLabelCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerIdDeTarjeta(testCaseData.debitCard);
        compartidosAPI.obtenerLabelDeTarjeta(testCaseData.debitCard);
        //Realiza la transferencia programada
        transferenciasSteps.trasnferenciaProgramadaContactoAgendado("TC0467BERSA");
    }
    @Test
    @Tag("Criticos")
    @DisplayName("TC-0483-BERSA: Transferencias Programadas (Fecha especifica) CVU - terceros $ AGENDADOS")
    public void TC0483BERSA(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0483BERSA");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        transferenciasSteps.eliminarContactoAgendado();
        transferenciasSteps.agendarNuevoContacto("TC0483BERSA",testCaseData.cbu);
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        //TEST
        //ejecucion de servicios compartidos datos de cuentas y datos de tarjetas
        compartidosAPI.cargaServiciosCompartidos();
        //obtencion de datos para realizar la transferencia
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerCurrencyCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerLabelCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerIdDeTarjeta(testCaseData.debitCard);
        compartidosAPI.obtenerLabelDeTarjeta(testCaseData.debitCard);
        //Realiza la transferencia programada
        transferenciasSteps.trasnferenciaProgramadaContactoAgendado("TC0483BERSA");
    }
    @Test
    @Tag("Criticos")
    @DisplayName("TC-0484-BERSA: Transferencias Programadas (Fecha especifica) CVU - terceros $ NO AGENDADOS")
    public void TC0484BERSA() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0484BERSA");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        transferenciasSteps.eliminarContactoAgendado();
        //TEST
        //ejecucion de servicios compartidos datos de cuentas y datos de tarjetas
        compartidosAPI.cargaServiciosCompartidos();
        //obtencion de datos para realizar la transferencia
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerCurrencyCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerLabelCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerIdDeTarjeta(testCaseData.debitCard);
        compartidosAPI.obtenerLabelDeTarjeta(testCaseData.debitCard);
        //Realiza la transferencia programada
        transferenciasSteps.transferenciaProgramadaTerceros();
    }
    @Test
    @Tag("Criticos")
    @DisplayName("TC-0485-BERSA: Transferencias Programadas (Fecha especifica) ALIAS de CVU - terceros $ NO AGENDADOS")
    public void TC0485BERSA() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0485BERSA");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarAliasAgendado(testCaseData.alias);
        transferenciasSteps.eliminarContactoAgendado();
        //TEST
        //ejecucion de servicios compartidos datos de cuentas y datos de tarjetas
        compartidosAPI.cargaServiciosCompartidos();
        //obtencion de datos para realizar la transferencia
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerCurrencyCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerLabelCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerIdDeTarjeta(testCaseData.debitCard);
        compartidosAPI.obtenerLabelDeTarjeta(testCaseData.debitCard);
        //Realiza la transferencia programada
        transferenciasSteps.transferenciaProgramadaTerceros();
    }
    @Test
    @Tag("Criticos")
    @DisplayName("TC-0008-BERSA: Transferencia Intrabancaria en Pesos - Propias (del mismo banco) Cargada Manualmente - Programada en Fecha Futura Botón Volver")
    public void TC0008BERSA() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0008BERSA");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //ejecucion de servicios compartidos datos de cuentas y datos de tarjetas
        compartidosAPI.cargaServiciosCompartidos();
        //obtencion de datos para realizar la transferencia
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerIdDeCuentaDestino(testCaseData.cuentaDestino);
        compartidosAPI.obtenerIdDeTarjeta(testCaseData.debitCard);
        compartidosAPI.obtenerLabelDeTarjeta(testCaseData.debitCard);
        compartidosAPI.obtenerCbuCuenta(testCaseData.cuentaOrigen);
        //realiza la transferencia
        transferenciasSteps.trasnferenciasProgramadaEntreCuentaPropias();;
    }
    @Test
    @Tag("Criticos")
    @DisplayName("TC-12938-BERSA: TRANSFERENCIAS - Generar CREDIN - Dolares")
    public void TC12938BERSA () {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC12938BERSA");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //ejecucion de servicios compartidos datos de cuentas y datos de tarjetas
        compartidosAPI.cargaServiciosCompartidos();
        //obtencion de datos para realizar el credin
        //compartidosAPI.obtenerIdDeTarjeta(testCaseData.debitCard);
        compartidosAPI.obtenerCbuCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerCurrencyCuenta(testCaseData.cuentaOrigen);
        //obtener datos de cuenta de debito
        transferenciasSteps.obtenerDatosDebitos();
        //obtener datos de cuenta credito
        transferenciasSteps.obtenerDatosCredito(testCaseData.cbu);
        //realizar credin
        transferenciasSteps.generarCredin(testCaseData.cbu);
    }

    @Test
    @DisplayName("TC-18089-BERSA: Transferencia a cuentas propias con CBU de otra entidad sin haber completado el campo - Descripción - al agendarse")
    public void TC18089BERSA(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC18089BERSA");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        transferenciasSteps.eliminarContactoAgendado();
        //TEST
        //ejecucion de servicios compartidos datos de cuentas y datos de tarjetas
        compartidosAPI.cargaServiciosCompartidos();
        //Obtener Monto Actual de la cuenta Origen y Destino
        compartidosAPI.obtenerMontoCuentaActualOrigen(testCaseData.cuentaOrigen);
        //obtencion de datos para realizar la transferencia
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        compartidosAPI.mostrarNumeroCtaOrigen(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerCurrencyCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerLabelCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerIdDeTarjeta(testCaseData.debitCard);
        compartidosAPI.obtenerLabelDeTarjeta(testCaseData.debitCard);
        //realiza la transferencia a terceros
        transferenciasSteps.transferenciaATercerosSinCampoDescripcion();
        //Validaciones en Cuenta Origen y Destino
        transferenciasSteps.validarticketSinCampoDescripcion();
    }


    @Test
    @DisplayName("TC-18090-BERSA: Transferencias Programadas (Fecha especifica) " +
            "CBU a cuentas propias otro banco $ - NO AGENDADOS" +
            "Todos los campos")
    public void TC18090BERSA() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC18090BERSA");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        transferenciasSteps.eliminarContactoAgendado();
        //TEST
        //ejecucion de servicios compartidos datos de cuentas y datos de tarjetas
        compartidosAPI.cargaServiciosCompartidos();
        //obtencion de datos para realizar la transferencia
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerCurrencyCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerLabelCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerIdDeTarjeta(testCaseData.debitCard);
        compartidosAPI.obtenerLabelDeTarjeta(testCaseData.debitCard);
        compartidosAPI.mostrarNumeroCtaOrigen(testCaseData.cuentaOrigen);
        //Realiza la transferencia programada
        transferenciasSteps.transferenciaProgramadaTerceros();
        //Valida los datos emitidos en el ticket
        transferenciasSteps.validarticketTodosLosCampos(testCaseData.cuentaOrigen);
    }

    @Test
    @DisplayName("TC-18093-BERSA: Transferencia a Terceros con CBU de otra entidad sin haber completado el campo - Descripción - al agendarse")
    public void TC18093BERSA(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC18093BERSA");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        transferenciasSteps.eliminarContactoAgendado();
        //TEST
        //ejecucion de servicios compartidos datos de cuentas y datos de tarjetas
        compartidosAPI.cargaServiciosCompartidos();
        //Obtener Monto Actual de la cuenta Origen y Destino
        compartidosAPI.obtenerMontoCuentaActualOrigen(testCaseData.cuentaOrigen);
        //obtencion de datos para realizar la transferencia
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        compartidosAPI.mostrarNumeroCtaOrigen(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerCurrencyCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerLabelCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerIdDeTarjeta(testCaseData.debitCard);
        compartidosAPI.obtenerLabelDeTarjeta(testCaseData.debitCard);
        //realiza la transferencia a terceros
        transferenciasSteps.transferenciaATercerosSinCampoDescripcion();
        //Valida los datos emitidos en el ticket
        transferenciasSteps.validarticketSinCampoDescripcion();
    }

    @Test
    @DisplayName("TC-18094-BERSA: Transferencias Programadas (Fecha especifica) " +
            "CBU a Terceros otro banco $ - NO AGENDADOS" +
            "Todos los campos")
    public void TC18094BERSA() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC18094BERSA");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        transferenciasSteps.eliminarContactoAgendado();
        //TEST
        //ejecucion de servicios compartidos datos de cuentas y datos de tarjetas
        compartidosAPI.cargaServiciosCompartidos();
        //obtencion de datos para realizar la transferencia
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerCurrencyCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerLabelCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerIdDeTarjeta(testCaseData.debitCard);
        compartidosAPI.obtenerLabelDeTarjeta(testCaseData.debitCard);


        //Realiza la transferencia programada
        transferenciasSteps.transferenciaProgramadaTerceros();
        //Valida los datos emitidos en el ticket
        transferenciasSteps.validarticketTodosLosCampos(testCaseData.cuentaOrigen);
    }

    @Test
    @DisplayName("TC-18097-BERSA: Saldo actualizado_Transferencias a un Alias CVU  en pesos")
    public void TC18097BERSA() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC18097BERSA");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si el cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarAliasAgendado(testCaseData.alias);
        transferenciasSteps.eliminarContactoAgendado();
        //TEST
        //ejecucion de servicios compartidos datos de cuentas y datos de tarjetas
        compartidosAPI.cargaServiciosCompartidos();
        //Obtener Monto Actual de la cuenta Origen y Destino

        compartidosAPI.obtenerMontoCuentaActualOrigen(testCaseData.cuentaOrigen);
        //obtencion de datos para realizar la transferencia
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerCurrencyCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerLabelCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerIdDeTarjeta(testCaseData.debitCard);
        compartidosAPI.obtenerLabelDeTarjeta(testCaseData.debitCard);
        //realiza la transferencia a terceros
        transferenciasSteps.transferenciaATerceros();
        //Obtener Monto Actualizado de cuenta Origen y Destino
        compartidosAPI.obtenerMontoActualizadoOrigen(testCaseData.cuentaOrigen);
        //Validaciones en Cuenta Origen y Destino
        transferenciasSteps.validarDescuentoCuenta();
        //transferenciasSteps.validarIncrementoEnCuenta();
    }

    @Test
    @DisplayName("TC-18098-BERSA: Saldo actualizado_Transferencias a un " +
            "Alias CBU  en dólares")
    public void TC18098BERSA() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC18098BERSA");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si el cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarAliasAgendado(testCaseData.alias);
        transferenciasSteps.eliminarContactoAgendado();
        //TEST
        //ejecucion de servicios compartidos datos de cuentas y datos de tarjetas
        compartidosAPI.cargaServiciosCompartidos();
        //Obtener Monto Actual de la cuenta Origen y Destino

        compartidosAPI.obtenerMontoCuentaActualOrigen(testCaseData.cuentaOrigen);
        //obtencion de datos para realizar la transferencia
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerCurrencyCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerLabelCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerIdDeTarjeta(testCaseData.debitCard);
        compartidosAPI.obtenerLabelDeTarjeta(testCaseData.debitCard);
        //realiza la transferencia a terceros
        transferenciasSteps.transferenciaATerceros();
        //Obtener Monto Actualizado de cuenta Origen y Destino
        compartidosAPI.obtenerMontoActualizadoOrigen(testCaseData.cuentaOrigen);
        //Validaciones en Cuenta Origen y Destino
        transferenciasSteps.validarDescuentoCuenta();
        transferenciasSteps.validarIncrementoEnCuenta();
    }

    @Test
    @DisplayName("TC-18100-BERSA: WEB - Transferencias programadas - Fecha especifica agendada - Historial de transferencias")
    public void TC18100BERSA() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC18100BERSA");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
//        transferenciasSteps.eliminarContactoAgendado();
//        transferenciasSteps.agendarNuevoContacto("TC18100BERSA",testCaseData.cbu);
//        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
//        //TEST
        //ejecucion de servicios compartidos datos de cuentas y datos de tarjetas
        compartidosAPI.cargaServiciosCompartidos();
        //obtencion de datos para realizar la transferencia
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerCurrencyCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerLabelCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerIdDeTarjeta(testCaseData.debitCard);
        compartidosAPI.obtenerLabelDeTarjeta(testCaseData.debitCard);
        //Realiza la transferencia programada
        transferenciasSteps.transferenciaProgramadaTerceros();
        transferenciasSteps.validaHistorialDeTransferencias();
    }

    @Test
    @DisplayName("TC-18101-BERSA: WEB - Transferencias a contacto agendado - Historial de transferencias -")
    public void TC18101BERSA(){
        //PREOCNDICION
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC18101BERSA");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        transferenciasSteps.eliminarContactoAgendado();
        transferenciasSteps.agendarNuevoContacto("TC18101BERSA",testCaseData.cbu);
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        //TEST
        //ejecucion de servicios compartidos datos de cuentas y datos de tarjetas
        compartidosAPI.cargaServiciosCompartidos();
        //Obtener Monto Actual de la cuenta Origen y Destino
        compartidosAPI.obtenerMontoCuentaActualOrigen(testCaseData.cuentaOrigen);
        //obtencion de datos para realizar la transferencia
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerCurrencyCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerLabelCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerIdDeTarjeta(testCaseData.debitCard);
        compartidosAPI.obtenerLabelDeTarjeta(testCaseData.debitCard);
        //Transferencias a contacto agendado
        transferenciasSteps.trasnferenciaContactoAgendado("TC18101BERSA");
        //Validaciones en Cuenta Origen y Destino
        transferenciasSteps.validaHistorialDeTransferencias();
    }

    @Test
    @DisplayName("TC-18102-BERSA: WEB - Transferencias a CVU - Historial de transferencias -")
    public void TC18102BERSA() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC18102BERSA");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        transferenciasSteps.eliminarContactoAgendado();
        //TEST
        //ejecucion de servicios compartidos datos de cuentas y datos de tarjetas
        compartidosAPI.cargaServiciosCompartidos();
        //Obtener Monto Actual de la cuenta Origen y Destino
        compartidosAPI.obtenerMontoCuentaActualOrigen(testCaseData.cuentaOrigen);
        //obtencion de datos para realizar la transferencia
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerCurrencyCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerLabelCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerIdDeTarjeta(testCaseData.debitCard);
        compartidosAPI.obtenerLabelDeTarjeta(testCaseData.debitCard);
        //realiza la transferencia a terceros
        transferenciasSteps.transferenciaATerceros();
        //Validaciones en Cuenta Origen y Destino
        transferenciasSteps.validaHistorialDeTransferencias();
    }

    @Test
    @DisplayName("TC-16759-BERSA: WEB - Transferencias realizadas - Ver mis transferencias - Filtros -")
    public void TC16759BERSA() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC16759BERSA");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Validaciones Historial de Transferencias
        transferenciasSteps.validaHistorialDeTransferenciasUnAñoAntiguedad();
    }

    @Test //Desarrollar precondición que genera la transferencia
    @DisplayName("TC-16765-BERSA: WEB - Transferencias realizadas - Ver mis transferencias - rango mas nombre en buscador")
    public void TC16765BERSA() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC16765BERSA");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Busca transferencia por Descripción
        transferenciasSteps.validaHistorialDeTransferenciasPorRangoNombreODescripcion("Borges");
    }

    @Test
    @DisplayName("TC-16768-BERSA: WEB - Transferencias realizadas - Ver mis transferencias - rango mas nombre en buscador")
    public void TC16768BERSA() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC16768BERSA");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        transferenciasSteps.validaHistorialDeTransferenciasPorRangoNombreODescripcion("TestAutomation");
    }
    @Test
    @DisplayName("TC-18104-BERSA: WEB - Transferencias realizadas - Ver mis transferencias - Búsqueda sin resultados")
    public void TC18104BERSA() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC18104BERSA");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
//        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
//        transferenciasSteps.eliminarContactoAgendado();
//        //TEST
//        //ejecucion de servicios compartidos datos de cuentas y datos de tarjetas
//        compartidosAPI.cargaServiciosCompartidos();
//        //Obtener Monto Actual de la cuenta Origen y Destino
//        compartidosAPI.obtenerMontoCuentaActualOrigen(testCaseData.cuentaOrigen);
//        //obtencion de datos para realizar la transferencia
//        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
//        compartidosAPI.obtenerCurrencyCuenta(testCaseData.cuentaOrigen);
//        compartidosAPI.obtenerLabelCuenta(testCaseData.cuentaOrigen);
//        compartidosAPI.obtenerIdDeTarjeta(testCaseData.debitCard);
//        compartidosAPI.obtenerLabelDeTarjeta(testCaseData.debitCard);
//        //realiza la transferencia a terceros
//        transferenciasSteps.transferenciaATerceros();
        //Validaciones en Cuenta Origen y Destino
        transferenciasSteps.validaBusquedasDelHistorialSinResultados("nombresinresultados");
    }

    @Test
    @DisplayName("TC-0344- BERSA: TRANSFERENCIAS - A cuentas de terceros ALIAS - otros bancos - Pesos")
    public void TC0344BERSA(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0344BERSA");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarAliasAgendado(testCaseData.alias);
        transferenciasSteps.eliminarContactoAgendado();
        //TEST
        //ejecucion de servicios compartidos datos de cuentas y datos de tarjetas
        compartidosAPI.cargaServiciosCompartidos();
        //Obtener Monto Actual de la cuenta Origen y Destino
        compartidosAPI.obtenerMontoCuentaActualOrigen(testCaseData.cuentaOrigen);
        //obtencion de datos para realizar la transferencia
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerCurrencyCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerLabelCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerIdDeTarjeta(testCaseData.debitCard);
        compartidosAPI.obtenerLabelDeTarjeta(testCaseData.debitCard);
        //realiza la transferencia a terceros
        transferenciasSteps.transferenciaATerceros();
        //Obtener Monto Actualizado de cuenta Origen y Destino
        compartidosAPI.obtenerMontoActualizadoOrigen(testCaseData.cuentaOrigen);
        //Validaciones en Cuenta Origen y Destino
        transferenciasSteps.validarDescuentoCuenta();
    }

    @Test
    @DisplayName("TC-0410- BERSA: Transferencias - terceros mismo banco  USD - AGENDADOS")
    public void TC0410BERSA(){
        //PREOCNDICION
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0410BERSA");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        transferenciasSteps.eliminarContactoAgendado();
        transferenciasSteps.agendarNuevoContacto("TC0410BERSA",testCaseData.cbu);
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        //TEST
        //ejecucion de servicios compartidos datos de cuentas y datos de tarjetas
        compartidosAPI.cargaServiciosCompartidos();
        //Obtener Monto Actual de la cuenta Origen y Destino
        compartidosAPI.obtenerMontoCuentaActualOrigen(testCaseData.cuentaOrigen);
        //obtencion de datos para realizar la transferencia
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerCurrencyCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerLabelCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerIdDeTarjeta(testCaseData.debitCard);
        compartidosAPI.obtenerLabelDeTarjeta(testCaseData.debitCard);
        //Transferencias a contacto agendado
        transferenciasSteps.trasnferenciaContactoAgendado("TC0410BERSA");
        //Obtener Monto Actualizado de cuenta Origen y Destino
        compartidosAPI.obtenerMontoActualizadoOrigen(testCaseData.cuentaOrigen);
        //Validaciones en Cuenta Origen y Destino
        transferenciasSteps.validarDescuentoCuenta();
    }

    @Test
    @DisplayName("TC-0412- BERSA: Transferencias - terceros mismo banco  USD - AGENDADOS")
    public void TC0412BERSA(){
        //PREOCNDICION
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0412BERSA");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        transferenciasSteps.eliminarContactoAgendado();
        transferenciasSteps.agendarNuevoContacto("TC0412BERSA",testCaseData.cbu);
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        //TEST
        //ejecucion de servicios compartidos datos de cuentas y datos de tarjetas
        compartidosAPI.cargaServiciosCompartidos();
        //Obtener Monto Actual de la cuenta Origen y Destino
        compartidosAPI.obtenerMontoCuentaActualOrigen(testCaseData.cuentaOrigen);
        //obtencion de datos para realizar la transferencia
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerCurrencyCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerLabelCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerIdDeTarjeta(testCaseData.debitCard);
        compartidosAPI.obtenerLabelDeTarjeta(testCaseData.debitCard);
        //Transferencias a contacto agendado
        transferenciasSteps.trasnferenciaContactoAgendado("TC0412BERSA");
        //Obtener Monto Actualizado de cuenta Origen y Destino
        compartidosAPI.obtenerMontoActualizadoOrigen(testCaseData.cuentaOrigen);
        //Validaciones en Cuenta Origen y Destino
        transferenciasSteps.validarDescuentoCuenta();
    }

    //@Test
    @Tag("Criticos")
    @DisplayName("TC-0413-BERSA: Transferencias - terceros otros bancos INTERBANK (No PETERSEN) $ - AGENDADOS")
    public void TC0413BERSA() {
        //PREOCNDICION
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0413BERSA");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        transferenciasSteps.eliminarContactoAgendado();
        transferenciasSteps.agendarNuevoContacto("TC0413BERSA",testCaseData.cbu);
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        //TEST
        //ejecucion de servicios compartidos datos de cuentas y datos de tarjetas
        compartidosAPI.cargaServiciosCompartidos();
        //Obtener Monto Actual de la cuenta Origen y Destino
        compartidosAPI.obtenerMontoCuentaActualOrigen(testCaseData.cuentaOrigen);
        //obtencion de datos para realizar la transferencia
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerCurrencyCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerLabelCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerIdDeTarjeta(testCaseData.debitCard);
        compartidosAPI.obtenerLabelDeTarjeta(testCaseData.debitCard);
        //Transferencias a contacto agendado
        transferenciasSteps.trasnferenciaContactoAgendado("TC0413BERSA");
        //Obtener Monto Actualizado de cuenta Origen y Destino
        compartidosAPI.obtenerMontoActualizadoOrigen(testCaseData.cuentaOrigen);
        //Validaciones en Cuenta Origen y Destino
        transferenciasSteps.validarDescuentoCuenta();
    }

    @Test
    @DisplayName("TC-0418-BERSA: Transferencias CBU - terceros otros bancos USD - NO AGENDADOS")
    public void TC0418BERSA(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0418BERSA");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        transferenciasSteps.eliminarContactoAgendado();
        //TEST
        //ejecucion de servicios compartidos datos de cuentas y datos de tarjetas
        compartidosAPI.cargaServiciosCompartidos();
        //Obtener Monto Actual de la cuenta Origen y Destino
        compartidosAPI.obtenerMontoCuentaActualOrigen(testCaseData.cuentaOrigen);
        //obtencion de datos para realizar la transferencia
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerCurrencyCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerLabelCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerIdDeTarjeta(testCaseData.debitCard);
        compartidosAPI.obtenerLabelDeTarjeta(testCaseData.debitCard);
        //realiza la transferencia a terceros
        transferenciasSteps.transferenciaProgramadaTerceros();
        //Obtener Monto Actualizado de cuenta Origen y Destino
        compartidosAPI.obtenerMontoActualizadoOrigen(testCaseData.cuentaOrigen);
        //Validaciones en Cuenta Origen y Destino
        //transferenciasSteps.validarDescuentoCuenta();
    }

    //@Test
    @DisplayName("TC-0420-BERSA: Transferencias CBU - terceros otros bancos INTERBANK (No PETERSEN) USD - NO AGENDADOS")
    public void TC0420BERSA() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0420BERSA");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        transferenciasSteps.eliminarContactoAgendado();
        //TEST
        //ejecucion de servicios compartidos datos de cuentas y datos de tarjetas
        compartidosAPI.cargaServiciosCompartidos();
        //Obtener Monto Actual de la cuenta Origen y Destino
        compartidosAPI.obtenerMontoCuentaActualOrigen(testCaseData.cuentaOrigen);
        //obtencion de datos para realizar la transferencia
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerCurrencyCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerLabelCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerIdDeTarjeta(testCaseData.debitCard);
        compartidosAPI.obtenerLabelDeTarjeta(testCaseData.debitCard);
        //realiza la transferencia a terceros
        transferenciasSteps.transferenciaATerceros();
        //Obtener Monto Actualizado de cuenta Origen y Destino
        compartidosAPI.obtenerMontoActualizadoOrigen(testCaseData.cuentaOrigen);
        //Validaciones en Cuenta Origen y Destino
        transferenciasSteps.validarDescuentoCuenta();
    }

    @Test
    @DisplayName("TC-0422-BERSA: Transferencias ALIAS - terceros mismo banco  USD - NO AGENDADOS")
    public void TC0422BERSA(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0422BERSA");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarAliasAgendado(testCaseData.alias);
        transferenciasSteps.eliminarContactoAgendado();
        //TEST
        //ejecucion de servicios compartidos datos de cuentas y datos de tarjetas
        compartidosAPI.cargaServiciosCompartidos();
        //Obtener Monto Actual de la cuenta Origen y Destino
        compartidosAPI.obtenerMontoCuentaActualOrigen(testCaseData.cuentaOrigen);
        //obtencion de datos para realizar la transferencia
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerCurrencyCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerLabelCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerIdDeTarjeta(testCaseData.debitCard);
        compartidosAPI.obtenerLabelDeTarjeta(testCaseData.debitCard);
        //realiza la transferencia a terceros
        transferenciasSteps.transferenciaATercerosUsd();
        //Obtener Monto Actualizado de cuenta Origen y Destino
        compartidosAPI.obtenerMontoActualizadoOrigen(testCaseData.cuentaOrigen);
        //Validaciones en Cuenta Origen y Destino
        transferenciasSteps.validarDescuentoCuenta();
    }

    @Test
    @DisplayName("TC-0424-BERSA: Transferencias ALIAS - terceros otros bancos USD - NO AGENDADOS")
    public void TC0424BERSA(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0424BERSA");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarAliasAgendado(testCaseData.alias);
        transferenciasSteps.eliminarContactoAgendado();
        //TEST
        //ejecucion de servicios compartidos datos de cuentas y datos de tarjetas
        compartidosAPI.cargaServiciosCompartidos();
        //Obtener Monto Actual de la cuenta Origen y Destino
        compartidosAPI.obtenerMontoCuentaActualOrigen(testCaseData.cuentaOrigen);
        //obtencion de datos para realizar la transferencia
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerCurrencyCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerLabelCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerIdDeTarjeta(testCaseData.debitCard);
        compartidosAPI.obtenerLabelDeTarjeta(testCaseData.debitCard);
        //realiza la transferencia a terceros
        transferenciasSteps.transferenciaATerceros();
        //Obtener Monto Actualizado de cuenta Origen y Destino
        compartidosAPI.obtenerMontoActualizadoOrigen(testCaseData.cuentaOrigen);
        //Validaciones en Cuenta Origen y Destino
        transferenciasSteps.validarDescuentoCuenta();
    }

    @Test
    @DisplayName("TC-0468-BERSA: Transferencias Programadas (Fecha especifica) - terceros otros bancos USD - AGENDADOS")
    public void TC0468BERSA(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0468BERSA");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        transferenciasSteps.eliminarContactoAgendado();
        transferenciasSteps.agendarNuevoContacto("TC0465BERSA",testCaseData.cbu);
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        //TEST
        //ejecucion de servicios compartidos datos de cuentas y datos de tarjetas
        compartidosAPI.cargaServiciosCompartidos();
        //obtencion de datos para realizar la transferencia
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerCurrencyCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerLabelCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerIdDeTarjeta(testCaseData.debitCard);
        compartidosAPI.obtenerLabelDeTarjeta(testCaseData.debitCard);
        //Realiza la transferencia programada
        transferenciasSteps.trasnferenciaProgramadaContactoAgendado("TC0465BERSA");
    }

    //@Test
    @DisplayName("TC-0469-BERSA: Transferencias Programadas (Fecha especifica) - terceros otros bancos INTERBANK (No PETERSEN) $ - AGENDADOS")
    public void TC0469BERSA(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0469BERSA");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        transferenciasSteps.eliminarContactoAgendado();
        transferenciasSteps.agendarNuevoContacto("TC0469BERSA",testCaseData.cbu);
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        //TEST
        //ejecucion de servicios compartidos datos de cuentas y datos de tarjetas
        compartidosAPI.cargaServiciosCompartidos();
        //obtencion de datos para realizar la transferencia
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerCurrencyCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerLabelCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerIdDeTarjeta(testCaseData.debitCard);
        compartidosAPI.obtenerLabelDeTarjeta(testCaseData.debitCard);
        //Realiza la transferencia programada
        transferenciasSteps.trasnferenciaProgramadaContactoAgendado("TC0469BERSA");
    }

    //@Test
    @Tag("Criticos")
    @DisplayName("TC-0470-BERSA: Transferencias - terceros otros bancos INTERBANK (No PETERSEN) USD - AGENDADOS")
    public void TC0470BERSA(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0469BERSA");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        transferenciasSteps.eliminarContactoAgendado();
        transferenciasSteps.agendarNuevoContacto("TC0469BERSA",testCaseData.cbu);
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        //TEST
        //ejecucion de servicios compartidos datos de cuentas y datos de tarjetas
        compartidosAPI.cargaServiciosCompartidos();
        //obtencion de datos para realizar la transferencia
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerCurrencyCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerLabelCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerIdDeTarjeta(testCaseData.debitCard);
        compartidosAPI.obtenerLabelDeTarjeta(testCaseData.debitCard);
        //Realiza la transferencia programada
        transferenciasSteps.trasnferenciaProgramadaContactoAgendado("TC0469BERSA");
    }

    @Test
    @DisplayName("TC-0472-BERSA: Transferencias Programadas (Fecha especifica) CBU - terceros mismo banco  USD - NO AGENDADOS")
    public void TC0472BERSA() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0472BERSA");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        transferenciasSteps.eliminarContactoAgendado();
        //TEST
        //ejecucion de servicios compartidos datos de cuentas y datos de tarjetas
        compartidosAPI.cargaServiciosCompartidos();
        //obtencion de datos para realizar la transferencia
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerCurrencyCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerLabelCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerIdDeTarjeta(testCaseData.debitCard);
        compartidosAPI.obtenerLabelDeTarjeta(testCaseData.debitCard);
        //Realiza la transferencia programada
        transferenciasSteps.transferenciaProgramadaTerceros();
    }

    @Test
    @DisplayName("TC-0474-BERSA: Transferencias Programadas (Fecha especifica) CBU - terceros otros bancos USD - NO AGENDADOS")
    public void TC0474BERSA() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0474BERSA");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        transferenciasSteps.eliminarContactoAgendado();
        //TEST
        //ejecucion de servicios compartidos datos de cuentas y datos de tarjetas
        compartidosAPI.cargaServiciosCompartidos();
        //obtencion de datos para realizar la transferencia
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerCurrencyCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerLabelCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerIdDeTarjeta(testCaseData.debitCard);
        compartidosAPI.obtenerLabelDeTarjeta(testCaseData.debitCard);
        //Realiza la transferencia programada
        transferenciasSteps.transferenciaProgramadaTerceros();
    }

    @Test
    @DisplayName("TC-0478-BERSA: Transferencias Programadas (Fecha especifica) ALIAS - terceros mismo banco  USD - NO AGENDADOS")
    public void TC0478BERSA() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0478BERSA");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarAliasAgendado(testCaseData.alias);
        transferenciasSteps.eliminarContactoAgendado();
        //TEST
        //ejecucion de servicios compartidos datos de cuentas y datos de tarjetas
        compartidosAPI.cargaServiciosCompartidos();
        //obtencion de datos para realizar la transferencia
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerCurrencyCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerLabelCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerIdDeTarjeta(testCaseData.debitCard);
        compartidosAPI.obtenerLabelDeTarjeta(testCaseData.debitCard);
        //Realiza la transferencia programada
        transferenciasSteps.transferenciaProgramadaTerceros();
    }

    @Test
    @DisplayName("TC-0479-BERSA: Transferencias Programadas (Fecha especifica) ALIAS - terceros otros bancos $ - NO AGENDADOS")
    public void TC0479BERSA() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0479BERSA");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarAliasAgendado(testCaseData.alias);
        transferenciasSteps.eliminarContactoAgendado();
        //TEST
        //ejecucion de servicios compartidos datos de cuentas y datos de tarjetas
        compartidosAPI.cargaServiciosCompartidos();
        //obtencion de datos para realizar la transferencia
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerCurrencyCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerLabelCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerIdDeTarjeta(testCaseData.debitCard);
        compartidosAPI.obtenerLabelDeTarjeta(testCaseData.debitCard);
        //Realiza la transferencia programada
        transferenciasSteps.transferenciaProgramadaTerceros();
    }

    @Test
    @DisplayName("TC-0480-BERSA: Transferencias Programadas (Fecha especifica) ALIAS - terceros otros bancos USD - NO AGENDADOS")
    public void TC0480BERSA() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0480BERSA");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarAliasAgendado(testCaseData.alias);
        transferenciasSteps.eliminarContactoAgendado();
        //TEST
        //ejecucion de servicios compartidos datos de cuentas y datos de tarjetas
        compartidosAPI.cargaServiciosCompartidos();
        //obtencion de datos para realizar la transferencia
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerCurrencyCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerLabelCuenta(testCaseData.cuentaOrigen);
        compartidosAPI.obtenerIdDeTarjeta(testCaseData.debitCard);
        compartidosAPI.obtenerLabelDeTarjeta(testCaseData.debitCard);
        //Realiza la transferencia programada
        transferenciasSteps.transferenciaProgramadaTerceros();
    }
}
