package pages.cuentas.cuotificalo;

import global.practia.jemeinsamstark.web.elements.PandoraElement;
import org.openqa.selenium.By;

public class CuotificaloPage {
    public PandoraElement getCuotificalo(){
        return new PandoraElement(By.xpath("//button[@aria-label='Cuotificalo']"));
    }
    public PandoraElement getResumenContrato(){
        return new PandoraElement(By.xpath("//*[text()='Descargar Resumen de Contrato']"));
    }
    public PandoraElement getPrimerCheckbox(){
        return new PandoraElement(By.xpath("(//input[@type='checkbox']/parent::div/descendant::span)[1]"));
    }
    public PandoraElement getSegundoCheckbox(){
        return new PandoraElement(By.xpath("(//input[@type='checkbox']/parent::div/descendant::span)[2]"));
    }
    public PandoraElement getTercerCheckbox(){
        return new PandoraElement(By.xpath("(//input[@type='checkbox']/parent::div/descendant::span)[3]"));
    }
    public PandoraElement getCuartoCheckbox(){
        return new PandoraElement(By.xpath("(//input[@type='checkbox']/parent::div/descendant::span)[4]"));
    }
    public PandoraElement getPlazoDoceCuotas(){
        return new PandoraElement(By.xpath("(//p[contains(text(), '12')]/../..//input[@name='optionsCancelationLine']/..)[1]"));
    }
    public PandoraElement getCuentaDeAcreditacion(){
        return new PandoraElement(By.id("loan.request.accreditationAccount"));
    }
    public PandoraElement getCuentaDebito(){
        return new PandoraElement(By.id("loan.request.debitAccount"));
    }
}
