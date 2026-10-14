package GP.SantaFe.inversiones;

import GP.Tags.Alta;
import GP.Tags.Baja;
import GP.Tags.Criticos;
import GP.Tags.Media;
import enums.inversiones.fci.FiltroMonedaFci;
import enums.inversiones.fci.FiltroRiesgoFci;
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
import steps.inversiones.InversionesSteps;
import steps.inversiones.fci.*;
import steps.login.LoginSteps;
import steps.menulateral.MenuLateralSteps;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import steps.ticket.TicketSteps;
import utils.UtilsGP;

import static db.ConexionDB.cerrarConexion;
import static utils.Constantes.*;

public class FondoComunDeInversionesTest {

    LoginSteps loginSteps = new LoginSteps();
    MenuLateralSteps menuLateralSteps = new MenuLateralSteps();
    InversionesSteps inversionesSteps = new InversionesSteps();
    FondoComunInversionSteps fondoComunInversionSteps = new FondoComunInversionSteps();
    DetalleDelFondoSteps detalleDelFondoSteps = new DetalleDelFondoSteps();
    CalendarioSteps calendarioSteps = new CalendarioSteps();
    LocalDateTime date = LocalDateTime.now();
    DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    SimularPerfilInversorSteps simularPerfilInversorSteps = new SimularPerfilInversorSteps();
    SuscripcionFCISteps suscripcionFCISteps = new SuscripcionFCISteps();
    TicketSteps ticketSteps = new TicketSteps();
    RescateFCISteps rescateFCISteps = new RescateFCISteps();
    BotonesGenericosSteps botonesGenericosSteps = new BotonesGenericosSteps();
    BotonesGenericosPage botonesGenericosPage = new BotonesGenericosPage();
    UtilsGP utilsGP = new UtilsGP();

    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0111-BSF: Suscribir FCI - Validación de pantalla de suscripción de FCI")
    public void TC0111BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uRuattar);
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
    //@Test
    @DisplayName("TC-0112-BSF: Suscribir FCI SUPERIOR A 10 MILLONES")
    public void TC0112BSF() {
        try {
            loginSteps.iniciarSesion(uAbramcelia);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            fondoComunInversionSteps.selectorCuentaComitente("2-000024701");
            fondoComunInversionSteps.clickIconoAccionesDetalleFCI("A-DELTA PESOS");
            fondoComunInversionSteps.clickSuscribir();
            suscripcionFCISteps.validarTituloPageGenerico("El monto debe ser mayor o igual a");
            suscripcionFCISteps.setImporteSuscribirFci("amount_sum");
            suscripcionFCISteps.clickContinuarGenerico();
            suscripcionFCISteps.validarTituloPageGenerico("Confirmar la suscripc");
            suscripcionFCISteps.validarTituloPageGenerico("Importe a Suscribir");
            suscripcionFCISteps.clickCheckbox();
            suscripcionFCISteps.clickContinuarGenerico();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            //POSTCONDICION
            suscripcionFCISteps.actualizarImporteSuscribir("amount_sum","+");
        } catch (Exception ex) {
           System.out.println("Error: " + ex.getMessage());
           Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0113-BSF: FCI - Rescatar - Importe Parcial - Validación de pantalla")
    public void TC0113BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uRuattar);
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
    @Test
    @Category({Alta.class})
    @DisplayName("TC-0114-BSF: FCI - Rescatar - Importe Total - Validación de pantalla")
    public void TC0114BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uPerezR);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            fondoComunInversionSteps.selectorCuentaComitente("2-000024689");
            fondoComunInversionSteps.clickRescatarPrimerElementoLiquidacionEnElDia();
            rescateFCISteps.validarTituloPageGenerico("Rescate de FCI");
            rescateFCISteps.clickTotal();
            rescateFCISteps.clickContinuarGenerico();
            rescateFCISteps.clickCheckTerminosCondicionesGenerico();
            rescateFCISteps.validarTituloPageGenerico("Confirmar rescate de FCI");
            rescateFCISteps.validacionPantallaConfirmacionRescate();
            botonesGenericosSteps.validarBotonConfirmar();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Alta.class})
    @DisplayName("TC-0115-BSF: FCI - Simular perfil de inversor sin seleccionar cuenta comitente")
    public void TC0115BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uTester1);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            fondoComunInversionSteps.clickSimularPerfilInversor();
            simularPerfilInversorSteps.validarTituloPageGenerico("Simular perfil del inversor");
            simularPerfilInversorSteps.completarFormularioDeSimulacion();
            simularPerfilInversorSteps.clickContinuarGenerico();
            simularPerfilInversorSteps.validarTituloPageGenerico("Resultado del perfil de inversor");
            simularPerfilInversorSteps.validarTituloPageGenerico("Tu perfil de inversor es");
            simularPerfilInversorSteps.clickConfirmarGenerico();
            simularPerfilInversorSteps.validarTituloPageGenerico("Información de fondos");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    //@Test //SE ELIMINA STEP QUE ELIGE CTA COMITENTE
    @DisplayName("TC-0116-BSF: FCI - Simular perfil de inversor seleccionando cuenta comitente")
    public void TC0116BSF() {
        try {
            loginSteps.iniciarSesion(uAbramcelia);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            fondoComunInversionSteps.selectorCuentaComitente("2-000024701");
            fondoComunInversionSteps.clickSimularPerfilInversor();
            simularPerfilInversorSteps.validarTituloPageGenerico("Simular perfil del inversor");
            simularPerfilInversorSteps.completarFormularioDeSimulacion();
            simularPerfilInversorSteps.clickContinuarGenerico();
            simularPerfilInversorSteps.validarTituloPageGenerico("Resultado del perfil de inversor");
            simularPerfilInversorSteps.validarTituloPageGenerico("Tu perfil de inversor es");
            simularPerfilInversorSteps.clickConfirmarGenerico();
            simularPerfilInversorSteps.validaNotificacionEmergenteGenerico("Tu operacion ya fue realizada con exito");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Media.class})
    @DisplayName("TC-0117-BSF: FCI- Filtros - Mi Tenencia - Fondos por Riesgo")
    public void TC0117BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uAbramcelia);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            fondoComunInversionSteps.selectorCuentaComitente("2-000049315");
            fondoComunInversionSteps.clickLabelMisFondos();
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
    @Category({Baja.class})
    @DisplayName("TC-0118-BSF: FCI- Filtros - Mi Tenencia - Fondos por Riesgo - Conservador")
    public void TC0118BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uSantafe2024);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            fondoComunInversionSteps.selectorCuentaComitente("2-000024678");
            fondoComunInversionSteps.clickLabelMisFondos();
            fondoComunInversionSteps.seleccionarUnicamenteFiltrosPorRiesgo(FiltroRiesgoFci.CONSERVADOR);
            fondoComunInversionSteps.validaFondosPorRiesgoVisiblesEnListado("Conservador");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Baja.class})
    @DisplayName("TC-0119-BSF: FCI- Filtros - Mi Tenencia - Fondos por Riesgo - Moderado")
    public void TC0119BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uSantafe2024);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            fondoComunInversionSteps.selectorCuentaComitente("2-000024678");
            fondoComunInversionSteps.clickLabelMisFondos();
            fondoComunInversionSteps.seleccionarUnicamenteFiltrosPorRiesgo(FiltroRiesgoFci.MODERADO);
            fondoComunInversionSteps.validaFondosPorRiesgoVisiblesEnListado("Moderado");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Baja.class})
    @DisplayName("TC-0120-BSF: FCI- Filtros - Mi Tenencia - Fondos por Riesgo - Dinámico")
    public void TC0120BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uAbramcelia);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            fondoComunInversionSteps.selectorCuentaComitente("2-000024701");
            fondoComunInversionSteps.clickLabelMisFondos();
            fondoComunInversionSteps.seleccionarUnicamenteFiltrosPorRiesgo(FiltroRiesgoFci.DINAMICO);
            fondoComunInversionSteps.validaFondosPorRiesgoVisiblesEnListado("Dinamico");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Media.class})
    @DisplayName("TC-0121-BSF: FCI- Filtros - Fondos por Riesgo")
    public void TC0121BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uAbramcelia);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            fondoComunInversionSteps.selectorCuentaComitente("2-000024701");
            fondoComunInversionSteps.seleccionarUnicamenteFiltrosPorRiesgo(
                    FiltroRiesgoFci.CONSERVADOR,
                    FiltroRiesgoFci.MODERADO,
                    FiltroRiesgoFci.DINAMICO
            );
            fondoComunInversionSteps.validaListadoFCI();
            fondoComunInversionSteps.validaFondoPorRiesgoEnListado("conservador");
            fondoComunInversionSteps.validaFondoPorRiesgoEnListado("moderado");
            fondoComunInversionSteps.validaFondoPorRiesgoEnListado("Din");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Baja.class})
    @DisplayName("TC-0122-BSF: FCI- Filtros - Fondos por Riesgo - Conservador")
    public void TC0122BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uAbramcelia);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            //fondoComunInversionSteps.selectorCuentaComitente("2-000024701");
            fondoComunInversionSteps.seleccionarUnicamenteFiltrosPorRiesgo(FiltroRiesgoFci.CONSERVADOR);
            fondoComunInversionSteps.validaFondosPorRiesgoVisiblesEnListado("Conservador");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Baja.class})
    @DisplayName("TC-0123-BSF: FCI- Filtros - Fondos por Riesgo - Moderado")
    public void TC0123BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uAbramcelia);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            fondoComunInversionSteps.selectorCuentaComitente("2-000024701");
            fondoComunInversionSteps.seleccionarUnicamenteFiltrosPorRiesgo(FiltroRiesgoFci.MODERADO);
            fondoComunInversionSteps.validaFondosPorRiesgoVisiblesEnListado("Moderado");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Baja.class})
    @DisplayName("TC-0124-BSF: FCI- Filtros - Fondos por Riesgo - Dinamico")
    public void TC0124BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uAbramcelia);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            fondoComunInversionSteps.selectorCuentaComitente("2-000024701");
            fondoComunInversionSteps.seleccionarUnicamenteFiltrosPorRiesgo(FiltroRiesgoFci.DINAMICO);
            fondoComunInversionSteps.validaFondosPorRiesgoVisiblesEnListado("Dinamico");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Alta.class})
    @DisplayName("TC-0125-BSF: FCI - Solicitar baja cuenta comitente - Validación de pantalla")
    public void TC0125BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uAbramcelia);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickMasOpcionesGenerico();
            inversionesSteps.clickSolicitarBajaDeCuentaComitente();
            inversionesSteps.validarTituloPageGenerico("Baja de cuenta comitente");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Baja.class})
    @DisplayName("TC-0126-BSF: FCI - Filtros: Fondos por moneda - Pesos")
    public void TC0126BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uAbramcelia);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            fondoComunInversionSteps.selectorCuentaComitente("2-000024701");
            fondoComunInversionSteps.seleccionarUnicamenteFiltrosPorMoneda(FiltroMonedaFci.PESOS);
            fondoComunInversionSteps.validaFondosPorMonedaEnListado("pesos");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Baja.class})
    @DisplayName("TC-0127-BSF: FCI - Filtros: Fondos por moneda - Dólar")
    public void TC0127BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uAbramcelia);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            fondoComunInversionSteps.selectorCuentaComitente("2-000024701");
            fondoComunInversionSteps.seleccionarUnicamenteFiltrosPorMoneda(FiltroMonedaFci.DOLARES);
            fondoComunInversionSteps.validaFondosPorMonedaEnListado("dolar");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Media.class})
    @DisplayName("TC-0128-BSF: FCI - Filtros: Mi Tenencia")
    public void TC0128BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uSantafe2024);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            fondoComunInversionSteps.selectorCuentaComitente("2-000024678");
            fondoComunInversionSteps.clickLabelMisFondos();
            fondoComunInversionSteps.validaListadoFCI();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Alta.class})
    @DisplayName("TC-0129-BSF: FCI  - Movimientos")
    public void TC0129BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uSantafe2024);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            fondoComunInversionSteps.selectorCuentaComitente("4678");
            fondoComunInversionSteps.clickIconoAccionesDetalleFCI("A-DELTA PESOS");
            fondoComunInversionSteps.clickMiFondo();
            //fondoComunInversionSteps.validarTituloPageGenerico("Detalle del fondo");
            //fondoComunInversionSteps.clickCalendarioDesde();
            //calendarioSteps.retrocederSegunMes(9);
            //calendarioSteps.clickEnDiaXParametro("15");
            //detalleDelFondoSteps.clickAplicar();
            detalleDelFondoSteps.validarPantallaDetallaDelFondoMovimientos();
            detalleDelFondoSteps.validarTituloPageGenerico("Fecha de concertaci");
            detalleDelFondoSteps.validarTituloPageGenerico("Fecha de liquidaci");
            detalleDelFondoSteps.validarTituloPageGenerico("Nombre del fondo");
            detalleDelFondoSteps.validarTituloPageGenerico("Operaci");
            detalleDelFondoSteps.validarTituloPageGenerico("Cuotapartes");
            detalleDelFondoSteps.validarTituloPageGenerico("Importe");
            //fondoComunInversionSteps.validarRegistros();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Media.class})
    @DisplayName("TC-0130-BSF: FCI  - Movimientos con descarga PDF")
    public void TC0130BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uSantafe2024);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            fondoComunInversionSteps.selectorCuentaComitente("2-000024678");
            fondoComunInversionSteps.clickIconoAccionesDetalleFCI("1");
            fondoComunInversionSteps.clickMiFondo();
            fondoComunInversionSteps.validarTituloPageGenerico("Detalle del fondo");
            detalleDelFondoSteps.validarPantallaDetallaDelFondoMovimientos();
            detalleDelFondoSteps.clickFechaDesde();
            calendarioSteps.retrocederSegunMes(3);
            calendarioSteps.clickEnDiaXParametro("28");
            detalleDelFondoSteps.clickAplicar();
            detalleDelFondoSteps.clickIconoDescargar();
            detalleDelFondoSteps.validarDescarga("DELTA AHORRO (A).pdf");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Baja.class})
    @DisplayName("TC-0131-BSF: FCI  - Movimientos - Filtro de fecha")
    public void TC0131BSF() {
        try {
            String fDesde = date.toLocalDate().minusDays(date.getDayOfMonth()-1).minusMonths(1).format(formatDate);
            String fHasta = date.toLocalDate().format(formatDate);
            loginSteps.iniciarSessionAlEscritorio(uAbramcelia);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            fondoComunInversionSteps.selectorCuentaComitente("2-000024701");
            fondoComunInversionSteps.clickIconoAccionesDetalleFCI("A-DELTA PESOS");
            fondoComunInversionSteps.clickMiFondo();
            fondoComunInversionSteps.validarTituloPageGenerico("Detalle del fondo");
            detalleDelFondoSteps.clickFechaDesde();
            calendarioSteps.clickEnDiaXParametro("1");
            detalleDelFondoSteps.clickFechaHasta();
            calendarioSteps.clickDiaActual();
            detalleDelFondoSteps.validaFechasSeleccionadas(fDesde, fHasta);
            detalleDelFondoSteps.clickAplicar();
            detalleDelFondoSteps.validarPantallaDetallaDelFondoMovimientos();
            detalleDelFondoSteps.validaFechasConcertacionVisiblesEnListado(fDesde, fHasta);
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Alta.class})
    @DisplayName("TC-0132-BSF: FCI  - Rendimiento")
    public void TC0132BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uTranspull4);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            fondoComunInversionSteps.clickIconoAccionesDetalleFCI("A-DELTA PESOS");
            detalleDelFondoSteps.clickDetalle();
            detalleDelFondoSteps.validarTituloPageGenerico("Detalle del fondo");
            detalleDelFondoSteps.validarPantallaDetallaDelFondoRendimiento();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Media.class})
    @DisplayName("TC-0133-BSF: FCI  - Mas información - Aceptar Modal")
    public void TC0133BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uTranspull4);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            fondoComunInversionSteps.clickIconoAccionesDetalleFCI("A-DELTA AHORRO");
            fondoComunInversionSteps.clickMasInformacion();
            fondoComunInversionSteps.clickAceptar();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Baja.class})
    @DisplayName("TC-0134-BSF: FCI  - Mas información - Rechazar Modal")
    public void TC0134BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uTranspull4);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            fondoComunInversionSteps.clickIconoAccionesDetalleFCI("A-DELTA AHORRO");
            fondoComunInversionSteps.clickMasInformacion();
            fondoComunInversionSteps.clickCancelarGenerico();
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
