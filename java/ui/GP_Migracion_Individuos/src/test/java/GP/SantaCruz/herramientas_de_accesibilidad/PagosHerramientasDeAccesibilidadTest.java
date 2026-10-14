package GP.SantaCruz.herramientas_de_accesibilidad;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import steps.herramientas_de_accesibilidad.HerramientasDeAccesibilidadSteps;
import steps.herramientas_de_accesibilidad.PagosHerramientasAccesibilidadSteps;
import steps.login.LoginSteps;
import steps.menulateral.MenuLateralSteps;
import steps.ticket.TicketSteps;

import java.io.IOException;

import static db.ConexionDB.cerrarConexion;
import static utils.Constantes.uBsc13400488;

public class PagosHerramientasDeAccesibilidadTest {
    LoginSteps loginSteps= new LoginSteps();
    HerramientasDeAccesibilidadSteps herramientasDeAccesibilidadSteps= new HerramientasDeAccesibilidadSteps();
    MenuLateralSteps menuLateralSteps= new MenuLateralSteps();
    TicketSteps ticketSteps= new TicketSteps();
    PagosHerramientasAccesibilidadSteps pagosHerramientasAccesibilidadSteps= new PagosHerramientasAccesibilidadSteps();

    @Test
    @DisplayName("TC-0288-BSC: Herramientas de Accesibilidad Pagos- Ingresar a Pagos con las herramientas de accesibilidad- Validar que se redirige a la pantalla de 'Próximos Vencimientos'")
    public void TC0288BSC() {
        try {
            loginSteps.iniciarSesion(uBsc13400488);
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            pagosHerramientasAccesibilidadSteps.ingresarAPagos();
            herramientasDeAccesibilidadSteps.validarTituloPageGenerico("Próximos Vencimientos");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0289-BSC: Herramientas de Accesibilidad Pagos- Pagos - Ingresar a 'Nuevo Pago'- Validar que se redirige a la pantalla de 'Nuevo pago'")
    public void TC0289BSC() {
        try {
            loginSteps.iniciarSesion(uBsc13400488);
            menuLateralSteps.clickPagos();
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            pagosHerramientasAccesibilidadSteps.ingresarANuevoPago();
            herramientasDeAccesibilidadSteps.validarTituloPageGenerico("Nuevo pago");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0290-BSC: Herramientas de Accesibilidad Pagos- Pagos - Ingresar a 'Comprobantes'- Validar que se redirige a la pantalla de 'Comprobantes de pago'")
    public void TC0290BSC() {
        try {
            loginSteps.iniciarSesion(uBsc13400488);
            menuLateralSteps.clickPagos();
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            pagosHerramientasAccesibilidadSteps.ingresarAComprobantes();
            herramientasDeAccesibilidadSteps.validarTituloPageGenerico("Comprobantes de pago");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0291-BSC: Herramientas de Accesibilidad Pagos- Pagos - Ingresar a 'Adherir impuesto/servicio'- Validar que se redirige a pantalla 'Adherir servicio'")
    public void TC0291BSC() {
        try {
            loginSteps.iniciarSesion(uBsc13400488);
            menuLateralSteps.clickPagos();
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            pagosHerramientasAccesibilidadSteps.ingresarAdherirImpuesto();
            herramientasDeAccesibilidadSteps.validarTituloPageGenerico("Adherir servicio");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0292-BSC: Herramientas de Accesibilidad Pagos- Pagos - Ingresar a 'Nuevo Pago'- Completar formulario- Validar que se habilita input segundo factor")
    public void TC0292BSC() {
        try {
            loginSteps.iniciarSesion(uBsc13400488);
            menuLateralSteps.clickPagos();
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            pagosHerramientasAccesibilidadSteps.ingresarANuevoPago();
            herramientasDeAccesibilidadSteps.validarTituloPageGenerico("Nuevo pago");
            pagosHerramientasAccesibilidadSteps.completarFormularioNuevoPago();
            pagosHerramientasAccesibilidadSteps.validarInputSegundoFactor();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0293-BSC: Herramientas de Accesibilidad Pagos- Pagos - Ingresar a 'Servicios Adheridos'- Validar que se redirige a la pantalla de 'Servicios adheridos'")
    public void TC0293BSC() {
        try {
            loginSteps.iniciarSesion(uBsc13400488);
            menuLateralSteps.clickPagos();
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            pagosHerramientasAccesibilidadSteps.ingresarAServiciosAdheridos();
            herramientasDeAccesibilidadSteps.validarTituloPageGenerico("Servicios adheridos");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0294-BSC: Herramientas de Accesibilidad Pagos- Pagos - Ingresar a 'Pago de VEP'- Validar que se redirige a la pantalla de 'Pago de VEP (AFIP)'")
    public void TC0294BSC() {
        try {
            loginSteps.iniciarSesion(uBsc13400488);
            menuLateralSteps.clickPagos();
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            pagosHerramientasAccesibilidadSteps.ingresarAPagosVep();
            herramientasDeAccesibilidadSteps.validarTituloPageGenerico("Pago de VEP (AFIP)");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0295-BSC: Herramientas de Accesibilidad Pagos- Pagos - Ingresar a 'Debin vendedor'- Validar que se redirige a la pantalla de 'Solicitudes realizadas de DEBIN vendedor'")
    public void TC0295BSC() {
        try {
            loginSteps.iniciarSesion(uBsc13400488);
            menuLateralSteps.clickPagos();
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            pagosHerramientasAccesibilidadSteps.ingresarADebinVendedor();
            herramientasDeAccesibilidadSteps.validarTituloPageGenerico("Solicitudes realizadas de DEBIN vendedor");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0296-BSC: Herramientas de Accesibilidad Pagos- Debin vendedor- Ingresar a 'Generar orden debin'- Validar que se redirige a la pantalla de 'Generar orden de DEBIN'")
    public void TC0296BSC() {
        try {
            loginSteps.iniciarSesion(uBsc13400488);
            menuLateralSteps.clickPagos();
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            pagosHerramientasAccesibilidadSteps.ingresarADebinVendedor();
            pagosHerramientasAccesibilidadSteps.ingresarAGenerarOrdenDebin();
            herramientasDeAccesibilidadSteps.validarTituloPageGenerico("Generar orden de DEBIN");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0297-BSC: Herramientas de Accesibilidad Pagos- Debin vendedor- Ingresar a 'Consultar Adhesiones'- Validar que se redirige a la pantalla de 'Cuentas adheridas a DEBIN'")
    public void TC0297BSC() {
        try {
            loginSteps.iniciarSesion(uBsc13400488);
            menuLateralSteps.clickPagos();
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            pagosHerramientasAccesibilidadSteps.ingresarADebinVendedor();
            pagosHerramientasAccesibilidadSteps.ingresarAConsultarAdhesiones();
            herramientasDeAccesibilidadSteps.validarTituloPageGenerico("Cuentas adheridas a DEBIN");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0298-BSC: Herramientas de Accesibilidad Pagos- Pagos - Ingresar a 'Debin comprador'- Validar que se redirige a la pantalla de 'Gestión de solicitudes de DEBIN recibidas'")
    public void TC0298BSC() {
        try {
            loginSteps.iniciarSesion(uBsc13400488);
            menuLateralSteps.clickPagos();
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            pagosHerramientasAccesibilidadSteps.ingresarADebinComprador();
            herramientasDeAccesibilidadSteps.validarTituloPageGenerico("Gestión de solicitudes de DEBIN recibidas");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0299-BSC: Herramientas de Accesibilidad Pagos- Debin comprador- Ingresar a 'Adherir recurrencia'- Validar que se redirige a la pantalla de 'Adherir recurrencia'")
    public void TC0299BSC() {
        try {
            loginSteps.iniciarSesion(uBsc13400488);
            menuLateralSteps.clickPagos();
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            pagosHerramientasAccesibilidadSteps.ingresarADebinComprador();
            pagosHerramientasAccesibilidadSteps.ingresarAdherirRecurrencias();
            herramientasDeAccesibilidadSteps.validarTituloPageGenerico("Adherir recurrencia");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0300-BSC: Herramientas de Accesibilidad Pagos- Debin comprador- Ingresar a 'Consultar recurrencias'- Validar que se redirige a la pantalla de 'Consulta de recurrencias'")
    public void TC0300BSC() {
        try {
            loginSteps.iniciarSesion(uBsc13400488);
            menuLateralSteps.clickPagos();
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            pagosHerramientasAccesibilidadSteps.ingresarADebinComprador();
            pagosHerramientasAccesibilidadSteps.ingresarConsultarRecurrencias();
            herramientasDeAccesibilidadSteps.validarTituloPageGenerico("Consulta de recurrencias");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0301-BSC: Herramientas de Accesibilidad Pagos- Debin comprador- Ingresar a 'Consultar contracargos'- Validar que se redirige a la pantalla de 'Consulta de contracargos'")
    public void TC0301BSC() {
        try {
            loginSteps.iniciarSesion(uBsc13400488);
            menuLateralSteps.clickPagos();
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            pagosHerramientasAccesibilidadSteps.ingresarADebinComprador();
            pagosHerramientasAccesibilidadSteps.ingresarConsultarContracargos();
            herramientasDeAccesibilidadSteps.validarTituloPageGenerico("Consulta de contracargos");
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
