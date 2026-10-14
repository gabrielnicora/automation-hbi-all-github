package pages.tarjetasdedebito.consultacompras;

import global.practia.jemeinsamstark.web.elements.PandoraElement;
import org.openqa.selenium.By;

public class ConsultaDeComprasPage {
    public PandoraElement getDesplegableCuenta(){
        return new PandoraElement(By.xpath("//input[@name='account']/parent::div"));
    }
    public PandoraElement getFechaDesde(){
        return new PandoraElement(By.xpath("//div[contains(@aria-label,'Desde')]/div"));
    }
    public PandoraElement getTablaConsultaCompras(){
        return new PandoraElement(By.xpath("//div[@aria-label='tabla']"));
    }
}
