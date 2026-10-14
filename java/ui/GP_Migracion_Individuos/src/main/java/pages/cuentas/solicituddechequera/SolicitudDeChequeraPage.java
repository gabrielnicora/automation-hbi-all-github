package pages.cuentas.solicituddechequera;

import global.practia.jemeinsamstark.web.elements.PandoraElement;
import org.openqa.selenium.By;

public class SolicitudDeChequeraPage {

    public PandoraElement getTipoDeCheque(){
        return new PandoraElement(By.xpath("//input[@name='checkbookType']/.."));
    }
    public PandoraElement getCantidadDeCheques(){
        return new PandoraElement(By.id("selectorDescribedBy_1"));
    }
    public PandoraElement getCantidadDeChequeras(){
        return new PandoraElement(By.id("selectorDescribedBy_2"));
    }
}
