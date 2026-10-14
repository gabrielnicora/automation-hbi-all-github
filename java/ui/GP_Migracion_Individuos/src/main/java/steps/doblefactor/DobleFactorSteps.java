package steps.doblefactor;

import api.DobleFactorAPI;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.bouncycastle.tsp.TSPUtil;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import pages.botonesgenericos.BotonesGenericosPage;
import pages.doblefactor.DoblefactorPage;
import steps.botonesgenericos.BotonesGenericosSteps;
import steps.login.LoginSteps;
import utils.UtilsGP;

import java.sql.SQLException;

public class DobleFactorSteps extends BotonesGenericosSteps {

    private DoblefactorPage doblefactor = new DoblefactorPage();
    private UtilsGP utilsGP = new UtilsGP();
    private String URL_DobleFactor = System.getProperty("dobleFactorUrl");
    private LoginSteps logApi = new LoginSteps();
    private BotonesGenericosPage botonesGenericosPage = new BotonesGenericosPage();
    DobleFactorAPI dobleFactorAPI = new DobleFactorAPI();


    @Step("Gestionar pestaña doble factor y CONFIRMAR")
    public void codigoSegundoFactor()  {
        UtilsGP.fixedWait(2000);
        if(existeOtroMetodo()){
            System.out.println("==========BOTON INTENTE OTRO METODO PRESENTE=============");
            clickOtroMetodo();
            clickSms();
        }else {
            System.out.println("================ NO CONTIENE OTRO METODO PRESENTE ===================");
        }
                
                 String dobleFactor, segundoDobleFactor, mensaje;
                 dobleFactor = dobleFactorAPI.getSecondFactor();
                 UtilsGP.fixedWait(3000);
                 doblefactor.setCodigo().sendKeys(Keys.chord(Keys.CONTROL, "a"), dobleFactor);
                 registrarCodigoSegundoFactor("Intento 1", dobleFactor, obtenerCodigoPegado());
                 clickConfirmarGenerico();
                 if (utilsGP.estaPresente(doblefactor.getMensajeCodigoIngresadoInvalidoBy(), 3)) {
                     mensaje = botonesGenericosPage.getNotificacionError().getText();
                     System.out.println("LABEL ERROR DOBLE FACTOR : " + mensaje);
                     Allure.addAttachment("2FA - Mensaje error notificacion", mensaje);

                     segundoDobleFactor = dobleFactorAPI.getSecondFactor();
                     doblefactor.setCodigo().sendKeys(Keys.chord(Keys.CONTROL, "a"), segundoDobleFactor);
                     registrarCodigoSegundoFactor("Reintento por notificacion", segundoDobleFactor, obtenerCodigoPegado());

                     clickConfirmarGenerico();
                 }else if (utilsGP.estaPresente(doblefactor.getMensajeEmergente(), 3)) {
                     mensaje = doblefactor.getMensajeEmergente().getText();
                     System.out.println("MENSAJE " + mensaje);
                     Allure.addAttachment("2FA - Mensaje emergente", mensaje);
                     segundoDobleFactor = dobleFactorAPI.getSecondFactor();
                     doblefactor.setCodigo().sendKeys(Keys.chord(Keys.CONTROL, "a"), segundoDobleFactor);
                     registrarCodigoSegundoFactor("Reintento por mensaje emergente", segundoDobleFactor, obtenerCodigoPegado());

                     clickConfirmarGenerico();
                 }
    }

    private void registrarCodigoSegundoFactor(String intento, String codigoObtenido, String codigoPegado) {
        String metodo = System.getProperty("metodo");
        String appUrl = System.getProperty("appUrl");
        String bankAPI = System.getProperty("bankAPI");

        String detalle = "Intento: " + intento + "\n"
                + "Metodo 2FA configurado: " + valorSeguro(metodo) + "\n"
                + "App URL: " + valorSeguro(appUrl) + "\n"
                + "Bank API: " + valorSeguro(bankAPI) + "\n"
                + "Codigo obtenido API/config: " + valorSeguro(codigoObtenido) + "\n"
                + "Codigo pegado en input: " + valorSeguro(codigoPegado) + "\n"
                + "Longitud codigo obtenido: " + longitud(codigoObtenido) + "\n"
                + "Longitud codigo pegado: " + longitud(codigoPegado) + "\n"
                + "Formato codigo obtenido: " + tipoCodigo(codigoObtenido) + "\n"
                + "Formato codigo pegado: " + tipoCodigo(codigoPegado) + "\n"
                + "Coinciden obtenido y pegado: " + String.valueOf(valoresCoinciden(codigoObtenido, codigoPegado));

        System.out.println("========== 2FA DEBUG ==========");
        System.out.println(detalle);
        Allure.addAttachment("2FA - Debug " + intento, detalle);
    }

    private String obtenerCodigoPegado() {
        try {
            return String.valueOf(doblefactor.setCodigo().getAttribute("value"));
        } catch (Exception e) {
            return "No se pudo leer value del input secondFactor: " + e.getMessage();
        }
    }

    private String valorSeguro(String valor) {
        return valor == null ? "null" : valor;
    }

    private int longitud(String valor) {
        return valor == null ? 0 : valor.length();
    }

    private String tipoCodigo(String valor) {
        if (valor == null) {
            return "null";
        }
        if (valor.trim().isEmpty()) {
            return "vacio";
        }
        if (valor.matches("\\d+")) {
            return "numerico";
        }
        return "alfanumerico/no numerico";
    }

    private boolean valoresCoinciden(String valorEsperado, String valorPegado) {
        if (valorEsperado == null) {
            return valorPegado == null;
        }
        return valorEsperado.equals(valorPegado);
    }

    private void clickOtroMetodo() {

        scrollTo(doblefactor.getIntentarOtroMedotodo().getElementBy());
        doblefactor.getIntentarOtroMedotodo().click();
        for(int i = 0; i<3; i++){
            if(!utilsGP.estaPresente(doblefactor.getNuevoCodigoSMS().getElementBy(),2)){
                doblefactor.getIntentarOtroMedotodo().click();
            }else break;
        }
    }

    @Step("Gestionar pestaña doble factor y CONFIRMAR, sin verificar Codigo")
    public void codigoSegundoFactorSinValidacion(String usuario) throws InterruptedException, SQLException {
        utilsGP.NuevoTab();
        utilsGP.ManageTab();
        utilsGP.NavegarUtilsWeb(URL_DobleFactor);
        doblefactor.update().click();
        if (usuario.contains("@")) {
            doblefactor.setOrigen().sendKeys(usuario);
            doblefactor.setOrigen().sendKeys(Keys.ENTER);
            System.out.println("mail");
        } else {
            setOrigen(usuario);
            System.out.println("usuario");
        }
        String df = getDobleFactor();
        utilsGP.backMainTap();
        doblefactor.setCodigo().sendKeys(Keys.chord(Keys.CONTROL, "a"), df);
        clickConfirmarGenerico();

    }
    @Step("Gestionar pestaña doble factor y CONTINUAR")
    public void codigoSegundoFactorBotonContinuar() throws InterruptedException, SQLException {
        /*utilsGP.NuevoTab();
        utilsGP.ManageTab();
        utilsGP.NavegarUtilsWeb(URL_DobleFactor);
        doblefactor.update().click();
        if (usuario.contains("@")) {
            doblefactor.setOrigen().sendKeys(usuario);
            doblefactor.setOrigen().sendKeys(Keys.ENTER);
            System.out.println("mail");
        } else {
            setOrigen(usuario);
            System.out.println("usuario");
        }
        String df = getDobleFactor();
        utilsGP.backMainTap();*/
        String df = dobleFactorAPI.getSecondFactor();
        doblefactor.setCodigo().sendKeys(Keys.chord(Keys.CONTROL, "a"), df);
        clickContinuarGenerico();
        try {
            if(utilsGP.estaPresente(botonesGenericosPage.getNotificacionError(),2)){
                String msg = botonesGenericosPage.getNotificacionError().getText();
                System.out.println("LABEL ERROR DOBLE FACTOR : " + msg);
                String dp = dobleFactorAPI.getSecondFactor();
                doblefactor.setCodigo().sendKeys(Keys.chord(Keys.CONTROL, "a"), dp);
                clickConfirmarGenerico();
            }
            if(utilsGP.estaPresente(doblefactor.getMensajeEmergente(),2)){
                String mensaje= doblefactor.getMensajeEmergente().getText();
                throw new Error("Mensaje emergente presente: " + mensaje);}
        }catch (Exception e){
            System.out.println("No hay mensaje emergente");
            //Thread.sleep(2000);
            //verificarCodigo(usuario);
        }
    }

    @Step("Setear dato de origen")
    public void setOrigen(String usuario) {
        String tel = utilsGP.consultaDB("usuarios", usuario, "telephone");
        doblefactor.setOrigen().sendKeys(tel);
        doblefactor.setOrigen().sendKeys(Keys.ENTER);
    }

    @Step("Obtener codigo de verificacion")
    public void codigoVerificacionEmail(String mail) throws InterruptedException {
        String codigoVerificacion,dobleFactor,segundoCodigoVerificacion,mensaje;

        codigoVerificacion = dobleFactorAPI.getSecondFactorOriginMail(mail);
        doblefactor.setCodigoVerificacion().sendKeys(Keys.chord(Keys.CONTROL, "a"), codigoVerificacion);
        Thread.sleep(2000);

        clickModificarGenerico();
        try {
           /* if(utilsGP.estaPresente(doblefactor.getMensajeEmergente(),1)){
                mensaje = doblefactor.getMensajeEmergente().getText();
                System.out.println("Mensaje emergente " + mensaje);
                Thread.sleep(2500);

                dobleFactor = dobleFactorAPI.getSecondFactor();
                dobleFactorAPI.emailSendCode(mail,dobleFactor);

                segundoCodigoVerificacion = dobleFactorAPI.getSecondFactorOriginMail(mail);
                doblefactor.setCodigo().sendKeys(Keys.chord(Keys.CONTROL, "a"), segundoCodigoVerificacion);

                clickModificarGenerico();
        }*/
            if (utilsGP.estaPresente(botonesGenericosPage.getNotificacionError(), 1)) {
                mensaje = botonesGenericosPage.getNotificacionError().getText();
                System.out.println("LABEL ERROR DOBLE FACTOR : " + mensaje);

                codigoVerificacion = dobleFactorAPI.getSecondFactorOriginMail(mail);
                doblefactor.setCodigo().sendKeys(Keys.chord(Keys.CONTROL, "a"), codigoVerificacion);

                clickConfirmarGenerico();
            }else if (utilsGP.estaPresente(doblefactor.getMensajeEmergente(), 1)) {
                mensaje = doblefactor.getMensajeEmergente().getText();
                System.out.println("MENSAJE " + mensaje);
                codigoVerificacion = dobleFactorAPI.getSecondFactor();
                doblefactor.setCodigo().sendKeys(Keys.chord(Keys.CONTROL, "a"), codigoVerificacion);

                clickConfirmarGenerico();
            }
        }catch (Exception e){
            System.out.println("No hay mensaje emergente de error");
            Thread.sleep(2000);
        }
    }
    @Step("Obtener codigo de verificacion")
    public String codigoVerificacionEmailRecuperoUsuario() throws InterruptedException {

        String df = dobleFactorAPI.getSecondFactorMailUser();
        System.out.println(" ========== CODIGO :"+ df);
        for(int i =0; i<=4;i++){
            if(df.equals(null)){
                df = dobleFactorAPI.getSecondFactorMailUser();
            }else{
                System.out.println("SE OBTUVO UN CODIGO VALIDO DISTINTO DE NULL");
                break;
            }
        }

        return df;
    }

    /*@Step("Gestionar doble factor para cambio de clave")
    public void codigoSegundoFactorPassword() throws InterruptedException {
        utilsGP.NuevoTab();
        utilsGP.ManageTab();
        utilsGP.NavegarUtilsWeb(URL_DobleFactor);
        String df = getDobleFactor();
        utilsGP.backMainTap();
        scrollTo(By.xpath("//button[@aria-label='Aceptar']"));
        doblefactor.setCodigo().sendKeys(Keys.chord(Keys.CONTROL, "a"), df);
        clickAceptarGenerico();
    }*/


    public String getDobleFactor() {
        Assert.assertTrue("Codigo no visible", doblefactor.getCodigo().isDisplayed());
        return doblefactor.getCodigo().getText();
    }

    public Boolean existeCodigoInvalido() {
        WebDriver driver = global.practia.jemeinsamstark.web.core.WebDriver.getInstance();
        return !driver.findElements(By.cssSelector("[class*='errorLabel']")).isEmpty();
    }

   /* @Step("Se valida mensaje de codigo invalido")
    public void verificarCodigo(String usuario) throws InterruptedException, SQLException {
        if (existeCodigoInvalido()) {
            doblefactor.getNuevoCodigoSMS().click();
            WebDriverWait wait = new WebDriverWait(global.practia.jemeinsamstark.web.core.WebDriver.getInstance(), 10);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='notification notification-success notification-visible']")));
            codigoSegundoFactor(usuario);
        }
    }*/

    public Boolean existeOtroMetodo() {
        WebDriver driver = global.practia.jemeinsamstark.web.core.WebDriver.getInstance();
        return !driver.findElements(By.xpath("//span[contains(text(), 'Intentar con otro')]")).isEmpty();
    }
    public void intentarConOtroMetodo() {
        doblefactor.getIntentarOtroMedotodo().click();
    }

    @Step("Gestionar doble factor con click en otro metodo")
    public void codigoSegundoFactorOtroMetodo() throws InterruptedException, SQLException {
        try {
            scrollTo(By.id("secondFactor"));
            String mensaje,segundoDobleFactor;
            Assert.assertTrue("NO esta el boton Intentar con otro metodo", doblefactor.getIntentarOtroMedotodo().isDisplayed());
            doblefactor.getIntentarOtroMedotodo().click();

            segundoDobleFactor = dobleFactorAPI.getSecondFactor();
            doblefactor.setCodigo().sendKeys(Keys.chord(Keys.CONTROL, "a"), segundoDobleFactor);

            UtilsGP.fixedWait(2000);
            clickConfirmarGenerico();

            if (utilsGP.estaPresente(botonesGenericosPage.getNotificacionError(), 1)) {
                mensaje = botonesGenericosPage.getNotificacionError().getText();
                System.out.println("LABEL ERROR DOBLE FACTOR : " + mensaje);

                segundoDobleFactor = dobleFactorAPI.getSecondFactor();
                doblefactor.setCodigo().sendKeys(Keys.chord(Keys.CONTROL, "a"), segundoDobleFactor);
                clickConfirmarGenerico();
               UtilsGP.fixedWait(2000);

                clickConfirmarGenerico();
            }/*else if (utilsGP.estaPresente(doblefactor.getMensajeEmergente(), 2)) {
                mensaje = doblefactor.getMensajeEmergente().getText();
                System.out.println("MENSAJE " + mensaje);
                segundoDobleFactor = dobleFactorAPI.getSecondFactor();
                doblefactor.setCodigo().sendKeys(Keys.chord(Keys.CONTROL, "a"), segundoDobleFactor);

                clickConfirmarGenerico();
            }*/
        }catch(Exception e){
            System.out.println("No se encontro nincgum mensaje emergente 2fA");
        }
    }

   /* @Step("Se obtiene codigo de verificacion para blanqueo")
    public void codigoVerificacionBlanqueo() throws InterruptedException {
        utilsGP.NuevoTab();
        utilsGP.ManageTab();
        utilsGP.NavegarUtilsWeb(URL_DobleFactor);
        String df = getDobleFactor();
        String codigoExtraido = StringUtils.substringBetween(df, "verificacion: ", " Banco");
        utilsGP.backMainTap();
        doblefactor.getCodigoVerificacion().sendKeys(Keys.chord(Keys.CONTROL, "a"), codigoExtraido);
        clickContinuarGenerico();
    }*/

    @Step("Gestionar código verificacion Telefono")
    public void codigoVerificacionTelefono(String numero) throws InterruptedException, SQLException {
        String codigoVerificacion,dobleFactor,segundoCodigoVerificacion;

        codigoVerificacion = dobleFactorAPI.getSecondFactorVerificationCode(numero);
        System.out.println("Cod. verificacion: " + codigoVerificacion);

        doblefactor.setCodigoVerificacionTel().sendKeys(Keys.chord(Keys.CONTROL, "a"), codigoVerificacion);
        Thread.sleep(2000);
        clickModificarGenerico();

        try {
            if(utilsGP.estaPresente(doblefactor.getMensajeEmergente(),2)) {
                String mensaje = doblefactor.getMensajeEmergente().getText();
                System.out.println("Mensaje emergente " + mensaje);
                Thread.sleep(2500);

                dobleFactor = dobleFactorAPI.getSecondFactorOriginMail();
                dobleFactorAPI.sendCode(dobleFactor,numero);

                segundoCodigoVerificacion = dobleFactorAPI.getSecondFactorVerificationCode(numero);
                doblefactor.setCodigo().sendKeys(Keys.chord(Keys.CONTROL, "a"), segundoCodigoVerificacion);
                clickConfirmarGenerico();
            }
        }catch (Exception e){
            System.out.println("No hay mensaje emergente de error");
        }
    }
    @Step("Codigo sms click")
    public void clickSms() {
        //scrollTo(By.xpath("//*[contains(text(),'Intentar con otro método')]"));
        //doblefactor.getIntentarOtroMedotodo().click();
        doblefactor.getNuevoCodigoSMS().click();
    }
    @Step("Gestionar codigo doble factor de email para Cambio de telefono")
    public void codigoSegundoFactorParaCambioTelefono(){
        try {
            String dobleFactor,segundoDobleFactor,mensaje;

            dobleFactor = dobleFactorAPI.getSecondFactorOriginMail();
            doblefactor.setCodigo().sendKeys(Keys.chord(Keys.CONTROL, "a"), dobleFactor);

            clickConfirmarGenerico();
                if(utilsGP.estaPresente(doblefactor.getMensajeEmergente(),4)){
                    mensaje= doblefactor.getMensajeEmergente().getText();
                    segundoDobleFactor = dobleFactorAPI.getSecondFactorOriginMail();
                    doblefactor.setCodigo().sendKeys(Keys.chord(Keys.CONTROL, "a"), segundoDobleFactor);
                    clickConfirmarGenerico();
                }
                if(utilsGP.estaPresente(botonesGenericosPage.getNotificacionError(),2)){
                    mensaje = botonesGenericosPage.getNotificacionError().getText();
                    System.out.println("LABEL ERROR DOBLE FACTOR : " + mensaje);

                    segundoDobleFactor = dobleFactorAPI.getSecondFactorOriginMail();
                    doblefactor.setCodigo().sendKeys(Keys.chord(Keys.CONTROL, "a"), segundoDobleFactor);

                    clickConfirmarGenerico();
                }
        }catch (Exception e){
            System.out.println("No hay mensaje emergente de error");
        }
    }

    //// Enrolamiento ////

    @Step("Gestionar pestaña doble factor de enrolamiento y CONFIRMAR")
    public void codigoSegundoFactorDeEnrolamiento()  {

        if(existeOtroMetodo()){
            System.out.println("==========BOTON INTENTE OTRO METODO PRESENTE=============");
            clickOtroMetodo();
            clickSms();
        }else {
            System.out.println("================ NO CONTIENE OTRO METODO PRESENTE ===================");
        }

        String dobleFactor, segundoDobleFactor, mensaje;
        dobleFactor = dobleFactorAPI.getSecondFactorValidateEnrolamiento();
        UtilsGP.fixedWait(3000);
        doblefactor.setCodigo().sendKeys(Keys.chord(Keys.CONTROL, "a"), dobleFactor);
        clickConfirmarGenerico();
        if (utilsGP.estaPresente(botonesGenericosPage.getNotificacionError(), 3)) {
            mensaje = botonesGenericosPage.getNotificacionError().getText();
            System.out.println("LABEL ERROR DOBLE FACTOR : " + mensaje);

            segundoDobleFactor = dobleFactorAPI.getSecondFactorValidateEnrolamiento();
            doblefactor.setCodigo().sendKeys(Keys.chord(Keys.CONTROL, "a"), segundoDobleFactor);

            clickConfirmarGenerico();
        }else if (utilsGP.estaPresente(doblefactor.getMensajeEmergente(), 3)) {
            mensaje = doblefactor.getMensajeEmergente().getText();
            System.out.println("MENSAJE " + mensaje);
            segundoDobleFactor = dobleFactorAPI.getSecondFactorValidateEnrolamiento();
            doblefactor.setCodigo().sendKeys(Keys.chord(Keys.CONTROL, "a"), segundoDobleFactor);

            clickConfirmarGenerico();
        }
    }

    @Step("Gestionar pestaña doble factor de enrolamiento y CONFIRMAR")
    public void codigoSegundoFactorDeEnrolamientoPinTarjetaDebito()  {

        if(existeOtroMetodo()){
            System.out.println("==========BOTON INTENTE OTRO METODO PRESENTE=============");
            clickOtroMetodo();
            clickSms();
        }else {
            System.out.println("================ NO CONTIENE OTRO METODO PRESENTE ===================");
        }

        String dobleFactor, segundoDobleFactor, mensaje;
        dobleFactor = dobleFactorAPI.getSecondFactorValidateEnrolamientoPinTarjetaDebito();
        UtilsGP.fixedWait(3000);
        doblefactor.setCodigo().sendKeys(Keys.chord(Keys.CONTROL, "a"), dobleFactor);
        clickConfirmarGenerico();
        if (utilsGP.estaPresente(botonesGenericosPage.getNotificacionError(), 3)) {
            mensaje = botonesGenericosPage.getNotificacionError().getText();
            System.out.println("LABEL ERROR DOBLE FACTOR : " + mensaje);

            segundoDobleFactor = dobleFactorAPI.getSecondFactorValidateEnrolamientoPinTarjetaDebito();
            doblefactor.setCodigo().sendKeys(Keys.chord(Keys.CONTROL, "a"), segundoDobleFactor);

            clickConfirmarGenerico();
        }else if (utilsGP.estaPresente(doblefactor.getMensajeEmergente(), 3)) {
            mensaje = doblefactor.getMensajeEmergente().getText();
            System.out.println("MENSAJE " + mensaje);
            segundoDobleFactor = dobleFactorAPI.getSecondFactorValidateEnrolamientoPinTarjetaDebito();
            doblefactor.setCodigo().sendKeys(Keys.chord(Keys.CONTROL, "a"), segundoDobleFactor);

            clickConfirmarGenerico();
        }
    }

    @Step("Click sobre botón -Util Logger-")
    public void clickBtnUtilLogger() {

        utilsGP.fixedWait(4000);
        doblefactor.gettUtilLogger().click();

    }
}
