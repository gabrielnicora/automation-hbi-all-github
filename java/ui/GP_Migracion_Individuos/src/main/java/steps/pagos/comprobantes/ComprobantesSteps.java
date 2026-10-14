package steps.pagos.comprobantes;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import pages.pagos.comprobantes.ComprobantesPage;
import steps.botonesgenericos.BotonesGenericosSteps;
import steps.mensaje.MensajeSteps;
import utils.UtilsGP;

import java.awt.*;
import java.awt.event.KeyEvent;

public class ComprobantesSteps extends BotonesGenericosSteps {
    MensajeSteps mensajes = new MensajeSteps();
    ComprobantesPage comprobantesPage = new ComprobantesPage();

    @Step("Click en empresa")
    public void clickEmpresa() {
        comprobantesPage.getEmpresa().click();
    }

    @Step("Click en {empresa}")
    public void setInputEmpresa(String empresa) {
        comprobantesPage.getInputEmpresa().sendKeys(empresa);
    }

    @Step("Click en descripcion")
    public void clickDescripcion() {
        comprobantesPage.getDescripcion().click();
    }

    @Step("Click Fecha Creacion Desde")
    public void clickFechaCreacionDesde() {
        comprobantesPage.getCalendarioFechaDesde().click();
    }

    @Step("Click Tres Puntos")
    public void clickTresPuntos() {
        comprobantesPage.getTresPuntos().click();
    }

    @Step("Click Descargar Comprobante")
    public void clickDescargarComprobante() {
        comprobantesPage.getDescargar().click();
    }

    @Step("Click Calendario Desde")
    public void clickDesde() {
        comprobantesPage.getCalendarDesde().click();
    }

    @Step("Click Buscar")
    public void clickBuscar() {
        comprobantesPage.getBotonBuscar().click();
    }

    @Step("Descargar periodo en XLS ")
    public void descargarPeriodoXLS() {
        comprobantesPage.getDescargarPeriodoXLS().click();
    }


    @Step("Descargar periodo en PDF")
    public void descargarPeriodoPDF() {
        comprobantesPage.getDescargarPeriodoEnPDF().click();
    }

    @Step("Descargar periodo en XLS")
    public void descargarMensajeXLS() {
        if (!comprobantesPage.getMensajeXLS().isDisplayed()) {
            for (int i = 0; i < 10; i++) {
                mensajes.clickSinLeer();
            }
        } else {
            comprobantesPage.getMensajeXLS().click();
        }
    }
    @Step("Descargar mensaje PDF ")
    public void descargarMensajePDF() {
        if (!comprobantesPage.getMensajePDF().isDisplayed()) {
            for (int i = 0; i < 10; i++) {
                mensajes.clickSinLeer();
            }
        } else {
            comprobantesPage.getMensajePDF().click();
        }
    }

    @Step("Seleccionar Empresa")
    public void seleccionarEmpresa(String empresa) throws AWTException {

        UtilsGP.fixedWait(5000);
        scrollTo(By.xpath("//div[contains(text(), '"+ empresa +"')]"));
        comprobantesPage.getSeleccionarEmpresa(empresa).click();

    }

}
