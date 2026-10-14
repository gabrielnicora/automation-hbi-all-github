package steps.pagos.debincomprador;

import io.qameta.allure.Step;
import pages.pagos.debincomprador.AdherirRecurrenciaPage;
import steps.botonesgenericos.BotonesGenericosSteps;

public class AdherirRecurrenciaSteps extends BotonesGenericosSteps {

    private AdherirRecurrenciaPage adherirRecurrencia = new AdherirRecurrenciaPage();

    @Step("Click Selector Cuenta Debito")
    public void clickSelectorCuentaDebito() {
        adherirRecurrencia.getSelectorCuentaDebito().click();
    }

    @Step("Ingresar CUIT Vendedor")
    public void setCuitVendedor(String cuitVendedor) {
        adherirRecurrencia.getCuitVendedor().sendKeys(cuitVendedor);
    }

    @Step("Click Boton Consultar")
    public void clickBotonConsultar() {
        adherirRecurrencia.getBotonConsultar().click();
    }

    @Step("Click Selector Prestacion")
    public void clickSelectorPrestacion() {
        adherirRecurrencia.getSelectorPrestacion().isDisplayed();
        adherirRecurrencia.getSelectorPrestacion().click();
    }

    @Step("Click Selector Concepto")
    public void clickSelectorConcepto() {
        adherirRecurrencia.getSelectorConcepto().click();
    }

    @Step("Ingresar Referencia")
    public void setReferencia(String referencia) {
        adherirRecurrencia.getReferencia().sendKeys(referencia);
    }

    @Step("Ingresar Descripcion")
    public void setDescripcion(String descripcion) {
        adherirRecurrencia.getDescripcion().sendKeys(descripcion);
    }

    @Step("Click Boton Continuar")
    public void clickBotonContinuar() {
        adherirRecurrencia.getBotonContinuar().click();
    }

}
