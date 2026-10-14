package steps.inversiones.plazosfijos;

import io.qameta.allure.Step;
import org.openqa.selenium.Keys;
import pages.botonesgenericos.BotonesGenericosPage;
import pages.doblefactor.DoblefactorPage;
import pages.inversiones.plazosfijos.SolicitarPlazoFijoPage;
import steps.botonesgenericos.BotonesGenericosSteps;
import steps.doblefactor.DobleFactorSteps;
import utils.UtilsGP;

public class SolicitarPlazoFijoSteps extends BotonesGenericosSteps {
    public DoblefactorPage doblefactorPage= new DoblefactorPage();
    public SolicitarPlazoFijoPage solicitarPlazoFijoPage = new SolicitarPlazoFijoPage();
    public BotonesGenericosPage botonesGenericosPage = new BotonesGenericosPage();

    @Step("Click Selector Tipo de PF")
    public void clickSelectorTipoPF() {
        scrollTo(solicitarPlazoFijoPage.getSelectorTipo().getElementBy());
        solicitarPlazoFijoPage.getSelectorTipo().click();
    }
    @Step("Click Selector Tipo de PF y selección tipo de PF")
    public void clickSelectorTipoPF(String tipoPF) {
        solicitarPlazoFijoPage.getSelectorTipo().click();
        solicitarPlazoFijoPage.getTipoPlazoFijo(tipoPF).click();
    }
    @Step("Ingresar Plazo en Dias")
    public void setPlazoEnDias(String plazo) {
        solicitarPlazoFijoPage.getPlazoEnDias().sendKeys(plazo);
    }
    @Step("Click Selector Cuenta Debito")
    public void clickSelectorCuentaDebito() {
        solicitarPlazoFijoPage.getSelectorCuentaDebito().click();
    }
    @Step("Click Seleccion Accion al Vencimiento")
    public void clickSelectorAccionVencimiento() {
        solicitarPlazoFijoPage.getSelectorAccionAlVencimiento().click();
    }

    @Step("Validar si existe mensaje emergente")
    public void validarSiExisteMensajeEmergente(){
        try {
            doblefactorPage.getMensajeEmergente().isDisplayed();
            String mensaje= doblefactorPage.getMensajeEmergente().getText();
            throw new Error("Mensaje emergente presente: " + mensaje);
        }catch (Exception e){
            System.out.println("No hay mensaje emergente");

        }
    }

    @Step("Click opción Plazo Fijo UVA")
    public void clickPlazoFijoUVA() {
        solicitarPlazoFijoPage.getListaDesplegableItemPlazoFijoUVA().click();
    }

    @Step("Limpiar campo Importe")
    public void clearCampoImporte() {

        String texto = String.valueOf(botonesGenericosPage.getCampoImporte().getAttribute("Value"));

        int tamaño = texto.length();

        UtilsGP.fixedWait(3000);
        for (int i = 0; i < tamaño; i++) {
            botonesGenericosPage.getCampoImporte().sendKeys(Keys.BACK_SPACE);
        }
    }

}
