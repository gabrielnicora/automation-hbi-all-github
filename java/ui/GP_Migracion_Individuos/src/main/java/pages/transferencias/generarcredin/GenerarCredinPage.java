package pages.transferencias.generarcredin;

import global.practia.jemeinsamstark.web.elements.PandoraElement;
import org.openqa.selenium.By;

public class GenerarCredinPage {


    public PandoraElement getSelectorTarjetaDebito(){
        return new PandoraElement(By.id("transfers.form.debitCardSelector"));
    }

    public PandoraElement getSelectorCuentaDebito(){
        return new PandoraElement(By.xpath("//input[@name='accountSelector']/.."));
    }

    public PandoraElement getCheckCBU(){
        return new PandoraElement(By.xpath("//div[contains(@aria-label,'CBU/CVU')]//label[@for='CBU']/div"));
    }

    public PandoraElement getCheckAlias(){
        return new PandoraElement(By.xpath("//*[@id='ALIAS']/following-sibling::label"));
    }

    public PandoraElement getCampoAliasCbu (){
        return new PandoraElement(By.id("aliasCbu"));
    }

    public PandoraElement getSelectorConcepto(){
        return new PandoraElement(By.id("selectorDescribedBy_1"));
    }


    public PandoraElement getCampoDescripcion(){
        return new PandoraElement(By.name("description"));
    }

    public PandoraElement getCampoNComprobante(){
        return new PandoraElement(By.name("receiptNumber"));
    }

    public PandoraElement getCheckEscuentapropia(){
        return new PandoraElement(By.xpath("//input[@type='checkbox']/following-sibling::span"));
    }

    public PandoraElement getCheckTerminosyCondiciones(){
        return new PandoraElement(By.xpath("//*[@type='checkbox']/following-sibling::span"));
    }
    public PandoraElement getCodigoSeguridad(){
        return new PandoraElement(By.name("securityNumber"));
    }
    public PandoraElement getMes(){
        return new PandoraElement(By.name("month"));
    }
    public PandoraElement getAnio(){
        return new PandoraElement(By.name("year"));
    }

    public  PandoraElement getNumDeTarjetaDeDebitoSeleccionada (String numTarjeta) {
        return new PandoraElement(By.xpath(String.format("//*[contains(text(),'%s')]",numTarjeta)));
    }
}
