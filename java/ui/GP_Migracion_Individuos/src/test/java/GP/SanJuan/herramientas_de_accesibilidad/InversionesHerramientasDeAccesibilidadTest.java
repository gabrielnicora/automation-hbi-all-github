package GP.SanJuan.herramientas_de_accesibilidad;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import steps.herramientas_de_accesibilidad.HerramientasDeAccesibilidadSteps;
import steps.herramientas_de_accesibilidad.InversionesHerramientasAccesibilidadSteps;
import steps.login.LoginSteps;
import steps.menulateral.MenuLateralSteps;

import java.io.IOException;

import static db.ConexionDB.cerrarConexion;
import static utils.Constantes.uCuyo2021;

public class InversionesHerramientasDeAccesibilidadTest {
    LoginSteps loginSteps= new LoginSteps();
    MenuLateralSteps menuLateralSteps= new MenuLateralSteps();
    HerramientasDeAccesibilidadSteps herramientasDeAccesibilidadSteps= new HerramientasDeAccesibilidadSteps();
    InversionesHerramientasAccesibilidadSteps inversionesHerramientasAccesibilidadSteps= new InversionesHerramientasAccesibilidadSteps();

    @Test
    @DisplayName("TC-0307-BSJ: Herramientas de Accesibilidad Inversiones - Ingresar a Inversiones con las Herramientas de Accesibilidad")
    public void TC0307BSJ() {
        try {
            loginSteps.iniciarSesion(uCuyo2021);
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            inversionesHerramientasAccesibilidadSteps.ingresarAInversiones();
            herramientasDeAccesibilidadSteps.validarTituloPageGenerico("Inversiones");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0308-BSJ: Herramientas de Accesibilidad Inversiones - Ingresar a Compra venta de dólares - Validar que se redirige a la pantalla 'Compra venta de dólares'")
    public void TC0308BSJ() {
        try {
            loginSteps.iniciarSesion(uCuyo2021);
            menuLateralSteps.clickInversiones();
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            inversionesHerramientasAccesibilidadSteps.ingresarACompraVentaDeDolar();
            herramientasDeAccesibilidadSteps.validarTituloPageGenerico("Compra venta de dólares");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0309-BSJ: Herramientas de Accesibilidad Inversiones - Ingresar a Fondo comun de inversion - Validar que se redirige a la pantalla 'Información de fondos'")
    public void TC0309BSJ() {
        try {
            loginSteps.iniciarSesion(uCuyo2021);
            menuLateralSteps.clickInversiones();
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            inversionesHerramientasAccesibilidadSteps.ingresarAFondoComunInversion();
            herramientasDeAccesibilidadSteps.validarTituloPageGenerico("Información de fondos");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0310-BSJ: Herramientas de Accesibilidad Inversiones - Ingresar a Plazos Fijos - Validar que se redirige a la pantalla 'Plazos fijos'")
    public void TC0310BSJ() {
        try {
            loginSteps.iniciarSesion(uCuyo2021);
            menuLateralSteps.clickInversiones();
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            inversionesHerramientasAccesibilidadSteps.ingresarAPlazosFijos();
            herramientasDeAccesibilidadSteps.validarTituloPageGenerico("Plazos fijos");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0311-BSJ: Herramientas de Accesibilidad Inversiones - Plazos Fijos - Ingresar a CONSULTA DE TASAS - Validar que se redirige a la pantalla 'Consulta de Tasas'")
    public void TC0311BSJ() {
        try {
            loginSteps.iniciarSesion(uCuyo2021);
            menuLateralSteps.clickInversiones();
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            inversionesHerramientasAccesibilidadSteps.ingresarAPlazosFijos();
            inversionesHerramientasAccesibilidadSteps.ingresarAConsultaDeTasas();
            herramientasDeAccesibilidadSteps.validarTituloPageGenerico("Consulta de Tasas");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0312-BSJ: Herramientas de Accesibilidad Inversiones - Plazos Fijos - Ingresar a SOLICITAR PLAZO FIJO - Validar que se redirige a la pantalla 'Simulación y Alta de Plazo Fijo'")
    public void TC0312BSJ() {
        try {
            loginSteps.iniciarSesion(uCuyo2021);
            menuLateralSteps.clickInversiones();
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            inversionesHerramientasAccesibilidadSteps.ingresarAPlazosFijos();
            inversionesHerramientasAccesibilidadSteps.ingresarASolicitarPlazoFijo();
            herramientasDeAccesibilidadSteps.validarTituloPageGenerico("Simulación y Alta de Plazo Fijo");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0313-BSJ: Herramientas de Accesibilidad Inversiones - Ingresar a Plazos Fijos(CEDIP) - Validar que se redirige a la pantalla 'Plazo fijo (CEDIP)'")
    public void TC0313BSJ() {
        try {
            loginSteps.iniciarSesion(uCuyo2021);
            menuLateralSteps.clickInversiones();
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            inversionesHerramientasAccesibilidadSteps.ingresarAPlazoFijoCEDIP();
            herramientasDeAccesibilidadSteps.validarTituloPageGenerico("Plazo fijo (CEDIP)");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0314-BSJ: Herramientas de Accesibilidad Inversiones - Ingresar a Solicitar nueva cuenta comitente - Validar que se redirige a la pantalla 'Apertura de cuenta comitente'")
    public void TC0314BSJ() {
        try {
            loginSteps.iniciarSesion(uCuyo2021);
            menuLateralSteps.clickInversiones();
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            inversionesHerramientasAccesibilidadSteps.ingresarASolicitarNuevaCuentaComitente();
            herramientasDeAccesibilidadSteps.validarTituloPageGenerico("Apertura de cuenta comitente");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0315-BSJ: Herramientas de Accesibilidad Inversiones - Ingresar a Solicitar baja de cuenta comitente - Validar que se redirige a la pantalla 'Baja de cuenta comitente'")
    public void TC0315BSJ() {
        try {
            loginSteps.iniciarSesion(uCuyo2021);
            menuLateralSteps.clickInversiones();
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            inversionesHerramientasAccesibilidadSteps.ingresarABajaCuentaComitente();
            herramientasDeAccesibilidadSteps.validarTituloPageGenerico("Baja de cuenta comitente");
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
