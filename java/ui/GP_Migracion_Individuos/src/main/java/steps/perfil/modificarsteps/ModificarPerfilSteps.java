package steps.perfil.modificarsteps;

import api.LoginAPI;
import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;
import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.botonesgenericos.BotonesGenericosPage;
import pages.perfil.modificarperfil.Modificar;
import steps.botonesgenericos.BotonesGenericosSteps;
import utils.RestAssuredApi;
import utils.UtilsGP;

public class ModificarPerfilSteps extends BotonesGenericosSteps {
    String calle = null;
    String localidad = null;
    String provincia = null;
    String localidadSinCp = null;
    UtilsGP utilsGP = new UtilsGP();
    private Modificar modificar = new Modificar();
    LoginAPI loginAPI = new LoginAPI();
    RestAssuredApi restAssuredApi = new RestAssuredApi();
    private BotonesGenericosPage botonesGenericos = new BotonesGenericosPage();

    @Step("Click Icono Editar")
    public void clickIconoEditar() {
        modificar.getEditarIcono().click();
    }

    @Step("Setear Cod.Area")
    public void setCodArea(String codArea) {
        modificar.getCodArea().sendKeys(codArea);
    }

    @Step("Click imagen avatar")
    public void clickAvatarNum(int num) {
        modificar.getAvatar(num).click();
    }

    @Step("Setear Telefono")
    public void setTelefono(String telefono) {
        modificar.getInputTelefono().sendKeys(telefono);
    }

    @Step("Setear Correo")
    public void setEditarCorreo(String correo) {
        modificar.getModificarCorreo().sendKeys(correo);
    }

    @Step("Setear Contraseña Antigua")
    public void setContrasenia(String pass) {
        modificar.getInputContraseniaAntigua().sendKeys(pass);
    }

    @Step("Setear Contraseña Nueva")
    public void setContrasenianew(String passNew) {
        modificar.getInputContraseniaNew().sendKeys(passNew);
    }

    @Step("Confirmar Contraseña")
    public void setContraseniaConfirmar(String passConfirm) {
        modificar.getInputConfirmarContraseniaNew().sendKeys(passConfirm);
    }

    @Step("Click en Avatar ")
    public void clickEnAvatar() {
        modificar.getSeleccionDeAvateres().click();
    }

    @Step("Click Desplegable Domicilio")
    public void clickDomicilio() {
        modificar.getDesplegableDomicilio().click();
        UtilsGP.fixedWait(3000);
    }

    @Step("Click Provincia")
    public void clickProvincia() {
        scrollTo(By.id("province"));
        modificar.getDesplegableProvincia().click();
        UtilsGP.fixedWait(3000);
    }

    @Step("Setear Provincia")
    public void ingresarProvincia() {
        // scrollTo(By.id("province"));
        botonesGenericos.getlistaDesplegableGenerico("BUENOS AIRES").click();
        UtilsGP.fixedWait(3000);
        provincia = modificar.datoProvincia().getAttribute("value");
    }

    @Step("Click Localidad")
    public void clickLocalidad() {
        modificar.getDesplegableLocalidad().click();
        UtilsGP.fixedWait(3000);
    }

    @Step("Setear Localidad")
    public void ingresarLocalidad() {
        botonesGenericos.getlistaDesplegableGenerico("Seleccionar").click();
        UtilsGP.fixedWait(2000);
        botonesGenericos.getlistaDesplegableGenerico("1761").click();
        UtilsGP.fixedWait(3000);
        localidad = modificar.datoLocalidad().getAttribute("value");
        localidadSinCp = localidad.split(" - ", 2)[1];
    }

    @Step("Click Codigo Postal")
    public void clickCodigoPostal() {
        modificar.getDesplegableCodigoPostal().click();
    }

    @Step("Click Sin Piso")
    public void clickSinPiso() {
        modificar.getSinPiso().click();
    }

    @Step("Click Sin Numero")
    public void clickSinNumero() {
        modificar.getSinNumero().click();
    }

    @Step("Setear Calle")
    public void setCalle(String calle) {
        modificar.getCalle().sendKeys(calle);
    }
    public String generarString(int largo, String string) {
        calle = RandomStringUtils.random(largo, string);
        return calle;
    }
    @Step("Guardar direccion")
    public void guardarDireccion() {
        localidad = modificar.getDesplegableLocalidad().getText();
        System.out.println("Calle guardada: " + calle + " Localidad: " + localidad);
    }

    @Step("Validar domicilio")
    public void validarDomicilio() {

        Assert.assertTrue("No se encuentra la modificacion correcta de la calle: " + calle.toUpperCase(), modificar.getTextsPage(calle.toUpperCase()).isDisplayed());
        Assert.assertTrue("No se encuentra la modificacion correcta de la localidad: " + localidad.toUpperCase(), modificar.getTextsPage(localidad.toUpperCase()).isDisplayed());
    }

    @Step("Setear Número")
    public void setNumero(String num) {
        modificar.getNumero().sendKeys(num);
    }

    @Step("Setear Piso")
    public void setPiso(String piso) {
        modificar.getPiso().sendKeys(piso);
    }

    @Step("Setear Apartamento")
    public void setApartamento(String apart) {
        modificar.getApartamento().sendKeys(apart);
    }


    @Step("Verificación Error Cod Area")
    public void verifyErrorCodArea() {
        Assert.assertTrue("El código de área ingresado no es válido", modificar.getErrorCodArea().isDisplayed());
    }

    @Step("Verificación Error Num")
    public void verifyErrorTel() {
        Assert.assertTrue("El número ingresado es inválido", modificar.getErrorTel().isDisplayed());
    }

    @Step("Validar correo")
    public void validarCorreo(String email) {
        Assert.assertTrue("Email incorrecto", modificar.getVerificarNuevoEmail(email).isDisplayed());
    }

    @Step("Validar Telefono")
    public void validarCambioTelefono() {

        String origin = loginAPI.getTelefono();

        assert loginAPI.originOriginal.equals(origin) : "NO SE PRODUJO EL CAMBIO DE TELEFONO";

    }
    @Step("Cambiar Imagen Perfil")
    public void cambiarImagenAvatar(int idImagenActual) {

        int IdImagenNueva = 0;

        if (idImagenActual > 1) {

            IdImagenNueva = idImagenActual - 1;

        } else {

            IdImagenNueva = idImagenActual + 1;

        }
        clickAvatarNum(IdImagenNueva);

    }
    @Step("Validar Email")
    public void validarCambioEmail() {

        String email = loginAPI.getMail();

        assert LoginAPI.emailOriginal.equals(email) : "NO SE PRODUJO EL CAMBIO DE EMAIL";

    }

    @Step("Click Modificaar domicilio")
    public void clickModificarDomicilio() {
        botonesGenericos.getlistaDesplegableGenerico("Modificar domicilio").click();
    }

    @Step("Validación de Datos")
    public void verificarDatos() {

        String calleModificada = modificar.datoModificado("Calle").getText();
        Assert.assertTrue("El dato Calle ingresado no esperado", calleModificada.equals("ROSARIO"));
        String numeroModificada = modificar.datoModificadoNúmero().getText();
        Assert.assertTrue("El dato número ingresado no esperado", numeroModificada.equals("772"));
        String pisoModificada = modificar.datoModificado("Piso").getText();
        Assert.assertTrue("El dato piso ingresado no esperado", pisoModificada.equals("3"));
        String departamentoModificada = modificar.datoModificado("Departamento").getText();
        Assert.assertTrue("El dato departemento ingresado no esperado", departamentoModificada.equals("24"));
        String provinciaModificada = modificar.datoModificado("Provincia").getText();
        Assert.assertTrue("El dato provincia ingresado no esperado", provincia.equals(provinciaModificada));
        String localidadModificada = modificar.datoModificado("Localidad").getText();
        Assert.assertTrue("El dato localidad ingresado no esperado", localidadSinCp.equals(localidadModificada));
    }
}



