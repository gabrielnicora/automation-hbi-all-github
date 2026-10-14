package pages.baja_arrepentimiento;

import global.practia.jemeinsamstark.web.elements.PandoraElement;
import org.openqa.selenium.By;

public class BajaArrepentimientoPage {

    public PandoraElement getLogoPopupBajaArrepentimiento(){
        return new PandoraElement(By.xpath("//div[@class='modal-content']//*[contains(@aria-label,'Banco')]"));
    }

    public PandoraElement getTituloPopupBajaArrepentimiento() {
        return new PandoraElement(By.xpath("//span[contains(text(),'Baja y arrepentimiento')]"));
    }

    public PandoraElement getTituloOnlineBanking(){
        return new PandoraElement(By.xpath("//h1[contains(text(),'Arrepentimiento')]"));
    }
}
