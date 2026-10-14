package steps.prestamos;
import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import pages.prestamos.PrestamosPage;
import pages.prestamos.PrestamosParaCancelarDeudasPage;
import steps.botonesgenericos.BotonesGenericosSteps;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class PrestamosParaCancelarDeudasSteps extends BotonesGenericosSteps {

    PrestamosParaCancelarDeudasPage prestamosParaCancelarDeudasPage = new PrestamosParaCancelarDeudasPage();
    PrestamosPage prestamosPage = new PrestamosPage();

    @Step("Click prestamos para cancelar deudas")
    public void clickPrestamosParaCancelarDeudas() {
        Assert.assertTrue("NO SE ENCONTRO EL BOTON : "+ prestamosParaCancelarDeudasPage.getPrestamoParaCancelarDeudas().getElementBy(),prestamosParaCancelarDeudasPage.getPrestamoParaCancelarDeudas().isDisplayed());
        prestamosParaCancelarDeudasPage.getPrestamoParaCancelarDeudas().click();
    }

    @Step("Click en el primer checkbox")
    public void clickPrimerCheckbox() {
        Assert.assertTrue("NO SE ENCONTRO EL CHECKBOX EN LA PAGINA  "+ prestamosParaCancelarDeudasPage.getPrimerCheckbox().getElementBy(),prestamosParaCancelarDeudasPage.getPrimerCheckbox().isDisplayed());
        prestamosParaCancelarDeudasPage.getPrimerCheckbox().click();
    }

    @Step("Click en checkbox tarjeta de credito")
    public void clickCheckboxTarjetaDeCredito() {
        scrollTo(By.xpath("//span[text()='Tarjetas']/../following::div//*[@data-src='../static/media/unchecked.535dfcb2.svg']"));
        prestamosParaCancelarDeudasPage.getCheckboxTarjetaCredito().click();
    }

    @Step("Validar pantalla completa 'Solicitud'")
    public void validacionCamposEtapaSolicitud() {
        List<String> campos = Arrays.asList(
                "Podés solicitar hasta ",
                "Préstamos vigentes",
                "Solicitá tu préstamo para cancelar deudas");
        for (String campo : campos) {
            String campo1 = new String(campo.getBytes(),
                    StandardCharsets.UTF_8);
            scrollTo(By.xpath(String.format("//*[contains(text(), '%s')]", campo1)));
            Assert.assertTrue("El campo '" + campo1 + "' no esta visible", prestamosPage.textos(campo1).isDisplayed());
        }
        System.out.println("Titulos validados con exito");
    }

    @Step("Validar que exista el mensaje 'El monto debe ser entre $ y $ '")
    public void validarMensajeElMontoDebeSerEntre() {
        prestamosPage.textos("El monto a solicitar debe ser entre").isDisplayed();
    }

    @Step("Validar pantalla completa 'Detalle'")
    public void validacionCamposEtapaDetalle() {
        List<String> campos = Arrays.asList(
                "Línea de préstamo",
                "Destino de préstamo",
                "Fecha de primer vencimiento",
                "Monto total a cancelar",
                "Sellados",
                "Dinero extra a acreditar en la cuenta",
                "Monto total del préstamo");
        for (String campo : campos) {
            scrollTo(By.xpath(String.format("//*[contains(text(), '%s')]", campo)));
            Assert.assertTrue("El campo '" + campo + "' no esta visible", prestamosPage.textos(campo).isDisplayed());
        }
        System.out.println("Titulos validados con exito");
    }

    @Step("Validar pantalla completa 'Cuotas'")
    public void validacionCamposEtapaCuotas() {
        List<String> campos = Arrays.asList(
                "Tipo de tasa",
                "CFT TEA",
                "Se acreditará en la cuenta",
                "Se debitará en la cuenta");
        for (String campo : campos) {
            scrollTo(By.xpath(String.format("//*[contains(text(), '%s')]", campo)));
            Assert.assertTrue("El campo '" + campo + "' no esta visible", prestamosPage.textos(campo).isDisplayed());
        }
        System.out.println("Titulos validados con exito");
    }

    @Step("Validar pantalla completa 'Resumen'")
    public void validacionCamposEtapaResumen() {
        List<String> campos = Arrays.asList(
                "Linea de Préstamo",
                "Destino de préstamo",
                "Fecha de Primer Vencimiento",
                "Fecha y Hora Solicitud",
                "Productos a cancelar",
                "Préstamo N°",
                "Monto de deudas a cancelar",
                "Sellados",
                "Dinero extra a acreditar en la cuenta",
                "Monto total del préstamo",
                "Cuotas",
                "Importe primer cuota",
                "TNA",
                "TEM",
                "TEA",
                "Tipo de tasa",
                "CFT TEA",
                "Cuenta de acreditación",
                "Cuenta débito");
        for (String campo : campos) {
            String campo1 = new String(campo.getBytes(),
                    StandardCharsets.UTF_8);
            scrollTo(By.xpath(String.format("//*[contains(text(), '%s')]", campo1)));
            Assert.assertTrue("El campo '" + campo1 + "' no esta visible", prestamosPage.textos(campo1).isDisplayed());
        }
        System.out.println("Titulos validados con exito");
    }



}
