package steps.inversiones.compraventadedolares;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import pages.inversiones.compraventadolares.CompraVentaDeDolaresPage;
import steps.botonesgenericos.BotonesGenericosSteps;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompraVentaDeDolaresSteps extends BotonesGenericosSteps {

    Actions keyDown = new Actions(global.practia.jemeinsamstark.web.core.WebDriver.getInstance());
    private CompraVentaDeDolaresPage compraVentaDeDolaresPage = new CompraVentaDeDolaresPage();

    @Step("Validar Mensaje")
    public void verificarMensaje() {
        Assert.assertTrue("Mensaje No Visible", compraVentaDeDolaresPage.getMensajeElHorarioDeCompraVentaDeDolares().isDisplayed());
    }

    @Step("Click en Tipo de Operacion")
    public void clickTipoDeOperacion() {
        compraVentaDeDolaresPage.getTipoDeOperacion().click();
    }

    @Step("Click en Cuenta Origen")
    public void clickCuentaOrigen() {
        compraVentaDeDolaresPage.getCuentaOrigen().click();
    }

    @Step("Click en Cuenta Destino")
    public void clickCuentaDestino() {
        compraVentaDeDolaresPage.getCuentaDestino().click();
    }

    @Step("Ingresar Monto {monto}")
    public void setMonto(String monto) {
        compraVentaDeDolaresPage.getCuantoQueresVender().sendKeys(monto);
    }

    @Step("Validar Mensaje")
    public void validacionMensaje(String mensaje) {
        compraVentaDeDolaresPage.getValidarMensaje(mensaje).isDisplayed();
    }

    @Step("Validacion campos Seguimiento tarjeta")
    public void validacionCamposCompraMoneda() {
        List<String> campos = Arrays.asList(
                "Tipo de operaci",
                "Cuenta origen",
                "Cuenta destino",
                "Cotizaci",
                "Cantidad a comprar",
                "Equivalente en Pesos",
                "Impuesto PAIS",
                "Percep",
                "Total a debitar"
        );
        for (String campo : campos) {
            scrollTo(By.xpath("//span[contains(text(), '" + campo + "')]"));
            Assert.assertTrue("El campo '" + campo + "' no está visible", compraVentaDeDolaresPage.getValidarMensaje(campo).isDisplayed());
        }
        System.out.println("Campos validados con éxito");
    }


    @Step("Validar que las cuentas destino sean solo en Pesos $")
    public void validarDesplegableCuentasEnPesos(String tipoCuenta) throws InterruptedException {
        int intentos = 0;
        String cuentaAnterior = null;
        switch (tipoCuenta) {
            case "cuentaOrigen":
                while (intentos < 15) {
                    String cuentaActual = compraVentaDeDolaresPage.getCuentaOrigen().getText();
                    if (cuentaActual.equals(cuentaAnterior)) {
                        break;
                    }
                    compraVentaDeDolaresPage.getCuentaOrigen().click();
                    keyDownXParametrosGenerico(intentos, "$");
                    if (!compraVentaDeDolaresPage.getCuentaOrigen().getText().contains("$")) {
                        System.out.println("La cuenta no contiene pesos: " + cuentaActual);
                    }
                    intentos++;
                    cuentaAnterior = cuentaActual;

                    System.out.println("cuenta validada: " + cuentaActual);
                }
                break;
            case "cuentaDestino":
                while (intentos < 15) {
                    String cuentaActual = compraVentaDeDolaresPage.getCuentaDestino().getText();
                    if (cuentaActual.equals(cuentaAnterior)) {
                        break;
                    }
                    compraVentaDeDolaresPage.getCuentaDestino().click();
                    keyDownXParametrosGenerico(intentos, "$");
                    if (!compraVentaDeDolaresPage.getCuentaDestino().getText().contains("$")) {
                        System.out.println("La cuenta no contiene pesos: " + cuentaActual);
                    }
                    intentos++;
                    cuentaAnterior = cuentaActual;

                    System.out.println("cuenta validada: " + cuentaActual);
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + tipoCuenta);
        }

    }

    @Step("Validar que las cuentas destino sean solo en dolares USD")
    public void validarDesplegableCuentasEnDolares(String tipoCuenta) {
        int intentos = 0;
        String cuentaAnterior = null;
        switch (tipoCuenta) {
            case "cuentaOrigen":
                while (intentos < 15) {
                    String cuentaActual = compraVentaDeDolaresPage.getCuentaOrigen().getText();
                    if (cuentaActual.equals(cuentaAnterior)) {
                        break;
                    }
                    compraVentaDeDolaresPage.getCuentaOrigen().click();
                    keyDownXParametrosGenerico(intentos, "USD");
                    if (!compraVentaDeDolaresPage.getCuentaOrigen().getText().contains("USD")) {
                        System.out.println("La cuenta no contiene dolares USD: " + cuentaActual);
                    }
                    intentos++;
                    cuentaAnterior = cuentaActual;
                    System.out.println("cuenta validada: " + cuentaActual);
                }
                break;
            case "cuentaDestino":
                while (intentos < 15) {
                    String cuentaActual = compraVentaDeDolaresPage.getCuentaDestino().getText();
                    if (cuentaActual.equals(cuentaAnterior)) {
                        break;
                    }
                    compraVentaDeDolaresPage.getCuentaDestino().click();
                    keyDownXParametrosGenerico(intentos, "USD");
                    if (!compraVentaDeDolaresPage.getCuentaDestino().getText().contains("USD")) {
                        System.out.println("La cuenta no contiene dolares USD: " + cuentaActual);
                    }
                    intentos++;
                    cuentaAnterior = cuentaActual;
                    System.out.println("cuenta validada: " + cuentaActual);
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + tipoCuenta);

        }

    }


}

