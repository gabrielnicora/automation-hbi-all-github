package pages.inversiones.titulos;

import global.practia.jemeinsamstark.web.elements.PandoraElement;
import org.openqa.selenium.By;

public class TitulosPage {

    public PandoraElement getPanelDePrecios(){
        return new PandoraElement(By.xpath("//p[contains(text(),'Panel de precios')]"));
    }

    public PandoraElement getMisTenencias(){
        return new PandoraElement(By.xpath("//p[contains(text(),'Mis tenencias')]"));
    }

    public PandoraElement getTituloEmpezaAGestionar(){
        return new PandoraElement(By.xpath("//p[contains(text(), 'Empezá a gestionar tus inversiones.')]"));
    }

    public PandoraElement getTituloComoFunciona(){
        return new PandoraElement(By.xpath("//span[contains(text(), '¿Cómo funciona?')]"));
    }

    public PandoraElement getTituloAccesoRapido(){
        return new PandoraElement(By.xpath("//span[contains(text(), 'Accesos rápidos')]"));
    }

    public PandoraElement getBtnMisTenencias(){
        return new PandoraElement(By.xpath("//p[contains(text(),'Mis tenencias')]"));
    }

    public PandoraElement getCtaComitente(){
        return new PandoraElement(By.xpath("//span[contains(text(), 'Cuenta comitente')]"));
    }

    public PandoraElement getPerfilInversor(){
        return new PandoraElement(By.xpath("//span[contains(text(), 'Perfil inversor')]"));
    }

    public PandoraElement getBtnInstrumentos(String titulos){
        return new PandoraElement(By.xpath("//p[contains(text(), '" +titulos+ "')]"));
    }

    public PandoraElement getPanelLider(){
        return new PandoraElement(By.xpath("//div[contains(text(), 'Panel lider')]"));
    }

    public PandoraElement getMisTenenciasEnMercadoDeCapitales(){
        return new PandoraElement(By.xpath("//span[contains(text(), 'Mis tenencias en Mercado de capitales')]"));
    }

    public PandoraElement getInstrumentos(String opcion){
        return new PandoraElement(By.xpath("//h3[contains(text(), '" +opcion+ "')]"));
    }

    public PandoraElement getcomboTitulosMistenencias(String comboMisTenencias){
        return new PandoraElement(By.xpath("//h2[contains(text(), '"+ comboMisTenencias +"')]"));
    }

    public PandoraElement getButtonPlazoYMoneda(String button){
        return new PandoraElement(By.xpath("//div[contains(@aria-label,'"+ button +"')]"));
    }

    public PandoraElement getTablaEspecie(){
        return new PandoraElement(By.xpath("//div[contains(@aria-label,'fila')]//div[1]"));
    }

    public PandoraElement getTextoHorario(){
        return new PandoraElement(By.xpath("//p[contains(text(),'El horario de mercado es ')]"));
    }

    public PandoraElement getTenencaTotalPesos(){
        return new PandoraElement(By.xpath(" //p[normalize-space()='Tenencia total']   /following-sibling::div   /descendant::p[2]/span"));
    }

    public PandoraElement getBtnOperar(String nroFila){
        return new PandoraElement(By.xpath("((//div[@aria-label='fila'])[" + nroFila + "]//div)[14]"));
    }

    public PandoraElement getDatoEspecieGrilla(String nroFila, String nroColumna) {
        return new PandoraElement(By.xpath(String.format("((//div[@aria-label='fila'])["+nroFila+"]//div)["+nroColumna+"]")));
    }

    public PandoraElement getBtnVerMas() {
        return new PandoraElement(By.xpath(String.format("//span[contains(text(),'Mostrar más')]")));
    }

    public PandoraElement getCheckMoneda(String opcion) {
        return new PandoraElement(By.xpath(String.format("//div[contains(@aria-label,'" + opcion + "')]")));
    }

    public PandoraElement getDatosPantallaSolicitudDeOrden(String opcion) {
        return new PandoraElement(By.xpath(String.format("//span[normalize-space()='"+ opcion +"']/ancestor::p[1]/following-sibling::p[1]")));
    }

    public PandoraElement getPlazoDeLiquidacion(String opcion) {
        return new PandoraElement(By.xpath(String.format("//input[@id='"+ opcion +"']")));
    }

    public PandoraElement getIconoVariacionDiaria() {
        return new PandoraElement(By.xpath(String.format("//span[normalize-space()='Variación diaria']/ancestor::p[1]/following::p[1]//div[@alt]//div[1]")));
    }

    public PandoraElement getInstrumentosOpciones(String opciones) {
        return new PandoraElement(By.xpath(String.format(" //h2[normalize-space()='"+ opciones +"']   /ancestor::*[self::div or self::section][.//div[@aria-label='Expandir']][1]   //div[@aria-label='Expandir']")));
    }

    public PandoraElement getTabCompraYVenta(String opcion){
        return new PandoraElement(By.xpath("//p[contains(text(),'"+ opcion +"')]"));
    }

    public PandoraElement getButtonMontoYNominal(String button){
        return new PandoraElement(By.xpath("//input[@id='"+ button +"']"));
    }

    public PandoraElement getInputComprarYVender(String opcion){
        return new PandoraElement(By.xpath("//input[contains(@name,'"+ opcion +"')]"));
    }

    public PandoraElement getTextoInformativoMontoNominal(String opcion){
        return new PandoraElement(By.xpath("//p[contains(text(),'"+ opcion +"')]"));
    }

    public PandoraElement getTextoInformativoCuantoQueresComprarVender(String opcion){
        return new PandoraElement(By.xpath("//span[contains(text(), '" +opcion+ "')]"));
    }

    public PandoraElement  getTituloNominales(){
        return new PandoraElement(By.xpath("//label[normalize-space()='Nominales']"));
    }

    public PandoraElement  getMonedaPesos(){
        return new PandoraElement(By.xpath("//span[contains(text(), '$')]"));
    }

    public PandoraElement  getRadioButtonMontoyNominales(String opcion){
        return new PandoraElement(By.xpath("//input[@id='" +opcion+ "']/..//div[starts-with(@class,'sc-hRJfrW sc-klVQfs')]")); //el doble punto retrocede en el arbol un paso
    }

    public PandoraElement  getDetalleDeOrden(String opcion){
        return new PandoraElement(By.xpath("//p[contains(normalize-space(.),'" +opcion+ "')]/ancestor::span[1]/following-sibling::p[1][contains(.,'$')]"));
    }

    public PandoraElement  getLimite(){
        return new PandoraElement(By.xpath("//span[contains(@class,'data-numeric-initial-value')]"));
    }

    public PandoraElement  getDatosConfirmacionDeOrdenYTicket(String opcion){
        return new PandoraElement(By.xpath("//span[normalize-space()='" +opcion+ "']/ancestor::p/following-sibling::p[1]"));
    }

    public PandoraElement  getCheckBoxConfirmacionDeOrden(){
        return new PandoraElement(By.xpath("//span[@id='riskProfile']"));
    }

    public PandoraElement  getCheckBoxConfirmacionDeOrdenVenta(){
        return new PandoraElement(By.xpath("//span[@role='checkbox']"));
    }

    public PandoraElement  getCheckBoxConfirmacionDeOrdenTerminosYCondiciones(){
        return new PandoraElement(By.xpath("//span[contains(@aria-label,'Acepto Términos')]"));
    }

    public PandoraElement  getBtnContinuarConfirmarYVolver(String opcion){
        return new PandoraElement(By.xpath("//span[contains(text(),'" +opcion+ "')]"));
    }

    public PandoraElement getDatosInformativosMonedaYNominalesDisponibles(String opcion){
        return new PandoraElement(By.xpath("(//p[contains(normalize-space(.),'" +opcion+ "')]/following::p)[1]"));
    }

    public PandoraElement  getDatosDetalleDeOrdenConfirmacionDeOrden(String opcion){
        return new PandoraElement(By.xpath("//text()[normalize-space(.)='" +opcion+ "']/parent::*"));
    }

    public PandoraElement getDatosInformativosNominalesDisponibles(){
        return new PandoraElement(By.xpath("//p[contains(text(),'Moneda')]"));
    }
    public PandoraElement getTicketIconoDescargaeImpresion(String opcion){
        return new PandoraElement(By.xpath("//div[contains(@aria-label,'" +opcion+ "')]"));
    }

    public PandoraElement getDatosDelTicket(String opcion){
        return new PandoraElement(By.xpath(""));
    }

    public PandoraElement getInformacionSolicitudPendiente(String opcion){
        return new PandoraElement(By.xpath("//span[normalize-space()='" +opcion+ "']/ancestor::p/following-sibling::p[3]"));
    }

    public PandoraElement getPanelAcciones(){
        return new PandoraElement(By.xpath("//div[@id= 'selectorDescribedBy_1']"));
    }

    public PandoraElement getTituloInformativo(String opcion){
        return new PandoraElement(By.xpath("//span[normalize-space()='Importante']/ancestor::p/following::p[1]"));
    }

    public PandoraElement getCheckboxOperacionSeleccionada(String opcion){
        return new PandoraElement(By.xpath("//span[contains(normalize-space(.), '" +opcion+ "')]   /ancestor::div   //span[@role='checkbox']"));
    }

}
