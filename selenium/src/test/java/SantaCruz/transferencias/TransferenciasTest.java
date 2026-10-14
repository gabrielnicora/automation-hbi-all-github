package SantaCruz.transferencias;

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
    @DisplayName("TC-0139-BSC: Transferencias Inmediatas - Entre cuentas propias - Flujo completo- Validar ticket con exito")
    public void TC0139BSC(){
            //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
            testCaseData.cargaDeDatosTest("TC0139BSC");
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
    @DisplayName("TC-0145-BSC: Transferencias - Añadir nuevo destinatario - Con cbu valido")
    public void TC0145BSC(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0145BSC");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        transferenciasSteps.eliminarContactoAgendado();
        //Test
        transferenciasSteps.agendarNuevoContacto("TC0145BSC",testCaseData.cbu);
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
    }
    @Test
    @Tag("Criticos")
    @DisplayName("TC-12930-BSC: TRANSFERENCIAS - A cuentas de terceros CBU - mismo banco - Pesos")
    public void TC12930BSC(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC12930BSC");
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
    @DisplayName("TC-12932-BSC: TRANSFERENCIAS - A cuentas de terceros ALIAS - mismo banco - Pesos")
    public void TC12932BSC() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC12932BSC");
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
    @DisplayName("TC-12935-BSC: Transferencias Programadas (Fecha especifica) CBU - terceros mismo banco  $ - NO AGENDADOS")
    public void TC12935BSC() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC12935BSC");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        //transferenciasSteps.eliminarContactoAgendado();
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
    @DisplayName("TC-12969-BSC: TRANSFERENCIAS - A cuentas de terceros CVU - otros bancos - Pesos")
    public void TC12969BSC() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC12969BSC");
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
    @DisplayName("TC-12981-BSC: Transferencias - Eliminar destinatario de agenda")
    public void TC12981BSC() {
        //PRECONDICION
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC12981BSC");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        transferenciasSteps.eliminarContactoAgendado();
        transferenciasSteps.agendarNuevoContacto("TC12981BSC",testCaseData.cbu);
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        //TEST
        transferenciasSteps.eliminarContactoAgendado();
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
    }
    @Test
    @Tag("Criticos")
    @DisplayName("TC-0014-BSC: Generar CREDIN - Cuenta propia $ (mismo Banco u otro) - Mas de una cuenta Debito - CBU - Segundo factor por SMS")
    public void TC0014BSC() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0014BSC");
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
    @DisplayName("TC-12939-BSC: TRANSFERENCIAS - Consultar CREDIN Originante")
    public void TC12939BSC() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC12939BSC");
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
    @DisplayName("TC-12940-BSC: TRANSFERENCIAS - Consultar CREDIN destinatario")
    public void TC12940BSC() {
        //PRECONDICION
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC12940BSC");
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
    //@Test SE COMENTA EL CASO POR LA FALTA DE USUARIO
    @Tag("Criticos")
    @DisplayName("TC-14250-BSC: Transferencias CBU a cuentas propias otro banco $ - NO AGENDADOS")
    public void TC14250BSC(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC14250BSC");
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
    @DisplayName("TC-0409-BSC: TRANSFERENCIAS - terceros mismo banco  $ - AGENDADOS")
    public void TC0409BSC(){
        //PREOCNDICION
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0409BSC");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        transferenciasSteps.eliminarContactoAgendado();
        transferenciasSteps.agendarNuevoContacto("TC0409BSC",testCaseData.cbu);
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
        transferenciasSteps.trasnferenciaContactoAgendado("TC0409BSC");
        //Obtener Monto Actualizado de cuenta Origen y Destino
        compartidosAPI.obtenerMontoActualizadoOrigen(testCaseData.cuentaOrigen);
        //Validaciones en Cuenta Origen y Destino
        transferenciasSteps.validarDescuentoCuenta();

    }
    //@Test //sin usuario
    @Tag("Criticos")
    @DisplayName("TC-0407-BSC: Transferencias ALIAS a cuentas propias otro banco $ - NO AGENDADOS")
    public void TC0407BSC(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0407BSC");
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
    //@Test
    @Tag("Criticos")
    @DisplayName("TC-0463-BSC: Transferencias Programadas (Fecha especifica) ALIAS a cuentas propias otro banco $ - NO AGENDADOS")
    public void TC0463BSC() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0463BSC");
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
    @DisplayName("TC-0473-BSC: Transferencias Programadas (Fecha especifica) CBU - terceros otros bancos $ - NO AGENDADOS")
    public void TC0473BSC() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0473BSC");
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
    @DisplayName("TC-0411-BSC: Transferencias - terceros otros bancos $ - AGENDADOS")
    public void TC0411BSC(){
        //PREOCNDICION
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0411BSC");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        transferenciasSteps.eliminarContactoAgendado();
        transferenciasSteps.agendarNuevoContacto("TC0411BSC",testCaseData.cbu);
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
        transferenciasSteps.trasnferenciaContactoAgendado("TC0411BSC");
        //Obtener Monto Actualizado de cuenta Origen y Destino
        compartidosAPI.obtenerMontoActualizadoOrigen(testCaseData.cuentaOrigen);
        //Validaciones en Cuenta Origen y Destino
        transferenciasSteps.validarDescuentoCuenta();
    }
    @Test
    @Tag("Criticos")
    @DisplayName("TC-0427-BSC: Transferencias CVU - terceros $ AGENDADOS")
    public void TC0427BSC(){
        //PREOCNDICION
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0427BSC");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        transferenciasSteps.eliminarContactoAgendado();
        transferenciasSteps.agendarNuevoContacto("TC0427BSC",testCaseData.cbu);
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
        transferenciasSteps.trasnferenciaContactoAgendado("TC0427BSC");
        //Obtener Monto Actualizado de cuenta Origen y Destino
        compartidosAPI.obtenerMontoActualizadoOrigen(testCaseData.cuentaOrigen);
        //Validaciones en Cuenta Origen y Destino
        transferenciasSteps.validarDescuentoCuenta();
    }
    @Test
    @Tag("Criticos")
    @DisplayName("TC-0429-BSC: Transferencias ALIAS de CVU - terceros $ NO AGENDADOS")
    public void TC0429BSC(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0429BSC");
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
    @DisplayName("TC-0465-BSC: Transferencias Programadas (Fecha especifica) - terceros mismo banco  $ - AGENDADOS")
    public void TC0465BSC(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0465BSC");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        transferenciasSteps.eliminarContactoAgendado();
        transferenciasSteps.agendarNuevoContacto("TC0465BSC",testCaseData.cbu);
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
        transferenciasSteps.trasnferenciaProgramadaContactoAgendado("TC0465BSC");
    }
    @Test
    @Tag("Criticos")
    @DisplayName("TC-0477-BSC: Transferencias Programadas (Fecha especifica) ALIAS - terceros mismo banco  $ - NO AGENDADOS")
    public void TC0477BSC() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0477BSC");
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
    @DisplayName("TC-0467-BSC: Transferencias Programadas (Fecha especifica) - terceros otros bancos $ - AGENDADOS")
    public void TC0467BSC(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0467BSC");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        transferenciasSteps.eliminarContactoAgendado();
        transferenciasSteps.agendarNuevoContacto("TC0467BSC",testCaseData.cbu);
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
        transferenciasSteps.trasnferenciaProgramadaContactoAgendado("TC0467BSC");
    }
    @Test
    @Tag("Criticos")
    @DisplayName("TC-0483-BSC: Transferencias Programadas (Fecha especifica) CVU - terceros $ AGENDADOS")
    public void TC0483BSC(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0483BSC");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        transferenciasSteps.eliminarContactoAgendado();
        transferenciasSteps.agendarNuevoContacto("TC0483BSC",testCaseData.cbu);
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
        transferenciasSteps.trasnferenciaProgramadaContactoAgendado("TC0483BSC");
    }
    @Test
    @Tag("Criticos")
    @DisplayName("TC-0484-BSC: Transferencias Programadas (Fecha especifica) CVU - terceros $ NO AGENDADOS")
    public void TC0484BSC() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0484BSC");
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
    @DisplayName("TC-0485-BSC: Transferencias Programadas (Fecha especifica) ALIAS de CVU - terceros $ NO AGENDADOS")
    public void TC0485BSC() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0485BSC");
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
    @DisplayName("TC-0008-BSC: Transferencia Intrabancaria en Pesos - Propias (del mismo banco) Cargada Manualmente - Programada en Fecha Futura Botón Volver")
    public void TC0008BSC() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0008BSC");
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
    @DisplayName("TC-12938-BSC: TRANSFERENCIAS - Generar CREDIN - Dolares")
    public void TC12938BSC () {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC12938BSC");
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
    @DisplayName("TC-18089-BSC: Transferencia a cuentas propias con CBU de otra entidad sin haber completado el campo - Descripción - al agendarse")
    public void TC18089BSC(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC18089BSC");
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
        //Obtener Monto Actualizado de cuenta Origen y Destino
        compartidosAPI.obtenerMontoActualizadoOrigen(testCaseData.cuentaOrigen);
        //Validaciones en Cuenta Origen y Destino
        transferenciasSteps.validarDescuentoCuenta();
        transferenciasSteps.validarticketSinCampoDescripcion();
    }

    @Test
    @DisplayName("TC-18090-BSC: Transferencias Programadas (Fecha especifica) " +
            "CBU a cuentas propias otro banco $ - NO AGENDADOS" +
            "Todos los campos")
    public void TC18090BSC() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC18090BSC");
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
    @DisplayName("TC-18093-BSC: Transferencia a Terceros con CBU de otra entidad sin haber completado el campo - Descripción - al agendarse")
    public void TC18093BSC(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC18093BSC");
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
    @DisplayName("TC-18094-BSC: Transferencias Programadas (Fecha especifica) " +
            "CBU a terceros $ - NO AGENDADOS" +
            "Todos los campos")
    public void TC18094BSC() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC18094BSC");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        transferenciasSteps.eliminarContactoAgendado();
        //TEST
        //ejecucion de servicios compartidos datos de cuentas y datos de tarjetas
        compartidosAPI.cargaServiciosCompartidos();
        //obtencion de datos para realizar la transferencia
        compartidosAPI.obtenerIdDeCuentaOrigen(testCaseData.cuentaOrigen);
        compartidosAPI.mostrarNumeroCtaOrigen(testCaseData.cuentaOrigen);
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
    @DisplayName("TC-18097-BSC: Saldo actualizado_Transferencias a un  " +
            "Alias CVU  en pesos")
    public void TC18097BSC() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC18097BSC");
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
    @DisplayName("TC-18098-BSC: Saldo actualizado_Transferencias a un  Alias CBU  en dóares")
    public void TC18098BSC() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC18098BSC");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si el cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        //transferenciasSteps.validarAliasAgendado(testCaseData.alias);
        //transferenciasSteps.eliminarContactoAgendado();
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
    @DisplayName("TC-18100-BSC: WEB - Transferencias programadas - Fecha especifica agendada - Historial de transferencias")
    public void TC18100BSC() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC18100BSC");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        transferenciasSteps.eliminarContactoAgendado();
        transferenciasSteps.agendarNuevoContacto("TC18100BSC",testCaseData.cbu);
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
    @DisplayName("TC-18101-BSC: WEB - Transferencias a contacto agendado - Historial de transferencias -")
    public void TC18101BSC(){
        //PREOCNDICION
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC18101BSC");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        transferenciasSteps.eliminarContactoAgendado();
        transferenciasSteps.agendarNuevoContacto("TC18101BSC",testCaseData.cbu);
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
        transferenciasSteps.trasnferenciaContactoAgendado("TC18101BSC");
        //Validaciones en Cuenta Origen y Destino
        transferenciasSteps.validaHistorialDeTransferencias();
    }

    @Test
    @DisplayName("TC-18102-BSC: WEB - Transferencias a CVU - Historial de transferencias -")
    public void TC18102BSC() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC18102BSC");
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
    @DisplayName("TC-16759-BSC: WEB - Transferencias realizadas - Ver mis transferencias - Filtros -")
    public void TC16759BSC() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC16759BSC");
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
    @DisplayName("TC-16765-BSC: WEB - Transferencias realizadas - Ver mis transferencias - rango mas nombre en buscador")
    public void TC16765BSC() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC16765BSC");
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
        transferenciasSteps.validaHistorialDeTransferenciasPorRangoNombreODescripcion("PEREZ");
    }

    @Test
    @DisplayName("TC-16768-BSC: WEB - Transferencias realizadas - Ver mis transferencias - rango mas nombre en buscador")
    public void TC16768BSC() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC16768BSC");
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
    @DisplayName("TC-18104-BSC: WEB - Transferencias realizadas - Ver mis transferencias - Búsqueda sin resultados")
    public void TC18104BSC() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC18104BSC");
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
    @DisplayName("TC-0344- BSC: TRANSFERENCIAS - A cuentas de terceros ALIAS - otros bancos - Pesos")
    public void TC0344BSC(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0344BSC");
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
    @DisplayName("TC-0410- BSC: Transferencias - terceros mismo banco  USD - AGENDADOS")
    public void TC0410BSC(){
        //PREOCNDICION
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0410BSC");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        transferenciasSteps.eliminarContactoAgendado();
        transferenciasSteps.agendarNuevoContacto("TC0410BSC",testCaseData.cbu);
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
        transferenciasSteps.trasnferenciaContactoAgendado("TC0410BSC");
        //Obtener Monto Actualizado de cuenta Origen y Destino
        compartidosAPI.obtenerMontoActualizadoOrigen(testCaseData.cuentaOrigen);
        //Validaciones en Cuenta Origen y Destino
        transferenciasSteps.validarDescuentoCuenta();
    }
    
    @Test
    @DisplayName("TC-0412- BSC: Transferencias - terceros otros bancos USD - AGENDADOS")
    public void TC0412BSC(){
        //PREOCNDICION
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0412BSC");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        transferenciasSteps.eliminarContactoAgendado();
        transferenciasSteps.agendarNuevoContacto("TC0412BSC",testCaseData.cbu);
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
        transferenciasSteps.trasnferenciaContactoAgendado("TC0412BSC");
        //Obtener Monto Actualizado de cuenta Origen y Destino
        compartidosAPI.obtenerMontoActualizadoOrigen(testCaseData.cuentaOrigen);
        //Validaciones en Cuenta Origen y Destino
        transferenciasSteps.validarDescuentoCuenta();
    }

    @Test
    @Tag("Criticos")
    @DisplayName("TC-0413-BSC: Transferencias - terceros otros bancos INTERBANK (No PETERSEN) $ - AGENDADOS")
    public void TC0413BSC() {
        //PREOCNDICION
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0413BSC");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        transferenciasSteps.eliminarContactoAgendado();
        transferenciasSteps.agendarNuevoContacto("TC0413BSC",testCaseData.cbu);
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
        transferenciasSteps.trasnferenciaContactoAgendado("TC0413BSC");
        //Obtener Monto Actualizado de cuenta Origen y Destino
        compartidosAPI.obtenerMontoActualizadoOrigen(testCaseData.cuentaOrigen);
        //Validaciones en Cuenta Origen y Destino
        transferenciasSteps.validarDescuentoCuenta();
    }

    @Test
    @DisplayName("TC-0418-BSC: Transferencias CBU - terceros otros bancos USD - NO AGENDADOS")
    public void TC0418BSC(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0418BSC");
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
    @DisplayName("TC-0420-BSC: Transferencias CBU - terceros otros bancos INTERBANK (No PETERSEN) USD - NO AGENDADOS")
    public void TC0420BSC() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0420BSC");
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
    @DisplayName("TC-0422-BSC: Transferencias ALIAS - terceros mismo banco  USD - NO AGENDADOS")
    public void TC0422BSC(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0422BSC");
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
    @DisplayName("TC-0424-BSC: Transferencias ALIAS - terceros otros bancos USD - NO AGENDADOS")
    public void TC0424BSC(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0424BSC");
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
    @DisplayName("TC-0468-BSC: Transferencias Programadas (Fecha especifica) - terceros otros bancos USD - AGENDADOS")
    public void TC0468BSC(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0468BSC");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        transferenciasSteps.eliminarContactoAgendado();
        transferenciasSteps.agendarNuevoContacto("TC0465BSC",testCaseData.cbu);
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
        transferenciasSteps.trasnferenciaProgramadaContactoAgendado("TC0465BSC");
    }

    @Test
    @DisplayName("TC-0469-BSC: Transferencias Programadas (Fecha especifica) - terceros otros bancos INTERBANK (No PETERSEN) $ - AGENDADOS")
    public void TC0469BSC(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0469BSC");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        transferenciasSteps.eliminarContactoAgendado();
        transferenciasSteps.agendarNuevoContacto("TC0469BSC",testCaseData.cbu);
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
        transferenciasSteps.trasnferenciaProgramadaContactoAgendado("TC0469BSC");
    }

    @Test
    @Tag("Criticos")
    @DisplayName("TC-0470-BSC: Transferencias - terceros otros bancos INTERBANK (No PETERSEN) USD - AGENDADOS")
    public void TC0470BSC(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0469BSC");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        transferenciasSteps.eliminarContactoAgendado();
        transferenciasSteps.agendarNuevoContacto("TC0469BSC",testCaseData.cbu);
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
        transferenciasSteps.trasnferenciaProgramadaContactoAgendado("TC0469BSC");
    }

    @Test
    @DisplayName("TC-0472-BSC: Transferencias Programadas (Fecha especifica) CBU - terceros mismo banco  USD - NO AGENDADOS")
    public void TC0472BSC() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0472BSC");
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
    @DisplayName("TC-0474-BSC: Transferencias Programadas (Fecha especifica) CBU - terceros otros bancos USD - NO AGENDADOS")
    public void TC0474BSC() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0474BSC");
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
    @DisplayName("TC-0478-BSC: Transferencias Programadas (Fecha especifica) ALIAS - terceros mismo banco  USD - NO AGENDADOS")
    public void TC0478BSC() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0478BSC");
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
    @DisplayName("TC-0479-BSC: Transferencias Programadas (Fecha especifica) ALIAS - terceros otros bancos $ - NO AGENDADOS")
    public void TC0479BSC() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0479BSC");
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
    @DisplayName("TC-0480-BSC: Transferencias Programadas (Fecha especifica) ALIAS - terceros otros bancos USD - NO AGENDADOS")
    public void TC0480BSC() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0480BSC");
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