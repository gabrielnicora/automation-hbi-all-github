package steps.tarjetasdedebito.consultacompras;

import io.qameta.allure.Step;
import org.junit.Assert;
import pages.tarjetasdedebito.consultacompras.ConsultaDeComprasPage;
import steps.botonesgenericos.BotonesGenericosSteps;

public class ConsultaDeComprasSteps extends BotonesGenericosSteps {
    ConsultaDeComprasPage consultaDeComprasPage= new ConsultaDeComprasPage();

    @Step("Click en Desplegable Cuenta")
    public void clickDesplegableCuenta(){
        consultaDeComprasPage.getDesplegableCuenta().click();
    }
    @Step("Click en Fecha Desde")
    public void clickFechaDesde(){
        consultaDeComprasPage.getFechaDesde().click();
    }
    @Step("Validar tabla de Consultas de Compras")
    public void validarTablaConsultaCompras(){
        Assert.assertTrue("No se encuenta historial de Consultar de Compras", consultaDeComprasPage.getTablaConsultaCompras().isDisplayed());
    }
}
