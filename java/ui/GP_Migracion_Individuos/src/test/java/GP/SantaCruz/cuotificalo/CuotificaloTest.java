package GP.SantaCruz.cuotificalo;

import GP.Tags.Baja;
import GP.Tags.Criticos;
import GP.Tags.Media;
import io.qameta.allure.Allure;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.botonesgenericos.BotonesGenericosPage;
import steps.botonesgenericos.BotonesGenericosSteps;
import steps.cuentas.CuentasSteps;
import steps.cuentas.cuotificalo.CuotificaloSteps;
import steps.doblefactor.DobleFactorSteps;
import steps.login.LoginSteps;
import steps.menulateral.MenuLateralSteps;
import steps.ticket.TicketSteps;
import utils.UtilsGP;

import java.io.IOException;

import static db.ConexionDB.cerrarConexion;
import static utils.Constantes.*;

public class CuotificaloTest {
    LoginSteps loginSteps = new LoginSteps();
    MenuLateralSteps menuLateralSteps = new MenuLateralSteps();
    CuentasSteps cuentasSteps= new CuentasSteps();
    CuotificaloSteps cuotificaloSteps= new CuotificaloSteps();
    DobleFactorSteps dobleFactorSteps = new DobleFactorSteps();
    TicketSteps ticketSteps= new TicketSteps();
    BotonesGenericosPage botonesGenericosPage = new BotonesGenericosPage();
    BotonesGenericosSteps botonesGenericosSteps = new BotonesGenericosSteps();
    UtilsGP utilsGP = new UtilsGP();


    @Test
    @Category({Media.class})
    @DisplayName("TC-0085-BSC: Cuotificalo - Validar descarga Resumen de contrato")
    public void TC0085BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(u24295762);
            menuLateralSteps.clickCuentas();
            menuLateralSteps.validarTituloPageGenerico("Cuentas");
            cuentasSteps.clickVerMas1erCuenta();
            cuotificaloSteps.clickCuotificalo();
            cuotificaloSteps.validarTituloPageGenerico("Cuotificalo");
            cuotificaloSteps.clickDescargarResumenContrato();
            cuotificaloSteps.validarDescarga("BSC - RESU-27");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0089-BSC: Cuotificalo - Cuotificar compras con TD")
    public void TC0089BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(u10027480);
            menuLateralSteps.clickCuentas();
            cuentasSteps.clickVerMas1erCuenta();
            cuotificaloSteps.clickCuotificalo();
            cuotificaloSteps.validarTituloPageGenerico("Cuotificalo");
            cuotificaloSteps.clickEnPrimerosCuatroCheckboxes();
            cuotificaloSteps.clickContinuarGenerico();
            cuotificaloSteps.clickOpcionPlazoDeDoceCuotas();
            cuotificaloSteps.clickCuentaAcreditacion();
            cuotificaloSteps.keyDownToText( "5269");
            cuotificaloSteps.clickCuentaDebito();
            cuotificaloSteps.keyDownToText("Caja de Ahorro $ 012325269 ");
            cuotificaloSteps.clickContinuarGenerico();
            cuotificaloSteps.clickCheckTerminosCondicionesGenerico();
            dobleFactorSteps.codigoSegundoFactorOtroMetodo();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
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

