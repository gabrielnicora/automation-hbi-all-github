package pages.seguros;

import global.practia.jemeinsamstark.web.elements.PandoraElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class SegurosPage {

    public PandoraElement getCardSeguroNotebook() {
        return new PandoraElement(By.xpath("//p[text()='Notebook']/.."));
    }
    public PandoraElement getCardSeguroRobo() {
        return new PandoraElement(By.xpath("//p[contains(text(),'Robo Bolso')]/.."));
    }
    public PandoraElement getCardSeguroAccidentes() {
        return new PandoraElement(By.xpath("//p[contains(text(),'Accidentes')]/.."));
    }
    public PandoraElement getCardSeguroHogar() {
        return new PandoraElement(By.xpath("//p[text()='Hogar']/.."));
    }

    public PandoraElement getValidacionText(String text) {
        return new PandoraElement(By.xpath(String.format("(//*[contains(text(), '%s')])[1]", text)));
    }
    public PandoraElement getValidacionCards(String card) {
        return new PandoraElement(By.xpath(String.format("(//p[contains(text(),'%s')]/..)[1]", card)));
    }
    public PandoraElement getDescargaResumenContrato() {
        return new PandoraElement(By.xpath("//span[contains(text(), 'Resumen de contrato')]"));
    }


}
