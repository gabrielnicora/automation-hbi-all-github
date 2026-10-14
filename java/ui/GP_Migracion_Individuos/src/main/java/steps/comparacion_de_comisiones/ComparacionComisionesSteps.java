package steps.comparacion_de_comisiones;

import global.practia.jemeinsamstark.web.core.WebDriver;
import io.qameta.allure.Step;
import org.junit.Assert;
import pages.comparacion_de_comisiones.ComparacionComisionesPage;
import steps.botonesgenericos.BotonesGenericosSteps;
import utils.UtilsGP;

public class ComparacionComisionesSteps extends BotonesGenericosSteps {

    ComparacionComisionesPage comparacionComisionesPage = new ComparacionComisionesPage();
    UtilsGP utilsGP = new UtilsGP();
    private String URL_BCRA = "https://www.bcra.gob.ar/BCRAyVos/Comparacion_de_Comisiones.asp";

    @Step("Se valida logo popup de comparacion de comisiones")
    public void validaPopupComparacionComisiones(){
        Assert.assertTrue("No es visible el pop up", comparacionComisionesPage.getLogoPopupComparacionComisiones().isDisplayed());
    }
    @Step("Se valida visibilidad titulo popup Comparacion de comisiones")
    public void validaTituloComparacionDeComisiones(){
        String cadenaSinAcentos = utilsGP.removerAcentos(comparacionComisionesPage.getTituloPopupComparacionComisiones().getText());
        Assert.assertEquals("Comparacion de Comisiones",cadenaSinAcentos);
    }
    @Step("Validar redireccion BCRA")
    public void validaRedireccionBancoCentral() {
        utilsGP.ManageTab();
        utilsGP.NavegarUtilsWeb(URL_BCRA);
        Assert.assertEquals(URL_BCRA, WebDriver.getInstance().getCurrentUrl());
        Assert.assertTrue("No es visible banner banco central", comparacionComisionesPage.getBannerPaginaBancoCentral().isDisplayed());
        utilsGP.backMainTap();
    }
}
