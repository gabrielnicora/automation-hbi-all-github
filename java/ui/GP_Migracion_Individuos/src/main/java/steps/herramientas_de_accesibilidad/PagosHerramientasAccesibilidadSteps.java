package steps.herramientas_de_accesibilidad;

import global.practia.jemeinsamstark.web.core.WebDriver;
import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.interactions.Actions;
import pages.doblefactor.DoblefactorPage;
import steps.botonesgenericos.BotonesGenericosSteps;

import java.awt.*;

public class PagosHerramientasAccesibilidadSteps extends HerramientasDeAccesibilidadSteps {
    Actions actions = new Actions(WebDriver.getInstance());
    BotonesGenericosSteps botonesGenericosSteps = new BotonesGenericosSteps();
    DoblefactorPage doblefactorPage = new DoblefactorPage();

    @Step("Ingresar a Pagos")
    public void ingresarAPagos() throws InterruptedException {
        presionarTeclaFin();
        sleep();
        presionarTecla9();
        presionarTecla10();
    }

    @Step("Ingresar a Nuevo pago")
    public void ingresarANuevoPago() throws InterruptedException {
        presionarTecla3();
    }

    @Step("Ingresar a Comprobantes")
    public void ingresarAComprobantes() throws InterruptedException {
        presionarTecla2();
    }

    @Step("Ingresar a Adherir Impuesto o Servicio")
    public void ingresarAdherirImpuesto() throws InterruptedException {
        //MAS OPCIONES
        presionarTecla4();
        Thread.sleep(1000);
        presionarTecla5();
    }

    @Step("Ingresar a Servicios Adheridos")
    public void ingresarAServiciosAdheridos() throws InterruptedException {
        //MAS OPCIONES
        presionarTecla4();
        Thread.sleep(1000);
        presionarTecla6();
    }

    @Step("Ingresar a Pagos Vep")
    public void ingresarAPagosVep() throws InterruptedException {
        //MAS OPCIONES
        presionarTecla4();
        Thread.sleep(1000);
        presionarTecla7();
    }

    @Step("Ingresar a Debin Vendedor")
    public void ingresarADebinVendedor() throws InterruptedException {
        //MAS OPCIONES
        presionarTecla4();
        Thread.sleep(1000);
        presionarTecla8();
        presionarTecla1();
    }

    @Step("Ingresar a Debin Comprador")
    public void ingresarADebinComprador() throws InterruptedException {
        //MAS OPCIONES
        presionarTecla4();
        Thread.sleep(1000);
        presionarTecla9();
        presionarTecla2();
    }

    @Step("Ingresar a Generar Orden Debin")
    public void ingresarAGenerarOrdenDebin() throws InterruptedException {
        presionarTecla2();
    }

    @Step("Ingresar a Consultar Adhesiones")
    public void ingresarAConsultarAdhesiones() throws InterruptedException {
        presionarTecla3();
    }

    @Step("Ingresar a Adherir recurrencias")
    public void ingresarAdherirRecurrencias() throws InterruptedException {
        presionarTecla2();
    }

    @Step("Ingresar a Consultar recurrencias")
    public void ingresarConsultarRecurrencias() throws InterruptedException {
        presionarTecla3();
    }

    @Step("Ingresar a Consultar contracargos")
    public void ingresarConsultarContracargos() throws InterruptedException {
        //MAS OPCIONES
        presionarTecla4();
        presionarTecla5();
    }

    @Step("Completar formulario de nuevo pago")
    public void completarFormularioNuevoPago() throws InterruptedException, AWTException {
        presionarTecla2();
        //TARJETA DE DÉBITO
      /*  presionarTecla1();
        presionarTeclaEspacio();
        botonesGenericosSteps.keyDownToText("6004");
        presionarTeclaFin();
        Thread.sleep(1000);*/

        //Rubro
        presionarTecla1();
        presionarTeclaEspacio();
        presionarTeclaEnter();
        presionarTeclaFin();

        //Codigo de pago
        presionarTecla3();
        completarCampo("99992153");
        presionarTeclaFin();

        //Empresa
        presionarTecla2();
        presionarTeclaEspacio();
        presionarTeclaEnter();
        presionarTeclaFin();
    }

    @Step("Validar input segundo factor")
    public void validarInputSegundoFactor() throws InterruptedException {
        Assert.assertTrue(doblefactorPage.setCodigo().isDisplayed());
    }
}
