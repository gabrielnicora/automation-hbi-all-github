package pages.login;

import global.practia.jemeinsamstark.web.elements.PandoraElement;
import org.openqa.selenium.By;

public class LoginPage {

    public PandoraElement waitLogo() {
        return new PandoraElement(By.xpath("//span[contains(text(),'Ingresar')]"));
    }

    public PandoraElement getIngresarStartPage() {
        return new PandoraElement(By.xpath("//button[@aria-label='Ingresar']"));
    }

    public PandoraElement getInputUser() {
        return new PandoraElement(By.id("username"));
    }

    public PandoraElement getInputPassword() {
        return new PandoraElement(By.name("password"));
    }

    public PandoraElement getInputRepetirPassword() {
        return new PandoraElement(By.id("passwordConfirmation"));
    }

    public PandoraElement getIngresarLogin() {
        return new PandoraElement(By.xpath("//button[@aria-label='Ingresar']"));
    }
    public PandoraElement getContinuarLogin() {
        return new PandoraElement(By.xpath("//button[@type='submit']"));
    }
    public PandoraElement getCheckGP() {
        return new PandoraElement(By.xpath("//a[@href='/desktop']"));
    }

    public PandoraElement getEnOtroMomento() {
        return new PandoraElement(By.xpath("//span[contains(text(),'En otro momento')]"));
    }
    public PandoraElement getOtraPropuesta() {
        return new PandoraElement(By.xpath("//span[contains(text(),'Tenemos otra propuesta para vos')]"));
    }

    public PandoraElement getBlanquearContrasenia() {
        return new PandoraElement(By.xpath("//*[contains(text(),'Blanquear contrase')]"));
    }

    public PandoraElement getBotonIniciarSesion() {
        return new PandoraElement(By.xpath("//button[contains(@aria-label, 'Iniciar sesi')]"));
    }

    public PandoraElement getIconoEditarUsuario() {
        return new PandoraElement(By.xpath("//*[@aria-label='Icono de editar']"));
    }

    public PandoraElement getRecuperarUsuario() {
        return new PandoraElement(By.xpath("//*[text()='Recuperar usuario']"));
    }

    public PandoraElement getInputDocumento() {
        return new PandoraElement(By.name("cuil"));
    }

    public PandoraElement getOmitir() {
        return new PandoraElement(By.id("newDeviceValidation.buttonLabelSecondary"));
    }

    public PandoraElement getSalirPrestamo() {
        return new PandoraElement(By.xpath("//button[@aria-label='Salir']"));
    }

    public PandoraElement btnTecladoVirtual() {
        return new PandoraElement(By.xpath("//*[text()='teclado virtual']"));
    }

    public PandoraElement getSaludo() {
        return new PandoraElement(By.xpath("//*[contains(text(),'Hola')]"));
    }

    public PandoraElement shiftButtonTecladoVirtual() {
        return new PandoraElement(By.xpath("//div[@data-skbtn='{shift}']"));
    }

    public PandoraElement numberSymbolButtonTecladoVirtual() {
        return new PandoraElement(By.xpath("//div[@data-skbtn='{?123}']"));
    }

    public PandoraElement abcButtonTecladoVirtual() {
        return new PandoraElement(By.xpath("//div[@data-skbtn='{ABC}']"));
    }

    public PandoraElement enterButtonTecladoVirtual() {
        return new PandoraElement(By.xpath("//div[@data-skbtn='{enter}']"));
    }

    public PandoraElement setTeclaVirtual(char teclaVirtual) {
        return new PandoraElement(By.xpath(String.format("//div[@data-skbtn='%s']", teclaVirtual)));
    }

    public PandoraElement getDesbloquearUsuario() {
        return new PandoraElement(By.xpath("//span[contains(text(),'Desbloquear usuario')]"));
    }

    public PandoraElement getCrearNuevoUsuario() {
        return new PandoraElement(By.xpath("//span[contains(text(),'Crear usuario')]"));
    }

    public PandoraElement getInputCuil() {
        return new PandoraElement(By.xpath("//*[@name='document']"));
    }

    public PandoraElement getBotonTengoElCodigoInvitacion() {
        return new PandoraElement(By.xpath("//*[contains(text(),'Tengo el c')]"));
    }

    public PandoraElement text(String text) {
        return new PandoraElement(By.xpath(String.format("//*[contains(text(),'%s')]", text)));
    }

    public PandoraElement imagenDescargaGooglePlay() {
        return new PandoraElement(By.id("Bitmap"));
    }

    public PandoraElement imagenDescargaAppStore() {
        return new PandoraElement(By.id("Group"));
    }

    public PandoraElement getBotonModificarDatos() {
        return new PandoraElement(By.xpath("//button[@aria-label='Modificar datos']"));
    }
    public PandoraElement getNombreCuentaPorDefecto() {
        return new PandoraElement(By.xpath("//p[@class='sc-JrDLc czLORG']"));
    }

    public PandoraElement getCheckCaptcha() {
        return new PandoraElement(By.xpath("//*[@id='recaptcha-anchor']"));
    }

    public PandoraElement getCheckAceptaTerminos() {
        return new PandoraElement(By.xpath("//span[contains(@aria-label, 'checkbox No chequeado Acepta')]"));
    }

    public PandoraElement getLogoAdvertencia() {
        return new PandoraElement(By.xpath("//*[@id='container']/div[1]/div/div"));
    }

    public PandoraElement getLogoAdvertenciaColor() {
        return new PandoraElement(By.xpath("//*[@id='container']"));
    }

}