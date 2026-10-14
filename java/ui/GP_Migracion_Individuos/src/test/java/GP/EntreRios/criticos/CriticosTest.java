package GP.EntreRios.criticos;

import db.ConexionDB;
import io.qameta.allure.Allure;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import pages.botonesgenericos.BotonesGenericosPage;
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
import steps.inversiones.plazosfijos.PlazosFijosSteps;
import steps.inversiones.plazosfijos.SolicitarPlazoFijoSteps;
import steps.login.LoginSteps;
import steps.mensaje.MensajeSteps;
import steps.menulateral.MenuLateralSteps;
import steps.menusuperior.MenuSuperiorSteps;
import steps.pagos.PagosSteps;
import steps.pagos.adherirservicio.AdherirServicioSteps;
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
import utils.Constantes;
import utils.TestCaseData;
import utils.UtilsGP;

import java.io.IOException;
import java.sql.SQLException;

import static db.ConexionDB.cerrarConexion;
import static utils.Constantes.*;
import static utils.UtilsGP.fixedWait;
import static utils.UtilsGP.generarString;

public class CriticosTest {
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
    UtilsGP utilsGP = new UtilsGP();
    TestCaseData testCaseData = new TestCaseData();
    private static String CLARO_ID = "600";
    private static String SUBE_ID = "618";
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
        global.practia.jemeinsamstark.web.core.WebDriver.getInstance().manage().window().setSize(new Dimension(1920,1080));
    }

    //CASOS DE ADELANTOS DE SUELDO
    @Test  //VERIFICAR USUARIO
    @DisplayName("TC-0223-BERSA: Adelantos de Sueldo - Realizar solicitud de adelanto de sueldo  - Validar pantalla de confimacion de solicitud")
    public void TC0223BERSA() {
        try {
            testCaseData.cargaDeDatosTest(uMati2024);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickCuentas();
            adelantosDeSueldoSteps.clickVerMasXnroCuenta("CC $ 010004651580");
            adelantosDeSueldoSteps.clickMasOpcionesGenerico();
            adelantosDeSueldoSteps.clickSolicitudAdelantoDeSueldo();
            adelantosDeSueldoSteps.setCuantoNecesitas("10000");
            adelantosDeSueldoSteps.clickContinuarGenerico();
            adelantosDeSueldoSteps.validacionCamposConfirmacion();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-12978-BERSA: OFERTA - Adelanto de Sueldo - Acceso Directo")
    public void TC12978BERSA() {
        try {
            testCaseData.cargaDeDatosTest(uMati2024);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickInicio();
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
    //CASOS CUENTAS ASOCIADAS
    //@Test //falta terminar test por error en el banco - PROBADO EN HOMO
    @DisplayName("TC-0287-BERSA: Consentimiento Billetera - Aprobar consentimiento")
    public void TC0287BERSA() {
        try {
            testCaseData.cargaDeDatosTest(uTranspull4);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickCuentas();
            cuentasSteps.clickMasOpcionesGenerico();
            cuentasSteps.clickCuentasAsociadas();
            cuentasAsociadasSteps.validarTituloPageGenerico("Cuentas asociadas");
            cuentasAsociadasSteps.clickVincularUnaCuenta();
            cuentasAsociadasSteps.clickCheckTerminosCondicionesGenerico();
            dobleFactorSteps.codigoSegundoFactor();
            cuentasAsociadasSteps.clickAceptarGenerico();
            utilsGP.ManageTabPorParametro(2);
            cuentasAsociadasSteps.botonContinuarPagExterna();
            cuentasAsociadasSteps.seleccionarBanco("Banco de San Juan");
            cuentasAsociadasSteps.botonContinuarPagExterna();
            cuentasAsociadasSteps.setUsuario("Transpull4");
            cuentasAsociadasSteps.setContrasenia("Banco045");
            cuentasAsociadasSteps.setTarjetaDebito("2005");
            cuentasAsociadasSteps.clickBotonIniciarSesion();
            cuentasAsociadasSteps.seleccionarPrimerCuentaChekbox();
            cuentasAsociadasSteps.checkboxTerminosYCondiciones();
            cuentasAsociadasSteps.botonContinuarPagExterna();
            utilsGP.paginaActual();
            cuentasAsociadasSteps.clickDesplegableEntidadesAsociadas();
            cuentasAsociadasSteps.validarEntidadAsociada("NUEVO BANCO DE SAN JUAN SOCIEDAD ANONIMA");
            cuentasAsociadasSteps.abrirPestaniaBanco("https://hbtest01.bancosantafe.com/startPage");
            loginSteps.iniciarSesion(uTranspull4);
            menuLateralSteps.clickCuentas();
            cuentasSteps.clickMasOpcionesGenerico();
            cuentasSteps.clickCuentasAsociadas();
            cuentasAsociadasSteps.desplegableCuentasAsociadasEnOtrasEntidades();
            cuentasAsociadasSteps.validarEntidadAsociada("Banco Entre Rios");
            utilsGP.backMainTap();
            //TEARDOWN
            menuLateralSteps.clickCuentas();
            cuentasSteps.clickMasOpcionesGenerico();
            cuentasSteps.clickCuentasAsociadas();
            cuentasAsociadasSteps.clickDesplegableEntidadesAsociadas();
            cuentasAsociadasSteps.validarEntidadAsociada("NUEVO BANCO DE SAN JUAN SOCIEDAD ANONIMA");
            cuentasAsociadasSteps.desasociarEntidadAsociada("NUEVO BANCO DE SAN JUAN SOCIEDAD ANONIMA");
            dobleFactorSteps.codigoSegundoFactor();
            cuentasAsociadasSteps.validarMensajeCuentaDesasociada();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test //CELULA CUENTAS //USUARIO OK
    @DisplayName("TC-0006-BERSA: Adherir Cuenta  - Nuevo destinatario - por Alias - Descarga e impresión del PDF")
    public void TC0006BERSA()  {
        try {
            //PRECONDICION
            testCaseData.cargaDeDatosTest(uBerhometest15);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("CAMPO-MONTE");
            //TEST
            menuLateralSteps.clickCuentas();
            cuentasSteps.clickMasOpcionesGenerico();
            cuentasSteps.clickAgendaDeDestinararios();
            agendaNuevoDestinatarioSteps.clickAdherirNuevoDestinatario();
            agendaNuevoDestinatarioSteps.setInputCbuAlias("CAMPO-MONTE");
            agendaNuevoDestinatarioSteps.clickBuscar();
            agendaNuevoDestinatarioSteps.validarDatosCuenta("3300000610000031001084", "RUATTA, REMIGIO RAMON", "20062783584");
            agendaNuevoDestinatarioSteps.setPerteneceA("PruebaAutomation");
            transferenciasInmediatas.botonSonCorrectos();
            dobleFactorSteps.codigoSegundoFactorOtroMetodo();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            ticketSteps.clickDescargas();
            ticketSteps.clickImprimir();
            ticketSteps.validarDescarga("Ticket.pdf");
            agendaNuevoDestinatarioSteps.clickVolverGenerico();
            //POSTCONDICION
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("3300000620000125788076");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test //TRANSPULL1 BERSA NO TIENE CUENTA - VERIFICAR USUARIO
    @DisplayName("TC-0027-BERSA: Generar Orden de Extracción_Con Saldo disponible en Cuenta.")
    public void TC0027BERSA() {
        try {
            testCaseData.cargaDeDatosTest(uEcarina27);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickCuentas();
            cuentasSteps.clickOrdenesDeExtraccion();
            ordenDeExtraccionConsulta_generarSteps.clickGenerarOrdenDeExtraccion();
            ordenDeExtraccionConsulta_generarSteps.validarTituloPageGenerico("Generar orden de extracc");
            ordenDeExtraccionConsulta_generarSteps.clickCuentaOrigen();
            ordenDeExtraccionConsulta_generarSteps.seleccionarDropdownGenerico("Caja de Ahorro $ 020000052210");
            ordenDeExtraccionConsulta_generarSteps.setDni("11912026");
            ordenDeExtraccionConsulta_generarSteps.setImporteGenerico("100");
            ordenDeExtraccionConsulta_generarSteps.setReferencia("T27");
            ordenDeExtraccionConsulta_generarSteps.clickBotonAgregar();
            ordenDeExtraccionConsulta_generarSteps.clickContinuarGenerico();
            dobleFactorSteps.codigoSegundoFactor();
            ordenDeExtraccionConsulta_generarSteps.validarTituloPageGenerico("Destinatario");
            ordenDeExtraccionConsulta_generarSteps.validarTituloPageGenerico("DNI 11912026");
            ordenDeExtraccionConsulta_generarSteps.clickIconoComprobante();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            ticketSteps.clickVolverGenerico();
            ordenDeExtraccionConsulta_generarSteps.validarTituloPageGenerico("Destinatario");
            ordenDeExtraccionConsulta_generarSteps.validarTituloPageGenerico("DNI 11912026");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0040-BERSA: Acceder a Consulta de movimientos de cuenta")
    public void TC0040BERSA() {
        try {
            testCaseData.cargaDeDatosTest(uEcarina27);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickCuentas();
            cuentasSteps.clickVerMasXnroCuenta("CC $ 001003691907");
            detalleCuentaSteps.clickDetalleMovimientoPorNroFila("1");
            detalleCuentaSteps.validarTituloPageGenerico("Fecha");
            detalleCuentaSteps.validarTituloPageGenerico("de Comprobante");
            detalleCuentaSteps.validarTituloPageGenerico("Concepto");
            detalleCuentaSteps.validarTituloPageGenerico("Monto");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    //CASOS DE LA CLASE ECHEQ
    @Test
    @DisplayName("TC-0025-BERSA: Echeq - Completitud de pantalla con filtro: Estado = Todos")
    public void TC0025BERSA() {
        try {
            testCaseData.cargaDeDatosTest(uEcarina27);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickCuentas();
            cuentasSteps.clickMasOpcionesGenerico();
            cuentasSteps.clickEcheq();
            echeqSteps.validarTituloPageGenerico("Echeq");
            echeqSteps.keyDownXParametrosGenerico(13, "Todos");
            echeqSteps.clickBotonBuscar();
            echeqSteps.validarCompletitudPantalla();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    //CASOS DE LA CLASE CUOTIFICALO
    //@Test //ESTE CASO QUEMA EL DATO - DE MOMENTO APAGADO - REFACTORIZRA CON USUARIO QUILICIE1 - UPS
    @DisplayName("TC-0089-BERSA: Cuotifícalo - Cuotificar compras con TD")
    public void TC0089BERSA() {
        try {
            testCaseData.cargaDeDatosTest(uPerezR);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickCuentas();
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
    //CASO DE LA CLASE COMPRA Y VENTA DE DOLARES
    @Test //PRECALIFICACION EN BERSA - SE APAGA PORQUE DURA 24 la precalificacion desde it - SE CORRE EN UNISYS
    @DisplayName("TC-0009-BERSA: CPA / VTA de Dolares  - Cliente Vende 10 Dólares y Acredita en CA en Pesos")
    public void TC0009BERSA() {
        try {
            testCaseData.cargaDeDatosTest(uBerhometest20);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickCompraVentaDeDolares();
            compraVentaDeDolaresSteps.validarTituloPageGenerico("Compra venta de d");
            compraVentaDeDolaresSteps.clickTipoDeOperacion();
            compraVentaDeDolaresSteps.keyDownToText("Vender");
            compraVentaDeDolaresSteps.clickCuentaOrigen();
            compraVentaDeDolaresSteps.keyDownToText( "USD");
            compraVentaDeDolaresSteps.clickCuentaDestino();
            compraVentaDeDolaresSteps.keyDownToText("4026");
            compraVentaDeDolaresSteps.setMonto("50");
            compraVentaDeDolaresSteps.clickContinuarGenerico();
            compraVentaDeDolaresSteps.clickCheckTerminosCondicionesGenerico();
            compraVentaDeDolaresSteps.clickConfirmarGenerico();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0069-BERSA: Compra de moneda extranjera (USD)")
    public void TC0069BERSA() {
        try {
            testCaseData.cargaDeDatosTest(uBerhometest20);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickCompraVentaDeDolares();
            compraVentaDeDolaresSteps.validarTituloPageGenerico("Compra venta de d");
            compraVentaDeDolaresSteps.clickTipoDeOperacion();
            compraVentaDeDolaresSteps.keyDownToText( "Comprar");
            compraVentaDeDolaresSteps.clickCuentaOrigen();
            compraVentaDeDolaresSteps.keyDownToText( "4026");
            compraVentaDeDolaresSteps.clickCuentaDestino();
            compraVentaDeDolaresSteps.keyDownToText("USD");
            compraVentaDeDolaresSteps.setMonto("1");
            compraVentaDeDolaresSteps.clickContinuarGenerico();
            compraVentaDeDolaresSteps.clickCheckTerminosCondicionesGenerico();
            compraVentaDeDolaresSteps.clickConfirmarGenerico();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    //CASOS DE LA CUENTA COMITENTE
    @Test
    @DisplayName("TC- 0173- BERSA-  Inversiones- Alta cuenta comitente- Solicitar cuenta comitente  - Validar pantalla etapa '5 Confirmacion'")
    public void TC0173BERSA() {
        try {
            testCaseData.cargaDeDatosTest(uUattesthb13);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickSolicitarNuevaCuentaComitente();
            cuentaComitenteSteps.clickSolicitarCuenta();
            cuentaComitenteSteps.validarTituloPageGenerico("Apertura de cuenta comitente");
            cuentaComitenteSteps.clickContinuarGenerico();
            cuentaComitenteSteps.clickCheckboxNingunaDeLasAnteriores();
            cuentaComitenteSteps.clickContinuarGenerico();
            cuentaComitenteSteps.clickCheckboxTerminosyCondiciones();
            cuentaComitenteSteps.clickContinuarGenerico();
            cuentaComitenteSteps.clickConfirmarGenerico();
            cuentaComitenteSteps.validarNumyTextDeEtapa("5", "Confirmación");
            cuentaComitenteSteps.validarTituloPageGenerico("Apertura de cuenta comitente");
            cuentaComitenteSteps.validarTexto("Estás por confirmar la solicitud de Alta de Cuenta Comitente");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    //CASO DE LA CLASE FONDO COMUN DE INVERSION
    //@Test //SE APAGA CASO YA QUE LAS CUENTAS COMITENTES FUERON BORRADAS - INCIDENTE ARPETEREVO-10456
    @DisplayName("TC-0111-BERSA: Suscribir FCI - Validación de pantalla de suscripción de FCI")
    public void TC0111BERSA() {
        try {
            testCaseData.cargaDeDatosTest(uBerhometest15);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            fondoComunInversionSteps.clickIconoAccionesDetalleFCI("A-Delta Pesos");
            fondoComunInversionSteps.clickSuscribir();
            suscripcionFCISteps.validarTituloPageGenerico("El monto debe ser mayor o igual a");
            suscripcionFCISteps.setImporteSuscribirFci("2000");
            suscripcionFCISteps.clickContinuarGenerico();
            suscripcionFCISteps.clickCheckbox();
            suscripcionFCISteps.validarTituloPageGenerico("Confirmar la suscripción de FCI");
            suscripcionFCISteps.validacionPantallaConfirmacionFCI();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    //@Test //SE APAGA CASO YA QUE LAS CUENTAS COMITENTES FUERON BORRADAS - INCIDENTE ARPETEREVO-10456
    @DisplayName("TC-0113-BERSA: FCI - Rescatar - Importe Parcial - Validación de pantalla")
    public void TC0113BERSA() {
        try {
            testCaseData.cargaDeDatosTest(uBerhometest15);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            fondoComunInversionSteps.clickIconoAccionesDetalleFCI("A-Delta Pesos");
            fondoComunInversionSteps.clickRescate();
            rescateFCISteps.validarTituloPageGenerico("Rescate de FCI");
            rescateFCISteps.clickParcial();
            rescateFCISteps.setImporteRescatar("300");
            rescateFCISteps.clickContinuarGenerico();
            rescateFCISteps.clickCheckTerminosCondicionesGenerico();
            rescateFCISteps.validarTituloPageGenerico("Confirmar rescate de FCI");
            rescateFCISteps.validacionPantallaConfirmacionRescate();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    //CASOS DE LA CLASE PLAZO FIJO
    @Test
    @DisplayName("TC-0042-BERSA: Alta de PF Tradicional en $ a 30 días")
    public void TC0042BERSA() {
        try {
            testCaseData.cargaDeDatosTest(uBerhometest20);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickPlazosFijos();
            plazosFijosSteps.clickSolicitarPlazoFijo();
            solicitarPlazoFijoSteps.clickSelectorTipoPF();
            solicitarPlazoFijoSteps.keyDownToText("Plazo Fijo Tradicional en Pesos");
            solicitarPlazoFijoSteps.setImporteGenerico("120000");
            solicitarPlazoFijoSteps.setPlazoEnDias("30");
            solicitarPlazoFijoSteps.clickSelectorCuentaDebito();
            solicitarPlazoFijoSteps.keyDownXParametrosGenerico(0, "4026");
            solicitarPlazoFijoSteps.clickSelectorAccionVencimiento();
            solicitarPlazoFijoSteps.keyDownToText("al vencimiento");
            solicitarPlazoFijoSteps.clickContinuarGenerico();
            solicitarPlazoFijoSteps.clickCheckTerminosCondicionesGenerico();
            solicitarPlazoFijoSteps.clickConfirmarGenerico();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    //@Test // (14-02-24) SE APAGA POR BUG RELACIONADOS 9240 - 8339
    @DisplayName("TC-0043-BERSA: Alta de PF UVA $ 350 días ")
    public void TC0043BERSA() {
        try {
            testCaseData.cargaDeDatosTest(uBerhometest15);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickPlazosFijos();
            plazosFijosSteps.clickSolicitarPlazoFijo();
            solicitarPlazoFijoSteps.clickSelectorTipoPF("Plazo Fijo UVA");
            solicitarPlazoFijoSteps.setImporteGenerico("15000");
            solicitarPlazoFijoSteps.setPlazoEnDias("350");
            solicitarPlazoFijoSteps.clickSelectorCuentaDebito();
            solicitarPlazoFijoSteps.keyDownXParametrosGenerico(0, "");
            solicitarPlazoFijoSteps.clickSelectorAccionVencimiento();
            solicitarPlazoFijoSteps.seleccionarDropdownGenerico("al vencimiento");
            solicitarPlazoFijoSteps.clickContinuarGenerico();
            solicitarPlazoFijoSteps.clickCheckTerminosCondicionesGenerico();
            solicitarPlazoFijoSteps.clickConfirmarGenerico();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test // (14-02-24) SE APAGA POR BUG RELACIONADOS 9240 - 8339
    @DisplayName("TC-12974-BERSA INVERSIONES - Plazo Fijo UVA Precancelable")
    public void TC12974BERSA() {
        try {
            testCaseData.cargaDeDatosTest(uBerhometest15);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickPlazosFijos();
            plazosFijosSteps.clickSolicitarPlazoFijo();
            solicitarPlazoFijoSteps.clickSelectorTipoPF();
            solicitarPlazoFijoSteps.seleccionarDropdownGenerico("Plazo Fijo UVA Precancelable");
            solicitarPlazoFijoSteps.setImporteGenerico("15000");
            solicitarPlazoFijoSteps.setPlazoEnDias("350");
            solicitarPlazoFijoSteps.clickSelectorCuentaDebito();
            solicitarPlazoFijoSteps.keyDownXParametrosGenerico(0, "");
            solicitarPlazoFijoSteps.clickSelectorAccionVencimiento();
            solicitarPlazoFijoSteps.seleccionarDropdownGenerico( "al vencimiento");
            solicitarPlazoFijoSteps.clickContinuarGenerico();
            solicitarPlazoFijoSteps.validarSiExisteMensajeEmergente();
            solicitarPlazoFijoSteps.clickCheckTerminosCondicionesGenerico();
            solicitarPlazoFijoSteps.clickConfirmarGenerico();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-12973-BERSA: INVERSIONES - Plazo Fijo Tradicional en Dolares")
    public void TC12973BERSA() {
        try {
            testCaseData.cargaDeDatosTest(uEcarina27);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickPlazosFijos();
            plazosFijosSteps.clickSolicitarPlazoFijo();
            solicitarPlazoFijoSteps.clickSelectorTipoPF();
            solicitarPlazoFijoSteps.keyDownToText("Plazo Fijo Tradicional en D");
            solicitarPlazoFijoSteps.setImporteGenerico("1000");
            solicitarPlazoFijoSteps.setPlazoEnDias("30");
            solicitarPlazoFijoSteps.clickSelectorCuentaDebito();
            solicitarPlazoFijoSteps.keyDownToText("5432");
            solicitarPlazoFijoSteps.clickSelectorAccionVencimiento();
            solicitarPlazoFijoSteps.keyDownToText( "Renovación automática");
            solicitarPlazoFijoSteps.clickContinuarGenerico();
            solicitarPlazoFijoSteps.clickCheckTerminosCondicionesGenerico();
            solicitarPlazoFijoSteps.clickConfirmarGenerico();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    //CASOS DE LA CLASE LOGIN
    @Test
    @DisplayName("TC-0048-BERSA: Recupero de Usuario - Recuperar Usuario")
    public void TC0048BERSA(){
        try {
            String randomStr = RandomStringUtils.randomAlphabetic(4);
            String user = uUattesthb14;
            String pass = UtilsGP.consultaDB("usuarios", user, "password");
            //TEST
            loginSteps.waitGP();
            loginSteps.clickIngresarStartPage();
            loginSteps.setUsuario(user);
            loginSteps.clickRecuperarUsuario();
            //loginSteps.setNroDocumento("17597399");
            loginSteps.clickContinuarGenerico();
            loginSteps.validarTituloPageGenerico("Tu solicitud");
            loginSteps.validarTituloPageGenerico("Enviamos tu usuario a");
            loginSteps.loginForAPI(user);
            loginSteps.validarMailRecibido(dobleFactorSteps.codigoVerificacionEmailRecuperoUsuario(),user);
            loginSteps.clickBotonIniciarSesion();
            loginSteps.clickIngresarLogin();
            loginSteps.setUsuario(user);
            loginSteps.clickContinuarLogin();
            loginSteps.setPassword(pass);
            loginSteps.clickIngresarLogin();
            loginSteps.verificarSaludo();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    //CASOS DE LA CLASE PAGOS
    //@Test API
    @DisplayName("TC-0012-BERSA: DEBIN Comprador - Consulta DEBINes históricos - Listado")
    public void TC0012BERSA() {
        try {
            testCaseData.cargaDeDatosTest(uEcarina27);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickPagos();
            pagosSteps.clickMasOpcionesGenerico();
            pagosSteps.clickDebinComprador();
            debinCompradorSteps.clickSelectorConsultarDebin();
            debinCompradorSteps.keyDownToText( "Hist");
            debinCompradorSteps.clickFechaCreacionDesde();
            calendarioSteps.clickMesAnterior(24); //PREGUNTAR EN EL CASO BERSA DICE 35
            calendarioSteps.clickEnDiaXParametro("1");
            calendarioSteps.clickEnFechaDeCreacionHasta();
            calendarioSteps.clickDiaActual();
            debinCompradorSteps.clickBuscar();
            debinCompradorSteps.validarColumnaIdDebin();
            debinCompradorSteps.clickDescargarPdfGenerico();
            menuSuperiorSteps.esperarMensajeNotificacion();
            mensajeSteps.clickPrimerMensaje();
            mensajeSteps.clickEnTresPuntos();
            mensajeSteps.clickDescargar();
            debinCompradorSteps.validarDescarga("Listado DEBIN historico");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    //@Test API
    @DisplayName("TC-0013-BERSA: Generar orden DEBIN - Seleccionando CBU")
    public void TC0013BERSA() {
        try {
            testCaseData.cargaDeDatosTest(uEcarina27);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickPagos();
            pagosSteps.clickMasOpcionesGenerico();
            pagosSteps.clickDebinVendedor();
            debinVendedorSteps.clickOrdenDebin();
            debinVendedorSteps.clickCuentaDestinoDesplegable();
            debinVendedorSteps.keyDownToText("2210");
            debinVendedorSteps.setImporteGenerico("1");
            debinVendedorSteps.clickCbu();
            debinVendedorSteps.setCbuOAlias("3860001003000056200723");
            debinVendedorSteps.clickBuscar();
            debinVendedorSteps.clickConcepto();
            debinVendedorSteps.keyDownToText( "Alquileres");
            debinVendedorSteps.setReferencia("Test0013");
            debinVendedorSteps.clickVigencia();
            debinVendedorSteps.keyDownToText( "10 Minutos");
            debinVendedorSteps.clickContinuarGenerico();
            debinVendedorSteps.clickContinuarGenerico();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    //@Test API
    @DisplayName("TC-0015-BERSA: Adherir DEBIN recurrente (Pesos) -  2do factor por SMS - Descargar - Boton Volver")
    public void TC0015BERSA() throws InterruptedException, SQLException {
        try {
            testCaseData.cargaDeDatosTest(uEcarina27);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickPagos();
            fixedWait(3000);
            pagosSteps.clickMasOpcionesGenerico();
            pagosSteps.clickDebinComprador();
            debinCompradorSteps.clickAdherirRecurrencia();
            adherirRecurrenciaSteps.clickSelectorCuentaDebito();
            adherirRecurrenciaSteps.keyDownToText( "2210");
            adherirRecurrenciaSteps.setCuitVendedor("20205056573");
            adherirRecurrenciaSteps.clickBotonConsultar();
            adherirRecurrenciaSteps.clickSelectorPrestacion();
            adherirRecurrenciaSteps.keyDownToText( "EDENOR SA");
            adherirRecurrenciaSteps.clickSelectorConcepto();
            adherirRecurrenciaSteps.keyDownToText( "Alquileres");
            adherirRecurrenciaSteps.setReferencia("TC0015");
            adherirRecurrenciaSteps.setDescripcion("TC0015");
            adherirRecurrenciaSteps.clickBotonContinuar();
            adherirRecurrenciaSteps.clickCheckTerminosCondicionesGenerico();
            dobleFactorSteps.codigoSegundoFactor();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    //@Test API
    @DisplayName("TC-0074-BERSA: ADHERIR NUEVO PAGO O SERVICIO")
    public void TC0074BERSA() {
        try {
            testCaseData.cargaDeDatosTest(uEcarina27);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickPagos();
            pagosSteps.clickServiciosAdheridos();
            pagosSteps.clickSelectorTarjetaDebito();
            pagosSteps.keyDownToText("3009");
            pagosSteps.clickAdherirImpuestoServicio();
            adherirServicioSteps.validarTituloPageGenerico("Adherir servicio");
            adherirServicioSteps.setInputRubro("Cobranzas para Empresas y Comercios");
            adherirServicioSteps.keyDownToText("Cobranzas para Empresas y Comercios");
            adherirServicioSteps.setInputEmpresa("Mercado Libre");
            adherirServicioSteps.keyDownToText( "Mercado Libre");
            adherirServicioSteps.setCodigoDePago("00449535691");
            adherirServicioSteps.setearDescripciondeAdhesion("TestAutomation");
            adherirServicioSteps.clickContinuarGenerico();
            adherirServicioSteps.clickConfirmarGenerico();
            dobleFactorSteps.codigoSegundoFactor();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            //POSTCONDICION
            pagosSteps.eliminarServicioAdheridoAPI(uEcarina27,"TestAutomation","3009","" );
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    //@Test API
    @DisplayName("TC-0070-BERSA: Aceptación de DEBIN")
    public void TC0070BERSA(){
        try {
            //PRECONDICION
            pagosSteps.generacionDebinApi("3860023201000000161496","CA $ 020000052210","20115814991");
            testCaseData.cargaDeDatosTest(uBersa2102);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            //TEST
            menuLateralSteps.clickPagos();
            pagosSteps.clickMasOpcionesGenerico();
            pagosSteps.clickDebinComprador();
            debinCompradorSteps.clickBuscar();
            debinCompradorSteps.validarColumnaIdDebin();
            debinCompradorSteps.clickDetalleDebinPrimerElemento();
            debinCompradorSteps.validarTituloPageGenerico("Detalle de DEBIN");
            debinCompradorSteps.clickAceptarGenerico();
            debinCompradorSteps.clickAceptarDebin();
            debinCompradorSteps.clickCheckTerminosCondicionesGenerico();
            dobleFactorSteps.codigoSegundoFactor();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    //@Test API
    @DisplayName("TC-12964-BERSA: PAGOS - Pagar Impuesto/Servicio Nuevo Pago (Sin Base)")
    public void TC12964BERSA() {
        try {
            //PRECONDICION
            testCaseData.cargaDeDatosTest(uEcarina27);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            //TEST
            menuLateralSteps.clickPagos();
            pagosSteps.clickNuevoPago();
            nuevoPagoSteps.clickCardServicios();
            nuevoPagoSteps.clickDesplegableTarjetaDebito();
            nuevoPagoSteps.keyDownToText("3013");
            nuevoPagoSteps.clickInputRubro();
            nuevoPagoSteps.keyDownToText("Cobranzas para Empresas y Comercios");
            nuevoPagoSteps.clickInputEmpresa();
            nuevoPagoSteps.keyDownToText("Mercado Libre");
            nuevoPagoSteps.validarMensajeCodigoPago();
            adherirServicioSteps.setCodigoDePago("00449535691");
            nuevoPagoSteps.clickCheckBoxServicio();
            //dobleFactorSteps.codigoSegundoFactorOtroMetodo();
            dobleFactorSteps.codigoSegundoFactor();
            botonesGenericosSteps.clickContinuarGenerico();
            nuevoPagoSteps.clickPagandoDesdeDesplegable();
            debinVendedorSteps.keyDownToText("2210");
            recargasSteps.clickBotonSeleccionar();
            debinVendedorSteps.keyDownToText("MPAGO COMPRA");
            debinVendedorSteps.setReferencia("1234567890");
            botonesGenericosSteps.setImporteGenerico("1500");
            adherirServicioSteps.clickContinuarGenerico();
            serviciosAdheridosSteps.clickConfirmarGenerico();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    //@Test API
    @DisplayName("TC-12966-BERSA: PAGOS - Pagar Impuesto/Servicio Eliminar servicio")
    public void TC12966BERSA() {
        try {
            //PRECONDICIÓN
            testCaseData.cargaDeDatosTest(uEcarina27);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            pagosSteps.adherirServicioApi("99992146", "Internet y Telecomunicaciones","ECOWIFI","8006");
            //CASO
            menuLateralSteps.clickCuentas();
            menuLateralSteps.clickPagos();
            pagosSteps.clickServiciosAdheridos();
            serviciosAdheridosSteps.buscarPorDescripcion("TestAutomation1");
            serviciosAdheridosSteps.clickBuscarGenerico();
            serviciosAdheridosSteps.eliminarServicioAdherido();
            serviciosAdheridosSteps.clickBotonDesvincular();
            ticketSteps.validaNotificacionExitosaGenerico("Desvinculaste a ECOWIFI");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    //CASOS DE PERFIL
    @Test
    @DisplayName("TC-0001-BERSA: Cambio de Teléfono - Cambio de teléfono exitoso")
    public void TC0001BERSA() {
        try {
            testCaseData.cargaDeDatosTest(uTranspull1);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuSuperiorSteps.clickPerfil();
            perfilSteps.clickMiPerfil();
            perfilSteps.validarTituloPageGenerico("Mi Perfil");
            perfilSteps.clickModificarTelefono();
            modificarPerfilSteps.validarTituloPageGenerico("Modificar tel");
            modificarPerfilSteps.clickIconoEditar();
            modificarPerfilSteps.setCodArea("11");
            modificarPerfilSteps.setTelefono("55840284");
            modificarPerfilSteps.clickModificarGenerico();
            dobleFactorSteps.codigoSegundoFactorParaCambioTelefono();
            modificarPerfilSteps.validarTituloPageGenerico("Enviamos un código de verificación al celular ingresado.");
            dobleFactorSteps.codigoVerificacionTelefono("1155840284");
            modificarPerfilSteps.validaNotificacionExitosaGenerico("Modificacion exitosa");
            //POSCONDICION
            perfilSteps.setearNumeroTelefonoOriginalAPI();
            modificarPerfilSteps.validarCambioTelefono();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }

    }
    @Test
    @DisplayName("TC-0002-BERSA: Cambio de Correo Electrónico - Cambio de correo electrónico exitoso")
    public void TC0002BERSA() {
        try {
            String nuevoMail = "gpautomation3@yopmail.com";

            testCaseData.cargaDeDatosTest(uTranspull1);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuSuperiorSteps.clickPerfil();
            perfilSteps.clickMiPerfil();
            perfilSteps.validarTituloPageGenerico("Mi Perfil");
            perfilSteps.clickModificarCorreo();
            modificarPerfilSteps.validarTituloPageGenerico("Modificar correo electr");
            modificarPerfilSteps.clickIconoEditar();
            modificarPerfilSteps.setEditarCorreo(nuevoMail);
            modificarPerfilSteps.clickModificarGenerico();
            dobleFactorSteps.codigoSegundoFactor();
            dobleFactorSteps.codigoVerificacionEmail(nuevoMail);
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
            testCaseData.cargaDeDatosTest(uTranspull1);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuSuperiorSteps.clickPerfil();
            perfilSteps.clickMiPerfil();
            perfilSteps.validarTituloPageGenerico("Mi Perfil");
            perfilSteps.clickModificarDomicilio();
            modificarPerfilSteps.validarTituloPageGenerico("Modificar Domicilio");
            modificarPerfilSteps.clickDomicilio();
            modificarPerfilSteps.seleccionarDropdownGenerico("Modificar domicilio");
            modificarPerfilSteps.setCalle(modificarPerfilSteps.generarString(8, "abcdefghias"));
            modificarPerfilSteps.setNumero(generarString(2, "54454511"));
            modificarPerfilSteps.setPiso(generarString(1, "21556"));
            modificarPerfilSteps.setApartamento((generarString(1, "abcploa")));
            modificarPerfilSteps.clickProvincia();
            modificarPerfilSteps.seleccionarDropdownGenerico("CORRIENTES");
            modificarPerfilSteps.clickLocalidad();
            modificarPerfilSteps.seleccionarDropdownGenerico("SAN MIGUEL");
            modificarPerfilSteps.clickCodigoPostal();
            modificarPerfilSteps.seleccionarDropdownGenerico("3781");
            modificarPerfilSteps.guardarDireccion();
            modificarPerfilSteps.clickContinuarGenerico();
            dobleFactorSteps.codigoSegundoFactor();
            //VALIDACION
            menuSuperiorSteps.clickPerfil();
            perfilSteps.clickMiPerfil();
            perfilSteps.clickModificarDomicilio();
            modificarPerfilSteps.validarDomicilio();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    //CASOS DE PRESTAMOS PARA CANCELAR DEUDAS
    //@Test //DATOS ESPECIFICOS NO SE CUENTA CON EL MISMO - DE MOMENTO LO APAGAMOS
    @DisplayName("TC-0220-BERSA: Prestamos- Prestamo para cancelar deudas - Validar pantalla etapa 5 'Confirmacion'")
    public void TC0220BERSA() {
        try {
            testCaseData.cargaDeDatosTest(uEcarina27);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickPrestamos();
            prestamosParaCancelarDeudasSteps.clickPrestamosParaCancelarDeudas();
            prestamosParaCancelarDeudasSteps.clickPrimerCheckbox();
            prestamosSteps.clickSiguiente();
            prestamosSteps.setMonto("9000");
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
    //CASOS DE LA CLASE PRESTAMO
    @Test //ok
    @DisplayName("TC-0206-BERSA: Prestamos - Nuevo prestamo - Validar pantalla etapa 4 'Confirmacion'")
    public void TC0206BERSA() {
        try {
            testCaseData.cargaDeDatosTest(uEcarina27);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickPrestamos();
            prestamosSteps.clickNuevoPrestamo();
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
    //CASO DE LA CLASE RECARGAS
    @Test
    @DisplayName("TC-0071-BERSA: RECARGAS - Realizar recarga a Transporte Publico")
    public void TC0071BERSA(){
        try {
            testCaseData.cargaDeDatosTest(uTranspull1);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            recargasSteps.consultaServiciosAdheridosAPI(SUBE_ID,"6061267159402278","5001");
            recargasSteps.realizarAdhesionAPI(SUBE_ID,"6061267159402278", "JUAN","5001");
            //TEST
            menuLateralSteps.clickRecargas();
            recargasSteps.clickCardTransportePublico();
            recargasSteps.clickEmpresa();
            recargasSteps.seleccionarDropdownGenerico("SUBE");
            recargasSteps.clickSeleccionarNumero();
            recargasSteps.seleccionarDropdownGenerico("JUAN");
            recargasSteps.clickSelectorMonto();
            recargasSteps.seleccionarDropdownGenerico("$2,000.00");
            recargasSteps.clickAgregar();
            recargasSteps.clickDesplegableDebito();
            recargasSteps.seleccionarDropdownGenerico("Caja de Ahorro $ 001005620072");
            recargasSteps.clickContinuar();
            recargasSteps.clickConfirmarGenerico();
            recargasSteps.clickIconoTicket();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0072-BERSA: Recargas - Adhesión para recarga")
    public void TC0072BERSA() {
        try {
            testCaseData.cargaDeDatosTest(uEcarina27);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickRecargas();
            recargasSteps.clickSelectorTarjetaDebito();
            recargasSteps.keyDownToText("8006");
            recargasSteps.clickContinuarGenerico();
            recargasSteps.clickCardTransportePublico();
            recargasSteps.clickEmpresa();
            recargasSteps.keyDownToText("SUBE");
            recargasSteps.clickSeleccionarNumero();
            recargasSteps.keyDownXParametrosGenerico(0,"");
            recargasSteps.setInputNumero("6061267195495203");
            recargasSteps.setInputPerteneceA("AUTOMATION3");
            dobleFactorSteps.codigoSegundoFactor();
            //POSTCONDICION
            recargasSteps.eliminarAdhesaionesdeServicioAPI(SUBE_ID,"6061267195495203","AUTOMATION3","8006");
            //TEARDOWN EN AFTER
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    //@Test // FALTA DE DATOS  - APAGADO POR BUG
    @DisplayName("TC-12955-BERSA: Realizar recarga a Tarjeta Recargable/Prepagada")
    public void TC12955BERSA() {
        try {
            testCaseData.cargaDeDatosTest(uTranspull1);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickRecargas();
            recargasSteps.clickSelectorTarjetaDebito();
            consultaRecargasSteps.keyDownToText( "6007");
            recargasSteps.clickContinuar();
            recargasSteps.clickCardtarjetaRecargable();
            recargasSteps.clickEmpresa();
            consultaRecargasSteps.keyDownToText( "VISA REGALO");
            recargasSteps.clickSeleccionarNumero();
            consultaRecargasSteps.keyDownToText( "JUAN");
            recargasSteps.clickMontoRecarga("1");
            recargasSteps.clickAgregar();
            recargasSteps.clickDesplegableDebito();
            consultaRecargasSteps.keyDownToText( "Cuenta Corriente");
            //consultaRecargasSteps.keyDownToText( "Caja de Ahorro $ 000000869803");
            recargasSteps.clickContinuar();
            recargasSteps.verificarDatosRecargaCuentaOrigen();
            recargasSteps.clickConfirmarGenerico();

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-12956-BERSA: RECARGAS - Eliminar adhesión")
    public void TC12956BERSA() {
        try {
            //PRECONDICION
            testCaseData.cargaDeDatosTest(uUathb04);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            recargasSteps.consultaServiciosAdheridosAPI(CLARO_ID,"5557504183","6009");
            recargasSteps.realizarAdhesionAPI(CLARO_ID,"5557504183", "TC12956","6009");
            //CASO
            menuLateralSteps.clickRecargas();
            recargasSteps.clickAdhesiones();
            recargasSteps.clickBotonSeleccionar();
            nuevoPagoSteps.keyDownToText("Celular");
            recargasSteps.clickBotonSeleccionar();
            recargasSteps.keyDownToText("CLARO");
            calendario.clickBuscar();
            recargasSteps.clickIconoEliminarAdhesion("TC12956");
            recargasSteps.clickConfirmarGenerico();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    //@Test //// SE APAGA EL CASO POR BUG [ARPETEREVO-10513] UAT V75.0 BERSA-RECARGAS-"No pudimos procesar la operación"
    @DisplayName("TC-12952-BERSA: RECARGAS - Realizar recarga a Telefonía Celular")
    public void TC12952BERSA() {
        try {
            //PRECONDICION
            testCaseData.cargaDeDatosTest(uUathb04);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickRecargas();
            recargasSteps.clickCardTelefoniaCelular();
            recargasSteps.clickEmpresa();
            recargasSteps.keyDownToText("CLARO");
            recargasSteps.clickSeleccionarNumero();
            recargasSteps.keyDownXParametrosGenerico(0,"");
            recargasSteps.setInputNumero("5557504183");
            recargasSteps.setInputPerteneceA("Juan");
            recargasSteps.clickSelectorMonto();
            recargasSteps.keyDownToText("$500.00");
            dobleFactorSteps.clickSms();
            dobleFactorSteps.codigoSegundoFactor();
            recargasSteps.clickAgregar();
            recargasSteps.clickDesplegableDebito();
            recargasSteps.keyDownToText("Cuenta Corriente $ 035000023427");
            recargasSteps.clickContinuar();
            recargasSteps.clickConfirmarGenerico();
            recargasSteps.verificarDatosRecargaTelefoniaCelular("CLARO","5557504183","500.00","No pudimos procesar la operación");
            //POSTCONDICION ELIMINAR ADHESION
            recargasSteps.eliminarAdhesaionesdeServicioAPI(CLARO_ID,"5557504183","Juan","6009");

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    //@Test // FALTA DATOS
    @DisplayName("TC-12954-BERSA: RECARGAS - Realizar recarga a Billetera/Serv. Prepago")
    public void TC12954BERSA() {
        try {
            //CASO
            testCaseData.cargaDeDatosTest(uUathb04);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickRecargas();
            recargasSteps.clickCardBilletera();
            recargasSteps.clickEmpresa();
            recargasSteps.keyDownToText("PIM TU PLATA EN TU CELULAR");
            recargasSteps.clickSeleccionarNumero();
            recargasSteps.keyDownXParametrosGenerico(0,"");
            recargasSteps.setInputNumero("1139448145"); //SE CAMBIO
            recargasSteps.setInputPerteneceA("TC-12954");
            recargasSteps.clickMontoRecarga("1");
            dobleFactorSteps.clickSms();
            dobleFactorSteps.codigoSegundoFactor();
            recargasSteps.clickAgregar();
            Thread.sleep(1000);
            recargasSteps.clickDesplegableDebito();
            consultaRecargasSteps.keyDownToText( "Cuenta Corriente $ 035000023427");
            recargasSteps.clickContinuar();
            recargasSteps.clickConfirmarGenerico();
            recargasSteps.verificarDatosRecargaTelefoniaCelular("PIM TU PLATA EN TU CELULAR","1139448145","1,00","No pudimos procesar la operación");
            //POSTCONDICION ELIMINAR ADHESION
            recargasSteps.eliminarAdhesaionesdeServicioAPI(SUBE_ID,"6061267195495203","TC0072","6009");

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    //CASOS DE LA CLASE SEGUROS
    @Test //DATOS OK - TEST OK
    @DisplayName("TC-12928-BERSA: SEGUROS - Alta de seguros  - Acceso por Seguro - Menú lateral - Solicitud  y validación de pantalla")
    public void TC12928BERSA() {
        try {
            testCaseData.cargaDeDatosTest(u11107010);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickSeguros();
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
    //CASOS DE TARJETA DE CREDITO
    @Test
    @DisplayName("TC-0032-BERSA: Consulta de Tarjetas de Crédito - Visa")
    public void TC0032BERSA() {
        try {
            testCaseData.cargaDeDatosTest(uBerhometest15);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.clickVerMasXnroDeTarjeta("3837");
            tarjetasDeCreditoSteps.verificarTituloVisa(testCaseData.tarjetaCredito);
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test //OK
    @DisplayName("TC-0033-BERSA: Consulta de Tarjetas de Crédito - Mastercard")
    public void TC0033BERSA() {
        try {
            //PRECONDICION
            testCaseData.cargaDeDatosTest(uUathb04);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            //TEST
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.clickVerMasXnroDeTarjeta("Mastercard");
            tarjetasDeCreditoSteps.verificarTituloMastercard();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0162-BERSA: Tarjeta de Crédito - Denunciar robo o extravío de Tarjeta de Crédito - WEB.")
    public void TC0162BERSA() {
        try {
            testCaseData.cargaDeDatosTest(u11107010);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.validarTituloPageGenerico("Tarjetas de cr");
            tarjetasDeCreditoSteps.clickVerMasXnroDeTarjeta("8507");
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
    @Test //USUARIO SIN TD ASOCIADAS SE PRECISA OTRO PARA PAGAR LA TD
    @DisplayName("TC-0165-BERSA: PAGOS - Pagar Tarjeta de Crédito Master - Otro importe")
    public void TC0165BERSA() {
        try {
            testCaseData.cargaDeDatosTest(uEcarina27);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.clickVerMasXnroDeTarjeta("0107");
            tarjetasDeCreditoSteps.clickMasOpcionesGenerico();
            tarjetasDeCreditoSteps.clickPagarTarjeta();
            tarjetasDeCreditoSteps.seleccionarTarjetaDebitoSiEstaPresente();
            tarjetasDeCreditoSteps.seleccionarCuentaDebitoSiEstaPresente("2210");
            tarjetasDeCreditoSteps.seleccionarTipoDePago( "Ingresar un importe");
            tarjetasDeCreditoSteps.setImporte("1");
            tarjetasDeCreditoSteps.clickBotonPagar();
            tarjetasDeCreditoSteps.validarTituloPageGenerico("Pago de tarjeta propia");
            tarjetasDeCreditoSteps.clickConfirmarGenerico();
            //AGREGAR PASOS PARA COMPLETAR LA ACCION
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0167-BERSA: Tarjeta de Crédito - Consultar último resumen de una Tarjeta de Crédito - WEB.")
    public void TC0167BERSA() {
        try {
            testCaseData.cargaDeDatosTest(uEcarina27);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.clickVerMasXnroDeTarjeta("0107");
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
    @DisplayName("TC-12967-BERSA: PAGOS - Pagar Tarjeta de Crédito Visa - Otro importe")
    public void TC12967BERSA() {
        try {
            testCaseData.cargaDeDatosTest(u20200960948);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.clickVerMasXnroDeTarjeta("2605");
            Thread.sleep(5000);
            botonesGenericosSteps.clickMasOpcionesGenerico();
            tarjetasDeCreditoSteps.clickPagarTarjeta();
            tarjetasDeCreditoSteps.seleccionarCuentaDebito("Caja de Ahorro $ 062003146320");
            tarjetasDeCreditoSteps.seleccionarTipoDePago("Ingresar un importe");
            tarjetasDeCreditoSteps.setImporte("1");
            tarjetasDeCreditoSteps.clickBotonPagar();
            nuevoPagoSteps.clickConfirmarGenerico();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    //CASO DE LA CLASE DE TARJETA DE DEBITO
    @Test //OK
    @DisplayName("TC-0068-BERSA: Activación de tarjeta de débito - Validación de completitud de pantalla")
    public void TC0068BERSA() {
        try {
            //PRECONDICION
            testCaseData.cargaDeDatosTest(uUattesthb14);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            //TEST
            menuLateralSteps.clickTarjetaDeDebito();
            tarjetasDeDebitoSteps.validarTituloPageGenerico("Tarjetas de d");
            tarjetasDeDebitoSteps.clickBotonVerMas_Activar("7009");
            tarjetasDeDebitoSteps.validarTituloPageGenerico("Activaci");
            tarjetasDeDebitoSteps.validarTituloPageGenerico("Visa D");
            tarjetasDeDebitoSteps.validacionPantallaActivacionTarjeta();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    //@Test 27 nov - Caso apagado para ser probado luego de resuelto el Incidente 10236
    @DisplayName("TC-0105-BERSA: Tarjeta de Débito - Consulta de compras")
    public void TC0105BERSA() {
        try {
            testCaseData.cargaDeDatosTest(uUattesthb14);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickTarjetaDeDebito();
            tarjetasDeDebitoSteps.validarTituloPageGenerico("Tarjetas de d");
            tarjetasDeDebitoSteps.clickBotonVerMas_Activar("7009");
            tarjetasDeDebitoSteps.clickMasOpcionesGenerico();
            tarjetasDeDebitoSteps.clickConsultarCompras();
            tarjetasDeDebitoSteps.validarTituloPageGenerico("Consulta de compras en comercios");
            consultaDeComprasSteps.clickDesplegableCuenta();
            tarjetasDeDebitoSteps.keyDownToText("6442");
            consultaDeComprasSteps.clickFechaDesde();
            calendarioSteps.retrocederSegunMes(10);
            calendarioSteps.clickEnDiaXParametro("1");
            calendarioSteps.clickBuscar();
            consultaDeComprasSteps.validarTablaConsultaCompras();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    //CASO DE LA CLASE TRANSFERENCIA
    //@Test API
    @DisplayName("TC-0008-BERSA: Transferencia Intrabancaria en Pesos - Propias (del mismo banco) Cargada Manualmente - Programada en Fecha Futura Botón Volver")
    public void TC0008BERSA() {
        try {
            testCaseData.cargaDeDatosTest(uEcarina27);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickTransferencias("","");
            transferenciasInmediatas.seleccionarTipoTransferencia("Cuenta Propia");
            transferenciasInmediatas.seleccionarCuentaDestinoCuentasPropias("CC $ 001004555432");
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
    //@Test API
    @DisplayName("TC-0014-BERSA: Generar CREDIN - Cuenta propia $ (mismo Banco u otro) - Mas de una cuenta Debito - CBU - Segundo factor por SMS")
    public void TC0014BERSA() {
        try {
            testCaseData.cargaDeDatosTest(uEcarina27);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickTransferencias("","");
            transferenciasSteps.clickMasOpcionesGenerico();
            transferenciasSteps.clickConsultarCredin();
            consultaCredinSteps.clickGenerarCredin();
            consultaCredinSteps.clickSelectorTarjetaDebito();
            consultaCredinSteps.keyDownToText( "8006");
            consultaCredinSteps.clickContinuarGenerico();
            generarCredinSteps.clickCuentaDebito();
            generarCredinSteps.keyDownXParametrosGenerico(0, "Cuenta Corriente $ 001004555432");
            generarCredinSteps.clickCBU();
            generarCredinSteps.setCampoAliasCbu("0450018601800093482711");
            generarCredinSteps.clickSelectorConcepto();
            generarCredinSteps.keyDownXParametrosGenerico(0,"Alquileres");
            generarCredinSteps.setImporteGenerico("1");
            generarCredinSteps.setCampoDescripcion("Test0014");
            generarCredinSteps.clickContinuarGenerico();
            generarCredinSteps.acpetarTyCDobleFactor();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    //@Test API
    @DisplayName("TC-0139-BERSA: Transferencias Inmediatas - Entre cuentas propias - Flujo completo- Validar ticket con exito")
    public void TC0139BERSA() {
        try {
            testCaseData.cargaDeDatosTest(uAngela01);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickTransferencias("","CA $ 195004110111");
            transferenciasInmediatas.seleccionarTipoTransferencia("Cuenta Propia");
            transferenciasInmediatas.seleccionarCuentaDestinoCuentasPropias("CC $ 195004110111");
            transferenciasInmediatas.seleccionarCuentaOrigen("CA $ 195004110111");
            transferenciasInmediatas.setImporteGenerico("100");
            transferenciasInmediatas.seleccionarConcepto("Alquileres");
            transferenciasInmediatas.setDescripcion("prueba");
            transferenciasInmediatas.clickBotonTransferirAhora();
            transferenciasInmediatas.clickConfirmarGenerico();
            fixedWait(1000);
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            //POSTCONDICION
            transferenciasInmediatas.verificarDescuentoCuentaOrigenTransferencia();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0145-BERSA: Transferencias - Añadir nuevo destinatario - Con cbu valido")
    public void TC0145BERSA() {
        try {
            testCaseData.cargaDeDatosTest(uEcarina27);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
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
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("3300000620000987477051");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    //@Test API
    @DisplayName("TC-12930-BERSA: TRANSFERENCIAS - A cuentas de terceros CBU - mismo banco - Pesos")
    public void TC12930BERSA() {
        try {
            //PRECONDICION
            testCaseData.cargaDeDatosTest(uBerhometest20);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("3860001005000045554325");
            //TEST
            menuLateralSteps.clickTransferencias("","CC $ 007000094026");
            transferenciasInmediatas.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatas.completarInputAliasCbu("3860001005000045554325");
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
    //@Test API
    @DisplayName("TC-12932-BERSA: TRANSFERENCIAS - A cuentas de terceros ALIAS - mismo banco - Pesos")
    public void TC12932BERSA() {
        try {
            testCaseData.cargaDeDatosTest(uBerhometest20);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("ECARINA.AHORRO.PESOS");

            menuLateralSteps.clickTransferencias("","CC $ 007000094026");
            transferenciasInmediatas.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatas.completarInputAliasCbu("ECARINA.AHORRO.PESOS");
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
    //@Test API
    @DisplayName("TC-12935-BERSA: Transferencias Programadas (Fecha especifica) CBU - terceros mismo banco  $ - NO AGENDADOS")
    public void TC12935BERSA() {
        try {
            testCaseData.cargaDeDatosTest(uEcarina27);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("3860195203000041101110");
            //TEST
            menuLateralSteps.clickTransferencias("","CA $ 020000052210");
            transferenciasInmediatas.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatas.completarInputAliasCbu("3860195203000041101110");
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
    //@Test API
    @DisplayName("TC-12969-BERSA: TRANSFERENCIAS - A cuentas de terceros CVU - otros bancos - Pesos")
    public void TC12969BERSA() {
        try {
            testCaseData.cargaDeDatosTest(uBerhometest17);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("0000001700300000000313");
            //TEST
            menuLateralSteps.clickTransferencias("","CC $ 009000026939");
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
    //@Test API
    @DisplayName("TC-12981-BERSA: Transferencias - Eliminar destinatario de agenda")
    public void TC12981BERSA() {
        try {
            //PRECONDICION
            testCaseData.cargaDeDatosTest(uEcarina27);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
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
    //@Test API
    @DisplayName("TC-12938-BERSA: TRANSFERENCIAS - Generar CREDIN - Dolares")
    public void TC12938BERSA() {
        try {
            testCaseData.cargaDeDatosTest(uBerhometest20);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickTransferencias("","");
            transferenciasInmediatas.clickMasOpcionesGenerico();
            transferenciasSteps.clickConsultarCredin();
            consultaCredinSteps.clickGenerarCredin();
            generarCredinSteps.clickCuentaDebito();
            generarCredinSteps.keyDownXParametrosGenerico(1, "USD");
            generarCredinSteps.clickCBU();
            //generarCredinSteps.setCampoAliasCbu("3860001004000031915438");
            generarCredinSteps.setCampoAliasCbu("3860007204000066890711");
            generarCredinSteps.clickSelectorConcepto();
            generarCredinSteps.keyDownXParametrosGenerico(0, "Alquileres");
            generarCredinSteps.setImporteGenerico("1");
            generarCredinSteps.setCampoDescripcion("Test12938");
            generarCredinSteps.clickContinuarGenerico();
            generarCredinSteps.acpetarTyCDobleFactor();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    //@Test API
    @DisplayName("TC-12936 - BERSA: TRANSFERENCIAS - Consulta de transferencias realizadas")
    public void TC12936BERSA() {
        try {
            //CASO
            testCaseData.cargaDeDatosTest(uBerhometest20);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
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
    //@Test API
    @DisplayName("TC-12939-BERSA: TRANSFERENCIAS - Consultar CREDIN Originante")
    public void TC12939BERSA() {
        try {
            //Precondicion: Generar Credin
            testCaseData.cargaDeDatosTest(uBerhometest17);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            generarCredinSteps.generarCredinApi("3860009601000000269398","3860001003000056200723","4029");
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
    //@Test API
    @DisplayName("TC-12940-BERSA: TRANSFERENCIAS - Consultar CREDIN destinatario")
    public void TC12940BERSA() {
        try {
            //Precondicion: Generar Credin
            loginSteps.loginForAPI(uEcarina27);
            generarCredinSteps.generarCredinApi("3860001005000045554325","3860035501000000234277","3009");
            //Test: Consulta de Credin
            testCaseData.cargaDeDatosTest(uUathbb04);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
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
    //@Test //27 nov - Caso apagado por falta de datos
    @DisplayName("TC-14250-BERSA: Transferencias CBU a cuentas propias otro banco $ - NO AGENDADOS")
    public void TC14250BERSA(){
        try{
            //CASO
            testCaseData.cargaDeDatosTest(uTranspull1);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("3300520915200003347070");
            //TEST
            menuLateralSteps.clickTransferencias("","CA $ 001005620072");
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
            //POSTCONDICION
            transferenciasInmediatas.verificarDescuentoCuentaOrigenTransferencia();
        }catch (Exception ex){
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    //@Test API
    @DisplayName("TC-0409-BFS: TRANSFERENCIAS - terceros mismo banco  $ - AGENDADOS")
    public void TC0409BERSA(){
        try{
            //PRECONDICION
            testCaseData.cargaDeDatosTest(uBerhometest20);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            agendaNuevoDestinatarioSteps.adherirNuevoDestinatarioCBU("3860020103000000522105","TC0409BERSA");
            //TEST
            menuLateralSteps.clickTransferencias("", "CC $ 007000094026");
            transferenciasInmediatas.seleccionarTipoTransferencia("Contacto Agendado");
            transferenciasInmediatas.clickAgendaDestinatario();
            transferenciasInmediatas.transferirAunContacto("TC0409BERSA");
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
    //@Test //27 nov - Caso apagado caso por falta de datos
    @DisplayName("TC-0407-BFS: Transferencias ALIAS a cuentas propias otro banco $ - NO AGENDADOS")
    public void TC0407BERSA(){
        try{
            //PRECONDICION
            testCaseData.cargaDeDatosTest(uAgustina2029);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("MARCHA.REINA.CELTA");
            //TEST
            menuLateralSteps.clickTransferencias("","CC $ 024003527202");
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
            //POSTCONDICION
            transferenciasInmediatas.verificarDescuentoCuentaOrigenTransferencia();
        }catch (Exception ex){
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    //@Test //27 nov - Caso apagado caso por falta de datos
    @DisplayName("TC-0463-BFS: Transferencias Programadas (Fecha especifica) ALIAS a cuentas propias otro banco $ - NO AGENDADOS")
    public void TC0463BERSA() {
        try {
            //TEST
            testCaseData.cargaDeDatosTest(uAgustina2029);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("MARCHA.REINA.CELTA");
            //TEST
            menuLateralSteps.clickTransferencias("","CC $ 024003527202");
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
    //@Test API
    @DisplayName("TC-0473-BFS: Transferencias Programadas (Fecha especifica) CBU - terceros otros bancos $ - NO AGENDADOS")
    public void TC0473BERSA() {
        try {
            //TEST
            testCaseData.cargaDeDatosTest(uBerhometest20);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("3300001320010001101040");
            //TEST
            menuLateralSteps.clickTransferencias("","CC $ 007000094026");
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
    //@Test API
    @DisplayName("TC-0411-BFS: Transferencias - terceros otros bancos $ - AGENDADOS")
    public void TC0411BERSA(){
        try{
            //PRECONDICION
            testCaseData.cargaDeDatosTest(uEcarina27);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            agendaNuevoDestinatarioSteps.adherirNuevoDestinatarioCBU("3300520915200000746085","TC0411BERSA");
            //TEST
            menuLateralSteps.clickTransferencias("3009","CA $ 020000052210");
            transferenciasInmediatas.seleccionarTipoTransferencia("Contacto Agendado");
            transferenciasInmediatas.clickAgendaDestinatario();
            transferenciasInmediatas.transferirAunContacto("TC0411BERSA");
            transferenciasInmediatas.setImporteGenerico("1");
            transferenciasInmediatas.seleccionarConcepto("Alquileres");
            transferenciasInmediatas.setDescripcion("TestAutomation");
            transferenciasInmediatas.clickBotonTransferirAhora();
            transferenciasInmediatas.clickConfirmarGenerico();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            //POSTCONDICION
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("3860001001000036919074");
            transferenciasInmediatas.verificarDescuentoCuentaOrigenTransferencia();
        }catch (Exception ex){
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    //@Test API
    @DisplayName("TC-0427-BFS: Transferencias CVU - terceros $ AGENDADOS")
    public void TC0427BERSA(){
        try{
            //PRECONDICION
            testCaseData.cargaDeDatosTest(uEcarina27);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            agendaNuevoDestinatarioSteps.adherirNuevoDestinatarioCBU("0000001700300000000313","CVU");
            //TEST
            menuLateralSteps.clickTransferencias("3009","CA $ 020000052210");
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
    //@Test 27 nov - Caso apagado para ser probado luego de resuelto el Incidente 10237
    @DisplayName("TC-0429-BFS: Transferencias ALIAS de CVU - terceros $ NO AGENDADOS")
    public void TC0429BERSA(){
        try{
            //PRECONDICION
            testCaseData.cargaDeDatosTest(uEcarina27);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("BOTE.FILA.MITO");
            //TEST
            menuLateralSteps.clickTransferencias("3009","CA $ 020000052210");
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
    //@Test API
    @DisplayName("TC-0465-BFS: Transferencias Programadas (Fecha especifica) - terceros mismo banco  $ - AGENDADOS")
    public void TC0465BERSA(){
        try{
            //PRECONDICION
            testCaseData.cargaDeDatosTest(uAngela01);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            agendaNuevoDestinatarioSteps.adherirNuevoDestinatarioCBU("3860020103000000522105","TC0465BERSA");
            //TEST
            menuLateralSteps.clickTransferencias("","CA $ 195004110111");
            transferenciasInmediatas.seleccionarTipoTransferencia("Contacto Agendado");
            transferenciasInmediatas.clickAgendaDestinatario();
            transferenciasInmediatas.transferirAunContacto("TC0465BERSA");
            transferenciasInmediatas.setImporteGenerico("1");
            transferenciasInmediatas.seleccionarConcepto("Alquileres");
            transferenciasInmediatas.setDescripcion("TestAutomation");
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
    //@Test API
    @DisplayName("TC-0477-BFS: Transferencias Programadas (Fecha especifica) ALIAS - terceros mismo banco  $ - NO AGENDADOS")
    public void TC0477BERSA() {
        try {
            //TEST
            testCaseData.cargaDeDatosTest(uBerhometest20);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("GLOBO.FIESTA.PAZ");
            //TEST
            menuLateralSteps.clickTransferencias("","CC $ 007000094026");
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
        }catch (Exception ex){
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    //@Test API
    @DisplayName("TC-0467-BFS: Transferencias Programadas (Fecha especifica) - terceros otros bancos $ - AGENDADOS")
    public void TC0467BERSA(){
        try{;
            //PRECONDICION
            testCaseData.cargaDeDatosTest(uEcarina27); //SE CAMBIO
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            agendaNuevoDestinatarioSteps.adherirNuevoDestinatarioCBU("3860010201000000171602","TER-BER");
            //TEST
            menuLateralSteps.clickTransferencias("","CA $ 020000052210"); //VERIFICAR
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
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("3860010201000000171602"); //SE CAMBIO
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    //@Test API
    @DisplayName("TC-0483-BFS: Transferencias Programadas (Fecha especifica) CVU - terceros $ AGENDADOS")
    public void TC0483BERSA(){
        try{
            //PRECONDICION
            testCaseData.cargaDeDatosTest(uEcarina27);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            agendaNuevoDestinatarioSteps.adherirNuevoDestinatarioCBU("0000001700300000000313","TC0483BERSA");
            //TEST
            menuLateralSteps.clickTransferencias("","CA $ 020000052210");
            transferenciasInmediatas.seleccionarTipoTransferencia("Contacto Agendado");
            transferenciasInmediatas.clickAgendaDestinatario();
            transferenciasInmediatas.transferirAunContacto("TC0483BERSA");
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
    //@Test API
    @DisplayName("TC-0484-BFS: Transferencias Programadas (Fecha especifica) CVU - terceros $ NO AGENDADOS")
    public void TC0484BERSA() {
        try {
            testCaseData.cargaDeDatosTest(uEcarina27);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("0000001700300000000313");
            //TEST
            menuLateralSteps.clickTransferencias("","CA $ 020000052210");
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
    //@Test API
    @DisplayName("TC-0485-BFS: Transferencias Programadas (Fecha especifica) ALIAS de CVU - terceros $ NO AGENDADOS")
    public void TC0485BERSA() {
        try {
            testCaseData.cargaDeDatosTest(uEcarina27);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("BOTE.FILA.MITO");
            //TEST
            menuLateralSteps.clickTransferencias("","CA $ 020000052210");
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
