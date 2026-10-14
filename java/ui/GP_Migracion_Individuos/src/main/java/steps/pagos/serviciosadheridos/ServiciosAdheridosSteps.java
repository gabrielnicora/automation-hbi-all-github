package steps.pagos.serviciosadheridos;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import pages.pagos.serviciosadheridos.ServiciosAdheridosPage;
import steps.botonesgenericos.BotonesGenericosSteps;
import steps.pagos.adherirservicio.AdherirServicioSteps;

public class ServiciosAdheridosSteps extends BotonesGenericosSteps {
    ServiciosAdheridosPage serviciosAdheridosPage = new ServiciosAdheridosPage();
    BotonesGenericosSteps botonesGenericosSteps = new BotonesGenericosSteps();
    AdherirServicioSteps adherirServicioSteps = new AdherirServicioSteps();

    @Step("Empresa")
    public void clickEmpresa(){
        serviciosAdheridosPage.getEmpresa().click();
    }

    @Step("Se ingresa empresa {empresa}")
    public void setInputEmpresa(String empresa){
        serviciosAdheridosPage.getInputEmpresa().sendKeys(empresa);
    }

    @Step("Click en tres puntos")
    public void clickTresPuntos(String codigoPago){
        if(!serviciosAdheridosPage.getTresPuntos(codigoPago).isDisplayed()){
            adherirServicioSteps.clickVerMasAdhesiones();
        }
        scrollTo(By.xpath(String.format("//*[text()='%s']/../../descendant::div//*[@id='Grupo_11105']", codigoPago)));
        serviciosAdheridosPage.getTresPuntos(codigoPago).click();
    }

    @Step("Eliminar Servicio adherido")
    public void  eliminarServicioAdherido(){
        scrollTo(By.xpath("//div[@aria-label='Icono de eliminar']"));
        serviciosAdheridosPage.getEliminarServicioAdherido().click();
    }

    @Step("Buscar Servicio Adherido por Descripción")
    public void buscarPorDescripcion(String descripcion){
        serviciosAdheridosPage.getInputSearch().click();
        serviciosAdheridosPage.getInputSearch().sendKeys(descripcion);
    }
    @Step("Boton desvincular servicio adherido")
    public void clickBotonDesvincular(){
        serviciosAdheridosPage.getConfirmarDesvinculacion().click();
    }

    @Step("Boton tres puntos por codigo de pago {codPago}")
    public void clickTresPuntosCodPago(String codPago){
        serviciosAdheridosPage.getTresPuntosCodPago(codPago).isDisplayed();
        serviciosAdheridosPage.getTresPuntosCodPago(codPago).click();
    }

    @Step("Eliminar Servicio Adherido Front Viejo")
    public void clickEliminarServicio(){
        serviciosAdheridosPage.getEliminarServicioAdheridoFV().isDisplayed();
        serviciosAdheridosPage.getEliminarServicioAdheridoFV().click();
    }
}
