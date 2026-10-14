package pages.transferencias.transferenciasinmediatas;

import global.practia.jemeinsamstark.web.elements.PandoraElement;
import org.openqa.selenium.*;
import org.openqa.selenium.WebElement;

import java.util.List;


public class TransferenciasInmediatasPage {
    public PandoraElement getTarjetaDebitoDesplegable(){
        return new PandoraElement(By.xpath("//input[@name='debitCard']/.."));
    }
    public PandoraElement getTablaTransferencias(){
        return new PandoraElement(By.xpath("//div[@role='table']"));
    }
    public PandoraElement getListadoDePlantillas(){
        return new PandoraElement(By.xpath("//span[contains(text(),'Listado de plantillas')]"));
    }
    public PandoraElement getTipoDeTransferenciaDesplegable(){
        return new PandoraElement(By.id("tranferType"));
    }
    public PandoraElement getTipoDeTransferencia(String tipoTransferencia){
        return new PandoraElement(By.xpath("//h2[contains(text(), '"+tipoTransferencia+"')]/../.."));
    }
    public PandoraElement getPrimerContactoAgendado(){
        return new PandoraElement(By.xpath("(//div[@class='slick-list'])[2]//div[@class='slick-slide slick-active'][1]"));
    }
    public PandoraElement getCuentaOrigenDesplegable(){
        //return new PandoraElement(By.xpath("//div[@class='sc-RYuTI gUJUgu react-select-container css-2b097c-container'] "));
        return new PandoraElement(By.xpath("//p[contains(text(),'Vas a transferir desde')]/../div"));

    }

    public PandoraElement getCuentaOrigenDesplegableSaldo1(){
        return new PandoraElement(By.xpath("//p[contains(text(),'Vas a transferir desde')]/../div//p[contains(text(),'$')][2]"));
    }
    //div[@class = 'react-select__control css-yk16xz-control']//div[1]//p[2]

//    public PandoraElement getCuentaOrigenDesplegableSaldo1(){
//        //return new PandoraElement(By.xpath("//div[@class='sc-RYuTI gUJUgu react-select-container css-2b097c-container'] "));
//        return new PandoraElement(By.xpath("" +
//                "//div[@class = 'react-select__menu css-26l3qy-menu']" +
//                "//div[2]//p[2]"));
//    }

    public PandoraElement getCuentaOrigenDesplegableSaldo2(){
        //return new PandoraElement(By.xpath("//div[@class='sc-RYuTI gUJUgu react-select-container css-2b097c-container'] "));
        return new PandoraElement(By.xpath("" +
                "//div[@class = 'react-select__menu css-26l3qy-menu']" +
                "//div[1]//p[1]"));
    }

    public PandoraElement getCuentaDestinoDesplegable(){
        return new PandoraElement(By.id("targetAccount"));
    }
    public PandoraElement getCuentaDestino(String cuentaDestino){
        return new PandoraElement(By.xpath(String.format("//*[contains(text(),'%s')]", cuentaDestino)));
    }
    public PandoraElement getIconoSiguiente(String numDeFlecha){
        return new PandoraElement(By.xpath(String.format("(//button[@aria-label='siguiente'])[%s]", numDeFlecha)));
    }
    public PandoraElement getConceptoDesplegable(){
        return new PandoraElement(By.id("concept"));
    }
    public PandoraElement getInputReferencia(){
        return new PandoraElement(By.id("reference"));
    }
    public PandoraElement getPlantillaPorNombre(String nombrePlantilla) {
        return new PandoraElement(By.xpath(String.format("//div[contains(text(),'%s')]", nombrePlantilla)));
    }
    public PandoraElement getProgramadaParaDesplegable(){
        return new PandoraElement(By.id("scheduledFor"));
    }
    public PandoraElement getProgramarOtroDia(){
        return new PandoraElement(By.xpath("//span[contains(text(),'Programar para otro d')]"));
    }
    public PandoraElement getEnUnaFechaEspeficia(){
        return new PandoraElement(By.xpath("//p[contains(text(),'En una fecha espec')]"));
    }
    public PandoraElement getElDiaDesplegable(){
        return new PandoraElement(By.id("weekDay"));
    }
    public PandoraElement getPorUnLapsoDeTiempoDesplegable(){
        return new PandoraElement(By.id("forLapseOfTime"));
    }
    public PandoraElement getEnLaFechaCalendario(){
        return new PandoraElement(By.id("customImputID"));
    }
    public PandoraElement getBotonTransferirAhora(){
        return new PandoraElement(By.xpath("//button[@aria-label='Transferir ahora']"));
    }
    public PandoraElement getBotonConfirmar(){
        return new PandoraElement(By.xpath("//button[@aria-label='Confirmar']"));
    }
    public PandoraElement getMensajeCampoObligatorio(){
        return new PandoraElement(By.xpath("//div[text()= 'Este campo es obligatorio']"));
    }

    public PandoraElement getNotificacionReferenciaVacia(){
        return new PandoraElement(By.cssSelector("[class='notification-message']"));
    }

    public PandoraElement getMensajeCuentasIguales(){
        return new PandoraElement(By.xpath("//div[contains(text(), 'La cuenta destino no puede ser igual a la cuenta origen')]"));
    }

    public PandoraElement getCardAgendaDestinatarios(){
        return new PandoraElement(By.xpath("//span[contains(text(),'Agenda completa')]"));
    }
    public PandoraElement getMontoCuentaDesde(){
        return new PandoraElement(By.xpath("//div[@class='labelOptionsHTML']/descendant::p[2]"));
    }
    public PandoraElement getMensajeNotificacion(){
        return new PandoraElement(By.xpath("//div[contains(text(), 'La cuenta destino no puede ser la misma')]"));
    }
    public PandoraElement getInputAliasCbu(){
        return new PandoraElement(By.id("cbuSearch"));
    }
    public PandoraElement getButtonSonCorrectos(){
        return new PandoraElement(By.xpath("//button[@aria-label='Son correctos']"));
    }
    public PandoraElement getMisTransferencias(){
        return new PandoraElement(By.xpath("//span[contains(text(),'Ver mis transferencias')]/../.."));
    }
    public PandoraElement getDesplegableCuenta(){
        return new PandoraElement(By.id("accounts"));
    }
    public PandoraElement getTodosLosMovimientos(){
        return new PandoraElement(By.xpath("//button[@aria-label='Todos los movimientos']"));
    }
    public PandoraElement getTablaHistorial(){
        return new PandoraElement(By.xpath("//*[@aria-label='tabla']"));
    }
    public PandoraElement getTransferenciaProgramadaPara(){
        return new PandoraElement(By.xpath(" //span[contains(text(), 'Programar para otro día')]/../.."));
    }
    public PandoraElement getSeleccionarFechaFutura(String fecha){
        return new PandoraElement(By.xpath(String.format("//p[contains(text(), '%s')]/..", fecha)));
    }
    public PandoraElement getManualdeUsuario(){return new PandoraElement(By.xpath("//span[contains(text(), 'Renovamos')]"));}

    public PandoraElement getAgregarCuenta(){
        //return new PandoraElement(By.xpath("//*[contains(text(),'Agregar cuenta')]/.."));
        return new PandoraElement(By.xpath("//*[contains(text(),'Agregar cuenta')]/.."));

    }
    public PandoraElement getInputBuscarContacto(){return new PandoraElement(By.id("belongsTo"));}
    public PandoraElement getBotonTrasnferir(){return new PandoraElement(By.xpath("//*[contains(text(),'Transferir')]//../parent::button"));}
    public PandoraElement getTarjetaAOperar(){return new PandoraElement(By.xpath("//span[contains(text(),'tarjeta')]//../../../div"));}
    public PandoraElement getTarjetaAOperarForm(){return new PandoraElement(By.xpath("//p[contains(text(),'tarjeta')]//../div"));}
    public PandoraElement getBotonTransferir(String perteneceA){return new PandoraElement(By.xpath(String.format("//p[contains(text(),'%s')]/../..//button[@aria-label='Transferir']", perteneceA)));}
    public PandoraElement getNombreCuentaDefault(){return new PandoraElement(By.xpath("//p[@class='sc-JrDLc czLORG']"));}

    public PandoraElement getCuentaDestinoPropia(){
        return new PandoraElement(By.xpath("//input[@id='belongsTo']"));
    }

    public PandoraElement getButtonCerrar(){
        return new PandoraElement(By.xpath("//button[@class='" +
                "QSIWebResponsiveDialog-Layout1-SI_795etUYzs9qQGPL_close-btn']"));
    }

    public PandoraElement getNumeroDeOperacion(){
        return new PandoraElement(By.xpath("//*[@id='adhesion.account.new']/div/div[1]/p[4]"));
    }

    public PandoraElement getDescripcion(){
        return new PandoraElement(By.xpath("//span[contains(text()," +
                "'Descripción')]"));
    }

    public PandoraElement getFiltrar(){
        return new PandoraElement(By.xpath
                ("//button[@aria-label='Filtrar']"));
    }

    public PandoraElement getfechaPasada(){
        return new PandoraElement(By.xpath
                ("//div[contains(@aria-label, 'Desde')]/div"));
    }

    public PandoraElement getfechaActual(){
        return new PandoraElement(By.xpath
                ("//div[contains(@aria-label, 'Hasta')]/div"));
    }

    public List<WebElement> getcantidadDeRegistrosGrilla(){

    return global.practia.jemeinsamstark.web.core.WebDriver.getInstance().
            findElements(By.xpath("//div[@aria-label='fila']"));
    }

    public PandoraElement validarPaginador(){
        return new PandoraElement(By.xpath
                ("//p[contains(text(), 'Pag ' )]"));
    }

    public PandoraElement getFechaMovimiento(String fecha){
        return new PandoraElement(By.xpath
                ("(//div[@type='grid']//p[contains(text(), '/"+ fecha +"/')])[1]"));

    }

    public PandoraElement getclickSiguientePaginaGrilla(){
        return new PandoraElement(By.xpath
                ("//div[@aria-label='Ir a la siguiente página']"));
    }

    public PandoraElement getdatePicker(){
        return new PandoraElement(By.xpath
                ("//div[@class='react-datepicker__month']/div"));
    }

    public PandoraElement getBorrarFiltros(){
        return new PandoraElement(By.xpath
                ("//span[contains(text(),'Borrar filtros')]"));
    }



}



