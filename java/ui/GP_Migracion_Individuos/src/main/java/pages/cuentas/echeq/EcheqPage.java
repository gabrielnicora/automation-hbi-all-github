package pages.cuentas.echeq;

import global.practia.jemeinsamstark.web.elements.PandoraElement;
import org.openqa.selenium.By;

public class EcheqPage {

    public PandoraElement getInputCuitCuilEmisor(){
        return new PandoraElement(By.name("cuitOriginator"));
    }
    public PandoraElement getInputIdAgrupador(){
        return new PandoraElement(By.id("groupingId"));
    }
    public PandoraElement getFechaDeEmisionDesde(){
        return new PandoraElement(By.xpath("//div[contains(@aria-label,'n desde')]/div"));
    }
    public PandoraElement getTextoFechaDeEmision(String fecha){
        return new PandoraElement(By.xpath(String.format("//div[contains(@aria-label,'n desde')]/div//p[contains(text(),'%s')]", fecha)));
    }
    public PandoraElement getFechaDeEmisionHasta(){
        return new PandoraElement(By.xpath("//div[contains(@aria-label,'n hasta')]/div"));
    }
    public PandoraElement getFechaDePagoDesde(){
        return new PandoraElement(By.xpath("//div[contains(@aria-label,'Fecha de pago desde')]/div"));
    }
    public PandoraElement getFechaDePagoHasta(){
        return new PandoraElement(By.xpath("//div[contains(@aria-label,'Fecha de pago hasta')]/div"));
    }
    public PandoraElement getDesplegableEstado(){
        return new PandoraElement(By.xpath("//input[@name='echeqStatus']/preceding-sibling::div"));
    }
    public PandoraElement getBotonBuscar(){
        return new PandoraElement(By.xpath("//button[@aria-label='Buscar']"));
    }
    public PandoraElement getTituloTablaEcheq(String titulo){
        return new PandoraElement(By.xpath(String.format("(//*[@aria-label='tabla']//*[contains(text(),'%s')])[1]",titulo)));
    }
    public PandoraElement getVerDetallePrimerEcheq(){
        return new PandoraElement(By.xpath("(//*[@aria-label='tabla']//*[contains(@aria-label,'flecha')])[1]"));
    }
    public PandoraElement getVerDetallePorNroEcheq(String nroEcheq){
        return new PandoraElement(By.xpath(String.format("//*[@aria-label='tabla']//*[contains(text(),'%s')]/parent::div/following-sibling::div[7]",nroEcheq)));
    }

    public PandoraElement getNroEcheq() {
        return new PandoraElement(By.xpath(String.format("//input[contains(@id,'echeq')]")));
    }

    public PandoraElement getDatoNroEcheq(String opcion) {
        return new PandoraElement(By.xpath(String.format("//*[contains(text(), '%s')]", opcion)));
    }
    public PandoraElement getBtnAplicar(){
        return new PandoraElement(By.xpath("//span[contains(text(),'Aplicar')]"));
    }
    public PandoraElement getButtonCalendar(String option){
        return new PandoraElement(By.xpath("//button[contains(text(), '" + option + "')]"));
    }

    public PandoraElement getDatoBusquedaEcheqGrilla(String nroFila, String nroColumna) {
        return new PandoraElement(By.xpath(String.format("((//div[@aria-label='fila'])["+nroFila+"]//div)["+nroColumna+"]")));
    }

    public PandoraElement getDatoFechaEmisionDesde() {
        return new PandoraElement(By.xpath("//div[contains(@aria-label,'n desde')]//div"));
    }

    public PandoraElement getTabEstadoEcheq() {
        return new PandoraElement(By.xpath("//p[contains(text(),'Emitidos')]"));
    }

    public PandoraElement getValidacionTextoEstadoOperativo() {
        return new PandoraElement(By.xpath("//p[contains(text(),'No es posible operar')]"));
    }

    public PandoraElement getCheckPorFila(String nroFila){
        return new PandoraElement(By.xpath(String.format("(//div[@aria-label='fila'])[%s]/div[1]", nroFila)));
    }

    public PandoraElement getMotivoDePagoGuion(){
        return new PandoraElement(By.xpath(String.format("//span[contains(text(),'Motivo de pago')]/ancestor::p/following-sibling::p[1]")));
    }

    public PandoraElement getEstadoEcheqPorFila(){
        return new PandoraElement(By.xpath(String.format("(//div[@aria-label='fila'])[1]//div[@class='sc-dNsVcS gphpGG']")));
    }



}
