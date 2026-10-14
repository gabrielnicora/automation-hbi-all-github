package steps.ticket;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.ticket.TicketPage;
import steps.botonesgenericos.BotonesGenericosSteps;
import steps.inversiones.fci.SuscripcionFCISteps;
import utils.UtilsGP;

import java.util.LinkedHashMap;

public class TicketSteps extends BotonesGenericosSteps {
    private TicketPage ticket = new TicketPage();
    private UtilsGP utilsGP = new UtilsGP();
    private SuscripcionFCISteps suscripcionFCISteps = new SuscripcionFCISteps();

    @Step("Validar Transaccion")
    public void validarTicket(String estadoEsp){
        //Assert.assertTrue("NO SE ENCONTRO EL TICKET ", ticket.getEstadoTicket().isDisplayed());
        ticket.getEstadoTicket().isDisplayed();
        String cadenaSinAcentos = utilsGP.removerAcentos(ticket.getEstadoTicket().getText());
        Assert.assertEquals(estadoEsp,cadenaSinAcentos);
    }
    @Step("Validar campos y valor del ticket")
    public void validarCampoValorTicket(String campo,String valorEspeado){
        scrollTo(By.xpath(String.format("(//span[text()='%s']/../following::p)[1]",campo)));
        String cadenaSinAcentos = utilsGP.removerAcentos(ticket.getCampoValorTicket(campo).getText());
        Assert.assertTrue("ERROR CAMPO "+"\""+campo+"\""+" En el ticket",cadenaSinAcentos.contains(valorEspeado));
        System.out.println("SE VALIDA CAMPO: "+"\""+campo+"\""+ " DENTRO DEL TICKET");
    }

    @Step("Click en Descargar")
    public void clickDescargas() {
        Assert.assertTrue("NO SE ENCONTRO EL BOTON",ticket.getBotonDescarga().isDisplayed());
        ticket.getBotonDescarga().click();
    }
    @Step("Click descarga Bono de salud")
    public void clickDescargaBono(){
        Assert.assertTrue("NO SE ENCONTRO EL BOTON",ticket.getBotonDescargaBono().isDisplayed());
        ticket.getBotonDescargaBono().click();
    }

    @Step("Click en Imprimir")
    public void clickImprimir() {
        ticket.getBotonImprimir().isEnabled();
        ticket.getBotonImprimir().click();
    }
    @Step("Validar campos y valor del ticket")
    public void validarCamposTicketFCI(LinkedHashMap<String, String> datos,String fecha){
        String campo = "";
        String cadenaSinAcentos ="";
        /*campo = "Operación";
        scrollTo(By.xpath(String.format("(//span[text()='%s']/../following::p)[1]",campo)));
        cadenaSinAcentos = utilsGP.removerAcentos(ticket.getCampoValorTicket(campo).getText());
        Assert.assertTrue("ERROR CAMPO "+"\""+campo+"\""+ " En el ticket",cadenaSinAcentos.contains("Suscripcion de FCI"));
        System.out.println("SE VALIDA CAMPO: "+"\""+campo+"\""+ " DENTRO DEL TICKET");
        cadenaSinAcentos=null;*/

        campo = "Número de operación";
        scrollTo(By.xpath(String.format("(//span[text()='%s']/../following::p)[1]",campo)));
        cadenaSinAcentos = utilsGP.removerAcentos(ticket.getCampoValorTicket(campo).getText());
        Assert.assertTrue("ERROR CAMPO "+"\""+campo+"\""+ " En el ticket",!cadenaSinAcentos.isEmpty());
        System.out.println("SE VALIDA CAMPO: "+"\""+campo+"\""+ " DENTRO DEL TICKET");

        campo = "Nombre del Fondo Común de inversión";
        scrollTo(By.xpath(String.format("(//span[text()='%s']/../following::p)[1]",campo)));
        cadenaSinAcentos = ticket.getCampoValorTicket(campo).getText();
        Assert.assertTrue("ERROR CAMPO "+"\""+campo+"\""+ " En el ticket",cadenaSinAcentos.equals(datos.get(campo)));
        System.out.println("SE VALIDA CAMPO: "+"\""+campo+"\""+  " DENTRO DEL TICKET");

        campo = "Fecha";
        scrollTo(By.xpath(String.format("(//span[text()='%s']/../following::p)[1]",campo)));
        cadenaSinAcentos = ticket.getCampoValorTicket(campo).getText();
        Assert.assertTrue("ERROR CAMPO "+"\""+campo+"\""+ " En el ticket",cadenaSinAcentos.contains(new UtilsGP().fechaActual()));
        System.out.println("SE VALIDA CAMPO: "+"\""+campo+"\""+  " DENTRO DEL TICKET");

        campo = "Fecha de suscripción";
        scrollTo(By.xpath(String.format("(//span[text()='%s']/../following::p)[1]",campo)));
        cadenaSinAcentos =ticket.getCampoValorTicket(campo).getText();
        Assert.assertTrue("ERROR CAMPO "+"\""+campo+"\""+ " En el ticket",cadenaSinAcentos.equals(fecha));
        System.out.println("SE VALIDA CAMPO: "+"\""+campo+"\""+  " DENTRO DEL TICKET");

        campo = "Cuenta comitente";
        scrollTo(By.xpath(String.format("(//span[text()='%s']/../following::p)[1]",campo)));
        cadenaSinAcentos = ticket.getCampoValorTicket(campo).getText();
        Assert.assertTrue("ERROR CAMPO "+"\""+campo+"\""+ " En el ticket",cadenaSinAcentos.equals(datos.get(campo)));
        System.out.println("SE VALIDA CAMPO: "+"\""+campo+"\""+  " DENTRO DEL TICKET");

        campo = "Cuenta débito";
        String cadena ="";
        scrollTo(By.xpath(String.format("(//span[text()='%s']/../following::p)[1]",campo)));
        cadenaSinAcentos = ticket.getCampoValorTicket(campo).getText();
        cadena = datos.get(campo).contains("Caja de Ahorro") ? datos.get(campo).replace("Caja de Ahorro","CA") : datos.get(campo).replace("Cuenta Corriente","CC");
        Assert.assertTrue("ERROR CAMPO "+"\""+campo+"\""+ " En el ticket",cadenaSinAcentos.equals(cadena));
        System.out.println("SE VALIDA CAMPO: "+"\""+campo+"\""+  " DENTRO DEL TICKET");

        campo = "Cantidad estimada de cuotapartes";
        scrollTo(By.xpath(String.format("(//span[text()='%s']/../following::p)[1]",campo)));
        cadenaSinAcentos = ticket.getCampoValorTicket(campo).getText();
        Assert.assertTrue("ERROR CAMPO "+"\""+campo+"\""+ " En el ticket",cadenaSinAcentos.equals(datos.get(campo)));
        System.out.println("SE VALIDA CAMPO: "+"\""+campo+"\""+  " DENTRO DEL TICKET");

        campo = "Importe a Suscribir";
        scrollTo(By.xpath(String.format("(//span[text()='%s']/../following::p)[1]",campo)));
        cadenaSinAcentos = ticket.getCampoValorTicket(campo).getText();
        Assert.assertTrue("ERROR CAMPO "+"\""+campo+"\""+ " En el ticket",cadenaSinAcentos.equals(datos.get(campo)));
        System.out.println("SE VALIDA CAMPO: "+"\""+campo+"\""+  " DENTRO DEL TICKET");
    }

}
