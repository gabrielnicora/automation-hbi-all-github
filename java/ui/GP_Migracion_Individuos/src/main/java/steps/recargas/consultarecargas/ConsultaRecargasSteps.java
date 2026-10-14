package steps.recargas.consultarecargas;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import pages.recargas.consultarecargas.ConsultaRecargasPage;
import steps.botonesgenericos.BotonesGenericosSteps;

public class ConsultaRecargasSteps extends BotonesGenericosSteps {
    ConsultaRecargasPage consultaRecargasPage= new ConsultaRecargasPage();
    @Step("Click en Desplegable Tarjeta de Debito")
    public void clickTarjetaDebito(){
        consultaRecargasPage.getTarjetaDeDebito().click();
    }
    @Step("Click en Desplegable Ente")
    public void clickEnte(){
        consultaRecargasPage.getEnte().click();
    }
    @Step("Click en Desplegable Numero")
    public void clickNumero(){
        consultaRecargasPage.getNumero().click();
    }
    @Step("Click en Desplegable Canal")
    public void clickCanal(){
        consultaRecargasPage.getCanal().click();
    }
    @Step("Setear {importe}")
    public void setImporte(String importe){
        consultaRecargasPage.getImporte().sendKeys(importe);
    }
    @Step("Click Fecha de Creacion Desde")
    public void clickFechaCreacion(){
        scrollTo(By.xpath("//div[contains(@aria-label,'Desde')]/div"));
        consultaRecargasPage.getFechaCreacionDesde().click();
    }
    @Step("Click Icono Ver Ticket")
    public void clickVerTicket(){
        consultaRecargasPage.getIconoVerTicket().click();
    }
    @Step("Click Icono Ver Ticket")
    public void clickIconoDescargar(){
        consultaRecargasPage.getIconoDescargar().click();
    }
    @Step("Validar Numero de Tarjeta Sube")
    public void validarNumeroTarjetaSube(String numSube){
        if (consultaRecargasPage.getNumeroTarjetaSube(numSube).isDisplayed()) {
            System.out.println("El numero de tarjeta sube seleccionado es: " + numSube);
        }
    }
    @Step("Validar fecha de desde")
    public void validarFechaDesde(String fecha){
        if (consultaRecargasPage.getFechaDesde(fecha).isDisplayed()) {
            System.out.println("Fecha seleccionada para la Consulta de recargas: " + fecha);
        }
    }
    @Step("Validar tabla recargas realizadas")
    public void validarRecargasRealizadas(){
        if(!consultaRecargasPage.getTablaRecargasRealizadas().isDisplayed()){
            throw new Error("No exista tabla recargas realizadas");
        }

    }
}
