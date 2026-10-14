package pages.pagos.serviciosadheridos;

import global.practia.jemeinsamstark.web.elements.PandoraElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ServiciosAdheridosPage {
    public PandoraElement getEmpresa() {
        return new PandoraElement(By.id("selectorDescribedBy_0"));
    }
    public PandoraElement getInputEmpresa() {
        return new PandoraElement(By.xpath("//*[contains(@aria-describedby, 'selectorDescribedBy_0')]"));
    }
    public PandoraElement getTresPuntos(String numeroDePago){
        return new PandoraElement(By.xpath(String.format("//*[text()='%s']/../../descendant::div//*[@id='Grupo_11105']", numeroDePago)));
    }
    public PandoraElement getEliminarServicioAdherido(){
        return new PandoraElement(By.xpath("//div[@aria-label='Icono de eliminar']"));
    }
    public PandoraElement getInputSearch(){return new PandoraElement(By.xpath("//input[@name='searchField']"));}

    public PandoraElement getConfirmarDesvinculacion() { return new PandoraElement(By.xpath("//span[contains(text(),'Si, desvincular')]/../.."));
    }
    public PandoraElement getTresPuntosCodPago(String codPago){
        return new PandoraElement(By.xpath("//p[contains(text(),'"+codPago+"')]/../../div/div/div"));
    }
    public PandoraElement getEliminarServicioAdheridoFV(){
        return  new PandoraElement(By.xpath("//div[contains(text(),'Eliminar servicio adherido')]"));
    }
}
