package pages.mensaje;

import global.practia.jemeinsamstark.web.elements.PandoraElement;
import org.openqa.selenium.By;

public class MensajePage {
    public PandoraElement getMensajes(){
        return new PandoraElement(By.xpath("//p[@for='mail_icon']"));
    }
    public PandoraElement getAbrirMensaje(){
            return new PandoraElement(By.xpath("//ul[@class='message-list']/div[1]"));
        }
    public PandoraElement getDescargarMensaje(){
        return new PandoraElement(By.xpath("(//div[@aria-label='Icono de descargar'])[1]"));
    }
    public PandoraElement getMensajeRecibido(){

        return new PandoraElement(By.id("message_recieved_icon"));
    }
    public PandoraElement getMensajeSinLeer()
    {
        return new PandoraElement(By.id("message_unread_icon"));
    }
    public PandoraElement getMensajeLeidos(){

        return new PandoraElement(By.id("message_read_icon"));
    }
    public PandoraElement getMensajeEnviados(){

        return new PandoraElement(By.id("message_sent_icon"));
    }
    public PandoraElement getCargarMasMensajes(){

        return new PandoraElement(By.id("communications.messages.more"));
    }
    public PandoraElement getBuscarMsj(){

        return new PandoraElement(By.id("search"));
    }
    public PandoraElement getLupa(){

        return new PandoraElement(By.xpath("//button[@aria-label='Buscar']"));
    }
    public PandoraElement getTresPuntos(){

        return new PandoraElement(By.xpath("(//div[@role='button'])[1]"));
    }
    public PandoraElement getTituloPrimerMensaje(String tituloMsj){

        return new PandoraElement(By.xpath(String.format("(//p[contains(text(),'%s')])[1]",tituloMsj)));
    }
    public PandoraElement getElementoElegido(){

        return new PandoraElement(By.xpath("//*[contains(concat(' ', normalize-space(@class), ' '), ' active ')]"));
    }
    public PandoraElement getMarcarComoLeido(){

        return new PandoraElement(By.xpath("//span[contains(text(), 'Marcar como')]"));
    }
    public PandoraElement getEliminarMsj(){

        return new PandoraElement(By.xpath("//span[contains(text(), 'Eliminar')]"));
    }

    public PandoraElement getOtraPropuesta() {
        return new PandoraElement(By.xpath("//*[contains(text(),'Tenemos otra propuesta para vos')]"));
    }
    public PandoraElement btnSalirOtraPropuesta() {
        return new PandoraElement(By.xpath("(//span[contains(text(),'Salir')])[2]"));
    }
    public PandoraElement getTextoUltimoMensaje() {
        return new PandoraElement(By.xpath("(//p[@class='sc-fjvvzt izfbkj'])[1]"));
    }
}
