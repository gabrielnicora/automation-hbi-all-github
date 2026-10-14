
package GP.EntreRios.transferencias;

import GP.Tags.Alta;
import GP.Tags.Baja;
import GP.Tags.Criticos;
import GP.Tags.Media;
import db.ConexionDB;
import io.qameta.allure.Allure;
import io.qameta.allure.junit4.DisplayName;
import org.junit.*;
import org.junit.BeforeClass;
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
import utils.TestCaseData;
import utils.UtilsGP;

import java.io.IOException;

import static utils.Constantes.*;

public class TransferenciasTest {
    LoginSteps loginSteps = new LoginSteps();
    MenuLateralSteps menuLateralSteps = new MenuLateralSteps();
    TransferenciasSteps transferenciasSteps = new TransferenciasSteps();
    BotonesGenericosSteps botonesGenericosSteps = new BotonesGenericosSteps();
    ConsultaCredinSteps consultaCredinSteps = new ConsultaCredinSteps();
    ValidacionDeIdentidadSteps validacionDeIdentidadSteps = new ValidacionDeIdentidadSteps();
    GenerarCredinSteps generarCredinSteps = new GenerarCredinSteps();
    DobleFactorSteps dobleFactorSteps = new DobleFactorSteps();
    TicketSteps ticketSteps = new TicketSteps();
    TransferenciasInmediatasSteps transferenciasInmediatas = new TransferenciasInmediatasSteps();
    AgendaNuevoDestinatarioSteps agendaNuevoDestinatarioSteps = new AgendaNuevoDestinatarioSteps();
    CuentasSteps cuentasSteps = new CuentasSteps();
    TransferenciasInmediatasSteps transProgramadasSteps = new TransferenciasInmediatasSteps();
    CalendarioSteps calendarioSteps = new CalendarioSteps();
    BotonesGenericosPage botonesGenericosPage = new BotonesGenericosPage();
    UtilsGP utilsGP = new UtilsGP();
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
    @DisplayName("TC-0007-BERSA: Realizar una transferencia en pesos Interbancaria - A Terceros de distinto banco -  ingresando los datos requeridos haciendo uso de plantillas guardadas")
    public void TC0007BERSA() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uEcarina27);
            menuLateralSteps.clickCuentas();
            cuentasSteps.clickMasOpcionesGenerico();
            cuentasSteps.clickTransferencias();
            cuentasSteps.clickMasOpcionesGenerico();
            transferenciasSteps.clickTransferenciasProgramadas();
            transferenciasInmediatas.clickTarjetaDeDebito();
            transferenciasInmediatas.keyDownToText("3009");
            transferenciasInmediatas.clickContinuarGenerico();
            transferenciasInmediatas.clickListadoDePlantillas();
            transferenciasInmediatas.clickPlantillaPorNombre("AAutomation TC0007");
            transferenciasInmediatas.clickContinuarGenerico();
            transferenciasInmediatas.clickBotonConfimar();
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
    @DisplayName("TC-0008-BERSA: Transferencia Intrabancaria en Pesos - Propias (del mismo banco) Cargada Manualmente - Programada en Fecha Futura Botón Volver")
    public void TC0008BERSA() {
        try {
            testCaseData.cargaDeDatosTest(uEcarina27);
            loginSteps.iniciarSessionAlEscritorioConPass(uEcarina27,testCaseData.password);
            menuLateralSteps.clickTransferencias("3009","CA $ 020000052210");
            //transferenciasInmediatas.seleccionarCuentaOrigen("CA $ 606033880");
            transferenciasInmediatas.seleccionarTipoTransferencia("Cuenta Propia");
            transferenciasInmediatas.seleccionarCuentaDestinoCuentasPropias("CC $ 001004555432");
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
            //POSTCONDICION
            //transferenciasInmediatas.verificarDescuentoCuentaOrigenTransferencia();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            //Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0014-BERSA: Generar CREDIN - Cuenta propia $ (mismo Banco u otro) - Mas de una cuenta Debito - CBU - Segundo factor por SMS")
    public void TC0014BERSA() {
        try {
            testCaseData.cargaDeDatosTest(uBerhometest20);
            loginSteps.iniciarSessionAlEscritorioConPass(uBerhometest20,testCaseData.password);
            menuLateralSteps.clickTransferencias("","");
            transferenciasSteps.clickMasOpcionesGenerico();
            transferenciasSteps.clickConsultarCredin();
            consultaCredinSteps.clickGenerarCredin();

            /*
            generarCredinSteps.seleccionarTarjetaDebitoSiEstaPresente();
            generarCredinSteps.validarTarjetaDeDebitoSeleccionada("8006");
             */

            generarCredinSteps.clickCuentaDebito();
            generarCredinSteps.keyDownXParametrosGenerico(0, "Cuenta Corriente $ 007000094026");
            generarCredinSteps.clickCBU();
            generarCredinSteps.setCampoAliasCbu("0450018601800093482711");
            generarCredinSteps.clickSelectorConcepto();
            generarCredinSteps.keyDownToText("Alquileres");
            generarCredinSteps.setImporteGenerico("1");
            generarCredinSteps.setCampoDescripcion("Test0014");
            generarCredinSteps.clickCheckEsCuentaPropia();
            generarCredinSteps.clickContinuarGenerico();
            generarCredinSteps.clickCheckTerminosCondiciones();
            //dobleFactorSteps.codigoSegundoFactor(utranspull1);
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0139-BERSA: Transferencias Inmediatas - Entre cuentas propias - Flujo completo- Validar ticket con exito")
    public void TC0139BERSA() {
        try {
            testCaseData.cargaDeDatosTest(uAngela01);
            loginSteps.iniciarSessionAlEscritorioConPass(uAngela01,testCaseData.password);
            menuLateralSteps.clickTransferencias("","CA $ 195004110111");
            transferenciasInmediatas.seleccionarTipoTransferencia("Cuenta Propia");
            transferenciasInmediatas.seleccionarCuentaDestinoCuentasPropias("CC $ 195004110111");
            transferenciasInmediatas.setImporteGenerico("1");
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

    // @Test // ya no se encuentra como campo obligatorio
    @DisplayName("TC-0140-BERSA: Transferencias Inmediatas - Validar campo obligatorio Tipo de transferencia")
    public void TC0140BERSA() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uEcarina27);
            menuLateralSteps.clickTransferencias("","");
            transferenciasSteps.clickTransferenciasInmediatas();
            generarCredinSteps.seleccionarTarjetaDebitoSiEstaPresente();
            transferenciasInmediatas.seleccionarCuentaOrigen("Caja de Ahorro $ 020000052210");
            transferenciasInmediatas.seleccionarCuentaDestino("CC $ 001004555432");
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
    @DisplayName("TC-0141-BERSA: Transferencias Inmediatas - Validar campo obligatorio Importe")
    public void TC0141BERSA() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uEcarina27);
            menuLateralSteps.clickTransferencias("","");
            transferenciasInmediatas.seleccionarTipoTransferencia("Cuenta Propia");
            transferenciasInmediatas.seleccionarCuentaDestinoCuentasPropias("CC $ 001004555432");
            transferenciasInmediatas.clickConcepto();
            transferenciasInmediatas.keyDownToText("Alquileres");
            transferenciasInmediatas.setDescripcion("Automation");
            transferenciasInmediatas.validarBotonTransferirDeshabilitado();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    //@Test el campo concepto se encuentra seleccionado por defecto
    @DisplayName("TC-0142-BERSA: Transferencias Inmediatas - Validar campo obligatorio Concepto")
    public void TC0142BERSA() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uEcarina27);
            menuLateralSteps.clickTransferencias("","");
            transferenciasSteps.clickTransferenciasInmediatas();
            generarCredinSteps.seleccionarTarjetaDebitoSiEstaPresente();
            transferenciasInmediatas.seleccionarCuentaOrigen("2210");
            transferenciasInmediatas.seleccionarCuentaDestino("CC $ 001004555432");
            transferenciasInmediatas.setImporteGenerico("100");
            transferenciasInmediatas.setDescripcion("prueba");
            transferenciasInmediatas.clickContinuarGenerico();
            transferenciasInmediatas.validarMsjeCampoObligatorio();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    //@Test //CAMPO OPCIONAL
    @Category({Baja.class})

    @DisplayName("TC-0143-BERSA: Transferencias Inmediatas - Validar campo obligatorio Referencia")
    public void TC0143BERSA() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uEcarina27);
            menuLateralSteps.clickTransferencias("","");
            transferenciasInmediatas.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatas.completarInputAliasCbu("GLOBO.FIESTA.PAZ");
            transferenciasInmediatas.clickBuscarGenerico();
            transferenciasInmediatas.botonSonCorrectos();
            transferenciasInmediatas.setImporteGenerico("100");
            transferenciasInmediatas.clickBotonTransferirAhora();
            transferenciasInmediatas.clickConfirmarGenerico();
            dobleFactorSteps.codigoSegundoFactor();
            transferenciasInmediatas.validarMsjeReferenciaVacia();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Baja.class})
    @DisplayName("TC-0144-BERSA: Transferencias Inmediatas - Transferencia Cuentas propias con MISMA cuenta origen y cuenta destino- Validar mensaje de cuentas iguales")
    public void TC0144BERSA() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uEcarina27);
            menuLateralSteps.clickTransferencias("","");
            transferenciasInmediatas.seleccionarTipoTransferencia("Cuenta Propia");
            transferenciasInmediatas.seleccionarCuentaDestinoCuentasPropias("CA $ 020000052210");
            transferenciasInmediatas.setImporteGenerico("1");
            transferenciasInmediatas.seleccionarConcepto("Alquileres");
            transferenciasInmediatas.clickBotonTransferirAhora();
            transferenciasInmediatas.validaNotificacionEmergenteGenerico("La cuenta destino no puede ser la misma que la cuenta origen");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0145-BERSA: Transferencias - Añadir nuevo destinatario - Con cbu valido")
    public void TC0145BERSA() {
        try {
            //PRECONDICION
            testCaseData.cargaDeDatosTest(uEcarina27);
            loginSteps.iniciarSessionAlEscritorioConPass(uEcarina27,testCaseData.password);
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("3300000620000987477051");
            //TEST
            menuLateralSteps.clickTransferencias("","");
            transferenciasInmediatas.seleccionarTipoTransferencia("Contacto Agendado");
            transferenciasInmediatas.clickAgendaDestinatario();
            agendaNuevoDestinatarioSteps.clickAdherirNuevoDestinatario();
            agendaNuevoDestinatarioSteps.setInputCbuAlias("3300000620000987477051");
            agendaNuevoDestinatarioSteps.clickBuscar();
            agendaNuevoDestinatarioSteps.validarDatosCuenta("3300000620000987477051", "ROLA, MARIO", "20080010045");
            agendaNuevoDestinatarioSteps.setPerteneceA("PruebaAutomation");
            transferenciasInmediatas.botonSonCorrectos();
            dobleFactorSteps.codigoSegundoFactor();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            agendaNuevoDestinatarioSteps.clickVolverGenerico();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Alta.class})
    @DisplayName("TC-0146-BERSA: Transferencias - Añadir nuevo destinatario - Con cbu invalido - Validar mensaje de error")
    public void TC0146BERSA() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uEcarina27);
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
    @DisplayName("TC-0147-BERSA- Transferencias - Añadir nuevo destinatario - Con alias valido- Validar datos de cuenta")
    public void TC0147BERSA() {
        try {
            //PRECONDICION
            loginSteps.iniciarSesion(uEcarina27);
            menuLateralSteps.clickTransferencias("","");
            transferenciasInmediatas.seleccionarTipoTransferencia("Contacto Agendado");
            transferenciasInmediatas.clickAgendaDestinatario();
            agendaNuevoDestinatarioSteps.eliminarContactoAgendado("PruebaAutomation");
            //TEST
            loginSteps.iniciarSessionAlEscritorio(utranspull1);
            menuLateralSteps.clickTransferencias("","");
            transferenciasInmediatas.seleccionarTipoTransferencia("Contacto Agendado");
            transferenciasInmediatas.clickAgendaDestinatario();
            agendaNuevoDestinatarioSteps.clickAdherirNuevoDestinatario();
            //agendaNuevoDestinatarioSteps.clickLabelAlias();
            agendaNuevoDestinatarioSteps.setInputCbuAlias("TRIGO.AMIGO.CELULA");
            agendaNuevoDestinatarioSteps.clickBuscar();
            agendaNuevoDestinatarioSteps.validarDatosCuenta("3300000620000987477051", "ROLA, MARIO", "20080010045");
            agendaNuevoDestinatarioSteps.setPerteneceA("PruebaAutomation");
            //agendaNuevoDestinatarioSteps.clickContinuarGenerico();
            transferenciasInmediatas.botonSonCorrectos();
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
    @DisplayName("TC-0148-BERSA: Transferencias - Añadir nuevo destinatario - Con alias invalido - Validar mensaje de error")
    public void TC0148BERSA() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uEcarina27);
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
    @DisplayName("TC-0344- BERSA: TRANSFERENCIAS - A cuentas de terceros ALIAS - otros bancos - Pesos")
    public void TC0344BERSA() {
        try {
            loginSteps.iniciarSessionAlEscritorio(utranspull1);
            menuLateralSteps.clickTransferencias("","CA $ 001005620072");
            //transferenciasInmediatas.seleccionarCuentaOrigen("CC $ 051004044189");
            transferenciasInmediatas.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatas.completarInputAliasCbu("Prueba.Practia");
            transferenciasInmediatas.clickBuscarGenerico();
            transferenciasInmediatas.botonSonCorrectos();
            transferenciasInmediatas.setImporteGenerico("1");
            transferenciasInmediatas.setDescripcion("Automation");
            transferenciasInmediatas.clickBotonTransferirAhora();
            transferenciasInmediatas.clickConfirmarGenerico();
            dobleFactorSteps.codigoSegundoFactor();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Media.class})
    @DisplayName("TC-0345- BERSA: Transferencias - Verificar Mis transferencias y validar historial")
    public void TC0345BERSA() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uEcarina27);
            menuLateralSteps.clickTransferencias("","");
            transferenciasInmediatas.misTransferencias();
            transferenciasInmediatas.validarTituloPageGenerico("Listado de transferencias");
            transferenciasInmediatas.desplegableCuenta();
            transferenciasInmediatas.keyDownToText("CC $ 001004555432");
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
    @DisplayName("TC-12930-BERSA: TRANSFERENCIAS - A cuentas de terceros CBU - mismo banco - Pesos")
    public void TC12930BERSA() {
        try {
            testCaseData.cargaDeDatosTest(uBerhometest20);
            loginSteps.iniciarSessionAlEscritorioConPass(uBerhometest20,testCaseData.password);
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("3860001005000045554325");

            menuLateralSteps.clickTransferencias("","CC $ 007000094026");
            //transferenciasInmediatas.seleccionarCuentaOrigen("CC $ 051004044189");
            transferenciasInmediatas.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatas.completarInputAliasCbu("3860001005000045554325");
            transferenciasInmediatas.clickBuscarGenerico();
            transferenciasInmediatas.botonSonCorrectos();
            transferenciasInmediatas.setImporteGenerico("1");
            transferenciasInmediatas.setDescripcion("Automation");
            transferenciasInmediatas.clickBotonTransferirAhora();
            transferenciasInmediatas.clickConfirmarGenerico();
            dobleFactorSteps.codigoSegundoFactor();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Alta.class})
    @DisplayName("TC-12931-BERSA: TRANSFERENCIAS - A cuentas de terceros CBU - otros bancos - Pesos")
    public void TC12931BERSA() {
        try {
            loginSteps.iniciarSessionAlEscritorio(utranspull1);
            menuLateralSteps.clickTransferencias("","CA $ 001005620072");
            //transferenciasInmediatas.seleccionarCuentaOrigen("CC $ 051004044189");
            transferenciasInmediatas.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatas.completarInputAliasCbu("3300001320010001101040");
            transferenciasInmediatas.clickBuscarGenerico();
            transferenciasInmediatas.botonSonCorrectos();
            transferenciasInmediatas.setImporteGenerico("1");
            transferenciasInmediatas.setDescripcion("Automation");
            transferenciasInmediatas.clickBotonTransferirAhora();
            transferenciasInmediatas.clickConfirmarGenerico();
            dobleFactorSteps.codigoSegundoFactor();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Criticos.class})
    @DisplayName("TC-12932-BERSA: TRANSFERENCIAS - A cuentas de terceros ALIAS - mismo banco - Pesos")
    public void TC12932BERSA() {
        try {
            testCaseData.cargaDeDatosTest(uBerhometest20);
            loginSteps.iniciarSessionAlEscritorioConPass(uBerhometest20,testCaseData.password);
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("ECARINA.AHORRO.PESOS");

            menuLateralSteps.clickTransferencias("","CC $ 007000094026");
            //transferenciasInmediatas.seleccionarCuentaOrigen("CA $ 001005620072");
            transferenciasInmediatas.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatas.completarInputAliasCbu("ECARINA.AHORRO.PESOS");
            transferenciasInmediatas.clickBuscarGenerico();
            transferenciasInmediatas.botonSonCorrectos();
            transferenciasInmediatas.setImporteGenerico("1");
            transferenciasInmediatas.setDescripcion("Automation");
            transferenciasInmediatas.clickBotonTransferirAhora();
            transferenciasInmediatas.clickConfirmarGenerico();
            dobleFactorSteps.codigoSegundoFactor();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Media.class})
    @DisplayName("TC-12934-BERSA: TRANSFERENCIAS - A cuentas de terceros CBU en dólares USD")
    public void TC12934BERSA() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uBersa2102);
            menuLateralSteps.clickTransferencias("","CA USD 051008044189");

            /*
            //transferenciasInmediatas.seleecionarTarjeaAOperar("8006");
            transferenciasInmediatas.seleccionarCuentaOrigen("CA USD 007006689071");

             */

            transferenciasInmediatas.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatas.completarInputAliasCbu("3860001004000083891188");
            transferenciasInmediatas.clickBuscarGenerico();
            transferenciasInmediatas.botonSonCorrectos();
            transferenciasInmediatas.setImporteGenerico("1");
            transferenciasInmediatas.setDescripcion("Automation");
            transferenciasInmediatas.clickBotonTransferirAhora();
            transferenciasInmediatas.clickConfirmarGenerico();
            dobleFactorSteps.codigoSegundoFactor();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Criticos.class})
    @DisplayName("TC-12938-BERSA: TRANSFERENCIAS - Generar CREDIN - Dolares")
    public void TC12938BERSA() {
        try {
            testCaseData.cargaDeDatosTest(uBerhometest20);
            loginSteps.iniciarSessionAlEscritorioConPass(uBerhometest20,testCaseData.password);
            menuLateralSteps.clickTransferencias("","");
            transferenciasInmediatas.clickMasOpcionesGenerico();
            transferenciasSteps.clickConsultarCredin();
            consultaCredinSteps.clickGenerarCredin();
            generarCredinSteps.clickCuentaDebito();
            generarCredinSteps.keyDownToText( "Caja de Ahorro USD 007006689071");
            generarCredinSteps.clickCBU();
            generarCredinSteps.setCampoAliasCbu(testCaseData.cbuDestino);
            generarCredinSteps.clickSelectorConcepto();
            generarCredinSteps.keyDownXParametrosGenerico(0, "Alquileres");
            generarCredinSteps.setImporteGenerico("1");
            generarCredinSteps.setCampoDescripcion("Test12938");
            generarCredinSteps.clickContinuarGenerico();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Criticos.class})
    @DisplayName("TC-12935-BERSA: Transferencias Programadas (Fecha especifica) CBU - terceros mismo banco  $ - NO AGENDADOS")
    public void TC12935BERSA() {
        try {
            testCaseData.cargaDeDatosTest(uEcarina27);
            loginSteps.iniciarSessionAlEscritorioConPass(uEcarina27,testCaseData.password);
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("3860195203000041101110");

            menuLateralSteps.clickTransferencias("","");
            transferenciasInmediatas.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatas.completarInputAliasCbu("3860195203000041101110");
            transferenciasInmediatas.clickBuscarGenerico();
            transferenciasInmediatas.botonSonCorrectos();
            transferenciasInmediatas.setImporteGenerico("1");
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
    @DisplayName("TC-12969-BERSA: TRANSFERENCIAS - A cuentas de terceros CVU - otros bancos - Pesos")
    public void TC12969BERSA() {
        try {
            testCaseData.cargaDeDatosTest(uBersa2102);
            loginSteps.iniciarSessionAlEscritorioConPass(uBersa2102,testCaseData.password);
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("0000001700300000000313");

            menuLateralSteps.clickTransferencias("","CC $ 051004044189");
            //transferenciasInmediatas.seleccionarCuentaOrigen("CA $ 020000052210");
            transferenciasInmediatas.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatas.completarInputAliasCbu("0000001700300000000313");
            transferenciasInmediatas.clickBuscarGenerico();
            transferenciasInmediatas.botonSonCorrectos();
            transferenciasInmediatas.setImporteGenerico("1");
            transferenciasInmediatas.setDescripcion("Automation");
            transferenciasInmediatas.clickBotonTransferirAhora();
            transferenciasInmediatas.clickConfirmarGenerico();
            dobleFactorSteps.codigoSegundoFactor();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Criticos.class})
    @DisplayName("TC-12981-BERSA: Transferencias - Eliminar destinatario de agenda")
    public void TC12981BERSA() {
        try {
            //PRECONDICION
            testCaseData.cargaDeDatosTest(uEcarina27);
            loginSteps.iniciarSessionAlEscritorioConPass(uEcarina27,testCaseData.password);
            agendaNuevoDestinatarioSteps.adherirNuevoDestinatarioCBU("3300000620000987477051", "TC12981");
            //TEST
            menuLateralSteps.clickTransferencias("","");
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
    @DisplayName("TC-12936-BERSA: TRANSFERENCIAS - Consulta de transferencias realizadas")
    public void TC12936BERSA() {
        try {
            //PRECONDICION
            //transferenciasInmediatas.generateTransferApi(uBersa2102,"Terceros","3860001005000045554325");
            //transferenciasInmediatas.generateTransferApi(uEcarina27,"Propia");//la cuenta debe tener mas de una cuenta propia en pesos
            //CASO
            testCaseData.cargaDeDatosTest(uBerhometest20);
            loginSteps.iniciarSessionAlEscritorioConPass(uBerhometest20,testCaseData.password);
            menuLateralSteps.clickTransferencias("","");
            transferenciasInmediatas.misTransferencias();
            transferenciasInmediatas.desplegableCuenta();
            transferenciasInmediatas.keyDownToText("CC $ 007000094026");
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
    @DisplayName("TC-12939-BERSA: TRANSFERENCIAS - Consultar CREDIN Originante")
    public void TC12939BERSA() {
        try {
            testCaseData.cargaDeDatosTest(uUathb04);
            loginSteps.iniciarSessionAlEscritorioConPass(uUathb04,testCaseData.password);
            generarCredinSteps.generarCredinApi("3860035501000000234277","3860001003000056200723","6009");
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
            consultaCredinSteps.setCuitDestinatario("23244825664");
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
    @DisplayName("TC-12940-BERSA: TRANSFERENCIAS - Consultar CREDIN destinatario")
    public void TC12940BERSA() {
        try {
            //Precondicion: Generar Credin
            loginSteps.loginForAPI(uEcarina27);
            generarCredinSteps.generarCredinApi("3860001005000045554325","3860035501000000234277","3009");
            //Test: Consulta de Credin
            testCaseData.cargaDeDatosTest(uUathbb04);
            loginSteps.iniciarSessionAlEscritorioConPass(uUathb04,testCaseData.password);
            menuLateralSteps.clickTransferencias("","");
            transferenciasInmediatas.clickMasOpcionesGenerico();
            transferenciasSteps.clickConsultarCredin();
            calendarioSteps.clickEnFechaDeCreacionDesde();
            calendarioSteps.clickDiaActual();
            calendarioSteps.clickEnFechaDeCreacionHasta();
            calendarioSteps.clickDiaActual();
            consultaCredinSteps.clickDestinatario();
            consultaCredinSteps.clickCuitOriginante();
            consultaCredinSteps.setCuitOriginante("20205056573");
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
    @DisplayName("TC-14250-BERSA: TRANSFERENCIAS - A cuentas propias otros bancos")
    public void TC14250BERSA(){
        try{
            testCaseData.cargaDeDatosTest(uTranspull1);
            loginSteps.iniciarSessionAlEscritorioConPass(utranspull1,testCaseData.password);
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("3300520915200003347070");
            //
            menuLateralSteps.clickTransferencias("","CA $ 001005620072");
            //transferenciasInmediatas.seleccionarCuentaOrigen("CC $ 024003527202");
            transferenciasInmediatas.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatas.completarInputAliasCbu("3300520915200003347070");
            transferenciasInmediatas.clickBuscarGenerico();
            transferenciasInmediatas.botonSonCorrectos();
            transferenciasInmediatas.setImporteGenerico("1");
            transferenciasInmediatas.seleccionarConcepto("Alquileres");
            transferenciasInmediatas.setDescripcion("TestAutomation");
            transferenciasInmediatas.clickBotonTransferirAhora();
            transferenciasInmediatas.clickConfirmarGenerico();
            dobleFactorSteps.codigoSegundoFactor();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
        }catch (Exception ex){
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0409-BERSA: TRANSFERENCIAS - terceros mismo banco  $ - AGENDADOS")
    public void TC0409BERSA(){
        try{
            //PRECONDICION
            testCaseData.cargaDeDatosTest(uAngela01);
            loginSteps.iniciarSessionAlEscritorioConPass(uAngela01,testCaseData.password);
            //agendaNuevoDestinatarioSteps.adherirNuevoDestinatarioCBU("3860001005000045554325","TC0409BERSA");
            agendaNuevoDestinatarioSteps.adherirNuevoDestinatarioCBU("3860020103000000522105","TC0409BERSA");
            //TEST
            menuLateralSteps.clickTransferencias("","");
            transferenciasInmediatas.seleccionarCuentaOrigen("CA $ 195004110111");
            transferenciasInmediatas.seleccionarTipoTransferencia("Contacto Agendado");
            transferenciasInmediatas.clickAgendaDestinatario();
            transferenciasInmediatas.transferirAunContacto("TC0409BERSA");
            transferenciasInmediatas.setImporteGenerico("5");
            transferenciasInmediatas.seleccionarConcepto("Alquileres");
            transferenciasInmediatas.setDescripcion("TC0409");
            transferenciasInmediatas.clickBotonTransferirAhora();
            transferenciasInmediatas.clickConfirmarGenerico();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            //POSTCONDICION
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("3860020103000000522105");
            transferenciasInmediatas.verificarDescuentoCuentaOrigenTransferencia();

        }catch (Exception ex){
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Alta.class})
    @DisplayName("TC-0403-BERSA: Transferencias a cuentas propias otro banco (GRUPO) $ - AGENDADOS")
    public void TC0403BERSA(){
        try{
            //PRECONDICION - AGENDAR CONTACTO
            loginSteps.iniciarSessionAlEscritorio(uAgustina2029);
            agendaNuevoDestinatarioSteps.adherirNuevoDestinatarioCBU("3300520915200003347070","PROPIA-BERSA");
            //TEST
            menuLateralSteps.clickTransferencias("","");
            transferenciasInmediatas.seleccionarTipoTransferencia("Cuenta Propia");
            transferenciasInmediatas.seleccionarCuentaDestinoCuentasPropias("PROPIA-BERSA");
            transferenciasInmediatas.seleccionarCuentaOrigen("CC $ 024003527202");
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
    //NO HAY DATOS AÚN

    @Test
    @Category({Alta.class})
    @DisplayName("TC-0410-BERSA: Transferencias - terceros mismo banco  USD - AGENDADOS")
    public void TC0410BERSA(){
        try{
            //PRECONDICION
            loginSteps.iniciarSessionAlEscritorio(u11107010);
            agendaNuevoDestinatarioSteps.adherirNuevoDestinatarioCBU("3860051504000080441898","TC0410BERSA");
            //TEST
            menuLateralSteps.clickTransferencias("","");
            transferenciasInmediatas.seleccionarCuentaOrigen("CA USD 001003191543");
            transferenciasInmediatas.seleccionarTipoTransferencia("Contacto Agendado");
            transferenciasInmediatas.clickAgendaDestinatario();
            transferenciasInmediatas.transferirAunContacto("TC0410BERSA");
            transferenciasInmediatas.setImporteGenerico("1");
            transferenciasInmediatas.seleccionarConcepto("Alquileres");
            transferenciasInmediatas.setDescripcion("TC0410");
            transferenciasInmediatas.clickBotonTransferirAhora();
            transferenciasInmediatas.clickConfirmarGenerico();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            //POSTCONDICION
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("3860051504000080441898");
            transferenciasInmediatas.verificarDescuentoCuentaOrigenTransferencia();

        }catch (Exception ex){
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

        @Test
        @Category({Criticos.class})
        @DisplayName("TC-0407-BERSA: Transferencias ALIAS a cuentas propias otro banco $ - NO AGENDADOS")
        public void TC0407BERSA(){
            try{
                //TEST
                testCaseData.cargaDeDatosTest(uAgustina2029);
                loginSteps.iniciarSessionAlEscritorioConPass(uAgustina2029,testCaseData.password);
                agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("MARCHA.REINA.CELTA");
                //
                menuLateralSteps.clickTransferencias("","");
                transferenciasInmediatas.seleccionarCuentaOrigen("CC $ 024003527202");
                transferenciasInmediatas.seleccionarTipoTransferencia("Terceros");
                transferenciasInmediatas.completarInputAliasCbu("MARCHA.REINA.CELTA");
                transferenciasInmediatas.clickBuscarGenerico();
                transferenciasInmediatas.botonSonCorrectos();
                transferenciasInmediatas.setImporteGenerico("1");
                transferenciasInmediatas.seleccionarConcepto("Alquiler");
                transferenciasInmediatas.setDescripcion("TestAutomation");
                transferenciasInmediatas.clickBotonTransferirAhora();
                transferenciasInmediatas.clickConfirmarGenerico();
                dobleFactorSteps.codigoSegundoFactor();
                ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            }catch (Exception ex){
                System.out.println("Error: " + ex.getMessage());
                Assert.fail("Ocurrio un error: " + ex.getMessage());
            }
        }

        //@Test // Sin usuarios con dos cuentas usd habilitadas para realizar la operación
        @Category({Alta.class})
        @DisplayName("TC-0402-BERSA: Transferencias entre cuentas propias mismo banco USD")
        public void TC0402BERSA(){
            try{
                loginSteps.iniciarSessionAlEscritorio(uTester1);
                menuLateralSteps.clickTransferencias("","");
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
            }catch (Exception ex){
                System.out.println("Error: " + ex.getMessage());
                Assert.fail("Ocurrio un error: " + ex.getMessage());
            }
        }


        @Test
        @Category({Baja.class})
        @DisplayName("TC-0419-BERSA: Transferencias CBU - terceros otros bancos INTERBANK (No PETERSEN) $ - NO AGENDADOS")
        public void TC0419BERSA(){
            try{
                //TEST
                loginSteps.iniciarSessionAlEscritorio(uEcarina27);
                menuLateralSteps.clickTransferencias("","");
                //transferenciasInmediatas.seleecionarTarjeaAOperar("8006");
                transferenciasInmediatas.seleccionarCuentaOrigen("CA $ 020000052210");
                transferenciasInmediatas.seleccionarTipoTransferencia("Terceros");
                transferenciasInmediatas.completarInputAliasCbu("0720000788000006008668");
                transferenciasInmediatas.clickBuscarGenerico();
                transferenciasInmediatas.botonSonCorrectos();
                transferenciasInmediatas.setImporteGenerico("1");
                transferenciasInmediatas.seleccionarConcepto("Alquiler");
                transferenciasInmediatas.setDescripcion("TestAutomation");
                transferenciasInmediatas.clickBotonTransferirAhora();
                transferenciasInmediatas.clickConfirmarGenerico();
                ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            }catch (Exception ex){
                System.out.println("Error: " + ex.getMessage());
                Assert.fail("Ocurrio un error: " + ex.getMessage());
            }
        }

    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0411-BERSA: Transferencias - terceros otros bancos $ - AGENDADOS")
    public void TC0411BBERSA(){
        try{
            //PRECONDICION
            testCaseData.cargaDeDatosTest(uEcarina27);
            loginSteps.iniciarSessionAlEscritorioConPass(uEcarina27,testCaseData.password);
            agendaNuevoDestinatarioSteps.adherirNuevoDestinatarioCBU("3300520915200000746085","TC0411BERSA");
            //TEST
            menuLateralSteps.clickTransferencias("3009","CA $ 020000052210");
            transferenciasInmediatas.seleccionarTipoTransferencia("Contacto Agendado");
            transferenciasInmediatas.clickAgendaDestinatario();
            transferenciasInmediatas.transferirAunContacto("TC0411BERSA");
            transferenciasInmediatas.setImporteGenerico("1");
            transferenciasInmediatas.seleccionarConcepto("Alquileres");
            transferenciasInmediatas.setDescripcion("TC0411");
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
    @Category({Media.class})
    @DisplayName("TC-0412-BERSA: Transferencias - terceros otros bancos USD - AGENDADOS")
    public void TC0412BERSA(){
        try{
            //PRECONDICION
            loginSteps.iniciarSessionAlEscritorio(uBersa2102);
            agendaNuevoDestinatarioSteps.adherirNuevoDestinatarioCBU("3300520945200000502031","TC0412BERSA");
            //TEST
            menuLateralSteps.clickTransferencias("","");
            transferenciasInmediatas.seleccionarTipoTransferencia("Contacto Agendado");
            transferenciasInmediatas.clickAgendaDestinatario();
            transferenciasInmediatas.transferirAunContacto("TC0412BERSA");
            transferenciasInmediatas.seleccionarCuentaOrigen("CA USD 051008044189");
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
    @DisplayName("TC-0413-BERSA: Transferencias - terceros otros bancos INTERBANK (No PETERSEN) $ - AGENDADOS")
    public void TC0413BERSA(){
        try{
            //PRECONDICION
            loginSteps.iniciarSessionAlEscritorio(uEcarina27);
            agendaNuevoDestinatarioSteps.adherirNuevoDestinatarioCBU("0720000788000006008668","TC0413BERSA");
            //TEST
            menuLateralSteps.clickTransferencias("","");
            transferenciasInmediatas.seleccionarTipoTransferencia("Contacto Agendado");
            transferenciasInmediatas.clickAgendaDestinatario();
            transferenciasInmediatas.transferirAunContacto("TC0413BERSA");
            transferenciasInmediatas.seleccionarCuentaOrigen("CA $ 020000052210");
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
    @DisplayName("TC-0414-BERSA: Transferencias - terceros otros bancos INTERBANK (No PETERSEN) USD - AGENDADOS")
    public void TC0414BERSA(){
        try{
            //PRECONDICION
            loginSteps.iniciarSessionAlEscritorio(uBersa2102);
            agendaNuevoDestinatarioSteps.adherirNuevoDestinatarioCBU("0720000788000006008668","TC0414BERSA");
            //TEST
            menuLateralSteps.clickTransferencias("","");
            transferenciasInmediatas.seleccionarTipoTransferencia("Contacto Agendado");
            transferenciasInmediatas.clickAgendaDestinatario();
            transferenciasInmediatas.transferirAunContacto("TC0414BERSA");
            transferenciasInmediatas.seleccionarCuentaOrigen("CA USD 051008044189");
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
    @DisplayName("TC-0427-BERSA: Transferencias CVU - terceros $ AGENDADOS")
    public void TC0427BERSA(){
        try{
            //PRECONDICION
            testCaseData.cargaDeDatosTest(uEcarina27);
            loginSteps.iniciarSessionAlEscritorioConPass(uEcarina27,testCaseData.password);
            agendaNuevoDestinatarioSteps.adherirNuevoDestinatarioCBU("0000001700300000000313","TC0427BERSA");
            //TEST
            menuLateralSteps.clickTransferencias("","");
            transferenciasInmediatas.seleccionarTipoTransferencia("Contacto Agendado");
            transferenciasInmediatas.clickAgendaDestinatario();
            transferenciasInmediatas.transferirAunContacto("TC0427BERSA");
            transferenciasInmediatas.seleccionarCuentaOrigen("CA $ 020000052210");
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
    @DisplayName("TC-0429-BERSA: Transferencias ALIAS de CVU - terceros $ NO AGENDADOS")
    public void TC0429BERSA(){
        try{
            //PRECONDICION
            testCaseData.cargaDeDatosTest(uEcarina27);
            loginSteps.iniciarSessionAlEscritorioConPass(uEcarina27,testCaseData.password);
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("BOTE.FILA.MITO");
            //TEST
            menuLateralSteps.clickTransferencias("3009","CA $ 020000052210");
            transferenciasInmediatas.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatas.completarInputAliasCbu("BOTE.FILA.MITO");
            transferenciasInmediatas.clickBuscarGenerico();
            transferenciasInmediatas.botonSonCorrectos();
            transferenciasInmediatas.seleccionarCuentaOrigen("CA $ 020000052210");
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
    @Test
    @Category({Baja.class})
    @DisplayName("TC-0420-BERSA: Transferencias CBU - terceros otros bancos INTERBANK (No PETERSEN) USD - NO AGENDADOS")
    public void TC0420BERSA(){
        try{
            //TEST
            loginSteps.iniciarSessionAlEscritorio(uBerhometest20);
            menuLateralSteps.clickTransferencias("","");
            transferenciasInmediatas.seleccionarCuentaOrigen("CA USD 007006689071");
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
    @DisplayName("TC-0418-BERSA: Transferencias CBU - terceros otros bancos USD - NO AGENDADOS")
    public void TC0418BERSA(){
        try{
            //TEST
            loginSteps.iniciarSessionAlEscritorio(uBerhometest20);
            menuLateralSteps.clickTransferencias("","");
            //transferenciasInmediatas.seleecionarTarjeaAOperar("8006");
            transferenciasInmediatas.seleccionarCuentaOrigen("CA USD 007006689071");
            transferenciasInmediatas.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatas.completarInputAliasCbu("3300520945200000502031");
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
    @DisplayName("TC-0465-BERSA: Transferencias Programadas (Fecha especifica) - terceros mismo banco  $ - AGENDADOS")
    public void TC0465BERSA(){
        try{
            //PRECONDICION
            testCaseData.cargaDeDatosTest(uAngela01);
            loginSteps.iniciarSessionAlEscritorioConPass(uAngela01,testCaseData.password);
            agendaNuevoDestinatarioSteps.adherirNuevoDestinatarioCBU("3860020103000000522105","TC0465BERSA");
            //TEST
            menuLateralSteps.clickTransferencias("","");
            transferenciasInmediatas.seleccionarTipoTransferencia("Contacto Agendado");
            transferenciasInmediatas.clickAgendaDestinatario();
            transferenciasInmediatas.transferirAunContacto("TC0465BERSA");
            transferenciasInmediatas.seleccionarCuentaOrigen("CA $ 195004110111");
            transferenciasInmediatas.setImporteGenerico("1");
            transferenciasInmediatas.seleccionarConcepto("Alquileres");
            transferenciasInmediatas.setDescripcion("TC0465");
            transferenciasInmediatas.transferenciaProgramadaPara();
            transferenciasInmediatas.seleccionFechaFutura("Especifica");
            transferenciasInmediatas.clickContinuarGenerico();
            transferenciasInmediatas.clickConfirmarGenerico();

            ticketSteps.validarTicket("Tu transaccion ha sido agendada");
            //POSTCONDICION
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("3860020103000000522105");
        }catch (Exception ex){
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Media.class})
    @DisplayName("TC-0424-BERSA: Transferencias ALIAS - terceros otros bancos USD - NO AGENDADOS")
    public void TC0424BERSA(){
        try{
            //TEST
            loginSteps.iniciarSessionAlEscritorio(uBerhometest20);
            menuLateralSteps.clickTransferencias("","");
            transferenciasInmediatas.seleccionarCuentaOrigen("CA USD 007006689071");
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
    @Category({Alta.class})
    @DisplayName("TC-0466-BERSA: Transferencias Programadas (Fecha especifica) - terceros mismo banco  USD - AGENDADOS")
    public void TC0466BERSA(){
        try{
            //PRECONDICION
            loginSteps.iniciarSessionAlEscritorio(uBersa2102);
            agendaNuevoDestinatarioSteps.adherirNuevoDestinatarioCBU("3860024904000087784062","TC0466BERSA");
            //TEST
            menuLateralSteps.clickTransferencias("","");
            transferenciasInmediatas.seleccionarTipoTransferencia("Contacto Agendado");
            transferenciasInmediatas.clickAgendaDestinatario();
            transferenciasInmediatas.transferirAunContacto("TC0466BERSA");
            transferenciasInmediatas.seleccionarCuentaOrigen("CA USD 051008044189");
            transferenciasInmediatas.setImporteGenerico("1");
            transferenciasInmediatas.seleccionarConcepto("Alquileres");
            transferenciasInmediatas.setDescripcion("TC0466");
            transferenciasInmediatas.transferenciaProgramadaPara();
            transferenciasInmediatas.seleccionFechaFutura("Especifica");
            transferenciasInmediatas.clickContinuarGenerico();
            transferenciasInmediatas.clickConfirmarGenerico();
            ticketSteps.validarTicket("Tu transaccion ha sido agendada");
            //POSTCONDICION
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("3860024904000087784062");

        }catch (Exception ex){
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Media.class})
    @DisplayName("TC-0422-BERSA: Transferencias ALIAS - terceros mismo banco  USD - NO AGENDADOS")
    public void TC0422BERSA(){
        try{
            //TEST
            loginSteps.iniciarSessionAlEscritorio(uBerhometest20);
            menuLateralSteps.clickTransferencias("","");
            transferenciasInmediatas.seleccionarCuentaOrigen("CA USD 007006689071");
            transferenciasInmediatas.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatas.completarInputAliasCbu("lyjk4.3lexT.9yBh0");
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
    @DisplayName("TC-0467-BERSA: Transferencias Programadas (Fecha especifica) - terceros otros bancos $ - AGENDADOS")
    public void TC0467BERSA(){
        try{
            //PRECONDICION
            testCaseData.cargaDeDatosTest(uEcarina27);
            loginSteps.iniciarSessionAlEscritorioConPass(uEcarina27,testCaseData.password);
            agendaNuevoDestinatarioSteps.adherirNuevoDestinatarioCBU("3860010201000000171602","TC0467BERSA");
            //TEST
            menuLateralSteps.clickTransferencias("","");
            transferenciasInmediatas.seleccionarTipoTransferencia("Contacto Agendado");
            transferenciasInmediatas.clickAgendaDestinatario();
            transferenciasInmediatas.transferirAunContacto("TC0467BERSA");
            transferenciasInmediatas.seleccionarCuentaOrigen("CA $ 020000052210");
            transferenciasInmediatas.setImporteGenerico("1");
            transferenciasInmediatas.seleccionarConcepto("Alquileres");
            transferenciasInmediatas.setDescripcion("TC0467");
            transferenciasInmediatas.transferenciaProgramadaPara();
            transferenciasInmediatas.seleccionFechaFutura("Especifica");
            transferenciasInmediatas.clickContinuarGenerico();
            transferenciasInmediatas.clickConfirmarGenerico();
            ticketSteps.validarTicket("Tu transaccion ha sido agendada");
            //POSTCONDICION
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("3860010201000000171602");

        }catch (Exception ex){
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    //@Test //Modificar datos cuando se tenga cuenta con dos cuentas activas para realizar la transferencia en USD
    @Category({Media.class})
    @DisplayName("TC-0458-BERSA: Transferencias Programadas (Fecha especifica) entre cuentas propias mismo banco USD")
    public void TC0458BERSA() {
        try {
            loginSteps.iniciarSessionAlEscritorio(u11107010);
            menuLateralSteps.clickTransferencias("","CA USD 001003191543");
            transferenciasInmediatas.seleccionarTipoTransferencia("Cuenta Propia");
            transferenciasInmediatas.seleccionarCuentaDestinoCuentasPropias("CASTIGLIONI GUSTAVO OSCAR MART");
            transferenciasInmediatas.seleecionarTarjeaAOperar("9000");
            transferenciasInmediatas.seleccionarCuentaOrigen("CA USD 520000050203");
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
        }catch (Exception ex){
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Alta.class})
    @DisplayName("TC-468-BERSA: Transferencias Programadas (Fecha especifica) - terceros otros bancos USD - AGENDADOS")
    public void TC0468BERSA(){
        try{
            //PRECONDICION
            loginSteps.iniciarSessionAlEscritorio(uBersa2102);
            agendaNuevoDestinatarioSteps.adherirNuevoDestinatarioCBU("3300000640000001703050","TC0468BERSA");
            //TEST
            menuLateralSteps.clickTransferencias("","");
            transferenciasInmediatas.seleccionarTipoTransferencia("Contacto Agendado");
            transferenciasInmediatas.clickAgendaDestinatario();
            transferenciasInmediatas.transferirAunContacto("TC0468BERSA");
            transferenciasInmediatas.seleccionarCuentaOrigen("CA USD 051008044189");
            transferenciasInmediatas.setImporteGenerico("1");
            transferenciasInmediatas.seleccionarConcepto("Alquileres");
            transferenciasInmediatas.setDescripcion("TC0468");
            transferenciasInmediatas.transferenciaProgramadaPara();
            transferenciasInmediatas.seleccionFechaFutura("Especifica");
            transferenciasInmediatas.clickContinuarGenerico();
            transferenciasInmediatas.clickConfirmarGenerico();
            ticketSteps.validarTicket("Tu transaccion ha sido agendada");
            //POSTCONDICION
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("3300000640000001703050");

        }catch (Exception ex){
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Alta.class})
    @DisplayName("TC-0459-BERSA: Transferencias Programadas (Fecha especifica) a cuentas propias otro banco $ - AGENDADOS")
    public void TC0459BERSA() {
        try {
            //Precondición Agendar cuenta propia de otro banco
            loginSteps.iniciarSessionAlEscritorio(uAgustina2029);
            agendaNuevoDestinatarioSteps.adherirNuevoDestinatarioCBU("3300520915200003347070","PROPIA-BERSA");
            //TEST
            menuLateralSteps.clickTransferencias("","");
            transferenciasInmediatas.seleccionarTipoTransferencia("Cuenta Propia");
            transferenciasInmediatas.seleccionarCuentaDestinoCuentasPropias("PROPIA-BERSA");
            transferenciasInmediatas.seleecionarTarjeaAOperar("4001");
            transferenciasInmediatas.seleccionarCuentaOrigen("CC $ 024003527202");
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
            //POSTCONDICION
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("3300520915200003347070");

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Baja.class})
    @DisplayName("TC-0469-BERSA: Transferencias - terceros otros bancos INTERBANK (No PETERSEN) $ - AGENDADOS")
    public void TC0469BERSA(){
        try{
            //PRECONDICION
            loginSteps.iniciarSessionAlEscritorio(uEcarina27);
            agendaNuevoDestinatarioSteps.adherirNuevoDestinatarioCBU("0720000788000006008668","TC0469BERSA");
            //TEST
            menuLateralSteps.clickTransferencias("","");
            transferenciasInmediatas.seleccionarTipoTransferencia("Contacto Agendado");
            transferenciasInmediatas.clickAgendaDestinatario();
            transferenciasInmediatas.transferirAunContacto("TC0469BERSA");
            transferenciasInmediatas.seleccionarCuentaOrigen("CA $ 020000052210");
            //transferenciasInmediatas.clickTarjetaDeDebito();
            //generarCredinSteps.keyDownXParametrosGenerico(11,"");
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
    @Category({Alta.class})
    @DisplayName("TC-0461-BERSA: Transferencias Programadas (Fecha especifica) CBU a cuentas propias otro banco $ - NO AGENDADOS")
    public void TC0461BERSA() {
        try {
            //TEST
            loginSteps.iniciarSessionAlEscritorio(uAgustina2029);
            menuLateralSteps.clickTransferencias("","");
            transferenciasInmediatas.seleccionarCuentaOrigen("CC $ 024003527202");
            transferenciasInmediatas.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatas.completarInputAliasCbu("3300520915200003347070");
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
    @Category({Baja.class})
    @DisplayName("TC-0470-BERSA: Transferencias - terceros otros bancos INTERBANK (No PETERSEN) USD - AGENDADOS")
    public void TC0470BERSA(){
        try{
            //PRECONDICION
            loginSteps.iniciarSessionAlEscritorio(uBersa2102);
            agendaNuevoDestinatarioSteps.adherirNuevoDestinatarioCBU("0720000788000006008668","TC0470BERSA");
            //TEST
            menuLateralSteps.clickTransferencias("","");
            transferenciasInmediatas.seleccionarTipoTransferencia("Contacto Agendado");
            transferenciasInmediatas.clickAgendaDestinatario();
            transferenciasInmediatas.transferirAunContacto("TC0470BERSA");
            transferenciasInmediatas.seleccionarCuentaOrigen("CA USD 051008044189");
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
    @DisplayName("TC-0463-BERSA: Transferencias Programadas (Fecha especifica) ALIAS a cuentas propias otro banco $ - NO AGENDADOS")
    public void TC0463BERSA() {
        try {
            //TEST
            testCaseData.cargaDeDatosTest(uAgustina2029);
            loginSteps.iniciarSessionAlEscritorioConPass(uAgustina2029,testCaseData.password);
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("MARCHA.REINA.CELTA");
            //
            menuLateralSteps.clickTransferencias("","");
            transferenciasInmediatas.seleccionarCuentaOrigen("CC $ 024003527202");
            transferenciasInmediatas.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatas.completarInputAliasCbu("MARCHA.REINA.CELTA");
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
    @DisplayName("TC-0483-BERSA: Transferencias Programadas (Fecha especifica) CVU - terceros $ AGENDADOS")
    public void TC0483BERSA(){
        try{
            //PRECONDICION
            testCaseData.cargaDeDatosTest(uEcarina27);
            loginSteps.iniciarSessionAlEscritorioConPass(uEcarina27,testCaseData.password);
            agendaNuevoDestinatarioSteps.adherirNuevoDestinatarioCBU("0000001700300000000313","TC0483BERSA");
            //TEST
            menuLateralSteps.clickTransferencias("","");
            transferenciasInmediatas.seleccionarTipoTransferencia("Contacto Agendado");
            transferenciasInmediatas.clickAgendaDestinatario();
            transferenciasInmediatas.transferirAunContacto("TC0483BERSA");
            transferenciasInmediatas.seleccionarCuentaOrigen("CA $ 020000052210");
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
    @Category({Alta.class})
    @DisplayName("TC-0472-BERSA: Transferencias Programadas (Fecha especifica) CBU - terceros mismo banco  USD - NO AGENDADOS")
    public void TC0472BERSA() {
        try {
            //TEST
            loginSteps.iniciarSessionAlEscritorio(uBerhometest20);
            menuLateralSteps.clickTransferencias("","");
            //transferenciasInmediatas.seleecionarTarjeaAOperar("2005");
            transferenciasInmediatas.seleccionarCuentaOrigen("CA USD 007006689071");
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
    @Category({Criticos.class})
    @DisplayName("TC-0484-BERSA: Transferencias Programadas (Fecha especifica) CVU - terceros $ NO AGENDADOS")
    public void TC0484BERSA() {
        try {
            testCaseData.cargaDeDatosTest(uEcarina27);
            loginSteps.iniciarSessionAlEscritorioConPass(uEcarina27,testCaseData.password);
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("0000001700300000000313");
            //
            menuLateralSteps.clickTransferencias("","");
            transferenciasInmediatas.seleccionarCuentaOrigen("CA $ 020000052210");
            transferenciasInmediatas.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatas.completarInputAliasCbu("0000001700300000000313");
            transferenciasInmediatas.clickBuscarGenerico();
            transferenciasInmediatas.botonSonCorrectos();
            transferenciasInmediatas.setImporteGenerico("1");
            transferenciasInmediatas.setDescripcion("TC0484");
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
    @DisplayName("TC-0473-BERSA: Transferencias Programadas (Fecha especifica) CBU - terceros otros bancos $ - NO AGENDADOS")
    public void TC0473BERSA() {
        try {
            //TEST
            testCaseData.cargaDeDatosTest(uBerhometest20);
            loginSteps.iniciarSessionAlEscritorioConPass(uBerhometest20,testCaseData.password);
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("3300001320010001101040");
            //
            menuLateralSteps.clickTransferencias("","");
            //transferenciasInmediatas.seleecionarTarjeaAOperar("2005");
            transferenciasInmediatas.seleccionarCuentaOrigen("CC $ 007000094026");
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
    @DisplayName("TC-0474-BERSA: Transferencias Programadas (Fecha especifica) CBU - terceros otros bancos USD - NO AGENDADOS")
    public void TC0474BERSA() {
        try {
            //TEST
            loginSteps.iniciarSessionAlEscritorio(uBerhometest20);
            menuLateralSteps.clickTransferencias("","");
            //transferenciasInmediatas.seleecionarTarjeaAOperar("2005");
            transferenciasInmediatas.seleccionarCuentaOrigen("CA USD 007006689071");
            transferenciasInmediatas.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatas.completarInputAliasCbu("3300520945200000504105");
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
    @DisplayName("TC-0485-BERSA: Transferencias Programadas (Fecha especifica) ALIAS de CVU - terceros $ NO AGENDADOS")
    public void TC0485BERSA() {
        try {
            testCaseData.cargaDeDatosTest(uEcarina27);
            loginSteps.iniciarSessionAlEscritorioConPass(uEcarina27,testCaseData.password);
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("BOTE.FILA.MITO");
            //
            menuLateralSteps.clickTransferencias("","");
            transferenciasInmediatas.seleccionarCuentaOrigen("CA $ 020000052210");
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

    @Test
    @Category({Baja.class})
    @DisplayName("TC-0475-BERSA: Transferencias Programadas (Fecha especifica) CBU - terceros otros bancos INTERBANK (No PETERSEN) $ - NO AGENDADOS")
    public void TC0475BERSA() {
        try {
            //TEST
            loginSteps.iniciarSessionAlEscritorio(uBerhometest20);
            menuLateralSteps.clickTransferencias("","");
            //transferenciasInmediatas.seleecionarTarjeaAOperar("2005");
            transferenciasInmediatas.seleccionarCuentaOrigen("CC $ 007000094026");
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
    @DisplayName("TC-0476-BERSA: Transferencias Programadas (Fecha especifica) CBU - terceros otros bancos INTERBANK (No PETERSEN) USD - NO AGENDADOS")
    public void TC0476BERSA() {
        try {
            //TEST
            loginSteps.iniciarSessionAlEscritorio(uBerhometest20);
            menuLateralSteps.clickTransferencias("","");
            //transferenciasInmediatas.seleecionarTarjeaAOperar("2005");
            transferenciasInmediatas.seleccionarCuentaOrigen("CA USD 007006689071");
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
    @DisplayName("TC-0477-BERSA: Transferencias Programadas (Fecha especifica) ALIAS - terceros mismo banco  $ - NO AGENDADOS")
    public void TC0477BERSA() {
        try {
            //TEST
            testCaseData.cargaDeDatosTest(uBerhometest20);
            loginSteps.iniciarSessionAlEscritorioConPass(uBerhometest20,testCaseData.password);
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("GLOBO.FIESTA.PAZ");
            //
            menuLateralSteps.clickTransferencias("","");
            //transferenciasInmediatas.seleecionarTarjeaAOperar("2005");
            transferenciasInmediatas.seleccionarCuentaOrigen("CC $ 007000094026");
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
    @Category({Alta.class})
    @DisplayName("TC-0478-BERSA: Transferencias Programadas (Fecha especifica) ALIAS - terceros mismo banco  USD - NO AGENDADOS")
    public void TC0478BERSA() {
        try {
            //TEST
            loginSteps.iniciarSessionAlEscritorio(uBerhometest20);
            menuLateralSteps.clickTransferencias("","");
            //transferenciasInmediatas.seleecionarTarjeaAOperar("2005");
            transferenciasInmediatas.seleccionarCuentaOrigen("CA USD 007006689071");
            transferenciasInmediatas.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatas.completarInputAliasCbu("lyjk4.3lexT.9yBh0");
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
    @DisplayName("TC-0479-BERSA: Transferencias Programadas (Fecha especifica) ALIAS - terceros otros bancos $ - NO AGENDADOS")
    public void TC0479BERSA() {
        try {
            //TEST
            loginSteps.iniciarSessionAlEscritorio(uBerhometest20);
            menuLateralSteps.clickTransferencias("","");
            //transferenciasInmediatas.seleecionarTarjeaAOperar("2005");
            transferenciasInmediatas.seleccionarCuentaOrigen("CC $ 007000094026");
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
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Media.class})
    @DisplayName("TC-0480-BERSA: Transferencias Programadas (Fecha especifica) ALIAS - terceros otros bancos USD - NO AGENDADOS")
    public void TC0480BERSA() {
        try {
            //TEST
            loginSteps.iniciarSessionAlEscritorio(uBerhometest20);
            menuLateralSteps.clickTransferencias("","");
            //transferenciasInmediatas.seleecionarTarjeaAOperar("2005");
            transferenciasInmediatas.seleccionarCuentaOrigen("CA USD 007006689071");
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
        //cerrarConexion();
    }
}
