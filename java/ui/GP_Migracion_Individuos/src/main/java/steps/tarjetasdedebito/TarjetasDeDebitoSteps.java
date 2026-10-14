package steps.tarjetasdedebito;

import io.qameta.allure.Step;
import org.junit.Assert;
import pages.tarjetasdedebito.TarjetasDeDebitoPage;
import steps.botonesgenericos.BotonesGenericosSteps;

public class TarjetasDeDebitoSteps extends BotonesGenericosSteps {

    private TarjetasDeDebitoPage tarjetasDeDebitoPage = new TarjetasDeDebitoPage();

    @Step("Click en Seguimiento de tarjetas")
    public void clickSeguimientoDeTarjetas(){
        tarjetasDeDebitoPage.getSeguimientoDeTaretas().click();
    }
    @Step("Click en boton Ver mas/Activar")
    public void clickBotonVerMas_Activar(String ultimosCuatroNrosTarjeta){
        tarjetasDeDebitoPage.getBotonVerMas_Activar(ultimosCuatroNrosTarjeta).click();
    }

    @Step("Se valida pantalla de Activacion de tarjeta")
    public void validacionPantallaActivacionTarjeta(){
        Assert.assertTrue("Boton Confirmar no es visible", tarjetasDeDebitoPage.getBotonConfirmar().isDisplayed());
        Assert.assertTrue("Input Segundo factor no es visible", tarjetasDeDebitoPage.getInputSegundoFactor().isDisplayed());
    }
    @Step("Click en Consultar Compras")
    public void clickConsultarCompras(){
        tarjetasDeDebitoPage.getConsultarCompras().click();
    }


}
