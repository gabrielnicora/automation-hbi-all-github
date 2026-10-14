package steps.login;

import io.qameta.allure.Step;
import org.junit.Assert;
import pages.login.PantallaPrincipalPage;
import steps.botonesgenericos.BotonesGenericosSteps;
import utils.UtilsGP;

public class PantallaPrincipalSteps extends BotonesGenericosSteps{

    private PantallaPrincipalPage pantallaPrincipal = new PantallaPrincipalPage();
    private UtilsGP utilsGP = new UtilsGP();
    private PantallaPrincipalPage tiendaClic = new PantallaPrincipalPage();
    private PantallaPrincipalPage TelefonosUtiles = new PantallaPrincipalPage();



    @Step("Click en Tienda Clic ")
    public void btnTiendaClic(){
        Assert.assertTrue("Boton Tienda Clic no encontrado", tiendaClic.btnTiendaClic().isDisplayed());
        pantallaPrincipal.btnTiendaClic().click();
    }
    @Step("Abrir tabs Tienda Clic")
    public void TiendaClicTab() throws InterruptedException {
        utilsGP.ManageTab();
        Assert.assertTrue("Logo Visible", tiendaClic.getLogoTiendaClic().isDisplayed());
        utilsGP.backMainTap();
    }
    @Step("Click en Telefonos Utiles")
    public void TelefonosUtiles(){
        Assert.assertTrue("Boton Telefonos Utiles no encontrado", pantallaPrincipal.btnTelefonosUtiles().isDisplayed());
        pantallaPrincipal.btnTelefonosUtiles().click();
    }
    @Step("Validar Telefonos Utiles")
    public void TelefonosUtilesTab(){
        utilsGP.ManageTab();
        Assert.assertTrue("Telefono Oficial Visible", TelefonosUtiles.getTelefonoOficial().isDisplayed());
        utilsGP.backMainTap();
    }
    @Step("Click Crear nuevo usuario")
    public void CrearNuevoUsuario(){

        Assert.assertTrue("Boton Crear nuevo usuario no encontrado", pantallaPrincipal.getCrearNuevoUsuario().isDisplayed());
        pantallaPrincipal.getCrearNuevoUsuario().click();
        UtilsGP.fixedWait(2000);
    }
    @Step("Click Terminos y ondiciones")
    public void TerminosYCondiciones(){
        Assert.assertTrue("Boton Terminos y condiciones no encontrado", pantallaPrincipal.getTerminosYCondiciones().isDisplayed());
        pantallaPrincipal.getTerminosYCondiciones().click();
    }
    @Step("Validacion Titulo Terminos y Condicoones")
    public void TituloTerminosYcondiciones(){
        Assert.assertTrue("Titulo Terminos y condiciones no encontrado", pantallaPrincipal.TituloTerminosYCondiciones().isDisplayed());
    }
    @Step("Click informacion de seguridad")
    public void ClickInfoDeSeguridad(){
        Assert.assertTrue("Boton Informacion de seguridad", pantallaPrincipal.getInfoSeguridad().isDisplayed());
        pantallaPrincipal.getInfoSeguridad().click();

    }

    /**
     * seccion removida, considerar eliminar metodo, reemplazada por Seguridad para usar Home Banking / Office Banking
     */
    @Deprecated
    @Step("Validacion Titulo Preguntas Frecuentes")
    public void PreguntasFrecuentes(String titulo){
        utilsGP.ManageTab();
        Assert.assertTrue("Preguntas Frecuentes", pantallaPrincipal.TituloPreguntasFrecuentes(titulo).isDisplayed());
        utilsGP.backMainTap();
    }

    @Step("Validacion Titulo Seguridad para usar Home Banking / Office Banking")
    public void seguridadParaUsarHomeBanking(){
        utilsGP.ManageTab();
        Assert.assertTrue("Seguridad en Internet", pantallaPrincipal.tituloSeguridadParaUsarHomeBanking().isDisplayed());
        utilsGP.backMainTap();
    }

    @Step("Click no sos cliente aun registrarme")
    public void clickRegistrarme(){
        pantallaPrincipal.getBtnRegistrarme().isDisplayed();
        pantallaPrincipal.getBtnRegistrarme().click();
    }
}
