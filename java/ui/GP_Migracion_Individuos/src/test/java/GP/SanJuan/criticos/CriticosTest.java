package GP.SanJuan.criticos;

import api.LoginAPI;
import db.ConexionDB;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.*;
import org.openqa.selenium.By;
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

import static db.ConexionDB.cerrarConexion;
import static utils.Constantes.*;
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
    LoginAPI loginAPI = new LoginAPI();
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
    }

    //CASOS DE ADELANTOS DE SUELDO
    //@Test  //SE APAGA POR BUG DE UX -ARPETEREVO-10346
    @DisplayName("TC-0223-BSJ: Adelantos de Sueldo - Realizar solicitud de adelanto de sueldo  - Validar pantalla de confimacion de solicitud")
    public void TC0223BSJ() {
        try {
            testCaseData.cargaDeDatosTest(uCuyo2023);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickCuentas();
            adelantosDeSueldoSteps.clickVerMasXnroCuenta("CA $ 90181358041");
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
    //@Test // V //SE APAGA POR BUG DE UX - ARPETEREVO-10346
    @DisplayName("TC-12978-BSJ: OFERTA - Adelanto de Sueldo - Acceso Directo")
    public void TC12978BSJ() {
        try {
            testCaseData.cargaDeDatosTest(uCuyo2023);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickInicio();
            adelantosDeSueldoSteps.clickAccesoDirectoAdelantoSueldo();
            adelantosDeSueldoSteps.validarTituloPageGenerico("Adelanto de sueldo");
            adelantosDeSueldoSteps.clickCardAdelantoSueldo();
            adelantosDeSueldoSteps.seleccionarDesplegableCuentaOrigen();
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
    @DisplayName("TC-0287-BSJ: Consentimiento Billetera - Aprobar consentimiento")
    public void TC0287BSJ() {
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
    //CASOS DE LA CLASE CUENTAS
    @Test //CELULA CUENTAS //USUARIO OK
    @DisplayName("TC-0006-BSJ: Adherir Cuenta  - Nuevo destinatario - por Alias - Descarga e impresión del PDF")
    public void TC0006BSJ()  {
        try {
            //PRECONDICION
            testCaseData.cargaDeDatosTest(uNazarena3);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("PAREJA.VUELTA.VUELTO");
            //TEST
            menuLateralSteps.clickCuentas();
            cuentasSteps.clickMasOpcionesGenerico();
            cuentasSteps.clickAgendaDeDestinararios();
            agendaNuevoDestinatarioSteps.clickAdherirNuevoDestinatario();
            agendaNuevoDestinatarioSteps.setInputCbuAlias("PAREJA.VUELTA.VUELTO");
            agendaNuevoDestinatarioSteps.clickBuscar();
            agendaNuevoDestinatarioSteps.validarDatosCuenta("0450500602800080579450", "PEREZ, LEONARDO DARIO", "20236383335"); //PENDIENTE
            agendaNuevoDestinatarioSteps.setPerteneceA("PruebaAutomation");
            transferenciasInmediatas.botonSonCorrectos();
            dobleFactorSteps.codigoSegundoFactor();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            ticketSteps.clickDescargas();
            ticketSteps.clickImprimir();
            ticketSteps.validarDescarga("Ticket.pdf");
            agendaNuevoDestinatarioSteps.clickVolverGenerico();
            //POSTCONDICION
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("PAREJA.VUELTA.VUELTO"); //PENDIENTE
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test //TRANSPULL1 BERSA NO TIENE CUENTA - VERIFICAR USUARIO
    @DisplayName("TC-0027-BSJ: Generar Orden de Extracción_Con Saldo disponible en Cuenta.")
    public void TC0027BSJ() {
        try {
            testCaseData.cargaDeDatosTest(uCarmend);//DARAGOA
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickCuentas();
            cuentasSteps.clickOrdenesDeExtraccion();
            ordenDeExtraccionConsulta_generarSteps.clickGenerarOrdenDeExtraccion();
            ordenDeExtraccionConsulta_generarSteps.validarTituloPageGenerico("Generar orden de extracc");
            ordenDeExtraccionConsulta_generarSteps.clickCuentaOrigen();
            ordenDeExtraccionConsulta_generarSteps.seleccionarDropdownGenerico("Caja de Ahorro $ 90180707235");
            ordenDeExtraccionConsulta_generarSteps.setDni("13666217");
            ordenDeExtraccionConsulta_generarSteps.setImporteGenerico("100");
            ordenDeExtraccionConsulta_generarSteps.setReferencia("T27");
            ordenDeExtraccionConsulta_generarSteps.clickBotonAgregar();
            ordenDeExtraccionConsulta_generarSteps.clickContinuarGenerico();
            dobleFactorSteps.codigoSegundoFactor();
            ordenDeExtraccionConsulta_generarSteps.validarTituloPageGenerico("Destinatario");
            ordenDeExtraccionConsulta_generarSteps.validarTituloPageGenerico("DNI 13666217");
            ordenDeExtraccionConsulta_generarSteps.clickIconoComprobante();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            ticketSteps.clickVolverGenerico();
            ordenDeExtraccionConsulta_generarSteps.validarTituloPageGenerico("Destinatario");
            ordenDeExtraccionConsulta_generarSteps.validarTituloPageGenerico("DNI 13666217");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0040-BSJ: Acceder a Consulta de movimientos de cuenta")
    public void TC0040BSJ() {
        try {
            testCaseData.cargaDeDatosTest(uGdrio_12);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickCuentas();
            cuentasSteps.clickVerMasXnroCuenta("CA $ 15008057945");
            UtilsGP.fixedWait(2000);
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
    @DisplayName("TC-0025-BSJ: Echeq - Completitud de pantalla con filtro: Estado = Todos")
    public void TC0025BSJ() {
        try {
            testCaseData.cargaDeDatosTest(uGdrio_12);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickCuentas();
            cuentasSteps.clickMasOpcionesGenerico();
            cuentasSteps.clickEcheq();
            echeqSteps.validarTituloPageGenerico("Echeq");
            echeqSteps.clickEstado();
            echeqSteps.seleccionarDropdownGenerico( "Todos");
            echeqSteps.clickBotonBuscar();
            echeqSteps.validarCompletitudPantalla();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    //CASOS DE LA CLASE CUOTIFICALO
    //@Test //ESTE CASO QUEMA EL DATO - DE MOMENTO APAGADO - REFACTORIZRA CON USUARIO QUILICIE1 - UPS
    @DisplayName("TC-0089-BSJ: Cuotifícalo - Cuotificar compras con TD")
    public void TC0089BSJ() {
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
    @DisplayName("TC-0009-BSJ: CPA / VTA de Dolares  - Cliente Vende 10 Dólares y Acredita en CA en Pesos")
    public void TC0009BSJ() {
        try {
            testCaseData.cargaDeDatosTest(uCarmend);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickCompraVentaDeDolares();
            compraVentaDeDolaresSteps.validarTituloPageGenerico("Compra venta de d");
            compraVentaDeDolaresSteps.clickTipoDeOperacion();
            compraVentaDeDolaresSteps.seleccionarDropdownGenerico("Vender");
            compraVentaDeDolaresSteps.clickCuentaOrigen();
            compraVentaDeDolaresSteps.seleccionarDropdownGenerico( "USD 25001995700");
            compraVentaDeDolaresSteps.clickCuentaDestino();
            compraVentaDeDolaresSteps.seleccionarDropdownGenerico("7235");
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
    @DisplayName("TC-0069-BSJ: Compra de moneda extranjera (USD)")
    public void TC0069BSJ() {
        try {
            testCaseData.cargaDeDatosTest(uSanjuan_135);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickCompraVentaDeDolares();
            compraVentaDeDolaresSteps.validarTituloPageGenerico("Compra venta de d");
            compraVentaDeDolaresSteps.clickTipoDeOperacion();
            compraVentaDeDolaresSteps.keyDownToText( "Comprar");
            compraVentaDeDolaresSteps.clickCuentaOrigen();
            compraVentaDeDolaresSteps.keyDownToText( "8271");
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
    @DisplayName("TC- 0173- BSJ-  Inversiones- Alta cuenta comitente- Solicitar cuenta comitente  - Validar pantalla etapa '5 Confirmacion'")
    public void TC0173BSJ() {
        try {
            testCaseData.cargaDeDatosTest(uPract2003);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickSolicitarNuevaCuentaComitente();
            cuentaComitenteSteps.clickSolicitarCuenta();
            cuentaComitenteSteps.validarTituloPageGenerico("Apertura de cuenta comitente");
            cuentaComitenteSteps.clickContinuarGenerico();
            cuentaComitenteSteps.clickCheckboxNingunaDeLasAnteriores();
            cuentaComitenteSteps.clickContinuarGenerico();
            UtilsGP.fixedWait(1000);
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
    @Test
    @DisplayName("TC-0111-BSJ: Suscribir FCI - Validación de pantalla de suscripción de FCI")
    public void TC0111BSJ() {
        try {
            testCaseData.cargaDeDatosTest(uGdrio_12);
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
    //@Test Se bloquea por BUG - https://technisys.atlassian.net/browse/ARPETEREVO-11015
    @DisplayName("TC-0113-BSJ: FCI - Rescatar - Importe Parcial - Validación de pantalla")
    public void TC0113BSJ() {
        try {
            testCaseData.cargaDeDatosTest(uNazarena3);
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
    //CASOS DE LA CLASE PLAZO FIJO - CONTINUAR DESDE AQUI
    @Test
    @DisplayName("TC-0042-BSJ: Alta de PF Tradicional en $ a 30 días")
    public void TC0042BSJ() {
        try {
            testCaseData.cargaDeDatosTest(uSanjuan_135);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickPlazosFijos();
            plazosFijosSteps.clickSolicitarPlazoFijo();
            solicitarPlazoFijoSteps.clickSelectorTipoPF();
            solicitarPlazoFijoSteps.keyDownToText("Plazo Fijo Tradicional en Pesos");
            solicitarPlazoFijoSteps.setImporteGenerico("120000");
            solicitarPlazoFijoSteps.setPlazoEnDias("30");
            solicitarPlazoFijoSteps.clickSelectorCuentaDebito();
            solicitarPlazoFijoSteps.keyDownXParametrosGenerico(0, "8271");
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
    //@Test // (11-12-24) SE APAGA POR BUG RELACIONADOS 9240 - 8339
    @DisplayName("TC-0043-BSJ: Alta de PF UVA $ 350 días ")
    public void TC0043BSJ() {
        try {
            testCaseData.cargaDeDatosTest(uCarmend);
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
    //@Test //APAGADO POR BUG 9240 - 8339
    @DisplayName("TC-12974-BSJ INVERSIONES - Plazo Fijo UVA Precancelable")
    public void TC12974BSJ() {
        try {
            testCaseData.cargaDeDatosTest(uSanjuan_135);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickPlazosFijos();
            plazosFijosSteps.clickSolicitarPlazoFijo();
            solicitarPlazoFijoSteps.clickSelectorTipoPF();
            solicitarPlazoFijoSteps.seleccionarDropdownGenerico("Plazo Fijo UVA Precancelable");
            solicitarPlazoFijoSteps.setImporteGenerico("15000");
            solicitarPlazoFijoSteps.setPlazoEnDias("350");
            solicitarPlazoFijoSteps.clickSelectorCuentaDebito();
            solicitarPlazoFijoSteps.seleccionarDropdownGenerico("Caja de Ahorro $ 90189348271 ");
            solicitarPlazoFijoSteps.clickSelectorAccionVencimiento();
            solicitarPlazoFijoSteps.keyDownToText( "al vencimiento");
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
    @DisplayName("TC-12973-BSJ: INVERSIONES - Plazo Fijo Tradicional en Dolares")
    public void TC12973BSJ() {
        try {
            testCaseData.cargaDeDatosTest(uSanjuan_135);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickPlazosFijos();
            plazosFijosSteps.clickSolicitarPlazoFijo();
            solicitarPlazoFijoSteps.clickSelectorTipoPF();
            solicitarPlazoFijoSteps.seleccionarDropdownGenerico("Plazo Fijo Tradicional en D");
            solicitarPlazoFijoSteps.setImporteGenerico("1000");
            solicitarPlazoFijoSteps.setPlazoEnDias("30");
            solicitarPlazoFijoSteps.clickSelectorCuentaDebito();
            solicitarPlazoFijoSteps.seleccionarDropdownGenerico("7613");
            solicitarPlazoFijoSteps.clickSelectorAccionVencimiento();
            solicitarPlazoFijoSteps.seleccionarDropdownGenerico( "Renovación automática");
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
    @DisplayName("TC-0048-BSJ: Recupero de Usuario - Recuperar Usuario")
    public void TC0048BSJ(){
        try {
            testCaseData.cargaDeDatosTest(uGdrio_12);
            String randomStr = RandomStringUtils.randomAlphabetic(4);
            //TEST
            loginSteps.waitGP();
            loginSteps.clickIngresarStartPage();
            loginSteps.setUsuario(testCaseData.usuario);
            loginSteps.clickRecuperarUsuario();
            //loginSteps.setNroDocumento("23638333");
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
            UtilsGP.fixedWait(2000);
            loginSteps.verificarSaludo();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    //CASOS DE LA CLASE PAGOS
    // @Test //API
    @DisplayName("TC-0012-BSJ: DEBIN Comprador - Consulta DEBINes históricos - Listado")
    public void TC0012BSJ() {
        try {
            testCaseData.cargaDeDatosTest(uSanjuan_135);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickPagos();
            pagosSteps.clickMasOpcionesGenerico();
            pagosSteps.clickDebinComprador();
            debinCompradorSteps.clickSelectorConsultarDebin();
            debinCompradorSteps.seleccionarDropdownGenerico( "Hist");
            debinCompradorSteps.clickFechaCreacionDesde();
            calendarioSteps.retrocedeMesYAnioEspecifico("diciembre 2022");
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
    // @Test //API
    @DisplayName("TC-0013-BSJ: Generar orden DEBIN - Seleccionando CBU")
    public void TC0013BSJ() {
        try {
            testCaseData.cargaDeDatosTest(uGdrio_12);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickPagos();
            pagosSteps.clickMasOpcionesGenerico();
            pagosSteps.clickDebinVendedor();
            debinVendedorSteps.clickOrdenDebin();
            debinVendedorSteps.clickCuentaDestinoDesplegable();
            debinVendedorSteps.seleccionarDropdownGenerico("7945");
            debinVendedorSteps.setImporteGenerico("1");
            debinVendedorSteps.clickCbu();
            debinVendedorSteps.setCbuOAlias("3300041910410410017014");
            debinVendedorSteps.clickBuscar();
            debinVendedorSteps.clickConcepto();
            debinVendedorSteps.seleccionarDropdownGenerico( "Alquileres");
            debinVendedorSteps.setReferencia("TestAutomation");
            debinVendedorSteps.clickVigencia();
            debinVendedorSteps.seleccionarDropdownGenerico( "10 Minutos");
            debinVendedorSteps.clickContinuarGenerico();
            debinVendedorSteps.clickContinuarGenerico();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    // @Test //API
    @DisplayName("TC-0015-BSJ: Adherir DEBIN recurrente (Pesos) -  2do factor por SMS - Descargar - Boton Volver")
    public void TC0015BSJ(){
        try {
            testCaseData.cargaDeDatosTest(uSanjuan_135);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickPagos();
            pagosSteps.clickMasOpcionesGenerico();
            pagosSteps.clickDebinComprador();
            debinCompradorSteps.clickAdherirRecurrencia();
            adherirRecurrenciaSteps.clickSelectorCuentaDebito();
            adherirRecurrenciaSteps.seleccionarDropdownGenerico( "8271");
            adherirRecurrenciaSteps.setCuitVendedor("20179326591");
            adherirRecurrenciaSteps.clickBotonConsultar();
            adherirRecurrenciaSteps.clickSelectorPrestacion();
            adherirRecurrenciaSteps.seleccionarDropdownGenerico( "EDENOR SA");
            adherirRecurrenciaSteps.clickSelectorConcepto();
            adherirRecurrenciaSteps.seleccionarDropdownGenerico( "Alquileres");
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
    // @Test //API
    @DisplayName("TC-0074-BSJ: ADHERIR NUEVO PAGO O SERVICIO")
    public void TC0074BSJ() {
        try {
            testCaseData.cargaDeDatosTest(uSanjuan_135);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickPagos();
            pagosSteps.clickServiciosAdheridos();
            nuevoPagoSteps.clickCardServicios();
            adherirServicioSteps.validarTituloPageGenerico("Adherir servicio");
            nuevoPagoSteps.clickInputRubro();
            adherirServicioSteps.seleccionarDropdownGenerico("Cobranzas para Empresas y Comercios");
            UtilsGP.fixedWait(4000);
            adherirServicioSteps.clickDesplegableEmpresa();
            adherirServicioSteps.seleccionarDropdownGenerico( "Mercado Pago");
            adherirServicioSteps.setCodigoDePago("10001059111");
            adherirServicioSteps.setearDescripciondeAdhesion("TestAutomation");
            adherirServicioSteps.clickContinuarGenerico();
            adherirServicioSteps.clickConfirmarGenerico();
            dobleFactorSteps.codigoSegundoFactor();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            //POSTCONDICION
            pagosSteps.eliminarServicioAdheridoAPI("Mercado Pago","TestAutomation","1012","00449535691");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    // @Test //API
    @DisplayName("TC-0070-BSJ: Aceptación de DEBIN")
    public void TC0070BSJ(){
        try {
            //PRECONDICION
            testCaseData.cargaDeDatosTest(uGdrio_12);
            loginSteps.loginAPI(testCaseData.usuario,testCaseData.password);
            pagosSteps.generacionDebinApi("0450500601800008112711","CA $ 15008057945","23244825664");
            //TEST
            testCaseData.cargaDeDatosTest(uTranspull1);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
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
    // @Test //API // FRONT NUEVO NO ACTIVAR HASTA NUEVO AVISO DE LA CELULA
    @DisplayName("TC-12964-BSJ: PAGOS - Pagar Impuesto/Servicio Nuevo Pago (Sin Base)")
    public void TC12964BSJ() {
        try {
            //PRECONDICION
            testCaseData.cargaDeDatosTest(uSanjuan_135);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            //TEST
            menuLateralSteps.clickPagos();
            pagosSteps.clickNuevoPago();
            nuevoPagoSteps.clickCardServicios();
            nuevoPagoSteps.clickInputRubro();
            nuevoPagoSteps.seleccionarDropdownGenerico("Cobranzas para Empresas y Comercios");
            nuevoPagoSteps.clickInputEmpresa();
            nuevoPagoSteps.seleccionarDropdownGenerico("Mercado Libre");
            nuevoPagoSteps.validarMensajeCodigoPago();
            adherirServicioSteps.setCodigoDePago("00449535691");
            nuevoPagoSteps.clickCheckBoxServicio();
            //dobleFactorSteps.codigoSegundoFactorOtroMetodo();
            dobleFactorSteps.codigoSegundoFactor();
            botonesGenericosSteps.clickContinuarGenerico();
            nuevoPagoSteps.clickPagandoDesdeDesplegable();
            debinVendedorSteps.seleccionarDropdownGenerico("8271");
            recargasSteps.clickBotonSeleccionar();
            debinVendedorSteps.seleccionarDropdownGenerico("MPAGO COMPRA");
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
    // @Test //API // FRONT VIEJO/PRODUCTIVO
    @DisplayName("TC-12964-BSJ: PAGOS - Pagar Impuesto/Servicio Nuevo Pago (Sin Base)")
    public void VTC12964BSJ(){
       try{
           testCaseData.cargaDeDatosTest(uGdrio_12);
           loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
           menuLateralSteps.clickPagos();
           pagosSteps.clickNuevoPagoFrontViejo();
           nuevoPagoSteps.clickInputRubro();
           nuevoPagoSteps.seleccionarDropdownGenerico("Cobranzas para Empresas y Comercios");
           nuevoPagoSteps.clickInputEmpresa();
           nuevoPagoSteps.seleccionarDropdownGenerico("Mercado Libre");
           nuevoPagoSteps.validarMensajeCodigoPago();
           adherirServicioSteps.setCodigoDePago("00449535691");
           dobleFactorSteps.codigoSegundoFactorBotonContinuar();
           nuevoPagoSteps.clickPagandoDesdeDesplegable();
           debinVendedorSteps.seleccionarDropdownGenerico("Caja de Ahorro $ 15008057945");
           recargasSteps.clickBotonSeleccionar();
           debinVendedorSteps.seleccionarDropdownGenerico("MLIBRE FACT");
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
    // @Test //API // SE APAGA POR FRONT NUEVO
    @DisplayName("TC-12966-BSJ: PAGOS - Pagar Impuesto/Servicio Eliminar servicio")
    public void TC12966BSJ() {
        try {
            //PRECONDICIÓN
            testCaseData.cargaDeDatosTest(uGdrio_12);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            pagosSteps.adherirServicioApi("99992146", "Cobranzas para Empresas y Comercios","Mercado Pago","1015"); //VERIFICAR
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
    // @Test //API //CASO FRONT VIEJO
    @DisplayName("TC-12966-BSJ: PAGOS - Pagar Impuesto/Servicio Eliminar servicio")
    public void VTC12966BSJ() {
        try{
            //PRECONDICION
            testCaseData.cargaDeDatosTest(uGdrio_12);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            pagosSteps.adherirServicioApi("99992150", "Internet y Telecomunicaciones","ECOWIFI","1015");
            //TEST
            menuLateralSteps.clickPagos();
            pagosSteps.clickMasOpcionesGenerico();
            pagosSteps.clickServiciosAdheridos();
            serviciosAdheridosSteps.clickEmpresa();
            serviciosAdheridosSteps.seleccionarDropdownGenerico("ECOWIFI");
            serviciosAdheridosSteps.clickTresPuntosCodPago("99992150");
            serviciosAdheridosSteps.clickEliminarServicio();
            serviciosAdheridosSteps.clickConfirmarGenerico();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    //CASOS DE PERFIL
    //@Test Se bloquea por BUG - https://technisys.atlassian.net/browse/ARPETEREVO-11013
    @DisplayName("TC-0001-BSJ: Cambio de Teléfono - Cambio de teléfono exitoso")
    public void TC0001BSJ() {
        try {
            testCaseData.cargaDeDatosTest(uGdrio_12);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            loginSteps.verificarTelefono();
            menuSuperiorSteps.clickPerfil();
            perfilSteps.clickMiPerfil();
            perfilSteps.validarTituloPageGenerico("Mi Perfil");
            perfilSteps.clickModificarTelefono();
            modificarPerfilSteps.validarTituloPageGenerico("Modificar tel");
            modificarPerfilSteps.clickIconoEditar();
            modificarPerfilSteps.setCodArea("11");
            modificarPerfilSteps.setTelefono(TELEFONO);
            modificarPerfilSteps.clickModificarGenerico();
            dobleFactorSteps.codigoSegundoFactorParaCambioTelefono();
            modificarPerfilSteps.validarTituloPageGenerico("Enviamos un código de verificación al celular ingresado.");
            dobleFactorSteps.codigoVerificacionTelefono("11"+TELEFONO);
            modificarPerfilSteps.validaNotificacionExitosaGenerico("Modificacion exitosa");
            //POSCONDICION
            perfilSteps.setearNumeroTelefonoOriginalAPI();
            modificarPerfilSteps.validarCambioTelefono();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }

    }
    //@Test Se bloquea el caso por bug - https://technisys.atlassian.net/browse/ARPETEREVO-11014 "Falta de Actualización Core"
    @DisplayName("TC-0002-BSJ: Cambio de Correo Electrónico - Cambio de correo electrónico exitoso")
    public void TC0002BSJ() {
        try {
            String nuevoMail = "practiagp@gmail.com";

            testCaseData.cargaDeDatosTest(uGdrio_12);
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
    //@Test // SE APAGA EL CASO HASTA RESOLUCION DEL BUG - BUG ARPETEREVO-10657
    @DisplayName("TC-0004-BSJ: Cambio domicilio")
    public void TC0004BSJ() {
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
            modificarPerfilSteps.seleccionarDropdownGenerico("CORRIENTES");
            modificarPerfilSteps.clickCodigoPostal();
            modificarPerfilSteps.seleccionarDropdownGenerico("3400");
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
    @DisplayName("TC-0220-BSJ: Prestamos- Prestamo para cancelar deudas - Validar pantalla etapa 5 'Confirmacion'")
    public void TC0220BSJ() {
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
    //@Test //SE APAGA YA QUE SE NECESITA UN NUEVO USUARIO HABILITADO PARA LA OPERATORIA - PEDIR DATOS A DARK
    @DisplayName("TC-0206-BSJ: Prestamos - Nuevo prestamo - Validar pantalla etapa 4 'Confirmacion'")
    public void TC0206BSJ() {
        try {
            testCaseData.cargaDeDatosTest(uCuyo2021);
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
    @DisplayName("TC-0071-BSJ: RECARGAS - Realizar recarga a Transporte Publico")
    public void TC0071BSJ(){
        try {
            //PRECONDICION
            testCaseData.cargaDeDatosTest(uSanjuan_135);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            recargasSteps.consultaServiciosAdheridosAPI(SUBE_ID,"6061267159402278","1012");
            recargasSteps.realizarAdhesionAPI(SUBE_ID,"6061267159402278", "JUAN","1012");
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
            recargasSteps.seleccionarDropdownGenerico("Caja de Ahorro $ 90189348271");
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
    @DisplayName("TC-0072-BSJ: Recargas - Adhesión para recarga")
    public void TC0072BSJ() {
        try {
            //PRECONDICION
            testCaseData.cargaDeDatosTest(uGdrio_12);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            recargasSteps.consultaServiciosAdheridosAPI(SUBE_ID,"6061267195495203","1015");
            //TEST
            menuLateralSteps.clickRecargas();
            recargasSteps.clickCardTransportePublico();
            recargasSteps.clickEmpresa();
            recargasSteps.seleccionarDropdownGenerico("SUBE");
            recargasSteps.clickSeleccionarNumero();
            recargasSteps.seleccionarDropdownGenerico("Nuevo");
            recargasSteps.setInputNumero("6061267195495203");
            recargasSteps.setInputPerteneceA("TestAutomation");
            dobleFactorSteps.codigoSegundoFactor();
            ticketSteps.validaNotificacionExitosaGenerico("Transaccion realizada con exito");
            //POSTCONDICION
            recargasSteps.eliminarAdhesaionesdeServicioAPI(SUBE_ID,"6061267195495203","TestAutomation","1015");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    //@Test // FALTA DE DATOS  - APAGADO POR BUG
    @DisplayName("TC-12955-BSJ: Realizar recarga a Tarjeta Recargable/Prepagada")
    public void TC12955BSJ() {
        try {
            testCaseData.cargaDeDatosTest(uSanJuan_357);
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
    @DisplayName("TC-12956-BSJ: RECARGAS - Eliminar adhesión")
    public void TC12956BSJ() {
        try {
            //PRECONDICION
            testCaseData.cargaDeDatosTest(uSanJuan_357);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            recargasSteps.realizarAdhesionAPI(CLARO_ID,"5557504183", "TC12956","2008");
            //CASO
            menuLateralSteps.clickRecargas();
            recargasSteps.clickAdhesiones();
            recargasSteps.clickBotonSeleccionar();
            nuevoPagoSteps.seleccionarDropdownGenerico("Celular");
            recargasSteps.clickBotonSeleccionar();
            recargasSteps.seleccionarDropdownGenerico("CLARO");
            calendario.clickBuscar();
            recargasSteps.clickIconoEliminarAdhesion("TC12956");
            recargasSteps.clickConfirmarGenerico();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    //@Test // SE APAGA EL CASO POR BUG [ARPETEREVO-10513] UAT V75.0 BERSA-RECARGAS-"No pudimos procesar la operación"
    @DisplayName("TC-12952-BSJ: RECARGAS - Realizar recarga a Telefonía Celular")
    public void TC12952BSJ() {
        try {
            //PRECONDICION
            testCaseData.cargaDeDatosTest(uGdrio_12);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            recargasSteps.consultaServiciosAdheridosAPI(CLARO_ID,"5557504183","1015");
            //TEST
            menuLateralSteps.clickRecargas();
            recargasSteps.clickCardTelefoniaCelular();
            recargasSteps.clickEmpresa();
            recargasSteps.seleccionarDropdownGenerico("CLARO");
            recargasSteps.clickSeleccionarNumero();
            recargasSteps.seleccionarDropdownGenerico("Nuevo");
            recargasSteps.setInputNumero("5557504183");
            recargasSteps.setInputPerteneceA("Juan");
            recargasSteps.clickSelectorMonto();
            recargasSteps.seleccionarDropdownGenerico("$500.00");
            dobleFactorSteps.clickSms();
            dobleFactorSteps.codigoSegundoFactor();
            recargasSteps.clickAgregar();
            recargasSteps.clickDesplegableDebito();
            recargasSteps.seleccionarDropdownGenerico("7945");
            recargasSteps.clickContinuar();
            recargasSteps.clickConfirmarGenerico();
            recargasSteps.verificarDatosRecargaTelefoniaCelular("CLARO","5557504183","$500.00","Tu solicitud se realizo con exito");
            //POSTCONDICION ELIMINAR ADHESION
            recargasSteps.eliminarAdhesaionesdeServicioAPI(CLARO_ID,"5557504183","Juan","1015");

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    //@Test // FALTA DATOS
    @DisplayName("TC-12954-BSJ: RECARGAS - Realizar recarga a Billetera/Serv. Prepago")
    public void TC12954BSJ() {
        try {
            //CASO
            testCaseData.cargaDeDatosTest(uSanJuan_357);
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
    @DisplayName("TC-12928-BSJ: SEGUROS - Alta de seguros  - Acceso por Seguro - Menú lateral - Solicitud  y validación de pantalla")
    public void TC12928BSJ() {
        try {
            testCaseData.cargaDeDatosTest(uSanjuan_135);
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
    @DisplayName("TC-0032-BSJ: Consulta de Tarjetas de Crédito - Visa")
    public void TC0032BSJ() {
        try {
            testCaseData.cargaDeDatosTest(uGdrio_12);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.clickVerMasXnroDeTarjeta("TC VISA");
            tarjetasDeCreditoSteps.verificarTituloVisa("TC VISA");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test //OK
    @DisplayName("TC-0033-BSJ: Consulta de Tarjetas de Crédito - Mastercard")
    public void TC0033BSJ() {
        try {
            //PRECONDICION
            testCaseData.cargaDeDatosTest(uCarmend);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            //TEST
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.clickVerMasXnroDeTarjeta("2324");
            tarjetasDeCreditoSteps.verificarTituloMastercard();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0162-BSJ: Tarjeta de Crédito - Denunciar robo o extravío de Tarjeta de Crédito - WEB.")
    public void TC0162BSJ() {
        try {
            testCaseData.cargaDeDatosTest(uGdrio_12);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.validarTituloPageGenerico("Tarjetas de cr");
            tarjetasDeCreditoSteps.clickVerMasXnroDeTarjeta("TC VISA");
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
    @DisplayName("TC-0165-BSJ: PAGOS - Pagar Tarjeta de Crédito Master - Otro importe")
    public void TC0165BSJ() {
        try {
            testCaseData.cargaDeDatosTest(uSanjuan_135);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.clickVerMasXnroDeTarjeta("3545");
            tarjetasDeCreditoSteps.clickMasOpcionesGenerico();
            tarjetasDeCreditoSteps.clickPagarTarjeta();
            tarjetasDeCreditoSteps.clickComboCuentaDebito();
            tarjetasDeCreditoSteps.seleccionarDropdownGenerico("8271");
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
    @DisplayName("TC-0167-BSJ: Tarjeta de Crédito - Consultar último resumen de una Tarjeta de Crédito - WEB.")
    public void TC0167BSJ() {
        try {
            testCaseData.cargaDeDatosTest(uSanjuan_135);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.clickVerMasXnroDeTarjeta("3545");
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
    @DisplayName("TC-12967-BSJ: PAGOS - Pagar Tarjeta de Crédito Visa - Otro importe")
    public void TC12967BSJ() {
        try {
            testCaseData.cargaDeDatosTest(uSanjuan_135);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.clickVerMasXnroDeTarjeta("3545");
            botonesGenericosSteps.clickMasOpcionesGenerico();
            tarjetasDeCreditoSteps.clickPagarTarjeta();
            tarjetasDeCreditoSteps.seleccionarCuentaDebito("Caja de Ahorro $ 90189348271");
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
    //@Test //SE APAGA POR FALTA DE USUARIO HABILITADO PARA LA OPERATORIA
    @DisplayName("TC-0068-BSJ: Activación de tarjeta de débito - Validación de completitud de pantalla")
    public void TC0068BSJ() {
        try {
            //PRECONDICION
            testCaseData.cargaDeDatosTest(uCarmend);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            //TEST
            menuLateralSteps.clickTarjetaDeDebito();
            tarjetasDeDebitoSteps.validarTituloPageGenerico("Tarjetas de d");
            tarjetasDeDebitoSteps.clickBotonVerMas_Activar("8004");
            tarjetasDeDebitoSteps.validarTituloPageGenerico("Activaci");
            tarjetasDeDebitoSteps.validarTituloPageGenerico("Visa D");
            tarjetasDeDebitoSteps.validacionPantallaActivacionTarjeta();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    //@Test Se comenta el caso ya que no se cuenta con el datos - Gestion de datos
    @DisplayName("TC-0105-BSJ: Tarjeta de Débito - Consulta de compras")
    public void TC0105BSJ() {
        try {
            testCaseData.cargaDeDatosTest(uSanJuan_357);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickTarjetaDeDebito();
            tarjetasDeDebitoSteps.validarTituloPageGenerico("Tarjetas de d");
            tarjetasDeDebitoSteps.clickBotonVerMas_Activar("2008");
            tarjetasDeDebitoSteps.clickMasOpcionesGenerico();
            tarjetasDeDebitoSteps.clickConsultarCompras();
            tarjetasDeDebitoSteps.validarTituloPageGenerico("Consulta de compras en comercios");
            consultaDeComprasSteps.clickDesplegableCuenta();
            tarjetasDeDebitoSteps.seleccionarDropdownGenerico("0528");
            consultaDeComprasSteps.clickFechaDesde();
            calendarioSteps.retrocedeMesYAnioEspecifico("octubre 2023");
            calendarioSteps.clickEnDiaXParametro("1");
            calendarioSteps.clickBuscar();
            consultaDeComprasSteps.validarTablaConsultaCompras();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    //CASO DE LA CLASE TRANSFERENCIA
    // @Test //API
    @DisplayName("TC-0008-BSJ: Transferencia Intrabancaria en Pesos - Propias (del mismo banco) Cargada Manualmente - Programada en Fecha Futura Botón Volver")
    public void TC0008BSJ() {
        try {
            testCaseData.cargaDeDatosTest(uGdrio_12);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickTransferencias("","CA $ 15005004165");
            transferenciasInmediatas.seleccionarTipoTransferencia("Cuenta Propia");
            transferenciasInmediatas.seleccionarCuentaDestinoCuentasPropias("CA $ 15008057945");
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
    // @Test //API
    @DisplayName("TC-0014-BSJ: Generar CREDIN - Cuenta propia $ (mismo Banco u otro) - Mas de una cuenta Debito - CBU - Segundo factor por SMS")
    public void TC0014BSJ() {
        try {
            testCaseData.cargaDeDatosTest(uSanjuan_135);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickTransferencias("","");
            transferenciasSteps.clickMasOpcionesGenerico();
            transferenciasSteps.clickConsultarCredin();
            consultaCredinSteps.clickGenerarCredin();
            generarCredinSteps.clickCuentaDebito();
            generarCredinSteps.seleccionarDropdownGenerico("Caja de Ahorro $ 90189348271");
            generarCredinSteps.clickCBU();
            generarCredinSteps.setCampoAliasCbu("0450500602800050041657");
            generarCredinSteps.clickSelectorConcepto();
            generarCredinSteps.seleccionarDropdownGenerico("Alquileres");
            generarCredinSteps.setImporteGenerico("1");
            generarCredinSteps.setCampoDescripcion("TestAutomation");
            generarCredinSteps.clickContinuarGenerico();
            generarCredinSteps.acpetarTyCDobleFactor();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    // @Test //API
    @DisplayName("TC-0139-BSJ: Transferencias Inmediatas - Entre cuentas propias - Flujo completo- Validar ticket con exito")
    public void TC0139BSJ() {
        try {
            testCaseData.cargaDeDatosTest(uGdrio_12);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickTransferencias("","CA $ 15005004165");
            transferenciasInmediatas.seleccionarTipoTransferencia("Cuenta Propia");
            transferenciasInmediatas.seleccionarCuentaDestinoCuentasPropias("CA $ 15008057945");
            transferenciasInmediatas.seleccionarCuentaOrigen("CA $ 15005004165");
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
    // @Test //API
    @DisplayName("TC-0145-BSJ: Transferencias - Añadir nuevo destinatario - Con cbu valido")
    public void TC0145BSJ() {
        try {
            testCaseData.cargaDeDatosTest(uGdrio_12);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("3300000620000987477051");
            //TEST
            menuLateralSteps.clickTransferencias("","CA $ 15005004165");
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
    // @Test //API
    @DisplayName("TC-12930-BSJ: TRANSFERENCIAS - A cuentas de terceros CBU - mismo banco - Pesos")
    public void TC12930BSJ() {
        try {
            //PRECONDICION
            testCaseData.cargaDeDatosTest(uGdrio_12);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("0450018601800093482711");
            //TEST
            menuLateralSteps.clickTransferencias("","CA $ 15005004165");
            transferenciasInmediatas.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatas.completarInputAliasCbu("0450018601800093482711");
            transferenciasInmediatas.clickBuscarGenerico();
            transferenciasInmediatas.botonSonCorrectos();
            transferenciasInmediatas.setImporteGenerico("100");
            transferenciasInmediatas.setDescripcion("TestAutomation");
            transferenciasInmediatas.clickBotonTransferirAhora();
            transferenciasInmediatas.clickConfirmarGenerico();
            dobleFactorSteps.codigoSegundoFactor();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            //POSTCONDICION
            UtilsGP.fixedWait(5000);
            transferenciasInmediatas.verificarDescuentoCuentaOrigenTransferencia();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    // @Test //API
    @DisplayName("TC-12932-BSJ: TRANSFERENCIAS - A cuentas de terceros ALIAS - mismo banco - Pesos")
    public void TC12932BSJ() {
        try {
            testCaseData.cargaDeDatosTest(uGdrio_12);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("CELTA.FLECO.PUERTA");

            menuLateralSteps.clickTransferencias("","CA $ 15005004165");
            transferenciasInmediatas.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatas.completarInputAliasCbu("CELTA.FLECO.PUERTA");
            transferenciasInmediatas.clickBuscarGenerico();
            transferenciasInmediatas.botonSonCorrectos();
            transferenciasInmediatas.setImporteGenerico("100");
            transferenciasInmediatas.setDescripcion("TestAutomation");
            transferenciasInmediatas.clickBotonTransferirAhora();
            transferenciasInmediatas.clickConfirmarGenerico();
            dobleFactorSteps.codigoSegundoFactor();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            //POSTCONDICION
            UtilsGP.fixedWait(5000);
            transferenciasInmediatas.verificarDescuentoCuentaOrigenTransferencia();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    // @Test //API
    @DisplayName("TC-12935-BSJ: Transferencias Programadas (Fecha especifica) CBU - terceros mismo banco  $ - NO AGENDADOS")
    public void TC12935BSJ() {
        try {
            testCaseData.cargaDeDatosTest(uGdrio_12);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("3860195203000041101110");
            //TEST
            menuLateralSteps.clickTransferencias("","CA $ 15005004165");
            transferenciasInmediatas.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatas.completarInputAliasCbu("0450500601800021234887");
            transferenciasInmediatas.clickBuscarGenerico();
            transferenciasInmediatas.botonSonCorrectos();
            transferenciasInmediatas.setImporteGenerico("100");
            transferenciasInmediatas.seleccionarConcepto("Alquileres");
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
    // @Test //API
    @DisplayName("TC-12969-BSJ: TRANSFERENCIAS - A cuentas de terceros CVU - otros bancos - Pesos")
    public void TC12969BSJ() {
        try {
            testCaseData.cargaDeDatosTest(uGdrio_12);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("0000254900000000115034");
            //TEST
            menuLateralSteps.clickTransferencias("","CA $ 15005004165");
            transferenciasInmediatas.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatas.completarInputAliasCbu("0000254900000000115034");
            transferenciasInmediatas.clickBuscarGenerico();
            transferenciasInmediatas.botonSonCorrectos();
            transferenciasInmediatas.setImporteGenerico("100");
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
    // @Test //API
    @DisplayName("TC-12981-BSJ: Transferencias - Eliminar destinatario de agenda")
    public void TC12981BSJ() {
        try {
            //PRECONDICION
            testCaseData.cargaDeDatosTest(uGdrio_12);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            agendaNuevoDestinatarioSteps.adherirNuevoDestinatarioCBU("3860010201000000171602","TC12981");
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
    // @Test //API
    @DisplayName("TC-12938-BSJ: TRANSFERENCIAS - Generar CREDIN - Dolares")
    public void TC12938BSJ() {
        try {
            testCaseData.cargaDeDatosTest(uSanjuan_135);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickTransferencias("","");
            transferenciasInmediatas.clickMasOpcionesGenerico();
            transferenciasSteps.clickConsultarCredin();
            consultaCredinSteps.clickGenerarCredin();
            generarCredinSteps.clickCuentaDebito();
            generarCredinSteps.seleccionarDropdownGenerico( "USD");
            generarCredinSteps.clickCBU();
            generarCredinSteps.setCampoAliasCbu("3300000640000008700092");
            generarCredinSteps.clickSelectorConcepto();
            generarCredinSteps.seleccionarDropdownGenerico( "Alquileres");
            generarCredinSteps.setImporteGenerico("1");
            generarCredinSteps.setCampoDescripcion("TestAutomation");
            generarCredinSteps.clickContinuarGenerico();
            generarCredinSteps.acpetarTyCDobleFactor();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    // @Test //API
    @DisplayName("TC-12936 - BSJ: TRANSFERENCIAS - Consulta de transferencias realizadas")
    public void TC12936BSJ() {
        try {
            //CASO
            testCaseData.cargaDeDatosTest(uSanjuan_135);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickTransferencias("","");
            transferenciasInmediatas.misTransferencias();
            transferenciasInmediatas.validarListadoTransferencias();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    // @Test //API
    @DisplayName("TC-12939-BSJ: TRANSFERENCIAS - Consultar CREDIN Originante")
    public void TC12939BSJ() {
        try {
            //Precondicion: Generar Credin
            testCaseData.cargaDeDatosTest(uGdrio_12);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            generarCredinSteps.generarCredinApi("0450500602800080579450","0450500601800008112711","1015");
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
    // @Test //API
    @DisplayName("TC-12940-BSJ: TRANSFERENCIAS - Consultar CREDIN destinatario")
    public void TC12940BSJ() {
        try {
            //Precondicion: Generar Credin
            loginSteps.loginForAPI(uSanjuan_135);
            generarCredinSteps.generarCredinApi("0450018601800093482711","0450500602800080579450","1012");
            //Test: Consulta de Credin
            testCaseData.cargaDeDatosTest(uGdrio_12);
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
            consultaCredinSteps.setCuitOriginante("27148696980");
            consultaCredinSteps.clickEstado();
            consultaCredinSteps.seleccionarDropdownGenerico( "Todos");
            consultaCredinSteps.clickBuscarGenerico();
            consultaCredinSteps.clickDetalleCredin();
            consultaCredinSteps.validacionDescripcionCredin("testAutomation");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    // @Test //API
    @DisplayName("TC-14250-BSJ: Transferencias CBU a cuentas propias otro banco $ - NO AGENDADOS")
    public void TC14250BSJ(){
        try{
            //CASO
            testCaseData.cargaDeDatosTest(uGdrio_12);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("3300000620000244825007");
            //TEST
            menuLateralSteps.clickTransferencias("","CA $ 15005004165");
            transferenciasInmediatas.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatas.completarInputAliasCbu("3300000620000244825007");
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
            UtilsGP.fixedWait(2000);
            transferenciasInmediatas.verificarDescuentoCuentaOrigenTransferencia();
        }catch (Exception ex){
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    // @Test //API
    @DisplayName("TC-0409-BSJ: TRANSFERENCIAS - terceros mismo banco  $ - AGENDADOS")
    public void TC0409BSJ(){
        try{
            //PRECONDICION
            testCaseData.cargaDeDatosTest(uGdrio_12);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            agendaNuevoDestinatarioSteps.adherirNuevoDestinatarioCBU("0450500602800055805281","TC0409BSJ");
            //TEST
            menuLateralSteps.clickTransferencias("", "CA $ 15005004165");
            transferenciasInmediatas.seleccionarTipoTransferencia("Contacto Agendado");
            transferenciasInmediatas.clickAgendaDestinatario();
            transferenciasInmediatas.transferirAunContacto("TC0409BSJ");
            transferenciasInmediatas.setImporteGenerico("1");
            transferenciasInmediatas.seleccionarConcepto("Alquileres");
            transferenciasInmediatas.clickBotonTransferirAhora();
            transferenciasInmediatas.clickConfirmarGenerico();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            //POSTCONDICION
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("0450500602800055805281");
            UtilsGP.fixedWait(4000);
            transferenciasInmediatas.verificarDescuentoCuentaOrigenTransferencia();

        }catch (Exception ex){
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    // @Test //API
    @DisplayName("TC-0407-BSJ: Transferencias ALIAS a cuentas propias otro banco $ - NO AGENDADOS")
    public void TC0407BSJ(){
        try{
            //PRECONDICION
            testCaseData.cargaDeDatosTest(uGdrio_12); //SE USABA TRANSPULL, SE SOLICITA NUEVO USUARIO
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("CATRE.PESO.cambio1");
            //TEST
            menuLateralSteps.clickTransferencias("","CA $ 15005004165");
            transferenciasInmediatas.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatas.completarInputAliasCbu("CATRE.PESO.cambio1");
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
            UtilsGP.fixedWait(5000);
            transferenciasInmediatas.verificarDescuentoCuentaOrigenTransferencia();
        }catch (Exception ex){
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    // @Test //API
    @DisplayName("TC-0463-BSJ: Transferencias Programadas (Fecha especifica) ALIAS a cuentas propias otro banco $ - NO AGENDADOS")
    public void TC0463BSJ() {
        try {
            //TEST
            testCaseData.cargaDeDatosTest(uGdrio_12);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("CATRE.PESO.cambio1");
            //TEST
            menuLateralSteps.clickTransferencias("","CA $ 15005004165");
            transferenciasInmediatas.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatas.completarInputAliasCbu("CATRE.PESO.cambio1");
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
    // @Test //API
    @DisplayName("TC-0473-BSJ: Transferencias Programadas (Fecha especifica) CBU - terceros otros bancos $ - NO AGENDADOS")
    public void TC0473BSJ() {
        try {
            //TEST
            testCaseData.cargaDeDatosTest(uSanjuan_135);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("3860062103000031463202");
            //TEST
            menuLateralSteps.clickTransferencias("","CA $ 90056723505");
            transferenciasInmediatas.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatas.completarInputAliasCbu("3860062103000031463202");
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
    // @Test //API
    @DisplayName("TC-0411-BSJ: Transferencias - terceros otros bancos $ - AGENDADOS")
    public void TC0411BSJ(){
        try{
            //PRECONDICION
            testCaseData.cargaDeDatosTest(uGdrio_12);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            agendaNuevoDestinatarioSteps.adherirNuevoDestinatarioCBU("3860020103000000522105","TC0411BERSA");
            //TEST
            menuLateralSteps.clickTransferencias("","CA $ 15005004165");
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
            UtilsGP.fixedWait(3000);
            transferenciasInmediatas.verificarDescuentoCuentaOrigenTransferencia();
        }catch (Exception ex){
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    // @Test //API
    @DisplayName("TC-0427-BSJ: Transferencias CVU - terceros $ AGENDADOS")
    public void TC0427BSJ(){
        try{
            //PRECONDICION
            testCaseData.cargaDeDatosTest(uGdrio_12);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            agendaNuevoDestinatarioSteps.adherirNuevoDestinatarioCBU("0000004800300000000313","TC0427BSJ");
            //TEST
            menuLateralSteps.clickTransferencias("","CA $ 15005004165");
            transferenciasInmediatas.seleccionarTipoTransferencia("Contacto Agendado");
            transferenciasInmediatas.clickAgendaDestinatario();
            transferenciasInmediatas.transferirAunContacto("TC0427BSJ");
            transferenciasInmediatas.setImporteGenerico("1");
            transferenciasInmediatas.seleccionarConcepto("Alquileres");
            transferenciasInmediatas.setDescripcion("TestAutomation");
            transferenciasInmediatas.clickBotonTransferirAhora();
            transferenciasInmediatas.clickConfirmarGenerico();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            //POSTCONDICION
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("0000004800300000000313");
            UtilsGP.fixedWait(4000);
            transferenciasInmediatas.verificarDescuentoCuentaOrigenTransferencia();
        }catch (Exception ex){
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    // @Test //API
    @DisplayName("TC-0429-BSJ: Transferencias ALIAS de CVU - terceros $ NO AGENDADOS")
    public void TC0429BSJ(){
        try{
            //PRECONDICION
            testCaseData.cargaDeDatosTest(uGdrio_12);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("BOTE.FILA.MITO");
            //TEST
            menuLateralSteps.clickTransferencias("","CA $ 15005004165");
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
            UtilsGP.fixedWait(4000);
            transferenciasInmediatas.verificarDescuentoCuentaOrigenTransferencia();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    // @Test //API
    @DisplayName("TC-0465-BSJ: Transferencias Programadas (Fecha especifica) - terceros mismo banco  $ - AGENDADOS")
    public void TC0465BSJ(){
        try{
            //PRECONDICION
            testCaseData.cargaDeDatosTest(uCuyo2023);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            agendaNuevoDestinatarioSteps.adherirNuevoDestinatarioCBU("0450500602800080579450","TC0465BSJ");
            //TEST
            menuLateralSteps.clickTransferencias("","CA $ 10075874841");
            transferenciasInmediatas.seleccionarTipoTransferencia("Contacto Agendado");
            transferenciasInmediatas.clickAgendaDestinatario();
            transferenciasInmediatas.transferirAunContacto("TC0465BSJ");
            transferenciasInmediatas.setImporteGenerico("1");
            transferenciasInmediatas.seleccionarConcepto("Alquileres");
            transferenciasInmediatas.setDescripcion("TestAutomation");
            transferenciasInmediatas.transferenciaProgramadaPara();
            transferenciasInmediatas.seleccionFechaFutura("Especifica");
            transferenciasInmediatas.clickContinuarGenerico();
            transferenciasInmediatas.clickConfirmarGenerico();
            ticketSteps.validarTicket("Tu transaccion ha sido agendada");
            //POSTCONDICION
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("0450500602800080579450");

        }catch (Exception ex){
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    // @Test //API
    @DisplayName("TC-0477-BSJ: Transferencias Programadas (Fecha especifica) ALIAS - terceros mismo banco  $ - NO AGENDADOS")
    public void TC0477BSJ() {
        try {
            //TEST
            testCaseData.cargaDeDatosTest(uSanjuan_135);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("LARGO.CELTA.PAREJA");
            //TEST
            menuLateralSteps.clickTransferencias("","CA $ 90056723505");
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
        }catch (Exception ex){
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    // @Test //API
    @DisplayName("TC-0467-BSJ: Transferencias Programadas (Fecha especifica) - terceros otros bancos $ - AGENDADOS")
    public void TC0467BSJ(){
        try{
            //PRECONDICION
            testCaseData.cargaDeDatosTest(uGdrio_12);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            agendaNuevoDestinatarioSteps.adherirNuevoDestinatarioCBU("3860020103000000522105","TC0467BSJ");
            //TEST
            menuLateralSteps.clickTransferencias("","CA $ 15005004165");
            transferenciasInmediatas.seleccionarTipoTransferencia("Contacto Agendado");
            transferenciasInmediatas.clickAgendaDestinatario();
            transferenciasInmediatas.transferirAunContacto("TC0467BSJ");
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
    // @Test //API
    @DisplayName("TC-0483-BSJ: Transferencias Programadas (Fecha especifica) CVU - terceros $ AGENDADOS")
    public void TC0483BSJ(){
        try{
            //PRECONDICION
            testCaseData.cargaDeDatosTest(uGdrio_12);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            agendaNuevoDestinatarioSteps.adherirNuevoDestinatarioCBU("0000001700300000000313","TC0483BSJ");
            //TEST
            menuLateralSteps.clickTransferencias("","CA $ 15005004165");
            transferenciasInmediatas.seleccionarTipoTransferencia("Contacto Agendado");
            transferenciasInmediatas.clickAgendaDestinatario();
            transferenciasInmediatas.transferirAunContacto("TC0483BSJ");
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
    // @Test //API
    @DisplayName("TC-0484-BSJ: Transferencias Programadas (Fecha especifica) CVU - terceros $ NO AGENDADOS")
    public void TC0484BSJ() {
        try {
            testCaseData.cargaDeDatosTest(uGdrio_12);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("0000001700300000000313");
            //TEST
            menuLateralSteps.clickTransferencias("","CA $ 15005004165");
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
    // @Test //API
    @DisplayName("TC-0485-BSJ: Transferencias Programadas (Fecha especifica) ALIAS de CVU - terceros $ NO AGENDADOS")
    public void TC0485BSJ() {
        try {
            testCaseData.cargaDeDatosTest(uGdrio_12);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("BOTE.FILA.MITO");
            //TEST
            menuLateralSteps.clickTransferencias("","CA $ 15005004165");
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
        if(utilsGP.estaPresente(botonesGenericosPage.getCodigoUps(),3) && botonesGenericosPage.getCodigoUps().getText().startsWith("#")) {
            String ups = botonesGenericosPage.getCodigoUps().getText();
            global.practia.jemeinsamstark.web.core.WebDriver.getInstance().navigate().to(System.getProperty("appUrl"));
            System.out.println("Se Espera que aparezca de nuevo el banco");
            loginSteps.waitGP();
            Assert.fail("UPS CODIGO : " + ups);
        }else{
            System.out.println("=================== NUEVO INICIO DE NAVEGADOR ==============================");
            global.practia.jemeinsamstark.web.core.WebDriver.getInstance().navigate().to(System.getProperty("appUrl"));
            System.out.println("Se Espera que aparezca de nuevo el banco");
            loginSteps.waitGP();
        }
    }
    @AfterClass
    public static void teardown() throws IOException, InterruptedException {
        global.practia.jemeinsamstark.web.core.WebDriver.closeDriver();
        cerrarConexion();
    }
}
