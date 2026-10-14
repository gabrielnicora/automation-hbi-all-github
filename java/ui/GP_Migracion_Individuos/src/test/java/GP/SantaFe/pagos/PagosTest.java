package GP.SantaFe.pagos;

import GP.Tags.Alta;
import GP.Tags.Baja;
import GP.Tags.Criticos;
import GP.Tags.Media;
import io.qameta.allure.Allure;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;
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
import utils.UtilsGP;

import java.io.IOException;
import java.sql.SQLException;

import static db.ConexionDB.cerrarConexion;
import static utils.Constantes.*;


public class PagosTest {
    LoginSteps loginSteps = new LoginSteps();
    MenuLateralSteps menuLateralSteps = new MenuLateralSteps();
    PagosSteps pagosSteps = new PagosSteps();
    ComprobantesSteps comprobantesSteps = new ComprobantesSteps();
    CalendarioSteps calendario = new CalendarioSteps();
    MensajeSteps mensajeSteps = new MensajeSteps();
    DebinCompradorSteps debinCompradorSteps = new DebinCompradorSteps();
    CalendarioSteps calendarioSteps = new CalendarioSteps();
    AdherirRecurrenciaSteps adherirRecurrenciaSteps = new AdherirRecurrenciaSteps();
    DobleFactorSteps dobleFactorSteps = new DobleFactorSteps();
    TicketSteps ticketSteps = new TicketSteps();
    MenuSuperiorSteps menuSuperiorSteps = new MenuSuperiorSteps();
    DebinVendedorSteps debinVendedorSteps = new DebinVendedorSteps();
    NuevoPagoSteps nuevoPagoSteps = new NuevoPagoSteps();
    AdherirServicioSteps adherirServicioSteps = new AdherirServicioSteps();
    ServiciosAdheridosSteps serviciosAdheridosSteps = new ServiciosAdheridosSteps();
    RecargasSteps recargasSteps = new RecargasSteps();
    BotonesGenericosSteps botonesGenericosSteps = new BotonesGenericosSteps();
    TarjetasDeCreditoSteps tarjetasDeCreditoSteps = new TarjetasDeCreditoSteps();
    BotonesGenericosPage botonesGenericosPage = new BotonesGenericosPage();
    UtilsGP utilsGP = new UtilsGP();
    
    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0012-BSF: DEBIN Comprador - Consulta DEBINes históricos - Listado")
    public void TC0012BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uAbramcelia);
            menuLateralSteps.clickPagos();
            pagosSteps.clickMasOpcionesGenerico();
            pagosSteps.clickDebinComprador();
            debinCompradorSteps.clickSelectorConsultarDebin();
            debinCompradorSteps.keyDownToText( "Hist");
            debinCompradorSteps.clickFechaCreacionDesde();
            calendarioSteps.clickMesAnterior(24);
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

    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0013-BSF: Generar orden DEBIN - Seleccionando CBU")
    public void TC0013BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uSantafe2024);
            menuLateralSteps.clickPagos();
            pagosSteps.clickMasOpcionesGenerico();
            pagosSteps.clickDebinVendedor();
            debinVendedorSteps.clickOrdenDebin();
            debinVendedorSteps.clickCuentaDestinoDesplegable();
            debinVendedorSteps.keyDownToText( "5704");
            debinVendedorSteps.setImporteGenerico("1");
            debinVendedorSteps.clickCbu();
            debinVendedorSteps.setCbuOAlias("3300000620000125788076");
            debinVendedorSteps.clickBuscar();
            debinVendedorSteps.clickNombre();
            debinVendedorSteps.keyDownToText("VOLPATO, MONICA LILIANA A");
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

    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0015-BSF: Adherir DEBIN recurrente (Pesos) -  2do factor por SMS - Descargar - Boton Volver")
    public void TC0015BSF() throws InterruptedException, SQLException {
        try {
            loginSteps.iniciarSessionAlEscritorio(uAbramcelia);
            menuLateralSteps.clickPagos();
            pagosSteps.clickMasOpcionesGenerico();
            pagosSteps.clickDebinComprador();
            debinCompradorSteps.clickAdherirRecurrencia();
            adherirRecurrenciaSteps.clickSelectorCuentaDebito();
            adherirRecurrenciaSteps.keyDownToText( "9803");
            adherirRecurrenciaSteps.setCuitVendedor("20179326591");
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
            //ticketSteps.clickDescargas();
            //ticketSteps.validarDescarga("Ticket.pdf");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    //@Test
    @Category({Criticos.class})
    @DisplayName("TC-0028-BSF: PAGOS - Pagar Impuesto/Servicio Nuevo Pago (Con Base)")
    public void TC0028BSF() {
        try {
            loginSteps.iniciarSesion(uVolpatomonica);
            menuLateralSteps.clickPagos();
            pagosSteps.clickNuevoPago();
            nuevoPagoSteps.validarTituloPageGenerico("Nuevo pago");
            nuevoPagoSteps.clickCardServicios();
            nuevoPagoSteps.seleccionarTarjetaDebitoSiEstaPresente("9008");
            nuevoPagoSteps.clickInputRubro();
            nuevoPagoSteps.keyDownToText("Internet y Telecomunicaciones");
            nuevoPagoSteps.clickInputEmpresa();
            nuevoPagoSteps.keyDownToText("ECOWIFI");
            String codigoPago = nuevoPagoSteps.getCodigoPagoDb("ECOWIFI");
            nuevoPagoSteps.setCodigoDePago(codigoPago);
            dobleFactorSteps.codigoSegundoFactorBotonContinuar();
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
    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0074-BSF: ADHERIR NUEVO PAGO O SERVICIO")
    public void TC0074BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uSantafe2024);
            menuLateralSteps.clickPagos();
            pagosSteps.clickMasOpcionesGenerico();
            pagosSteps.clickAdherirImpuestoServicio();
            //pagosSteps.clickServiciosAdheridos();
            //pagosSteps.clickSelectorTarjetaDebito();
            //pagosSteps.keyDownToText("1006");
            //pagosSteps.clickContinuarGenerico();
            //pagosSteps.clickAdherirImpuestoServicio();
            adherirServicioSteps.validarTituloPageGenerico("Adherir servicio");
            //adherirServicioSteps.seleccionarTarjetaDesplegableSiEstaPresente("1006");
            //adherirServicioSteps.clickDesplegableRubro();
            adherirServicioSteps.setInputRubro("Cobranzas para Empresas y Comercios");
            adherirServicioSteps.keyDownToText("Cobranzas para Empresas y Comercios");
            //adherirServicioSteps.clickDesplegableEmpresa();
            adherirServicioSteps.setInputEmpresa("Mercado Libre");
            adherirServicioSteps.keyDownToText( "Mercado Libre");
            adherirServicioSteps.setCodigoDePago("00449535691");
            adherirServicioSteps.setearDescripciondeAdhesion("TestAutomation");
            adherirServicioSteps.clickContinuarGenerico();
            adherirServicioSteps.validarTituloPageGenerico("Confirmar adhesi");
            //dobleFactorSteps.clickSms();
            dobleFactorSteps.codigoSegundoFactorOtroMetodo();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            //TEARDOWN
            menuLateralSteps.clickPagos();
            pagosSteps.clickMasOpcionesGenerico();
            pagosSteps.clickServiciosAdheridos();
            //pagosSteps.clickSelectorTarjetaDebito();
            //pagosSteps.keyDownToText("1006");
            //adherirServicioSteps.clickContinuarGenerico();
            adherirServicioSteps.validarTituloPageGenerico("Servicios adheridos");
            serviciosAdheridosSteps.clickEmpresa();
            serviciosAdheridosSteps.setInputEmpresa("Mercado Libre");
            adherirServicioSteps.keyDownToText("Mercado Libre");
            serviciosAdheridosSteps.clickTresPuntos("00449535691");
            serviciosAdheridosSteps.eliminarServicioAdherido();
            serviciosAdheridosSteps.clickConfirmarGenerico();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0070-BSF: Aceptación de DEBIN")
    public void TC0070BSF() throws SQLException, InterruptedException {
        try {
            //PRECONDICION
            pagosSteps.generacionDebinApi("3300000620000008698032","CA $ 000012578807","27114486952");
            //TEST
            loginSteps.iniciarSessionAlEscritorio(uAbramcelia);
            menuLateralSteps.clickPagos();
            pagosSteps.clickMasOpcionesGenerico();
            pagosSteps.clickDebinComprador();
            debinCompradorSteps.clickSelectorConsultarDebin();
            debinCompradorSteps.keyDownXParametrosGenerico(0, "Pendientes");
            debinCompradorSteps.clickBuscar();
            debinCompradorSteps.validarColumnaIdDebin();
            debinCompradorSteps.clickDetalleDebinPrimerElemento();
            debinCompradorSteps.validarTituloPageGenerico("Detalle de DEBIN");
            debinCompradorSteps.clickAceptarGenerico();
            debinCompradorSteps.clickAceptarDebin();
            debinCompradorSteps.clickCheckTerminosCondicionesGenerico();
            //dobleFactorSteps.clickSms();
            dobleFactorSteps.codigoSegundoFactorOtroMetodo();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Alta.class})
    @DisplayName("TC-0108-BSF: Pagos - Consulta de comprobantes")
    public void TC0108BSF() {
        try {
            //PRECONDICION
            loginSteps.iniciarSessionAlEscritorio(uTranspull4);
           // pagosSteps.generarUnPagoDeServicioApi("Mercado Libre","12345678987","00449535691");
            //TEST
            menuLateralSteps.clickPagos();
            pagosSteps.clickComprobantes();
            pagosSteps.validarTituloPageGenerico("Comprobantes de pago");
            comprobantesSteps.clickEmpresa();
            comprobantesSteps.setInputEmpresa("Chab Digital");
            comprobantesSteps.keyDownToText( "Chab Digital");
            comprobantesSteps.clickDescripcion();
            comprobantesSteps.keyDownXParametrosGenerico(0, "");
            comprobantesSteps.clickFechaCreacionDesde();
            calendarioSteps.clickMesAnterior(1);
            calendarioSteps.clickEnDiaXParametro("1");
            calendarioSteps.clickBuscar();
            comprobantesSteps.clickTresPuntos();
            comprobantesSteps.clickDescargarComprobante();
            comprobantesSteps.validarDescarga("Ticket.pdf");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Media.class})
    @DisplayName("TC-0137-BSF: PAGOS -COMPROBANTES - Descargar periodo en PDF")
    public void TC0137BSF() throws InterruptedException {
        try {
            //PRECONDICION
            loginSteps.iniciarSessionAlEscritorio(uTranspull4);
           // pagosSteps.generarUnPagoDeServicioApi("Mercado Libre","12345678987","00449535691");
            //TEST
            menuLateralSteps.clickPagos();
            pagosSteps.clickComprobantes();
            pagosSteps.validarTituloPageGenerico("Comprobantes de pago");
            comprobantesSteps.clickEmpresa();
            comprobantesSteps.keyDownToText("ECOWIFI");
            comprobantesSteps.clickDescripcion();
            pagosSteps.keyDownXParametrosGenerico(1, "");
            comprobantesSteps.clickDesde();
            calendario.clickMesAnterior(1);
            calendario.clickEnDiaXParametro("1");
            comprobantesSteps.clickBuscar();
            pagosSteps.clickMasOpcionesGenerico();
            comprobantesSteps.descargarPeriodoPDF();
            mensajeSteps.clickMensajes();
            mensajeSteps.clickSinLeer();
            comprobantesSteps.descargarMensajePDF();
            mensajeSteps.clickEnTresPuntos();
            mensajeSteps.clickDescargar();
            pagosSteps.validarDescarga("Pagos de impuestos o servicios realizados.pdf");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }

    }

    @Test
    @Category({Media.class})
    @DisplayName("TC-0138-BSF: PAGOS -COMPROBANTES - Ver Pagos en XLS")
    public void TC0138BSF() {
        try {
            //PRECONDICION
            loginSteps.iniciarSessionAlEscritorio(uTranspull4);
            //pagosSteps.generarUnPagoDeServicioApi("Mercado Libre","1234567898","00449535691");
            //TEST
            menuLateralSteps.clickPagos();
            pagosSteps.clickComprobantes();
            pagosSteps.validarTituloPageGenerico("Comprobantes de pago");
            comprobantesSteps.clickEmpresa();
            comprobantesSteps.keyDownToText("ECOWIFI");
            comprobantesSteps.clickDescripcion();
            pagosSteps.keyDownXParametrosGenerico(1, "");
            comprobantesSteps.clickDesde();
            calendario.clickMesAnterior(11);
            calendario.clickEnDiaXParametro("1");
            comprobantesSteps.clickBuscar();
            pagosSteps.clickMasOpcionesGenerico();
            comprobantesSteps.descargarPeriodoXLS();
            mensajeSteps.clickMensajes();
            mensajeSteps.clickSinLeer();
            comprobantesSteps.descargarMensajeXLS();
            mensajeSteps.clickEnTresPuntos();
            mensajeSteps.clickDescargar();
            pagosSteps.validarDescarga("Pagos de impuestos o servicios realizados.xls");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Alta.class})
    @DisplayName("TC-0183-BSF: DEBIN Vendedor - Generar orden de DEBIN - Validar campo requerido al no ingresar un CBU y seleccionar buscar.")
    public void TC0183BSF() {
        try {
            loginSteps.iniciarSesion(uVolpatomonica);
            menuLateralSteps.clickPagos();
            pagosSteps.clickMasOpcionesGenerico();
            pagosSteps.clickDebinVendedor();
            debinVendedorSteps.clickOrdenDebin();
            debinVendedorSteps.clickCuentaDestinoDesplegable();
            debinVendedorSteps.keyDownToText( "8807");
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
    @DisplayName("TC-0184-BSF: DEBIN Vendedor - Generar orden de DEBIN - Validar campo requerido al no ingresar 'Alias' y seleccionar buscar.")
    public void TC0184BSF() {
        try {
            loginSteps.iniciarSesion(uVolpatomonica);
            menuLateralSteps.clickPagos();
            pagosSteps.clickMasOpcionesGenerico();
            pagosSteps.clickDebinVendedor();
            debinVendedorSteps.clickOrdenDebin();
            debinVendedorSteps.clickCuentaDestinoDesplegable();
            debinVendedorSteps.keyDownToText( "8807");
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
    @DisplayName("TC-12964-BSF: PAGOS - Pagar Impuesto/Servicio Nuevo Pago (Sin Base)")
    public void TC12964BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uBenicio2022);
            menuLateralSteps.clickPagos();
            pagosSteps.clickNuevoPago();
            nuevoPagoSteps.clickCardServicios();
            nuevoPagoSteps.clickInputRubro();
            nuevoPagoSteps.keyDownToText("Cobranzas para Empresas y Comercios");
            nuevoPagoSteps.clickInputEmpresa();
            nuevoPagoSteps.keyDownToText("Mercado Libre");
            nuevoPagoSteps.validarMensajeCodigoPago();
            adherirServicioSteps.setCodigoDePago("00449535691");
            nuevoPagoSteps.clickCheckBoxServicio();
            dobleFactorSteps.clickSms();
            dobleFactorSteps.codigoSegundoFactorBotonContinuar();
            nuevoPagoSteps.clickPagandoDesdeDesplegable();
            debinVendedorSteps.keyDownToText("0001");
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
    @Test
    @Category({Criticos.class})
    @DisplayName("TC-12966-BSF: PAGOS - Pagar Impuesto/Servicio Eliminar servicio")
    public void TC12966BSF() {
        try {
            //PRECONDICIÓN
            loginSteps.iniciarSessionAlEscritorio(uTosticarelli);
            pagosSteps.adherirServicioApi("99992146", "Internet y Telecomunicaciones","ECOWIFI", "1006");
            //CASO
            menuLateralSteps.clickCuentas();
            menuLateralSteps.clickPagos();
            pagosSteps.clickServiciosAdheridos();
            serviciosAdheridosSteps.clickTresPuntos("99992146");
            serviciosAdheridosSteps.eliminarServicioAdherido();
            serviciosAdheridosSteps.clickConfirmarGenerico();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
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