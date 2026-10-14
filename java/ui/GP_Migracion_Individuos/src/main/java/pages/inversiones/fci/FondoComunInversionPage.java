package pages.inversiones.fci;

import enums.inversiones.fci.FiltroFci;
import global.practia.jemeinsamstark.web.core.WebDriver;
import global.practia.jemeinsamstark.web.elements.PandoraElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class FondoComunInversionPage {

    public PandoraElement getCuentaComitenteSeleccionada() {
        return new PandoraElement(By.xpath("//*[contains(@aria-label,'n seleccionada')]"));
    }

    public PandoraElement getDesplegableCuentaComitente(String nroCuenta) {
        return new PandoraElement(By.xpath(String.format("//div[@id='commitentAccountSelector']/descendant::div[contains(text(), '%s')]", nroCuenta)));
    }


    public PandoraElement getDesplegableCuentaComitente() {
        return new PandoraElement(By.xpath("//input[@name='commitentAccountSelector']/.."));
    }


    public PandoraElement getSimularPerfilInversor() {
        return new PandoraElement(By.xpath("//span[text()='Simular perfil del inversor']"));
    }
    public PandoraElement getSelectorFondosPorMoneda() {
        return new PandoraElement(By.id("selectorDescribedBy_1"));
    }
    public PandoraElement getSelectorFondosPorRiesgo() {
        return new PandoraElement(By.id("selectorDescribedBy_2"));
    }
    public PandoraElement getIconoAccionesDetalleFCI(String tipoFCI) {
        return new PandoraElement(By.xpath(String.format("//p[contains(text(),'%s')]/../..//following::div[@aria-label='Icono de listar acciones del FCI'][1]", tipoFCI)));
    }
    public PandoraElement getDetalleDelFondo() {
        return new PandoraElement(By.xpath("//span[text()='Detalle del fondo']"));
    }
    public PandoraElement getMovimientos() {
        return new PandoraElement(By.xpath("//*[@id='mainLayout']//span[text()='Movimientos']"));
    }
    public PandoraElement getRendimiento() {
        return new PandoraElement(By.xpath("//*[@id='mainLayout']//span[text()='Rendimiento']"));
    }
    public PandoraElement getMasInformacion() {
        return new PandoraElement(By.xpath("//span[contains(text(),'Más Información')]/../../.."));
    }
    public PandoraElement getRechazar() {
        return new PandoraElement(By.xpath("//span[text()='Rechazar']"));
    }
    public PandoraElement getAceptar() {
        return new PandoraElement(By.xpath("//span[text()='Aceptar']"));
    }

    public PandoraElement getSuscribir() {
        return new PandoraElement(By.xpath("//*[@id='mainLayout']//span[text()='Suscribir']"));
    }
    public PandoraElement getDescargarDetalle() {
        return new PandoraElement(By.xpath("//*[@id='mainLayout']//span[text()='Descargar detalle']"));
    }
    public PandoraElement getRescate() {
        return new PandoraElement(By.xpath("//span[contains(text(),'Rescatar')]"));
    }

    private String getXpathFiltro(FiltroFci filtro) {
        return String.format(
                "//*[normalize-space()='%s']/following-sibling::div[p[normalize-space()='%s']]",
                filtro.getGrupoDom(),
                filtro.getTextoDom()
        );
    }

    public PandoraElement getFiltro(FiltroFci filtro) {
        return new PandoraElement(By.xpath(getXpathFiltro(filtro)));
    }

    public boolean isFiltroSeleccionado(FiltroFci filtro) {
        By indicadorSeleccionado = By.xpath(getXpathFiltro(filtro) + "/div[@fill]");
        return !WebDriver.getInstance().findElements(indicadorSeleccionado).isEmpty();
    }
    public PandoraElement getListadoFiltrosAplicados(){
        return new PandoraElement(By.xpath("//*[@aria-label='tabla']/div[2]"));
    }
    public PandoraElement getListadoFiltroPorFondoDeRiesgo(String fondo){
        return new PandoraElement(By.xpath(String.format("//*[@role='cell']//div[contains(text(),'%s')]",fondo)));
    }
    public List<WebElement> getTodosLosFondosDeRiesgoVisiblesEnListado(){
        return WebDriver.getInstance().findElements(By.xpath("//div[@role='cell'][3]/p/div"));
    }
    public List<WebElement> getTodosLosFondosDeRiesgoConservadorEnListado(){
        return WebDriver.getInstance().findElements(By.xpath("//div[@role='cell'][4]/p/div"));
    }
    public List<WebElement> getTodosLosFondosPorMonedaVisiblesEnListado(){
        return WebDriver.getInstance().findElements(By.xpath("//*[@aria-label='fila']/div[2]/p"));
    }
    public PandoraElement getRescatarPrimerElemento() {
        return new PandoraElement(By.xpath("//p[contains(text(), 'En el día')]/../parent::div//button[@aria-label='Rescatar']"));
    }
    public PandoraElement getLabelMisFondos() {
        return new PandoraElement(By.xpath("//p[text()='Mis fondos']/../descendant::label"));
    }
    public PandoraElement getMiFondo() {
        return new PandoraElement(By.xpath("//p[text()= 'Mi fondo']"));
    }
    public PandoraElement getNumeroCuentaComitente() {
        return new PandoraElement(By.xpath("//*[text()='Cuenta comitente']/following-sibling::p"));
    }
    public PandoraElement getCalendarioDesde() {
        return new PandoraElement(By.xpath("//div[contains(@aria-label,'Desde')]/div"));
    }
    public PandoraElement getRegistros() {
        return new PandoraElement(By.xpath("//div[@role='row']"));
    }

    public PandoraElement getSuscribirEnElDia(){return new PandoraElement(By.xpath("//p[contains(text(), 'En el día')]/../parent::div//button[@aria-label='Suscribir']"));}
    public PandoraElement getRescatar(String tipoFCI){return new PandoraElement(By.xpath(String.format("//p[contains(text(), '%s')]/../..//parent::div//button[@aria-label='Rescatar']", tipoFCI)));}
    public PandoraElement getSuscribirDiaHabil(String tipoFci){return new PandoraElement(By.xpath(String.format("//p[contains(text(), '%s')]/../..//parent::div//button[@aria-label='Suscribir']",tipoFci)));}
    public PandoraElement getTipoDeFCI(String tipoFCI){return new PandoraElement(By.xpath(String.format("//p[contains(text(),'%s')]",tipoFCI)));}
    public PandoraElement getTenenciaValorizada(){return new PandoraElement(By.xpath("(//span[@aria-label='pesos'])[1]/.."));}

    public PandoraElement getTenenciaValorizadaUSD(){return new PandoraElement(By.xpath("(//span[@aria-label='dolares'])[1]/.."));}

    public PandoraElement getCotizacion(){return  new PandoraElement(By.xpath("(//div[@class='sc-jXbUNg keyMMM']/p)[8]/span"));}
    public PandoraElement getCantidadCuotaPartes(){
        return new PandoraElement(By.xpath("(//p[contains(text(),'Cuotapartes disponibles')]//following::div/p)[1]"));
    }
    public PandoraElement getSuscipcionEnCurso(){return  new PandoraElement(By.xpath("(//div[@class='sc-jXbUNg keyMMM']/p)[6]/span"));}
    public List<WebElement> getTenenciaValorizadaGrilla() {
        return WebDriver.getInstance().findElements(By.xpath("(//span[@aria-label='*accessibility.aria.label.currency.label.Pesos*'])/.."));
    }
    public List<WebElement> getTenenciaValorizadaUSDGrilla() {
        return WebDriver.getInstance().findElements(By.xpath("(//span[@aria-label='*accessibility.aria.label.currency.label.Dólar*'])/.."));
    }
    public List<WebElement> getTenenciaValorizadaUSDGrillaBSC() {
        return WebDriver.getInstance().findElements(By.xpath("(//span[@aria-label='*accessibility.aria.label.currency.label.Dolar*'])/.."));
    }


    public PandoraElement getModalFCI(String msgModal){return  new PandoraElement(By.xpath(String.format("//p[contains(text(),'%s')]",msgModal)));}
    public PandoraElement getMensajeImportanteDiaHabil(String mensaje){return new PandoraElement(By.xpath(String.format("//p[contains(text(),'%s')]",mensaje)));}
}
