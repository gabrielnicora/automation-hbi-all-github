package GP.EntreRios.herramientas_de_accesibilidad;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import steps.herramientas_de_accesibilidad.HerramientasDeAccesibilidadSteps;
import steps.herramientas_de_accesibilidad.TarjetasDeCreditoHerramientasAccesibilidadSteps;
import steps.login.LoginSteps;
import steps.menulateral.MenuLateralSteps;
import steps.tarjetasdecredito.TarjetasDeCreditoSteps;

import java.io.IOException;

import static db.ConexionDB.cerrarConexion;
import static utils.Constantes.uUathb04;

public class TarjetasDeCreditoHerramientasDeAccesibilidadTest {
    LoginSteps loginSteps = new LoginSteps();
    MenuLateralSteps menuLateralSteps = new MenuLateralSteps();
    HerramientasDeAccesibilidadSteps herramientasDeAccesibilidadSteps = new HerramientasDeAccesibilidadSteps();
    TarjetasDeCreditoHerramientasAccesibilidadSteps tarjetasDeCreditoHerramientasAccesibilidadSteps = new TarjetasDeCreditoHerramientasAccesibilidadSteps();
    TarjetasDeCreditoSteps tarjetasDeCreditoSteps = new TarjetasDeCreditoSteps();

    @Test
    @DisplayName("TC-0327-BERSA: Herramientas de Accesibilidad Tarjetas de credito - Ingresar a Tarjetas de Credito -  Validar que se redirige a la pantalla 'Tarjetas de Credito'")
    public void TC0327BERSA() {
        try {
            loginSteps.iniciarSesion(uUathb04);
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            tarjetasDeCreditoHerramientasAccesibilidadSteps.ingresarATarjetasCredito();
            herramientasDeAccesibilidadSteps.validarTituloPageGenerico("Tarjetas de crédito");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-0328-BERSA: Herramientas de Accesibilidad Tarjetas de credito - Ingresar a SOLICITAR TARJETA NUEVA - Validar que se redirige a la pantalla 'Solicitud de tarjeta de crédito'")
    public void TC0328BERSA() {
        try {
            loginSteps.iniciarSesion(uUathb04);
            menuLateralSteps.clickTarjetaDeCredito();
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            tarjetasDeCreditoHerramientasAccesibilidadSteps.ingresarASolicitarTarjetaNueva();
            herramientasDeAccesibilidadSteps.validarTituloPageGenerico("Solicitud de tarjeta de crédito");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-0329-BERSA: Herramientas de Accesibilidad Tarjetas de credito - Solicitud de tarjeta de crédito - Descargar Resumen de contrato - Validar redireccion de URL")
    public void TC0329BERSA() {
        try {
            loginSteps.iniciarSesion(uUathb04);
            menuLateralSteps.clickTarjetaDeCredito();
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            tarjetasDeCreditoHerramientasAccesibilidadSteps.ingresarASolicitarTarjetaNueva();
            tarjetasDeCreditoHerramientasAccesibilidadSteps.descargarResumenDeContrato();
            herramientasDeAccesibilidadSteps.validarFragmentoURL("goanywhere");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    //@Test //sin datos
    @DisplayName("TC-0330-BERSA: Herramientas de Accesibilidad Tarjetas de credito - Ingresar a SEGUIMIENTO DE TARJETAS - Validar que se redirige a la pantalla 'Seguimiento de tarjetas'")
    public void TC0330BERSA() {
        try {
            loginSteps.iniciarSesion(uUathb04);
            menuLateralSteps.clickTarjetaDeCredito();
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            tarjetasDeCreditoHerramientasAccesibilidadSteps.ingresarASeguimientoDeTarjetas();
            herramientasDeAccesibilidadSteps.validarTituloPageGenerico("Seguimiento de tarjetas");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-0331-BERSA: Herramientas de Accesibilidad Tarjetas de credito - Ingresar a Ver mas Tarjetas de credito - Validar que se redirige a la pantalla de la Tarjeta de credito")
    public void TC0331BERSA() {
        try {
            loginSteps.iniciarSesion(uUathb04);
            menuLateralSteps.clickTarjetaDeCredito();
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            tarjetasDeCreditoHerramientasAccesibilidadSteps.ingresarAVerMas();
            tarjetasDeCreditoSteps.validacionTarjetaCredito();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-0332-BERSA: Herramientas de Accesibilidad Tarjetas de credito - Ingresar a DESCARGAR RESÚMENES - Validar que se redirige a la pantalla 'Descargar resumen'")
    public void TC0332BERSA() {
        try {
            loginSteps.iniciarSesion(uUathb04);
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.clickVerMasPrimerTarjeta();
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            tarjetasDeCreditoHerramientasAccesibilidadSteps.descargarResumenDeTC();
            herramientasDeAccesibilidadSteps.validarTituloPageGenerico("Descargar resumen");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-0333-BERSA: Herramientas de Accesibilidad Tarjetas de credito - Ingresar a SOLICITAR ADICIONAL - Validar que se redirige a la pantalla 'Solicitud de tarjeta de crédito'")
    public void TC0333BERSA() {
        try {
            loginSteps.iniciarSesion(uUathb04);
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.clickVerMasPrimerTarjeta();
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            tarjetasDeCreditoHerramientasAccesibilidadSteps.solicitarAdicional();
            herramientasDeAccesibilidadSteps.validarTituloPageGenerico("Solicitud de tarjeta de crédito");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-0334-BERSA: Herramientas de Accesibilidad Tarjetas de credito - Ingresar a Denunciar robo o extravío - Validar que se redirige a la pantalla 'Denuncia por robo o extravío de tarjeta'")
    public void TC0334BERSA() {
        try {
            loginSteps.iniciarSesion(uUathb04);
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.clickVerMasPrimerTarjeta();
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            tarjetasDeCreditoHerramientasAccesibilidadSteps.ingresarADenunciarRoboyExtravio();
            herramientasDeAccesibilidadSteps.validarTituloPageGenerico("Denuncia por robo o extravío de tarjeta");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-0335-BERSA: Herramientas de Accesibilidad Tarjetas de credito - Ingresar a Pagar mi tarjeta - Validar que se redirige a la pantalla 'Pago de tarjeta propia'")
    public void TC0335BERSA() {
        try {
            loginSteps.iniciarSesion(uUathb04);
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.clickVerMasPrimerTarjeta();
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            tarjetasDeCreditoHerramientasAccesibilidadSteps.ingresarAPagarMiTarjeta();
            herramientasDeAccesibilidadSteps.validarTituloPageGenerico("Pago de tarjeta propia");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-0336-BERSA: Herramientas de Accesibilidad Tarjetas de credito - Ingresar a Aviso de viaje al exterior - Validar que se redirige a la pantalla 'Aviso de viaje al exterior'")
    public void TC0336BERSA() {
        try {
            loginSteps.iniciarSesion(uUathb04);
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.clickVerMasPrimerTarjeta();
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            tarjetasDeCreditoHerramientasAccesibilidadSteps.ingresarAvisoDeViajeAlExterior();
            herramientasDeAccesibilidadSteps.validarTituloPageGenerico("Aviso de viaje al exterior");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-0337-BERSA: Herramientas de Accesibilidad Tarjetas de credito - Ingresar a Solicitar baja de tarjeta - Validar que se redirige a la pantalla 'Baja de tarjeta'")
    public void TC0337BERSA() {
        try {
            loginSteps.iniciarSesion(uUathb04);
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.clickVerMasPrimerTarjeta();
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            tarjetasDeCreditoHerramientasAccesibilidadSteps.ingresarASolicitarBajaDeTarjeta();
            herramientasDeAccesibilidadSteps.validarTituloPageGenerico("Baja de tarjeta");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-0338-BERSA: Herramientas de Accesibilidad Tarjetas de credito - Ingresar a Consultar último resumen- Validar que se redirige a la pantalla 'Consulta último resumen'")
    public void TC0338BERSA() {
        try {
            loginSteps.iniciarSesion(uUathb04);
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.clickVerMasPrimerTarjeta();
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            tarjetasDeCreditoHerramientasAccesibilidadSteps.ingresarAConsultarUltimoResumen();
            herramientasDeAccesibilidadSteps.validarTituloPageGenerico("Consulta último resumen");
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
