package GP.SanJuan.pagos;

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

import static db.ConexionDB.cerrarConexion;
import static utils.Constantes.*;


public class PagosTest {
    LoginSteps loginSteps = new LoginSteps();
    TarjetasDeCreditoSteps tarjetasDeCreditoSteps = new TarjetasDeCreditoSteps();
    MenuLateralSteps menuLateralSteps = new MenuLateralSteps();
    PagosSteps pagosSteps = new PagosSteps();
    ComprobantesSteps comprobantesSteps = new ComprobantesSteps();
    CalendarioSteps calendario = new CalendarioSteps();
    MensajeSteps mensajeSteps = new MensajeSteps();
    AdherirServicioSteps adherirServicioSteps = new AdherirServicioSteps();
    DobleFactorSteps dobleFactorSteps = new DobleFactorSteps();
    TicketSteps ticketSteps = new TicketSteps();
    ServiciosAdheridosSteps serviciosAdheridosSteps = new ServiciosAdheridosSteps();
    NuevoPagoSteps nuevoPagoSteps = new NuevoPagoSteps();
    DebinCompradorSteps debinCompradorSteps = new DebinCompradorSteps();
    CalendarioSteps calendarioSteps = new CalendarioSteps();
    MenuSuperiorSteps menuSuperiorSteps = new MenuSuperiorSteps();
    DebinVendedorSteps debinVendedorSteps = new DebinVendedorSteps();
    AdherirRecurrenciaSteps adherirRecurrenciaSteps = new AdherirRecurrenciaSteps();
    BotonesGenericosSteps botonesGenericosSteps = new BotonesGenericosSteps();
    RecargasSteps recargasSteps = new RecargasSteps();

    BotonesGenericosPage botonesGenericosPage = new BotonesGenericosPage();
    UtilsGP utilsGP = new UtilsGP();

    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0012-BSJ: DEBIN Comprador - Consulta DEBINes históricos - Listado")
    public void TC0012BSJ() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uSanjuan_135);
            menuLateralSteps.clickPagos();
            pagosSteps.clickMasOpcionesGenerico();
            pagosSteps.clickDebinComprador();
            debinCompradorSteps.clickSelectorConsultarDebin();
            debinCompradorSteps.keyDownXParametrosGenerico(1, "Hist");
            debinCompradorSteps.clickFechaCreacionDesde();
            calendarioSteps.clickMesAnterior(30);
            calendarioSteps.clickEnDiaXParametro("1");
            debinCompradorSteps.clickBuscar();
            debinCompradorSteps.validarColumnaIdDebin();
            debinCompradorSteps.clickDescargarPdfGenerico();
            menuSuperiorSteps.esperarMensajeNotificacion();
            //menuSuperiorSteps.clickMensaje();
            //mensajeSteps.clickRecibidos();
            //mensajeSteps.clickSinLeer();
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
    @DisplayName("TC-0013-BSJ: Generar orden DEBIN - Seleccionando CBU")
    public void TC0013BSJ() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uSanjuan_135);
            menuLateralSteps.clickPagos();
            pagosSteps.clickMasOpcionesGenerico();
            pagosSteps.clickDebinVendedor();
            debinVendedorSteps.clickOrdenDebin();
            debinVendedorSteps.clickCuentaDestinoDesplegable();
            debinVendedorSteps.keyDownToText( "8271");
            debinVendedorSteps.setImporteGenerico("10");
            debinVendedorSteps.clickCbu();
            debinVendedorSteps.setCbuOAlias("3300041910410410017014");
            debinVendedorSteps.clickBuscar();
            debinVendedorSteps.clickConcepto();
            debinVendedorSteps.keyDownToText( "Alquileres");
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
    @DisplayName("TC-0015-BSJ: Adherir DEBIN recurrente (Pesos) -  2do factor por SMS - Descargar - Boton Volver")
    public void TC0015BSJ() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uSanjuan_135);
            menuLateralSteps.clickPagos();
            pagosSteps.clickMasOpcionesGenerico();
            pagosSteps.clickDebinComprador();
            debinCompradorSteps.clickAdherirRecurrencia();
            adherirRecurrenciaSteps.clickSelectorCuentaDebito();
            adherirRecurrenciaSteps.keyDownToText( "8271");
            adherirRecurrenciaSteps.setCuitVendedor("20179326591");
            adherirRecurrenciaSteps.clickBotonConsultar();
            adherirRecurrenciaSteps.clickSelectorPrestacion();
            adherirRecurrenciaSteps.keyDownToText( "EDENOR");
            adherirRecurrenciaSteps.clickSelectorConcepto();
            adherirRecurrenciaSteps.keyDownToText( "Alquileres");
            adherirRecurrenciaSteps.setReferencia("TC0015");
            adherirRecurrenciaSteps.setDescripcion("TestAutomation");
            adherirRecurrenciaSteps.clickBotonContinuar();
            adherirRecurrenciaSteps.clickCheckTerminosCondicionesGenerico();
            dobleFactorSteps.codigoSegundoFactorOtroMetodo();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            ticketSteps.clickDescargas();
            ticketSteps.validarDescarga("Ticket.pdf");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    //@Test //NO EJECUTAMOS PAGOS CON BASE / VENCIMIENTO
    @Category({Criticos.class})
    @DisplayName("TC-0028-BSJ: PAGOS - Pagar Impuesto/Servicio Nuevo Pago (Con Base)")
    public void TC0028BSJ() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uCuyo2021);
            menuLateralSteps.clickPagos();
            pagosSteps.clickNuevoPago();
            nuevoPagoSteps.validarTituloPageGenerico("Nuevo pago");
            nuevoPagoSteps.clickCardServicios();
            nuevoPagoSteps.clickInputRubro();
            nuevoPagoSteps.keyDownToText("Internet y Telecomunicaciones");
            nuevoPagoSteps.clickInputEmpresa();
            nuevoPagoSteps.keyDownToText("ECOWIFI");
            String codigoPago = nuevoPagoSteps.getCodigoPagoDb("ECOWIFI");
            nuevoPagoSteps.setCodigoDePago(codigoPago);
            dobleFactorSteps.codigoSegundoFactorBotonContinuar();
            nuevoPagoSteps.validarTituloPageGenerico("Nuevo pago");
            nuevoPagoSteps.clickPagandoDesdeDesplegable();
            nuevoPagoSteps.keyDownXParametrosGenerico(0, "");
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
    @DisplayName("TC-0070-BSJ: Aceptación de DEBIN")
    public void TC0070BSJ() {
        try {
            //PRECONDICION
            //pagosSteps.generacionDebinApi(uGdrio_12,"0450018601800093482711");
            //FIN PRECONDICION
            loginSteps.iniciarSessionAlEscritorio(uSanjuan_135);
            menuLateralSteps.clickPagos();
            pagosSteps.clickMasOpcionesGenerico();
            pagosSteps.clickDebinComprador();
            debinCompradorSteps.clickSelectorConsultarDebin();
            debinCompradorSteps.keyDownToText( "Pendientes");
            debinCompradorSteps.clickBuscar();
            debinCompradorSteps.validarColumnaIdDebin();
            debinCompradorSteps.clickDetalleDebinPrimerElemento();
            debinCompradorSteps.validarTituloPageGenerico("Detalle de DEBIN");
            debinCompradorSteps.clickAceptarConEstafas();
            //debinCompradorSteps.clickAceptarDebin();
            debinCompradorSteps.clickCheckTerminosCondicionesGenerico();
            //debinCompradorSteps.setCodigoSeguridad("451");
            //debinCompradorSteps.setMes("05");
            //debinCompradorSteps.setAnio("30");
            dobleFactorSteps.codigoSegundoFactor();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0074-BSJ: ADHERIR NUEVO PAGO Ó SERVICIO")
    public void TC0074BSJ() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uSanjuan_135);
            menuLateralSteps.clickPagos();
            pagosSteps.clickServiciosAdheridos();
            //pagosSteps.clickSelectorTarjetaDebito();
            //pagosSteps.keyDownToText("8006");
            //pagosSteps.clickContinuarGenerico();
            pagosSteps.clickAdherirImpuestoServicio();
            adherirServicioSteps.validarTituloPageGenerico("Adherir servicio");
            //adherirServicioSteps.seleccionarTarjetaDesplegableSiEstaPresente("8006");
            adherirServicioSteps.clickDesplegableRubro();
            adherirServicioSteps.setInputRubro("Cobranzas para Empresas y Comercios");
            adherirServicioSteps.keyDownToText("Cobranzas para Empresas y Comercios");
            adherirServicioSteps.clickDesplegableEmpresa();
            adherirServicioSteps.setInputEmpresa("Mercado Libre");
            adherirServicioSteps.keyDownToText( "Mercado Libre");
            String codigoPago = "00449535691";
            adherirServicioSteps.setCodigoDePago(codigoPago);
            adherirServicioSteps.setearDescripciondeAdhesion("testAutomation1");
            adherirServicioSteps.clickContinuarGenerico();
            adherirServicioSteps.clickConfirmarGenerico();
            adherirServicioSteps.validarTituloPageGenerico("Confirmar adhesi");
            dobleFactorSteps.codigoSegundoFactorOtroMetodo();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            //TEARDOWN

            /*menuLateralSteps.clickPagos();
            pagosSteps.clickServiciosAdheridos();
            //pagosSteps.clickSelectorTarjetaDebito();
            //pagosSteps.keyDownToText("8006");
            //adherirServicioSteps.clickContinuarGenerico();
            adherirServicioSteps.validarTituloPageGenerico("Servicios adheridos");
            serviciosAdheridosSteps.clickEmpresa();
            serviciosAdheridosSteps.setInputEmpresa("Mercado Libre");
            adherirServicioSteps.keyDownToText("Mercado Libre");
            serviciosAdheridosSteps.clickTresPuntos(codigoPago);
            serviciosAdheridosSteps.eliminarServicioAdherido();
            serviciosAdheridosSteps.clickConfirmarGenerico();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");*/

            pagosSteps.eliminarServicioAdheridoAPI(uEcarina27,"testAutomation1", "1016", "");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Alta.class})
    @DisplayName("TC-0108-BSJ: Pagos - Consulta de comprobantes")
    public void TC0108BSJ() {
        try {
            //PRECONDICION
            loginSteps.iniciarSessionAlEscritorio(uCarmend);
          //  pagosSteps.generarUnPagoDeServicioApi("Mercado Libre","12345678987","00449535691");
            //TEST
            menuLateralSteps.clickPagos();
            pagosSteps.clickComprobantes();
            //pagosSteps.validarTituloPageGenerico("Comprobantes de pago");
            comprobantesSteps.clickEmpresa();
            comprobantesSteps.setInputEmpresa("Mercado Pago");
            comprobantesSteps.keyDownToText( "Mercado Pago");
            comprobantesSteps.clickDescripcion();
            comprobantesSteps.keyDownXParametrosGenerico(0, "");
            comprobantesSteps.clickFechaCreacionDesde();
            calendarioSteps.clickMesAnterior(5);
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
    @DisplayName("TC-0137-BSJ: PAGOS -COMPROBANTES - Descargar periodo en PDF")
    public void TC0137BSJ() throws InterruptedException {
        try {
            //PRECONDICION
            loginSteps.iniciarSessionAlEscritorio(uCarmend);
            //pagosSteps.generarUnPagoDeServicioApi("Mercado Libre","1234567898","00449535691");
            //TEST
            menuLateralSteps.clickPagos();
            pagosSteps.clickComprobantes();
            pagosSteps.validarTituloPageGenerico("Comprobantes de pago");
            comprobantesSteps.clickEmpresa();
            pagosSteps.keyDownToText("Tel. Fija Personal");
            comprobantesSteps.clickDescripcion();
            pagosSteps.keyDownXParametrosGenerico(2, "");
            comprobantesSteps.clickDesde();
            calendario.clickMesAnterior(35);
            calendario.clickEnDiaXParametro("10");
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
    @DisplayName("TC-0138-BSJ: PAGOS -COMPROBANTES - Ver Pagos en XLS")
    public void TC0138BSJ() {
        try {
            //PRECONDICION
            loginSteps.iniciarSessionAlEscritorio(uCarmend);
           // pagosSteps.generarUnPagoDeServicioApi("Mercado Libre","12345678987","00449535691");
            //TEST
            menuLateralSteps.clickPagos();
            pagosSteps.clickComprobantes();
            pagosSteps.validarTituloPageGenerico("Comprobantes de pago");
            comprobantesSteps.clickEmpresa();
            pagosSteps.keyDownToText( "Tel. Fija Personal");
            comprobantesSteps.clickDescripcion();
            pagosSteps.keyDownXParametrosGenerico(0, "");
            comprobantesSteps.clickDesde();
            calendario.clickMesAnterior(6);
            comprobantesSteps.clickBuscar();
            pagosSteps.clickMasOpcionesGenerico();
            comprobantesSteps.descargarPeriodoXLS();
            mensajeSteps.clickMensajes();
            mensajeSteps.clickSinLeer();
            comprobantesSteps.descargarMensajeXLS();
            mensajeSteps.clickEnTresPuntos();
            pagosSteps.validarDescarga("Pagos de impuestos o servicios realizados.xls");

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Alta.class})
    @DisplayName("TC-0183-BSF: DEBIN Vendedor - Generar orden de DEBIN - Validar campo requerido al no ingresar un CBU y seleccionar buscar.")
    public void TC0183BSJ() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uCuyo2021);
            menuLateralSteps.clickPagos();
            pagosSteps.clickMasOpcionesGenerico();
            pagosSteps.clickDebinVendedor();
            debinVendedorSteps.clickOrdenDebin();
            debinVendedorSteps.clickCuentaDestinoDesplegable();
            debinVendedorSteps.keyDownXParametrosGenerico(0,"");
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
    @DisplayName("TC-0184-BSJ: DEBIN Vendedor - Generar orden de DEBIN - Validar campo requerido al no ingresar 'Aias' y seleccionar buscar.")
    public void TC0184BSJ() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uCuyo2021);
            menuLateralSteps.clickPagos();
            pagosSteps.clickMasOpcionesGenerico();
            pagosSteps.clickDebinVendedor();
            debinVendedorSteps.clickOrdenDebin();
            debinVendedorSteps.clickCuentaDestinoDesplegable();
            debinVendedorSteps.keyDownXParametrosGenerico(0,"");
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
    @DisplayName("TC-12964-BSJ: PAGOS - Pagar Impuesto/Servicio Nuevo Pago (Sin Base)")
    public void TC12964BSJ() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uSanjuan_135);
            menuLateralSteps.clickPagos();
            pagosSteps.clickNuevoPago();
            nuevoPagoSteps.clickCardServicios();
            nuevoPagoSteps.clickInputRubro();
            nuevoPagoSteps.keyDownToText("Cobranzas para Empresas y Comercios");
            nuevoPagoSteps.clickInputEmpresa();
            nuevoPagoSteps.keyDownToText("Mercado Libre");
            nuevoPagoSteps.validarMensajeCodigoPago();
            String codigoPago = "00449535691";
            adherirServicioSteps.setCodigoDePago(codigoPago);
            dobleFactorSteps.codigoSegundoFactorBotonContinuar();
            nuevoPagoSteps.clickPagandoDesdeDesplegable();
            nuevoPagoSteps.keyDownXParametrosGenerico(0,"");
            debinVendedorSteps.keyDownToText("8271");
            recargasSteps.clickBotonSeleccionar();
            debinVendedorSteps.keyDownToText("MLIBRE FACT");
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
    @DisplayName("TC-12966-BSJ: PAGOS - Pagar Impuesto/Servicio Eliminar servicio")
    public void TC12966BSJ() {
        try {
            //PRECONDICIÓN
            /*
            loginSteps.iniciarSesion(uCuyo2021);
            menuLateralSteps.clickPagos();
            pagosSteps.clickServiciosAdheridos();
            pagosSteps.clickAdherirImpuestoServicio();
            adherirServicioSteps.validarTituloPageGenerico("Adherir servicio");
            adherirServicioSteps.clickDesplegableRubro();
            adherirServicioSteps.setInputRubro("Internet y Telecomunicaciones");
            adherirServicioSteps.keyDownToText("Internet y Telecomunicaciones");
            adherirServicioSteps.clickDesplegableEmpresa();
            adherirServicioSteps.setInputEmpresa("ECOWIFI");
            adherirServicioSteps.keyDownToText( "ECOWIFI");
            String codigoPago = "99992146";
            adherirServicioSteps.setCodigoDePago(codigoPago);
            adherirServicioSteps.clickContinuarGenerico();
            adherirServicioSteps.validarTituloPageGenerico("Confirmar adhesi");
            dobleFactorSteps.codigoSegundoFactorOtroMetodo("uCuyo2021");
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
             */

            loginSteps.iniciarSessionAlEscritorio(uSanjuan_135);
           // pagosSteps.adherirServicioApi("99992146", "Internet y Telecomunicaciones","ECOWIFI", );
            //CASO
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