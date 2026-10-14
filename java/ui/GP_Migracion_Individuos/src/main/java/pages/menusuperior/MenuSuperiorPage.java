package pages.menusuperior;

import global.practia.jemeinsamstark.web.elements.PandoraElement;
import org.openqa.selenium.By;

public class MenuSuperiorPage {


    public PandoraElement getMensajes() {
        return new PandoraElement(By.xpath("//a[contains(@href, 'communications')]"));
    }
    public PandoraElement getPerfil() {
        return new PandoraElement(By.id("user"));
    }
    public PandoraElement getSalir() {
        return new PandoraElement(By.id("logOff"));
    }
}
