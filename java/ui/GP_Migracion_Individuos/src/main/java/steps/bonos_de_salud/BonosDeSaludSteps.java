package steps.bonos_de_salud;

import api.bonosDeSaludAPI;
import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import pages.bonos_de_salud.BonosDeSaludPage;
import steps.botonesgenericos.BotonesGenericosSteps;
import steps.menulateral.MenuLateralSteps;
import utils.MetodosAPI;
import utils.UtilsGP;

import java.awt.*;

public class BonosDeSaludSteps extends BotonesGenericosSteps {
    BonosDeSaludPage bonosDeSaludPage = new BonosDeSaludPage();
    MenuLateralSteps menuLateralSteps= new MenuLateralSteps();
    MetodosAPI metodosAPI = new MetodosAPI();
    bonosDeSaludAPI bonosDeSaludAPI = new bonosDeSaludAPI();
    @Step("Click Desplegable Tarjeta Debito")
    public void clickDesplegableTarjetaDebito(){
        bonosDeSaludPage.getTarjetaDebitoDesplegable().click();
    }
    @Step("Click Desplegable Ente")
    public void clickDesplegableEnte(){
        Assert.assertTrue("NO SE ENCONTRO EL DESPLEGABLE ENTE",bonosDeSaludPage.getEnte().isDisplayed());
         bonosDeSaludPage.getEnte().click();
    }
    @Step("Click Desplegable Numero de Afiliado")
    public void clickDesplegableNumeroDeAfiliado(){
        UtilsGP.fixedWait(3000);
        Assert.assertTrue("NO SE ENCONTRO EL DESPLEGABLE NUMERO DE AFILIADO",bonosDeSaludPage.getDesplegableNumeroAfiliado().isDisplayed());
        bonosDeSaludPage.getDesplegableNumeroAfiliado().click();
    }
    @Step("Click numero")
    public void clickNumero(){
        bonosDeSaludPage.getInputNumero().click();
    }
    @Step("Set {numero}")
    public void setInputNumero(String numero){
        Assert.assertTrue("NO SE ENCONTRO INPUT NUMERO",bonosDeSaludPage.getInputNumero().isDisplayed());
        bonosDeSaludPage.getInputNumero().sendKeys(numero);
    }
    @Step("Click Pertenece A")
    public void clickPeterneceA(){
        bonosDeSaludPage.getInputPerteneceA().click();
    }
    @Step("Set {pertenece}")
    public void setInputPerteneceA(String pertenece){
        Assert.assertTrue("NO SE ENCONTRO INPUT REFERENCIA",bonosDeSaludPage.getInputPerteneceA().isDisplayed());
        bonosDeSaludPage.getInputPerteneceA().sendKeys(pertenece);
    }
    @Step("Click Icono Confirmar Numero")
    public void clickIconoConfirmar(){
        scrollTo(By.xpath("//button[@aria-label='Icono de confirmar']"));
        bonosDeSaludPage.getConfirmarNumero().click();
    }
    @Step("Click Agregar Bonos")
    public void clickAgregarBonos(){
        bonosDeSaludPage.getAgregarUnBono().click();
    }
    @Step("Click Desplegable Cuenta De Origen")
    public void clickDesplegableCuentaOrigen(){
        bonosDeSaludPage.getDesplegableCuentaOrigen().click();
    }
    @Step("Click Adhesiones")
    public void clickAdhesiones(){
        bonosDeSaludPage.getAdhesiones().click();
    }
    @Step("Eliminar Afiliado")
    public void clickEliminarAfiliado(){
        bonosDeSaludPage.getEliminarAfiliado().click();
    }
    @Step("Eliminar Afiliado")
    public void eliminarAfiliado(String afiliado) throws AWTException, InterruptedException {
        clickDesplegableEnte();
        keyDownToText(afiliado);
        clickBuscar();
        if(bonosDeSaludPage.getEliminarAfiliado().isDisplayed()){
            clickEliminarAfiliado();
            clickConfirmarGenerico();
        }else{
            System.out.println("No se encuentra ningun afiliado: " + afiliado);
        }
    }
    @Step("Post condicion Eliminar Afiliado")
    public void postCondicionEliminarAfiliado(String afiliado) throws InterruptedException, AWTException {
        menuLateralSteps.clickBonosDeSalud();
        Thread.sleep(5000);

        clickAdhesiones();
        //eliminarAfiliado(afiliado);
    }
    @Step("Click Buscar")
    public void clickBuscar(){
        scrollTo(By.xpath("//button[@aria-label='Buscar']"));
        bonosDeSaludPage.getBuscar().click();
    }
    @Step("Validar Tabla Afialiados")
    public void validarTablaAfiliados(){
        bonosDeSaludPage.getTablaAfialiados().isDisplayed();
    }
    @Step("Click Consultar Compras")
    public void clickConsultarCompras(){
        bonosDeSaludPage.getConsultarCompras().click();
    }
    @Step("Click Desplegable Canal")
    public void clickDesplegableCanal(){
        bonosDeSaludPage.getDesplegableCanal().click();
    }
    @Step("Click Calendario Desde")
    public void clickCalendarioDesde(){
        bonosDeSaludPage.getCalendarioDesde().click();
    }
    @Step("Eliminar Adhesion API")
    public void eliminarAdhesionAPI(String Ente, String NumAfiliado, String PerteneceA) throws InterruptedException {
        metodosAPI.eliminarAdhesionBonosDeSalud(Ente,NumAfiliado,PerteneceA);
    }
    @Step("Adherir Afiliado API")
    public void realizarAdhesionAPI(String Ente, String NumAfiliado, String PerteneceA) throws InterruptedException {
        metodosAPI.realizarAdhesionAfiliadoBonosDeSalud(Ente,NumAfiliado,PerteneceA);
    }
    @Step("Adherir Afiliado si no encuentra adherido API ")
    public void realizarAdheciónAfiliadoAPI(String tarjeta,String codigo){
        bonosDeSaludAPI.realizarNuevaAdhesionBonosDeSalud(tarjeta,codigo);
    }
}
