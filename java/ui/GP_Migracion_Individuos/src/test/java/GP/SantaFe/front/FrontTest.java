package GP.SantaFe.front;

import GP.Tags.Alta;
import GP.Tags.Baja;
import GP.Tags.Criticos;
import GP.Tags.Media;
import api.EcheqAPI;
import api.LoginAPI;
import db.ConexionDB;
import enums.inversiones.fci.FiltroMonedaFci;
import enums.inversiones.fci.FiltroRiesgoFci;
import io.qameta.allure.Allure;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.*;
import org.junit.experimental.categories.Category;
import org.mozilla.javascript.tools.shell.Global;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.botonesgenericos.BotonesGenericosPage;
import pages.cuentas.echeq.EcheqPage;
import steps.administracion_de_productos.AdministracionDeProductosSteps;
import steps.baja_arrepentimiento.BajaArrepentimientoSteps;
import steps.bonos_de_salud.BonosDeSaludSteps;
import steps.botonesgenericos.BotonesGenericosSteps;
import steps.calendarios.CalendarioSteps;
import steps.cuentas.CuentasSteps;
import steps.cuentas.adelantosDeSueldo.AdelantosDeSueldoSteps;
import steps.cuentas.adherirnuevodestinatario.AdherirNuevoDestinatarioSteps;
import steps.cuentas.agendadedestinatarios.AgendaDeDesetinatariosSteps;
import steps.cuentas.cuentas_asociadas.CuentasAsociadasSteps;
import steps.cuentas.cuotificalo.CuotificaloSteps;
import steps.cuentas.detallecuenta.DetalleCuentaSteps;
import steps.cuentas.echeq.EcheqSteps;
import steps.cuentas.ordendeextraccionconsulta_generar.OrdenDeExtraccionConsulta_GenerarSteps;
import steps.doblefactor.DobleFactorSteps;
import steps.herramientas_de_accesibilidad.CuentasHerramientasAccesibilidadSteps;
import steps.herramientas_de_accesibilidad.HerramientasDeAccesibilidadSteps;
import steps.herramientas_de_accesibilidad.LoginHerramientasDeAccesibilidadSteps;
import steps.inversiones.InversionesSteps;
import steps.inversiones.compraventadedolares.CompraVentaDeDolaresSteps;
import steps.inversiones.cuentacomitente.CuentaComitenteSteps;
import steps.inversiones.fci.FondoComunInversionSteps;
import steps.inversiones.fci.RescateFCISteps;
import steps.inversiones.fci.SimularPerfilInversorSteps;
import steps.inversiones.fci.SuscripcionFCISteps;
import steps.inversiones.plazosfijos.ConsultaDeTasasSteps;
import steps.inversiones.plazosfijos.PlazosFijosSteps;
import steps.inversiones.plazosfijos.SolicitarPlazoFijoSteps;
import steps.login.LoginSteps;
import steps.login.PantallaPrincipalSteps;
import steps.mensaje.MensajeSteps;
import steps.menulateral.MenuLateralSteps;
import steps.menusuperior.MenuSuperiorSteps;
import steps.pagos.PagosSteps;
import steps.pagos.adherirservicio.AdherirServicioSteps;
import steps.pagos.comprobantes.ComprobantesSteps;
import steps.pagos.debincomprador.AdherirRecurrenciaSteps;
import steps.pagos.debincomprador.DebinCompradorSteps;
import steps.pagos.debinvendedor.DebinVendedorSteps;
import steps.pagos.nuevopago.NuevoPagoSteps;
import steps.pagos.serviciosadheridos.ServiciosAdheridosSteps;
import steps.perfil.PerfilSteps;
import steps.perfil.modificarsteps.ModificarPerfilSteps;
import steps.prestamos.PrestamosParaCancelarDeudasSteps;
import steps.prestamos.PrestamosSteps;
import steps.recargas.RecargasSteps;
import steps.recargas.consultarecargas.ConsultaRecargasSteps;
import steps.seguros.SegurosSteps;
import steps.tarjetasdecredito.TarjetasDeCreditoSteps;
import steps.tarjetasdedebito.TarjetasDeDebitoSteps;
import steps.tarjetasdedebito.consultacompras.ConsultaDeComprasSteps;
import steps.ticket.TicketSteps;
import steps.transferencias.TransferenciasSteps;
import steps.transferencias.agenda_nuevo_destinatario.AgendaNuevoDestinatarioSteps;
import steps.transferencias.consultacredin.ConsultaCredinSteps;
import steps.transferencias.generarcredin.GenerarCredinSteps;
import steps.transferencias.transferenciasinmediatas_programadas.TransferenciasInmediatasSteps;
import steps.turnosonline.TurnosOnlineSteps;
import steps.validaciondeidentidad.ValidacionDeIdentidadSteps;
import steps.utillogger.UtilLoggerSteps;
import utils.Constantes;
import utils.Settings;
import utils.TestCaseData;
import utils.UtilsGP;
import steps.cuentas.solicitarnuevacuenta.SolicitarNuevaCuentaSteps;



import java.awt.*;
import java.io.IOException;
import java.time.LocalDateTime;

import static db.ConexionDB.cerrarConexion;
import static utils.Constantes.*;
import static utils.UtilsGP.generarString;

public class FrontTest {
    private static final Logger log = LoggerFactory.getLogger(FrontTest.class);
    LoginSteps loginSteps = new LoginSteps();
    MenuLateralSteps menuLateralSteps = new MenuLateralSteps();
    BonosDeSaludSteps bonos = new BonosDeSaludSteps();
    AdelantosDeSueldoSteps adelantosDeSueldoSteps = new AdelantosDeSueldoSteps();
    CuentasAsociadasSteps cuentasAsociadasSteps= new CuentasAsociadasSteps();
    DobleFactorSteps dobleFactorSteps= new DobleFactorSteps();
    CuentasSteps cuentasSteps = new CuentasSteps();
    CalendarioSteps calendario = new CalendarioSteps();
    AgendaDeDesetinatariosSteps agendaDeDesetinatariosSteps = new AgendaDeDesetinatariosSteps();
    AdherirNuevoDestinatarioSteps adherirNuevoDestinatarioSteps = new AdherirNuevoDestinatarioSteps();
    OrdenDeExtraccionConsulta_GenerarSteps ordenDeExtraccionConsulta_generarSteps = new OrdenDeExtraccionConsulta_GenerarSteps();
    DetalleCuentaSteps detalleCuentaSteps = new DetalleCuentaSteps();
    EcheqSteps echeqSteps = new EcheqSteps();
    CuotificaloSteps cuotificaloSteps = new CuotificaloSteps();
    InversionesSteps inversionesSteps = new InversionesSteps();
    ConsultaDeTasasSteps consultaDeTasasSteps = new ConsultaDeTasasSteps();
    BajaArrepentimientoSteps bajaArrepentimientoSteps = new BajaArrepentimientoSteps();
    AdministracionDeProductosSteps administracionDeProductosSteps = new AdministracionDeProductosSteps();
    CompraVentaDeDolaresSteps compraVentaDeDolaresSteps = new CompraVentaDeDolaresSteps();
    CuentaComitenteSteps cuentaComitenteSteps= new CuentaComitenteSteps();
    FondoComunInversionSteps fondoComunInversionSteps = new FondoComunInversionSteps();
    SuscripcionFCISteps suscripcionFCISteps = new SuscripcionFCISteps();
    RescateFCISteps rescateFCISteps = new RescateFCISteps();
    PlazosFijosSteps plazosFijosSteps = new PlazosFijosSteps();
    SolicitarPlazoFijoSteps solicitarPlazoFijoSteps = new SolicitarPlazoFijoSteps();
    DebinCompradorSteps debinCompradorSteps = new DebinCompradorSteps();
    CalendarioSteps calendarioSteps = new CalendarioSteps();
    MenuSuperiorSteps menuSuperiorSteps = new MenuSuperiorSteps();
    PagosSteps pagosSteps = new PagosSteps();
    MensajeSteps mensajeSteps = new MensajeSteps();
    DebinVendedorSteps debinVendedorSteps = new DebinVendedorSteps();
    AdherirRecurrenciaSteps adherirRecurrenciaSteps = new AdherirRecurrenciaSteps();
    AdherirServicioSteps adherirServicioSteps = new AdherirServicioSteps();
    ServiciosAdheridosSteps serviciosAdheridosSteps = new ServiciosAdheridosSteps();
    NuevoPagoSteps nuevoPagoSteps = new NuevoPagoSteps();
    RecargasSteps recargasSteps = new RecargasSteps();
    PerfilSteps perfilSteps = new PerfilSteps();
    ModificarPerfilSteps modificarPerfilSteps = new ModificarPerfilSteps();
    PrestamosSteps prestamosSteps = new PrestamosSteps();
    PrestamosParaCancelarDeudasSteps prestamosParaCancelarDeudasSteps= new PrestamosParaCancelarDeudasSteps();
    ConsultaRecargasSteps consultaRecargasSteps = new ConsultaRecargasSteps();
    SegurosSteps segurosSteps = new SegurosSteps();
    TarjetasDeCreditoSteps tarjetasDeCreditoSteps = new TarjetasDeCreditoSteps();
    TarjetasDeDebitoSteps tarjetasDeDebitoSteps = new TarjetasDeDebitoSteps();
    ConsultaDeComprasSteps consultaDeComprasSteps= new ConsultaDeComprasSteps();
    TransferenciasInmediatasSteps transferenciasInmediatas = new TransferenciasInmediatasSteps();
    AgendaNuevoDestinatarioSteps agendaNuevoDestinatarioSteps = new AgendaNuevoDestinatarioSteps();
    GenerarCredinSteps generarCredinSteps = new GenerarCredinSteps();
    ConsultaCredinSteps consultaCredinSteps = new ConsultaCredinSteps();
    TransferenciasSteps transferenciasSteps = new TransferenciasSteps();
    TicketSteps ticketSteps = new TicketSteps();
    BotonesGenericosPage botonesGenericosPage = new BotonesGenericosPage();
    BotonesGenericosSteps botonesGenericosSteps = new BotonesGenericosSteps();
    TurnosOnlineSteps turnosOnlineSteps = new TurnosOnlineSteps();
    ComprobantesSteps comprobantesSteps = new ComprobantesSteps();
    PantallaPrincipalSteps pantallaPrincipalSteps = new PantallaPrincipalSteps();
    SimularPerfilInversorSteps simularPerfilInversorSteps = new SimularPerfilInversorSteps();
    UtilsGP utilsGP = new UtilsGP();
    TestCaseData testCaseData = new TestCaseData();
    EcheqAPI echeqAPI = new EcheqAPI();
    LoginHerramientasDeAccesibilidadSteps loginHerramientasDeAccesibilidadSteps= new LoginHerramientasDeAccesibilidadSteps();
    ValidacionDeIdentidadSteps validacionDeIdentidadSteps = new ValidacionDeIdentidadSteps();
    HerramientasDeAccesibilidadSteps herramientasDeAccesibilidadSteps= new HerramientasDeAccesibilidadSteps();
    CuentasHerramientasAccesibilidadSteps cuentasHerramientasAccesibilidadSteps= new CuentasHerramientasAccesibilidadSteps();
    UtilLoggerSteps utilLoggerSteps = new UtilLoggerSteps();
    SolicitarNuevaCuentaSteps solicitarNuevaCuentaSteps = new SolicitarNuevaCuentaSteps();

    /**
     *Metodo que realiza la conexión a BD y seteo de datos para los casos de pruebas.
     *
     *@return objeto Json con los datos necesarios para cada uno de los test.
     */
    @BeforeClass
    public static void extraerDatosBD(){
        System.out.println("============= BEFORE CLASS ==========================");
        if(Constantes.usuarios == null){
            System.out.println("============= Array de datos vacio extraer desde la BD ==============");
            Settings.dataTestCase = ConexionDB.usuariosDB();
            System.out.println("============= se completo el array de datos ==========================");
        }else {
            System.out.println("================ EL ARRAY CONTIENE DATOS ===================");
        }
    }
    /**
     * Casos de pruebas Sección Bonos de Salud.
     *
     * Se necesita que los usuarios en esta sección esten pre-calificados para la operatoria.
     * El setting del ambiente para esta sección depende de la Sección "Pagos" - Servicio por SOAP (variables con valor "false" en BO - CHANELS HB MHB).
      */
    //@Test NO SE CORRE YA QUE SE CANCELO EL CONVENIO CON EL BANCO
    @DisplayName("TC -0081 BSF - Bonos de salud-  Consultar compras")
    public void TC0081BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC0081BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.BONOS_SALUD);
            bonos.validarTituloPageGenerico("Compra de bonos de salud");
            bonos.clickConsultarCompras();
            bonos.clickDesplegableTarjetaDebito();
            bonos.seleccionarDropdownGenerico(testCaseData.tarjetaDebito);
            UtilsGP.fixedWait(3000);
            bonos.clickDesplegableEnte();
            bonos.seleccionarDropdownGenerico("IAPOS");
            bonos.clickDesplegableCanal();
            bonos.seleccionarDropdownGenerico("HOMEBANKING");
            bonos.clickCalendarioDesde();
            calendario.clickMesAnterior(6);
            calendario.clickEnDiaXParametro("1");
            bonos.clickDesplegableNumeroDeAfiliado();
            bonos.seleccionarDropdownGenerico("10018054");
            calendario.clickBuscar();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    //@Test NO SE CORRE YA QUE SE CANCELO EL CONVENIO CON EL BANCO
    @DisplayName("TC-0091 BSF - Bonos de salud - Adhesiones - Afiliados adheridos-")
    public void TC0091BSF() {
        try {
            //PRECONDICION
            testCaseData.cargaDeDatosTests("TC0091BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.BONOS_SALUD);
            //bonos.realizarAdhesionAPI("IAPOS","30379884","AUTOMATIZACION");
            //TEST
            bonos.validarTituloPageGenerico("Compra de bonos de salud");
            bonos.clickAdhesiones();
            bonos.validarTituloPageGenerico("Afiliados adheridos");
            bonos.clickDesplegableEnte();
            bonos.keyDownToText("IAPOS");
            bonos.clickBuscar();
            bonos.validarTablaAfiliados();
            //POSTCONDICION
            //bonos.eliminarAdhesionAPI("IAPOS","30379884","AUTOMATIZACION");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    /**
     * Casos de pruebas sección Cuentas - Echeqs
     *
     * Se necesita que los usuarios en esta sección esten pre-calificados para la operatoria.
     */
    //@Test Sew unifica con TC0026
    @DisplayName("TC-0025-BSF: Echeq - Completitud de pantalla con filtro: Estado = Todos")
    public void TC0025BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC0025BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.ECHEQS);
            echeqSteps.validarTituloPageGenerico("ECheq");
            transferenciasInmediatas.clickbtnFiltrar();
            echeqSteps.clickEstado();
            botonesGenericosSteps.clickListaDesplegableGenerico("Todos");
            echeqSteps.clickBotonAplicarBusqueda();
            echeqSteps.validarCompletitudPantalla();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0026-BSF: Echeq - Consulta de Echeq - Verificar detalle de echeq - Estado = Todos")
    public void TC0026BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC0026BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.ECHEQS);
            echeqSteps.validarTituloPageGenerico("ECheq");
            transferenciasInmediatas.clickbtnFiltrar();
            echeqSteps.clickEstado();
            botonesGenericosSteps.seleccionarDropdownGenerico("Todos");
            echeqSteps.clickBotonAplicarBusqueda();
            echeqSteps.clickVerDetallePrimerEcheq();
            echeqSteps.validarTituloPageGenerico("Detalle de ECHEQ");
            echeqSteps.validarTituloPageGenerico("Id Echeq");
            echeqSteps.validarTituloPageGenerico("Nro. ECheq");
            echeqSteps.validarTituloPageGenerico("Estado");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }


    /**
     * Casos de pruebas seccion Cuentas
     */
    @Test
    @DisplayName("TC-0040-BSF: Acceder a Consulta de movimientos de cuenta")
    public void TC0040BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC0040BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.CUENTA);
            cuentasSteps.validarTituloPageGenerico("Cuentas");
            cuentasSteps.clickCancelarGenerico();
            cuentasSteps.clickVerMasXnroCuenta(testCaseData.cuentaOrigen);
            detalleCuentaSteps.clickDetalleMovimientoPorNroFila("1");
            detalleCuentaSteps.validarTituloPageGenerico2("Fecha");
            detalleCuentaSteps.validarTituloPageGenerico2("Concepto");
            detalleCuentaSteps.validarTituloPageGenerico2("Monto");
//            detalleCuentaSteps.validarTituloPageGenerico2("Saldo");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0041-BSF: Filtrar Movimientos por Tipo en una cuenta. Elegir Todos")
    public void TC0041BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC0041BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.ESCRITORIO);
            loginSteps.validarTituloPageGenerico("Hola");
            menuLateralSteps.clickCuentas();
            cuentasSteps.validarTituloPageGenerico("Cuentas");
            cuentasSteps.clickCancelarGenerico();
            cuentasSteps.clickVerMas();
            cuentasSteps.clickVerMasXnroCuenta(testCaseData.cuentaOrigen);
            detalleCuentaSteps.clickMasFiltros();
            calendarioSteps.clickEnFechaDeCreacionDesde();
            calendarioSteps.clickMesAnterior(3);
            calendarioSteps.clickEnDiaXParametro("8");
            detalleCuentaSteps.clickBuscarPor();
            detalleCuentaSteps.seleccionarDropdownGenerico("Tipo de transacción");
            detalleCuentaSteps.clickBuscar();
            detalleCuentaSteps.validarMovimiento();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-128241-BSF: Acceder a Consulta de movimientos de cuenta")
    public void TC128241BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC128241BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.CUENTA);
            cuentasSteps.validarTituloPageGenerico("Cuentas");
            cuentasSteps.clickCancelarGenerico();
            cuentasSteps.clickVerMas();
            cuentasSteps.clickVerMasXnroCuenta(testCaseData.cuentaOrigen);
            UtilsGP.fixedWait(4000);
            detalleCuentaSteps.clickModificarAlias();
            botonesGenericosSteps.validarTituloPageGenerico("Modificar Alias");
            detalleCuentaSteps.validarBotonDeshabilitadoyGris();
            detalleCuentaSteps.validarBotonDeshabilitadoyGrisCaracteres();
            detalleCuentaSteps.validarVeinteCaracteres();
            detalleCuentaSteps.validarAliasInvalido();
            detalleCuentaSteps.validarIngresoInvalido();

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-29410-BSF: Web-Botón continuar deshabilitado-CA pesos")
    public void TC29410BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC29410BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.CUENTA);
            cuentasSteps.validarTituloPageGenerico("Cuentas");
            cuentasSteps.clickCancelarGenerico();
            cuentasSteps.clickBtnSolicitarNuevaCta("Solicitar nueva cuenta");
            cuentasSteps.validarBtnDeshabilitado();
            solicitarNuevaCuentaSteps.clickTipoDeCuenta();
            botonesGenericosSteps.clickListaDesplegableGenerico("Caja de Ahorro");
            solicitarNuevaCuentaSteps.clickMonedaDeLaCuenta();
            botonesGenericosSteps.clickListaDesplegableGenerico("Pesos");
            botonesGenericosSteps.validarBotonHabilitado("Continuar");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-29411-BSF: Web-Botón continuar deshabilitado-CA dolares")
    public void TC29411BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC29411BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.CUENTA);
            cuentasSteps.validarTituloPageGenerico("Cuentas");
            cuentasSteps.clickCancelarGenerico();
            cuentasSteps.clickBtnSolicitarNuevaCta("Solicitar nueva cuenta");
            cuentasSteps.validarBtnDeshabilitado();
            solicitarNuevaCuentaSteps.clickTipoDeCuenta();
            botonesGenericosSteps.clickListaDesplegableGenerico("Caja de Ahorro");
            solicitarNuevaCuentaSteps.clickMonedaDeLaCuenta();
            botonesGenericosSteps.clickListaDesplegableGenerico("Dólares");
            botonesGenericosSteps.validarBotonHabilitado("Continuar");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-29412-BSF: Web-Botón continuar deshabilitado-CC pesos")
    public void TC29412BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC29412BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.CUENTA);
            cuentasSteps.validarTituloPageGenerico("Cuentas");
            cuentasSteps.clickCancelarGenerico();
            cuentasSteps.clickBtnSolicitarNuevaCta("Solicitar nueva cuenta");
            cuentasSteps.validarBtnDeshabilitado();
            solicitarNuevaCuentaSteps.clickTipoDeCuenta();
            botonesGenericosSteps.clickListaDesplegableGenerico("Cuenta Corriente");
            solicitarNuevaCuentaSteps.clickMonedaDeLaCuenta();
            botonesGenericosSteps.clickListaDesplegableGenerico("Pesos");
            botonesGenericosSteps.validarBotonHabilitado("Continuar");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    /**
     * Casos de pruebas sección Cuentas - Adelanto de Sueldo
     *
     * Se necesita que los usuarios en esta sección esten pre-calificados para la operatoria.
     */
    //@Test Se valida con otro TC - SE APAAGO EL CASO
    @DisplayName("TC-0239-BSF: Adelantos de Sueldo - Realizar solicitud de adelanto de sueldo - Validar campos requeridos")
    public void TC0239BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC0239BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.CUENTA);
            adelantosDeSueldoSteps.validarTituloPageGenerico("Cuentas");
            adelantosDeSueldoSteps.clickVerMasXnroCuenta(testCaseData.cuentaOrigen);
            adelantosDeSueldoSteps.clickMasOpcionesGenerico();
            adelantosDeSueldoSteps.clickSolicitudAdelantoDeSueldo();
            adelantosDeSueldoSteps.clickContinuarGenerico();
            adelantosDeSueldoSteps.validarCampoObligatorioGenerico();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    //@Test Se APAGA POR REVISION DE SECCION - DEPENDE DE DATOS -APAGAR EN FRONT
    @DisplayName("TC-12978-BSF: OFERTA - Adelanto de Sueldo - Acceso Directo")
    public void TC12978BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC12978BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.ESCRITORIO);
            adelantosDeSueldoSteps.validarTituloPageGenerico("Hola");
            adelantosDeSueldoSteps.clickAccesoDirectoAdelantoSueldo();
            adelantosDeSueldoSteps.validarTituloPageGenerico("Adelanto de haberes");
            adelantosDeSueldoSteps.clickCardAdelantoSueldo();
            adelantosDeSueldoSteps.setCuantoNecesitas("10000");
            adelantosDeSueldoSteps.clickContinuarGenerico();
            adelantosDeSueldoSteps.validacionCamposConfirmacion();
            adelantosDeSueldoSteps.clickCheckTyC();
            adelantosDeSueldoSteps.validarBotonConfirmar();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0222-BSF: Adelantos de Sueldo - Solicitud de Adelanto de Sueldo - Validar Pantalla completa")
    public void TC0222BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC0222BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.ESCRITORIO);
            adelantosDeSueldoSteps.validarTituloPageGenerico("Hola");
            menuLateralSteps.clickCuentas();
            adelantosDeSueldoSteps.clickCancelarGenerico();
            adelantosDeSueldoSteps.clickVerMas();
            adelantosDeSueldoSteps.clickVerMasXnroCuenta(testCaseData.cuentaOrigen);
            adelantosDeSueldoSteps.clickMasOpcionesGenerico();
            adelantosDeSueldoSteps.clickSolicitudAdelantoDeSueldo();
            adelantosDeSueldoSteps.validacionPantallaSolicitudAH();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-0225-BSF: Adelantos de Sueldo - Realizar Solicitud de adelanto de sueldo - Cancelar operacion al presionar volver - Validar pantalla de solicitud de adelanto de sueldo")
    public void TC0225BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC0225BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.CUENTA);
            adelantosDeSueldoSteps.validarTituloPageGenerico("Cuentas");
            adelantosDeSueldoSteps.clickCancelarGenerico();
            adelantosDeSueldoSteps.clickVerMas();
            adelantosDeSueldoSteps.clickVerMasXnroCuenta(testCaseData.cuentaOrigen);
            adelantosDeSueldoSteps.clickMasOpcionesGenerico();
            adelantosDeSueldoSteps.clickSolicitudAdelantoDeSueldo();
            adelantosDeSueldoSteps.setCuantoNecesitas("10000");
            adelantosDeSueldoSteps.clickContinuarGenerico();
            adelantosDeSueldoSteps.clickVolverFlechaGenerico();
            adelantosDeSueldoSteps.validacionPantallaSolicitudAH();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-0226-BSF: Adelantos de Sueldo - Pantalla de confirmacion de solicitud - Confirmar sin seleccionar valores requeridos - validar campos obligatorios")
    public void TC0226BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC0226BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.CUENTA);
            adelantosDeSueldoSteps.validarTituloPageGenerico("Cuentas");
            adelantosDeSueldoSteps.clickCancelarGenerico();
            adelantosDeSueldoSteps.clickVerMas();
            adelantosDeSueldoSteps.clickVerMasXnroCuenta(testCaseData.cuentaOrigen);
            adelantosDeSueldoSteps.clickMasOpcionesGenerico();
            adelantosDeSueldoSteps.clickSolicitudAdelantoDeSueldo();
            adelantosDeSueldoSteps.setCuantoNecesitas("10000");
            adelantosDeSueldoSteps.clickContinuarGenerico();
            adelantosDeSueldoSteps.clickConfirmarGenerico();
            adelantosDeSueldoSteps.validarCamposRequeridos("Leer y aceptar los términos y condiciones es obligatorio para continuar");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-0227-BSF: Adelantos de Sueldo - Solicitud de Adelanto de Sueldo - Resumen de contrato - Validar redireccion a documento pdf")
    public void TC0227BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC0227BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.CUENTA);
            adelantosDeSueldoSteps.validarTituloPageGenerico("Cuentas");
            adelantosDeSueldoSteps.clickCancelarGenerico();
            adelantosDeSueldoSteps.clickVerMas();
            adelantosDeSueldoSteps.clickVerMasXnroCuenta(testCaseData.cuentaOrigen);
            adelantosDeSueldoSteps.clickMasOpcionesGenerico();
            adelantosDeSueldoSteps.clickSolicitudAdelantoDeSueldo();
            adelantosDeSueldoSteps.clickResumenDeContrato();
            prestamosSteps.clickAceptarGenerico();
            prestamosSteps.validarFragmentoURL("assets");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-0228-BSF: Adelantos de Sueldo - Resumen de contrato - Cancelar redireccion a documento pdf - validar volver a la pagina de solicitud de adelanto de sueldo")
    public void TC0228BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC0228BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.CUENTA);
            adelantosDeSueldoSteps.validarTituloPageGenerico("Cuentas");
            adelantosDeSueldoSteps.clickCancelarGenerico();
            adelantosDeSueldoSteps.clickVerMas();
            adelantosDeSueldoSteps.clickVerMasXnroCuenta(testCaseData.cuentaOrigen);
            adelantosDeSueldoSteps.clickMasOpcionesGenerico();
            adelantosDeSueldoSteps.clickSolicitudAdelantoDeSueldo();
            adelantosDeSueldoSteps.clickResumenDeContrato();
            adelantosDeSueldoSteps.clickCancelarGenerico();
            adelantosDeSueldoSteps.validacionPantallaSolicitudAH();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-0237-BSF: Adelantos de Sueldo - Realizar solicitud de adelanto de sueldo  - Validar mensaje de error ingreso de monto MENOR al minimo")
    public void TC0237BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC0237BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.CUENTA);
            adelantosDeSueldoSteps.validarTituloPageGenerico("Cuentas");
            adelantosDeSueldoSteps.clickCancelarGenerico();
            adelantosDeSueldoSteps.clickVerMasXnroCuenta(testCaseData.cuentaOrigen);
            adelantosDeSueldoSteps.clickMasOpcionesGenerico();
            adelantosDeSueldoSteps.clickSolicitudAdelantoDeSueldo();
            adelantosDeSueldoSteps.setCuantoNecesitas("0");
            adelantosDeSueldoSteps.clickContinuarGenerico();
            adelantosDeSueldoSteps.validarAlertMontoPermitido();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-0238-BSF: Adelantos de Sueldo - Realizar solicitud de adelanto de sueldo  - Validar mensaje de error ingreso de monto MAYOR al maximo")
    public void TC0238BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC0238BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.CUENTA);
            adelantosDeSueldoSteps.validarTituloPageGenerico("Cuentas");
            adelantosDeSueldoSteps.clickCancelarGenerico();
            adelantosDeSueldoSteps.clickVerMas();
            adelantosDeSueldoSteps.clickVerMasXnroCuenta(testCaseData.cuentaOrigen);
            adelantosDeSueldoSteps.clickMasOpcionesGenerico();
            adelantosDeSueldoSteps.clickSolicitudAdelantoDeSueldo();
            adelantosDeSueldoSteps.setCuantoNecesitas("9999999");
            adelantosDeSueldoSteps.clickContinuarGenerico();
            adelantosDeSueldoSteps.validarAlertMontoPermitido();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    /**
     * Casos de pruebas de la sección Inversiones - Fondos Común de Inversión
     *
     * Se necesita tener usuario con cuenta comitentes y perfil de inversión realizado.
     */
    //@Test En BSF no se corre porque tenemos un lapso de 24 para que se apreuebe (dato especial)
    @DisplayName("TC-0117-BSF: FCI- Filtros - Mi Tenencia - Fondos por Riesgo")
    public void TC0117BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC0117BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.FCI);
            fondoComunInversionSteps.validarTituloPageGenerico("Información de fondos");
            //fondoComunInversionSteps.selectorCuentaComitente("2-000024701");
            fondoComunInversionSteps.seleccionarUnicamenteFiltrosPorRiesgo(
                    FiltroRiesgoFci.CONSERVADOR,
                    FiltroRiesgoFci.MODERADO,
                    FiltroRiesgoFci.DINAMICO
            );
            fondoComunInversionSteps.validaListadoFCI();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0118-BSF: FCI- Filtros - Mi Tenencia - Fondos por Riesgo - Conservador")
    public void TC0118BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC0118BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.FCI);
            fondoComunInversionSteps.validarTituloPageGenerico("Información de fondos");
            //fondoComunInversionSteps.selectorCuentaComitente("2-000024678");
            fondoComunInversionSteps.seleccionarUnicamenteFiltrosPorRiesgo(FiltroRiesgoFci.CONSERVADOR);
            fondoComunInversionSteps.validaFondosConversadorVisibleEnLista("Conservador");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0119-BSF: FCI- Filtros - Mi Tenencia - Fondos por Riesgo - Moderado")
    public void TC0119BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC0119BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.FCI);
            fondoComunInversionSteps.validarTituloPageGenerico("Información de fondos");
            //fondoComunInversionSteps.selectorCuentaComitente("2-000024678");
            fondoComunInversionSteps.seleccionarUnicamenteFiltrosPorRiesgo(FiltroRiesgoFci.MODERADO);
            fondoComunInversionSteps.validaFondosPorRiesgoVisiblesEnListado("Moderado");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0120-BSF: FCI- Filtros - Mi Tenencia - Fondos por Riesgo - Dinámico")
    public void TC0120BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC0120BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.FCI);
            fondoComunInversionSteps.validarTituloPageGenerico("Información de fondos");
            //fondoComunInversionSteps.selectorCuentaComitente("2-000024701");
            fondoComunInversionSteps.seleccionarUnicamenteFiltrosPorRiesgo(FiltroRiesgoFci.DINAMICO);
            fondoComunInversionSteps.validaFondosPorRiesgoVisiblesEnListado("Dinamico");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-0126-BSF: FCI - Filtros: Fondos por moneda - Pesos")
    public void TC0126BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC0126BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.FCI);
            fondoComunInversionSteps.validarTituloPageGenerico("Información de fondos");
            //menuLateralSteps.clickInversiones();
            //inversionesSteps.clickFondoComunDeInversion();
            fondoComunInversionSteps.seleccionarUnicamenteFiltrosPorMoneda(FiltroMonedaFci.PESOS);
            fondoComunInversionSteps.validaFondosPorMonedaEnListado("pesos");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0127-BSF: FCI - Filtros: Fondos por moneda - Dólar")
    public void TC0127BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC0127BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.FCI);
            fondoComunInversionSteps.validarTituloPageGenerico("Información de fondos");
            fondoComunInversionSteps.seleccionarUnicamenteFiltrosPorMoneda(FiltroMonedaFci.DOLARES);
            fondoComunInversionSteps.validaFondosPorMonedaEnListado("dolar");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    
    @Test
    @DisplayName("TC-0128-BSF: FCI - Filtros: Mi Tenencia")
    public void TC0128BSF() {
        try {
           testCaseData.cargaDeDatosTests("TC0128BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.FCI);
            fondoComunInversionSteps.validarTituloPageGenerico("Información de fondos");
            fondoComunInversionSteps.clickLabelMisFondos();
            fondoComunInversionSteps.validaListadoFCI();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC- 0173- BSF-  Inversiones- Alta cuenta comitente- Solicitar cuenta comitente  - Validar pantalla etapa '5 Confirmacion'")
    public void TC0173BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC0173BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.FCI);
            fondoComunInversionSteps.validarTituloPageGenerico("Información de fondos");
            inversionesSteps.clickSolicitarNuevaCuentaComitente();
            cuentaComitenteSteps.clickSolicitarCuenta();
            cuentaComitenteSteps.validarTituloPageGenerico("Apertura de cuenta comitente");
            cuentaComitenteSteps.clickContinuarGenerico();
            cuentaComitenteSteps.clickCheckboxNingunaDeLasAnteriores();
            cuentaComitenteSteps.clickContinuarGenerico();
            cuentaComitenteSteps.clickCheckboxTerminosyCondiciones();
            cuentaComitenteSteps.clickContinuarGenerico();
            simularPerfilInversorSteps.completarFormularioDeSimulacion();
            cuentaComitenteSteps.clickContinuarGenerico();
           // cuentaComitenteSteps.clickAceptarTyCGenerico();
           cuentaComitenteSteps.clickConfirmarGenerico();
            cuentaComitenteSteps.validarNumyTextDeEtapa("5", "Confirmación");
            cuentaComitenteSteps.validarTituloPageGenerico("Apertura de cuenta comitente");
            cuentaComitenteSteps.validarTexto("Estás por confirmar la solicitud de Alta de Cuenta Comitente");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-0133-BSF: FCI  - Mas información - Aceptar Modal")
    public void TC0133BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC0133BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.FCI);
            fondoComunInversionSteps.validarTituloPageGenerico("Información de fondos");
            fondoComunInversionSteps.clickIconoAccionesDetalleFCI("A-DELTA AHORRO");
            fondoComunInversionSteps.clickMasInformacion();
            fondoComunInversionSteps.clickAceptar();
            utilsGP.ManageTab();
            utilsGP.backMainTap();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-0134-BSF: FCI  - Mas información - Rechazar Modal")
    public void TC0134BSF() {
        try {
           testCaseData.cargaDeDatosTests("TC0134BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.FCI);
            fondoComunInversionSteps.validarTituloPageGenerico("Información de fondos");
            fondoComunInversionSteps.clickIconoAccionesDetalleFCI("A-DELTA AHORRO");
            fondoComunInversionSteps.clickMasInformacion();
            fondoComunInversionSteps.clickCancelarGenerico();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    /**
     * Casos de pruebas de la sección Inversiones - Plazo Fijo
     *
     */

    @Test
    @DisplayName("TC-0044-BSF: PF - Consulta de Tasas Tipo - Tradicional en Pesos")
    public void TC0044BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC0044BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.PLAZO_FIJO);
            fondoComunInversionSteps.validarTituloPageGenerico("Plazo fijo");
            plazosFijosSteps.clickConsultaTasas();
            consultaDeTasasSteps.validarTituloPageGenerico("Consulta de Tasas");
            consultaDeTasasSteps.clickSelectorTipoPlazoFijo();
            consultaDeTasasSteps.seleccionarDropdownGenerico( "Plazo Fijo Tradicional en Pesos");
            consultaDeTasasSteps.clickSelectorTipoConsulta();
            UtilsGP.fixedWait(8000);
            consultaDeTasasSteps.seleccionarDropdownGenerico("Monto");
            consultaDeTasasSteps.setImporteGenerico("120000");
            consultaDeTasasSteps.clickBuscar();
            consultaDeTasasSteps.validarPlazoDesde();
            consultaDeTasasSteps.validarPlazoHasta();
            consultaDeTasasSteps.validarTasaTNA();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0045-BSF: PF - Consulta de Tasas - Tipo - Tradicional en Dólares")
    public void TC0045BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC0045BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.PLAZO_FIJO);
            fondoComunInversionSteps.validarTituloPageGenerico("Plazo fijo");
            plazosFijosSteps.clickConsultaTasas();
            consultaDeTasasSteps.validarTituloPageGenerico("Consulta de Tasas");
            consultaDeTasasSteps.clickSelectorTipoPlazoFijo();
            consultaDeTasasSteps.seleccionarDropdownGenerico( "Plazo Fijo Tradicional en D");
            consultaDeTasasSteps.clickSelectorTipoConsulta();
            UtilsGP.fixedWait(8000);
           consultaDeTasasSteps.seleccionarDropdownGenerico("Monto");
            consultaDeTasasSteps.setImporteGenerico("120000");
            consultaDeTasasSteps.clickBuscar();
            consultaDeTasasSteps.validarPlazoDesde();
            consultaDeTasasSteps.validarPlazoHasta();
            consultaDeTasasSteps.validarTasaTNA();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0046 BSF - PF - Consulta de Tasas - Tipo - UVA")
    public void TC0046BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC0046BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.PLAZO_FIJO);
            fondoComunInversionSteps.validarTituloPageGenerico("Plazo fijo");
            plazosFijosSteps.clickConsultaTasas();
            consultaDeTasasSteps.validarTituloPageGenerico("Consulta de Tasas");
            consultaDeTasasSteps.clickSelectorTipoPlazoFijo();
            consultaDeTasasSteps.seleccionarDropdownGenerico( "Plazo Fijo UVA");
            consultaDeTasasSteps.clickSelectorTipoConsulta();
            UtilsGP.fixedWait(8000);
               consultaDeTasasSteps.seleccionarDropdownGenerico("Monto");
            consultaDeTasasSteps.setImporteGenerico("120000");
            consultaDeTasasSteps.clickBuscar();
            consultaDeTasasSteps.validarPlazoDesde();
            consultaDeTasasSteps.validarPlazoHasta();
            consultaDeTasasSteps.validarTasaTNA();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0047-BSF - PF - Consulta de Tasas - Tipo - UVA Precancelable")
    public void TC0047BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC0047BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.PLAZO_FIJO);
            fondoComunInversionSteps.validarTituloPageGenerico("Plazo fijo");
            plazosFijosSteps.clickConsultaTasas();
            consultaDeTasasSteps.validarTituloPageGenerico("Consulta de Tasas");
            consultaDeTasasSteps.clickSelectorTipoPlazoFijo();
            consultaDeTasasSteps.seleccionarDropdownGenerico( "Plazo Fijo UVA Precancelable");
            consultaDeTasasSteps.clickSelectorTipoConsulta();
            consultaDeTasasSteps.seleccionarDropdownGenerico("Monto");
            UtilsGP.fixedWait(80000);
            consultaDeTasasSteps.setImporteGenerico("120000");
            consultaDeTasasSteps.clickBuscar();
            consultaDeTasasSteps.validarPlazoDesde();
            consultaDeTasasSteps.validarPlazoHasta();
            consultaDeTasasSteps.validarTasaTNA();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-128244-BSF: Web - Alta de PF - Verificar lógica de control del botón Continuar y mensaje de error en Simulación y alta de PF")
    public void TC128244BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC128244BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.PLAZO_FIJO);
            fondoComunInversionSteps.validarTituloPageGenerico("Plazo fijo");
            plazosFijosSteps.clickSolicitarPlazoFijo();
            plazosFijosSteps.validarBotonDeshabilitado("Continuar");
            solicitarPlazoFijoSteps.clickSelectorTipoPF();
            solicitarPlazoFijoSteps.seleccionarDropdownGenerico("Plazo Fijo Tradicional en Pesos");
            solicitarPlazoFijoSteps.clickCampoImporte();
            solicitarPlazoFijoSteps.setImporteGenerico("999999999");
            solicitarPlazoFijoSteps.setPlazoEnDias("180");
            solicitarPlazoFijoSteps.clickSelectorCuentaDebito();
            solicitarPlazoFijoSteps.seleccionarDropdownGenerico(testCaseData.cuentaOrigen);
            solicitarPlazoFijoSteps.clickSelectorAccionVencimiento();
            solicitarPlazoFijoSteps.seleccionarDropdownGenerico("vencimiento");
            botonesGenericosPage.getContinuar().isEnabled();
            solicitarPlazoFijoSteps.clickContinuarGenerico();
            solicitarPlazoFijoSteps.validarTituloPageGenerico("Este monto supera tu saldo en cuenta. Ingresá un importe inferior.");
            plazosFijosSteps.validarBotonDeshabilitado("Continuar");
            solicitarPlazoFijoSteps.clearCampoImporte();
            solicitarPlazoFijoSteps.setImporteGenerico("999");
            botonesGenericosPage.getContinuar().isEnabled();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    /**
     * Casos de pruebas de la sección Inversiones - Compra y Venta de Dolares.
     *
     */

    @Test
    @DisplayName("TC- 0178- BSF-  Inversiones- Compra de moneda extranjera - Colocar importe 0 de compra de dolar - Validar mensaje 'No se pudo realizar la operación'")
    public void TC0178BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC0178BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.COMPRA_VENTA_USD);
            compraVentaDeDolaresSteps.validarTituloPageGenerico("Compra venta de d");
            compraVentaDeDolaresSteps.clickTipoDeOperacion();
            compraVentaDeDolaresSteps.seleccionarDropdownGenerico("Comprar");
            compraVentaDeDolaresSteps.clickCuentaOrigen();
            compraVentaDeDolaresSteps.seleccionarDropdownGenerico( testCaseData.cuentaOrigen);
            compraVentaDeDolaresSteps.clickCuentaDestino();
            compraVentaDeDolaresSteps.seleccionarDropdownGenerico("USD");
            compraVentaDeDolaresSteps.setMonto("0");
            compraVentaDeDolaresSteps.clickContinuarGenerico();
            compraVentaDeDolaresSteps.validacionMensaje("El monto ingresado debe ser mayor a 0");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC- 0179- BSF-  Inversiones- Compra de moneda extranjera - Seguir flujo de compra de dolar sin aceptar check terminos y condiciones - Validar campo obligatorio")
    public void TC0179BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC0179BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.COMPRA_VENTA_USD);
            compraVentaDeDolaresSteps.validarTituloPageGenerico("Compra venta de d");
            compraVentaDeDolaresSteps.clickTipoDeOperacion();
            compraVentaDeDolaresSteps.seleccionarDropdownGenerico( "Comprar");
            compraVentaDeDolaresSteps.clickCuentaOrigen();
            compraVentaDeDolaresSteps.seleccionarDropdownGenerico( testCaseData.cuentaOrigen);
            compraVentaDeDolaresSteps.clickCuentaDestino();
            compraVentaDeDolaresSteps.seleccionarDropdownGenerico( "USD");
            compraVentaDeDolaresSteps.setMonto("10");
            compraVentaDeDolaresSteps.clickContinuarGenerico();
            compraVentaDeDolaresSteps.clickConfirmarGenerico();
            compraVentaDeDolaresSteps.validacionMensaje("Leer y aceptar los términos y condiciones es obligatorio para continuar");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC- 0181- BSF-  Inversiones- Compra de moneda extranjera - Validar desplegable Cuenta Origen solo cuentas en Pesos")
    public void TC0181BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC0181BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.COMPRA_VENTA_USD);
            compraVentaDeDolaresSteps.validarTituloPageGenerico("Compra venta de d");
            compraVentaDeDolaresSteps.clickTipoDeOperacion();
            compraVentaDeDolaresSteps.seleccionarDropdownGenerico("Comprar");
            compraVentaDeDolaresSteps.validarDesplegableCuentasEnPesos("cuentaOrigen");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC- 0182- BSF-  Inversiones- Compra de moneda extranjera - Validar desplegable Cuenta Destino solo cuentas en Dolares")
    public void TC0182BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC0182BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.COMPRA_VENTA_USD);
            compraVentaDeDolaresSteps.validarTituloPageGenerico("Compra venta de d");
            compraVentaDeDolaresSteps.clickTipoDeOperacion();
            compraVentaDeDolaresSteps.seleccionarDropdownGenerico("Comprar");
            compraVentaDeDolaresSteps.validarDesplegableCuentasEnDolares("cuentaDestino");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC- 0187- BSF-  Inversiones- Venta de moneda extranjera - Colocar importe 0 de compra de dolar - Validar mensaje 'No se pudo realizar la operación'")
    public void TC0187BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC0187BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.COMPRA_VENTA_USD);
            compraVentaDeDolaresSteps.validarTituloPageGenerico("Compra venta de d");
            compraVentaDeDolaresSteps.clickTipoDeOperacion();
            compraVentaDeDolaresSteps.seleccionarDropdownGenerico( "Vender");
            compraVentaDeDolaresSteps.clickCuentaOrigen();
            compraVentaDeDolaresSteps.seleccionarDropdownGenerico( testCaseData.cuentaOrigen);
            compraVentaDeDolaresSteps.clickCuentaDestino();
            compraVentaDeDolaresSteps.seleccionarDropdownGenerico("$");
            compraVentaDeDolaresSteps.setMonto("0");
            compraVentaDeDolaresSteps.clickContinuarGenerico();
            compraVentaDeDolaresSteps.validacionMensaje("El monto ingresado debe ser mayor a 0");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC- 0188- BSF-  Inversiones- Venta de moneda extranjera - Seguir flujo de venta de dolar sin aceptar check terminos y condiciones - Validar campo obligatorio")
    public void TC0188BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC0188BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.COMPRA_VENTA_USD);
            compraVentaDeDolaresSteps.validarTituloPageGenerico("Compra venta de d");
            compraVentaDeDolaresSteps.clickTipoDeOperacion();
            compraVentaDeDolaresSteps.seleccionarDropdownGenerico( "Vender");
            compraVentaDeDolaresSteps.clickCuentaOrigen();
            compraVentaDeDolaresSteps.seleccionarDropdownGenerico( testCaseData.cuentaOrigen);
            compraVentaDeDolaresSteps.clickCuentaDestino();
            compraVentaDeDolaresSteps.seleccionarDropdownGenerico("$");
            compraVentaDeDolaresSteps.setMonto("10");
            compraVentaDeDolaresSteps.clickContinuarGenerico();
            compraVentaDeDolaresSteps.clickConfirmarGenerico();
            compraVentaDeDolaresSteps.validacionMensaje("Leer y aceptar los términos y condiciones es obligatorio para continuar");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC- 0189- BSF-  Inversiones- Venta de moneda extranjera - Validar desplegable Cuenta Destino solo cuentas en Pesos")
    public void TC0189BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC0189BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.COMPRA_VENTA_USD);
            compraVentaDeDolaresSteps.validarTituloPageGenerico("Compra venta de d");
            compraVentaDeDolaresSteps.clickTipoDeOperacion();
            compraVentaDeDolaresSteps.seleccionarDropdownGenerico( "Vender");
            compraVentaDeDolaresSteps.validarDesplegableCuentasEnPesos("cuentaDestino");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC- 0190- BSF-  Inversiones- Venta de moneda extranjera - Validar desplegable Cuenta Origen solo cuentas en Dolares")
    public void TC0190BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC0190BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.COMPRA_VENTA_USD);
            compraVentaDeDolaresSteps.validarTituloPageGenerico("Compra venta de d");
            compraVentaDeDolaresSteps.clickTipoDeOperacion();
            compraVentaDeDolaresSteps.seleccionarDropdownGenerico( "Vender");
            compraVentaDeDolaresSteps.validarDesplegableCuentasEnDolares("cuentaOrigen");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    //@Test Seteo de variable no aplica
    @DisplayName("TC-128245- BSF-  Web - Compra/Venta USD - Validar mensaje no puedo operar USD en dias no habiles y feriados'")
    public void TC128245BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC128245BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.COMPRA_VENTA_USD);
            compraVentaDeDolaresSteps.validarMsjCompraFueraDeHorario("Podés comprar");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    /**
     * Casos de prueba de la sección Login
     *
     */
    @Test
    @DisplayName("TC-0017-BSF: Autenticación con usuario - Login Exitoso")
    public void TC0017BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC0017BSF");
            loginSteps.iniciarSesionFront(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickInicio();
            loginSteps.validarTituloPageGenerico("Hola Diaz, Leonides Eladio");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    //@Test Caso desactualizado y actualizado en TC128240
    @DisplayName("TC-0048-BSF: Recupero de Usuario - Recuperar Usuario")
    public void TC0048BSF(){
        try {
            testCaseData.cargaDeDatosTests("TC0048BSF");
            String randomStr = RandomStringUtils.randomAlphabetic(4);
            //TEST
            loginSteps.waitGP();
            loginSteps.clickIngresarStartPage();
            loginSteps.setUsuario(testCaseData.usuario);
            loginSteps.clickRecuperarUsuario();
            loginSteps.setNumeroCuil("20080010045");
            loginSteps.clickContinuarGenerico();
            loginSteps.validarTituloPageGenerico("Tu solicitud");
            loginSteps.validarTituloPageGenerico("Enviamos tu usuario a");
            loginSteps.loginAPI(testCaseData.usuario,testCaseData.password);
            loginSteps.validarMailRecibido(dobleFactorSteps.codigoVerificacionEmailRecuperoUsuario(),testCaseData.usuario);
            loginSteps.clickBotonIniciarSesion();
            loginSteps.clickIngresarLogin();
            loginSteps.setUsuario(testCaseData.usuario);
            loginSteps.clickContinuarLogin();
            loginSteps.setPassword(testCaseData.password);
            loginSteps.clickIngresarLogin();
            loginSteps.verificarSaludo();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    //@Test Caso desestimado por Captcha
    @DisplayName("TC-128238-BSF: Web - Enrolamiento - Validar pantalla de enrolamiento de usuario")
    public void TC128238BSF() {
        try {
            loginSteps.waitGP();
            loginSteps.clickCrearUsuario();
            loginHerramientasDeAccesibilidadSteps.validarTituloPageGenerico("Solicitá tu código");
            UtilsGP.fixedWait(7000);
            loginSteps.setNumeroCuil2("27001013152");
            UtilsGP.fixedWait(7000);
            loginSteps.clickCheckboxCaptcha();
            loginSteps.clickAceptarGenerico();
            botonesGenericosSteps.validarTituloPageGenerico("Validación de identidad");
            validacionDeIdentidadSteps.setDigitosTarjeta("5000");
            validacionDeIdentidadSteps.clickPin();
            validacionDeIdentidadSteps.setPinPorNro("1");
            validacionDeIdentidadSteps.setPinPorNro("3");
            validacionDeIdentidadSteps.setPinPorNro("1");
            validacionDeIdentidadSteps.setPinPorNro("5");
            validacionDeIdentidadSteps.clickValidarGenerico();
            botonesGenericosSteps.validarTituloPageGenerico("Verificá tu código");
            dobleFactorSteps.codigoSegundoFactorDeEnrolamientoPinTarjetaDebito();
            dobleFactorSteps.codigoSegundoFactorDeEnrolamiento();
            botonesGenericosSteps.validarTituloPageGenerico("Verificá tu código");
            dobleFactorSteps.codigoVerificacionEmail("practiagp1@gmail.com");
            botonesGenericosSteps.validarTituloPageGenerico("Hola");
            loginSteps.setUsuario("Useruatsf03");
            loginSteps.setPassword("Banco045");
            loginSteps.setRepetirPassword("Banco045");
            loginSteps.clickCheckTerminosCondicionesGenerico();
            botonesGenericosSteps.validacionTextosCrearUsuario();

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-128240-BSF: Web - Login - Validar pantalla de Recupero de usuario y y el control del campo mandatorio CUIL")
    public void TC128240BSF(){
        try {
            testCaseData.cargaDeDatosTests("TC128240BSF");
            String randomStr = RandomStringUtils.randomAlphabetic(4);
            //TEST
            loginSteps.waitGP();
            loginSteps.clickIngresarStartPage();
            loginSteps.setUsuario(testCaseData.usuario);
            loginSteps.clickRecuperarUsuario();
            loginSteps.validarBtnContinuarDeshabilitado();
            loginSteps.setNumeroCuil("20080010045");
            loginSteps.validarBtnContinuarHabilitado();
            loginSteps.clickContinuarGenerico();
            loginSteps.validarTituloPageGenerico("Tu solicitud");
            loginSteps.validarTituloPageGenerico("Enviamos tu usuario a");
            loginSteps.loginAPI(testCaseData.usuario,testCaseData.password);
            loginSteps.validarMailRecibido(dobleFactorSteps.codigoVerificacionEmailRecuperoUsuario(),testCaseData.usuario);
            loginSteps.clickBotonIniciarSesion();
            loginSteps.clickIngresarLogin();
            loginSteps.setUsuario(testCaseData.usuario);
            loginSteps.clickContinuarLogin();
            loginSteps.setPassword(testCaseData.password);
            loginSteps.clickIngresarLogin();
            loginSteps.verificarSaludo();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-128243-BSF: Web - Home - Validar la visualización del Alert Information en formato ADVERTENCIA")
    public void TC128243BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC128243BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.ESCRITORIO);
            UtilsGP.fixedWait(5000);
            loginSteps.validarLogoAdvertencia();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    /**
     * Casos de pruebas de la sección Mensajes.
     *
     */
    @Test
    @DisplayName("TC-0086-BSF: Mensajes - Marcar no leido")
    public void TC0086BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC0086BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.MENSAJES);
            loginSteps.validarTituloPageGenerico("Notificaciones");
            //PRECONDICION
            mensajeSteps.clickSinLeer();
            mensajeSteps.clickPrimerMensaje();
            //FIN PRECONDICION
            mensajeSteps.clickLeidos();
            mensajeSteps.validarTipoMsjElegido();
            mensajeSteps.clickEnTresPuntos();
            mensajeSteps.clickMarcarComoLeido();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0090-BSF: Mensajes - Eliminar msj.")
    public void TC0090BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC0090BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.MENSAJES);
            loginSteps.validarTituloPageGenerico("Notificaciones");
            mensajeSteps.tomarTextoUltimoMensaje();
            mensajeSteps.clickEnTresPuntos();
            mensajeSteps.clickEliminarMsj();
            mensajeSteps.validarEliminacionUltimoMensaje();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }


    /**
     * Casos de prueba de la sección Mis Productos
     *
     */
    @Test
    @DisplayName("TC-0075-BSF: Baja / Arrepentimiento - POPUP - Redirecciomamiento")
    public void TC0075BSF(){
        try {
            String urlOnlineBanking = "https://www.bancosantafe.com.ar/baja-y-arrepentimiento";
            testCaseData.cargaDeDatosTests("TC0075BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.BAJA_ARREPENTIMIENTO);
            bajaArrepentimientoSteps.validaPopupBajaArrepentimiento();
            bajaArrepentimientoSteps.validarTituloBajaArrepentimiento();
            bajaArrepentimientoSteps.clickAceptarGenerico();
            bajaArrepentimientoSteps.validaRedireccionOnlineBanking(urlOnlineBanking);
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0077-BSF: Administracion de productos - Descarga ")
    public void TC0077BSF(){
        try {
            testCaseData.cargaDeDatosTests("TC0077BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.CONTRATOS);
            menuLateralSteps.validarTituloPageGenerico("Administraci");
            administracionDeProductosSteps.clickDesplegableProducto();
            administracionDeProductosSteps.seleccionarDropdownGenerico("Desbloqueo (aceptación) de C. Ahorros Dólar Paquet");
            administracionDeProductosSteps.descargarArchivo();
            administracionDeProductosSteps.validarDescarga("Desbloqueo (aceptación) de C. Ahorros Dólar Paquet.pdf");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    /**
     * Casos de pruebas de la sección Pantalla Principal
     *
     */
    @Test
    @DisplayName("TC-0151-BSF: PANTALLA PRINCIPAL - Ingresar - Loguearse con teclado virtual")
    public void TC0151BSF(){
        try {
            testCaseData.cargaDeDatosTests("TC0151BSF");
            loginSteps.iniciarSesionTecladoVirtual(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickInicio();
            loginSteps.verificarSaludo();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    //@Test
    @DisplayName("TC-0152-BSF: PANTALLA PRINCIPAL - Ingresar - Acceso directo 'Tienda.Clic'")
    public void TC0152BSF() {
        try {
            loginSteps.waitGP();
            loginSteps.clickIngresarStartPage();
            pantallaPrincipalSteps.btnTiendaClic();
            pantallaPrincipalSteps.TiendaClicTab();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    //@Test Caso obsoleto
    @DisplayName("TC-0154-BSF: PANTALLA PRINCIPAL - Ingresar - Acceso directo 'Teléfonos Utiles'")
    public void TC0154BSF() {
        try {
            loginSteps.waitGP();
            loginSteps.clickIngresarStartPage();
            pantallaPrincipalSteps.TelefonosUtiles();
            pantallaPrincipalSteps.TelefonosUtilesTab();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-0156-BSF: PANTALLA PRINCIPAL - Ingresar - Acceso directo '¿No sos cliente aún? Registrarme'")
    public void TC0156BSF() {
        try {
            loginSteps.waitGP();
            //loginSteps.clickIngresarStartPage();
            pantallaPrincipalSteps.clickRegistrarme();
            pantallaPrincipalSteps.validarTituloPageGenerico("Es necesario validar tu identidad para continuar");
            pantallaPrincipalSteps.clickVolverGenerico();
        } catch (Exception ex){
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-0161-BSF: PANTALLA PRINCIPAL - Ingresar - Enlace 'Información de seguridad'")
    public void TC0161BSF() {
        try {
            loginSteps.waitGP();
            loginSteps.clickIngresarStartPage();
            pantallaPrincipalSteps.ClickInfoDeSeguridad();
            botonesGenericosSteps.validarFragmentoURL("seguridad-y-prevencion-digital/normativo");
        } catch (Exception ex){
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    /**
     * Caoss de pruebas de la sección Perfil
     *
     */
   // @Test Esta operación sólo se hace por App
    @DisplayName("TC-0002-BSF: Cambio de Correo Electrónico - Cambio de correo electrónico exitoso")
    public void TC0002BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC0002BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.PERFIL);
            loginSteps.verificarGmail();
            perfilSteps.validarTituloPageGenerico("Mi Perfil");
            perfilSteps.clickModificarCorreo();
            modificarPerfilSteps.validarTituloPageGenerico("Modificar correo");
            modificarPerfilSteps.validarTituloPageGenerico("En el caso que quieras actualizar tu correo, debes descargarte la app ");

//            modificarPerfilSteps.clickIconoEditar();
//            modificarPerfilSteps.setEditarCorreo(CORREO);
//            modificarPerfilSteps.clickModificarGenerico();
//            dobleFactorSteps.codigoSegundoFactor();
//            dobleFactorSteps.codigoVerificacionEmail(CORREO);
//            //POSCONDICION
//            perfilSteps.setearEmailOriginalAPI();
//            modificarPerfilSteps.validarCambioEmail();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0004-BSF: Cambio domicilio")
    public void TC0004BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC0004BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.PERFIL);
            perfilSteps.validarTituloPageGenerico("Mi Perfil");
            perfilSteps.clickModificarDomicilio();
            modificarPerfilSteps.validarTituloPageGenerico("Modificar Dirección");
            modificarPerfilSteps.validarTituloPageGenerico("En el caso que quieras actualizar tu domicilio, debes descargarte la app ");

//            modificarPerfilSteps.clickDomicilio();
//            modificarPerfilSteps.seleccionarDropdownGenerico( "Modificar Dirección");
//            modificarPerfilSteps.setCalle(modificarPerfilSteps.generarString(8, "abcdefghis"));
//            modificarPerfilSteps.setNumero(generarString(2, "54454511"));
//            modificarPerfilSteps.setPiso(generarString(1, "21556"));
//            modificarPerfilSteps.setApartamento((generarString(1, "abcdefjh")));
//            modificarPerfilSteps.clickProvincia();
//            modificarPerfilSteps.seleccionarDropdownGenerico("CORRIENTES");
//            modificarPerfilSteps.clickLocalidad();
//            modificarPerfilSteps.seleccionarDropdownGenerico( "LA CRUZ");
//            modificarPerfilSteps.clickCodigoPostal();
//            modificarPerfilSteps.seleccionarDropdownGenerico( "3346");
//            modificarPerfilSteps.guardarDireccion();
//            modificarPerfilSteps.clickContinuarGenerico();
//            dobleFactorSteps.codigoSegundoFactor();
//            //VALIDACION
//            menuSuperiorSteps.clickPerfil();
//            perfilSteps.clickMiPerfil();
//            perfilSteps.clickModificarDomicilio();
//            modificarPerfilSteps.validarDomicilio();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0035-BSF: Cambio de sello/avatar de seguridad - Cambio de avatar")
    public void TC0035BSF() {
        try {
            int idImagenActual;
            testCaseData.cargaDeDatosTests("TC0035BSF");
            //PRECONDICION TOMAR ID IMAGEN ACTUAL
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.ESCRITORIO);
            loginSteps.validarTituloPageGenerico("Hola");
            idImagenActual = loginSteps.obtenerIdImagenPerfilUsuario(testCaseData.usuario);

            //CASO
            menuSuperiorSteps.clickPerfil();
            perfilSteps.clickMiPerfil();
            perfilSteps.validarTituloPageGenerico("Mi Perfil");
            perfilSteps.clickCambiarImagen();
            modificarPerfilSteps.cambiarImagenAvatar(idImagenActual);
            dobleFactorSteps.codigoSegundoFactor();
            perfilSteps.compararIdImagen(testCaseData.usuario,idImagenActual);
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    /**
     * Casos de prueba de la sección Préstamos
     *
     * Se necesita que los usuarios en esta sección estén pre-calificados para la operatoria.
     */
    @Test
    @DisplayName("TC-0206-BSF: Prestamos - Nuevo prestamo - Validar pantalla etapa 4 'Confirmacion'")
    public void TC0206BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC0206BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.NUEVO_PRESTAMO);
            loginSteps.validarTituloPageGenerico("Solicitá tu préstamo");
            prestamosSteps.setMonto("10000");
            prestamosSteps.clickSiguiente();
            prestamosSteps.seleccionDestino("INMUEBLES");
            prestamosSteps.clickContinuarGenerico();
            prestamosSteps.validarTituloEtapa("Cuotas");
            prestamosSteps.validarTituloPageGenerico("Seleccioná la cantidad de cuotas");
            prestamosSteps.seleccionarLabelCuotas();
            prestamosSteps.clickContinuarGenerico();
            prestamosSteps.clickCheckTerminosCondicionesGenerico();
            prestamosSteps.clickContinuarGenerico();
            prestamosSteps.validarTituloEtapa("Confirmaci");
            prestamosSteps.validarTituloPageGenerico("Confirmá tu solicitud");
            prestamosSteps.validarTituloPageGenerico("Para terminar, ingresá el código que recibiste por mensaje de texto.");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test //DATOS ESPECIFICOS NO SE CUENTA CON EL MISMO - DE MOMENTO LO APAGAMOS
    @DisplayName("TC-0220-BSF: Prestamos- Prestamo para cancelar deudas - Validar pantalla etapa 5 'Confirmacion'")
    public void TC0220BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC0220BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.PRES_CANCELAR_DEUDAS);
            loginSteps.validarTituloPageGenerico("Solicitá tu préstamo para cancelar deudas");
            prestamosParaCancelarDeudasSteps.clickPrimerCheckbox();
            prestamosSteps.clickSiguiente();
            prestamosSteps.setMonto("500000");
            prestamosParaCancelarDeudasSteps.clickContinuarGenerico();
            prestamosParaCancelarDeudasSteps.clickContinuarGenerico();
            prestamosSteps.seleccionarLabelCuotas();
            prestamosParaCancelarDeudasSteps.clickContinuarGenerico();
            prestamosParaCancelarDeudasSteps.clickCheckTerminosCondicionesGenerico();
            prestamosParaCancelarDeudasSteps.clickContinuarGenerico();
            prestamosSteps.validarTituloEtapa("Confirmaci");
            prestamosParaCancelarDeudasSteps.validarTituloPageGenerico("Confirmá tu solicitud");
            prestamosParaCancelarDeudasSteps.validarTituloPageGenerico("La acreditación se verá reflejada en el transcurso del día.");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0221-BSF: Prestamos- Prestamo para cancelar deudas - Descargar resumen de contrato - validar descarga archivo")
    public void TC0221BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC0221BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.PRES_CANCELAR_DEUDAS);
            loginSteps.validarTituloPageGenerico("Solicitá tu préstamo para cancelar deudas");
            prestamosParaCancelarDeudasSteps.clickPrimerCheckbox();
            prestamosSteps.clickSiguiente();
            prestamosSteps.descargarResumenContrato();
            prestamosParaCancelarDeudasSteps.validarFragmentoURL("assets");

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    /**
     * Casos de prueba de la sección Seguros.
     *
     * Se necesita que los usuarios en esta sección estén pre-calificados para la operatoria.
     */
    @Test
    @DisplayName("TC-0196-BSF: Seguros - Validar cards de Seguros disponibles")
    public void TC0196BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC0196BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.SEGUROS);
            segurosSteps.validarTituloPageGenerico("Seguros");
            segurosSteps.validacionCardRobo();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Media.class})
    @DisplayName("TC-0197-BSF: Seguros - Ingresar a un seguro - Validar detalle de seguro")
    public void TC0197BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC0197BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.SEGUROS);
            segurosSteps.validarTituloPageGenerico("Seguros");
            segurosSteps.clickCardSeguroRobo();
            segurosSteps.validacionDetalleSeguroRobo();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-12928-BSF: SEGUROS - Alta de seguros  - Acceso por Seguro - Menú lateral - Solicitud  y validación de pantalla")
    public void TC12928BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC12928BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.SEGUROS);
            segurosSteps.validarTituloPageGenerico("Seguros");
            segurosSteps.clickCardSeguroRobo();
            segurosSteps.clickContinuarGenerico();
            segurosSteps.clickCheckTerminosCondicionesGenerico();
            segurosSteps.validarBotonConfirmar();
            segurosSteps.validarBotonCancelarDisponible();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    /**
     * Casos de pruebas de la sección Transferencias.
     *
     */
    @Test
    @DisplayName("TC-0146-BSF: Transferencias - Añadir nuevo destinatario - Con cbu invalido - Validar mensaje de error")
    public void TC0146BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC0146BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.TRANSFERENCIAS);
            segurosSteps.validarTituloPageGenerico("Transferencias");
            transferenciasInmediatas.seleccionarTipoTransferencia("Contacto Agendado");
            transferenciasInmediatas.clickAgendaDestinatario();
            agendaNuevoDestinatarioSteps.clickAdherirNuevoDestinatario();
            agendaNuevoDestinatarioSteps.setInputCbuAlias(testCaseData.cbuDestino);
            agendaNuevoDestinatarioSteps.clickBuscar();
            agendaNuevoDestinatarioSteps.validarMensajeCbuInvalido();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0148-BSF: Transferencias - Añadir nuevo destinatario - Con alias invalido - Validar mensaje de error")
    public void TC0148BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC0148BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.TRANSFERENCIAS);
            segurosSteps.validarTituloPageGenerico("Transferencias");
            transferenciasInmediatas.seleccionarTipoTransferencia("Contacto Agendado");
            transferenciasInmediatas.clickAgendaDestinatario();
            agendaNuevoDestinatarioSteps.clickAdherirNuevoDestinatario();
            agendaNuevoDestinatarioSteps.setInputCbuAlias(testCaseData.aliasDestino);
            agendaNuevoDestinatarioSteps.clickBuscar();
            agendaNuevoDestinatarioSteps.validarMensajeAliasInvalido();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-18088-BSF: Transferencia a cuentas propias con CBU de otra entidad con todos los campos completos")
    public void TC18088BSF() {
        try {
            String fechaTransferencia;
            testCaseData.cargaDeDatosTests("TC18088BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.TRANSFERENCIAS);
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI(testCaseData.cbuDestino);
            //TEST
            bonos.validarTituloPageGenerico("Transferencias");
            transferenciasInmediatas.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatas.completarInputAliasCbu(testCaseData.cbuDestino);
            transferenciasInmediatas.clickBuscarGenerico();
            transferenciasInmediatas.botonSonCorrectos();
            transferenciasInmediatas.setImporteGenerico("1");
            transferenciasInmediatas.clickConcepto();
            transferenciasInmediatas.seleccionarDropdownGenerico("Alquileres");
            transferenciasInmediatas.setDescripcion("TC18088");
            fechaTransferencia = utilsGP.fechaYHoraActual();
            System.out.println("FechaTransferencia " + fechaTransferencia);
            transferenciasInmediatas.clickBotonTransferirAhora();
            transferenciasInmediatas.clickConfirmarGenerico();
            dobleFactorSteps.codigoSegundoFactor();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            ticketSteps.validarCampoValorTicket("Fecha", fechaTransferencia);
            ticketSteps.validarCampoValorTicket("Monto","$ 1,00");
            ticketSteps.validarCampoValorTicket("Emisor","PEREZ, ROQUE");
            ticketSteps.validarCampoValorTicket("Cuenta de origen","CC $ 001000090502");
            ticketSteps.validarCampoValorTicket("CUIT/CUIL emisor","20025000014");
            ticketSteps.validarCampoValorTicket("Destinatario","CHARLEEN, ALVARA");
            ticketSteps.validarCampoValorTicket("CBU/CVU destino","0450500601800008112711");
            ticketSteps.validarCampoValorTicket("CUIT/CUIL destino","23244825664");
            ticketSteps.validarCampoValorTicket("Entidad destino","BANCO DE SAN JUAN S.A.");
            ticketSteps.validarCampoValorTicket("Concepto","Alquileres");
            ticketSteps.validarCampoValorTicket("Descripción","TC18088");

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }


    @Test
    @DisplayName("TC-18096-BSF: Saldo actualizado_Transferencias_Ctas. Propias en pesos del mismo banco")
    public void TC18096BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC18096BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.TRANSFERENCIAS);
            //agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI(testCaseData.cbuDestino);
            //TEST
            bonos.validarTituloPageGenerico("Transferencias");
            transferenciasInmediatas.obtenerSaldoInicial();
            transferenciasInmediatas.seleccionarTipoTransferencia("Cuenta Propia");
            transferenciasInmediatas.seleccionarCtaOrigen();
            transferenciasInmediatas.seleccionarCuentaDestinoCuentasPropias(testCaseData.cuentaDestino);
            botonesGenericosSteps.validarTituloPageGenerico("Transferencias");
            transferenciasInmediatas.setImporteGenerico("1000");
            transferenciasInmediatas.clickConcepto();
            transferenciasInmediatas.seleccionarDropdownGenerico("Alquileres");
            transferenciasInmediatas.setDescripcion("TC18096");
            transferenciasInmediatas.clickBotonTransferirAhora();
            transferenciasInmediatas.clickConfirmarGenerico();
            ticketSteps.validarTicket("Tu solicitud se realizo " +
                    "con exito");
            botonesGenericosSteps.clickVolverGenerico();
            transferenciasInmediatas.saldoActualizado();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-18099-BSF: TRANSFERENCIAS - WEB - Transferencias a CBU - Historial de transferencias ")
    public void TC18099BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC18099BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.TRANSFERENCIAS);
            //TEST
            bonos.validarTituloPageGenerico("Transferencias");
            transferenciasInmediatas.misTransferencias();
            transferenciasInmediatas.clickbtnFiltrar();
            transferenciasInmediatas.obtenertransferenciaMasAntigua();
            transferenciasInmediatas.obtenerUltimaTransferencia();
            transferenciasInmediatas.getregistrosPorPaginaGrilla();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }


    @Test
    @DisplayName("TC-16756-BSF: WEB - Transferencias realizadas - Ver mis transferencias ")
    public void TC16756BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC16756BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.TRANSFERENCIAS);
            //agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI(testCaseData.cbuDestino);
            //TEST
            transferenciasInmediatas.validarTituloPageGenerico("Transferencias");
            transferenciasInmediatas.misTransferencias();
            transferenciasInmediatas.buscarMovimientosConAntiguedadDeDosMeses();

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-18103-BSF: WEB - Transferencias realizadas - Ver mis transferencias ")
    public void TC18103BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC18103BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.TRANSFERENCIAS);
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI(testCaseData.cbuDestino);
            //TEST
            transferenciasInmediatas.validarTituloPageGenerico("Transferencias");
            transferenciasInmediatas.misTransferencias();
            transferenciasInmediatas.validarBorrarFiltros();



        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-18092-BSF: WEB - Cambios en comprobante - TF Inmediatas a terceros - " +
            "Todos los campos - CBU otra entidad (banco)")
    public void TC18092BSF() {
        try {
            String fechaTransferencia;
            testCaseData.cargaDeDatosTests("TC18092BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.TRANSFERENCIAS);
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI(testCaseData.cbuDestino);
            //TEST
            bonos.validarTituloPageGenerico("Transferencias");
            transferenciasInmediatas.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatas.completarInputAliasCbu(testCaseData.cbuDestino);
            transferenciasInmediatas.clickBuscarGenerico();
            transferenciasInmediatas.botonSonCorrectos();
            transferenciasInmediatas.setImporteGenerico("1");
            transferenciasInmediatas.clickConcepto();
            transferenciasInmediatas.seleccionarDropdownGenerico("Alquileres");
            transferenciasInmediatas.setDescripcion("TC18092");
            fechaTransferencia = utilsGP.fechaYHoraActual();
            System.out.println("FechaTransferencia " + fechaTransferencia);
            transferenciasInmediatas.clickBotonTransferirAhora();
            transferenciasInmediatas.clickConfirmarGenerico();
            dobleFactorSteps.codigoSegundoFactor();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            ticketSteps.validarCampoValorTicket("Fecha", fechaTransferencia);
            ticketSteps.validarCampoValorTicket("Monto","$ 1,00");
            ticketSteps.validarCampoValorTicket("Emisor","DIAZ, LEONIDES ELADIO");
            ticketSteps.validarCampoValorTicket("Cuenta de origen","CC $ 520000074608");
            ticketSteps.validarCampoValorTicket("CUIT/CUIL emisor","20122981003");
            ticketSteps.validarCampoValorTicket("Destinatario","AMIN, MIGUEL ALBERTO");
            ticketSteps.validarCampoValorTicket("CBU/CVU destino","0450001801800068429071");
            ticketSteps.validarCampoValorTicket("CUIT/CUIL destino","20144516207");
            ticketSteps.validarCampoValorTicket("Entidad destino","BANCO DE SAN JUAN S.A.");
            ticketSteps.validarCampoValorTicket("Concepto","Alquileres");
            ticketSteps.validarCampoValorTicket("Descripción","TC18092");

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-128246-BSF: Web - VU Fraud - Verificar que al ejecutarse el activity adhesionAccount se envíe el metodoValidacionFP con la marca de validación del dispositivo")
    public void TC128246BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC128246BSF");
            loginSteps.iniciarSesionFrontConBearerApi(testCaseData.usuario, testCaseData.password);
            menuLateralSteps.clickSoloTransferencias();
            botonesGenericosSteps.clickBtnAhoraNo("Ahora no");
            segurosSteps.validarTituloPageGenerico("Transferencias");
            UtilsGP.fixedWait(5000);
            transferenciasInmediatas.seleccionarTipoTransferencia("Contacto Agendado");
            UtilsGP.fixedWait(5000);
            transferenciasInmediatas.clickAgendaDestinatario();
            agendaNuevoDestinatarioSteps.clickAdherirNuevoDestinatario();
            agendaNuevoDestinatarioSteps.setInputCbuAlias(testCaseData.cbuDestino);
            agendaNuevoDestinatarioSteps.clickBuscar();
            transferenciasInmediatas.botonSonCorrectos();
            dobleFactorSteps.codigoSegundoFactor();
            botonesGenericosSteps.clickVolverGenerico();
            agendaNuevoDestinatarioSteps.eliminaContactoEnAgenda(testCaseData.cbuDestino, "No se registran cuentas agendadas");
            UtilsGP.fixedWait(3000);
            dobleFactorSteps.clickBtnUtilLogger();
            utilsGP.switchTab();
            utilLoggerSteps.clickBtnActualizar();
            utilLoggerSteps.clickRequestSvc("/FraudAnalysis/service/analyze");
            utilsGP.backMainTap();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-128247-BSF: Web - VU Fraud - Verificar que al ejecutarse el activity transfers.account.makeWithCaps se envíe el metodoValidacionFP con la marca de validación del dispositivo")
    public void TC128247BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC128247BSF");
            loginSteps.iniciarSesionFrontConBearerApi(testCaseData.usuario, testCaseData.password);
            menuLateralSteps.clickSoloTransferencias();
            botonesGenericosSteps.clickBtnAhoraNo("Ahora no");
            segurosSteps.validarTituloPageGenerico("Transferencias");
            UtilsGP.fixedWait(5000);
            transferenciasInmediatas.seleccionarTipoTransferencia("Contacto Agendado");
            UtilsGP.fixedWait(5000);
            transferenciasInmediatas.clickAgendaDestinatario();
            agendaNuevoDestinatarioSteps.clickAdherirNuevoDestinatario();
            agendaNuevoDestinatarioSteps.setInputCbuAlias(testCaseData.cbuDestino);
            agendaNuevoDestinatarioSteps.clickBuscar();
            transferenciasInmediatas.botonSonCorrectos();
            dobleFactorSteps.codigoSegundoFactor();
            transferenciasInmediatas.clickBtnTransferiAEsteContacto("Transferir a este");
            segurosSteps.validarTituloPageGenerico("Transferencias");
            transferenciasInmediatas.setImporteGenerico("1");
            transferenciasInmediatas.clickConcepto();
            transferenciasInmediatas.seleccionarDropdownGenerico("Alquileres");
            transferenciasInmediatas.setDescripcion("128247");
            transferenciasInmediatas.clickBotonTransferirAhora();
            transferenciasInmediatas.clickConfirmarGenerico();
//            dobleFactorSteps.codigoSegundoFactor();
            botonesGenericosSteps.clickVolverGenerico();
            transferenciasInmediatas.seleccionarTipoTransferencia("Contacto Agendado");
            UtilsGP.fixedWait(5000);
            transferenciasInmediatas.clickAgendaDestinatario();
            agendaNuevoDestinatarioSteps.eliminaContactoEnAgenda(testCaseData.cbuDestino, "No se registran cuentas agendadas");
            UtilsGP.fixedWait(3000);
            dobleFactorSteps.clickBtnUtilLogger();
            utilsGP.switchTab();
            utilLoggerSteps.clickBtnActualizar();
            utilLoggerSteps.clickRequestSvc("/FraudAnalysis/service/analyze");
            utilsGP.backMainTap();

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-128248-BSF: Web - VU Fraud - Verificar que al ejecutarse el activity credin.generate se envíe el metodoValidacionFP con la marca de validación del dispositivo")
    public void TC128248BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC128248BSF");
            loginSteps.iniciarSesionFrontConBearerApi(testCaseData.usuario, testCaseData.password);
            menuLateralSteps.clickSoloTransferencias();
            botonesGenericosSteps.clickBtnAhoraNo("Ahora no");
            segurosSteps.validarTituloPageGenerico("Transferencias");
            transferenciasSteps.clickMasOpcionesGenerico();
            transferenciasSteps.clickConsultarCredin();
            consultaCredinSteps.clickGenerarCredin();
            generarCredinSteps.clickTarjetaDebito();
            generarCredinSteps.keyDownToText("4002");
            botonesGenericosSteps.clickContinuarGenerico();
            generarCredinSteps.clickCuentaDebito();
            generarCredinSteps.keyDownXParametrosGenerico(0, "Cuenta Corriente $ 000000870608 ");
            generarCredinSteps.clickCBU();
            generarCredinSteps.setCampoAliasCbu(testCaseData.cbuDestino);
            generarCredinSteps.clickSelectorConcepto();
            generarCredinSteps.keyDownXParametrosGenerico(0,"Alquileres");
            generarCredinSteps.setImporteGenerico("1");
            generarCredinSteps.setCampoDescripcion("Test0014");
            generarCredinSteps.clickCheckEsCuentaPropia();
            generarCredinSteps.clickContinuarGenerico();
            generarCredinSteps.clickCheckTerminosCondiciones();
            dobleFactorSteps.codigoSegundoFactor();
            UtilsGP.fixedWait(3000);
            dobleFactorSteps.clickBtnUtilLogger();
            utilsGP.switchTab();
            utilLoggerSteps.clickBtnActualizar();
            utilLoggerSteps.clickRequestSvc("/FraudAnalysis/service/analyze");
            utilsGP.backMainTap();

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-128249-BSF: Web - VU Fraud - Verificar que al ejecutarse el activity transfers.account.scheduleMakeWithCaps se envíe el metodoValidacionFP con la marca de validación del dispositivo")
    public void TC128249BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC128249BSF");
            loginSteps.iniciarSesionFrontConBearerApi(testCaseData.usuario, testCaseData.password);
            menuLateralSteps.clickSoloTransferencias();
            botonesGenericosSteps.clickBtnAhoraNo("Ahora no");
            segurosSteps.validarTituloPageGenerico("Transferencias");
            UtilsGP.fixedWait(5000);
            transferenciasInmediatas.seleccionarTipoTransferencia("Contacto Agendado");
            UtilsGP.fixedWait(5000);
            transferenciasInmediatas.clickAgendaDestinatario();
            agendaNuevoDestinatarioSteps.eliminaContactoEnAgenda(testCaseData.cbuDestino, "No se registran cuentas agendadas");
            //TEST
            botonesGenericosSteps.clickVolverFlechaGenerico();
            transferenciasInmediatas.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatas.completarInputAliasCbu(testCaseData.cbuDestino);
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
            UtilsGP.fixedWait(3000);
            dobleFactorSteps.clickBtnUtilLogger();
            utilsGP.switchTab();
            utilLoggerSteps.clickBtnActualizar();
            utilLoggerSteps.clickRequestSvc("/FraudAnalysis/service/analyze");
            utilsGP.backMainTap();

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-29408-BSF: Web-Tranferencias Programadas-Nuevo historial-Visualización ")
    public void TC29408BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC29408BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.TRANSFERENCIAS);
            //TEST
            bonos.validarTituloPageGenerico("Transferencias");
            transferenciasInmediatas.misTransferencias();
            transferenciasInmediatas.clickbtnFiltrar();
            transferenciasInmediatas.getregistrosPorPaginaGrilla();
            transferenciasInmediatas.clickVolverFlechaGenerico();
            bonos.validarTituloPageGenerico("Transferencias");

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }


    @Test
    @DisplayName("TC-29409-BSF: Web-Tranferencias Programadas-Nuevo historial-Botón editar.")
    public void TC29409BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC29409BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.TRANSFERENCIAS);
            //TEST
            bonos.validarTituloPageGenerico("Transferencias");
            adelantosDeSueldoSteps.clickMasOpcionesGenerico();
            transferenciasInmediatas.clickBtnDesplegableTransferenciasProgramadas("Transferencias programadas");
            bonos.validarTituloPageGenerico("Transferencias programadas");
            transferenciasInmediatas.clickBtnEditar("Editar");
            transferenciasInmediatas.misTransferencias();
            transferenciasInmediatas.getregistrosPorPaginaGrilla();
            transferenciasInmediatas.clickVolverFlechaGenerico();
            bonos.validarTituloPageGenerico("Transferencias programadas");

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    /**
     * Casos de prueba de la sección Tarjetas.
     *
     */
    @Test
    @DisplayName("TC-0032-BSF: Consulta de Tarjetas de Crédito - Visa")
    public void TC0032BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC0032BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.TARJETA_CREDITO);
            segurosSteps.validarTituloPageGenerico("Tarjetas de crédito");
            tarjetasDeCreditoSteps.clickVerMasXnroDeTarjeta(testCaseData.tarjetaCredito);
            tarjetasDeCreditoSteps.verificarTituloVisa(testCaseData.tarjetaCredito);
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0033-BSF: Consulta de Tarjetas de Crédito - Mastercard")
    public void TC0033BSF() {
        try {
            //PRECONDICION
            testCaseData.cargaDeDatosTests("TC0033BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.TARJETA_CREDITO);
            segurosSteps.validarTituloPageGenerico("Tarjetas de crédito");
            tarjetasDeCreditoSteps.clickVerMasXnroDeTarjeta(testCaseData.tarjetaCredito);
            tarjetasDeCreditoSteps.verificarTituloMastercard();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0062-BSF: Detalle de tarjeta de crédito - Mostrar datos tarjeta y movimientos del período")
    public void TC0062BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC0062BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.TARJETA_CREDITO);
            segurosSteps.validarTituloPageGenerico("Tarjetas de crédito");
            tarjetasDeCreditoSteps.clickVerMasXnroDeTarjeta(testCaseData.tarjetaCredito);
            tarjetasDeCreditoSteps.verificarDescargarResumen();
            tarjetasDeCreditoSteps.verificarMovimientoPeriodico();
            tarjetasDeCreditoSteps.verificarSolicitarAdicional();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0104-BSF: Tarjeta de Crédito - Descarga de resumen")
    public void TC0104BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC0104BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.TARJETA_CREDITO);
            segurosSteps.validarTituloPageGenerico("Tarjetas de crédito");
            tarjetasDeCreditoSteps.clickVerMasXnroDeTarjeta(testCaseData.tarjetaCredito);
            tarjetasDeCreditoSteps.clickDescargarResumen();
            tarjetasDeCreditoSteps.validarTituloPageGenerico("Descargar resumen");
            tarjetasDeCreditoSteps.clickPrimerPeriodo();
            tarjetasDeCreditoSteps.clickDescargaPrimerPeriodo();
            tarjetasDeCreditoSteps.validarDescarga("Estado");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0163-BSF: Tarjeta de Crédito - Pagar el total de una Tarjeta de Crédito Mastercard - Validación de pantalla")
    public void TC0163BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC0163BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.ESCRITORIO);
            segurosSteps.validarTituloPageGenerico("Hola");
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.clickVerMasXnroDeTarjeta(testCaseData.tarjetaCredito);
            tarjetasDeCreditoSteps.clickMasOpcionesGenerico();
            tarjetasDeCreditoSteps.clickPagarTarjeta();
            tarjetasDeCreditoSteps.validarTituloPageGenerico("Pago de tarjeta propia");;
            tarjetasDeCreditoSteps.seleccionarCuentaDebitoSiEstaPresente(testCaseData.cuentaOrigen);
            tarjetasDeCreditoSteps.seleccionarTipoDePago( "Pagar el total");
            tarjetasDeCreditoSteps.clickBotonPagar();
            tarjetasDeCreditoSteps.validacionDeCamposPagoDeTarjeta();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    //@Test - REvision de casuisticas
    @DisplayName("TC-0191-BSF: Tarjetas de credito - Solicitud de tarjeta de credito- Al no contar con oferta de TC se debe validar mensaje 'No contás con oferta de Tarjeta de Crédito en este momento. '")
    public void TC0191BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC0191BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.TARJETA_CREDITO);
            segurosSteps.validarTituloPageGenerico("Tarjetas de crédito");
            tarjetasDeCreditoSteps.clickSolicitarTarjetaNueva();
            tarjetasDeCreditoSteps.validarMensajeNoContasConOfertas();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-0153-BSF: Tarjeta de Crédito - Segumiento de Tarjetas de Crédito - WEB.")
    public void TC0153BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC0153BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.TARJETA_CREDITO);
            segurosSteps.validarTituloPageGenerico("Tarjetas de crédito");
            tarjetasDeCreditoSteps.clickSeguimientoDeTarjetas();
            tarjetasDeCreditoSteps.validarTituloPageGenerico("Seguimiento de tarjetas");
            tarjetasDeCreditoSteps.validacionCamposSeguimientoTarjeta();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-0155-BSF: Tarjeta de Crédito - Seguimiento de Tarjetas de Crédito - Detalles - WEB.")
    public void TC0155BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC0155BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.TARJETA_CREDITO);
            segurosSteps.validarTituloPageGenerico("Tarjetas de crédito");
            tarjetasDeCreditoSteps.clickSeguimientoDeTarjetas();
            tarjetasDeCreditoSteps.validarTituloPageGenerico("Seguimiento de tarjetas");
            tarjetasDeCreditoSteps.validacionCamposSeguimientoTarjeta();
            tarjetasDeCreditoSteps.ingresarADetalleSeguimientoTarjeta();
            tarjetasDeCreditoSteps.validacionDentroDeTarjeta();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-0157-BSF: Tarjeta de Crédito - Seguimiento de Tarjetas de Crédito - Descargar Detalles - WEB.")
    public void TC0157BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC0157BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.TARJETA_CREDITO);
            segurosSteps.validarTituloPageGenerico("Tarjetas de crédito");
            tarjetasDeCreditoSteps.clickSeguimientoDeTarjetas();
            tarjetasDeCreditoSteps.validarTituloPageGenerico("Seguimiento de tarjetas");
            tarjetasDeCreditoSteps.validacionCamposSeguimientoTarjeta();
            tarjetasDeCreditoSteps.ingresarADetalleSeguimientoTarjeta();
            tarjetasDeCreditoSteps.clickDescargaPrimerPeriodo();
            tarjetasDeCreditoSteps.validarDescarga("Detalle de seguimiento de tarjeta.pdf");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-0158-BSF: Tarjeta de Crédito - Seguimiento de Tarjetas de Crédito - Imprimir Detalles - WEB")
    public void TC0158BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC0158BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.TARJETA_CREDITO);
            segurosSteps.validarTituloPageGenerico("Tarjetas de crédito");
            tarjetasDeCreditoSteps.clickSeguimientoDeTarjetas();
            tarjetasDeCreditoSteps.validarTituloPageGenerico("Seguimiento de tarjetas");
            tarjetasDeCreditoSteps.validacionCamposSeguimientoTarjeta();
            tarjetasDeCreditoSteps.ingresarADetalleSeguimientoTarjeta();
            tarjetasDeCreditoSteps.clickImprimir();
            utilsGP.ManageTab();
           // utilsGP.backMainTap();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-0195-BSF: Tarjetas de credito - Solicitud de tarjeta de credito- Al solicitar una nueva TC y verificar mensaje de solicitar turno, clickear en  'Solicitá tu turno acá.' y validar redireccion de pantalla")
    public void TC0195BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC0195BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.TARJETA_CREDITO);
            segurosSteps.validarTituloPageGenerico("Tarjetas de crédito");
            tarjetasDeCreditoSteps.clickSolicitarTarjetaNueva();
            tarjetasDeCreditoSteps.validarTituloPageGenerico("Solicitud de tarjeta de cr");
            tarjetasDeCreditoSteps.seleccionarCuentaDebitoSiEstaPresente(testCaseData.cuentaOrigen);
            tarjetasDeCreditoSteps.clickCheckboxSujetoObligadoIUF();
            tarjetasDeCreditoSteps.clickContinuarGenerico();
            tarjetasDeCreditoSteps.clickSolicitaTurnoEnSucursal();
            tarjetasDeCreditoSteps.clickAceptarGenerico();
            utilsGP.ManageTab();
            tarjetasDeCreditoSteps.validarMensaje("Bienvenido a Citas");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-0162-BSF: Tarjeta de Crédito - Denunciar robo o extravío de Tarjeta de Crédito - WEB.")
    public void TC0162BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC0162BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.TARJETA_CREDITO);
            segurosSteps.validarTituloPageGenerico("Tarjetas de crédito");
            tarjetasDeCreditoSteps.clickVerMasXnroDeTarjeta(testCaseData.tarjetaCredito);
            tarjetasDeCreditoSteps.clickMasOpcionesGenerico();
            tarjetasDeCreditoSteps.clickDenunciarRoboExtravio();
            tarjetasDeCreditoSteps.checkRobo();
            tarjetasDeCreditoSteps.clickContinuarGenerico();
            tarjetasDeCreditoSteps.validarTituloPageGenerico("Confirmar denuncia por robo o extrav");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-0167-BSF: Tarjeta de Crédito - Consultar último resumen de una Tarjeta de Crédito - WEB.")
    public void TC0167BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC0167BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.ESCRITORIO);
            segurosSteps.validarTituloPageGenerico("Hola");
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.clickVerMasXnroDeTarjeta(testCaseData.tarjetaCredito);
            tarjetasDeCreditoSteps.clickDescargarResumen();
            tarjetasDeCreditoSteps.clickPrimerPeriodo();
            tarjetasDeCreditoSteps.clickDescargaPrimerPeriodo();
            ticketSteps.validarDescarga("Estado");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    // Necesita un usuario con tarjetas de crédito VISA/MASTER
    @Test
    @DisplayName("TC-128242-BSF: Web - Detalle de TC - Validar el reordenamiento de la información en Detalle de TC VISA y MASTER")
    public void TC128242BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC128242BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.TARJETA_CREDITO);
            segurosSteps.validarTituloPageGenerico("Tarjetas de crédito");
            tarjetasDeCreditoSteps.clickVerMasXnroDeTarjeta(testCaseData.tarjetaCredito);
            tarjetasDeCreditoSteps.validacionTarjetaCreditoVisaYMaster();
            botonesGenericosSteps.clickVolverFlechaGenerico();
            segurosSteps.validarTituloPageGenerico("Tarjetas de crédito");
            tarjetasDeCreditoSteps.clickVerMasXnroDeTarjeta(testCaseData.tarjetaDebito); // Utiliza el campo de Tarjeta de Debito pero con el dato de tarjeta de credito
            tarjetasDeCreditoSteps.validacionTarjetaCreditoVisaYMaster();

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    /**
     * Casos de las Seccion Tramites
     *
     */

    @Test
    @DisplayName("TC-0052-BSF: Turnero online_Aceptar redireccionamiento la página de solicitud de turnos online fuera del HB.")
    public void TC0052BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC0052BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.ESCRITORIO);
            menuLateralSteps.clickTurnosOnline();
            turnosOnlineSteps.VerificarMensajeYAceptar();
            turnosOnlineSteps.validarFragmentoURL("TurnosWeb");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }

    }

    /**
     * Casos de prueba de la sección Pagos.
     *
     *
     */

    //@Test se desactiva por unificar con caso TC110847BSF
    @DisplayName("TC-107431-BSF: Web-Detalle de los pagos realizados en simultaneo-" +
            "Pantalla Estado de tus pagos")
    public void TC107431BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC107431BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario, testCaseData.password,Settings.PAGOS);
            botonesGenericosSteps.validarTituloPageGenerico("Pago de servicios");
            pagosSteps.clickCheckboxServicio("1");
            pagosSteps.clickCheckboxServicio("2");
            botonesGenericosSteps.clickbtnPagar();
            botonesGenericosSteps.setImporteGenerico("1");
            botonesGenericosSteps.setReferenciaGenerica("1234567890");
            botonesGenericosSteps.clickContinuarGenerico();
            botonesGenericosSteps.setImporteGenerico("1");
            botonesGenericosSteps.setReferenciaGenerica("1234567890");
            botonesGenericosSteps.clickbtnPagar();
            botonesGenericosSteps.validarTituloPageGenerico("Nuevo pago");
            botonesGenericosSteps.clickbtnPagar();
            botonesGenericosSteps.clickConfirmarGenerico();
            botonesGenericosSteps.validarTituloPageGenerico("Estado de tus pagos");

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-110363-BSF: WEB - Comprobante Pagos de servicio - Validar comportamiento filtros de busqueda")
    public void TC110363BSF() throws AWTException {
        try {
            testCaseData.cargaDeDatosTests("TC110363BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario, testCaseData.password,Settings.PAGOS);
            botonesGenericosSteps.validarTituloPageGenerico("Pago de servicios");
            //nuevoPagoSteps.validarBotonMostrarTodos();
            //nuevoPagoSteps.validarServiciosSantaFe("MLPAYMENT");
//            botonesGenericosSteps.clickVolverFlechaGenerico();
            pagosSteps.clickMisComprobantes("Mis Comprobantes");
            pagosSteps.validarTituloPageGenerico("Comprobantes de pago");
            botonesGenericosSteps.clickListaDesplegableGenerico("Seleccionar");
            botonesGenericosSteps.seleccionarDropdownGenerico
                    ("4002");
            botonesGenericosSteps.clickContinuarGenerico();
            pagosSteps.validarTituloPageGenerico("Comprobantes de pago");
            comprobantesSteps.clickEmpresa();
            comprobantesSteps.seleccionarEmpresa("Mercado Libre");
            comprobantesSteps.clickDescripcion();
            botonesGenericosSteps.seleccionarDropdownGenerico("00449535691");
            comprobantesSteps.clickBuscar();
            botonesGenericosSteps.validarComprobante("00449535691");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0183-BSF: DEBIN Vendedor - Generar orden de DEBIN - Validar campo requerido al no ingresar un CBU y seleccionar buscar.")
    public void TC0183BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC0183BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario, testCaseData.password,Settings.PAGOS);
            botonesGenericosSteps.validarTituloPageGenerico("Pago de servicios");
            UtilsGP.fixedWait(5000);
            pagosSteps.clickMasOpcionesGenerico();
            pagosSteps.clickDebinVendedor();
            debinVendedorSteps.clickOrdenDebin();
            debinVendedorSteps.clickCuentaDestinoDesplegable();
            debinVendedorSteps.seleccionarDropdownGenerico( testCaseData.cuentaOrigen);
            debinVendedorSteps.setImporteGenerico("1");
            debinVendedorSteps.clickCbu();
            debinVendedorSteps.setCbuOAlias(" ");
            debinVendedorSteps.clickBuscar();
            pagosSteps.validarCampoObligatorio();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    //@Test este caso se unifica con el TC110847BSF
    @DisplayName("TC-110846-BSF: Web-Detalle de los pagos realizados en simultaneo - Pantalla Estado de tus pagos")
    public void TC110846BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC110846BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario, testCaseData.password,Settings.PAGOS);
            botonesGenericosSteps.validarTituloPageGenerico("Pago de servicios");
            nuevoPagoSteps.validarBotonMostrarTodos();
            nuevoPagoSteps.validarServiciosSantaFe("MLPAYMENT");
            pagosSteps.clickCheckboxServicio("1");
            pagosSteps.clickCheckboxServicio("2");
            botonesGenericosSteps.clickbtnPagar();
            botonesGenericosSteps.validarTituloPageGenerico("Mercado Libre");
            botonesGenericosSteps.setImporteGenerico("1");
            botonesGenericosSteps.setReferenciaGenerica("1234567890");
            botonesGenericosSteps.clickContinuarGenerico();
            botonesGenericosSteps.validarTituloPageGenerico("Mercado Libre");
            botonesGenericosSteps.setImporteGenerico("1");
            botonesGenericosSteps.setReferenciaGenerica("1234567890");
            botonesGenericosSteps.clickbtnPagar();
            botonesGenericosSteps.validarTituloPageGenerico("Nuevo pago");
            botonesGenericosSteps.clickbtnPagar();
            botonesGenericosSteps.clickConfirmarGenerico();
            botonesGenericosSteps.validarTituloPageGenerico("Estado de tus pagos");
            botonesGenericosSteps.clickBtnGenerico("Descargar todos");
            UtilsGP.fixedWait(20000);
            botonesGenericosSteps.validarDescargaSinEliminarArchivo("Ticket.pdf");
            botonesGenericosSteps.validarDescargaSinEliminarArchivo("Ticket (1).pdf");
            botonesGenericosSteps.eliminarArchivoDescargado("Ticket.pdf");
            botonesGenericosSteps.eliminarArchivoDescargado("Ticket (1).pdf");

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }


    @Test
    @DisplayName("TC-110847-BSF: Web-Detalle de los pagos realizados en simultaneo- Pantalla Estado de tus pagos")
    public void TC110847BSF() throws AWTException {
        try {
            testCaseData.cargaDeDatosTests("TC110847BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario, testCaseData.password,Settings.PAGOS);
            botonesGenericosSteps.validarTituloPageGenerico("Pago de servicios");
            //nuevoPagoSteps.validarBotonMostrarTodos();
            pagosSteps.clickCheckboxServicio("1");
            pagosSteps.clickCheckboxServicio("2");
            botonesGenericosSteps.clickbtnPagar();
            botonesGenericosSteps.setImporteGenerico("1");
            botonesGenericosSteps.setReferenciaGenerica("1234567890");
            botonesGenericosSteps.clickContinuarGenerico();
            botonesGenericosSteps.setImporteGenerico("1");
            botonesGenericosSteps.setReferenciaGenerica("1234567890");
            botonesGenericosSteps.clickbtnPagar();
            botonesGenericosSteps.validarTituloPageGenerico("Nuevo pago");
            botonesGenericosSteps.clickbtnPagar();
            botonesGenericosSteps.clickConfirmarGenerico();
            botonesGenericosSteps.validarTituloPageGenerico("Estado de tus pagos");
            botonesGenericosSteps.clickBtnGenerico("Descargar todos");
            UtilsGP.fixedWait(20000);
            botonesGenericosSteps.validarDescargaSinEliminarArchivo("Ticket.pdf");
            botonesGenericosSteps.validarDescargaSinEliminarArchivo("Ticket (1).pdf");
            botonesGenericosSteps.eliminarArchivoDescargado("Ticket.pdf");
            botonesGenericosSteps.eliminarArchivoDescargado("Ticket (1).pdf");
            botonesGenericosSteps.clickVolverGenerico();
            botonesGenericosSteps.validarTituloPageGenerico("Pago de servicios");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-0184-BSF: DEBIN Vendedor - Generar orden de DEBIN - Validar campo requerido al no ingresar 'Alias' y seleccionar buscar.")
    public void TC0184BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC0184BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario, testCaseData.password,Settings.PAGOS);
            botonesGenericosSteps.validarTituloPageGenerico("Pago de servicios");
            UtilsGP.fixedWait(5000);
            pagosSteps.clickMasOpcionesGenerico();
            pagosSteps.clickDebinVendedor();
            debinVendedorSteps.clickOrdenDebin();
            debinVendedorSteps.clickCuentaDestinoDesplegable();
            debinVendedorSteps.seleccionarDropdownGenerico( testCaseData.cuentaOrigen);
            debinVendedorSteps.setImporteGenerico("1");
            debinVendedorSteps.clickAlias();
            debinVendedorSteps.setCbuOAlias(" ");
            debinVendedorSteps.clickBuscar();
            pagosSteps.validarCampoObligatorio();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    /**
     * Casos de prueba de la sección Echeq.
     *
     *
     */

    @Test
    @DisplayName("TC-124865-BSF: Web-Validar que la Aceptacion de un Echeq en estado 'Emitido pendiente' con la variable Encendida, " +
            "informe el Nuevo servicio de Aceptacion de Echeq")
    public void TC124865BSF() {
        try {
            //TEST
            testCaseData.cargaDeDatosTests("TC124865BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario, testCaseData.password,Settings.CUENTA);
            botonesGenericosSteps.validarTituloPageGenerico("Cuentas");
            cuentasSteps.clickCancelarGenerico();
            cuentasSteps.clickMasOpcionesGenerico();
            cuentasSteps.clickEcheq();
            echeqSteps.validarTituloPageGenerico("ECheq");
            transferenciasInmediatas.clickbtnFiltrar();
            echeqSteps.clickEstado();
            botonesGenericosSteps.seleccionarDropdownGenerico("Emitido pendiente");
            echeqSteps.clickBotonAplicarBusqueda();
            echeqSteps.clickVerDetallePrimerEcheq();
            echeqSteps.validarTituloPageGenerico("Detalle de ECheq");
            echeqSteps.validarTituloPageGenerico("Id Echeq");
            echeqSteps.validarTituloPageGenerico("Nro. ECheq");
            echeqSteps.validarTituloPageGenerico("Estado");
            botonesGenericosSteps.clickAceptarGenerico();
            botonesGenericosSteps.validarTituloPageGenerico("Aceptar ECheqs");
            echeqSteps.validarDatoEhceqGrilla("EMITIDO-PENDIENTE");
            botonesGenericosSteps.clickContinuarGenerico();
            botonesGenericosSteps.clickCheckTerminosCondicionesGenerico();
            dobleFactorSteps.codigoSegundoFactor();
            botonesGenericosSteps.validarTituloPageGenerico("Aceptar ECheqs");
            recargasSteps.clickIconoTicket();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
        }
        catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-29413-BSF: Web-Validar que la Aceptacion de un Echeq en estado 'Emitido pendiente' con la variable Encendida, " +
            "informe el Nuevo servicio de Aceptacion de Echeq")
    public void TC29413BSF() {
        try {
            //TEST
            testCaseData.cargaDeDatosTests("TC29413BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario, testCaseData.password,Settings.CUENTA);
            botonesGenericosSteps.validarTituloPageGenerico("Cuentas");
            cuentasSteps.clickCancelarGenerico();
            cuentasSteps.clickMasOpcionesGenerico();
            cuentasSteps.clickEcheq();
            echeqSteps.validarTituloPageGenerico("ECheq");
            transferenciasInmediatas.clickbtnFiltrar();
            echeqSteps.validarOrdenDeTitulosDeFiltrosDeBusqueda();
        }
        catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-29414-BSF: WEB-Búsqueda combinada de filtros (funcionalidad Buscar)")
    public void TC29414BSF() {
        try {
            //TEST
            testCaseData.cargaDeDatosTests("TC29414BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario, testCaseData.password,Settings.CUENTA);
            botonesGenericosSteps.validarTituloPageGenerico("Cuentas");
            cuentasSteps.clickCancelarGenerico();
            cuentasSteps.clickMasOpcionesGenerico();
            cuentasSteps.clickEcheq();
            echeqSteps.validarTituloPageGenerico("ECheq");
            transferenciasInmediatas.clickbtnFiltrar();
            echeqSteps.setCuitCuilEmisor("20059373863");
            echeqSteps.clickEstado();
            botonesGenericosSteps.seleccionarDropdownGenerico("Emitido pendiente");
            calendarioSteps.clickEnFechaDeCreacionDesde();
            calendarioSteps.clickMesAnterior(6);
            calendarioSteps.clickEnDiaXParametro("1");
            calendarioSteps.clickEnFechaDeCreacionHasta();
            calendarioSteps.clickMesProximo(6);
            calendarioSteps.clickDiaActual();
            echeqSteps.clickBotonAplicarBusqueda();
            transferenciasInmediatas.clickbtnFiltrar();
            echeqSteps.validarDatoBusquedaEhceqGrilla6meses("20059373863", "Emitido-Pendiente");
        }
        catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-29415-BSF: WEB-Opciones del dropdown -Estado- correctas")
    public void TC29415BSF() {
        try {
            //TEST
            testCaseData.cargaDeDatosTests("TC29415BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario, testCaseData.password,Settings.CUENTA);
            botonesGenericosSteps.validarTituloPageGenerico("Cuentas");
            cuentasSteps.clickCancelarGenerico();
            cuentasSteps.clickMasOpcionesGenerico();
            cuentasSteps.clickEcheq();
            echeqSteps.validarTituloPageGenerico("ECheq");
            transferenciasInmediatas.clickbtnFiltrar();
            echeqSteps.validarDropdownEstado();
        }
        catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-29486-BSF: WEB- Emitidos - Casuística 1.2 - Barra inferior flotante - ECheqs de distinto estado")
    public void TC29486BSF() {
        try {
            //TEST
            testCaseData.cargaDeDatosTests("TC29486BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario, testCaseData.password,Settings.CUENTA);
            botonesGenericosSteps.validarTituloPageGenerico("Cuentas");
            cuentasSteps.clickCancelarGenerico();
            cuentasSteps.clickMasOpcionesGenerico();
            cuentasSteps.clickEcheq();
            botonesGenericosSteps.validarTituloPageGenerico("ECheq");
            echeqSteps.clickTabEmitidos();
            echeqSteps.seleccionarEcheq("1", "2", "3");
            echeqSteps.validarTextoEstadoOperaativo();
        }
        catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-29487-BSF: WEB- Tab Recibidos - Detalle de ECheq Visualización guión en -Motivo de Pago-")
    public void TC29487BSF() {
        try {
            //TEST
            testCaseData.cargaDeDatosTests("TC29487BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario, testCaseData.password,Settings.CUENTA);
            botonesGenericosSteps.validarTituloPageGenerico("Cuentas");
            cuentasSteps.clickCancelarGenerico();
            cuentasSteps.clickMasOpcionesGenerico();
            cuentasSteps.clickEcheq();
            botonesGenericosSteps.validarTituloPageGenerico("ECheq");
            echeqSteps.validarGuionEnDetalleMotivoDePago();
        }
        catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-29488-BSF WEB- WEB-Visualización correcta de estado Cesión - Pendiente en TAB Recibidos – WEB")
    public void TC29488BSF() {
        try {
            //TEST
            testCaseData.cargaDeDatosTests("TC29488BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario, testCaseData.password,Settings.CUENTA);
            botonesGenericosSteps.validarTituloPageGenerico("Cuentas");
            cuentasSteps.clickCancelarGenerico();
            cuentasSteps.clickMasOpcionesGenerico();
            cuentasSteps.clickEcheq();
            botonesGenericosSteps.validarTituloPageGenerico("ECheq");
            echeqSteps.validarEstadoCesionPendiente();
        }
        catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-29489-BSF WEB- WEB-Visualización correcta de estado Devolución - Pendiente en TAB Recibidos – WEB")
    public void TC29489BSF() {
        try {
            //TEST
            testCaseData.cargaDeDatosTests("TC29489BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario, testCaseData.password,Settings.CUENTA);
            botonesGenericosSteps.validarTituloPageGenerico("Cuentas");
            cuentasSteps.clickCancelarGenerico();
            cuentasSteps.clickMasOpcionesGenerico();
            cuentasSteps.clickEcheq();
            botonesGenericosSteps.validarTituloPageGenerico("ECheq");
            echeqSteps.validarEstadoDevolucionPendiente();
        }
        catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

//    @Test
//    @DisplayName("TC-128242-BSF: Web - VU Fraud - Verificar que al ejecutarse el activity adhesionAccount se envíe el metodoValidacionFP con la marca de validación del dispositivo")
//    public void TC128242BSF() {
//        try {
//            testCaseData.cargaDeDatosTests("TC128242BSF");
//            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.TRANSFERENCIAS);
//            segurosSteps.validarTituloPageGenerico("Transferencias");
//            transferenciasInmediatas.seleccionarTipoTransferencia("Contacto Agendado");
//            transferenciasInmediatas.clickAgendaDestinatario();
//            agendaNuevoDestinatarioSteps.clickAdherirNuevoDestinatario();
//            agendaNuevoDestinatarioSteps.setInputCbuAlias(testCaseData.cbuDestino);
//            agendaNuevoDestinatarioSteps.clickBuscar();
//            transferenciasInmediatas.botonSonCorrectos();
//            dobleFactorSteps.codigoSegundoFactor();
//            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
//            transferenciasInmediatas.guardarNroDeOperacion();
//            loginapi.validarServicioFraudAnalysis();
//
//
//        } catch (Exception ex) {
//            System.out.println("Error: " + ex.getMessage());
//            Assert.fail("Ocurrio un error: " + ex.getMessage());
//        }
//    }

    @After
    public void cerrarSesionCapturarUPS(){
        if(utilsGP.estaPresente(botonesGenericosPage.getCodigoUps(),2) && botonesGenericosPage.getCodigoUps().getText().startsWith("#")) {
            String ups = botonesGenericosPage.getCodigoUps().getText();
            global.practia.jemeinsamstark.web.core.WebDriver.getInstance().navigate().to(System.getProperty("appUrl"));
            System.out.println("Se Espera que aparezca la pagina de nuevo");
            loginSteps.waitGP();
            Assert.fail("UPS CODIGO : " + ups);
        }else{
            System.out.println("=================== NO PUDO INICIAR SESION REFRESCAR NAVEGADOR ==============================");
            Allure.addAttachment("INICIO SESSION,","=================== NO PUDO INICIAR SESION REFRESCAR NAVEGADOR ==============================");
            global.practia.jemeinsamstark.web.core.WebDriver.getInstance().navigate().to(System.getProperty("appUrl"));
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
