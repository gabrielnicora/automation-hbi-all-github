package GP.SantaCruz.herramientas_de_accesibilidad;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import steps.calendarios.CalendarioSteps;
import steps.doblefactor.DobleFactorSteps;
import steps.herramientas_de_accesibilidad.EscritorioHerramientasDeAccesibilidadSteps;
import steps.herramientas_de_accesibilidad.HerramientasDeAccesibilidadSteps;
import steps.herramientas_de_accesibilidad.RecargasHerramientasDeAccesibilidadStep;
import steps.login.LoginSteps;
import steps.menulateral.MenuLateralSteps;
import steps.recargas.RecargasSteps;
import steps.recargas.consultarecargas.ConsultaRecargasSteps;
import steps.ticket.TicketSteps;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static db.ConexionDB.cerrarConexion;
import static utils.Constantes.*;


public class RecargasHerramientasDeAccesibilidadTest {

    LoginSteps loginSteps = new LoginSteps();
    MenuLateralSteps menuLateralSteps = new MenuLateralSteps();
    RecargasSteps recargasSteps = new RecargasSteps();
    DobleFactorSteps dobleFactorSteps = new DobleFactorSteps();
    TicketSteps ticketSteps = new TicketSteps();
    ConsultaRecargasSteps consultaRecargasSteps = new ConsultaRecargasSteps();
    HerramientasDeAccesibilidadSteps herramientasDeAccesibilidadSteps = new HerramientasDeAccesibilidadSteps();
    RecargasHerramientasDeAccesibilidadStep recargasHerramientasDeAccesibilidadStep = new RecargasHerramientasDeAccesibilidadStep();
    CalendarioSteps calendario = new CalendarioSteps();


    @Test
    @DisplayName("TC-0302-BSC: Herramientas de Accesibilidad Recragas - Recargas de servicio - Validar Redireccion a la seccion de Recargas.")
    public void TC0302BSC() {
        try {
            loginSteps.iniciarSesion(uPruebaqaBSC03);
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            recargasHerramientasDeAccesibilidadStep.ingresarARecargas();
            recargasHerramientasDeAccesibilidadStep.validarTituloPageGenerico("Recargas");
            recargasHerramientasDeAccesibilidadStep.validarPantallaDeRecargas();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-0303-BSC: Herramientas de Accesibilidad Recragas - Recargas adheridas - Consulta de recargas adheridas 'Trasnporte publico'")
    public void TC0303BSC() {
        try {
            loginSteps.iniciarSesion(uPruebaqaBSC03);
            menuLateralSteps.clickRecargas();
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            recargasHerramientasDeAccesibilidadStep.completitudRecargasAdheridasSube();
            recargasHerramientasDeAccesibilidadStep.validarRecargasAdheridas();
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
