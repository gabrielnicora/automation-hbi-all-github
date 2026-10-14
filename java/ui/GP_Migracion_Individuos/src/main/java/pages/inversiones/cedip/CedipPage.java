package pages.inversiones.cedip;

import global.practia.jemeinsamstark.web.elements.PandoraElement;
import org.openqa.selenium.By;

public class CedipPage {
    public PandoraElement getConstituir() {return new PandoraElement(By.xpath("//span[contains(text(),'CONSTITUIR')]"));}
    public PandoraElement getLoQueTenesQueSaber() {return new PandoraElement(By.xpath("//span[contains(text(),'LO QUE TENÉS QUE SABER')]"));}
    public PandoraElement getTipoCedipDesplegable(){return new PandoraElement(By.xpath("//span[contains(text(),'Tipo')]/../../..//div[contains(text(),'Seleccionar')]"));}
    public PandoraElement getCapital(){return new PandoraElement(By.xpath("//input[@name='amount']"));}
    public PandoraElement getPlazoEnDias(){return new PandoraElement(By.xpath("//input[@id='term']"));}
    public PandoraElement getDespegableCuentaDebito() {return new PandoraElement(By.id("account"));}
    public PandoraElement getDespegableAccionAlVencer() {return new PandoraElement(By.xpath("//span[contains(text(),'Acción al vencimiento')]/../../..//div[contains(text(),'Seleccionar')]"));}


}
