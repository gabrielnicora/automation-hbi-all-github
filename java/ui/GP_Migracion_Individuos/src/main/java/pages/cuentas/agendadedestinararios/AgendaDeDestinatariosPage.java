package pages.cuentas.agendadedestinararios;

import global.practia.jemeinsamstark.web.elements.PandoraElement;
import org.openqa.selenium.By;

public class AgendaDeDestinatariosPage {

    public PandoraElement getCuentasPropias(){
        return new PandoraElement(By.xpath("//*[@id='PROPIA']/following-sibling::label"));
    }
    public PandoraElement getCuentaTerceros(){
        return new PandoraElement(By.xpath("//*[@id='TERCERO']/following-sibling::label"));
    }
    public PandoraElement getEntidadBancariaDesplegable(){
        return new PandoraElement(By.id("selectorDescribedBy_0"));
    }
    public PandoraElement getPerteneceA(){
        return new PandoraElement(By.id("belongsTo"));
    }
    public PandoraElement getAdherirNuevoDestinatario(){
        return new PandoraElement(By.xpath("//a[@href='/diaryTransfer/adhesionAccount?fromMenu=false']"));
    }
    public PandoraElement getCuentaAdheridaXnroCuenta(String nroCuenta){
        return new PandoraElement(By.xpath(String.format("//*[text()='%s']/ancestor::div[@aria-label='fila']", nroCuenta)));
    }
}
