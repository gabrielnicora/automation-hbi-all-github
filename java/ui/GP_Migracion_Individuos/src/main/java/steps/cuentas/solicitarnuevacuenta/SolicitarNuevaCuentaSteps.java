package steps.cuentas.solicitarnuevacuenta;

import io.qameta.allure.Step;
import pages.cuentas.solicitarnuevacuenta.SolicitarNuevaCuentaPage;
import steps.botonesgenericos.BotonesGenericosSteps;
import utils.UtilsGP;

public class SolicitarNuevaCuentaSteps extends BotonesGenericosSteps {
    private SolicitarNuevaCuentaPage solicitarNuevaCuentaPage = new SolicitarNuevaCuentaPage();

    @Step("Click desplegable Tipo de cuenta")
    public void clickTipoDeCuenta(){
        UtilsGP.fixedWait(3000);
        solicitarNuevaCuentaPage.getTipoDeCuentaDesplegable().click();
    }
    @Step("Click desplegable Moneda de la cuenta")
    public void clickMonedaDeLaCuenta(){
        UtilsGP.fixedWait(3000);
        solicitarNuevaCuentaPage.getMonedaDeLaCuentaDesplegable().click();
    }
}
