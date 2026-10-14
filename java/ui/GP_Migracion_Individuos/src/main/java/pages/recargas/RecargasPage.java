package pages.recargas;

import global.practia.jemeinsamstark.web.elements.PandoraElement;
import org.openqa.selenium.By;

public class RecargasPage {

    public PandoraElement getTarjetaDeDebitoSelector() {
        return new PandoraElement(By.id("selectorDescribedBy_0"));
    }
    public PandoraElement getEmpresa() {
        return new PandoraElement(By.id("company"));
    }public PandoraElement getServicioEmpresa() {
        return new PandoraElement(By.id("number"));
    }public PandoraElement getTarjeta_Adhesiones() {
        return new PandoraElement(By.xpath("//input[@name='card']/preceding-sibling::div"));
    }
    public PandoraElement getMensajeError() {
        return new PandoraElement(By.xpath("//div[contains(@aria-label, 'No pudimos procesar la operaci')]"));
    }public PandoraElement getRubroEliminar() {
        return new PandoraElement(By.xpath("//span[contains(text(), 'Rubro')]/parent::p/parent::label/parent::div//div[contains(text(), 'Sele')]"));
    }public PandoraElement getEmpresaEliminar() {
        return new PandoraElement(By.xpath("//input[@name='company']/preceding-sibling::div"));
    }
    public PandoraElement getMonto() {
        return new PandoraElement(By.xpath("//input[@name='amountCode']/preceding-sibling::div"));
    }
    public PandoraElement getMontoRecarga() {
        return new PandoraElement(By.xpath("//input[@label='Monto a recargar']"));
    }
    public PandoraElement getTextoGenerico(String texto) {
        return new PandoraElement(By.xpath(String.format("//span[text()='%s'] | //p[contains(text(),'%s')] | //div[contains(text(),'%s')] ",texto,texto,texto)));
    }
    public PandoraElement getCardTarjetaRecargable() {

        return new PandoraElement(By.xpath("//p[contains(text(), 'TARJETA RECARGABLE/PREPAGA')]/../.."));

    }
    public PandoraElement getBotonSeleccionar() {
        return new PandoraElement(By.xpath("//div[text()='Seleccionar']"));
    }

    public PandoraElement getTituloRecarga() {
        return new PandoraElement(By.xpath("//h1/span[text() = 'Recargas']"));

    }

    public PandoraElement getCardTelofoniaCelular() {
        return new PandoraElement(By.xpath("//p[contains(text(), 'TELEFONIA CELULAR') or contains(text(),'Telefonia Celular')]/.."));

    }
    public PandoraElement getCardBilletera_ServPublico() {
        return new PandoraElement(By.xpath("//p[contains(text(),'BILLETERA/SERV PREPAGO')]"));
    }
    public PandoraElement getCardTransportePublico() {
        return new PandoraElement(By.xpath("//p[contains(text(), 'TRANSPORTE PUBLICO')]"));
    }
    public PandoraElement getEliminarSube(String perteneceA) {
        return new PandoraElement(By.xpath(String.format("//div[contains(text(), '%s')]/parent::div/parent::form/parent::div/parent::p/parent::div/parent::div/div[4]//button", perteneceA)));
    }
    public PandoraElement getBuscar() {
        return new PandoraElement(By.xpath("//button[@aria-label='Buscar']"));}
    public PandoraElement getAgregar() {
        return new PandoraElement(By.xpath("//button[@aria-label='Agregar']"));}
    public PandoraElement getContinuar() {
        return new PandoraElement(By.xpath("//button[@aria-label='Continuar']"));}

    public PandoraElement getNumero_Codigo() {
        return new PandoraElement(By.xpath("//input[@id='code']"));
    }
    public PandoraElement getPerteneceA() {
        return new PandoraElement(By.xpath("//input[@id='inputDescription']"));}
    public PandoraElement getAdhesiones() {
        return new PandoraElement(By.xpath("//a[contains(@href, '/recharge/AdhesionConsultation')]"));
    }
    public PandoraElement getConsultaRecargas() {
        return new PandoraElement(By.xpath("//a[contains(@href, '/recharge/consult')]"));
    }
    public PandoraElement getSeleccionarNumero() {
        return new PandoraElement(By.xpath("//*[contains(@name, 'number')]/.."));
    }
    public PandoraElement getDesplegableNumeroTarjeta() {
        return new PandoraElement(By.xpath("(//div[contains(@class, 'react-select__indicators')])[2]"));
    }
    public PandoraElement getIconoTicket() {
        return new PandoraElement(By.xpath("//div[@aria-label='Icono de ver ticket']"));
    }
    public PandoraElement getTarjetaDebito() {
        return new PandoraElement(By.xpath("//input[@name='debitAccount']/../.."));
    }
    public PandoraElement getRegistroRecargaAutomation(String perteneceA) {
        return new PandoraElement(By.xpath(String.format("//*[contains(text(), '%s')]",perteneceA)));
    }
    public PandoraElement getVisaDebitoRecargasAdheridas() {
        return new PandoraElement(By.xpath("//input[@name='card']/.."));
    }
    public PandoraElement getCuentaADebitar() {
        return new PandoraElement(By.xpath("//input[@name='debitAccount']/parent::div"));
    }
    public PandoraElement getTextoRecarga(String recarga) {
        return new PandoraElement(By.xpath(String.format("//div[contains(text(), '%s')]", recarga)));
    }
    public PandoraElement getMontoRecargaTicket(String monto){
        return new PandoraElement(By.xpath(String.format("//div[contains(text(),'%s')]",monto)));
    }

}
