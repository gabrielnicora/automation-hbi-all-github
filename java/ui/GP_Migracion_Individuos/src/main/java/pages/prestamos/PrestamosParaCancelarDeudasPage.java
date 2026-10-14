package pages.prestamos;

import global.practia.jemeinsamstark.web.elements.PandoraElement;
import org.openqa.selenium.By;

public class PrestamosParaCancelarDeudasPage {
    public PandoraElement getPrestamoParaCancelarDeudas() {
        return new PandoraElement(By.xpath("//p[contains(text(),'stamo para cancelar deudas')]/.."));
    }

    public PandoraElement getCheckBoxSeleccionarTodos() {
        return new PandoraElement(By.xpath("//span[contains(text(),'Seleccionar T')]/.."));
    }

    public PandoraElement getPrimerCheckbox() {
        return new PandoraElement(By.xpath("(//p[contains(text(),'PRÉSTAMO')]//..//../div)[2]"));
    }

    public PandoraElement getCheckboxTarjetaCredito() {
        return new PandoraElement(By.xpath("//span[text()='Tarjetas']/../following::div//*[@data-src='../static/media/unchecked.535dfcb2.svg']"));
    }


}
