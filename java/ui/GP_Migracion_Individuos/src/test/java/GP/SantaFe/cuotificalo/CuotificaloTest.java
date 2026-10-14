package GP.SantaFe.cuotificalo;

import GP.Tags.Alta;
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
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

import static db.ConexionDB.cerrarConexion;
import static utils.Constantes.*;

public class CuotificaloTest {
    LoginSteps loginSteps = new LoginSteps();
    MenuLateralSteps menuLateralSteps = new MenuLateralSteps();
    CuentasSteps cuentasSteps = new CuentasSteps();
    CuotificaloSteps cuotificaloSteps = new CuotificaloSteps();
    WebDriverWait wait = new WebDriverWait(global.practia.jemeinsamstark.web.core.WebDriver.getInstance(), 10);
    DobleFactorSteps dobleFactorSteps = new DobleFactorSteps();

    BotonesGenericosSteps botonesGenericosSteps = new BotonesGenericosSteps();
    BotonesGenericosPage botonesGenericosPage = new BotonesGenericosPage();
    UtilsGP utilsGP = new UtilsGP();

    @Test
    @Category({Media.class})
    @DisplayName("TC-0085-BSF: Cuotificalo - Validar descarga Resumen de contrato")
    public void TC0085BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uPerezR);
            menuLateralSteps.clickCuentas();
            cuentasSteps.clickVerMas1erCuenta();
            cuotificaloSteps.clickCuotificalo();
            cuotificaloSteps.validarTituloPageGenerico("Cuotificalo");
            cuotificaloSteps.clickDescargarResumenContrato();
            cuotificaloSteps.validarDescarga("BSF RESU-27");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0089-BSF: Cuotifícalo - Cuotificar compras con TD")
    public void TC0089BSF() throws SQLException, InterruptedException, UnsupportedEncodingException {
        try {
            loginSteps.iniciarSessionAlEscritorio(uPerezR);
            menuLateralSteps.clickCuentas();
            cuentasSteps.clickVerMas1erCuenta();
            cuotificaloSteps.clickCuotificalo();
            cuotificaloSteps.validarTituloPageGenerico("Cuotificalo");
            cuotificaloSteps.clickEnPrimerosCuatroCheckboxes();
            cuotificaloSteps.clickContinuarGenerico();
            Thread.sleep(1000);
            cuotificaloSteps.clickOpcionPlazoDeDoceCuotas();
            cuotificaloSteps.clickCuentaAcreditacion();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='accreditationAccount']/parent::div//div[contains(text(), 'CA $ 000003554002')]")));
            cuotificaloSteps.keyDownXParametrosGenerico(0, "CA $ 001000090502");
            cuotificaloSteps.scrollTo(By.xpath("//input[@name='debitAccount']/preceding-sibling::div"));
            cuotificaloSteps.clickCuentaDebito();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='debitAccount']/parent::div//div[contains(text(), 'Caja de Ahorro $ 000003554002 ')]")));
            cuotificaloSteps.keyDownXParametrosGenerico(0, "Caja de Ahorro $ 000003554002 ");
            cuotificaloSteps.clickContinuarGenerico();
            cuotificaloSteps.scrollTo(By.xpath("//*[@name='agreement']/following-sibling::span"));
            cuotificaloSteps.clickCheckTerminosCondicionesGenerico();
            dobleFactorSteps.codigoSegundoFactor();
            TicketSteps ticketSteps = new TicketSteps();
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
