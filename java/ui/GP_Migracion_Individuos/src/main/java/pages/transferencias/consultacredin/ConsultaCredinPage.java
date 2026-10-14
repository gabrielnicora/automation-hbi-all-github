package pages.transferencias.consultacredin;

import global.practia.jemeinsamstark.web.elements.PandoraElement;
import org.openqa.selenium.By;

public class ConsultaCredinPage {

    public PandoraElement getGenerarCredin(){
        return new PandoraElement(By.xpath("(//*[@id='mainLayout']//span[contains(text(),'Generar CREDIN')])"));
    }

    public PandoraElement getOriginante(){
        return new PandoraElement(By.xpath("//label[@for='Originante']"));
    }
    public PandoraElement getDestinatario(){
        return new PandoraElement(By.xpath("//label[@for='Destinatario']"));
    }
    public PandoraElement getEstado(){
        return new PandoraElement(By.xpath("//div[@id='selectorDescribedBy_0']"));
    }

    public PandoraElement getCuitDestinatario(){
        return new PandoraElement(By.xpath("//input[@name='cuitRecipient']"));    }

    public PandoraElement getCuitOriginante(){
        return new PandoraElement(By.xpath("//input[@name='cuitOriginator']"));    }

    public PandoraElement getDetalleCredin(){
        return new PandoraElement(By.xpath("(//div[@aria-label='Icono de flecha derecha '])[1]"));    }

    public PandoraElement getDescripcionCredin(){
        return new PandoraElement(By.xpath("//span[text()='Descripción']/../following-sibling::p"));    }

    public PandoraElement getTarjetaDeDebitoSelector() {
        return new PandoraElement(By.id("selectorDescribedBy_0"));
    }

}
