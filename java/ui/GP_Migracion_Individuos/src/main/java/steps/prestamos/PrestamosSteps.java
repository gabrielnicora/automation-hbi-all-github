package steps.prestamos;
import io.qameta.allure.Step;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.junit.Assert;
import org.openqa.selenium.By;
import pages.prestamos.PrestamosPage;
import steps.botonesgenericos.BotonesGenericosSteps;
import utils.UtilsGP;
import java.awt.*;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class PrestamosSteps extends BotonesGenericosSteps {
    UtilsGP utils = new UtilsGP();
    PrestamosPage prestamosPage = new PrestamosPage();

    @Step("Click card Nuevo Prestamo")
    public void clickNuevoPrestamo() {
        Assert.assertTrue("NO SE ENCONTRO EL BOTON :"+prestamosPage.getNuevoPrestamo().getElementBy(),prestamosPage.getNuevoPrestamo().isDisplayed());
        prestamosPage.getNuevoPrestamo().click();
    }

    @Step("Click card Prestamo para cancelar deudas")
    public void clickPrestamoParaCancelarDeudas() {
        prestamosPage.getPrestamoParaCancelarDeudas().click();
    }

    @Step("Validacion de titulo de etapa de solicitud prestamo")
    public void validarTituloEtapa(String tituloEtapa) {
        prestamosPage.getValidarEtapa(tituloEtapa).isDisplayed();
    }

    @Step("Ingresar monto")
    public void setMonto(String monto) {
        scrollTo(By.xpath("//*[@name='amount']"));
        Assert.assertTrue("NO SE ENCONTRO EL INPUT MONTO ", prestamosPage.getInputMonto().isDisplayed());
        prestamosPage.getInputMonto().click();
        prestamosPage.getInputMonto().sendKeys(monto);
    }

    @Step("Click boton Siguiente")
    public void clickSiguiente() {
        scrollTo(prestamosPage.getSiguiente().getElementBy());
        prestamosPage.getSiguiente().click();
    }
    @Step("Validar boton siguiente deshabilitado")
    public void validarBotonSiguienteDeshabilitado() {
        Assert.assertFalse("El botón 'Siguiente' debería estar deshabilitado", prestamosPage.getSiguiente().isEnabled());

    }
    @Step("Validar mensaje monto mayor o igual")
    public void validarMensajeMontoMayorIgual(String mensajeMonto) {
        prestamosPage.getMensajeMontoMayorIgual(mensajeMonto).isDisplayed();
    }

    @Step("Validar mensaje el monto no puede ser 0")
    public void validarMensajeElmontoNoPuedeSer0() {
        prestamosPage.getMensajeMonto0().isDisplayed();
    }

    @Step("Seleccionar destino")
    public void seleccionDestino(String destino) throws AWTException {
        Assert.assertTrue("NO SE ENCUENTRA SELECION DE DESTINO ", prestamosPage.getSelectorDestinacion().isDisplayed());
        prestamosPage.getSelectorDestinacion().click();
        keyDownToText(destino);
    }

    @Step("Seleccionar cantidad de cuotas")
    public void seleccionarLabelCuotas() throws AWTException {
        scrollTo(By.xpath("//label[@for='1']"));
        prestamosPage.getLabelCuotas("1").click();
    }

    @Step("Validar pantalla completa 'Solicitud'")
    public void validacionCamposEtapaSolicitud() {
        List<String> campos = Arrays.asList(
                "Tipo",
                "Línea de préstamo",
                "Fecha de primer vencimiento",
                "¿Cuánto querés solicitar?",
                "El monto a solicitar debe ser entre $");
        for (String campo : campos) {
            scrollTo(By.xpath(String.format("//*[contains(text(), '%s')]",campo)));
            Assert.assertTrue("El campo '" + campo + "' no esta visible", prestamosPage.textos(campo).isDisplayed());
        }
        System.out.println("Titulos validados con exito");
    }

    @Step("Validar pantalla completa 'Cuotas'")
    public void validacionCamposEtapaCuotas() {
        List<String> campos = Arrays.asList(
                "Seleccionar",
                "Cuotas",
                "Importe primera cuota",
                "TNA",
                "TEM",
                "TEA",
                "Tipo de tasa",
                "Se acreditará en la cuenta",
                "Se debitará en la cuenta");
        for (String campo : campos) {
            scrollTo(By.xpath(String.format("//*[contains(text(), '%s')]",campo)));
            Assert.assertTrue("El campo '" + campo + "' no esta visible", prestamosPage.textos(campo).isDisplayed());
        }
        System.out.println("Titulos validados con exito");
    }
    @Step("Validar pantalla completa 'Resumen'")
    public void validacionCamposEtapaResumen() {
        List<String> campos = Arrays.asList(
                "Tipo",
                "Línea de préstamo",
                "Monto",
                "Sellados",
                "Dinero a acreditar en la cuenta",
                "Fecha de primer vencimiento",
                "Destino de préstamo",
                "Cuotas",
                "Importe primera cuota",
                "TNA",
                "TEM",
                "TEA",
                "Tipo de tasa",
                "Cuenta de acreditación",
                "Cuenta débito");
        for (String campo : campos) {
                  scrollTo(By.xpath(String.format("//*[contains(text(), '%s')]",campo)));
            Assert.assertTrue("El campo '" + campo + "' no esta visible", prestamosPage.textos(campo).isDisplayed());
        }
        System.out.println("Titulos validados con exito");
    }

    @Step("Click descargar resumen contrato")
    public void descargarResumenContrato() throws AWTException {
        Assert.assertTrue("NO SE ENCONTRO EL BOTON DE DESCARGA DE CONTRATO " + prestamosPage.getDescargarResumenContrato().getElementBy(),  prestamosPage.getDescargarResumenContrato().isDisplayed());
        prestamosPage.getDescargarResumenContrato().click();
        clickAceptarGenerico();

    }
    

}