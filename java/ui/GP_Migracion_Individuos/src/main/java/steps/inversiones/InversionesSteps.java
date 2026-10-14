package steps.inversiones;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.inversiones.InversionesPage;
import steps.botonesgenericos.BotonesGenericosSteps;
import utils.UtilsGP;

public class InversionesSteps extends BotonesGenericosSteps {
    private InversionesPage inversionesPage = new InversionesPage();
    private UtilsGP utilsGP = new UtilsGP();
    @Step("Click en Compra Venta de Dolares")
    public void clickCompraVentaDeDolares(){
        if(utilsGP.estaPresente(inversionesPage.getBotonNuevaInversion(),2)){
            System.out.println("Botón encontrado");
            inversionesPage.getBotonNuevaInversion().click();
            inversionesPage.getCompraVentaDeDolares().click();
        }else {
            System.out.println("No se Encontró el botón Nueva inversión");
        }}
    @Step("Click en Fondo Comun de Inversion")
    public void clickFondoComunDeInversion(){
        if(utilsGP.estaPresente(inversionesPage.getBotonNuevaInversion(),2)){
            inversionesPage.getBotonNuevaInversion().click();
            inversionesPage.getFondoComunDeInversion().click();
            System.out.println("SE INGRESA A FCI");
        }else{
            System.out.println("No se Encontró el botón Nueva inversión");
        }}
    @Step("Click en Plazos Fijos")
    public void clickPlazosFijos(){
        if (utilsGP.estaPresente(inversionesPage.getBotonNuevaInversion(), 2)) {
            System.out.println("Botón encontrado");
            inversionesPage.getBotonNuevaInversion().click();
            inversionesPage.getPlazosFijos().click();
        }else{
            System.out.println("No se Encontró el botón Nueva inversión");
        }}
    @Step("Click en Solicitar Nueva Cuenta Comitente")
    public void clickSolicitarNuevaCuentaComitente(){
        Assert.assertTrue("NO SE ENCONTRO EL BOTON : "+inversionesPage.getSolicitarNuevaCuentaComitente().getElementBy(),inversionesPage.getSolicitarNuevaCuentaComitente().isDisplayed());
        inversionesPage.getSolicitarNuevaCuentaComitente().click();}
    @Step("Click en Solicitar Baja de Cuenta Comitente")

    public void clickSolicitarBajaDeCuentaComitente(){
        WebDriverWait wait = new WebDriverWait(global.practia.jemeinsamstark.web.core.WebDriver.getInstance(), 10);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Solicitar baja de cuenta comitente']")));
        inversionesPage.getSolicitarBajaDeCuentaComitente().click();}
    @Step("Click en seleccionar cuenta")
    public void clickSeleccionarCuenta(){inversionesPage.getSeleccionarCuenta().click();}
    @Step("Click en CEDIP")
    public void clickCedip(){
        if(utilsGP.estaPresente(inversionesPage.getBotonNuevaInversion(),2)){
            System.out.println("Botón encontrado");
            inversionesPage.getBotonNuevaInversion().click();
            inversionesPage.getSeleccionarCedip().click();
        }else{
            System.out.println("No se Encontró el botón Nueva inversión");
        }}

}
