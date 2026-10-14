package GP.SantaFe.criticos;

import db.ConexionDB;
import io.qameta.allure.Allure;
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
    }

    //CASOS DE BONOS DE SALUD
    //@Test //correr para refactorizar USUARIO SIN PRECALIFIACION GESTION DE DATOS
    @DisplayName("TC- 0080-BSF - Bonos de salud- Comprar Bonos de salud - Ticket de transacción")
    public void TC0080BSF() {
        try {
            //PRECONDICION
            testCaseData.cargaDeDatosTest(uAbramcelia);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            bonos.realizarAdheciónAfiliadoAPI("6007","30379830");
            //TEST
            menuLateralSteps.clickBonosDeSalud();
            bonos.validarTituloPageGenerico("Compra de bonos de salud");
            bonos.clickDesplegableTarjetaDebito();
            bonos.keyDownToText("6007");
            bonos.clickDesplegableEnte();
            bonos.keyDownToText("IAPOS");
            bonos.clickDesplegableNumeroDeAfiliado();
            bonos.keyDownToText("30379830");
            bonos.clickAgregarBonos();
            bonos.clickDesplegableCuentaOrigen();
            bonos.keyDownToText("Caja de Ahorro $ 000000869803");
            bonos.clickContinuarGenerico();
            bonos.clickConfirmarGenerico();
            ticketSteps.clickDescargas();
            ticketSteps.clickDescargaBono();
            ticketSteps.validarDescarga("Ticket.pdf");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test //No se encuentran compras registradas - BONOS DE SALUD VA POR SOAP y CHANELS HB MHB
    @DisplayName("TC -0081 BSF - Bonos de salud-  Consultar compras")
    public void TC0081BSF() {
        try {
            testCaseData.cargaDeDatosTest(uAbramcelia);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickBonosDeSalud();
            bonos.validarTituloPageGenerico("Compra de bonos de salud");
            bonos.clickConsultarCompras();
            bonos.clickDesplegableTarjetaDebito();
            bonos.keyDownToText("6007");
            Thread.sleep(3000);
            bonos.clickDesplegableEnte();
            bonos.keyDownToText("IAPOS");
            bonos.clickDesplegableCanal();
            bonos.keyDownToText("HOMEBANKING");
            bonos.clickCalendarioDesde();
            calendario.clickMesAnterior(6);
            calendario.clickEnDiaXParametro("1");
            bonos.clickDesplegableNumeroDeAfiliado();
            bonos.keyDownXParametrosGenerico(0, "");
            calendario.clickBuscar();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    //CASOS DE ADELANTOS DE SUELDO
    //@Test  //DARAGO SIN  PRECALIFICACION - GESTION DE DATOS
    @DisplayName("TC-0223-BSF: Adelantos de Sueldo - Realizar solicitud de adelanto de sueldo  - Validar pantalla de confimacion de solicitud")
    public void TC0223BSF() {
        try {
            testCaseData.cargaDeDatosTest(uDagaroa);//dagaroa
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickCuentas();
            adelantosDeSueldoSteps.clickVerMasXnroCuenta("CA $ 001000102810");
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
    //@Test  //DARAGO SIN  PRECALIFICACION - GESTION DE DATOS
    @DisplayName("TC-12978-BSF: OFERTA - Adelanto de Sueldo - Acceso Directo")
    public void TC12978BSF() {
        try {
            testCaseData.cargaDeDatosTest(uDagaroa);//DAGAROA
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
    @DisplayName("TC-0287-BSF: Consentimiento Billetera - Aprobar consentimiento")
    public void TC0287BSF() {
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
    @Test //CELULA CUENTAS
    @DisplayName("TC-0006-BSF: Adherir Cuenta  - Nuevo destinatario - por Alias - Descarga e impresión del PDF")
    public void TC0006BSF()  {
        try {
            //PRECONDICION
            testCaseData.cargaDeDatosTest(uTranspull4);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("VOLPATO.PESOS.BSF");
            //TEST
            menuLateralSteps.clickCuentas();
            cuentasSteps.clickMasOpcionesGenerico();
            cuentasSteps.clickAgendaDeDestinararios();
            agendaNuevoDestinatarioSteps.clickAdherirNuevoDestinatario();
            agendaNuevoDestinatarioSteps.setInputCbuAlias("VOLPATO.PESOS.BSF");
            agendaNuevoDestinatarioSteps.clickBuscar();
            agendaNuevoDestinatarioSteps.validarDatosCuenta("3300000620000125788076", "VOLPATO, MONICA LILIANA A", "27162670390");
            agendaNuevoDestinatarioSteps.setPerteneceA("PruebaAutomation");
            transferenciasInmediatas.botonSonCorrectos();
            dobleFactorSteps.codigoSegundoFactor();
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
    @Test
    @DisplayName("TC-0027-BSF: Generar Orden de Extracción_Con Saldo disponible en Cuenta.")
    public void TC0027BSF() {
        try {
            testCaseData.cargaDeDatosTest(uPrueba2734);//DARAGOA
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickCuentas();
            cuentasSteps.clickOrdenesDeExtraccion();
            ordenDeExtraccionConsulta_generarSteps.clickGenerarOrdenDeExtraccion();
            ordenDeExtraccionConsulta_generarSteps.validarTituloPageGenerico("Generar orden de extracc");
            ordenDeExtraccionConsulta_generarSteps.setDni("2500001");
            ordenDeExtraccionConsulta_generarSteps.setImporteGenerico("100");
            ordenDeExtraccionConsulta_generarSteps.setReferencia("T27");
            ordenDeExtraccionConsulta_generarSteps.clickBotonAgregar();
            ordenDeExtraccionConsulta_generarSteps.clickContinuarGenerico();
            dobleFactorSteps.codigoSegundoFactorOtroMetodo();
            ordenDeExtraccionConsulta_generarSteps.validarTituloPageGenerico("Destinatario");
            ordenDeExtraccionConsulta_generarSteps.validarTituloPageGenerico("DNI 2500001");
            ordenDeExtraccionConsulta_generarSteps.clickIconoComprobante();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            ticketSteps.clickVolverGenerico();
            ordenDeExtraccionConsulta_generarSteps.validarTituloPageGenerico("Destinatario");
            ordenDeExtraccionConsulta_generarSteps.validarTituloPageGenerico("DNI 2500001");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0040-BSF: Acceder a Consulta de movimientos de cuenta")
    public void TC0040BSF() {
        try {
            testCaseData.cargaDeDatosTest(uTranspull4);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickCuentas();
            cuentasSteps.clickVerMasXnroCuenta("CC $ 000021333202");
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
    @DisplayName("TC-0025-BSF: Echeq - Completitud de pantalla con filtro: Estado = Todos")
    public void TC0025BSF() {
        try {
            testCaseData.cargaDeDatosTest(uPerezR);
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
    @DisplayName("TC-0089-BSF: Cuotifícalo - Cuotificar compras con TD")
    public void TC0089BSF() {
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
    //@Test //PRECALIFICACION EN BSF - SE APAGA PORQUE DURA 24 la precalificacion desde it - SE CORRE EN UNISYS
    @DisplayName("TC-0009-BSF: CPA / VTA de Dolares  - Cliente Vende 10 Dólares y Acredita en CA en Pesos")
    public void TC0009BSF() {
        try {
            testCaseData.cargaDeDatosTest(uPrueba12);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickCompraVentaDeDolares();
            compraVentaDeDolaresSteps.validarTituloPageGenerico("Compra venta de d");
            compraVentaDeDolaresSteps.clickTipoDeOperacion();
            compraVentaDeDolaresSteps.keyDownToText("Vender");
            compraVentaDeDolaresSteps.clickCuentaOrigen();
            compraVentaDeDolaresSteps.keyDownToText( "USD");
            compraVentaDeDolaresSteps.clickCuentaDestino();
            compraVentaDeDolaresSteps.keyDownToText("6309");
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
    //@Test
    @DisplayName("TC-0069-BSF: Compra de moneda extranjera (USD)")
    public void TC0069BSF() {
        try {
            testCaseData.cargaDeDatosTest(uTester1);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickCompraVentaDeDolares();
            compraVentaDeDolaresSteps.validarTituloPageGenerico("Compra venta de d");
            compraVentaDeDolaresSteps.clickTipoDeOperacion();
            compraVentaDeDolaresSteps.keyDownToText( "Comprar");
            compraVentaDeDolaresSteps.clickCuentaOrigen();
            compraVentaDeDolaresSteps.keyDownToText( "Caja de Ahorro $ 000077914707");
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
    @DisplayName("TC- 0173- BSF-  Inversiones- Alta cuenta comitente- Solicitar cuenta comitente  - Validar pantalla etapa '5 Confirmacion'")
    public void TC0173BSF() {
        try {
            testCaseData.cargaDeDatosTest(uSantafe140);//DATO OK
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
            UtilsGP.fixedWait(5000);
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
    @DisplayName("TC-0111-BSF: Suscribir FCI - Validación de pantalla de suscripción de FCI")
    public void TC0111BSF() {
        try {
            testCaseData.cargaDeDatosTest(uRuattar);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            fondoComunInversionSteps.clickIconoAccionesDetalleFCI("A-DELTA PESOS");
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
    @Test
    @DisplayName("TC-0113-BSF: FCI - Rescatar - Importe Parcial - Validación de pantalla")
    public void TC0113BSF() {
        try {
            testCaseData.cargaDeDatosTest(uRuattar);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            fondoComunInversionSteps.clickIconoAccionesDetalleFCI("A-DELTA PESOS");
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
    @DisplayName("TC-0042-BSF: Alta de PF Tradicional en $ a 30 días")
    public void TC0042BSF() {
        try {
            testCaseData.cargaDeDatosTest(uTranspull1);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickPlazosFijos();
            plazosFijosSteps.clickSolicitarPlazoFijo();
            solicitarPlazoFijoSteps.clickSelectorTipoPF();
            solicitarPlazoFijoSteps.keyDownToText("Plazo Fijo Tradicional en Pesos");
            solicitarPlazoFijoSteps.setImporteGenerico("120000");
            solicitarPlazoFijoSteps.setPlazoEnDias("30");
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
    @Test
    @DisplayName("TC-0043-BSF: Alta de PF UVA $ 350 días ")
    public void TC0043BSF() {
        try {
            testCaseData.cargaDeDatosTest(uPerezR);
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
    @Test
    @DisplayName("TC-12974-BSF INVERSIONES - Plazo Fijo UVA Precancelable")
    public void TC12974BSF() {
        try {
            testCaseData.cargaDeDatosTest(uPerezR);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickPlazosFijos();
            plazosFijosSteps.clickSolicitarPlazoFijo();
            solicitarPlazoFijoSteps.clickSelectorTipoPF();
            solicitarPlazoFijoSteps.keyDownToText("Plazo Fijo UVA Precancelable");
            solicitarPlazoFijoSteps.setImporteGenerico("15000");
            solicitarPlazoFijoSteps.setPlazoEnDias("350");
            solicitarPlazoFijoSteps.clickSelectorCuentaDebito();
            solicitarPlazoFijoSteps.keyDownXParametrosGenerico(0, "");
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
    @DisplayName("TC-12973-BSF: INVERSIONES - Plazo Fijo Tradicional en Dolares")
    public void TC12973BSF() {
        try {
            testCaseData.cargaDeDatosTest(uTranspull1);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickPlazosFijos();
            plazosFijosSteps.clickSolicitarPlazoFijo();
            solicitarPlazoFijoSteps.clickSelectorTipoPF();
            solicitarPlazoFijoSteps.keyDownToText("Plazo Fijo Tradicional en D");
            solicitarPlazoFijoSteps.setImporteGenerico("1000");
            solicitarPlazoFijoSteps.setPlazoEnDias("30");
            solicitarPlazoFijoSteps.clickSelectorCuentaDebito();
            solicitarPlazoFijoSteps.keyDownToText("2609");
            solicitarPlazoFijoSteps.clickSelectorAccionVencimiento();
            solicitarPlazoFijoSteps.keyDownToText( "al vencimiento");
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
    @DisplayName("TC-0048-BSF: Recupero de Usuario - Recuperar Usuario")
    public void TC0048BSF(){
        try {
            testCaseData.cargaDeDatosTest(uRolamario);
            String randomStr = RandomStringUtils.randomAlphabetic(4);
            //TEST
            loginSteps.waitGP();
            loginSteps.clickIngresarStartPage();
            loginSteps.setUsuario(testCaseData.usuario);
            loginSteps.clickRecuperarUsuario();
            //loginSteps.setNroDocumento("08001004");
            loginSteps.clickContinuarGenerico();
            loginSteps.validarTituloPageGenerico("Tu solicitud");
            loginSteps.validarTituloPageGenerico("Enviamos tu usuario a");
            loginSteps.loginForAPI(testCaseData.usuario);
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
    //CASOS DE LA CLASE PAGOS
    //SECCION POR API SE COMENTAN TODOS LOS CASOS
    //@Test
    @DisplayName("TC-0012-BSF: DEBIN Comprador - Consulta DEBINes históricos - Listado")
    public void TC0012BSF() {
        try {
            testCaseData.cargaDeDatosTest(uAbramcelia);
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
    //@Test
    @DisplayName("TC-0013-BSF: Generar orden DEBIN - Seleccionando CBU")
    public void TC0013BSF() {
        try {
            testCaseData.cargaDeDatosTest(uSantafe2024);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickPagos();
            pagosSteps.clickMasOpcionesGenerico();
            pagosSteps.clickDebinVendedor();
            debinVendedorSteps.clickOrdenDebin();
            debinVendedorSteps.clickCuentaDestinoDesplegable();
            debinVendedorSteps.seleccionarDropdownGenerico( "5704");
            debinVendedorSteps.setImporteGenerico("1");
            debinVendedorSteps.clickCbu();
            debinVendedorSteps.setCbuOAlias("3300000620000125788076");
            debinVendedorSteps.clickBuscar();
            debinVendedorSteps.clickNombre();
            debinVendedorSteps.seleccionarDropdownGenerico("VOLPATO, MONICA LILIANA A");
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
    //@Test
    @DisplayName("TC-0015-BSF: Adherir DEBIN recurrente (Pesos) -  2do factor por SMS - Descargar - Boton Volver")
    public void TC0015BSF() {
        try {
            testCaseData.cargaDeDatosTest(uAbramcelia);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickPagos();
            pagosSteps.clickMasOpcionesGenerico();
            pagosSteps.clickDebinComprador();
            debinCompradorSteps.clickAdherirRecurrencia();
            adherirRecurrenciaSteps.clickSelectorCuentaDebito();
            adherirRecurrenciaSteps.seleccionarDropdownGenerico( "9803");
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
    //@Test//OK
    @DisplayName("TC-0074-BSF: ADHERIR NUEVO PAGO O SERVICIO")
    public void TC0074BSF() {
        try {
            testCaseData.cargaDeDatosTest(uAbramcelia);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickPagos();
            pagosSteps.clickServiciosAdheridos();
            utilsGP.fixedWait(3000);
            pagosSteps.clickSelectorTarjetaDebito();
            pagosSteps.seleccionarDropdownGenerico("8003");
            nuevoPagoSteps.clickCardServicios();
            adherirServicioSteps.validarTituloPageGenerico("Adherir servicio");
            adherirServicioSteps.clickDesplegableRubro();
            adherirServicioSteps.seleccionarDropdownGenerico("Cobranzas para Empresas y Comercios");
            adherirServicioSteps.clickDesplegableEmpresa();
            adherirServicioSteps.seleccionarDropdownGenerico( "Mercado Libre");
            adherirServicioSteps.setCodigoDePago("00449535691");
            adherirServicioSteps.setearDescripciondeAdhesion("TestAutomation");
            adherirServicioSteps.clickContinuarGenerico();
            adherirServicioSteps.clickConfirmarGenerico();
            dobleFactorSteps.codigoSegundoFactor();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            //POSTCONDICION
            pagosSteps.eliminarServicioAdheridoAPI("Mercado Libre","TestAutomation","8003", "00449535691");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    //@Test // validar con equipo manual - - OK
    @DisplayName("TC-0070-BSF: Aceptación de DEBIN")
    public void TC0070BSF(){
        try {
            //PRECONDICION
            testCaseData.cargaDeDatosTest(uAbramcelia);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            pagosSteps.generacionDebinApi("3300000620000035540021","CC $ 000000869901","20025000014");
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
    //@Test
    @DisplayName("TC-12964-BSF: PAGOS - Pagar Impuesto/Servicio Nuevo Pago (Sin Base)")
    public void TC12964BSF() {
        try {
            //PRECONDICION
            testCaseData.cargaDeDatosTest(uBenicio2022);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            //TEST
            menuLateralSteps.clickPagos();
            UtilsGP.fixedWait(3000);
            pagosSteps.clickNuevoPago();
            nuevoPagoSteps.clickCardServicios();
            nuevoPagoSteps.clickInputRubro();
            nuevoPagoSteps.seleccionarDropdownGenerico("Cobranzas para Empresas y Comercios");
            nuevoPagoSteps.clickInputEmpresa();
            nuevoPagoSteps.seleccionarDropdownGenerico("Mercado Libre");
            nuevoPagoSteps.validarMensajeCodigoPago();
            adherirServicioSteps.setCodigoDePago("00449535691");
            nuevoPagoSteps.clickCheckBoxServicio();
            dobleFactorSteps.codigoSegundoFactor();
            botonesGenericosSteps.clickContinuarGenerico();
            nuevoPagoSteps.clickPagandoDesdeDesplegable();
            debinVendedorSteps.seleccionarDropdownGenerico("0001");
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
    //@Test//OK
    @DisplayName("TC-12966-BSF: PAGOS - Pagar Impuesto/Servicio Eliminar servicio")
    public void TC12966BSF() {
        try {
            //PRECONDICIÓN
            testCaseData.cargaDeDatosTest(uAbramcelia);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            pagosSteps.adherirServicioApi("00449535691", "Cobranzas para Empresas y Comercios","Mercado Libre", "4002");
            //CASO
            menuLateralSteps.clickCuentas();
            menuLateralSteps.clickPagos();
            pagosSteps.clickServiciosAdheridos();
            serviciosAdheridosSteps.buscarPorDescripcion("TestAutomation1");
            serviciosAdheridosSteps.clickBuscarGenerico();
            serviciosAdheridosSteps.eliminarServicioAdherido();
            serviciosAdheridosSteps.clickBotonDesvincular();
            ticketSteps.validaNotificacionExitosaGenerico("Desvinculaste a Mercado Libre");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    //CASOS DE PERFIL
    @Test
    @DisplayName("TC-0001-BSF: Cambio de Teléfono - Cambio de teléfono exitoso")
    public void TC0001BSF() {
        try {
            testCaseData.cargaDeDatosTest(uAgustina2014);
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
   @Test
    @DisplayName("TC-0002-BSF: Cambio de Correo Electrónico - Cambio de correo electrónico exitoso")
    public void TC0002BSF() {
        try {
            String nuevoMail = "practiagp@gmail.com";

            testCaseData.cargaDeDatosTest(uPerezR);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuSuperiorSteps.clickPerfil();
            perfilSteps.clickMiPerfil();
            perfilSteps.validarTituloPageGenerico("Mi Perfil");
            perfilSteps.clickModificarCorreo();
            modificarPerfilSteps.validarTituloPageGenerico("Modificar correo");
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
    @DisplayName("TC-0004-BSF: Cambio domicilio")
    public void TC0004BSF() {
        try {
            testCaseData.cargaDeDatosTest(uRuattar);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuSuperiorSteps.clickPerfil();
            perfilSteps.clickMiPerfil();
            perfilSteps.validarTituloPageGenerico("Mi Perfil");
            perfilSteps.clickModificarDomicilio();
            modificarPerfilSteps.validarTituloPageGenerico("Modificar Domicilio");
            modificarPerfilSteps.clickDomicilio();
            modificarPerfilSteps.keyDownXParametrosGenerico(1, "Modificar domicilio");
            modificarPerfilSteps.setCalle(modificarPerfilSteps.generarString(8, "abcdefghis"));
            modificarPerfilSteps.setNumero(generarString(2, "54454511"));
            modificarPerfilSteps.setPiso(generarString(1, "21556"));
            modificarPerfilSteps.setApartamento((generarString(1, "abcdefjh")));
            modificarPerfilSteps.clickProvincia();
            modificarPerfilSteps.keyDownToText("CORRIENTES");
            modificarPerfilSteps.clickLocalidad();
            modificarPerfilSteps.keyDownXParametrosGenerico(0, "LA CRUZ");
            modificarPerfilSteps.clickCodigoPostal();
            modificarPerfilSteps.keyDownXParametrosGenerico(0, "3346");
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
    @DisplayName("TC-0220-BSF: Prestamos- Prestamo para cancelar deudas - Validar pantalla etapa 5 'Confirmacion'")
    public void TC0220BSF() {
        try {
            testCaseData.cargaDeDatosTest(uRolamario);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickPrestamos();
            prestamosParaCancelarDeudasSteps.clickPrestamosParaCancelarDeudas();
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
    //CASOS DE LA CLASE PRESTAMO
    @Test //ok con santafe033 12/11/24
    @DisplayName("TC-0206-BSF: Prestamos - Nuevo prestamo - Validar pantalla etapa 4 'Confirmacion'")
    public void TC0206BSF() {
        try {
            testCaseData.cargaDeDatosTest(uSantafe033);
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
    @DisplayName("TC-0071-BSF: RECARGAS - Realizar recarga a Transporte Publico")
    public void TC0071BSF(){
        try {
            //PRECONDICION
            testCaseData.cargaDeDatosTest(uAbramcelia);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            recargasSteps.consultaServiciosAdheridosAPI(SUBE_ID,"6061267159402278","6007");
            recargasSteps.realizarAdhesionAPI(SUBE_ID,"6061267159402278", "JUAN","6007");
            //TEST
            menuLateralSteps.clickRecargas();
            recargasSteps.clickSelectorTarjetaDebito();
            consultaRecargasSteps.seleccionarDropdownGenerico("6007");
            recargasSteps.clickContinuar();
            recargasSteps.clickCardTransportePublico();
            recargasSteps.clickEmpresa();
            recargasSteps.seleccionarDropdownGenerico("SUBE");
            recargasSteps.clickSeleccionarNumero();
            recargasSteps.seleccionarDropdownGenerico("JUAN");
            recargasSteps.clickSelectorMonto();
            recargasSteps.seleccionarDropdownGenerico("$2,000.00");
            recargasSteps.clickAgregar();
            recargasSteps.clickDesplegableDebito();
            recargasSteps.seleccionarDropdownGenerico("Caja de Ahorro $ 000000869803");
            recargasSteps.clickContinuar();
            recargasSteps.clickConfirmarGenerico();
            Thread.sleep(1000);
            recargasSteps.clickIconoTicket();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0072-BSF: Recargas - Adhesión para recarga")
    public void TC0072BSF() {
        try {
            //PRECONDICION
            testCaseData.cargaDeDatosTest(uBenicio2022);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            recargasSteps.consultaServiciosAdheridosAPI(SUBE_ID,"6061267195495204","9003");
            //TEST
            menuLateralSteps.clickRecargas();
            recargasSteps.clickCardTransportePublico();
            recargasSteps.clickEmpresa();
            recargasSteps.seleccionarDropdownGenerico("SUBE");
            recargasSteps.clickSeleccionarNumero();
            recargasSteps.seleccionarDropdownGenerico("Nuevo");
            recargasSteps.setInputNumero("6061267195495204");
            recargasSteps.setInputPerteneceA("AUTOMATION");
            dobleFactorSteps.codigoSegundoFactorOtroMetodo();
            dobleFactorSteps.validaNotificacionExitosaGenerico("Transaccion realizada con exito");
            //POSTCONDICION
            //recargasSteps.eliminarAdhesaionesdeServicioAPI(SUBE_ID,"6061267195495204","AUTOMATION3","9003");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    //@Test // FALTA DE DATOS  - APAGADO POR BUG
    @DisplayName("TC-12955-BSF: Realizar recarga a Tarjeta Recargable/Prepagada")
    public void TC12955BSF() {
        try {
            testCaseData.cargaDeDatosTest(uAbramcelia);
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
    @DisplayName("TC-12956-BSF: RECARGAS - Eliminar adhesión")
    public void TC12956BSF() {
        try {
            //PRECONDICION
            testCaseData.cargaDeDatosTest(uAbramcelia);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            recargasSteps.consultaServiciosAdheridosAPI(CLARO_ID,"5557504183","8003");
            recargasSteps.realizarAdhesionAPI(CLARO_ID,"5557504183", "TC12956","8003");
            //CASO
            menuLateralSteps.clickRecargas();
            recargasSteps.clickAdhesiones();
            recargasSteps.clickBotonSeleccionar();
            nuevoPagoSteps.seleccionarDropdownGenerico("8003");
            UtilsGP.fixedWait(2000);
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
    @DisplayName("TC-12952-BSF: RECARGAS - Realizar recarga a Telefonía Celular")
    public void TC12952BSF() {
        try {
            //PRECONDICION
            testCaseData.cargaDeDatosTest(uAbramcelia);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            recargasSteps.consultaServiciosAdheridosAPI(CLARO_ID,"5557504183","8003");
            //TEST
            menuLateralSteps.clickRecargas();
            recargasSteps.clickSelectorTarjetaDebito();
            recargasSteps.seleccionarDropdownGenerico("8003");
            recargasSteps.clickContinuar();
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
            recargasSteps.seleccionarDropdownGenerico("Caja de Ahorro $ 000000869803");
            recargasSteps.clickContinuar();
            recargasSteps.clickConfirmarGenerico();
            recargasSteps.verificarDatosRecargaTelefoniaCelular("CLARO","5557504183","500.00","No pudimos procesar la operación");
            //POSTCONDICION ELIMINAR ADHESION
            //recargasSteps.eliminarAdhesaionesdeServicioAPI(CLARO_ID,"5557504183","Juan","8003");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    //@Test // FALTA DATOS
    @DisplayName("TC-12954-BSF: RECARGAS - Realizar recarga a Billetera/Serv. Prepago")
    public void TC12954BSF() {
        try {
            //CASO
            testCaseData.cargaDeDatosTest(uAbramcelia);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickRecargas();
            recargasSteps.clickSelectorTarjetaDebito();
            recargasSteps.keyDownToText("6007");
            recargasSteps.clickContinuar();
            recargasSteps.clickCardBilletera();
            recargasSteps.clickEmpresa();
            recargasSteps.keyDownToText("PIM TU PLATA EN TU CELULAR");
            recargasSteps.clickSeleccionarNumero();
            recargasSteps.keyDownXParametrosGenerico(0,"");
            recargasSteps.setInputNumero("1167262370");
            recargasSteps.setInputPerteneceA("TC-12954");
            recargasSteps.clickMontoRecarga("1");
            dobleFactorSteps.clickSms();
            dobleFactorSteps.codigoSegundoFactor();
            recargasSteps.clickAgregar();
            Thread.sleep(1000);
            recargasSteps.clickDesplegableDebito();
            consultaRecargasSteps.keyDownToText( "Caja de Ahorro $ 000000869803");
            recargasSteps.clickContinuar();
            recargasSteps.clickConfirmarGenerico();
            recargasSteps.verificarDatosRecargaTelefoniaCelular("PIM TU PLATA EN TU CELULAR","1167262379","1,00","No pudimos procesar la operación");
            //POSTCONDICION ELIMINAR ADHESION
            recargasSteps.eliminarAdhesaionesdeServicioAPI(SUBE_ID,"6061267195495203","TC0072","9003");

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    //CASOS DE LA CLASE SEGUROS
    @Test //DATOS OK - TEST OK
    @DisplayName("TC-12928-BSF: SEGUROS - Alta de seguros  - Acceso por Seguro - Menú lateral - Solicitud  y validación de pantalla")
    public void TC12928BSF() {
        try {
            testCaseData.cargaDeDatosTest(uDiazleon);
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
    @DisplayName("TC-0032-BSF: Consulta de Tarjetas de Crédito - Visa")
    public void TC0032BSF() {
        try {
            testCaseData.cargaDeDatosTest(uSantafe033);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.clickVerMasXnroDeTarjeta("9087");
            tarjetasDeCreditoSteps.verificarTituloVisa("9087");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test //OK
    @DisplayName("TC-0033-BSF: Consulta de Tarjetas de Crédito - Mastercard")
    public void TC0033BSF() {
        try {
            //PRECONDICION
            testCaseData.cargaDeDatosTest(uPrueba8240);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            //TEST
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.clickVerMasXnroDeTarjeta("1209");
            tarjetasDeCreditoSteps.verificarTituloMastercard();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0162-BSF: Tarjeta de Crédito - Denunciar robo o extravío de Tarjeta de Crédito - WEB.")
    public void TC0162BSF() {
        try {
            testCaseData.cargaDeDatosTest(uPrueba8240);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.validarTituloPageGenerico("Tarjetas de cr");
            tarjetasDeCreditoSteps.clickVerMasXnroDeTarjeta("1209");
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
    @DisplayName("TC-0165-BSF: PAGOS - Pagar Tarjeta de Crédito Master - Otro importe")
    public void TC0165BSF() {
        try {
            testCaseData.cargaDeDatosTest(uSantafe038);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.clickVerMasXnroDeTarjeta("1304");
            tarjetasDeCreditoSteps.clickMasOpcionesGenerico();
            tarjetasDeCreditoSteps.clickPagarTarjeta();
            //tarjetasDeCreditoSteps.seleccionarCuentaDebito("Caja de Ahorro $ 005001003005");
            //tarjetasDeCreditoSteps.clickContinuarGenerico();
            tarjetasDeCreditoSteps.seleccionarCuentaDebitoSiEstaPresente("Caja de Ahorro $ 005001003005");
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
    @DisplayName("TC-0167-BSF: Tarjeta de Crédito - Consultar último resumen de una Tarjeta de Crédito - WEB.")
    public void TC0167BSF() {
        try {
            testCaseData.cargaDeDatosTest(uSantafe033);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.clickVerMasXnroDeTarjeta("9087");
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
    @DisplayName("TC-12967-BSF: PAGOS - Pagar Tarjeta de Crédito Visa - Otro importe")
    public void TC12967BSF() {
        try {
            testCaseData.cargaDeDatosTest(uSantafe033);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.clickVerMasXnroDeTarjeta("9087");
            botonesGenericosSteps.clickMasOpcionesGenerico();
            tarjetasDeCreditoSteps.clickPagarTarjeta();
            tarjetasDeCreditoSteps.seleccionarCuentaDebito("Caja de Ahorro $ 001000009500 ");
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
    @DisplayName("TC-0068-BSF: Activación de tarjeta de débito - Validación de completitud de pantalla")
    public void TC0068BSF() {
        try {
            //PRECONDICION
            testCaseData.cargaDeDatosTest(uSantafe140);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            //TEST
            menuLateralSteps.clickTarjetaDeDebito();
            tarjetasDeDebitoSteps.validarTituloPageGenerico("Tarjetas de d");
            tarjetasDeDebitoSteps.clickBotonVerMas_Activar("5005");
            tarjetasDeDebitoSteps.validarTituloPageGenerico("Activaci");
            tarjetasDeDebitoSteps.validarTituloPageGenerico("Visa D");
            tarjetasDeDebitoSteps.validacionPantallaActivacionTarjeta();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    //@Test - 27 nov - Caso apagado para ser probado luego de resuelto el Incidente 10236
    @DisplayName("TC-0105-BSF: Tarjeta de Débito - Consulta de compras")
    public void TC0105BSF() {
        try {
            testCaseData.cargaDeDatosTest(uVolpatomonica);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickTarjetaDeDebito();
            tarjetasDeDebitoSteps.validarTituloPageGenerico("Tarjetas de d");
            tarjetasDeDebitoSteps.clickBotonVerMas_Activar("9008");
            tarjetasDeDebitoSteps.clickMasOpcionesGenerico();
            tarjetasDeDebitoSteps.clickConsultarCompras();
            tarjetasDeDebitoSteps.validarTituloPageGenerico("Consulta de compras en comercios");
            consultaDeComprasSteps.clickDesplegableCuenta();
            tarjetasDeDebitoSteps.keyDownToText("5705");
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
    // SECCION POR API SE APAGAN LOS CASOS
    //@Test
    @DisplayName("TC-0008-BSF: Transferencia Intrabancaria en Pesos - Propias (del mismo banco) Cargada Manualmente - Programada en Fecha Futura Botón Volver")
    public void TC0008BSF() {
        try {
            testCaseData.cargaDeDatosTest(uTranspull4);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
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
    //@Test
    @DisplayName("TC-0014-BSF: Generar CREDIN - Cuenta propia $ (mismo Banco u otro) - Mas de una cuenta Debito - CBU - Segundo factor por SMS")
    public void TC0014BSF() {
        try {
            testCaseData.cargaDeDatosTest(uPerezR);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickTransferencias("","");
            transferenciasSteps.clickMasOpcionesGenerico();
            transferenciasSteps.clickConsultarCredin();
            consultaCredinSteps.clickGenerarCredin();
            consultaCredinSteps.clickSelectorTarjetaDebito();
            consultaCredinSteps.seleccionarDropdownGenerico("9008");
            consultaCredinSteps.clickContinuarGenerico();
            generarCredinSteps.clickCuentaDebito();
            generarCredinSteps.seleccionarDropdownGenerico( "5008");//Cuenta Corriente $ 000042365705
            generarCredinSteps.clickCBU();
            generarCredinSteps.setCampoAliasCbu("3300000610000244827075");
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
    //@Test
    @DisplayName("TC-0139-BSF: Transferencias Inmediatas - Entre cuentas propias - Flujo completo- Validar ticket con exito")
    public void TC0139BSF() {
        try {
            testCaseData.cargaDeDatosTest(uPerezR);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickTransferencias("9008","CC $ 000000085008");
            transferenciasInmediatas.seleccionarTipoTransferencia("Cuenta Propia");
            transferenciasInmediatas.seleccionarCuentaDestinoCuentasPropias("CC $ 001000350002");
            transferenciasInmediatas.setImporteGenerico("100");
            transferenciasInmediatas.seleccionarConcepto("Alquileres");
            transferenciasInmediatas.setDescripcion("TestAutomation");
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
    //@Test
    @DisplayName("TC-0145-BSF: Transferencias - Añadir nuevo destinatario - Con cbu valido")
    public void TC0145BSF() {
        try {
            testCaseData.cargaDeDatosTest(uPerezR);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
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
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("3300000620000125788076");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    //@Test
    @DisplayName("TC-12930-BSF: TRANSFERENCIAS - A cuentas de terceros CBU - mismo banco - Pesos")
    public void TC12930BSF() {
        try {
            //PRECONDICION
            testCaseData.cargaDeDatosTest(uPerezR);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("3300000610000090000011");
            //TEST
            menuLateralSteps.clickTransferencias("9008","CC $ 000000085008");
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
    //@Test
    @DisplayName("TC-12932-BSF: TRANSFERENCIAS - A cuentas de terceros ALIAS - mismo banco - Pesos")
    public void TC12932BSF() {
        try {
            testCaseData.cargaDeDatosTest(uPerezR);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("CONDE.TRIGO.AMIGO");

            menuLateralSteps.clickTransferencias("9008","CC $ 000000085008");
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
    //@Test
    @DisplayName("TC-12935-BSF: Transferencias Programadas (Fecha especifica) CBU - terceros mismo banco  $ - NO AGENDADOS")
    public void TC12935BSF() {
        try {
            testCaseData.cargaDeDatosTest(uPerezR);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("3300001320010001101040");
            //TEST
            menuLateralSteps.clickTransferencias("9008","CC $ 000000085008");
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
    //@Test//OK
    @DisplayName("TC-12969-BSF: TRANSFERENCIAS - A cuentas de terceros CVU - otros bancos - Pesos")
    public void TC12969BSF() {
        try {
            testCaseData.cargaDeDatosTest(uPerezR);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("0000001700300000000313");
            //TEST
            menuLateralSteps.clickTransferencias("9008","CC $ 000000085008");
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
    //@Test
    @DisplayName("TC-12981-BSF: Transferencias - Eliminar destinatario de agenda")
    public void TC12981BSF() {
        try {
            //PRECONDICION
            testCaseData.cargaDeDatosTest(uPerezR);
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
    //@Test//OK
    @DisplayName("TC-12938-BSF: TRANSFERENCIAS - Generar CREDIN - Dolares")
    public void TC12938BSF() {
        try {
            testCaseData.cargaDeDatosTest(uVolpatomonica);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickTransferencias("","");
            transferenciasInmediatas.clickMasOpcionesGenerico();
            transferenciasSteps.clickConsultarCredin();
            consultaCredinSteps.clickGenerarCredin();
            consultaCredinSteps.clickSelectorTarjetaDebito();
            consultaCredinSteps.seleccionarDropdownGenerico("8000");
            consultaCredinSteps.clickContinuarGenerico();
            generarCredinSteps.clickCuentaDebito();
            generarCredinSteps.seleccionarDropdownGenerico( "USD 000000170305");
            generarCredinSteps.clickCBU();
            generarCredinSteps.setCampoAliasCbu("3300000640000010329025");
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
    //@Test
    @DisplayName("TC-12936 - BSF: TRANSFERENCIAS - Consulta de transferencias realizadas")
    public void TC12936BSF() {
        try {
            //CASO
            testCaseData.cargaDeDatosTest(uPerezR);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            menuLateralSteps.clickTransferencias("","");
            transferenciasInmediatas.misTransferencias();
            transferenciasInmediatas.validarListadoTransferencias();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    //@Test//OK
    @DisplayName("TC-12939-BSF: TRANSFERENCIAS - Consultar CREDIN Originante")
    public void TC12939BSF() {
        try {
            //Precondicion: Generar Credin
            testCaseData.cargaDeDatosTest(uPerezR);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            generarCredinSteps.generarCredinApi("3300000610000000850084","3300520915200003347070","9008");
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
            consultaCredinSteps.seleccionarDropdownGenerico( "Todos");
            consultaCredinSteps.clickBuscarGenerico();
            consultaCredinSteps.clickDetalleCredin();
            consultaCredinSteps.validacionDescripcionCredin("testAutomation");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    //@Test //OK
    @DisplayName("TC-12940-BSF: TRANSFERENCIAS - Consultar CREDIN destinatario")
    public void TC12940BSF() {
        try {
            //Precondicion: Generar Credin
            loginSteps.loginForAPI(uPerezR);
            generarCredinSteps.generarCredinApi("3300000610000000850084","3300520915200003347070","9008");
            //Test: Consulta de Credin
            testCaseData.cargaDeDatosTest(uAgustina2014);
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
            consultaCredinSteps.setCuitOriginante("20025000014"); //"20127315052 Agustina"
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
    //@Test
    @DisplayName("TC-14250-BSF: Transferencias CBU a cuentas propias otro banco $ - NO AGENDADOS")
    public void TC14250BSF(){
        try{
            //CASO
            testCaseData.cargaDeDatosTest(uPerezR);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("3860024901000035272026");
            //TEST
            menuLateralSteps.clickTransferencias("9008","CC $ 000000085008");
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
    //@Test
    @DisplayName("TC-0409-BFS: TRANSFERENCIAS - terceros mismo banco  $ - AGENDADOS")
    public void TC0409BSF(){
        try{
            //PRECONDICION
            testCaseData.cargaDeDatosTest(uPerezR);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            agendaNuevoDestinatarioSteps.adherirNuevoDestinatarioCBU("3300520915200000746085","TC0409BSF");
            //TEST
            menuLateralSteps.clickTransferencias("9008","CC $ 000000085008");
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
    //@Test
    @DisplayName("TC-0407-BFS: Transferencias ALIAS a cuentas propias otro banco $ - NO AGENDADOS")
    public void TC0407BSF(){
        try{
            //PRECONDICION
            testCaseData.cargaDeDatosTest(uAgustina2014);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("FLECO.GENIO.CELTA");
            //TEST
            menuLateralSteps.clickTransferencias("","CC $ 520000334707");
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
    @DisplayName("TC-0463-BFS: Transferencias Programadas (Fecha especifica) ALIAS a cuentas propias otro banco $ - NO AGENDADOS")
    public void TC0463BSF() {
        try {
            //TEST
            testCaseData.cargaDeDatosTest(uAgustina2014);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("FLECO.GENIO.CELTA");
            //TEST
            menuLateralSteps.clickTransferencias("","CC $ 520000334707");
            transferenciasInmediatas.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatas.completarInputAliasCbu("FLECO.GENIO.CELTA");
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
    //@Test
    @DisplayName("TC-0473-BFS: Transferencias Programadas (Fecha especifica) CBU - terceros otros bancos $ - NO AGENDADOS")
    public void TC0473BSF() {
        try {
            //TEST
            testCaseData.cargaDeDatosTest(uPerezR);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("3860001001000036919074");
            //TEST
            menuLateralSteps.clickTransferencias("9008","CC $ 000000085008");
            transferenciasInmediatas.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatas.completarInputAliasCbu("3860001001000036919074");
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
    //@Test
    @DisplayName("TC-0411-BFS: Transferencias - terceros otros bancos $ - AGENDADOS")
    public void TC0411BSF(){
        try{
            //PRECONDICION
            testCaseData.cargaDeDatosTest(uPerezR);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            agendaNuevoDestinatarioSteps.adherirNuevoDestinatarioCBU("3860001001000036919074","TER-BERSA");
            //TEST
            menuLateralSteps.clickTransferencias("9008","CC $ 000000085008");
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
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("3860001001000036919074");
            transferenciasInmediatas.verificarDescuentoCuentaOrigenTransferencia();
        }catch (Exception ex){
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    //@Test
    @DisplayName("TC-0427-BFS: Transferencias CVU - terceros $ AGENDADOS")
    public void TC0427BSF(){
        try{
            //PRECONDICION
            testCaseData.cargaDeDatosTest(uPerezR);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            agendaNuevoDestinatarioSteps.adherirNuevoDestinatarioCBU("0000001700300000000313","CVU");
            //TEST
            menuLateralSteps.clickTransferencias("9008","CC $ 000000085008");
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
    //@Test
    @DisplayName("TC-0429-BFS: Transferencias ALIAS de CVU - terceros $ NO AGENDADOS")
    public void TC0429BSF(){
        try{
            //PRECONDICION
            testCaseData.cargaDeDatosTest(uPerezR);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("BOTE.FILA.MITO");
            //TEST
            menuLateralSteps.clickTransferencias("9008","CC $ 000000085008");
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
    //@Test
    @DisplayName("TC-0465-BFS: Transferencias Programadas (Fecha especifica) - terceros mismo banco  $ - AGENDADOS")
    public void TC0465BSF(){
        try{
            //PRECONDICION
            testCaseData.cargaDeDatosTest(uPerezR);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            agendaNuevoDestinatarioSteps.adherirNuevoDestinatarioCBU("3300050110500001239081","TER-BSF");
            //TEST
            menuLateralSteps.clickTransferencias("9008","CC $ 000000085008");
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
    //@Test
    @DisplayName("TC-0477-BFS: Transferencias Programadas (Fecha especifica) ALIAS - terceros mismo banco  $ - NO AGENDADOS")
    public void TC0477BSF() {
        try {
            //TEST
            testCaseData.cargaDeDatosTest(uAbramcelia);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("TRIGO.AMIGO.PIE");
            //TEST
            menuLateralSteps.clickTransferencias("4002","CC $ 000000870608");
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
    //@Test
    @DisplayName("TC-0467-BFS: Transferencias Programadas (Fecha especifica) - terceros otros bancos $ - AGENDADOS")
    public void TC0467BSF(){
        try{;
            //PRECONDICION
            testCaseData.cargaDeDatosTest(uPerezR);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            agendaNuevoDestinatarioSteps.adherirNuevoDestinatarioCBU("3860001001000036919074","TER-BER");
            //TEST
            menuLateralSteps.clickTransferencias("9008","CC $ 000000085008");
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
    //@Test
    @DisplayName("TC-0483-BFS: Transferencias Programadas (Fecha especifica) CVU - terceros $ AGENDADOS")
    public void TC0483BSF(){
        try{
            //PRECONDICION
            testCaseData.cargaDeDatosTest(uPerezR);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            agendaNuevoDestinatarioSteps.adherirNuevoDestinatarioCBU("0000001700300000000313","TC0483BSF");
            //TEST
            menuLateralSteps.clickTransferencias("9008","CC $ 000000085008");
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
    //@Test
    @DisplayName("TC-0484-BFS: Transferencias Programadas (Fecha especifica) CVU - terceros $ NO AGENDADOS")
    public void TC0484BSF() {
        try {
            testCaseData.cargaDeDatosTest(uPerezR);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("0000001700300000000313");
            //TEST
            menuLateralSteps.clickTransferencias("9008","CC $ 000000085008");
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
    //@Test
    @DisplayName("TC-0485-BFS: Transferencias Programadas (Fecha especifica) ALIAS de CVU - terceros $ NO AGENDADOS")
    public void TC0485BSF() {
        try {
            testCaseData.cargaDeDatosTest(uPerezR);
            loginSteps.iniciarSessionAlEscritorioConPass(testCaseData.usuario,testCaseData.password);
            agendaNuevoDestinatarioSteps.eliminarDestinatarioAgendadoAPI("BOTE.FILA.MITO");
            //TEST
            menuLateralSteps.clickTransferencias("9008","CC $ 000000085008");
            transferenciasInmediatas.seleccionarTipoTransferencia("Terceros");
            transferenciasInmediatas.completarInputAliasCbu("BOTE.FILA.MITO");
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
