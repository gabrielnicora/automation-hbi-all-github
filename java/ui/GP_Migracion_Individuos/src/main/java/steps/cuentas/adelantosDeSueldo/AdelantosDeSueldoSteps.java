package steps.cuentas.adelantosDeSueldo;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import pages.botonesgenericos.BotonesGenericosPage;
import pages.cuentas.CuentasPage;
import pages.cuentas.adelantosDeSueldo.AdelantosDeSueldoPage;
import steps.botonesgenericos.BotonesGenericosSteps;
import steps.login.LoginSteps;
import utils.UtilsGP;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;


public class AdelantosDeSueldoSteps extends BotonesGenericosSteps {

    AdelantosDeSueldoPage adelantosDeSueldoPage = new AdelantosDeSueldoPage();
    LoginSteps loginSteps= new LoginSteps();


   BotonesGenericosPage botonesGenericos = new BotonesGenericosPage();


    @Step("Click en Ver Mas de cuenta {nroCuenta}") //Ej: "CA $ 000003555304"
    public void clickVerMasXnroCuenta(String nroCuenta){

        adelantosDeSueldoPage.getVerMasXnroCuenta(nroCuenta).isDisplayed();
        adelantosDeSueldoPage.getVerMasXnroCuenta(nroCuenta).click();
    }
    @Step("Click Mas Opciones Generico")
    public void clickMasOpcionesGenerico(){
        adelantosDeSueldoPage.getMasOpciones().isDisplayed();
        adelantosDeSueldoPage.getMasOpciones().click();
    }
    @Step("Click Solicitud de adelanto de Sueldo")
    public void clickSolicitudAdelantoDeSueldo(){
        adelantosDeSueldoPage.getAdelantoDeSueldo().isDisplayed();
        adelantosDeSueldoPage.getAdelantoDeSueldo().click();
        //loginSteps.clickSalirPrestamo();
    }


    @Step("Validar pantalla completa de Adelantos de sueldo")
    public void validacionPantallaSolicitudAH() {
        if(!UtilsGP.estaPresente(adelantosDeSueldoPage.getNoOfertaDisponible(),2)) {
            //HAY OFERTA DISPONIBLE
            System.out.println("HAY OFERTA DISPONIBLE");
            List<String> campos = Arrays.asList(
                    "Solicitud de adelanto",
                    "solicitar entre $",
                    "El importe solicitado más el interés se devolverá en la próxima acreditación de sueldo.",
                    "El monto de interés se calculará según la cantidad de días que transcurran desde que solicites el adelanto hasta que cobres tu próximo sueldo.",
                    "Resumen de contrato",
                    "Mis adelantos");
            for (int i = 0; i < campos.size(); i++) {
                scrollTo(By.xpath(String.format("//*[contains(text(), '%s')]", campos.get(i))));
                Assert.assertTrue("El campo '" + campos.get(i) + "' no esta visible", UtilsGP.estaPresente(adelantosDeSueldoPage.textos(campos.get(i)),2 ));


                Assert.assertTrue("Boton Continuar no se encuentra disponible", botonesGenericos.getContinuar().isEnabled());
            }
        }else{
            //NO HAY OFERTA DISPONIBLE
            System.out.println("NO HAY OFERTA DISPONIBLE");
            List<String> campos = Arrays.asList(
                    "Adelanto de sueldo",
                    "Resumen de contrato",
                    "Mis adelantos",
                    "En este momento no podemos procesar tu solicitud",
                    "No contamos con una oferta disponible para vos!");
            for (int i = 0; i < campos.size(); i++) {
                scrollTo(By.xpath(String.format("//*[contains(text(), '%s')]", campos.get(i))));
                Assert.assertTrue("El campo '" + campos.get(i) + "' no esta visible", UtilsGP.estaPresente(adelantosDeSueldoPage.textos(campos.get(i)),2 ));
            }


        }
        System.out.println("Titulos validados con exito");
    }
    @Step("Set Cuanto necesitas adelando de sueldo")
    public void setCuantoNecesitas(String monto){
        Assert.assertTrue("NO SE ENCONTRO EL ElEMENTO :"+adelantosDeSueldoPage.getCuantoNecesitas().getElementBy(),adelantosDeSueldoPage.getCuantoNecesitas().isPresent());
        adelantosDeSueldoPage.getCuantoNecesitas().sendKeys(monto);
    }
    @Step("Validar pantalla completa 'Confirmacion de solicitud'")
    public void validacionCamposConfirmacion() {
        List<String> campos = Arrays.asList(
                "Confirmación",
                "Número de cuenta",
                "Monto total del préstamo",
                "Sellados",
                "Dinero a acreditar en la cuenta",
                "T.E.A",
                "T.N.A",
                "CFT T.E.A",
                "El importe solicitado más el interés se devolverá en la próxima acreditación de sueldo.",
                "El monto de interés se calculará según la cantidad de días que transcurran desde que solicites el adelanto hasta que cobres tu próximo sueldo.",
                "Acepta términos y condiciones",
                "Información sobre acreditación",
                "La acreditación se verá reflejada en el transcurso del día.",
                "Confirmar");
        for (String campo : campos) {
            scrollTo(By.xpath(String.format("//*[contains(text(), '%s')]", campo)));
            Assert.assertTrue("El campo '" + campo + "' no esta visible", adelantosDeSueldoPage.textos(campo).isDisplayed());
            //Assert.assertTrue("Boton confirmar no se encuentra disponible", botonesGenericos.getConfirmar().isEnabled());
        }
        System.out.println("Titulos validados con exito");
    }
    @Step("Validar campos requeridos {campos}")
    public void validarCamposRequeridos(String campos) throws UnsupportedEncodingException {
        Assert.assertTrue("Titulo no visible", adelantosDeSueldoPage.getCampoRequerido(campos).isDisplayed());
    }
    @Step("Click Resumen de Contrato")
    public void clickResumenDeContrato(){
        adelantosDeSueldoPage.getResumenDeContrato().isDisplayed();
        adelantosDeSueldoPage.getResumenDeContrato().click();
    }
    @Step("Click Mis Adelantos")
    public void clickMisAdelantos(){
        adelantosDeSueldoPage.getMisAdelantos().click();
    }
    @Step("Validar Card Mis adelantos")
    public void validacionCardMisAdelantos() {
        List<String> campos = Arrays.asList(
                "Mis adelantos",
                "Cuenta",
                "Tasas",
                "$",
                "TNA 0,01% TEA 25%");
        for (String campo : campos) {
            String campo1 = new String(campo.getBytes(),
                    StandardCharsets.UTF_8);
            scrollTo(By.xpath(String.format("//*[contains(text(), '%s')]", campo1)));
            Assert.assertTrue("El campo '" + campo1 + "' no esta visible", adelantosDeSueldoPage.textos(campo1).isDisplayed());
        }
        System.out.println("Titulos validados con exito");
    }
    @Step("Validar alert monto permitido")
    public void validarAlertMontoPermitido() {
        String montosPermitidos = adelantosDeSueldoPage.getMontoPermitido().getText();
        System.out.println("Alert - Monto invalido - " + montosPermitidos);
        Assert.assertTrue("Alert de Monto permitidos no esta disponible", adelantosDeSueldoPage.getMontoPermitido().isDisplayed());
    }
    @Step("Click Acceso directo Adelanto de Sueldo")
    public void clickAccesoDirectoAdelantoSueldo() {
        scrollTo(adelantosDeSueldoPage.getAccesoDirectoAdelantoSueldo().getElementBy());
        adelantosDeSueldoPage.getAccesoDirectoAdelantoSueldo().click();
        loginSteps.clickSalirPrestamo();
    }
    @Step("Click Card Adelanto de Sueldo")
    public void clickCardAdelantoSueldo() {
        adelantosDeSueldoPage.getCardAdelantoSueldo().click();
    }
    @Step("Click Check tyc")
    public void clickCheckTyC() {
        adelantosDeSueldoPage.getCheckTyC().click();

    }
    @Step("Seleccionar desplegable cuenta origen si es visible")
    public void seleccionarDesplegableCuentaOrigen() {
        if(adelantosDeSueldoPage.getDesplegableCuentaOrigen().isDisplayed()){
            adelantosDeSueldoPage.getDesplegableCuentaOrigen().click();
        }
        else {
            System.out.println("El desplegable de cuenta origen no está disponible");
        }
    }
}
