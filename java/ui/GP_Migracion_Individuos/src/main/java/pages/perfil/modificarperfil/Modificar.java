package pages.perfil.modificarperfil;

import global.practia.jemeinsamstark.web.elements.PandoraElement;
import org.openqa.selenium.By;

public class Modificar {


    public PandoraElement getEditarIcono() {
        return new PandoraElement(By.xpath("//div[@aria-label='Icono de editar']")); }
    public PandoraElement getCodArea() {
        return new PandoraElement(By.xpath("//input[@name='mobileAreaCode']"));
    }
    public PandoraElement getInputContraseniaAntigua() {
        return new PandoraElement(By.id("password"));
    }
    public PandoraElement getInputContraseniaNew() {
        return new PandoraElement(By.id("newPassword"));
    }
    public PandoraElement getAvatar(int num) {
        return new PandoraElement(
                //By.xpath(String.format("(//div[@class='sc-dWZqqJ iyeJHe'])[%d]",num)));
                  By.xpath(String.format("(//div[@class='is-current']/../div)[%d]",num)));

    }
    public PandoraElement getInputConfirmarContraseniaNew() {return new PandoraElement(By.id("passwordConfirmation")); }
    public PandoraElement getInputTelefono() {
        return new PandoraElement(By.xpath("//input[@name='mobilePhone']"));
    }
    public PandoraElement getSeleccionDeAvateres(){
        return new PandoraElement(By.xpath("//div[contains(@class,'is-current')]"));
    }
    public PandoraElement getDesplegableDomicilio() {return new PandoraElement(By.id("selectorDescribedBy_0"));}
    public PandoraElement getCalle() {return new PandoraElement(By.id("street"));}
    public PandoraElement getNumero() {return new PandoraElement(By.id("number"));}
    public PandoraElement getSinNumero() {return new PandoraElement(By.id("noNumber"));}
    public PandoraElement getPiso() {return new PandoraElement(By.id("floor"));}
    public PandoraElement getSinPiso() {return new PandoraElement(By.id("floorPB"));}
    public PandoraElement getApartamento() {return new PandoraElement(By.id("apartment"));}
    public PandoraElement getDesplegableProvincia() {return new PandoraElement(By.id("province"));}
    public PandoraElement getDesplegableLocalidad() {return new PandoraElement(By.id("locality"));}
    public PandoraElement getDesplegableCodigoPostal() {return new PandoraElement(By.id("postalCode"));}
    public PandoraElement getModificarCorreo() {return new PandoraElement(By.id("mail"));}
    public PandoraElement getErrorCodArea() {
        return new PandoraElement(By.xpath("//div[contains(text(), 'El código de área ingresado no es válido')]"));
    }
    public PandoraElement getErrorTel() {
        return new PandoraElement(By.xpath("//div[contains(text(), 'El número ingresado es inválido')]"));
    }
    public PandoraElement getVerificarNuevoEmail(String email){
        return new PandoraElement(By.xpath(String.format("//p[contains(text(), '%s')]",email)));
    }
    public PandoraElement getVerificarTelefono(String tel){
        return new PandoraElement(By.xpath(String.format("//p[contains(text(), '%s')]",tel)));
    }
    public PandoraElement getTextsPage(String textos){
        return new PandoraElement(By.xpath(String.format("//*[contains(text(), '%s')]",textos)));
    }

    public PandoraElement datoModificado(String textos){
        return new PandoraElement(By.xpath(String.format("//*[contains(text(), '%s')]/following::p[1]",textos)));
    }

    public PandoraElement datoModificadoNúmero(){
        return new PandoraElement(By.xpath(String.format("(//span[contains(text(), 'Número')])[2]/following::p[1]")));
    }

    public PandoraElement datoLocalidad(){
        return new PandoraElement(By.xpath(String.format("(//input[@aria-label='Localidad'])[3]")));
    }

    public PandoraElement datoProvincia(){
        return new PandoraElement(By.xpath(String.format("(//input[@aria-label='Localidad'])[2]")));
    }

}

