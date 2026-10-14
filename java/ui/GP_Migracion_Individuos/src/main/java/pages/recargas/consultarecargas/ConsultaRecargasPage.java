package pages.recargas.consultarecargas;

import global.practia.jemeinsamstark.web.elements.PandoraElement;
import org.openqa.selenium.By;

public class ConsultaRecargasPage{
    public PandoraElement getTarjetaDeDebito() {
        return new PandoraElement(By.xpath("//input[@name='debitCards']/parent::div"));
    }
    public PandoraElement getEnte() {
        return new PandoraElement(By.xpath("//input[@name='companies']/parent::div"));
    }
    public PandoraElement getNumero() {
        return new PandoraElement(By.xpath("//*[@id='selectorDescribedBy_1']"));
    }
    public PandoraElement getCanal() {
        return new PandoraElement(By.xpath("//input[@name='channelType']/parent::div"));
    }
    public PandoraElement getTablaRecargasRealizadas() {
        return new PandoraElement(By.xpath("//div[@class='sc-imWYAI fcVLEU']"));
    }
    public PandoraElement getImporte() {
        return new PandoraElement(By.xpath("//input[@label='Importe']"));
    }
    public PandoraElement getFechaCreacionDesde(){
        return new PandoraElement(By.xpath("//div[contains(@aria-label,'Desde')]/div"));
    }
    public PandoraElement getValidarTabla(){
        return new PandoraElement(By.xpath("//div[@aria-label='tabla']"));
    }

    public PandoraElement getIconoVerTicket(){
        return new PandoraElement(By.xpath("//div[@aria-label='Icono de ver ticket']"));
    }
    public PandoraElement getIconoDescargar(){
        return new PandoraElement(By.xpath("//div[@aria-label='Icono de descargar']"));
    }
    public PandoraElement getNumeroTarjetaSube(String numSube){
        return new PandoraElement(By.xpath(String.format("//*[contains(text(),'%s')]",numSube)));
    }
    public PandoraElement getFechaDesde(String fecha){
        return new PandoraElement(By.xpath(String.format("//*[contains(text(),'%s')]",fecha)));
    }
}
