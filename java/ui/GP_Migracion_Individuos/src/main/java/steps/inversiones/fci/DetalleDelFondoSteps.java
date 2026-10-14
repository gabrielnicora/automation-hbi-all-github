package steps.inversiones.fci;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pages.inversiones.fci.DetalleDelFondoPage;
import steps.botonesgenericos.BotonesGenericosSteps;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DetalleDelFondoSteps extends BotonesGenericosSteps {

    private DetalleDelFondoPage detalleDelFondoPage = new DetalleDelFondoPage();

    @Step("Se valida completitud de pantalla Detalle del fondo - Movimientos")
    public void validarPantallaDetallaDelFondoMovimientos(){
        scrollTo(By.xpath("//p[text()='Movimientos']"));
        Assert.assertTrue("La opcion Movimientos no es visible", detalleDelFondoPage.getMovimientos().isDisplayed());
        Assert.assertTrue("La opcion Fecha Desde no es visible", detalleDelFondoPage.getFechaDesde().isDisplayed());
        Assert.assertTrue("La opcion Fecha Hasta no es visible", detalleDelFondoPage.getFechaHasta().isDisplayed());
        Assert.assertTrue("La opcion Suscribir no es visible", detalleDelFondoPage.getSuscribir().isDisplayed());

    }
    @Step("Click boton Aplicar")
    public void clickAplicar(){
        detalleDelFondoPage.getAplicar().click();
    }
    @Step("Click boton Suscribir")
    public void clickSucribir(){
        detalleDelFondoPage.getSuscribir().click();
    }
    @Step("Click boton Rescatar")
    public void clickRescatar(){
        detalleDelFondoPage.getRescatar().click();
    }
    @Step("Click opcion Movimientos")
    public void clickMovimientos(){
        detalleDelFondoPage.getMovimientos().click();
    }
    @Step("Click opcion Detalle")
    public void clickDetalle(){
        detalleDelFondoPage.getDetalle().click();
    }
    @Step("Click opcion Rendimiento")
    public void clickRendimiento(){
        detalleDelFondoPage.getRendimiento().click();
    }
    @Step("Click desplegable fecha desde")
    public void clickFechaDesde(){
        scrollTo(By.xpath("//div[@aria-label='calendario Desde']/div"));
        detalleDelFondoPage.getFechaDesde().click();
    }
    @Step("Click desplegable fecha hasta")
    public void clickFechaHasta(){
        detalleDelFondoPage.getFechaHasta().click();
    }
    @Step("Click en icono Descargar fila {nroFila}")
    public void clickIconoDescargar(String nroFila) throws AWTException, InterruptedException {
        scrollTo(By.xpath((String.format("(//*[@aria-label='Icono de descargar'])[%s]", nroFila))));
        detalleDelFondoPage.getIconoDescargar(nroFila).click();
  }
    @Step("Click en icono Descargar fila ")
    public void clickIconoDescargar() throws AWTException, InterruptedException {
        scrollTo(By.xpath("(//*[@aria-label='Comprobante de solicitud'])[1]"));
        detalleDelFondoPage.getIconoComprobanteSolicitud().click();
    }
    @Step("Validar fechas seleccionadas, desde: {fDesde} y hasta: {fHasta}")
    public void validaFechasSeleccionadas(String fDesde, String fHasta){
        if (fDesde != ""){
            Assert.assertTrue("Fecha desde no coincide con la ingresada",detalleDelFondoPage.getFechaDesde().getText().contains(fDesde));
        }
        if (fHasta != ""){
            Assert.assertTrue("Fecha hasta no coincide con la ingresada",detalleDelFondoPage.getFechaHasta().getText().contains(fHasta));
        }
    }
    @Step("Se validan fechas en listado que coincidan con el filtro: {fDesde} y {fHasta}")
    public void validaFechasConcertacionVisiblesEnListado(String fDesde, String fHasta) throws ParseException {
        List<WebElement> listFechaDesde = detalleDelFondoPage.getTodasLasFechasDeConcertacion();
        List<WebElement> listFechaHasta = detalleDelFondoPage.getTodasLasFechasDeLiquidacion();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date fechaDesde = formato.parse(fDesde);
        Date fechaHasta = formato.parse(fHasta);
        if(!listFechaDesde.isEmpty() || !listFechaHasta.isEmpty()){
            for(int i=0;i<listFechaDesde.size();i++){
                Date itemFechaDesde = formato.parse(listFechaDesde.get(i).getText());
                Date itemFechaHasta = formato.parse(listFechaHasta.get(i).getText());
                if(itemFechaDesde.before(fechaDesde) || itemFechaHasta.after(fechaHasta)){
                    System.out.println("--FECHA INCORRECTA "+listFechaDesde.get(i).getText()+" -- "+listFechaHasta.get(i).getText());
                    Assert.fail("El listado muestra fechas que NO cumplen los parametros del filtro");
                }else{
                    System.out.println("--FECHA CORRECTA "+listFechaDesde.get(i).getText()+" -- "+listFechaHasta.get(i).getText());
                }
            }
        }else{
            Assert.fail("No hay datos disponibles para los filtros aplicados");
        }
    }
    @Step("Se valida completitud de pantalla Detalle del fondo - Rendimiento")
    public void validarPantallaDetallaDelFondoRendimiento(){
        Assert.assertTrue("La opcion Fecha Desde no es visible", detalleDelFondoPage.getFechaDesde().isDisplayed());
        Assert.assertTrue("La opcion Fecha Hasta no es visible", detalleDelFondoPage.getFechaHasta().isDisplayed());
        Assert.assertTrue("La opcion Suscribir no es visible", detalleDelFondoPage.getSuscribir().isDisplayed());
        Assert.assertTrue("La opcion 7 dias no es visible", detalleDelFondoPage.getFiltroSieteDias().isDisplayed());
        Assert.assertTrue("La opcion 30 dias no es visible", detalleDelFondoPage.getFiltroTreintaDias().isDisplayed());
        Assert.assertTrue("La opcion 12 meses no es visible", detalleDelFondoPage.getFiltroDoceMeses().isDisplayed());
    }
}
