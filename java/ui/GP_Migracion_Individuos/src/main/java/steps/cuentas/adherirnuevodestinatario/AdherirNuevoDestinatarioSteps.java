package steps.cuentas.adherirnuevodestinatario;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import pages.cuentas.adherirnuevodestinatario.AdherirNuevoDestinatarioPage;
import steps.botonesgenericos.BotonesGenericosSteps;

public class AdherirNuevoDestinatarioSteps extends BotonesGenericosSteps {
    AdherirNuevoDestinatarioPage adherirNuevoDestinatarioPage = new AdherirNuevoDestinatarioPage();

    @Step("Click radio button CBU")
    public void clickCBU(){
        adherirNuevoDestinatarioPage.getCBU().click();
    }
    @Step("Click radio button Alias")
    public void clickAlias(){
        adherirNuevoDestinatarioPage.getAlias().click();
    }
    @Step("Ingresar CBU/CVU o Alias: {cbuAlias}")
    public void setCbuAlias(String cbuAlias){
        adherirNuevoDestinatarioPage.getInputCbuCvuAlias().sendKeys(cbuAlias);
    }
    @Step("Click boton Buscar")
    public void clickBotonBuscar(){
        adherirNuevoDestinatarioPage.getBotonBuscar().click();
    }
    @Step("Ingresar Pertenece A")
    public void setPerteneceA(String perteneceA){
        adherirNuevoDestinatarioPage.getInputPerteneceA().sendKeys(perteneceA);
    }
    @Step("Ingresar Email")
    public void setEmail(String email){
        adherirNuevoDestinatarioPage.getInputEmail().sendKeys(email);
    }
    @Step("Click checkbox Es cuenta propia")
    public void clickCheckEsCuentaPropia(){
        adherirNuevoDestinatarioPage.getCheckboxEsCuentaPropia().click();
    }
    @Step("Ingresar Codigo de seguridad")
    public void setCodigoSeguridad(String codigo){
        scrollTo(By.name("securityNumber"));
        adherirNuevoDestinatarioPage.getInputCodigoDeSeguridad().sendKeys(codigo);
    }
    @Step("Ingresar Mes de vencimiento")
    public void setMesVencimiento(String mes){
        adherirNuevoDestinatarioPage.getInputMes().sendKeys(mes);
    }
    @Step("Ingresar Año de vencimiento")
    public void setAnioVencimiento(String anio){
        adherirNuevoDestinatarioPage.getInputAnio().sendKeys(anio);
    }
}
