package pages.inversiones.fci;

import global.practia.jemeinsamstark.web.core.WebDriver;
import global.practia.jemeinsamstark.web.elements.PandoraElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class DetalleDelFondoPage {

    public PandoraElement getAplicar() {
        return new PandoraElement(By.xpath("//button[@aria-label='Aplicar']"));
    }

    public PandoraElement getSuscribir() {
        return new PandoraElement(By.xpath("//button[@aria-label='Suscribir']"));
    }

    public PandoraElement getRescatar() {
        return new PandoraElement(By.xpath("//button[@aria-label='Rescatar']"));
    }

    public PandoraElement getMovimientos() {
        return new PandoraElement(By.xpath("//p[text()='Movimientos']"));
    }

    public PandoraElement getDetalle() {
        return new PandoraElement(By.xpath("//p[contains(text(),'Detalle')]"));
    }

    public PandoraElement getRendimiento() {
        return new PandoraElement(By.xpath("//p[text()='Rendimiento']"));
    }

    public PandoraElement getFechaDesde() {
        return new PandoraElement(By.xpath("//div[@aria-label='calendario Desde']/div"));
    }

    public PandoraElement getFechaHasta() {
        return new PandoraElement(By.xpath("//div[@aria-label='calendario Hasta']/div"));
    }

    public PandoraElement getIconoDescargar(String nroFila) {
        return new PandoraElement(By.xpath(String.format("(//*[@aria-label='Icono de descargar'])[%s]", nroFila)));
    }
    public PandoraElement getIconoDescargar() {
        return new PandoraElement(By.xpath("(//*[@aria-label='Icono de descargar'])[1]"));
    }
    public PandoraElement getIconoComprobanteSolicitud() {
        return new PandoraElement(By.xpath("(//*[@aria-label='Comprobante de solicitud'])[1]"));
    }
    public List<WebElement> getTodasLasFechasDeConcertacion() {
        return WebDriver.getInstance().findElements(By.xpath("//*[@aria-label='fila']/div[1]/p"));
    }

    public List<WebElement> getTodasLasFechasDeLiquidacion() {
        return WebDriver.getInstance().findElements(By.xpath("//*[@aria-label='fila']/div[2]/p"));
    }

    public PandoraElement getFiltroSieteDias() {
        return new PandoraElement(By.xpath("//*[@id='mainLayout']//div[contains(text(),'7 d')]"));
    }
    public PandoraElement getFiltroTreintaDias() {
        return new PandoraElement(By.xpath("//*[@id='mainLayout']//div[contains(text(),'30 d')]"));
    }
    public PandoraElement getFiltroDoceMeses() {
        return new PandoraElement(By.xpath("//*[@id='mainLayout']//div[contains(text(),'12 meses')]"));
    }
}