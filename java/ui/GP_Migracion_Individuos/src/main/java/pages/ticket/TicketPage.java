package pages.ticket;

import global.practia.jemeinsamstark.web.elements.PandoraElement;
import org.openqa.selenium.By;

public class TicketPage {

    public PandoraElement getBotonDescarga(){
        return new PandoraElement(By.xpath("//div[@aria-label='Icono de descargar']"));
    }
    public PandoraElement getBotonDescargaBono(){
        return  new PandoraElement(By.xpath("(//div[@aria-label='Icono de descargar'])[2]"));
    }
    public PandoraElement getBotonImprimir(){
        return new PandoraElement(By.xpath("//*[@aria-label='Icono de imprimir']"));
    }
    public PandoraElement getEstadoTicket(){
        return new PandoraElement(By.xpath("//span[contains(text(), 'Tu solicitud') or (contains(text(),'Tu transacci'))]"));
    }
    public PandoraElement getCampoValorTicket(String campo){
        return new PandoraElement(By.xpath(String.format("(//span[text()='%s']/../following::p)[1]",campo)));
    }


}
