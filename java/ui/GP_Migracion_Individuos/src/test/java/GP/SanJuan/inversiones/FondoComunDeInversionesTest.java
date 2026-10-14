package GP.SanJuan.inversiones;

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
import steps.ticket.TicketSteps;
import utils.UtilsGP;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    RescateFCISteps rescateFCISteps = new RescateFCISteps();
    SuscripcionFCISteps suscripcionFCISteps = new SuscripcionFCISteps();
    TicketSteps ticketSteps = new TicketSteps();
    BotonesGenericosSteps botonesGenericosSteps = new BotonesGenericosSteps();
    BotonesGenericosPage botonesGenericosPage = new BotonesGenericosPage();
    UtilsGP utilsGP = new UtilsGP();

    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0111-BSJ: Suscribir FCI - Validación de pantalla de suscripción de FCI")
    public void TC0111BSJ() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uGdrio_12);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            //fondoComunInversionSteps.selectorCuentaComitente("2-000005871");
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
    //@Test //No se puede hacer efectiva la suscripción y la casuistica se prueba en el caso TC0111
    @DisplayName("TC-0112-BSJ: Suscribir FCI SUPERIOR A 10 MILLONES")
    public void TC0112BSJ() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uCuyo2023);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            fondoComunInversionSteps.selectorCuentaComitente("2-000005871");
            fondoComunInversionSteps.clickIconoAccionesDetalleFCI("2");
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
    @DisplayName("TC-0113-BSJ: FCI - Rescatar - Importe Parcial - Validación de pantalla")
    public void TC0113BSJ() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uGdrio_12);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            //fondoComunInversionSteps.selectorCuentaComitente("2-000005885");
            fondoComunInversionSteps.clickIconoAccionesDetalleFCI("A-Delta Ahorro");
            fondoComunInversionSteps.clickRescate();
            rescateFCISteps.validarTituloPageGenerico("Rescate de FCI");
            rescateFCISteps.clickParcial();
            rescateFCISteps.setImporteRescatar("300");//Modificar nombre de metodo por setImporte a rescatar
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
    @DisplayName("TC-0114-BSJ: FCI - Rescatar - Importe Total - Validación de pantalla")
    public void TC0114BSJ() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uLuna2053);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            //fondoComunInversionSteps.selectorCuentaComitente("2-000005885");
            fondoComunInversionSteps.clickIconoAccionesDetalleFCI("A-Delta pesos");
            fondoComunInversionSteps.clickRescate();
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
    @DisplayName("TC-0115-BSJ: FCI - Simular perfil de inversor sin seleccionar cuenta comitente")
    public void TC0115BSJ() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uCuyo2023);
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
    @Test  //Sin datos al aplicar Mis fondos / ni "conservador", ni "Moderado", ni "dinamico"
    @Category({Media.class})
    @DisplayName("TC-0117-BSJ: FCI - Filtros - Mi Tenencia - Fondos por Riesgo")
    public void TC0117BSJ() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uLuna2053);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            //fondoComunInversionSteps.selectorCuentaComitente("2-000005885");
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
    @DisplayName("TC-118-BSJ: FCI- Filtros - Mi Tenencia - Fondos por Riesgo - Conservador")
    public void TC0118BSJ() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uSanJuan_357);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            //fondoComunInversionSteps.selectorCuentaComitente("2-000005857");
            fondoComunInversionSteps.clickLabelMisFondos();
            fondoComunInversionSteps.seleccionarUnicamenteFiltrosPorRiesgo(FiltroRiesgoFci.CONSERVADOR);
            fondoComunInversionSteps.validaFondosPorRiesgoVisiblesEnListado("CONSERVADOR");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Baja.class})
    @DisplayName("TC-0119-BSJ: FCI- Filtros - Mi Tenencia - Fondos por Riesgo - Moderado")
    public void TC0119BSJ() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uSanJuan_357);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            //fondoComunInversionSteps.selectorCuentaComitente("2-000005857");
            fondoComunInversionSteps.clickLabelMisFondos();
            fondoComunInversionSteps.seleccionarUnicamenteFiltrosPorRiesgo(FiltroRiesgoFci.MODERADO);
            fondoComunInversionSteps.validaFondosPorRiesgoVisiblesEnListado("MODERADO");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test //Sin datos al aplicar Mis fondos / ni "conservador", ni "Moderado", ni "dinamico"
    @Category({Baja.class})
    @DisplayName("TC-0120-BSJ: FCI- Filtros - Mi Tenencia - Fondos por Riesgo - Dinámico")
    public void TC0120BSJ() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uSanJuan_357);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            //fondoComunInversionSteps.selectorCuentaComitente("2-000005857");
            fondoComunInversionSteps.clickLabelMisFondos();
            fondoComunInversionSteps.seleccionarUnicamenteFiltrosPorRiesgo(FiltroRiesgoFci.DINAMICO);
            fondoComunInversionSteps.validaFondosPorRiesgoVisiblesEnListado("DIN");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Media.class})
    @DisplayName("TC-0121-BSJ: FCI- Filtros - Fondos por Riesgo")
    public void TC0121BSJ() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uCuyo2023);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            //fondoComunInversionSteps.selectorCuentaComitente("2-000005871");
            fondoComunInversionSteps.seleccionarUnicamenteFiltrosPorRiesgo(
                    FiltroRiesgoFci.CONSERVADOR,
                    FiltroRiesgoFci.MODERADO,
                    FiltroRiesgoFci.DINAMICO
            );
            fondoComunInversionSteps.validaListadoFCI();
            fondoComunInversionSteps.validaFondoPorRiesgoEnListado("CONSERVADOR");
            fondoComunInversionSteps.validaFondoPorRiesgoEnListado("MODERADO");
            fondoComunInversionSteps.validaFondoPorRiesgoEnListado("DIN");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Baja.class})
    @DisplayName("TC-0122-BSJ: FCI- Filtros - Fondos por Riesgo - Conservador")
    public void TC0122BSJ() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uCuyo2023);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            //fondoComunInversionSteps.selectorCuentaComitente("2-000005871");
            fondoComunInversionSteps.seleccionarUnicamenteFiltrosPorRiesgo(FiltroRiesgoFci.CONSERVADOR);
            fondoComunInversionSteps.validaFondosPorRiesgoVisiblesEnListado("CONSERVADOR");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Baja.class})
    @DisplayName("TC-0123-BSJ: FCI- Filtros - Fondos por Riesgo - Moderado")
    public void TC0123BSJ() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uCuyo2023);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            //fondoComunInversionSteps.selectorCuentaComitente("2-000005871");
            fondoComunInversionSteps.seleccionarUnicamenteFiltrosPorRiesgo(FiltroRiesgoFci.MODERADO);
            fondoComunInversionSteps.validaFondosPorRiesgoVisiblesEnListado("MODERADO");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Baja.class})
    @DisplayName("TC-0124-BSJ: FCI- Filtros - Fondos por Riesgo - Dinamico")
    public void TC0124BSJ() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uSanJuan_357);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            //fondoComunInversionSteps.selectorCuentaComitente("2-000005857");
            fondoComunInversionSteps.seleccionarUnicamenteFiltrosPorRiesgo(FiltroRiesgoFci.DINAMICO);
            fondoComunInversionSteps.validaFondosPorRiesgoVisiblesEnListado("DINAMICO");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Alta.class})
    @DisplayName("TC-0125-BSJ: FCI - Solicitar baja cuenta comitente - Validación de pantalla")
    public void TC0125BSJ() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uCuyo2023);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickMasOpcionesGenerico();
            inversionesSteps.clickSolicitarBajaDeCuentaComitente();
            inversionesSteps.clickContinuarGenerico();
            inversionesSteps.validarTituloPageGenerico("Baja de cuenta comitente");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test //Sin datos de registro en pesos ni dolares
    @Category({Baja.class})
    @DisplayName("TC-0126-BSJ: FCI - Filtros: Fondos por moneda - Pesos")
    public void TC0126BSJ() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uCuyo2023);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            //fondoComunInversionSteps.selectorCuentaComitente("2-000005871");
            fondoComunInversionSteps.seleccionarUnicamenteFiltrosPorMoneda(FiltroMonedaFci.PESOS);
            fondoComunInversionSteps.validaFondosPorMonedaEnListado("pesos");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test //Sin datos de registro en pesos ni dolares
    @Category({Baja.class})
    @DisplayName("TC-0127-BSJ: FCI - Filtros: Fondos por moneda - Dólar")
    public void TC0127BSJ() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uCuyo2023);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            //fondoComunInversionSteps.selectorCuentaComitente("2-000005871");
            fondoComunInversionSteps.seleccionarUnicamenteFiltrosPorMoneda(FiltroMonedaFci.DOLARES);
            fondoComunInversionSteps.validaFondosPorMonedaEnListado("dolar");

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test //Sin datos al aplicar Mis fondos
    @Category({Media.class})
    @DisplayName("TC-0128-BSJ: FCI - Filtros: Mi Tenencia")
    public void TC0128BSJ() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uLuna2053);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            //fondoComunInversionSteps.selectorCuentaComitente("2-000005885");
            fondoComunInversionSteps.clickLabelMisFondos();
            fondoComunInversionSteps.validaListadoFCI();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Alta.class})
    @DisplayName("TC-0129-BSJ: FCI  - Movimientos")
    public void TC0129BSJ() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uCuyo2023);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            //fondoComunInversionSteps.selectorCuentaComitente("2-000005871");
            fondoComunInversionSteps.clickIconoAccionesDetalleFCI("A-Delta Pesos");
            fondoComunInversionSteps.clickMiFondo();
            detalleDelFondoSteps.validarPantallaDetallaDelFondoMovimientos();
            detalleDelFondoSteps.validarTituloPageGenerico("Fecha de concertaci");
            detalleDelFondoSteps.validarTituloPageGenerico("Fecha de liquidaci");
            detalleDelFondoSteps.validarTituloPageGenerico("Nombre del fondo");
            detalleDelFondoSteps.validarTituloPageGenerico("Operaci");
            detalleDelFondoSteps.validarTituloPageGenerico("Cuotapartes");
            detalleDelFondoSteps.validarTituloPageGenerico("Importe");

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Media.class})
    @DisplayName("TC-0130-BSJ: FCI  - Movimientos con descarga PDF")
    public void TC0130BSJ() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uLuna2053);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            //fondoComunInversionSteps.selectorCuentaComitente("2-000005885");
            fondoComunInversionSteps.clickIconoAccionesDetalleFCI("A-Delta Pesos");
            fondoComunInversionSteps.clickMiFondo();
            fondoComunInversionSteps.validarTituloPageGenerico("Detalle del fondo");
            detalleDelFondoSteps.validarPantallaDetallaDelFondoMovimientos();
            detalleDelFondoSteps.clickIconoDescargar();
            detalleDelFondoSteps.validarDescarga("Delta Ahorro (A).pdf");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Baja.class})
    @DisplayName("TC-0131-BSJ: FCI  - Movimientos - Filtro de fecha")
    public void TC0131BSJ() {
        try {
            String fDesde = date.toLocalDate().minusDays(date.getDayOfMonth()-1).minusMonths(1).format(formatDate);
            String fHasta = date.toLocalDate().format(formatDate);
            loginSteps.iniciarSessionAlEscritorio(uLuna2053);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            //fondoComunInversionSteps.selectorCuentaComitente("2-000005885");
            fondoComunInversionSteps.clickIconoAccionesDetalleFCI("A-Delta Ahorro");
            fondoComunInversionSteps.clickMiFondo();
            fondoComunInversionSteps.validarTituloPageGenerico("Detalle de fondo");
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
    @DisplayName("TC-0132-BSJ: FCI  - Rendimiento")
    public void TC0132BSJ() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uSanJuan_357);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            //fondoComunInversionSteps.selectorCuentaComitente("2-000005857");
            fondoComunInversionSteps.clickIconoAccionesDetalleFCI("A-Delta Pesos");
            detalleDelFondoSteps.validarTituloPageGenerico("Detalle de fondo");
            detalleDelFondoSteps.validarPantallaDetallaDelFondoRendimiento();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Media.class})
    @DisplayName("TC-0133-BSJ: FCI - Mas información - Aceptar Modal")
    public void TC0133BSJ() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uCuyo2023);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            //fondoComunInversionSteps.selectorCuentaComitente("2-000005871");
            fondoComunInversionSteps.clickIconoAccionesDetalleFCI("A-Delta Pesos");
            fondoComunInversionSteps.clickMasInformacion();
            fondoComunInversionSteps.clickAceptar();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Baja.class})
    @DisplayName("TC-0134-BSJ: FCI  - Mas información - Rechazar Modal")
    public void TC0134BSJ() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uCuyo2023);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            //fondoComunInversionSteps.selectorCuentaComitente("2-000005871");
            fondoComunInversionSteps.clickIconoAccionesDetalleFCI("A-Delta Ahorro Plus");
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
