package pages.herramientas_de_accesibilidad;

import global.practia.jemeinsamstark.web.elements.PandoraElement;
import org.openqa.selenium.By;

public class RecargasHerramientasDeAccesibilidadPage {
    public PandoraElement textos(String text) {
        return new PandoraElement(By.xpath(String.format("//*[contains(text(), '%s')]", text)));
    }
    public PandoraElement getTablaRegistros() {
        return new PandoraElement(By.xpath("//div[@aria-label='tabla']"));
    }
}