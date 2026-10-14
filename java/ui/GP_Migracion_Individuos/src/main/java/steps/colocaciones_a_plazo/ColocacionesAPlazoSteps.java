package steps.colocaciones_a_plazo;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.colocaciones_a_plazo.ColocacionesAPlazoPage;
import steps.botonesgenericos.BotonesGenericosSteps;

public class ColocacionesAPlazoSteps extends BotonesGenericosSteps {
    ColocacionesAPlazoPage colocacionesAPlazoPage= new ColocacionesAPlazoPage();
    @Step("Click en Consultar Debin Desplegable ")
    public void clickConsultarDebinDesplegable(){
        colocacionesAPlazoPage.getConsultarDebinDesplegable().click();
    }

    @Step("Click Fecha de Creacion Desde")
    public void clickFechaCreacionDesde(){
        colocacionesAPlazoPage.getFechaCreacionDesde().click();
    }
    @Step("Click Fecha de Creacion Hasta")
    public void clickFechaCreacionHasta(){
        colocacionesAPlazoPage.getFechaCreacionHasta().click();
    }
    @Step("Click en Buscar")
    public void clickBuscar(){
        colocacionesAPlazoPage.getBuscar().click();
    }
    @Step("Validar Columna ID Debin")
    public void validarTablaDeHistoricoyPendientes() {
        WebDriverWait wait = new WebDriverWait(global.practia.jemeinsamstark.web.core.WebDriver.getInstance(), 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@aria-label, 'tabla')]")));
        Assert.assertTrue("No se registran Historicos", colocacionesAPlazoPage.getTablaHistoricoyPendiente().isDisplayed());
    }
    @Step("Click en Adherir Recurrencia")
    public void clickAdherirRecurrencia(){
        colocacionesAPlazoPage.getAdherirRecurrencia().click();
    }
    @Step("Click en Cuenta Debito")
    public void clickDesplegableCuentaDebito(){
        colocacionesAPlazoPage.getCuentaDebito().click();
    }
    @Step("Click Cuit Vendedor")
    public void clickCuitVendedor(){
        colocacionesAPlazoPage.getInputCuitVendedor().click();
    }
    @Step("Set {cuitVendedor}")
    public void setCuitVendedor(String cuitVendedor){
        colocacionesAPlazoPage.getInputCuitVendedor().sendKeys(cuitVendedor);
    }
    @Step("Click Desplegable Prestacion")
    public void clickDesplegablePrestacion(){
        colocacionesAPlazoPage.getDesplegablePrestacion().click();
    }
    @Step("Click Desplegable Concepto")
    public void clickDesplegableConcepto(){
        colocacionesAPlazoPage.getDesplegableConcepto().click();
    }
    @Step("Set {referencia}")
    public void setReferencia(String referencia){
        colocacionesAPlazoPage.getInputReferencia().sendKeys(referencia);
    }
    @Step("Set {descripcion}")
    public void setDescripcion(String descripcion){
        colocacionesAPlazoPage.getInputDescription().sendKeys(descripcion);
    }
    @Step("Click continuar")
    public void clickContinuar(){
        colocacionesAPlazoPage.getContinuar().click();
    }
    @Step("Click Consultar Recurrencias")
    public void clickConsultarRecurrencias(){
        colocacionesAPlazoPage.getConsultarRecurrencias().click();
    }
    @Step("Click Desplegable Estado")
    public void clickDesplegableEstado(){
        colocacionesAPlazoPage.getDesplegableEstado().click();
    }
}
