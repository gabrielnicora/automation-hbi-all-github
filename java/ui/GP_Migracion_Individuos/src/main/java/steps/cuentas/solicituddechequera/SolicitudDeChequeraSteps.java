package steps.cuentas.solicituddechequera;

import io.qameta.allure.Step;
import org.junit.Assert;
import pages.cuentas.solicituddechequera.SolicitudDeChequeraPage;
import steps.botonesgenericos.BotonesGenericosSteps;

public class SolicitudDeChequeraSteps extends BotonesGenericosSteps {

    private SolicitudDeChequeraPage solicitudDeChequeraPage = new SolicitudDeChequeraPage();

    @Step("Click desplegable Tipo de cheque")
    public void clickDesplegableTipoDeCheque(){
        Assert.assertTrue("Tipo de cheque no visible", solicitudDeChequeraPage.getTipoDeCheque().isDisplayed());
        solicitudDeChequeraPage.getTipoDeCheque().click();
    }
    @Step("Click desplegable Cantidad de cheques")
    public void clickDesplegableCantidadDeCheques(){
        Assert.assertTrue("Cantidad de cheques no visible", solicitudDeChequeraPage.getCantidadDeCheques().isDisplayed());
        solicitudDeChequeraPage.getCantidadDeCheques().click();
    }
    @Step("Click desplegable Cantidad de chequeras")
    public void clickDesplegableCantidadDeChequeras(){
        Assert.assertTrue("Cantidad de chequeras no visible", solicitudDeChequeraPage.getCantidadDeChequeras().isDisplayed());
        solicitudDeChequeraPage.getCantidadDeChequeras().click();
    }

}
