package steps.validaciondeidentidad;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pages.validaciondeidentidad.ValidacionDeIdentidadPage;
import steps.botonesgenericos.BotonesGenericosSteps;

public class ValidacionDeIdentidadSteps extends BotonesGenericosSteps {
    private ValidacionDeIdentidadPage validacionDeIdentidadPage = new ValidacionDeIdentidadPage();

    @Step("Ingresar ultimos digitos de tarjeta")
    public void setDigitosTarjeta(String digitos){
        validacionDeIdentidadPage.getDigitos().sendKeys(digitos);
    }
    @Step("Click input PIN")
    public void clickPin(){
        validacionDeIdentidadPage.getPin().click();
    }
    @Step("Click en boton numero {nroBoton}")
    public void setPinPorNro(String nroBoton){
        validacionDeIdentidadPage.getBotonTableroPinPorNro(nroBoton).click();
    }

    @Step("Validacion de identidad")
    public void validacionIdentidad(String digitosTarjeta, String pin1, String pin2, String pin3, String pin4) {
        if(existePantallaValidacion()){
            setDigitosTarjeta(digitosTarjeta);
            clickPin();
            setPinPorNro(pin1);
            setPinPorNro(pin2);
            setPinPorNro(pin3);
            setPinPorNro(pin4);
            clickContinuarGenerico();
        }else {
            System.out.println("NO ESTA LA PANTALLA DE VALIDACION");
        }
    }
    public Boolean existePantallaValidacion() {
        return validacionDeIdentidadPage.getDigitos().isDisplayed();
    }
}
