package GP.EntreRios.front;

import GP.Tags.Criticos;
import GP.Tags.Media;
import api.EcheqAPI;
import db.ConexionDB;
import enums.inversiones.fci.FiltroMonedaFci;
import enums.inversiones.fci.FiltroRiesgoFci;
import io.qameta.allure.Allure;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.*;
import org.junit.experimental.categories.Category;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import pages.botonesgenericos.BotonesGenericosPage;
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
import steps.inversiones.InversionesSteps;
import steps.inversiones.compraventadedolares.CompraVentaDeDolaresSteps;
import steps.inversiones.cuentacomitente.CuentaComitenteSteps;
import steps.inversiones.fci.FondoComunInversionSteps;
import steps.inversiones.fci.RescateFCISteps;
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
import steps.utillogger.UtilLoggerSteps;
import utils.Constantes;
import utils.Settings;
import utils.TestCaseData;
import utils.UtilsGP;
import steps.cuentas.solicitarnuevacuenta.SolicitarNuevaCuentaSteps;

import java.awt.*;
import java.io.IOException;

import static db.ConexionDB.cerrarConexion;
import static utils.Constantes.CORREO;
import static utils.Constantes.uSantafe036;
import static utils.UtilsGP.generarString;

public class FrontTest {
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
    UtilsGP utilsGP = new UtilsGP();
    TestCaseData testCaseData = new TestCaseData();
    EcheqAPI echeqAPI = new EcheqAPI();
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

        global.practia.jemeinsamstark.web.core.WebDriver.getInstance().manage().window().setSize(new Dimension(1920,1080));
    }
    /**
     * Casos de pruebas Sección Bonos de Salud.
     *
     * Se necesita que los usuarios en esta sección esten pre-calificados para la operatoria.
     * El setting del ambiente para esta sección depende de la Sección "Pagos" - Servicio por SOAP (variables con valor "false" en BO - CHANELS HB MHB).
      */
    //@Test
    @DisplayName("TC -0081 BERSA - Bonos de salud-  Consultar compras")
    public void TC0081BERSA() {
        try {
            testCaseData.cargaDeDatosTests("TC0081BERSA");
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
    //@Test
    @DisplayName("TC-0091 BERSA - Bonos de salud - Adhesiones - Afiliados adheridos-")
    public void TC0091BERSA() {
        try {
            //PRECONDICION
            testCaseData.cargaDeDatosTests("TC0091BERSA");
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
    //@Test
    @DisplayName("TC-0025-BERSA: Echeq - Completitud de pantalla con filtro: Estado = Todos")
    public void TC0025BERSA() {
        try {
            testCaseData.cargaDeDatosTests("TC0025BERSA");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.ECHEQS);
            echeqSteps.validarTituloPageGenerico("Echeq");
            echeqSteps.keyDownXParametrosGenerico(13, "Todos");
            echeqSteps.clickBotonBuscar();
            echeqSteps.validarCompletitudPantalla();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0026-BERSA: Echeq - Consulta de Echeq - Verificar detalle de echeq - Estado = Todos")
    public void TC0026BERSA() {
        try {
            testCaseData.cargaDeDatosTests("TC0026BERSA");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.ECHEQS);
            echeqSteps.validarTituloPageGenerico("ECheq");
            transferenciasInmediatas.clickbtnFiltrar();
            echeqSteps.keyDownXParametrosGenerico(13, "Todos");
            echeqSteps.clickBotonAplicarBusqueda();
            echeqSteps.clickVerDetallePrimerEcheq();
            echeqSteps.validarTituloPageGenerico("Detalle de ECheq");
            echeqSteps.validarTituloPageGenerico("Id ECheq");
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
    @DisplayName("TC-0040-BERSA: Acceder a Consulta de movimientos de cuenta")
    public void TC0040BERSA() {
        try {
            testCaseData.cargaDeDatosTests("TC0040BERSA");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.CUENTA);
            botonesGenericosSteps.clickBtnCancelarSobreCartelEmpezaAOperar();
            cuentasSteps.validarTituloPageGenerico("Cuentas");
            cuentasSteps.clickVerMasXnroCuenta(testCaseData.cuentaOrigen);
            detalleCuentaSteps.clickDetalleMovimientoPorNroFila("1");
            detalleCuentaSteps.validarTituloPageGenerico("Fecha");
            //detalleCuentaSteps.validarTituloPageGenerico("de Comprobante");
            detalleCuentaSteps.validarTituloPageGenerico("Concepto");
            detalleCuentaSteps.validarTituloPageGenerico("Monto");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0041-BERSA: Filtrar Movimientos por Tipo en una cuenta. Elegir Todos")
    public void TC0041BERSA() {
        try {
            testCaseData.cargaDeDatosTests("TC0041BERSA");
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickCuentas();
            botonesGenericosSteps.clickBtnCancelarSobreCartelEmpezaAOperar();
            cuentasSteps.validarTituloPageGenerico("Cuentas");
            cuentasSteps.clickVerMasXnroCuenta(testCaseData.cuentaOrigen);
            detalleCuentaSteps.clickMasFiltros();
            calendarioSteps.clickEnFechaDeCreacionDesde();
            calendarioSteps.clickMesAnterior(2);
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
    @DisplayName("TC-128241-BERSA: Acceder a Consulta de movimientos de cuenta")
    public void TC128241BERSA() {
        try {
            testCaseData.cargaDeDatosTests("TC128241BERSA");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.CUENTA);
            botonesGenericosSteps.clickBtnCancelarSobreCartelEmpezaAOperar();
            cuentasSteps.validarTituloPageGenerico("Cuentas");
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
    @DisplayName("TC-29410-BERSA: Web-Botón continuar deshabilitado-CA pesos")
    public void TC29410BERSA() {
        try {
            testCaseData.cargaDeDatosTests("TC29410BERSA");
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
    @DisplayName("TC-29411-BERSA: Web-Botón continuar deshabilitado-CA dolares")
    public void TC29411BERSA() {
        try {
            testCaseData.cargaDeDatosTests("TC29411BERSA");
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
    @DisplayName("TC-29412-BERSA: Web-Botón continuar deshabilitado-CC pesos")
    public void TC29412BERSA() {
        try {
            testCaseData.cargaDeDatosTests("TC29412BERSA");
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
    //@Test
    @DisplayName("TC-0239-BERSA: Adelantos de Sueldo - Realizar solicitud de adelanto de sueldo - Validar campos requeridos")
    public void TC0239BERSA() {
        try {
            testCaseData.cargaDeDatosTests("TC0239BERSA");
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
    //@Test
    @DisplayName("TC-12978-BERSA: OFERTA - Adelanto de Sueldo - Acceso Directo")
    public void TC12978BERSA() {
        try {
            testCaseData.cargaDeDatosTests("TC12978BERSA");
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
    @DisplayName("TC-0222-BERSA: Adelantos de Sueldo - Solicitud de Adelanto de Sueldo - Validar Pantalla completa")
    public void TC0222BERSA() {
        try {
            testCaseData.cargaDeDatosTests("TC0222BERSA");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.ESCRITORIO);
            adelantosDeSueldoSteps.validarTituloPageGenerico("Hola");
            menuLateralSteps.clickCuentas();
            botonesGenericosSteps.clickBtnCancelarSobreCartelEmpezaAOperar();
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
    @DisplayName("TC-0225-BERSA: Adelantos de Sueldo - Realizar Solicitud de adelanto de sueldo - Cancelar operacion al presionar volver - Validar pantalla de solicitud de adelanto de sueldo")
    public void TC0225BERSA() {
        try {
            testCaseData.cargaDeDatosTests("TC0225BERSA");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.CUENTA);
            botonesGenericosSteps.clickBtnCancelarSobreCartelEmpezaAOperar();
            adelantosDeSueldoSteps.validarTituloPageGenerico("Cuentas");
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
    @DisplayName("TC-0226-BERSA: Adelantos de Sueldo - Pantalla de confirmacion de solicitud - Confirmar sin seleccionar valores requeridos - validar campos obligatorios")
    public void TC0226BERSA() {
        try {
            testCaseData.cargaDeDatosTests("TC0226BERSA");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.CUENTA);
            adelantosDeSueldoSteps.validarTituloPageGenerico("Cuentas");
            botonesGenericosSteps.clickBtnCancelarSobreCartelEmpezaAOperar();
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
    @DisplayName("TC-0227-BERSA: Adelantos de Sueldo - Solicitud de Adelanto de Sueldo - Resumen de contrato - Validar redireccion a documento pdf")
    public void TC0227BERSA() {
        try {
            testCaseData.cargaDeDatosTests("TC0227BERSA");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.CUENTA);
            adelantosDeSueldoSteps.validarTituloPageGenerico("Cuentas");
            botonesGenericosSteps.clickBtnCancelarSobreCartelEmpezaAOperar();
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
    @DisplayName("TC-0228-BERSA: Adelantos de Sueldo - Resumen de contrato - Cancelar redireccion a documento pdf - validar volver a la pagina de solicitud de adelanto de sueldo")
    public void TC0228BERSA() {
        try {
            testCaseData.cargaDeDatosTests("TC0228BERSA");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.CUENTA);
            botonesGenericosSteps.clickBtnCancelarSobreCartelEmpezaAOperar();
            adelantosDeSueldoSteps.validarTituloPageGenerico("Cuentas");
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
    @DisplayName("TC-0237-BERSA: Adelantos de Sueldo - Realizar solicitud de adelanto de sueldo  - Validar mensaje de error ingreso de monto MENOR al minimo")
    public void TC0237BERSA() {
        try {
            testCaseData.cargaDeDatosTests("TC0237BERSA");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.CUENTA);
            botonesGenericosSteps.clickBtnCancelarSobreCartelEmpezaAOperar();
            adelantosDeSueldoSteps.validarTituloPageGenerico("Cuentas");
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
    @DisplayName("TC-0238-BERSA: Adelantos de Sueldo - Realizar solicitud de adelanto de sueldo  - Validar mensaje de error ingreso de monto MAYOR al maximo")
    public void TC0238BERSA() {
        try {
            testCaseData.cargaDeDatosTests("TC0238BERSA");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.CUENTA);
            botonesGenericosSteps.clickBtnCancelarSobreCartelEmpezaAOperar();
            adelantosDeSueldoSteps.validarTituloPageGenerico("Cuentas");
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
     * Casos de prueba de la sección Cuentas - Cuotificalo
     *
     * Se necesita tener compras en la TD para poder realizar el proceso - Posible Pre-calificacion del usuario
     * No se esta generando datos para cuotificar.
     */
    //@Test
    @DisplayName("TC-0089-BERSA: Cuotifícalo - Cuotificar compras con TD")
    public void TC0089BERSA() {
        try {
            testCaseData.cargaDeDatosTests("TC0089BERSA");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.CUENTA);
            cuentasSteps.validarTituloPageGenerico("Cuentas");
            cuentasSteps.clickVerMas1erCuenta();
            cuotificaloSteps.clickCuotificalo();
            cuotificaloSteps.validarTituloPageGenerico("Cuotificalo");
            cuotificaloSteps.clickEnPrimerosCuatroCheckboxes();
            cuotificaloSteps.clickContinuarGenerico();
            Thread.sleep(1000);
            cuotificaloSteps.clickOpcionPlazoDeDoceCuotas();
            cuotificaloSteps.clickCuentaAcreditacion();
            //wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='accreditationAccount']/parent::div//div[contains(text(), 'CA $ 000003554002')]")));
            cuotificaloSteps.keyDownXParametrosGenerico(0, "CA $ 001000090502");
            cuotificaloSteps.scrollTo(By.xpath("//input[@name='debitAccount']/preceding-sibling::div"));
            cuotificaloSteps.clickCuentaDebito();
            //wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='debitAccount']/parent::div//div[contains(text(), 'Caja de Ahorro $ 000003554002 ')]")));
            cuotificaloSteps.keyDownXParametrosGenerico(0, "Caja de Ahorro $ 000003554002 ");
            cuotificaloSteps.clickContinuarGenerico();
            cuotificaloSteps.scrollTo(By.xpath("//*[@name='agreement']/following-sibling::span"));
            cuotificaloSteps.clickCheckTerminosCondicionesGenerico();
            dobleFactorSteps.codigoSegundoFactor();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
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
    //@Test
    @DisplayName("TC-0117-BERSA: FCI- Filtros - Mi Tenencia - Fondos por Riesgo")
    public void TC0117BERSA() {
        try {
            testCaseData.cargaDeDatosTests("TC0117BERSA");
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
    @DisplayName("TC-0118-BERSA: FCI- Filtros - Mi Tenencia - Fondos por Riesgo - Conservador")
    public void TC0118BERSA() {
        try {
            testCaseData.cargaDeDatosTests("TC0118BERSA");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.FCI);
            fondoComunInversionSteps.validarTituloPageGenerico("Información de fondos");
            //fondoComunInversionSteps.selectorCuentaComitente("2-000024678");
            fondoComunInversionSteps.seleccionarUnicamenteFiltrosPorRiesgo(FiltroRiesgoFci.CONSERVADOR);
            fondoComunInversionSteps.validaFondosPorRiesgoVisiblesEnListado("Conservador");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0119-BERSA: FCI- Filtros - Mi Tenencia - Fondos por Riesgo - Moderado")
    public void TC0119BERSA() {
        try {
            testCaseData.cargaDeDatosTests("TC0119BERSA");
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
    @DisplayName("TC-0120-BERSA: FCI- Filtros - Mi Tenencia - Fondos por Riesgo - Dinámico")
    public void TC0120BERSA() {
        try {
            testCaseData.cargaDeDatosTests("TC0120BERSA");
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
    @DisplayName("TC- 0173- BERSA-  Inversiones- Alta cuenta comitente- Solicitar cuenta comitente  - Validar pantalla etapa '5 Confirmacion'")
    public void TC0173BERSA() {
        try {
            testCaseData.cargaDeDatosTests("TC0173BERSA");
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
            cuentaComitenteSteps.clickAceptarTyCGenerico();
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
    @DisplayName("TC-0126-BERSA: FCI - Filtros: Fondos por moneda - Pesos")
    public void TC0126BERSA() {
        try {
            testCaseData.cargaDeDatosTests("TC0126BERSA");
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
    @DisplayName("TC-0127-BERSA: FCI - Filtros: Fondos por moneda - Dólar")
    public void TC0127BERSA() {
        try {
            testCaseData.cargaDeDatosTests("TC0127BERSA");
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
    @DisplayName("TC-0128-BERSA: FCI - Filtros: Mi Tenencia")
    public void TC0128BERSA() {
        try {
           testCaseData.cargaDeDatosTests("TC0128BERSA");
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
    @DisplayName("TC-0133-BERSA: FCI  - Mas información - Aceptar Modal")
    public void TC0133BERSA() {
        try {
           testCaseData.cargaDeDatosTests("TC0133BERSA");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.FCI);
            fondoComunInversionSteps.validarTituloPageGenerico("Información de fondos");
            fondoComunInversionSteps.clickIconoAccionesDetalleFCI("A-Delta Ahorro");
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
    @DisplayName("TC-0134-BERSA: FCI  - Mas información - Rechazar Modal")
    public void TC0134BERSA() {
        try {
           testCaseData.cargaDeDatosTests("TC0134BERSA");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.FCI);
            fondoComunInversionSteps.validarTituloPageGenerico("Información de fondos");
            fondoComunInversionSteps.clickIconoAccionesDetalleFCI("A-Delta Ahorro");
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
    @DisplayName("TC-0044-BERSA: PF - Consulta de Tasas Tipo - Tradicional en Pesos")
    public void TC0044BERSA() {
        try {
            testCaseData.cargaDeDatosTests("TC0044BERSA");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.PLAZO_FIJO);
            fondoComunInversionSteps.validarTituloPageGenerico("Plazo fijo");
            plazosFijosSteps.clickConsultaTasas();
            consultaDeTasasSteps.validarTituloPageGenerico("Consulta de Tasas");
            consultaDeTasasSteps.clickSelectorTipoPlazoFijo();
            consultaDeTasasSteps.seleccionarDropdownGenerico( "Plazo Fijo Tradicional en Pesos");
            //consultaDeTasasSteps.clickSelectorTipoConsulta();
            UtilsGP.fixedWait(8000);
            //consultaDeTasasSteps.seleccionarDropdownGenerico("Monto");
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
    @DisplayName("TC-0045-BERSA: PF - Consulta de Tasas - Tipo - Tradicional en Dólares")
    public void TC0045BERSA() {
        try {
            testCaseData.cargaDeDatosTests("TC0045BERSA");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.PLAZO_FIJO);
            fondoComunInversionSteps.validarTituloPageGenerico("Plazo fijo");
            plazosFijosSteps.clickConsultaTasas();
            consultaDeTasasSteps.validarTituloPageGenerico("Consulta de Tasas");
            consultaDeTasasSteps.clickSelectorTipoPlazoFijo();
            consultaDeTasasSteps.seleccionarDropdownGenerico( "Plazo Fijo Tradicional en D");
//            consultaDeTasasSteps.clickSelectorTipoConsulta();
            UtilsGP.fixedWait(8000);
//            consultaDeTasasSteps.seleccionarDropdownGenerico("Monto");
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
    //@Test
    @DisplayName("TC-0046 BERSA - PF - Consulta de Tasas - Tipo - UVA")
    public void TC0046BERSA() {
        try {
            testCaseData.cargaDeDatosTests("TC0046BERSA");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.PLAZO_FIJO);
            fondoComunInversionSteps.validarTituloPageGenerico("Plazo fijo");
            plazosFijosSteps.clickConsultaTasas();
            consultaDeTasasSteps.validarTituloPageGenerico("Consulta de Tasas");
            consultaDeTasasSteps.clickSelectorTipoPlazoFijo();
            consultaDeTasasSteps.seleccionarPFU();
//            solicitarPlazoFijoSteps.clickSelectorTipoPF("Plazo Fijo UVA");
            //consultaDeTasasSteps.seleccionarDropdownGenerico( "Plazo Fijo UVA");
//            consultaDeTasasSteps.clickSelectorTipoConsulta();
            UtilsGP.fixedWait(8000);
//            consultaDeTasasSteps.seleccionarDropdownGenerico("Monto");
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
    //@Test
    @DisplayName("TC-0047-BERSA - PF - Consulta de Tasas - Tipo - UVA Precancelable")
    public void TC0047BERSA() {
        try {
            testCaseData.cargaDeDatosTests("TC0047BERSA");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.PLAZO_FIJO);
            fondoComunInversionSteps.validarTituloPageGenerico("Plazo fijo");
            plazosFijosSteps.clickConsultaTasas();
            consultaDeTasasSteps.validarTituloPageGenerico("Consulta de Tasas");
            consultaDeTasasSteps.clickSelectorTipoPlazoFijo();
            consultaDeTasasSteps.seleccionarDropdownGenerico( "Plazo Fijo UVA Precancelable");
//            consultaDeTasasSteps.clickSelectorTipoConsulta();
            UtilsGP.fixedWait(8000);
//            consultaDeTasasSteps.seleccionarDropdownGenerico("Monto");
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
    @DisplayName("TC-128244-BERSA: Web - Alta de PF - Verificar lógica de control del botón Continuar y mensaje de error en Simulación y alta de PF")
    public void TC128244BERSA() {
        try {
            testCaseData.cargaDeDatosTests("TC128244BERSA");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.PLAZO_FIJO);
            inversionesSteps.clickPlazosFijos();
            plazosFijosSteps.clickSolicitarPlazoFijo();
            plazosFijosSteps.validarBtnContinuarDeshabilitado();
            plazosFijosSteps.validarBtnContinuarHabilitado(testCaseData.cuentaOrigen);
            botonesGenericosSteps.clickVolverFlechaGenerico();
            plazosFijosSteps.clickSolicitarPlazoFijo();
            plazosFijosSteps.validarBtnContinuarHabilitadoDolares(testCaseData.cuentaDestino);
            botonesGenericosSteps.clickVolverFlechaGenerico();
            plazosFijosSteps.clickSolicitarPlazoFijo();
            plazosFijosSteps.validarBtnContinuarHabilitadoUva(testCaseData.cuentaOrigen);
            botonesGenericosSteps.clickVolverFlechaGenerico();
            plazosFijosSteps.clickSolicitarPlazoFijo();
            plazosFijosSteps.validarBtnContinuarHabilitadoPrecancelable(testCaseData.cuentaOrigen);
            botonesGenericosSteps.clickVolverFlechaGenerico();
            plazosFijosSteps.clickSolicitarPlazoFijo();
            plazosFijosSteps.validarMensajeMontoSuperaSaldoCta(testCaseData.cuentaOrigen);
            plazosFijosSteps.validarBotonContinuarHabilitadoSaldoModificado();

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
    @DisplayName("TC- 0178- BERSA-  Inversiones- Compra de moneda extranjera - Colocar importe 0 de compra de dolar - Validar mensaje 'No se pudo realizar la operación'")
    public void TC0178BERSA() {
        try {
            testCaseData.cargaDeDatosTests("TC0178BERSA");
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
    @DisplayName("TC- 0179- BERSA-  Inversiones- Compra de moneda extranjera - Seguir flujo de compra de dolar sin aceptar check terminos y condiciones - Validar campo obligatorio")
    public void TC0179BERSA() {
        try {
            testCaseData.cargaDeDatosTests("TC0179BERSA");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.COMPRA_VENTA_USD);
            compraVentaDeDolaresSteps.validarTituloPageGenerico("Compra venta de d");
            compraVentaDeDolaresSteps.clickTipoDeOperacion();
            compraVentaDeDolaresSteps.seleccionarDropdownGenerico( "Comprar");
            compraVentaDeDolaresSteps.clickCuentaOrigen();
            compraVentaDeDolaresSteps.seleccionarDropdownGenerico(testCaseData.cuentaOrigen);
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
    @DisplayName("TC- 0181- BERSA-  Inversiones- Compra de moneda extranjera - Validar desplegable Cuenta Origen solo cuentas en Pesos")
    public void TC0181BERSA() {
        try {
            testCaseData.cargaDeDatosTests("TC0181BERSA");
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
    @DisplayName("TC- 0182- BERSA-  Inversiones- Compra de moneda extranjera - Validar desplegable Cuenta Destino solo cuentas en Dolares")
    public void TC0182BERSA() {
        try {
            testCaseData.cargaDeDatosTests("TC0182BERSA");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.COMPRA_VENTA_USD);
            compraVentaDeDolaresSteps.validarTituloPageGenerico("Compra venta de d");
            compraVentaDeDolaresSteps.clickTipoDeOperacion();
            compraVentaDeDolaresSteps.keyDownToText("Comprar");
            compraVentaDeDolaresSteps.validarDesplegableCuentasEnDolares("cuentaDestino");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC- 0187- BERSA-  Inversiones- Venta de moneda extranjera - Colocar importe 0 de compra de dolar - Validar mensaje 'No se pudo realizar la operación'")
    public void TC0187BERSA() {
        try {
            testCaseData.cargaDeDatosTests("TC0187BERSA");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.COMPRA_VENTA_USD);
            compraVentaDeDolaresSteps.validarTituloPageGenerico("Compra venta de d");
            compraVentaDeDolaresSteps.clickTipoDeOperacion();
            compraVentaDeDolaresSteps.seleccionarDropdownGenerico( "Vender");
            compraVentaDeDolaresSteps.clickCuentaOrigen();
            compraVentaDeDolaresSteps.seleccionarDropdownGenerico(testCaseData.cuentaOrigen);
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
    @DisplayName("TC- 0188- BERSA-  Inversiones- Venta de moneda extranjera - Seguir flujo de venta de dolar sin aceptar check terminos y condiciones - Validar campo obligatorio")
    public void TC0188BERSA() {
        try {
            testCaseData.cargaDeDatosTests("TC0188BERSA");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.COMPRA_VENTA_USD);
            compraVentaDeDolaresSteps.validarTituloPageGenerico("Compra venta de d");
            compraVentaDeDolaresSteps.clickTipoDeOperacion();
            compraVentaDeDolaresSteps.seleccionarDropdownGenerico( "Vender");
            compraVentaDeDolaresSteps.clickCuentaOrigen();
            compraVentaDeDolaresSteps.seleccionarDropdownGenerico(testCaseData.cuentaOrigen);
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
    @DisplayName("TC- 0189- BERSA-  Inversiones- Venta de moneda extranjera - Validar desplegable Cuenta Destino solo cuentas en Pesos")
    public void TC0189BERSA() {
        try {
            testCaseData.cargaDeDatosTests("TC0189BERSA");
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
    @DisplayName("TC- 0190- BERSA-  Inversiones- Venta de moneda extranjera - Validar desplegable Cuenta Origen solo cuentas en Dolares")
    public void TC0190BERSA() {
        try {
            testCaseData.cargaDeDatosTests("TC0190BERSA");
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
    @DisplayName("TC-128245- BERSA-  Web - Compra/Venta USD - Validar mensaje no puedo operar USD en dias no habiles y feriados'")
    public void TC128245BERSA() {
        try {
            testCaseData.cargaDeDatosTests("TC128245BERSA");
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
    @DisplayName("TC-0017-BERSA: Autenticación con usuario - Login Exitoso")
    public void TC0017BERSA() {
        try {
            testCaseData.cargaDeDatosTests("TC0017BERSA");
            loginSteps.iniciarSesionFront(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickInicio();
            loginSteps.validarTituloPageGenerico("Hola Diewc, Mxdq Mrvh Odxuhqwino");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0048-BERSA: Recupero de Usuario - Recuperar Usuario")
    public void TC0048BERSA(){
        try {
            testCaseData.cargaDeDatosTests("TC0048BERSA");
            String randomStr = RandomStringUtils.randomAlphabetic(4);
            //TEST
            loginSteps.waitGP();
            loginSteps.clickIngresarStartPage();
            loginSteps.setUsuario(testCaseData.usuario);
            loginSteps.clickRecuperarUsuario();
            loginSteps.setNumeroCuil("20136570820");
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
    @DisplayName("TC-128240-BERSA: Web - Login - Validar pantalla de Recupero de usuario y y el control del campo mandatorio CUIL")
    public void TC128240BERSA(){
        try {
            testCaseData.cargaDeDatosTests("TC128240BERSA");
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
    @DisplayName("TC-128243-BERSA: Web - Home - Validar la visualización del Alert Information en formato ADVERTENCIA")
    public void TC128243BERSA() {
        try {
            testCaseData.cargaDeDatosTests("TC128243BERSA");
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
    @DisplayName("TC-0086-BERSA: Mensajes - Marcar no leido")
    public void TC0086BERSA() {
        try {
            testCaseData.cargaDeDatosTests("TC0086BERSA");
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
    @DisplayName("TC-0090-BERSA: Mensajes - Eliminar msj.")
    public void TC0090BERSA() {
        try {
            testCaseData.cargaDeDatosTests("TC0090BERSA");
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
    @DisplayName("TC-0075-BERSA: Baja / Arrepentimiento - POPUP - Redirecciomamiento")
    public void TC0075BERSA(){
        try {
            String urlOnlineBanking = "https://www.bancosantafe.com.ar/baja-y-arrepentimiento";
            testCaseData.cargaDeDatosTests("TC0075BERSA");
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
    @DisplayName("TC-0077-BERSA: Administracion de productos - Descarga ")
    public void TC0077BERSA(){
        try {
            testCaseData.cargaDeDatosTests("TC0077BERSA");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.CONTRATOS);
            menuLateralSteps.validarTituloPageGenerico("Administraci");
            administracionDeProductosSteps.clickDesplegableProducto();
            administracionDeProductosSteps.keyDownToText("Alta Cuenta Comitente desde Home Banking");
            administracionDeProductosSteps.descargarArchivo();
            administracionDeProductosSteps.validarDescarga("Alta Cuenta Comitente desde Home Banking .pdf");
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
    @DisplayName("TC-0151-BERSA: PANTALLA PRINCIPAL - Ingresar - Loguearse con teclado virtual")
    public void TC0151BERSA(){
        try {
            testCaseData.cargaDeDatosTests("TC0151BERSA");
            loginSteps.iniciarSesionTecladoVirtual(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickInicio();
            loginSteps.verificarSaludo();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-0152-BERSA: PANTALLA PRINCIPAL - Ingresar - Acceso directo 'Tienda.Clic'")
    public void TC0152BERSA() {
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
    @DisplayName("TC-0154-BERSA: PANTALLA PRINCIPAL - Ingresar - Acceso directo 'Teléfonos Utiles'")
    public void TC0154BERSA() {
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
    @DisplayName("TC-0156-BERSA: PANTALLA PRINCIPAL - Ingresar - Acceso directo '¿No sos cliente aún? Registrarme'")
    public void TC0156BERSA() {
        try {
            loginSteps.waitGP();
            loginSteps.clickIngresarStartPage();
            pantallaPrincipalSteps.clickVolverFlechaGenerico();
        } catch (Exception ex){
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-0161-BERSA: PANTALLA PRINCIPAL - Ingresar - Enlace 'Información de seguridad'")
    public void TC0161BERSA() {
        try {
            loginSteps.waitGP();
            loginSteps.clickIngresarStartPage();
            pantallaPrincipalSteps.ClickInfoDeSeguridad();
            pantallaPrincipalSteps.PreguntasFrecuentes("Seguridad para usar Home Banking / Office Banking");
        } catch (Exception ex){
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    /**
     * Caoss de pruebas de la sección Perfil
     *
     */
   // @Test
    @DisplayName("TC-0002-BERSA: Cambio de Correo Electrónico - Cambio de correo electrónico exitoso")
    public void TC0002BERSA() {
        try {
            testCaseData.cargaDeDatosTests("TC0002BERSA");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.PERFIL);
            loginSteps.verificarGmail();
            perfilSteps.validarTituloPageGenerico("Mi Perfil");
            perfilSteps.clickModificarCorreo();
            modificarPerfilSteps.validarTituloPageGenerico("Modificar correo");
            modificarPerfilSteps.clickIconoEditar();
            modificarPerfilSteps.setEditarCorreo(CORREO);
            modificarPerfilSteps.clickModificarGenerico();
            dobleFactorSteps.codigoSegundoFactor();
            dobleFactorSteps.codigoVerificacionEmail(CORREO);
            //POSCONDICION
            perfilSteps.setearEmailOriginalAPI();
            modificarPerfilSteps.validarCambioEmail();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0004-BERSA: Cambio domicilio")
    public void TC0004BERSA() {
        try {
            testCaseData.cargaDeDatosTests("TC0004BERSA");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.PERFIL);
            perfilSteps.validarTituloPageGenerico("Mi Perfil");
            perfilSteps.clickModificarDomicilio();
            modificarPerfilSteps.validarTituloPageGenerico("Modificar Domicilio");
            modificarPerfilSteps.validarTituloPageGenerico("Podés verificar la dirección que tenemos registrada actualmente o actualizarla de ser necesario.");
            modificarPerfilSteps.clickDomicilio();
            modificarPerfilSteps.clickModificarDomicilio();
            modificarPerfilSteps.setCalle("Rosario");
            modificarPerfilSteps.setNumero("772");
            modificarPerfilSteps.setPiso("3");
            modificarPerfilSteps.setApartamento("24");
            modificarPerfilSteps.clickProvincia();
            modificarPerfilSteps.ingresarProvincia();
            modificarPerfilSteps.ingresarLocalidad();
            botonesGenericosSteps.clickContinuarGenerico();
            dobleFactorSteps.codigoSegundoFactor();
            modificarPerfilSteps.verificarDatos();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0035-BERSA: Cambio de sello/avatar de seguridad - Cambio de avatar")
    public void TC0035BERSA() {
        try {
            int idImagenActual;
            testCaseData.cargaDeDatosTests("TC0035BERSA");
            //PRECONDICION TOMAR ID IMAGEN ACTUAL
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
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
    @DisplayName("TC-0206-BERSA: Prestamos - Nuevo prestamo - Validar pantalla etapa 4 'Confirmacion'")
    public void TC0206BERSA() {
        try {
            testCaseData.cargaDeDatosTests("TC0206BERSA");
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
    @DisplayName("TC-0220-BERSA: Prestamos- Prestamo para cancelar deudas - Validar pantalla etapa 5 'Confirmacion'")
    public void TC0220BERSA() {
        try {
            testCaseData.cargaDeDatosTests("TC0220BERSA");
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
    @DisplayName("TC-0221-BERSA: Prestamos- Prestamo para cancelar deudas - Descargar resumen de contrato - validar descarga archivo")
    public void TC0221BERSA() {
        try {
            testCaseData.cargaDeDatosTests("TC0221BERSA");
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
    @DisplayName("TC-0196-BERSA: Seguros - Validar cards de Seguros disponibles")
    public void TC0196BERSA() {
        try {
            testCaseData.cargaDeDatosTests("TC0196BERSA");
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
    @DisplayName("TC-0197-BERSA: Seguros - Ingresar a un seguro - Validar detalle de seguro")
    public void TC0197BERSA() {
        try {
            testCaseData.cargaDeDatosTests("TC0197BERSA");
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
    @DisplayName("TC-12928-BERSA: SEGUROS - Alta de seguros  - Acceso por Seguro - Menú lateral - Solicitud  y validación de pantalla")
    public void TC12928BERSA() {
        try {
            testCaseData.cargaDeDatosTests("TC12928BERSA");
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
    @DisplayName("TC-0146-BERSA: Transferencias - Añadir nuevo destinatario - Con cbu invalido - Validar mensaje de error")
    public void TC0146BERSA() {
        try {
            testCaseData.cargaDeDatosTests("TC0146BERSA");
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
    @DisplayName("TC-0148-BERSA: Transferencias - Añadir nuevo destinatario - Con alias invalido - Validar mensaje de error")
    public void TC0148BERSA() {
        try {
            testCaseData.cargaDeDatosTests("TC0148BERSA");
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
    @Category({Criticos.class})
    @DisplayName("TC-18088-BERSA: Transferencia a cuentas propias con CBU de otra entidad con todos los campos completos")
    public void TC18088BERSA() {
        try {
            testCaseData.cargaDeDatosTests("TC18088BERSA");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.TRANSFERENCIAS);
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("0450500601800008112711");
            //TEST
            bonos.validarTituloPageGenerico("Transferencias");
            transferenciasInmediatas.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatas.completarInputAliasCbu("0450500601800008112711");
            transferenciasInmediatas.clickBuscarGenerico();
            transferenciasInmediatas.botonSonCorrectos();
            transferenciasInmediatas.setImporteGenerico("1");
            transferenciasInmediatas.clickConcepto();
            transferenciasInmediatas.seleccionarDropdownGenerico("Alquileres");
            //transferenciasInmediatas.seleccionarConcepto("Alquileres");
            transferenciasInmediatas.setDescripcion("TC18088");
            String FechaTransferencia = utilsGP.fechaYHoraActual();
            System.out.println("FechaTransferencia " + FechaTransferencia);
            transferenciasInmediatas.clickBotonTransferirAhora();
            transferenciasInmediatas.clickConfirmarGenerico();
            dobleFactorSteps.codigoSegundoFactor();
            transferenciasInmediatas.btnCerrar();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            transferenciasInmediatas.txtNroDeOperacion();
            ticketSteps.validarCampoValorTicket("Fecha", FechaTransferencia);
            ticketSteps.validarCampoValorTicket("Monto","$ 1,00");
            ticketSteps.validarCampoValorTicket("Emisor","PERHC, HOELR KHUNAN");
            ticketSteps.validarCampoValorTicket("Cuenta de origen","CA $ 020000052210");
            ticketSteps.validarCampoValorTicket("CUIT/CUIL emisor","20205056573");
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
    @DisplayName("TC-18096-BERSA: Saldo actualizado_Transferencias_Ctas. Propias en pesos del mismo banco")
    public void TC18096BERSA() {
        try {
            testCaseData.cargaDeDatosTests("TC18096BERSA");
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
//          transferenciasInmediatas.seleccionarConcepto("Alquileres");
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
    @DisplayName("TC-18099-BERSA: TRANSFERENCIAS - WEB - Transferencias a CBU - Historial de transferencias ")
    public void TC18099BERSA() {
        try {
            testCaseData.cargaDeDatosTests("TC18099BERSA");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.TRANSFERENCIAS);
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI(testCaseData.cbuDestino);
            //TEST
            bonos.validarTituloPageGenerico("Transferencias");
            transferenciasInmediatas.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatas.completarInputAliasCbu(testCaseData.cbuDestino);
            transferenciasInmediatas.clickBuscarGenerico();
            transferenciasInmediatas.botonSonCorrectos();
            botonesGenericosSteps.validarTituloPageGenerico("Transferencias");
            transferenciasInmediatas.setImporteGenerico("1");
            transferenciasInmediatas.clickConcepto();
            transferenciasInmediatas.seleccionarDropdownGenerico("Alquileres");
            transferenciasInmediatas.setDescripcion("TC18099");
            transferenciasInmediatas.clickBotonTransferirAhora();
            transferenciasInmediatas.clickConfirmarGenerico();
            dobleFactorSteps.codigoSegundoFactor();
            ticketSteps.validarTicket("Tu solicitud se realizo " +
                    "con exito");
            ticketSteps.clickDescargas();
            ticketSteps.validarDescarga("Ticket.pdf");
            botonesGenericosSteps.clickVolverGenerico();
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
    @DisplayName("TC-16756-BERSA: WEB - Transferencias realizadas - Ver mis transferencias ")
    public void TC16756BERSA() {
        try {
            testCaseData.cargaDeDatosTests("TC16756BERSA");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.TRANSFERENCIAS);
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI(testCaseData.cbuDestino);
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
    @DisplayName("TC-18103-BERSA: WEB - Transferencias realizadas - Ver mis transferencias ")
    public void TC18103BERSA() {
        try {
            testCaseData.cargaDeDatosTests("TC18103BERSA");
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
    @DisplayName("TC-18092-BERSA: WEB - Cambios en comprobante - TF Inmediatas a terceros - " +
            "Todos los campos - CBU otra entidad (banco)")
    public void TC18092BERSA() {
        try {
            String fechaTransferencia;
            testCaseData.cargaDeDatosTests("TC18092BERSA");
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
            ticketSteps.validarCampoValorTicket("Emisor","REYES, JOSE LUIS");
            ticketSteps.validarCampoValorTicket("Cuenta de origen","CC $ 035003401876");
            ticketSteps.validarCampoValorTicket("CUIT/CUIL emisor","20119120269");
            ticketSteps.validarCampoValorTicket("Destinatario","PEREZ, ROQUE");
            ticketSteps.validarCampoValorTicket("CBU/CVU destino","3300000620000035540021");
            ticketSteps.validarCampoValorTicket("CUIT/CUIL destino","20025000014");
            ticketSteps.validarCampoValorTicket("Entidad destino","NUEVO BANCO DE SANTA FE SOCIEDAD ANONIMA");
            ticketSteps.validarCampoValorTicket("Concepto","Alquileres");
            ticketSteps.validarCampoValorTicket("Descripción","TC18092");

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-128246-BERSA: Web - VU Fraud - Verificar que al ejecutarse el activity adhesionAccount se envíe el metodoValidacionFP con la marca de validación del dispositivo")
    public void TC128246BERSA() {
        try {
            testCaseData.cargaDeDatosTests("TC128246BERSA");
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
            UtilsGP.fixedWait(4000);
            utilLoggerSteps.clickBtnActualizar();
            UtilsGP.fixedWait(3000);
            utilLoggerSteps.clickRequestSvc("/FraudAnalysis/service/analyze");
            utilsGP.backMainTap();

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-128247-BERSA: Web - VU Fraud - Verificar que al ejecutarse el activity transfers.account.makeWithCaps se envíe el metodoValidacionFP con la marca de validación del dispositivo")
    public void TC128247BERSA() {
        try {
            testCaseData.cargaDeDatosTests("TC128247BERSA");
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
    @DisplayName("TC-128248-BERSA: Web - VU Fraud - Verificar que al ejecutarse el activity credin.generate se envíe el metodoValidacionFP con la marca de validación del dispositivo")
    public void TC128248BERSA() {
        try {
            testCaseData.cargaDeDatosTests("TC128248BERSA");
            loginSteps.iniciarSesionFrontConBearerApi(testCaseData.usuario, testCaseData.password);
            menuLateralSteps.clickSoloTransferencias();
            botonesGenericosSteps.clickBtnAhoraNo("Ahora no");
            segurosSteps.validarTituloPageGenerico("Transferencias");
            transferenciasSteps.clickMasOpcionesGenerico();
            transferenciasSteps.clickConsultarCredin();
            consultaCredinSteps.clickGenerarCredin();
            generarCredinSteps.clickCuentaDebito();
            generarCredinSteps.keyDownXParametrosGenerico(0, testCaseData.cuentaOrigen);
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
    @DisplayName("TC-128249-BERSA: Web - VU Fraud - Verificar que al ejecutarse el activity transfers.account.scheduleMakeWithCaps se envíe el metodoValidacionFP con la marca de validación del dispositivo")
    public void TC128249BERSA() {
        try {
            testCaseData.cargaDeDatosTests("TC128249BERSA");
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
    @DisplayName("TC-29408-BERSA: Web-Tranferencias Programadas-Nuevo historial-Visualización ")
    public void TC29408BERSA() {
        try {
            testCaseData.cargaDeDatosTests("TC29408BERSA");
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

    /**
     * Casos de prueba de la sección Tarjetas.
     *
     */
    @Test
    @DisplayName("TC-0032-BERSA: Consulta de Tarjetas de Crédito - Visa")
    public void TC0032BERSA() {
        try {
            testCaseData.cargaDeDatosTests("TC0032BERSA");
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
    @DisplayName("TC-0033-BERSA: Consulta de Tarjetas de Crédito - Mastercard")
    public void TC0033BERSA() {
        try {
            //PRECONDICION
            testCaseData.cargaDeDatosTests("TC0033BERSA");
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
    @DisplayName("TC-0062-BERSA: Detalle de tarjeta de crédito - Mostrar datos tarjeta y movimientos del período")
    public void TC0062BERSA() {
        try {
            testCaseData.cargaDeDatosTests("TC0062BERSA");
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
    @DisplayName("TC-0104-BERSA: Tarjeta de Crédito - Descarga de resumen")
    public void TC0104BERSA() {
        try {
            testCaseData.cargaDeDatosTests("TC0104BERSA");
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
    @DisplayName("TC-0163-BERSA: Tarjeta de Crédito - Pagar el total de una Tarjeta de Crédito Mastercard - Validación de pantalla")
    public void TC0163BERSA() {
        try {
            testCaseData.cargaDeDatosTests("TC0163BERSA");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.TARJETA_CREDITO);
            segurosSteps.validarTituloPageGenerico("Tarjetas de crédito");
            tarjetasDeCreditoSteps.clickVerMasXnroDeTarjeta(testCaseData.tarjetaCredito);
            tarjetasDeCreditoSteps.clickMasOpcionesGenerico();
            tarjetasDeCreditoSteps.clickPagarTarjeta();
            tarjetasDeCreditoSteps.validarTituloPageGenerico("Pago de tarjeta propia");;
            tarjetasDeCreditoSteps.seleccionarTarjetaDebito(testCaseData.cuentaOrigen);
            tarjetasDeCreditoSteps.clickContinuarGenerico();
            tarjetasDeCreditoSteps.seleccionarCuentaDebitoSiEstaPresente(testCaseData.cuentaOrigen);
            tarjetasDeCreditoSteps.seleccionarTipoDePago( "Pagar el total");
            tarjetasDeCreditoSteps.clickBotonPagar();
            tarjetasDeCreditoSteps.validacionDeCamposPagoDeTarjeta();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    //@Test - Revision de Causiticas
    @DisplayName("TC-0191-BERSA: Tarjetas de credito - Solicitud de tarjeta de credito- Al no contar con oferta de TC se debe validar mensaje 'No contás con oferta de Tarjeta de Crédito en este momento. '")
    public void TC0191BERSA() {
        try {
            testCaseData.cargaDeDatosTests("TC0191BERSA");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.TARJETA_CREDITO);
            segurosSteps.validarTituloPageGenerico("Tarjetas de crédito");
            tarjetasDeCreditoSteps.clickSolicitarTarjetaNueva();
            tarjetasDeCreditoSteps.validarMensajeNoContasConOfertas();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    //@Test sE Elimina el caso
    @DisplayName("TC-0192-BERSA: Tarjetas de credito - Solicitud de tarjeta de credito- Al solicitar una nueva TC y clickear el checkbox Persona expuesta políticamente no se debe permitir continuar con la solicitud-  validar pantalla mensaje")
    public void TC0192BERSA() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uSantafe036);
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.validarTituloPageGenerico("Tarjetas de cr");
            tarjetasDeCreditoSteps.clickSolicitarTarjetaNueva();
            tarjetasDeCreditoSteps.validarTituloPageGenerico("Solicitud de tarjeta de cr");
            tarjetasDeCreditoSteps.seleccionarCuentaDebitoSiEstaPresente("5304");
            tarjetasDeCreditoSteps.clickCheckboxPersonaExpuesta();
            tarjetasDeCreditoSteps.clickContinuarGenerico();
            tarjetasDeCreditoSteps.validarMensajeSolicitaTurnoEnSucursal();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    //@Test SE Elimina el caso
    @DisplayName("TC-0193-BERSA: Tarjetas de credito - Solicitud de tarjeta de credito- Al solicitar una nueva TC y clickear el checkbox 'residencia fiscar en el exterior'  no se debe permitir continuar con la solicitud-  validar pantalla mensaje")
    public void TC0193BERSA() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uSantafe036);
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.validarTituloPageGenerico("Tarjetas de cr");
            tarjetasDeCreditoSteps.clickSolicitarTarjetaNueva();
            tarjetasDeCreditoSteps.validarTituloPageGenerico("Solicitud de tarjeta de cr");
            tarjetasDeCreditoSteps.seleccionarCuentaDebitoSiEstaPresente("5304");
            tarjetasDeCreditoSteps.clickCheckboxResidenciaFiscalExterior();
            tarjetasDeCreditoSteps.clickContinuarGenerico();
            tarjetasDeCreditoSteps.validarMensajeSolicitaTurnoEnSucursal();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    //@Test SE ELIMINA EL CASO
    @DisplayName("TC-0194-BERSA: Tarjetas de credito - Solicitud de tarjeta de credito- Al solicitar una nueva TC y clickear el checkbox \"sujeto obligado iuf\"  no se debe permitir continuar con la solicitud-  validar pantalla mensaje")
    public void TC0194BERSA() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uSantafe036);
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.validarTituloPageGenerico("Tarjetas de cr");
            tarjetasDeCreditoSteps.clickSolicitarTarjetaNueva();
            tarjetasDeCreditoSteps.validarTituloPageGenerico("Solicitud de tarjeta de cr");
            tarjetasDeCreditoSteps.seleccionarCuentaDebitoSiEstaPresente("5304");
            tarjetasDeCreditoSteps.clickCheckboxSujetoObligadoIUF();
            tarjetasDeCreditoSteps.clickContinuarGenerico();
            tarjetasDeCreditoSteps.validarMensajeSolicitaTurnoEnSucursal();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-0153-BERSA: Tarjeta de Crédito - Segumiento de Tarjetas de Crédito - WEB.")
    public void TC0153BERSA() {
        try {
            testCaseData.cargaDeDatosTests("TC0153BERSA");
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
    @DisplayName("TC-0155-BERSA: Tarjeta de Crédito - Seguimiento de Tarjetas de Crédito - Detalles - WEB.")
    public void TC0155BERSA() {
        try {
            testCaseData.cargaDeDatosTests("TC0155BERSA");
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
    @DisplayName("TC-0157-BERSA: Tarjeta de Crédito - Seguimiento de Tarjetas de Crédito - Descargar Detalles - WEB.")
    public void TC0157BERSA() {
        try {
            testCaseData.cargaDeDatosTests("TC0157BERSA");
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
    @DisplayName("TC-0158-BERSA: Tarjeta de Crédito - Seguimiento de Tarjetas de Crédito - Imprimir Detalles - WEB")
    public void TC0158BERSA() {
        try {
            testCaseData.cargaDeDatosTests("TC0158BERSA");
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
    @DisplayName("TC-0195-BERSA: Tarjetas de credito - Solicitud de tarjeta de credito- Al solicitar una nueva TC y verificar mensaje de solicitar turno, clickear en  'Solicitá tu turno acá.' y validar redireccion de pantalla")
    public void TC0195BERSA() {
        try {
            testCaseData.cargaDeDatosTests("TC0195BERSA");
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
    @DisplayName("TC-0162-BERSA: Tarjeta de Crédito - Denunciar robo o extravío de Tarjeta de Crédito - WEB.")
    public void TC0162BERSA() {
        try {
            testCaseData.cargaDeDatosTests("TC0162BERSA");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.TARJETA_CREDITO);
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
    @DisplayName("TC-0167-BERSA: Tarjeta de Crédito - Consultar último resumen de una Tarjeta de Crédito - WEB.")
    public void TC0167BERSA() {
        try {
            testCaseData.cargaDeDatosTests("TC0167BERSA");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password,Settings.TARJETA_CREDITO);
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

    @Test
    @DisplayName("TC-128242-BERSA: Web - Detalle de TC - Validar el reordenamiento de la información en Detalle de TC VISA y MASTER")
    public void TC128242BERSA() {
        try {
            testCaseData.cargaDeDatosTests("TC128242BERSA");
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
    @DisplayName("TC-0052-BERSA: Turnero online_Aceptar redireccionamiento la página de solicitud de turnos online fuera del HB.")
    public void TC0052BERSA() {
        try {
            testCaseData.cargaDeDatosTests("TC0052BERSA");
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

    //@Test
    @DisplayName("TC-107431-BERSA: Web-Detalle de los pagos realizados en simultaneo-" +
            "Pantalla Estado de tus pagos")
    public void TC107431BERSA() {
        try {
            testCaseData.cargaDeDatosTests("TC107431BERSA");
            loginSteps.iniciarSessionFront(testCaseData.usuario, testCaseData.password,Settings.PAGOS);
            botonesGenericosSteps.validarTituloPageGenerico("Pago de servicios");
            nuevoPagoSteps.validarBotonMostrarTodos();
            nuevoPagoSteps.validarServiciosUnisys("MLPAYMENT");
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

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TR-110363-BERSA: WEB - Comprobante Pagos de servicio - Validar comportamiento filtros de busqueda")
    public void TC110363BERSA() throws AWTException {
        try {
            testCaseData.cargaDeDatosTests("TC110363BERSA");
            loginSteps.iniciarSessionFront(testCaseData.usuario, testCaseData.password,Settings.PAGOS);
            botonesGenericosSteps.validarTituloPageGenerico("Pago de servicios");
            nuevoPagoSteps.validarBotonMostrarTodos();
            nuevoPagoSteps.validarServiciosUnisys("MLPAYMENT");
            botonesGenericosSteps.clickVolverFlechaGenerico();
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
    @DisplayName("TC-0183-BERSA: DEBIN Vendedor - Generar orden de DEBIN - Validar campo requerido al no ingresar un CBU y seleccionar buscar.")
    public void TC0183BERSA() {
        try {
            testCaseData.cargaDeDatosTests("TC0183BERSA");
            loginSteps.iniciarSessionFront(testCaseData.usuario, testCaseData.password,Settings.PAGOS);
            botonesGenericosSteps.validarTituloPageGenerico("Pago de servicios");
            UtilsGP.fixedWait(5000);
            pagosSteps.clickMasOpcionesGenerico();
            pagosSteps.clickDebinVendedor();
            debinVendedorSteps.clickOrdenDebin();
            debinVendedorSteps.clickCuentaDestinoDesplegable();
            debinVendedorSteps.seleccionarDropdownGenerico(testCaseData.cuentaOrigen);
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

    @Test
    @DisplayName("TR-110846-BERSA: Web-Detalle de los pagos realizados en simultaneo-" +
            "Pantalla Estado de tus pagos")
    public void TR110846BERSA() throws AWTException {
        try {
            testCaseData.cargaDeDatosTests("TR110846BERSA");
            loginSteps.iniciarSessionFront(testCaseData.usuario, testCaseData.password,Settings.PAGOS);
            botonesGenericosSteps.validarTituloPageGenerico("Pago de servicios");
            nuevoPagoSteps.validarBotonMostrarTodos();
            nuevoPagoSteps.validarServiciosUnisys("MLPAYMENT");
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
    @DisplayName("TC-0184-BERSA: DEBIN Vendedor - Generar orden de DEBIN - Validar campo requerido al no ingresar 'Alias' y seleccionar buscar.")
    public void TC0184BERSA() {
        try {
            testCaseData.cargaDeDatosTests("TC0184BERSA");
            loginSteps.iniciarSessionFront(testCaseData.usuario, testCaseData.password,Settings.PAGOS);
            botonesGenericosSteps.validarTituloPageGenerico("Pago de servicios");
            UtilsGP.fixedWait(5000);
            pagosSteps.clickMasOpcionesGenerico();
            pagosSteps.clickDebinVendedor();
            debinVendedorSteps.clickOrdenDebin();
            debinVendedorSteps.clickCuentaDestinoDesplegable();
            debinVendedorSteps.seleccionarDropdownGenerico(testCaseData.cuentaOrigen);
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

    @Test
    @DisplayName("TC-110847-BERSA: Web-Detalle de los pagos realizados en simultaneo-" +
            "Pantalla Estado de tus pagos")
    public void TC110847BERSA() {
        try {
            testCaseData.cargaDeDatosTests("TC110847BERSA");
            loginSteps.iniciarSessionFront(testCaseData.usuario, testCaseData.password,Settings.PAGOS);
            botonesGenericosSteps.validarTituloPageGenerico("Pago de servicios");
            nuevoPagoSteps.validarBotonMostrarTodos();
            nuevoPagoSteps.validarServiciosUnisys("MLPAYMENT");
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
            botonesGenericosSteps.clickVolverGenerico();
            botonesGenericosSteps.validarTituloPageGenerico("Pago de servicios");

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
    @DisplayName("TC-124865-BERSA: Web-Validar que la Aceptacion de un Echeq en estado 'Emitido pendiente' con la variable Encendida, " +
            "informe el Nuevo servicio de Aceptacion de Echeq")
    public void TC124865BERSA() {
        try {
            //PRE-CONDICION
            testCaseData.cargaDeDatosTests("TCECHEQBERSA");
            echeqAPI.generacionDeEcheqAPI(testCaseData.usuario, testCaseData.password, testCaseData.ambiente,
                    testCaseData.cuitDestino, testCaseData.cuentaOrigen, "0",
                    Settings.aLaOrden);
            //TEST
            testCaseData.cargaDeDatosTests("TC124865BERSA");
            loginSteps.iniciarSessionFront(testCaseData.usuario, testCaseData.password,Settings.CUENTA);
            botonesGenericosSteps.validarTituloPageGenerico("Cuentas");
            cuentasSteps.clickMasOpcionesGenerico();
            cuentasSteps.clickEcheq();
            echeqSteps.validarTituloPageGenerico("Consulta de ECheq");
            echeqSteps.setNroEcheq(Settings.nroDeEcheq);
            botonesGenericosSteps.clickListaDesplegableGenerico("Todos");
            botonesGenericosSteps.seleccionarDropdownGenerico("Emitido pendiente");
            echeqSteps.clickBotonBuscar();
            echeqSteps.clickVerDetallePrimerEcheq();
            echeqSteps.validarTituloPageGenerico("Detalle de ECHEQ");
            echeqSteps.validarTituloPageGenerico("Detalle de ECHEQ");
            echeqSteps.validarTituloPageGenerico("Id Echeq");
            echeqSteps.validarTituloPageGenerico("Nro Echeq");
            echeqSteps.validarTituloPageGenerico("Estado");
            botonesGenericosSteps.clickAceptarGenerico();
            botonesGenericosSteps.validarTituloPageGenerico("Aceptar ECheqs");
            echeqSteps.validarDatoEhceqGrilla(Settings.nroDeEcheq);
            echeqSteps.validarDatoEhceqGrilla("EMITIDO-PENDIENTE");
            botonesGenericosSteps.clickContinuarGenerico();
            dobleFactorSteps.codigoSegundoFactor();
            botonesGenericosSteps.validarTituloPageGenerico("Aceptar ECheqs");
            echeqSteps.validarDatoEhceqGrilla(Settings.nroDeEcheq);
            echeqSteps.validarDatoEhceqGrilla("EMITIDO-PENDIENTE");
        }
        catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-29413-BERSA: Web-Validar que la Aceptacion de un Echeq en estado 'Emitido pendiente' con la variable Encendida, " +
            "informe el Nuevo servicio de Aceptacion de Echeq")
    public void TC29413BERSA() {
        try {
            //TEST
            testCaseData.cargaDeDatosTests("TC29413BERSA");
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
    @DisplayName("TC-29414-BERSA: WEB-Búsqueda combinada de filtros (funcionalidad Buscar)")
    public void TC29414BERSA() {
        try {
            //TEST
            testCaseData.cargaDeDatosTests("TC29414BERSA");
            loginSteps.iniciarSessionFront(testCaseData.usuario, testCaseData.password,Settings.CUENTA);
            botonesGenericosSteps.validarTituloPageGenerico("Cuentas");
            cuentasSteps.clickCancelarGenerico();
            cuentasSteps.clickMasOpcionesGenerico();
            cuentasSteps.clickEcheq();
            echeqSteps.validarTituloPageGenerico("ECheq");
            transferenciasInmediatas.clickbtnFiltrar();
            echeqSteps.setCuitCuilEmisor("20063078175");
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
            echeqSteps.validarDatoBusquedaEhceqGrilla6meses("20063078175", "Emitido-Pendiente");
        }
        catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

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
