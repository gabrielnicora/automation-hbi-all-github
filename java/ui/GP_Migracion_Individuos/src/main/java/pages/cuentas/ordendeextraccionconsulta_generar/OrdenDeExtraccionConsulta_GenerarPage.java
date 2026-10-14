package pages.cuentas.ordendeextraccionconsulta_generar;

import global.practia.jemeinsamstark.web.elements.PandoraElement;
import org.openqa.selenium.By;

public class OrdenDeExtraccionConsulta_GenerarPage {

    public PandoraElement getCuentaOrigenDesplegable(){
        return new PandoraElement(By.xpath("//span[contains(text(),'Cuenta')]/../../..//div[contains(text(),'Seleccionar')]"));
    }
    public PandoraElement getFechaDesde(){
        return new PandoraElement(By.xpath("(//div[@id='customImputID'])[1]"));
    }
    public PandoraElement getFechaHasta(){
        return new PandoraElement(By.xpath("(//div[@id='customImputID'])[2]"));
    }
    public PandoraElement getDniDesplegable(){
        return new PandoraElement(By.id("selectorDescribedBy_1"));
    }
    public PandoraElement getInputDni(){
        return new PandoraElement(By.name("receiverDocument"));
    }
    public PandoraElement getInputReferencia(){
        return new PandoraElement(By.name("reference"));
    }
    public PandoraElement getBotonBuscar(){
        return new PandoraElement(By.id("filter.button.search.label"));
    }
    public PandoraElement getGenerarOrdenDeExtraccion(){
        return new PandoraElement(By.xpath("//*[@href='/atmPullOrder']"));
    }
    public PandoraElement getBotonAgregar(){
        return new PandoraElement(By.xpath("//button[@aria-label='Agregar']"));
    }
    public PandoraElement getIconoComprobante(){
        //return new PandoraElement(By.xpath("//div[contains(@*,'Icono de ver ticket')]/div"));
        return new PandoraElement(By.xpath("//div[contains(@*,'Icono de ver ticket')]/.."));
    }
}
