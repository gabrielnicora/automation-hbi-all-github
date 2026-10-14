package pages.validaciondeidentidad;

import global.practia.jemeinsamstark.web.elements.PandoraElement;
import org.openqa.selenium.By;

public class ValidacionDeIdentidadPage {

    public PandoraElement getDigitos(){
        return new PandoraElement(By.name("digits"));
    }
    public PandoraElement getPin(){
        return new PandoraElement(By.name("pinDebitCard"));
    }
    public PandoraElement getBotonTableroPinPorNro(String nroBoton){
        return new PandoraElement(By.xpath(String.format("//div[@class='hg-rows']/div/div[@data-skbtn='%s']", nroBoton)));
    }
}
