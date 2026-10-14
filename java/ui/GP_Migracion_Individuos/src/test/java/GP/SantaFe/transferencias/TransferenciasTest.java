
package GP.SantaFe.transferencias;

import GP.Tags.*;
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
import utils.MetodosAPI;
import utils.TestCaseData;
import utils.UtilsGP;
import org.junit.BeforeClass;
import java.io.IOException;
import java.util.LinkedHashMap;

import static db.ConexionDB.cerrarConexion;
import static utils.Constantes.*;

public class TransferenciasTest {
    LoginSteps loginSteps = new LoginSteps();
    BotonesGenericosSteps botonesGenericosSteps = new BotonesGenericosSteps();
    MenuLateralSteps menuLateralSteps = new MenuLateralSteps();
    TransferenciasSteps transferenciasSteps = new TransferenciasSteps();
    ConsultaCredinSteps consultaCredinSteps = new ConsultaCredinSteps();
    ValidacionDeIdentidadSteps validacionDeIdentidadSteps = new ValidacionDeIdentidadSteps();
    GenerarCredinSteps generarCredinSteps = new GenerarCredinSteps();
    DobleFactorSteps dobleFactorSteps = new DobleFactorSteps();
    TicketSteps ticketSteps = new TicketSteps();
    CuentasSteps cuentasSteps = new CuentasSteps();
    TransferenciasInmediatasSteps transferenciasInmediatas = new TransferenciasInmediatasSteps();
    AgendaNuevoDestinatarioSteps agendaNuevoDestinatarioSteps = new AgendaNuevoDestinatarioSteps();
    BotonesGenericosPage botonesGenericosPage = new BotonesGenericosPage();
    CalendarioSteps calendarioSteps = new CalendarioSteps();
    UtilsGP utilsGP = new UtilsGP();
    LinkedHashMap<String,String> usuario;
    MetodosAPI metodosAPI = new MetodosAPI();
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
    @DisplayName("TC-0007-BSF: Realizar una transferencia en pesos Interbancaria - A Terceros de distinto banco -  ingresando los datos requeridos haciendo uso de plantillas guardadas")
    public void TC0007BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uPerezR);
            menuLateralSteps.clickCuentas();
            cuentasSteps.clickMasOpcionesGenerico();
            transferenciasSteps.clickTransferenciasProgramadas();
            transferenciasInmediatas.clickTarjetaDeDebito();
            transferenciasInmediatas.keyDownToText("9008");
            transferenciasInmediatas.clickContinuarGenerico();
            transferenciasInmediatas.clickListadoDePlantillas();
            transferenciasInmediatas.clickPlantillaPorNombre("test007");
            transferenciasInmediatas.clickContinuarGenerico();
            transferenciasInmediatas.clickBotonConfimar();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0008-BSF: Transferencia Intrabancaria en Pesos - Propias (del mismo banco) Cargada Manualmente - Programada en Fecha Futura Botón Volver")
    public void TC0008BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uTranspull4);
            menuLateralSteps.clickTransferencias("","");
            transferenciasInmediatas.seleccionarTipoTransferencia("Cuenta Propia");
            transferenciasInmediatas.seleccionarCuentaDestinoCuentasPropias("CC $ 000021333202");
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
    @DisplayName("TC-0014-BSF: Generar CREDIN - Cuenta propia $ (mismo Banco u otro) - Mas de una cuenta Debito - CBU - Segundo factor por SMS")
    public void TC0014BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uTranspull1);
            menuLateralSteps.clickTransferencias("","");
            transferenciasSteps.clickMasOpcionesGenerico();
            transferenciasSteps.clickConsultarCredin();
            consultaCredinSteps.clickGenerarCredin();
            generarCredinSteps.clickTarjetaDebito();
            generarCredinSteps.keyDownToText("3004");
            generarCredinSteps.clickCuentaDebito();
            generarCredinSteps.keyDownXParametrosGenerico(0, "Caja de Ahorro $ 000024482500");
            generarCredinSteps.clickCBU();
            generarCredinSteps.setCampoAliasCbu("3300000610000244827075");
            generarCredinSteps.clickSelectorConcepto();
            generarCredinSteps.keyDownXParametrosGenerico(0,"Alquileres");
            generarCredinSteps.setImporteGenerico("1");
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
    @DisplayName("TC-0139-BSF: Transferencias Inmediatas - Entre cuentas propias - Flujo completo- Validar ticket con exito")
    public void TC0139BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uTranspull1);
            menuLateralSteps.clickTransferencias("","CA $ 000024482500");
            transferenciasInmediatas.seleccionarTipoTransferencia("Cuenta Propia");
            transferenciasInmediatas.seleccionarCuentaDestinoCuentasPropias("CC $ 520000334707");
            transferenciasInmediatas.seleccionarCuentaOrigen("CA $ 000024482500");
            transferenciasInmediatas.setImporteGenerico("100");
            transferenciasInmediatas.seleccionarConcepto("Alquileres");
            transferenciasInmediatas.setDescripcion("prueba");
            transferenciasInmediatas.clickBotonTransferirAhora();
            transferenciasInmediatas.clickConfirmarGenerico();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            //POSTCONDICION
            transferenciasInmediatas.verificarDescuentoCuentaOrigenTransferencia();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    //@Test // ya no se encuentra como campo obligatorio
    @DisplayName("TC-0140-BSF: Transferencias Inmediatas - Validar campo obligatorio Tipo de transferencia")
    public void TC0140BSF() {
        try {
            loginSteps.iniciarSesion(uPerezR);
            menuLateralSteps.clickTransferencias("","");
            transferenciasSteps.clickTransferenciasInmediatas();
            transferenciasInmediatas.clickTarjetaDeDebito();
            transferenciasInmediatas.keyDownToText("6004");
            transferenciasInmediatas.clickContinuarGenerico();
            transferenciasInmediatas.clickCuentaOrigen();
            transferenciasInmediatas.keyDownToText("4209");
            transferenciasInmediatas.clickCuentaDestino();
            transferenciasInmediatas.keyDownToText("CA $ 000000316410");
            transferenciasInmediatas.setImporteGenerico("100");
            transferenciasInmediatas.seleccionarConcepto("Alquileres");
            transferenciasInmediatas.setDescripcion("prueba");
            transferenciasInmediatas.clickContinuarGenerico();
            transferenciasInmediatas.validarMsjeCampoObligatorio();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Baja.class})
    @DisplayName("TC-0141-BSF: Transferencias Inmediatas - Validar campo obligatorio Importe")
    public void TC0141BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uPerezR);
            menuLateralSteps.clickTransferencias("","");
            transferenciasInmediatas.seleccionarTipoTransferencia("Cuenta Propia");
            transferenciasInmediatas.seleccionarCuentaOrigen("CC $ 001000090502");
            transferenciasInmediatas.seleccionarCuentaDestinoCuentasPropias("CA $ 000003554003");
            transferenciasInmediatas.clickConcepto();
            transferenciasInmediatas.keyDownToText("Alquileres");
            transferenciasInmediatas.setDescripcion("Automation");
            transferenciasInmediatas.validarBotonTransferirDeshabilitado();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    //@Test //el campo concepto se encuentra seleccionado por defecto
    @DisplayName("TC-0142-BSF: Transferencias Inmediatas - Validar campo obligatorio Concepto")
    public void TC0142BSF() {
        try {
            loginSteps.iniciarSesion(uPerezR);
            menuLateralSteps.clickTransferencias("","");
            transferenciasSteps.clickTransferenciasInmediatas();
            transferenciasInmediatas.clickTarjetaDeDebito();
            transferenciasInmediatas.keyDownToText("6004");
            transferenciasInmediatas.clickContinuarGenerico();
            transferenciasInmediatas.clickCuentaOrigen();
            transferenciasInmediatas.keyDownToText("4209");
            transferenciasInmediatas.clickCuentaDestino();
            transferenciasInmediatas.keyDownToText("CA $ 000000316410");
            transferenciasInmediatas.setImporteGenerico("100");
            transferenciasInmediatas.setDescripcion("prueba");
            transferenciasInmediatas.clickContinuarGenerico();
            transferenciasInmediatas.validarMsjeCampoObligatorio();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test //CAMPO OPCIONAL
    @Category({Baja.class})
    @DisplayName("TC-0143-BSF: Transferencias Inmediatas - Validar campo obligatorio Referencia")
    public void TC0143BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uPerezR);
            menuLateralSteps.clickTransferencias("","");
            transferenciasInmediatas.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatas.completarInputAliasCbu("GLOBO.FIESTA.PAZ");
            transferenciasInmediatas.clickBuscarGenerico();
            transferenciasInmediatas.botonSonCorrectos();
            transferenciasInmediatas.setImporteGenerico("100");
            transferenciasInmediatas.clickBotonTransferirAhora();
            transferenciasInmediatas.clickConfirmarGenerico();
            //dobleFactorSteps.codigoSegundoFactorSinValidacion(uPerezR);
            transferenciasInmediatas.validarMsjeReferenciaVacia();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Baja.class})
    @DisplayName("TC-0144-BSF: Transferencias Inmediatas - Transferencia Cuentas propias con MISMA cuenta origen y cuenta destino- Validar mensaje de cuentas iguales")
    public void TC0144BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uPerezR);
            menuLateralSteps.clickTransferencias("","");
            transferenciasInmediatas.seleccionarTipoTransferencia("Cuenta Propia");
            transferenciasInmediatas.seleccionarCuentaDestinoCuentasPropias("CC $ 001000090502");
            transferenciasInmediatas.setImporteGenerico("1");
            transferenciasInmediatas.seleccionarConcepto("Alquileres");
            transferenciasInmediatas.clickBotonTransferirAhora();
            transferenciasInmediatas.validaNotificacionEmergenteGenerico("La cuenta destino no puede ser la misma que la cuenta origen");
            //transferenciasInmediatas.seleccionarMismaCuenta();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0145-BSF: Transferencias - Añadir nuevo destinatario - Con cbu valido")
    public void TC0145BSF() {
        try {
            //PRECONDICION
            testCaseData.cargaDeDatosTest(uTranspull1);
            loginSteps.iniciarSessionAlEscritorioConPass(uTranspull1, testCaseData.password);
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("3300000620000125788076");
            //TEST
            menuLateralSteps.clickTransferencias("","");
            transferenciasInmediatas.seleccionarTipoTransferencia("Contacto Agendado");
            transferenciasInmediatas.clickAgendaDestinatario();
            agendaNuevoDestinatarioSteps.clickAdherirNuevoDestinatario();
            agendaNuevoDestinatarioSteps.setInputCbuAlias("3300000620000125788076");
            agendaNuevoDestinatarioSteps.clickBuscar();
            agendaNuevoDestinatarioSteps.validarDatosCuenta("3300000620000125788076", "VOLPATO, MONICA LILIANA A", "27162670390");
            agendaNuevoDestinatarioSteps.setPerteneceA("PruebaAutomation");
            transferenciasInmediatas.botonSonCorrectos();
            dobleFactorSteps.codigoSegundoFactor();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            agendaNuevoDestinatarioSteps.clickVolverGenerico();
            //agendaNuevoDestinatarioSteps.eliminarContactoAgendado("PruebaAutomation");
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("3300000620000125788076");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Alta.class})
    @DisplayName("TC-0146-BSF: Transferencias - Añadir nuevo destinatario - Con cbu invalido - Validar mensaje de error")
    public void TC0146BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uTranspull4);
            menuLateralSteps.clickTransferencias("","");
            transferenciasInmediatas.seleccionarTipoTransferencia("Contacto Agendado");
            transferenciasInmediatas.clickAgendaDestinatario();
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
    @DisplayName("TC-0147-BSF: Transferencias - Añadir nuevo destinatario - Con alias valido- Validar datos de cuenta")
    public void TC0147BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uPrueba2734);
            menuLateralSteps.clickTransferencias("","");
            transferenciasInmediatas.seleccionarTipoTransferencia("Contacto Agendado");
            transferenciasInmediatas.clickAgendaDestinatario();
            agendaNuevoDestinatarioSteps.clickAdherirNuevoDestinatario();
            agendaNuevoDestinatarioSteps.clickLabelAlias();
            agendaNuevoDestinatarioSteps.setInputCbuAlias("OTRONOMBRE");
            agendaNuevoDestinatarioSteps.clickBuscar();
            agendaNuevoDestinatarioSteps.validarDatosCuenta("3300000620000125788076", "VOLPATO, MONICA LILIANA A", "27162670390");
            agendaNuevoDestinatarioSteps.setPerteneceA("PruebaAutomation");
            agendaNuevoDestinatarioSteps.clickContinuarGenerico();
            dobleFactorSteps.codigoSegundoFactor();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            agendaNuevoDestinatarioSteps.clickVolverGenerico();
            //POSTCONDICION
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("OTRONOMBRE");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Alta.class})
    @DisplayName("TC-0148-BSF: Transferencias - Añadir nuevo destinatario - Con alias invalido - Validar mensaje de error")
    public void TC0148BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uTranspull4);
            menuLateralSteps.clickTransferencias("","");
            transferenciasInmediatas.seleccionarTipoTransferencia("Contacto Agendado");
            transferenciasInmediatas.clickAgendaDestinatario();
            agendaNuevoDestinatarioSteps.clickAdherirNuevoDestinatario();
            agendaNuevoDestinatarioSteps.clickLabelAlias();
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
    @DisplayName("TC-0344- BSF: TRANSFERENCIAS - A cuentas de terceros ALIAS - otros bancos - Pesos")
    public void TC0344BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uTranspull1);
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("PERLA.ROCA.ARBOL");
            //TEST
            menuLateralSteps.clickTransferencias("","CA $ 000024482500");
            transferenciasInmediatas.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatas.completarInputAliasCbu("PERLA.ROCA.ARBOL");
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
    @DisplayName("TC-0345- BSF: Transferencias - Verificar Mis transferencias y validar historial")
    public void TC0345BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uVolpatomonica);
            menuLateralSteps.clickTransferencias("","");
            transferenciasInmediatas.misTransferencias();
            transferenciasInmediatas.validarTituloPageGenerico("Listado de transferencias");
            transferenciasInmediatas.desplegableCuenta();
            transferenciasInmediatas.keyDownToText("CA $ 000012578807");
            transferenciasInmediatas.clickContinuarGenerico();
            transferenciasInmediatas.todosLosMovimientos();
            transferenciasInmediatas.validarHistorial();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Criticos.class})
    @DisplayName("TC-12930-BSF: TRANSFERENCIAS - A cuentas de terceros CBU - mismo banco - Pesos")
    public void TC12930BSF() {
        try {
            //PRECONDICION
            loginSteps.iniciarSessionAlEscritorio(uTranspull1);
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("3300000610000090000011");
            //TEST
            menuLateralSteps.clickTransferencias("","CA $ 000024482500");
            transferenciasInmediatas.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatas.completarInputAliasCbu("3300000610000090000011");
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
    @Category({Alta.class})
    @DisplayName("TC-12931-BSF: TRANSFERENCIAS - A cuentas de terceros CBU - otros bancos - Pesos")
    public void TC12931BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uTranspull1);
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("3860020101000061733757");
            //TEST
            menuLateralSteps.clickTransferencias("","CA $ 000024482500");
            transferenciasInmediatas.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatas.completarInputAliasCbu("3860020101000061733757");
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
    @Category({Criticos.class})
    @DisplayName("TC-12932-BSF: TRANSFERENCIAS - A cuentas de terceros ALIAS - mismo banco - Pesos")
    public void TC12932BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uTranspull1);
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("CONDE.TRIGO.AMIGO");

            menuLateralSteps.clickTransferencias("","CA $ 000024482500");
            transferenciasInmediatas.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatas.completarInputAliasCbu("CONDE.TRIGO.AMIGO");
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
    @DisplayName("TC-12934-BSF: TRANSFERENCIAS - A cuentas de terceros CBU en dólares USD")
    public void TC12934BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uTranspull1);
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("3300520945200000502031");
            //TEST
            menuLateralSteps.clickTransferencias("","CA USD 000024482609");
            transferenciasInmediatas.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatas.completarInputAliasCbu("3300520945200000502031");
            transferenciasInmediatas.clickBuscarGenerico();
            transferenciasInmediatas.botonSonCorrectos();
            transferenciasInmediatas.setImporteGenerico("1");
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
    @Category({Criticos.class})
    @DisplayName("TC-12935-BSF: Transferencias Programadas (Fecha especifica) CBU - terceros mismo banco  $ - NO AGENDADOS")
    public void TC12935BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uTranspull1);
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("3300001320010001101040");
            //TEST
            menuLateralSteps.clickTransferencias("","CA $ 000024482500");
            transferenciasInmediatas.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatas.completarInputAliasCbu("3300001320010001101040");
            transferenciasInmediatas.clickBuscarGenerico();
            transferenciasInmediatas.botonSonCorrectos();
            transferenciasInmediatas.setImporteGenerico("100");
            transferenciasInmediatas.seleccionarConcepto("Alquileres");
            transferenciasInmediatas.setDescripcion("automation");
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
    @Category({Criticos.class})
    @DisplayName("TC-12969-BSF: TRANSFERENCIAS - A cuentas de terceros CVU - otros bancos - Pesos")
    public void TC12969BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uTranspull1);
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("0000001700300000000313");
            //TEST
            menuLateralSteps.clickTransferencias("","CA $ 000024482500");
            //transferenciasInmediatas.seleccionarCuentaOrigen("CA $ 000024482500");
            transferenciasInmediatas.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatas.completarInputAliasCbu("0000001700300000000313");
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
    @Category({Criticos.class})
    @DisplayName("TC-12981-BSF: Transferencias - Eliminar destinatario de agenda")
    public void TC12981BSF() {
        try {
            //PRECONDICION
            testCaseData.cargaDeDatosTest(uVolpatomonica);
            loginSteps.iniciarSessionAlEscritorioConPass(uVolpatomonica,testCaseData.password);
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
    @Test
    @Category({Criticos.class})
    @DisplayName("TC-12938-BSF: TRANSFERENCIAS - Generar CREDIN - Dolares")
    public void TC12938BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uTranspull1);
            menuLateralSteps.clickTransferencias("","");
            transferenciasInmediatas.clickMasOpcionesGenerico();
            transferenciasSteps.clickConsultarCredin();
            consultaCredinSteps.clickGenerarCredin();
            generarCredinSteps.clickCuentaDebito();
            generarCredinSteps.keyDownXParametrosGenerico(2, "USD");
            generarCredinSteps.clickCBU();
            generarCredinSteps.setCampoAliasCbu("3300000640000010329025");
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
    @DisplayName("TC-12936 - BSF: TRANSFERENCIAS - Consulta de transferencias realizadas")
    public void TC12936BSF() {
        try {
            //CASO
            loginSteps.iniciarSessionAlEscritorio(uSantafe2024);
            menuLateralSteps.clickTransferencias("","");
            transferenciasInmediatas.misTransferencias();
            transferenciasInmediatas.desplegableCuenta();
            transferenciasInmediatas.keyDownToText("CA $ 000098735704");
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

    @Test
    @Category({Criticos.class})
    @DisplayName("TC-12939-BSF: TRANSFERENCIAS - Consultar CREDIN Originante")
    public void TC12939BSF() {
        try {
            //Precondicion: Generar Credin
            loginSteps.iniciarSessionAlEscritorio(uTranspull1);
            generarCredinSteps.generarCredinApi("3300000620000244825007","3300520915200003347070","3004");
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
            consultaCredinSteps.setCuitDestinatario("20127315052");
            consultaCredinSteps.clickEstado();
            consultaCredinSteps.keyDownXParametrosGenerico(6, "Todos");
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
    @DisplayName("TC-12940-BSF: TRANSFERENCIAS - Consultar CREDIN destinatario")
    public void TC12940BSF() {
        try {
            //Precondicion: Generar Credin
            loginSteps.loginForAPI(uAgustina2014);
            generarCredinSteps.generarCredinApi("3300520915200003347070","3300000620000244825007","0002");
            //Test: Consulta de Credin
            loginSteps.iniciarSessionAlEscritorio(uTranspull1);
            menuLateralSteps.clickTransferencias("","");
            transferenciasInmediatas.clickMasOpcionesGenerico();
            transferenciasSteps.clickConsultarCredin();
            calendarioSteps.clickEnFechaDeCreacionDesde();
            calendarioSteps.clickDiaActual();
            calendarioSteps.clickEnFechaDeCreacionHasta();
            calendarioSteps.clickDiaActual();
            consultaCredinSteps.clickDestinatario();
            consultaCredinSteps.clickCuitOriginante();
            consultaCredinSteps.setCuitOriginante("23244825664");
            consultaCredinSteps.clickEstado();
            consultaCredinSteps.keyDownXParametrosGenerico(6, "Todos");
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
    @DisplayName("TC-14250-BSF: Transferencias CBU a cuentas propias otro banco $ - NO AGENDADOS")
    public void TC14250BSF(){
        try{
            //CASO
            loginSteps.iniciarSessionAlEscritorio(uTranspull1);
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("3860024901000035272026");
            //TEST
            menuLateralSteps.clickTransferencias("","CA $ 000024482500");
            transferenciasInmediatas.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatas.completarInputAliasCbu("3860024901000035272026");
            transferenciasInmediatas.clickBuscarGenerico();
            transferenciasInmediatas.botonSonCorrectos();
            transferenciasInmediatas.setImporteGenerico("1");
            transferenciasInmediatas.seleccionarConcepto("Alquileres");
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
    @DisplayName("TC-0409-BFS: TRANSFERENCIAS - terceros mismo banco  $ - AGENDADOS")
    public void TC0409BSF(){
        try{
            //PRECONDICION
            loginSteps.iniciarSessionAlEscritorio(uAbramcelia);
            agendaNuevoDestinatarioSteps.adherirNuevoDestinatarioCBU("3300520915200000746085","TC0409BSF");
            //TEST
            menuLateralSteps.clickTransferencias("5009","CA $ 000000870510");
            transferenciasInmediatas.seleccionarTipoTransferencia("Contacto Agendado");
            transferenciasInmediatas.clickAgendaDestinatario();
            transferenciasInmediatas.transferirAunContacto("TC0409BSF");
            transferenciasInmediatas.setImporteGenerico("1");
            transferenciasInmediatas.seleccionarConcepto("Alquileres");
            transferenciasInmediatas.clickBotonTransferirAhora();
            transferenciasInmediatas.clickConfirmarGenerico();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            //POSTCONDICION
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("3300520915200000746085");
            transferenciasInmediatas.verificarDescuentoCuentaOrigenTransferencia();

        }catch (Exception ex){
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Alta.class})
    @DisplayName("TC-0403-BFS: Transferencias a cuentas propias otro banco (GRUPO) $ - AGENDADOS")
    public void TC0403BSF(){
        try{
            String cuentaOrigen="CC $ 520000334707";
            //PRECONDICION - AGENDAR CONTACTO
            loginSteps.iniciarSessionAlEscritorio(uAgustina2014);
            agendaNuevoDestinatarioSteps.adherirNuevoDestinatarioCBU("3860024901000035272026","PROPIA-BSF");
            //TEST
            menuLateralSteps.clickTransferencias("",cuentaOrigen);
            transferenciasInmediatas.seleccionarTipoTransferencia("Cuenta Propia");
            transferenciasInmediatas.seleccionarCuentaDestinoCuentasPropias("PROPIA-BSF");
            transferenciasInmediatas.setImporteGenerico("1");
            transferenciasInmediatas.seleccionarConcepto("Alquiler");
            transferenciasInmediatas.setDescripcion("TestAutomation");
            transferenciasInmediatas.clickBotonTransferirAhora();
            transferenciasInmediatas.clickConfirmarGenerico();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            //POSTCONDICION - ELIMINAR CONTACTO
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("3860024901000035272026");
            transferenciasInmediatas.verificarDescuentoCuentaOrigenTransferencia();

        }catch (Exception ex){
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0407-BFS: Transferencias ALIAS a cuentas propias otro banco $ - NO AGENDADOS")
    public void TC0407BSF(){
        try{
            String cuentaOrigen="CC $ 520000334707";
            //PRECONDICION
            loginSteps.iniciarSessionAlEscritorio(uAgustina2014);
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("FLECO.GENIO.CELTA");
            //TEST
            menuLateralSteps.clickTransferencias("",cuentaOrigen);
            transferenciasInmediatas.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatas.completarInputAliasCbu("FLECO.GENIO.CELTA");
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

    //@Test
    @Category({Alta.class})
    @DisplayName("TC-0402-BFS: Transferencias entre cuentas propias mismo banco USD")
    public void TC0402BSF(){
        try{
            usuario = UtilsGP.consultaDBsqlTag(uTranspull1USD);
            loginSteps.iniciarSessionAlEscritorio(usuario.get("nombre"));
            menuLateralSteps.clickTransferencias("",usuario.get("cuentaOrigen"));
            transferenciasInmediatas.seleccionarTipoTransferencia("Cuenta Propia");
            transferenciasInmediatas.seleccionarCuentaDestinoCuentasPropias(usuario.get("cuentaDestino"));
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

    @Test
    @Category({Baja.class})
    @DisplayName("TC-0419-BFS: Transferencias CBU - terceros otros bancos INTERBANK (No PETERSEN) $ - NO AGENDADOS")
    public void TC0419BSF(){
        try{
            String cuentaOrigen = "CA $ 000000870510";
            String cbu="0720000788000006008668";
            String tarjetaAOperar="5009";
            //PRECONDICION
            loginSteps.iniciarSessionAlEscritorio(uAbramcelia);
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI(cbu);
            //TEST
            menuLateralSteps.clickTransferencias(tarjetaAOperar,cuentaOrigen);
            transferenciasInmediatas.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatas.completarInputAliasCbu(cbu);
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
    @Category({Baja.class})
    @DisplayName("TC-0420-BFS: Transferencias CBU - terceros otros bancos INTERBANK (No PETERSEN) USD - NO AGENDADOS")
    public void TC0420BSF(){
        try{
            String usuario = uTranspull1;
            String cuentaOrigen="CA USD 000024482609";
            String cbu="0720000788000006008668";
            //TEST
            loginSteps.iniciarSessionAlEscritorio(usuario);
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI(cbu);
            menuLateralSteps.clickTransferencias("",cuentaOrigen);
            transferenciasInmediatas.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatas.completarInputAliasCbu(cbu);
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
    @DisplayName("TC-0418-BFS: Transferencias CBU - terceros otros bancos USD - NO AGENDADOS")
    public void TC0418BSF(){
        try{
            String usuario = uTranspull1;
            String cuentaOrigen="CA USD 000024482609";
            String cbu="0450500602020050105885";
            //PRE
            loginSteps.iniciarSessionAlEscritorio(usuario);
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI(cbu);
            //TEST
            menuLateralSteps.clickTransferencias("",cuentaOrigen);
            transferenciasInmediatas.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatas.completarInputAliasCbu(cbu);
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
    @DisplayName("TC-0424-BFS: Transferencias ALIAS - terceros otros bancos USD - NO AGENDADOS")
    public void TC0424BSF(){
        try{
            String usuario = uTranspull1;
            String cuentaOrigen="CA USD 000024482609";
            String alias="PASTA.PARMA.PARLA";
            //TEST
            loginSteps.iniciarSessionAlEscritorio(usuario);
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI(alias);
            //TEST
            menuLateralSteps.clickTransferencias("","CA USD 000024482609");
            transferenciasInmediatas.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatas.completarInputAliasCbu(alias);
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
    @DisplayName("TC-0422-BFS: Transferencias ALIAS - terceros mismo banco  USD - NO AGENDADOS")
    public void TC0422BSF(){
        try{
            String usuario = uTranspull1;
            String cuentaOrigen="CA USD 000024482609";
            String alias="TEST.CALL";
            //TEST
            loginSteps.iniciarSessionAlEscritorio(usuario);
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI(alias);
            //TEST
            menuLateralSteps.clickTransferencias("","CA USD 000024482609");
            transferenciasInmediatas.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatas.completarInputAliasCbu(alias);
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

    //@Test
    @Category({Media.class})
    @DisplayName("TC-0458-BFS: Transferencias Programadas (Fecha especifica) entre cuentas propias mismo banco USD")
    public void TC0458BSF() {
        try {
            String usuario=uTranspull1;
            String cuentaOrigen="CA USD 000024482609";
            String cuentaDestino="CA USD 520000334500";
            loginSteps.iniciarSessionAlEscritorio(usuario);
            menuLateralSteps.clickTransferencias("",cuentaOrigen);
            transferenciasInmediatas.seleccionarTipoTransferencia("Cuenta Propia");
            transferenciasInmediatas.seleccionarCuentaDestinoCuentasPropias(cuentaDestino);
            transferenciasInmediatas.setImporteGenerico("1");
            transferenciasInmediatas.seleccionarConcepto("Alquileres");
            transferenciasInmediatas.setDescripcion("TestAutomation");
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
    @Category({Alta.class})
    @DisplayName("TC-0459-BFS: Transferencias Programadas (Fecha especifica) a cuentas propias otro banco $ - AGENDADOS")
    public void TC0459BSF() {
        try {
            String usuario= uAgustina2014;
            String cuentaOrigen="CC $ 520000334707";
            String cbu="3860024901000035272026";
            //Precondición Agendar cuenta propia de otro banco
            loginSteps.iniciarSessionAlEscritorio(usuario);
            agendaNuevoDestinatarioSteps.adherirNuevoDestinatarioCBU(cbu,"PROPIA-BERSA");
            //TEST
            menuLateralSteps.clickTransferencias("",cuentaOrigen);
            transferenciasInmediatas.seleccionarTipoTransferencia("Cuenta Propia");
            transferenciasInmediatas.seleccionarCuentaDestinoCuentasPropias("PROPIA-BERSA");
            transferenciasInmediatas.setImporteGenerico("1");
            transferenciasInmediatas.seleccionarConcepto("Alquileres");
            transferenciasInmediatas.setDescripcion("TestAutomation");
            transferenciasInmediatas.transferenciaProgramadaPara();
            transferenciasInmediatas.seleccionFechaFutura("Especifica");
            transferenciasInmediatas.clickContinuarGenerico();
            transferenciasInmediatas.clickConfirmarGenerico();
            ticketSteps.validarTicket("Tu transaccion ha sido agendada");
            ticketSteps.clickVolverGenerico();
            transferenciasInmediatas.validarTituloPageGenerico("Transferencias");
            //PostCondición
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI(cbu);
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Alta.class})
    @DisplayName("TC-0461-BFS: Transferencias Programadas (Fecha especifica) CBU a cuentas propias otro banco $ - NO AGENDADOS")
    public void TC0461BSF() {
        try {
            String usuario= uAgustina2014;
            String cuentaOrigen="CC $ 520000334707";
            String cbu="3860024901000035272026";
            //TEST
            loginSteps.iniciarSessionAlEscritorio(usuario);
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI(cbu);
            //TEST
            menuLateralSteps.clickTransferencias("",cuentaOrigen);
            transferenciasInmediatas.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatas.completarInputAliasCbu(cbu);
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
    @DisplayName("TC-0463-BFS: Transferencias Programadas (Fecha especifica) ALIAS a cuentas propias otro banco $ - NO AGENDADOS")
    public void TC0463BSF() {
        try {
            String usuario= uAgustina2014;
            String cuentaOrigen="CC $ 520000334707";
            String alias="FLECO.GENIO.CELTA";
            //TEST
            loginSteps.iniciarSessionAlEscritorio(usuario);
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI(alias);
            //TEST
            menuLateralSteps.clickTransferencias("",cuentaOrigen);
            transferenciasInmediatas.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatas.completarInputAliasCbu(alias);
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
    @DisplayName("TC-0472-BFS: Transferencias Programadas (Fecha especifica) CBU - terceros mismo banco  USD - NO AGENDADOS")
    public void TC0472BSF() {
        try {
            String usuario= uVolpatomonica;
            String tarjetaAoperar="2005";
            String cuentaOrigen="CA USD 000000170305";
            String cbu="3300520945200000502031";
            //TEST
            loginSteps.iniciarSessionAlEscritorio(usuario);
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI(cbu);
            //TEST
            menuLateralSteps.clickTransferencias(tarjetaAoperar,cuentaOrigen);
            transferenciasInmediatas.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatas.completarInputAliasCbu(cbu);
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
    @DisplayName("TC-0410-BFS: Transferencias - terceros mismo banco  USD - AGENDADOS")
    public void TC0410BSF(){
        try{
            String usuario= uVolpatomonica;
            String tarjetaAoperar="2005";
            String cuentaOrigen="CA USD 000000170305";
            String cbu="3300000640000008700092";
            //PRECONDICION
            loginSteps.iniciarSessionAlEscritorio(usuario);
            //agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("",cbu,"");
            agendaNuevoDestinatarioSteps.adherirNuevoDestinatarioCBU(cbu,"TER-BSF");
            //TEST
            menuLateralSteps.clickTransferencias(tarjetaAoperar,cuentaOrigen);
            transferenciasInmediatas.seleccionarTipoTransferencia("Contacto Agendado");
            transferenciasInmediatas.clickAgendaDestinatario();
            transferenciasInmediatas.transferirAunContacto("TER-BSF");
            transferenciasInmediatas.setImporteGenerico("1");
            transferenciasInmediatas.seleccionarConcepto("Alquileres");
            transferenciasInmediatas.setDescripcion("TestAutomation");
            transferenciasInmediatas.clickBotonTransferirAhora();
            transferenciasInmediatas.clickConfirmarGenerico();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            //POSTCONDICION
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI(cbu);
            transferenciasInmediatas.verificarDescuentoCuentaOrigenTransferencia();

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0473-BFS: Transferencias Programadas (Fecha especifica) CBU - terceros otros bancos $ - NO AGENDADOS")
    public void TC0473BSF() {
        try {
            String usuario= uVolpatomonica;
            String tarjetaAoperar="2005";
            String cuentaOrigen="CC $ 000006978500";
            String cbu="3860001001000036919074";
            //TEST
            loginSteps.iniciarSessionAlEscritorio(usuario);
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI(cbu);
            //TEST
            menuLateralSteps.clickTransferencias(tarjetaAoperar,cuentaOrigen);
            transferenciasInmediatas.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatas.completarInputAliasCbu(cbu);
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
    @DisplayName("TC-0411-BFS: Transferencias - terceros otros bancos $ - AGENDADOS")
    public void TC0411BSF(){
        try{
            String usuario = uAbramcelia;
            String tarjetaAOperar="5009";
            String cuentaOrigen = "CA $ 000000870510";
            String cbu="3860001001000036919074";

            //PRECONDICION
            loginSteps.iniciarSessionAlEscritorio(usuario);
            agendaNuevoDestinatarioSteps.adherirNuevoDestinatarioCBU(cbu,"TER-BERSA");
            //TEST
            menuLateralSteps.clickTransferencias(tarjetaAOperar,cuentaOrigen);
            transferenciasInmediatas.seleccionarTipoTransferencia("Contacto Agendado");
            transferenciasInmediatas.clickAgendaDestinatario();
            transferenciasInmediatas.transferirAunContacto("TER-BERSA");
            transferenciasInmediatas.setImporteGenerico("1");
            transferenciasInmediatas.seleccionarConcepto("Alquileres");
            transferenciasInmediatas.setDescripcion("TestAutomation");
            transferenciasInmediatas.clickBotonTransferirAhora();
            transferenciasInmediatas.clickConfirmarGenerico();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            //POSTCONDICION
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI(cbu);
            transferenciasInmediatas.verificarDescuentoCuentaOrigenTransferencia();
        }catch (Exception ex){
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Media.class})
    @DisplayName("TC-0412-BFS: Transferencias - terceros otros bancos USD - AGENDADOS")
    public void TC0412BSF(){
        try{
            String usuario = uTranspull1;
            String cuentaOrigen="CA USD 000024482609";
            String cbu="0450500602020050105885";
            //PRECONDICION
            loginSteps.iniciarSessionAlEscritorio(usuario);
            agendaNuevoDestinatarioSteps.adherirNuevoDestinatarioCBU(cbu,"TER-BSJ");
            //TEST
            menuLateralSteps.clickTransferencias("",cuentaOrigen);
            transferenciasInmediatas.seleccionarTipoTransferencia("Contacto Agendado");
            transferenciasInmediatas.clickAgendaDestinatario();
            transferenciasInmediatas.transferirAunContacto("TER-BSJ");
            transferenciasInmediatas.setImporteGenerico("1");
            transferenciasInmediatas.seleccionarConcepto("Alquileres");
            transferenciasInmediatas.setDescripcion("TestAutomation");
            transferenciasInmediatas.clickBotonTransferirAhora();
            transferenciasInmediatas.clickConfirmarGenerico();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            //POSTCONDICION
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI(cbu);
            transferenciasInmediatas.verificarDescuentoCuentaOrigenTransferencia();

        }catch (Exception ex){
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Media.class})
    @DisplayName("TC-0413-BFS: Transferencias - terceros otros bancos INTERBANK (No PETERSEN) $ - AGENDADOS")
    public void TC0413BSF(){
        try{
            usuario = UtilsGP.consultaDBsqlTag(uAbramcelia);
            String cbu="0720000788000006008668";
            //PRECONDICION
            loginSteps.iniciarSessionAlEscritorio(usuario.get("nombre"));
            agendaNuevoDestinatarioSteps.adherirNuevoDestinatarioCBU(cbu,"TER-STD");
            //TEST
            menuLateralSteps.clickTransferencias(usuario.get("tarjetaAOperar"),usuario.get("cuentaOrigen"));
            transferenciasInmediatas.seleccionarTipoTransferencia("Contacto Agendado");
            transferenciasInmediatas.clickAgendaDestinatario();
            transferenciasInmediatas.transferirAunContacto("TER-STD");
            transferenciasInmediatas.setImporteGenerico("1");
            transferenciasInmediatas.seleccionarConcepto("Alquileres");
            transferenciasInmediatas.setDescripcion("TestAutomation");
            transferenciasInmediatas.clickBotonTransferirAhora();
            transferenciasInmediatas.clickConfirmarGenerico();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            //POSTCONDICION
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI(cbu);
            transferenciasInmediatas.verificarDescuentoCuentaOrigenTransferencia();
        }catch (Exception ex){
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Media.class})
    @DisplayName("TC-0414-BFS: Transferencias - terceros otros bancos INTERBANK (No PETERSEN) USD - AGENDADOS")
    public void TC0414BSF(){
        try{
            usuario = UtilsGP.consultaDBsqlTag(uTranspull1USD);
            //PRECONDICION
            loginSteps.iniciarSessionAlEscritorio(usuario.get("nombre"));
            agendaNuevoDestinatarioSteps.adherirNuevoDestinatarioCBU("0720000788000006008668","TER-STD-USD");
            //TEST
            menuLateralSteps.clickTransferencias("",usuario.get("cuentaOrigen"));
            transferenciasInmediatas.seleccionarTipoTransferencia("Contacto Agendado");
            transferenciasInmediatas.clickAgendaDestinatario();
            transferenciasInmediatas.transferirAunContacto("TER-STD-USD");
            transferenciasInmediatas.setImporteGenerico("1");
            transferenciasInmediatas.seleccionarConcepto("Alquileres");
            transferenciasInmediatas.setDescripcion("TestAutomation");
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
    @DisplayName("TC-0427-BFS: Transferencias CVU - terceros $ AGENDADOS")
    public void TC0427BSF(){
        try{
            usuario=UtilsGP.consultaDBsqlTag(uAbramcelia);
            //PRECONDICION
            loginSteps.iniciarSessionAlEscritorio(usuario.get("nombre"));
            agendaNuevoDestinatarioSteps.adherirNuevoDestinatarioCBU("0000001700300000000313","CVU");
            //TEST
            menuLateralSteps.clickTransferencias(usuario.get("tarjetaAOperar"),usuario.get("cuentaOrigen"));
            transferenciasInmediatas.seleccionarTipoTransferencia("Contacto Agendado");
            transferenciasInmediatas.clickAgendaDestinatario();
            transferenciasInmediatas.transferirAunContacto("CVU");
            transferenciasInmediatas.setImporteGenerico("1");
            transferenciasInmediatas.seleccionarConcepto("Alquileres");
            transferenciasInmediatas.setDescripcion("TestAutomation");
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
    @DisplayName("TC-0429-BFS: Transferencias ALIAS de CVU - terceros $ NO AGENDADOS")
    public void TC0429BSF(){
        try{
            usuario=UtilsGP.consultaDBsqlTag(uAbramcelia);
            //PRECONDICION
            loginSteps.iniciarSessionAlEscritorio(usuario.get("nombre"));
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("BOTE.FILA.MITO");
            //TEST
            menuLateralSteps.clickTransferencias("5009","CA $ 000000870510");
            transferenciasInmediatas.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatas.completarInputAliasCbu("BOTE.FILA.MITO");
            transferenciasInmediatas.clickBuscarGenerico();
            transferenciasInmediatas.botonSonCorrectos();
            transferenciasInmediatas.setImporteGenerico("1");
            transferenciasInmediatas.seleccionarConcepto("Alquileres");
            transferenciasInmediatas.setDescripcion("TestAutomation");
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
    @Category({Alta.class})
    @DisplayName("TC-0474-BFS: Transferencias Programadas (Fecha especifica) CBU - terceros otros bancos USD - NO AGENDADOS")
    public void TC0474BSF() {
        try {
            usuario=UtilsGP.consultaDBsqlTag(uTranspull1USD);
            //TEST
            loginSteps.iniciarSessionAlEscritorio(usuario.get("nombre"));
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("3860001004000083891188");
            //TEST
            menuLateralSteps.clickTransferencias("",usuario.get("cuentaOrigen"));
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
    @DisplayName("TC-0475-BFS: Transferencias Programadas (Fecha especifica) CBU - terceros otros bancos INTERBANK (No PETERSEN) $ - NO AGENDADOS")
    public void TC0475BSF() {
        try {
            usuario = UtilsGP.consultaDBsqlTag(uAbramcelia);
            //TEST
            loginSteps.iniciarSessionAlEscritorio(usuario.get("nombre"));
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("0720000788000006008668");
            //TEST
            menuLateralSteps.clickTransferencias("5009","CA $ 000000870510");
            transferenciasInmediatas.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatas.completarInputAliasCbu("0720000788000006008668");//3300050110500001239081
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
    @DisplayName("TC-0465-BFS: Transferencias Programadas (Fecha especifica) - terceros mismo banco  $ - AGENDADOS")
    public void TC0465BSF(){
        try{
            usuario = UtilsGP.consultaDBsqlTag(uAbramcelia);
            //PRECONDICION
            loginSteps.iniciarSessionAlEscritorio(usuario.get("nombre"));
            agendaNuevoDestinatarioSteps.adherirNuevoDestinatarioCBU("3300050110500001239081","TER-BSF");
            //TEST
            menuLateralSteps.clickTransferencias(usuario.get("tarjetaAOperar"),usuario.get("cuentaOrigen"));
            transferenciasInmediatas.seleccionarTipoTransferencia("Contacto Agendado");
            transferenciasInmediatas.clickAgendaDestinatario();
            transferenciasInmediatas.transferirAunContacto("TER-BSF");
            transferenciasInmediatas.setImporteGenerico("1");
            transferenciasInmediatas.seleccionarConcepto("Alquileres");
            transferenciasInmediatas.setDescripcion("TestAutomation");
            transferenciasInmediatas.transferenciaProgramadaPara();
            transferenciasInmediatas.seleccionFechaFutura("Especifica");
            transferenciasInmediatas.clickContinuarGenerico();
            transferenciasInmediatas.clickConfirmarGenerico();
            ticketSteps.validarTicket("Tu transaccion ha sido agendada");
            //POSTCONDICION
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("3300050110500001239081");

        }catch (Exception ex){
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Alta.class})
    @DisplayName("TC-0466-BFS: Transferencias Programadas (Fecha especifica) - terceros mismo banco  USD - AGENDADOS")
    public void TC0466BSF(){
        try{
            usuario = UtilsGP.consultaDBsqlTag(uTranspull1USD);
            //PRECONDICION
            loginSteps.iniciarSessionAlEscritorio(usuario.get("nombre"));
            agendaNuevoDestinatarioSteps.adherirNuevoDestinatarioCBU("3300000640000008700092","TER-BSF-USD");
            //TEST
            menuLateralSteps.clickTransferencias("",usuario.get("cuentaOrigen"));
            transferenciasInmediatas.seleccionarTipoTransferencia("Contacto Agendado");
            transferenciasInmediatas.clickAgendaDestinatario();
            transferenciasInmediatas.transferirAunContacto("TER-BSF-USD");
            transferenciasInmediatas.setImporteGenerico("1");
            transferenciasInmediatas.seleccionarConcepto("Alquileres");
            transferenciasInmediatas.setDescripcion("TestAutomation");
            transferenciasInmediatas.transferenciaProgramadaPara();
            transferenciasInmediatas.seleccionFechaFutura("Especifica");
            transferenciasInmediatas.clickContinuarGenerico();
            transferenciasInmediatas.clickConfirmarGenerico();
            ticketSteps.validarTicket("Tu transaccion ha sido agendada");
            //POSTCONDICION
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("3300000640000008700092");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Baja.class})
    @DisplayName("TC-0476-BFS: Transferencias Programadas (Fecha especifica) CBU - terceros otros bancos INTERBANK (No PETERSEN) USD - NO AGENDADOS")
    public void TC0476BSF() {
        try {
            usuario = UtilsGP.consultaDBsqlTag(uTranspull1USD);
            //TEST
            loginSteps.iniciarSessionAlEscritorio(usuario.get("nombre"));
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("0720000788000006008668");
            //TEST
            menuLateralSteps.clickTransferencias("",usuario.get("cuentaOrigen"));
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
    @DisplayName("TC-0477-BFS: Transferencias Programadas (Fecha especifica) ALIAS - terceros mismo banco  $ - NO AGENDADOS")
    public void TC0477BSF() {
        try {
            usuario = UtilsGP.consultaDBsqlTag(uAbramcelia);
            //TEST
            loginSteps.iniciarSessionAlEscritorio(usuario.get("nombre"));
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("TRIGO.AMIGO.PIE");
            //TEST
            menuLateralSteps.clickTransferencias(usuario.get("tarjetaAOperar"),usuario.get("cuentaOrigen"));
            transferenciasInmediatas.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatas.completarInputAliasCbu("TRIGO.AMIGO.PIE");
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
        }catch (Exception ex){
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Alta.class})
    @DisplayName("TC-0478-BFS: Transferencias Programadas (Fecha especifica) ALIAS - terceros mismo banco  USD - NO AGENDADOS")
    public void TC0478BSF() {
        try {
            usuario = UtilsGP.consultaDBsqlTag(uTranspull1USD);
            //TEST
            loginSteps.iniciarSessionAlEscritorio(usuario.get("nombre"));
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("TEST.CALL");
            //TEST
            menuLateralSteps.clickTransferencias("",usuario.get("cuentaOrigen"));
            transferenciasInmediatas.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatas.completarInputAliasCbu("TEST.CALL");
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
    @DisplayName("TC-0479-BFS: Transferencias Programadas (Fecha especifica) ALIAS - terceros otros bancos $ - NO AGENDADOS")
    public void TC0479BSF() {
        try {
            usuario = UtilsGP.consultaDBsqlTag(uAbramcelia);
            //TEST
            loginSteps.iniciarSessionAlEscritorio(usuario.get("nombre"));
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("GLOBO.FIESTA.PAZ");
            //TEST
            menuLateralSteps.clickTransferencias(usuario.get("tarjetaAOperar"),usuario.get("cuentaOrigen"));
            transferenciasInmediatas.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatas.completarInputAliasCbu("GLOBO.FIESTA.PAZ");
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
    @DisplayName("TC-0467-BFS: Transferencias Programadas (Fecha especifica) - terceros otros bancos $ - AGENDADOS")
    public void TC0467BSF(){
        try{
            usuario = UtilsGP.consultaDBsqlTag(uAbramcelia);
            //PRECONDICION
            loginSteps.iniciarSessionAlEscritorio(usuario.get("nombre"));
            agendaNuevoDestinatarioSteps.adherirNuevoDestinatarioCBU("3860001001000036919074","TER-BER");
            //TEST
            menuLateralSteps.clickTransferencias(usuario.get("tarjetaAOperar"),usuario.get("cuentaOrigen"));
            transferenciasInmediatas.seleccionarTipoTransferencia("Contacto Agendado");
            transferenciasInmediatas.clickAgendaDestinatario();
            transferenciasInmediatas.transferirAunContacto("TER-BER");
            transferenciasInmediatas.setImporteGenerico("1");
            transferenciasInmediatas.seleccionarConcepto("Alquileres");
            transferenciasInmediatas.setDescripcion("TestAutomation");
            transferenciasInmediatas.transferenciaProgramadaPara();
            transferenciasInmediatas.seleccionFechaFutura("Especifica");
            transferenciasInmediatas.clickContinuarGenerico();
            transferenciasInmediatas.clickConfirmarGenerico();
            ticketSteps.validarTicket("Tu transaccion ha sido agendada");
            ticketSteps.clickVolverGenerico();
            transferenciasInmediatas.validarTituloPageGenerico("Transferencias");
            //POST
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("3860001001000036919074");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Media.class})
    @DisplayName("TC-0480-BFS: Transferencias Programadas (Fecha especifica) ALIAS - terceros otros bancos USD - NO AGENDADOS")
    public void TC0480BSF() {
        try {
            usuario = UtilsGP.consultaDBsqlTag(uTranspull1USD);
            //TEST
            loginSteps.iniciarSessionAlEscritorio(usuario.get("nombre"));
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("PASTA.PARMA.PARLA");
            menuLateralSteps.clickTransferencias("",usuario.get("cuentaOrigen"));
            transferenciasInmediatas.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatas.completarInputAliasCbu("PASTA.PARMA.PARLA");
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
    @DisplayName("TC-0468-BFS: Transferencias Programadas (Fecha especifica) - terceros otros bancos USD - AGENDADOS")
    public void TC0468BSF(){
        try{
            //PRECONDICION
           usuario=UtilsGP.consultaDBsqlTag(uTranspull1USD);
           loginSteps.iniciarSessionAlEscritorio(usuario.get("nombre"));
            agendaNuevoDestinatarioSteps.adherirNuevoDestinatarioCBU("3860001004000083891188","TER-USD");
            //TEST
            menuLateralSteps.clickTransferencias("",usuario.get("cuentaOrigen"));
            transferenciasInmediatas.seleccionarTipoTransferencia("Contacto Agendado");
            transferenciasInmediatas.clickAgendaDestinatario();
            transferenciasInmediatas.transferirAunContacto("TER-USD");
            transferenciasInmediatas.setImporteGenerico("1");
            transferenciasInmediatas.seleccionarConcepto("Alquileres");
            transferenciasInmediatas.setDescripcion("TestAutomation");
            transferenciasInmediatas.transferenciaProgramadaPara();
            transferenciasInmediatas.seleccionFechaFutura("Especifica");
            transferenciasInmediatas.clickContinuarGenerico();
            transferenciasInmediatas.clickConfirmarGenerico();
            ticketSteps.validarTicket("Tu transaccion ha sido agendada");
            //POSTCONDICION
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("3860001004000083891188");

        }catch (Exception ex){
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Baja.class})
    @DisplayName("TC-0469-BFS: Transferencias - terceros otros bancos INTERBANK (No PETERSEN) $ - AGENDADOS")
    public void TC0469BSF(){
        try{
            //PRECONDICION
            loginSteps.iniciarSessionAlEscritorio(uTester1);
            agendaNuevoDestinatarioSteps.adherirNuevoDestinatarioCBU("0720000788000006008668","TC0469BSF");
            //TEST
            menuLateralSteps.clickTransferencias("","");
            transferenciasInmediatas.seleccionarTipoTransferencia("Contacto Agendado");
            transferenciasInmediatas.clickAgendaDestinatario();
            transferenciasInmediatas.transferirAunContacto("TC0469BSF");
            transferenciasInmediatas.clickTarjetaDeDebito();
            generarCredinSteps.keyDownXParametrosGenerico(6,"Visa Débito **** **** **** 5002");
            transferenciasInmediatas.seleccionarCuentaOrigen("CA $ 001000110104");

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
        }catch (Exception ex){
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test //revisar
    @Category({Baja.class})
    @DisplayName("TC-0470-BFS: Transferencias - terceros otros bancos INTERBANK (No PETERSEN) USD - AGENDADOS")
    public void TC0470BSF(){
        try{
            //PRECONDICION
            loginSteps.iniciarSessionAlEscritorio(uTester1);
            agendaNuevoDestinatarioSteps.adherirNuevoDestinatarioCBU("0720000788000006008668","TC0470BSF");
            //TEST
            menuLateralSteps.clickTransferencias("","");
            transferenciasInmediatas.seleccionarTipoTransferencia("Contacto Agendado");
            transferenciasInmediatas.clickAgendaDestinatario();
            transferenciasInmediatas.transferirAunContacto("TC0470BSF");
            transferenciasInmediatas.clickTarjetaDeDebito();
            generarCredinSteps.keyDownXParametrosGenerico(5,"Visa Débito **** **** **** 5002");
            transferenciasInmediatas.seleccionarCuentaOrigen("CA USD 000000376909");
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
        }catch (Exception ex){
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0483-BFS: Transferencias Programadas (Fecha especifica) CVU - terceros $ AGENDADOS")
    public void TC0483BSF(){
        try{
            //PRECONDICION
            usuario=UtilsGP.consultaDBsqlTag(uAbramcelia);
            loginSteps.iniciarSessionAlEscritorio(usuario.get("nombre"));
            agendaNuevoDestinatarioSteps.adherirNuevoDestinatarioCBU("0000001700300000000313","TC0483BSF");
            //TEST
            menuLateralSteps.clickTransferencias(usuario.get("tarjetaAOperar"),usuario.get("cuentaOrigen"));
            transferenciasInmediatas.seleccionarTipoTransferencia("Contacto Agendado");
            transferenciasInmediatas.clickAgendaDestinatario();
            transferenciasInmediatas.transferirAunContacto("TC0483BSF");
            transferenciasInmediatas.setImporteGenerico("1");
            transferenciasInmediatas.seleccionarConcepto("Alquileres");
            transferenciasInmediatas.setDescripcion("TestAutomation");
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
    @DisplayName("TC-0484-BFS: Transferencias Programadas (Fecha especifica) CVU - terceros $ NO AGENDADOS")
    public void TC0484BSF() {
        try {
            usuario=UtilsGP.consultaDBsqlTag(uAbramcelia);
            loginSteps.iniciarSessionAlEscritorio(usuario.get("nombre"));
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("0000001700300000000313");
            //TEST
            menuLateralSteps.clickTransferencias(usuario.get("tarjetaAOperar"),usuario.get("cuentaOrigen"));
            transferenciasInmediatas.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatas.completarInputAliasCbu("0000001700300000000313");
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
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0485-BFS: Transferencias Programadas (Fecha especifica) ALIAS de CVU - terceros $ NO AGENDADOS")
    public void TC0485BSF() {
        try {
            usuario=UtilsGP.consultaDBsqlTag(uAbramcelia);
            loginSteps.iniciarSessionAlEscritorio(usuario.get("nombre"));
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("BOTE.FILA.MITO");
            //TEST
            menuLateralSteps.clickTransferencias(usuario.get("tarjetaAOperar"),usuario.get("cuentaOrigen"));
            transferenciasInmediatas.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatas.completarInputAliasCbu("BOTE.FILA.MITO");
            transferenciasInmediatas.clickBuscarGenerico();
            transferenciasInmediatas.botonSonCorrectos();
            transferenciasInmediatas.setImporteGenerico("1");
            transferenciasInmediatas.setDescripcion("TC0485");
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

