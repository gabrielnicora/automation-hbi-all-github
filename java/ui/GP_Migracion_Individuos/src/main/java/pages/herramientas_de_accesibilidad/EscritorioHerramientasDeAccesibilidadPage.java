package pages.herramientas_de_accesibilidad;
import global.practia.jemeinsamstark.web.elements.PandoraElement;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;



public class EscritorioHerramientasDeAccesibilidadPage {

    public PandoraElement getOpcionesHerraientaDeAccesibilidad(String text) {

        return new PandoraElement(By.xpath(String.format("//span[contains(text(), '%s')]",text)));
    }
    public PandoraElement getTituloCardOferta(String numCard){
        return new PandoraElement(By.xpath(String.format("(//div[contains(@id,'container')]/descendant::p[1])[%s]",numCard)));
    }
    public PandoraElement getTituloCard2(String tituloCard){

        return new PandoraElement(By.xpath(String.format("//span[contains(text(),'%s')]/parent::h1",tituloCard)));
    }
    public PandoraElement getTituloCardCuenta(String numCard){

        return new PandoraElement(By.xpath(String.format("(//div[@data-index='%s']/descendant::p[4])[1]",numCard)));
    }
    public PandoraElement getTituloCuenta(String nameCuenta){

        return new PandoraElement(By.xpath(String.format("//h1[@aria-label='Alias de producto %s']",nameCuenta)));
    }
    public PandoraElement getTituloCardTarjetas(String numCard){

        return new PandoraElement(By.xpath(String.format("//span[text()='Tarjetas']/../../..//*[@data-index='%s']/descendant::p[1]",numCard)));
    }
    public PandoraElement getTituloTarjetas(String nameCard){

        return new PandoraElement(By.xpath(String.format("//h1[@aria-label='Alias de producto %s']",nameCard)));
    }
    public PandoraElement getTituloCardAccesoRapido(String numCard){

        return new PandoraElement(By.xpath(String.format("(//*[contains(text(), 'Acceso R')]/ancestor::div/following::div/*[p[contains(@href,'')]])[%s]",numCard)));
    }
    public PandoraElement getTituloAccesoRapido(String nameCard){

        return new PandoraElement(By.xpath(String.format("//h1[contains(@aria-label,'%s')]",nameCard)));
    }

}
