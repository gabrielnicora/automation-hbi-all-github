package GP.SantaCruz.herramientas_de_accesibilidad;

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
import static utils.Constantes.uBsc13400488;

public class TarjetasDeCreditoHerramientasDeAccesibilidadTest {
    LoginSteps loginSteps = new LoginSteps();
    MenuLateralSteps menuLateralSteps = new MenuLateralSteps();
    HerramientasDeAccesibilidadSteps herramientasDeAccesibilidadSteps = new HerramientasDeAccesibilidadSteps();
    TarjetasDeCreditoHerramientasAccesibilidadSteps tarjetasDeCreditoHerramientasAccesibilidadSteps = new TarjetasDeCreditoHerramientasAccesibilidadSteps();
    TarjetasDeCreditoSteps tarjetasDeCreditoSteps= new TarjetasDeCreditoSteps();

    @Test
    @DisplayName("TC-0327-BSC: Herramientas de Accesibilidad Tarjetas de credito - Ingresar a Tarjetas de Credito -  Validar que se redirige a la pantalla 'Tarjetas de Credito'")
    public void TC0327BSC() {
        try {
            loginSteps.iniciarSesion(uBsc13400488);
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            tarjetasDeCreditoHerramientasAccesibilidadSteps.ingresarATarjetasCredito();
            herramientasDeAccesibilidadSteps.validarTituloPageGenerico("Tarjetas de crédito");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0328-BSC: Herramientas de Accesibilidad Tarjetas de credito - Ingresar a SOLICITAR TARJETA NUEVA - Validar que se redirige a la pantalla 'Solicitud de tarjeta de crédito'")
    public void TC0328BSC() {
        try {
            loginSteps.iniciarSesion(uBsc13400488);
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
    @DisplayName("TC-0329-BSC: Herramientas de Accesibilidad Tarjetas de credito - Solicitud de tarjeta de crédito - Descargar Resumen de contrato - Validar redireccion de URL")
    public void TC0329BSC() {
        try {
            loginSteps.iniciarSesion(uBsc13400488);
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
    @Test
    @DisplayName("TC-0330-BSC: Herramientas de Accesibilidad Tarjetas de credito - Ingresar a SEGUIMIENTO DE TARJETAS - Validar que se redirige a la pantalla 'Seguimiento de tarjetas'")
    public void TC0330BSC() {
        try {
            loginSteps.iniciarSesion(uBsc13400488);
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
    @DisplayName("TC-0331-BSC: Herramientas de Accesibilidad Tarjetas de credito - Ingresar a Ver mas Tarjetas de credito - Validar que se redirige a la pantalla de la Tarjeta de credito")
    public void TC0331BSC() {
        try {
            loginSteps.iniciarSesion(uBsc13400488);
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
    @DisplayName("TC-0332-BSC: Herramientas de Accesibilidad Tarjetas de credito - Ingresar a DESCARGAR RESÚMENES - Validar que se redirige a la pantalla 'Descargar resumen'")
    public void TC0332BSC() {
        try {
            loginSteps.iniciarSesion(uBsc13400488);
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
    @DisplayName("TC-0333-BSC: Herramientas de Accesibilidad Tarjetas de credito - Ingresar a SOLICITAR ADICIONAL - Validar que se redirige a la pantalla 'Solicitud de tarjeta de crédito'")
    public void TC0333BSC() {
        try {
            loginSteps.iniciarSesion(uBsc13400488);
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
    @DisplayName("TC-0334-BSC: Herramientas de Accesibilidad Tarjetas de credito - Ingresar a Denunciar robo o extravío - Validar que se redirige a la pantalla 'Denuncia por robo o extravío de tarjeta'")
    public void TC0334BSC() {
        try {
            loginSteps.iniciarSesion(uBsc13400488);
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
    @DisplayName("TC-0335-BSC: Herramientas de Accesibilidad Tarjetas de credito - Ingresar a Pagar mi tarjeta - Validar que se redirige a la pantalla 'Pago de tarjeta propia'")
    public void TC0335BSC() {
        try {
            loginSteps.iniciarSesion(uBsc13400488);
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
    //@Test sin datos
    @DisplayName("TC-0336-BSC: Herramientas de Accesibilidad Tarjetas de credito - Ingresar a Aviso de viaje al exterior - Validar que se redirige a la pantalla 'Aviso de viaje al exterior'")
    public void TC0336BSC() {
        try {
            loginSteps.iniciarSesion(uBsc13400488);
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.clickVerMasPrimerTarjeta();
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            herramientasDeAccesibilidadSteps.presionarTecla5();
            herramientasDeAccesibilidadSteps.presionarTecla8();
            herramientasDeAccesibilidadSteps.validarTituloPageGenerico("Aviso de viaje al exterior");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0337-BSC: Herramientas de Accesibilidad Tarjetas de credito - Ingresar a Solicitar baja de tarjeta - Validar que se redirige a la pantalla 'Baja de tarjeta'")
    public void TC0337BSC() {
        try {
            loginSteps.iniciarSesion(uBsc13400488);
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.clickVerMasPrimerTarjeta();
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            herramientasDeAccesibilidadSteps.presionarTecla5();
            herramientasDeAccesibilidadSteps.presionarTecla8();
            herramientasDeAccesibilidadSteps.validarTituloPageGenerico("Baja de tarjeta");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0338-BSC: Herramientas de Accesibilidad Tarjetas de credito - Ingresar a Consultar último resumen- Validar que se redirige a la pantalla 'Consulta último resumen'")
    public void TC0338BSC() {
        try {
            loginSteps.iniciarSesion(uBsc13400488);
            menuLateralSteps.clickTarjetaDeCredito();
            tarjetasDeCreditoSteps.clickVerMasPrimerTarjeta();
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            herramientasDeAccesibilidadSteps.presionarTecla5();
            herramientasDeAccesibilidadSteps.presionarTecla9();
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
