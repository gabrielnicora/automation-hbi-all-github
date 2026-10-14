package steps.cuentas.cuentas_asociadas;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.cuentas_asociadas.CuentasAsociadasPage;
import steps.botonesgenericos.BotonesGenericosSteps;
import steps.doblefactor.DobleFactorSteps;
import utils.UtilsGP;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class CuentasAsociadasSteps extends BotonesGenericosSteps {

    private CuentasAsociadasPage cuentasAsociadasPage = new CuentasAsociadasPage();
    UtilsGP utilsGP = new UtilsGP();
    @Step("Click en Como funciona?")
    public void clickEnComoFunciona() {
        scrollTo(By.xpath("//span[contains(text(), 'mo funciona?')]"));
        cuentasAsociadasPage.getComoFunciona().click();
    }

    @Step("Validar pantalla ¿Como funciona?")
    public void validacionCamposComoFunciona() {
        List<String> campos = Arrays.asList(
                "Ingresar dinero",
                "¿Qué es?",
                "¿Qué beneficios tiene?",
                "Importante"
        );

        for (String campo : campos) {
            scrollTo(By.xpath(String.format("//*[contains(text(), '%s')]", campo)));
            Assert.assertTrue("El campo '" + campo + "' no esta visible", cuentasAsociadasPage.textos(campo).isDisplayed());
        }

        System.out.println("Titulos validados con exito");
    }

    @Step("Click en Vincular una Cuenta")
    public void clickVincularUnaCuenta() {
        cuentasAsociadasPage.getVincularUnaCuenta().click();
    }

    @Step("Click en Continuar de pagina externa")
    public void botonContinuarPagExterna() throws InterruptedException {
        cuentasAsociadasPage.getContinuarPaginaExterna().click();
        Thread.sleep(1500);
    }

    @Step("Seleccionar BANCO a asociar")
    public void seleccionarBanco(String banco) {
        cuentasAsociadasPage.getInputBuscar().sendKeys(banco);
        cuentasAsociadasPage.getSeleccionBanco(banco).click();
    }

    @Step("Completar usuario")
    public void setUsuario(String usuario) {
        cuentasAsociadasPage.getInputUserName().sendKeys(usuario);
    }

    @Step("Completar contrasenia")
    public void setContrasenia(String contrasenia) {
        cuentasAsociadasPage.getInputPassword().sendKeys(contrasenia);
    }

    @Step("Completar tarjeta de debito")
    public void setTarjetaDebito(String TD) {
        cuentasAsociadasPage.getInputTD().sendKeys(TD);
    }

    @Step("Click boton iniciar sesion")
    public void clickBotonIniciarSesion() {
        cuentasAsociadasPage.getBotonIniciarSesion().click();
    }

    @Step("Click primer checkbox cuenta a vincular")
    public void seleccionarPrimerCuentaChekbox() {
        cuentasAsociadasPage.getPrimerCuentaAVincular().click();
    }

    @Step("Click checkbox terminos y condiciones")
    public void checkboxTerminosYCondiciones() {
        cuentasAsociadasPage.getCheckboxTerminosyCondiciones().click();
    }

    @Step("Click desplegable entidades asociadas")
    public void clickDesplegableEntidadesAsociadas() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@aria-label='Expandir']/..")));
        cuentasAsociadasPage.getDesplegableEntidadesAsociadas().click();
    }

    @Step("Validacion entidad asociada")
    public void validarEntidadAsociada(String entidadAsociada) {
        Assert.assertTrue("La entidadv" + entidadAsociada + "vno se encuentra asociada", cuentasAsociadasPage.getEntidadAsociada(entidadAsociada).isDisplayed());
    }

    @Step("Click desplegable Cuentas Asociadas en otras entidades")
    public void desplegableCuentasAsociadasEnOtrasEntidades() {
        cuentasAsociadasPage.getDesplegableCuentasAsociadasEnOtrasEntidades().click();
    }

    @Step("Abrir nueva pestania con banco")
    public void abrirPestaniaBanco(String urlBanco) throws InterruptedException {
        utilsGP.NuevoTab();
        utilsGP.ManageTabPorParametro(2);
        utilsGP.NavegarUtilsWeb(urlBanco);

    }

    @Step("Desasociar entidad asociada")
    public void desasociarEntidadAsociada(String entidadAsociada) {
        cuentasAsociadasPage.getEntidadAsociada(entidadAsociada).click();
        cuentasAsociadasPage.botonDesasociarEntidad().click();
        cuentasAsociadasPage.botonConfirmarDesasociarEntidad().click();
    }

    @Step("Validar Mensaje Cuenta Desasociada")
    public void validarMensajeCuentaDesasociada() {
        List<String> campos = Arrays.asList(
                "Listo",
                "Tus cuentas",
                "fueron desasociadas");
        for (String campo : campos) {
            String campo1 = new String(campo.getBytes(),
                    StandardCharsets.UTF_8);
            scrollTo(By.xpath(String.format("//*[contains(text(), '%s')]",campo1)));
            Assert.assertTrue("El campo '" + campo1 + "' no esta visible", cuentasAsociadasPage.textos(campo1).isDisplayed());
        }
        System.out.println("Titulos validados con exito");
    }
    @Step("click en entidad asociada")
    public void clickCuentaAsociada(String cuentaAsociada) {
        cuentasAsociadasPage.getEntidadAsociada(cuentaAsociada).click();

    }
}
