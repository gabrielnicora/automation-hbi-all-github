package pages.inversiones.plazosfijos;

import global.practia.jemeinsamstark.web.elements.PandoraElement;
import org.openqa.selenium.By;

public class PlazosFijosPage {

    public PandoraElement getConsultaDeTasas() {
        return new PandoraElement(By.xpath("//a[@href='/fixedTerms/rates']"));
    }

    public PandoraElement getSolicitarPlazoFijo() {
        return new PandoraElement(By.xpath("//span[contains(text(),'Solicitar plazo fijo')]/../.."));
    }


    public PandoraElement getValidarMensajeDeError() {
        return new PandoraElement(By.xpath("//*[@id='mainLayout']/div[1]/div[2]/div/div/div[2]/div/p"));
    }





}
