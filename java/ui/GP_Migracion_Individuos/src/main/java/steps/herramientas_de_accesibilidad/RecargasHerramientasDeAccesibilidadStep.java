package steps.herramientas_de_accesibilidad;

import global.practia.jemeinsamstark.web.core.WebDriver;
import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import pages.doblefactor.DoblefactorPage;
import pages.herramientas_de_accesibilidad.RecargasHerramientasDeAccesibilidadPage;
import steps.botonesgenericos.BotonesGenericosSteps;

import java.awt.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class RecargasHerramientasDeAccesibilidadStep extends HerramientasDeAccesibilidadSteps {
    Actions actions = new Actions(WebDriver.getInstance());
    BotonesGenericosSteps botonesGenericosSteps = new BotonesGenericosSteps();
    DoblefactorPage doblefactorPage= new DoblefactorPage();
    RecargasHerramientasDeAccesibilidadPage recargasHerramientasDeAccesibilidad = new RecargasHerramientasDeAccesibilidadPage();

    @Step("Ingresar a Recargas")
    public void ingresarARecargas() throws InterruptedException {
        presionarTeclaFin();
        sleep();
        presionarTecla9();
        presionarTecla11();
    }
    @Step("Validar pantalla de Recargas")
    public void validarPantallaDeRecargas() {
        List<String> campos = Arrays.asList(
                "TARJETA RECARGABLE/PREPAGA",
                "Telefonia Celular",
                "BILLETERA/SERV PREPAGO",
                "TRANSPORTE PUBLICO",
                "Adhesiones",
                "Consultar recargas");
        for (String campo : campos) {
            String campo1 = new String(campo.getBytes(),
                    StandardCharsets.UTF_8);
            scrollTo(By.xpath(String.format("//*[contains(text(), '%s')]", campo1)));
            Assert.assertTrue("El campo '" + campo1 + "' no esta visible", recargasHerramientasDeAccesibilidad.textos(campo1).isDisplayed());
        }
        System.out.println("Titulos validados con exito");
    }
    @Step("Validar Recargas adheridas")
    public void validarRecargasAdheridas() throws InterruptedException {
        Assert.assertTrue("Consulta adheridas no disponible", recargasHerramientasDeAccesibilidad.getTablaRegistros().isDisplayed());
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
    }

    @Step("Ingresar a Debin Comprador")
    public void ingresarADebinComprador() throws InterruptedException {
        //MAS OPCIONES
        presionarTecla4();
        Thread.sleep(1000);
        presionarTecla9();
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

    @Step("Recargas adheridas Sube")
    public void completitudRecargasAdheridasSube() throws InterruptedException, AWTException {
        //Ingresar a Adhesiones
        presionarTecla2();
        //RUBRO
        presionarTecla2();
        presionarTecla1();
        presionarTeclaEspacio();
        keyDownToText("TRANSPORTE PUBLICO");
        presionarTeclaAbajo();
        presionarTeclaEnter();
        //EMPRESA
        presionarTeclaFin();
        presionarTecla2();
        presionarTeclaEspacio();
        keyDownToText("SUBE");
        presionarTeclaArriba();
        presionarTeclaEnter();
        //BUSCAR
        presionarTeclaFin();
        presionarTecla3();

    }
    @Step("Validar input segundo factor")
    public void validarInputSegundoFactor() throws InterruptedException {
        Assert.assertTrue(doblefactorPage.setCodigo().isDisplayed());
    }
}
