package pages.colocaciones_a_plazo;

import global.practia.jemeinsamstark.web.elements.PandoraElement;
import org.openqa.selenium.By;

public class ColocacionesAPlazoPage{

    public PandoraElement getConsultarDebinDesplegable(){
        return new PandoraElement(By.xpath("//input[@name='debinSearchType']/parent::div"));
    }
    public PandoraElement getFechaCreacionDesde(){
        return new PandoraElement(By.xpath("//div[contains(@aria-label,'n desde')]/div"));
    }
    public PandoraElement getFechaCreacionHasta(){
        return new PandoraElement(By.xpath("//div[contains(@aria-label,'n hasta')]/div"));
    }
    public PandoraElement getBuscar(){
        return new PandoraElement(By.xpath("//button[@aria-label='Buscar']"));
    }
    public PandoraElement getTablaHistoricoyPendiente(){
        return new PandoraElement(By.xpath("//*[contains(@aria-label, 'tabla')]"));
    }
    public PandoraElement getAdherirRecurrencia(){
        return new PandoraElement(By.xpath("//a[@href='/payments/debinAsBuyer/recurrencies/subscribe']"));
    }
    public PandoraElement getCuentaDebito(){
        return new PandoraElement(By.xpath("//input[@name='account']/parent::div"));
    }
    public PandoraElement getInputCuitVendedor(){
        return new PandoraElement(By.xpath("//input[@name='sellerCuit'] "));
    }
    public PandoraElement getDesplegablePrestacion(){
        return new PandoraElement(By.xpath("//input[@name='entitlement']/parent::div"));
    }
    public PandoraElement getDesplegableConcepto(){
        return new PandoraElement(By.xpath("//input[@name='conceptSelector']/parent::div"));
    }
    public PandoraElement getInputReferencia(){
        return new PandoraElement(By.xpath("//input[@name='reference']"));
    }
    public PandoraElement getInputDescription(){
        return new PandoraElement(By.xpath("//input[@name='description']"));
    }
    public PandoraElement getContinuar(){
        return new PandoraElement(By.xpath("//button[@aria-label='Continuar']"));
    }
    public PandoraElement getConsultarRecurrencias(){
        return new PandoraElement(By.xpath("//span[text()='Consultar recurrencias']"));
    }
    public PandoraElement getDesplegableEstado(){
        return new PandoraElement(By.xpath("//input[@name='statement']/parent::div"));
    }
}
