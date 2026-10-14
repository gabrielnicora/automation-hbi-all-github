package steps.transferencias.generarcredin;

import api.DobleFactorAPI;
import api.TransferenciasAPI;
import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import pages.doblefactor.DoblefactorPage;
import pages.transferencias.generarcredin.GenerarCredinPage;
import steps.botonesgenericos.BotonesGenericosSteps;
import steps.doblefactor.DobleFactorSteps;
import utils.MetodosAPI;
import utils.UtilsGP;

public class GenerarCredinSteps extends BotonesGenericosSteps {

    private GenerarCredinPage generarCredin = new GenerarCredinPage();
    private MetodosAPI metodosAPI = new MetodosAPI();
    DobleFactorSteps dobleFactorSteps = new DobleFactorSteps();
    DoblefactorPage doblefactorPage = new DoblefactorPage();
    TransferenciasAPI transferenciasAPI = new TransferenciasAPI();

    @Step("Click Tarjeta Debito")
    public void clickTarjetaDebito(){
        generarCredin.getSelectorTarjetaDebito().click();
    }
    @Step("Click Tarjeta Debito")
    public void seleccionarTarjetaDebitoSiEstaPresente(){
        if(generarCredin.getSelectorTarjetaDebito().isDisplayed()){
            generarCredin.getSelectorTarjetaDebito().click();
            keyDownXParametrosGenerico(0, "");
            clickContinuarGenerico();
        }else{
            System.out.println("No está presente el selector de tarjeta de debito");
        }
    }
    @Step("Validar TD Seleccionada")
    public void validarTarjetaDeDebitoSeleccionada(String numTarjeta) throws InterruptedException {
        Thread.sleep(1000);
        Assert.assertTrue("La tarejta selecionada no correspode a los ultimos 4 digitos: " + numTarjeta,
                generarCredin.getNumDeTarjetaDeDebitoSeleccionada(numTarjeta).isDisplayed());
    }

    @Step("Setear Tarjera Debito")
    public void setTarjetaDebito(){
        Actions keyDown = new
                Actions(global.practia.jemeinsamstark.web.core.WebDriver.getInstance());
        keyDown.sendKeys(Keys.chord(Keys.DOWN, Keys.DOWN, Keys.ENTER)).perform();
    }

    @Step("Click Cuenta Debito")
    public void clickCuentaDebito(){
        UtilsGP.fixedWait(3000);
        generarCredin.getSelectorCuentaDebito().isDisplayed();
        generarCredin.getSelectorCuentaDebito().click();
        UtilsGP.fixedWait(3000);

    }

    @Step("Setear Cuenta Debito")
    public void setCuentaDebito(){
        Actions keyDown = new
                Actions(global.practia.jemeinsamstark.web.core.WebDriver.getInstance());
        keyDown.sendKeys(Keys.chord(Keys.ENTER)).perform();
    }

    @Step("Click CBU")
    public void clickCBU(){
        UtilsGP.fixedWait(3000);
        generarCredin.getCheckCBU().click();
        UtilsGP.fixedWait(3000);
    }

    @Step("Click Alias")
    public void clickALIAS(){
        generarCredin.getCheckAlias().click();
    }

    @Step("Click Campo CBU/Alias")
    public void clickCampoAliasCbu(){
        generarCredin.getCampoAliasCbu().click();
    }
    @Step("Ingresar CBU/Alias")
    public void setCampoAliasCbu(String texto){
        generarCredin.getCampoAliasCbu().sendKeys(texto);
    }

    @Step("Click Selector Concepto")
    public void clickSelectorConcepto(){
        scrollTo(By.id("selectorDescribedBy_1"));
        generarCredin.getSelectorConcepto().click();
    }

    @Step("Setear Selector Concepto")
    public void setSelectorConcepto(){
        Actions keyDown = new Actions(global.practia.jemeinsamstark.web.core.WebDriver.getInstance());
        keyDown.sendKeys(Keys.chord(Keys.ENTER)).perform(); }

    @Step("Click Campo Descripcion")
    public void clickCampoDescripcion(){
        generarCredin.getCampoDescripcion().click();
    }

    @Step("Ingresar Descripcion")
    public void setCampoDescripcion(String texto){
        generarCredin.getCampoDescripcion().sendKeys(texto);
    }

    @Step("Click Campo NComprobante")
    public void clickNComprobante(){
        generarCredin.getCampoNComprobante().click();
    }

    @Step("Ingresar Numero de Comprobante")
    public void setCampoNComprobante(String texto){
        generarCredin.getCampoNComprobante().sendKeys(texto);
    }

    @Step("Click Check 'Es cuenta propia'")
    public void clickCheckEsCuentaPropia(){
        this.scrollTo(By.xpath("//input[@type='checkbox']/following-sibling::span"));
        generarCredin.getCheckEscuentapropia().click();
    }

    @Step("Click Acepto Terminos y Condiciones")
    public void clickCheckTerminosCondiciones(){
        this.scrollTo(By.xpath("//*[@type='checkbox']/following-sibling::span"));
        generarCredin.getCheckTerminosyCondiciones().click();
        UtilsGP.fixedWait(3000);
    }

    @Step("Ingresar codigo de seguridad: {codigoSeguridad}")
    public void setCodigoSeguridad(String codigoSeguridad){
        generarCredin.getCodigoSeguridad().sendKeys(codigoSeguridad);
    }

    @Step("Ingresar mes vencimiento: {mes}")
    public void setMes(String mes){
        generarCredin.getMes().sendKeys(mes);
    }

    @Step("Ingresar anio vencimiento: {anio}")
    public void setAnio(String anio){
        generarCredin.getAnio().sendKeys(anio);
    }
    @Step("Generación de Credin por Api")
    public void generarCredinApi(String cbuOrigen, String cbuDestino, String tarjetaDebito){
        transferenciasAPI.generarDebin(cbuOrigen,cbuDestino,tarjetaDebito);
    }
    @Step("Finalizar proceso de credin Aceptar tyC y doble factor")
    public void acpetarTyCDobleFactor(){
        scrollTo(By.xpath("//*[@type='checkbox']/following-sibling::span"));
        Assert.assertTrue("NO SE ECNONTRO TERMINE Y CONDICIONES",generarCredin.getCheckTerminosyCondiciones().isDisplayed());
        generarCredin.getCheckTerminosyCondiciones().click();
        int intentos = 0;
        dobleFactorSteps.codigoSegundoFactor();
        System.out.println("========= PRIMER INTENTO DE CREDIN ===============");
        while (intentos < 4){
            System.out.println("================== PASO POR AQUI ==================");
            if(doblefactorPage.getMensajeEmergente().isDisplayed()){
                scrollTo(By.xpath("//*[@type='checkbox']/following-sibling::span"));
                Assert.assertTrue("No se ECNONTRO TERMINE Y CONDICIONES",generarCredin.getCheckTerminosyCondiciones().isDisplayed());
                generarCredin.getCheckTerminosyCondiciones().click();
                dobleFactorSteps.codigoSegundoFactor();
                System.out.println("INTENTO NUMERO CREDIN " + (intentos+1));
            }else{
                System.out.println("========= ENTONCES AHORA FUNCIONA A LA PRIMERA ==============");
                break;
            }
            intentos++;
        }
    }

}
