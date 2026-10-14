package pages.cuentas_asociadas;

import global.practia.jemeinsamstark.web.elements.PandoraElement;
import org.openqa.selenium.By;

public class CuentasAsociadasPage {
    public PandoraElement getComoFunciona() {
        return new PandoraElement(By.xpath("//span[contains(text(), 'mo funciona?')]"));
    }
    public PandoraElement textos(String text) {
        return new PandoraElement(By.xpath(String.format("//*[contains(text(), '%s')]",text)));
    }
    public PandoraElement getVincularUnaCuenta() {
        return new PandoraElement(By.xpath("//a[@href='/associatedAccounts']"));
    }
    public PandoraElement getContinuarPaginaExterna() {
        return new PandoraElement(By.xpath("//button[text()='Continuar']"));
    }
    public PandoraElement getSeleccionBERSA() {
        return new PandoraElement(By.xpath("//span[text()='Banco de Entre Rios']"));
    }
    public PandoraElement getInputBuscar() {
        return new PandoraElement(By.xpath("//input[@placeholder='Buscar']"));
    }
    public PandoraElement getSeleccionBanco(String banco) {
        return new PandoraElement(By.xpath(String.format("//span[contains(text(),'%s')]", banco)));
    }

    public PandoraElement getInputUserName() {
        return new PandoraElement(By.id("Username"));
    }
    public PandoraElement getInputPassword() {
        return new PandoraElement(By.id("Password"));
    }
    public PandoraElement getInputTD() {
        return new PandoraElement(By.id("Digits"));
    }
    public PandoraElement getBotonIniciarSesion() {
        return new PandoraElement(By.xpath("//button[contains(text(), 'Inicio de sesi')]"));
    }
    public PandoraElement getPrimerCuentaAVincular() {
        return new PandoraElement(By.xpath("(//input[@name='SelectedAccounts'])[1]"));
    }
    public PandoraElement getCheckboxTerminosyCondiciones() {
        return new PandoraElement(By.id("TermOfService"));
    }
    public PandoraElement getDesplegableEntidadesAsociadas() {
        return new PandoraElement(By.xpath("//*[@aria-label='Expandir']/.."));
    }
    public PandoraElement getEntidadAsociada(String bancoAsociado) {
        return new PandoraElement(By.xpath(String.format("//p[contains(text(),'%s')]", bancoAsociado)));
    }
    public PandoraElement getDesplegableCuentasAsociadasEnOtrasEntidades() {
        return new PandoraElement(By.xpath("//p[contains(text(),'asociadas en otras entidades')]"));
    }

    public PandoraElement botonDesasociarEntidad() {
        return new PandoraElement(By.xpath("//button[@aria-label='Desasociar entidad']"));
    }
    public PandoraElement botonConfirmarDesasociarEntidad() {
        return new PandoraElement(By.xpath("//button[@aria-label='Si, desasociar']"));
    }

}
