package pages.doblefactor;

import global.practia.jemeinsamstark.web.elements.PandoraElement;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import java.util.Set;

public class DoblefactorPage {

    public PandoraElement getCodigo(){
        return new PandoraElement(By.xpath("(//div[@aria-label='Icono de copiar']/parent::div)[1]"));
    }

    public PandoraElement setCodigo(){
        return new PandoraElement(By.id("secondFactor"));
    }

    public PandoraElement getConfirmarDobleFactor(){

        return new PandoraElement(By.xpath("//button[@type='submit']"));
    }

    public PandoraElement getNuevoCodigoSMS(){
        return new PandoraElement(By.xpath("//span[contains(text(),'SMS')]"));
    }

    public PandoraElement getIntentarOtroMedotodo(){
        return new PandoraElement(By.xpath("//span[contains(text(), 'Intentar con otro')]"));
    }

    public PandoraElement setCodigoVerificacion(){
        return new PandoraElement(By.id("mailCode"));
    }

    public PandoraElement getCodigoVerificacion(){
        return new PandoraElement(By.name("code"));
    }

    public PandoraElement setCodigoVerificacionTel(){
        return new PandoraElement(By.xpath("//input[@name='mobilePhoneCode']"));
    }
    public PandoraElement setOrigen(){
        return new PandoraElement(By.id("react-select-3-input"));
    }

    public PandoraElement update(){
        return new PandoraElement(By.xpath("//button[@aria-label='Update']"));
    }

    public PandoraElement getMensajeEmergente(){
        return new PandoraElement(By.cssSelector("[class='notification notification-error notification-visible']"));
    }

    public By getMensajeCodigoIngresadoInvalidoBy(){
        return By.xpath("//p[contains(normalize-space(.), 'Por favor') and contains(normalize-space(.), 'ingresado')]");
    }

    public PandoraElement gettUtilLogger(){
        return new PandoraElement(By.xpath("//span[contains(text(), 'utilLogger')]"));
    }

}
