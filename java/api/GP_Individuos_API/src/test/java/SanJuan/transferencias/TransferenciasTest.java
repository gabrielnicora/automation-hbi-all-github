package SanJuan.transferencias;

import Config.Settings;
import Config.TestCaseData;
import Steps.Compartidos.CompartidosAPI;
import Steps.Login.LoginApiSteps;
import Steps.Transferencias.TransferenciasSteps;
import org.GP_Individuos_API.ConexionDB;
import org.junit.experimental.categories.Category;
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
    @DisplayName("TC-0139-BSJ: Transferencias Inmediatas - Entre cuentas propias - Flujo completo- Validar ticket con exito")
    public void TC0139BSJ(){
            //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
            testCaseData.cargaDeDatosTest("TC0139BSJ");
            loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
            //ejecucion de servicios compartidos datos de cuentas y datos de tarjetas
            compartidosAPI.cargaServiciosCompartidos();
            //Obtener Monto Actual de la cuenta Origen y Destino
            compartidosAPI.obtenerMontoCuentaActualOrigen(testCaseData.cuentaOrigen);
            //compartidosAPI.obtenerMontoCuentaActualDestino(testCaseData.cuentaDestino);
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
            //compartidosAPI.obtenerMontoActualizadoDestino(testCaseData.cuentaDestino);
            //Validaciones en Cuenta Origen y Destino
            transferenciasSteps.validarDescuentoCuenta();
//            transferenciasSteps.validarIncrementoEnCuenta();
    }
    @Test
    @Tag("Criticos")
    @DisplayName("TC-0145-BSJ: Transferencias - Añadir nuevo destinatario - Con cbu valido")
    public void TC0145BSJ(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0145BSJ");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        transferenciasSteps.eliminarContactoAgendado();
        //Test
        transferenciasSteps.agendarNuevoContacto("TC0145BSJ",testCaseData.cbu);
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
    }
    @Test
    @Tag("Criticos")
    @DisplayName("TC-12930-BSJ: TRANSFERENCIAS - A cuentas de terceros CBU - mismo banco - Pesos")
    public void TC12930BSJ(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC12930BSJ");
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
    @DisplayName("TC-12932-BSJ: TRANSFERENCIAS - A cuentas de terceros ALIAS - mismo banco - Pesos")
    public void TC12932BSJ() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC12932BSJ");
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
    @DisplayName("TC-12935-BSJ: Transferencias Programadas (Fecha especifica) CBU - terceros mismo banco  $ - NO AGENDADOS")
    public void TC12935BSJ() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC12935BSJ");
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
    @DisplayName("TC-12969-BSJ: TRANSFERENCIAS - A cuentas de terceros CVU - otros bancos - Pesos")
    public void TC12969BSJ() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC12969BSJ");
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
    @DisplayName("TC-12981-BSJ: Transferencias - Eliminar destinatario de agenda")
    public void TC12981BSJ() {
        //PRECONDICION
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC12981BSJ");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        transferenciasSteps.eliminarContactoAgendado();
        transferenciasSteps.agendarNuevoContacto("TC12981BSJ",testCaseData.cbu);
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        //TEST
        transferenciasSteps.eliminarContactoAgendado();
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
    }
    @Test
    @Tag("Criticos")
    @DisplayName("TC-0014-BSJ: Generar CREDIN - Cuenta propia $ (mismo Banco u otro) - Mas de una cuenta Debito - CBU - Segundo factor por SMS")
    public void TC0014BSJ() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0014BSJ");
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
    @DisplayName("TC-12939-BSJ: TRANSFERENCIAS - Consultar CREDIN Originante")
    public void TC12939BSJ() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC12939BSJ");
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
    @DisplayName("TC-12940-BSJ: TRANSFERENCIAS - Consultar CREDIN destinatario")
    public void TC12940BSJ() {
        //PRECONDICION
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC12940BSJ");
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
    @Test
    @Tag("Criticos")
    @DisplayName("TC-14250-BSJ: Transferencias CBU a cuentas propias otro banco $ - NO AGENDADOS")
    public void TC14250BSJ(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC14250BSJ");
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
    @DisplayName("TC-0409-BSJ: TRANSFERENCIAS - terceros mismo banco  $ - AGENDADOS")
    public void TC0409BSJ(){
        //PREOCNDICION
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0409BSJ");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        transferenciasSteps.eliminarContactoAgendado();
        transferenciasSteps.agendarNuevoContacto("TC0409BSJ",testCaseData.cbu);
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
        transferenciasSteps.trasnferenciaContactoAgendado("TC0409BSJ");
        //Obtener Monto Actualizado de cuenta Origen y Destino
        compartidosAPI.obtenerMontoActualizadoOrigen(testCaseData.cuentaOrigen);
        //Validaciones en Cuenta Origen y Destino
        transferenciasSteps.validarDescuentoCuenta();

    }
    @Test
    @Tag("Criticos")
    @DisplayName("TC-0407-BSJ: Transferencias ALIAS a cuentas propias otro banco $ - NO AGENDADOS")
    public void TC0407BSJ(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0407BSJ");
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
    @DisplayName("TC-0463-BSJ: Transferencias Programadas (Fecha especifica) ALIAS a cuentas propias otro banco $ - NO AGENDADOS")
    public void TC0463BSJ() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0463BSJ");
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
    @Test //revisar CBU
    @Tag("Criticos")
    @DisplayName("TC-0473-BSJ: Transferencias Programadas (Fecha especifica) CBU - terceros otros bancos $ - NO AGENDADOS")
    public void TC0473BSJ() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0473BSJ");
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
    @DisplayName("TC-0411-BSJ: Transferencias - terceros otros bancos $ - AGENDADOS")
    public void TC0411BSJ(){
        //PREOCNDICION
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0411BSJ");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        transferenciasSteps.eliminarContactoAgendado();
        transferenciasSteps.agendarNuevoContacto("TC0411BSJ",testCaseData.cbu);
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
        transferenciasSteps.trasnferenciaContactoAgendado("TC0411BSJ");
        //Obtener Monto Actualizado de cuenta Origen y Destino
        compartidosAPI.obtenerMontoActualizadoOrigen(testCaseData.cuentaOrigen);
        //Validaciones en Cuenta Origen y Destino
        transferenciasSteps.validarDescuentoCuenta();
    }
    @Test
    @Tag("Criticos")
    @DisplayName("TC-0427-BSJ: Transferencias CVU - terceros $ AGENDADOS")
    public void TC0427BSJ(){
        //PREOCNDICION
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0427BSJ");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        transferenciasSteps.eliminarContactoAgendado();
        transferenciasSteps.agendarNuevoContacto("TC0427BSJ",testCaseData.cbu);
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
        transferenciasSteps.trasnferenciaContactoAgendado("TC0427BSJ");
        //Obtener Monto Actualizado de cuenta Origen y Destino
        compartidosAPI.obtenerMontoActualizadoOrigen(testCaseData.cuentaOrigen);
        //Validaciones en Cuenta Origen y Destino
        transferenciasSteps.validarDescuentoCuenta();
    }
    @Test
    @Tag("Criticos")
    @DisplayName("TC-0429-BSJ: Transferencias ALIAS de CVU - terceros $ NO AGENDADOS")
    public void TC0429BSJ(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0429BSJ");
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
    @DisplayName("TC-0465-BSJ: Transferencias Programadas (Fecha especifica) - terceros mismo banco  $ - AGENDADOS")
    public void TC0465BSJ(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0465BSJ");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        transferenciasSteps.eliminarContactoAgendado();
        transferenciasSteps.agendarNuevoContacto("TC0465BSJ",testCaseData.cbu);
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
        transferenciasSteps.trasnferenciaProgramadaContactoAgendado("TC0465BSJ");
    }
    @Test
    @Tag("Criticos")
    @DisplayName("TC-0477-BSJ: Transferencias Programadas (Fecha especifica) ALIAS - terceros mismo banco  $ - NO AGENDADOS")
    public void TC0477BSJ() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0477BSJ");
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
    @DisplayName("TC-0467-BSJ: Transferencias Programadas (Fecha especifica) - terceros otros bancos $ - AGENDADOS")
    public void TC0467BSJ(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0467BSJ");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        transferenciasSteps.eliminarContactoAgendado();
        transferenciasSteps.agendarNuevoContacto("TC0467BSJ",testCaseData.cbu);
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
        transferenciasSteps.trasnferenciaProgramadaContactoAgendado("TC0467BSJ");
    }
    @Test
    @Tag("Criticos")
    @DisplayName("TC-0483-BSJ: Transferencias Programadas (Fecha especifica) CVU - terceros $ AGENDADOS")
    public void TC0483BSJ(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0483BSJ");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        transferenciasSteps.eliminarContactoAgendado();
        transferenciasSteps.agendarNuevoContacto("TC0483BSJ",testCaseData.cbu);
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
        transferenciasSteps.trasnferenciaProgramadaContactoAgendado("TC0483BSJ");
    }
    @Test //refactor error de validacion
    @Tag("Criticos")
    @DisplayName("TC-0484-BSJ: Transferencias Programadas (Fecha especifica) CVU - terceros $ NO AGENDADOS")
    public void TC0484BSJ() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0484BSJ");
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
    @DisplayName("TC-0485-BSJ: Transferencias Programadas (Fecha especifica) ALIAS de CVU - terceros $ NO AGENDADOS")
    public void TC0485BSJ() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0485BSJ");
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
    @DisplayName("TC-0008-BSJ: Transferencia Intrabancaria en Pesos - Propias (del mismo banco) Cargada Manualmente - Programada en Fecha Futura Botón Volver")
    public void TC0008BSJ() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0008BSJ");
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
    @DisplayName("TC-12938-BSJ: TRANSFERENCIAS - Generar CREDIN - Dolares")
    public void TC12938BSJ () {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC12938BSJ");
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
    @DisplayName("TC-18089-BSJ: Transferencia a cuentas propias con CBU de otra entidad sin haber completado el campo - Descripción - al agendarse")
    public void TC18089BSJ(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC18089BSJ");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        transferenciasSteps.eliminarContactoAgendado();
        transferenciasSteps.agendarNuevoContacto("TC18089BSJ(",testCaseData.cbu);
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
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
        //Obtener Monto Actualizado de cuenta Origen y Destino
        compartidosAPI.obtenerMontoActualizadoOrigen(testCaseData.cuentaOrigen);
        //Validaciones en Cuenta Origen y Destino
        transferenciasSteps.validarDescuentoCuenta();
        transferenciasSteps.validarticketSinCampoDescripcion();

    }

    @Test
    @DisplayName("TC-18090-BSJ: Transferencias Programadas (Fecha especifica) " +
            "CBU a cuentas propias otro banco $ - NO AGENDADOS" +
            "Todos los campos")
    public void TC18090BSJ() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC18090BSJ");
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
    @DisplayName("TC-18093-BSJ: Transferencia a cuentas propias con CBU de otra entidad sin haber completado el campo - Descripción - al agendarse")
    public void TC18093BSJ(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC18093BSJ");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        transferenciasSteps.eliminarContactoAgendado();
        transferenciasSteps.agendarNuevoContacto("TC18093BSJ",testCaseData.cbu);
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
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
    @DisplayName("TC-18094-BSJ: Transferencias Programadas (Fecha especifica) " +
            "CBU a Terceros otro banco $ - NO AGENDADOS" +
            "Todos los campos")
    public void TC18094BSJ() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC18094BSJ");
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
    @DisplayName("TC-18097-BSJ: Saldo actualizado_Transferencias a un " +
            "Alias CVU  en pesos")
    public void TC18097BSJ() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC18097BSJ");
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
    @DisplayName("TC-18098-BSJ: Saldo actualizado_Transferencias a un " +
            "Alias CBU  en dólares")
    public void TC18098BSJ() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC18098BSJ");
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
        transferenciasSteps.transferenciaATercerosUsd();
        //Obtener Monto Actualizado de cuenta Origen y Destino
        compartidosAPI.obtenerMontoActualizadoOrigen(testCaseData.cuentaOrigen);
        //Validaciones en Cuenta Origen y Destino
        transferenciasSteps.validarDescuentoCuenta();
    }

    @Test
    @DisplayName("TC-18100-BSJ: WEB - Transferencias programadas - Fecha especifica agendada - Historial de transferencias")
    public void TC18100BSJ() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC18100BSJ");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        transferenciasSteps.eliminarContactoAgendado();
        transferenciasSteps.agendarNuevoContacto("TC18100BSJ",testCaseData.cbu);
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
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
    @DisplayName("TC-18101-BSJ: WEB - Transferencias a contacto agendado - Historial de transferencias -")
    public void TC18101BSJ(){
        //PREOCNDICION
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC18101BSJ");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        transferenciasSteps.eliminarContactoAgendado();
        transferenciasSteps.agendarNuevoContacto("TC18101BSJ",testCaseData.cbu);
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
        transferenciasSteps.trasnferenciaContactoAgendado("TC18101BSJ");
        //Validaciones en Cuenta Origen y Destino
        transferenciasSteps.validaHistorialDeTransferencias();
    }

    @Test
    @DisplayName("TC-18102-BSJ: WEB - Transferencias a CVU - Historial de transferencias -")
    public void TC18102BSJ() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC18102BSJ");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Busca transferencia por Descripción
        transferenciasSteps.validaHistorialDeTransferencias();
    }

    @Test
    @DisplayName("TC-16759-BSJ: API - Transferencias realizadas - Ver mis transferencias - Filtros -")
    public void TC16759BSJ() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC16759BSJ");
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
        transferenciasSteps.validaHistorialDeTransferenciasUnAñoAntiguedad();
    }

    @Test
    @DisplayName("TC-16765-BSJ: API - Transferencias realizadas - Ver mis transferencias - rango mas nombre en buscador")
    public void TC16765BSJ() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC16765BSJ");
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
        transferenciasSteps.validaHistorialDeTransferenciasPorRangoNombreODescripcion("TestAutomation");
    }

    @Test
    @DisplayName("TC-16768-BSJ: WEB - Transferencias realizadas - Ver mis transferencias - rango mas nombre en buscador")
    public void TC16768BSJ() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC16768BSJ");
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
        transferenciasSteps.validaHistorialDeTransferenciasPorRangoNombreODescripcion("TestAutomation");
    }

    @Test
    @DisplayName("TC-18104-BSJ: WEB - Transferencias realizadas - Ver mis transferencias - Búsqueda sin resultados")
    public void TC18104BSJ() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC18104BSJ");
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
    @DisplayName("TC-0344- BSJ: TRANSFERENCIAS - A cuentas de terceros ALIAS - otros bancos - Pesos")
    public void TC0344BSJ(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0344BSJ");
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
    @DisplayName("TC-0410- BSJ: Transferencias - terceros mismo banco  USD - AGENDADOS")
    public void TC0410BSJ(){
        //PREOCNDICION
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0410BSJ");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        transferenciasSteps.eliminarContactoAgendado();
        transferenciasSteps.agendarNuevoContacto("TC0410BSJ",testCaseData.cbu);
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
        transferenciasSteps.trasnferenciaContactoAgendado("TC0410BSJ");
        //Obtener Monto Actualizado de cuenta Origen y Destino
        compartidosAPI.obtenerMontoActualizadoOrigen(testCaseData.cuentaOrigen);
        //Validaciones en Cuenta Origen y Destino
        transferenciasSteps.validarDescuentoCuenta();
    }

    @Test
    @DisplayName("TC-0412- BSJ: Transferencias - terceros mismo banco  USD - AGENDADOS")
    public void TC0412BSJ(){
        //PREOCNDICION
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0412BSJ");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        transferenciasSteps.eliminarContactoAgendado();
        transferenciasSteps.agendarNuevoContacto("TC0412BSJ",testCaseData.cbu);
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
        transferenciasSteps.trasnferenciaContactoAgendado("TC0412BSJ");
        //Obtener Monto Actualizado de cuenta Origen y Destino
        compartidosAPI.obtenerMontoActualizadoOrigen(testCaseData.cuentaOrigen);
        //Validaciones en Cuenta Origen y Destino
        transferenciasSteps.validarDescuentoCuenta();
    }

    //@Test
    @Tag("Criticos")
    @DisplayName("TC-0413-BSJ: Transferencias - terceros otros bancos INTERBANK (No PETERSEN) $ - AGENDADOS")
    public void TC0413BSJ() {
        //PREOCNDICION
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0413BSJ");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        transferenciasSteps.eliminarContactoAgendado();
        transferenciasSteps.agendarNuevoContacto("TC0413BSJ",testCaseData.cbu);
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
        transferenciasSteps.trasnferenciaContactoAgendado("TC0413BSJ");
        //Obtener Monto Actualizado de cuenta Origen y Destino
        compartidosAPI.obtenerMontoActualizadoOrigen(testCaseData.cuentaOrigen);
        //Validaciones en Cuenta Origen y Destino
        transferenciasSteps.validarDescuentoCuenta();
    }

    @Test
    @DisplayName("TC-0418-BSJ: Transferencias CBU - terceros otros bancos USD - NO AGENDADOS")
    public void TC0418BSJ(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0418BSJ");
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
        transferenciasSteps.transferenciaATercerosUsd();
        //Obtener Monto Actualizado de cuenta Origen y Destino
        compartidosAPI.obtenerMontoActualizadoOrigen(testCaseData.cuentaOrigen);
        //Validaciones en Cuenta Origen y Destino
        transferenciasSteps.validarDescuentoCuenta();
    }

    //@Test
    @DisplayName("TC-0420-BSJ: Transferencias CBU - terceros otros bancos INTERBANK (No PETERSEN) USD - NO AGENDADOS")
    public void TC0420BSJ() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0420BSJ");
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
        transferenciasSteps.transferenciaATercerosUsd();
        //Obtener Monto Actualizado de cuenta Origen y Destino
        compartidosAPI.obtenerMontoActualizadoOrigen(testCaseData.cuentaOrigen);
        //Validaciones en Cuenta Origen y Destino
        transferenciasSteps.validarDescuentoCuenta();
    }

    @Test
    @DisplayName("TC-0422-BSJ: Transferencias ALIAS - terceros mismo banco  USD - NO AGENDADOS")
    public void TC0422BSJ(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0422BSJ");
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
    @DisplayName("TC-0424-BSJ: Transferencias ALIAS - terceros otros bancos USD - NO AGENDADOS")
    public void TC0424BSJ(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0424BSJ");
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
    @DisplayName("TC-0468-BSJ: Transferencias Programadas (Fecha especifica) - terceros otros bancos USD - AGENDADOS")
    public void TC0468BSJ(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0468BSJ");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        transferenciasSteps.eliminarContactoAgendado();
        transferenciasSteps.agendarNuevoContacto("TC0465BSJ",testCaseData.cbu);
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
        transferenciasSteps.trasnferenciaProgramadaContactoAgendado("TC0465BSJ");
    }

    //@Test
    @DisplayName("TC-0469-BSJ: Transferencias Programadas (Fecha especifica) - terceros otros bancos INTERBANK (No PETERSEN) $ - AGENDADOS")
    public void TC0469BSJ(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0469BSJ");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        transferenciasSteps.eliminarContactoAgendado();
        transferenciasSteps.agendarNuevoContacto("TC0469BSJ",testCaseData.cbu);
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
        transferenciasSteps.trasnferenciaProgramadaContactoAgendado("TC0469BSJ");
    }

    //@Test
    @Tag("Criticos")
    @DisplayName("TC-0470-BSJ: Transferencias - terceros otros bancos INTERBANK (No PETERSEN) USD - AGENDADOS")
    public void TC0470BSJ(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0469BSJ");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        transferenciasSteps.eliminarContactoAgendado();
        transferenciasSteps.agendarNuevoContacto("TC0469BSJ",testCaseData.cbu);
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
        transferenciasSteps.trasnferenciaProgramadaContactoAgendado("TC0469BSJ");
    }

    @Test
    @DisplayName("TC-0472-BSJ: Transferencias Programadas (Fecha especifica) CBU - terceros mismo banco  USD - NO AGENDADOS")
    public void TC0472BSJ() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0472BSJ");
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
    @DisplayName("TC-0474-BSJ: Transferencias Programadas (Fecha especifica) CBU - terceros otros bancos USD - NO AGENDADOS")
    public void TC0474BSJ() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0474BSJ");
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
    @DisplayName("TC-0478-BSJ: Transferencias Programadas (Fecha especifica) ALIAS - terceros mismo banco  USD - NO AGENDADOS")
    public void TC0478BSJ() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0478BSJ");
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
    @DisplayName("TC-0479-BSJ: Transferencias Programadas (Fecha especifica) ALIAS - terceros otros bancos $ - NO AGENDADOS")
    public void TC0479BSJ() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0479BSJ");
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
    @DisplayName("TC-0480-BSJ: Transferencias Programadas (Fecha especifica) ALIAS - terceros otros bancos USD - NO AGENDADOS")
    public void TC0480BSJ() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0480BSJ");
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

    //@Test
    @DisplayName("TC-128242-BSJ: Transferencias - Web - VU Fraud - Verificar que al ejecutarse el activity adhesionAccount se envíe el metodoValidacionFP con la marca de validación del dispositivo")
    public void TC128242BSJ(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC128242BSJ");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        transferenciasSteps.eliminarContactoAgendado();
        //Test
        transferenciasSteps.agendarNuevoContacto("TC128242BSJ",testCaseData.cbu);
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        loginApiSteps.validarServicioFraudAnalysis();
    }

}
