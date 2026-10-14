package pages.inversiones.cuentacomitente;

import global.practia.jemeinsamstark.web.elements.PandoraElement;
import org.openqa.selenium.By;


public class CuentaComitentePage {

    public PandoraElement getText(String text) {
        return new PandoraElement(By.xpath(String.format("//*[contains(text(), '%s')]",text)));

    }
    public PandoraElement getSolicitarCuenta() {
        return new PandoraElement(By.xpath("//button[@aria-label='Solicitar cuenta']"));

    }
    public PandoraElement getNumeroDePaso(String numeroPaso) {
        return new PandoraElement(By.xpath(String.format("//div[contains(text(), '%s')]",numeroPaso)));
    }

    public PandoraElement getTituloDePaso(String tituloEtapa) {
        return new PandoraElement(By.xpath(String.format("//p[contains(text(), '%s')]",tituloEtapa)));
    }

    public PandoraElement getCheckboxNingunaDeLasAnteriores() {
        return new PandoraElement(By.xpath("//span[@id='nao']"));

    }
    public PandoraElement getRadioFormDeclaracionJurada(int cont) {
        return new PandoraElement(By.xpath(String.format("(//label[contains(@for,'no')]/*)" + "[" + cont + "]")));

    }
    public PandoraElement getCheckTerminosyCondiciones() {
        return new PandoraElement(By.xpath("//span[contains(text(),'Términos y Condiciones')]/../../span"));

    }
    public PandoraElement getTextTestPerfilDeInversor() {
        return new PandoraElement(By.xpath("//span[contains(text(), 'Test de Perfil del Inversor')]"));

    }
    public PandoraElement getBotonContinuarInhabilitado() {
        return new PandoraElement(By.xpath("//button[@disabled]"));

    }
    public PandoraElement getOpcionPerfilDelInversor(String opcion){
        return new PandoraElement(By.xpath("//label[contains(text(),'"+opcion+"')]/div"));
    }


}
