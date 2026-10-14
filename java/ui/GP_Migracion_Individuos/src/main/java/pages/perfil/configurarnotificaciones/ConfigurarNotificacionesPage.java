package pages.perfil.configurarnotificaciones;

import global.practia.jemeinsamstark.web.elements.PandoraElement;
import org.openqa.selenium.By;

public class ConfigurarNotificacionesPage {
    public PandoraElement getMensajesDelBanco() {
        return new PandoraElement(By.xpath("//a[@href='/settings/notificationsConfiguration/message']"));
    }
    public PandoraElement getOperacionesRealizadas() {
        return new PandoraElement(By.xpath("///a[@href='/settings/notificationsConfiguration/accepted']"));
    }
    public PandoraElement getUsuarioBloqueado() {
        return new PandoraElement(By.xpath("//a[@href='/settings/notificationsConfiguration/userBlock']"));
    }
    public PandoraElement getSwitchCorreoActivado(){
        return new PandoraElement(By.xpath("(//input[@value='false'])[1]"));
    }
    public PandoraElement getSwitchAplicacionActivado(){
        return new PandoraElement(By.xpath("(//input[@value='false'])[2]"));
    }
    public PandoraElement getSwitchMensajeTextoActivado(){
        return new PandoraElement(By.xpath("(//input[@value='false'])[3]"));
    }

}
