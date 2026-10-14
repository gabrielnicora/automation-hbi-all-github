package GP.EntreRios.comparacion_de_comisiones;

import GP.Tags.Alta;
import GP.Tags.Criticos;
import io.qameta.allure.Allure;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import pages.botonesgenericos.BotonesGenericosPage;
import steps.botonesgenericos.BotonesGenericosSteps;
import steps.comparacion_de_comisiones.ComparacionComisionesSteps;
import steps.login.LoginSteps;
import steps.menulateral.MenuLateralSteps;
import utils.UtilsGP;

import java.io.IOException;

import static db.ConexionDB.cerrarConexion;
import static utils.Constantes.uUattesthb14;

public class ComparacionComisionesTest {
    MenuLateralSteps menuLateralSteps = new MenuLateralSteps();
    LoginSteps loginSteps = new LoginSteps();
    ComparacionComisionesSteps comparacionComisionesSteps = new ComparacionComisionesSteps();
    BotonesGenericosSteps botonesGenericosSteps = new BotonesGenericosSteps();
    BotonesGenericosPage botonesGenericosPage = new BotonesGenericosPage();
    UtilsGP utilsGP = new UtilsGP();

    @Test
    @Category({Alta.class})
    @DisplayName("TC-0079-BERSA: Comparación de comisiones - POPUP - Redirecciomamiento")
    public void TC0079BERSA(){
        try {
            loginSteps.iniciarSessionAlEscritorio(uUattesthb14);
            menuLateralSteps.clickComparacionDeComision();
            comparacionComisionesSteps.validaPopupComparacionComisiones();
            comparacionComisionesSteps.validaTituloComparacionDeComisiones();
            comparacionComisionesSteps.clickAceptarGenerico();
            comparacionComisionesSteps.validaRedireccionBancoCentral();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @After
    public void cerrarSesionCapturarUPS(){
        if(utilsGP.estaPresente(botonesGenericosPage.getCodigoUps(),2) && botonesGenericosPage.getCodigoUps().getText().startsWith("#")) {
            String ups = botonesGenericosPage.getCodigoUps().getText();
            botonesGenericosSteps.clickVolverGenerico();
            loginSteps.clickSalirDelaSesion();
            loginSteps.waitGP();
            Assert.fail("UPS CODIGO : " + ups);
        }else if(utilsGP.estaPresente(botonesGenericosPage.getSalirSesion(),2))
        {
            loginSteps.clickSalirDelaSesion();
            loginSteps.waitGP();
        }else{
            System.out.println("=================== NO PUDO INICIAR SESION REFRESCAR NAVEGADOR ==============================");
            Allure.addAttachment("INICIO SESSION,","=================== NO PUDO INICIAR SESION REFRESCAR NAVEGADOR ==============================");
            global.practia.jemeinsamstark.web.core.WebDriver.getInstance().navigate().to(System.getProperty("bank"));
            System.out.println("Se Espera que aparezca la pagina de nuevo");
            loginSteps.waitGP();
        }
    }
    @AfterClass
    public static void teardown() throws IOException, InterruptedException {
        global.practia.jemeinsamstark.web.core.WebDriver.closeDriver();
        cerrarConexion();
    }
}
