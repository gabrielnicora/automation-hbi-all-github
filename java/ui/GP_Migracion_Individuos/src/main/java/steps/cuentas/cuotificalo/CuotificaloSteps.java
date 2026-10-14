package steps.cuentas.cuotificalo;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.cuentas.cuotificalo.CuotificaloPage;
import steps.botonesgenericos.BotonesGenericosSteps;

import java.awt.*;
import java.awt.event.KeyEvent;

public class CuotificaloSteps extends BotonesGenericosSteps {
    private CuotificaloPage cuotificaloPage = new CuotificaloPage();
    WebDriverWait wait = new WebDriverWait(global.practia.jemeinsamstark.web.core.WebDriver.getInstance(), 30);

    @Step("Click en Cuotificalo")
    public void clickCuotificalo() {
        cuotificaloPage.getCuotificalo().click();
    }

    @Step("Click en Descargar Resumen de Contrato")
    public void clickDescargarResumenContrato() throws AWTException, InterruptedException {
        cuotificaloPage.getResumenContrato().click();
        BotonesGenericosSteps botonesGenericosSteps = new BotonesGenericosSteps();
        botonesGenericosSteps.clickAceptarGenerico();
    }

    @Step("Click en Primeros Cuatro Checkboxes")
    public void clickEnPrimerosCuatroCheckboxes() {
        Assert.assertTrue("NO SE ENCONTRO LOS CHECKBOXES : "+ cuotificaloPage.getPrimerCheckbox().getElementBy(),cuotificaloPage.getPrimerCheckbox().isDisplayed());
        cuotificaloPage.getPrimerCheckbox().click();
        cuotificaloPage.getSegundoCheckbox().click();
        cuotificaloPage.getTercerCheckbox().click();
        cuotificaloPage.getCuartoCheckbox().click();
    }

    @Step("Click en Opcion de Plazo de Doce Cuotas")
    public void clickOpcionPlazoDeDoceCuotas() {
        cuotificaloPage.getPlazoDoceCuotas().click();
    }

    @Step("Click en Cuenta de Acreditacion")
    public void clickCuentaAcreditacion() {
        scrollTo(By.id("loan.request.accreditationAccount"));
        wait.until(ExpectedConditions.visibilityOf(cuotificaloPage.getCuentaDeAcreditacion()));
        cuotificaloPage.getCuentaDeAcreditacion().click();
    }

    @Step("Click en Cuenta Debito")
    public void clickCuentaDebito() throws InterruptedException {
        scrollTo(By.xpath("//button[@aria-label='Continuar']"));
        cuotificaloPage.getCuentaDebito().click();
    }
}
