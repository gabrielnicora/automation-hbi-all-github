package steps.inversiones.cedip;

import io.qameta.allure.Step;
import org.junit.Assert;
import pages.botonesgenericos.BotonesGenericosPage;
import pages.inversiones.cedip.CedipPage;
import steps.botonesgenericos.BotonesGenericosSteps;

import java.awt.*;
import java.io.UnsupportedEncodingException;


public class CedipSteps {
    private BotonesGenericosPage botonesGenericos = new BotonesGenericosPage();

    CedipPage cedipPage = new CedipPage();

    BotonesGenericosSteps botonesGenericosSteps = new BotonesGenericosSteps();

    @Step("Validar titulo Generico: {titulo}")
    public void validarTituloPageGenerico(String titulo) throws UnsupportedEncodingException {
        Assert.assertTrue("Titulo no visible", botonesGenericos.getTituloPage(titulo).isDisplayed());
    }
    @Step("Seleccionar TIPO CEDIP:  {TipoCedip}")
    public void seleccionarTipoCedip(String TipoCedip) throws AWTException {

        cedipPage.getTipoCedipDesplegable().click();
        botonesGenericosSteps.keyDownToText(TipoCedip);
    }
    @Step("Click Constituir Plazo Fijo Cedip")
    public void clickConstituirPlazoFijoCedip(){
        cedipPage.getConstituir().click();
    }
    @Step("Ingresar Capital")
    public void ingresarCapital(String Monto){
        cedipPage.getCapital().sendKeys(Monto);
    }
    @Step("Ingresar Plazo En Dias")
    public void ingresarPlazoEnDias(String CantDias){
        cedipPage.getPlazoEnDias().sendKeys(CantDias);
    }
    @Step("Seleccionar Cuenta Debito: {CuentaDebito}")
    public void seleccionarCuentaDebito(String CuentaDebito) throws AWTException {
        cedipPage.getDespegableCuentaDebito().click();
        botonesGenericosSteps.keyDownToText(CuentaDebito);

    }
    @Step("Seleccionar Accion Al Vencimiento: {AccionVencer}")
    public void seleccionarAccionAlVencer(String AccionVencer) throws AWTException {
        cedipPage.getDespegableAccionAlVencer().click();
        botonesGenericosSteps.keyDownToText(AccionVencer);

    }
}
