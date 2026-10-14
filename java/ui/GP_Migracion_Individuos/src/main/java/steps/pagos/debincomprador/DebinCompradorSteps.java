package steps.pagos.debincomprador;

import global.practia.jemeinsamstark.web.elements.PandoraElement;
import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.botonesgenericos.BotonesGenericosPage;
import pages.pagos.debincomprador.DebinCompradorPage;
import steps.botonesgenericos.BotonesGenericosSteps;
import utils.UtilsGP;

import java.awt.*;
import java.util.Map;

public class DebinCompradorSteps extends BotonesGenericosSteps {

    private DebinCompradorPage debinComprador = new DebinCompradorPage();
    private BotonesGenericosPage botonesGenericos = new BotonesGenericosPage();
    private UtilsGP utilsGP = new UtilsGP();

    @Step("Click Selector Consultar DEBIN")
    public void clickSelectorConsultarDebin() { debinComprador.getSelectorConsultatDebin().click(); }


    @Step("Click Boton Buscar")
    public void clickBuscar() { debinComprador.getBuscar().click(); }

    @Step("Seleccionar Calendario Fecha de Creacion Desde")
    public void clickFechaCreacionDesde() { debinComprador.getFechaCreacionDesde().click(); }

    @Step("Seleccionar Calendario Fecha de Creacion Hasta")
    public void clickFechaCreacionHasta() { debinComprador.getFechaCreacionHasta().click(); }

    @Step("Validar Resultado de Busqueda Debines Recibidos")
    public void validarColumnaIdDebin() {
        WebDriverWait wait = new WebDriverWait(global.practia.jemeinsamstark.web.core.WebDriver.getInstance(), 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='mainLayout']//span[contains(text(),'Id DEBIN')]")));
        Assert.assertTrue("Resultado no visible", debinComprador.getColumnaIdDebin().isDisplayed());
    }

    @Step("Validar Msj 'No se registran órdenes para los criterios ingresados.'")
    public void validarMsjSinDebinesRecibidos() {
        WebDriverWait wait = new WebDriverWait(global.practia.jemeinsamstark.web.core.WebDriver.getInstance(), 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='mainLayout']//span[contains(text(),'No se registran')]")));
        Assert.assertTrue("Mensaje no visible", debinComprador.getMsjSinDebinesRecibidos().isDisplayed());
    }

    @Step("Click Adherir Recurrencia")
    public void clickAdherirRecurrencia() {
        debinComprador.getAdherirRecurrencia().click();
    }

    @Step("Click icono flecha detalle DEBIN primer elemento")
    public void clickDetalleDebinPrimerElemento(){
        debinComprador.getIconoFlechaDetalleDebin().click();
    }
    @Step("Click aceptar DEBIN")
    public void clickAceptarDebin(){
        scrollTo(By.xpath("(//button[@aria-label='Aceptar'])[2]"));
        debinComprador.getAceptarDebin().click();
    }
    @Step("Se ingresa codigo de seguridad {codigo}")
    public void setCodigoSeguridad(String codigo){
        debinComprador.getCodigoSeguridad().sendKeys(codigo);
    }
    @Step("Se ingresa mes {mes}")
    public void setMes(String mes){
        debinComprador.getMes().sendKeys(mes);
    }
    @Step("Se ingresa anio {anio}")
    public void setAnio(String anio){
        debinComprador.getAnio().sendKeys(anio);
    }
    @Step("Click desplegable Tarjeta de debito")
    public void clickDesplegableTarjetaDebito(){
        debinComprador.getDesplegableTarjetaDebito().click();
    }
    @Step("Click desplegable Tarjeta de debito segundo factor")
    public void clickTarjetaDebito2doFactor(){
        debinComprador.getTarjetaDebitoSegundoFactor().click();
    }

    @Step("Click Tarjeta Debito")
    public void seleccionarTarjetaDebitoSiEstaPresente(String tarjetaDebito) throws AWTException {
        if(debinComprador.getDesplegableTarjetaDebito().isDisplayed()){
            debinComprador.getDesplegableTarjetaDebito().click();
            keyDownToText(tarjetaDebito);
        }else{
            System.out.println("No está presente el selector de tarjeta de debito");
        }
    }
    @Step("Aceptar Debin con pantalla de estafa")
    public void clickAceptarConEstafas() {
        botonesGenericos.getAceptar().click();
        PandoraElement botonAceptarModal = new PandoraElement(By.xpath("(//*[contains(text(),'Aceptar')]/../..)[2]"));
        if(utilsGP.estaPresente(botonAceptarModal, 2)){
            botonAceptarModal.click();
        }
    }
}
