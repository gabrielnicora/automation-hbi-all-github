package steps.turnosonline;

import io.qameta.allure.Step;
import org.junit.Assert;
import pages.turnosonline.TurnosOnlinePage;
import steps.botonesgenericos.BotonesGenericosSteps;
import utils.UtilsGP;

public class TurnosOnlineSteps extends BotonesGenericosSteps {

    private TurnosOnlinePage turnosOnline = new TurnosOnlinePage();
    private UtilsGP utilsGP = new UtilsGP();
    private String URL_TurnoOnline = "https://www.bancosantafe.com.ar/Turnosweb/home";

    @Step("Pestaña Turnos Online")
    public void TurnosOnlineTab() throws InterruptedException {
        utilsGP.NuevoTab();
        utilsGP.ManageTabPorParametro(2);
        utilsGP.NavegarUtilsWeb(URL_TurnoOnline);
        Assert.assertTrue("Logo Visible", turnosOnline.getLogoSFTurnoOnline().isDisplayed());
        utilsGP.backMainTap();
    }

    @Step("Verificacion mensaje y Aceptar Redireccion")
    public void VerificarMensajeYAceptar() {
        Assert.assertTrue("Mensaje Redireccion", turnosOnline.getMensajeRedireccion().isDisplayed());
        clickAceptarGenerico();

    }
}
