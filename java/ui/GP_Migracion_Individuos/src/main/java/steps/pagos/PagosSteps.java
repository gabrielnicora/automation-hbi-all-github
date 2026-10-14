package steps.pagos;

import api.PagosAPI;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.qameta.allure.Step;
import org.codehaus.jettison.json.JSONException;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.botonesgenericos.BotonesGenericosPage;
import pages.pagos.PagosPage;
import steps.botonesgenericos.BotonesGenericosSteps;
import utils.MetodosAPI;
import utils.UtilsGP;

public class PagosSteps extends BotonesGenericosSteps {
    private PagosPage pagos = new PagosPage();

    private MetodosAPI metodosAPI = new MetodosAPI();
    private PagosAPI pagosAPI = new PagosAPI();
    private BotonesGenericosPage botonesGenericos = new BotonesGenericosPage();
    WebDriverWait wait = new WebDriverWait(global.practia.jemeinsamstark.web.core.WebDriver.getInstance(), 30);
    @Step("Click Selector Tarjeta Debito")
    public void clickSelectorTarjetaDebito(){
        UtilsGP.fixedWait(5000);
        scrollTo(By.id("selectorDescribedBy_0"));
        pagos.getSelectorTarjetaDebito().isDisplayed();
        pagos.getSelectorTarjetaDebito().click();

    }

    @Step("Click Comprobantes")
    public void clickComprobantes() throws InterruptedException {
        Thread.sleep(6000);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[@id='mainLayout']//a[@href='/payments/paymentsMadeList'])")));
        pagos.getComprobantes().click();
    }

    @Step("Click Nuevo Pago")
    public void clickNuevoPago(){
        pagos.getNuevoPago().click();
    }

    @Step("Click Adherir Impuesto/Servicio")
    public void clickAdherirImpuestoServicio(){
        Assert.assertTrue("NO SE ENCONTRO EL BOTON ADHERIRI IMPUESTOS Y SERVICIO",pagos.getAdherirImpuestoServicio().isDisplayed());
        pagos.getAdherirImpuestoServicio().click();
    }

    @Step("Click Servicios Adheridos")
    public void clickServiciosAdheridos(){
        pagos.getServiciosAdheridos().isDisplayed();
        pagos.getServiciosAdheridos().click();
    }

    @Step("Click Pagos VEP")
    public void clickPagosVep(){
        pagos.getPagosVep().click();
    }

    @Step("Click DebinVendedor")
    public void clickDebinVendedor(){
        pagos.getDebinVendedor().click();
        Assert.assertTrue("Solicitudes realizadas de DEBIN vendedor", pagos.getSeccionDebinVendedor().isDisplayed());
        pagos.getGenerarOrdenDebinVendedor().isEnabled();
        pagos.getConsultarAdhesionesDebinVendedor().isEnabled();
    }
    @Step("Click DebinComprador")
    public void clickDebinComprador(){
        pagos.getDebinComprador().click();
        Assert.assertTrue("Gestión de solicitudes de DEBIN recibidas", pagos.getSeccionDebinComprador().isDisplayed());
        pagos.getAdherirRecurrencia().isEnabled();
        pagos.getConsultarRecurrencia().isEnabled();
    }

    @Step("Click en nuevo pagos Front Viejo ")
    public void clickNuevoPagoFrontViejo(){
        pagos.getNuevoPagoFrontViejo().isDisplayed();
        pagos.getNuevoPagoFrontViejo().click();

    }


    @Step("Validar Seccion DebinComprador")
    public void validarSeccionDebinComprador(){
        Assert.assertTrue("Gestión de solicitudes de DEBIN recibidas", pagos.getSeccionDebinComprador().isDisplayed());
        pagos.getAdherirRecurrencia().isEnabled();
        pagos.getConsultarRecurrencia().isEnabled();
    }
    @Step("Validar Seccion DebinVendedor")
    public void validarSeccionDebinVendedor(){
        Assert.assertTrue("Solicitudes realizadas de DEBIN vendedor", pagos.getSeccionDebinVendedor().isDisplayed());
        pagos.getGenerarOrdenDebinVendedor().isEnabled();
        pagos.getConsultarAdhesionesDebinVendedor().isEnabled();
    }
    @Step("Validar Seccion DebinVendedor")
    public void validarCampoObligatorio(){
        Assert.assertTrue("Este campo es obligatorio", pagos.getCampoObligatorio().isDisplayed());
    }

    @Step("Generacion de Orden de Debin por API")
    public void generacionDebinApi(String cbu, String cuenta, String cuit){
        //metodosAPI.getGenerateDebin(usuario,pass,cbu,cuenta,cuit);
        pagosAPI.generarDebinAPI(cuenta,cbu,cuit);
    }

    @Step("Elimnar una adhesion de servicio por medio de API")
    public void eliminarServicioAdheridoAPI(String empresa, String pertenece, String card, String codeServicio){
        //metodosAPI.getEliminarAdhesionPago( usuario,password,belong,card);
        pagosAPI.eliminarSerivicoAPI(empresa,pertenece,codeServicio,card);
    }
    @Step("Generar un pago de Servicio")
    public void generarUnPagoDeServicioApi(String Ente, String Referencia, String CodPago,String cuenta,String card) throws JSONException, InterruptedException, JsonProcessingException {
        metodosAPI.realizarPagoServicio(Ente,Referencia,CodPago,cuenta,card );
    }
    @Step("Adherir Servicio por metodo API")
    public void adherirServicioApi(String codPago, String rubro, String empresa, String card) throws JSONException, InterruptedException {
        pagosAPI.adherirServicioAPI(codPago,rubro,empresa,card);
    }

    @Step("Click Servicios Agendados")
    public void clickServicioAgendado(){

        Assert.assertTrue("NO SE ENCONTRO EL BOTON ADHERIR IMPUESTOS Y SERVICIO",pagos.getserviciosAgendados().isDisplayed());
        pagos.getserviciosAgendados().click();
    }

    @Step("Click sobre la lista Pagando Desde")
    public void clicklistaPagandoDesde(){
        pagos.getlistaPagandoDesde().click();
    }

    @Step("Click sobre la lista Vencimiento")
    public void clicklistaVencimiento(){
        pagos.getlistavencimiento().click();
    }

    @Step("Setear Referencia de Pago")
    public void setearReferenciaDePago(String reference){
        pagos.getreferencia().sendKeys(reference);
    }


    @Step("Seleccionar servicios a pagar")
    public void clickServicios(String servicio){
        scrollTo(By.xpath("//div[@name= '"+ servicio +"']"));
        pagos.getServicioAPagar(servicio).click();
    }

    @Step("Mostrar todos los Servicios")
    public void clickMostrarTodos(){
        UtilsGP.fixedWait(3000);
        scrollTo(By.xpath("//a[contains(text(),'Mostrar todos')]"));
        pagos.getMostrarTodos().click();
    }

    @Step("Seleccionar servicios a pagar")
    public void clickCheckboxServicio(String servicio){
        scrollTo(pagos.getSeleccinaServicioAPagar(servicio).getElementBy());
        pagos.getSeleccinaServicioAPagar(servicio).click();
    }


    @Step("Seleccionar Mis Comprobantes")
    public void clickMisComprobantes(String comprobantes){
        botonesGenericos.getSeleccionarServicio2(comprobantes).click();
    }

    @Step("Click Guardar Cambios")
    public void clickGuardarCambios(){
        pagos.getBotonGuardarCambios().click();
    }


}















