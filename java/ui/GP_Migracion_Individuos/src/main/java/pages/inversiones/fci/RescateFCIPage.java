package pages.inversiones.fci;

import global.practia.jemeinsamstark.web.elements.PandoraElement;
import org.openqa.selenium.By;

public class RescateFCIPage {

    public PandoraElement getCuentaDestino() {
        return new PandoraElement(By.id("creditProduct"));
    }

    public PandoraElement getParcial() {
        return new PandoraElement(By.xpath("//label[@for='partial']/*"));
    }

    public PandoraElement getTotal() {
        return new PandoraElement(By.xpath("//label[@for='total']"));
    }

    public PandoraElement getInputCuotapartes(){
        return new PandoraElement(By.name("amount"));
    }

    public PandoraElement getImporteEstimadoRescatar(){
        return new PandoraElement(By.xpath("//*[text()='Importe estimado a rescatar']/../following-sibling::p/span"));
    }
}
