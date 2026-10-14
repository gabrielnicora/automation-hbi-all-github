package steps.inversiones.cuentacomitente;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.inversiones.cuentacomitente.CuentaComitentePage;
import steps.botonesgenericos.BotonesGenericosSteps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CuentaComitenteSteps extends BotonesGenericosSteps {
    CuentaComitentePage cuentaComitentePage = new CuentaComitentePage();

    @Step("Validacion de texto")
    public void validarTexto(String text) {
        cuentaComitentePage.getText(text).isDisplayed();
    }

    @Step("Validar boton de solicitar cuenta")
    public void validarBotonSolicitarCuenta() {
        cuentaComitentePage.getSolicitarCuenta().isDisplayed();
        cuentaComitentePage.getSolicitarCuenta().isEnabled();
    }

    @Step("Click boton de solicitar cuenta")
    public void clickSolicitarCuenta() {
        Assert.assertTrue("NO SE ENCONTRO EL BOTON : "+cuentaComitentePage.getSolicitarCuenta().getElementBy(),cuentaComitentePage.getSolicitarCuenta().isDisplayed());
        cuentaComitentePage.getSolicitarCuenta().click();
    }

    @Step("Validar numero y texto de etapa")
    public void validarNumyTextDeEtapa(String numeroEtapa, String textEtapa) {
        cuentaComitentePage.getNumeroDePaso(numeroEtapa).isDisplayed();
        cuentaComitentePage.getTituloDePaso(textEtapa).isDisplayed();
    }

    @Step("Validar campos de informacion personal del usuario")
    public void validacionCamposInfoPersonal() {
        List<String> campos = Arrays.asList(
                "Apellido y Nombres",
                "DNI",
                "Nacionalidad",
                "s de Nacimiento",
                "n IVA",
                "Correo",
                "Domicilio"
        );

        for (String campo : campos) {
            scrollTo(By.xpath("//*[contains(text(), '" + campo + "')]"));
            Assert.assertTrue("El campo '" + campo + "' no está visible", cuentaComitentePage.getText(campo).isDisplayed());
        }

        System.out.println("Titulos validados con exito");
    }

    @Step("ClickCheckbox Ninguna De Las Anteriores")
    public void clickCheckboxNingunaDeLasAnteriores() {
        scrollTo(cuentaComitentePage.getCheckboxNingunaDeLasAnteriores().getElementBy());
        cuentaComitentePage.getCheckboxNingunaDeLasAnteriores().isDisplayed();
        cuentaComitentePage.getCheckboxNingunaDeLasAnteriores().click();
    }

    @Step("Validar campos de informacion personal del usuario")
    public void validacionPreguntas() {
        List<String> campos = Arrays.asList(
                "dica registrada por la CNV",
                "Persona humana inscripta en el registro de id",
                "Inversiones en valores negociables o dep"
        );

        for (String campo : campos) {
            scrollTo(By.xpath("//*[contains(text(), '" + campo + "')]"));
            Assert.assertTrue("El campo '" + campo + "' no está visible", cuentaComitentePage.getText(campo).isDisplayed());
        }

        System.out.println("Titulos validados con éxito");
    }

    @Step("Click en las respuestas 'NO' del Formulario Declaracion Jurada")
    public void clickRadioFormDeclaracionJurada() {
        int cont = 1;
        while (cont < 4) {
            cuentaComitentePage.getRadioFormDeclaracionJurada(cont).click();
            scrollTo(By.xpath("(//label[contains(@for,'no')]/*)" + "[" + cont + "]"));
            cont++;
            System.out.println("Se hizo click en todos las respuestas 'no'");
        }
    }
    @Step("click check box terminos y condiciones")
    public void clickCheckboxTerminosyCondiciones() {
        //scrollTo(cuentaComitentePage.getCheckTerminosyCondiciones().getElementBy());
        scrollTo(By.xpath("//span[contains(text(),'Términos y Condiciones')]/../.."));
        cuentaComitentePage.getCheckTerminosyCondiciones().isDisplayed();
        cuentaComitentePage.getCheckTerminosyCondiciones().click();
    }
    @Step("Click test perfil inversor")
    public void clickTestPerfilInversor() {
        cuentaComitentePage.getTextTestPerfilDeInversor().isDisplayed();
        cuentaComitentePage.getTextTestPerfilDeInversor().click();
    }
    @Step("Click test perfil inversor")
    public void validarBotonInhabilitado() {
        Assert.assertTrue(cuentaComitentePage.getBotonContinuarInhabilitado().isDisplayed());
    }
    @Step("Completa Formulario de perfil de inversor respuesta : {opcion}")
    public void seleccionOpcionPerfilDeInversor(String opcion){
        scrollTo(cuentaComitentePage.getOpcionPerfilDelInversor(opcion).getElementBy());
        cuentaComitentePage.getOpcionPerfilDelInversor(opcion).click();
    }

}
