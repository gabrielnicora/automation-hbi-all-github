package pages.pagos.adherirservicio;
import global.practia.jemeinsamstark.web.elements.PandoraElement;
import org.openqa.selenium.By;

public class AdherirServicioPage {
    public PandoraElement getTarjetaDebito() {
        return new PandoraElement(By.xpath("//input[@name='debitCard']/preceding-sibling::div/div[1]"));
    }
    public PandoraElement getRubro() {
        return new PandoraElement(By.xpath("//*[@id='selectorControlBy_serviceCompany']"));
}
    public PandoraElement getVerMasAdhesiones(){
        return new PandoraElement(By.xpath("//span[contains(text(),'Ver m')]"));
    }
    public PandoraElement getInputRubro() {
        return new PandoraElement(By.xpath("//input[@name='category']/preceding-sibling::div/div[1]/div[2]//input"));
    }
    public PandoraElement getEmpresa() {
        return new PandoraElement(By.xpath("//input[@name='entity']/preceding-sibling::div/div[1]"));
    }
    public PandoraElement getInputEmpresa() {
        return new PandoraElement(By.xpath("//input[@name='entity']/preceding-sibling::div/div[1]/div[2]//input"));
    }
    public PandoraElement getCodigoDePago() {
        return new PandoraElement(By.name("paymentCode"));
    }

    public PandoraElement getdescripcion(){
        return new PandoraElement(By.xpath("//input[@name='belongsTo']"));
    }

    public PandoraElement getcodigoempresa(){
        return new PandoraElement(By.xpath("//input[@aria-describedby='paymentCodeDescription']"));
    }

    public PandoraElement getservicio(){
        return new PandoraElement(By.xpath("//div[@name='0']"));
    }





}
