package pages.inversiones.fci;

import global.practia.jemeinsamstark.web.core.WebDriver;
import global.practia.jemeinsamstark.web.elements.PandoraElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SuscripcionFCIPage {

    public PandoraElement getCuentaDebito() {
        return new PandoraElement(By.id("debitProduct"));
    }

    public List<WebElement> getTodosLosCheckboxNoChequeados(){
        return WebDriver.getInstance().findElements(By.xpath("//*[contains(@aria-label,'checkbox No chequeado')]"));
    }
    public PandoraElement textos(String text) {
        return new PandoraElement(By.xpath(String.format("//*[contains(text(), '%s')]",text)));
    }

    public PandoraElement getCamposAValidar(String campo){return new PandoraElement(By.xpath(String.format("(//span[text()='%s']/../following::p)[1]",campo)));}
}
