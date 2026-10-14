package pages.cuentas;

import global.practia.jemeinsamstark.web.elements.PandoraElement;
import org.openqa.selenium.By;

public class CuentasPage {

    public PandoraElement getSolicitarNuevaCuenta(){
        return new PandoraElement(By.xpath("(//a[@href='/openingAccount'])[1]"));
    }
    public PandoraElement getOrdenesDeExtraccion(){
        return new PandoraElement(By.xpath("//span[contains(text(),'Órdenes de extracción')]/../.."));
    }
    public PandoraElement getTenencia3112(){
        return new PandoraElement(By.xpath("//a[@href='/holding']"));
    }
    public PandoraElement getAgendaDeDestinatarios(){
        return new PandoraElement(By.xpath("//a[@href='/diaryTransfer?fromMenu=false']"));
    }
    public PandoraElement getListadoDeTransacciones(){
        return new PandoraElement(By.xpath("//a[@href='/transactions/list']"));
    }
    public PandoraElement getTransferencias(){
        return new PandoraElement(By.xpath("//a[@href='/accounts']/span[text()='Transferencias']"));
    }
    public PandoraElement getTransferenciasProgramadas(){
        return new PandoraElement(By.xpath("//a[@href='/accounts']/span[text()='Transferencias programadas']"));
    }
    public PandoraElement getTransferenciasDeImporteSuperior(){
        return new PandoraElement(By.xpath("//a[@href='/transfersTIS']"));
    }
    public PandoraElement getLimiteDeTransferencia(){
        return new PandoraElement(By.xpath("//a[@href='/accountLimitTis']"));
    }
    public PandoraElement getTransferenciasRealizadas(){
        return new PandoraElement(By.xpath("//a[@href='/accounts/list/transfers']"));
    }
    public PandoraElement getEcheq(){
        return new PandoraElement(By.xpath("//a[@href='/accounts/echeqs']"));
    }
    public PandoraElement getRentaFinanciera(){
        return new PandoraElement(By.xpath("//a[@href='/financialIncome']"));
    }
    public PandoraElement getVerMas1erCuenta(){
        return new PandoraElement(By.xpath("(//a[contains(@href,'account')]/span)[1]"));
    }
    public PandoraElement getVerMasXnroCuenta(String nroCuenta){
        return new PandoraElement(By.xpath(String.format("//span[contains(text(),'%s')]/ancestor::div[1]/following-sibling::div/a", nroCuenta)));
    }
    public PandoraElement getVerMasPorPosicion(){
        return new PandoraElement(By.xpath("(//a[contains(@href,'account')]/span)[2]"));
    }
    public PandoraElement getAdelantoDeSueldo(){
        return new PandoraElement(By.xpath("//span[text()='Solicitud Adelanto de Sueldo']"));
    }
    public PandoraElement getCuantoNecesitas(){
        return new PandoraElement(By.id("amount"));
    }
    public PandoraElement getConfirmacionAdelantoDeSueldo(){
        return new PandoraElement(By.xpath("//*[contains(text(),'Confirmac')]"));
    }
    public PandoraElement getResumenDeContrato(){
        return new PandoraElement(By.xpath("//*[contains(text(),'Resumen de contrato')]"));
    }
    public PandoraElement getMisAdelantos(){
        return new PandoraElement(By.xpath("//*[contains(text(),'Mis adelantos')]"));
    }
    public PandoraElement textos(String text) {
        return new PandoraElement(By.xpath(String.format("//*[contains(text(), '%s')]",text)));
    }
    public PandoraElement getCampoRequeridoTyC() {
        return new PandoraElement(By.xpath("//*[contains(text(), 'rminos y condiciones es obligatorio para continuar')]"));
    }
    public PandoraElement getCampoRequerido(String titulo) {
        return new PandoraElement(By.xpath(String.format("(//*[contains(text(),'%s')])[1]", titulo)));
    }
    public PandoraElement getMontoPermitido() {
        return new PandoraElement(By.className("notification-message"));
    }

    public PandoraElement getCuentasAsociadas(){
        return new PandoraElement(By.xpath("//a[@href='/associatedAccounts']"));
    }
    public PandoraElement getAccesoDirectoAdelantoSueldo(){
        return new PandoraElement(By.xpath("//p[contains(text(),'Adelanto de sueldo')]"));
    }
    public PandoraElement getCardAdelantoSueldo(){
        return new PandoraElement(By.xpath("//span[contains(text(),'Adelanto de sueldo')]"));
    }
    public PandoraElement getDesplegableCuentaOrigen(){
        return new PandoraElement(By.xpath("//input[@name='accounts']/.."));
    }
    public PandoraElement getCheckTyC(){
        return new PandoraElement(By.xpath("//input[@name='acceptConditions']/../descendant::span[1]"));
    }

    public PandoraElement getListTipoDeCta(String nombre){
        return new PandoraElement(By.xpath("//span[contains(text(),'" + nombre + "')]/following::div[1]"));
    }
}
