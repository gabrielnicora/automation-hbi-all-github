package pages.transferencias.limitedetransferencia;

import global.practia.jemeinsamstark.web.elements.PandoraElement;
import org.openqa.selenium.By;
import steps.botonesgenericos.BotonesGenericosSteps;

public class LimiteDeTransferenciaPage extends BotonesGenericosSteps {

    public PandoraElement getTarjetaDebitoDesplegable(){
        return new PandoraElement(By.id("selectorDescribedBy_0"));
    }
    public PandoraElement getCuentaOrigenDesplegable(){
        return new PandoraElement(By.xpath("//*[contains(text(),'Cuenta Origen')]/ancestor::div[1]//*[contains(@id,'selector')]"));
    }
    public PandoraElement getEnLaFechaCalendario(){
        return new PandoraElement(By.id("customImputID"));
    }
    public PandoraElement getCorreoElectronicoInput(){
        return new PandoraElement(By.name("email"));
    }
    public PandoraElement getBotonContinuar(){
        return new PandoraElement(By.id("tisRequest.button.continue"));
    }

}
