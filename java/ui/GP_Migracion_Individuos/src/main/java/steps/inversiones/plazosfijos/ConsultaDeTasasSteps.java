package steps.inversiones.plazosfijos;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.inversiones.plazosfijos.ConsultaDeTasasPage;
import steps.botonesgenericos.BotonesGenericosSteps;

public class ConsultaDeTasasSteps extends BotonesGenericosSteps {

    private ConsultaDeTasasPage consultaDeTasasPage = new ConsultaDeTasasPage();

    @Step("Click Selector Tipo de Plazo Fijo")
    public void clickSelectorTipoPlazoFijo() {
        consultaDeTasasPage.getSelectorTipoPlazoFijo().click();
    }

    @Step("Click Selector Tipo de Consulta ")
    public void clickSelectorTipoConsulta() {
        consultaDeTasasPage.getSelectorTipoConsulta().click();
    }

    @Step("Click Boton Buscar")
    public void clickBuscar() {
        clickBuscarGenerico();
    }

    @Step("Ingresar Plazo")
    public void setPlazo() {
        consultaDeTasasPage.getPlazo().click();
    }

    @Step("Validar Columna Plazo Desde")
    public void validarPlazoDesde() {
        WebDriverWait wait = new WebDriverWait(global.practia.jemeinsamstark.web.core.WebDriver.getInstance(), 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='mainLayout']//span[contains(text(),'Plazo Desde')]")));
        Assert.assertTrue("Resultado de busqueda no visible", consultaDeTasasPage.getColumnaPlazoDesde().isDisplayed());
    }

    @Step("Validar Columna Plazo Hasta")
    public void validarPlazoHasta() {
        WebDriverWait wait = new WebDriverWait(global.practia.jemeinsamstark.web.core.WebDriver.getInstance(), 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='mainLayout']//span[contains(text(),'Plazo Hasta')]")));
        Assert.assertTrue("Resultado de busqueda no visible", consultaDeTasasPage.getColumnaPlazoHasta().isDisplayed());
    }

    @Step("Validar Columna Tasa TNA pizarra")
    public void validarTasaTNA() {
        WebDriverWait wait = new WebDriverWait(global.practia.jemeinsamstark.web.core.WebDriver.getInstance(), 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='mainLayout']//span[contains(text(),'Tasa TNA pizarra')]")));
        Assert.assertTrue("Resultado de busqueda no visible", consultaDeTasasPage.getColumnaTasaTNA().isDisplayed());
    }

}
