package steps.baja_arrepentimiento;

import global.practia.jemeinsamstark.web.core.WebDriver;
import io.qameta.allure.Step;
import org.junit.Assert;
import pages.baja_arrepentimiento.BajaArrepentimientoPage;
import steps.botonesgenericos.BotonesGenericosSteps;
import utils.UtilsGP;

public class BajaArrepentimientoSteps extends BotonesGenericosSteps {

    BajaArrepentimientoPage bajaArrepentimientoPage = new BajaArrepentimientoPage();
    UtilsGP utilsGP = new UtilsGP();

    @Step("Se valida logo popup de baja y arrepentimiento")
    public void validaPopupBajaArrepentimiento(){
        Assert.assertTrue("No es visible el pop up",
                bajaArrepentimientoPage.getLogoPopupBajaArrepentimiento().isDisplayed());
    }
    @Step("Se valida visibilidad titulo popup baja y arrepentimiento")
    public void validarTituloBajaArrepentimiento(){
        Assert.assertTrue("No es visible titulo en pop up",
                bajaArrepentimientoPage.getTituloPopupBajaArrepentimiento().isDisplayed());
    }
    @Step("Validar redireccion Online banking")
    public void validaRedireccionOnlineBanking(String urlOnlineBanking)  {
        utilsGP.ManageTab();
        utilsGP.NavegarUtilsWeb(urlOnlineBanking);
        Assert.assertEquals(urlOnlineBanking, WebDriver.getInstance().getCurrentUrl());
        Assert.assertTrue("No es visible titulo en Online Banking", bajaArrepentimientoPage.getTituloOnlineBanking().isDisplayed());
        utilsGP.backMainTap();
    }



}
