package GP.EntreRios.inversiones;

import GP.Tags.Alta;
import GP.Tags.Baja;
import GP.Tags.Criticos;
import GP.Tags.Media;
import db.ConexionDB;
import enums.inversiones.fci.FiltroMonedaFci;
import enums.inversiones.fci.FiltroRiesgoFci;
import io.qameta.allure.Allure;
import io.qameta.allure.junit4.DisplayName;
import org.junit.*;
import org.junit.experimental.categories.Category;
import pages.botonesgenericos.BotonesGenericosPage;
import steps.botonesgenericos.BotonesGenericosSteps;
import steps.calendarios.CalendarioSteps;
import steps.inversiones.InversionesSteps;
import steps.inversiones.fci.*;
import steps.login.LoginSteps;
import steps.menulateral.MenuLateralSteps;
import steps.ticket.TicketSteps;
import utils.Constantes;
import utils.TestCaseData;
import utils.UtilsGP;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static db.ConexionDB.cerrarConexion;
import static utils.Constantes.uAngela01;
import static utils.Constantes.uBerhometest15;

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
    SuscripcionFCISteps suscripcionFCISteps= new SuscripcionFCISteps();
    TicketSteps ticketSteps= new TicketSteps();
    BotonesGenericosSteps botonesGenericosSteps = new BotonesGenericosSteps();
    BotonesGenericosPage botonesGenericosPage = new BotonesGenericosPage();
    UtilsGP utilsGP = new UtilsGP();
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
    @DisplayName("TC-0111-BSF: Suscribir FCI - Validación de pantalla de suscripción de FCI")
    public void TC0111BERSA() {
        try {
            testCaseData.cargaDeDatosTest(uBerhometest15);
            loginSteps.iniciarSessionAlEscritorioConPass(uBerhometest15,testCaseData.password);
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
    //@Test
    @DisplayName("TC-0112-BERSA: Suscribir FCI SUPERIOR A 10 MILLONES")
    public void TC0112BERSA() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uAngela01);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            fondoComunInversionSteps.selectorCuentaComitente("2-000341434");
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
    @DisplayName("TC-0113-BERSA: FCI - Rescatar - Importe Parcial - Validación de pantalla")
    public void TC0113BERSA() {
        try {
            testCaseData.cargaDeDatosTest(uBerhometest15);
            loginSteps.iniciarSessionAlEscritorioConPass(uBerhometest15,testCaseData.password);
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
    @DisplayName("TC-0114-BERSA: FCI - Rescatar - Importe Total - Validación de pantalla")
    public void TC0114BERSA() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uAngela01);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            fondoComunInversionSteps.selectorCuentaComitente("2-000341434");
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
    @DisplayName("TC-0115-BERSA: FCI - Simular perfil de inversor sin seleccionar cuenta comitente")
    public void TC0115BERSA() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uAngela01);
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
            //simularPerfilInversorSteps.validaNotificacionEmergenteGenerico("Tu operacion ya fue realizada con exito");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    //@Test //CASO COMENTADO PORQUE SE ELIMINA STEP QUE ELIGE CTA COMITENTE
    @DisplayName("TC-0116-BERSA: FCI - Simular perfil de inversor seleccionando cuenta comitente")
    public void TC0116BERSA() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uAngela01);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            fondoComunInversionSteps.selectorCuentaComitente("2-000341434");
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

    @Test //Falta de datos, no hay registro de fondo "Moderado" en mis fondos
    @Category({Media.class})
    @DisplayName("TC-117-BERSA: FCI- Filtros - Mi Tenencia - Fondos por Riesgo")
    public void TC0117BERSA() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uAngela01);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            fondoComunInversionSteps.selectorCuentaComitente("2-000341434");
            fondoComunInversionSteps.clickLabelMisFondos();
            fondoComunInversionSteps.seleccionarUnicamenteFiltrosPorRiesgo(
                    FiltroRiesgoFci.CONSERVADOR,
                    FiltroRiesgoFci.MODERADO,
                    FiltroRiesgoFci.DINAMICO
            );
            fondoComunInversionSteps.validaListadoFCI();
            /*fondoComunInversionSteps.validaFondoPorRiesgoEnListado("Conservador");
            fondoComunInversionSteps.validaFondoPorRiesgoEnListado("Moderado");
            fondoComunInversionSteps.validaFondoPorRiesgoEnListado("Din");*/
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Baja.class})
    @DisplayName("TC-118-BERSA: FCI- Filtros - Mi Tenencia - Fondos por Riesgo - Conservador")
    public void TC0118BERSA() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uAngela01);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            fondoComunInversionSteps.selectorCuentaComitente("2-000341434");
            fondoComunInversionSteps.clickLabelMisFondos();
            fondoComunInversionSteps.seleccionarUnicamenteFiltrosPorRiesgo(FiltroRiesgoFci.CONSERVADOR);
            fondoComunInversionSteps.validaFondosPorRiesgoVisiblesEnListado("Conservador");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test //Falta de datos, no hay registro de fondo "Moderado" en mis fondos
    @Category({Baja.class})
    @DisplayName("TC-119-BERSA: FCI- Filtros - Mi Tenencia - Fondos por Riesgo - Moderado")
    public void TC0119BERSA() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uAngela01);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            fondoComunInversionSteps.selectorCuentaComitente("2-000341434");
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
    @DisplayName("TC-120-BERSA: FCI- Filtros - Mi Tenencia - Fondos por Riesgo - Dinamico")
    public void TC0120BERSA() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uAngela01);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            fondoComunInversionSteps.selectorCuentaComitente("2-000341434");
            fondoComunInversionSteps.clickLabelMisFondos();
            fondoComunInversionSteps.seleccionarUnicamenteFiltrosPorRiesgo(FiltroRiesgoFci.DINAMICO);
            fondoComunInversionSteps.validaFondosPorRiesgoVisiblesEnListado("Dinamico");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test //Falta de datos, no hay registro de fondo "Moderado"
    @Category({Media.class})
    @DisplayName("TC-121-BERSA: FCI- Filtros - Fondos por Riesgo")
    public void TC0121BERSA() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uAngela01);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            fondoComunInversionSteps.selectorCuentaComitente("2-000341434");
            fondoComunInversionSteps.seleccionarUnicamenteFiltrosPorRiesgo(
                    FiltroRiesgoFci.CONSERVADOR,
                    FiltroRiesgoFci.MODERADO,
                    FiltroRiesgoFci.DINAMICO
            );
            fondoComunInversionSteps.validaListadoFCI();
            fondoComunInversionSteps.validaFondoPorRiesgoEnListado("Conservador");
            fondoComunInversionSteps.validaFondoPorRiesgoEnListado("Moderado");
            fondoComunInversionSteps.validaFondoPorRiesgoEnListado("Din");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Baja.class})
    @DisplayName("TC-122-BERSA: FCI- Filtros - Fondos por Riesgo - Conservador")
    public void TC0122BERSA() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uAngela01);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            fondoComunInversionSteps.selectorCuentaComitente("2-000341434");
            fondoComunInversionSteps.seleccionarUnicamenteFiltrosPorRiesgo(FiltroRiesgoFci.CONSERVADOR);
            fondoComunInversionSteps.validaFondosPorRiesgoVisiblesEnListado("Conservador");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test //Falta de datos, no hay registro de fondo "Moderado"
    @Category({Baja.class})
    @DisplayName("TC-123-BERSA: FCI- Filtros - Fondos por Riesgo - Moderado")
    public void TC0123BERSA() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uAngela01);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            fondoComunInversionSteps.selectorCuentaComitente("2-000341434");
            fondoComunInversionSteps.seleccionarUnicamenteFiltrosPorRiesgo(FiltroRiesgoFci.MODERADO);
            fondoComunInversionSteps.validaFondosPorRiesgoVisiblesEnListado("Moderado");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Baja.class})
    @DisplayName("TC-124-BERSA: FCI- Filtros - Fondos por Riesgo - Dinamico")
    public void TC0124BERSA() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uAngela01);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            fondoComunInversionSteps.selectorCuentaComitente("2-000341434");
            fondoComunInversionSteps.seleccionarUnicamenteFiltrosPorRiesgo(FiltroRiesgoFci.DINAMICO);
            fondoComunInversionSteps.validaFondosPorRiesgoVisiblesEnListado("Dinamico");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Alta.class})
    @DisplayName("TC-0125-BERSA: FCI - Solicitar baja cuenta comitente - Validación de pantalla")
    public void TC0125BERSA() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uAngela01);
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
    @DisplayName("TC-126-BERSA: FCI - Filtros: Fondos por moneda - Pesos")
    public void TC0126BERSA() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uAngela01);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            fondoComunInversionSteps.selectorCuentaComitente("2-000341434");
            fondoComunInversionSteps.seleccionarUnicamenteFiltrosPorMoneda(FiltroMonedaFci.PESOS);
            fondoComunInversionSteps.validaFondosPorMonedaEnListado("pesos");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test //No hay registro de fondos en dolar
    @Category({Baja.class})
    @DisplayName("TC-127-BERSA: FCI - Filtros: Fondos por moneda - Dolar")
    public void TC0127BERSA() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uAngela01);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            fondoComunInversionSteps.selectorCuentaComitente("2-000341434");
            fondoComunInversionSteps.seleccionarUnicamenteFiltrosPorMoneda(FiltroMonedaFci.DOLARES);
            fondoComunInversionSteps.validaFondosPorMonedaEnListado("dolar");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Media.class})
    @DisplayName("TC-128-BERSA: FCI - Filtros: Mi Tenencia")
    public void TC0128BERSA() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uAngela01);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            fondoComunInversionSteps.selectorCuentaComitente("2-000341434");
            fondoComunInversionSteps.clickLabelMisFondos();
            fondoComunInversionSteps.validaListadoFCI();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Alta.class})
    @DisplayName("TC-129-BERSA: FCI  - Movimientos")
    public void TC0129BERSA() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uAngela01);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            fondoComunInversionSteps.selectorCuentaComitente("2-000341434");
            fondoComunInversionSteps.clickIconoAccionesDetalleFCI("3");
            fondoComunInversionSteps.clickMiFondo();
           // fondoComunInversionSteps.validarTituloPageGenerico("Detalle del fondo");
            detalleDelFondoSteps.validarPantallaDetallaDelFondoMovimientos();
            detalleDelFondoSteps.validarTituloPageGenerico("Fecha de concertaci");
            detalleDelFondoSteps.validarTituloPageGenerico("Fecha de liquidaci");
            detalleDelFondoSteps.validarTituloPageGenerico("Nombre del fondo");
            detalleDelFondoSteps.validarTituloPageGenerico("Operaci");
            //detalleDelFondoSteps.validarTituloPageGenerico("Cuotapartes");
            detalleDelFondoSteps.validarTituloPageGenerico("Cuotapartes");
            detalleDelFondoSteps.validarTituloPageGenerico("Importe");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Media.class})
    @DisplayName("TC-130-BERSA: FCI  - Movimientos con descarga PDF")
    public void TC0130BERSA() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uAngela01);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            fondoComunInversionSteps.selectorCuentaComitente("2-000341434");
            fondoComunInversionSteps.clickIconoAccionesDetalleFCI("3");
            fondoComunInversionSteps.clickMiFondo();
            fondoComunInversionSteps.validarTituloPageGenerico("Detalle del fondo");
            detalleDelFondoSteps.validarPantallaDetallaDelFondoMovimientos();
            detalleDelFondoSteps.clickFechaDesde();
            calendarioSteps.clickMesAnterior(4);
            calendarioSteps.clickEnDiaXParametro("5");
            detalleDelFondoSteps.clickAplicar();
            detalleDelFondoSteps.clickIconoDescargar();
            Thread.sleep(2000);
            detalleDelFondoSteps.validarDescarga("Delta Ahorro Plus");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Baja.class})
    @DisplayName("TC-131-BERSA: FCI  - Movimientos - Filtro de fecha")
    public void TC0131BERSA() {
        try {
            String fDesde = date.toLocalDate().minusDays(date.getDayOfMonth() - 1).minusMonths(1).format(formatDate);
            String fHasta = date.toLocalDate().format(formatDate);
            loginSteps.iniciarSessionAlEscritorio(uAngela01);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            fondoComunInversionSteps.selectorCuentaComitente("2-000341434");
            fondoComunInversionSteps.clickIconoAccionesDetalleFCI("3");
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
    @DisplayName("TC-132-BERSA: FCI  - Rendimiento")
    public void TC0132BERSA() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uAngela01);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            //fondoComunInversionSteps.selectorCuentaComitente("2-000341434");
            fondoComunInversionSteps.clickIconoAccionesDetalleFCI("3");
            detalleDelFondoSteps.validarTituloPageGenerico("Detalle del fondo");
            detalleDelFondoSteps.validarPantallaDetallaDelFondoRendimiento();

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Media.class})
    @DisplayName("TC-133-BERSA: FCI  - Mas información - Aceptar Modal")
    public void TC0133BERSA() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uAngela01);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            fondoComunInversionSteps.selectorCuentaComitente("2-000341434");
            fondoComunInversionSteps.clickIconoAccionesDetalleFCI("3");
            fondoComunInversionSteps.clickMasInformacion();
            fondoComunInversionSteps.clickAceptar();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Baja.class})
    @DisplayName("TC-134-BERSA: FCI  - Mas información - Rechazar Modal")
    public void TC0134BERSA() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uAngela01);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickFondoComunDeInversion();
            //fondoComunInversionSteps.selectorCuentaComitente("2-000341434");
            fondoComunInversionSteps.clickIconoAccionesDetalleFCI("3");
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
        //cerrarConexion();
    }
}
