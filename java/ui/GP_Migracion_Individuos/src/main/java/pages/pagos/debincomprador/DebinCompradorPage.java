package pages.pagos.debincomprador;

import global.practia.jemeinsamstark.web.elements.PandoraElement;
import org.openqa.selenium.By;
import sun.security.x509.IPAddressName;

public class DebinCompradorPage {

    public PandoraElement getSelectorConsultatDebin() {
        return new PandoraElement(By.id("selectorDescribedBy_0"));
    }

    public PandoraElement getBuscar() {
        return new PandoraElement(By.xpath("//button[@aria-label='Buscar']"));
    }

    public PandoraElement getFechaCreacionDesde() {
        return new PandoraElement(By.xpath("//div[contains(@aria-label,'n desde')]/div"));
    }

    public PandoraElement getFechaCreacionHasta() {
        return new PandoraElement(By.xpath("//div[contains(@aria-label,'n hasta')]/div"));
    }

    public PandoraElement getColumnaIdDebin() {
        return new PandoraElement(By.xpath("//*[@id='mainLayout']//span[contains(text(),'Id DEBIN')]"));
    }

    public PandoraElement getMsjSinDebinesRecibidos() {
        return new PandoraElement(By.xpath("//*[@id='mainLayout']//span[contains(text(),'No se registran')]"));
    }

    public PandoraElement getAdherirRecurrencia() {
        return new PandoraElement(By.xpath("//span[contains(text(),'Adherir recurrencia')]"));
    }

    public PandoraElement getIconoFlechaDetalleDebin(){
        return new PandoraElement(By.xpath("(//*[contains(@aria-label,'Icono de flecha derecha')])[1]"));
    }

    public PandoraElement getAceptarDebin(){
        return new PandoraElement(By.xpath("(//button[@aria-label='Aceptar'])[2]"));
    }
    public PandoraElement getCodigoSeguridad(){
        return new PandoraElement(By.name("securityNumber"));
    }
    public PandoraElement getMes(){
        return new PandoraElement(By.name("month"));
    }
    public PandoraElement getAnio(){
        return new PandoraElement(By.name("year"));
    }
    public PandoraElement getDesplegableTarjetaDebito(){
        return new PandoraElement(By.id("debin.detail.making.debitCardSelector"));
    }
    public PandoraElement getTarjetaDebitoSegundoFactor(){
        return new PandoraElement(By.id("secondFactor.general.debitCardSelector"));
    }
}
