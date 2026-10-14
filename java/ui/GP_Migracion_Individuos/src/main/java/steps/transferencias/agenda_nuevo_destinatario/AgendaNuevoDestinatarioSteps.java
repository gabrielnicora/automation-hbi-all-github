package steps.transferencias.agenda_nuevo_destinatario;

import api.TransferenciasAPI;
import io.qameta.allure.Step;
import org.codehaus.jettison.json.JSONException;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.transferencias.agenda_nuevo_destinatario.AgendaNuevoDestinatarioPage;
import steps.botonesgenericos.BotonesGenericosSteps;
import utils.MetodosAPI;
import utils.UtilsGP;
import pages.botonesgenericos.BotonesGenericosPage;

public class AgendaNuevoDestinatarioSteps extends BotonesGenericosSteps {

   AgendaNuevoDestinatarioPage agendaNuevoDestinatarioPage= new AgendaNuevoDestinatarioPage();
   TransferenciasAPI transferenciasAPI = new TransferenciasAPI();
   MetodosAPI metodosAPI = new MetodosAPI();
   BotonesGenericosPage botonesGenericosPage = new BotonesGenericosPage();



    @Step("Click en Adherir nuevo destinatario")
    public void clickAdherirNuevoDestinatario(){
        scrollTo(By.xpath("//span[contains(text(),'Agregar contacto')]"));
        agendaNuevoDestinatarioPage.getAdherirNuevoDestinatario().isDisplayed();
        agendaNuevoDestinatarioPage.getAdherirNuevoDestinatario().click();
    }
    @Step("Ingresar CBU {cbuAlias}")
    public void setInputCbuAlias(String cbuAlias){
        wait.until(ExpectedConditions.elementToBeClickable(By.id("cbuSearch")));
        agendaNuevoDestinatarioPage.getInputCbuAlias().click();
        agendaNuevoDestinatarioPage.getInputCbuAlias().sendKeys(cbuAlias);
    }
    @Step("Validar mensaje de cbu invalido")
    public void validarMensajeCbuInvalido(){
        agendaNuevoDestinatarioPage.getMensajeCbuInvalido().isDisplayed();
    }
    @Step("Validar mensaje de Alias invalido")
    public void validarMensajeAliasInvalido(){
        agendaNuevoDestinatarioPage.getMensajeAliasInvalido().isDisplayed();
    }

    @Step("Adherir nuevo destinatario CBU API")
    public void adherirNuevoDestinatarioCBU(String DestinatarioCBU,String PerteneceA) throws InterruptedException, JSONException {
        transferenciasAPI.agregarDestinatarioAgendaCBU(DestinatarioCBU,PerteneceA);
    }
    @Step("Click buscar")
    public void clickBuscar(){
        agendaNuevoDestinatarioPage.getBuscar().click();
    }

    @Step("Validar datos de la cuenta")
    public void validarDatosCuenta(String cbu, String titular, String cuit){
        try{
            Assert.assertEquals(cbu, agendaNuevoDestinatarioPage.getCbu().getText());
            Assert.assertEquals(titular, agendaNuevoDestinatarioPage.getTitular().getText());
            Assert.assertEquals(cuit, agendaNuevoDestinatarioPage.getCuit().getText());
        }
      catch (Exception e){
          System.out.println("Alguno de los datos no pudo ser validado correctamente");
          e.getMessage();
      }
    }

    @Step("Completar Pertenece A")
    public void setPerteneceA(String perteneceA){
        //scrollTo(By.id("belongsTo"));
        scrollTo(By.id("reference"));
        agendaNuevoDestinatarioPage.getInputPerteneceA().sendKeys(perteneceA);
    }
    @Step("Click label Alias")
    public void clickLabelAlias(){
        agendaNuevoDestinatarioPage.getLabelAlias().click();
    }


    @Step("Eliminar contacto agendado")
    public void eliminarContactoAgendado(String contactoAgendado){
        Assert.assertTrue("No se encuentra - Input Buscar Contacto", agendaNuevoDestinatarioPage.getInputBuscarContacto().isDisplayed());
        agendaNuevoDestinatarioPage.getInputBuscarContacto().sendKeys(contactoAgendado);
        Assert.assertTrue("No se encuentra boton - Tres Puntos", agendaNuevoDestinatarioPage.getTresPuntos(contactoAgendado).isDisplayed());
        scrollTo(By.xpath("//p[contains(text(),'"+contactoAgendado+"')]/../../div[last()]"));
        agendaNuevoDestinatarioPage.getTresPuntos(contactoAgendado).click();
        Assert.assertTrue("No se encuentra boton - Eliminar", agendaNuevoDestinatarioPage.getEliminar().isDisplayed());
        agendaNuevoDestinatarioPage.getEliminar().click();
        Assert.assertTrue("No se encuentra boton - Si, Eliminar", agendaNuevoDestinatarioPage.getConfirmarEliminacionContacto().isDisplayed());
        agendaNuevoDestinatarioPage.getConfirmarEliminacionContacto().click();
    }

    @Step("Click Cuenta terceros")
    public void clickCuentaTerceros(){

        agendaNuevoDestinatarioPage.getLabelCuentaTerceros().click();
    }

    @Step("Eliminar destinatario agendado API")
    public void eliminarDestinatarioAgendadoAPI(String aliasCbu) {
        transferenciasAPI.eliminarContantoAgendado(aliasCbu);
    }

    @Step("Eliminar Contacto en Agenda")
    public void eliminaContactoEnAgenda(String tercero, String texto){

        UtilsGP.fixedWait(6000);
        agendaNuevoDestinatarioPage.getInputBuscarContacto().sendKeys(tercero);
        UtilsGP.fixedWait(6000);

        if (!botonesGenericosPage.getSeleccionarServicio2(texto).isDisplayed()) {

            scrollTo(agendaNuevoDestinatarioPage.getTresPieDPag().getElementBy());
            agendaNuevoDestinatarioPage.getTresPuntosAlternativo().click();
            UtilsGP.fixedWait(6000);
            scrollTo(By.xpath("//span[contains(text(), 'Eliminar')]"));

            agendaNuevoDestinatarioPage.getEliminar().click();
            agendaNuevoDestinatarioPage.getConfirmarEliminacionContacto().click();
        }
    }


}
