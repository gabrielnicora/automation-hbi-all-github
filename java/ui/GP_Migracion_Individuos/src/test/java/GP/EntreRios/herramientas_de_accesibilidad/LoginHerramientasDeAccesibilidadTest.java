package GP.EntreRios.herramientas_de_accesibilidad;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import steps.herramientas_de_accesibilidad.HerramientasDeAccesibilidadSteps;
import steps.herramientas_de_accesibilidad.LoginHerramientasDeAccesibilidadSteps;
import steps.login.LoginSteps;

import java.io.IOException;

import static db.ConexionDB.cerrarConexion;

public class LoginHerramientasDeAccesibilidadTest {
   LoginSteps loginSteps= new LoginSteps();
   HerramientasDeAccesibilidadSteps herramientasDeAccesibilidadSteps= new HerramientasDeAccesibilidadSteps();
   LoginHerramientasDeAccesibilidadSteps loginHerramientasDeAccesibilidadSteps= new LoginHerramientasDeAccesibilidadSteps();

    @Test
    @DisplayName("TC-0230-BERSA: Herramientas de Accesibilidad Login- Ingresar a Herramientas de accesibilidad- Cerrar tutorial con f7")
    public void TC0230BERSA() {
        try {
            loginSteps.waitGP();
            Thread.sleep(5000);
            herramientasDeAccesibilidadSteps.clickHerramientasAccesibilidad();
            herramientasDeAccesibilidadSteps.presionarTeclaF7();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0231-BERSA: Herramientas de Accesibilidad Login- Ingresar a Herramientas de accesibilidad- Presionar boton 1 'Ingresar' - Presionar boton 1 'Volver' y validar Start Page")
    public void TC0231BERSA() {
        try {
            loginSteps.waitGP();
            Thread.sleep(5000);
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            herramientasDeAccesibilidadSteps.presionarTecla1();
            loginHerramientasDeAccesibilidadSteps.validarInputUsuario();
            herramientasDeAccesibilidadSteps.presionarTecla1();
            loginHerramientasDeAccesibilidadSteps.validarBotonIngresar();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0232-BERSA: Herramientas de Accesibilidad Login- Inicio Start Page - Presionar boton 1 'Ingresar' - Validar completitud de pantalla")
    public void TC0232BERSA() {
        try {
            loginSteps.waitGP();
            Thread.sleep(5000);
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            herramientasDeAccesibilidadSteps.presionarTecla1();
            loginHerramientasDeAccesibilidadSteps.validarPantallaLogin();

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0233-BERSA: Herramientas de Accesibilidad Login- Inicio Start Page - Presionar boton 2 'Crear usuario' - Validar completitud de pantalla")
    public void TC0233BERSA() {
        try {
            loginSteps.waitGP();
            Thread.sleep(5000);
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            herramientasDeAccesibilidadSteps.presionarTecla2();
            loginHerramientasDeAccesibilidadSteps.validarTituloPageGenerico("Solicitá tu código");
            loginHerramientasDeAccesibilidadSteps.validarTituloPageGenerico("Ingresá tu documento para identificarte");
            loginHerramientasDeAccesibilidadSteps.validarPantallaCrearUsuario();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0234-BERSA: Herramientas de Accesibilidad Login- Inicio Start Page - Presionar boton 3 '¿No sos cliente aún? Registrarme' - Validar completitud de pantalla")
    public void TC0234BERSA() {
        try {
            loginSteps.waitGP();
            Thread.sleep(5000);
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            loginHerramientasDeAccesibilidadSteps.validarTextoNoSosClienteAun();
            herramientasDeAccesibilidadSteps.presionarTecla3();
            loginHerramientasDeAccesibilidadSteps.validarTituloPageGenerico("Es necesario validar tu identidad para continuar");
            loginHerramientasDeAccesibilidadSteps.validarIconosDeDescargasApps();
            loginHerramientasDeAccesibilidadSteps.validarBotonVolver();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0235-BERSA: Herramientas de Accesibilidad Login- Inicio Start Page - Presionar boton 4 'Terminos y condiciones' - Validar completitud de pantalla")
    public void TC0235BERSA() {
        try {
            loginSteps.waitGP();
            Thread.sleep(5000);
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            herramientasDeAccesibilidadSteps.presionarTecla4();
            loginHerramientasDeAccesibilidadSteps.validarTituloPageGenerico("Términos y condiciones");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test // luego de presionar la tecla 5 no redirecciona a las recomendaciones y seguridad
    @DisplayName("TC-0236-BERSA: Herramientas de Accesibilidad Login- Inicio Start Page - Presionar boton 5 'Informacion y seguridad' - Validar redireccion de pagina")
    public void TC0236BERSA() {
        try {
            loginSteps.waitGP();
            Thread.sleep(5000);
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            herramientasDeAccesibilidadSteps.presionarTecla5();
            Thread.sleep(1000);
            loginHerramientasDeAccesibilidadSteps.validarFragmentoURL("recomendaciones-seguridad");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0240-BERSA: Herramientas de Accesibilidad Login- Login - Presionar boton 2 - Loguearse con usuario y contraseña - Verificar ingreso")
    public void TC0240BERSA() {
        try {
            loginSteps.waitGP();
            Thread.sleep(5000);
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            herramientasDeAccesibilidadSteps.presionarTecla1();
            herramientasDeAccesibilidadSteps.presionarTecla2();
            herramientasDeAccesibilidadSteps.presionarTecla1();
            loginHerramientasDeAccesibilidadSteps.ingresarUsuarioContrasenia("ecarina27");
            herramientasDeAccesibilidadSteps.presionarTeclaFin();
            herramientasDeAccesibilidadSteps.presionarTeclaFin();
            herramientasDeAccesibilidadSteps.presionarTecla3();
            herramientasDeAccesibilidadSteps.presionarTecla2();
            herramientasDeAccesibilidadSteps.presionarTecla3();
            loginHerramientasDeAccesibilidadSteps.ingresarUsuarioContrasenia("Practia2027");
            herramientasDeAccesibilidadSteps.presionarTeclaFin();
            herramientasDeAccesibilidadSteps.presionarTeclaFin();
            herramientasDeAccesibilidadSteps.presionarTecla3();
            loginSteps.verificarIngreso();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0241-BERSA: Herramientas de Accesibilidad Login- Blanquear contraseña - Presionar boton 5 'Blanquear contraseña' - Validar completitud de pantalla")
    public void TC0241BERSA() {
        try {
            loginSteps.waitGP();
            Thread.sleep(5000);
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            herramientasDeAccesibilidadSteps.presionarTecla1();
            herramientasDeAccesibilidadSteps.presionarTecla2();
            herramientasDeAccesibilidadSteps.presionarTecla1();
            loginHerramientasDeAccesibilidadSteps.ingresarUsuarioContrasenia("ecarina27");
            herramientasDeAccesibilidadSteps.presionarTeclaFin();
            herramientasDeAccesibilidadSteps.presionarTeclaFin();
            herramientasDeAccesibilidadSteps.presionarTecla3();
            herramientasDeAccesibilidadSteps.presionarTecla2();
            loginHerramientasDeAccesibilidadSteps.validarBotonBlanquearContrasenia();
            herramientasDeAccesibilidadSteps.presionarTecla5();
            loginHerramientasDeAccesibilidadSteps.validarTituloPageGenerico("Verificá tu teléfono celular");
            loginHerramientasDeAccesibilidadSteps.validarPantallaBlanquearContrasenia();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0242-BERSA: Herramientas de Accesibilidad Login- Desbloquear usuario - Presionar boton 6 'Desbloquear usuario' - Validar completitud de pantalla")
    public void TC0242BERSA() {
        try {
            loginSteps.waitGP();
            Thread.sleep(5000);
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            herramientasDeAccesibilidadSteps.presionarTecla1();
            herramientasDeAccesibilidadSteps.presionarTecla2();
            herramientasDeAccesibilidadSteps.presionarTecla1();
            loginHerramientasDeAccesibilidadSteps.ingresarUsuarioContrasenia("ecarina27");
            herramientasDeAccesibilidadSteps.presionarTeclaFin();
            herramientasDeAccesibilidadSteps.presionarTeclaFin();
            herramientasDeAccesibilidadSteps.presionarTecla3();
            herramientasDeAccesibilidadSteps.presionarTecla2();
            loginHerramientasDeAccesibilidadSteps.validarBotonDesbloquearUsuario();
            herramientasDeAccesibilidadSteps.presionarTecla6();
            loginHerramientasDeAccesibilidadSteps.validarTituloPageGenerico("Desbloqueá tu usuario");
            loginHerramientasDeAccesibilidadSteps.validarPantallaDesbloquearUsuario();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0243-BERSA: Herramientas de Accesibilidad Login- Recuperar usuario - Presionar boton 3 'Recuperar usuario' - Validar completitud de pantalla")
    public void TC0243BERSA() {
        try {
            loginSteps.waitGP();
            Thread.sleep(5000);
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            herramientasDeAccesibilidadSteps.presionarTecla1();
            herramientasDeAccesibilidadSteps.presionarTecla2();
            herramientasDeAccesibilidadSteps.presionarTecla3();
            loginHerramientasDeAccesibilidadSteps.validarTituloPageGenerico("Recuperá tu usuario");
            loginHerramientasDeAccesibilidadSteps.validarTituloPageGenerico("Por favor, ingresá tipo y número de documento.");
            loginHerramientasDeAccesibilidadSteps.validarPantallaRecuperarUsuario();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0244-BERSA: Herramientas de Accesibilidad Login- Tienda clic - Ingresar a tienda clic y validar redireccion de Url")
    public void TC0244BERSA() {
        try {
            loginSteps.waitGP();
            Thread.sleep(5000);
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            herramientasDeAccesibilidadSteps.presionarTecla1();
            herramientasDeAccesibilidadSteps.presionarTecla4();
            herramientasDeAccesibilidadSteps.presionarTecla1();
            loginHerramientasDeAccesibilidadSteps.validarFragmentoURL("tiendaclic");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0245-BERSA: Herramientas de Accesibilidad Login- Telefonos utiles - Ingresar a telefonos utiles y validar redireccion de Url")
    public void TC0245BERSA() {
        try {
            loginSteps.waitGP();
            Thread.sleep(5000);
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            herramientasDeAccesibilidadSteps.presionarTecla1();
            herramientasDeAccesibilidadSteps.presionarTecla5();
            loginHerramientasDeAccesibilidadSteps.validarFragmentoURL("telefonos-utiles");
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
