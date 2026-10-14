package pages.yopmail;

import global.practia.jemeinsamstark.web.elements.PandoraElement;
import org.openqa.selenium.By;

public class YopmailPage {

    public PandoraElement getInputMail(){
        return new PandoraElement(By.id("login"));
    }
    public PandoraElement getFlechaIngresarMail(){
        return new PandoraElement(By.id("refreshbut"));
    }
    public PandoraElement getMenuTresPuntos(){
        return new PandoraElement(By.xpath("//*[@class='menu']/preceding-sibling::button"));
    }
    public PandoraElement getVaciarCasilla(){
        return new PandoraElement(By.id("delall"));
    }
    public PandoraElement getPrimerMail(){
        return new PandoraElement(By.xpath("//body[@class='bodyinbox yscrollbar']/div[2]/div[2]/button"));
    }
    public PandoraElement getActualizarCasilla(){
        return new PandoraElement(By.id("refresh"));
    }
    public PandoraElement getTituloMail(){
        return new PandoraElement(By.xpath("//*[@class='fl']/div[1]"));
    }

    public PandoraElement getTituloEmail(String titulo){
        return new PandoraElement(By.xpath(String.format("//div[@class='fl']//*[contains(text(), '%s')]", titulo)));
    }

    public PandoraElement getLogoYopmail(){
        return new PandoraElement(By.id("logoacc"));
    }
}
