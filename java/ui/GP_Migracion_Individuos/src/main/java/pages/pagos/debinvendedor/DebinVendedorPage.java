package pages.pagos.debinvendedor;

import global.practia.jemeinsamstark.web.elements.PandoraElement;
import org.openqa.selenium.By;

public class DebinVendedorPage {

    public PandoraElement getGenerarOrdenDebin() {
        return new PandoraElement(By.xpath("//a[@href='/payments/debins/generate']"));
    }
    public PandoraElement getConsultaAdhesiones() {
        return new PandoraElement(By.xpath("//a[@href='/payments/debins/subscriptions']"));
    }
    public PandoraElement getCuentaDestinoDesplegable() {
        return new PandoraElement(By.id("accountSelector"));
    }
    public PandoraElement getCbu() {
        return new PandoraElement(By.xpath("//label[@for='CBU']/div/div"));
    }
    public PandoraElement getAlias() {
        return new PandoraElement(By.xpath("//label[@for='ALIAS']/div/div"));
    }
    public PandoraElement getCbuYAliasComprador() {
        return new PandoraElement(By.id("aliasCbu"));
    }
    public PandoraElement getBuscar() {
        return new PandoraElement(By.xpath("//button[@aria-label='Buscar']"));
    }
    public PandoraElement getConceptoDesplegable() {
        return new PandoraElement(By.id("conceptSelector"));
    }
    public PandoraElement getReferencia() {
        return new PandoraElement(By.id("reference"));
    }
    public PandoraElement getVigencia() {
        return new PandoraElement(By.id("validitySelector"));
    }
    public PandoraElement getNombreDesplegable(){
        return new PandoraElement(By.xpath("//input[@name='nameSelector']/.."));
    }

}
