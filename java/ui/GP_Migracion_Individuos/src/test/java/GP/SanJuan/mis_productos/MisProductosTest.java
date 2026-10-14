package GP.SanJuan.mis_productos;

import GP.Tags.Alta;
import io.qameta.allure.Allure;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import pages.botonesgenericos.BotonesGenericosPage;
import steps.administracion_de_productos.AdministracionDeProductosSteps;
import steps.baja_arrepentimiento.BajaArrepentimientoSteps;
import steps.botonesgenericos.BotonesGenericosSteps;
import steps.login.LoginSteps;
import steps.menulateral.MenuLateralSteps;
import utils.UtilsGP;

import java.io.IOException;

import static db.ConexionDB.cerrarConexion;
import static utils.Constantes.uSanjuan_135;
import static utils.Constantes.uTranspull4;

public class MisProductosTest {
    LoginSteps loginSteps = new LoginSteps();
    MenuLateralSteps menuLateralSteps = new MenuLateralSteps();
    AdministracionDeProductosSteps administracionDeProductosSteps=new AdministracionDeProductosSteps();
    BajaArrepentimientoSteps bajaArrepentimientoSteps = new BajaArrepentimientoSteps();
    BotonesGenericosPage botonesGenericosPage = new BotonesGenericosPage();
    BotonesGenericosSteps botonesGenericosSteps = new BotonesGenericosSteps();
    UtilsGP utilsGP = new UtilsGP();

    @Test
    @Category({Alta.class})
    @DisplayName("TC-0077-BSJ: Administracion de productos - Descarga ")
    public void TC0077BSJ() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uSanjuan_135);
            menuLateralSteps.clickContratos();
            menuLateralSteps.validarTituloPageGenerico("Administraci");
            administracionDeProductosSteps.clickDesplegableProducto();
            administracionDeProductosSteps.keyDownToText("Alta de Tarjeta de Crédito Sucursal");
            administracionDeProductosSteps.descargarArchivo();
            administracionDeProductosSteps.validarDescarga("Alta de Tarjeta de Crédito Sucursal .pdf");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Alta.class})
    @DisplayName("TC-0075-BSJ: Baja / Arrepentimiento - POPUP - Redirecciomamiento")
    public void TC0075BSJ(){
        try {
            String urlOnlineBanking = "https://www.bancosanjuan.com/baja-y-arrepentimiento";
            loginSteps.iniciarSessionAlEscritorio(uTranspull4);
            menuLateralSteps.clickBajaDeArrepentimiento();
            bajaArrepentimientoSteps.validaPopupBajaArrepentimiento();
            bajaArrepentimientoSteps.validarTituloBajaArrepentimiento();
            bajaArrepentimientoSteps.clickAceptarGenerico();
            bajaArrepentimientoSteps.validaRedireccionOnlineBanking(urlOnlineBanking);
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
