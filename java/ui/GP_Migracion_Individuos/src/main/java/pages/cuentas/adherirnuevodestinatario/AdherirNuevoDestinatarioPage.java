package pages.cuentas.adherirnuevodestinatario;

import global.practia.jemeinsamstark.web.elements.PandoraElement;
import org.openqa.selenium.By;

public class AdherirNuevoDestinatarioPage {

    public PandoraElement getCBU(){
        return new PandoraElement(By.xpath("//*[@id='CBU']/following-sibling::label"));
    }
    public PandoraElement getAlias(){
        return new PandoraElement(By.xpath("//label[@for='ALIAS']/*"));
    }
    public PandoraElement getInputCbuCvuAlias(){
        return new PandoraElement(By.id("account"));
    }
    public PandoraElement getBotonBuscar(){
        return new PandoraElement(By.xpath("//button[@aria-label='Buscar']"));
    }
    public PandoraElement getInputPerteneceA(){
        return new PandoraElement(By.id("reference"));
    }
    public PandoraElement getInputEmail(){
        return new PandoraElement(By.id("email"));
    }
    public PandoraElement getCheckboxEsCuentaPropia(){
        return new PandoraElement(By.xpath("//*[contains(@aria-label,'checkbox')]/div"));
    }
    public PandoraElement getInputCodigoDeSeguridad(){
        return new PandoraElement(By.name("securityNumber"));
    }
    public PandoraElement getInputMes(){
        return new PandoraElement(By.name("month"));
    }
    public PandoraElement getInputAnio(){
        return new PandoraElement(By.name("year"));
    }
}
