package GP.SantaFe.herramientas_de_accesibilidad;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import steps.herramientas_de_accesibilidad.EscritorioHerramientasDeAccesibilidadSteps;
import steps.herramientas_de_accesibilidad.HerramientasDeAccesibilidadSteps;
import steps.login.LoginSteps;
import steps.menulateral.MenuLateralSteps;

import java.io.IOException;

import static db.ConexionDB.cerrarConexion;
import static utils.Constantes.*;


public class EscritorioHerramientasDeAccesibilidadTest {
    LoginSteps loginSteps= new LoginSteps();
    HerramientasDeAccesibilidadSteps herramientasDeAccesibilidadSteps = new HerramientasDeAccesibilidadSteps();
    EscritorioHerramientasDeAccesibilidadSteps escritorioHerramientasDeAccesibilidad = new EscritorioHerramientasDeAccesibilidadSteps();
    MenuLateralSteps menuLateralSteps= new MenuLateralSteps();


    
    @Test
    @DisplayName("TC-0251-BSF: Herramientas de Accesibilidad Escritorio - Seleccionar Escritorio mediante HA Opcion 5 - Validar pagina completa Escritorio.")
    public void TC0251BSF() {
        try {
            loginSteps.iniciarSesion(uVolpatomonica);
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            herramientasDeAccesibilidadSteps.presionarTeclaFin();
            herramientasDeAccesibilidadSteps.presionarTecla5();
            herramientasDeAccesibilidadSteps.presionarTecla27();
            herramientasDeAccesibilidadSteps.validarTituloPageGenerico("¡Hola VOLPATO, MONICA LILIANA A!");
            escritorioHerramientasDeAccesibilidad.validarCuadroSelectorOpcion1HA();
            escritorioHerramientasDeAccesibilidad.validarTituloSeccion("Ofertas");
            escritorioHerramientasDeAccesibilidad.validarCuadroSelectorOpcion2HA();
            escritorioHerramientasDeAccesibilidad.validarTituloSeccion("Cuentas");
            escritorioHerramientasDeAccesibilidad.validarCuadroSelectorOpcion3HA();
            escritorioHerramientasDeAccesibilidad.validarTituloSeccion("Tarjetas");
            escritorioHerramientasDeAccesibilidad.validarCuadroSelectorOpcion4HA();
            escritorioHerramientasDeAccesibilidad.validarTituloSeccion("Acceso Rápido");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0252-BSF: Herramientas de Accesibilidad Escritorio - OFERTAS Opcion 1 - Seleccionar en carrusel de OFERTAS Opcion 1 - Validar pantalla de oferta seleccionada.")
    public void TC0252BSF() {
        try {
            loginSteps.iniciarSesion(uVolpatomonica);
            menuLateralSteps.clickInicio();
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            escritorioHerramientasDeAccesibilidad.getTituloCardOfertas("1");
            herramientasDeAccesibilidadSteps.presionarTecla1();
            herramientasDeAccesibilidadSteps.presionarTecla1();
            herramientasDeAccesibilidadSteps.presionarTecla1();
            escritorioHerramientasDeAccesibilidad.validarTitulosOfertas();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0253-BSF: Herramientas de Accesibilidad Escritorio - OFERTAS Opcion 1 - Seleccionar en carrusel de OFERTAS Opcion 2 - Validar pantalla de oferta seleccionada.")
    public void TC0253BSF() {
        try {
            loginSteps.iniciarSesion(uVolpatomonica);
            menuLateralSteps.clickInicio();
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            escritorioHerramientasDeAccesibilidad.getTituloCardOfertas("2");
            herramientasDeAccesibilidadSteps.presionarTecla1();
            herramientasDeAccesibilidadSteps.presionarTecla2();
            herramientasDeAccesibilidadSteps.presionarTecla1();
            Thread.sleep(1000);
            escritorioHerramientasDeAccesibilidad.validarTitulosOfertas();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0254-BSF: Herramientas de Accesibilidad Escritorio - OFERTAS Opcion 1 - Seleccionar en carrusel de OFERTAS Opcion 3 - Validar pantalla de oferta seleccionada.")
    public void TC0254BSF() {
        try {
            loginSteps.iniciarSesion(uVolpatomonica);
            menuLateralSteps.clickInicio();
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            escritorioHerramientasDeAccesibilidad.getTituloCardOfertas("3");
            herramientasDeAccesibilidadSteps.presionarTecla1();
            herramientasDeAccesibilidadSteps.presionarTecla3();
            herramientasDeAccesibilidadSteps.presionarTecla1();
            escritorioHerramientasDeAccesibilidad.validarTitulosOfertas();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0255-BSF: Herramientas de Accesibilidad Escritorio - OFERTAS Opcion 1 - Seleccionar en carrusel de OFERTAS Opcion 4 - Validar ver mas cards de ofertas y las opciones 1 al 5.")
    public void TC0255BSF() {
        try {
            loginSteps.iniciarSesion(uVolpatomonica);
            menuLateralSteps.clickInicio();
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            escritorioHerramientasDeAccesibilidad.getTituloCardOfertas("3");
            herramientasDeAccesibilidadSteps.presionarTecla1();
            herramientasDeAccesibilidadSteps.presionarTecla4();
            escritorioHerramientasDeAccesibilidad.validarCuadroSelectorOpcion1HA();
            escritorioHerramientasDeAccesibilidad.validarCuadroSelectorOpcion5HA();

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0256-BSF: Herramientas de Accesibilidad Escritorio - CUENTAS Opcion 2 - Seleccionar en carrusel de CUENTAS Opcion 1 - Validar pantalla de cuenta seleccionada.")
    public void TC0256BSF() {
        try {
            loginSteps.iniciarSesion(uVolpatomonica);
            menuLateralSteps.clickInicio();
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            Thread.sleep(1000);
            herramientasDeAccesibilidadSteps.presionarTecla2();
            escritorioHerramientasDeAccesibilidad.getTituloCardCuentas("0");
            herramientasDeAccesibilidadSteps.presionarTecla1();
            herramientasDeAccesibilidadSteps.presionarTecla1();
            escritorioHerramientasDeAccesibilidad.validarTitulosCardCuentas();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0257-BSF: Herramientas de Accesibilidad Escritorio - CUENTAS Opcion 2 - Seleccionar en carrusel de CUENTAS Opcion 4 - Validar poder ver mas cards de cuentas y las opciones 1 al 5.")
    public void TC0257BSF() {
        try {
            loginSteps.iniciarSesion(uVolpatomonica);
            menuLateralSteps.clickInicio();
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            Thread.sleep(2000);
            herramientasDeAccesibilidadSteps.presionarTecla2();
            herramientasDeAccesibilidadSteps.presionarTecla4();
            escritorioHerramientasDeAccesibilidad.validarCuadroSelectorOpcion1HA();
            escritorioHerramientasDeAccesibilidad.validarCuadroSelectorOpcion5HA();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0258-BSF: Herramientas de Accesibilidad Escritorio - TARJETAS Opcion 3 - Seleccionar en carrusel de TARJETAS Opcion 1 - Validar pantalla informacion de tarjeta seleccionada.")
    public void TC0258BSF() {
        try {
            loginSteps.iniciarSesion(uPerezR);
            menuLateralSteps.clickInicio();
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            herramientasDeAccesibilidadSteps.presionarTecla3();
            escritorioHerramientasDeAccesibilidad.getTituloCardTarjetas("0");
            herramientasDeAccesibilidadSteps.presionarTecla1();
            herramientasDeAccesibilidadSteps.presionarTecla1();
            escritorioHerramientasDeAccesibilidad.validarTitulosCardTarjetas();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0259-BSF: Herramientas de Accesibilidad Escritorio - TARJETAS Opcion 3 - Seleccionar en carrusel de TARJETAS Opcion 2 - Validar pantalla informacion de tarjeta seleccionada")
    public void TC0259BSF() {
        try {
            loginSteps.iniciarSesion(uPerezR);
            menuLateralSteps.clickInicio();
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            herramientasDeAccesibilidadSteps.presionarTecla3();
            escritorioHerramientasDeAccesibilidad.getTituloCardTarjetas("1");
            herramientasDeAccesibilidadSteps.presionarTecla2();
            herramientasDeAccesibilidadSteps.presionarTecla1();
            escritorioHerramientasDeAccesibilidad.validarTitulosCardTarjetas();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0260-BSF: Herramientas de Accesibilidad Escritorio - TARJETAS Opcion 3 - Seleccionar en carrusel de TARJETAS Opcion 3 - Validar pantalla informacion de tarjeta seleccionada.")
    public void TC0260BSF() {
        try {
            loginSteps.iniciarSesion(uPerezR);
            menuLateralSteps.clickInicio();
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            herramientasDeAccesibilidadSteps.presionarTecla3();
            escritorioHerramientasDeAccesibilidad.getTituloCardTarjetas("2");
            herramientasDeAccesibilidadSteps.presionarTecla3();
            herramientasDeAccesibilidadSteps.presionarTecla1();
            escritorioHerramientasDeAccesibilidad.validarTitulosCardTarjetas();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test //USER SIN DATOS
    @DisplayName("TC-0261-BSF: Herramientas de Accesibilidad Escritorio - TARJETAS Opcion 3 - Seleccionar en carrusel de TARJETAS Opcion 4 - Validar poder ver mas cards de tarjetas y las opciones 1 al 5.")
    public void TC0261BSF() {
        try {
            loginSteps.iniciarSesion(uVolpatomonica);
            menuLateralSteps.clickInicio();
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            herramientasDeAccesibilidadSteps.presionarTecla3();
            escritorioHerramientasDeAccesibilidad.getTituloCardTarjetas("3");
            herramientasDeAccesibilidadSteps.presionarTecla4();
            escritorioHerramientasDeAccesibilidad.validarCuadroSelectorOpcion1HA();
            //escritorioHerramientasDeAccesibilidad.validarCuadroSelectorOpcion5HA(); Se debera encontrar un dato de usuario q cuente com mas de 4 TD para poder realizar esta validacion
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0262-BSF: Herramientas de Accesibilidad Escritorio - ACCESO RAPIDO Opcion 4 - Seleccionar en carrusel de ACCESO RAPIDO Opcion 1 - Validar pantalla informacion de Acceso Rapido seleccionado.")
    public void TC0262BSF() {
        try {
            loginSteps.iniciarSesion(uVolpatomonica);
            menuLateralSteps.clickInicio();
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            herramientasDeAccesibilidadSteps.presionarTeclaFin();
            herramientasDeAccesibilidadSteps.ingresoEscritorio();
            escritorioHerramientasDeAccesibilidad.getTituloCardAccesoRapido("1");
            herramientasDeAccesibilidadSteps.presionarTecla4();
            herramientasDeAccesibilidadSteps.presionarTecla1();
            escritorioHerramientasDeAccesibilidad.validarTitulosAccesoRapido();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0263-BSF: Herramientas de Accesibilidad Escritorio - ACCESO RAPIDO Opcion 4 - Seleccionar en carrusel de ACCESO RAPIDO Opcion 2 - Validar pantalla informacion de Acceso Rapido seleccionado.")
    public void TC0263BSF() {
        try {
            loginSteps.iniciarSesion(uVolpatomonica);
            menuLateralSteps.clickInicio();
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            herramientasDeAccesibilidadSteps.presionarTeclaFin();
            herramientasDeAccesibilidadSteps.ingresoEscritorio();
            escritorioHerramientasDeAccesibilidad.getTituloCardAccesoRapido("2");
            herramientasDeAccesibilidadSteps.presionarTecla4();
            herramientasDeAccesibilidadSteps.presionarTecla2();
            escritorioHerramientasDeAccesibilidad.validarTitulosAccesoRapido();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0264-BSF: Herramientas de Accesibilidad Escritorio - ACCESO RAPIDO Opcion 4 - Seleccionar en carrusel de ACCESO RAPIDO Opcion 3 - Validar pantalla informacion de Acceso Rapido seleccionado.")
    public void TC0264BSF() {
        try {
            loginSteps.iniciarSesion(uVolpatomonica);
            menuLateralSteps.clickInicio();
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            herramientasDeAccesibilidadSteps.presionarTeclaFin();
            herramientasDeAccesibilidadSteps.ingresoEscritorio();
            escritorioHerramientasDeAccesibilidad.getTituloCardAccesoRapido("3");
            herramientasDeAccesibilidadSteps.presionarTecla4();
            herramientasDeAccesibilidadSteps.presionarTecla3();
            escritorioHerramientasDeAccesibilidad.validarTitulosAccesoRapido();
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
