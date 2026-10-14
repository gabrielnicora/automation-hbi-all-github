package pages.cuentas.adelantosDeSueldo;

import global.practia.jemeinsamstark.web.elements.PandoraElement;
import org.openqa.selenium.By;

public class AdelantosDeSueldoPage {
    public PandoraElement getVerMasXnroCuenta(String nroCuenta){
        return new PandoraElement(By.xpath(String.format("//span[text()='%s']/ancestor::div[1]/following-sibling::div/a", nroCuenta)));
    }
    public PandoraElement getMasOpciones(){
        return new PandoraElement(By.xpath("//span[contains(text(),'Opciones')]"));
    }
    public PandoraElement getAdelantoDeSueldo(){
        return new PandoraElement(By.xpath("//span[text()='Solicitud Adelanto de Sueldo']"));
    }
    public PandoraElement textos(String text) {
        return new PandoraElement(By.xpath(String.format("//*[contains(text(),'%s')]",text)));
    }
    public PandoraElement getCampoRequerido(String titulo) {
        return new PandoraElement(By.xpath(String.format("(//*[contains(text(),'%s')])[1]", titulo)));
    }
    public PandoraElement getCuantoNecesitas(){
        return new PandoraElement(By.id("amount"));
    }
    public PandoraElement getResumenDeContrato(){
        return new PandoraElement(By.xpath("//*[contains(text(),'Resumen de contrato')]"));
    }
    public PandoraElement getMisAdelantos(){
        return new PandoraElement(By.xpath("//*[contains(text(),'Mis adelantos')]"));
    }
    public PandoraElement getMontoPermitido() {
        return new PandoraElement(By.xpath("//div[@class='notification notification-error notification-visible']"));
    }
    public PandoraElement getAccesoDirectoAdelantoSueldo(){
        return new PandoraElement(By.xpath("//p[contains(text(),'Adelanto de haberes')]"));
    }
    public PandoraElement getCardAdelantoSueldo(){
        return new PandoraElement(By.xpath("//span[contains(text(),'Adelanto de haberes') or contains (text(), 'Adelanto de sueldo')]"));
    }
    public PandoraElement getCheckTyC(){
        return new PandoraElement(By.xpath("//input[@name='acceptConditions']/../descendant::span[1]"));
    }
    public PandoraElement getDesplegableCuentaOrigen(){
        return new PandoraElement(By.xpath("//input[@name='accounts']/.."));
    }
    public PandoraElement getNoOfertaDisponible(){
        return new PandoraElement(By.xpath("//span[contains(text(),'No contamos con una oferta disponible para vos!')]"));
    }
}
