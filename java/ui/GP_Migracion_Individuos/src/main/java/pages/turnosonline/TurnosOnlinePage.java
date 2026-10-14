package pages.turnosonline;

import global.practia.jemeinsamstark.web.elements.PandoraElement;
import org.openqa.selenium.By;

public class TurnosOnlinePage {

    public PandoraElement getMensajeRedireccion(){
        return new PandoraElement(By.xpath("//span[contains(text(), 'Vas a ser redirigido a una ')]"));
    }
    public PandoraElement getLogoSFTurnoOnline(){
        return new PandoraElement(By.xpath("//div[@class='header-logo']"));
    }
}
