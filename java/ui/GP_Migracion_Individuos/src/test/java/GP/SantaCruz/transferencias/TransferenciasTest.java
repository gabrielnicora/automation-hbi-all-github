
package GP.SantaCruz.transferencias;

import GP.Tags.Alta;
import GP.Tags.Baja;
import GP.Tags.Criticos;
import GP.Tags.Media;
import db.ConexionDB;
import io.qameta.allure.Allure;
import io.qameta.allure.junit4.DisplayName;
import org.junit.*;
import org.junit.experimental.categories.Category;
import pages.botonesgenericos.BotonesGenericosPage;
import steps.botonesgenericos.BotonesGenericosSteps;
import steps.calendarios.CalendarioSteps;
import steps.cuentas.CuentasSteps;
import steps.doblefactor.DobleFactorSteps;
import steps.login.LoginSteps;
import steps.menulateral.MenuLateralSteps;
import steps.ticket.TicketSteps;
import steps.transferencias.TransferenciasSteps;
import steps.transferencias.agenda_nuevo_destinatario.AgendaNuevoDestinatarioSteps;
import steps.transferencias.consultacredin.ConsultaCredinSteps;
import steps.transferencias.generarcredin.GenerarCredinSteps;
import steps.transferencias.transferenciasinmediatas_programadas.TransferenciasInmediatasSteps;
import steps.validaciondeidentidad.ValidacionDeIdentidadSteps;
import utils.Constantes;
import utils.UtilsGP;
import utils.TestCaseData;


import java.io.IOException;

import static db.ConexionDB.cerrarConexion;
import static utils.Constantes.*;

public class TransferenciasTest {
    LoginSteps loginSteps = new LoginSteps();
    MenuLateralSteps menuLateralSteps = new MenuLateralSteps();
    BotonesGenericosSteps botonesGenericosSteps = new BotonesGenericosSteps();
    TransferenciasSteps transferenciasSteps = new TransferenciasSteps();
    ConsultaCredinSteps consultaCredinSteps = new ConsultaCredinSteps();
    ValidacionDeIdentidadSteps validacionDeIdentidadSteps = new ValidacionDeIdentidadSteps();
    GenerarCredinSteps generarCredinSteps = new GenerarCredinSteps();
    TransferenciasInmediatasSteps transferenciasInmediatas = new TransferenciasInmediatasSteps();
    DobleFactorSteps dobleFactorSteps = new DobleFactorSteps();
    TicketSteps ticketSteps = new TicketSteps();
    TransferenciasInmediatasSteps transferenciasInmediatasSteps = new TransferenciasInmediatasSteps();AgendaNuevoDestinatarioSteps agendaNuevoDestinatarioSteps = new AgendaNuevoDestinatarioSteps();
    CuentasSteps cuentasSteps = new CuentasSteps();
    CalendarioSteps calendarioSteps = new CalendarioSteps();
    UtilsGP utilsGP = new UtilsGP();
    BotonesGenericosPage botonesGenericosPage = new BotonesGenericosPage();
    TestCaseData testCaseData = new TestCaseData();

    @BeforeClass
    public static void extraerDatosBD(){
        System.out.println("============= BEFORE CLASS ==========================");
        if(Constantes.usuarios == null){
            System.out.println("============= Array de datos vacio extraer desde la BD ==============");
            Constantes.usuarios = ConexionDB.extraerUsuariosDB();
            System.out.println("============= se completo el array de datos ==========================");
        }else {
            System.out.println("================ EL ARRAY CONTIENE DATOS ===================");
        }
    }

    //@Test
    @Category({Alta.class})
    @DisplayName("TC-0007-BSC: Realizar una transferencia en pesos Interbancaria - A Terceros de distinto banco -  ingresando los datos requeridos haciendo uso de plantillas guardadas")
    public void TC0007BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uAndu0406);
            //loginSteps.iniciarSessionAlEscritorio(u23013315);
            menuLateralSteps.clickCuentas();
            cuentasSteps.clickMasOpcionesGenerico();
            transferenciasSteps.clickTransferenciasProgramadas();
            transferenciasInmediatasSteps.clickListadoDePlantillas();
            transferenciasInmediatasSteps.clickPlantillaPorNombre("plantilla bsfxbsc");
            transferenciasInmediatasSteps.clickContinuarGenerico();
            transferenciasInmediatasSteps.clickBotonConfimar();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0008-BSC: Transferencia Intrabancaria en Pesos - Propias (del mismo banco) Cargada Manualmente - Programada en Fecha Futura Botón Volver")
    public void TC0008BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(u5404364);
            menuLateralSteps.clickTransferencias("","");

            transferenciasInmediatas.seleccionarTipoTransferencia("Cuenta Propia");
            transferenciasInmediatas.seleccionarCuentaDestinoCuentasPropias("CC $ 602824875");
            transferenciasInmediatas.setImporteGenerico("1000");
            transferenciasInmediatas.seleccionarConcepto("Alquileres");
            transferenciasInmediatas.setDescripcion("prueba");
            transferenciasInmediatas.transferenciaProgramadaPara();
            transferenciasInmediatas.seleccionFechaFutura("Especifica");
            transferenciasInmediatas.clickContinuarGenerico();
            transferenciasInmediatas.clickConfirmarGenerico();
            ticketSteps.validarTicket("Tu transaccion ha sido agendada");
            ticketSteps.clickVolverGenerico();
            transferenciasInmediatas.validarTituloPageGenerico("Transferencias");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0014-BSC: Generar CREDIN - Cuenta propia $ (mismo Banco u otro) - Mas de una cuenta Debito - CBU - Segundo factor por SMS")
    public void TC0014BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uAndu0406);
            menuLateralSteps.clickTransferencias("","");
            transferenciasSteps.clickMasOpcionesGenerico();
            transferenciasSteps.clickConsultarCredin();
            consultaCredinSteps.clickGenerarCredin();
            generarCredinSteps.clickTarjetaDebito();
            generarCredinSteps.keyDownXParametrosGenerico(0, "7001");
            generarCredinSteps.clickContinuarGenerico();
            generarCredinSteps.clickCuentaDebito();
            generarCredinSteps.keyDownXParametrosGenerico(0, "Caja de Ahorro $ 502977134");
            generarCredinSteps.clickCBU();
            generarCredinSteps.setCampoAliasCbu("0450018601800093482711");
            generarCredinSteps.clickSelectorConcepto();
            generarCredinSteps.keyDownXParametrosGenerico(0, "Alquileres");
            generarCredinSteps.setImporteGenerico("10");
            generarCredinSteps.setCampoDescripcion("Test0014");
            generarCredinSteps.clickCheckEsCuentaPropia();
            generarCredinSteps.clickContinuarGenerico();
            generarCredinSteps.clickCheckTerminosCondiciones();
            dobleFactorSteps.codigoSegundoFactor();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0139-BSC: Transferencias Inmediatas - Entre cuentas propias - Flujo completo- Validar ticket con exito")
    public void TC0139BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uTranspull8);
            menuLateralSteps.clickTransferencias("4003","CA $ 014208540");
            transferenciasInmediatasSteps.seleccionarTipoTransferencia("Cuenta Propia");
            transferenciasInmediatasSteps.seleccionarCuentaDestinoCuentasPropias("CA $ 014208892");
            transferenciasInmediatasSteps.setImporteGenerico("100");
            transferenciasInmediatasSteps.seleccionarConcepto("Alquileres");
            transferenciasInmediatasSteps.setDescripcion("prueba");
            transferenciasInmediatasSteps.clickBotonTransferirAhora();
            transferenciasInmediatasSteps.clickConfirmarGenerico();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            //POSTCONDICION
            transferenciasInmediatas.verificarDescuentoCuentaOrigenTransferencia();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    //@Test // ya no se encuentra como campo obligatorio
    @DisplayName("TC-0140-BSC: Transferencias Inmediatas - Validar campo obligatorio Tipo de transferencia")
    public void TC0140BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(u10027480);
            menuLateralSteps.clickTransferencias("","");
            transferenciasSteps.clickTransferenciasInmediatas();
            transferenciasInmediatasSteps.seleccionarCuentaOrigen("3145");
            transferenciasInmediatasSteps.seleccionarCuentaDestino("1906");
            transferenciasInmediatasSteps.setImporteGenerico("100");
            transferenciasInmediatasSteps.seleccionarConcepto("Alquileres");
            transferenciasInmediatasSteps.setDescripcion("prueba");
            transferenciasInmediatasSteps.clickContinuarGenerico();
            transferenciasInmediatasSteps.validarMsjeCampoObligatorio();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Baja.class})
    @DisplayName("TC-0141-BSC: Transferencias Inmediatas - Validar campo obligatorio Importe")
    public void TC0141BSC() {
        try {

            loginSteps.iniciarSessionAlEscritorio(u10027480);
            menuLateralSteps.clickTransferencias("","");
            transferenciasInmediatasSteps.seleccionarTipoTransferencia("Cuenta Propia");
            transferenciasInmediatasSteps.seleccionarCuentaDestinoCuentasPropias("CC $ 200001906");
            transferenciasInmediatasSteps.clickConcepto();
            transferenciasInmediatasSteps.keyDownToText("Alquileres");
            transferenciasInmediatasSteps.setDescripcion("Automation");
            transferenciasInmediatasSteps.validarBotonTransferirDeshabilitado();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    //@Test //el campo concepto se encuentra seleccionado por defecto
    @DisplayName("TC-0142-BSC: Transferencias Inmediatas - Validar campo obligatorio Concepto")
    public void TC0142BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(u10027480);
            menuLateralSteps.clickTransferencias("","");
            transferenciasSteps.clickTransferenciasInmediatas();
            transferenciasInmediatasSteps.seleccionarCuentaOrigen("3145");
            transferenciasInmediatasSteps.seleccionarCuentaDestino("1906");
            transferenciasInmediatasSteps.setImporteGenerico("100");
            transferenciasInmediatasSteps.setDescripcion("prueba");
            transferenciasInmediatasSteps.clickContinuarGenerico();
            transferenciasInmediatasSteps.validarMsjeCampoObligatorio();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test //CAMPO OPCIONAL
    @Category({Baja.class})
    @DisplayName("TC-0143-BSC: Transferencias Inmediatas - Validar campo obligatorio Referencia")
    public void TC0143BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(u10027480);
            menuLateralSteps.clickTransferencias("","");
            transferenciasInmediatasSteps.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatasSteps.completarInputAliasCbu("GLOBO.FIESTA.PAZ");
            transferenciasInmediatasSteps.clickBuscarGenerico();
            transferenciasInmediatasSteps.botonSonCorrectos();
            transferenciasInmediatasSteps.setImporteGenerico("100");
            transferenciasInmediatasSteps.clickBotonTransferirAhora();
            transferenciasInmediatasSteps.clickConfirmarGenerico();
            dobleFactorSteps.codigoSegundoFactor();
            transferenciasInmediatasSteps.validarMsjeReferenciaVacia();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Baja.class})
    @DisplayName("TC-0144-BSC: Transferencias Inmediatas - Transferencia Cuentas propias con MISMA cuenta origen y cuenta destino- Validar mensaje de cuentas iguales")
    public void TC0144BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(u10027480);
            menuLateralSteps.clickTransferencias("","");
            transferenciasInmediatasSteps.seleccionarTipoTransferencia("Cuenta Propia");
            transferenciasInmediatasSteps.seleccionarMismaCuenta();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0145-BSC: Transferencias - Añadir nuevo destinatario - Con cbu valido")
    public void TC0145BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uAndu0406);
            menuLateralSteps.clickTransferencias("","");
            transferenciasInmediatasSteps.seleccionarTipoTransferencia("Contacto Agendado");
            transferenciasInmediatasSteps.clickAgendaDestinatario();
            agendaNuevoDestinatarioSteps.clickAdherirNuevoDestinatario();
            agendaNuevoDestinatarioSteps.setInputCbuAlias("3300000620000987477051");
            agendaNuevoDestinatarioSteps.clickBuscar();
            agendaNuevoDestinatarioSteps.validarDatosCuenta("3300000620000987477051", "ROLA, MARIO", "20080010045");
            agendaNuevoDestinatarioSteps.setPerteneceA("PruebaAutomation");
            transferenciasInmediatasSteps.botonSonCorrectos();
            dobleFactorSteps.codigoSegundoFactor();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            agendaNuevoDestinatarioSteps.clickVolverGenerico();
            agendaNuevoDestinatarioSteps.eliminarContactoAgendado("PruebaAutomation");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Alta.class})
    @DisplayName("TC-0146-BSC: Transferencias - Añadir nuevo destinatario - Con cbu invalido - Validar mensaje de error")
    public void TC0146BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(u10027480);
            menuLateralSteps.clickTransferencias("","");
            transferenciasInmediatasSteps.seleccionarTipoTransferencia("Contacto Agendado");
            transferenciasInmediatasSteps.clickAgendaDestinatario();
            agendaNuevoDestinatarioSteps.clickAdherirNuevoDestinatario();
            agendaNuevoDestinatarioSteps.setInputCbuAlias("330000062000098747705");
            agendaNuevoDestinatarioSteps.clickBuscar();
            agendaNuevoDestinatarioSteps.validarMensajeCbuInvalido();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Alta.class})
    @DisplayName("TC-0147-BSC: Transferencias - Añadir nuevo destinatario - Con alias valido- Validar datos de cuenta")
    public void TC0147BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uAndu0406);
            menuLateralSteps.clickTransferencias("","");

            transferenciasInmediatasSteps.seleccionarTipoTransferencia("Contacto Agendado");
            transferenciasInmediatasSteps.clickAgendaDestinatario();
            agendaNuevoDestinatarioSteps.clickAdherirNuevoDestinatario();
            //agendaNuevoDestinatarioSteps.clickLabelAlias();
            agendaNuevoDestinatarioSteps.setInputCbuAlias("PISTA.INDIO.CELTA");
            agendaNuevoDestinatarioSteps.clickBuscar();
            agendaNuevoDestinatarioSteps.validarDatosCuenta("0860001102800023334791", "CARRARO MARCOS MIGUEL", "20281561643");
            agendaNuevoDestinatarioSteps.setPerteneceA("PruebaAutomation");
            transferenciasInmediatasSteps.botonSonCorrectos();
            dobleFactorSteps.codigoSegundoFactor();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            agendaNuevoDestinatarioSteps.clickVolverGenerico();
            agendaNuevoDestinatarioSteps.eliminarContactoAgendado("PruebaAutomation");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Alta.class})
    @DisplayName("TC-0148-BSC: Transferencias - Añadir nuevo destinatario - Con alias invalido - Validar mensaje de error")
    public void TC0148BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(u10027480);
            menuLateralSteps.clickTransferencias("","");
            transferenciasInmediatasSteps.seleccionarTipoTransferencia("Contacto Agendado");
            transferenciasInmediatasSteps.clickAgendaDestinatario();
            agendaNuevoDestinatarioSteps.clickAdherirNuevoDestinatario();
            agendaNuevoDestinatarioSteps.setInputCbuAlias("fdsfs");
            agendaNuevoDestinatarioSteps.clickBuscar();
            agendaNuevoDestinatarioSteps.validarMensajeAliasInvalido();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Alta.class})
    @DisplayName("TC-0344-BSC: TRANSFERENCIAS - A cuentas de terceros ALIAS - otros bancos - Pesos")
    public void TC0344BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uTranspull8);
            menuLateralSteps.clickTransferencias("4003","CA $ 014208540");
            transferenciasInmediatas.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatas.completarInputAliasCbu("RRmqz.ieT50.Yt6W9");
            transferenciasInmediatas.clickBuscarGenerico();
            transferenciasInmediatas.botonSonCorrectos();
            transferenciasInmediatas.setImporteGenerico("100");
            transferenciasInmediatas.setDescripcion("Automation");
            transferenciasInmediatas.clickBotonTransferirAhora();
            transferenciasInmediatas.clickConfirmarGenerico();
            dobleFactorSteps.codigoSegundoFactor();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            //POSTCONDICION
            transferenciasInmediatas.verificarDescuentoCuentaOrigenTransferencia();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Media.class})
    @DisplayName("TC-0345-BSC: Transferencias - Verificar Mis transferencias y validar historial")
    public void TC0345BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(u10027480);
            menuLateralSteps.clickTransferencias("","");
            transferenciasInmediatasSteps.misTransferencias();
            transferenciasInmediatasSteps.validarTituloPageGenerico("Listado de transferencias");
            transferenciasInmediatasSteps.desplegableCuenta();
            transferenciasInmediatasSteps.keyDownToText("CA $ 202123145");
            transferenciasInmediatasSteps.clickContinuarGenerico();
            transferenciasInmediatasSteps.todosLosMovimientos();
            transferenciasInmediatasSteps.validarHistorial();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Criticos.class})
    @DisplayName("TC-12930-BSC: TRANSFERENCIAS - A cuentas de terceros CBU - mismo banco - Pesos")
    public void TC12930BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uTranspull8);
            menuLateralSteps.clickTransferencias("4003","CA $ 014208540");
            transferenciasInmediatasSteps.seleccionarCuentaOrigen("CA $ 014208540");
            transferenciasInmediatasSteps.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatasSteps.completarInputAliasCbu("0860060802800060338804");
            transferenciasInmediatasSteps.clickBuscarGenerico();
            transferenciasInmediatasSteps.botonSonCorrectos();
            transferenciasInmediatasSteps.setImporteGenerico("100");
            transferenciasInmediatasSteps.setDescripcion("Automation");
            transferenciasInmediatasSteps.clickBotonTransferirAhora();
            transferenciasInmediatasSteps.clickConfirmarGenerico();
            dobleFactorSteps.codigoSegundoFactor();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Alta.class})
    @DisplayName("TC-12931-BSC: TRANSFERENCIAS - A cuentas de terceros CBU - otros bancos - Pesos")
    public void TC12931BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uTranspull8);
            menuLateralSteps.clickTransferencias("4003","CA $ 014208540");
            transferenciasInmediatasSteps.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatasSteps.completarInputAliasCbu("3860001005000045554325");
            transferenciasInmediatasSteps.clickBuscarGenerico();
            transferenciasInmediatasSteps.botonSonCorrectos();
            transferenciasInmediatasSteps.setImporteGenerico("100");
            transferenciasInmediatasSteps.setDescripcion("Automation");
            transferenciasInmediatasSteps.clickBotonTransferirAhora();
            transferenciasInmediatasSteps.clickConfirmarGenerico();
            dobleFactorSteps.codigoSegundoFactor();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            //POSTCONDICION
            transferenciasInmediatas.verificarDescuentoCuentaOrigenTransferencia();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Criticos.class})
    @DisplayName("TC-12932-BSC: TRANSFERENCIAS - A cuentas de terceros ALIAS - mismo banco - Pesos")
    public void TC12932BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uTranspull8);
            menuLateralSteps.clickTransferencias("4003","CA $ 014208540");
            transferenciasInmediatasSteps.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatasSteps.completarInputAliasCbu("RON.SILLA.AMIGO");
            transferenciasInmediatasSteps.clickBuscarGenerico();
            transferenciasInmediatasSteps.botonSonCorrectos();
            transferenciasInmediatasSteps.setImporteGenerico("100");
            transferenciasInmediatasSteps.setDescripcion("Automation");
            transferenciasInmediatasSteps.clickBotonTransferirAhora();
            transferenciasInmediatasSteps.clickConfirmarGenerico();
            dobleFactorSteps.codigoSegundoFactor();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            //POSTCONDICION
            transferenciasInmediatas.verificarDescuentoCuentaOrigenTransferencia();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Media.class})
    @DisplayName("TC-12934-BSC: TRANSFERENCIAS - A cuentas de terceros CBU en dólares USD")
    public void TC12934BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uAndu0406);
            menuLateralSteps.clickTransferencias("3011","CA USD 503470069");
            transferenciasInmediatasSteps.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatasSteps.completarInputAliasCbu("0860060802020034435476");
            transferenciasInmediatasSteps.clickBuscarGenerico();
            transferenciasInmediatasSteps.botonSonCorrectos();
            transferenciasInmediatasSteps.setImporteGenerico("10");
            transferenciasInmediatasSteps.setDescripcion("Automation");
            transferenciasInmediatasSteps.clickBotonTransferirAhora();
            transferenciasInmediatasSteps.clickConfirmarGenerico();
            dobleFactorSteps.codigoSegundoFactor();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            //POSTCONDICION
            transferenciasInmediatas.verificarDescuentoCuentaOrigenTransferencia();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }

        }
    @Test
    @Category({Criticos.class})
    @DisplayName("TC-12935-BSC: Transferencias Programadas (Fecha especifica) CBU - terceros mismo banco  $ - NO AGENDADOS")
    public void TC12935BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uAndu0406);
            menuLateralSteps.clickTransferencias("3011","CC $ 500652404");
            transferenciasInmediatas.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatas.completarInputAliasCbu("0860020202800021231452");
            transferenciasInmediatas.clickBuscarGenerico();
            transferenciasInmediatas.botonSonCorrectos();
            transferenciasInmediatas.setImporteGenerico("100");
            transferenciasInmediatas.seleccionarConcepto("Alquileres");
            transferenciasInmediatas.setDescripcion("automation");
            transferenciasInmediatas.transferenciaProgramadaPara();
            transferenciasInmediatas.seleccionFechaFutura("Especifica");
            transferenciasInmediatasSteps.clickContinuarGenerico();
            transferenciasInmediatasSteps.clickConfirmarGenerico();
            dobleFactorSteps.codigoSegundoFactor();
            ticketSteps.validarTicket("Tu transaccion ha sido agendada");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Criticos.class})
    @DisplayName("TC-12969-BSC: TRANSFERENCIAS - A cuentas de terceros CVU - otros bancos - Pesos")
    public void TC12969BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uAndu0406);
            menuLateralSteps.clickTransferencias("7001","CA $ 502977134");
            transferenciasInmediatasSteps.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatasSteps.completarInputAliasCbu("0000254900000000115034");
            transferenciasInmediatasSteps.clickBuscarGenerico();
            transferenciasInmediatasSteps.botonSonCorrectos();
            transferenciasInmediatasSteps.setImporteGenerico("100");
            transferenciasInmediatasSteps.setDescripcion("Automation");
            transferenciasInmediatasSteps.clickBotonTransferirAhora();
            transferenciasInmediatasSteps.clickConfirmarGenerico();
            dobleFactorSteps.codigoSegundoFactor();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            //POSTCONDICION
            transferenciasInmediatas.verificarDescuentoCuentaOrigenTransferencia();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
     @Test
     @Category({Criticos.class})
     @DisplayName("TC-12981-BSC: Transferencias - Eliminar destinatario de agenda")
     public void TC12981BSC() {
        try {
            //PRECONDICION
            testCaseData.cargaDeDatosTest(uAndu0406);
            loginSteps.iniciarSessionAlEscritorioConPass(uAndu0406,testCaseData.password);
            agendaNuevoDestinatarioSteps.adherirNuevoDestinatarioCBU("3300000620000987477051","TC12981");
            //TEST
            menuLateralSteps.clickSoloTransferencias();
            transferenciasInmediatas.seleccionarTipoTransferencia("Contacto Agendado");
            transferenciasInmediatas.clickAgendaDestinatario();
            agendaNuevoDestinatarioSteps.eliminarContactoAgendado("TC12981");
            agendaNuevoDestinatarioSteps.validaNotificacionExitosaGenerico("Eliminaste a");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test //Error Tarjeta Inexistente0086 - 4069980055241015
    @Category({Criticos.class})
    @DisplayName("TC-12938-BSC: TRANSFERENCIAS - Generar CREDIN - Dolares")
    public void TC12938BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uAndu0406);
            menuLateralSteps.clickTransferencias("","");
            transferenciasInmediatas.clickMasOpcionesGenerico();
            transferenciasSteps.clickConsultarCredin();
            consultaCredinSteps.clickGenerarCredin();
            generarCredinSteps.clickTarjetaDebito();
            generarCredinSteps.keyDownXParametrosGenerico(0, "7001");
            generarCredinSteps.clickContinuarGenerico();
            generarCredinSteps.clickCuentaDebito();
            generarCredinSteps.keyDownXParametrosGenerico(1, "Caja de Ahorro USD 503470069");
            generarCredinSteps.clickCBU();
            generarCredinSteps.setCampoAliasCbu("3860051504000080441898");
            generarCredinSteps.clickSelectorConcepto();
            generarCredinSteps.keyDownXParametrosGenerico(0, "Alquileres");
            generarCredinSteps.setImporteGenerico("1");
            generarCredinSteps.setCampoDescripcion("Test12938");
            generarCredinSteps.clickContinuarGenerico();
            generarCredinSteps.clickCheckTerminosCondiciones();
            dobleFactorSteps.codigoSegundoFactor();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Criticos.class})
    @DisplayName("TC-12936-BSC: TRANSFERENCIAS - Consulta de transferencias realizadas")
    public void TC12936BSC() {
        try {
            //CASO
            loginSteps.iniciarSessionAlEscritorio(u10027480);
            menuLateralSteps.clickTransferencias("","");
            transferenciasInmediatas.misTransferencias();
            transferenciasInmediatas.desplegableCuenta();
            transferenciasInmediatas.keyDownToText("CA $ 202123145");
            transferenciasInmediatas.clickContinuarGenerico();
            botonesGenericosSteps.clickVerMas();
            calendarioSteps.clickEnFechaDeCreacionDesde();
            calendarioSteps.clickMesAnterior(5);
            calendarioSteps.clickEnDiaXParametro("10");
            transferenciasInmediatas.clickBuscarGenerico();
            transferenciasInmediatas.validarListadoTransferencias();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test //Falta probar el caso por error en la busqueda del CREDIN (UPS)
    @Category({Criticos.class})
    @DisplayName("TC-12939-BSC: TRANSFERENCIAS - Consultar CREDIN Originante")
    public void TC12939BSC() {
        try {
            //Precondicion
            loginSteps.iniciarSessionAlEscritorio(uAndu0406);
            generarCredinSteps.generarCredinApi("0860050902800029771347","0860001102800004836191","7001");
            //Test: Consulta de Credin
            menuLateralSteps.clickTransferencias("","");
            transferenciasInmediatas.clickMasOpcionesGenerico();
            transferenciasSteps.clickConsultarCredin();
            calendarioSteps.clickEnFechaDeCreacionDesde();
            calendarioSteps.clickDiaActual();
            calendarioSteps.clickEnFechaDeCreacionHasta();
            calendarioSteps.clickDiaActual();
            consultaCredinSteps.clickOriginante();
            consultaCredinSteps.clickCuitDestinatario();
            consultaCredinSteps.setCuitDestinatario("20281561643");
            consultaCredinSteps.clickEstado();
            consultaCredinSteps.keyDownToText("Todos");
            consultaCredinSteps.clickBuscarGenerico();
            consultaCredinSteps.clickDetalleCredin();
            consultaCredinSteps.validacionDescripcionCredin("testAutomation");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test //Falta probar el caso por error en la busqueda del CREDIN (UPS)
    @Category({Criticos.class})
    @DisplayName("TC-12940-BSC: TRANSFERENCIAS - Consultar CREDIN destinatario")
    public void TC12940BSC() {
        try {
            //Precondicion: Generar Credin
            loginSteps.loginForAPI(uBsc001);
            generarCredinSteps.generarCredinApi("0860001102800004836191","0860050902800029771347","2019");
            //Test: Consulta de Credin
            loginSteps.iniciarSessionAlEscritorio(uAndu0406);
            menuLateralSteps.clickTransferencias("","");
            transferenciasInmediatas.clickMasOpcionesGenerico();
            transferenciasSteps.clickConsultarCredin();
            calendarioSteps.clickEnFechaDeCreacionDesde();
            calendarioSteps.clickDiaActual();
            calendarioSteps.clickEnFechaDeCreacionHasta();
            calendarioSteps.clickDiaActual();
            consultaCredinSteps.clickDestinatario();
            consultaCredinSteps.clickCuitOriginante();
            consultaCredinSteps.setCuitOriginante("20281561643");
            consultaCredinSteps.clickEstado();
            consultaCredinSteps.keyDownToText("Todos");
            consultaCredinSteps.clickBuscarGenerico();
            consultaCredinSteps.clickDetalleCredin();
            consultaCredinSteps.validacionDescripcionCredin("testAutomation");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Criticos.class})
    @DisplayName("TC-14250-BSC: TRANSFERENCIAS - A cuentas propias otros bancos")
    public void TC14250BSC(){
        try{
            loginSteps.iniciarSessionAlEscritorio(uTranspull8);
            menuLateralSteps.clickTransferencias("4003","CA $ 014208540");
            transferenciasInmediatas.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatas.completarInputAliasCbu("3860001003000056201573");
            transferenciasInmediatas.clickBuscarGenerico();
            transferenciasInmediatas.botonSonCorrectos();
            transferenciasInmediatas.setImporteGenerico("1");
            transferenciasInmediatas.seleccionarConcepto("Alquileres");
            transferenciasInmediatas.setDescripcion("prueba");
            transferenciasInmediatas.clickBotonTransferirAhora();
            transferenciasInmediatas.clickConfirmarGenerico();
            dobleFactorSteps.codigoSegundoFactor();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            //POSTCONDICION
            transferenciasInmediatas.verificarDescuentoCuentaOrigenTransferencia();
        }catch (Exception ex){
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0409-BSC: TRANSFERENCIAS - terceros mismo banco  $ - AGENDADOS")
    public void TC0409BSC(){
        try{
            //PRECONDICION
            loginSteps.iniciarSessionAlEscritorio(uAndu0406);
            agendaNuevoDestinatarioSteps.adherirNuevoDestinatarioCBU("0860001102800074151237","TC0409BSC");
            //TEST
            menuLateralSteps.clickTransferencias("3011","CC $ 500652404");
            transferenciasInmediatas.seleccionarTipoTransferencia("Contacto Agendado");
            transferenciasInmediatas.clickAgendaDestinatario();
            transferenciasInmediatas.transferirAunContacto("TC0409BSC");
            transferenciasInmediatas.setImporteGenerico("1");
            transferenciasInmediatas.seleccionarConcepto("Alquileres");
            //transferenciasInmediatas.setDescripcion("TC0409");
            transferenciasInmediatas.clickBotonTransferirAhora();
            transferenciasInmediatas.clickConfirmarGenerico();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            //POSTCONDICION
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("0860001102800074151237");
            transferenciasInmediatas.verificarDescuentoCuentaOrigenTransferencia();
        }catch (Exception ex){
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Alta.class})
    @DisplayName("TC-0403-BSC: Transferencias a cuentas propias otro banco (GRUPO) $ - AGENDADOS")
    public void TC0403BSC(){
        try{
            //PRECONDICION - AGENDAR CONTACTO
            loginSteps.iniciarSessionAlEscritorio(u21829785);
            agendaNuevoDestinatarioSteps.adherirNuevoDestinatarioCBU("3300520915200003347070","PROPIA-BSF");
            //TEST
            menuLateralSteps.clickTransferencias("","CC $ 850060283");
            transferenciasInmediatas.seleccionarTipoTransferencia("Cuenta Propia");
            transferenciasInmediatas.seleccionarCuentaDestinoCuentasPropias("PROPIA-BSF");
            transferenciasInmediatas.seleccionarCuentaOrigen("CC $ 850060283");
            transferenciasInmediatas.setImporteGenerico("1");
            transferenciasInmediatas.seleccionarConcepto("Alquiler");
            transferenciasInmediatas.setDescripcion("TestAutomation");
            transferenciasInmediatas.clickBotonTransferirAhora();
            transferenciasInmediatas.clickConfirmarGenerico();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            //POSTCONDICION - ELIMINAR CONTACTO
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("3300520915200003347070");
            transferenciasInmediatas.verificarDescuentoCuentaOrigenTransferencia();
        }catch (Exception ex){
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test //Falta de datos modificar usuarios y datos en pasos
    @Category({Criticos.class})
    @DisplayName("TC-0407-BSC: Transferencias ALIAS a cuentas propias otro banco $ - NO AGENDADOS")
    public void TC0407BSJ(){
        try{
            //TEST
            loginSteps.iniciarSessionAlEscritorio(utranspull1);
            menuLateralSteps.clickTransferencias("","CA $ 014208472");
            transferenciasInmediatas.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatas.completarInputAliasCbu("CATRE.PESO.CELTO");
            transferenciasInmediatas.clickBuscarGenerico();
            transferenciasInmediatas.botonSonCorrectos();
            transferenciasInmediatas.setImporteGenerico("1");
            transferenciasInmediatas.seleccionarConcepto("Alquiler");
            transferenciasInmediatas.setDescripcion("TestAutomation");
            transferenciasInmediatas.clickBotonTransferirAhora();
            transferenciasInmediatas.clickConfirmarGenerico();
            dobleFactorSteps.codigoSegundoFactor();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            //POSTCONDICION
            transferenciasInmediatas.verificarDescuentoCuentaOrigenTransferencia();
        }catch (Exception ex){
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test // Sin usuarios con dos cuentas usd habilitadas para realizar la operación
    @Category({Alta.class})
    @DisplayName("TC-0402-BSC: Transferencias entre cuentas propias mismo banco USD")
    public void TC0402BSC(){
        try{
            loginSteps.iniciarSessionAlEscritorio(uTranspull8);
            menuLateralSteps.clickTransferencias("9000","CA USD 520000050203");

            transferenciasInmediatas.seleccionarTipoTransferencia("Cuenta Propia");
            transferenciasInmediatas.seleccionarCuentaDestinoCuentasPropias("CA USD 000000376909");
            transferenciasInmediatas.seleecionarTarjeaAOperar("9000");
            transferenciasInmediatas.seleccionarCuentaOrigen("CA USD 520000050203");
            transferenciasInmediatas.setImporteGenerico("1");
            transferenciasInmediatas.seleccionarConcepto("Alquiler");
            transferenciasInmediatas.setDescripcion("TestAutomation");
            transferenciasInmediatas.clickBotonTransferirAhora();
            transferenciasInmediatas.clickConfirmarGenerico();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            //POSTCONDICION
            transferenciasInmediatas.verificarDescuentoCuentaOrigenTransferencia();
        }catch (Exception ex){
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test//variable prendida de identidad
    @Category({Baja.class})
    @DisplayName("TC-0419-BSC: Transferencias CBU - terceros otros bancos INTERBANK (No PETERSEN) $ - NO AGENDADOS")
    public void TC0419BSC(){
        try{
            //TEST
            loginSteps.iniciarSessionAlEscritorio(uPruebaqaBSC03);
            menuLateralSteps.clickTransferencias("","CA $ 606003276");
            transferenciasInmediatas.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatas.completarInputAliasCbu("0720000788000006008668");
            transferenciasInmediatas.clickBuscarGenerico();
            transferenciasInmediatas.botonSonCorrectos();
            transferenciasInmediatas.setImporteGenerico("1");
            transferenciasInmediatas.seleccionarConcepto("Alquiler");
            transferenciasInmediatas.setDescripcion("TestAutomation");
            transferenciasInmediatas.clickBotonTransferirAhora();
            transferenciasInmediatas.clickConfirmarGenerico();
            dobleFactorSteps.codigoSegundoFactor();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            //POSTCONDICION
            transferenciasInmediatas.verificarDescuentoCuentaOrigenTransferencia();

        }catch (Exception ex){
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test //Modificar usuario y dato de cuenta cuando no tega variable prendida el ambiente de identidad
    @Category({Baja.class})
    @DisplayName("TC-0420-BSC: Transferencias CBU - terceros otros bancos INTERBANK (No PETERSEN) USD - NO AGENDADOS")
    public void TC0420BSC(){
        try{
            //TEST
            loginSteps.iniciarSessionAlEscritorio(uPruebaqaBSC03);
            menuLateralSteps.clickTransferencias("","CA $ 600009140");
            transferenciasInmediatas.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatas.completarInputAliasCbu("0720000788000006008668");
            transferenciasInmediatas.clickBuscarGenerico();
            transferenciasInmediatas.botonSonCorrectos();
            transferenciasInmediatas.setImporteGenerico("1");
            transferenciasInmediatas.seleccionarConcepto("Alquiler");
            transferenciasInmediatas.setDescripcion("TestAutomation");
            transferenciasInmediatas.clickBotonTransferirAhora();
            transferenciasInmediatas.clickConfirmarGenerico();
            dobleFactorSteps.codigoSegundoFactor();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            //POSTCONDICION
            transferenciasInmediatas.verificarDescuentoCuentaOrigenTransferencia();
        }catch (Exception ex){
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Media.class})
    @DisplayName("TC-0418-BSC: Transferencias CBU - terceros otros bancos USD - NO AGENDADOS")
    public void TC0418BSC(){
        try{
            //TEST
            loginSteps.iniciarSessionAlEscritorio(uSantacruz02);
            menuLateralSteps.clickTransferencias("","CA USD 603403321");
            transferenciasInmediatas.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatas.completarInputAliasCbu("3300000640000001703050");
            transferenciasInmediatas.clickBuscarGenerico();
            transferenciasInmediatas.botonSonCorrectos();
            transferenciasInmediatas.setImporteGenerico("1");
            transferenciasInmediatas.seleccionarConcepto("Alquiler");
            transferenciasInmediatas.setDescripcion("TestAutomation");
            transferenciasInmediatas.clickBotonTransferirAhora();
            transferenciasInmediatas.clickConfirmarGenerico();
            dobleFactorSteps.codigoSegundoFactor();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            //POSTCONDICION
            transferenciasInmediatas.verificarDescuentoCuentaOrigenTransferencia();
        }catch (Exception ex){
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Alta.class})
    @DisplayName("TC-0410-BSC: Transferencias - terceros mismo banco  USD - AGENDADOS")
    public void TC0410BSC(){
        try{
            //PRECONDICION
            loginSteps.iniciarSessionAlEscritorio(u21829785);
            agendaNuevoDestinatarioSteps.adherirNuevoDestinatarioCBU("0860025702020037224765","TC0410BSC");
            //TEST
            menuLateralSteps.clickTransferencias("","CA USD 853630210");
            transferenciasInmediatas.seleccionarTipoTransferencia("Contacto Agendado");
            transferenciasInmediatas.clickAgendaDestinatario();
            transferenciasInmediatas.transferirAunContacto("TC0410BSC");
            transferenciasInmediatas.setImporteGenerico("1");
            transferenciasInmediatas.seleccionarConcepto("Alquileres");
            transferenciasInmediatas.setDescripcion("TC0410");
            transferenciasInmediatas.clickBotonTransferirAhora();
            transferenciasInmediatas.clickConfirmarGenerico();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            //POSTCONDICION
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("0860025702020037224765");
            transferenciasInmediatas.verificarDescuentoCuentaOrigenTransferencia();
        }catch (Exception ex){
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Media.class})
    @DisplayName("TC-0412-BSC: Transferencias - terceros otros bancos USD - AGENDADOS")
    public void TC0412BSC(){
        try{
            //PRECONDICION
            loginSteps.iniciarSessionAlEscritorio(u21829785);
            agendaNuevoDestinatarioSteps.adherirNuevoDestinatarioCBU("3300520945200000502031","TC0412BSC");
            //TEST
            menuLateralSteps.clickTransferencias("","CA USD 853630210");
            transferenciasInmediatas.seleccionarTipoTransferencia("Contacto Agendado");
            transferenciasInmediatas.clickAgendaDestinatario();
            transferenciasInmediatas.transferirAunContacto("TC0412BSC");
            transferenciasInmediatas.seleccionarCuentaOrigen("CA USD 853630210");
            transferenciasInmediatas.setImporteGenerico("1");
            transferenciasInmediatas.seleccionarConcepto("Alquileres");
            transferenciasInmediatas.setDescripcion("TC0412");
            transferenciasInmediatas.clickBotonTransferirAhora();
            transferenciasInmediatas.clickConfirmarGenerico();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            //POSTCONDICION
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("3300520945200000502031");
            transferenciasInmediatas.verificarDescuentoCuentaOrigenTransferencia();
        }catch (Exception ex){
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Media.class})
    @DisplayName("TC-0413-BSC: Transferencias - terceros otros bancos INTERBANK (No PETERSEN) $ - AGENDADOS")
    public void TC0413BSC(){
        try{
            //PRECONDICION
            loginSteps.iniciarSessionAlEscritorio(u21829785);
            agendaNuevoDestinatarioSteps.adherirNuevoDestinatarioCBU("0720000788000006008668","TC0413BSC");
            //TEST
            menuLateralSteps.clickTransferencias("","CA $ 850062210");
            transferenciasInmediatas.seleccionarTipoTransferencia("Contacto Agendado");
            transferenciasInmediatas.clickAgendaDestinatario();
            transferenciasInmediatas.transferirAunContacto("TC0413BSC");
            transferenciasInmediatas.seleccionarCuentaOrigen("");
            transferenciasInmediatas.setImporteGenerico("1");
            transferenciasInmediatas.seleccionarConcepto("Alquileres");
            transferenciasInmediatas.setDescripcion("TC0413");
            transferenciasInmediatas.clickBotonTransferirAhora();
            transferenciasInmediatas.clickConfirmarGenerico();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            //POSTCONDICION
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("0720000788000006008668");
            transferenciasInmediatas.verificarDescuentoCuentaOrigenTransferencia();


        }catch (Exception ex){
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Media.class})
    @DisplayName("TC-0414-BSC: Transferencias - terceros otros bancos INTERBANK (No PETERSEN) USD - AGENDADOS")
    public void TC0414BSC(){
        try{
            //PRECONDICION
            loginSteps.iniciarSessionAlEscritorio(u21829785);
            agendaNuevoDestinatarioSteps.adherirNuevoDestinatarioCBU("0720000788000006008668","TC0414BSC");
            //TEST
            menuLateralSteps.clickTransferencias("","CA USD 853630210");
            transferenciasInmediatas.seleccionarTipoTransferencia("Contacto Agendado");
            transferenciasInmediatas.clickAgendaDestinatario();
            transferenciasInmediatas.transferirAunContacto("TC0414BSC");
            transferenciasInmediatas.setImporteGenerico("1");
            transferenciasInmediatas.seleccionarConcepto("Alquileres");
            transferenciasInmediatas.setDescripcion("TC0414");
            transferenciasInmediatas.clickBotonTransferirAhora();
            transferenciasInmediatas.clickConfirmarGenerico();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            //POSTCONDICION
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("0720000788000006008668");
            transferenciasInmediatas.verificarDescuentoCuentaOrigenTransferencia();
        }catch (Exception ex){
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0427-BSC: Transferencias CVU - terceros $ AGENDADOS")
    public void TC0427BSC(){
        try{
            //PRECONDICION
            loginSteps.iniciarSessionAlEscritorio(u21829785);
            agendaNuevoDestinatarioSteps.adherirNuevoDestinatarioCBU("0000001700300000000313","TC0427BSC");
            //TEST
            menuLateralSteps.clickTransferencias("","CA $ 850062210");
            transferenciasInmediatas.seleccionarTipoTransferencia("Contacto Agendado");
            transferenciasInmediatas.clickAgendaDestinatario();
            transferenciasInmediatas.transferirAunContacto("TC0427BSC");
            transferenciasInmediatas.setImporteGenerico("1");
            transferenciasInmediatas.seleccionarConcepto("Alquileres");
            transferenciasInmediatas.setDescripcion("TC0427");
            transferenciasInmediatas.clickBotonTransferirAhora();
            transferenciasInmediatas.clickConfirmarGenerico();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            //POSTCONDICION
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("0000001700300000000313");
            transferenciasInmediatas.verificarDescuentoCuentaOrigenTransferencia();
        }catch (Exception ex){
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0429-BSC: Transferencias ALIAS de CVU - terceros $ NO AGENDADOS")
    public void TC0429BSC(){
        try{
            //TEST
            loginSteps.iniciarSessionAlEscritorio(u21829785);
            menuLateralSteps.clickTransferencias("","CA $ 850062210");
            transferenciasInmediatas.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatas.completarInputAliasCbu("BOTE.FILA.MITO");
            transferenciasInmediatas.clickBuscarGenerico();
            transferenciasInmediatas.botonSonCorrectos();
            transferenciasInmediatas.setImporteGenerico("1");
            transferenciasInmediatas.seleccionarConcepto("Alquileres");
            transferenciasInmediatas.setDescripcion("TC0429");
            transferenciasInmediatas.clickBotonTransferirAhora();
            transferenciasInmediatas.clickConfirmarGenerico();
            dobleFactorSteps.codigoSegundoFactor();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            //POSTCONDICION
            transferenciasInmediatas.verificarDescuentoCuentaOrigenTransferencia();
        }catch (Exception ex){
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test //Modificar usuario y dato de cuenta cuando no tega variable prendida el ambiente de identidad
    @Category({Media.class})
    @DisplayName("TC-0424-BSC: Transferencias ALIAS - terceros otros bancos USD - NO AGENDADOS")
    public void TC0424BSC(){
        try{
            //TEST
            loginSteps.iniciarSessionAlEscritorio(u21829785);
            menuLateralSteps.clickTransferencias("","CA USD 853630210");
            transferenciasInmediatas.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatas.completarInputAliasCbu("K3SI4.2827d.9URLD");
            transferenciasInmediatas.clickBuscarGenerico();
            transferenciasInmediatas.botonSonCorrectos();
            transferenciasInmediatas.setImporteGenerico("1");
            transferenciasInmediatas.seleccionarConcepto("Alquiler");
            transferenciasInmediatas.setDescripcion("TestAutomation");
            transferenciasInmediatas.clickBotonTransferirAhora();
            transferenciasInmediatas.clickConfirmarGenerico();
            dobleFactorSteps.codigoSegundoFactor();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            //POSTCONDICION
            transferenciasInmediatas.verificarDescuentoCuentaOrigenTransferencia();
        }catch (Exception ex){
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0465-BSC: Transferencias Programadas (Fecha especifica) - terceros mismo banco  $ - AGENDADOS")
    public void TC0465BSC(){
        try{
            //PRECONDICION
            loginSteps.iniciarSessionAlEscritorio(u21829785);
            agendaNuevoDestinatarioSteps.adherirNuevoDestinatarioCBU("0860001102800001733105","TC0465BSC");
            //TEST
            menuLateralSteps.clickTransferencias("","CA $ 850062210");
            transferenciasInmediatas.seleccionarTipoTransferencia("Contacto Agendado");
            transferenciasInmediatas.clickAgendaDestinatario();
            transferenciasInmediatas.transferirAunContacto("TC0465BSC");
            transferenciasInmediatas.setImporteGenerico("1");
            transferenciasInmediatas.seleccionarConcepto("Alquileres");
            transferenciasInmediatas.setDescripcion("TC0465");
            transferenciasInmediatas.transferenciaProgramadaPara();
            transferenciasInmediatas.seleccionFechaFutura("Especifica");
            transferenciasInmediatas.clickContinuarGenerico();
            transferenciasInmediatas.clickConfirmarGenerico();
            ticketSteps.validarTicket("Tu transaccion ha sido agendada");
            //POSTCONDICION
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("0860001102800001733105");

        }catch (Exception ex){
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test //Modificar usuario y dato de cuenta cuando no tega variable prendida el ambiente de identidad
    @Category({Media.class})
    @DisplayName("TC-0422-BSC: Transferencias ALIAS - terceros mismo banco  USD - NO AGENDADOS")
    public void TC0422BSC(){
        try{
            //TEST
            loginSteps.iniciarSessionAlEscritorio(u21829785);
            menuLateralSteps.clickTransferencias("","CA USD 853630210");
            transferenciasInmediatas.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatas.completarInputAliasCbu("PISTA.CELTA.REFRAN");
            transferenciasInmediatas.clickBuscarGenerico();
            transferenciasInmediatas.botonSonCorrectos();
            transferenciasInmediatas.setImporteGenerico("1");
            transferenciasInmediatas.seleccionarConcepto("Alquiler");
            transferenciasInmediatas.setDescripcion("TestAutomation");
            transferenciasInmediatas.clickBotonTransferirAhora();
            transferenciasInmediatas.clickConfirmarGenerico();
            dobleFactorSteps.codigoSegundoFactor();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            //POSTCONDICION
            transferenciasInmediatas.verificarDescuentoCuentaOrigenTransferencia();
        }catch (Exception ex){
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Alta.class})
    @DisplayName("TC-0466-BSC: Transferencias Programadas (Fecha especifica) - terceros mismo banco  USD - AGENDADOS")
    public void TC0466BSC(){
        try{
            //PRECONDICION
            loginSteps.iniciarSessionAlEscritorio(u23013315);
            agendaNuevoDestinatarioSteps.adherirNuevoDestinatarioCBU("0860085102020076584659","TC0466BSC");
            //TEST
            menuLateralSteps.clickTransferencias("","CA USD 253722476");
            transferenciasInmediatas.seleccionarTipoTransferencia("Contacto Agendado");
            transferenciasInmediatas.clickAgendaDestinatario();
            transferenciasInmediatas.transferirAunContacto("TC0466BSC");
            transferenciasInmediatas.seleccionarCuentaOrigen("CA USD 253722476");
            transferenciasInmediatas.setImporteGenerico("1");
            transferenciasInmediatas.seleccionarConcepto("Alquileres");
            transferenciasInmediatas.setDescripcion("TC0466");
            transferenciasInmediatas.transferenciaProgramadaPara();
            transferenciasInmediatas.seleccionFechaFutura("Especifica");
            transferenciasInmediatas.clickContinuarGenerico();
            transferenciasInmediatas.clickConfirmarGenerico();
            ticketSteps.validarTicket("Tu transaccion ha sido agendada");
            //POSTCONDICION
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("0860085102020076584659");

        }catch (Exception ex){
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0467-BSC: Transferencias Programadas (Fecha especifica) - terceros otros bancos $ - AGENDADOS")
    public void TC0467BSC(){
        try{
            //PRECONDICION
            loginSteps.iniciarSessionAlEscritorio(uAndu0406);
            agendaNuevoDestinatarioSteps.adherirNuevoDestinatarioCBU("3860195203000041101110","TC0467BSC");
            //TEST
            menuLateralSteps.clickTransferencias("7001","CA $ 502977134");
            transferenciasInmediatas.seleccionarTipoTransferencia("Contacto Agendado");
            transferenciasInmediatas.clickAgendaDestinatario();
            transferenciasInmediatas.transferirAunContacto("TC0467BSC");
            transferenciasInmediatas.clickTarjetaDeDebito();
            transferenciasInmediatas.keyDownToText("7001");
            transferenciasInmediatas.seleccionarCuentaOrigen("CA $ 502977134");
            transferenciasInmediatas.setImporteGenerico("1");
            transferenciasInmediatas.seleccionarConcepto("Alquileres");
            transferenciasInmediatas.setDescripcion("TC0467");
            transferenciasInmediatas.transferenciaProgramadaPara();
            transferenciasInmediatas.seleccionFechaFutura("Especifica");
            transferenciasInmediatas.clickContinuarGenerico();
            transferenciasInmediatas.clickConfirmarGenerico();
            ticketSteps.validarTicket("Tu transaccion ha sido agendada");
            //POSTCONDICION
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("3860195203000041101110");
        }catch (Exception ex){
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    //@Test
    @Category({Media.class})
    @DisplayName("TC-0458-BSC: Transferencias Programadas (Fecha especifica) entre cuentas propias mismo banco USD")
    public void TC0458BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(u21829785);
            menuLateralSteps.clickTransferencias("","CA USD 853630210");
            transferenciasInmediatas.seleccionarTipoTransferencia("Cuenta Propia");
            transferenciasInmediatas.seleccionarCuentaDestinoCuentasPropias("CA USD 857658465");
            //transferenciasInmediatas.seleecionarTarjeaAOperar("9000");
            transferenciasInmediatas.seleccionarCuentaOrigen("CA USD 853630210");
            transferenciasInmediatas.setImporteGenerico("1");
            transferenciasInmediatas.seleccionarConcepto("Alquileres");
            transferenciasInmediatas.setDescripcion("TestAutomation");
            transferenciasInmediatas.transferenciaProgramadaPara();
            transferenciasInmediatas.seleccionFechaFutura("Especifica");
            transferenciasInmediatas.clickContinuarGenerico();
            transferenciasInmediatas.clickConfirmarGenerico();
            dobleFactorSteps.codigoSegundoFactor();
            ticketSteps.validarTicket("Tu transaccion ha sido agendada");
            ticketSteps.clickVolverGenerico();
            transferenciasInmediatas.validarTituloPageGenerico("Transferencias");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }


    @Test
    @Category({Alta.class})
    @DisplayName("TC-0468-BSC: Transferencias Programadas (Fecha especifica) - terceros otros bancos USD - AGENDADOS")
    public void TC0468BSC(){
        try{
            //PRECONDICION
            loginSteps.iniciarSessionAlEscritorio(u23013315);
            agendaNuevoDestinatarioSteps.adherirNuevoDestinatarioCBU("0450500602020050297801","TC0468BSC");
            //TEST
            menuLateralSteps.clickTransferencias("","CA USD 253722476");
            transferenciasInmediatas.seleccionarTipoTransferencia("Contacto Agendado");
            transferenciasInmediatas.clickAgendaDestinatario();
            transferenciasInmediatas.transferirAunContacto("TC0468BSC");
            transferenciasInmediatas.seleccionarCuentaOrigen("CA USD 253722476");
            transferenciasInmediatas.setImporteGenerico("1");
            transferenciasInmediatas.seleccionarConcepto("Alquileres");
            transferenciasInmediatas.setDescripcion("TC0468");
            transferenciasInmediatas.transferenciaProgramadaPara();
            transferenciasInmediatas.seleccionFechaFutura("Especifica");
            transferenciasInmediatas.clickContinuarGenerico();
            transferenciasInmediatas.clickConfirmarGenerico();
            ticketSteps.validarTicket("Tu transaccion ha sido agendada");
            //POSTCONDICION
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("0450500602020050297801");

        }catch (Exception ex){
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Baja.class})
    @DisplayName("TC-0469-BSC: Transferencias - terceros otros bancos INTERBANK (No PETERSEN) $ - AGENDADOS")
    public void TC0469BSC(){
        try{
            //PRECONDICION
            loginSteps.iniciarSessionAlEscritorio(u21829785);
            agendaNuevoDestinatarioSteps.adherirNuevoDestinatarioCBU("0720000788000006008668","TC0469BSC");
            //TEST
            menuLateralSteps.clickTransferencias("","CA $ 850062210");
            transferenciasInmediatas.seleccionarTipoTransferencia("Contacto Agendado");
            transferenciasInmediatas.clickAgendaDestinatario();
            transferenciasInmediatas.transferirAunContacto("TC0469BSC");
            transferenciasInmediatas.seleccionarCuentaOrigen("CA $ 850062210");
            transferenciasInmediatas.setImporteGenerico("1");
            transferenciasInmediatas.seleccionarConcepto("Alquileres");
            transferenciasInmediatas.setDescripcion("TC0468");
            transferenciasInmediatas.transferenciaProgramadaPara();
            transferenciasInmediatas.seleccionFechaFutura("Especifica");
            transferenciasInmediatas.clickContinuarGenerico();
            transferenciasInmediatas.clickConfirmarGenerico();
            ticketSteps.validarTicket("Tu transaccion ha sido agendada");
            //POSTCONDICION
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("0720000788000006008668");
            transferenciasInmediatas.verificarDescuentoCuentaOrigenTransferencia();
        }catch (Exception ex){
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Baja.class})
    @DisplayName("TC-0470-BSC: Transferencias - terceros otros bancos INTERBANK (No PETERSEN) USD - AGENDADOS")
    public void TC0470BSC(){
        try{
            //PRECONDICION
            loginSteps.iniciarSessionAlEscritorio(u21829785);
            agendaNuevoDestinatarioSteps.adherirNuevoDestinatarioCBU("0720000788000006008668","TC0470BSC");
            //TEST
            menuLateralSteps.clickTransferencias("","CA USD 853630210");
            transferenciasInmediatas.seleccionarTipoTransferencia("Contacto Agendado");
            transferenciasInmediatas.clickAgendaDestinatario();
            transferenciasInmediatas.transferirAunContacto("TC0470BSC");
            transferenciasInmediatas.seleccionarCuentaOrigen("CA USD 853630210");
            transferenciasInmediatas.setImporteGenerico("1");
            transferenciasInmediatas.seleccionarConcepto("Alquileres");
            transferenciasInmediatas.setDescripcion("TC0470");
            transferenciasInmediatas.transferenciaProgramadaPara();
            transferenciasInmediatas.seleccionFechaFutura("Especifica");
            transferenciasInmediatas.clickContinuarGenerico();
            transferenciasInmediatas.clickConfirmarGenerico();
            ticketSteps.validarTicket("Tu transaccion ha sido agendada");
            //POSTCONDICION
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("0720000788000006008668");
            transferenciasInmediatas.verificarDescuentoCuentaOrigenTransferencia();
        }catch (Exception ex){
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0483-BSC: Transferencias Programadas (Fecha especifica) CVU - terceros $ AGENDADOS")
    public void TC0483BSC(){
        try{
            //PRECONDICION
            loginSteps.iniciarSessionAlEscritorio(u21829785);
            agendaNuevoDestinatarioSteps.adherirNuevoDestinatarioCBU("0000001700300000000313","TC0483BSC");
            //TEST
            menuLateralSteps.clickTransferencias("","CA $ 850062210");
            transferenciasInmediatas.seleccionarTipoTransferencia("Contacto Agendado");
            transferenciasInmediatas.clickAgendaDestinatario();
            transferenciasInmediatas.transferirAunContacto("TC0483BSC");
            transferenciasInmediatas.seleccionarCuentaOrigen("CA $ 850062210");
            transferenciasInmediatas.setImporteGenerico("1");
            transferenciasInmediatas.seleccionarConcepto("Alquileres");
            transferenciasInmediatas.setDescripcion("TC0483");
            transferenciasInmediatas.transferenciaProgramadaPara();
            transferenciasInmediatas.seleccionFechaFutura("Especifica");
            transferenciasInmediatas.clickContinuarGenerico();
            transferenciasInmediatas.clickConfirmarGenerico();
            ticketSteps.validarTicket("Tu transaccion ha sido agendada");
            //POSTCONDICION
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("0000001700300000000313");
        }catch (Exception ex){
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0484-BSC: Transferencias Programadas (Fecha especifica) CVU - terceros $ NO AGENDADOS")
    public void TC0484BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(u21829785);
            menuLateralSteps.clickTransferencias("","CA $ 850062210");
            transferenciasInmediatasSteps.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatasSteps.completarInputAliasCbu("0000001700300000000313");
            transferenciasInmediatasSteps.clickBuscarGenerico();
            transferenciasInmediatasSteps.botonSonCorrectos();
            transferenciasInmediatasSteps.setImporteGenerico("1");
            transferenciasInmediatasSteps.setDescripcion("TC0484");
            transferenciasInmediatas.transferenciaProgramadaPara();
            transferenciasInmediatas.seleccionFechaFutura("Especifica");
            transferenciasInmediatas.clickContinuarGenerico();
            transferenciasInmediatas.clickConfirmarGenerico();
            dobleFactorSteps.codigoSegundoFactor();
            ticketSteps.validarTicket("Tu transaccion ha sido agendada");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Alta.class})
    @DisplayName("TC-0459-BSC: Transferencias Programadas (Fecha especifica) a cuentas propias otro banco $ - AGENDADOS")
    public void TC0459BSC() {
        try {
            //Precondición Agendar cuenta propia de otro banco
            loginSteps.iniciarSessionAlEscritorio(utranspull1);
            agendaNuevoDestinatarioSteps.adherirNuevoDestinatarioCBU("3300000620000244825007","PROPIA-BSF");
            //TEST
            menuLateralSteps.clickTransferencias("","CA $ 014208472");
            transferenciasInmediatas.seleccionarTipoTransferencia("Cuenta Propia");
            transferenciasInmediatas.seleccionarCuentaDestinoCuentasPropias("PROPIA-BSF");
            //transferenciasInmediatas.seleecionarTarjeaAOperar("9017");
            transferenciasInmediatas.seleccionarCuentaOrigen("CA $ 014208472");
            transferenciasInmediatas.setImporteGenerico("1");
            transferenciasInmediatas.seleccionarConcepto("Alquileres");
            transferenciasInmediatas.setDescripcion("TestAutomation");
            transferenciasInmediatas.transferenciaProgramadaPara();
            transferenciasInmediatas.seleccionFechaFutura("Especifica");
            transferenciasInmediatas.clickContinuarGenerico();
            transferenciasInmediatas.clickConfirmarGenerico();
            //dobleFactorSteps.codigoSegundoFactor(utranspull1);
            ticketSteps.validarTicket("Tu transaccion ha sido agendada");
            ticketSteps.clickVolverGenerico();
            transferenciasInmediatas.validarTituloPageGenerico("Transferencias");
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("3300000620000244825007");

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0485-BSC: Transferencias Programadas (Fecha especifica) ALIAS de CVU - terceros $ NO AGENDADOS")
    public void TC0485BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(u21829785);
            menuLateralSteps.clickTransferencias("","CA $ 850062210");
            transferenciasInmediatasSteps.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatasSteps.completarInputAliasCbu("BOTE.FILA.MITO");
            transferenciasInmediatasSteps.clickBuscarGenerico();
            transferenciasInmediatasSteps.botonSonCorrectos();
            transferenciasInmediatasSteps.setImporteGenerico("1");
            transferenciasInmediatasSteps.setDescripcion("TC0485");
            transferenciasInmediatas.transferenciaProgramadaPara();
            transferenciasInmediatas.seleccionFechaFutura("Especifica");
            transferenciasInmediatas.clickContinuarGenerico();
            transferenciasInmediatas.clickConfirmarGenerico();
            ticketSteps.validarTicket("Tu transaccion ha sido agendada");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Alta.class})
    @DisplayName("TC-0461-BSC: Transferencias Programadas (Fecha especifica) CBU a cuentas propias otro banco $ - NO AGENDADOS")
    public void TC0461BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(utranspull1);
            menuLateralSteps.clickTransferencias("","CA $ 014208472");
            transferenciasInmediatas.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatas.completarInputAliasCbu("3300000620000244825007");
            transferenciasInmediatas.clickBuscarGenerico();
            transferenciasInmediatas.botonSonCorrectos();
            transferenciasInmediatas.setImporteGenerico("1");
            transferenciasInmediatas.seleccionarConcepto("Alquileres");
            transferenciasInmediatas.setDescripcion("TestAutomation");
            transferenciasInmediatas.transferenciaProgramadaPara();
            transferenciasInmediatas.seleccionFechaFutura("Especifica");
            transferenciasInmediatas.clickContinuarGenerico();
            transferenciasInmediatas.clickConfirmarGenerico();
            dobleFactorSteps.codigoSegundoFactor();
            ticketSteps.validarTicket("Tu transaccion ha sido agendada");
            ticketSteps.clickVolverGenerico();
            transferenciasInmediatas.validarTituloPageGenerico("Transferencias");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0463-BSC: Transferencias Programadas (Fecha especifica) ALIAS a cuentas propias otro banco $ - NO AGENDADOS")
    public void TC0463BSC() {
        try {
            //TEST
            loginSteps.iniciarSessionAlEscritorio(utranspull1);
            menuLateralSteps.clickTransferencias("","CA $ 014208472");
            transferenciasInmediatas.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatas.completarInputAliasCbu("CATRE.PESO.cambio");
            transferenciasInmediatas.clickBuscarGenerico();
            transferenciasInmediatas.botonSonCorrectos();
            transferenciasInmediatas.setImporteGenerico("1");
            transferenciasInmediatas.seleccionarConcepto("Alquileres");
            transferenciasInmediatas.setDescripcion("TestAutomation");
            transferenciasInmediatas.transferenciaProgramadaPara();
            transferenciasInmediatas.seleccionFechaFutura("Especifica");
            transferenciasInmediatas.clickContinuarGenerico();
            transferenciasInmediatas.clickConfirmarGenerico();
            dobleFactorSteps.codigoSegundoFactor();
            ticketSteps.validarTicket("Tu transaccion ha sido agendada");
            ticketSteps.clickVolverGenerico();
            transferenciasInmediatas.validarTituloPageGenerico("Transferencias");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Alta.class})
    @DisplayName("TC-0472-BSC: Transferencias Programadas (Fecha especifica) CBU - terceros mismo banco  USD - NO AGENDADOS")
    public void TC0472BSC() {
        try {
            //TEST
            loginSteps.iniciarSessionAlEscritorio(uAndu0406);
            menuLateralSteps.clickTransferencias("3011","CA USD 503470069");
            transferenciasInmediatas.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatas.completarInputAliasCbu("0860060802020034435476");
            transferenciasInmediatas.clickBuscarGenerico();
            transferenciasInmediatas.botonSonCorrectos();
            transferenciasInmediatas.setImporteGenerico("1");
            transferenciasInmediatas.setDescripcion("TestAutomation");
            transferenciasInmediatas.transferenciaProgramadaPara();
            transferenciasInmediatas.seleccionFechaFutura("Especifica");
            transferenciasInmediatas.clickContinuarGenerico();
            transferenciasInmediatas.clickConfirmarGenerico();
            dobleFactorSteps.codigoSegundoFactor();
            ticketSteps.validarTicket("Tu transaccion ha sido agendada");
            ticketSteps.clickVolverGenerico();
            transferenciasInmediatas.validarTituloPageGenerico("Transferencias");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0473-BSC: Transferencias Programadas (Fecha especifica) CBU - terceros otros bancos $ - NO AGENDADOS")
    public void TC0473BSC() {
        try {
            //TEST
            loginSteps.iniciarSessionAlEscritorio(uAndu0406);
            menuLateralSteps.clickTransferencias("3011","CC $ 500652404");
            transferenciasInmediatas.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatas.completarInputAliasCbu("3300001320010001101040");
            transferenciasInmediatas.clickBuscarGenerico();
            transferenciasInmediatas.botonSonCorrectos();
            transferenciasInmediatas.setImporteGenerico("1");
            transferenciasInmediatas.setDescripcion("TestAutomation");
            transferenciasInmediatas.transferenciaProgramadaPara();
            transferenciasInmediatas.seleccionFechaFutura("Especifica");
            transferenciasInmediatas.clickContinuarGenerico();
            transferenciasInmediatas.clickConfirmarGenerico();
            dobleFactorSteps.codigoSegundoFactor();
            ticketSteps.validarTicket("Tu transaccion ha sido agendada");
            ticketSteps.clickVolverGenerico();
            transferenciasInmediatas.validarTituloPageGenerico("Transferencias");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Alta.class})
    @DisplayName("TC-0474-BSC: Transferencias Programadas (Fecha especifica) CBU - terceros otros bancos USD - NO AGENDADOS")
    public void TC0474BSC() {
        try {
            //TEST
            loginSteps.iniciarSessionAlEscritorio(uAndu0406);
            menuLateralSteps.clickTransferencias("3011","CA USD 503470069");
            transferenciasInmediatas.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatas.completarInputAliasCbu("3860001004000083891188");
            transferenciasInmediatas.clickBuscarGenerico();
            transferenciasInmediatas.botonSonCorrectos();
            transferenciasInmediatas.setImporteGenerico("1");
            transferenciasInmediatas.setDescripcion("TestAutomation");
            transferenciasInmediatas.transferenciaProgramadaPara();
            transferenciasInmediatas.seleccionFechaFutura("Especifica");
            transferenciasInmediatas.clickContinuarGenerico();
            transferenciasInmediatas.clickConfirmarGenerico();
            dobleFactorSteps.codigoSegundoFactor();
            ticketSteps.validarTicket("Tu transaccion ha sido agendada");
            ticketSteps.clickVolverGenerico();
            transferenciasInmediatas.validarTituloPageGenerico("Transferencias");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Baja.class})
    @DisplayName("TC-0475-BSC: Transferencias Programadas (Fecha especifica) CBU - terceros otros bancos INTERBANK (No PETERSEN) $ - NO AGENDADOS")
    public void TC0475BSC() {
        try {
            //TEST
            loginSteps.iniciarSessionAlEscritorio(uAndu0406);
            menuLateralSteps.clickTransferencias("3011","CC $ 500652404");
            transferenciasInmediatas.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatas.completarInputAliasCbu("0720000788000006008668");
            transferenciasInmediatas.clickBuscarGenerico();
            transferenciasInmediatas.botonSonCorrectos();
            transferenciasInmediatas.setImporteGenerico("1");
            transferenciasInmediatas.setDescripcion("TestAutomation");
            transferenciasInmediatas.transferenciaProgramadaPara();
            transferenciasInmediatas.seleccionFechaFutura("Especifica");
            transferenciasInmediatas.clickContinuarGenerico();
            transferenciasInmediatas.clickConfirmarGenerico();
            dobleFactorSteps.codigoSegundoFactor();
            ticketSteps.validarTicket("Tu transaccion ha sido agendada");
            ticketSteps.clickVolverGenerico();
            transferenciasInmediatas.validarTituloPageGenerico("Transferencias");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Baja.class})
    @DisplayName("TC-0476-BSC: Transferencias Programadas (Fecha especifica) CBU - terceros otros bancos INTERBANK (No PETERSEN) USD - NO AGENDADOS")
    public void TC0476BSC() {
        try {
            //TEST
            loginSteps.iniciarSessionAlEscritorio(uAndu0406);
            menuLateralSteps.clickTransferencias("3011","CA USD 503470069");
            transferenciasInmediatas.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatas.completarInputAliasCbu("0720000788000006008668");
            transferenciasInmediatas.clickBuscarGenerico();
            transferenciasInmediatas.botonSonCorrectos();
            transferenciasInmediatas.setImporteGenerico("1");
            transferenciasInmediatas.setDescripcion("TestAutomation");
            transferenciasInmediatas.transferenciaProgramadaPara();
            transferenciasInmediatas.seleccionFechaFutura("Especifica");
            transferenciasInmediatas.clickContinuarGenerico();
            transferenciasInmediatas.clickConfirmarGenerico();
            dobleFactorSteps.codigoSegundoFactor();
            ticketSteps.validarTicket("Tu transaccion ha sido agendada");
            ticketSteps.clickVolverGenerico();
            transferenciasInmediatas.validarTituloPageGenerico("Transferencias");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0477-BSC: Transferencias Programadas (Fecha especifica) ALIAS - terceros mismo banco  $ - NO AGENDADOS")
    public void TC0477BSC() {
        try {
            //TEST
            loginSteps.iniciarSessionAlEscritorio(uAndu0406);
            menuLateralSteps.clickTransferencias("3011","CC $ 500652404");
            transferenciasInmediatas.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatas.completarInputAliasCbu("CRUZ-NATER-3567");
            transferenciasInmediatas.clickBuscarGenerico();
            transferenciasInmediatas.botonSonCorrectos();
            transferenciasInmediatas.setImporteGenerico("1");
            transferenciasInmediatas.setDescripcion("TestAutomation");
            transferenciasInmediatas.transferenciaProgramadaPara();
            transferenciasInmediatas.seleccionFechaFutura("Especifica");
            transferenciasInmediatas.clickContinuarGenerico();
            transferenciasInmediatas.clickConfirmarGenerico();
            dobleFactorSteps.codigoSegundoFactor();
            ticketSteps.validarTicket("Tu transaccion ha sido agendada");
            ticketSteps.clickVolverGenerico();
            transferenciasInmediatas.validarTituloPageGenerico("Transferencias");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Alta.class})
    @DisplayName("TC-0478-BSC: Transferencias Programadas (Fecha especifica) ALIAS - terceros mismo banco  USD - NO AGENDADOS")
    public void TC0478BSC() {
        try {
            //TEST
            loginSteps.iniciarSessionAlEscritorio(uAndu0406);
            menuLateralSteps.clickTransferencias("3011","CA USD 503470069");
            transferenciasInmediatas.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatas.completarInputAliasCbu("MONTE-LAGO");
            transferenciasInmediatas.clickBuscarGenerico();
            transferenciasInmediatas.botonSonCorrectos();
            transferenciasInmediatas.setImporteGenerico("1");
            transferenciasInmediatas.setDescripcion("TestAutomation");
            transferenciasInmediatas.transferenciaProgramadaPara();
            transferenciasInmediatas.seleccionFechaFutura("Especifica");
            transferenciasInmediatas.clickContinuarGenerico();
            transferenciasInmediatas.clickConfirmarGenerico();
            dobleFactorSteps.codigoSegundoFactor();
            ticketSteps.validarTicket("Tu transaccion ha sido agendada");
            ticketSteps.clickVolverGenerico();
            transferenciasInmediatas.validarTituloPageGenerico("Transferencias");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Alta.class})
    @DisplayName("TC-0479-BSC: Transferencias Programadas (Fecha especifica) ALIAS - terceros otros bancos $ - NO AGENDADOS")
    public void TC0479BSC() {
        try {
            //TEST
            loginSteps.iniciarSessionAlEscritorio(uAndu0406);
            menuLateralSteps.clickTransferencias("3011","CC $ 500652404");
            transferenciasInmediatas.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatas.completarInputAliasCbu("LARGO.CELTA.PAREJA");
            transferenciasInmediatas.clickBuscarGenerico();
            transferenciasInmediatas.botonSonCorrectos();
            transferenciasInmediatas.setImporteGenerico("1");
            transferenciasInmediatas.setDescripcion("TestAutomation");
            transferenciasInmediatas.transferenciaProgramadaPara();
            transferenciasInmediatas.seleccionFechaFutura("Especifica");
            transferenciasInmediatas.clickContinuarGenerico();
            transferenciasInmediatas.clickConfirmarGenerico();
            dobleFactorSteps.codigoSegundoFactor();
            ticketSteps.validarTicket("Tu transaccion ha sido agendada");
            ticketSteps.clickVolverGenerico();
            transferenciasInmediatas.validarTituloPageGenerico("Transferencias");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Media.class})
    @DisplayName("TC-0480-BSC: Transferencias Programadas (Fecha especifica) ALIAS - terceros otros bancos USD - NO AGENDADOS")
    public void TC0480BSC() {
        try {
            //TEST
            loginSteps.iniciarSessionAlEscritorio(uAndu0406);
            menuLateralSteps.clickTransferencias("3011","CA USD 503470069");
            transferenciasInmediatas.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatas.completarInputAliasCbu("PAREJA.CELTA.MOTO");
            transferenciasInmediatas.clickBuscarGenerico();
            transferenciasInmediatas.botonSonCorrectos();
            transferenciasInmediatas.setImporteGenerico("1");
            transferenciasInmediatas.setDescripcion("TestAutomation");
            transferenciasInmediatas.transferenciaProgramadaPara();
            transferenciasInmediatas.seleccionFechaFutura("Especifica");
            transferenciasInmediatas.clickContinuarGenerico();
            transferenciasInmediatas.clickConfirmarGenerico();
            dobleFactorSteps.codigoSegundoFactor();
            ticketSteps.validarTicket("Tu transaccion ha sido agendada");
            ticketSteps.clickVolverGenerico();
            transferenciasInmediatas.validarTituloPageGenerico("Transferencias");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @After
    public void cerrarSesionCapturarUPS(){
        if(utilsGP.estaPresente(botonesGenericosPage.getCodigoUps(),2) && botonesGenericosPage.getCodigoUps().getText().startsWith("#")) {
            String ups = botonesGenericosPage.getCodigoUps().getText();
            botonesGenericosSteps.clickVolverGenerico();
            loginSteps.clickSalirDelaSesion();
            loginSteps.waitGP();
            Assert.fail("UPS CODIGO : " + ups);
        }else if(utilsGP.estaPresente(botonesGenericosPage.getSalirSesion(),2))
        {
            loginSteps.clickSalirDelaSesion();
            loginSteps.waitGP();
        }else{
            System.out.println("=================== NO PUDO INICIAR SESION REFRESCAR NAVEGADOR ==============================");
            Allure.addAttachment("INICIO SESSION,","=================== NO PUDO INICIAR SESION REFRESCAR NAVEGADOR ==============================");
            global.practia.jemeinsamstark.web.core.WebDriver.getInstance().navigate().to(System.getProperty("bank"));
            System.out.println("Se Espera que aparezca la pagina de nuevo");
            loginSteps.waitGP();
        }
    }
    @AfterClass
    public static void teardown() throws IOException, InterruptedException {
        global.practia.jemeinsamstark.web.core.WebDriver.closeDriver();
        cerrarConexion();
    }
}
