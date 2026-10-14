package pages.perfil.productosfavoritos;

import global.practia.jemeinsamstark.web.elements.PandoraElement;
import org.openqa.selenium.By;

public class ProductosFavoritosPage {

    public PandoraElement getDesplegableTipoDeProducto() {
        return new PandoraElement(By.xpath("//input[@name='productType']//preceding-sibling::div/div[1]/div"));
    }

    public PandoraElement getCheckSeleccionado(String cuenta) {
        return new PandoraElement(By.xpath(String.format("//div[contains(text(), '%s')]/parent::div//span[@aria-label='checkbox chequeado undefined']",cuenta)));

    }

    public PandoraElement getCheckNoSelecionado(String cuenta) {
        return new PandoraElement(By.xpath(String.format("//div[contains(text(), '%s')]/parent::div//span[@aria-label='checkbox No chequeado undefined']",cuenta)));
    }
}
