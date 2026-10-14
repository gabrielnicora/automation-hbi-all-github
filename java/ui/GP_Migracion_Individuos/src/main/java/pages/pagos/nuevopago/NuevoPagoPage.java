package pages.pagos.nuevopago;

import global.practia.jemeinsamstark.web.elements.PandoraElement;
import org.openqa.selenium.By;


public class NuevoPagoPage {


    public PandoraElement getTarjetaDebito() {
        return new PandoraElement(By.id("debitCard"));
    }
    public PandoraElement getRubro() {
        return new PandoraElement(By.id("category"));
    }
    public PandoraElement getCardServicios() {
        return new PandoraElement(By.xpath("//p[contains(text(),'Servicios')]/../.."));
    }
    public PandoraElement getInputRubro() {
        //return new PandoraElement(By.xpath("//*[@name='category']/../descendant::input[1]"));
            return new PandoraElement(By.xpath("(//span[contains(text(),'Rubro')]/../../../div) | (//div[contains(text(),'Buscar rubro')]/../../../div)"));
    }
    public PandoraElement getEmpresa() {
        return new PandoraElement(By.id("entity"));
    }
    public PandoraElement getInputEmpresa() {
        //return new PandoraElement(By.xpath("//*[@name='entity']/../descendant::input[1]"));
        return new PandoraElement(By.xpath("//span[contains(text(),'Empresa')]/../../../div"));
    }
    public PandoraElement getElementoCodigoPago() {
        return new PandoraElement(By.xpath("//div[contains(text(), 'digo de pago')]"));
    }
    public PandoraElement getPagandoDesde() {
        return new PandoraElement(By.id("debitAccount"));
    }
    public PandoraElement getCodigoDePago() {
        return new PandoraElement(By.id("paymentCode"));
    }

    public PandoraElement getAdherirServicio(){return new PandoraElement((By.xpath("//*[contains(text(),'Añadir a servicios adheridos')]/../..")));}
    public PandoraElement getTarjetaDebitoDesplegable(){
        return new PandoraElement(By.xpath("(//*[contains(text(), 'Tarjeta de ')]/following::div[1])[2]"));
    }

    public PandoraElement getlistaRubro(){
        return new PandoraElement(By.xpath("//div[@id='selectorDescribedBy_1']"));
    }

    public PandoraElement getlistaEmpresa(){
        return new PandoraElement(By.xpath("//div[@id='selectorDescribedBy_2']"));
    }

}
