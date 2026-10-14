package pages.comparacion_de_comisiones;

import global.practia.jemeinsamstark.web.core.WebDriver;
import global.practia.jemeinsamstark.web.elements.PandoraElement;
import org.openqa.selenium.By;

public class ComparacionComisionesPage {

    public PandoraElement getLogoPopupComparacionComisiones(){
        return new PandoraElement(By.xpath("//div[@class='modal-content']//*[contains(@aria-label,'Banco')]"));
    }

    public PandoraElement getTituloPopupComparacionComisiones() {
        return new PandoraElement(By.xpath("(//*[contains(text(),'de Comisiones')])[1]"));
    }

    public PandoraElement getBannerPaginaBancoCentral(){
        return new PandoraElement(By.xpath("//img[@src='../Imagenes/header/encabezado.jpg']"));
    }

}
