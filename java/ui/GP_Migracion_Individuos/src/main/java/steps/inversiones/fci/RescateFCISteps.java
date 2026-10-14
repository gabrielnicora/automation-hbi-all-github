package steps.inversiones.fci;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import pages.botonesgenericos.BotonesGenericosPage;
import pages.inversiones.fci.RescateFCIPage;
import pages.inversiones.fci.SuscripcionFCIPage;
import steps.botonesgenericos.BotonesGenericosSteps;

import java.util.Arrays;
import java.util.List;

public class RescateFCISteps extends BotonesGenericosSteps {
    SuscripcionFCIPage suscripcionFCI= new SuscripcionFCIPage();
    private RescateFCIPage rescateFCI = new RescateFCIPage();
    private BotonesGenericosPage botonesGenericosPage = new BotonesGenericosPage();

    @Step("Click Selector Cuenta Destino")
    public void clickCuentaDestino() { rescateFCI.getCuentaDestino().click(); }

    @Step("Seleccionar Rescate Parcial")
    public void clickParcial() { rescateFCI.getParcial().click(); }

    @Step("Seleccionar Rescate Total")
    public void clickTotal() { rescateFCI.getTotal().click(); }

    @Step("Se ingresa cuotapartes a rescatar: {cuotapartes}")
    public void setImporteRescatar(String cuotapartes) {
        rescateFCI.getInputCuotapartes().isDisplayed();
        rescateFCI.getInputCuotapartes().sendKeys(cuotapartes);
    }

    @Step("Obtener importe estimado a rescatar")
    public String getImporteEstimado() {
        return rescateFCI.getImporteEstimadoRescatar().getText();
    }

    @Step("Validar que el importe estimado se actualice")
    public void validaImporteEstimadoActualizado(String importeEstimadoAnterior){
        String importeEstimadoActual = rescateFCI.getImporteEstimadoRescatar().getText();
        System.out.println("Anterior "+importeEstimadoAnterior+ " Actual "+importeEstimadoActual);
        Assert.assertNotEquals("El importe estimado no se actualizó", importeEstimadoAnterior, importeEstimadoActual);
    }

    @Step("Se valida pantalla Confirmar rescate de FCI")
    public void validaPantallaConfirmarRescateFCI(){
        Assert.assertTrue("", botonesGenericosPage.getContinuar().isDisplayed());
    }
    @Step("Se valida visibilidad boton Continuar")
    public void validaBotonContinuarRescateFCI(){
        Assert.assertTrue("", botonesGenericosPage.getContinuar().isDisplayed());
    }
    @Step("Validar pantalla completa 'Confirmar rescate de FCI'")
    public void validacionPantallaConfirmacionRescate() {
        List<String> campos = Arrays.asList(
                "Nombre del Fondo Común de inversión",
                //"Fecha de rescate",
                "Cuenta Comitente",
                "Cuenta destino",
                "Cantidad de cuotapartes",
                "Importe estimado a rescatar",
                "Plazo de liquidación");
                //"Monto estimado a rescatar en base al último valor de cuotaparte publicado. Dicha operación se liquidará con el valor de la cuotaparte del día de la fecha, generado al cierre de las operaciones.");
        for (String campo : campos) {
            scrollTo(By.xpath(String.format("//*[contains(text(), '%s')]",campo)));
            Assert.assertTrue("El campo '" + campo + "' no esta visible", suscripcionFCI.textos(campo).isDisplayed());
        }
        System.out.println("Titulos validados con exito");
    }
}
