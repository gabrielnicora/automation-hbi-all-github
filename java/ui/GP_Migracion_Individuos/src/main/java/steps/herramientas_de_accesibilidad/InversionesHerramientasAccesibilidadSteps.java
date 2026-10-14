package steps.herramientas_de_accesibilidad;

import global.practia.jemeinsamstark.web.core.WebDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.interactions.Actions;
import pages.doblefactor.DoblefactorPage;
import steps.botonesgenericos.BotonesGenericosSteps;

public class InversionesHerramientasAccesibilidadSteps extends HerramientasDeAccesibilidadSteps{
    Actions actions = new Actions(WebDriver.getInstance());
    BotonesGenericosSteps botonesGenericosSteps = new BotonesGenericosSteps();
    DoblefactorPage doblefactorPage= new DoblefactorPage();

    @Step("Ingresar a Inversiones")
    public void ingresarAInversiones() throws InterruptedException {
        presionarTeclaFin();
        sleep();
        presionarTecla8();
    }
    @Step("Ingresar a Compra venta de dolar")
    public void ingresarACompraVentaDeDolar() throws InterruptedException {
        sleep();
        presionarTecla4();
        presionarTecla1();
    }
    @Step("Ingresar a Fondo comun de inversion")
    public void ingresarAFondoComunInversion() throws InterruptedException {
        sleep();
        presionarTecla4();
        presionarTecla2();
    }
    @Step("Ingresar a Plazos Fijos")
    public void ingresarAPlazosFijos() throws InterruptedException {
        sleep();
        presionarTecla4();
        presionarTecla3();
    }
    @Step("Ingresar a Plazos Fijos CEDIP")
    public void ingresarAPlazoFijoCEDIP() throws InterruptedException {
        sleep();
        presionarTecla4();
        presionarTecla4();
    }
    @Step("Ingresar a Consulta de tasas")
    public void ingresarAConsultaDeTasas() throws InterruptedException {
        sleep();
        presionarTecla2();
    }
    @Step("Ingresar a Solicitar plazo fijo")
    public void ingresarASolicitarPlazoFijo() throws InterruptedException {
        sleep();
        presionarTecla3();
    }
    @Step("Ingresar a Solicitar Nueva cuenta comitente")
    public void ingresarASolicitarNuevaCuentaComitente() throws InterruptedException {
        sleep();
        presionarTecla2();
    }
    @Step("Ingresar a Solicitar baja de cuenta comitente")
    public void ingresarABajaCuentaComitente() throws InterruptedException {
        sleep();
        //MAS OPCIONES
        presionarTecla3();
        sleep();
        presionarTecla4();
    }
}
