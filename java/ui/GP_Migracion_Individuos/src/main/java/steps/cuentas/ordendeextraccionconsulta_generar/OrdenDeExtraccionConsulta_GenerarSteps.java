package steps.cuentas.ordendeextraccionconsulta_generar;

import io.qameta.allure.Step;
import org.codehaus.jettison.json.JSONException;
import org.openqa.selenium.By;
import pages.cuentas.ordendeextraccionconsulta_generar.OrdenDeExtraccionConsulta_GenerarPage;
import steps.botonesgenericos.BotonesGenericosSteps;
import utils.MetodosAPI;
import utils.UtilsGP;

import java.awt.*;
import java.sql.SQLOutput;

public class OrdenDeExtraccionConsulta_GenerarSteps extends BotonesGenericosSteps {
    private OrdenDeExtraccionConsulta_GenerarPage ordenDeExtraccionConsultaGenerarPage = new OrdenDeExtraccionConsulta_GenerarPage();
    private UtilsGP utilsGP = new UtilsGP();
    private MetodosAPI metodosAPI = new MetodosAPI();

    @Step("Click Cuenta Origen desplegable")
    public void clickCuentaOrigen(){
        ordenDeExtraccionConsultaGenerarPage.getCuentaOrigenDesplegable().click();
    }
    @Step("Click Fecha Desde")
    public void clickFechaDesde(){
        ordenDeExtraccionConsultaGenerarPage.getFechaDesde().click();
    }
    @Step("Click Fecha Hasta")
    public void clickFechaHasta(){
        ordenDeExtraccionConsultaGenerarPage.getFechaHasta().click();
    }
    @Step("Click DNI desplegable")
    public void clickDniDesplegable(){
        ordenDeExtraccionConsultaGenerarPage.getDniDesplegable().click();
    }
    @Step("Se ingresa DNI {dni}")
    public void setDni(String dni){
        ordenDeExtraccionConsultaGenerarPage.getInputDni().sendKeys(dni);
    }
    @Step("Se ingresa Referencia {referencia}")
    public void setReferencia(String referencia){
        ordenDeExtraccionConsultaGenerarPage.getInputReferencia().sendKeys(referencia);
    }
    @Step("Click boton Buscar")
    public void clickBotonBuscar(){
        scrollTo(By.id("filter.button.search.label"));
        ordenDeExtraccionConsultaGenerarPage.getBotonBuscar().click();
    }
    @Step("Click en Generar orden de extraccion")
    public void clickGenerarOrdenDeExtraccion(){
        ordenDeExtraccionConsultaGenerarPage.getGenerarOrdenDeExtraccion().click();
    }
    @Step("Click boton Agregar")
    public void clickBotonAgregar(){
        scrollTo(By.xpath("//button[@aria-label='Agregar']"));
        ordenDeExtraccionConsultaGenerarPage.getBotonAgregar().click();
    }
    @Step("Click icono comprobante")
    public void clickIconoComprobante(){
        ordenDeExtraccionConsultaGenerarPage.getIconoComprobante().isDisplayed();
        ordenDeExtraccionConsultaGenerarPage.getIconoComprobante().click();
    }
    @Step("Click Cuenta Origen desplegable si Existe y elige una cuenta con saldo")
    public void clickCuentaOrigenConSaldoSiExiste(String moneda) throws JSONException, AWTException {
        if(utilsGP.estaPresente(ordenDeExtraccionConsultaGenerarPage.getCuentaOrigenDesplegable(),2)){
            ordenDeExtraccionConsultaGenerarPage.getCuentaOrigenDesplegable().click();
            String cuenta = metodosAPI.extraerCuentaConMonto(moneda);
            keyDownToText(cuenta);
        }else{
            System.out.println("No se Encontró Desplegable cuenta Origen");
        }
    }
}
