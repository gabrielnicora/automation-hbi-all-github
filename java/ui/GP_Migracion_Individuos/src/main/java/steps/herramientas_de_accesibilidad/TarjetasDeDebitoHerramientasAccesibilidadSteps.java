package steps.herramientas_de_accesibilidad;

import global.practia.jemeinsamstark.web.core.WebDriver;
import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.tarjetasdedebito.TarjetasDeDebitoPage;
import pages.transferencias.agenda_nuevo_destinatario.AgendaNuevoDestinatarioPage;
import steps.botonesgenericos.BotonesGenericosSteps;
import steps.login.LoginSteps;
import steps.tarjetasdedebito.TarjetasDeDebitoSteps;

public class TarjetasDeDebitoHerramientasAccesibilidadSteps extends HerramientasDeAccesibilidadSteps {
    Actions actions = new Actions(WebDriver.getInstance());
    TarjetasDeDebitoPage tarjetasDeDebitoPage = new TarjetasDeDebitoPage();
    LoginSteps loginSteps= new LoginSteps();
    @Step("Ingresar a Tarjetas de Debito")
    public void ingresarATarjetasDebito() throws InterruptedException {
        presionarTeclaFin();
        presionarTecla12();
        sleep();
        presionarTecla14();
        loginSteps.clickSalirPrestamo();
    }

    @Step("Ingresar a Seguimiento de tarjeta")
    public void ingresarASeguimientoDeTarjeta() throws InterruptedException {
        presionarTecla2();
    }

    @Step("Ingresar a Ver mas tarjeta de debito")
    public void ingresarAVerMas() throws InterruptedException {
        if (tarjetasDeDebitoPage.getSeguimientoDeTaretas().isDisplayed()) {
            presionarTecla3();
        } else {
            presionarTecla2();
        }

    }

    @Step("Valida titulo visa debito")
    public void validarTituloVisaDebito() throws InterruptedException {
        Assert.assertTrue("El titulo visa debito no esta visible", tarjetasDeDebitoPage.getTituloVisaDebito().isDisplayed());
    }

    @Step("Ingresar a Gestion de limites")
    public void ingresarAGestionDeLimites() throws InterruptedException {
        presionarTecla3();
    }

    @Step("Ingresar a Blanquear Pin")
    public void ingresarABlanquearPin() throws InterruptedException {
        presionarTecla4();
    }

    @Step("Ingresar a Bloqueo de tarjeta")
    public void ingresarABloqueoTarjeta() throws InterruptedException {
        //MAS OPCIONES
        presionarTecla5();
        Thread.sleep(1000);
        //BLOQUEO TARJETA
        presionarTecla6();
    }

    @Step("Ingresar a Consultar Compras")
    public void ingresarAConsultarCompras() throws InterruptedException {
        Thread.sleep(1000);
        //MAS OPCIONES
        presionarTecla5();
        Thread.sleep(1000);
        //CONSULTAR COMPRAS
        presionarTecla7();
    }

    @Step("Ingresar a Definir cuenta primaria")
    public void ingresarADefinirCuentaPrimaria() throws InterruptedException {
        //MAS OPCIONES
        Thread.sleep(1000);
        presionarTecla5();
        Thread.sleep(1000);
        //DEFINIR CUENTA PRIMARIA
        presionarTecla8();
    }

    @Step("Ingresar a Aviso de viaje al exterior")
    public void ingresarAAvisoDeViajeAlExterior() throws InterruptedException {
        //MAS OPCIONES
        Thread.sleep(1000);
        presionarTecla5();
        Thread.sleep(1000);
        //Aviso de viaje al exterior
        presionarTecla9();
    }

    @Step("Ingresar a Consulta devolucionIVA")
    public void ingresarAConsultaDevolucionIva() throws InterruptedException {
        //MAS OPCIONES
        Thread.sleep(1000);
        presionarTecla5();
        //Aviso de viaje al exterior
        Thread.sleep(1000);
        presionarTecla10();
    }

    @Step("Ingresar a Cuotificalo")
    public void ingresarACuotificalo() throws InterruptedException {
        presionarTecla2();
    }
}
