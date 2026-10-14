package pages.login;

import global.practia.jemeinsamstark.web.elements.PandoraElement;
import org.openqa.selenium.By;

public class PantallaPrincipalPage {

    public PandoraElement btnTiendaClic(){
        return new PandoraElement(By.xpath("//div[@aria-label='Icono de compra']")); }
    public PandoraElement getLogoTiendaClic(){
        return new PandoraElement(By.xpath("(//img[@alt='Tienda Clic'])[1]")); }
    public PandoraElement btnTelefonosUtiles(){
        return new PandoraElement(By.xpath("//div[contains(@aria-label,'Icono de tel')]")); }
    public PandoraElement getTelefonoOficial(){
        return new PandoraElement(By.xpath("//strong[contains(text(),'Whatsapp oficial')]"));
    }
    public PandoraElement getCrearNuevoUsuario(){
        return new PandoraElement(By.xpath("//*[@id='startPage.button1.label']"));
    }
    public PandoraElement getTerminosYCondiciones(){
        return new PandoraElement(By.xpath("//a[contains(@href,'termsAndConditions')]"));
    }
    public PandoraElement TituloTerminosYCondiciones(){
        return new PandoraElement(By.xpath("//h1/span[contains(text(),Terminosycondiciones)]"));
    }
    public PandoraElement getInfoSeguridad(){
        return new PandoraElement(By.xpath("//span[contains(text(),'n de seguridad')]"));
    }
    public PandoraElement TituloPreguntasFrecuentes(String opcion){
        return new PandoraElement(By.xpath("//h1[contains(text(),'"+ opcion +"')]"));
    }
    public PandoraElement tituloSeguridadParaUsarHomeBanking(){
        return new PandoraElement(By.xpath("//h1[contains(text(),'Seguridad para usar Home Banking / Office Banking')]"));
    }
    public PandoraElement getBtnRegistrarme(){
        return  new PandoraElement(By.xpath("//span[contains(text(),'Registrarme')]"));
    }



}