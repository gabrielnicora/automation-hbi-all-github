package GP.SanJuan.herramientas_de_accesibilidad;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import steps.herramientas_de_accesibilidad.HerramientasDeAccesibilidadSteps;
import steps.herramientas_de_accesibilidad.TransferenciasHerramientasAccesibilidadSteps;
import steps.login.LoginSteps;
import steps.menulateral.MenuLateralSteps;
import steps.ticket.TicketSteps;
import steps.transferencias.TransferenciasSteps;

import java.io.IOException;

import static db.ConexionDB.cerrarConexion;
import static utils.Constantes.uCuyo2021;

public class TransferenciasHerramientasDeAccesibilidadTest {
    LoginSteps loginSteps= new LoginSteps();
    HerramientasDeAccesibilidadSteps herramientasDeAccesibilidadSteps= new HerramientasDeAccesibilidadSteps();
    MenuLateralSteps menuLateralSteps= new MenuLateralSteps();
    TransferenciasHerramientasAccesibilidadSteps transferenciasHerramientasAccesibilidadSteps= new TransferenciasHerramientasAccesibilidadSteps();
    TicketSteps ticketSteps= new TicketSteps();
    TransferenciasSteps transferenciasSteps= new TransferenciasSteps();

    @Test
    @DisplayName("TC-0276-BSJ: Herramientas de Accesibilidad Transferencias- Ingresar a Transferencias con las herramientas de accesibilidad- Validar que se redirige a Transferencias")
    public void TC0276BSJ() {
        try {
            loginSteps.iniciarSesion(uCuyo2021);
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            transferenciasHerramientasAccesibilidadSteps.ingresarATransferencias();
            herramientasDeAccesibilidadSteps.validarTituloPageGenerico("Transferencias");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    //@Test No se puede ingresar a transferencias a un contacto agendado
    @DisplayName("TC-0277-BSJ: Herramientas de Accesibilidad Transferencias- Ingresar a Transferencias Inmediatas a Terceros- Realizar el flujo completo de una transferencia -Validar que se redirige a pantalla de Confirmacion")
    public void TC0277BSJ() {
        try {
            loginSteps.iniciarSesion(uCuyo2021);
            menuLateralSteps.clickTransferencias("","");
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            transferenciasHerramientasAccesibilidadSteps.ingresarATransferenciasInmediatas();
            herramientasDeAccesibilidadSteps.validarTituloPageGenerico("Transferencias  inmediatas");
            transferenciasHerramientasAccesibilidadSteps.completarFormularioTransferencia();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0278-BSJ: Herramientas de Accesibilidad Transferencias- Ingresar a Agenda de Destinatarios - Filtrar por Cuentas terceros - Verificar radio 'Cuentas terceros' presionado")
    public void TC0278BSJ() {
        try {
            loginSteps.iniciarSesion(uCuyo2021);
            menuLateralSteps.clickTransferencias("","");
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            transferenciasHerramientasAccesibilidadSteps.ingresarAgendaDestinatarios();
            transferenciasHerramientasAccesibilidadSteps.presionarFiltroCuentasTerceros();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0279-BSJ: Herramientas de Accesibilidad Transferencias- Ingresar a Agenda de Destinatarios - Filtrar por Cuentas Propias- Verificar radio 'Cuentas propias' presionado")
    public void TC0279BSJ() {
        try {
            loginSteps.iniciarSesion(uCuyo2021);
            menuLateralSteps.clickTransferencias("","");
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            transferenciasHerramientasAccesibilidadSteps.ingresarAgendaDestinatarios();
            transferenciasHerramientasAccesibilidadSteps.presionarFiltroCuentasPropias();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0280-BSJ: Herramientas de Accesibilidad Transferencias- Ingresar a Historico de Transferencias - Validar que se redirige a la pantalla Listado de transferencias")
    public void TC0280BSJ() {
        try {
            loginSteps.iniciarSesion(uCuyo2021);
            menuLateralSteps.clickTransferencias("","");
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            transferenciasHerramientasAccesibilidadSteps.ingresarHistoricoTransferencias();
            transferenciasHerramientasAccesibilidadSteps.validarTituloPageGenerico("Listado de transferencias");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0281-BSJ: Herramientas de Accesibilidad Transferencias- Ingresar a Transferencias de importe superior - Validar que se redirige a la pantalla Transferencia de importe superior")
    public void TC0281BSJ() {
        try {
            loginSteps.iniciarSesion(uCuyo2021);
            menuLateralSteps.clickTransferencias("","");
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            transferenciasHerramientasAccesibilidadSteps.ingresarATransferenciasImporteSuperior();
            transferenciasHerramientasAccesibilidadSteps.validarTituloPageGenerico("Transferencia de importe superior");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0282-BSJ: Herramientas de Accesibilidad Transferencias- Ingresar a Limite de transferencia - Validar que se redirige a la pantalla Limite de transferencia")
    public void TC0282BSJ() {
        try {
            loginSteps.iniciarSesion(uCuyo2021);
            menuLateralSteps.clickTransferencias("","");
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            transferenciasHerramientasAccesibilidadSteps.ingresarALimiteDeTransferencia();
            transferenciasHerramientasAccesibilidadSteps.validarTituloPageGenerico("Límite de transferencia");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0283-BSJ: Herramientas de Accesibilidad Transferencias- Ingresar a Gestionar Limite de transferencia - Validar que se redirige a la pantalla 'Cambio de límite de transferencias'")
    public void TC0283BSJ() {
        try {
            loginSteps.iniciarSesion(uCuyo2021);
            menuLateralSteps.clickTransferencias("","");
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            transferenciasHerramientasAccesibilidadSteps.ingresarAGestionarLimiteDeTransferencia();
            transferenciasHerramientasAccesibilidadSteps.validarTituloPageGenerico("Cambio de límite de transferencias");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0284-BSJ: Herramientas de Accesibilidad Transferencias- Ingresar a Consultar Credin - Validar que se redirige a la pantalla 'Consulta de créditos en línea'")
    public void TC0284BSJ() {
        try {
            loginSteps.iniciarSesion(uCuyo2021);
            menuLateralSteps.clickTransferencias("","");
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            transferenciasHerramientasAccesibilidadSteps.ingresarAConsultarCredin();
            transferenciasHerramientasAccesibilidadSteps.validarTituloPageGenerico("Consulta de créditos en línea");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0285- BSF- Herramientas de Accesibilidad Transferencias- Ingresar a Consultar Credin - Generar un credin - Validar que se redirige a pantalla de Credin ")
    public void TC0285BSJ() {
        try {
            loginSteps.iniciarSesion(uCuyo2021);
            menuLateralSteps.clickTransferencias("","");
            transferenciasSteps.clickMasOpcionesGenerico();
            transferenciasSteps.clickConsultarCredin();
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            transferenciasHerramientasAccesibilidadSteps.ingresarAGenerarCredin();
            transferenciasHerramientasAccesibilidadSteps.validarTituloPageGenerico("Generar CREDIN");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0286-BSJ: Herramientas de Accesibilidad Transferencias- Ingresar a Transferencias Programadas- Validar que se redirige a pantalla de Transferencias Programadas")
    public void TC0286BSJ() {
        try {
            loginSteps.iniciarSesion(uCuyo2021);
            menuLateralSteps.clickTransferencias("","");
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            transferenciasHerramientasAccesibilidadSteps.ingresarATransferenciasProgramadas();
            transferenciasHerramientasAccesibilidadSteps.validarTituloPageGenerico("Transferencias programadas");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @After
    public void teardown() throws IOException, InterruptedException {
        global.practia.jemeinsamstark.web.core.WebDriver.closeDriver();
        cerrarConexion();
    }
}
