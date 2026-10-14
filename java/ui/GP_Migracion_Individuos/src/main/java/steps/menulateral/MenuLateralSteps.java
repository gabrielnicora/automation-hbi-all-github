package steps.menulateral;


import io.qameta.allure.Step;
import org.codehaus.jettison.json.JSONException;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import pages.botonesgenericos.BotonesGenericosPage;
import pages.menulateral.MenuLateralPage;
import pages.transferencias.transferenciasinmediatas.TransferenciasInmediatasPage;
import steps.botonesgenericos.BotonesGenericosSteps;
import steps.login.LoginSteps;
import steps.transferencias.generarcredin.GenerarCredinSteps;
import steps.transferencias.transferenciasinmediatas_programadas.TransferenciasInmediatasSteps;
import utils.MetodosAPI;
import utils.UtilsGP;

import java.awt.*;

public class MenuLateralSteps extends BotonesGenericosSteps {

    private MenuLateralPage MenuLateral = new MenuLateralPage();
    private LoginSteps loginSteps = new LoginSteps();
    private UtilsGP utilsGP = new UtilsGP();
    private TransferenciasInmediatasPage transferenciasInmediatasPage = new TransferenciasInmediatasPage();
    private MetodosAPI metodosAPI = new MetodosAPI();
    BotonesGenericosPage botonesGenericosPage = new BotonesGenericosPage();

    TransferenciasInmediatasSteps transferenciasInmediatas = new TransferenciasInmediatasSteps();

    GenerarCredinSteps generarCredinSteps = new GenerarCredinSteps();



    @Step("Click en Inicio")
    public void clickInicio(){
        MenuLateral.getInicio().click();
        //loginSteps.clickSalirPrestamo();
        //loginSteps.clickSacarPublicidad();
    }
    @Step("Click en Cuentas")
    public void clickCuentas() {
        MenuLateral.getCuentas().sendKeys(Keys.RETURN);
        //loginSteps.clickSalirPrestamo();
    }
    @Step("Click en Tarjeta de Credito")
    public void clickTarjetaDeCredito() {
        MenuLateral.getTarjetas().sendKeys(Keys.RETURN);
        MenuLateral.getTarjetaDeCredito().sendKeys(Keys.RETURN);
        //loginSteps.clickSalirPrestamo();
    }
    @Step("Click en Tarjeta de Debito")
    public void clickTarjetaDeDebito() {
        MenuLateral.getTarjetas().sendKeys(Keys.RETURN);
        MenuLateral.getTarjetaDeDebito().sendKeys(Keys.RETURN);
        //loginSteps.clickSalirPrestamo();
    }
    @Step("Click en Prestamos")
    public void clickPrestamos() {
        MenuLateral.getPrestamos().click();
        //loginSteps.clickSalirPrestamo();
    }
    @Step("Click en Transferencias")
    public void clickTransferencias(String NumTarjeta,String NomCuenta) throws JSONException, AWTException, InterruptedException {
        MenuLateral.getTransferencias().click();
        //loginSteps.clickSalirPrestamo();

        if(!NumTarjeta.equals("")) {transferenciasInmediatas.seleecionarTarjeaAOperar(NumTarjeta);}
        if(!NomCuenta.equals("")){transferenciasInmediatas.seleccionarCuentaOrigen(NomCuenta);}



    }
    @Step("Click sólo Transferencias")
    public void clickSoloTransferencias() {
        MenuLateral.getTransferencias().click();
    }
    @Step("Click en Pagos")
    public void clickPagos() {
        UtilsGP.fixedWait(3000);
        MenuLateral.getPagosYServicios().sendKeys(Keys.RETURN);
        MenuLateral.getPagos().sendKeys(Keys.RETURN);
        try {
            if(botonesGenericosPage.getMensajeEmergente().isDisplayed()){
                botonesGenericosPage.getMensajeEmergente().click();
                System.out.println("Apareció una notificación de error");
            }
        } catch (TimeoutException e) {
            System.out.println("No se encuentra ninguna notificacion presente");
        }
    }
    @Step("Click en Bonos de Salud")
    public void clickBonosDeSalud() {
        MenuLateral.getBonosDeSalud().sendKeys(Keys.RETURN);
        //loginSteps.clickSalirPrestamo();
    }
    @Step("Click en Recargas")
    public void clickRecargas() {
        MenuLateral.getPagosYServicios().sendKeys(Keys.RETURN);
        MenuLateral.getRecargas().sendKeys(Keys.RETURN);
        //loginSteps.clickSalirPrestamo();
    }
    @Step("Click en Inversiones")
    public void clickInversiones() {
        UtilsGP.fixedWait(6000);
        MenuLateral.getInversiones().sendKeys(Keys.RETURN);
        //loginSteps.clickSalirPrestamo();
    }
    @Step("Click en Seguros")
    public void clickSeguros() {
        this.scrollTo(By.xpath("//p[contains(text(), 'Seguros')]//ancestor::button"));
        MenuLateral.getSeguros().sendKeys(Keys.RETURN);
        //loginSteps.clickSalirPrestamo();
    }
    @Step("Click en Baja de Arrepentimiento")
    public void clickBajaDeArrepentimiento() {
        MenuLateral.getMisProductos().sendKeys(Keys.RETURN);
        MenuLateral.getBaja_Arrepentimiento().sendKeys(Keys.RETURN);
        //loginSteps.clickSalirPrestamo();
    }
    @Step("Click en Mis Tramites")
    public void clickMisTramites() {
        MenuLateral.getTramites().sendKeys(Keys.RETURN);
        MenuLateral.getMisTramites().sendKeys(Keys.RETURN);
        //loginSteps.clickSalirPrestamo();
    }
    @Step("Click en Fe De Vida")
    public void clickFeDeVida() {
        MenuLateral.getFeDeVida().sendKeys(Keys.RETURN);
        //loginSteps.clickSalirPrestamo();
    }
    @Step("Click en Turnos Online")
    public void clickTurnosOnline() {
        Assert.assertTrue("No se encopntro el xpath tramites",MenuLateral.getTramites().isDisplayed());
        scrollTo(MenuLateral.getTramites().getElementBy());
        MenuLateral.getTramites().sendKeys(Keys.RETURN);
        Assert.assertTrue("No se encontro el boton Turnos Online", MenuLateral.getTurnosOnline().isDisplayed());
        MenuLateral.getTurnosOnline().sendKeys(Keys.RETURN);
        //loginSteps.clickSalirPrestamo();
    }
    @Step("Click en Comparacion De Comision")
    public void clickComparacionDeComision() {
        MenuLateral.getComparacionDeComisiones().sendKeys(Keys.RETURN);
        //loginSteps.clickSalirPrestamo();
    }
    @Step("Click en Colocacion A Plazos Fijos")
    public void clickColocacionAPlazosFijos() {
        MenuLateral.getColocacionesAPlazo().sendKeys(Keys.RETURN);
        //loginSteps.clickSalirPrestamo();
    }
    @Step("Click en Contratos")
    public void clickContratos() {
        MenuLateral.getMisProductos().sendKeys(Keys.RETURN);
        MenuLateral.getContratos().sendKeys(Keys.RETURN);
        //loginSteps.clickSalirPrestamo();
    }
    @Step("Click en Cuotificalo")
    public void clickCuotificalo() {
        MenuLateral.getCuotificalo().sendKeys(Keys.RETURN);
        //loginSteps.clickSalirPrestamo();
    }




}
