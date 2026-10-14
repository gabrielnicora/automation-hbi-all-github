package pages.inversiones;

import global.practia.jemeinsamstark.web.elements.PandoraElement;
import org.openqa.selenium.By;

public class InversionesPage {

    public PandoraElement getCompraVentaDeDolares(){
        return new PandoraElement(By.xpath("//span[contains(text(),'Compra venta de dólares')]"));
    }
    public PandoraElement getFondoComunDeInversion(){
        return new PandoraElement(By.xpath("//span[contains(text(),'Fondo común de inversión')]/../.."));
    }
    public PandoraElement getPlazosFijos(){
        return new PandoraElement(By.xpath("//span[contains(text(),'Plazo fijo')]/../.."));
    }
    public PandoraElement getSolicitarNuevaCuentaComitente(){
        return new PandoraElement(By.xpath("//span[contains(text(),'Solicitar nueva cuenta comitente')]/.."));
    }
    public PandoraElement getSolicitarBajaDeCuentaComitente(){
        return new PandoraElement(By.xpath("//span[text()='Solicitar baja de cuenta comitente']"));
    }
    public PandoraElement getSeleccionarCuenta(){
        return new PandoraElement(By.xpath("//input[@name='commitentAccount']/preceding-sibling::div"));
    }
    public PandoraElement getSeleccionarCedip(){
        return new PandoraElement(By.xpath("//span[contains(text(),'CEDIP')]/../.."));
    }
    public PandoraElement getBotonNuevaInversion(){return new PandoraElement(By.xpath("//span[contains(text(),'Gestioná tus inversiones') or contains(text(),'Nueva inversión')]/.."));}
}
