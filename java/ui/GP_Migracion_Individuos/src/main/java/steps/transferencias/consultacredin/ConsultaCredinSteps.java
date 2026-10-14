package steps.transferencias.consultacredin;

import global.practia.jemeinsamstark.web.elements.PandoraElement;
import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import pages.transferencias.consultacredin.ConsultaCredinPage;
import steps.botonesgenericos.BotonesGenericosSteps;
import utils.UtilsGP;

public class ConsultaCredinSteps extends BotonesGenericosSteps {

    private ConsultaCredinPage consultaCredin = new ConsultaCredinPage();

    @Step("Click en Generar Credin")
    public void clickGenerarCredin() {
        UtilsGP.fixedWait(3000);
        consultaCredin.getGenerarCredin().click();
    }

    @Step("Click en Buscar como Originante")
    public void clickOriginante() {
        consultaCredin.getOriginante().click();
    }

    @Step("Click en Buscar como Destinatario")
    public void clickDestinatario() {
        consultaCredin.getDestinatario().click();
    }

    @Step("Click desplegable Estado")
    public void clickEstado() {
        scrollTo(consultaCredin.getEstado().getElementBy());
        consultaCredin.getEstado().click();
    }

    @Step("Click Campo CUIT Originante")
    public void clickCuitOriginante() {
        consultaCredin.getCuitOriginante().click();
    }

    @Step("Click Campo CUIT Destinatario")
    public void clickCuitDestinatario() {
        scrollTo(By.xpath("//input[@name='cuitRecipient']"));
        consultaCredin.getCuitDestinatario().click();
    }

    @Step("Set CUIT Originante")
    public void setCuitOriginante(String cuit) {
        consultaCredin.getCuitOriginante().sendKeys(cuit);
    }

    @Step("Set CUIT Destinatario")
    public void setCuitDestinatario(String cuit) {
        scrollTo(By.xpath("//input[@name='cuitRecipient']"));
        consultaCredin.getCuitDestinatario().sendKeys(cuit);
    }

    @Step("Click detalle de CREDIN")
    public void clickDetalleCredin() {
        this.scrollTo(By.xpath("(//div[@aria-label='Icono de flecha derecha '])[1]"));
        consultaCredin.getDetalleCredin().click();
    }
    @Step("Validacion de Descripcion de CREDIN")
    public void validacionDescripcionCredin(String descripcion){
        scrollTo(By.xpath("//span[text()='Descripción']/../following-sibling::p"));
        String DescripcionCredin=consultaCredin.getDescripcionCredin().getText();
        Assert.assertEquals("La descripcion no coincide con la esperada",descripcion,DescripcionCredin);    }
    @Step("Click Selector Tarjeta Debito")
    public void clickSelectorTarjetaDebito(){
        consultaCredin.getTarjetaDeDebitoSelector().click();
    }

}



