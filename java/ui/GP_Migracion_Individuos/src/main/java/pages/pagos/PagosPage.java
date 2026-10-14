package pages.pagos;

import global.practia.jemeinsamstark.web.elements.PandoraElement;
import org.openqa.selenium.By;

public class PagosPage {

    public PandoraElement getSelectorTarjetaDebito() {
        return new PandoraElement(By.id("selectorDescribedBy_0"));
    }

    public PandoraElement getComprobantes() {
        return new PandoraElement(By.xpath("(//*[@id='mainLayout']//a[@href='/payments/paymentsMadeList'])"));
    }

    public PandoraElement getNuevoPago() {
        return new PandoraElement(By.xpath("//p[contains(text(),'Nuevo pago')]/../.."));
    }

    public PandoraElement getAdherirImpuestoServicio() {
        return new PandoraElement(By.xpath("//span[contains(text(),'Adherir impuesto/servicio')]/.."));
    }

    public PandoraElement getServiciosAdheridos() {
        return new PandoraElement(By.xpath("//*[contains(text(),'Servicios adheridos')]/../.."));
    }

    public PandoraElement getPagosVep() {
        return new PandoraElement(By.xpath("//a[@href='/payments/vepPayments']"));
    }

    public PandoraElement getDebinVendedor() {
        return new PandoraElement(By.xpath("//a[@href='/payments/debinAsSeller/debins']"));
    }

    public PandoraElement getDebinComprador() {
        return new PandoraElement(By.xpath("//a[@href='/payments/debinAsBuyer/debins']"));
    }
    public PandoraElement getSeccionDebinComprador() {
        return new PandoraElement(By.xpath("//*[contains(text(),'solicitudes de DEBIN recibidas')]"));
    }
    public PandoraElement getAdherirRecurrencia() {
        return new PandoraElement(By.xpath("//*[contains(text(),'Adherir recurrencia')]"));
    }
    public PandoraElement getConsultarRecurrencia() {
        return new PandoraElement(By.xpath("//*[contains(text(),'Consultar recurrencias')]"));
    }
    public PandoraElement getSeccionDebinVendedor() {
        return new PandoraElement(By.xpath("//*[contains(text(),'tudes realizadas de DEBIN vendedor')]"));
    }
    public PandoraElement getGenerarOrdenDebinVendedor() {
        return new PandoraElement(By.xpath("//span[contains(text(),'Generar orden de DEBIN')]"));
    }
    public PandoraElement getConsultarAdhesionesDebinVendedor() {
        return new PandoraElement(By.xpath("//span[contains(text(),'Consultar adhesiones')]"));
    }
    public PandoraElement getCampoObligatorio() {
        return new PandoraElement(By.xpath("//*[contains(text(),'Este campo es obligatorio')]"));
    }
    public PandoraElement getCampoTarjetaDebito() {
        return new PandoraElement(By.xpath("//*[contains(text(),'Este campo es obligatorio')]"));
    }
    public PandoraElement getNuevoPagoFrontViejo(){
        return new PandoraElement(By.xpath("//span[contains(text(),'Nuevo pago')]/../.."));
    }

    public PandoraElement getserviciosAgendados(){
        return new PandoraElement(By.xpath("//span[contains(text(),'Servicios agendados')]"));
    }



    public PandoraElement getlistaPagandoDesde(){
        return new PandoraElement(By.xpath("//div[@id='selectorDescribedBy_0']"));
    }

    public PandoraElement getlistavencimiento(){
        return new PandoraElement(By.xpath("//div[@id='selectorDescribedBy_1']"));
    }

    public PandoraElement getreferencia(){
        return new PandoraElement(By.xpath("//input[@name='reference']"));
    }


    public PandoraElement getServicioAPagar(String opcion) {
        return new PandoraElement(By.xpath("//div[@name= '"+ opcion +"']"));
    }



    public PandoraElement getValidarTituloML() {
        return new PandoraElement(By.xpath("//p[contains(text(), 'Este campo es obligatorio')]"));
    }

    public PandoraElement getMostrarTodos() {
        return new PandoraElement(By.xpath("//a[contains(text(),'Mostrar todos')]"));
    }

    public PandoraElement getSeleccinaServicioAPagar(String opcion) {
        return new PandoraElement(By.xpath("(//p[contains(text(),'Mercado Pago')]//..//..//input/..)["+ opcion +"]"));
    }

    public PandoraElement getBotonGuardarCambios() {
        return new PandoraElement(By.xpath("//button[@aria-label='Guardar cambios']"));
    }


}


