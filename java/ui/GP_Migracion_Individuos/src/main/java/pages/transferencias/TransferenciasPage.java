package pages.transferencias;

import global.practia.jemeinsamstark.web.elements.PandoraElement;
import org.openqa.selenium.By;

public class TransferenciasPage {

    public PandoraElement getConsultarCredin(){
        return new PandoraElement(By.xpath("(//*[@id='mainLayout']//a[@href='/diaryTransfer/credins?fromMenu=true'])"));
    }
    public PandoraElement getCuentaDesplegable(){
        return new PandoraElement(By.id("accounts"));
    }

    public PandoraElement getCardTransferenciasInmediatas(){
        return new PandoraElement(By.xpath("//span[contains(text(), 'Transferencias inmediatas')]/../.."));
    }
    public PandoraElement getTransferenciasProgramadas(){
        return new PandoraElement(By.xpath("//span[contains(text(),'Transferencias programadas')]"));
    }

}
