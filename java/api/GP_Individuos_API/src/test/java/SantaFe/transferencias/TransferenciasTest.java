package SantaFe.transferencias;

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
    @DisplayName("TC-0139-BSF: Transferencias Inmediatas - Entre cuentas propias - Flujo completo- Validar ticket con exito")
    public void TC0139BSF(){
            //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
            testCaseData.cargaDeDatosTest("TC0139BSF");
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
    @DisplayName("TC-0145-BSF: Transferencias - Añadir nuevo destinatario - Con cbu valido")
    public void TC0145BSF(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0145BSF");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        transferenciasSteps.eliminarContactoAgendado();
        //Test
        transferenciasSteps.agendarNuevoContacto("TC0145BSF",testCaseData.cbu);
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
    }
    @Test
    @Tag("Criticos")
    @DisplayName("TC-12930-BSF: TRANSFERENCIAS - A cuentas de terceros CBU - mismo banco - Pesos")
    public void TC12930BSF(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC12930BSF");
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
    @DisplayName("TC-12932-BSF: TRANSFERENCIAS - A cuentas de terceros ALIAS - mismo banco - Pesos")
    public void TC12932BSF() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC12932BSF");
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
    @DisplayName("TC-12935-BSF: Transferencias Programadas (Fecha especifica) CBU - terceros mismo banco  $ - NO AGENDADOS")
    public void TC12935BSF() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC12935BSF");
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
    @DisplayName("TC-12969-BSF: TRANSFERENCIAS - A cuentas de terceros CVU - otros bancos - Pesos")
    public void TC12969BSF() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC12969BSF");
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
    @DisplayName("TC-12981-BSF: Transferencias - Eliminar destinatario de agenda")
    public void TC12981BSF() {
        //PRECONDICION
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC12981BSF");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        transferenciasSteps.eliminarContactoAgendado();
        transferenciasSteps.agendarNuevoContacto("TC12981BSF",testCaseData.cbu);
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        //TEST
        transferenciasSteps.eliminarContactoAgendado();
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
    }
    @Test
    @Tag("Criticos")
    @DisplayName("TC-0014-BSF: Generar CREDIN - Cuenta propia $ (mismo Banco u otro) - Mas de una cuenta Debito - CBU - Segundo factor por SMS")
    public void TC0014BSF() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0014BSF");
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
    @DisplayName("TC-12939-BSF: TRANSFERENCIAS - Consultar CREDIN Originante")
    public void TC12939BSF() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC12939BSF");
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
    @DisplayName("TC-12940-BSF: TRANSFERENCIAS - Consultar CREDIN destinatario")
    public void TC12940BSF() {
        //PRECONDICION
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC12940BSF");
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
    @DisplayName("TC-14250-BSF: Transferencias CBU a cuentas propias otro banco $ - NO AGENDADOS")
    public void TC14250BSF(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC14250BSF");
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
    @DisplayName("TC-18089-BSF: Transferencia a cuentas propias con CBU de otra entidad sin haber completado el campo - Descripción - al agendarse")
    public void TC18089BSF(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC18089BSF");
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
    @Tag("Criticos")
    @DisplayName("TC-0409-BSF: TRANSFERENCIAS - terceros mismo banco  $ - AGENDADOS")
    public void TC0409BSF(){
        //PREOCNDICION
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0409BSF");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        transferenciasSteps.eliminarContactoAgendado();
        transferenciasSteps.agendarNuevoContacto("TC0409BSF",testCaseData.cbu);
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
        transferenciasSteps.trasnferenciaContactoAgendado("TC0409BSF");
        //Obtener Monto Actualizado de cuenta Origen y Destino
        compartidosAPI.obtenerMontoActualizadoOrigen(testCaseData.cuentaOrigen);
        //Validaciones en Cuenta Origen y Destino
        transferenciasSteps.validarDescuentoCuenta();

    }
    @Test
    @Tag("Criticos")
    @DisplayName("TC-0407-BSF: Transferencias ALIAS a cuentas propias otro banco $ - NO AGENDADOS")
    public void TC0407BSF(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0407BSF");
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
    @DisplayName("TC-0463-BSF: Transferencias Programadas (Fecha especifica) ALIAS a cuentas propias otro banco $ - NO AGENDADOS")
    public void TC0463BSF() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0463BSF");
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
    @DisplayName("TC-0473-BSF: Transferencias Programadas (Fecha especifica) CBU - terceros otros bancos $ - NO AGENDADOS")
    public void TC0473BSF() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0473BSF");
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
    @DisplayName("TC-0411-BSF: Transferencias - terceros otros bancos $ - AGENDADOS")
    public void TC0411BSF(){
        //PREOCNDICION
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0411BSF");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        transferenciasSteps.eliminarContactoAgendado();
        transferenciasSteps.agendarNuevoContacto("TC0411BSF",testCaseData.cbu);
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
        transferenciasSteps.trasnferenciaContactoAgendado("TC0411BSF");
        //Obtener Monto Actualizado de cuenta Origen y Destino
        compartidosAPI.obtenerMontoActualizadoOrigen(testCaseData.cuentaOrigen);
        //Validaciones en Cuenta Origen y Destino
        transferenciasSteps.validarDescuentoCuenta();
    }
    @Test
    @Tag("Criticos")
    @DisplayName("TC-0427-BSF: Transferencias CVU - terceros $ AGENDADOS")
    public void TC0427BSF(){
        //PREOCNDICION
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0427BSF");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        transferenciasSteps.eliminarContactoAgendado();
        transferenciasSteps.agendarNuevoContacto("TC0427BSF",testCaseData.cbu);
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
        transferenciasSteps.trasnferenciaContactoAgendado("TC0427BSF");
        //Obtener Monto Actualizado de cuenta Origen y Destino
        compartidosAPI.obtenerMontoActualizadoOrigen(testCaseData.cuentaOrigen);
        //Validaciones en Cuenta Origen y Destino
        transferenciasSteps.validarDescuentoCuenta();
    }
    @Test
    @Tag("Criticos")
    @DisplayName("TC-0429-BSF: Transferencias ALIAS de CVU - terceros $ NO AGENDADOS")
    public void TC0429BSF(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0429BSF");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        //transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        transferenciasSteps.validarAliasAgendado(testCaseData.alias);
        transferenciasSteps.eliminarContactoAgendado();
//        transferenciasSteps.agendarNuevoContacto("TC0429BSF",testCaseData.cbu);
//        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
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
    @DisplayName("TC-0465-BSF: Transferencias Programadas (Fecha especifica) - terceros mismo banco  $ - AGENDADOS")
    public void TC0465BSF(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0465BSF");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        transferenciasSteps.eliminarContactoAgendado();
        transferenciasSteps.agendarNuevoContacto("TC0465BSF",testCaseData.cbu);
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
        transferenciasSteps.trasnferenciaProgramadaContactoAgendado("TC0465BSF");
    }
    @Test
    @Tag("Criticos")
    @DisplayName("TC-0477-BSF: Transferencias Programadas (Fecha especifica) ALIAS - terceros mismo banco  $ - NO AGENDADOS")
    public void TC0477BSF() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0477BSF");
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
    @DisplayName("TC-0467-BSF: Transferencias Programadas (Fecha especifica) - terceros otros bancos $ - AGENDADOS")
    public void TC0467BSF(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0467BSF");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        transferenciasSteps.eliminarContactoAgendado();
        transferenciasSteps.agendarNuevoContacto("TC0467BSF",testCaseData.cbu);
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
        transferenciasSteps.trasnferenciaProgramadaContactoAgendado("TC0467BSF");
    }
    @Test
    @Tag("Criticos")
    @DisplayName("TC-0483-BSF: Transferencias Programadas (Fecha especifica) CVU - terceros $ AGENDADOS")
    public void TC0483BSF(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0483BSF");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        transferenciasSteps.eliminarContactoAgendado();
        transferenciasSteps.agendarNuevoContacto("TC0483BSF",testCaseData.cbu);
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
        transferenciasSteps.trasnferenciaProgramadaContactoAgendado("TC0483BSF");
    }
    @Test
    @Tag("Criticos")
    @DisplayName("TC-0484-BSF: Transferencias Programadas (Fecha especifica) CVU - terceros $ NO AGENDADOS")
    public void TC0484BSF() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0484BSF");
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
    @DisplayName("TC-0485-BSF: Transferencias Programadas (Fecha especifica) ALIAS de CVU - terceros $ NO AGENDADOS")
    public void TC0485BSF() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0485BSF");
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
    @DisplayName("TC-0008-BSF: Transferencia Intrabancaria en Pesos - Propias (del mismo banco) Cargada Manualmente - Programada en Fecha Futura Botón Volver")
    public void TC0008BSF() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0008BSF");
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
    @DisplayName("TC-12938-BSF: TRANSFERENCIAS - Generar CREDIN - Dolares")
    public void TC12938BSF() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC12938BSF");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //ejecucion de servicios compartidos datos de cuentas y datos de tarjetas
        compartidosAPI.cargaServiciosCompartidos();
        //obtencion de datos para realizar el credin
        //compartidosAPI.obtenerIdDeTarjetaUSD(testCaseData.debitCard);
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
    @DisplayName("TC-18090-BSF: Transferencias Programadas (Fecha especifica) " +
            "CBU a cuentas propias otro banco $ - NO AGENDADOS" +
            "Todos los campos")
    public void TC18090BSF() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC18090BSF");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        transferenciasSteps.eliminarContactoAgendado();
        //TEST
        //ejecucion de servicios compartidos datos de cuentas y datos de tarjetas
        compartidosAPI.cargaServiciosCompartidos();
        //obtencion de datos para realizar la transferencia
        compartidosAPI.mostrarNumeroCtaOrigen(testCaseData.cuentaOrigen);
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
    @DisplayName("TC-18093-BSF: Transferencia a Terceros con CBU de otra entidad sin haber completado el campo - Descripción - al agendarse")
    public void TC18093BSF(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC18093BSF");
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
    @DisplayName("TC-18094-BSF: Transferencias Programadas (Fecha especifica) CBU a Terceros otro banco $ - NO AGENDADOS Todos los campos")
    public void TC18094BSF() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC18094BSF");
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
    @DisplayName("TC-18097-BSF: Saldo actualizado_Transferencias a un  Alias CVU  en pesos")
    public void TC18097BSF() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC18097BSF");
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
    @DisplayName("TC-18098-BSF: Saldo actualizado_Transferencias a un  Alias CBU  en dóares")
    public void TC18098BSF() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC18098BSF");
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
        transferenciasSteps.validarIncrementoEnCuenta();
    }

    @Test
    @DisplayName("TC-18100-BSF: WEB - Transferencias programadas - Fecha especifica agendada - Historial de transferencias")
    public void TC18100BSF() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC18100BSF");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        transferenciasSteps.eliminarContactoAgendado();
        transferenciasSteps.agendarNuevoContacto("TC18100BSF",testCaseData.cbu);
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
        transferenciasSteps.transferenciaProgramadaTerceros();
        transferenciasSteps.validaHistorialDeTransferencias();
    }

    @Test
    @DisplayName("TC-18101-BSF: WEB - Transferencias a contacto agendado - Historial de transferencias -")
    public void TC18101BSF(){
        //PREOCNDICION
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC18101BSF");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        transferenciasSteps.eliminarContactoAgendado();
        transferenciasSteps.agendarNuevoContacto("TC18101BSF",testCaseData.cbu);
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
        transferenciasSteps.trasnferenciaContactoAgendado("TC18101BSF");
        //Validaciones en Cuenta Origen y Destino
        transferenciasSteps.validaHistorialDeTransferencias();

    }

    @Test
    @DisplayName("TC-18102-BSF: WEB - Transferencias a CVU - Historial de transferencias -")
    public void TC18102BSF() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC18102BSF");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        transferenciasSteps.validaHistorialDeTransferencias();
    }

    @Test
    @DisplayName("TC-16759-BSF: WEB - Transferencias realizadas - Ver mis transferencias - Filtros -")
    public void TC16759BSF() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC16759BSF");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        transferenciasSteps.validaHistorialDeTransferenciasUnAñoAntiguedad();
    }

    @Test
    @DisplayName("TC-16765-BSF: WEB - Transferencias realizadas - Ver mis transferencias - rango mas nombre en buscador")
    public void TC16765BSF() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC16765BSF");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        transferenciasSteps.validaHistorialDeTransferenciasPorRangoNombreODescripcion("Borges");
    }

    @Test
    @DisplayName("TC-16768-BSF: WEB - Transferencias realizadas - Ver mis transferencias - rango mas descripcion")
    public void TC16768BSF() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
//        testCaseData.cargaDeDatosTest("TC16768BSF");
//        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
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
        transferenciasSteps.validaHistorialDeTransferenciasPorRangoNombreODescripcion("TestAutomation");
    }

    @Test
    @DisplayName("TC-18104-BSF: WEB - Transferencias realizadas - Ver mis transferencias - Búsqueda sin resultados")
    public void TC18104BSF() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC18104BSF");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        transferenciasSteps.validaBusquedasDelHistorialSinResultados("nombresinresultados");
    }

    @Test
    @DisplayName("TC-0344- BSF: TRANSFERENCIAS - A cuentas de terceros ALIAS - otros bancos - Pesos")
    public void TC0344BSF(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0344BSF");
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
    @DisplayName("TC-0410- BSF: Transferencias - terceros mismo banco  USD - AGENDADOS")
    public void TC0410BSF(){
        //PREOCNDICION
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0410BSF");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        transferenciasSteps.eliminarContactoAgendado();
        transferenciasSteps.agendarNuevoContacto("TC0410BSF",testCaseData.cbu);
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
        transferenciasSteps.trasnferenciaContactoAgendado("TC0410BSF");
        //Obtener Monto Actualizado de cuenta Origen y Destino
        compartidosAPI.obtenerMontoActualizadoOrigen(testCaseData.cuentaOrigen);
        //Validaciones en Cuenta Origen y Destino
        transferenciasSteps.validarDescuentoCuenta();
    }

    @Test
    @DisplayName("TC-0412- BSF: Transferencias - terceros mismo banco  USD - AGENDADOS")
    public void TC0412BSF(){
        //PREOCNDICION
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0412BSF");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        transferenciasSteps.eliminarContactoAgendado();
        transferenciasSteps.agendarNuevoContacto("TC0412BSF",testCaseData.cbu);
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
        transferenciasSteps.trasnferenciaContactoAgendado("TC0412BSF");
        //Obtener Monto Actualizado de cuenta Origen y Destino
        compartidosAPI.obtenerMontoActualizadoOrigen(testCaseData.cuentaOrigen);
        //Validaciones en Cuenta Origen y Destino
        transferenciasSteps.validarDescuentoCuenta();
    }

    //@Test
    @Tag("Criticos")
    @DisplayName("TC-0413-BSF: Transferencias - terceros otros bancos INTERBANK (No PETERSEN) $ - AGENDADOS")
    public void TC0413BSF() {
        //PREOCNDICION
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0413BSF");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        transferenciasSteps.eliminarContactoAgendado();
        transferenciasSteps.agendarNuevoContacto("TC0413BSF",testCaseData.cbu);
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
        transferenciasSteps.trasnferenciaContactoAgendado("TC0413BSF");
        //Obtener Monto Actualizado de cuenta Origen y Destino
        compartidosAPI.obtenerMontoActualizadoOrigen(testCaseData.cuentaOrigen);
        //Validaciones en Cuenta Origen y Destino
        transferenciasSteps.validarDescuentoCuenta();
    }

    @Test
    @DisplayName("TC-0418-BSF: Transferencias CBU - terceros otros bancos USD - NO AGENDADOS")
    public void TC0418BSF(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0418BSF");
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
    @DisplayName("TC-0420-BSF: Transferencias CBU - terceros otros bancos INTERBANK (No PETERSEN) USD - NO AGENDADOS")
    public void TC0420BSF() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0420BSF");
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
    @DisplayName("TC-0422-BSF: Transferencias ALIAS - terceros mismo banco  USD - NO AGENDADOS")
    public void TC0422BSF(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0422BSF");
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
    @DisplayName("TC-0424-BSF: Transferencias ALIAS - terceros otros bancos USD - NO AGENDADOS")
    public void TC0424BSF(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0424BSF");
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
    @DisplayName("TC-0468-BSF: Transferencias Programadas (Fecha especifica) - terceros otros bancos USD - AGENDADOS")
    public void TC0468BSF(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0468BSF");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        transferenciasSteps.eliminarContactoAgendado();
        transferenciasSteps.agendarNuevoContacto("TC0468BSF",testCaseData.cbu);
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
        transferenciasSteps.trasnferenciaProgramadaContactoAgendado("TC0465BSF");
    }

    //@Test
    @DisplayName("TC-0469-BSF: Transferencias Programadas (Fecha especifica) - terceros otros bancos INTERBANK (No PETERSEN) $ - AGENDADOS")
    public void TC0469BSF(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0469BSF");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        transferenciasSteps.eliminarContactoAgendado();
        transferenciasSteps.agendarNuevoContacto("TC0469BSF",testCaseData.cbu);
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
        transferenciasSteps.trasnferenciaProgramadaContactoAgendado("TC0469BSF");
    }

    //@Test
    @Tag("Criticos")
    @DisplayName("TC-0470-BSF: Transferencias - terceros otros bancos INTERBANK (No PETERSEN) USD - AGENDADOS")
    public void TC0470BSF(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0469BSF");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        transferenciasSteps.eliminarContactoAgendado();
        transferenciasSteps.agendarNuevoContacto("TC0469BSF",testCaseData.cbu);
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
        transferenciasSteps.trasnferenciaProgramadaContactoAgendado("TC0469BSF");
    }

    @Test
    @DisplayName("TC-0472-BSF: Transferencias Programadas (Fecha especifica) CBU - terceros mismo banco  USD - NO AGENDADOS")
    public void TC0472BSF() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0472BSF");
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
    @DisplayName("TC-0474-BSF: Transferencias Programadas (Fecha especifica) CBU - terceros otros bancos USD - NO AGENDADOS")
    public void TC0474BSF() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0474BSF");
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
    @DisplayName("TC-0478-BSF: Transferencias Programadas (Fecha especifica) ALIAS - terceros mismo banco  USD - NO AGENDADOS")
    public void TC0478BSF() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0478BSF");
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
    @DisplayName("TC-0479-BSF: Transferencias Programadas (Fecha especifica) ALIAS - terceros otros bancos $ - NO AGENDADOS")
    public void TC0479BSF() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0479BSF");
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
    @DisplayName("TC-0480-BSF: Transferencias Programadas (Fecha especifica) ALIAS - terceros otros bancos USD - NO AGENDADOS")
    public void TC0480BSF() {
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC0480BSF");
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
    @DisplayName("TC-128242-BSF: Transferencias - Web - VU Fraud - Verificar que al ejecutarse el activity adhesionAccount se envíe el metodoValidacionFP con la marca de validación del dispositivo")
    public void TC128242BSF(){
        //Obtiene los datos para el test case - Usuario, cuentas,cbu,etc..
        testCaseData.cargaDeDatosTest("TC128242BSF");
        loginApiSteps.loginUsuario(testCaseData.usuario, testCaseData.password);
        //Consultar si la cbu ya ha sido agendado anteriormente
        transferenciasSteps.validarCbuAgendado(testCaseData.cbu);
        transferenciasSteps.eliminarContactoAgendado();
        //Test
        transferenciasSteps.agendarNuevoContacto("TC128242BSF",testCaseData.cbu);
        loginApiSteps.validarServicioFraudAnalysis();
    }


}
