package pages.inversiones.compraventadolares;

import global.practia.jemeinsamstark.web.elements.PandoraElement;
import org.openqa.selenium.By;

public class CompraVentaDeDolaresPage {
    public PandoraElement getMensajeElHorarioDeCompraVentaDeDolares(){
        return new PandoraElement(By.xpath("//span[contains(text(), 'El horario para realizar operaciones de compra/venta de moneda extranjera')]")); }

     public PandoraElement getTipoDeOperacion(){
        return new PandoraElement(By.id("transferType"));}

     public PandoraElement getCuentaOrigen(){
         return new PandoraElement(By.id("originAccount"));}

     public PandoraElement getCuentaDestino(){
         return new PandoraElement(By.id("targetAccount"));}

     public PandoraElement getCuantoQueresVender(){
        return new PandoraElement(By.id("userAmount"));}


    public PandoraElement getValidarMensaje(String mensaje){
        return new PandoraElement(By.xpath(String.format("//*[contains(text(), '%s')]", mensaje)));}

}