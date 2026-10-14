package steps.utillogger;

import global.practia.jemeinsamstark.web.core.WebDriver;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.path.json.JsonPath;
import org.junit.Assert;
import pages.utillogger.UtilLoggerPage;
import steps.botonesgenericos.BotonesGenericosSteps;
import utils.UtilsGP;

import java.util.ArrayList;
import java.util.List;

public class UtilLoggerSteps extends BotonesGenericosSteps {
    UtilLoggerPage utilLoggerPage = new UtilLoggerPage();
    UtilsGP utilsGP = new UtilsGP();

    @Step("Validar datos del Servicio")
    public void clickRequestSvc(String servicio) {
        UtilsGP.fixedWait(10000);

        String nameEsperado = "metodoValidacionFP";
        String valorEsperado = "DESCONOCIDO";
        List<String> requestsRevisados = new ArrayList<>();
        int cantidadRequests = obtenerCantidadRequests(servicio);

        Assert.assertTrue("No se encontraron requests para el servicio: " + servicio, cantidadRequests > 0);

        for (int i = 1; i <= cantidadRequests; i++) {
            abrirRequest(servicio, i);
            String bodyRequest = leerBodyRequest();
            requestsRevisados.add("Request " + i + "\n" + bodyRequest);

            String name = obtenerOptionalParam(bodyRequest, "metodoValidacionFP", "name");
            String value = obtenerOptionalParam(bodyRequest, "metodoValidacionFP", "value");

            if (nameEsperado.equals(name)) {
                Allure.addAttachment("UtilLogger request validado " + servicio, bodyRequest);
                cerrarDetalleRequest();
                Assert.assertEquals("Valor incorrecto", valorEsperado, value);
                return;
            }

            cerrarDetalleRequest();
        }

        Allure.addAttachment("UtilLogger requests sin metodoValidacionFP", String.join("\n\n", requestsRevisados));
        Assert.fail("No se encontro optionalParam metodoValidacionFP en los requests del servicio: " + servicio);
    }

    private int obtenerCantidadRequests(String servicio) {
        return WebDriver.getInstance().findElements(utilLoggerPage.getBtnRequestBy(servicio)).size();
    }

    private void abrirRequest(String servicio, int indice) {
        scrollTo(utilLoggerPage.getBtnRequestByIndex(servicio, indice));
        WebDriver.getInstance().findElement(utilLoggerPage.getBtnRequestByIndex(servicio, indice)).click();
        UtilsGP.fixedWait(5000);
    }

    private String leerBodyRequest() {
        return utilLoggerPage.getDatoServicio().getText();
    }

    private String obtenerOptionalParam(String bodyRequest, String nombreParametro, String campo) {
        return utilsGP.quitarCochetesJsonPath(JsonPath.from(bodyRequest)
                .getString("optionalParams.findAll { it.name == '" + nombreParametro + "' }." + campo));
    }

    private void cerrarDetalleRequest() {
        try {
            WebDriver.getInstance().findElement(utilLoggerPage.getBtnCerrarDetalleRequest()).click();
            UtilsGP.fixedWait(1000);
        } catch (Exception e) {
            System.out.println("No se pudo cerrar detalle request utilLogger: " + e.getMessage());
        }
    }

    @Step("Click en boton Actualizar")
    public void clickBtnActualizar() {
        utilLoggerPage.getBtnActualizar().click();
    }
}
