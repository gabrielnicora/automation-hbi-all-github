package steps.recargas;

import api.RecargasAPI;
import io.qameta.allure.Step;
import org.codehaus.jettison.json.JSONException;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.recargas.RecargasPage;
import steps.botonesgenericos.BotonesGenericosSteps;
import steps.menulateral.MenuLateralSteps;
import steps.ticket.TicketSteps;
import utils.MetodosAPI;
import utils.UtilsGP;

import java.awt.*;

public class RecargasSteps extends BotonesGenericosSteps {

    private RecargasPage recargasPage = new RecargasPage();
    MenuLateralSteps menuLateralSteps = new MenuLateralSteps();
    WebDriverWait wait = new WebDriverWait(global.practia.jemeinsamstark.web.core.WebDriver.getInstance(), 30);
    MetodosAPI metodosAPI = new MetodosAPI();
    RecargasAPI recargasAPI = new RecargasAPI();

    @Step("Click Selector Tarjeta Debito")
    public void clickSelectorTarjetaDebito() {
        recargasPage.getTarjetaDeDebitoSelector().click();
    }

    @Step("Click Selector Empresa")
    public void clickEmpresa() {
        recargasPage.getEmpresa().isDisplayed();
        recargasPage.getEmpresa().click();
    }
    @Step("Verificar: Datos de recarga - Cuenta Origen")
    public void verificarDatosRecargaCuentaOrigen() {
        Assert.assertTrue("No se visualiza Empresa",recargasPage.getTextoGenerico("Empresa").isDisplayed());
        Assert.assertTrue("No se visualiza Cuenta origen",recargasPage.getTextoGenerico("Cuenta origen").isDisplayed());
        Assert.assertTrue("No se visualiza Monto a recargar",recargasPage.getTextoGenerico("Monto a recargar").isDisplayed());
        if(recargasPage.getMensajeError().isDisplayed()){
            throw new Error("Mensaje emergente presente: No pudimos procesar la operación");
        };
    }
    @Step("Verificar: Datos de recarga a Telefonía Celular")
    public void verificarDatosRecargaTelefoniaCelular(String empresa,String numero,String monto,String mensajeSiFalla) {
        Assert.assertTrue("La Empresa no coincide",recargasPage.getTextoGenerico(empresa).isDisplayed());
        Assert.assertTrue("El Numero de telefono no coincide",recargasPage.getTextoGenerico(numero).isDisplayed());
        Assert.assertTrue("El monto de recarga no coincide",recargasPage.getTextoGenerico(monto).isDisplayed());
        if(recargasPage.getMensajeError().isDisplayed()){
            throw new Error("Mensaje emergente presente: " + mensajeSiFalla);
        };
    }
    @Step("Click Selector Servicios-Rubro")
    public void clickSelectorServicioEmpresa() {
        wait.until(ExpectedConditions.elementToBeClickable(By.id("number")));
        recargasPage.getServicioEmpresa().click();
    }
    @Step("Realizar Adehesión Telefonica API")
    public void realizarAdhesionAPI(String codigoCompania, String num, String perteneceA, String card){
        recargasAPI.realizarAdhesionAPI(codigoCompania, num, perteneceA,card);
    }
    @Step("Click botón Seleccionar")
    public void clickBotonSeleccionar() {
        recargasPage.getBotonSeleccionar().isDisplayed();
        recargasPage.getBotonSeleccionar().click();
    }
    @Step("Eliminar Adhesion De Servicio Prepago API")
    public void eliminarAdhesionAPI(String user, String compania, String codigoCompania, String numTelefono, String perteneceA, String card) {
        String pass = UtilsGP.consultaDB("usuarios",user,"password");
        metodosAPI.eliminarAdhesionServicioPrepagoAPI(pass, user, compania, codigoCompania,numTelefono,perteneceA,card);
    }
    @Step("Click Selector Tarjeta seccion adhesiones")
    public void clickSelectorTarjeta_adhesiones() {
        recargasPage.getTarjeta_Adhesiones().click();
    }
    @Step("Ingresar monto a recargar")
    public void clickMontoRecarga(String monto) {
        recargasPage.getMontoRecarga().sendKeys("%s", monto);
    }

    @Step("Click Selector Rubro Eliminar")
    public void clickSelectorRubroEliminar() {
        recargasPage.getRubroEliminar().click();
    }

    @Step("Click Selector Empresa a Eliminar")
    public void clickSelectorEmpresaEliminar() {
        recargasPage.getEmpresaEliminar().click();
    }

    @Step("Click Selector Card Tarjeta Recargable")
    public void clickCardtarjetaRecargable() {
        recargasPage.getCardTarjetaRecargable().click();
    }

    @Step("Click Selector Card Telefonia Celular")
    public void clickCardTelefoniaCelular() {
        recargasPage.getTituloRecarga().isDisplayed();
        recargasPage.getCardTelofoniaCelular().isDisplayed();
        recargasPage.getCardTelofoniaCelular().click();
    }

    @Step("Click Selector Card Billetera")
    public void clickCardBilletera() {recargasPage.getCardBilletera_ServPublico().click();
    }

    @Step("Click Selector Card Transporte Publico")
    public void clickCardTransportePublico() {
        recargasPage.getTituloRecarga().isDisplayed();
        recargasPage.getCardTransportePublico().isDisplayed();
        recargasPage.getCardTransportePublico().click();
    }

    @Step("Click Selector de Monto")
    public void clickSelectorMonto() throws InterruptedException {
        Thread.sleep(1000);
        scrollTo(By.xpath("//input[@name='amountCode']/preceding-sibling::div"));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@name='amountCode']/preceding-sibling::div")));
        recargasPage.getMonto().click();
    }

    @Step("Click eliminar icono Adhesion")
    public void clickIconoEliminarAdhesion(String perteneceA) {
        scrollTo((By.xpath(String.format("//div[contains(text(), '%s')]/parent::div/parent::form/parent::div/parent::p/parent::div/parent::div/div[4]//button", perteneceA))));
        recargasPage.getEliminarSube(perteneceA).isDisplayed();
        recargasPage.getEliminarSube(perteneceA).click();
    }

    @Step("Click Adhesiones")
    public void clickAdhesiones() {
        recargasPage.getAdhesiones().isDisplayed();
        recargasPage.getAdhesiones().click();
    }

    @Step("Click Consultas Recargas")
    public void clickConsultasRecargas() {
        recargasPage.getConsultaRecargas().click();
    }

    @Step("Click Buscar")
    public void clickBuscar() {
        recargasPage.getBuscar().click();
    }

    @Step("Click Agregar")
    public void clickAgregar() {
        recargasPage.getAgregar().isDisplayed();
        recargasPage.getAgregar().click();
    }

    @Step("Click Continuar")
    public void clickContinuar() {
        scrollTo(By.xpath("//button[@aria-label='Continuar']"));
        recargasPage.getContinuar().isDisplayed();
        recargasPage.getContinuar().click();
    }

    @Step("Se ingresa rubro {codigo}")
    public void setInputNumero(String codigo) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='code']")));
        recargasPage.getNumero_Codigo().isDisplayed();
        //recargasPage.getNumero_Codigo().click();
        recargasPage.getNumero_Codigo().sendKeys(codigo);
    }

    @Step("Se ingresa Pertenece A")
    public void setInputPerteneceA(String pertenece) {
        recargasPage.getPerteneceA().sendKeys(pertenece);
    }

    @Step("Click desplegable Seleccionar numero")
    public void clickSeleccionarNumero() throws InterruptedException {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@name, 'number')]/..")));
        Thread.sleep(3000);
        recargasPage.getSeleccionarNumero().click();
        recargasPage.getDesplegableNumeroTarjeta().isDisplayed();
    }

    @Step("Click icono ticket")
    public void clickIconoTicket() {
        recargasPage.getIconoTicket().isDisplayed();
        recargasPage.getIconoTicket().click();
    }

    @Step("Click Seleccionar")
    public void clickSeleccionar() {
        recargasPage.getSeleccionarNumero().click();
    }

    @Step("Click desplegable tarjeta debito")
    public void clickDesplegableDebito() {
        scrollTo(By.xpath("//input[@name='debitAccount']/../.."));
        recargasPage.getTarjetaDebito().click();
    }

    @Step("Post condicion eliminar adhesion")
    public void postCondicionEliminarAdhesion(String perteneceA, String tarjetaDebito) throws AWTException {
        menuLateralSteps.clickRecargas();
        clickAdhesiones();
        if (recargasPage.getVisaDebitoRecargasAdheridas().isDisplayed()) {
            recargasPage.getVisaDebitoRecargasAdheridas().click();
            keyDownToText(tarjetaDebito);
        }
        else {
            System.out.println("No se encontro desplegable tarjeta debito en Adhesiones");
        }
        recargasPage.getRubroEliminar().click();
        keyDownToText( "TRANSPORTE PUBLICO");
        clickSelectorEmpresaEliminar();
        keyDownToText( "SUBE");
        clickBuscar();
        if (recargasPage.getRegistroRecargaAutomation(perteneceA).isDisplayed()) {
            clickIconoEliminarAdhesion(perteneceA);
            clickConfirmarGenerico();
            TicketSteps ticketSteps = new TicketSteps();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
        } else {
            System.out.println("No se encontraron recargas para eliminar");
        }
    }
    @Step("Click desplegable Cuenta a debitar")
    public void clickCuentaADebitar() {
        recargasPage.getCuentaADebitar().click();
    }

    @Step("Validar recarga adherida")
    public void validarRecargaAdherida(String nombreRecarga) {
        recargasPage.getTextoRecarga(nombreRecarga).isDisplayed();
    }

    @Step("Eliminar adhesiones por  API")
    public void eliminarAdhesaionesdeServicioAPI(String codCompania,String num,String perteneceA,String card){
        recargasAPI.eliminarAdhesionServicioAPI(codCompania,num,perteneceA,card);
    }

    @Step( "CONSULTA DE SERIVICOS ADHERIDOS ")
    public void consultaServiciosAdheridosAPI(String codeCompany, String numero, String card){
        recargasAPI.consultaAdhesionDeServicioAPI(codeCompany,card,numero);
    }
}