package steps.pagos.debinvendedor;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import pages.pagos.debinvendedor.DebinVendedorPage;
import steps.botonesgenericos.BotonesGenericosSteps;

public class DebinVendedorSteps extends BotonesGenericosSteps {

    private DebinVendedorPage debinVendedor = new DebinVendedorPage();

    @Step("Click Generar Orden Debin")
    public void clickOrdenDebin(){
        debinVendedor.getGenerarOrdenDebin().click();
    }
    @Step("Click Consulta Adhesiones")
    public void clickConsultaAdhesiones(){ debinVendedor.getConsultaAdhesiones().click();
    }
    @Step("Click Cuenta Destino")
    public void clickCuentaDestinoDesplegable(){
        Assert.assertTrue("NO SE ENCONTRO EL DESPLEGABLE : "+debinVendedor.getCuentaDestinoDesplegable().getElementBy(),debinVendedor.getCuentaDestinoDesplegable().isDisplayed());
        debinVendedor.getCuentaDestinoDesplegable().click();
    }
    @Step("Seleccionar Cuenta Destino Si Esta Presente")
    public void seleccionarCuentaDestinoSiEstaPresente(){
        if(debinVendedor.getCuentaDestinoDesplegable().isDisplayed()){
            debinVendedor.getCuentaDestinoDesplegable().click();
            keyDownXParametrosGenerico(0, "");
            clickContinuarGenerico();
        }else{
            System.out.println("No está presente el selector de tarjeta de debito");
        }
    }


    @Step("Click CBU")
    public void clickCbu(){
        debinVendedor.getCbu().click();
    }
    @Step("Click ALIAS")
    public void clickAlias(){debinVendedor.getAlias().click();
    }
    @Step("Setear Cbu y Alias Comprador")
    public void setCbuOAlias(String cbu_alias){ debinVendedor.getCbuYAliasComprador().sendKeys(cbu_alias);
    }
    @Step("Click Buscar")
    public void clickBuscar(){
        debinVendedor.getBuscar().click();
    }
    @Step("Click Concepto Desplegable")
    public void clickConcepto(){
        scrollTo(By.id("conceptSelector"));
        debinVendedor.getConceptoDesplegable().click();
    }
    @Step("Setear Referencia")
    public void setReferencia(String referencia){debinVendedor.getReferencia().sendKeys(referencia);
    }
    @Step("Click Vigencia Desplegable")
    public void clickVigencia(){
        debinVendedor.getVigencia().click();
    }
    @Step("Click Nombre Desplegable")
    public void clickNombre(){
        debinVendedor.getNombreDesplegable().click();
    }

}
