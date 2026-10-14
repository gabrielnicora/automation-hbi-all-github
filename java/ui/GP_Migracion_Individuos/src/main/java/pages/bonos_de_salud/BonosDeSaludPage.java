package pages.bonos_de_salud;
import global.practia.jemeinsamstark.web.elements.PandoraElement;
import org.openqa.selenium.By;

public class BonosDeSaludPage {
    public PandoraElement getTarjetaDebitoDesplegable(){
        return new PandoraElement(By.xpath("(//*[contains(text(), 'Tarjeta de ')]/following::div[1])[2]"));
    }
    public PandoraElement getEnte(){
        return new PandoraElement(By.xpath("//div[text()='Seleccionar']"));
    }
    public PandoraElement getDesplegableNumeroAfiliado(){
        return new PandoraElement(By.xpath("//*[contains(text(), 'Afiliado')]/following::div[1]"));
    }
    public PandoraElement getInputNumero(){
        return new PandoraElement(By.xpath("//input[@aria-describedby='codeDescription' or @id='code']"));
    }
    public PandoraElement getInputPerteneceA(){
        return new PandoraElement(By.xpath("//input[@aria-describedby='textDescription']"));
    }
    public PandoraElement getConfirmarNumero(){
        return new PandoraElement(By.xpath("//button[@aria-label='Icono de confirmar']"));
    }
    public PandoraElement getAgregarUnBono(){
        return new PandoraElement(By.xpath("(//div[@aria-label='Agregar uno'])[1]"));
    }
    public PandoraElement getDesplegableCuentaOrigen(){
        return new PandoraElement(By.xpath("//input[@name='originAccount']/parent::div"));
    }
    public PandoraElement getAdhesiones(){
        return new PandoraElement(By.xpath("//span[text()='Adhesiones']"));
    }
    public PandoraElement getEliminarAfiliado(){
        return new PandoraElement(By.xpath("(//button[@aria-label='Icono de eliminar'])[1]"));
    }
    public PandoraElement getBuscar(){
        return new PandoraElement(By.xpath("//button[@aria-label='Buscar']"));
    }

    public PandoraElement getTablaAfialiados(){
        return new PandoraElement(By.xpath("//div[@aria-label='tabla']"));
    }

    public PandoraElement getConsultarCompras(){
        return new PandoraElement(By.xpath("//span[text()='Consultar Compras']"));
    }
    public PandoraElement getDesplegableCanal(){
        return new PandoraElement(By.xpath("//input[@name='channel']/parent::div"));
    }
    public PandoraElement getCalendarioDesde(){
        return new PandoraElement(By.xpath("//div[@aria-label='calendario Desde']/descendant::div[1]"));
    }



}
