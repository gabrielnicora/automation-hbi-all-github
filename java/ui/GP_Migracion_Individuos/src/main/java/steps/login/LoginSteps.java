package steps.login;

import api.LoginAPI;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Assert;
import org.junit.jupiter.api.MethodOrderer;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import pages.botonesgenericos.BotonesGenericosPage;
import pages.login.LoginPage;
import steps.botonesgenericos.BotonesGenericosSteps;
import steps.menusuperior.MenuSuperiorSteps;
import utils.Constantes;
import utils.UtilsGP;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import static global.practia.jemeinsamstark.web.core.ConfigHelper.getAppDefaultWait;
import static org.junit.Assert.*;
import static utils.Settings.nroDeOperacion;


public class LoginSteps extends BotonesGenericosSteps {

    private LoginPage login = new LoginPage();

    private LoginAPI loginAPI = new LoginAPI();
    BotonesGenericosPage botonesGenericosPage= new BotonesGenericosPage();



    UtilsGP ultisGP = new UtilsGP();
    @Step("Ingresar el usuario")
    public void setUsuario(String usuario) {
        login.getInputUser().sendKeys(usuario);
    }

    @Step("Esperar Home Banking")
    public void waitGP() {
        global.practia.jemeinsamstark.web.core.WebDriver.getInstance().manage().timeouts().implicitlyWait(getAppDefaultWait(), TimeUnit.SECONDS);
        int idx = 0;
        while (idx < 3) {
            idx++;
            try {
                if (login.waitLogo().isDisplayed())
                    break;
            } catch (Exception e) {
                System.out.println("================ La página aún no se cargo... ================");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    @Step("Ingresar el password")
    public void setPassword(String password) {
        login.getInputPassword().sendKeys(password);
    }

    @Step("Repetir password")
    public void setRepetirPassword(String repetirPassword) {
        login.getInputRepetirPassword().sendKeys(repetirPassword);
    }

    @Step("Click en Ingresar StartPage")
    public void clickIngresarStartPage() {
        UtilsGP.fixedWait(3000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@aria-label='Ingresar']")));
        login.getIngresarStartPage().click();
    }

    @Step("Click en Ingresar login")
    public void clickIngresarLogin() {
        login.getIngresarLogin().click();
    }

    @Step("Validar que se haya ingresado")
    public void verificarIngreso() {
        assertTrue("No se pudo ingresar, comprueba usuario y contraseña", login.getCheckGP().isDisplayed());
    }
    @Step("Click Continuar Login")
    public void clickContinuarLogin() {
        login.getContinuarLogin().click();
    }


    @Step("Click en Otro momento (Publicidad)")
    public void clickSacarPublicidad() {
        try {
            //WebElement EnOtroMomento = wait.until(ExpectedConditions.visibilityOf(login.getEnOtroMomento()));
            if(ultisGP.estaPresente(login.getEnOtroMomento(),1)){login.getEnOtroMomento().click();}
        } catch (TimeoutException e) {
            System.out.println("El elemento de Publicidad en otro Momento no esta presente en la pagina.");
        }
    }
    @Step("Tenemos otra propuesta (Publicidad)")
    public void clickSacarPublicidadTenemosOtraPropuesta() {
        //WebElement otraPropuesta = wait.until(ExpectedConditions.visibilityOf(login.getOtraPropuesta()));
        if(ultisGP.estaPresente(login.getOtraPropuesta(),1)){
            clickSalirPrestamo();
        }
        else{
            System.out.println("El elemento de Publicidad Otra propuesta, no esta presente en la pagina.");
        }
    }

    @Step("Click Salir Prestamo")
    public void clickSalirPrestamo() {
        try {
            //WebElement salirPrestamo = wait.until(ExpectedConditions.visibilityOf(login.getSalirPrestamo()));
            if(ultisGP.estaPresente(login.getSalirPrestamo(),1)){
            login.getSalirPrestamo().click();}
        } catch (Exception e) {
            System.out.println("El elemento de Publicidad en Prestamo no esta presente en la pagina.");
        }
    }

    @Step("Iniciar sesion con: {usuario}")
    public void iniciarSesion(String usuario) {
        try {
            String clave = UtilsGP.consultaDB("usuarios", usuario, "password");
            waitGP();
            clickIngresarStartPage();
            setUsuario(usuario);
            clickContinuarLogin();
            setPassword(clave);
            clickContinuarLogin();
            /*if(botonesGenericosPage.getContinuar().isDisplayed()){
                clickContinuarGenerico();
            }else{
                System.out.println("no hay boton");
            }*/
            verificarIngreso();
            //clickSacarPublicidad();
        } catch (Exception e) {
            System.out.println("No pudo iniciar sesion, error: " + e.getMessage());

        }
    }
    @Step("Iniciar sesion con: {usuario}")
    public void iniciarSesionFront(String usuario,String pass) {
        try {
            waitGP();
            clickIngresarStartPage();
            setUsuario(usuario);
            clickContinuarLogin();
            setPassword(pass);
            clickContinuarLogin();
            /*if(botonesGenericosPage.getContinuar().isDisplayed()){
                clickContinuarGenerico();
            }else{
                System.out.println("no hay boton");
            }*/
            verificarIngreso();
            //clickSacarPublicidad();
        } catch (Exception e) {
            System.out.println("No pudo iniciar sesion, error: " + e.getMessage());

        }
    }

    @Step("Iniciar sesion front y preparar bearer API para segundo factor comun con: {usuario}")
    public void iniciarSesionFrontConBearerApi(String usuario,String pass) {
        iniciarSesionFront(usuario, pass);
        if(pass != null){
            loginAPI.getBearerToken(usuario, pass);
        }else{
            System.out.println("====================== PASSWORD VACIA =================");
            fail("REVISAR PASSWORD EN BASE DE DATOS - PASSWORD NULA");
        }
    }


    @Step("Sanity test iniciar sesion con: {usuario}")
    public void loginParaSanityTest(String usuario) throws Exception {
        try {
            String clave = UtilsGP.consultaDB("usuarios", usuario, "password");
            waitGP();
            clickIngresarStartPage();
            setUsuario(usuario);
            clickContinuarGenerico();
            setPassword(clave);
            clickIngresarLogin();
            if (login.getCheckGP().isDisplayed()) {
                System.out.println("LOGIN EXITOSO " + usuario + " -------------");
                MenuSuperiorSteps menuSuperiorSteps = new MenuSuperiorSteps();
                menuSuperiorSteps.clickSalir();
            } else {
                System.out.println("USUARIO CON ERROR EN LOGIN " + usuario + " -------------");
                clickIconoEditarUsuario();
                clickVolverFlechaGenerico();
            }
        } catch (Exception e) {
            System.out.println("No pudo iniciar sesion, error: " + e.getMessage());
            throw new Exception("Error dato" + usuario);
        }
    }

    @Step("Click en Blanquear contraseña")
    public void clickBlanquearContrasenia() {
        assertTrue("Boton Blanquear contraseña no encontrado", login.getBlanquearContrasenia().isDisplayed());
        login.getBlanquearContrasenia().click();
    }

    @Step("Click en boton Iniciar sesion")
    public void clickBotonIniciarSesion() {
        assertTrue("Boton Iniciar sesion no se encuentra", ultisGP.estaPresente(login.getBotonIniciarSesion(),1));
        login.getBotonIniciarSesion().click();
    }

    @Step("Click icono editar usuario")
    public void clickIconoEditarUsuario() {
        assertTrue("Editar usuario no se encuentra", login.getIconoEditarUsuario().isDisplayed());
        login.getIconoEditarUsuario().click();
    }

    @Step("Se valida si el usuario esta cargado")
    public void validarUsuarioCargado() {
        if (login.getInputPassword().isDisplayed()) {
            clickIconoEditarUsuario();
            clickVolverFlechaGenerico();
            clickIngresarStartPage();
        }
    }

    @Step("Click Recuperar usuario")
    public void clickRecuperarUsuario() {
        login.getRecuperarUsuario().click();
    }


    @Step("Set nro de cuil {nroDocumento}")
    public void setNumeroCuil(String nroDocumento) {
        login.getInputDocumento().sendKeys(nroDocumento);
    }

    @Step("Validar saludo")
    public void verificarSaludo() {
        assertTrue("No se pudo ingresar, comprueba usuario y contraseña",login.getSaludo().isDisplayed());
    }

    @Step("Setear clave mediante teclado virtual")
    public void setClaveTecladoVirtual(String clave) {
        String password = clave;
        login.shiftButtonTecladoVirtual().click();
        for (char character : password.toCharArray()) {
            if (Character.isLetter(character)) {
                if (Character.isUpperCase(character)) {
                    login.shiftButtonTecladoVirtual().click();
                    setTecladoVirtual(character);
                    login.shiftButtonTecladoVirtual().click();
                } else {
                    setTecladoVirtual(character);
                }
            } else {
                login.numberSymbolButtonTecladoVirtual().click();
                setTecladoVirtual(character);
                login.abcButtonTecladoVirtual().click();
            }
        }
    }

    @Step("Ingresar clave desde teclado virtual")
    public void setTecladoVirtual(char clave) {
        login.setTeclaVirtual(clave).click();
    }

    public void iniciarSesionTecladoVirtual(String usuario, String clave) {
        try {
            //String clave = UtilsGP.consultaDB("usuarios", usuario, "password");
            waitGP();
            clickIngresarStartPage();
            UtilsGP.fixedWait(3000);
            setUsuario(usuario);
            clickContinuarGenerico();
            login.btnTecladoVirtual().click();
            setClaveTecladoVirtual(clave);
            login.enterButtonTecladoVirtual().click();
            clickSacarPublicidad();

        } catch (Exception e) {
            System.out.println("No pudo iniciar sesion, error: " + e.getMessage());

        }
    }


    @Step("Logeo por Api en Individuos")
    public void loginForAPI(String usuarios){
        try {
                String pass = UtilsGP.consultaDB("usuarios", usuarios, "password");
                loginAPI.getBearerToken(usuarios, pass);
        }catch (Exception e){
            System.out.println("No se pudo iniciar session por API: " + e.getMessage());
        }
    }

    @Step ("Logeo de usuario Por API Sin Escritorio")
    public void loginAPI(String usuario,String password){
        if(password != null){
            loginAPI.getBearerToken(usuario,password);
        }else{
            System.out.println("====================== CONTRASEÑA VACIA =================");
            fail("REVISAR CONTRASEÑA EN BASE DE DATOS - CONTRASEÑA NULA");
        }
    }
    @Step("Iniciar session por aqui visualizando el escritorio")
    public void iniciarSessionAlEscritorio(String usuario){
        String pass = UtilsGP.consultaDB("usuarios", usuario, "password");
        if(pass != null){
            String persistSession = loginAPI.getBearerAndPersistSession(usuario,pass);
            System.out.println("inicio sesion intento 1");
            loginAlEscritorio(persistSession);
            verificarIngresoPersistSession(persistSession);
        }
    }
    @Step("Iniciar session por aqui visualizando el escritorio")
    public void iniciarSessionAlEscritorioConPass(String usuario,String pass){
        if(pass != null){
            String persistSession = loginAPI.getBearerAndPersistSession(usuario,pass);
            System.out.println("inicio sesion intento 1");
            loginAlEscritorio(persistSession);
            verificarSaludo();
        }
    }
    @Step("Iniciar session al escritorio por api a la sección : {seccion}")
    public void iniciarSessionFront(String user, String pass,String seccion){
        if(pass != null){
            String persistSession = loginAPI.getBearerAndPersistSession(user,pass);
            System.out.println("inicio sesion intento 1");
            loginConSeccion(persistSession,seccion);
        }
    }
    @Step("Incrustando la sección en el driver")
    private void loginAlEscritorio(String persistSession) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("sessionStorage.setItem('persist:session',arguments[0]);",persistSession);
        driver.navigate().to(System.getProperty("appUrl").replace("startPage","") + "desktop");
    }
    @Step("Incrustando en el driver a la sección")
    private void loginConSeccion(String persistSession, String seccion) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("sessionStorage.setItem('persist:session',arguments[0]);",persistSession);
        driver.navigate().to(System.getProperty("appUrl").replace("startPage","") + ""+seccion+"");
    }

    @Step("Validar que se haya ingresado")
   public void verificarIngresoPersistSession(String persistSession){
        UtilsGP utilsGP = new UtilsGP();
        LoginPage login = new LoginPage();
        if (!utilsGP.estaPresente(login.getCheckGP().getElementBy(), 10)){;
            JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
            jsExecutor.executeScript(String.format("sessionStorage.setItem('%s', '%s');", "persist:session", persistSession));
            driver.navigate().to(System.getProperty("appUrl").replace("startPage","") + "desktop");

        }
        assertTrue("No se pudo ingresar, comprueba usuario y contraseña", login.getCheckGP().isDisplayed());
    }

   /*@Step("Logeo por Api para obtener segundo factor")
    public String loginAPIForSecondFactor(String usuarios){
        String code = null;
        try {
            if (loginAPI.accessToken == null && loginAPI.origin == null && !usuarios.contains("@")) {

                String pass = UtilsGP.consultaDB("usuarios", usuarios, "password");
                loginAPI.getBearerToken(usuarios, pass);
                System.out.println("================= TELEFONO : " + loginAPI.origin);
                code = loginAPI.getSecondFactor();

            }else if(usuarios.equals("email")){

                code = loginAPI.getSecondFactorMail();

            }else if(usuarios.contains("@")){

                code = loginAPI.getSecondFactorMail(usuarios);

            }else if(usuarios.startsWith("11") && usuarios.length()==10){

                code = loginAPI.getSecondFactor(usuarios);
                loginAPI.accessToken = null;
                loginAPI.origin = null;

            }else{

                code = loginAPI.getSecondFactor();

            }
        } catch (Exception e) {
            System.out.println("No se pudo iniciar session por API: " + e.getMessage());
        }

        return code;
    }

    @Step("Logeo por Api para obtener segundo factor")
    public String loginAPIForSecondFactorUser(String mail,String usuario) throws InterruptedException {
        String code = null;
        try {
                //logueamos
                loginForAPI(usuario);

                code = loginAPI.getSecondFactorMailUser(mail);


        } catch (Exception e) {
            System.out.println("No se pudo iniciar session por API: " + e.getMessage());
        }

        return code;
    }*/
    @Step("Verificacion de mail recibido para recupero de usuario")
    public void validarMailRecibido(String codigo, String user) {
        assertEquals("No se pude recuperar el mail","Tu usuario de Home Banking es: "+user.toLowerCase()+"",codigo);
    }
    @Step("Obtener el ID de la Imagen de Perfil Usuario")
    public int obtenerIdImagenPerfilUsuario(String user){
        return loginAPI.tomarIdImagen(user);
    }

    @Step("Verificar telefono es igual al del test")
    public void verificarTelefono(){
        String telefono, telefonoTC;
        telefonoTC = Constantes.TELEFONO;
        telefono = loginAPI.origin.substring(2);
        if(telefono.equals(telefonoTC)){
            int telModificado =  Integer.parseInt(telefonoTC);
            telModificado = telModificado +1;
            Constantes.TELEFONO =  String.valueOf(telModificado);
            System.out.println("============= NUEVO NUMERO MODIFICADO " + Constantes.TELEFONO);
        }else{
            System.out.println("=========== LOS DATOS NO COINCIDEN ======================");
        }
    }
    @Step("Verificar correo es igual al del test")
    public void verificarGmail(){
        String correo, correoTC;
        correoTC = Constantes.CORREO;
        correo = loginAPI.email;
        if(correo.equals(correoTC)){
            Constantes.CORREO = ultisGP.generarUsuarioCorreoAleatorio();
            System.out.println("============= NUEVO CORREO MODIFICADO " + Constantes.CORREO);
        }else{
            System.out.println("=========== LOS DATOS NO COINCIDEN ======================");
        }
    }

    @Step("Click en Crear Usuario")
    public void clickCrearUsuario() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@aria-label='Crear usuario']")));
        login.getCrearNuevoUsuario().click();
    }

    @Step("Click en Checkbox -No soy un robot-")
    public void clickCheckboxCaptcha() {
        //scrollTo(By.xpath("//div[@class = 'recaptcha-checkbox-border']"));

        global.practia.jemeinsamstark.web.core.WebDriver.getInstance().switchTo().frame(0);

        login.getCheckCaptcha().click();

        global.practia.jemeinsamstark.web.core.WebDriver.getInstance().switchTo().defaultContent();

    }

    @Step("Click en Checkbox -Acepta términos y condiciones-")
    public void clickCheckboxRobot() {
        login.getCheckAceptaTerminos().click();
    }

    @Step("Set nro de cuil {nroDocumento}")
    public void setNumeroCuil2(String nroDocumento) {
        login.getInputCuil().sendKeys(nroDocumento);
        UtilsGP.fixedWait(7000);
    }

    @Step("Validar botón -Continuar- deshabilitado")
    public void validarBtnContinuarDeshabilitado() {

        assertFalse("El botón Continuar aparece habilitado", login.getContinuarLogin().isEnabled());
        Assert.assertFalse("El botón no tiene color", Boolean.parseBoolean(login.getContinuarLogin().getCssValue("008000")));
    }

    @Step("Validar botón -Continuar- habilitado y con color ")
    public void validarBtnContinuarHabilitado() {


        String valor;

        valor = login.getInputDocumento().getText();

        if (valor.length() == 11) {

            Assert.assertTrue("El botón no está habilitado", login.getContinuarLogin().isEnabled());
            Assert.assertTrue("El botón no tiene color", Boolean.parseBoolean(login.getContinuarLogin().getCssValue("008000")));
        };

    }

    @Step("Verificar logo Advertencia -!-")
    public void validarLogoAdvertencia(){

        String color = login.getLogoAdvertenciaColor().getCssValue("background-color");

        Assert.assertTrue("logo de advertencia no aparece visible", login.getLogoAdvertencia().isDisplayed());
        Assert.assertEquals("rgba(255, 241, 184, 1)", color  );
    }





}
