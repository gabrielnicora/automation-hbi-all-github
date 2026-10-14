package GP.SantaCruz.inversiones;

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
import static utils.Constantes.u23013315;
import static utils.Constantes.uPruebaqaBSC03;

public class FondoComunDeInversionesTest {

    LoginSteps loginSteps = new LoginSteps();
    MenuLateralSteps menuLateralSteps = new MenuLateralSteps();
    InversionesSteps inversionesSteps = new InversionesSteps();
    FondoComunInversionSteps fondoComunInversionSteps = new FondoComunInversionSteps();
    SimularPerfilInversorSteps simularPerfilInversorSteps = new SimularPerfilInversorSteps();
    RescateFCISteps rescateFCISteps = new RescateFCISteps();
    SuscripcionFCISteps suscripcionFCISteps = new SuscripcionFCISteps();
    TicketSteps ticketSteps = new TicketSteps();
    DetalleDelFondoSteps detalleDelFondoSteps = new DetalleDelFondoSteps();
    CalendarioSteps calendarioSteps = new CalendarioSteps();
    LocalDateTime date = LocalDateTime.now();
    DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    BotonesGenericosSteps botonesGenericosSteps = new BotonesGenericosSteps();
    BotonesGenericosPage botonesGenericosPage = new BotonesGenericosPage();
    UtilsGP utilsGP = new UtilsGP();

    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0111-BSF: Suscribir FCI - Validación de pantalla de suscripción de FCI")
    public void TC0111BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uPruebaqaBSC03);
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

    //@Test //No se puede hacer efectiva la suscripción y la casuistica se prueba en el caso TC0111
    @DisplayName("TC-0112-BSC: Suscribir FCI SUPERIOR A 10 MILLONES")
    public void TC0112BSC() {
        try {
            loginSteps.iniciarSesion(u23013315);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            fondoComunInversionSteps.selectorCuentaComitente("2-000970434");
            fondoComunInversionSteps.clickIconoAccionesDetalleFCI("2");
            fondoComunInversionSteps.clickSuscribir();
            suscripcionFCISteps.validarTituloPageGenerico("El monto debe ser mayor o igual a");
            suscripcionFCISteps.setImporteSuscribirFci("amount_sum");
            suscripcionFCISteps.clickContinuarGenerico();
            suscripcionFCISteps.validarTituloPageGenerico("excede tu perfil de riesgo");
            suscripcionFCISteps.validarTituloPageGenerico("Acepto que la operaci");
            suscripcionFCISteps.clickCheckbox();
            suscripcionFCISteps.clickContinuarGenerico();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            //POSTCONDICION
            suscripcionFCISteps.actualizarImporteSuscribir("amount_sum", "+");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0113-BSC: FCI - Rescatar - Importe Parcial - Validación de pantalla")
    public void TC0113BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uPruebaqaBSC03);
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

    @Test
    @Category({Alta.class})
    @DisplayName("TC-0114-BSC: FCI - Rescatar - Importe Total - Validación de pantalla")
    public void TC0114BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uPruebaqaBSC03);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            //fondoComunInversionSteps.selectorCuentaComitente("2-000970406");
            //fondoComunInversionSteps.clickContinuarGenerico();
            fondoComunInversionSteps.clickIconoAccionesDetalleFCI("A-Delta Pesos");
            fondoComunInversionSteps.clickRescate();
            rescateFCISteps.validarTituloPageGenerico("Rescate de FCI");
            rescateFCISteps.clickTotal();
            rescateFCISteps.clickContinuarGenerico();
            rescateFCISteps.clickCheckTerminosCondicionesGenerico();
            rescateFCISteps.validarTituloPageGenerico("Confirmar rescate de FCI");
            rescateFCISteps.validacionPantallaConfirmacionRescate();
            rescateFCISteps.validaBotonContinuarRescateFCI();
            botonesGenericosSteps.validarBotonConfirmar();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Alta.class})
    @DisplayName("TC-0115-BSC: FCI - Simular perfil de inversor sin seleccionar cuenta comitente")
    public void TC0115BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uPruebaqaBSC03);
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

    //@Test //comentadom realiza la misma accion que el caso 115
    @DisplayName("TC-0116-BSC: FCI - Simular perfil de inversor seleccionando cuenta comitente")
    public void TC0116BSC() {
        try {
            loginSteps.iniciarSesion(u23013315);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            fondoComunInversionSteps.selectorCuentaComitente("2-000970434");
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
    @DisplayName("TC-117-BSC: FCI- Filtros - Mi Tenencia - Fondos por Riesgo")
    public void TC0117BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uPruebaqaBSC03);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            //fondoComunInversionSteps.selectorCuentaComitente("2-000970406");
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
    @DisplayName("TC-118-BSC: FCI- Filtros - Mi Tenencia - Fondos por Riesgo - Conservador")
    public void TC0118BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(u23013315);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
           // fondoComunInversionSteps.selectorCuentaComitente("2-000970434");
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
    @DisplayName("TC-119-BSC: FCI- Filtros - Mi Tenencia - Fondos por Riesgo - Moderado")
    public void TC0119BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(u23013315);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            //fondoComunInversionSteps.selectorCuentaComitente("2-000970434");
            fondoComunInversionSteps.clickLabelMisFondos();
            fondoComunInversionSteps.seleccionarUnicamenteFiltrosPorRiesgo(FiltroRiesgoFci.MODERADO);
            fondoComunInversionSteps.validaFondosPorRiesgoVisiblesEnListado("Moderado");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test //Sin datos al aplicar MI TENENCIA
    @Category({Baja.class})
    @DisplayName("TC-120-BSC: FCI- Filtros - Mi Tenencia - Fondos por Riesgo - Dinámico")
    public void TC0120BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(u23013315);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            fondoComunInversionSteps.clickLabelMisFondos();
           // fondoComunInversionSteps.selectorCuentaComitente("2-000970434");
            fondoComunInversionSteps.seleccionarUnicamenteFiltrosPorRiesgo(FiltroRiesgoFci.DINAMICO);
            fondoComunInversionSteps.validaFondosPorRiesgoVisiblesEnListado("Dinamico");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Media.class})
    @DisplayName("TC-121-BSC: FCI- Filtros - Fondos por Riesgo")
    public void TC0121BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(u23013315);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            //fondoComunInversionSteps.selectorCuentaComitente("2-000970434");
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
    @DisplayName("TC-122-BSC: FCI- Filtros - Fondos por Riesgo - Conservador")
    public void TC0122BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(u23013315);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            //fondoComunInversionSteps.selectorCuentaComitente("2-000970434");
            fondoComunInversionSteps.seleccionarUnicamenteFiltrosPorRiesgo(FiltroRiesgoFci.CONSERVADOR);
            fondoComunInversionSteps.validaFondosPorRiesgoVisiblesEnListado("Conservador");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test// no cuenta con datos al aplicar filtro en perfil moderado.
    @Category({Baja.class})
    @DisplayName("TC-123-BSC: FCI- Filtros - Fondos por Riesgo - Moderado")
    public void TC0123BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(u23013315);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            //No cuenta con dicha opcion
            //fondoComunInversionSteps.clickSelectorCuentaComitente("2-000024701");
            fondoComunInversionSteps.seleccionarUnicamenteFiltrosPorRiesgo(FiltroRiesgoFci.MODERADO);
            fondoComunInversionSteps.validaFondosPorRiesgoVisiblesEnListado("Moderado");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Baja.class})
    @DisplayName("TC-124-BSC: FCI- Filtros - Fondos por Riesgo - Dinamico")
    public void TC0124BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(u23013315);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            //No cuenta con dicha opcion
            //fondoComunInversionSteps.clickSelectorCuentaComitente("2-000024701");
            fondoComunInversionSteps.seleccionarUnicamenteFiltrosPorRiesgo(FiltroRiesgoFci.DINAMICO);
            fondoComunInversionSteps.validaFondosPorRiesgoVisiblesEnListado("Dinamico");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Alta.class})
    @DisplayName("TC-0125-BSC: FCI - Solicitar baja cuenta comitente - Validación de pantalla")
    public void TC0125BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(u23013315);
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
    @DisplayName("TC-126-BSC: FCI - Filtros: Fondos por moneda - Pesos")
    public void TC0126BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(u23013315);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            //fondoComunInversionSteps.selectorCuentaComitente("2-000970434");
            fondoComunInversionSteps.seleccionarUnicamenteFiltrosPorMoneda(FiltroMonedaFci.PESOS);
            fondoComunInversionSteps.validaFondosPorMonedaEnListado("pesos");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Baja.class})
    @DisplayName("TC-127-BSC: FCI - Filtros: Fondos por moneda - Dólar")
    public void TC0127BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(u23013315);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
           // fondoComunInversionSteps.selectorCuentaComitente("2-000970434");
            fondoComunInversionSteps.seleccionarUnicamenteFiltrosPorMoneda(FiltroMonedaFci.DOLARES);
            fondoComunInversionSteps.validaFondosPorMonedaEnListado("dolar");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Media.class})
    @DisplayName("TC-128-BSC: FCI - Filtros: Mi Tenencia")
    public void TC0128BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(u23013315);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
           // fondoComunInversionSteps.selectorCuentaComitente("2-000970434");
            fondoComunInversionSteps.clickLabelMisFondos();
            fondoComunInversionSteps.validaListadoFCI();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Alta.class})
    @DisplayName("TC-129-BSC: FCI  - Movimientos")
    public void TC0129BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(u23013315);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            //fondoComunInversionSteps.clickSelectorCuentaComitente("2-000024701");
            fondoComunInversionSteps.clickIconoAccionesDetalleFCI("A-Delta Ahorro");
            fondoComunInversionSteps.clickMiFondo();
            fondoComunInversionSteps.validarTituloPageGenerico("Detalle del fondo");
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
    @DisplayName("TC-130-BSC: FCI  - Movimientos con descarga PDF")
    public void TC0130BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(u23013315);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            //fondoComunInversionSteps.selectorCuentaComitente("2-000970434");
            fondoComunInversionSteps.clickIconoAccionesDetalleFCI("A-Delta Pesos");
            fondoComunInversionSteps.clickMiFondo();
            fondoComunInversionSteps.validarTituloPageGenerico("Detalle del fondo");
            detalleDelFondoSteps.validarPantallaDetallaDelFondoMovimientos();
            detalleDelFondoSteps.clickIconoDescargar("1");
            detalleDelFondoSteps.validarDescarga("DELTA PESOS (A).pdf");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Baja.class})
    @DisplayName("TC-131-BSC: FCI  - Movimientos - Filtro de fecha")
    public void TC0131BSC() {
        try {
            String fDesde = date.toLocalDate().minusDays(date.getDayOfMonth() - 1).minusMonths(1).format(formatDate);
            String fHasta = date.toLocalDate().format(formatDate);
            loginSteps.iniciarSessionAlEscritorio(u23013315);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            //fondoComunInversionSteps.selectorCuentaComitente("2-000970434");
            fondoComunInversionSteps.clickIconoAccionesDetalleFCI("A-Delta Pesos");
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
    @DisplayName("TC-132-BSC: FCI  - Rendimiento")
    public void TC0132BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(u23013315);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            //fondoComunInversionSteps.selectorCuentaComitente("2-000970434");
            fondoComunInversionSteps.clickIconoAccionesDetalleFCI("A-Delta Pesos");
            detalleDelFondoSteps.validarTituloPageGenerico("Detalle del fondo");
            detalleDelFondoSteps.validarPantallaDetallaDelFondoRendimiento();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Media.class})
    @DisplayName("TC-133-BSC: FCI  - Mas información - Aceptar Modal")
    public void TC0133BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(u23013315);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            //fondoComunInversionSteps.selectorCuentaComitente("2-000970434");
            fondoComunInversionSteps.clickIconoAccionesDetalleFCI("A-Delta Ahorro");
            fondoComunInversionSteps.clickMasInformacion();
            fondoComunInversionSteps.clickAceptar();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Baja.class})
    @DisplayName("TC-134-BSC: FCI  - Mas información - Rechazar Modal")
    public void TC0134BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(u23013315);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
           //fondoComunInversionSteps.selectorCuentaComitente("2-000970434");
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
