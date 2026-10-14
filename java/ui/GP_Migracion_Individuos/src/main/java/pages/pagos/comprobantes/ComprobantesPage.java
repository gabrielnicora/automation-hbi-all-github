package pages.pagos.comprobantes;

import global.practia.jemeinsamstark.web.elements.PandoraElement;
import org.openqa.selenium.By;

public class ComprobantesPage {
    public PandoraElement getEmpresa() {
        return new PandoraElement(By.xpath("//input[@name='ente']/parent::div"));
    }
    public PandoraElement getInputEmpresa(){
        return new PandoraElement(By.xpath("//input[@name='ente']/parent::div/descendant::input[1]"));
    }
    public PandoraElement getDescripcion() {
      return new PandoraElement(By.xpath("//input[@name='payment']/parent::div"));
    }

    public PandoraElement getCalendarioFechaDesde(){
        return new PandoraElement(By.xpath("//div[contains(@aria-label,'Desde')]/div"));
    }
    public PandoraElement getTresPuntos(){
        return new PandoraElement(By.xpath("(//div[@role='button'])[2]"));
    }
    public PandoraElement getDescargar(){
        return new PandoraElement(By.xpath("//div[@aria-label='Icono de descargar']"));
    }

    public PandoraElement getCalendarDesde(){
        return new PandoraElement(By.xpath("//div[contains(@aria-label,'Desde')]/*[2]"));
    }
    public PandoraElement getBotonBuscar(){
        return new PandoraElement(By.xpath("//span[contains(text(),'Buscar')]"));
    }
    public PandoraElement getDescargarPeriodoEnPDF(){
        return new PandoraElement(By.xpath("//*[contains(text(),'Descargar')]"));
    }
    public PandoraElement getDescargarPeriodoXLS(){
        return new PandoraElement(By.xpath("//*[contains(text(),'Ver pagos en XLS')]"));
    }

    public PandoraElement getMensajeXLS(){
        return new PandoraElement(By.xpath("//*[contains(text(),'Pagos de impuestos o servicios realizados.xls')]"));
    }
    public PandoraElement getMensajePDF(){
        return new PandoraElement(By.xpath("//*[contains(text(),'Pagos de impuestos o servicios realizados.pdf')]"));
    }

    public PandoraElement getSeleccionarEmpresa(String opcion) {
        return new PandoraElement(By.xpath("//div[contains(text(), '"+ opcion +"')]"));}


}
