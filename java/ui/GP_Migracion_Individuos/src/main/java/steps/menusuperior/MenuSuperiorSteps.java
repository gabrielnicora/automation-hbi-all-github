package steps.menusuperior;

import global.practia.jemeinsamstark.web.elements.PandoraElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import pages.botonesgenericos.BotonesGenericosPage;
import pages.menusuperior.MenuSuperiorPage;
import steps.botonesgenericos.BotonesGenericosSteps;
import steps.login.LoginSteps;
import steps.mensaje.MensajeSteps;
import utils.UtilsGP;

public class MenuSuperiorSteps {
    private MenuSuperiorPage MenuSuperior = new MenuSuperiorPage();
    LoginSteps loginSteps = new LoginSteps();

    @Step("Click en Mensajes")
    public void clickMensaje() throws InterruptedException {
        MenuSuperior.getMensajes().click();
        loginSteps.clickSalirPrestamo();
    }

    @Step("Click en Perfil")
    public void clickPerfil() {
        MenuSuperior.getPerfil().click();
    }

    @Step("Click en Salir")
    public void clickSalir() {
        MenuSuperior.getSalir().click();
    }

    @Step("Esperar notificacion de mensaje")
    public void esperarMensajeNotificacion() throws InterruptedException {
        String nuevoMensaje = "";
        String nuevoMensajeEntrante = "";
        boolean var = new PandoraElement(By.xpath("(//div[@id='mail_icon']//div)[3]")).isPresent();
        if(!var){
            System.out.println("No tiene mensajes");
        }else{
            nuevoMensaje = new PandoraElement(By.xpath("(//div[@id='mail_icon']//div)[3]")).getText();
            System.out.println("cantidad de mensaje en bandeja de entrada " + nuevoMensaje);
        }
        for(int i =1; i<=5;i++){
            clickMensaje();
            new MensajeSteps().clickRecibidos();
            Thread.sleep(2000);
            boolean var1 = new PandoraElement(By.xpath("(//div[@id='mail_icon']//div)[3]")).isPresent();
            if(var1){
                nuevoMensajeEntrante = new PandoraElement(By.xpath("(//div[@id='mail_icon']//div)[3]")).getText();
            }if(!nuevoMensaje.equals(nuevoMensajeEntrante)){
                System.out.println("entro un nuevo mensaje");
                break;
            }
            System.out.println("Tiempo de espera " + i*2 + " segundos");
        }
    }
}
