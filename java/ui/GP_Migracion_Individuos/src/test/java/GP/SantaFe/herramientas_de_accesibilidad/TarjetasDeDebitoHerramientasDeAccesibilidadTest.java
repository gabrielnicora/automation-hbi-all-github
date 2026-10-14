package GP.SantaFe.herramientas_de_accesibilidad;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import steps.herramientas_de_accesibilidad.HerramientasDeAccesibilidadSteps;
import steps.herramientas_de_accesibilidad.TarjetasDeDebitoHerramientasAccesibilidadSteps;
import steps.login.LoginSteps;
import steps.menulateral.MenuLateralSteps;
import java.io.IOException;
import static db.ConexionDB.cerrarConexion;
import static utils.Constantes.uDiazleon;

public class TarjetasDeDebitoHerramientasDeAccesibilidadTest {
    LoginSteps loginSteps= new LoginSteps();
    MenuLateralSteps menuLateralSteps= new MenuLateralSteps();
    HerramientasDeAccesibilidadSteps herramientasDeAccesibilidadSteps= new HerramientasDeAccesibilidadSteps();
    TarjetasDeDebitoHerramientasAccesibilidadSteps tarjetasDeDebitoHerramientasAccesibilidadSteps= new TarjetasDeDebitoHerramientasAccesibilidadSteps();


    @Test
    @DisplayName("TC-0316-BSF: Herramientas de Accesibilidad Tarjetas de Debito - Ingresar a Tarjetas de debito -  Validar que se redirige a la pantalla 'Tarjetas de débito'")
    public void TC0316BSF() {
        try {
            loginSteps.iniciarSesion(uDiazleon);
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            tarjetasDeDebitoHerramientasAccesibilidadSteps.ingresarATarjetasDebito();
            herramientasDeAccesibilidadSteps.validarTituloPageGenerico("Tarjetas de débito");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0317-BSF: Herramientas de Accesibilidad Tarjetas de Debito - Ingresar a SEGUIMIENTO DE TARJETAS -  Validar que se redirige a la pantalla 'Seguimiento de tarjetas'")
    public void TC0317BSF() {
        try {
            loginSteps.iniciarSesion(uDiazleon);
            menuLateralSteps.clickTarjetaDeDebito();
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            tarjetasDeDebitoHerramientasAccesibilidadSteps.ingresarASeguimientoDeTarjeta();
            herramientasDeAccesibilidadSteps.validarTituloPageGenerico("Seguimiento de tarjetas");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0318-BSF: Herramientas de Accesibilidad Tarjetas de Debito - Ingresar a Ver mas de TD-  Validar que se redirige a la pantalla 'Visa Débito'")
    public void TC0318BSF() {
        try {
            loginSteps.iniciarSesion(uDiazleon);
            menuLateralSteps.clickTarjetaDeDebito();
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            tarjetasDeDebitoHerramientasAccesibilidadSteps.ingresarAVerMas();
            tarjetasDeDebitoHerramientasAccesibilidadSteps.validarTituloVisaDebito();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0319-BSF: Herramientas de Accesibilidad Tarjetas de Debito - Visa Debito - Ingresar a GESTION DE LIMITES - Validar que se redirige a pantalla 'Gestión de límites de extracción'")
    public void TC0319BSF() {
        try {
            loginSteps.iniciarSesion(uDiazleon);
            menuLateralSteps.clickTarjetaDeDebito();
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            tarjetasDeDebitoHerramientasAccesibilidadSteps.ingresarAVerMas();
            tarjetasDeDebitoHerramientasAccesibilidadSteps.ingresarAGestionDeLimites();
            herramientasDeAccesibilidadSteps.validarTituloPageGenerico("Gestión de límites de extracción");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0320-BSF: Herramientas de Accesibilidad Tarjetas de Debito - Visa Debito - Ingresar a BLANQUEAR PIN/PIL - Validar que se redirige a pantalla 'Blanqueo de PIN/PIL'")
    public void TC0320BSF() {
        try {
            loginSteps.iniciarSesion(uDiazleon);
            menuLateralSteps.clickTarjetaDeDebito();
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            tarjetasDeDebitoHerramientasAccesibilidadSteps.ingresarAVerMas();
            tarjetasDeDebitoHerramientasAccesibilidadSteps.ingresarABlanquearPin();
            herramientasDeAccesibilidadSteps.validarTituloPageGenerico("Blanqueo de PIN/PIL");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0321-BSF: Herramientas de Accesibilidad Tarjetas de Debito - Visa Debito - Ingresar a Bloquear Tarjeta de Debito - Validar que se redirige a pantalla 'Bloqueo de Tarjeta de débito'")
    public void TC0321BSF() {
        try {
            loginSteps.iniciarSesion(uDiazleon);
            menuLateralSteps.clickTarjetaDeDebito();
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            tarjetasDeDebitoHerramientasAccesibilidadSteps.ingresarAVerMas();
            tarjetasDeDebitoHerramientasAccesibilidadSteps.ingresarABloqueoTarjeta();
            herramientasDeAccesibilidadSteps.validarTituloPageGenerico("Bloqueo de Tarjeta de débito");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0322-BSF: Herramientas de Accesibilidad Tarjetas de Debito - Visa Debito - Ingresar a Consultar Compras - Validar que se redirige a pantalla 'Consulta de compras en comercios'")
    public void TC0322BSF() {
        try {
            loginSteps.iniciarSesion(uDiazleon);
            menuLateralSteps.clickTarjetaDeDebito();
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            tarjetasDeDebitoHerramientasAccesibilidadSteps.ingresarAVerMas();
            tarjetasDeDebitoHerramientasAccesibilidadSteps.ingresarAConsultarCompras();
            herramientasDeAccesibilidadSteps.validarTituloPageGenerico("Consulta de compras en comercios");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0323-BSF: Herramientas de Accesibilidad Tarjetas de Debito - Visa Debito - Ingresar a Definir cuenta primaria - Validar que se redirige a pantalla 'Definir cuenta primaria'")
    public void TC0323BSF() {
        try {
            loginSteps.iniciarSesion(uDiazleon);
            menuLateralSteps.clickTarjetaDeDebito();
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            tarjetasDeDebitoHerramientasAccesibilidadSteps.ingresarAVerMas();
            tarjetasDeDebitoHerramientasAccesibilidadSteps.ingresarADefinirCuentaPrimaria();
            herramientasDeAccesibilidadSteps.validarTituloPageGenerico("Definir cuenta primaria");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0324-BSF: Herramientas de Accesibilidad Tarjetas de Debito - Visa Debito - Ingresar a Aviso de viaje al exterior - Validar que se redirige a pantalla 'Aviso de viaje al exterior'")
    public void TC0324BSF() {
        try {
            loginSteps.iniciarSesion(uDiazleon);
            menuLateralSteps.clickTarjetaDeDebito();
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            tarjetasDeDebitoHerramientasAccesibilidadSteps.ingresarAVerMas();
            tarjetasDeDebitoHerramientasAccesibilidadSteps.ingresarAAvisoDeViajeAlExterior();
            herramientasDeAccesibilidadSteps.validarTituloPageGenerico("Aviso de viaje al exterior");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0325-BSF: Herramientas de Accesibilidad Tarjetas de Debito - Visa Debito - Ingresar a Consulta de devolución de IVA - Validar que se redirige a pantalla 'Consulta de devolución de IVA'")
    public void TC0325BSF() {
        try {
            loginSteps.iniciarSesion(uDiazleon);
            menuLateralSteps.clickTarjetaDeDebito();
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            tarjetasDeDebitoHerramientasAccesibilidadSteps.ingresarAVerMas();
            tarjetasDeDebitoHerramientasAccesibilidadSteps.ingresarAConsultaDevolucionIva();
            herramientasDeAccesibilidadSteps.validarTituloPageGenerico("Consulta de devolución de IVA");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0326-BSF: Herramientas de Accesibilidad Tarjetas de Debito - Consultar Compras - Ingresar a Cuotificalo - Validar que se redirige a pantalla 'Cuotificalo'")
    public void TC0326BSF() {
        try {
            loginSteps.iniciarSesion(uDiazleon);
            menuLateralSteps.clickTarjetaDeDebito();
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            tarjetasDeDebitoHerramientasAccesibilidadSteps.ingresarAVerMas();
            tarjetasDeDebitoHerramientasAccesibilidadSteps.ingresarAConsultarCompras();
            tarjetasDeDebitoHerramientasAccesibilidadSteps.ingresarACuotificalo();
            herramientasDeAccesibilidadSteps.validarTituloPageGenerico("Cuotificalo");
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
