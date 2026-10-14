package pages.prestamos;

import global.practia.jemeinsamstark.web.elements.PandoraElement;
import org.openqa.selenium.By;

public class PrestamosPage {
    public PandoraElement getNuevoPrestamo() {
        return new PandoraElement(By.xpath("//p[contains(text(),'Nuevo pr')]/.."));
    }
    public PandoraElement getPrestamoParaCancelarDeudas() {
        return new PandoraElement(By.xpath("//p[contains(text(),'tamo para cancelar deudas')]/.."));
    }
    public PandoraElement getValidarEtapa(String tituloEtapa) {
        return new PandoraElement(By.xpath(String.format("//p[contains(text(),'%s')]", tituloEtapa)));
    }
    public PandoraElement textos(String text) {
        return new PandoraElement(By.xpath(String.format("//*[contains(text(), '%s')]",text)));
    }
    public PandoraElement getInputMonto() {
        return new PandoraElement(By.xpath("//*[@name='amount']"));
    }
    public PandoraElement getSiguiente() {
        return new PandoraElement(By.xpath("//button[@aria-label='Siguiente']"));
    }
    public PandoraElement getMensajeMontoMayorIgual(String mensajeMonto) {
        return new PandoraElement(By.xpath(String.format("//*[contains(text(), '%s')]",mensajeMonto)));

    }
    public PandoraElement getMensajeMonto0() {
        return new PandoraElement(By.xpath("//*[contains(text(), 'El monto no puede ser cero')]"));

    }
    public PandoraElement getSelectorDestinacion() {
        return new PandoraElement(By.xpath("//input[@name='loanDestination']/.."));
    }
    public PandoraElement getLabelCuotas(String cuotas) {
        return new PandoraElement(By.xpath(String.format("//label[@for='%s']", cuotas)));
    }
    public PandoraElement getDescargarResumenContrato() {
        return new PandoraElement(By.xpath("//span[contains(text(),'Resumen de ')]"));
    }


}
