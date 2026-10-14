package pages.utillogger;

import global.practia.jemeinsamstark.web.elements.PandoraElement;
import org.openqa.selenium.By;

public class UtilLoggerPage {

    public PandoraElement getBtnReuqest(String servicio) {

        return new PandoraElement(getBtnRequestBy(servicio));
    }

    public By getBtnRequestBy(String servicio) {
        return By.xpath("//h5[normalize-space(.)='"+servicio+"']/ancestor::div[2]/following-sibling::div//button[normalize-space(.)='Request']");
    }

    public By getBtnRequestByIndex(String servicio, int indice) {
        return By.xpath("(//h5[normalize-space(.)='"+servicio+"']/ancestor::div[2]/following-sibling::div//button[normalize-space(.)='Request'])["+indice+"]");
    }

//    public PandoraElement getDatoServicio(String servicio) {
//        return new PandoraElement(By.xpath("//pre"));
//
//        return new PandoraElement(By.xpath("//h5[contains(., '"+ servicio +"')] /ancestor::div[2] /following-sibling::div[1]"));
//    }

    public PandoraElement getDatoServicio() {
        return new PandoraElement(By.xpath("//button[contains(text(), 'Meta')] /ancestor::div[2] /following-sibling::div[1]"));
    }

    public By getBtnCerrarDetalleRequest() {
        return By.xpath("//button[.//*[local-name()='svg']/*[local-name()='path' and contains(@d, 'M4.293 4.293')]]");
    }

    public PandoraElement getBtnActualizar(){
        return new PandoraElement(By.xpath
                ("//button[contains(text(),'Actualizar')]"));
    }
}
