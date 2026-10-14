package pages.botonesgenericos;

import global.practia.jemeinsamstark.web.elements.PandoraElement;
import org.openqa.selenium.By;


public class BotonesGenericosPage {

    public PandoraElement getConfirmar() {

        return new PandoraElement(By.xpath("//button[@aria-label='Confirmar']"));
    }
    public PandoraElement getContinuar() {
        return new PandoraElement(By.xpath("//button[@aria-label='Continuar']"));
    }
    public PandoraElement getAceptar() {
        return new PandoraElement(By.xpath("//button[@aria-label='Aceptar']"));
    }
    public PandoraElement getAceptarEstafa(){
        return  new PandoraElement(By.xpath("(//button[@aria-label='Aceptar'])[2]"));
    }
    public PandoraElement getConsultar(){

        return new PandoraElement(By.xpath("//button[@aria-label='Consultar']"));
    }
    public PandoraElement getModificar() {

        return new PandoraElement(By.xpath("//button[@aria-label='Modificar']"));
    }
    public PandoraElement getCancelar() {
        return new PandoraElement(By.xpath("//button[@aria-label='Cancelar']"));
    }
    public PandoraElement getRechazar(){
        return new PandoraElement(By.xpath("//button[@aria-label='Rechazar']"));
    }
    public PandoraElement getVolver() {
        return new PandoraElement(By.xpath("//*[contains(text(), 'Volver')]"));
    }
    public PandoraElement getGuardar() {
        return new PandoraElement(By.xpath("//button[@aria-label='Guardar']"));
    }
    public PandoraElement getVolverFlecha() {
        return new PandoraElement(By.xpath("//span[normalize-space(.)='Volver']/ancestor::div[1]"));
    }
    public PandoraElement getTituloPage(String titulo) {
        return new PandoraElement(By.xpath(String.format("//*[contains(text(), '%s')]", titulo)));
    }
    public PandoraElement getTituloPage2(String titulo) {
        return new PandoraElement(By.xpath(String.format("(//span[contains(text(),'%s')])[1]", titulo)));
    }
    public PandoraElement getCampoObligatorio() {
        return new PandoraElement(By.xpath("//p[contains(text(), 'Este campo es obligatorio')]"));
    }
    public PandoraElement getMasOpciones(){
        return new PandoraElement(By.xpath("//span[contains(text(),'Opciones')]/../.."));
    }

    public PandoraElement getVerMas(){
        return new PandoraElement(By.xpath("(//span[contains(text(),'Mostrar más')])[last()] "));
    }
    public PandoraElement getTextoDesplegableGenerico(String textoValidar){
        return new PandoraElement(By.xpath(String.format("//div[contains(text(),'%s')]", textoValidar)));
    }
    public PandoraElement getFechaHasta(){
        return new PandoraElement(By.xpath("(//div[@id='customImputID'])[2]"));
    }
    public PandoraElement getTextKeyDownToText(String textoValidar){
        return new PandoraElement(By.xpath(String.format("//*[contains(text(),'%s')]", textoValidar)));
    }
     public PandoraElement getDescargarPdf() {
        return new PandoraElement(By.xpath("//div[@aria-label='Icono de descargar  en formato pdf']"));
    }
    public PandoraElement getDescargarXls() {
        return new PandoraElement(By.xpath("//div[@aria-label='Icono de descargar  en formato xls']"));
    }
    public PandoraElement getCampoImporte(){
        return new PandoraElement(By.id("amount"));
    }
    public PandoraElement getGuardarPlantilla(){
        return new PandoraElement(By.id("global.save.template"));
    }
    public PandoraElement getGuardarBorrador(){
        return new PandoraElement(By.id("global.save.draft"));
    }
    public PandoraElement getCheckTerminosCondiciones(){
        return new PandoraElement(By.xpath("//*[@name='agreement']/following-sibling::span"));
    }
    public PandoraElement getCancelarTerminos(){
        return new PandoraElement(By.id("termsAndConditions.component.cancelar.label"));
    }
    public PandoraElement getAceptarTerminos(){
        return new PandoraElement(By.id("termsAndConditions.component.aceptar.label"));
    }
    public PandoraElement getValidar(){
        return new PandoraElement(By.xpath("//button[@aria-label='Validar']"));
    }
    public PandoraElement getIconoEliminar(){
        return new PandoraElement(By.xpath("//button[contains(@aria-label,'Icono de eliminar')]"));
    }
    public PandoraElement getIconoEditar(){
        return new PandoraElement(By.xpath("//button[contains(@aria-label,'Icono para editar')]"));
    }
    public PandoraElement getSalirSesion(){

        return new PandoraElement(By.xpath("//span[text()='Salir']"));
    }
    public PandoraElement getTarjetaDebitoDobleFactor(){
        return new PandoraElement(By.id("secondFactor.general.debitCardSelector"));
    }
    public PandoraElement getNotificacionEmergente() {
        return new PandoraElement(By.xpath("//div[@class='notification-message']"));
    }

    public PandoraElement getNotificacionError() {
        return new PandoraElement(By.xpath("//div[@class='notification notification-error notification-visible']"));
    }

    public PandoraElement getIconoImprimir() {
        return new PandoraElement(By.xpath(" //div[@aria-label='Icono de imprimir']"));
    }
    public PandoraElement getBuscar() {
        return new PandoraElement(By.xpath("//button[@aria-label='Buscar']"));
    }
    public PandoraElement getTituloCards(String titulo) {
        return new PandoraElement(By.xpath(String.format("(//*[@id='mainLayout']//*[contains(text(),'%s')])[1]", titulo)));
    }
    public PandoraElement getIconoCerrar() {
        return new PandoraElement(By.xpath("//*[@aria-label='Icono de cerrar']"));
    }
    public PandoraElement buscarTexto(String text) {
        return new PandoraElement(By.xpath(String.format("//*[contains(text(), '%s')]", text)));
    }
    public PandoraElement getBotonesDiables(String nombreBoton){return new PandoraElement(By.xpath(String.format("//button[@disabled and @aria-label='%s']", nombreBoton)));
    }
    public PandoraElement getMontoCuenta(String cuenta){return new PandoraElement(By.xpath(String.format("//span[contains(text(),'%s')]/../../following-sibling::div//span[@aria-label='pesos']/..",cuenta)));}

    public PandoraElement getMontoCuentaUSD(String cuenta){return new PandoraElement(By.xpath(String.format("//span[contains(text(),'%s')]/../../following-sibling::div//span[@aria-label='dolares']/..",cuenta)));}

    public PandoraElement getImporteRescateTotal(String currency){return new PandoraElement(By.xpath("//span[@aria-label='"+currency+"']/.."));}

    public PandoraElement getImporteRescateTotalUSD(){return new PandoraElement(By.xpath("//span[@aria-label='dolares']/.."));}

    public PandoraElement getNotificationSuccess(){
        return  new PandoraElement(By.xpath("//div[@class='notification notification-success notification-visible']"));}

    public PandoraElement getCodigoUps(){return new PandoraElement(By.xpath("//h1/following::p[2]"));}
    public PandoraElement getMensajeEmergente(){
        return new PandoraElement(By.xpath("//div[@class='notification notification-error notification-visible']"));
    }

    public PandoraElement getSeleccionarGenerico(String opcion) {
        return new PandoraElement(By.xpath("//div[contains(text(), '"+ opcion +"')]"));
    }

    public PandoraElement getSeleccionarPlazoFijoUVA() {
        return new PandoraElement(By.xpath("//div[text() = 'Plazo Fijo UVA']"));
    }

    public PandoraElement getSeleccionCuentaDebito(){
        return new PandoraElement(By.xpath("(//span[contains(text(),'Cuenta débito')]/../../..//div)[1]"));
    }


    public PandoraElement getCampoReferencia(){
        return new PandoraElement(By.id("reference"));
    }


    public PandoraElement getPagar(){
        return new PandoraElement(By.xpath("//span[contains(text(),'Pagar')]"));
    }


    public PandoraElement getSeleccionarServicio1(String opcion) {
        return new PandoraElement(By.xpath("//p[contains(text(), '"+ opcion +"')]"));}


    public PandoraElement  getSeleccionarServicio2(String opcion) {
        return new PandoraElement(By.xpath("//span[contains(text(), '"+ opcion +"')]"));
    }

    public PandoraElement getlistaDesplegableGenerico(String opcion) {
        return new PandoraElement(By.xpath("//div[contains(text(), '"+ opcion +"')]"));
    }

    public PandoraElement getlistaDesplegableGenerico2(String opcion) {
        return new PandoraElement(By.xpath("//span[contains(text(), '"+ opcion +"')]"));
    }

    public PandoraElement getDatoGrillaGenerico(String opcion) {
        return new PandoraElement(By.xpath("//p[contains(text(), '"+ opcion +"')]"));
    }

    public PandoraElement getBotonPorTexto(String texto) {
        return new PandoraElement(By.xpath("//button[contains(text(), '"+ texto +"')]"));
    }




}




