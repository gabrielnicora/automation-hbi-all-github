package GP.EntreRios.pagos;

import GP.Tags.Alta;
import GP.Tags.Baja;
import GP.Tags.Criticos;
import GP.Tags.Media;
import db.ConexionDB;
import io.qameta.allure.Allure;
import io.qameta.allure.junit4.DisplayName;
import org.junit.*;
import org.junit.experimental.categories.Category;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.botonesgenericos.BotonesGenericosPage;
import steps.botonesgenericos.BotonesGenericosSteps;
import steps.calendarios.CalendarioSteps;
import steps.doblefactor.DobleFactorSteps;
import steps.login.LoginSteps;
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
import steps.recargas.RecargasSteps;
import steps.tarjetasdecredito.TarjetasDeCreditoSteps;
import steps.ticket.TicketSteps;
import utils.Constantes;
import utils.MetodosAPI;
import utils.TestCaseData;
import utils.UtilsGP;

import java.io.IOException;

import static utils.Constantes.*;


public class   PagosTest {
    LoginSteps loginSteps = new LoginSteps();
    MenuLateralSteps menuLateralSteps = new MenuLateralSteps();
    TarjetasDeCreditoSteps tarjetasDeCreditoSteps = new TarjetasDeCreditoSteps();
    MenuSuperiorSteps menuSuperiorSteps = new MenuSuperiorSteps();
    PagosSteps pagosSteps = new PagosSteps();
    ComprobantesSteps comprobantesSteps = new ComprobantesSteps();
    CalendarioSteps calendarioSteps = new CalendarioSteps();
    MensajeSteps mensajeSteps = new MensajeSteps();
    BotonesGenericosSteps botonesGenericosSteps = new BotonesGenericosSteps();
    DebinCompradorSteps debinCompradorSteps = new DebinCompradorSteps();
    DebinVendedorSteps debinVendedorSteps = new DebinVendedorSteps();
    TicketSteps ticketSteps = new TicketSteps();
    AdherirRecurrenciaSteps adherirRecurrenciaSteps = new AdherirRecurrenciaSteps();
    NuevoPagoSteps nuevoPagoSteps = new NuevoPagoSteps();
    DobleFactorSteps dobleFactorSteps = new DobleFactorSteps();
    AdherirServicioSteps adherirServicioSteps = new AdherirServicioSteps();
    ServiciosAdheridosSteps serviciosAdheridosSteps = new ServiciosAdheridosSteps();
    RecargasSteps recargasSteps = new RecargasSteps();
    BotonesGenericosPage botonesGenericosPage = new BotonesGenericosPage();
    MetodosAPI metodosAPI = new MetodosAPI();
    UtilsGP utilsGP = new UtilsGP();
    WebDriverWait wait = new WebDriverWait(global.practia.jemeinsamstark.web.core.WebDriver.getInstance(), 40);
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
    
    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0012-BERSA: DEBIN Comprador - Consulta DEBINes históricos - Listado")
    public void TC0012BERSA() {
        try {
            //pagosSteps.generacionDebinApi(uTranspull1,"3860020103000000522105");
            testCaseData.cargaDeDatosTest(uEcarina27);
            loginSteps.iniciarSessionAlEscritorioConPass(uEcarina27,testCaseData.password);
            menuLateralSteps.clickPagos();
            pagosSteps.clickMasOpcionesGenerico();
            pagosSteps.clickDebinComprador();
            debinCompradorSteps.clickSelectorConsultarDebin();
            debinCompradorSteps.keyDownToText( "Hist");
            calendarioSteps.clickEnFechaDeCreacionHasta();
            calendarioSteps.clickDiaActual();
            debinCompradorSteps.clickFechaCreacionDesde();
            calendarioSteps.clickMesAnterior(35);
            calendarioSteps.clickEnDiaXParametro("1");
            debinCompradorSteps.clickBuscar();
            debinCompradorSteps.validarColumnaIdDebin();
            debinCompradorSteps.clickDescargarPdfGenerico();
            menuSuperiorSteps.esperarMensajeNotificacion();
            mensajeSteps.clickPrimerMensaje();
            mensajeSteps.clickEnTresPuntos();
            mensajeSteps.clickDescargar();
            debinCompradorSteps.validarDescarga("Listado DEBIN historico.pdf");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0013-BERSA: Generar orden DEBIN - Seleccionando CBU")
    public void TC0013BERSA() {
        try {
            testCaseData.cargaDeDatosTest(uEcarina27);
            loginSteps.iniciarSessionAlEscritorioConPass(uEcarina27,testCaseData.password);
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
            //debinVendedorSteps.clickNombre();
            //debinVendedorSteps.keyDownToText("VOLPATO, MONICA LILIANA A"); DE MODIFICAR SE DEBE CAMBIAR EL NOMBRE
            debinVendedorSteps.clickConcepto();
            debinVendedorSteps.keyDownToText("Alquileres");
            debinVendedorSteps.setReferencia("TestAutomation");
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

    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0015-BERSA: Adherir DEBIN recurrente (Pesos) -  2do factor por SMS - Descargar - Boton Volver")
    public void TC0015BERSA() {
        try {
            testCaseData.cargaDeDatosTest(uEcarina27);
            loginSteps.iniciarSessionAlEscritorioConPass(uEcarina27,testCaseData.password);
            menuLateralSteps.clickPagos();
            pagosSteps.clickMasOpcionesGenerico();
            pagosSteps.clickDebinComprador();
            debinCompradorSteps.clickAdherirRecurrencia();
            adherirRecurrenciaSteps.clickSelectorCuentaDebito();
            adherirRecurrenciaSteps.keyDownToText( "2210");
            adherirRecurrenciaSteps.setCuitVendedor("20205056573");
            adherirRecurrenciaSteps.clickBotonConsultar();
            adherirRecurrenciaSteps.clickSelectorPrestacion();
            adherirRecurrenciaSteps.keyDownToText( "ejemplo");
            adherirRecurrenciaSteps.clickSelectorConcepto();
            adherirRecurrenciaSteps.keyDownToText( "Alquileres");
            adherirRecurrenciaSteps.setReferencia("TestAutomation");
            adherirRecurrenciaSteps.setDescripcion("TC0015");
            adherirRecurrenciaSteps.clickBotonContinuar();
            adherirRecurrenciaSteps.clickCheckTerminosCondicionesGenerico();
            dobleFactorSteps.codigoSegundoFactor();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            ticketSteps.clickDescargas();
            ticketSteps.validarDescarga("Ticket.pdf");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    //@Test
    @Category({Criticos.class})
    @DisplayName("TC-0028-BERSA: PAGOS - Pagar Impuesto/Servicio Nuevo Pago (Con Base)")
    public void TC0028BERSA() {
        try {
            testCaseData.cargaDeDatosTest(uEcarina27);
            loginSteps.iniciarSessionAlEscritorioConPass(uEcarina27,testCaseData.password);
            menuLateralSteps.clickPagos();
            pagosSteps.clickNuevoPago();
            nuevoPagoSteps.validarTituloPageGenerico("Nuevo pago");
            nuevoPagoSteps.clickCardServicios();
            nuevoPagoSteps.seleccionarTarjetaDebitoSiEstaPresente("8006");
            nuevoPagoSteps.clickInputRubro();
            nuevoPagoSteps.keyDownToText("Internet y Telecomunicaciones");
            nuevoPagoSteps.clickInputEmpresa();
            nuevoPagoSteps.keyDownToText("ECOWIFI");
            String codigoPago = nuevoPagoSteps.getCodigoPagoDb("ECOWIFI");
            nuevoPagoSteps.setCodigoDePago(codigoPago);
            //dobleFactorSteps.codigoSegundoFactorBotonContinuar(uEcarina27);
            nuevoPagoSteps.validarTituloPageGenerico("Preparar pago");
            nuevoPagoSteps.clickPagandoDesdeDesplegable();
            nuevoPagoSteps.keyDownXParametrosGenerico(0,"");
            nuevoPagoSteps.clickContinuarGenerico();
            nuevoPagoSteps.clickConfirmarGenerico();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            nuevoPagoSteps.actualizarCodigoPagoDb(codigoPago, "ECOWIFI");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test //solo ejecutar 1 vez al dia
    @Category({Criticos.class})
    @DisplayName("TC-0070-BERSA: Aceptación de DEBIN")
    public void TC0070BERSA() {
        try {
            //PRECONDICION
            pagosSteps.generacionDebinApi("3860023201000000161496","CA $ 020000052210","20115814991");
            //FIN PRECONDICION
            testCaseData.cargaDeDatosTest(uUathb04);
            loginSteps.iniciarSessionAlEscritorioConPass(uUathb04,testCaseData.password);
            menuLateralSteps.clickPagos();
            pagosSteps.clickMasOpcionesGenerico();
            pagosSteps.clickDebinComprador();
           // debinCompradorSteps.clickSelectorConsultarDebin();
            //debinCompradorSteps.keyDownToText( "Pendientes");
            Thread.sleep(3000);
            debinCompradorSteps.clickBuscar();
            debinCompradorSteps.validarColumnaIdDebin();
            debinCompradorSteps.clickDetalleDebinPrimerElemento();
            //ValidacionDeIdentidadSteps validacionDeIdentidadSteps = new ValidacionDeIdentidadSteps();
            //validacionDeIdentidadSteps.validacionIdentidad("3013", "1", "1", "1", "1");
            debinCompradorSteps.seleccionarTarjetaDebitoSiEstaPresente("");
            debinCompradorSteps.clickAceptarConEstafas();
            //debinCompradorSteps.clickAceptarDebin();
            debinCompradorSteps.clickCheckTerminosCondicionesGenerico();
            //dobleFactorSteps.codigoSegundoFactorOtroMetodo(uUathbb04);
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0074-BERSA: ADHERIR NUEVO PAGO O SERVICIO")
    public void TC0074BERSA() {
        try {
            testCaseData.cargaDeDatosTest(uEcarina27);
            loginSteps.iniciarSessionAlEscritorioConPass(uEcarina27,testCaseData.password);
            menuLateralSteps.clickPagos();
            Thread.sleep(5000);
            pagosSteps.clickServiciosAdheridos();
            pagosSteps.clickSelectorTarjetaDebito();
            pagosSteps.keyDownToText("3009");
            pagosSteps.clickAdherirImpuestoServicio();
            adherirServicioSteps.validarTituloPageGenerico("Adherir servicio");
            adherirServicioSteps.clickDesplegableRubro();
            adherirServicioSteps.setInputRubro("Cobranzas para Empresas y Comercios");
            adherirServicioSteps.keyDownToText("Cobranzas para Empresas y Comercios");
            adherirServicioSteps.clickDesplegableEmpresa();
            adherirServicioSteps.setInputEmpresa("Mercado Libre");
            adherirServicioSteps.keyDownToText( "Mercado Libre");
            adherirServicioSteps.setCodigoDePago("00449535691");
            adherirServicioSteps.setearDescripciondeAdhesion("testAutomation1");
            adherirServicioSteps.clickContinuarGenerico();
            adherirServicioSteps.clickConfirmarGenerico();
            //dobleFactorSteps.codigoSegundoFactorOtroMetodo(uEcarina27);
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            //POSTCONDICION
            pagosSteps.eliminarServicioAdheridoAPI(uEcarina27,"testAutomation1", "3009", "");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Alta.class})
    @DisplayName("TC-0108-BERSA: Pagos - Consulta de comprobantes")
    public void TC0108BERSA() {
        try {
            //PRECONDICION
           // loginSteps.iniciarSessionAlEscritorio(uEcarina27);
           // pagosSteps.generarUnPagoDeServicioApi("Mercado Libre","1234567890","00449535691","CA $ 020000052210","8006");
            loginSteps.iniciarSessionAlEscritorio(uUathb04);
            pagosSteps.generarUnPagoDeServicioApi("Mercado Libre","1234567890","00449535691","CC $ 035000023427","6009");
            //TEST
            menuLateralSteps.clickPagos();
            pagosSteps.clickComprobantes();
            pagosSteps.validarTituloPageGenerico("Comprobantes de pago");
           // nuevoPagoSteps.seleccionarTarjetaDebitoSiEstaPresente("3009");
            //nuevoPagoSteps.clickContinuarGenerico();
            comprobantesSteps.clickEmpresa();
            //comprobantesSteps.setInputEmpresa("Mercado Pago");
            comprobantesSteps.keyDownToText( "Mercado Libre");
            comprobantesSteps.clickDescripcion();
            comprobantesSteps.keyDownXParametrosGenerico(0,"");
            comprobantesSteps.clickFechaCreacionDesde();
            calendarioSteps.clickMesAnterior(22);
            calendarioSteps.clickEnDiaXParametro("1");
            calendarioSteps.clickBuscar();
            comprobantesSteps.clickTresPuntos();
            comprobantesSteps.clickDescargarComprobante();
            comprobantesSteps.validarDescarga("Ticket.pdf");
            //post
            pagosSteps.eliminarServicioAdheridoAPI(uUathbb04,"PruebaAutomation","3009", "");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Media.class})
    @DisplayName("TC-0137-BERSA: PAGOS -COMPROBANTES - Descargar periodo en PDF")
    public void TC0137BERSA() throws InterruptedException {
        try {
            //PRECONDICION
            //loginSteps.iniciarSessionAlEscritorio(uUathb04);
            //pagosSteps.generarUnPagoDeServicioApi("Mercado Libre","1234567890","00449535691","CC $ 035000023427","6009");
            loginSteps.iniciarSessionAlEscritorio(uEcarina27);
            pagosSteps.generarUnPagoDeServicioApi("Mercado Libre","1234567890","00449535691","CA $ 020000052210","8006");
            //TEST
            menuLateralSteps.clickPagos();
            pagosSteps.clickComprobantes();
            pagosSteps.validarTituloPageGenerico("Comprobantes de pago");
            comprobantesSteps.clickEmpresa();
            pagosSteps.keyDownToText( "Mercado Libre");
            comprobantesSteps.clickDescripcion();
            pagosSteps.keyDownXParametrosGenerico(0,"");
            comprobantesSteps.clickDesde();
            calendarioSteps.clickMesAnterior(10);
            calendarioSteps.clickEnDiaXParametro("10");
            comprobantesSteps.clickBuscar();
            pagosSteps.clickMasOpcionesGenerico();
            comprobantesSteps.descargarPeriodoPDF();
            mensajeSteps.clickMensajes();
            mensajeSteps.clickSinLeer();
            comprobantesSteps.descargarMensajePDF();
            mensajeSteps.clickEnTresPuntos();
            mensajeSteps.clickDescargar();
            pagosSteps.validarDescarga("Pagos de impuestos o servicios realizados.pdf");
            //POST condición
            pagosSteps.eliminarServicioAdheridoAPI(uUathbb04,"PruebaAutomation","6009", "");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Media.class})
    @DisplayName("TC-0138-BERSA: PAGOS -COMPROBANTES - Ver Pagos en XLS")
    public void TC0138BERSA() {
        try {
            //PRECONDICION
            loginSteps.iniciarSessionAlEscritorio(uUathb04);
            pagosSteps.generarUnPagoDeServicioApi("Mercado Libre","1234567899","00449535691","CC $ 035000023427","6009");
            //TEST
            menuLateralSteps.clickPagos();
            pagosSteps.clickComprobantes();
            pagosSteps.validarTituloPageGenerico("Comprobantes de pago");
            comprobantesSteps.clickEmpresa();
            pagosSteps.keyDownToText( "Mercado Libre");
            comprobantesSteps.clickDescripcion();
            pagosSteps.keyDownXParametrosGenerico(0,"");
            comprobantesSteps.clickDesde();
            calendarioSteps.clickMesAnterior(10);
            comprobantesSteps.clickBuscar();
            pagosSteps.clickMasOpcionesGenerico();
            comprobantesSteps.descargarPeriodoXLS();
            mensajeSteps.clickMensajes();
            mensajeSteps.clickSinLeer();
            comprobantesSteps.descargarMensajeXLS();
            mensajeSteps.clickEnTresPuntos();
            pagosSteps.validarDescarga("Pagos de impuestos o servicios realizados.xls");
            //post condicion
            pagosSteps.eliminarServicioAdheridoAPI(uUathbb04,"PruebaAutomation","6009", "");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Alta.class})
    @DisplayName("TC-0183-BERSA: DEBIN Vendedor - Generar orden de DEBIN - Validar campo requerido al no ingresar un CBU y seleccionar buscar.")
    public void TC0183BERSA() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uUathb04);
            menuLateralSteps.clickPagos();
            pagosSteps.clickMasOpcionesGenerico();
            pagosSteps.clickDebinVendedor();
            debinVendedorSteps.clickOrdenDebin();
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
    @Category({Baja.class})
    @DisplayName("TC-0184-BERSA: DEBIN Vendedor - Generar orden de DEBIN - Validar campo requerido al no ingresar 'Aias' y seleccionar buscar.")
    public void TC0184BERSA() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uUathb04);
            menuLateralSteps.clickPagos();
            pagosSteps.clickMasOpcionesGenerico();
            pagosSteps.clickDebinVendedor();
            debinVendedorSteps.clickOrdenDebin();
            debinVendedorSteps.clickCuentaDestinoDesplegable();
            debinVendedorSteps.keyDownToText("3427");
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
    @Category({Criticos.class})
    @DisplayName("TC-12964-BERSA: PAGOS - Pagar Impuesto/Servicio Nuevo Pago (Sin Base)")
    public void TC12964BERSA() {
        try {
            testCaseData.cargaDeDatosTest(uEcarina27);
            loginSteps.iniciarSessionAlEscritorioConPass(uEcarina27,testCaseData.password);
            menuLateralSteps.clickPagos();
            pagosSteps.clickNuevoPago();
            nuevoPagoSteps.clickCardServicios();
            pagosSteps.clickSelectorTarjetaDebito();
            pagosSteps.keyDownToText("8006");
            nuevoPagoSteps.clickInputRubro();
            nuevoPagoSteps.keyDownToText("Cobranzas para Empresas y Comercios");
            nuevoPagoSteps.clickInputEmpresa();
            nuevoPagoSteps.keyDownToText("Mercado Libre");
            nuevoPagoSteps.validarMensajeCodigoPago();
            String codigoPago = "00449535691";
            adherirServicioSteps.setCodigoDePago(codigoPago);
            //dobleFactorSteps.codigoSegundoFactorBotonContinuar(uEcarina27);
            nuevoPagoSteps.clickPagandoDesdeDesplegable();
            debinVendedorSteps.keyDownToText("2210");
            recargasSteps.clickBotonSeleccionar();
            debinVendedorSteps.keyDownToText("MPAGO COMPRA");
            debinVendedorSteps.setReferencia("1234554321");
            botonesGenericosSteps.setImporteGenerico("1");
            adherirServicioSteps.clickContinuarGenerico();
            serviciosAdheridosSteps.clickConfirmarGenerico();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Criticos.class})
    @DisplayName("TC-12966-BERSA: PAGOS - Pagar Impuesto/Servicio Eliminar servicio")
    public void TC12966BERSA() {
        try {
            //PRECONDICIÓN
            testCaseData.cargaDeDatosTest(uEcarina27);
            loginSteps.iniciarSessionAlEscritorioConPass(uEcarina27,testCaseData.password);
            pagosSteps.adherirServicioApi("99992146", "Internet y Telecomunicaciones","ECOWIFI","8006" );
            //CASO
            menuLateralSteps.clickCuentas();
            menuLateralSteps.clickPagos();
            pagosSteps.clickServiciosAdheridos();
            serviciosAdheridosSteps.buscarPorDescripcion("TestAutomation1");
            serviciosAdheridosSteps.clickBuscarGenerico();
            serviciosAdheridosSteps.eliminarServicioAdherido();
            serviciosAdheridosSteps.clickBotonDesvincular();
            //ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            //ticketSteps.validaNotificacionExitsaGenerico();
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