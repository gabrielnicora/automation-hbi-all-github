package steps.transferencias;

import io.qameta.allure.Step;
import pages.transferencias.TransferenciasPage;
import steps.botonesgenericos.BotonesGenericosSteps;

public class TransferenciasSteps extends BotonesGenericosSteps {

    private TransferenciasPage transferenciasPage = new TransferenciasPage();

    @Step("Click en Consultar Credin")
    public void clickConsultarCredin(){
        transferenciasPage.getConsultarCredin().click();
    }

    @Step("Click desplegable Cuenta")
    public void clickDesplegableCuenta(){
        transferenciasPage.getCuentaDesplegable().click();
    }

    @Step("Click en card transferencias inmediatas")
    public void clickTransferenciasInmediatas(){
        transferenciasPage.getCardTransferenciasInmediatas().click();
    }
    @Step("Click transferencias programadas")
    public void clickTransferenciasProgramadas(){
        transferenciasPage.getTransferenciasProgramadas().click();
    }
}
