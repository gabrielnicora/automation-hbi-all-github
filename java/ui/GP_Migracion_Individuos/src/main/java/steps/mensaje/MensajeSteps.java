package steps.mensaje;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.mensaje.MensajePage;
import utils.UtilsGP;

import java.awt.*;
import java.awt.event.KeyEvent;

public class MensajeSteps {
    private MensajePage mensaje = new MensajePage();
    public static String textoUltimoMensaje = "";
    UtilsGP utilsGP = new UtilsGP();
    @Step("Click en Mensajes")
    public void clickMensajes(){mensaje.getMensajes().click();}
    @Step("Click en Recibidos")
    public void clickRecibidos(){mensaje.getMensajeRecibido().click();}
    @Step("Click Primer Mensaje")
    public void clickPrimerMensaje(){
        Assert.assertTrue("NO SE ENCONTRO EL PRIMER MENSAJE "+mensaje.getAbrirMensaje().getElementBy(), mensaje.getAbrirMensaje().isDisplayed());
        mensaje.getAbrirMensaje().click();}

    @Step("Click en Descarga")
    public void clickDescargar() throws AWTException, InterruptedException {
        mensaje.getDescargarMensaje().click();
    }

    @Step("Click en Sin Leer")
    public void clickSinLeer(){
        Assert.assertTrue("NO SE ENCONTRO EL BOTON MENSAJES SIN LEER "+ mensaje.getMensajeSinLeer().getElementBy(),mensaje.getMensajeSinLeer().isDisplayed());
        mensaje.getMensajeSinLeer().click();}
    @Step("Click en Leidos")
    public void clickLeidos(){
        Assert.assertTrue("NO SE ENCONTRO EL BOTON MENSAJE LEIDOS " + mensaje.getMensajeLeidos().getElementBy(),mensaje.getMensajeLeidos().isDisplayed());
        mensaje.getMensajeLeidos().click();
    }
    @Step("Click en Enviados")
    public void clickEnviados(){mensaje.getMensajeEnviados().click();}
    @Step("Click en Cargar Mas Mensajes")
    public void clickCargarMasMsj(){mensaje.getCargarMasMensajes().click();}
    @Step("Click en Lupa")
    public void clickLupa(){mensaje.getLupa().click();}
    @Step("Click en Tres Puntos")
    public void clickEnTresPuntos(){
        //tomarTextoUltimoMensaje();
        mensaje.getTresPuntos().click();
    }
    @Step("Validar Titulo Msj")
    public void validarTituloMsj(String tituloMsj){
        Assert.assertTrue("Titulo no visible", mensaje.getTituloPrimerMensaje(tituloMsj).isDisplayed());
    }
    @Step("Buscar Mensaje")
    public void setBuscarMsj(String buscarMsj){
        mensaje.getBuscarMsj().sendKeys(buscarMsj);
    }
    @Step("Validar Elemento elegido")
    public void validarTipoMsjElegido(){
        Assert.assertTrue("Elemento no seleccionado", mensaje.getElementoElegido().isDisplayed());
    }

    @Step("Eliminar Mensaje")
    public void clickEliminarMsj() {
        mensaje.getEliminarMsj().click();
    }
    @Step("Marcar Como Leido")
    public void clickMarcarComoLeido() {
        mensaje.getMarcarComoLeido().click();
    }
    @Step("Click Salir Otra Propuesta Mensaje")
    public void salirOtraPropuesta() {
        if(mensaje.getElementoElegido().isEnabled()){
            mensaje.btnSalirOtraPropuesta().click();
        }else {
            Assert.assertTrue("Elemento no seleccionado", mensaje.getElementoElegido().isDisplayed());
        }
    }
    @Step("Tomar texto ultimo mensaje")
    public void tomarTextoUltimoMensaje() {
        textoUltimoMensaje =  mensaje.getTextoUltimoMensaje().getText();
    }
    @Step("Validar eliminación último mensaje")
    public void validarEliminacionUltimoMensaje() {
        UtilsGP.fixedWait(3000);
        Assert.assertTrue("No se elimino el último mensaje", (mensaje.getTextoUltimoMensaje().getText() != textoUltimoMensaje));

    }
}
