package pages.tarjetasdedebito;

import global.practia.jemeinsamstark.web.elements.PandoraElement;
import org.openqa.selenium.By;
import steps.botonesgenericos.BotonesGenericosSteps;

public class TarjetasDeDebitoPage extends BotonesGenericosSteps {

    public PandoraElement getSeguimientoDeTaretas(){
        return new PandoraElement(By.xpath("//*[@href='/creditCard/creditCardTracking']"));
    }
    public PandoraElement getBotonVerMas_Activar(String ultimosCuatroNrosTarjeta){
        return new PandoraElement(By.xpath(String.format("//*[contains(text(),'%s')]/parent::div/following-sibling::div[2]/a", ultimosCuatroNrosTarjeta)));
    }
    public PandoraElement getInputSegundoFactor(){
        return new PandoraElement(By.id("secondFactor"));
    }
    public PandoraElement getBotonConfirmar(){
        return new PandoraElement(By.xpath("//button[@aria-label='Confirmar']"));
    }
    public PandoraElement getConsultarCompras(){return new PandoraElement(By.xpath("//span[text()='Consultar compras']/parent::a"));}
    public PandoraElement getTituloVisaDebito(){return new PandoraElement(By.xpath("//h1[contains(@aria-label, 'Alias de producto ')]"));}

}
